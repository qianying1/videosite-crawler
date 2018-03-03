package com.crawl.videosite.dao.impl;


import com.crawl.videosite.dao.Dao;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * sql执行，dao基层实现
 */
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
            setParam(pstmt, val, i);
        }
        return true;
    }

    /**
     * 向表中批量插入数据
     *
     * @param conn
     * @param table
     * @param keys
     * @param vals
     * @return
     */
    @Override
    public boolean insertsTable(Connection conn, String table, List<String> keys, List<Object> vals) throws SQLException {
        if (conn == null || StringUtils.isBlank(table) || keys == null || vals == null || keys.isEmpty() || vals.isEmpty() || (keys.size() > vals.size()) || (keys.size() / vals.size() != 0)) {
            return false;
        }
        StringBuffer sql = new StringBuffer("insert into ").append(table.trim().replace(" ", "")).append(" (");
        StringBuffer keySql = new StringBuffer("");
        StringBuffer valSql = new StringBuffer("");
        int length = 0;
        for (String key : keys) {
            keySql.append(key.trim().replace(" ", "")).append(",");
            length++;
        }
        sql.append(keys.toString().substring(0, keys.lastIndexOf(",")));
        sql.append(")");
        int depth = vals.size() / length;
        for (int j = 0; j < depth; j++) {
            valSql.append("(");
            for (int k = 0; k < length; k++) {
                valSql.append("?").append(",");
            }
            valSql = new StringBuffer(valSql.substring(0, valSql.lastIndexOf(",")));
            valSql.append("),");
        }
        valSql = new StringBuffer(valSql.substring(0, valSql.lastIndexOf(",")));
        sql.append(" values").append(valSql);
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        for (int i = 0; i < vals.size(); i++) {
            setParam(pstmt, vals.get(i), i);
        }
        return true;
    }

    /**
     * 向preparedStatement中设置参数值
     *
     * @param pstmt
     * @param val
     */
    private void setParam(PreparedStatement pstmt, Object val, int index) throws SQLException {
        if (val instanceof Long) {
            pstmt.setLong(index + 1, Long.valueOf(val.toString()));
        } else if (val instanceof String) {
            pstmt.setString(index + 1, String.valueOf(val));
        } else if (val instanceof Integer) {
            pstmt.setInt(index + 1, Integer.valueOf(val.toString()));
        } else if (val instanceof Float) {
            pstmt.setFloat(index + 1, Float.valueOf(val.toString()));
        } else if (val instanceof Double) {
            pstmt.setDouble(index + 1, Double.valueOf(val.toString()));
        } else if (val instanceof Date) {
            pstmt.setDate(index + 1, (java.sql.Date) val);
        } else if (val instanceof List) {
            pstmt.setArray(index + 1, (Array) val);
        } else if (val instanceof BigDecimal) {
            pstmt.setBigDecimal(index + 1, BigDecimal.valueOf(Long.valueOf(val.toString())));
        } else if (val instanceof Boolean) {
            pstmt.setBoolean(index + 1, (Boolean) val);
        } else {
            pstmt.setObject(index + 1, val);
        }
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
