package com.crawl.videosite.dao;

import com.crawl.videosite.domain.Fiction;

/**
 * 小说持久层
 */
public interface FictionDao extends Dao{

    /**
     * 是否存在小说
     *
     * @param seasonId
     * @param newestIndex
     * @return
     */
    boolean isExistFiction(Long seasonId,String newestIndex);

    /**
     * 插入小说数据
     *
     * @param fiction
     * @return
     */
    Long insertFiction(Fiction fiction);

    /**
     * 更新小说数据
     *
     * @param fiction
     * @return
     */
    Long updateFiction(Fiction fiction);
}
