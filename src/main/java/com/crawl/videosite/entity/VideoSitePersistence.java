package com.crawl.videosite.entity;

import java.io.Serializable;

/**
 * 将会被持久化数据在本地的各个网站的各种id或者属性
 *
 * Created by qianhaibin on 2018/3/3.
 */
public class VideoSitePersistence implements Serializable{

    /**
     * b站视频id
     */
    private Long biliBili_aid=0l;
    /**
     * b站视频类型id
     */
    private Long biliBili_rid=0l;
    /**
     * b站视频大类型id
     */
    private Long biliBili_original=0l;
    /**
     * b站视频星期数
     */
    private Integer biliBili_day=1;
    /**
     * b站up主id
     */
    private Long biliBili_mid=0l;

}
