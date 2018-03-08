package com.crawl.videosite.entity;

import java.io.Serializable;

/**
 * 视频作者需要持久化的信息
 */
public class VideoAuthorPersistence implements Serializable {
    /**
     * 视频作者id
     */
    private Long mid;

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }
}
