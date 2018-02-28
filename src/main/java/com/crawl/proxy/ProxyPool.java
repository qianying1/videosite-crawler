package com.crawl.proxy;

import com.crawl.proxy.entity.Direct;
import com.crawl.proxy.entity.Proxy;
import com.crawl.proxy.site.ip181.Ip181ProxyListPageParser;
import com.crawl.proxy.site.ip66.Ip66ProxyListPageParser;
import com.crawl.proxy.site.mimiip.MimiipProxyListPageParser;
import com.crawl.proxy.site.xicidaili.XicidailiProxyListPageParser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.crawl.core.util.Constants.TIME_INTERVAL;

/**
 * 代理池
 */
public class ProxyPool {
    /**
     * proxySet读写锁
     */
    public final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public final static Set<Proxy> acfunProxySet = new HashSet<Proxy>();
    public final static Set<Proxy> biliBiliProxySet = new HashSet<Proxy>();
    public final static Set<Proxy> douyuProxySet = new HashSet<Proxy>();
    public final static Set<Proxy> iqiyiProxySet = new HashSet<Proxy>();
    public final static Set<Proxy> letvProxySet = new HashSet<Proxy>();
    public final static Set<Proxy> pptvProxySet = new HashSet<Proxy>();
    public final static Set<Proxy> sohuProxySet = new HashSet<Proxy>();
    public final static Set<Proxy> tudouProxySet = new HashSet<Proxy>();
    public final static Set<Proxy> youkuProxySet = new HashSet<Proxy>();
    public final static Set<Proxy> youtubeProxySet = new HashSet<Proxy>();
    /**
     * 共同代理池延迟队列
     */
    public final static DelayQueue<Proxy> proxyQueue = new DelayQueue();
    /**
     * a站代理池延迟队列
     */
    public final static DelayQueue<Proxy> acfunProxyQueue = new DelayQueue();
    /**
     * b站代理池延迟队列
     */
    public final static DelayQueue<Proxy> biliBiliProxyQueue = new DelayQueue();
    /**
     * 斗鱼代理池延迟队列
     */
    public final static DelayQueue<Proxy> douyuProxyQueue = new DelayQueue();
    /**
     * 爱奇艺代理池延迟队列
     */
    public final static DelayQueue<Proxy> iqiyiProxyQueue = new DelayQueue();
    /**
     * 乐视代理池延迟队列
     */
    public final static DelayQueue<Proxy> letvProxyQueue = new DelayQueue();
    /**
     * 皮皮电影代理池延迟队列
     */
    public final static DelayQueue<Proxy> pptvProxyQueue = new DelayQueue();
    /**
     * 搜狐代理池延迟队列
     */
    public final static DelayQueue<Proxy> sohuProxyQueue = new DelayQueue();
    /**
     * 土豆代理池延迟队列
     */
    public final static DelayQueue<Proxy> tudouProxyQueue = new DelayQueue();
    /**
     * 优酷代理池延迟队列
     */
    public final static DelayQueue<Proxy> youkuProxyQueue = new DelayQueue();
    /**
     * youtube代理池延迟队列
     */
    public final static DelayQueue<Proxy> youtubeProxyQueue = new DelayQueue();
    public final static Map<String, Class> proxyMap = new HashMap<>();

    static {
        int pages = 8;
        for (int i = 1; i <= pages; i++) {
            proxyMap.put("http://www.xicidaili.com/wt/" + i + ".html", XicidailiProxyListPageParser.class);
            proxyMap.put("http://www.xicidaili.com/nn/" + i + ".html", XicidailiProxyListPageParser.class);
            proxyMap.put("http://www.xicidaili.com/wn/" + i + ".html", XicidailiProxyListPageParser.class);
            proxyMap.put("http://www.xicidaili.com/nt/" + i + ".html", XicidailiProxyListPageParser.class);
            proxyMap.put("http://www.ip181.com/daili/" + i + ".html", Ip181ProxyListPageParser.class);
            proxyMap.put("http://www.mimiip.com/gngao/" + i, MimiipProxyListPageParser.class);//高匿
            proxyMap.put("http://www.mimiip.com/gnpu/" + i, MimiipProxyListPageParser.class);//普匿
            proxyMap.put("http://www.66ip.cn/" + i + ".html", Ip66ProxyListPageParser.class);
            for (int j = 1; j < 34; j++) {
                proxyMap.put("http://www.66ip.cn/areaindex_" + j + "/" + i + ".html", Ip66ProxyListPageParser.class);
            }
        }
        proxyQueue.add(new Direct(TIME_INTERVAL));
        acfunProxyQueue.add(new Direct(TIME_INTERVAL));
        biliBiliProxyQueue.add(new Direct(TIME_INTERVAL));
        douyuProxyQueue.add(new Direct(TIME_INTERVAL));
        iqiyiProxyQueue.add(new Direct(TIME_INTERVAL));
        letvProxyQueue.add(new Direct(TIME_INTERVAL));
        pptvProxyQueue.add(new Direct(TIME_INTERVAL));
        sohuProxyQueue.add(new Direct(TIME_INTERVAL));
        tudouProxyQueue.add(new Direct(TIME_INTERVAL));
        youkuProxyQueue.add(new Direct(TIME_INTERVAL));
        youtubeProxyQueue.add(new Direct(TIME_INTERVAL));
    }

}
