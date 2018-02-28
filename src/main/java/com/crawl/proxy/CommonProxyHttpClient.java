package com.crawl.proxy;

import com.crawl.core.httpclient.AbstractHttpClient;
import com.crawl.core.util.*;
import com.crawl.proxy.entity.Proxy;
import com.crawl.proxy.task.acfun.AcfunProxyPageTask;
import com.crawl.proxy.task.acfun.AcfunProxySerializeTask;
import com.crawl.videosite.entity.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * a站代理客户端
 */
public class CommonProxyHttpClient extends AbstractHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(CommonProxyHttpClient.class);
    private volatile static CommonProxyHttpClient instance;
    public static Set<Page> downloadFailureProxyPageSet = new HashSet<>(ProxyPool.proxyMap.size());

    public static CommonProxyHttpClient getInstance(){
        if (instance == null){
            synchronized (CommonProxyHttpClient.class){
                if (instance == null){
                    instance = new CommonProxyHttpClient();
                }
            }
        }
        return instance;
    }
    /**
     * 代理测试线程池
     */
    private ThreadPoolExecutor proxyTestThreadExecutor;
    /**
     * 代理网站下载线程池
     */
    private ThreadPoolExecutor proxyDownloadThreadExecutor;
    public CommonProxyHttpClient(){
        initThreadPool();
        initProxy();
    }
    /**
     * 初始化线程池
     */
    private void initThreadPool(){
        proxyTestThreadExecutor = new SimpleThreadPoolExecutor(100, 100,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(10000),
                new ThreadPoolExecutor.DiscardPolicy(),
                "acfunProxyTestThreadExecutor");
        proxyDownloadThreadExecutor = new SimpleThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), "" +
                "acfunProxyDownloadThreadExecutor");
        new Thread(new ThreadPoolMonitor(proxyTestThreadExecutor, "AcfunProxyTestThreadPool")).start();
        new Thread(new ThreadPoolMonitor(proxyDownloadThreadExecutor, "AcfunProxyDownloadThreadExecutor")).start();
    }

    /**
     * 初始化proxy
     *
     */
    private void initProxy(){
        Proxy[] proxyArray = null;
        try {
            proxyArray = (Proxy[]) HttpClientUtil.deserializeObject(Config.proxyPath);
            int usableProxyCount = 0;
            for (Proxy p : proxyArray){
                if (p == null){
                    continue;
                }
                p.setTimeInterval(Constants.TIME_INTERVAL);
                p.setFailureTimes(0);
                p.setSuccessfulTimes(0);
                long nowTime = System.currentTimeMillis();
                if (nowTime - p.getLastSuccessfulTime() < 1000 * 60 *60){
                    //上次成功离现在少于一小时
                    ProxyPool.acfunProxyQueue.add(p);
                    ProxyPool.acfunProxySet.add(p);
                    usableProxyCount++;
                }
            }
            logger.info("反序列化a站proxy成功，" + proxyArray.length + "个代理,可用代理" + usableProxyCount + "个");
        } catch (Exception e) {
            logger.warn("反序列化a站proxy失败");
        }
    }

    /**
     * 抓取代理
     */
    public void startCrawl(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    for (String url : ProxyPool.proxyMap.keySet()){
                        /**
                         * 首次本机直接下载代理页面
                         */
                        proxyDownloadThreadExecutor.execute(new AcfunProxyPageTask(url, false));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(1000 * 60 * 60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new AcfunProxySerializeTask()).start();
    }
    public ThreadPoolExecutor getProxyTestThreadExecutor() {
        return proxyTestThreadExecutor;
    }

    public ThreadPoolExecutor getProxyDownloadThreadExecutor() {
        return proxyDownloadThreadExecutor;
    }
}