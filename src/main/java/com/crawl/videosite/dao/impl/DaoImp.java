package com.crawl.videosite.dao.impl;


import com.crawl.videosite.dao.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

public abstract class DaoImp implements Dao {
    private static Logger logger = LoggerFactory.getLogger(DaoImp.class);

    /*@Override
    public boolean isExistRecord(String sql) throws SQLException {
        return isExistRecord(ConnectionManager.getConnection(), sql);
    }*/

    /**
     * 是否存在数据记录
     *
     * @param table 表名
     * @param field 字段名
     * @param token 关键字
     * @return
     * @throws SQLException
     */
    @Override
    public boolean isExistRecord(Connection conn, String table, String field, Object token) throws SQLException {
        String sql = "select count(*) from " + table + " where " + field + " = ?";
        int num = 0;
        PreparedStatement pstmt;
        pstmt = conn.prepareStatement(sql);
        if (token instanceof Long) {
            pstmt.setLong(1, Long.valueOf(token.toString()));
        } else if (token instanceof String) {
            pstmt.setString(2, String.valueOf(token));
        }
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

    /**
     * 向表中插入数据
     *
     * @param conn
     * @param table
     * @param keyVal
     * @return
     */
    @Override
    public boolean insertTable(Connection conn, String table, Map<String, Object> keyVal) throws SQLException {
        if (keyVal.isEmpty()) {
            return false;
        }
        StringBuffer sql = new StringBuffer("insert into ").append(table.trim().replace(" ", "")).append(" (");
        StringBuffer keys = new StringBuffer("");
        StringBuffer vals = new StringBuffer("");
        int length = 0;
        for (String key : keyVal.keySet()) {
            keys.append(key.trim().replace(" ", "")).append(",");
            vals.append("?").append(",");
            length++;
        }
        sql.append(keys.toString().substring(0, keys.lastIndexOf(",")));
        sql.append(")");
        sql.append(" values(").append(vals.toString().substring(0, vals.lastIndexOf(","))).append(")");
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        for (int i = 0; i < length; i++) {
            Object val = keyVal.get(i);
            if (val instanceof Long) {
                pstmt.setLong(i + 1, Long.valueOf(val.toString()));
            } else if (val instanceof String) {
                pstmt.setString(i + 1, String.valueOf(val));
            } else if (val instanceof Integer) {
                pstmt.setInt(i + 1, Integer.valueOf(val.toString()));
            } else if (val instanceof Float) {
                pstmt.setFloat(i + 1, Float.valueOf(val.toString()));
            } else if (val instanceof Double) {
                pstmt.setDouble(i + 1, Double.valueOf(val.toString()));
            } else if (val instanceof Date) {
                pstmt.setDate(i + 1, (java.sql.Date) val);
            } else {
                pstmt.setObject(i + 1, val);
            }
        }
        return true;
    }

    /*@Override
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
    }*/


}
