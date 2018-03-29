package com.crawl.videosite.task.acfun.api;

import com.alibaba.fastjson.JSONObject;
import com.crawl.core.util.Config;
import com.crawl.core.util.Constants;
import com.crawl.core.util.HttpClientUtil;
import com.crawl.core.util.JsoupUtil;
import com.crawl.proxy.ProxyPool;
import com.crawl.proxy.entity.Proxy;
import com.crawl.videosite.entity.AcfunAuthorPersistence;
import com.crawl.videosite.entity.AcfunParams;
import com.crawl.videosite.task.CommonTask;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * a站视频作者信息爬取任务
 */
public abstract class AbstractAcfunAuthorApiTask extends CommonTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(AbstractAcfunAuthorApiTask.class);
    /**
     * 目标固定api地址
     */
    private static final String targetDomain = AcfunParams.upManDetailDomain;
    /**
     * 目标地址userId参数值
     */
    private static Long userId = 0l;
    /**
     * 爬取的次数
     */
    private static int crawlCount = 0;
    /**
     * 最大爬取次数
     */
    private final int _MAXCRAWL_COUNT = 1200;

    protected AbstractAcfunAuthorApiTask(Long userId) {
        AbstractAcfunAuthorApiTask.userId = userId;
    }

    @Override
    public void run() {
        while (true) {
            if (crawlCount >= _MAXCRAWL_COUNT) {
                persistenceData();
                crawlCount = 0;
            }
            crawlCount++;
            String jsonStr;
            try {
                if (Config.acfunIsProxy) {
                    Proxy proxy = ProxyPool.acfunProxyQueue.take();
                    jsonStr = JsoupUtil.getJsonFromApiByProxy(getTargetUrl(), proxy.getIp(), proxy.getPort());
                } else {
                    jsonStr = JsoupUtil.getJsonFromApi(getTargetUrl());
                }
                if (StringUtils.isBlank(jsonStr)) {
                    jsonStr = JsoupUtil.getJsonFromApi(getTargetUrl());
                }
            } catch (IOException e) {
                logger.error("在获取a站视频作者api数据时遇到io读写错误!", e);
                continue;
            } catch (InterruptedException interuptE) {
                logger.error("在使用代理获取a站视频作者api数据时遇到线程中断错误!", interuptE);
                continue;
            }
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            if (!ObjectUtils.notEqual(jsonObject, null)
                    || jsonObject.isEmpty()
                    || Integer.valueOf(jsonObject.get("code").toString()) != 200) {
                AbstractAcfunAuthorApiTask.userId++;
                continue;
            }
            handle((Map<String, Object>) jsonObject.get("result"));
            AbstractAcfunAuthorApiTask.userId++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("在爬取目标链接数据完成线程休眠时遇到线程中断错误", e);
            }
        }
    }

    /**
     * 进行数据分析处理
     *
     * @param jsonMap
     */
    protected abstract void handle(Map<String, Object> jsonMap);

    /**
     * 获取目标地址
     *
     * @return
     */
    private String getTargetUrl() {
        return targetDomain + userId;
    }

    /**
     * 将数据进行持久化
     */
    private void persistenceData() {
        AcfunAuthorPersistence persistence = new AcfunAuthorPersistence();
        persistence.setUserId(AbstractAcfunAuthorApiTask.userId);
        HttpClientUtil.serializeObject(persistence, Constants.acfunAuthorDataSerialPath);
    }
}
