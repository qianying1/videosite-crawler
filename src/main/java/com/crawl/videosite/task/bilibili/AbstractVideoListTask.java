package com.crawl.videosite.task.bilibili;

/**
 *
 */
public abstract class AbstractVideoListTask implements Runnable {

    private String targetUrl;

    public AbstractVideoListTask(String target) {
        this.targetUrl = target;
    }

    @Override
    public void run() {

    }

    /**
     * 子类实现page的处理
     *
     * @param
     */
    abstract void handle();
}
