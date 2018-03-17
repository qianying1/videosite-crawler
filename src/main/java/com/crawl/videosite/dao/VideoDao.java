package com.crawl.videosite.dao;

import com.crawl.videosite.domain.Video;

import java.sql.Connection;

/**
 * 视频持久层
 */
public interface VideoDao extends Dao {

    boolean isExistVideo(Long aid);

    /**
     * 是否存在视频
     *
     * @param aid
     * @return
     */
    boolean isExistVideo(Connection conn, Long aid);

    /**
     * 插入视频数据
     *
     * @param video
     * @return
     */
    Long insertVideo(Video video);

    /**
     * 插入视频数据
     *
     * @param conn
     * @param video
     * @return
     */
    Long insertVideo(Connection conn,Video video);

    /**
     * 更新视频数据
     *
     * @param video
     * @return
     */
    boolean updateVideo(Video video);

    /**
     * 通过视频id查找视频数据
     *
     * @param conn
     * @param aid
     * @return
     */
    Video selectVideoByAid(Connection conn,Long aid);

    /**
     * 更新视频数据
     *
     * @param conn
     * @param video
     * @return
     */
    boolean updateVideo(Connection conn,Video video);
}
