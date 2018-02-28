package com.crawl.core.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 加载配置文件
 */
public class Config {
    /**
     * 是否持久化到数据库
     */
    public static boolean dbEnable;

    /**
     * 是否启用代理
     */
    public static boolean isProxy;
    /**
     * 是否使用代理抓取
     */
    public static boolean bilibiliIsProxy;
    /**
     * 是否使用代理抓取
     */
    public static boolean youkuIsProxy;
    /**
     * 是否使用代理抓取
     */
    public static boolean tudouIsProxy;
    /**
     * 是否使用代理抓取
     */
    public static boolean acfunIsProxy;
    /**
     * 是否使用代理抓取
     */
    public static boolean iqiyiIsProxy;
    /**
     * 是否使用代理抓取
     */
    public static boolean sohuIsProxy;
    /**
     * 是否使用代理抓取
     */
    public static boolean pptvIsProxy;
    /**
     * 是否使用代理抓取
     */
    public static boolean letvIsProxy;
    /**
     * 是否使用代理抓取
     */
    public static boolean douyuIsProxy;
    /**
     * 是否使用代理抓取
     */
    public static boolean youtubeIsProxy;
    /**
     * 下载网页线程数
     */
    public static int downloadThreadSize;
    /**
     * 需要开启的爬取网站线程数
     */
    public static int websitesThreadSize;
    /**
     * 验证码路径
     */
    public static String verificationCodePath;
    /**
     * 知乎注册手机号码或有限
     */
    public static String emailOrPhoneNum;
    /**
     * 知乎密码
     */
    public static String password;
    /**
     * b站爬虫入口
     */
    public static String biliBiliStartURL;
    /**
     * a站爬虫入口
     */
    public static String acfunStartURL;
    /**
     * 斗鱼爬虫入口
     */
    public static String douyuStartURL;
    /**
     * 爱奇艺爬虫入口
     */
    public static String iqiyiStartURL;
    /**
     * 乐视爬虫入口
     */
    public static String letvStartURL;
    /**
     * 皮皮tv爬虫入口
     */
    public static String pptvStartURL;
    /**
     * 搜狐爬虫入口
     */
    public static String sohuStartURL;
    /**
     * 土豆爬虫入口
     */
    public static String tudouStartURL;
    /**
     * 优酷爬虫入口
     */
    public static String youkuStartURL;
    /**
     * Youtube爬虫入口
     */
    public static String youtubeStartURL;

    public static String startUserToken;
    /**
     * 下载网页数
     */
    public static int downloadPageCount;
    /**
     * db.name
     */
    public static String dbName;
    /**
     * db.username
     */
    public static String dbUsername;
    /**
     * db.host
     */
    public static String dbHost;
    /**
     * db.password
     */
    public static String dbPassword;
    /**
     * 创建Url表语句
     */
    public static String createUrlTable;

    /**
     * 创建user表语句
     */
    public static String createUserTable;

    /**
     * cookie路径
     */
    public static String cookiePath;
    /**
     * proxyPath
     */
    public static String proxyPath;

    /**
     * b站代理路径
     */
    public static String biliBiliProxyPath;
    /**
     * proxyPath
     */
    public static String acfunProxyPath;
    /**
     * proxyPath
     */
    public static String douyuProxyPath;
    /**
     * proxyPath
     */
    public static String iqiyiProxyPath;
    /**
     * proxyPath
     */
    public static String letvProxyPath;
    /**
     * proxyPath
     */
    public static String pptvProxyPath;
    /**
     * proxyPath
     */
    public static String sohuProxyPath;
    /**
     * proxyPath
     */
    public static String tudouProxyPath;
    /**
     * proxyPath
     */
    public static String youkuProxyPath;
    /**
     * proxyPath
     */
    public static String youtubeProxyPath;

    static {
        Properties p = new Properties();
        try {
            p.load(Config.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbEnable = Boolean.parseBoolean(p.getProperty("db.enable"));
        verificationCodePath = p.getProperty("verificationCodePath");
        emailOrPhoneNum = p.getProperty("zhiHu.emailOrPhoneNum");
        password = p.getProperty("zhiHu.password");
        biliBiliStartURL = p.getProperty("biliBiliStartURL");
        acfunStartURL = p.getProperty("acfunStartURL");
        douyuStartURL = p.getProperty("douyuStartURL");
        iqiyiStartURL = p.getProperty("iqiyiStartURL");
        letvStartURL = p.getProperty("letvStartURL");
        pptvStartURL = p.getProperty("pptvStartURL");
        sohuStartURL = p.getProperty("sohuStartURL");
        tudouStartURL = p.getProperty("tudouStartURL");
        youkuStartURL = p.getProperty("youkuStartURL");
        youtubeStartURL = p.getProperty("youtubeStartURL");
        startUserToken = p.getProperty("startUserToken");
        downloadPageCount = Integer.valueOf(p.getProperty("downloadPageCount"));
        downloadThreadSize = Integer.valueOf(p.getProperty("downloadThreadSize"));
        websitesThreadSize = Integer.valueOf(p.getProperty("websitesThreadSize"));
        cookiePath = p.getProperty("cookiePath");
        proxyPath = p.getProperty("proxyPath");
        biliBiliProxyPath = p.getProperty("biliBiliProxyPath");
        acfunProxyPath = p.getProperty("acfunProxyPath");
        douyuProxyPath = p.getProperty("douyuProxyPath");
        iqiyiProxyPath = p.getProperty("iqiyiProxyPath");
        letvProxyPath = p.getProperty("letvProxyPath");
        pptvProxyPath = p.getProperty("pptvProxyPath");
        sohuProxyPath = p.getProperty("sohuProxyPath");
        youkuProxyPath = p.getProperty("youkuProxyPath");
        youtubeProxyPath = p.getProperty("youtubeProxyPath");
        tudouProxyPath = p.getProperty("tudouProxyPath");
        isProxy=Boolean.valueOf(p.getProperty("isProxy"));
        bilibiliIsProxy = Boolean.valueOf(p.getProperty("bilibiliIsProxy"));
        acfunIsProxy = Boolean.valueOf(p.getProperty("acfunIsProxy"));
        douyuIsProxy = Boolean.valueOf(p.getProperty("douyuIsProxy"));
        iqiyiIsProxy = Boolean.valueOf(p.getProperty("iqiyiIsProxy"));
        letvIsProxy = Boolean.valueOf(p.getProperty("letvIsProxy"));
        pptvIsProxy = Boolean.valueOf(p.getProperty("pptvIsProxy"));
        sohuIsProxy = Boolean.valueOf(p.getProperty("sohuIsProxy"));
        tudouIsProxy = Boolean.valueOf(p.getProperty("tudouIsProxy"));
        youkuIsProxy = Boolean.valueOf(p.getProperty("youkuIsProxy"));
        youtubeIsProxy = Boolean.valueOf(p.getProperty("youtubeIsProxy"));
        if (dbEnable) {
            dbName = p.getProperty("db.name");
            dbHost = p.getProperty("db.host");
            dbUsername = p.getProperty("db.username");
            dbPassword = p.getProperty("db.password");
            createUrlTable = p.getProperty("createUrlTable");
            createUserTable = p.getProperty("createUserTable");
        }
    }

}
