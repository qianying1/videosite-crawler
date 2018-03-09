package com.crawl.videosite.parser.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.crawl.core.util.Constants;
import com.crawl.videosite.domain.Teleplay;
import com.crawl.videosite.parser.bilibili.api.abstra.AbstractBangumiGuochanParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 视频列表Json数据抓取任务
 */
public class BangumiGuochanParser extends AbstractBangumiGuochanParser {

    Logger logger = LoggerFactory.getLogger(BangumiGuochanParser.class);

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
    public void parseJson(JSONObject jsonObject) {
        if (jsonObject == null || jsonObject.isEmpty())
            return;
        logger.info("开始分析国产视频视频列表数据>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(jsonObject);
        Map<String, Object> result = (Map<String, Object>) jsonObject.get("result");
        List<Map<String, Object>> datas = (List<Map<String, Object>>) result.get("list");
        for (Map<String, Object> data : datas) {
            if (data == null || data.isEmpty())
                continue;
            parseDataToPersistence(data);
        }
    }

    /**
     * 将json数据分析成为持久化数据
     *
     * @param teleplay
     */
    private void parseDataToPersistence(Map<String, Object> teleplay) {
        if (teleplay == null || teleplay.isEmpty())
            return;
        Teleplay tp = new Teleplay();
        tp.setCover(teleplay.get("cover").toString());
        tp.setDm_count(Long.valueOf(teleplay.get("dm_count") != null ? teleplay.get("dm_count").toString() : "0"));
        tp.setFavorite(Long.valueOf(teleplay.get("fav").toString()));
        tp.setIs_finish(Integer.valueOf(teleplay.get("is_finish").toString()));
        tp.setIs_started(Integer.valueOf(teleplay.get("is_started").toString()));
        tp.setNewest_ep_index(teleplay.get("newest_ep_index").toString());
        tp.setPlay_count(Long.valueOf(teleplay.get("play_count").toString()));
        tp.setPts(Long.valueOf(teleplay.get("pts").toString()));
        tp.setSeasion_id(Long.valueOf(teleplay.get("season_id").toString()));
        tp.setSeason_status(Short.valueOf(teleplay.get("season_status").toString()));
        tp.setSquare_cover(teleplay.get("square_cover").toString());
        tp.setTitle(teleplay.get("title").toString());
        tp.setTotal_count(Long.valueOf(teleplay.get("total_count").toString()));
        insertTeleplay(tp);
    }

    private void insertTeleplay(Teleplay teleplay) {
        if (Constants.isUpdateTeleplay_biliBili) {
            boolean teleplayExsist = dao.isExistTeleplay(teleplay.getSeasion_id(), teleplay.getNewest_ep_index());
            if (teleplayExsist)
                dao.updateTeleplay(teleplay);
            else {
                Long id = dao.insertTeleplay(teleplay);
                if (id != -1) {
                    teleplay.setId(id);
                } else {
                    logger.error("插入电视剧数据失败: " + teleplay.getTitle());
                }
            }
        } else {
            Long id = dao.insertTeleplay(teleplay);
            if (id != -1) {
                teleplay.setId(id);
            } else {
                logger.error("插入电视剧数据失败: " + teleplay.getTitle());
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
