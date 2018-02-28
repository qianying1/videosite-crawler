package com.crawl.videosite.task.letv;


import com.crawl.videosite.entity.Page;


/**
 * GeneralPageTaskCommon
 * 下载初始化authorization字段页面
 */
public class LetvGeneralPageTask extends LetvAbstractPageTask {
    private Page page = null;

    public LetvGeneralPageTask(String url, boolean proxyFlag) {
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
        this.page = page;
    }

    public Page getPage(){
        return page;
    }
}