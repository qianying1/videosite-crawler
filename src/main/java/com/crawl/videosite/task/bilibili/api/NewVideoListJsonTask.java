package com.crawl.videosite.task.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.crawl.videosite.entity.BiliBiliParams;
import com.crawl.videosite.parser.bilibili.api.*;
import com.crawl.videosite.parser.bilibili.api.abstra.AbstractNewVideoListParser;
import com.crawl.videosite.task.bilibili.api.abstra.AbstractNewVideoListJsonTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * b站新视频列表api抓取任务
 * <p>
 * Created by qianhaibin on 2018/3/5.
 */
public class NewVideoListJsonTask extends AbstractNewVideoListJsonTask {
    private static Logger logger = LoggerFactory.getLogger(VideoRankListJsonTask.class);
    /**
     * 目标地址
     */
    private final String targetDomain = BiliBiliParams.listDomain;  // + "&rid=1&ps=50&pn=1"
    /**
     * 目标地址
     */
    private String target;
    /**
     * 视频类型id
     */
    public static Long rid = 0l;
    /**
     * 视频大类型(属于假设)
     */
    public static Long original = 0l;
    /**
     * original最大值为8
     */
    private static final Integer MAXORIGINAL = 8;
    /**
     * 数据量
     */
    private static Integer count = 0;
    /**
     * 页面大小
     */
    private Integer ps = 50;
    /**
     * 页码
     */
    public static Integer pn = 1;
    /**
     * 视频列表分析器
     */
    private AbstractNewVideoListParser videoListParser;

    public NewVideoListJsonTask(String target) {
        super(target);
    }

    public NewVideoListJsonTask(Long rid, Long original, Integer pn) {
        super(getTargetUrl(BiliBiliParams.newListDomain, rid, original, 50, pn));
        this.target = getTargetUrl(BiliBiliParams.newListDomain, rid, original, 50, pn);
        videoListParser = new NewVideoListJsonParser();
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
            setPn(1);
            setPs(50);
            setTargetUrl(getTargetUrl(targetDomain, rid, original, getPs(), getPn()));
            return;
        } else {
            videoListParser.parseJson(jsonObject, rid, original);
        }
        Map<String, Object> page = (Map<String, Object>) ((Map<String, Object>) jsonObject.get("data")).get("page");
        setPn(Integer.valueOf(page.get("num").toString()));
        setPs(Integer.valueOf(page.get("size").toString()));
        count = Integer.valueOf(page.get("count").toString());
        if (getPn() * getPs() >= count) {
            rid++;
            setPn(1);
            setPs(50);
        } else {
            setPn(getPn() + 1);
            setPs(getPs());
        }
        if (getEmptyCount() > MAXEMPTYCOUNT && original <= MAXORIGINAL) {
            original++;
            setEmptyCount(0);
            rid = 1l;
            setPn(1);
            setPs(50);
        }
        setTargetUrl(getTargetUrl(targetDomain, rid, original, getPs(), getPn()));
    }

    public Integer getPs() {
        return ps;
    }

    public void setPs(Integer ps) {
        this.ps = ps;
    }

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
