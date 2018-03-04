package com.crawl.videosite.dao;


import com.crawl.videosite.domain.Style;
import com.crawl.videosite.domain.Video;
import com.crawl.videosite.domain.VideoAuthor;

import java.sql.Connection;

public interface BiliBiliDao extends Dao {

    boolean isExistVideo(Long aid);

    /**
     * 是否存在视频
     *
     * @param aid
     * @return
     */
    boolean isExistVideo(Connection conn, Long aid);

    boolean isExistVideoType(Long rid);

    boolean isExistAuthor(Long mid);

    boolean isExistAuthor(Connection conn, Long mid);

    boolean isExistVideoType(Connection cn, Long rid);

    Long insertVideoType(Style type);

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

    Long insertVideoType(Connection cn, Style type);

    Long insertAuthor(VideoAuthor author);

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
     */Video selectVideoByAid(Connection conn,Long aid);

    /**
     * 更新视频数据
     *
     * @param conn
     * @param video
     * @return
     */
    boolean updateVideo(Connection conn,Video video);

    Long updateAuthor(VideoAuthor author);

    /**
     * 更新视频作者信息
     *
     * @param author
     * @param conn
     * @return
     */
    Long updateAuthor(Connection conn, VideoAuthor author);

    Long insertAuthor(Connection conn, VideoAuthor author);

    VideoAuthor selectAuthorByMid(Connection conn, Long mid);

    /**
     * 根据视频类型id查询视频类型
     *
     * @param conn
     * @param rid
     * @return
     */
    Style selectVideoTypeByRid(Connection conn, Long rid);

    Long updateVideoType(Style type);

    /**
     * 更新视频类型数据
     *
     * @param type
     * @return
     */
    Long updateVideoType(Connection conn, Style type);

    /*boolean isExistUser(Connection cn, String userToken);

    boolean insertUser(User user);

    boolean insertUser(Connection cn, User user);

    *//**
     * 插入url,插入成功返回true，若已存在该url则返回false
     *
     * @param cn
     * @param md5Url
     * @return
     *//*
    boolean insertUrl(Connection cn, String md5Url);

    boolean isExistUser(String userToken);*/
}
