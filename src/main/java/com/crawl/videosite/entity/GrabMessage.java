package com.crawl.videosite.entity;

import java.io.Serializable;

/**
 * 需要爬取的信息
 */
public class GrabMessage extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 3147314731230065771L;

	/**
	 * 视频名称
	 */
	private String videoName;
	/**
	 * 视频播放次数
	 */
	private String playCount;
	/**
	 * 收藏数
	 */
	private Integer likeCount;
	/**
	 * 评论数
	 */
	private Integer commentCount;
	/**
	 * 弹幕数
	 */
	private Integer barrage;
	/**
	 * 投蕉数
	 */
	private Integer bananaCount;
	/**
	 * 视频发布时间
	 */
	private String videoAddTime;
	/**
	 * 作者
	 */
	private VideoAuthor author;
	/**
	 * 视频类型
	 */
	private Style videoType;

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getPlayCount() {
		return playCount;
	}

	public void setPlayCount(String playCount) {
		this.playCount = playCount;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getBarrage() {
		return barrage;
	}

	public void setBarrage(Integer barrage) {
		this.barrage = barrage;
	}

	public Integer getBananaCount() {
		return bananaCount;
	}

	public void setBananaCount(Integer bananaCount) {
		this.bananaCount = bananaCount;
	}

	public String getVideoAddTime() {
		return videoAddTime;
	}

	public void setVideoAddTime(String videoAddTime) {
		this.videoAddTime = videoAddTime;
	}

	public VideoAuthor getAuthor() {
		return author;
	}

	public void setAuthor(VideoAuthor author) {
		this.author = author;
	}

	public Style getVideoType() {
		return videoType;
	}

	public void setVideoType(Style videoType) {
		this.videoType = videoType;
	}
}
