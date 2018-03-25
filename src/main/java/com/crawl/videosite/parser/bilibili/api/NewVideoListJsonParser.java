package com.crawl.videosite.parser.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.crawl.core.util.Constants;
import com.crawl.videosite.dao.AuthorDao;
import com.crawl.videosite.dao.VideoDao;
import com.crawl.videosite.dao.VideoTypeDao;
import com.crawl.videosite.dao.impl.AuthorDaoImp;
import com.crawl.videosite.dao.impl.VideoDaoImp;
import com.crawl.videosite.dao.impl.VideoTypeDaoImp;
import com.crawl.videosite.domain.Style;
import com.crawl.videosite.domain.Video;
import com.crawl.videosite.domain.VideoAuthor;
import com.crawl.videosite.parser.bilibili.api.abstra.AbstractNewVideoListParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 视频列表Json数据抓取任务
 */
public class NewVideoListJsonParser extends AbstractNewVideoListParser {

    Logger logger = LoggerFactory.getLogger(NewVideoListJsonParser.class);

    private VideoDao videoDao;
    private AuthorDao authorDao;
    private VideoTypeDao videoTypeDao;

    public NewVideoListJsonParser() {
        videoDao = new VideoDaoImp();
        authorDao = new AuthorDaoImp();
        videoTypeDao = new VideoTypeDaoImp();
    }

    /**
     * 视频类型id
     */
    private Long rid = 1l;

