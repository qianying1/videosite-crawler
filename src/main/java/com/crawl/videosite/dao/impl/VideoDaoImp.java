package com.crawl.videosite.dao.impl;

import com.crawl.core.dao.ConnectionManager;
import com.crawl.videosite.dao.VideoDao;
import com.crawl.videosite.domain.Style;
import com.crawl.videosite.domain.Video;
import com.crawl.videosite.domain.VideoAuthor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 视频持久层
 */
public class VideoDaoImp extends DaoImp implements VideoDao {
    private static Logger logger = LoggerFactory.getLogger(VideoDaoImp.class);

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
     * a站中是否存在视频
     *
     * @param acfunVid
     * @return
     */
    public boolean isExistVideoInAcfun(Connection conn, Long acfunVid) {
        try {
            if (isExistRecord(conn, "video", "acfun_vid", acfunVid)) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("通过视频id查询视频数据失败" + acfunVid, e);
        }
        return false;
    }

    /**
     * a站中是否存在视频
     *
     * @param acfunVid
     * @return
     */
    public boolean isExistVideoInAcfun(Long acfunVid) {
        Connection conn = ConnectionManager.getConnection();
        return isExistVideoInAcfun(conn, acfunVid);
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
     * 是否存在a站的视频数据
     *
     * @param acfunVid
     * @param conn
     * @return
     */
    public boolean isExistVideoByAcfunVid(Connection conn, Long acfunVid) {
        try {
            if (isExistRecord(conn, "video", "acfun_vid", acfunVid)) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("通过视频id查询视频数据失败" + acfunVid, e);
        }
        return false;
    }

    /**
     * 是否存在a站的视频数据
     *
     * @param acfunVid
     * @return
     */
    public boolean isExistVideoByAcfunVid(Long acfunVid) {
        Connection conn = ConnectionManager.getConnection();
        return isExistVideoByAcfunVid(conn, acfunVid);
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
     * 更新a站视频信息
     *
     * @param video
     * @return
     */
    public boolean updateAcfunVideo(Connection conn,Video video){
        try {
            Long vid = video.getAcfun_vid();
            Video origin = selectVideoByAcfunVid(conn, vid);
            if (origin == null) {
                return false;
            }
            video.setBiliBili_mid(video.getBiliBili_mid() != 0 ? video.getBiliBili_mid() : origin.getBiliBili_mid());
            video.setBiliBili_aid(video.getBiliBili_aid() != 0 ? video.getBiliBili_aid() : origin.getBiliBili_aid());
            video.setAcfun_vid(video.getAcfun_vid() != null ? video.getAcfun_vid() : origin.getAcfun_vid());
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
            video.setAcfun_tid(video.getAcfun_tid() != -1l ? video.getAcfun_tid() : origin.getAcfun_tid());
            video.setAcfun_uid(video.getAcfun_uid() != -1l ? video.getAcfun_uid() : origin.getAcfun_uid());
            video.setAcfun_vid(video.getAcfun_vid() != -1l ? video.getAcfun_vid() : origin.getAcfun_vid());
            String sql = "update video set bili_aid=?,biliBili_rid=?,biliBili_mid=?,biliBili_videos=?,biliBili_copyright=?,state=?,attribute=?" +
                    ",duration=?,now_rank=?,his_rank=?,rights=?,description=?,ctime=?,pubdate=?,dynamic=?,likes=?,share=?,coin=?,favorite=?," +
                    "replay=?,href=?,title=?,logo=?,upMan=?,views=?,masks=?,times=?,bananas=?,comments=?,videoAuthor=?,location=?,createDate=?,type=?,badgepay=?,review=?,pts=?,acfun_uid=?,acfun_vid=?,acfun_tid=? where" +
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
            pstmt.setInt(34, video.getBadgepay());
            pstmt.setLong(35, video.getReview());
            pstmt.setLong(36, video.getPts());
            pstmt.setLong(37, video.getAcfun_uid());
            pstmt.setLong(38, video.getAcfun_vid());
            pstmt.setLong(39, video.getAcfun_tid());
            pstmt.setLong(40, video.getId() != null ? video.getId() : origin.getId());
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
     * 更新a站视频信息
     *
     * @param video
     * @return
     */
    public boolean updateAcfunVideo(Video video) {
        Connection conn = ConnectionManager.getConnection();
        return updateAcfunVideo(conn,video);
    }

    /**
     * 插入视频数据
     *
     * @param conn
     * @param video
     * @return
     */
    @Override
    public Long insertVideo(Connection conn, Video video) {
        try {
            /*if (isExistVideo(conn, video.getBiliBili_aid())) {
                return -1l;
            }*/
            String column = "bili_aid,biliBili_rid,biliBili_mid,biliBili_videos,biliBili_copyright,state," +
                    "attribute,duration,now_rank,his_rank,rights,description,ctime,pubdate,dynamic,likes,share,coin,favorite," +
                    "replay,href,title,logo,upMan,views,masks,times,bananas,comments,videoAuthor,location,createDate,type,subtitle,badgepay,pts,review,acfun_vid,acfun_uid,acfun_tid";
            String values = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
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
            pstmt.setLong(38, video.getAcfun_vid() != null ? video.getAcfun_vid() : -1);
            pstmt.setLong(39, video.getAcfun_uid());
            pstmt.setLong(40, video.getAcfun_tid());
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
     * 更新视频数据
     *
     * @param video
     * @return
     */
    public boolean updateVideo(Video video) {
        return updateVideo(ConnectionManager.getConnection(), video);
    }

    /**
     * 通过a站视频id查找视频
     *
     * @param conn
     * @param vid
     * @return
     */
    @Override
    public synchronized Video selectVideoByAcfunVid(Connection conn, Long vid) {
        String sql = "select * from video where acfun_vid= ?";
        List<Video> videos = new ArrayList<>();
        try {
            Video video;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, vid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                video = new Video();
                video.setId(rs.getLong("id"));
                video.setBiliBili_mid(rs.getLong("biliBili_mid"));
                video.setBiliBili_aid(rs.getLong("bili_aid"));
                video.setAcfun_vid(rs.getLong("acfun_vid"));
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
                video.setAcfun_vid(rs.getLong("acfun_vid"));
                video.setAcfun_uid(rs.getLong("acfun_uid"));
                video.setAcfun_tid(rs.getLong("acfun_tid"));
                Style type = new Style();
                type.setId(rs.getLong("type"));
                type.setAcfun_tid(rs.getLong("acfun_tid"));
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
                video.setAcfun_vid(rs.getLong("acfun_vid"));
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
    public boolean updateVideo(Connection conn, Video video) {
        try {
            Long aid = video.getBiliBili_aid();
            Video origin = selectVideoByAid(ConnectionManager.getConnection(), aid);
            if (origin == null) {
                return false;
            }
            video.setBiliBili_mid(video.getBiliBili_mid() != 0 ? video.getBiliBili_mid() : origin.getBiliBili_mid());
            video.setBiliBili_aid(video.getBiliBili_aid() != 0 ? video.getBiliBili_aid() : origin.getBiliBili_aid());
            video.setAcfun_vid(video.getAcfun_vid() != null ? video.getAcfun_vid() : origin.getAcfun_vid());
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
            pstmt.setInt(34, video.getBadgepay());
            pstmt.setLong(35, video.getReview());
            pstmt.setLong(36, video.getPts());
            pstmt.setLong(37, video.getId() != null ? video.getId() : origin.getId());
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
}
