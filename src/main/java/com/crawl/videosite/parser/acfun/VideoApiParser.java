package com.crawl.videosite.parser.acfun;

import com.crawl.videosite.dao.VideoDao;
import com.crawl.videosite.dao.impl.VideoDaoImp;
import com.crawl.videosite.domain.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * a站视频api数据分析器
 */
public class VideoApiParser {
    private Logger logger = LoggerFactory.getLogger(VideoApiParser.class);
    private VideoDao videoDao;

    public VideoApiParser() {
        videoDao = new VideoDaoImp();
    }

    /**
     * 分析视频里的数字
     *
     * @param jsonArray
     * @param contentId
     */
    public void parseVideoCounts(String[] jsonArray, Long contentId, Connection conn) {
        if (jsonArray == null || jsonArray.length <= 0 || contentId == null)
            return;
        logger.info("开始爬取a站视频数据>>>>>>>>>>>>>>>>");
        Long views = Long.valueOf(jsonArray[0]);
        Long comments = Long.valueOf(jsonArray[1]);
        Long count1 = Long.valueOf(jsonArray[2]);
        Long count2 = Long.valueOf(jsonArray[3]);
        Long masks = Long.valueOf(jsonArray[4]);
        Long favorite = Long.valueOf(jsonArray[5]);
        Long banana = Long.valueOf(jsonArray[6]);
        Long count3 = Long.valueOf(jsonArray[7]);
        Video video = new Video();
        video.setAcfun_vid(contentId);
        video.setViews(views);
        video.setComments(comments);
        video.setMasks(masks);
        video.setFavorite(favorite);
        video.setBananas(banana);
        insertVideo(video,conn);
    }

    private void insertVideo(Video video,Connection conn) {
        boolean exsist = videoDao.isExistVideoByAcfunVid(conn,video.getAcfun_vid());
        if (exsist) {
            videoDao.updateVideo(conn,video);
        } else {
            videoDao.insertVideo(conn,video);
        }
    }
}
