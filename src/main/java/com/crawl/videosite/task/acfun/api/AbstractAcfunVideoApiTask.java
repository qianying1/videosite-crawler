package com.crawl.videosite.task.acfun.api;

import com.alibaba.fastjson.JSONObject;
import com.crawl.core.util.Config;
import com.crawl.core.util.Constants;
import com.crawl.core.util.HttpClientUtil;
import com.crawl.core.util.JsoupUtil;
import com.crawl.proxy.ProxyPool;
import com.crawl.proxy.entity.Proxy;
import com.crawl.videosite.entity.AcfunParams;
import com.crawl.videosite.entity.AcfunVideoPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * a站视频json数据爬取任务
 */
public abstract class AbstractAcfunVideoApiTask implements Runnable {
    private Logger logger = LoggerFactory.getLogger(AbstractAcfunVideoApiTask.class);
    /**
     * 当前正在爬取数据的视频id
     */
    protected static Long contentId = 0l;
    /**
     * 当前抓取次数
     */
    private static Integer crawlCount = 0;
    /**
     * 最大次数为一千时进行持久化参数数据
     */
    private final Integer PERSISTENCEINCOUNT = 1000;
    /**
     * 目标地址
     */
    private final String tagetDomain = AcfunParams.videoViewContentDomain;

    protected AbstractAcfunVideoApiTask(Long contentId) {
        AbstractAcfunVideoApiTask.contentId = contentId;
    }

    @Override
    public void run() {
        while (true) {
            crawlCount++;
            if (crawlCount >= PERSISTENCEINCOUNT) {
                persistenceData();
            }
            String jsonStr = null;
            try {
                if (Config.acfunIsProxy) {
                    Proxy proxy = ProxyPool.acfunProxyQueue.take();
                    jsonStr = JsoupUtil.getJsonFromApiByProxy(getTagetURL(AbstractAcfunVideoApiTask.contentId), proxy.getIp(), proxy.getPort());
                } else {
                    jsonStr = JsoupUtil.getJsonFromApi(getTagetURL(AbstractAcfunVideoApiTask.contentId));
                }
                if (jsonStr == null) {
                    jsonStr = JsoupUtil.getJsonFromApi(getTagetURL(AbstractAcfunVideoApiTask.contentId));
                }
            } catch (IOException e) {
                logger.error("抓取目标链接失败: " + getTagetURL(AbstractAcfunVideoApiTask.contentId), e);
            } catch (InterruptedException e) {
                logger.error("当前线程被中断", e);
            }
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            if (jsonStr == null || jsonObject.isEmpty()) {
                AbstractAcfunVideoApiTask.contentId++;
                continue;
            }
            handleVideoApi(jsonObject, AbstractAcfunVideoApiTask.contentId);
            AbstractAcfunVideoApiTask.contentId++;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        persistenceData();
    }

    /**
     * 将数据进行持久化
     */
    private void persistenceData() {
        AcfunVideoPersistence persistence = new AcfunVideoPersistence();
        persistence.setContentId(AbstractAcfunVideoApiTask.contentId);
        HttpClientUtil.serializeObject(persistence, Constants.acfunVideoDataSerialPath);
    }

    /**
     * 处理json数据
     *
     * @param jsonObject
     */
    protected abstract void handleVideoApi(JSONObject jsonObject, Long contentId);

    /**
     * 返回目标链接地址
     *
     * @param contentId
     * @return
     */
    private String getTagetURL(Long contentId) {
        if (contentId == null || contentId < 0) {
            contentId = 0l;
        }
        return tagetDomain + contentId;
    }
}
