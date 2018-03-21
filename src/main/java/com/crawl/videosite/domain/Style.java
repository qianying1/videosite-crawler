package com.crawl.videosite.domain;

import java.io.Serializable;

/**
 * 视频分类
 */
public class Style extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 6836446947871751901L;
    /**
     * b站视频分类id
     */
    private Long biliBili_rid = 0l;
    /**
     * a站视频分类id
     */
    private Long acfun_tid = -1l;
    /**
     * 分类名称
     */
    private String styleName="";
    /**
     * 父级分类
     */
    private Style parent;

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public Style getParent() {
        return parent;
    }

    public void setParent(Style parent) {
        this.parent = parent;
    }

    public Long getBiliBili_rid() {
        return biliBili_rid;
    }

    public void setBiliBili_rid(Long biliBili_rid) {
        this.biliBili_rid = biliBili_rid;
    }

    public Long getAcfun_tid() {
        return acfun_tid;
    }

    public void setAcfun_tid(Long acfun_tid) {
        this.acfun_tid = acfun_tid;
    }
}
