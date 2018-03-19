package com.crawl.videosite.dao.impl;

import com.crawl.core.dao.ConnectionManager;
import com.crawl.videosite.dao.VideoTypeDao;
import com.crawl.videosite.domain.Style;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 视频类型持久层
 */
public class VideoTypeDaoImp extends DaoImp implements VideoTypeDao {
    private Logger logger = LoggerFactory.getLogger(VideoTypeDaoImp.class);

    /**
     * 是否存在视频类型
     *
     * @param rid
     * @return
     */
    @Override
    public boolean isExistVideoType(Long rid) {
        return isExistVideoType(ConnectionManager.getConnection(), rid);
    }

    /**
     * 是否存在视频类型
     *
     * @param cn
     * @param rid
     * @return
     */
    public synchronized boolean isExistVideoType(Connection cn, Long rid) {
//        String isContainSql = "select count(*) from style where biliBili_rid=" + rid;
        try {
            if (isExistRecord(cn, "style", "biliBili_rid", rid)) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("通过视频类型id查询视频类型数据失败: " + rid);
        }
        return false;
    }

    /**
     * a站中是否已存在视频类型
     *
     * @param acfunTid
     * @return
     */
    public boolean isExistVideoTypeInAcfun(Long acfunTid) {
        Connection conn = ConnectionManager.getConnection();
        try {
            if (isExistRecord(conn, "style", "acfun_tid", acfunTid)) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("通过视频类型id查询视频类型数据失败: " + acfunTid);
        }
        return false;
    }

    /**
     * 通过视屏类型名称判断是否存在视频类型
     *
     * @param typeName
     * @return
     */
    public synchronized boolean isExistVideoTypeByName(String typeName) {
        try {
            if (isExistRecord(ConnectionManager.getConnection(), "style", "styleName", typeName)) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("通过视频类型名称查询视频类型数据失败: " + typeName, e);
        }
        return false;
    }

    /**
     * 插入视频类型
     *
     * @param type
     * @return
     */
    @Override
    public Long insertVideoType(Style type) {
        return insertVideoType(ConnectionManager.getConnection(), type);
    }

    private Style selectVideoTypeByAcfunTid(Connection conn, Long acfunTid) {
        String sql = "select * from style where acfun_tid= ?";
        List<Style> types = new ArrayList<>();
        try {
            Style type;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, acfunTid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                type = new Style();
                type.setId(rs.getLong("id"));
                type.setBiliBili_rid(rs.getLong("biliBili_rid"));
                type.setAcfun_tid(rs.getLong("acfun_tid"));
                type.setStyleName(rs.getString("styleName"));
                type.setCreateDate(rs.getDate("createDate"));
                Long parentId = rs.getLong("parentId");
                if (parentId != null) {
                    Style parentType = new Style();
                    parentType.setAcfun_tid(parentId);
                    type.setParent(parentType);
                }
                types.add(type);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            logger.error("查询视频类型数据失败", e);
        }
        if (types.isEmpty())
            return null;
        return types.get(0);
    }

