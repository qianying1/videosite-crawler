package com.crawl.videosite.domain;

import java.io.Serializable;

/**
 * 视频
 */
public class Video extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -8877562315779202950L;

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
}
