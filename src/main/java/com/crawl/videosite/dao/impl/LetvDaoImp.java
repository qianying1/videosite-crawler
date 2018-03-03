package com.crawl.videosite.dao.impl;


import com.crawl.core.dao.ConnectionManager;
import com.crawl.videosite.dao.LetvDao;
import com.crawl.videosite.dao.VideoSiteDao1;
import com.crawl.videosite.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class LetvDaoImp extends DaoImp implements LetvDao {
    private static Logger logger = LoggerFactory.getLogger(LetvDao.class);

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

}
