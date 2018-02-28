package com.crawl.videosite.parser;


import com.crawl.core.parser.ListPageParser;
import com.crawl.videosite.CommonHttpClient;
import com.crawl.videosite.entity.Page;
import com.crawl.videosite.entity.User;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class VideoSiteIndexPageParser implements ListPageParser{
    private static VideoSiteIndexPageParser instance;
    public static VideoSiteIndexPageParser getInstance(){
        if (instance == null){
            synchronized (CommonHttpClient.class){
                if (instance == null){
                    instance = new VideoSiteIndexPageParser();
                }
            }
        }
        return instance;
    }
    @Override
    public List<User> parseListPage(Page page) {
        List<User> userList = new ArrayList<>();
        String baseJsonPath = "$.data.length()";
        DocumentContext dc = JsonPath.parse(page.getHtml());
        Integer userCount = dc.read(baseJsonPath);
        for (int i = 0; i < userCount; i++){
            User user = new User();
            String userBaseJsonPath = "$.data[" + i + "]";
            setUserInfoByJsonPth(user, "userToken", dc, userBaseJsonPath + ".url_token");//user_token
            setUserInfoByJsonPth(user, "username", dc, userBaseJsonPath + ".name");//username
            setUserInfoByJsonPth(user, "hashId", dc, userBaseJsonPath + ".id");//hashId
            setUserInfoByJsonPth(user, "followees", dc, userBaseJsonPath + ".following_count");//关注人数
            setUserInfoByJsonPth(user, "location", dc, userBaseJsonPath + ".locations[0].name");//位置
            setUserInfoByJsonPth(user, "business", dc, userBaseJsonPath + ".business.name");//行业
            setUserInfoByJsonPth(user, "employment", dc, userBaseJsonPath + ".employments[0].company.name");//公司
            setUserInfoByJsonPth(user, "position", dc, userBaseJsonPath + ".employments[0].job.name");//职位
            setUserInfoByJsonPth(user, "education", dc, userBaseJsonPath + ".educations[0].school.name");//学校
            setUserInfoByJsonPth(user, "answers", dc, userBaseJsonPath + ".answer_count");//回答数
            setUserInfoByJsonPth(user, "asks", dc, userBaseJsonPath + ".question_count");//提问数
            setUserInfoByJsonPth(user, "posts", dc, userBaseJsonPath + ".articles_count");//文章数
            setUserInfoByJsonPth(user, "followers", dc, userBaseJsonPath + ".follower_count");//粉丝数
            setUserInfoByJsonPth(user, "agrees", dc, userBaseJsonPath + ".voteup_count");//赞同数
            setUserInfoByJsonPth(user, "thanks", dc, userBaseJsonPath + ".thanked_count");//感谢数
            try {
                Integer gender = dc.read(userBaseJsonPath + ".gender");
                if (gender != null && gender == 1){
                    user.setSex("male");
                }
                else if(gender != null && gender == 0){
                    user.setSex("female");
                }
            } catch (PathNotFoundException e){
                //没有该属性
            }
            userList.add(user);
        }
        return userList;
    }
    /**
     * jsonPath获取值，并通过反射直接注入到user中
     * @param user
     * @param fieldName
     * @param dc
     * @param jsonPath
     */
    private void setUserInfoByJsonPth(User user, String fieldName, DocumentContext dc , String jsonPath){
        try {
            Object o = dc.read(jsonPath);
            Field field = user.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(user, o);
        } catch (PathNotFoundException e1) {
            //no results
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
