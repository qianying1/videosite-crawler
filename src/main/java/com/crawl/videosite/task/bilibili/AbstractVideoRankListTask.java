package com.crawl.videosite.task.bilibili;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crawl.core.util.Config;
import com.crawl.core.util.HttpClientUtil;
import com.crawl.core.util.JsoupUtil;
import com.crawl.videosite.BiliBiliHttpClient;
import com.crawl.videosite.entity.VideoSiteDynamicPersistence;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * 等级视频列表json数据任务
 */
public abstract class AbstractVideoRankListTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(AbstractVideoRankListTask.class);
    protected static BiliBiliHttpClient httpClient = BiliBiliHttpClient.getInstance();
    /**
     * 目标地址
     */
    private static String targetUrl;
    /**
     * 返回空数据的计数器
     */
    private static Integer emptyCount = 0;
    /**
     * 爬取次数
     */
    private static Integer crawlerCount = 0;
    /**
     * 返回空数据的最大次数不超过100次
     */
    protected static final Integer MAXEMPTYCOUNT = 100;

    public AbstractVideoRankListTask(String target) {
        this.targetUrl = target;
    }

    public AbstractVideoRankListTask() {
    }

    @Override
    public void run() {
        while (true) {
            crawlerCount++;
            if (crawlerCount > 1000) {
                /*VideoSiteDynamicPersistence persistence = new VideoSiteDynamicPersistence();
                persistence.setBiliBili_original(VideoDynamicListJsonTask.original);
                persistence.setBiliBili_pn(VideoDynamicListJsonTask.pn);
                persistence.setBiliBili_rid(VideoDynamicListJsonTask.rid);
                persistence.setBiliBili_aid(0l);
                persistence.setBiliBili_day(1);
                persistence.setBiliBili_mid(0l);
                HttpClientUtil.serializeObject(persistence, Config.biliBiliDataSerialPath);*/
            }
            if (emptyCount > MAXEMPTYCOUNT) {
                emptyCount = 0;
                VideoDynamicListJsonTask.rid = 0l;
            }
            String result;
            try {
                result = JsoupUtil.getJsonFromApi(getTargetUrl());
            } catch (IOException e) {
                logger.error("running fail to catch json data from url: " + getTargetUrl(), e);
                break;
            }
            Map<String, Object> jsonData = (Map<String, Object>) JSON.parse(result);
            if (jsonData.isEmpty() || Integer.valueOf(jsonData.get("code").toString()) != 0) {
                VideoDynamicListJsonTask.rid++;
                logger.warn("running fail to catch data from url: " + targetUrl);
                emptyCount++;
            } else {
                emptyCount = 1;
            }
            JSONObject jsonObject = JSON.parseObject(result);
            handle(jsonObject);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("InterruptedException", e);
            }
        }
    }

    /**
     * 子类实现page的处理
     *
     * @param
     */
    abstract void handle(JSONObject jsonObject);

    /**
     * 获取目标地址
     *
     * @param domain
     * @param rid
     * @return
     */
    protected static String getTargetUrl(@NotNull String domain, @NotNull Long rid) {
        return domain + "&rid=" + rid;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    protected static Integer getEmptyCount() {
        return emptyCount;
    }

    protected static void setEmptyCount(Integer emptyCount) {
        AbstractVideoRankListTask.emptyCount = emptyCount;
    }
}
