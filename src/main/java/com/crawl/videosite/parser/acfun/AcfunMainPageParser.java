package com.crawl.videosite.parser.acfun;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * a站主页分析器
 */
public class AcfunMainPageParser {
    private static Logger logger = LoggerFactory.getLogger(AcfunMainPageParser.class);

    /**
     * 分析主页视频类型
     *
     * @param page
     */
    public void parseMainPage(HtmlPage page) {
        logger.info("开始分析a站主页>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
        System.out.println(page);
    }
}
