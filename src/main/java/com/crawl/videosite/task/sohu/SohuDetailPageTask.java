package com.crawl.videosite.task.sohu;


import com.crawl.core.parser.DetailPageParser;
import com.crawl.core.util.Config;
import com.crawl.core.util.SimpleInvocationHandler;
import com.crawl.videosite.CommonHttpClient;
import com.crawl.videosite.entity.Page;
import com.crawl.videosite.entity.User;
import com.crawl.videosite.parser.VideoSiteNewUserDetailPageParser;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static com.crawl.videosite.CommonHttpClient.parseUserCount;

/**
 * 知乎用户详情页task
 * 下载成功解析出用户信息并添加到数据库，获取该用户的关注用户list url，添加到ListPageDownloadThreadPool
 */
public class SohuDetailPageTask extends SohuAbstractPageTask {
    private static Logger logger = LoggerFactory.getLogger(SohuDetailPageTask.class);
    private static DetailPageParser proxyDetailPageParser;

    static {
        proxyDetailPageParser = getProxyDetailParser();
    }

    public SohuDetailPageTask(String url, boolean proxyFlag) {
        super(url, proxyFlag);
    }

    @Override
    void retry() {
        commonHttpClient.getDetailPageThreadPool().execute(new SohuDetailPageTask(url, Config.isProxy));
    }

    @Override
    void handle(Page page) {
        DetailPageParser parser = null;
//        parser = VideoSiteNewUserDetailPageParser.getInstance();
        parser = proxyDetailPageParser;
        User u = parser.parseDetailPage(page);
        logger.info("解析用户成功:" + u.toString());
        if (Config.dbEnable) {
//            VideoSiteDAO.insertUser(u);
            videoSiteDao1.insertUser(u);
        }
        parseUserCount.incrementAndGet();
        for (int i = 0; i < u.getFollowees() / 20 + 1; i++) {
            String userFolloweesUrl = formatUserFolloweesUrl(u.getUserToken(), 20 * i);
            handleUrl(userFolloweesUrl);
        }
    }

    public String formatUserFolloweesUrl(String userToken, int offset) {
        String url = "https://www.videosite.com/api/v4/members/" + userToken + "/followees?include=data%5B*%5D.answer_count%2Carticles_count%2Cfollower_count%2C" +
                "is_followed%2Cis_following%2Cbadge%5B%3F(type%3Dbest_answerer)%5D.topics&offset=" + offset + "&limit=20";
        return url;
    }

    private void handleUrl(String url) {
        HttpGet request = new HttpGet(url);
        request.setHeader("authorization", "oauth " + CommonHttpClient.getAuthorization());
        if (!Config.dbEnable) {
            commonHttpClient.getListPageThreadPool().execute(new SohuListPageTask(request, Config.isProxy));
            return;
        }
        commonHttpClient.getListPageThreadPool().execute(new SohuListPageTask(request, Config.isProxy));
    }

    /**
     * 代理类
     *
     * @return
     */
    private static DetailPageParser getProxyDetailParser() {
        DetailPageParser detailPageParser = VideoSiteNewUserDetailPageParser.getInstance();
        InvocationHandler invocationHandler = new SimpleInvocationHandler(detailPageParser);
        DetailPageParser proxyDetailPageParser = (DetailPageParser) Proxy.newProxyInstance(detailPageParser.getClass().getClassLoader(),
                detailPageParser.getClass().getInterfaces(), invocationHandler);
        return proxyDetailPageParser;
    }
}
