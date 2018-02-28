package com.crawl.videosite.entity;

import java.io.Serializable;

/**
 * 视频分类
 */
public class Style extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 6836446947871751901L;
	/**
	 * 分类名称
	 */
	private String styleName;
	/**
	 * 父级分类
	 */
	private Style parent;

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public Style getParent() {
		return parent;
	}

	public void setParent(Style parent) {
		this.parent = parent;
	}
}
