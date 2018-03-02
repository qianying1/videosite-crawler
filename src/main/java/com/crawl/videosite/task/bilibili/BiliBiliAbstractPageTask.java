package com.crawl.videosite.task.bilibili;

import com.crawl.core.util.Constants;
import com.crawl.core.util.HtmlUnitWebClientUtil;
import com.crawl.core.util.SimpleInvocationHandler;
import com.crawl.core.util.WebRequestParams;
import com.crawl.proxy.ProxyPool;
import com.crawl.proxy.entity.Direct;
import com.crawl.proxy.entity.Proxy;
import com.crawl.proxy.util.ProxyUtil;
import com.crawl.videosite.BiliBiliHttpClient;
import com.crawl.videosite.dao.VideoSiteDao1;
import com.crawl.videosite.dao.impl.VideoSiteDao1Imp;
import com.crawl.videosite.entity.Page;
import com.crawl.videosite.entity.WebHtmlPage;
import com.gargoylesoftware.htmlunit.WebRequest;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;


/**
 * page task
 * 下载网页并解析，具体解析由子类实现
 * 若使用代理，从ProxyPool中取
 *
 * @see ProxyPool
 */
public abstract class BiliBiliAbstractPageTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(BiliBiliAbstractPageTask.class);
    protected String url;
    protected WebRequest request;
    protected boolean proxyFlag;//是否通过代理下载
    protected Proxy currentProxy;//当前线程使用的代理
    protected static VideoSiteDao1 videoSiteDao1;
    protected static WebRequestParams params;
    protected static BiliBiliHttpClient httpClient = BiliBiliHttpClient.getInstance();

    static {
        videoSiteDao1 = getVideoSiteDao1();
    }

    private String pageText;

    public BiliBiliAbstractPageTask() {

    }

    public BiliBiliAbstractPageTask(String url, boolean proxyFlag) {
        this.url = url;
        this.proxyFlag = proxyFlag;
    }

    public BiliBiliAbstractPageTask(WebRequest request, boolean proxyFlag) {
        this.request = request;
        this.proxyFlag = proxyFlag;
    }

    public void run() {
        long requestStartTime = 0l;
        WebRequest tempRequest = null;
        params = new WebRequestParams();
        try {
            HtmlUnitWebClientUtil.showOrHiddenLog(false);
            WebHtmlPage page = null;
            if (url != null) {
                if (proxyFlag) {
                    currentProxy = ProxyPool.biliBiliProxyQueue.take();
                    tempRequest = HtmlUnitWebClientUtil.getRequest(url, null, params, currentProxy);
                    if (!(currentProxy instanceof Direct)) {
                        tempRequest.setSocksProxy(true);
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
                    currentProxy = ProxyPool.biliBiliProxyQueue.take();
                    if (!(currentProxy instanceof Direct)) {
                        request.setSocksProxy(true);
                        request.setProxyHost(currentProxy.getIp());
                        request.setProxyPort(currentProxy.getPort());
                    }
                }
                requestStartTime = System.currentTimeMillis();
                try {
                    page = httpClient.getWebPage(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(page.getBodyContent());
            }

            long requestEndTime = System.currentTimeMillis();
            page.setProxy(currentProxy != null ? currentProxy : new Proxy("127.0.0.1", 8080, 1000));
            int status = page.getStatusCode();
            String logStr = Thread.currentThread().getName() + " " + currentProxy +
                    "  executing request " + page.getUrl() + " response statusCode:" + status +
                    "  request cost time:" + (requestEndTime - requestStartTime) + "ms";
            if (status == HttpStatus.SC_OK) {
                /*if (page.getHtml().contains("bili-header-m")) {
                    AbstractPageTaskCommon.setProxyTimesLog(currentProxy, logStr, logger, requestEndTime, requestStartTime);
                    handle(page);
                } else {
                    *//**
                 * 代理异常，没有正确返回目标url
                 *//*
                    logger.warn("proxy exception:" + currentProxy.toString());
                }*/
            } else {
                logger.error(logStr);
                Thread.sleep(100);
                retry();
            }
        } catch (InterruptedException e) {
            logger.error("InterruptedException", e);
        } catch (Exception e) {
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
                ProxyPool.biliBiliProxyQueue.add(currentProxy);
            }
//            HtmlUnitWebClientUtil.showOrHiddenLog(true);
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
