package com.crawl.proxy.task.acfun;

import com.crawl.core.util.Constants;
import com.crawl.proxy.ProxyPool;
import com.crawl.proxy.entity.Proxy;
import com.crawl.videosite.ProxyHttpClient;
import com.crawl.videosite.entity.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 代理检测task
 * 通过访问视频网站首页，能否正确响应
 * 将可用代理添加到DelayQueue延时队列中
 */
public class AcfunProxyTestTask implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(AcfunProxyTestTask.class);
    private Proxy proxy;

    public AcfunProxyTestTask(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        HttpGet request = new HttpGet(Constants.ACFUN_INDEX_URL);
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Constants.TIMEOUT).
                    setConnectTimeout(Constants.TIMEOUT).
                    setConnectionRequestTimeout(Constants.TIMEOUT).
                    setProxy(new HttpHost(proxy.getIp(), proxy.getPort())).
                    setCookieSpec(CookieSpecs.STANDARD).
                    build();
            request.setConfig(requestConfig);
            Page page = ProxyHttpClient.getInstance().getWebPage(request);
            long endTime = System.currentTimeMillis();
            String logStr = Thread.currentThread().getName() + " " + proxy.getProxyStr() +
                    "  executing request " + page.getUrl() + " response statusCode:" + page.getStatusCode() +
                    "  request cost time:" + (endTime - startTime) + "ms";
            if (page == null || page.getStatusCode() != 200 || StringUtils.isBlank(page.getHtml()) || page.getHtml().contains("<title>出错啦！</title>")) {
                logger.warn(logStr);
                return;
            }
            request.releaseConnection();
            logger.debug(proxy.toString() + "---------" + page.toString());
            logger.debug(proxy.toString() + "----------代理可用--------请求耗时:" + (endTime - startTime) + "ms");
            ProxyPool.acfunProxyQueue.add(proxy);
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
