package com.crawl.videosite.dao;

import com.crawl.videosite.domain.Video;

import java.sql.Connection;

/**
 * 视频持久层
 */
public interface VideoDao extends Dao {

    boolean isExistVideo(Long aid);

    /**
     * a站中是否存在视频
     *
     * @param acfunVid
     * @return
     */
    boolean isExistVideoInAcfun(Long acfunVid);

    /**
     * 是否存在视频
     *
     * @param aid
     * @return
     */
    boolean isExistVideo(Connection conn, Long aid);

    /**
     * 是否存在a站的视频数据
     *
     * @param acfunVid
     * @return
     */
    boolean isExistVideoByAcfunVid(Long acfunVid);

    /**
     * 插入视频数据
     *
     * @param video
     * @return
     */
    Long insertVideo(Video video);

    /**
     * 更新a站视频信息
     *
     * @param video
     * @return
     */
    boolean updateAcfunVideo(Video video);

    /**
     * 插入视频数据
     *
     * @param conn
     * @param video
     * @return
     */
    Long insertVideo(Connection conn, Video video);

    /**
     * 更新视频数据
     *
     * @param video
     * @return
     */
    boolean updateVideo(Video video);

    /**
     * 通过a站视频id查找视频
     *
     * @param conn
     * @param vid
     * @return
     */
    Video selectVideoByAcfunVid(Connection conn, Long vid);

    /**
     * 通过视频id查找视频数据
     *
     * @param conn
     * @param aid
     * @return
     */
    Video selectVideoByAid(Connection conn, Long aid);

    /**
     * 更新视频数据
     *
     * @param conn
     * @param video
     * @return
     */
    boolean updateVideo(Connection conn, Video video);
}
