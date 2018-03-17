package com.crawl.videosite.entity;

/**
 * b站的api参数表
 */
public class BiliBiliParams {

    /**
     * 弹幕api
     */
    public static final String danmuDomain = "https://api.bilibili.com/x/v2/dm/ajax";
    /**
     * 活动视频列表api
     */
    public static final String listDomain = "https://api.bilibili.com/x/web-interface/dynamic/region?jsonp=jsonp";
    /**
     * 图片信息api
     */
    public static final String iconDomain = "https://api.bilibili.com/x/web-interface/index/icon?jsonp=jsonp";
    /**
     * 等级视频列表api
     */
    public static final String rankDomain = "https://api.bilibili.com/x/web-interface/ranking/region?jsonp=jsonp";
    /**
     * 新视频列表api
     */
    public static final String newListDomain = "https://api.bilibili.com/x/web-interface/newlist?jsonp=jsonp";
    /**
     * 在线用户统计api
     */
    public static final String onlineDomain = "https://api.bilibili.com/x/web-interface/online?jsonp=jsonp";
    /**
     * 国产连载api（获取到的是每天更新的数据）?callback=guochuangRankCallback
     */
    public static final String seasonDomain = "https://bangumi.bilibili.com/jsonp/season_rank_list/cn/3.ver";
    /**
     * bangumi全局api（获取到的是每天更新的数据）
     */
    public static final String bangumiGlobalDomain = "https://bangumi.bilibili.com/jsonp/season_rank_list/global/3.ver?callback=bangumiRankCallback";
    /**
     * 连载小说api（获取到的是每天更新的数据）
     */
    public static final String timelineDomain = "https://bangumi.bilibili.com/jsonp/timeline_v2_cn.ver?type=jsonp";
    /**
     * 日本小说连载（获取到的是每天更新的数据）
     */
    public static final String ribenDomain = "https://bangumi.bilibili.com/jsonp/timeline_v2_global.ver?type=jsonp";
    /**
     * 评论数据列表api（需要的参数有pn=1,ps=20,oid=1-up主id,type=1-视频类型,sort=0排序类型）返回的json有多种格式
     * 1){code:,data{config{},hots[],top:,upper:},message:,tt1:}
     * 2){code:data{config{},hots[],notice:page{},replies[],top:,upper{}},message:,tt1:}
     */
    public static final String reviewDomain = "https://api.bilibili.com/x/v2/reply?callback=jQuery172039433489087977835_1520301142059&jsonp=jsonp&pn=2&type=1&oid=2367167&sort=0&_=1520301153559";
    /**
     * 正在直播的页面api（在后面加上数字表示直播的id）
     */
    public static final String liveDomain = "https://live.bilibili.com/";
    /**
     * 正在直播列表数据api
     */
    public static final String listLiveDomain = "https://api.live.bilibili.com/bili/recom";
    /**
     * up主的space空间数据api
     * 参数[host_uid-upMan的id,offset_dynamic_id-是否启用动态id,visitor_uid-访问者id（可有可无）]
     */
    public static final String upManSpaceDomain = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history?host_uid=2&offset_dynamic_id=0";
    /**
     * 直播房间用户列表
     * 参数[roomid房间id,page-页码]
     */
    public static final String liveReviewDomain = "https://api.live.bilibili.com/guard/topList?roomid=6&page=3";
    /**
     * 用户欢迎信息api
     * 参数[roomid-房间id]
     */
    public static final String welcomeInfoDomain = "https://api.live.bilibili.com/activity/v1/Common/welcomeInfo?";
    /**
     * 房间广告列表api
     */
    public static final String roomAdListDomain = "https://api.live.bilibili.com/live/roomAdList";
    /**
     * 获取用户信息api
     * 参数[ts-用户凭据(需要一定条件进行获取)]
     */
    public static final String userInfoDomain = "https://api.live.bilibili.com/User/getUserInfo?";
    /**
     * 获取视频url地址api
     * 参数[cid-上传人的id,platform-平台,quantity-数量]
     */
    public static final String playUrlDomain = "https://api.live.bilibili.com/room/v1/Room/playUrl?";
    /**
     * 房间类型等级牌
     * 参数[roomid-房间id]
     */
    public static final String rankTabDomain = "https://api.live.bilibili.com/live/rankTab?";
    /**
     * 房间许愿瓶api
     * 参数[ruid-房间用户id]
     */
    public static final String roomWishBottleDomain = "https://api.live.bilibili.com/live_user/v1/WishBottle/wishList?";
    /**
     * 获取直播房间信息(不同的房间可能返回的数据格式不一样)
     * 参数[room_id-房间id,from(room)-拿数据的地方]
     */
    public static final String roomInfoDomain = "https://api.live.bilibili.com/room/v1/Room/get_info?";
    /**
     * 房间初始化api
     * 参数[id-房主id]
     */
    public static final String roomInitDomain = "https://api.live.bilibili.com/room/v1/Room/room_init?";
    /**
     * 视频作者身份卡api
     * 参数[mid-视频作者id,jsonp(jsonp)-返回的格式,photo(1)-是否显示图片]
     */
    public static final String videoUpManCardDomain = "https://api.bilibili.com/x/web-interface/card?";
    /**
     * 视频标签api
     * 参数[aid-视频id,jsonp-返回数据类型]
     */
    public static final String tagDomain = "https://api.bilibili.com/x/tag/archive/tags?";
    /**
     * 视频信息（包含收藏数量等信息）api
     * 参数[aid-视频id,jsonp-返回的数据类型]
     */
    public static final String videoStatDomain = "https://api.bilibili.com/x/web-interface/archive/stat?";
    /**
     * 观看视频的用户信息api
     * 参数[mid-作者id,loginid-登陆id]
     */
    public static final String videoUserInfoDomain = "https://api.bilibili.com/vipinfo/default?type=jsonp";
    /**
     * 正在观看视频的用户信息
     * 参数[]
     */
    public static final String viewingVideoUserInfosDomain = "https://api.bilibili.com/x/v2/reply/web/emojis?callback=emoji&jsonp=jsonp";
    /**
     * 视频地址api
     */
    public static final String videoDomain = "https://interface.bilibili.com/v2/playurl?cid=33346440&appkey=84956560bc028eb7&otype=json&type=&quality=0&qn=0&sign=559b050fcb642701a9a12448b2fbb96c";
    /**
     * 视频作者的个人空间(在后面加上数字表示视频作者的id)
     */
    public static final String authorSpaceDomain = "https://space.bilibili.com/";

