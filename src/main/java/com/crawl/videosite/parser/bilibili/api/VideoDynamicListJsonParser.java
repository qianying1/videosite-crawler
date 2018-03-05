package com.crawl.videosite.parser.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.crawl.core.util.Constants;
import com.crawl.videosite.domain.Style;
import com.crawl.videosite.domain.Video;
import com.crawl.videosite.domain.VideoAuthor;
import com.crawl.videosite.entity.VideoSiteDynamicPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 视频列表Json数据抓取任务
 */
public class VideoDynamicListJsonParser extends AbstractVideoDynamicListParser {

    Logger logger = LoggerFactory.getLogger(VideoDynamicListJsonParser.class);

    /**
     * 视频类型id
     */
    private Long rid = 1l;
    /**
     * 页面大小
     */
    private Integer ps = 50;
    /**
     * 页码
     */
    private Integer pn = 1;

    /**
     * 分析json数据
     *
     * @param jsonObject
     */
    @Override
    public void parseJson(JSONObject jsonObject, Long rid, Long original) {
        if (jsonObject == null || jsonObject.isEmpty())
            return;
        System.out.println(jsonObject);
        Map<String, Object> data = (Map<String, Object>) jsonObject.get("data");
        Map<String, Object> page = (Map<String, Object>) data.get("page");
        List<Map<String, Object>> archives = (List<Map<String, Object>>) data.get("archives");
        VideoSiteDynamicPersistence persistence = new VideoSiteDynamicPersistence();
        persistence.setBiliBili_pn(Integer.valueOf(page.get("num").toString()));
        persistence.setBiliBili_rid(rid);
        persistence.setBiliBili_original(original);
        for (Map<String, Object> archive : archives) {
            if (archive == null || archive.isEmpty())
                continue;
            parseDataToPersistence(archive);
        }
    }

    /**
     * 将json数据分析成为持久化数据
     *
     * @param archive
     */
    private void parseDataToPersistence(Map<String, Object> archive) {
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
        insertType(type, video);
        insertAuthor(author, video);
        insertVideo(video);
    }

    private void insertVideo(Video video) {
        if (Constants.isUpdateVideo_biliBili) {
            boolean videoExsist = dao.isExistVideo(video.getBiliBili_aid());
            if (!videoExsist) {
                Long id = dao.insertVideo(video);
                if (id != -1l) {
                    video.setId(id);
                } else {
                    logger.error("插入视频数据失败: " + video.getTitle());
                }
            } else {
                dao.updateVideo(video);
            }
        } else {
            Long id = dao.insertVideo(video);
            if (id != -1l) {
                video.setId(id);
            } else {
                logger.error("插入视频数据失败: " + video.getTitle());
            }
        }
    }

    private void insertType(Style type, Video video) {
        if (Constants.isUpdateVideoType_biliBili) {
            boolean isExsit = dao.isExistVideoType(type.getBiliBili_rid());
            if (!isExsit) {
//                Long id = dao.insertVideoType(type);
                Long id = dao.selectVideoTypeIdByRid(type.getBiliBili_rid());
                if (id != -1) {
                    type.setId(id);
                    video.setStyle(type);
                } else {
                    logger.error("插入视频类型数据失败================: " + type.getStyleName());
                }
            } else {
                Long id = dao.updateVideoType(type);
                type.setId(id);
                video.setStyle(type);
            }
        } else {
            Long id = dao.insertVideoType(type);
            if (id != -1) {
                type.setId(id);
                video.setStyle(type);
            } else {
                logger.error("插入视频类型数据失败-------------------------: " + type.getStyleName());
            }
        }

    }

    private void insertAuthor(VideoAuthor author, Video video) {
        if (Constants.isUpdateVideoAuthor_biliBili) {
            boolean authorExsist = dao.isExistAuthor(author.getBiliBili_mid());
            if (!authorExsist) {
                Long id = dao.insertAuthor(author);
                if (id != -1l) {
                    author.setId(id);
                    video.setAuthor(author);
                } else {
                    logger.error("插入视频作者数据失败: " + author.getName());
                }
            } else {
//                Long id = dao.updateAuthor(author);
                Long id = dao.selectAuthorIdByMid(author.getBiliBili_mid());
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

    public Integer getPs() {
        return ps;
    }

    public void setPs(Integer ps) {
        this.ps = ps;
    }

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

}
