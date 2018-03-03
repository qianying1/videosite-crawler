package com.crawl.videosite.dao;


import com.crawl.videosite.domain.Style;
import com.crawl.videosite.domain.User;
import com.crawl.videosite.domain.VideoAuthor;

import java.sql.Connection;
import java.sql.SQLException;

public interface BiliBiliDao extends Dao {

    boolean isExistVideoType(Long rid);

    boolean isExistAuthor(Long mid);

    boolean isExistAuthor(Connection conn,Long mid);

    boolean isExistVideoType(Connection cn, Long rid);

    boolean insertVideoType(Style type);

    boolean insertVideoType(Connection cn, Style type);

    boolean insertAuthor(VideoAuthor author);

    boolean insertAuthor(Connection conn,VideoAuthor author);

    VideoAuthor selectAuthorByMid(Connection conn,Long mid);

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
