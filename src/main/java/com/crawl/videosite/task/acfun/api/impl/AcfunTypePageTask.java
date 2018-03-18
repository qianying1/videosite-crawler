package com.crawl.videosite.task.acfun.api.impl;

import com.crawl.videosite.parser.acfun.AcfunMainPageParser;
import com.crawl.videosite.task.acfun.api.AbstractAcfunTypePageTask;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * a站视频类型页面爬取任务
 */
public class AcfunTypePageTask extends AbstractAcfunTypePageTask {

    private AcfunMainPageParser pageParser;

    public AcfunTypePageTask(){
        pageParser=new AcfunMainPageParser();
    }

    @Override
    protected void handleMainPage(HtmlPage page) {
        if (page==null)
            return;
        pageParser.parseMainPage(page);
    }
}
