package com.crawl.videosite;

import com.crawl.Main;
import com.crawl.core.httpclient.AbstractHttpClient;
import com.crawl.core.httpclient.IHttpClient;
import com.crawl.core.util.Config;
import com.crawl.core.util.Constants;
import com.crawl.core.util.SimpleThreadPoolExecutor;
import com.crawl.core.util.ThreadPoolMonitor;
import com.crawl.proxy.ProxyHttpClient;
import com.crawl.videosite.dao.impl.VideoSiteDao1Imp;
import com.crawl.videosite.task.bilibili.DetailListPageTask;
import com.crawl.videosite.task.bilibili.DetailPageTask;
import com.crawl.videosite.task.bilibili.GeneralPageTask;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by qianhaibin on 2018/2/27.
 */
public class LetvHttpClient extends AbstractHttpClient implements IHttpClient {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private volatile static LetvHttpClient instance;
    /**
     * 统计用户数量
     */
    public static AtomicInteger parseUserCount = new AtomicInteger(0);
    private static long startTime = System.currentTimeMillis();
    public static volatile boolean isStop = false;

    public static LetvHttpClient getInstance() {
        if (instance == null) {
            synchronized (LetvHttpClient.class) {
                if (instance == null) {
                    instance = new LetvHttpClient();
                }
            }
        }
        return instance;
    }

    /**
     * 详情页下载线程池
     */
    private ThreadPoolExecutor detailPageThreadPool;
    /**
     * 列表页下载线程池
     */
    private ThreadPoolExecutor listPageThreadPool;
    /**
     * 详情列表页下载线程池
     */
    private ThreadPoolExecutor detailListPageThreadPool;
    /**
     * request　header
     * 获取列表页时，必须带上
     */
    private static String authorization;

    private LetvHttpClient() {
        initHttpClient();
        intiThreadPool();
    }

    /**
     * 初始化HttpClient
     */
    @Override
    public void initHttpClient() {
        if (Config.dbEnable) {
            VideoSiteDao1Imp.DBTablesInit();
        }
    }

    /**
     * 初始化线程池
     */
    private void intiThreadPool() {
        //详情页线程池
        detailPageThreadPool = new SimpleThreadPoolExecutor(Config.downloadThreadSize,
                Config.downloadThreadSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                "detailPageThreadPool");

        //列表页线程池
        listPageThreadPool = new SimpleThreadPoolExecutor(50, 80,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(5000),
                new ThreadPoolExecutor.DiscardPolicy(), "listPageThreadPool");
        new Thread(new ThreadPoolMonitor(detailPageThreadPool, "DetailPageDownloadThreadPool")).start();
        new Thread(new ThreadPoolMonitor(listPageThreadPool, "ListPageDownloadThreadPool")).start();
        detailListPageThreadPool = new SimpleThreadPoolExecutor(Config.downloadThreadSize,
                Config.downloadThreadSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(2000),
                new ThreadPoolExecutor.DiscardPolicy(),
                "detailListPageThreadPool");
        new Thread(new ThreadPoolMonitor(detailListPageThreadPool, "DetailListPageThreadPool")).start();

    }

    /**
     * 开始对链接进行爬取(不需要登录的爬取)
     *
     * @param url
     */
    public void startCrawl(String url) {
        detailPageThreadPool.execute(new DetailPageTask(url, Config.isProxy));
        manageHttpClient();
    }

    /**
     * 开始对链接进行爬取（需要登录的爬取）
     */
    @Override
    public void startCrawl() {
        authorization = initAuthorization();

        String startToken = Config.startUserToken;
        String startUrl = String.format(Constants.USER_FOLLOWEES_URL, startToken, 0);
        HttpGet request = new HttpGet(startUrl);
        request.setHeader("authorization", "oauth " + LetvHttpClient.getAuthorization());
        detailListPageThreadPool.execute(new DetailListPageTask(request, Config.isProxy));
        manageHttpClient();
    }

    /**
     * 初始化authorization
     *
     * @return
     */
    private String initAuthorization() {
        logger.info("初始化authoriztion中...");
        String content = null;

        GeneralPageTask generalPageTask = new GeneralPageTask(Config.letvStartURL, true);
        generalPageTask.run();
        content = generalPageTask.getPage().getHtml();

        Pattern pattern = Pattern.compile("https://static\\.zhihu\\.com/heifetz/main\\.app\\.([0-9]|[a-z])*\\.js");
        Matcher matcher = pattern.matcher(content);
        String jsSrc = null;
        if (matcher.find()) {
            jsSrc = matcher.group(0);
        } else {
            throw new RuntimeException("not find javascript url");
        }
        String jsContent = null;
        GeneralPageTask jsPageTask = new GeneralPageTask(jsSrc, true);
        jsPageTask.run();
        jsContent = jsPageTask.getPage().getHtml();

        pattern = Pattern.compile("oauth\\\"\\),h=\\\"(([0-9]|[a-z])*)\"");
        matcher = pattern.matcher(jsContent);
        if (matcher.find()) {
            String authorization = matcher.group(1);
            logger.info("初始化authoriztion完成");
            return authorization;
        }
        throw new RuntimeException("not get authorization");
    }

    public static String getAuthorization() {
        return authorization;
    }

    /**
     * 管理知乎客户端
     * 关闭整个爬虫
     */
    public void manageHttpClient() {
        while (true) {
            /**
             * 获取下载网页数
             */
            long downloadPageCount = detailListPageThreadPool.getTaskCount();

            if (downloadPageCount >= Config.downloadPageCount &&
                    !detailListPageThreadPool.isShutdown()) {
                isStop = true;
                ThreadPoolMonitor.isStopMonitor = true;
                detailListPageThreadPool.shutdown();
            }
            if (detailListPageThreadPool.isTerminated()) {
                //关闭数据库连接
                Map<Thread, Connection> map = DetailListPageTask.getConnectionMap();
                for (Connection cn : map.values()) {
                    try {
                        if (cn != null && !cn.isClosed()) {
                            cn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                //关闭代理检测线程池
                ProxyHttpClient.getInstance().getProxyTestThreadExecutor().shutdownNow();
                //关闭代理下载页线程池
                ProxyHttpClient.getInstance().getProxyDownloadThreadExecutor().shutdownNow();

                break;
            }
            double costTime = (System.currentTimeMillis() - startTime) / 1000.0;//单位s
            logger.debug("抓取速率：" + parseUserCount.get() / costTime + "个/s");
//            logger.info("downloadFailureProxyPageSet size:" + ProxyHttpClient.downloadFailureProxyPageSet.size());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ThreadPoolExecutor getDetailPageThreadPool() {
        return detailPageThreadPool;
    }

    public ThreadPoolExecutor getListPageThreadPool() {
        return listPageThreadPool;
    }

    public ThreadPoolExecutor getDetailListPageThreadPool() {
        return detailListPageThreadPool;
    }
}
