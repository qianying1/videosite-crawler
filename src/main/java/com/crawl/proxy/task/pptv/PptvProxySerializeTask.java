package com.crawl.proxy.task.pptv;

import com.crawl.core.util.Config;
import com.crawl.core.util.HttpClientUtil;
import com.crawl.proxy.ProxyPool;
import com.crawl.proxy.entity.Proxy;
import com.crawl.proxy.util.ProxyUtil;
import com.crawl.videosite.ProxyHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 代理序列化
 */
public class PptvProxySerializeTask implements Runnable{
    private static Logger logger = LoggerFactory.getLogger(PptvProxyPageTask.class);
    @Override
    public void run() {
        while (!ProxyHttpClient.isStop){
            try {
                Thread.sleep(1000 * 60 * 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Proxy[] proxyArray = null;
            ProxyPool.lock.readLock().lock();
            try {
                proxyArray = new Proxy[ProxyPool.pptvProxySet.size()];
                int i = 0;
                for (Proxy p : ProxyPool.pptvProxySet){
                    if (!ProxyUtil.isDiscardProxy(p)){
                        proxyArray[i++] = p;
                    }
                }
            } finally {
                ProxyPool.lock.readLock().unlock();
            }

            HttpClientUtil.serializeObject(proxyArray, Config.pptvProxyPath);
            logger.info("成功序列化皮皮电影" + proxyArray.length + "个代理");
        }
    }
}
