package com.crawl.videosite.task.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.crawl.videosite.entity.BiliBiliParams;
import com.crawl.videosite.parser.bilibili.api.VideoJsonParser;
import com.crawl.videosite.parser.bilibili.api.abstra.AbstractVideoParser;
import com.crawl.videosite.task.bilibili.api.abstra.AbstractVideoTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 活动视频列表Json数据抓取任务
 */
public class VideoJsonTask extends AbstractVideoTask {
    private static Logger logger = LoggerFactory.getLogger(VideoJsonTask.class);
    /**
     * 目标地址
     */
    private final String targetDomain = BiliBiliParams.videoStatDomain;  // + "&rid=1&ps=50&pn=1"
    /**
     * 目标地址
     */
    private String target;
    /**
     * 视频类型id
     */
    public static Long aid = 0l;
    /**
     * 数据量
     */
    private static Integer count = 0;
    /**
     * 视频列表分析器
     */
    private AbstractVideoParser videoListParser;

    public VideoJsonTask(String target) {
        super(target);
    }

    public VideoJsonTask(Long aid) {
        super(getTargetUrl(BiliBiliParams.videoStatDomain, aid));
        this.target = getTargetUrl(BiliBiliParams.videoStatDomain, aid);
        VideoJsonTask.aid=aid;
        videoListParser = new VideoJsonParser();
    }

    /**
     * 处理返回的json数据
     *
     * @param jsonObject
     */
    @Override
    public void handle(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        if (Integer.valueOf(jsonObject.get("code").toString()) != 0) {
            logger.warn("fail to catch json data from url: " + this.target);
            aid++;
            setTargetUrl(getTargetUrl(targetDomain, aid));
            return;
        } else {
            videoListParser.parseJson(jsonObject);
        }
        if (getEmptyCount() > MAXEMPTYCOUNT) {
            setEmptyCount(0);
            aid = 0l;
        }
        aid++;
        setTargetUrl(getTargetUrl(targetDomain, aid));
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
