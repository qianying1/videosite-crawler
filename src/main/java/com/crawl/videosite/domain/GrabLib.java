package com.crawl.videosite.domain;

import java.io.Serializable;

/**
 * 爬取字典
 */
public class GrabLib extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -4830710894170606522L;
	/**
	 * 所属网站
	 */
	private String website;
	/**
	 * 链接地址
	 */
	private String webSiteAddr;
	/**
	 * 是否已经爬取
	 */
	private Boolean isGrabbed=false;
	/**
	 * 链接地址名称
	 */
	private String name;

	public GrabLib() {
		super();
	}

	public Boolean getIsGrabbed() {
		return isGrabbed;
	}

	public void setIsGrabbed(Boolean isGrabbed) {
		this.isGrabbed = isGrabbed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebSiteAddr() {
		return webSiteAddr;
	}

	public void setWebSiteAddr(String webSiteAddr) {
		this.webSiteAddr = webSiteAddr;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

}
