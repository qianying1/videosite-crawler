package com.crawl.videosite.dao;


import com.crawl.core.dao.ConnectionManager;
import com.crawl.videosite.domain.Style;
import com.crawl.videosite.domain.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface BiliBiliDao extends Dao{

    boolean isExistRecord(String sql) throws SQLException;

    boolean isExistRecord(Connection cn, String sql) throws SQLException;

    boolean isExistUser(String userToken);

    boolean isExistVideoType(Long rid);

    boolean isExistVideoType(Connection cn,Long rid);

    boolean isExistUser(Connection cn, String userToken);

    boolean insertUser(User user);

    boolean insertUser(Connection cn, User user);

    boolean insertVideoType(Style type);

    boolean insertVideoType(Connection cn, Style type);

    /**
     * 插入url,插入成功返回true，若已存在该url则返回false
     * @param cn
     * @param md5Url
     * @return
     */
    boolean insertUrl(Connection cn, String md5Url);
}
