package com.crawl.videosite.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 短视频作者
 */
public class VideoAuthor extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -2043979559882919440L;
    /**
     * b站作者id
     */
    private Long biliBili_mid = -1l;
    /**
     * 作者名称
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 首页地址
     */
    private String indexHref;
    /**
     * 签名
     */
    private String signature;
    /**
     * 文章数量
     */
    private Long article=0l;
    /**
     * 追随者数量
     */
    private Long follower=0l;
    /**
     * 拥有视频数量
     */
    private Integer videoCount = 0;
    /**
     * 受关注度
     */
    private Integer attentionCount = 0;
    /**
     * 粉丝数量
     */
    private Integer audienceCount = 0;
    /**
     * 作者头像
     */
    private String logo;
    /**
     * 位置
     */
    private String location;
    /**
     * 短视频
     */
    private List<Video> videoNodes;

    public VideoAuthor() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndexHref() {
        return indexHref;
    }

    public void setIndexHref(String indexHref) {
        this.indexHref = indexHref;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public Integer getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(Integer attentionCount) {
        this.attentionCount = attentionCount;
    }

    public Integer getAudienceCount() {
        return audienceCount;
    }

    public void setAudienceCount(Integer audienceCount) {
        this.audienceCount = audienceCount;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Video> getVideoNodes() {
        return videoNodes;
    }

    public void setVideoNodes(List<Video> videoNodes) {
        this.videoNodes = videoNodes;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getArticle() {
        return article;
    }

    public void setArticle(Long article) {
        this.article = article;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getFollower() {
        return follower;
    }

    public void setFollower(Long follower) {
        this.follower = follower;
    }

    public Long getBiliBili_mid() {
        return biliBili_mid;
    }

    public void setBiliBili_mid(Long biliBili_mid) {
        this.biliBili_mid = biliBili_mid;
    }
}
