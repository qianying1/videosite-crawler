package com.crawl.videosite.task.acfun.api.impl;

import com.crawl.videosite.parser.acfun.AcfunMainPageParser;
import com.crawl.videosite.task.acfun.api.AbstractAcfunTypePageTask;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * a站视频类型页面爬取任务
 */
public class AcfunTypePageTask extends AbstractAcfunTypePageTask {
    private static Logger logger = LoggerFactory.getLogger(AcfunTypePageTask.class);
    private AcfunMainPageParser pageParser;

    public AcfunTypePageTask() {
        pageParser = new AcfunMainPageParser();
    }

    @Override
    protected void handleMainPage(HtmlPage page) {
        if (page == null)
            return;
        try {
            pageParser.parseMainPage(page);
        } catch (Exception e) {
            logger.error("分析主页时产生异常",e);
            return;
        }
        not_completed = false;
    }
}
