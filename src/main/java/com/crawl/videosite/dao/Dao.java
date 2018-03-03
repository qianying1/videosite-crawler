package com.crawl.videosite.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public interface Dao {

    /*boolean isExistRecord(String sql) throws SQLException;

    boolean isExistRecord(Connection cn, String sql) throws SQLException;*/

    boolean isExistRecord(Connection conn,String table,String field,Object token) throws SQLException;

    boolean insertTable(Connection conn, String table, Map<String,Object> keyVal) throws SQLException;

}
