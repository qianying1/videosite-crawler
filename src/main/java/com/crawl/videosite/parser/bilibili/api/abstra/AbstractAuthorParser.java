package com.crawl.videosite.parser.bilibili.api.abstra;

import com.alibaba.fastjson.JSONObject;

public abstract class AbstractAuthorParser {

    public abstract void parseJson(JSONObject jsonObject, Long mid);
}
