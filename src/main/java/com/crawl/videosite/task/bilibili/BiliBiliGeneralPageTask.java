package com.crawl.videosite.task.bilibili;


import com.crawl.videosite.entity.Page;


/**
 * GeneralPageTaskCommon
 * 下载初始化authorization字段页面
 */
public class BiliBiliGeneralPageTask extends BiliBiliAbstractPageTask {
    private Page page = null;

    public BiliBiliGeneralPageTask(String url, boolean proxyFlag) {
        super(url, proxyFlag);
    }

    @Override
    void retry() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.run();//继续下载
    }

    @Override
    void handle(Page page) {
        System.out.println("BiliBiliGeneralPageTask used!");
        this.page = page;
    }

    public Page getPage() {
        return page;
    }
}