    /**
     * 分析json数据
     *
     * @param jsonObject
     */
    @Override
    public void parseJson(JSONObject jsonObject, Long rid, Long original, Connection conn) {
        if (jsonObject == null || jsonObject.isEmpty())
            return;
        logger.info("开始分析新视频列表数据>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        System.out.println(jsonObject);
        Map<String, Object> data = (Map<String, Object>) jsonObject.get("data");
        if (data.isEmpty())
            return;
        List<Map<String, Object>> archives = (List<Map<String, Object>>) data.get("archives");
        for (Map<String, Object> archive : archives) {
            if (archive == null || archive.isEmpty())
                continue;
            parseDataToPersistence(archive,conn);
        }
    }

    /**
     * 将json数据分析成为持久化数据
     *
     * @param archive
     */
    private void parseDataToPersistence(Map<String, Object> archive, Connection conn) {
        if (archive == null || archive.isEmpty())
            return;
        Video video = new Video();
        Style type = new Style();
        VideoAuthor author = new VideoAuthor();
        video.setCreateDate(new Date());
        type.setCreateDate(new Date());
        author.setCreateDate(new Date());
        video.setBiliBili_aid(Long.valueOf(archive.get("aid").toString())); //视频id
        video.setBiliBili_videos(Integer.valueOf(archive.get("videos").toString()));
        video.setBiliBili_rid(Long.valueOf(archive.get("tid").toString())); //视频类型id
        type.setBiliBili_rid(Long.valueOf(archive.get("tid").toString()));
        author.setType_id(Long.valueOf(archive.get("tid").toString()));
        type.setStyleName(archive.get("tname").toString());     //视频类型名称
        video.setBiliBili_copyright(Integer.valueOf(archive.get("copyright").toString()));
        video.setLogo(archive.get("pic").toString());
        video.setTitle(archive.get("title").toString());
        video.setPubdate(new Date(Long.parseLong(archive.get("pubdate").toString())));
        video.setCtime(new Date(Long.parseLong(archive.get("ctime").toString())));
        video.setDesc(archive.get("desc").toString());
        if (archive.get("state") != null)
            video.setState(Integer.valueOf(archive.get("state").toString()));
        else
            video.setState(0);
        if (archive.get("attribute") != null)
            video.setAttribute(Long.valueOf(archive.get("attribute").toString()));
        else
            video.setAttribute(0l);
        video.setDuration(Integer.valueOf(archive.get("duration").toString()));
        video.setRights(archive.get("rights").toString());
        Map<String, Object> owner = (Map<String, Object>) archive.get("owner");
        video.setBiliBili_mid(Long.valueOf(owner.get("mid").toString()));
        author.setBiliBili_mid(Long.valueOf(owner.get("mid").toString()));
        author.setName(owner.get("name").toString());
        author.setLogo(owner.get("face").toString());
        Map<String, Object> stat = (Map<String, Object>) archive.get("stat");
        video.setViews(Long.valueOf(stat.get("view").toString()));
        video.setMasks(Long.valueOf(stat.get("danmaku").toString()));
        video.setReplay(Long.valueOf(stat.get("reply").toString()));
        video.setFavorite(Long.valueOf(stat.get("favorite").toString()));
        video.setCoin(Long.valueOf(stat.get("coin").toString()));
        video.setShare(Long.valueOf(stat.get("share").toString()));
        video.setNow_rank(Integer.valueOf(stat.get("now_rank").toString()));
        video.setHis_rank(Integer.valueOf(stat.get("his_rank").toString()));
        video.setLike(Long.valueOf(stat.get("like").toString()));
        video.setDynamic(archive.get("dynamic").toString());
        insertType(type, video, author,conn);
        insertAuthor(author, video,conn);
        insertVideo(video,conn);
    }

    private void insertVideo(Video video, Connection conn) {
        if (Constants.isUpdateVideo_biliBili) {
            boolean videoExsist = videoDao.isExistVideo(conn,video.getBiliBili_aid());
            if (!videoExsist) {
                Long id = videoDao.insertVideo(conn,video);
                if (id != -1l) {
                    video.setId(id);
                } else {
                    logger.error("插入视频数据失败: " + video.getTitle());
                }
            } else {
                videoDao.updateVideo(conn,video);
            }
        } else {
            Long id = videoDao.insertVideo(conn,video);
            if (id != -1l) {
                video.setId(id);
            } else {
                logger.error("插入视频数据失败: " + video.getTitle());
            }
        }
    }

    private void insertType(Style type, Video video, VideoAuthor author, Connection conn) {
        if (Constants.isUpdateVideoType_biliBili) {
            boolean isExsit = videoTypeDao.isExistVideoType(conn,type.getBiliBili_rid());
            if (!isExsit) {
                Long id = videoTypeDao.insertVideoType(conn,type);
                if (id != -1) {
                    type.setId(id);
                    video.setStyle(type);
                    author.setType_id(id);
                } else {
                    logger.error("插入视频类型数据失败================: " + type.getStyleName());
                }
            } else {
//                Long id = dao.updateVideoType(type);
                Long id = videoTypeDao.selectVideoTypeIdByRid(conn,type.getBiliBili_rid());
                type.setId(id);
                video.setStyle(type);
                author.setType_id(id);
            }
        } else {
            Long id = videoTypeDao.insertVideoType(conn,type);
            if (id != -1) {
                type.setId(id);
                video.setStyle(type);
                author.setType_id(id);
            } else {
                logger.error("插入视频类型数据失败-------------------------: " + type.getStyleName());
            }
        }

    }

    private void insertAuthor(VideoAuthor author, Video video, Connection conn) {
        if (Constants.isUpdateVideoAuthor_biliBili) {
            boolean authorExsist = authorDao.isExistAuthor(conn,author.getBiliBili_mid());
            if (!authorExsist) {
                Long id = authorDao.insertAuthor(conn,author);
                if (id != -1l) {
                    author.setId(id);
                    video.setAuthor(author);
                } else {
                    logger.error("插入视频作者数据失败: " + author.getName());
                }
            } else {
//                Long id = dao.updateAuthor(author);
                Long id = authorDao.selectAuthorIdByMid(conn,author.getBiliBili_mid());
                if (id != -1l) {
                    author.setId(id);
                }
                video.setAuthor(author);
            }
        }

    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

}
