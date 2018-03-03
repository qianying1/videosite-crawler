package com.crawl.videosite.entity;

import java.util.List;
import java.util.Map;

/**
 * b站视频数据分页
 * <p>
 * Created by qianhaibin on 2018/3/3.
 */
public class BiliBliData {

    /**
     * 页面信息
     */
    private Map<String, Object> page;

    /**
     * 视频数据
     */
    private List<Map<String, Object>> archives;

    public Map<String, Object> getPage() {
        return page;
    }

    public void setPage(Map<String, Object> page) {
        this.page = page;
    }

    public List<Map<String, Object>> getArchives() {
        return archives;
    }

    public void setArchives(List<Map<String, Object>> archives) {
        this.archives = archives;
    }
}
