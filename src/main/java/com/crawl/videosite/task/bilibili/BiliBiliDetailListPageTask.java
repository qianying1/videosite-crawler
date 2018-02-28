package com.crawl.videosite.task.bilibili;


import com.crawl.core.dao.ConnectionManager;
import com.crawl.core.parser.ListPageParser;
import com.crawl.core.util.Config;
import com.crawl.core.util.SimpleInvocationHandler;
import com.crawl.videosite.entity.Page;
import com.crawl.videosite.entity.Video;
import com.crawl.videosite.parser.VideoSiteUserListPageParser;
import com.crawl.videosite.parser.bilibili.BiliBiliVideoSiteIndexPageParser;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 知乎用户列表详情页task
 */
public class BiliBiliDetailListPageTask extends BiliBiliAbstractPageTask {
    private static Logger logger = LoggerFactory.getLogger(BiliBiliDetailListPageTask.class);
    private static ListPageParser proxyUserListPageParser;
    private static ListPageParser proxyVideoListPageParser;
    /**
     * Thread-数据库连接
     */
    private static Map<Thread, Connection> connectionMap = new ConcurrentHashMap<>();

    static {
        proxyUserListPageParser = getProxyUserListPageParser();
        proxyVideoListPageParser = getProxyVideoListPageParser();
    }


    public BiliBiliDetailListPageTask(HttpRequestBase request, boolean proxyFlag) {
        super(request, proxyFlag);
    }

    /**
     * 代理类
     *
     * @return
     */
    private static ListPageParser getProxyUserListPageParser() {
        ListPageParser userListPageParser = VideoSiteUserListPageParser.getInstance();
        InvocationHandler invocationHandler = new SimpleInvocationHandler(userListPageParser);
        ListPageParser proxyUserListPageParser = (ListPageParser) Proxy.newProxyInstance(userListPageParser.getClass().getClassLoader(),
                userListPageParser.getClass().getInterfaces(), invocationHandler);
        return proxyUserListPageParser;
    }

    /**
     * 视频列表分析器代理类
     *
     * @return
     */
    private static ListPageParser getProxyVideoListPageParser() {
        ListPageParser videoListPageParser = BiliBiliVideoSiteIndexPageParser.getInstance();
        InvocationHandler invocationHandler = new SimpleInvocationHandler(videoListPageParser);
        ListPageParser proxyUserListPageParser = (ListPageParser) Proxy.newProxyInstance(videoListPageParser.getClass().getClassLoader(),
                videoListPageParser.getClass().getInterfaces(), invocationHandler);
        return proxyUserListPageParser;
    }

    @Override
    void retry() {
        httpClient.getDetailListPageThreadPool().execute(new BiliBiliDetailListPageTask(request, Config.isProxy));
    }

    @Override
    void handle(Page page) {
        /*if (!page.getHtml().startsWith("bili-header-m")) {
            //代理异常，未能正确返回目标请求数据，丢弃
            currentProxy = null;
            return;
        }*/
        System.out.println("BiliBiliDetailListPageTask used!");
        List<Video> list = proxyVideoListPageParser.parseListPage(page);
        for (Video v : list) {
            /*logger.info("解析用户成功:" + u.toString());
            if (Config.dbEnable) {
                Connection cn = getConnection();
                if (videoSiteDao1.insertUser(cn, u)) {
                    parseUserCount.incrementAndGet();
                }
                for (int j = 0; j < u.getFollowees() / 20; j++) {
                    if (commonHttpClient.getDetailListPageThreadPool().getQueue().size() > 1000) {
                        continue;
                    }
                    String nextUrl = String.format(USER_FOLLOWEES_URL, u.getUserToken(), j * 20);
                    if (videoSiteDao1.insertUrl(cn, Md5Util.Convert2Md5(nextUrl)) ||
                            commonHttpClient.getDetailListPageThreadPool().getActiveCount() == 1) {
                        //防止死锁
                        HttpGet request = new HttpGet(nextUrl);
                        request.setHeader("authorization", "oauth " + CommonHttpClient.getAuthorization());
                        commonHttpClient.getDetailListPageThreadPool().execute(new BiliBiliDetailListPageTask(request, true));
                    }
                }
            } else if (!Config.dbEnable || commonHttpClient.getDetailListPageThreadPool().getActiveCount() == 1) {
                parseUserCount.incrementAndGet();
                for (int j = 0; j < u.getFollowees() / 20; j++) {
                    String nextUrl = String.format(USER_FOLLOWEES_URL, u.getUserToken(), j * 20);
                    HttpGet request = new HttpGet(nextUrl);
                    request.setHeader("authorization", "oauth " + CommonHttpClient.getAuthorization());
                    commonHttpClient.getDetailListPageThreadPool().execute(new BiliBiliDetailListPageTask(request, true));
                }
            }*/
        }
    }

    /**
     * 每个thread维护一个Connection
     *
     * @return
     */
    private Connection getConnection() {
        Thread currentThread = Thread.currentThread();
        Connection cn = null;
        if (!connectionMap.containsKey(currentThread)) {
            cn = ConnectionManager.createConnection();
            connectionMap.put(currentThread, cn);
        } else {
            cn = connectionMap.get(currentThread);
        }
        return cn;
    }

    public static Map<Thread, Connection> getConnectionMap() {
        return connectionMap;
    }

}