    //--------------------------------------------------------直播间----------------------------------------------------------------------------------//
    public static final String playerDomain = "https://api.live.bilibili.com/api/player";
    public static final String roomDomain = "https://api.live.bilibili.com/room/v1/Room/playUrl";
    public static final String msgDomain = "https://api.live.bilibili.com/msg/send";
    public static final String getSuserDomain = "https://api.live.bilibili.com/msg/getSuser";
    public static final String recommendByRoomDomain = "https://api.live.bilibili.com/room/v1/room/get_recommend_by_room";
    public static final String roomSilentDomain = "https://api.live.bilibili.com/liveact/room_silent";
    public static final String userSilentDomain = "https://api.live.bilibili.com/liveact/user_silent";
    public static final String shieldKewordDomain = "https://api.live.bilibili.com/liveact/shield_keyword";
    public static final String shieldUserDomain = "https://api.live.bilibili.com/liveact/shield_user";
    public static final String setRoomShieldDomain = "https://api.live.bilibili.com/liveact/set_room_shield";
    public static final String setUserSheidDomain = "https://api.live.bilibili.com/liveact/set_user_sheid";
    public static final String dmreportDomain = "https://api.live.bilibili.com/liveact/dmreport";
    public static final String getRoundPlayVideoDomain = "https://api.live.bilibili.com/live/getRoundPlayVideo";
    public static final String getIpAddrDomain = "https://api.live.bilibili.com/ip_service/v1/ip_service/get_ip_addr";
    public static final String playuvDomain = "//live-trace.bilibili.com/event/playuv";
    public static final String h5PlayerDomain = "//data.bilibili.com/v/flashplay/live_h5_player";
    public static final String logWebDomain = "https://data.bilibili.com/log/web";
    //--------------------------------------------------------直播间----------------------------------------------------------------------------------//
    /**
     * 返回数据形式
     */
    public static final String jsonp = "jsonp";
    /**
     * 视频id
     */
    private Long aid;
    /**
     * 星期数
     */
    private Integer day = 1;
    /**
     * 大类型
     */
    private Integer original = 1;
    /**
     * 视频源类型--与tid对应
     */
    private Long rid = 1l;
    /**
     * 类型id
     */
    private Long tid = 1l;
    /**
     * up主id
     */
    private Long mid = 1l;
    /**
     * 连载季id
     */
    private Long season_id = 0l;
    /**
     * 连载bangumi id
     */
    private Long bangumi_id = 0l;
    /**
     * 列表页码
     */
    private Integer pn = 1;
    /**
     * 列表页面大小最大为50
     */
    private Integer ps = 50;

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getOriginal() {
        return original;
    }

    public void setOriginal(Integer original) {
        this.original = original;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

    public Integer getPs() {
        return ps;
    }

    public void setPs(Integer ps) {
        this.ps = ps;
    }
}
