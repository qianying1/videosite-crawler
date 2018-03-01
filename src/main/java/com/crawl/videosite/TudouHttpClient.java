package com.crawl.videosite;

import com.crawl.Main;
import com.crawl.core.htmlunit.AbstractHtmlUnit;
import com.crawl.core.htmlunit.IHtmlUnit;
import com.crawl.core.util.Config;
import com.crawl.core.util.Constants;
import com.crawl.core.util.SimpleThreadPoolExecutor;
import com.crawl.core.util.ThreadPoolMonitor;
import com.crawl.proxy.TudouProxyHttpClient;
import com.crawl.videosite.task.bilibili.BiliBiliDetailListPageTask;
import com.crawl.videosite.task.bilibili.BiliBiliDetailPageTask;
import com.crawl.videosite.task.bilibili.BiliBiliGeneralPageTask;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qianhaibin on 2018/2/27.
 */
public class TudouHttpClient extends AbstractHtmlUnit implements IHtmlUnit {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private volatile static TudouHttpClient instance;
    /**
     * 统计用户数量
     */
    public static AtomicInteger parseUserCount = new AtomicInteger(0);
    private static long startTime = System.currentTimeMillis();
    public static volatile boolean isStop = false;

    public static TudouHttpClient getInstance() {
        if (instance == null) {
            synchronized (TudouHttpClient.class) {
                if (instance == null) {
                    instance = new TudouHttpClient();
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

    private TudouHttpClient() {
        initHttpClient();
        intiThreadPool();
    }

    /**
     * 初始化HttpClient
     */
    @Override
    public void initHttpClient() {
        /*if (Config.dbEnable) {
            VideoSiteDao1Imp.DBTablesInit();
        }*/
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
                "tudouDetailPageThreadPool");

        //列表页线程池
        listPageThreadPool = new SimpleThreadPoolExecutor(50, 80,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(5000),
                new ThreadPoolExecutor.DiscardPolicy(), "tudouListPageThreadPool");
        new Thread(new ThreadPoolMonitor(detailPageThreadPool, "TudouDetailPageDownloadThreadPool")).start();
        new Thread(new ThreadPoolMonitor(listPageThreadPool, "TudouListPageDownloadThreadPool")).start();
        detailListPageThreadPool = new SimpleThreadPoolExecutor(Config.downloadThreadSize,
                Config.downloadThreadSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(2000),
                new ThreadPoolExecutor.DiscardPolicy(),
                "tudouDetailListPageThreadPool");
        new Thread(new ThreadPoolMonitor(detailListPageThreadPool, "TudouDetailListPageThreadPool")).start();

    }

    /**
     * 开始对链接进行爬取(不需要登录的爬取)
     *
     * @param url
     */
    public void startCrawl(String url) {
        detailPageThreadPool.execute(new BiliBiliDetailPageTask(url, Config.isProxy));
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
        request.setHeader("authorization", "oauth " + TudouHttpClient.getAuthorization());
//        detailListPageThreadPool.execute(new BiliBiliDetailListPageTask(request, Config.isProxy));
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

        BiliBiliGeneralPageTask generalPageTask = new BiliBiliGeneralPageTask(Config.tudouStartURL, true);
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
        BiliBiliGeneralPageTask jsPageTask = new BiliBiliGeneralPageTask(jsSrc, true);
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
                Map<Thread, Connection> map = BiliBiliDetailListPageTask.getConnectionMap();
                CommonHttpClient.closeConnections(map);
                //关闭代理检测线程池
                TudouProxyHttpClient.getInstance().getProxyTestThreadExecutor().shutdownNow();
                //关闭代理下载页线程池
                TudouProxyHttpClient.getInstance().getProxyDownloadThreadExecutor().shutdownNow();

                break;
            }
            double costTime = (System.currentTimeMillis() - startTime) / 1000.0;//单位s
            logger.debug("土豆抓取速率：" + parseUserCount.get() / costTime + "个/s");
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
