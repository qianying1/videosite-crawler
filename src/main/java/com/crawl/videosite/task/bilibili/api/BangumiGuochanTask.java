package com.crawl.videosite.task.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.crawl.videosite.entity.BiliBiliParams;
import com.crawl.videosite.parser.bilibili.api.BangumiGuochanParser;
import com.crawl.videosite.parser.bilibili.api.abstra.AbstractBangumiGuochanParser;
import com.crawl.videosite.parser.bilibili.api.abstra.AbstractVideoRankListParser;
import com.crawl.videosite.parser.bilibili.api.VideoRankListJsonParser;
import com.crawl.videosite.task.bilibili.api.abstra.AbstractBangumiGuochanTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * bangumi国产连载视频列表Json数据抓取任务
 */
public class BangumiGuochanTask extends AbstractBangumiGuochanTask {
    private static Logger logger = LoggerFactory.getLogger(BangumiGuochanTask.class);
    /**
     * 目标地址
     */
    private final String targetDomain = BiliBiliParams.seasonDomain;  // + "&rid=1&ps=50&pn=1"
    /**
     * 目标地址
     */
    private String target;
    /**
     * 视频列表分析器
     */
    private AbstractBangumiGuochanParser parser;

    public BangumiGuochanTask(String target) {
        super(target);
    }

    public BangumiGuochanTask() {
        super(getTargetUrl(BiliBiliParams.rankDomain));
        this.target = getTargetUrl(BiliBiliParams.rankDomain);
        parser = new BangumiGuochanParser();
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
            return;
        }
        parser.parseJson(jsonObject);
        if (getEmptyCount() > MAXEMPTYCOUNT) {
            setEmptyCount(0);
        }
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
