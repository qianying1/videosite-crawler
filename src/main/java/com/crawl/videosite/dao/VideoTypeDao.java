package com.crawl.videosite.dao;

import com.crawl.videosite.domain.Style;

import java.sql.Connection;

/**
 * 视频类型持久层
 */
public interface VideoTypeDao extends Dao {

    boolean isExistVideoType(Long rid);

    boolean isExistVideoType(Connection cn, Long rid);

    /**
     * a站中是否已存在视频类型
     *
     * @param acfunTid
     * @return
     */
    boolean isExistVideoTypeInAcfun(Long acfunTid);

    Long insertVideoType(Style type);



    Long insertVideoType(Connection cn, Style type);

    /**
     * 根据视频类型id查询视频类型
     *
     * @param conn
     * @param rid
     * @return
     */
    Style selectVideoTypeByRid(Connection conn, Long rid);

    /**
     * 更新视频类型数据
     *
     * @param type
     * @return
     */
    Long updateVideoType(Style type);

    /**
     * 通过视屏类型名称判断是否存在视频类型
     *
     * @param typeName
     * @return
     */
    boolean isExistVideoTypeByName(String typeName);

    /**
     * 通过视频类型名称获取视频类型的id
     *
     * @param typeName
     * @return
     */
    Long selectVideoTypeIdByName(String typeName);

    /**
     * 通过视频类型rid获取视频类型id
     *
     * @param rid
     * @return
     */
    Long selectVideoTypeIdByRid(Long rid);

    /**
     * 通过视频类型rid获取视频类型id
     *
     * @param conn
     * @param rid
     * @return
     */
    Long selectVideoTypeIdByRid(Connection conn, Long rid);

    /**
     * 更新视频类型数据
     *
     * @param type
     * @return
     */
    Long updateVideoType(Connection conn, Style type);
}
