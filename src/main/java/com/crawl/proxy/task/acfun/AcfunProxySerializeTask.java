package com.crawl.proxy.task.acfun;

import com.crawl.core.util.Config;
import com.crawl.core.util.HttpClientUtil;
import com.crawl.proxy.ProxyPool;
import com.crawl.proxy.entity.Proxy;
import com.crawl.proxy.util.ProxyUtil;
import com.crawl.videosite.CommonHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 代理序列化
 */
public class AcfunProxySerializeTask implements Runnable{
    private static Logger logger = LoggerFactory.getLogger(AcfunProxyPageTask.class);
    @Override
    public void run() {
        while (!CommonHttpClient.isStop){
            try {
                Thread.sleep(1000 * 60 * 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Proxy[] proxyArray = null;
            ProxyPool.lock.readLock().lock();
            try {
                proxyArray = new Proxy[ProxyPool.acfunProxySet.size()];
                int i = 0;
                for (Proxy p : ProxyPool.acfunProxySet){
                    if (!ProxyUtil.isDiscardProxy(p)){
                        proxyArray[i++] = p;
                    }
                }
            } finally {
                ProxyPool.lock.readLock().unlock();
            }

            HttpClientUtil.serializeObject(proxyArray, Config.acfunProxyPath);
            logger.info("a站成功序列化" + proxyArray.length + "个代理");
        }
    }
}
