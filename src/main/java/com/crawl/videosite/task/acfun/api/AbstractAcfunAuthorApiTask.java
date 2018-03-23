package com.crawl.videosite.task.acfun.api;

import com.crawl.videosite.entity.AcfunParams;
import com.crawl.videosite.task.CommonTask;

/**
 * a站视频作者信息爬取任务
 */
public abstract class AbstractAcfunAuthorApiTask extends CommonTask implements Runnable {

    /**
     * 目标固定api地址
     */
    private static final String targetDomain = AcfunParams.upManDetailDomain;
    /**
     * 目标地址userId参数值
     */
    private static Long userId = 0l;

    protected AbstractAcfunAuthorApiTask(Long userId) {
        AbstractAcfunAuthorApiTask.userId=userId;
    }

    @Override
    public void run() {

    }

    /**
     * 获取目标地址
     *
     * @return
     */
    private String getTargetUrl() {
        return targetDomain + userId;
    }
}
