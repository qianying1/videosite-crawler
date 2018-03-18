package com.crawl.videosite.parser.acfun;

import com.crawl.videosite.dao.AuthorDao;
import com.crawl.videosite.dao.VideoDao;
import com.crawl.videosite.dao.VideoTypeDao;
import com.crawl.videosite.dao.impl.AuthorDaoImp;
import com.crawl.videosite.dao.impl.VideoDaoImp;
import com.crawl.videosite.dao.impl.VideoTypeDaoImp;
import com.crawl.videosite.domain.Style;
import com.crawl.videosite.domain.Video;
import com.crawl.videosite.domain.VideoAuthor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * a站视频列表数据信息分析器
 */
public class VideoListApiParser {
    private static Logger logger = LoggerFactory.getLogger(VideoListApiParser.class);
    private VideoDao videoDao;
    private AuthorDao authorDao;
    private VideoTypeDao typeDao;

    public VideoListApiParser() {
        videoDao = new VideoDaoImp();
        authorDao = new AuthorDaoImp();
        typeDao = new VideoTypeDaoImp();
    }

    /**
     * 分析json数据映射
     *
     * @param jsonMap
     */
    public void parseJsonMap(Map<String, Object> jsonMap) {
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) jsonMap.get("list");
        if (mapList == null || mapList.isEmpty()) {
            return;
        }
        for (Map<String, Object> vid : mapList) {
            if (vid == null || vid.isEmpty())
                continue;
            Video video = new Video();
            VideoAuthor author = new VideoAuthor();
            Style type = new Style();
            video.setAcfun_vid(vid.get("contentId") != null ? Long.valueOf(vid.get("contentId").toString().replace("ac", "")) : -1l);
            video.setTitle(vid.get("title") != null ? vid.get("title").toString() : "");
            video.setDesc(vid.get("description") != null ? vid.get("description").toString() : "");
            video.setViews(vid.get("views") != null ? Long.valueOf(vid.get("views").toString()) : 0);
            video.setComments(vid.get("comments") != null ? Long.valueOf(vid.get("comments").toString()) : 0);
            video.setAcfun_uid(vid.get("userId") != null ? Long.valueOf(vid.get("userId").toString()) : -1);
            author.setAcfun_uid(vid.get("userId") != null ? Long.valueOf(vid.get("userId").toString()) : -1);
            author.setLogo(vid.get("avatar") != null ? vid.get("avatar").toString() : "");
            video.setLogo(vid.get("titleImg") != null ? vid.get("titleImg").toString() : "");
            author.setName(vid.get("username") != null ? vid.get("username").toString() : "");
            video.setPubdate(vid.get("releaseDate") != null ? new Date(Long.valueOf(vid.get("releaseDate").toString())) : new Date());
            video.setDuration(vid.get("time") != null ? Integer.valueOf(vid.get("time").toString()) : 0);
            author.setFollower(vid.get("followedCount") != null ? Long.valueOf(vid.get("followedCount").toString()) : 0);
            author.setVideoCount(vid.get("contributes") != null ? Integer.valueOf(vid.get("contributes").toString()) : 0);
            author.setSignature(vid.get("signature") != null ? vid.get("signature").toString() : "");
            video.setMasks(vid.get("danmakuSize") != null ? Long.valueOf(vid.get("danmakuSize").toString()) : 0);
            video.setAcfun_tid(vid.get("channelId") != null ? Long.valueOf(vid.get("channelId").toString()) : -1);
            type.setAcfun_tid(vid.get("channelId") != null ? Long.valueOf(vid.get("channelId").toString()) : -1);
            author.setAcfun_tid(vid.get("channelId") != null ? Long.valueOf(vid.get("channelId").toString()) : -1);
            insertType(type, video, author);
            insertAuthor(author, video);
            insertVideo(video);
        }

    }

    private void insertVideo(Video video) {
        boolean videoExsist = videoDao.isExistVideoInAcfun(video.getAcfun_vid());
        if (!videoExsist) {
            Long id = videoDao.insertAcfunVideo(video);
            if (id != -1l) {
                video.setId(id);
            } else {
                logger.error("插入视频数据失败: " + video.getTitle());
            }
        } else {
            videoDao.updateAcfunVideo(video);
        }
    }

    private void insertType(Style type, Video video, VideoAuthor author) {
        boolean isExsit = typeDao.isExistVideoTypeInAcfun(type.getAcfun_tid());
        if (!isExsit) {
            Long id = typeDao.insertVideoType(type);
            if (id != -1) {
                type.setId(id);
                video.setStyle(type);
                author.setAcfun_tid(type.getAcfun_tid());
            } else {
                logger.error("插入视频类型数据失败================: " + type.getStyleName());
            }
        } else {
            Long id = typeDao.updateAcfunVideoType(type);
            type.setId(id);
            video.setStyle(type);
            author.setAcfun_tid(type.getAcfun_tid());
        }
    }

    private void insertAuthor(VideoAuthor author, Video video) {
        boolean authorExsist = authorDao.isExistAuthorInAcfun(author.getAcfun_uid());
        if (!authorExsist) {
            Long id = authorDao.insertAuthor(author);
            if (id != -1l) {
                author.setId(id);
                video.setAuthor(author);
            } else {
                logger.error("插入视频作者数据失败: " + author.getName());
            }
        } else {
            Long id = authorDao.updateAcfunAuthor(author);
            if (id != -1l) {
                author.setId(id);
            }
            video.setAuthor(author);
        }
    }

}
