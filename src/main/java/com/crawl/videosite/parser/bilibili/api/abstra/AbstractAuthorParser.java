package com.crawl.videosite.parser.bilibili.api.abstra;

import com.alibaba.fastjson.JSONObject;
import com.crawl.videosite.dao.BiliBiliDao;
import com.crawl.videosite.dao.impl.BiliBiliDaoImp;

public abstract class AbstractAuthorParser {
    protected BiliBiliDao biliBiliDao;

    protected AbstractAuthorParser() {
        biliBiliDao = new BiliBiliDaoImp();
    }

    public abstract void parseJson(JSONObject jsonObject, Long mid);
}
