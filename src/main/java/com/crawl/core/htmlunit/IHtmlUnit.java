package com.crawl.core.htmlunit;

/**
 * htmlunit客户端接口
 *
 * Created by qianhaibin on 2018/2/28.
 */
public interface IHtmlUnit {

    /**
     * 初始化客户端
     * 模拟登录，持久化Cookie到本地
     * 不用以后每次都登录
     */
    abstract void initHttpClient();

    /**
     * 爬虫入口
     *
     * @param url
     */
    void startCrawl(String url);

    /**
     * 爬虫入口
     */
    void startCrawl();
}
