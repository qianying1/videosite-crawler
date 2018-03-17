package com.crawl.videosite.parser.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.crawl.core.util.Constants;
import com.crawl.videosite.dao.AuthorDao;
import com.crawl.videosite.dao.FictionDao;
import com.crawl.videosite.dao.VideoDao;
import com.crawl.videosite.dao.VideoTypeDao;
import com.crawl.videosite.dao.impl.AuthorDaoImp;
import com.crawl.videosite.dao.impl.FictionDaoImp;
import com.crawl.videosite.dao.impl.VideoDaoImp;
import com.crawl.videosite.dao.impl.VideoTypeDaoImp;
import com.crawl.videosite.domain.Video;
import com.crawl.videosite.parser.bilibili.api.abstra.AbstractVideoParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * 视频列表Json数据抓取任务
 */
public class VideoJsonParser extends AbstractVideoParser {

    Logger logger = LoggerFactory.getLogger(VideoJsonParser.class);
    private VideoDao videoDao;

    public VideoJsonParser(){
        videoDao=new VideoDaoImp();
    }
    /**
     * 分析json数据
     *
     * @param jsonObject
     */
    @Override
    public void parseJson(JSONObject jsonObject) {
        if (jsonObject == null || jsonObject.isEmpty())
            return;
        logger.info("开始分析视频数据>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(jsonObject);
        Map<String, Object> data = (Map<String, Object>) jsonObject.get("data");
        parseDataToPersistence(data);
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
        video.setCreateDate(new Date());
        video.setBiliBili_aid(Long.valueOf(archive.get("aid").toString())); //视频id
        video.setViews(archive.get("view").toString().equals("--") ? 0l : Long.valueOf(archive.get("view").toString()));
        video.setMasks(Long.valueOf(archive.get("danmaku").toString()));
        video.setReplay(Long.valueOf(archive.get("reply").toString()));
        video.setFavorite(Long.valueOf(archive.get("favorite").toString()));
        video.setCoin(Long.valueOf(archive.get("coin").toString()));
        video.setShare(Long.valueOf(archive.get("share").toString()));
        video.setNow_rank(Integer.valueOf(archive.get("now_rank").toString()));
        video.setHis_rank(Integer.valueOf(archive.get("his_rank").toString()));
        insertVideo(video);
    }

    private void insertVideo(Video video) {
        if (Constants.isUpdateVideo_biliBili) {
            boolean videoExsist = videoDao.isExistVideo(video.getBiliBili_aid());
            if (!videoExsist) {
                Long id = videoDao.insertVideo(video);
                if (id != -1l) {
                    video.setId(id);
                } else {
                    logger.error("插入视频数据失败: " + video.getTitle());
                }
            } else {
                videoDao.updateVideo(video);
            }
        } else {
            Long id = videoDao.insertVideo(video);
            if (id != -1l) {
                video.setId(id);
            } else {
                logger.error("插入视频数据失败: " + video.getTitle());
            }
        }
    }

}
