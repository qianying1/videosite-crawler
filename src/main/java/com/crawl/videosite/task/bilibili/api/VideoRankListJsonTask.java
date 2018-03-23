package com.crawl.videosite.task.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.crawl.videosite.entity.BiliBiliParams;
import com.crawl.videosite.parser.bilibili.api.abstra.AbstractVideoRankListParser;
import com.crawl.videosite.parser.bilibili.api.VideoRankListJsonParser;
import com.crawl.videosite.task.bilibili.api.abstra.AbstractVideoRankListTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 等级视频列表Json数据抓取任务
 */
public class VideoRankListJsonTask extends AbstractVideoRankListTask {
    private static Logger logger = LoggerFactory.getLogger(VideoRankListJsonTask.class);
    /**
     * 目标地址
     */
    private final String targetDomain = BiliBiliParams.rankDomain;  // + "&rid=1&ps=50&pn=1"
    /**
     * 目标地址
     */
    private String target;
    /**
     * 视频类型id
     */
    public static Long rid = 0l;
    /**
     * 视频列表分析器
     */
    private AbstractVideoRankListParser videoListParser;

    public VideoRankListJsonTask(String target) {
        super(target);
    }

    public VideoRankListJsonTask(Long rid) {
        super(getTargetUrl(BiliBiliParams.rankDomain, rid));
        this.target = getTargetUrl(BiliBiliParams.rankDomain, rid);
        videoListParser = new VideoRankListJsonParser();
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
            rid++;
            setTargetUrl(getTargetUrl(targetDomain, rid));
            return;
        } else {
            videoListParser.parseJson(jsonObject, rid,getConnection());
        }
        if (getEmptyCount() > MAXEMPTYCOUNT) {
            setEmptyCount(0);
            rid = 1l;
        }
        setTargetUrl(getTargetUrl(targetDomain, rid));
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
