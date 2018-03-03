package com.crawl.videosite.dao.impl;


import com.crawl.core.dao.ConnectionManager;
import com.crawl.videosite.dao.IqiyiDao;
import com.crawl.videosite.dao.VideoSiteDao1;
import com.crawl.videosite.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class IqiyiDaoImp extends DaoImp implements IqiyiDao {
    private static Logger logger = LoggerFactory.getLogger(IqiyiDao.class);

    @Override
    public boolean isExistRecord(String sql) throws SQLException{
        return isExistRecord(ConnectionManager.getConnection(), sql);
    }

    @Override
    public boolean isExistUser(String userToken) {
        return isExistUser(ConnectionManager.getConnection(), userToken);
    }

    @Override
    public boolean isExistUser(Connection cn, String userToken) {
        String isContainSql = "select count(*) from user WHERE user_token='" + userToken + "'";
        try {
            if(isExistRecord(isContainSql)){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertUrl(Connection cn, String md5Url) {
        String isContainSql = "select count(*) from url WHERE md5_url ='" + md5Url + "'";
        try {
            if(isExistRecord(cn, isContainSql)){
                logger.debug("数据库已经存在该url---" + md5Url);
                return false;
            }
            String sql = "insert into url (md5_url) values( ?)";
            PreparedStatement pstmt;
            pstmt = cn.prepareStatement(sql);
            pstmt.setString(1,md5Url);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug("url插入成功---");
        return true;
    }
}
