package com.crawl.videosite.entity;

import java.io.Serializable;

/**
 * 需要抓取的网站
 */
@SuppressWarnings("serial")
public class WebSites implements Serializable{

	private Integer websiteId;
	private String websiteName;
	private String websiteUrl;

	public WebSites() {
		super();
	}

	@Override
	public String toString() {
		return "WebSites [websiteId=" + websiteId + ", websiteName=" + websiteName + ", websiteUrl=" + websiteUrl + "]";
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public Integer getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}

}
