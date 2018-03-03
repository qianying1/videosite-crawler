package com.crawl.videosite.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * sql执行，dao基层接口
 */
public interface Dao {

    /*boolean isExistRecord(String sql) throws SQLException;

    boolean isExistRecord(Connection cn, String sql) throws SQLException;*/
    /**
     * 是否存在数据记录
     *
     * @param table 表名
     * @param field 字段名
     * @param token 关键字
     * @return
     * @throws SQLException
     */
    boolean isExistRecord(Connection conn,String table,String field,Object token) throws SQLException;
    /**
     * 向表中插入数据
     *
     * @param conn
     * @param table
     * @param keyVal
     * @return
     */
    boolean insertTable(Connection conn, String table, Map<String,Object> keyVal) throws SQLException;
    /**
     * 向表中批量插入数据
     *
     * @param conn
     * @param table
     * @param keys
     * @param vals
     * @return
     */
    boolean insertsTable(Connection conn, String table, List<String> keys, List<Object> vals) throws SQLException;
}
