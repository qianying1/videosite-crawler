package com.crawl.videosite.task.bilibili;


import com.crawl.core.util.Config;
import com.crawl.core.util.Constants;
import com.crawl.videosite.entity.Page;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * 知乎用户关注列表页task
 * 下载成功解析出用户token，去重,构造用户详情url，获，添加到DetailPageDownloadThreadPool
 */
public class BiliBiliListPageTask extends BiliBiliAbstractPageTask {

    public BiliBiliListPageTask(HttpRequestBase request, boolean proxyFlag) {
        super(request, proxyFlag);
    }


    @Override
    void retry() {
        httpClient.getListPageThreadPool().execute(new BiliBiliListPageTask(request, Config.isProxy));
    }

    @Override
    void handle(Page page) {
        System.out.println("BiliBiliListPageTask used!");
        /**
         * "我关注的人"列表页
         */
        /*List<String> urlTokenList = JsonPath.parse(page.getHtml()).read("$.data..url_token");
        for (String s : urlTokenList) {
            if (s == null) {
                continue;
            }
            handleUserToken(s);
        }*/
    }

    private void handleUserToken(String userToken) {
        String url = Constants.BILIBILI_INDEX_URL + "/people/" + userToken + "/following";
        if (!Config.dbEnable) {
            httpClient.getDetailPageThreadPool().execute(new BiliBiliDetailPageTask(url, Config.isProxy));
            return;
        }
//        boolean existUserFlag = VideoSiteDAO.isExistUser(userToken);
        boolean existUserFlag = videoSiteDao1.isExistUser(userToken);
        while (httpClient.getDetailPageThreadPool().getQueue().size() > 1000) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!existUserFlag || httpClient.getDetailPageThreadPool().getActiveCount() == 0) {
            /**
             * 防止互相等待，导致死锁
             */
            httpClient.getDetailPageThreadPool().execute(new BiliBiliDetailPageTask(url, Config.isProxy));

        }
    }
}
