package com.crawl.videosite.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 基类
 */
public class BaseEntity implements Serializable{
    private static final long serialVersionUID = -7223924703320916160L;

    /**
     * 唯一主键
     */
    private Long id;
    /**
     * 产生时间
     */
    private Date createDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
