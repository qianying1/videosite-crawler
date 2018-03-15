package com.crawl.videosite.entity;

import java.io.Serializable;

/**
 * 国产连载序列化类
 */
public class BangumiGuochanPersistence implements Serializable {
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
     * b站视频大类型id
     */
    private Long biliBili_original = 0l;
    /**
     * b站视频星期数
     */
    private Integer biliBili_day = 1;
    /**
     * b站up主id
     */
    private Long biliBili_mid = 0l;
    /**
     * b站当前页码
     */
    private Integer biliBili_pn = 1;

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

    public Long getBiliBili_original() {
        return biliBili_original;
    }

    public void setBiliBili_original(Long biliBili_original) {
        this.biliBili_original = biliBili_original;
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

    public Integer getBiliBili_pn() {
        return biliBili_pn;
    }

    public void setBiliBili_pn(Integer biliBili_pn) {
        this.biliBili_pn = biliBili_pn;
    }
}
