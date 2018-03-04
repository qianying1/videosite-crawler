package com.crawl.videosite.parser.bilibili;

import com.alibaba.fastjson.JSONObject;
import com.crawl.videosite.domain.Style;
import com.crawl.videosite.domain.Video;
import com.crawl.videosite.domain.VideoAuthor;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 视频列表Json数据抓取任务
 */
public class VideoRankListJsonParser extends AbstractVideoRankListParser {

    Logger logger = LoggerFactory.getLogger(VideoRankListJsonParser.class);

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
    public void parseJson(JSONObject jsonObject, Long rid) {
        if (jsonObject == null || jsonObject.isEmpty())
            return;
        System.out.println(jsonObject);
        List<Map<String, Object>> archives = (List) jsonObject.get("data");
        for (Map<String, Object> archive : archives) {
            if (archive == null || archive.isEmpty())
                continue;
            parseDataToPersistence(archive);
        }
        System.out.println(jsonObject);
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
        type.setStyleName(archive.get("typename").toString());
        video.setTitle(archive.get("title").toString());
        video.setSubtitle(archive.get("subtitle").toString());
        video.setViews(Long.valueOf(archive.get("play").toString()));
        video.setComments(Long.valueOf(archive.get("video_review").toString()));
        video.setReplay(Long.valueOf(archive.get("review").toString()));
        video.setFavorite(Long.valueOf(archive.get("favorites").toString()));
        video.setBiliBili_mid(Long.valueOf(archive.get("mid").toString()));
        author.setBiliBili_mid(Long.valueOf(archive.get("mid").toString()));
        author.setName(archive.get("author").toString());
        video.setDesc(archive.get("description").toString());
        video.setCtime(DateUtils.parseDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(archive.get("create").toString())));
        video.setLogo(archive.get("pic").toString());
        video.setCoin(Long.valueOf(archive.get("coins").toString()));
        String[] times = archive.get("duration").toString().split(":");
        video.setDuration(Integer.valueOf(times[0]) + (Integer.valueOf(times[1]) - 29 >= 0 ? 1 : 0));
        video.setBadgepay(Boolean.valueOf(archive.get("badgepay").toString()) == false ? 0 : 1);
        video.setPts(Long.valueOf(archive.get("pts").toString()));
        insertType(type, video);
        insertAuthor(author, video);
        insertVideo(video);
    }

    private void insertVideo(Video video) {
        boolean videoExsist = dao.isExistVideo(video.getBiliBili_aid());
        if (!videoExsist) {
            Long id = dao.insertVideo(video);
            if (id != -1) {
                video.setId(id);
            } else {
                logger.error("插入视频数据失败: " + video.getTitle());
            }
        } else {
            dao.updateVideo(video);
        }
    }

    private void insertType(Style type, Video video) {
        boolean typeExsist = dao.isExistVideoType(type.getBiliBili_rid());
        if (!typeExsist) {
            Long id = dao.insertVideoType(type);
            if (id != -1) {
                type.setId(id);
                video.setStyle(type);
            } else {
                logger.error("插入视频类型数据失败: " + type.getStyleName());
            }
        } else {
            Long id = dao.updateVideoType(type);
            type.setId(id);
            video.setStyle(type);
        }
    }

    private void insertAuthor(VideoAuthor author, Video video) {
        boolean authorExsist = dao.isExistAuthor(author.getBiliBili_mid());
        if (!authorExsist) {
            Long id = dao.insertAuthor(author);
            if (id != -1) {
                author.setId(id);
                video.setAuthor(author);
            } else {
                logger.error("插入视频作者数据失败: " + author.getName());
            }
        } else {
            Long id = dao.updateAuthor(author);
            if (id != -1) {
                author.setId(id);
            }
            video.setAuthor(author);
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
