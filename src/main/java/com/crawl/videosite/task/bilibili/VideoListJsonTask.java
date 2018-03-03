package com.crawl.videosite.task.bilibili;

import com.alibaba.fastjson.JSONObject;
import com.crawl.videosite.entity.BiliBiliParams;
import com.crawl.videosite.parser.bilibili.AbstractVideoListParser;
import com.crawl.videosite.parser.bilibili.VideoListJsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 视频列表Json数据抓取任务
 */
public class VideoListJsonTask extends AbstractVideoListTask {
    private static Logger logger = LoggerFactory.getLogger(VideoListJsonTask.class);
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
    private static Long rid = 1l;
    /**
     * 视频大类型(属于假设)
     */
    private static Long original = 0l;
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
    private Integer pn = 1;
    /**
     * 视频列表分析器
     */
    private AbstractVideoListParser videoListParser;

    public VideoListJsonTask(String target) {
        super(target);
    }

    public VideoListJsonTask(Long rid, Long original, Integer pn) {
        super(getTargetUrl(BiliBiliParams.listDomain, rid, original, 50, pn));
        this.target = getTargetUrl(BiliBiliParams.listDomain, rid, original, 50, pn);
        System.out.println(target);
        videoListParser = new VideoListJsonParser();
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
            logger.error("fail to catch json data from url: " + this.target);
            return;
        } else {
            videoListParser.parseJson(jsonObject, getRid(), original);
        }
        Map<String, Object> page = (Map<String, Object>) ((Map<String, Object>) jsonObject.get("data")).get("page");
        setPn(Integer.valueOf(page.get("num").toString()));
        setPs(Integer.valueOf(page.get("size").toString()));
        count = Integer.valueOf(page.get("count").toString());
        if (getPn() * getPs() >= count) {
            setRid(getRid() + 1);
            setPn(1);
            setPs(50);
        }else {
            setPn(getPn() + 1);
            setPs(getPs());
        }
        if (getEmptyCount()>MAXEMPTYCOUNT&&original <= MAXORIGINAL){
            original++;
            setEmptyCount(0);
            setRid(1l);
            setPn(1);
            setPs(50);
        }
        setTargetUrl(getTargetUrl(targetDomain, getRid(), original, getPs(), getPn()));
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
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
