package com.crawl.videosite.dao.impl;


import com.crawl.core.dao.ConnectionManager;
import com.crawl.videosite.dao.BiliBiliDao;
import com.crawl.videosite.domain.Fiction;
import com.crawl.videosite.domain.Style;
import com.crawl.videosite.domain.Video;
import com.crawl.videosite.domain.VideoAuthor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * b站dao层
 */
public class BiliBiliDaoImp extends DaoImp implements BiliBiliDao {
    private static Logger logger = LoggerFactory.getLogger(BiliBiliDaoImp.class);

    /**
     * 是否存在视频
     *
     * @param aid
     * @return
     */
    @Override
    public boolean isExistVideo(Long aid) {
        return isExistVideo(ConnectionManager.getConnection(), aid);
    }

    /**
     * 是否存在视频
     *
     * @param aid
     * @return
     */
    @Override
    public synchronized boolean isExistVideo(Connection conn, Long aid) {
        try {
            if (isExistRecord(conn, "video", "bili_aid", aid)) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("通过视频id查询视频数据失败" + aid, e);
        }
        return false;
    }

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

    /**
     * 插入视频数据
     *
     * @param video
     * @return
     */
    @Override
    public Long insertVideo(Video video) {
        return insertVideo(ConnectionManager.getConnection(), video);
    }

