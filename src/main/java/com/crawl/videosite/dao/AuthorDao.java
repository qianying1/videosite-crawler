package com.crawl.videosite.dao;

import com.crawl.videosite.domain.VideoAuthor;

import java.sql.Connection;

/**
 * 视频作者持久层
 */
public interface AuthorDao extends Dao {

    boolean isExistAuthor(Long mid);

    boolean isExistAuthor(Connection conn, Long mid);

    /**
     * a站中是否已存在该视频作者
     *
     * @param acfunUid
     * @return
     */
    boolean isExistAuthorInAcfun(Long acfunUid);

    Long insertAuthor(VideoAuthor author);

    /**
     * 更新视频作者数据
     *
     * @param author
     * @return
     */
    Long updateAuthor(VideoAuthor author);

    /**
     * 更新视频作者信息
     *
     * @param author
     * @param conn
     * @return
     */
    Long updateAuthor(Connection conn, VideoAuthor author);

    Long insertAuthor(Connection conn, VideoAuthor author);

    VideoAuthor selectAuthorByMid(Connection conn, Long mid);

    /**
     * 通过视频作者mid获取视频作者id
     *
     * @param mid
     * @return
     */
    Long selectAuthorIdByMid(Long mid);

    /**
     * 通过视频作者mid获取视频作者id
     *
     * @param conn
     * @param mid
     * @return
     */
    Long selectAuthorIdByMid(Connection conn,Long mid);
}
