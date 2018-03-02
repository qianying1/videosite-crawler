package com.crawl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 爬虫入口
 */
public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) {
        //开始进行视频网站的抓取
        Initializer.getInstance().startVideoSitesCrawler();
    }

}
