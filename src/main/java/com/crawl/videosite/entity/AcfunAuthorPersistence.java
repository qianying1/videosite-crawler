package com.crawl.videosite.entity;

import java.io.Serializable;

/**
 * a站视频作者持久信息
 */
public class AcfunAuthorPersistence implements Serializable{
    private static final long serialVersionUID = 6836446947871751998L;

    /**
     * 视频作者用户id
     */
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
