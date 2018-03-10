package com.crawl.videosite.entity;

import java.io.Serializable;

/**
 * 将会被持久化数据在本地的各个网站的各种id或者属性
 * <p>
 * Created by qianhaibin on 2018/3/3.
 */
public class VideoPersistence implements Serializable {

    /**
     * b站视频id
     */
    private Long biliBili_aid = 0l;

    public Long getBiliBili_aid() {
        return biliBili_aid;
    }

    public void setBiliBili_aid(Long biliBili_aid) {
        this.biliBili_aid = biliBili_aid;
    }
}
