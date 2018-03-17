package com.crawl.videosite.dao.impl;

import com.crawl.core.dao.ConnectionManager;
import com.crawl.videosite.dao.AuthorDao;
import com.crawl.videosite.domain.VideoAuthor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 视频作者持久层
 */
public class AuthorDaoImp extends DaoImp implements AuthorDao {
    private Logger logger = LoggerFactory.getLogger(AuthorDaoImp.class);

    /**
     * 是否存在该视频作者
     *
     * @param mid
     * @return
     */
    public boolean isExistAuthor(Long mid) {
        return isExistAuthor(ConnectionManager.getConnection(), mid);
    }


    /**
     * 是否存在视频作者
     *
     * @param conn
     * @param mid
     * @return
     */
    public synchronized boolean isExistAuthor(Connection conn, Long mid) {
        try {
            if (isExistRecord(conn, "video_author", "biliBili_mid", mid)) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("通过视频作者id查询视频作者数据失败" + mid, e);
        }
        return false;
    }

    /**
     * 插入视频作者
     *
     * @param author
     * @return
     */
    @Override
    public Long insertAuthor(VideoAuthor author) {
        return insertAuthor(ConnectionManager.getConnection(), author);
    }

    /**
     * 更新视频作者信息
     *
     * @param author
     * @return
     */
    @Override
    public Long updateAuthor(VideoAuthor author) {
        return updateAuthor(ConnectionManager.getConnection(), author);
    }

