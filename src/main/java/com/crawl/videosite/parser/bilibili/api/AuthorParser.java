package com.crawl.videosite.parser.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.crawl.core.util.Constants;
import com.crawl.videosite.domain.Style;
import com.crawl.videosite.domain.Video;
import com.crawl.videosite.domain.VideoAuthor;
import com.crawl.videosite.parser.bilibili.api.abstra.AbstractAuthorParser;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

public class AuthorParser extends AbstractAuthorParser {
    private Logger logger = LoggerFactory.getLogger(AuthorParser.class);

    /**
     * 分析json数据
     *
     * @param jsonObject
     */
    @Override
    public void parseJson(JSONObject jsonObject, Long mid) {
        if (jsonObject == null || jsonObject.isEmpty())
            return;
        logger.info("开始分析视频作者数据>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(jsonObject);
        Map<String, Object> authorData = (Map<String, Object>) jsonObject.get("data");
        Map<String, Object> card = (Map<String, Object>) authorData.get("card");
        VideoAuthor author = new VideoAuthor();
        author.setBiliBili_mid(mid);
        author.setName(card.get("name")!=null?card.get("name").toString():"");
        author.setSex(card.get("sex")!=null?card.get("sex").toString():"");
        author.setLogo(card.get("face")!=null?card.get("face").toString():"");
        author.setLocation(card.get("place")!=null?card.get("place").toString():"");
        author.setSignature(card.get("description")!=null?card.get("description").toString():"");
        author.setAudienceCount(card.get("fans")!=null?Integer.valueOf(card.get("fans").toString()):0);
        author.setAttentionCount(card.get("attention")!=null?Integer.valueOf(card.get("attention").toString()):0);
        author.setVideoCount(authorData.get("archive_count")!=null?Integer.valueOf(authorData.get("archive_count").toString()):0);
        author.setArticle(authorData.get("article_count")!=null?Long.valueOf(authorData.get("article_count").toString()):0);
        author.setFollower(authorData.get("follower")!=null?Long.valueOf(authorData.get("follower").toString()):0l);
        insertAuthor(author);
    }

    private void insertAuthor(VideoAuthor author) {
        if (Constants.isUpdateVideoAuthor_biliBili) {
            boolean authorExsist = biliBiliDao.isExistAuthor(author.getBiliBili_mid());
            if (authorExsist) {
                Long id = biliBiliDao.updateAuthor(author);
//                Long id = biliBiliDao.selectAuthorIdByMid(author.getBiliBili_mid());
                author.setId(id);
            } else {
                Long id = biliBiliDao.insertAuthor(author);
                if (id != -1) {
                    logger.error("插入视频作者数据成功: " + author.getName());
                }else {
                    logger.error("插入视频作者数据失败: " + author.getName());
                }
            }
        } else {
            Long id = biliBiliDao.insertAuthor(author);
            if (id != -1) {
                author.setId(id);
            } else {
                logger.error("插入视频作者数据失败: " + author.getName());
            }
        }
    }
}
