package com.crawl.videosite.entity;

/**
 * a站参数
 */
public class AcfunParams {

    /**
     * 模糊搜索api，参数:
     * [id-视频id(以ac为前缀),pageSize-页面大小,pageNo-页码,type-类型id,cd-类似于类型id,format-格式(system.recomlist),_-时间戳(1521263766293)]
     * 成功status=200,失败status=400
     */
    public static final String searchVideoContentDomain="http://search.acfun.cn/like?";
    /**
     * 视频页面返回例如收藏人数等数据，contentId为视频的id，channelId-类似于类型id，返回数据为列表，格式为[播放数量/阅读量,评论数量,,,弹幕数量,收藏数量,投蕉数量,]
     */
    public static final String videoViewContentDomain="http://www.acfun.cn/content_view.aspx?contentId=";
    /**
     * up主最近时间发布的文章信息uid为用户id
     */
    public static final String articleContentDomain="http://webapi.acfun.cn/query/article/latest?uid=";
    /**
     * 用户信息api,参数userId-用户id
     * 返回contributeCount-投稿数量,gender-性别[1-男人,0女人],followedCount-粉丝数量,followingCount-关注数量
     */
    public static final String upManDetailDomain="http://www.acfun.cn/u/profile.aspx?userId=";
    /**
     * 评论列表,contentId-视频id
     */
    public static final String commentsDomain="http://www.acfun.cn/comment_list_json.aspx?contentId=";


    /**
     * 用户主页；rest的URL风格，数字代表用户的id，后缀为.aspx
     */
    public static final String userDomain="http://www.acfun.cn/u/";
    /**
     * 视频页面；rest的URL风格,后面数字代表视频的id
     */
    public static final String videoDomain="http://www.acfun.cn/v/ac";
    /**
     * 作者空间,rest的URL风格,后面数字代表作者id
     */
    public static final String authorDomain="http://www.acfun.cn/a/ac";
}
