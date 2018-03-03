package com.crawl.videosite.dao;


import java.sql.Connection;
import java.sql.SQLException;

public interface LetvDao extends Dao {

    boolean isExistRecord(String sql) throws SQLException;

    boolean isExistRecord(Connection cn, String sql) throws SQLException;

    boolean isExistUser(String userToken);

    boolean isExistUser(Connection cn, String userToken);

}
