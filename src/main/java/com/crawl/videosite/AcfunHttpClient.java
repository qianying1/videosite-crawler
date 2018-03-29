package com.crawl.videosite;

import com.crawl.core.htmlunit.AbstractHtmlUnit;
import com.crawl.core.htmlunit.IHtmlUnit;
import com.crawl.core.util.*;
import com.crawl.proxy.AcfunProxyHttpClient;
import com.crawl.videosite.entity.AcfunAuthorPersistence;
import com.crawl.videosite.entity.AcfunVideoListPersistence;
import com.crawl.videosite.entity.AcfunVideoPersistence;
import com.crawl.videosite.task.acfun.api.impl.AcfunAuthorApiTask;
import com.crawl.videosite.task.acfun.api.impl.AcfunTypePageTask;
import com.crawl.videosite.task.acfun.api.impl.AcfunVideoApiTask;
import com.crawl.videosite.task.acfun.api.impl.AcfunVideoListApiTask;
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
public class AcfunHttpClient extends AbstractHtmlUnit implements IHtmlUnit {
    private static Logger logger = LoggerFactory.getLogger(AcfunHttpClient.class);
    private volatile static AcfunHttpClient instance;
    /**
     * 统计用户数量
     */
    public static AtomicInteger parseUserCount = new AtomicInteger(0);
    private static long startTime = System.currentTimeMillis();
    public static volatile boolean isStop = false;

    public static AcfunHttpClient getInstance() {
        if (instance == null) {
            synchronized (AcfunHttpClient.class) {
                if (instance == null) {
                    instance = new AcfunHttpClient();
                }
            }
        }
        return instance;
    }

    /**
     * 详情列表页下载线程池
     */
    private ThreadPoolExecutor detailListPageThreadPool;
    /**
     * 作者信息
     */
    private AcfunAuthorApiTask acfunAuthorApiTask;
    /**
     * 视频类型
     */
    private AcfunTypePageTask acfunTypePageTask;
    /**
     * 视频
     */
    private AcfunVideoApiTask acfunVideoApiTask;
    /**
     * 视频列表
     */
    private AcfunVideoListApiTask acfunVideoListApiTask;

    /**
     * request　header
     * 获取列表页时，必须带上
     */
    private AcfunHttpClient() {
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
        /*detailPageThreadPool = new SimpleThreadPoolExecutor(Config.downloadThreadSize,
                Config.downloadThreadSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                "biliBiliDetailPageThreadPool");*/

        //列表页下载线程池
        /*listPageThreadPool = new SimpleThreadPoolExecutor(50, 80,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(5000),
                new ThreadPoolExecutor.DiscardPolicy(), "acfunListPageThreadPool");
        new Thread(new ThreadPoolMonitor(detailPageThreadPool, "acfunDetailPageDownloadThreadPool")).start();
        new Thread(new ThreadPoolMonitor(listPageThreadPool, "acfunListPageDownloadThreadPool")).start();*/

        //详情列表页下载线程池
        detailListPageThreadPool = new SimpleThreadPoolExecutor(Config.downloadThreadSize,
                Config.downloadThreadSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(2000),
                new ThreadPoolExecutor.DiscardPolicy(),
                "acfunDetailListPageThreadPool");
        new Thread(new ThreadPoolMonitor(detailListPageThreadPool, "acfunDetailListPageThreadPool")).start();

    }

    /**
     * 开始对链接进行爬取(不需要登录的爬取)
     *
     * @param url
     */
    public void startCrawl(String url) {
        /*detailPageThreadPool.execute(new AcfunDetailPageTask(url, Config.acfunIsProxy));
        manageHttpClient();*/
    }

    /**
     * 开始对种子链接进行爬取（需要登录的爬取）
     */
    @Override
    public void startCrawl() {
        //视频类型
        /*typeCrawler();
        //视频内容
        videoContentCrawler();
        //视频列表内容
        videoListCrawler();*/
        //视频作者信息
        authorDetail();
        manageHttpClient();
    }

    /**
     * 作者信息爬取
     */
    private void authorDetail(){
        AcfunAuthorPersistence persistence = null;
        try {
            persistence = (AcfunAuthorPersistence) HttpClientUtil.deserializeObject(Constants.acfunAuthorDataSerialPath);
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            logger.error("fail to deserialize object from url: " + Constants.acfunAuthorDataSerialPath, e);
        }
        if (persistence != null) {
            acfunAuthorApiTask= new AcfunAuthorApiTask(persistence.getUserId());
        } else {
            acfunAuthorApiTask= new AcfunAuthorApiTask(0l);
        }
        detailListPageThreadPool.execute(acfunAuthorApiTask);
    }

    /**
     * 视频类型抓取
     */
    private void typeCrawler(){
        acfunTypePageTask=new AcfunTypePageTask();
        detailListPageThreadPool.execute(acfunTypePageTask);
    }

    /**
     * 爬取视频列表api
     */
    private void videoContentCrawler() {
        AcfunVideoPersistence persistence = null;
        try {
            persistence = (AcfunVideoPersistence) HttpClientUtil.deserializeObject(Constants.acfunVideoDataSerialPath);
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            logger.error("fail to deserialize object from url: " + Constants.acfunVideoDataSerialPath, e);
        }
        if (persistence != null) {
            acfunVideoApiTask=new AcfunVideoApiTask(persistence.getContentId());
        } else {
            acfunVideoApiTask=new AcfunVideoApiTask(0l);
        }
        detailListPageThreadPool.execute(acfunVideoApiTask);
    }

    /**
     * 爬取视频列表内容
     */
    private void videoListCrawler() {
        AcfunVideoListPersistence persistence = null;
        try {
            persistence = (AcfunVideoListPersistence) HttpClientUtil.deserializeObject(Constants.acfunVideoListDataSerialPath);
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            logger.error("fail to deserialize object from url: " + Constants.acfunVideoListDataSerialPath, e);
        }
        if (persistence != null) {
            acfunVideoListApiTask= new AcfunVideoListApiTask(persistence);
        } else {
            acfunVideoListApiTask= new AcfunVideoListApiTask(new AcfunVideoListPersistence());
        }
        detailListPageThreadPool.execute(acfunVideoListApiTask);
    }


    /**
     * 管理a站客户端
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
                Map<Thread, Connection> authorMap = acfunAuthorApiTask.getConnectionMap();
                ProxyHttpClient.closeConnections(authorMap);
                //关闭数据库连接
                Map<Thread, Connection> typeMap = acfunTypePageTask.getConnectionMap();
                ProxyHttpClient.closeConnections(typeMap);
                //关闭数据库连接
                Map<Thread, Connection> videoMap = acfunVideoApiTask.getConnectionMap();
                ProxyHttpClient.closeConnections(videoMap);
                //关闭数据库连接
                Map<Thread, Connection> videoListMap = acfunVideoListApiTask.getConnectionMap();
                ProxyHttpClient.closeConnections(videoListMap);
                //关闭代理检测线程池
                AcfunProxyHttpClient.getInstance().getProxyTestThreadExecutor().shutdownNow();
                //关闭代理下载页线程池
                AcfunProxyHttpClient.getInstance().getProxyDownloadThreadExecutor().shutdownNow();
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

}
