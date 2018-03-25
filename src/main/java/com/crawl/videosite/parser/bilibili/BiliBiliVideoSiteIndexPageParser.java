package com.crawl.videosite.parser.bilibili;


import com.crawl.core.parser.ListPageParser;
import com.crawl.videosite.ProxyHttpClient;
import com.crawl.videosite.entity.Page;
import com.crawl.videosite.domain.User;
import com.crawl.videosite.domain.Video;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.PathNotFoundException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * b站主页分析器
 */
public class BiliBiliVideoSiteIndexPageParser implements ListPageParser {
    private static BiliBiliVideoSiteIndexPageParser instance;

    public static BiliBiliVideoSiteIndexPageParser getInstance() {
        if (instance == null) {
            synchronized (ProxyHttpClient.class) {
                if (instance == null) {
                    instance = new BiliBiliVideoSiteIndexPageParser();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Video> parseListPage(Page page) {
        List<Video> userList = new ArrayList<>();
//        System.out.println(page.getHtml());
        return userList;
    }

    /**
     * jsonPath获取值，并通过反射直接注入到user中
     *
     * @param user
     * @param fieldName
     * @param dc
     * @param jsonPath
     */
    private void setUserInfoByJsonPth(User user, String fieldName, DocumentContext dc, String jsonPath) {
        try {
            Object o = dc.read(jsonPath);
            Field field = user.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(user, o);
        } catch (PathNotFoundException e1) {
            //no results
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
