package com.crawl;

import com.crawl.core.util.Config;
import com.crawl.proxy.*;
import com.crawl.videosite.BiliBiliHttpClient;
import com.crawl.videosite.dao.impl.VideoSiteDao1Imp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 网站数据爬取初始节点
 */
public class Initializer {
    private volatile static Initializer instance;
    public static Initializer getInstance() {
        if (instance == null) {
            synchronized (Initializer.class) {
                if (instance == null) {
                    instance = new Initializer();
                }
            }
        }
        return instance;
    }

    private Initializer() {
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
//        AcfunProxyHttpClient.getInstance().startCrawl();
//        AcfunHttpClient.getInstance().startCrawl();
        //b站
        BiliBiliProxyHttpClient.getInstance().startCrawl();
        BiliBiliHttpClient.getInstance().startCrawl();
        //斗鱼
//        DouyuProxyHttpClient.getInstance().startCrawl();
//        DouyuHttpClient.getInstance().startCrawl();
//        //爱奇艺
//        IqiyiProxyHttpClient.getInstance().startCrawl();
//        IqiyiHttpClient.getInstance().startCrawl();
//        //乐视
//        LetvProxyHttpClient.getInstance().startCrawl();
//        LetvHttpClient.getInstance().startCrawl();
//        //皮皮电影
//        PptvProxyHttpClient.getInstance().startCrawl();
//        PptvHttpClient.getInstance().startCrawl();
//        //搜狐
//        SohuProxyHttpClient.getInstance().startCrawl();
//        SohuHttpClient.getInstance().startCrawl();
//        //土豆
//        TudouProxyHttpClient.getInstance().startCrawl();
//        TudouHttpClient.getInstance().startCrawl();
//        //优酷
//        YoukuProxyHttpClient.getInstance().startCrawl();
//        YoukuHttpClient.getInstance().startCrawl();
//        //youtube
//        YoutubeProxyHttpClient.getInstance().startCrawl();
//        YoutubeHttpClient.getInstance().startCrawl();
    }

}
