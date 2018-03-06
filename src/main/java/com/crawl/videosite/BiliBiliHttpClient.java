package com.crawl.videosite;

import com.crawl.Main;
import com.crawl.core.htmlunit.AbstractHtmlUnit;
import com.crawl.core.htmlunit.IHtmlUnit;
import com.crawl.core.util.*;
import com.crawl.proxy.BiliBiliProxyHttpClient;
import com.crawl.videosite.entity.BangumiGuochanPersistence;
import com.crawl.videosite.entity.VideoSiteDynamicPersistence;
import com.crawl.videosite.entity.VideoSiteNewVideoPersistence;
import com.crawl.videosite.entity.VideoSiteRankPersistence;
import com.crawl.videosite.task.bilibili.BiliBiliDetailListPageTask;
import com.crawl.videosite.task.bilibili.BiliBiliDetailPageTask;
import com.crawl.videosite.task.bilibili.api.BangumiGuochanTask;
import com.crawl.videosite.task.bilibili.api.NewVideoListJsonTask;
import com.crawl.videosite.task.bilibili.api.VideoDynamicListJsonTask;
import com.crawl.videosite.task.bilibili.api.VideoRankListJsonTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by qianhaibin on 2018/2/27.
 */
public class BiliBiliHttpClient extends AbstractHtmlUnit implements IHtmlUnit {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private volatile static BiliBiliHttpClient instance;
    /**
     * 统计用户数量
     */
    public static AtomicInteger parseUserCount = new AtomicInteger(0);
    private static long startTime = System.currentTimeMillis();
    public static volatile boolean isStop = false;

    public static BiliBiliHttpClient getInstance() {
        if (instance == null) {
            synchronized (BiliBiliHttpClient.class) {
                if (instance == null) {
                    instance = new BiliBiliHttpClient();
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
//    private static String authorization;
    private BiliBiliHttpClient() {
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
        //详情页下载线程池
        detailPageThreadPool = new SimpleThreadPoolExecutor(Config.downloadThreadSize,
                Config.downloadThreadSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                "biliBiliDetailPageThreadPool");

        //列表页下载线程池
        listPageThreadPool = new SimpleThreadPoolExecutor(50, 80,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(5000),
                new ThreadPoolExecutor.DiscardPolicy(), "biliBiliListPageThreadPool");
        new Thread(new ThreadPoolMonitor(detailPageThreadPool, "biliBiliDetailPageDownloadThreadPool")).start();
        new Thread(new ThreadPoolMonitor(listPageThreadPool, "biliBiliListPageDownloadThreadPool")).start();

        //详情列表页下载线程池
        detailListPageThreadPool = new SimpleThreadPoolExecutor(Config.downloadThreadSize,
                Config.downloadThreadSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(2000),
                new ThreadPoolExecutor.DiscardPolicy(),
                "biliBiliDetailListPageThreadPool");
        new Thread(new ThreadPoolMonitor(detailListPageThreadPool, "biliBiliDetailListPageThreadPool")).start();

    }

    /**
     * 开始对链接进行爬取(不需要登录的爬取)
     *
     * @param url
     */
    public void startCrawl(String url) {
        detailPageThreadPool.execute(new BiliBiliDetailPageTask(url, Config.bilibiliIsProxy));
        manageHttpClient();
    }

    /**
     * 开始对种子链接进行爬取（需要登录的爬取）
     */
    @Override
    public void startCrawl() {
        /*String startUrl = Constants.BILIBILI_INDEX_URL;
        WebRequest request = null;
        WebRequestParams params = new WebRequestParams();
        try {
            request = HtmlUnitWebClientUtil.getRequest(startUrl, null, params, null);
        } catch (MalformedURLException e) {
            logger.error("fail to new WebRequest from uri: " + startUrl, e);
        } catch (IOException e) {
            logger.error("fail to new WebRequest from uri: " + startUrl, e);
        }*/
//        detailListPageThreadPool.execute(new BiliBiliDetailListPageTask(request, Config.bilibiliIsProxy));
        //活动视频列表
        dynamicVideoCrawler();
        //等级视频列表
        rankVideoCrawler();
        //新视频列表
        newVideoListCrawler();
        //---------------------------------------bangumi begin------------------------------------------------------------------//
//        bangumiGuochangCrawler();
        //---------------------------------------bangumi end--------------------------------------------------------------------//
        manageHttpClient();
    }

    /**
     * 爬取活动视频列表api
     */
    private void dynamicVideoCrawler() {
        VideoSiteDynamicPersistence persistence = null;
        try {
            persistence = (VideoSiteDynamicPersistence) HttpClientUtil.deserializeObject(Constants.biliBiliDynamicVideoDataSerialPath);
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            logger.error("fail to deserialize object from url: " + Constants.biliBiliDynamicVideoDataSerialPath, e);
        }
        if (persistence != null) {
            detailListPageThreadPool.execute(new VideoDynamicListJsonTask(persistence.getBiliBili_rid(), persistence.getBiliBili_original(), persistence.getBiliBili_pn()));
        } else {
            detailListPageThreadPool.execute(new VideoDynamicListJsonTask(1l, 0l, 1));
        }
    }

    /**
     * 爬取等级视频列表api
     */
    private void rankVideoCrawler() {
        VideoSiteRankPersistence persistence = null;
        try {
            persistence = (VideoSiteRankPersistence) HttpClientUtil.deserializeObject(Constants.biliBiliRankVideoDataSerialPath);
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            logger.error("fail to deserialize object from url: " + Constants.biliBiliRankVideoDataSerialPath, e);
        }
        if (persistence != null) {
            detailListPageThreadPool.execute(new VideoRankListJsonTask(persistence.getBiliBili_rid()));
        } else {
            detailListPageThreadPool.execute(new VideoRankListJsonTask(1l));
        }
    }

    /**
     * 爬取新视频列表api
     */
    private void newVideoListCrawler() {
        VideoSiteNewVideoPersistence persistence = null;
        try {
            persistence = (VideoSiteNewVideoPersistence) HttpClientUtil.deserializeObject(Constants.biliBiliNewVideoDataSerialPath);
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            logger.error("fail to deserialize object from url: " + Constants.biliBiliNewVideoDataSerialPath, e);
        }
        if (persistence != null) {
            detailListPageThreadPool.execute(new NewVideoListJsonTask(persistence.getBiliBili_rid(), persistence.getBiliBili_original(), persistence.getBiliBili_pn()));
        } else {
            detailListPageThreadPool.execute(new NewVideoListJsonTask(0l, 0l, 1));
        }
    }

    /**
     * 国产连载api（获取到的是每天更新的数据）
     */
    private void bangumiGuochangCrawler() {
        detailListPageThreadPool.execute(new BangumiGuochanTask());
    }

    /**
     * 初始化authorization
     *
     * @return
     */
    /*private String initAuthorization() {
        logger.info("初始化authoriztion中...");
        String content = null;

        BiliBiliGeneralPageTask generalPageTask = new BiliBiliGeneralPageTask(Config.biliBiliStartURL, true);
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
    }*/

    /**
     * 管理b站客户端
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
                BiliBiliProxyHttpClient.getInstance().getProxyTestThreadExecutor().shutdownNow();
                //关闭代理下载页线程池
                BiliBiliProxyHttpClient.getInstance().getProxyDownloadThreadExecutor().shutdownNow();

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
