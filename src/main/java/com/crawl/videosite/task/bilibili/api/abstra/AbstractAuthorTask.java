package com.crawl.videosite.task.bilibili.api.abstra;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crawl.core.util.Config;
import com.crawl.core.util.Constants;
import com.crawl.core.util.HttpClientUtil;
import com.crawl.core.util.JsoupUtil;
import com.crawl.proxy.ProxyPool;
import com.crawl.proxy.entity.Direct;
import com.crawl.proxy.entity.Proxy;
import com.crawl.videosite.BiliBiliHttpClient;
import com.crawl.videosite.entity.VideoAuthorPersistence;
import com.crawl.videosite.task.CommonTask;
import com.crawl.videosite.task.bilibili.api.AuthorTask;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * 爬取视频作者任务
 */
public abstract class AbstractAuthorTask  extends CommonTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(AbstractAuthorTask.class);
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
     * 当前线程使用的代理
     */
    protected Proxy currentProxy;
    /**
     * 爬取次数
     */
    private static Integer crawlerCountToSleep = 0;
    /**
     * 返回空数据的最大次数不超过100次
     */
    protected static final Integer MAXEMPTYCOUNT = 100;

    public AbstractAuthorTask(String target) {
        this.targetUrl = target;
    }

    public AbstractAuthorTask() {
    }

    @Override
    public void run() {
        while (true) {
            crawlerCount++;
            crawlerCountToSleep++;
            if (crawlerCount > 1000) {
                VideoAuthorPersistence persistence = new VideoAuthorPersistence();
                persistence.setMid(AuthorTask.mid);
                HttpClientUtil.serializeObject(persistence, Constants.biliBiliVideoAuthorDataSerialPath);
                crawlerCount = 0;
            }
            /*if (crawlerCountToSleep>=5000){
                //每爬取5000次就休息30分钟
                try {
                    Thread.sleep(1000*60*30);
                }catch (InterruptedException e){
                    logger.error("当前线程被中断执行",e);
                }
            }*/
            if (emptyCount > MAXEMPTYCOUNT) {
                emptyCount = 0;
                AuthorTask.mid=0l;
            }
            String result;
            try {
                if (Config.bilibiliIsProxy) {
                    currentProxy = ProxyPool.biliBiliProxyQueue.take();
                    if (!(currentProxy instanceof Direct)) {
                        result = JsoupUtil.getJsonFromApiByProxy(getTargetUrl(), currentProxy.getIp(), currentProxy.getPort());
                    } else {
                        result = JsoupUtil.getJsonFromApi(getTargetUrl());
                    }
                    if (StringUtils.isBlank(result)) {
                        result = JsoupUtil.getJsonFromApi(getTargetUrl());
                    }
                } else {
                    result = JsoupUtil.getJsonFromApi(getTargetUrl());
                }
            } catch (IOException e) {
                logger.warn("正在爬取目标链接时产生io读写错误: " + getTargetUrl());
                continue;
            } catch (InterruptedException e) {
                logger.error("当前代理线程已被中断: " + getTargetUrl(), e);
                try {
                    result = JsoupUtil.getJsonFromApi(getTargetUrl());
                } catch (IOException e1) {
                    logger.error("从链接获取json数据失败: " + getTargetUrl(), e1);
                    continue;
                }
            }
            if (StringUtils.isBlank(result)) {
                logger.info("爬取目标链接地址时返回的数据为空: " + targetUrl);
                emptyCount++;
                continue;
            }
            Map<String, Object> jsonData = (Map<String, Object>) JSON.parse(result);
            if (jsonData.isEmpty() || Integer.valueOf(jsonData.get("code").toString()) != 0) {
                logger.warn("running fail to catch data from url: " + targetUrl);
                emptyCount++;
            } else {
                emptyCount = 1;
            }
            JSONObject jsonObject = JSON.parseObject(result);
            handle(jsonObject);
            try {
//                Thread.sleep(2000);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("当前爬取目标地址时线程被中断: " + getTargetUrl(), e);
            }
        }
    }

    /**
     * 子类实现page的处理
     *
     * @param
     */
    protected abstract void handle(JSONObject jsonObject);

    /**
     * 获取目标地址
     *
     * @param domain
     * @param mid
     * @return
     */
    protected static String getTargetUrl(String domain, Long mid) {
        return domain + "&mid=" + mid;
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
        AbstractAuthorTask.emptyCount = emptyCount;
    }
}
