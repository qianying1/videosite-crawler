package com.crawl.videosite;

import com.crawl.Main;
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


public class VideoSiteInitializer {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private volatile static VideoSiteInitializer instance;
    /**
     * 统计用户数量
     */
    public static AtomicInteger parseUserCount = new AtomicInteger(0);
    private static long startTime = System.currentTimeMillis();
    public static volatile boolean isStop = false;

    public static VideoSiteInitializer getInstance() {
        if (instance == null) {
            synchronized (VideoSiteInitializer.class) {
                if (instance == null) {
                    instance = new VideoSiteInitializer();
                }
            }
        }
        return instance;
    }

    private VideoSiteInitializer() {
        initVideoCrawlerWeapon();
    }

    /**
     * 初始化视频网站数据抓取之前需要准备的各种参数
     */
    private void initVideoCrawlerWeapon() {
        if (Config.dbEnable) {
            VideoSiteDao1Imp.DBTablesInit();
        }
    }

    /**
     * 开始对链接进行爬取（需要登录的爬取）
     */
    public void startVideoSitesCrawler() {
        //a站
//        AcfunHttpClient.getInstance().startCrawl();
        //b站
        BiliBiliHttpClient.getInstance().startCrawl();
        //斗鱼
//        DouyuHttpClient.getInstance().startCrawl();
//        //爱奇艺
//        IqiyiHttpClient.getInstance().startCrawl();
//        //乐视
//        LetvHttpClient.getInstance().startCrawl();
//        //皮皮电影
//        PptvHttpClient.getInstance().startCrawl();
//        //搜狐
//        SohuHttpClient.getInstance().startCrawl();
//        //土豆
//        TudouHttpClient.getInstance().startCrawl();
//        //优酷
//        YoukuHttpClient.getInstance().startCrawl();
//        //youtube
//        YoutubeHttpClient.getInstance().startCrawl();
    }

}
