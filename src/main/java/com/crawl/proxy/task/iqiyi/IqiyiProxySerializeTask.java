package com.crawl.proxy.task.iqiyi;

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
public class IqiyiProxySerializeTask implements Runnable{
    private static Logger logger = LoggerFactory.getLogger(IqiyiProxyPageTask.class);
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
                proxyArray = new Proxy[ProxyPool.iqiyiProxySet.size()];
                int i = 0;
                for (Proxy p : ProxyPool.iqiyiProxySet){
                    if (!ProxyUtil.isDiscardProxy(p)){
                        proxyArray[i++] = p;
                    }
                }
            } finally {
                ProxyPool.lock.readLock().unlock();
            }

            HttpClientUtil.serializeObject(proxyArray, Config.iqiyiProxyPath);
            logger.info("成功序列化爱奇艺" + proxyArray.length + "个代理");
        }
    }
}