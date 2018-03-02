package com.crawl.videosite.task.bilibili;

import com.crawl.videosite.entity.BiliBiliParams;

/**
 * 视频列表Json数据抓取任务
 */
public class VideoListJsonTask extends AbstractVideoListTask {

    /**
     * 目标地址
     */
    private final String target = BiliBiliParams.listDomain + "&rid=1&ps=50&pn=1";
    ;

    public VideoListJsonTask(String target) {
        super(target);
    }

    @Override
    public void handle() {

    }
}
