package com.crawl.videosite.dao.impl;


import com.crawl.core.dao.ConnectionManager;
import com.crawl.videosite.dao.BiliBiliDao;
import com.crawl.videosite.domain.Style;
import com.crawl.videosite.domain.VideoAuthor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BiliBiliDaoImp extends DaoImp implements BiliBiliDao {
    private static Logger logger = LoggerFactory.getLogger(BiliBiliDaoImp.class);

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
    public boolean isExistAuthor(Connection conn, Long mid) {
        try {
            if (isExistRecord(conn, "video_author", "biliBili_mid", mid)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否存在视频类型
     *
     * @param cn
     * @param rid
     * @return
     */
    public boolean isExistVideoType(Connection cn, Long rid) {
//        String isContainSql = "select count(*) from style where biliBili_rid=" + rid;
        try {
            if (isExistRecord(cn, "style", "biliBili_rid", rid)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    public boolean insertVideoType(Style type) {
        return insertVideoType(ConnectionManager.getConnection(), type);
    }

    /**
     * 插入视频类型
     *
     * @param cn
     * @param type
     * @return
     */
    @Override
    public boolean insertVideoType(Connection cn, Style type) {
        try {
            if (isExistVideoType(cn, type.getBiliBili_rid())) {
                return false;
            }
            String column = "biliBili_rid,styleName,parent";
            String values = "?,?,?";
            String sql = "insert into style (" + column + ") values(" + values + ")";
            PreparedStatement pstmt;
            pstmt = cn.prepareStatement(sql);
            pstmt.setLong(1, type.getBiliBili_rid());
            pstmt.setString(2, type.getStyleName());
            pstmt.setLong(3, type.getParent() != null ? type.getParent().getId() : null);
            pstmt.executeUpdate();
            pstmt.close();
            logger.info("插入数据库成功---" + type.getStyleName());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            ConnectionManager.close();
        }
        return true;
    }

    /**
     * 插入视频作者
     *
     * @param author
     * @return
     */
    @Override
    public boolean insertAuthor(VideoAuthor author) {
        return insertAuthor(ConnectionManager.getConnection(), author);
    }

    /**
     * 更新视频作者信息
     *
     * @param author
     * @return
     */
    @Override
    public boolean updateAuthor(VideoAuthor author){

    }

    /**
     * 插入视频作者
     *
     * @param conn
     * @param author
     * @return
     */
    @Override
    public boolean insertAuthor(Connection conn, VideoAuthor author) {
        try {
            String column = "biliBili_mid,name,indexHref,signature,videoCount,attentionCount,audienceCount,logo,createDate";
            String values = "?,?,?,?,?,?,?,?,?";
            String sql = "insert into video_author (" + column + ") values(" + values + ")";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, author.getBiliBili_mid());
            pstmt.setString(2, author.getName());
            pstmt.setString(3, author.getIndexHref());
            pstmt.setString(4, author.getSignature());
            pstmt.setInt(5, author.getVideoCount());
            pstmt.setInt(6, author.getAttentionCount());
            pstmt.setInt(7, author.getAudienceCount());
            pstmt.setString(8, author.getLogo());
            pstmt.setDate(9, (Date) author.getCreateDate());
            int columns = pstmt.executeUpdate();
            if (columns > 0) {
                logger.info("插入数据库成功---" + author.getName());
            } else {
                logger.error("插入数据失败" + author.getName());
                return false;
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            ConnectionManager.close();
        }
        return true;
    }

    /**
     * 根据作者id查询作者
     *
     * @param conn
     * @param mid
     * @return
     */
    @Override
    public VideoAuthor selectAuthorByMid(Connection conn, Long mid) {
        String sql = "select * from video_author where biliBili_mid= ?";
        List<VideoAuthor> authors = new ArrayList<>();
        try {
            VideoAuthor author;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, mid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                author = new VideoAuthor();
                author.setId(Long.valueOf(rs.getString("id")));
                author.setBiliBili_mid(Long.valueOf(rs.getString("biliBili_mid")));
                author.setName(rs.getString("name"));
                author.setIndexHref(rs.getString("indexHref"));
                author.setSignature(rs.getString("signature"));
                author.setVideoCount(Integer.valueOf(rs.getString("videoCount")));
                author.setAttentionCount(Integer.valueOf(rs.getString("attentionCount")));
                author.setAudienceCount(Integer.valueOf(rs.getString("audienceCount")));
                author.setLogo(rs.getString("logo"));
                author.setCreateDate(new java.util.Date(rs.getString("createDate")));
                authors.add(author);
            }
        } catch (SQLException e) {
            logger.error("fail to search data from video author table", e);
        }
        if (authors.isEmpty())
            return null;
        return authors.get(0);
    }

    /*@Override
    public boolean insertUser(User u) {
        return insertUser(ConnectionManager.getConnection(), u);
    }

    @Override
    public boolean insertUser(Connection cn, User u) {
        try {
            if (isExistUser(cn, u.getUserToken())) {
                return false;
            }
            String column = "location,business,sex,employment,username,url,agrees,thanks,asks," +
                    "answers,posts,followees,followers,hashId,education,user_token";
            String values = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
            String sql = "insert into user (" + column + ") values(" + values + ")";
            PreparedStatement pstmt;
            pstmt = cn.prepareStatement(sql);
            pstmt.setString(1, u.getLocation());
            pstmt.setString(2, u.getBusiness());
            pstmt.setString(3, u.getSex());
            pstmt.setString(4, u.getEmployment());
            pstmt.setString(5, u.getUsername());
            pstmt.setString(6, u.getUrl());
            pstmt.setInt(7, u.getAgrees());
            pstmt.setInt(8, u.getThanks());
            pstmt.setInt(9, u.getAsks());
            pstmt.setInt(10, u.getAnswers());
            pstmt.setInt(11, u.getPosts());
            pstmt.setInt(12, u.getFollowees());
            pstmt.setInt(13, u.getFollowers());
            pstmt.setString(14, u.getHashId());
            pstmt.setString(15, u.getEducation());
            pstmt.setString(16, u.getUserToken());
            pstmt.executeUpdate();
            pstmt.close();
            logger.info("插入数据库成功---" + u.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            ConnectionManager.close();
        }
        return true;
    }

    @Override
    public boolean insertUrl(Connection cn, String md5Url) {
        String isContainSql = "select count(*) from url WHERE md5_url ='" + md5Url + "'";
        try {
            if (isExistRecord(cn, isContainSql)) {
                logger.debug("数据库已经存在该url---" + md5Url);
                return false;
            }
            String sql = "insert into url (md5_url) values( ?)";
            PreparedStatement pstmt;
            pstmt = cn.prepareStatement(sql);
            pstmt.setString(1, md5Url);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug("url插入成功---");
        return true;
    }

    @Override
    public boolean isExistUser(Connection cn, String userToken) {
        String isContainSql = "select count(*) from user WHERE user_token='" + userToken + "'";
        try {
            if (isExistRecord(isContainSql)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isExistRecord(String sql) throws SQLException {
        return isExistRecord(ConnectionManager.getConnection(), sql);
    }

    @Override
    public boolean isExistUser(String userToken) {
        return isExistUser(ConnectionManager.getConnection(), userToken);
    }*/
}
