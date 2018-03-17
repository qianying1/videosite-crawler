package com.crawl.videosite.task.acfun.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.crawl.videosite.parser.acfun.VideoApiParser;
import com.crawl.videosite.task.acfun.api.AbstractAcfunVideoApiTask;

import java.util.List;

/**
 * a站视频api数据爬取任务
 */
public class AcfunVideoApiTask extends AbstractAcfunVideoApiTask {

    public AcfunVideoApiTask(Long contentId){
        super(contentId);
    }

    @Override
    protected void handleVideoApi(JSONObject jsonObject,Long contentId) {
        if (jsonObject==null||jsonObject.isEmpty()){
            return;
        }
        List<Long> counts=(List<Long>) jsonObject;
        if (counts.isEmpty()||(counts.get(0)<=0&&counts.get(1)<=0&&counts.get(2)<=0&&counts.get(3)<=0&&counts.get(4)<=0&&counts.get(5)<=0&&counts.get(6)<=0&&counts.get(7)<=0))
            return;
        VideoApiParser.parseVideoCounts(counts,contentId);
    }
}
