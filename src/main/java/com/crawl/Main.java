package com.crawl;

import com.crawl.proxy.ProxyHttpClient;
import com.crawl.videosite.VideoSiteInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 爬虫入口
 */
public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) {
        //代理抓取
        ProxyHttpClient.getInstance().startCrawl();
        //开始进行视频网站的抓取
        VideoSiteInitializer.getInstance().startVideoSitesCrawler();
    }
}
