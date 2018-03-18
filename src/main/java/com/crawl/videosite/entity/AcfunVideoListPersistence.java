package com.crawl.videosite.entity;

import java.util.Date;

/**
 * a站视频列表内容
 */
public class AcfunVideoListPersistence {
    /**
     * 视频id
     */
    private Long id=0l;
    /**
     * 视频页面大小
     */
    private Integer pageSize=50;
    /**
     * 视频页码
     */
    private Integer pageNo=1;
    /**
     * 视频内容id
     */
    private Integer type=1;
    /**
     * 类似于视频分类的东西
     */
    private Integer cd=1;
    /**
     * 格式
     */
    private String format="system.recomlist";
    /**
     * 时间戳
     */
    private Long _=new Date().getTime();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCd() {
        return cd;
    }

    public void setCd(Integer cd) {
        this.cd = cd;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Long get_() {
        return _;
    }

    public void set_(Long _) {
        this._ = _;
    }
}
