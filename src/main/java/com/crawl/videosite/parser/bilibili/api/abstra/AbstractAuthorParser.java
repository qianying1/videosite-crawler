package com.crawl.videosite.parser.bilibili.api.abstra;

import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;

public abstract class AbstractAuthorParser {

    public abstract void parseJson(JSONObject jsonObject, Long mid, Connection conn);
}
