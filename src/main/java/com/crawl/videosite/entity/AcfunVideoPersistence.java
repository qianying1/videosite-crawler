package com.crawl.videosite.entity;

import java.io.Serializable;

/**
 * a站视频持久信息
 */
public class AcfunVideoPersistence implements Serializable {
    private static final long serialVersionUID = 6836446947871751981L;
    /**
     * 视频内容id
     */
    private Long contentId = 0l;

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }
}
