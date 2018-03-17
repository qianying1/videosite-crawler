package com.crawl.videosite.dao.impl;

import com.crawl.core.dao.ConnectionManager;
import com.crawl.videosite.dao.FictionDao;
import com.crawl.videosite.domain.Fiction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 小说持久层
 */
public class FictionDaoImp extends DaoImp implements FictionDao{
    private Logger logger= LoggerFactory.getLogger(FictionDaoImp.class);
    /**
     * 是否存在小说
     *
     * @param seasonId
     * @param newestIndex
     * @return
     */
    @Override
    public boolean isExistFiction(Long seasonId, String newestIndex) {
        return isExistFiction(ConnectionManager.getConnection(), seasonId, newestIndex);
    }

    /**
     * 插入小说数据
     *
     * @param fiction
     * @return
     */
    public Long insertFiction(Fiction fiction) {
        if (fiction == null) {
            return -1l;
        }
        return insertFiction(ConnectionManager.getConnection(), fiction);
    }

    /**
     * 插入小说数据
     *
     * @param conn
     * @param fiction
     * @return
     */
    private Long insertFiction(Connection conn, Fiction fiction) {
        try {
            /*if (isExistVideo(conn, video.getBiliBili_aid())) {
                return -1l;
            }*/
            String column = "cover,dm_count,favorite,is_finish,is_started,newest_ep_index," +
                    "play_count,pts,season_id,season_status,square_cover,title,total_count";
            String values = "?,?,?,?,?,?,?,?,?,?,?,?,?";
            String sql = "insert into fiction (" + column + ") values(" + values + ")";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fiction.getCover() != null ? fiction.getCover() : "");
            pstmt.setLong(2, fiction.getDm_count() > 0 ? fiction.getDm_count() : 0);
            pstmt.setLong(3, fiction.getFavorite() > 0 ? fiction.getFavorite() : 0);
            pstmt.setInt(4, fiction.getIs_finish());
            pstmt.setInt(5, fiction.getIs_started());
            pstmt.setString(6, fiction.getNewest_ep_index() != null ? fiction.getNewest_ep_index().toString() : "");
            pstmt.setLong(7, fiction.getPlay_count() > 0 ? fiction.getPlay_count() : 0);
            pstmt.setLong(8, fiction.getPts() > 0 ? fiction.getPts() : 0);
            pstmt.setLong(9, fiction.getSeasion_id() > 0 ? fiction.getSeasion_id() : 0l);
            pstmt.setInt(10, fiction.getSeason_status());
            pstmt.setString(11, fiction.getSquare_cover() != null ? fiction.getSquare_cover() : "");
            pstmt.setString(12, fiction.getTitle() != null ? fiction.getTitle() : "");
            pstmt.setLong(13, fiction.getTotal_count() > 0 ? fiction.getTotal_count() : 0l);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            Long id = -1l;
            while (rs.next())
                id = rs.getLong(1);
            rs.close();
            pstmt.close();
            logger.info("插入小说数据成功---" + fiction.getTitle());
            return id;
        } catch (SQLException e) {
            logger.error("插入小说数据失败: " + fiction.getTitle(), e);
            return -1l;
        } finally {
//            ConnectionManager.close();
        }
    }

    /**
     * 更新小说数据
     *
     * @param fiction
     * @return
     */
    public Long updateFiction(Fiction fiction) {
        return updateFiction(ConnectionManager.getConnection(), fiction);
    }

    /**
     * 通过季id和最新集数查询小说数据
     *
     * @param conn
     * @param seasonId
     * @param newestIndex
     * @return
     */
    private Fiction selectFictionBySIdAndNewestIndex(Connection conn, Long seasonId, String newestIndex) {
        String sql = "select * from fiction where season_id=? and newest_ep_index=?";
        PreparedStatement pstmt;
        List<Fiction> fictions = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, seasonId);
            pstmt.setString(2, newestIndex);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Fiction fiction = new Fiction();
                fiction.setTotal_count(rs.getLong("total_count"));
                fiction.setTitle(rs.getString("title"));
                fiction.setSquare_cover(rs.getString("square_cover"));
                fiction.setSeason_status(rs.getShort("season_status"));
                fiction.setPts(rs.getLong("pts"));
                fiction.setPlay_count(rs.getLong("play_count"));
                fiction.setIs_started(rs.getShort("is_started"));
                fiction.setIs_finish(rs.getShort("is_finish"));
                fiction.setFavorite(rs.getLong("favorite"));
                fiction.setDm_count(rs.getLong("dm_count"));
                fiction.setCover(rs.getString("cover"));
                fictions.add(fiction);
            }
            rs.close();
            pstmt.close();
            if (fictions.isEmpty()) {
                return null;
            }
            logger.info("通过season_id 和newestIndex 查询小说数据成功");
            return fictions.get(0);
        } catch (SQLException e) {
            logger.error("通过season_id 和newestIndex 查询小说数据失败: " + seasonId + " " + newestIndex, e);
            return null;
        }
    }

    private Long updateFiction(Connection conn, Fiction fiction) {
        Fiction origin = selectFictionBySIdAndNewestIndex(conn, fiction.getSeasion_id(), fiction.getNewest_ep_index());
        if (fiction == null) {
            return -1l;
        }
        fiction.setTotal_count(fiction.getTotal_count() > 0 ? fiction.getTotal_count() : (origin.getTotal_count() != null ? origin.getTotal_count() : 0));
        fiction.setTitle(StringUtils.isNotBlank(fiction.getTitle()) ? fiction.getTitle() : origin.getTitle());
        fiction.setSquare_cover(StringUtils.isNotBlank(fiction.getSquare_cover()) ? fiction.getSquare_cover() : origin.getSquare_cover());
        fiction.setSeason_status(fiction.getSeason_status() > 0 ? fiction.getSeason_status() : origin.getSeason_status());
        fiction.setPts(fiction.getPts() > 0 ? fiction.getPts() : origin.getPts());
        fiction.setPlay_count(fiction.getPlay_count() > 0 ? fiction.getPlay_count() : origin.getPlay_count());
        fiction.setIs_started(fiction.getIs_started() != 0 ? fiction.getIs_started() : origin.getIs_started());
        fiction.setIs_finish(fiction.getIs_finish() != 0 ? fiction.getIs_finish() : origin.getIs_finish());
        fiction.setFavorite(fiction.getFavorite() > 0 ? fiction.getFavorite() : origin.getFavorite());
        fiction.setDm_count(fiction.getDm_count() > 0 ? fiction.getDm_count() : origin.getDm_count());
        fiction.setCover(StringUtils.isNotBlank(fiction.getCover()) ? fiction.getCover() : origin.getCover());
        String sql = "update fiction set total_count=?,title=?,square_cover=?,season_status=?,pts=?,play_count=?,is_started=?,is_finish=?,favorite=?,dm_count=?,cover=? where season_id=? and newest_ep_index=?";
        PreparedStatement pstmt;
        Long id = -1l;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, fiction.getTotal_count());
            pstmt.setString(2, fiction.getTitle());
            pstmt.setString(3, fiction.getSquare_cover());
            pstmt.setShort(4, fiction.getSeason_status());
            pstmt.setLong(5, fiction.getPts());
            pstmt.setLong(6, fiction.getPlay_count());
            pstmt.setInt(7, fiction.getIs_started());
            pstmt.setInt(8, fiction.getIs_finish());
            pstmt.setLong(9, fiction.getFavorite());
            pstmt.setLong(10, fiction.getDm_count());
            pstmt.setString(11, fiction.getCover());
            pstmt.setLong(12, fiction.getSeasion_id());
            pstmt.setString(13, fiction.getNewest_ep_index());
            int num = pstmt.executeUpdate();
            if (num > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    id = rs.getLong("id");
                }
                rs.close();
                pstmt.close();
                logger.info("更新小说数据成功: " + fiction.getTitle());
            }
            return id;
        } catch (SQLException e) {
            logger.error("插入小说数据失败: " + fiction.getTitle(), e);
            return -1l;
        }
    }

    /**
     * 是否存在小说sql
     *
     * @param conn
     * @param seasonId
     * @param newestIndex
     * @return
     */
    private boolean isExistFiction(Connection conn, Long seasonId, String newestIndex) {
        String sql = "select count(*) from fiction where season_id=? and newest_ep_index=?";
        int num = 0;
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, seasonId);
            pstmt.setString(2, newestIndex);
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
        } catch (SQLException e) {
            logger.error("通过season_id和newest_index查询小说数据错误: " + seasonId + " " + newestIndex, e);
            return false;
        }
    }
}