    /**
     * 更新a站视频类型
     *
     * @param type
     * @return
     */
    public Long updateAcfunVideoType(Style type) {
        Connection conn = ConnectionManager.getConnection();
        Long tid = type.getAcfun_tid();
        Style origin = selectVideoTypeByAcfunTid(conn, tid);
        if (origin == null) {
            return -1l;
        }
        type.setParent(type.getParent() != null ? type.getParent() : origin.getParent());
        type.setAcfun_tid(type.getAcfun_tid() != null ? type.getAcfun_tid() : origin.getAcfun_tid());
        type.setStyleName(type.getStyleName() != null ? type.getStyleName() : origin.getStyleName());
        type.setCreateDate(type.getCreateDate() != null ? type.getCreateDate() : origin.getCreateDate());
        try {
            String sql = "update style set parentId=?,styleName=?,createDate=? where acfun_tid=?";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, type.getParent() != null ? type.getParent().getAcfun_tid() : -1);
            pstmt.setString(2, type.getStyleName());
            pstmt.setDate(3, new Date(type.getCreateDate().getTime()));
            pstmt.setLong(4, type.getAcfun_tid());
            int columns = pstmt.executeUpdate();
            Long id = -1l;
            if (columns > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    id = rs.getLong("id");
                }
                rs.close();
                logger.info("更新视频类型数据库成功---" + type.getStyleName());
            } else {
                logger.error("更新视频类型数据失败" + type.getStyleName());
                return -1l;
            }
            pstmt.close();
            return id;
        } catch (SQLException e) {
            logger.error("更新视频数据失败: " + type.getStyleName(), e);
            return -1l;
        } finally {
//            ConnectionManager.close();
        }

    }

    /**
     * 插入视频类型
     *
     * @param cn
     * @param type
     * @return
     */
    @Override
    public Long insertVideoType(Connection cn, Style type) {
        try {
            /*if (isExistVideoType(cn, type.getBiliBili_rid())) {
                return -1l;
            }*/
            String column = "biliBili_rid,styleName,parentId,createDate,acfun_tid";
            String values = "?,?,?,?,?";
            String sql = "insert into style (" + column + ") values(" + values + ")";
            PreparedStatement pstmt;
            pstmt = cn.prepareStatement(sql);
            pstmt.setLong(1, type.getBiliBili_rid());
            pstmt.setString(2, type.getStyleName());
            pstmt.setLong(3, type.getParent() != null ? type.getParent().getBiliBili_rid() : -1);
            pstmt.setDate(4, new Date(type.getCreateDate().getTime()));
            pstmt.setLong(5, type.getAcfun_tid());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            Long id = -1l;
            while (rs.next())
                id = rs.getLong(1);
            rs.close();
            pstmt.close();
            logger.info("插入视频类型数据成功---" + type.getStyleName());
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1l;
        } finally {
//            ConnectionManager.close();
        }
    }

    /**
     * 插入视频类型
     *
     * @param type
     * @return
     */
    @Override
    public Long insertAcfunVideoType(Style type) {
        return insertAcfunVideoType(ConnectionManager.getConnection(), type);
    }

    /**
     * 插入视频类型
     *
     * @param cn
     * @param type
     * @return
     */
    @Override
    public Long insertAcfunVideoType(Connection cn, Style type) {
        try {
            /*if (isExistVideoType(cn, type.getBiliBili_rid())) {
                return -1l;
            }*/
            String column = "biliBili_rid,styleName,parentId,createDate,acfun_tid";
            String values = "?,?,?,?,?";
            String sql = "insert into style (" + column + ") values(" + values + ")";
            PreparedStatement pstmt;
            pstmt = cn.prepareStatement(sql);
            pstmt.setLong(1, type.getBiliBili_rid());
            pstmt.setString(2, type.getStyleName());
            pstmt.setLong(3, type.getParent() != null ? type.getParent().getAcfun_tid() : -1);
            pstmt.setDate(4, new Date(type.getCreateDate().getTime()));
            pstmt.setLong(5, type.getAcfun_tid());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            Long id = -1l;
            while (rs.next())
                id = rs.getLong(1);
            rs.close();
            pstmt.close();
            logger.info("插入视频类型数据成功---" + type.getStyleName());
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1l;
        } finally {
//            ConnectionManager.close();
        }
    }

    /**
     * 通过视频类型名称获取视频类型的id
     *
     * @param typeName
     * @return
     */
    public Long selectVideoTypeIdByName(String typeName) {
        Connection conn = ConnectionManager.getConnection();
        String sql = "select id from style where styleName=?";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, typeName);
            ResultSet rs = pstmt.executeQuery();
            Long id = -1l;
            while (rs.next()) {
                id = rs.getLong("id");
            }
            rs.close();
            pstmt.close();
            return id;
        } catch (SQLException e) {
            logger.error("通过视频类型名称查询视频类型id出现错误: " + typeName, e);
            return -1l;
        }
    }

    /**
     * 根据视频类型id查询视频类型
     *
     * @param conn
     * @param rid
     * @return
     */
    @Override
    public synchronized Style selectVideoTypeByRid(Connection conn, Long rid) {
        String sql = "select * from style where biliBili_rid= ?";
        List<Style> types = new ArrayList<>();
        try {
            Style type;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, rid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                type = new Style();
                type.setId(rs.getLong("id"));
                type.setBiliBili_rid(rs.getLong("biliBili_rid"));
                type.setStyleName(rs.getString("styleName"));
                type.setCreateDate(rs.getDate("createDate"));
                types.add(type);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            logger.error("查询视频类型数据失败", e);
        }
        if (types.isEmpty())
            return null;
        return types.get(0);
    }

    /**
     * 更新视频类型数据
     *
     * @param type
     * @return
     */
    @Override
    public Long updateVideoType(Style type) {
        return updateVideoType(ConnectionManager.getConnection(), type);
    }

    /**
     * 通过视频类型rid获取视频类型id
     *
     * @param rid
     * @return
     */
    @Override
    public Long selectVideoTypeIdByRid(Long rid) {
        return selectVideoTypeIdByRid(ConnectionManager.getConnection(), rid);
    }

    /**
     * 通过视频类型rid获取视频类型id
     *
     * @param conn
     * @param rid
     * @return
     */
    public synchronized Long selectVideoTypeIdByRid(Connection conn, Long rid) {
        String sql = "select id from style where biliBili_rid=?";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, rid);
            ResultSet rs = pstmt.executeQuery();
            Long id = -1l;
            while (rs.next()) {
                id = rs.getLong("id");
            }
            rs.close();
            pstmt.close();
            return id;
        } catch (SQLException e) {
            logger.error("执行视频类型查询时sql语句错误", e);
            return -1l;
        }
    }

    /**
     * 更新视频类型数据
     *
     * @param type
     * @return
     */
    @Override
    public Long updateVideoType(Connection conn, Style type) {
        Long rid = type.getBiliBili_rid();
        Style origin = selectVideoTypeByRid(ConnectionManager.getConnection(), rid);
        if (origin == null) {
            return -1l;
        }
        type.setParent(type.getParent() != null ? type.getParent() : origin.getParent());
        type.setStyleName(type.getStyleName() != null ? type.getStyleName() : origin.getStyleName());
        type.setCreateDate(type.getCreateDate() != null ? type.getCreateDate() : origin.getCreateDate());
        try {
            String sql = "update style set parentId=?,styleName=?,createDate=? where biliBili_rid=?";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, type.getParent() != null ? type.getParent().getId() : -1);
            pstmt.setString(2, type.getStyleName());
            pstmt.setDate(3, new Date(type.getCreateDate().getTime()));
            pstmt.setLong(4, type.getBiliBili_rid());
            int columns = pstmt.executeUpdate();
            Long id = -1l;
            if (columns > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    id = rs.getLong("id");
                }
                rs.close();
                logger.info("更新视频类型数据库成功---" + type.getStyleName());
            } else {
                logger.error("更新视频类型数据失败" + type.getStyleName());
                return -1l;
            }
            pstmt.close();
            return id;
        } catch (SQLException e) {
            logger.error("更新视频数据失败: " + type.getStyleName(), e);
            return -1l;
        } finally {
//            ConnectionManager.close();
        }
    }
}
