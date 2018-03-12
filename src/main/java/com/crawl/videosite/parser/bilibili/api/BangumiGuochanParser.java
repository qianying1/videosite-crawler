package com.crawl.videosite.parser.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.crawl.core.util.Constants;
import com.crawl.videosite.domain.Fiction;
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
     * 分析json数据
     *
     * @param jsonObject
     */
    @Override
    public void parseJson(JSONObject jsonObject) {
        if (jsonObject == null || jsonObject.isEmpty())
            return;
        logger.info("开始分析小说视频列表数据>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
     * @param fiction
     */
    private void parseDataToPersistence(Map<String, Object> fiction) {
        if (fiction == null || fiction.isEmpty())
            return;
        Fiction tp = new Fiction();
        tp.setCover(fiction.get("cover").toString());
        tp.setDm_count(Long.valueOf(fiction.get("dm_count") != null ? fiction.get("dm_count").toString() : "0"));
        tp.setFavorite(Long.valueOf(fiction.get("fav").toString()));
        tp.setIs_finish(Integer.valueOf(fiction.get("is_finish").toString()));
        tp.setIs_started(Integer.valueOf(fiction.get("is_started").toString()));
        tp.setNewest_ep_index(fiction.get("newest_ep_index").toString());
        tp.setPlay_count(Long.valueOf(fiction.get("play_count").toString()));
        tp.setPts(Long.valueOf(fiction.get("pts").toString()));
        tp.setSeasion_id(Long.valueOf(fiction.get("season_id").toString()));
        tp.setSeason_status(Short.valueOf(fiction.get("season_status").toString()));
        tp.setSquare_cover(fiction.get("square_cover").toString());
        tp.setTitle(fiction.get("title").toString());
        tp.setTotal_count(Long.valueOf(fiction.get("total_count").toString()));
        insertFiction(tp);
    }

    private void insertFiction(Fiction fiction) {
        if (Constants.isUpdateFiction_biliBili) {
            boolean fictionExsist = dao.isExistFiction(fiction.getSeasion_id(), fiction.getNewest_ep_index());
            if (fictionExsist)
                dao.updateFiction(fiction);
            else {
                Long id = dao.insertFiction(fiction);
                if (id != -1) {
                    fiction.setId(id);
                } else {
                    logger.error("插入小说数据失败: " + fiction.getTitle());
                }
            }
        } else {
            Long id = dao.insertFiction(fiction);
            if (id != -1) {
                fiction.setId(id);
            } else {
                logger.error("插入小说数据失败: " + fiction.getTitle());
            }
        }
    }

}
