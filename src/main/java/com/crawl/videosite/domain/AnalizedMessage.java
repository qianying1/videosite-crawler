package com.crawl.videosite.domain;

import java.io.Serializable;

/**
 * 分析信息
 */
public class AnalizedMessage extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -6138304750843680637L;

	/**
	 * 视频类型
	 */
	private Style videoType;
	/**
	 * 所属视频
	 */
	private Video video;
	/**
	 * 视频名称
	 */
	private String videoName;
	/**
	 * 视频点击次数
	 */
	private Long views;
	/**
	 * 视频平均评分
	 */
	private Float averageEval;
	/**
	 * 视频评论数量
	 */
	private Long comments;

	public Style getVideoType() {
		return videoType;
	}

	public void setVideoType(Style videoType) {
		this.videoType = videoType;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public Long getViews() {
		return views;
	}

	public void setViews(Long views) {
		this.views = views;
	}

	public Float getAverageEval() {
		return averageEval;
	}

	public void setAverageEval(Float averageEval) {
		this.averageEval = averageEval;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}
}
