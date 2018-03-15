package com.crawl.videosite.task.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.crawl.videosite.entity.BiliBiliParams;
import com.crawl.videosite.parser.bilibili.api.AuthorParser;
import com.crawl.videosite.parser.bilibili.api.abstra.AbstractAuthorParser;
import com.crawl.videosite.task.bilibili.api.abstra.AbstractAuthorTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 视频作者信息爬取任务
 */
public class AuthorTask extends AbstractAuthorTask {

    private static Logger logger = LoggerFactory.getLogger(AuthorTask.class);
    /**
     * 目标地址
     */
    private final String targetDomain = BiliBiliParams.videoUpManCardDomain;  // + "&rid=1&ps=50&pn=1"
    /**
     * 目标地址
     */
    private String target;
    /**
     * 视频作者id
     */
    public static Long mid = 0l;
    /**
     * 数据量
     */
    private static Integer count = 0;
    /**
     * 视频列表分析器
     */
    private AbstractAuthorParser videoListParser;

    public AuthorTask(String target) {
        super(target);
    }

    public AuthorTask(Long mid) {
        super(getTargetUrl(BiliBiliParams.listDomain, mid));
        this.target = getTargetUrl(BiliBiliParams.listDomain, mid);
        AuthorTask.mid = mid;
        videoListParser = new AuthorParser();
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
        /*if(mid==61926l){
            mid+=3085l;
        }*/
        if (Integer.valueOf(jsonObject.get("code").toString()) != 0) {
            logger.warn("fail to catch json data from url: " + this.target);
            mid--;
            setTargetUrl(getTargetUrl(targetDomain, mid));
            return;
        } else {
            videoListParser.parseJson(jsonObject, mid);
        }
        mid--;
        if (getEmptyCount() > MAXEMPTYCOUNT) {
            setEmptyCount(0);
            mid = 0l;
        }
        setTargetUrl(getTargetUrl(targetDomain, mid));
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
