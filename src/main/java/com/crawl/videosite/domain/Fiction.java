package com.crawl.videosite.domain;

import java.io.Serializable;

/**
 * 小说
 */
public class Fiction extends BaseEntity implements Serializable {
    /**
     * 封面
     */
    private String cover;
    /**
     * 弹幕数量
     */
    private Long dm_count = 0l;
    /**
     * 收藏人数
     */
    private Long favorite = 0l;
    /**
     * 是否已完结
     */
    private int is_finish = 0;
    /**
     * 是否已开始上线
     */
    private int is_started = 0;
    /**
     * 最新集数
     */
    private String newest_ep_index;
    /**
     * 播放次数
     */
    private Long play_count = 0l;
    /**
     * b站中不知道是什么的字段
     */
    private Long pts = 0l;
    /**
     * 季id
     */
    private Long seasion_id = 0l;
    /**
     * 季状态
     */
    private short season_status = 0;
    /**
     * 方形封面
     */
    private String square_cover;
    /**
     * 标题
     */
    private String title;
    /**
     * 总集数
     */
    private Long total_count = 0l;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getDm_count() {
        return dm_count;
    }

    public void setDm_count(Long dm_count) {
        this.dm_count = dm_count;
    }

    public Long getFavorite() {
        return favorite;
    }

    public void setFavorite(Long favorite) {
        this.favorite = favorite;
    }

    public int getIs_finish() {
        return is_finish;
    }

    public void setIs_finish(int is_finish) {
        this.is_finish = is_finish;
    }

    public int getIs_started() {
        return is_started;
    }

    public void setIs_started(int is_started) {
        this.is_started = is_started;
    }

    public String getNewest_ep_index() {
        return newest_ep_index;
    }

    public void setNewest_ep_index(String newest_ep_index) {
        this.newest_ep_index = newest_ep_index;
    }

    public Long getPlay_count() {
        return play_count;
    }

    public void setPlay_count(Long play_count) {
        this.play_count = play_count;
    }

    public Long getPts() {
        return pts;
    }

    public void setPts(Long pts) {
        this.pts = pts;
    }

    public Long getSeasion_id() {
        return seasion_id;
    }

    public void setSeasion_id(Long seasion_id) {
        this.seasion_id = seasion_id;
    }

    public short getSeason_status() {
        return season_status;
    }

    public void setSeason_status(short season_status) {
        this.season_status = season_status;
    }

    public String getSquare_cover() {
        return square_cover;
    }

    public void setSquare_cover(String square_cover) {
        this.square_cover = square_cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Long total_count) {
        this.total_count = total_count;
    }
}
