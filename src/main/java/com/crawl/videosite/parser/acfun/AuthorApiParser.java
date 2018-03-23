package com.crawl.videosite.parser.acfun;

import com.crawl.videosite.dao.AuthorDao;
import com.crawl.videosite.dao.impl.AuthorDaoImp;
import com.crawl.videosite.domain.VideoAuthor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Map;

/**
 * a站视频作者分析器
 */
public class AuthorApiParser {
    private static final Logger logger = LoggerFactory.getLogger(AuthorApiParser.class);
    private AuthorDao authorDao;

    public AuthorApiParser() {
        authorDao = new AuthorDaoImp();
    }

    /**
     * 分析视频作者数据
     *
     * @param jsonMap
     * @param conn
     */
    public void parseAuthorJson(Map<String, Object> jsonMap, Connection conn) {
        if (jsonMap.isEmpty() || conn == null)
            return;
        logger.info("开始分析a站视频作者信息>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        VideoAuthor author = new VideoAuthor();
        author.setLogo(jsonMap.get("userImg") != null ? jsonMap.get("userImg").toString() : null);
        author.setVideoCount(jsonMap.get("contributeCount") != null ? Integer.valueOf(jsonMap.get("contributeCount").toString()) : 0);
        author.setSex(jsonMap.get("gender") != null ? (Integer.valueOf(jsonMap.get("gender").toString()) == 1 ? "男" : "女") : "保密");
        author.setSignature(jsonMap.get("signature") != null ? jsonMap.get("signature").toString() : null);
        author.setAudienceCount(jsonMap.get("followedCount") != null ? Integer.valueOf(jsonMap.get("followedCount").toString()) : 0);
        author.setAttentionCount(jsonMap.get("followingCount") != null ? Integer.valueOf(jsonMap.get("followingCount").toString()) : 0);
        author.setAcfun_uid(jsonMap.get("userId") != null ? Long.valueOf(jsonMap.get("userId").toString()) : -1l);
        author.setName(jsonMap.get("username") != null ? jsonMap.get("username").toString() : null);
        insertAuthor(author, conn);
    }

    /**
     * 持久化视频作者数据
     *
     * @param author
     * @param conn
     */
    private void insertAuthor(VideoAuthor author, Connection conn) {
        boolean authorExsist = authorDao.isExistAuthorInAcfun(conn, author.getAcfun_uid());
        if (!authorExsist) {
            Long id = authorDao.insertAuthor(conn, author);
            if (id != -1l) {
                author.setId(id);
            } else {
                logger.error("插入视频作者数据失败: " + author.getName());
            }
        } else {
            Long id = authorDao.updateAcfunAuthor(conn, author);
            if (id != -1l) {
                author.setId(id);
            }
        }
    }
}
