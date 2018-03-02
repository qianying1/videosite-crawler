package com.crawl.videosite.task.sohu;

import com.crawl.core.util.Constants;
import com.crawl.core.util.SimpleInvocationHandler;
import com.crawl.proxy.ProxyPool;
import com.crawl.proxy.entity.Direct;
import com.crawl.proxy.entity.Proxy;
import com.crawl.proxy.util.ProxyUtil;
import com.crawl.videosite.SohuHttpClient;
import com.crawl.videosite.dao.VideoSiteDao1;
import com.crawl.videosite.dao.impl.VideoSiteDao1Imp;
import com.crawl.videosite.entity.Page;
import com.gargoylesoftware.htmlunit.WebRequest;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.net.URL;


/**
 * page task
 * 下载网页并解析，具体解析由子类实现
 * 若使用代理，从ProxyPool中取
 *
 * @see ProxyPool
 */
public abstract class SohuAbstractPageTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(SohuAbstractPageTask.class);
    protected String url;
    protected WebRequest request;
    protected boolean proxyFlag;//是否通过代理下载
    protected Proxy currentProxy;//当前线程使用的代理
    protected static VideoSiteDao1 videoSiteDao1;
    protected static SohuHttpClient httpClient = SohuHttpClient.getInstance();

    static {
        videoSiteDao1 = getVideoSiteDao1();
    }

    public SohuAbstractPageTask() {

    }

    public SohuAbstractPageTask(String url, boolean proxyFlag) {
        this.url = url;
        this.proxyFlag = proxyFlag;
    }

    public SohuAbstractPageTask(WebRequest request, boolean proxyFlag) {
        this.request = request;
        this.proxyFlag = proxyFlag;
    }

    public void run() {
        long requestStartTime = 0l;
        WebRequest tempRequest = null;
        try {
            Page page = null;
            if (url != null) {
                if (proxyFlag) {
                    tempRequest = new WebRequest(new URL(url));
                    currentProxy = ProxyPool.acfunProxyQueue.take();
                    if (!(currentProxy instanceof Direct)) {
                        tempRequest.setProxyHost(currentProxy.getIp());
                        tempRequest.setProxyPort(currentProxy.getPort());
                    }
                    requestStartTime = System.currentTimeMillis();
                    page = httpClient.getWebPage(tempRequest);
                } else {
                    requestStartTime = System.currentTimeMillis();
                    page = httpClient.getWebPage(url);
                }
            } else if (request != null) {
                if (proxyFlag) {
                    currentProxy = ProxyPool.acfunProxyQueue.take();
                    if (!(currentProxy instanceof Direct)) {
                        request.setProxyHost(currentProxy.getIp());
                        request.setProxyPort(currentProxy.getPort());
                    }
                    requestStartTime = System.currentTimeMillis();
                    page = httpClient.getWebPage(request);
                } else {
                    requestStartTime = System.currentTimeMillis();
                    page = httpClient.getWebPage(request);
                }
            }
            long requestEndTime = System.currentTimeMillis();
            page.setProxy(currentProxy);
            int status = page.getStatusCode();
            String logStr = Thread.currentThread().getName() + " " + currentProxy +
                    "  executing request " + page.getUrl() + " response statusCode:" + status +
                    "  request cost time:" + (requestEndTime - requestStartTime) + "ms";
            if (status == HttpStatus.SC_OK) {
                if (page.getHtml().contains("videosite") && !page.getHtml().contains("安全验证")) {
                    logger.debug(logStr);
                    currentProxy.setSuccessfulTimes(currentProxy.getSuccessfulTimes() + 1);
                    currentProxy.setSuccessfulTotalTime(currentProxy.getSuccessfulTotalTime() + (requestEndTime - requestStartTime));
                    double aTime = (currentProxy.getSuccessfulTotalTime() + 0.0) / currentProxy.getSuccessfulTimes();
                    currentProxy.setSuccessfulAverageTime(aTime);
                    currentProxy.setLastSuccessfulTime(System.currentTimeMillis());
                    handle(page);
                } else {
                    /**
                     * 代理异常，没有正确返回目标url
                     */
                    logger.warn("proxy exception:" + currentProxy.toString());
                }

            }
            /**
             * 401--不能通过验证
             */
            else if (status == 404 || status == 401 ||
                    status == 410) {
                logger.warn(logStr);
            } else {
                logger.error(logStr);
                Thread.sleep(100);
                retry();
            }
        } catch (InterruptedException e) {
            logger.error("InterruptedException", e);
        } catch (Exception e) {
            e.printStackTrace();
            if (currentProxy != null) {
                /**
                 * 该代理可用，将该代理继续添加到proxyQueue
                 */
                currentProxy.setFailureTimes(currentProxy.getFailureTimes() + 1);
            }
            if (!httpClient.getDetailListPageThreadPool().isShutdown()) {
                retry();
            }
        } finally {
            if (currentProxy != null && !ProxyUtil.isDiscardProxy(currentProxy)) {
                currentProxy.setTimeInterval(Constants.TIME_INTERVAL);
                ProxyPool.sohuProxyQueue.add(currentProxy);
            }
        }
    }

    /**
     * retry
     */
    abstract void retry();


    /**
     * 子类实现page的处理
     *
     * @param page
     */
    abstract void handle(Page page);

    private String getProxyStr(Proxy proxy) {
        if (proxy == null) {
            return "";
        }
        return proxy.getIp() + ":" + proxy.getPort();
    }

    /**
     * 代理类，统计方法执行时间
     *
     * @return
     */
    private static VideoSiteDao1 getVideoSiteDao1() {
        VideoSiteDao1 videoSiteDao1 = new VideoSiteDao1Imp();
        InvocationHandler invocationHandler = new SimpleInvocationHandler(videoSiteDao1);
        VideoSiteDao1 proxyVideoSiteDao1 = (VideoSiteDao1) java.lang.reflect.Proxy.newProxyInstance(videoSiteDao1.getClass().getClassLoader(),
                videoSiteDao1.getClass().getInterfaces(), invocationHandler);
        return proxyVideoSiteDao1;
    }
}
