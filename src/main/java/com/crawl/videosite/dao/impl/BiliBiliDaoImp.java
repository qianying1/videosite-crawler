package com.crawl.videosite.dao.impl;


import com.crawl.core.dao.ConnectionManager;
import com.crawl.videosite.dao.BiliBiliDao;
import com.crawl.videosite.domain.Style;
import com.crawl.videosite.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class BiliBiliDaoImp extends DaoImp implements BiliBiliDao {
    private static Logger logger = LoggerFactory.getLogger(BiliBiliDaoImp.class);

    @Override
    public boolean isExistRecord(String sql) throws SQLException {
        return isExistRecord(ConnectionManager.getConnection(), sql);
    }

    @Override
    public boolean isExistUser(String userToken) {
        return isExistUser(ConnectionManager.getConnection(), userToken);
    }

    @Override
    public boolean isExistVideoType(Long rid) {
        return isExistVideoType(ConnectionManager.getConnection(), rid);
    }

    public boolean isExistVideoType(Connection cn, Long rid) {
        String isContainSql = "select count(*) from style where biliBili_rid=" + rid;
        try {
            if (isExistRecord(isContainSql)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isExistUser(Connection cn, String userToken) {
        String isContainSql = "select count(*) from user WHERE user_token='" + userToken + "'";
        try {
            if (isExistRecord(isContainSql)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertVideoType(Style type) {
        return insertVideoType(ConnectionManager.getConnection(), type);
    }

    @Override
    public boolean insertVideoType(Connection cn, Style type) {
        try {
            if (isExistVideoType(cn, type.getBiliBili_rid())) {
                return false;
            }
            String column = "biliBili_rid,styleName,parent";
            String values = "?,?,?";
            String sql = "insert into style (" + column + ") values(" + values + ")";
            PreparedStatement pstmt;
            pstmt = cn.prepareStatement(sql);
            pstmt.setLong(1, type.getBiliBili_rid());
            pstmt.setString(2, type.getStyleName());
            pstmt.setLong(3, type.getParent()!=null?type.getParent().getId():null);
            pstmt.executeUpdate();
            pstmt.close();
            logger.info("插入数据库成功---" + type.getStyleName());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            ConnectionManager.close();
        }
        return true;
    }

    @Override
    public boolean insertUser(User u) {
        return insertUser(ConnectionManager.getConnection(), u);
    }

    @Override
    public boolean insertUser(Connection cn, User u) {
        try {
            if (isExistUser(cn, u.getUserToken())) {
                return false;
            }
            String column = "location,business,sex,employment,username,url,agrees,thanks,asks," +
                    "answers,posts,followees,followers,hashId,education,user_token";
            String values = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
            String sql = "insert into user (" + column + ") values(" + values + ")";
            PreparedStatement pstmt;
            pstmt = cn.prepareStatement(sql);
            pstmt.setString(1, u.getLocation());
            pstmt.setString(2, u.getBusiness());
            pstmt.setString(3, u.getSex());
            pstmt.setString(4, u.getEmployment());
            pstmt.setString(5, u.getUsername());
            pstmt.setString(6, u.getUrl());
            pstmt.setInt(7, u.getAgrees());
            pstmt.setInt(8, u.getThanks());
            pstmt.setInt(9, u.getAsks());
            pstmt.setInt(10, u.getAnswers());
            pstmt.setInt(11, u.getPosts());
            pstmt.setInt(12, u.getFollowees());
            pstmt.setInt(13, u.getFollowers());
            pstmt.setString(14, u.getHashId());
            pstmt.setString(15, u.getEducation());
            pstmt.setString(16, u.getUserToken());
            pstmt.executeUpdate();
            pstmt.close();
            logger.info("插入数据库成功---" + u.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            ConnectionManager.close();
        }
        return true;
    }

    @Override
    public boolean insertUrl(Connection cn, String md5Url) {
        String isContainSql = "select count(*) from url WHERE md5_url ='" + md5Url + "'";
        try {
            if (isExistRecord(cn, isContainSql)) {
                logger.debug("数据库已经存在该url---" + md5Url);
                return false;
            }
            String sql = "insert into url (md5_url) values( ?)";
            PreparedStatement pstmt;
            pstmt = cn.prepareStatement(sql);
            pstmt.setString(1, md5Url);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug("url插入成功---");
        return true;
    }
}
