package com.crawl.proxy.task.youtube;

import com.crawl.core.util.Config;
import com.crawl.core.util.Constants;
import com.crawl.core.util.HttpClientUtil;
import com.crawl.proxy.ProxyListPageParser;
import com.crawl.proxy.ProxyPool;
import com.crawl.proxy.YoutubeProxyHttpClient;
import com.crawl.proxy.entity.Direct;
import com.crawl.proxy.entity.Proxy;
import com.crawl.proxy.site.ProxyListPageParserFactory;
import com.crawl.videosite.CommonHttpClient;
import com.crawl.videosite.entity.Page;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static com.crawl.proxy.ProxyPool.youtubeProxyQueue;

/**
 * 下载代理网页并解析
 * 若下载失败，通过代理去下载代理网页
 */
public class YoutubeProxyPageTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(YoutubeProxyPageTask.class);
    protected String url;
    private boolean proxyFlag;//是否通过代理下载
    private Proxy currentProxy;//当前线程使用的代理

    protected static YoutubeProxyHttpClient proxyHttpClient = YoutubeProxyHttpClient.getInstance();

    private YoutubeProxyPageTask() {

    }

    public YoutubeProxyPageTask(String url, boolean proxyFlag) {
        this.url = url;
        this.proxyFlag = proxyFlag;
    }

    public void run() {
        long requestStartTime = System.currentTimeMillis();
        HttpGet tempRequest = null;
        try {
            Page page = null;
            if (proxyFlag) {
                tempRequest = new HttpGet(url);
                currentProxy = youtubeProxyQueue.take();
                if (!(currentProxy instanceof Direct)) {
                    HttpHost proxy = new HttpHost(currentProxy.getIp(), currentProxy.getPort());
                    tempRequest.setConfig(HttpClientUtil.getRequestConfigBuilder().setProxy(proxy).build());
                }
                page = proxyHttpClient.getWebPage(tempRequest);
            } else {
                page = proxyHttpClient.getWebPage(url);
            }
            page.setProxy(currentProxy);
            int status = page.getStatusCode();
            long requestEndTime = System.currentTimeMillis();
            String logStr = Thread.currentThread().getName() + " " + getProxyStr(currentProxy) +
                    "  executing request " + page.getUrl() + " response statusCode:" + status +
                    "  request cost time:" + (requestEndTime - requestStartTime) + "ms";
            if (status == HttpStatus.SC_OK) {
                logger.debug(logStr);
                handle(page);
            } else {
                logger.error(logStr);
                Thread.sleep(100);
                retry();
            }
        } catch (InterruptedException e) {
            logger.error("InterruptedException", e);
        } catch (IOException e) {
            retry();
        } finally {
            if (currentProxy != null) {
                currentProxy.setTimeInterval(Constants.TIME_INTERVAL);
                youtubeProxyQueue.add(currentProxy);
            }
            if (tempRequest != null) {
                tempRequest.releaseConnection();
            }
        }
    }

    /**
     * retry
     */
    public void retry() {
        proxyHttpClient.getProxyDownloadThreadExecutor().execute(new YoutubeProxyPageTask(url, Config.youtubeIsProxy));
    }

    public void handle(Page page) {
        if (page.getHtml() == null || page.getHtml().equals("")) {
            return;
        }

        ProxyListPageParser parser = ProxyListPageParserFactory.
                getProxyListPageParser(ProxyPool.proxyMap.get(url));
        List<Proxy> proxyList = parser.parse(page.getHtml());
        for (Proxy p : proxyList) {
            if (!CommonHttpClient.getInstance().getDetailListPageThreadPool().isTerminated()) {
                ProxyPool.lock.readLock().lock();
                boolean containFlag = ProxyPool.youtubeProxySet.contains(p);
                ProxyPool.lock.readLock().unlock();
                if (!containFlag) {
                    ProxyPool.lock.writeLock().lock();
                    ProxyPool.youtubeProxySet.add(p);
                    ProxyPool.lock.writeLock().unlock();

                    proxyHttpClient.getProxyTestThreadExecutor().execute(new YoutubeProxyTestTask(p));
                }
            }
        }
    }

    private String getProxyStr(Proxy proxy) {
        if (proxy == null) {
            return "";
        }
        return proxy.getIp() + ":" + proxy.getPort();
    }
}