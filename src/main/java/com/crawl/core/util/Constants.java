package com.crawl.core.util;


public class Constants {
    public final static String DIRECT_CRAWL_STRATEGY = "direct";
    public final static String PROXY_CRAWL_STRATEGY = "proxy";
    public final static int TIMEOUT = 10000;
    /**
     * 单个ip请求间隔，单位ms
     */
    public final static long TIME_INTERVAL = 1000;
    //a站首页
    public final static String ACFUN_INDEX_URL = "http://www.acfun.cn/";
    //b站首页
    public final static String BILIBILI_INDEX_URL = "http://www.bilibili.com/index.html";
    //斗鱼首页
    public final static String DOUYU_INDEX_URL = "https://www.douyu.com/";
    //爱奇艺首页
    public final static String IQIYI_INDEX_URL = "http://www.iqiyi.com/";
    //乐视首页
    public final static String LETV_INDEX_URL = "http://www.le.com/";
    //皮皮电影首页
    public final static String PPTV_INDEX_URL = "http://www.pptv.com/";
    //搜狐首页
    public final static String SOHU_INDEX_URL = "http://tv.sohu.com/";
    //土豆首页
    public final static String TUDOU_INDEX_URL = "http://www.tudou.com/";
    //优酷首页
    public final static String YOUKU_INDEX_URL = "http://www.youku.com/";
    //youtube首页
    public final static String YOUTUBE_INDEX_URL = "https://www.youtube.com/";


    public final static String USER_FOLLOWEES_URL = "https://www.videosite.com/api/v4/members/%s/followees?" +
            "include=data[*].educations,employments,answer_count,business,locations,articles_count,follower_count," +
            "gender,following_count,question_count,voteup_count,thanked_count,is_followed,is_following," +
            "badge[?(type=best_answerer)].topics&offset=%d&limit=20";
    /**
     * 浏览器用户代理
     */
    public final static String[] userAgentArray = new String[]{
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:50.0) Gecko/20100101 Firefox/50.0",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36"
    };
}
