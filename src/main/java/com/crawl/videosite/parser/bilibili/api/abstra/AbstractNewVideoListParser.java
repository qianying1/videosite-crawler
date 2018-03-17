package com.crawl.videosite.parser.bilibili.api.abstra;

import com.alibaba.fastjson.JSONObject;

/**
 * 视频列表json数据任务
 */
public abstract class AbstractNewVideoListParser {

    /**
     * 获取目标地址
     *
     * @param domain
     * @param rid
     * @param ps
     * @param pn
     * @return
     */
    protected static String getTargetUrl(String domain, Long rid, Integer ps, Integer pn) {
        if (ps == null) {
            ps = 50;
        }
        if (pn == null) {
            pn = 1;
        }
        return domain + "&rid=" + rid + "&pn=" + pn + "&ps=" + ps;
    }

    /**
     * 分析json数据
     *
     * @param jsonObject
     * @param rid
     */
    public abstract void parseJson(JSONObject jsonObject, Long rid, Long original);
}
