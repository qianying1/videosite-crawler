package com.crawl.videosite.task.bilibili;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crawl.core.util.JsoupUtil;
import com.crawl.videosite.BiliBiliHttpClient;
import com.crawl.videosite.exception.JsonDataEmptyException;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * 视频列表json数据任务
 */
public abstract class AbstractVideoListTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(AbstractVideoListTask.class);
    protected static BiliBiliHttpClient httpClient = BiliBiliHttpClient.getInstance();
    /**
     * 目标地址
     */
    private String targetUrl;
    /**
     * 返回空数据的计数器
     */
    private static Integer emptyCount = 0;
    /**
     * 返回空数据的最大次数不超过100次
     */
    protected static final Integer MAXEMPTYCOUNT = 100;

    public AbstractVideoListTask(String target) {
        this.targetUrl = target;
    }

    public AbstractVideoListTask() {
    }

    @Override
    public void run() {
        while (true) {
            if (emptyCount > MAXEMPTYCOUNT) {
                break;
            }
            String result = null;
            try {
                result = JsoupUtil.getJsonFromApi(getTargetUrl());
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("fail to catch json data from url: " + targetUrl, e);
            }
            Map<String, Object> jsonData = (Map<String, Object>) JSON.parse(result);
            if (jsonData.isEmpty()) {
                throw new JsonDataEmptyException("fail to catch json data from url: " + targetUrl);
            } else if (Integer.valueOf(jsonData.get("code").toString()) != 0) {
                emptyCount++;
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
     * @param ps
     * @param pn
     * @return
     */
    protected static String getTargetUrl(@NotNull String domain, @NotNull Long rid, Long original, Integer ps, Integer pn) {
        if (ps == null) {
            ps = 50;
        }
        if (pn == null) {
            pn = 1;
        }
        return domain + "&rid=" + rid + "&pn=" + pn + "&ps=" + ps + "&original=" + original;
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
        AbstractVideoListTask.emptyCount = emptyCount;
    }
}
