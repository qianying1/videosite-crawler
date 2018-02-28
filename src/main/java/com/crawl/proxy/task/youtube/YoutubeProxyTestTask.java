package com.crawl.proxy.task.youtube;

import com.crawl.core.util.Constants;
import com.crawl.proxy.ProxyPool;
import com.crawl.proxy.entity.Proxy;
import com.crawl.videosite.CommonHttpClient;
import com.crawl.videosite.entity.Page;
import org.apache.http.HttpHost;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 代理检测task
 * 通过访问知乎首页，能否正确响应
 * 将可用代理添加到DelayQueue延时队列中
 */
public class YoutubeProxyTestTask implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(YoutubeProxyTestTask.class);
    private Proxy proxy;

    public YoutubeProxyTestTask(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        HttpGet request = new HttpGet(Constants.YOUTUBE_INDEX_URL);
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Constants.TIMEOUT).
                    setConnectTimeout(Constants.TIMEOUT).
                    setConnectionRequestTimeout(Constants.TIMEOUT).
                    setProxy(new HttpHost(proxy.getIp(), proxy.getPort())).
                    setCookieSpec(CookieSpecs.STANDARD).
                    build();
            request.setConfig(requestConfig);
            Page page = CommonHttpClient.getInstance().getWebPage(request);
            long endTime = System.currentTimeMillis();
            String logStr = Thread.currentThread().getName() + " " + proxy.getProxyStr() +
                    "  executing request " + page.getUrl() + " response statusCode:" + page.getStatusCode() +
                    "  request cost time:" + (endTime - startTime) + "ms";
            if (page == null || page.getStatusCode() != 200) {
                logger.warn(logStr);
                return;
            }
            request.releaseConnection();
            logger.debug(proxy.toString() + "---------" + page.toString());
            logger.debug(proxy.toString() + "----------代理可用--------请求耗时:" + (endTime - startTime) + "ms");
            ProxyPool.youtubeProxyQueue.add(proxy);
        } catch (IOException e) {
            logger.debug("IOException:", e);
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
        }
    }

    private String getProxyStr() {
        return proxy.getIp() + ":" + proxy.getPort();
    }
}