package com.crawl.videosite.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 视频
 */
public class Video extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -8877562315779202950L;
    /**
     * b站视频id
     */
    private Long biliBili_aid;
    /**
     * b站作者id
     */
    private Long biliBili_mid;
    /**
     * b站视频分类id
     */
    private Long biliBili_rid;
    /**
     * 指向的链接
     */
    private String href;
    /**
     * 标题
     */
    private String title;
    /**
     * logo图片
     */
    private String logo;
    /**
     * up主名称
     */
    private String upMan;

    /**
     * 播放次数
     */
    private Long views;
    /**
     * 评论回复数量
     */
    private Long replay;
    /**
     * 视频收藏数量
     */
    private Long favorite;
    /**
     * 视频投币数量
     */
    private Long coin;
    /**
     * 视频分享数量
     */
    private Long share;
    /**
     * 视频喜欢数量
     */
    private Long like;
    /**
     * 视频动态
     */
    private String dynamic;
    /**
     * 弹幕数量
     */
    private Long masks;
    /**
     * 视频长度
     */
    private String times;
    /**
     * 投蕉数
     */
    private Long bananas;

    /**
     * 评论数量
     */
    private Long comments;

    /**
     * 视频作者
     */
    private VideoAuthor author;

    /**
     * 在页面中的位置
     */
    private String location;
    /**
     * 视频类型
     */
    private Style style;
    /**
     * 视频发布时间
     */
    private Date pubdate;
    /**
     * 视频创建时间
     */
    private Date ctime;
    /**
     * 视频描述
     */
    private String desc;


    public Long getBiliBili_mid() {
        return biliBili_mid;
    }

    public void setBiliBili_mid(Long biliBili_mid) {
        this.biliBili_mid = biliBili_mid;
    }

    public Long getBiliBili_rid() {
        return biliBili_rid;
    }

    public void setBiliBili_rid(Long biliBili_rid) {
        this.biliBili_rid = biliBili_rid;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public VideoAuthor getAuthor() {
        return author;
    }

    public void setAuthor(VideoAuthor author) {
        this.author = author;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public Long getBananas() {
        return bananas;
    }

    public void setBananas(Long bananas) {
        this.bananas = bananas;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Video() {
        super();
    }

    public String getUpMan() {
        return upMan;
    }

    public void setUpMan(String upMan) {
        this.upMan = upMan;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getMasks() {
        return masks;
    }

    public void setMasks(Long masks) {
        this.masks = masks;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Long getBiliBili_aid() {
        return biliBili_aid;
    }

    public void setBiliBili_aid(Long biliBili_aid) {
        this.biliBili_aid = biliBili_aid;
    }

    public Long getReplay() {
        return replay;
    }

    public void setReplay(Long replay) {
        this.replay = replay;
    }

    public Long getFavorite() {
        return favorite;
    }

    public void setFavorite(Long favorite) {
        this.favorite = favorite;
    }

    public Long getCoin() {
        return coin;
    }

    public void setCoin(Long coin) {
        this.coin = coin;
    }

    public Long getShare() {
        return share;
    }

    public void setShare(Long share) {
        this.share = share;
    }

    public Long getLike() {
        return like;
    }

    public void setLike(Long like) {
        this.like = like;
    }

    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }

    public Date getPubdate() {
        return pubdate;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
