package com.crawl.videosite.entity;

/**
 * b站的api参数表
 */
public class BiliBiliParams {

    /**
     * 弹幕api
     */
    public static final String danmuDomain="https://api.bilibili.com/x/v2/dm/ajax";
    /**
     * 活动视频列表api
     */
    public static final String listDomain="https://api.bilibili.com/x/web-interface/dynamic/region?jsonp=jsonp";
    /**
     * 图片信息api
     */
    public static final String iconDomain="https://api.bilibili.com/x/web-interface/index/icon?jsonp=jsonp";
    /**
     * 等级视频列表api
     */
    public static final String rankDomain="https://api.bilibili.com/x/web-interface/ranking/region?jsonp=jsonp";
    /**
     * 新视频列表api
     */
    public static final String newListDomain="https://api.bilibili.com/x/web-interface/newlist?jsonp=jsonp";
    /**
     * 在线用户统计api
     */
    public static final String onlineDomain="https://api.bilibili.com/x/web-interface/online?jsonp=jsonp";
    /**
     * 国产连载api
     */
    public static final String seasonDomain="https://bangumi.bilibili.com/jsonp/season_rank_list/cn/3.ver?callback=guochuangRankCallback";
    /**
     * bangumi全局api
     */
    public static final String bangumiGlobalDomain="https://bangumi.bilibili.com/jsonp/season_rank_list/global/3.ver?callback=bangumiRankCallback";
    /**
     * 连载小说api
     */
    public static final String timelineDomain="https://bangumi.bilibili.com/jsonp/timeline_v2_cn.ver?type=jsonp";
    /**
     * 日本小说连载
     */
    public static final String ribenDomain="https://bangumi.bilibili.com/jsonp/timeline_v2_global.ver?type=jsonp";
    /**
     * 返回数据形式
     */
    public static final String jsonp="jsonp";
    /**
     * 视频id
     */
    private Long aid;
    /**
     * 星期数
     */
    private Integer day=1;
    /**
     * 大类型
     */
    private Integer original=1;
    /**
     * 视频源类型--与tid对应
     */
    private Long rid=1l;
    /**
     * 类型id
     */
    private Long tid=1l;
    /**
     * up主id
     */
    private Long mid=1l;
    /**
     * 连载季id
     */
    private Long season_id=0l;
    /**
     * 连载bangumi id
     */
    private Long bangumi_id=0l;
    /**
     * 列表页码
     */
    private Integer pn=1;
    /**
     * 列表页面大小最大为50
     */
    private Integer ps=50;

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
