package com.crawl.videosite.dao.impl;


import com.crawl.core.dao.ConnectionManager;
import com.crawl.videosite.dao.Dao;
import com.crawl.videosite.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DaoImp implements Dao {
    private static Logger logger = LoggerFactory.getLogger(DaoImp.class);

    @Override
    public boolean isExistRecord(String sql) throws SQLException {
        return isExistRecord(ConnectionManager.getConnection(), sql);
    }

    @Override
    public boolean isExistRecord(Connection cn, String sql) throws SQLException {
        int num = 0;
        PreparedStatement pstmt;
        pstmt = cn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            num = rs.getInt("count(*)");
        }
        rs.close();
        pstmt.close();
        if (num == 0) {
            return false;
        } else {
            return true;
        }
    }

}
