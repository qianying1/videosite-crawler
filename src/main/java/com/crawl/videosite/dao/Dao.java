package com.crawl.videosite.dao;


import java.sql.Connection;
import java.sql.SQLException;

public interface Dao {

    boolean isExistRecord(String sql) throws SQLException;

    boolean isExistRecord(Connection cn, String sql) throws SQLException;

}
