package com.crawl.videosite.task.acfun.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.crawl.videosite.entity.AcfunVideoListPersistence;
import com.crawl.videosite.parser.acfun.VideoListApiParser;
import com.crawl.videosite.task.acfun.api.AbstractAcfunVideoListApiTask;

import java.util.Map;

/**
 * 视频分页数据爬取任务
 */
public class AcfunVideoListApiTask extends AbstractAcfunVideoListApiTask {

    private VideoListApiParser parser;
    public AcfunVideoListApiTask(AcfunVideoListPersistence persistence){
        super(persistence);
        parser=new VideoListApiParser();
    }

    @Override
    protected void handleJsonData(JSONObject jsonObject) {
        if (jsonObject.isEmpty())
            return;
        Map<String,Object> dataMap=(Map<String, Object>) jsonObject.get("data");
        Map<String,Object> pageMap=(Map)dataMap.get("page");
        parser.parseJsonMap(pageMap);
    }
}
