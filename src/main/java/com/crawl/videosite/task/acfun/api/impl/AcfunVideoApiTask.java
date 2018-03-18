package com.crawl.videosite.task.acfun.api.impl;

import com.crawl.videosite.parser.acfun.VideoApiParser;
import com.crawl.videosite.task.acfun.api.AbstractAcfunVideoApiTask;

/**
 * a站视频api数据爬取任务
 */
public class AcfunVideoApiTask extends AbstractAcfunVideoApiTask {
    /**
     * 视频api数据分析器
     */
    private VideoApiParser parser;

    public AcfunVideoApiTask(Long contentId) {
        super(contentId);
        parser = new VideoApiParser();
    }

    @Override
    protected void handleVideoApi(String[] jsonArray, Long contentId) {
        if (jsonArray == null || jsonArray.length <= 0) {
            return;
        }
        /*if (Integer.valueOf(jsonArray[0])<=0&&Integer.valueOf(jsonArray[1])<=0&&Integer.valueOf(jsonArray[2])<=0&&Integer.valueOf(jsonArray[3])<=0&&Integer.valueOf(jsonArray[4])<=0&&Integer.valueOf(jsonArray[5])<=0&&Integer.valueOf(jsonArray[6])<=0&&Integer.valueOf(jsonArray[7])<=0)
            return;*/
        parser.parseVideoCounts(jsonArray, contentId);
    }
}
