package com.crawl.videosite.domain;

import java.io.Serializable;

/**
 *	正在抓取的网站
 */
public class GrabingWeb extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -9032312874483163637L;

	private String webName;
	private String url;

	@Override
	public String toString() {
		return "GrabingWeb [webName=" + webName + ", url=" + url + "]";
	}

	public GrabingWeb() {
		super();
	}

	public GrabingWeb(String webName, String url) {
		super();
		this.setWebName(webName);
		this.setUrl(url);
	}

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