    /**
     * 更新视频作者信息
     *
     * @param author
     * @param conn
     * @return
     */
    @Override
    public Long updateAuthor(Connection conn, VideoAuthor author) {
        Long mid = author.getBiliBili_mid();
        VideoAuthor origin = selectAuthorByMid(ConnectionManager.getConnection(), mid);
        if (origin == null) {
            return -1l;
        }
        author.setAttentionCount(author.getAttentionCount() != 0 ? author.getAttentionCount() : origin.getAttentionCount());
        author.setAudienceCount(author.getAudienceCount() != 0 ? author.getAudienceCount() : origin.getAudienceCount());
        author.setIndexHref(author.getIndexHref() != null ? author.getIndexHref() : origin.getIndexHref());
        author.setLogo(author.getLogo() != null ? author.getLogo() : origin.getLogo());
        author.setName(author.getName() != null ? author.getName() : origin.getName());
        author.setSignature(author.getSignature() != null ? author.getSignature() : origin.getSignature());
        author.setVideoCount(author.getVideoCount() != 0 ? author.getVideoCount() : origin.getVideoCount());
        author.setCreateDate(author.getCreateDate() != null ? author.getCreateDate() : origin.getCreateDate());
        author.setId(author.getId() != null ? author.getId() : origin.getId());
        author.setSex(author.getSex() != null ? author.getSex() : origin.getSex());
        author.setLocation(author.getLocation() != null ? author.getLocation() : origin.getLocation());
        author.setFollower(author.getFollower() != null ? author.getFollower() : origin.getFollower());
        author.setArticle(author.getArticle() != null ? author.getArticle() : origin.getArticle());
        author.setType_id(author.getType_id() != -1l ? author.getType_id() : origin.getType_id());
        try {
            String sql = "update video_author set name=?,indexHref=?,signature=?,videoCount=?,attentionCount=?,audienceCount=?,logo=?,createDate=?,sex=?,location=?,follower=?,article=?,type_id=? WHERE id=?";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, author.getName());
            pstmt.setString(2, author.getIndexHref());
            pstmt.setString(3, author.getSignature());
            pstmt.setInt(4, author.getVideoCount());
            pstmt.setInt(5, author.getAttentionCount());
            pstmt.setInt(6, author.getAudienceCount());
            pstmt.setString(7, author.getLogo());
            pstmt.setDate(8, new Date(author.getCreateDate().getTime()));
            pstmt.setString(9, author.getSex());
            pstmt.setString(10, author.getLocation());
            pstmt.setLong(11, author.getFollower());
            pstmt.setLong(12, author.getArticle());
            pstmt.setLong(13, author.getType_id());
            pstmt.setLong(14, author.getId());

            int columns = pstmt.executeUpdate();
            Long id = -1l;
            if (columns > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    id = rs.getLong("id");
                }
                logger.info("更新视频作者数据成功---" + author.getName());
                rs.close();
                pstmt.close();
            } else {
                logger.error("更新视频作者数据失败" + author.getName());
                return -1l;
            }
            return id;
        } catch (SQLException e) {
            logger.error("更新视频作者数据失败: " + author.getName(), e);
            return -1l;
        } finally {
//            ConnectionManager.close();
        }
    }


    /**
     * 插入视频作者
     *
     * @param conn
     * @param author
     * @return
     */
    @Override
    public Long insertAuthor(Connection conn, VideoAuthor author) {
        try {
            String column = "biliBili_mid,name,indexHref,signature,videoCount,attentionCount,audienceCount,logo,createDate,sex,location,follower,article,type_id";
            String values = "?,?,?,?,?,?,?,?,?,?,?,?,?,?";
            String sql = "insert into video_author (" + column + ") values(" + values + ")";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, author.getBiliBili_mid());
            pstmt.setString(2, author.getName());
            pstmt.setString(3, author.getIndexHref());
            pstmt.setString(4, author.getSignature());
            pstmt.setInt(5, author.getVideoCount() != null ? author.getVideoCount() : 0);
            pstmt.setInt(6, author.getAttentionCount() != null ? author.getAttentionCount() : 0);
            pstmt.setInt(7, author.getAudienceCount() != null ? author.getAudienceCount() : 0);
            pstmt.setString(8, author.getLogo());
            pstmt.setDate(9, new Date(author.getCreateDate().getTime()));
            pstmt.setString(10, author.getSex() != null ? author.getSex() : "");
            pstmt.setString(11, author.getLocation() != null ? author.getLocation() : "");
            pstmt.setLong(12, author.getFollower() != null ? author.getFollower() : 0l);
            pstmt.setLong(13, author.getArticle() != null ? author.getArticle() : 0l);
            pstmt.setLong(14, author.getType_id() != -1l ? author.getType_id() : -1l);
            int columns = pstmt.executeUpdate();
            Long id = -1l;
            if (columns > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    id = rs.getLong(1);
                }
                logger.info("插入视频作者数据成功---" + author.getName());
                rs.close();
            } else {
                logger.error("插入作者视频数据失败" + author.getName());
                return -1l;
            }
            pstmt.close();
            return id;
        } catch (SQLException e) {
            logger.error("插入视频作者数据失败 " + author.getName(), e);
            return -1l;
        } finally {
//            ConnectionManager.close();
        }
    }

    /**
     * 根据作者id查询作者
     *
     * @param conn
     * @param mid
     * @return
     */
    @Override
    public synchronized VideoAuthor selectAuthorByMid(Connection conn, Long mid) {
        String sql = "select * from video_author where biliBili_mid= ?";
        List<VideoAuthor> authors = new ArrayList<>();
        try {
            VideoAuthor author;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, mid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                author = new VideoAuthor();
                author.setId(rs.getLong("id"));
                author.setBiliBili_mid(rs.getLong("biliBili_mid"));
                author.setName(rs.getString("name"));
                author.setIndexHref(rs.getString("indexHref"));
                author.setSignature(rs.getString("signature"));
                author.setVideoCount(rs.getInt("videoCount"));
                author.setAttentionCount(rs.getInt("attentionCount"));
                author.setAudienceCount(rs.getInt("audienceCount"));
                author.setLogo(rs.getString("logo"));
                author.setCreateDate(rs.getDate("createDate"));
                author.setType_id(rs.getLong("type_id"));
                authors.add(author);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            logger.error("fail to search data from video author table", e);
        }
        if (authors.isEmpty())
            return null;
        return authors.get(0);
    }

    /**
     * 通过视频作者mid获取视频作者id
     *
     * @param mid
     * @return
     */
    public Long selectAuthorIdByMid(Long mid) {
        return selectAuthorIdByMid(ConnectionManager.getConnection(), mid);
    }

    /**
     * 通过视频作者mid获取视频作者id
     *
     * @param conn
     * @param mid
     * @return
     */
    public synchronized Long selectAuthorIdByMid(Connection conn, Long mid) {
        String sql = "select id from video_author where biliBili_mid=?";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, mid);
            ResultSet rs = pstmt.executeQuery();
            Long id = -1l;
            while (rs.next()) {
                id = rs.getLong("id");
            }
            rs.close();
            pstmt.close();
            return id;
        } catch (SQLException e) {
            logger.error("执行查询视频作者sql语句错误", e);
            return -1l;
        }
    }
}