    /**
     * 插入视频数据
     *
     * @param conn
     * @param video
     * @return
     */
    @Override
    public synchronized Long insertVideo(Connection conn, Video video) {
        try {
            /*if (isExistVideo(conn, video.getBiliBili_aid())) {
                return -1l;
            }*/
            String column = "bili_aid,biliBili_rid,biliBili_mid,biliBili_videos,biliBili_copyright,state," +
                    "attribute,duration,now_rank,his_rank,rights,description,ctime,pubdate,dynamic,likes,share,coin,favorite," +
                    "replay,href,title,logo,upMan,views,masks,times,bananas,comments,videoAuthor,location,createDate,type,subtitle,badgepay,pts,review";
            String values = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
            String sql = "insert into video (" + column + ") values(" + values + ")";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, video.getBiliBili_aid());
            pstmt.setLong(2, video.getBiliBili_rid());
            pstmt.setLong(3, video.getBiliBili_mid());
            pstmt.setInt(4, video.getBiliBili_videos());
            pstmt.setInt(5, video.getBiliBili_copyright());
            pstmt.setInt(6, video.getState());
            pstmt.setLong(7, video.getAttribute());
            pstmt.setInt(8, video.getDuration());
            pstmt.setInt(9, video.getNow_rank());
            pstmt.setInt(10, video.getHis_rank());
            pstmt.setString(11, video.getRights());
            pstmt.setString(12, video.getDesc());
            pstmt.setDate(13, new Date(video.getCtime() != null ? video.getCtime().getTime() : new java.util.Date().getTime()));
            pstmt.setDate(14, new Date(video.getPubdate() != null ? video.getPubdate().getTime() : new java.util.Date().getTime()));
            pstmt.setString(15, video.getDynamic());
            pstmt.setLong(16, video.getLike());
            pstmt.setLong(17, video.getShare());
            pstmt.setLong(18, video.getCoin());
            pstmt.setLong(19, video.getFavorite());
            pstmt.setLong(20, video.getReplay());
            pstmt.setString(21, video.getHref() != null ? video.getHref() : "");
            pstmt.setString(22, video.getTitle());
            pstmt.setString(23, video.getLogo());
            pstmt.setString(24, video.getUpMan() != null ? video.getUpMan() : "");
            pstmt.setLong(25, video.getViews() != null ? video.getViews() : 0);
            pstmt.setLong(26, video.getMasks() != null ? video.getMasks() : 0);
            pstmt.setString(27, video.getTimes() != null ? video.getTimes() : "");
            pstmt.setLong(28, video.getBananas() != null ? video.getBananas() : 0);
            pstmt.setLong(29, video.getComments() != null ? video.getComments() : 0);
            pstmt.setLong(30, video.getAuthor() != null ? video.getAuthor().getId() : -1);
            pstmt.setString(31, video.getLocation() != null ? video.getLocation() : "");
            pstmt.setDate(32, new Date(video.getCreateDate().getTime()));
            pstmt.setLong(33, (video.getStyle() != null ? video.getStyle().getId() : -1));

            pstmt.setString(34, (video.getSubtitle() != null ? video.getSubtitle() : ""));
            pstmt.setInt(35, (video.getBadgepay() != null ? video.getBadgepay() : -1));
            pstmt.setLong(36, (video.getPts() != null ? video.getPts() : -1));
            pstmt.setLong(37, video.getReview() != null ? video.getReview() : -1);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            Long id = -1l;
            while (rs.next())
                id = rs.getLong(1);
            rs.close();
            pstmt.close();
            logger.info("插入视频数据库成功---" + video.getTitle());
            return id;
        } catch (SQLException e) {
            logger.error("插入视频数据失败: " + video.getTitle(), e);
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
    public synchronized Long insertVideoType(Connection cn, Style type) {
        try {
            /*if (isExistVideoType(cn, type.getBiliBili_rid())) {
                return -1l;
            }*/
            String column = "biliBili_rid,styleName,parentId,createDate";
            String values = "?,?,?,?";
            String sql = "insert into style (" + column + ") values(" + values + ")";
            PreparedStatement pstmt;
            pstmt = cn.prepareStatement(sql);
            pstmt.setLong(1, type.getBiliBili_rid());
            pstmt.setString(2, type.getStyleName());
            pstmt.setLong(3, type.getParent() != null ? type.getParent().getId() : -1);
            pstmt.setDate(4, new Date(type.getCreateDate().getTime()));
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
    public synchronized Long updateAuthor(Connection conn, VideoAuthor author) {
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
     * 更新视频数据
     *
     * @param video
     * @return
     */
    public boolean updateVideo(Video video) {
        return updateVideo(ConnectionManager.getConnection(), video);
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
     * 通过视频id查找视频数据
     *
     * @param conn
     * @param aid
     * @return
     */
    @Override
    public synchronized Video selectVideoByAid(Connection conn, Long aid) {
        String sql = "select * from video where bili_aid= ?";
        List<Video> videos = new ArrayList<>();
        try {
            Video video;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, aid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                video = new Video();
                video.setId(rs.getLong("id"));
                video.setBiliBili_mid(rs.getLong("biliBili_mid"));
                video.setBiliBili_aid(rs.getLong("bili_aid"));
                video.setBiliBili_rid(rs.getLong("biliBili_rid"));
                video.setBiliBili_videos(rs.getInt("biliBili_videos"));
                video.setBiliBili_copyright(rs.getInt("biliBili_copyright"));
                video.setState(rs.getInt("state"));
                video.setAttribute(rs.getLong("attribute"));
                video.setDuration(rs.getInt("duration"));
                video.setNow_rank(rs.getInt("now_rank"));
                video.setHis_rank(rs.getInt("his_rank"));
                video.setRights(rs.getString("rights"));
                video.setDesc(rs.getString("description"));
                video.setCtime(rs.getDate("ctime"));
                video.setPubdate(rs.getDate("pubdate"));
                video.setDynamic(rs.getString("dynamic"));
                video.setLike(rs.getLong("likes"));
                video.setShare(rs.getLong("share"));
                video.setCoin(rs.getLong("coin"));
                video.setFavorite(rs.getLong("favorite"));
                video.setReplay(rs.getLong("replay"));
                video.setHref(rs.getString("href"));
                video.setTitle(rs.getString("title"));
                video.setLogo(rs.getString("logo"));
                video.setUpMan(rs.getString("upMan"));
                video.setViews(rs.getLong("views"));
                video.setMasks(rs.getLong("masks"));
                video.setTimes(rs.getString("times"));
                video.setBananas(rs.getLong("bananas"));
                video.setComments(rs.getLong("comments"));
                video.setBadgepay(rs.getInt("badgepay"));
                video.setPts(rs.getLong("pts"));
                video.setReview(rs.getLong("review"));
                VideoAuthor author = new VideoAuthor();
                author.setId(rs.getLong("videoAuthor"));
                video.setAuthor(author);
                video.setLocation(rs.getString("location"));
                video.setCreateDate(rs.getDate("createDate"));
                Style type = new Style();
                type.setId(rs.getLong("type"));
                video.setStyle(type);
                videos.add(video);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            logger.error("fail to search data from video author table", e);
            return null;
        }
        if (videos.isEmpty())
            return null;
        return videos.get(0);
    }

    /**
     * 更新视频数据
     *
     * @param conn
     * @param video
     * @return
     */
    public synchronized boolean updateVideo(Connection conn, Video video) {
        try {
            Long aid = video.getBiliBili_aid();
            Video origin = selectVideoByAid(ConnectionManager.getConnection(), aid);
            if (origin == null) {
                return false;
            }
            video.setBiliBili_mid(video.getBiliBili_mid() != 0 ? video.getBiliBili_mid() : origin.getBiliBili_mid());
            video.setBiliBili_aid(video.getBiliBili_aid() != 0 ? video.getBiliBili_aid() : origin.getBiliBili_aid());
            video.setBiliBili_rid(video.getBiliBili_rid() != 0 ? video.getBiliBili_rid() : origin.getBiliBili_rid());
            video.setBiliBili_videos(video.getBiliBili_videos() > 0 ? video.getBiliBili_videos() : origin.getBiliBili_videos());
            video.setBiliBili_copyright(video.getBiliBili_copyright() != null ? video.getBiliBili_copyright() : origin.getBiliBili_copyright());
            video.setState(video.getState() != null ? video.getState() : origin.getState());
            video.setAttribute(video.getAttribute() != null ? video.getAttribute() : origin.getAttribute());
            video.setDuration(video.getDuration() != null ? video.getDuration() : origin.getDuration());
            video.setNow_rank(video.getNow_rank() != null ? video.getNow_rank() : origin.getNow_rank());
            video.setHis_rank(video.getHis_rank() != null ? video.getHis_rank() : origin.getHis_rank());
            video.setRights(video.getRights() != null ? video.getRights() : origin.getRights());
            video.setDesc(video.getDesc() != null ? video.getDesc() : origin.getDesc());
            video.setCtime(video.getCtime() != null ? video.getCtime() : origin.getCtime());
            video.setPubdate(video.getPubdate() != null ? video.getPubdate() : origin.getPubdate());
            video.setDynamic(video.getDynamic() != null ? video.getDynamic() : origin.getDynamic());
            video.setLike(video.getLike() > 0 ? video.getLike() : origin.getLike());
            video.setShare(video.getShare() > 0 ? video.getShare() : origin.getShare());
            video.setCoin(video.getCoin() > 0 ? video.getCoin() : origin.getCoin());
            video.setFavorite(video.getFavorite() > 0 ? video.getFavorite() : origin.getFavorite());
            video.setReplay(video.getReplay() > 0 ? video.getReplay() : origin.getReplay());
            video.setHref(video.getHref() != null ? video.getHref() : origin.getHref());
            video.setTitle(video.getTitle() != null ? video.getTitle() : origin.getTitle());
            video.setLogo(video.getLogo() != null ? video.getLogo() : origin.getLogo());
            video.setUpMan(video.getUpMan() != null ? video.getUpMan() : origin.getUpMan());
            video.setViews(video.getViews() > 0 ? video.getViews() : origin.getViews());
            video.setMasks(video.getMasks() > 0 ? video.getMasks() : origin.getMasks());
            video.setTimes(video.getTimes() != null ? video.getTimes() : origin.getTimes());
            video.setBananas(video.getBananas() > 0 ? video.getBananas() : origin.getBananas());
            video.setComments(video.getComments() > 0 ? video.getComments() : origin.getComments());
            video.setAuthor(video.getAuthor() != null ? video.getAuthor() : origin.getAuthor());
            video.setLocation(video.getLocation() != null ? video.getLocation() : origin.getLocation());
            video.setCreateDate(video.getCreateDate() != null ? video.getCreateDate() : origin.getCreateDate());
            video.setStyle(video.getStyle() != null ? video.getStyle() : origin.getStyle());
            video.setBadgepay(video.getBadgepay() > 0 ? video.getBadgepay() : origin.getBadgepay());
            video.setReview(video.getReview() > 0 ? video.getReview() : origin.getReview());
            video.setPts(video.getPts() > 0 ? video.getPts() : origin.getPts());
            String sql = "update video set bili_aid=?,biliBili_rid=?,biliBili_mid=?,biliBili_videos=?,biliBili_copyright=?,state=?,attribute=?" +
                    ",duration=?,now_rank=?,his_rank=?,rights=?,description=?,ctime=?,pubdate=?,dynamic=?,likes=?,share=?,coin=?,favorite=?," +
                    "replay=?,href=?,title=?,logo=?,upMan=?,views=?,masks=?,times=?,bananas=?,comments=?,videoAuthor=?,location=?,createDate=?,type=?,badgepay=?,review=?,pts=? where" +
                    " id=?";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, video.getBiliBili_aid());
            pstmt.setLong(2, video.getBiliBili_rid());
            pstmt.setLong(3, video.getBiliBili_mid());
            pstmt.setInt(4, video.getBiliBili_videos());
            pstmt.setInt(5, video.getBiliBili_copyright());
            pstmt.setInt(6, video.getState());
            pstmt.setLong(7, video.getAttribute());
            pstmt.setInt(8, video.getDuration());
            pstmt.setInt(9, video.getNow_rank());
            pstmt.setInt(10, video.getHis_rank());
            pstmt.setString(11, video.getRights());
            pstmt.setString(12, video.getDesc());
            pstmt.setDate(13, new Date(video.getCtime().getTime()));
            pstmt.setDate(14, new Date(video.getPubdate().getTime()));
            pstmt.setString(15, video.getDynamic());
            pstmt.setLong(16, video.getLike());
            pstmt.setLong(17, video.getShare());
            pstmt.setLong(18, video.getCoin());
            pstmt.setLong(19, video.getFavorite());
            pstmt.setLong(20, video.getReplay());
            pstmt.setString(21, video.getHref());
            pstmt.setString(22, video.getTitle());
            pstmt.setString(23, video.getLogo());
            pstmt.setString(24, video.getUpMan());
            pstmt.setLong(25, video.getViews());
            pstmt.setLong(26, video.getMasks());
            pstmt.setString(27, video.getTimes());
            pstmt.setLong(28, video.getBananas());
            pstmt.setLong(29, video.getComments());
            pstmt.setLong(30, video.getAuthor() != null ? video.getAuthor().getId() : null);
            pstmt.setString(31, video.getLocation());
            pstmt.setDate(32, new Date(video.getCreateDate().getTime()));
            pstmt.setLong(33, video.getStyle() != null ? video.getStyle().getId() : null);
            pstmt.setLong(34, video.getId() != null ? video.getId() : origin.getId());
            pstmt.setInt(35, video.getBadgepay());
            pstmt.setLong(36, video.getReview());
            pstmt.setLong(37, video.getPts());
            int columns = pstmt.executeUpdate();
            if (columns <= 0) {
                return false;
            }
            logger.info("更新视频数据库成功---" + video.getTitle());
            pstmt.close();
            return true;
        } catch (SQLException e) {
            logger.error("更新视频数据失败: " + video.getTitle());
            return false;
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
    public synchronized Long insertAuthor(Connection conn, VideoAuthor author) {
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

    /**
     * 更新视频类型数据
     *
     * @param type
     * @return
     */
    @Override
    public synchronized Long updateVideoType(Connection conn, Style type) {
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
