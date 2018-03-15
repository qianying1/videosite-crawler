package com.crawl.videosite.entity;

import java.io.Serializable;

/**
 * 将会被持久化数据在本地的各个网站的各种id或者属性
 * <p>
 * Created by qianhaibin on 2018/3/3.
 */
public class VideoSiteRankPersistence implements Serializable {
    private static final long serialVersionUID = 6836446947871751901L;
    /**
     * b站视频id
     */
    private Long biliBili_aid = 0l;
    /**
     * b站视频类型id
     */
    private Long biliBili_rid = 0l;
    /**
     * b站视频星期数
     */
    private Integer biliBili_day = 1;
    /**
     * b站up主id
     */
    private Long biliBili_mid = 0l;

    public Long getBiliBili_aid() {
        return biliBili_aid;
    }

    public void setBiliBili_aid(Long biliBili_aid) {
        this.biliBili_aid = biliBili_aid;
    }

    public Long getBiliBili_rid() {
        return biliBili_rid;
    }

    public void setBiliBili_rid(Long biliBili_rid) {
        this.biliBili_rid = biliBili_rid;
    }

    public Integer getBiliBili_day() {
        return biliBili_day;
    }

    public void setBiliBili_day(Integer biliBili_day) {
        this.biliBili_day = biliBili_day;
    }

    public Long getBiliBili_mid() {
        return biliBili_mid;
    }

    public void setBiliBili_mid(Long biliBili_mid) {
        this.biliBili_mid = biliBili_mid;
    }
}
