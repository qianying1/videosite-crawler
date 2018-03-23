package com.crawl.videosite.task.acfun.api.impl;

import com.crawl.videosite.parser.acfun.AuthorApiParser;
import com.crawl.videosite.task.acfun.api.AbstractAcfunAuthorApiTask;

import java.util.Map;

/**
 * a站视频作者信息爬取任务
 */
public class AcfunAuthorApiTask extends AbstractAcfunAuthorApiTask {
    /**
     * a站视频作者分析器
     */
    private AuthorApiParser authorApiParser;

    public AcfunAuthorApiTask(Long userId) {
        super(userId);
        authorApiParser = new AuthorApiParser();
    }

    /**
     * 进行数据分析处理
     *
     * @param jsonMap
     */
    @Override
    protected void handle(Map<String, Object> jsonMap) {
        if (jsonMap.isEmpty())
            return;
        authorApiParser.parseAuthorJson(jsonMap,getConnection());
    }
}
