package com.crawl.core.util;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * 加载配置文件
 */
public class Config {

    //-----------------------------------------------------------------------------------------acfun-----------------------------------------------------------------------------//

    /**
     * 是否使用代理抓取
     */
    public static boolean acfunIsProxy;

    /**
     * proxyPath
     */
    public static String acfunProxyPath;

    /**
     * a站爬虫入口
     */
    public static String acfunStartURL;

    /**
     * 是否使用代理抓取
     */
    public static boolean bilibiliIsProxy;

    /**
     * 连接超时时间
     */
    public static int acfunTimeout;

    /**
     * 用户代理器
     */
    public static String acfunUserAgent;

    /**
     * 浏览器cookie key
     */
    public static String acfunCookieKey;

    /**
     * 浏览器cookie value
     */
    public static String acfunCookieValue;

    /**
     * 浏览器cookies
     */
    public static Map<String,Object> acfunCookies;

    /**
     * 需要传递数据的key
     */
    public static String acfunDataKey;

    /**
     * 需要传递数据的value
     */
    public static String acfunDataValue;

    /**
     * 需要传递datas
     */
    public static Map<String,Object> acfunDatas;

    /**
     * 是否忽略数据类型
     */
    public static Boolean acfunIgnoreContentType;

    /**
     * 是否接受重定向
     */
    public static Boolean acfunFollowRedirects;

    /**
     * 是否忽略http请求错误
     */
    public static Boolean acfunIgnoreHttpErrors;

    /**
     * 文档内容容量大小
     */
    public static Integer acfunMaxBodySize;

    /**
     * 访问者
     */
    public static String acfunReferrer;

    /**
     * 是否允许javascript脚本执行
     */
    public static Boolean acfunJavaScriptEnabled;
    /**
     * 是否允许css样式
     */
    public static Boolean acfunCssEnabled;
    /**
     * 是否在遇到返回失败响应码时抛出异常
     */
    public static Boolean acfunThrowExceptionOnFailingStatusCode;
    /**
     * 是否在遇到脚本运行错误时抛出异常
     */
    public static Boolean acfunThrowExceptionOnScriptError;

    /**
     * http请求字符编码
     */
    public static String acfunCharset;

    //-----------------------------------------------------------------------------------------biliBili-----------------------------------------------------------------------------//

    /**
     * b站代理路径
     */
    public static String biliBiliProxyPath;

    /**
     * b站爬虫入口
     */
    public static String biliBiliStartURL;

    /**
     * 连接超时时间
     */
    public static int biliBiliTimeout;

    /**
     * 用户代理器
     */
    public static String biliBiliUserAgent;

    /**
     * 浏览器cookie key
     */
    public static String biliBiliCookieKey;

    /**
     * 浏览器cookie value
     */
    public static String biliBiliCookieValue;

    /**
     * 浏览器cookies
     */
    public static Map<String,Object> biliBiliCookies;

    /**
     * 需要传递数据的key
     */
    public static String biliBiliDataKey;

    /**
     * 需要传递数据的value
     */
    public static String biliBiliDataValue;

    /**
     * 需要传递datas
     */
    public static Map<String,Object> biliBiliDatas;

    /**
     * 是否忽略数据类型
     */
    public static Boolean biliBiliIgnoreContentType;

    /**
     * 是否接受重定向
     */
    public static Boolean biliBiliFollowRedirects;

    /**
     * 是否忽略http请求错误
     */
    public static Boolean biliBiliIgnoreHttpErrors;

    /**
     * 文档内容容量大小
     */
    public static Integer biliBiliMaxBodySize;

    /**
     * 访问者
     */
    public static String biliBiliReferrer;

    /**
     * 是否允许javascript脚本执行
     */
    public static Boolean biliBiliJavaScriptEnabled;
    /**
     * 是否允许css样式
     */
    public static Boolean biliBiliCssEnabled;
    /**
     * 是否在遇到返回失败响应码时抛出异常
     */
    public static Boolean biliBiliThrowExceptionOnFailingStatusCode;
    /**
     * 是否在遇到脚本运行错误时抛出异常
     */
    public static Boolean biliBiliThrowExceptionOnScriptError;

    /**
     * http请求字符编码
     */
    public static String biliBiliCharset;

    //-----------------------------------------------------------------------------------------douyu-----------------------------------------------------------------------------//
    /**
     * proxyPath
     */
    public static String douyuProxyPath;
    /**
     * 斗鱼爬虫入口
     */
    public static String douyuStartURL;
    /**
     * 是否使用代理抓取
     */
    public static boolean douyuIsProxy;
    /**
     * 连接超时时间
     */
    public static int douyuTimeout;

    /**
     * 用户代理器
     */
    public static String douyuUserAgent;

    /**
     * 浏览器cookie key
     */
    public static String douyuCookieKey;

    /**
     * 浏览器cookie value
     */
    public static String douyuCookieValue;

    /**
     * 浏览器cookies
     */
    public static Map<String,Object> douyuCookies;

    /**
     * 需要传递数据的key
     */
    public static String douyuDataKey;

    /**
     * 需要传递数据的value
     */
    public static String douyuDataValue;

    /**
     * 需要传递datas
     */
    public static Map<String,Object> douyuDatas;

    /**
     * 是否忽略数据类型
     */
    public static Boolean douyuIgnoreContentType;

    /**
     * 是否接受重定向
     */
    public static Boolean douyuFollowRedirects;

    /**
     * 是否忽略http请求错误
     */
    public static Boolean douyuIgnoreHttpErrors;

    /**
     * 文档内容容量大小
     */
    public static Integer douyuMaxBodySize;

    /**
     * 访问者
     */
    public static String douyuReferrer;

    /**
     * 是否允许javascript脚本执行
     */
    public static Boolean douyuJavaScriptEnabled;
    /**
     * 是否允许css样式
     */
    public static Boolean douyuCssEnabled;
    /**
     * 是否在遇到返回失败响应码时抛出异常
     */
    public static Boolean douyuThrowExceptionOnFailingStatusCode;
    /**
     * 是否在遇到脚本运行错误时抛出异常
     */
    public static Boolean douyuThrowExceptionOnScriptError;

    /**
     * http请求字符编码
     */
    public static String douyuCharset;

    //-----------------------------------------------------------------------------------------iqiyi-----------------------------------------------------------------------------//
    /**
     * 爱奇艺爬虫入口
     */
    public static String iqiyiStartURL;
    /**
     * 是否使用代理抓取
     */
    public static boolean iqiyiIsProxy;
    /**
     * proxyPath
     */
    public static String iqiyiProxyPath;
    /**
     * 连接超时时间
     */
    public static int iqiyiTimeout;

    /**
     * 用户代理器
     */
    public static String iqiyiUserAgent;

    /**
     * 浏览器cookie key
     */
    public static String iqiyiCookieKey;

    /**
     * 浏览器cookie value
     */
    public static String iqiyiCookieValue;

    /**
     * 浏览器cookies
     */
    public static Map<String,Object> iqiyiCookies;

    /**
     * 需要传递数据的key
     */
    public static String iqiyiDataKey;

    /**
     * 需要传递数据的value
     */
    public static String iqiyiDataValue;

    /**
     * 需要传递datas
     */
    public static Map<String,Object> iqiyiDatas;

    /**
     * 是否忽略数据类型
     */
    public static Boolean iqiyiIgnoreContentType;

    /**
     * 是否接受重定向
     */
    public static Boolean iqiyiFollowRedirects;

    /**
     * 是否忽略http请求错误
     */
    public static Boolean iqiyiIgnoreHttpErrors;

    /**
     * 文档内容容量大小
     */
    public static Integer iqiyiMaxBodySize;

    /**
     * 访问者
     */
    public static String iqiyiReferrer;

    /**
     * 是否允许javascript脚本执行
     */
    public static Boolean iqiyiJavaScriptEnabled;
    /**
     * 是否允许css样式
     */
    public static Boolean iqiyiCssEnabled;
    /**
     * 是否在遇到返回失败响应码时抛出异常
     */
    public static Boolean iqiyiThrowExceptionOnFailingStatusCode;
    /**
     * 是否在遇到脚本运行错误时抛出异常
     */
    public static Boolean iqiyiThrowExceptionOnScriptError;

    /**
     * http请求字符编码
     */
    public static String iqiyiCharset;

    //-----------------------------------------------------------------------------------------letv-----------------------------------------------------------------------------//
    /**
     * proxyPath
     */
    public static String letvProxyPath;
    /**
     * 是否使用代理抓取
     */
    public static boolean letvIsProxy;
    /**
     * 乐视爬虫入口
     */
    public static String letvStartURL;
    /**
     * 连接超时时间
     */
    public static int letvTimeout;

    /**
     * 用户代理器
     */
    public static String letvUserAgent;

    /**
     * 浏览器cookie key
     */
    public static String letvCookieKey;

    /**
     * 浏览器cookie value
     */
    public static String letvCookieValue;

    /**
     * 浏览器cookies
     */
    public static Map<String,Object> letvCookies;

    /**
     * 需要传递数据的key
     */
    public static String letvDataKey;

    /**
     * 需要传递数据的value
     */
    public static String letvDataValue;

    /**
     * 需要传递datas
     */
    public static Map<String,Object> letvDatas;

    /**
     * 是否忽略数据类型
     */
    public static Boolean letvIgnoreContentType;

    /**
     * 是否接受重定向
     */
    public static Boolean letvFollowRedirects;

    /**
     * 是否忽略http请求错误
     */
    public static Boolean letvIgnoreHttpErrors;

    /**
     * 文档内容容量大小
     */
    public static Integer letvMaxBodySize;

    /**
     * 访问者
     */
    public static String letvReferrer;

    /**
     * 是否允许javascript脚本执行
     */
    public static Boolean letvJavaScriptEnabled;
    /**
     * 是否允许css样式
     */
    public static Boolean letvCssEnabled;
    /**
     * 是否在遇到返回失败响应码时抛出异常
     */
    public static Boolean letvThrowExceptionOnFailingStatusCode;
    /**
     * 是否在遇到脚本运行错误时抛出异常
     */
    public static Boolean letvThrowExceptionOnScriptError;

    /**
     * http请求字符编码
     */
    public static String letvCharset;

    //-----------------------------------------------------------------------------------------pptv-----------------------------------------------------------------------------//

    /**
     * 连接超时时间
     */
    public static int pptvTimeout;

    /**
     * 用户代理器
     */
    public static String pptvUserAgent;

    /**
     * 浏览器cookie key
     */
    public static String pptvCookieKey;

    /**
     * 浏览器cookie value
     */
    public static String pptvCookieValue;

    /**
     * 浏览器cookies
     */
    public static Map<String,Object> pptvCookies;

    /**
     * 需要传递数据的key
     */
    public static String pptvDataKey;

    /**
     * 需要传递数据的value
     */
    public static String pptvDataValue;

    /**
     * 需要传递datas
     */
    public static Map<String,Object> pptvDatas;

    /**
     * 是否忽略数据类型
     */
    public static Boolean pptvIgnoreContentType;

    /**
     * 是否接受重定向
     */
    public static Boolean pptvFollowRedirects;

    /**
     * 是否忽略http请求错误
     */
    public static Boolean pptvIgnoreHttpErrors;

    /**
     * 文档内容容量大小
     */
    public static Integer pptvMaxBodySize;

    /**
     * 访问者
     */
    public static String pptvReferrer;

    /**
     * 是否允许javascript脚本执行
     */
    public static Boolean pptvJavaScriptEnabled;
    /**
     * 是否允许css样式
     */
    public static Boolean pptvCssEnabled;
    /**
     * 是否在遇到返回失败响应码时抛出异常
     */
    public static Boolean pptvThrowExceptionOnFailingStatusCode;
    /**
     * 是否在遇到脚本运行错误时抛出异常
     */
    public static Boolean pptvThrowExceptionOnScriptError;

    /**
     * http请求字符编码
     */
    public static String pptvCharset;
    /**
     * 皮皮tv爬虫入口
     */
    public static String pptvStartURL;
    /**
     * 是否使用代理抓取
     */
    public static boolean pptvIsProxy;
    /**
     * proxyPath
     */
    public static String pptvProxyPath;

    //-----------------------------------------------------------------------------------------sohu-----------------------------------------------------------------------------//

    /**
     * 连接超时时间
     */
    public static int sohuTimeout;

    /**
     * 用户代理器
     */
    public static String sohuUserAgent;

    /**
     * 浏览器cookie key
     */
    public static String sohuCookieKey;

    /**
     * 浏览器cookie value
     */
    public static String sohuCookieValue;

    /**
     * 浏览器cookies
     */
    public static Map<String,Object> sohuCookies;

    /**
     * 需要传递数据的key
     */
    public static String sohuDataKey;

    /**
     * 需要传递数据的value
     */
    public static String sohuDataValue;

    /**
     * 需要传递datas
     */
    public static Map<String,Object> sohuDatas;

    /**
     * 是否忽略数据类型
     */
    public static Boolean sohuIgnoreContentType;

    /**
     * 是否接受重定向
     */
    public static Boolean sohuFollowRedirects;

    /**
     * 是否忽略http请求错误
     */
    public static Boolean sohuIgnoreHttpErrors;

    /**
     * 文档内容容量大小
     */
    public static Integer sohuMaxBodySize;

    /**
     * 访问者
     */
    public static String sohuReferrer;

    /**
     * 是否允许javascript脚本执行
     */
    public static Boolean sohuJavaScriptEnabled;
    /**
     * 是否允许css样式
     */
    public static Boolean sohuCssEnabled;
    /**
     * 是否在遇到返回失败响应码时抛出异常
     */
    public static Boolean sohuThrowExceptionOnFailingStatusCode;
    /**
     * 是否在遇到脚本运行错误时抛出异常
     */
    public static Boolean sohuThrowExceptionOnScriptError;

    /**
     * http请求字符编码
     */
    public static String sohuCharset;
    /**
     * 是否使用代理抓取
     */
    public static boolean sohuIsProxy;
    /**
     * 搜狐爬虫入口
     */
    public static String sohuStartURL;
    /**
     * proxyPath
     */
    public static String sohuProxyPath;

    //-----------------------------------------------------------------------------------------tudou-----------------------------------------------------------------------------//

    /**
     * 连接超时时间
     */
    public static int tudouTimeout;

    /**
     * 用户代理器
     */
    public static String tudouUserAgent;

    /**
     * 浏览器cookie key
     */
    public static String tudouCookieKey;

    /**
     * 浏览器cookie value
     */
    public static String tudouCookieValue;

    /**
     * 浏览器cookies
     */
    public static Map<String,Object> tudouCookies;

    /**
     * 需要传递数据的key
     */
    public static String tudouDataKey;

    /**
     * 需要传递数据的value
     */
    public static String tudouDataValue;

    /**
     * 需要传递datas
     */
    public static Map<String,Object> tudouDatas;

    /**
     * 是否忽略数据类型
     */
    public static Boolean tudouIgnoreContentType;

    /**
     * 是否接受重定向
     */
    public static Boolean tudouFollowRedirects;

    /**
     * 是否忽略http请求错误
     */
    public static Boolean tudouIgnoreHttpErrors;

    /**
     * 文档内容容量大小
     */
    public static Integer tudouMaxBodySize;

    /**
     * 访问者
     */
    public static String tudouReferrer;

    /**
     * 是否允许javascript脚本执行
     */
    public static Boolean tudouJavaScriptEnabled;
    /**
     * 是否允许css样式
     */
    public static Boolean tudouCssEnabled;
    /**
     * 是否在遇到返回失败响应码时抛出异常
     */
    public static Boolean tudouThrowExceptionOnFailingStatusCode;
    /**
     * 是否在遇到脚本运行错误时抛出异常
     */
    public static Boolean tudouThrowExceptionOnScriptError;

    /**
     * http请求字符编码
     */
    public static String tudouCharset;
    /**
     * 是否使用代理抓取
     */
    public static boolean tudouIsProxy;
    /**
     * 土豆爬虫入口
     */
    public static String tudouStartURL;
    /**
     * proxyPath
     */
    public static String tudouProxyPath;

    //-----------------------------------------------------------------------------------------youku-----------------------------------------------------------------------------//

    /**
     * 连接超时时间
     */
    public static int youkuTimeout;

    /**
     * 用户代理器
     */
    public static String youkuUserAgent;

    /**
     * 浏览器cookie key
     */
    public static String youkuCookieKey;

    /**
     * 浏览器cookie value
     */
    public static String youkuCookieValue;

    /**
     * 浏览器cookies
     */
    public static Map<String,Object> youkuCookies;

    /**
     * 需要传递数据的key
     */
    public static String youkuDataKey;

    /**
     * 需要传递数据的value
     */
    public static String youkuDataValue;

    /**
     * 需要传递datas
     */
    public static Map<String,Object> youkuDatas;

    /**
     * 是否忽略数据类型
     */
    public static Boolean youkuIgnoreContentType;

    /**
     * 是否接受重定向
     */
    public static Boolean youkuFollowRedirects;

    /**
     * 是否忽略http请求错误
     */
    public static Boolean youkuIgnoreHttpErrors;

    /**
     * 文档内容容量大小
     */
    public static Integer youkuMaxBodySize;

    /**
     * 访问者
     */
    public static String youkuReferrer;

    /**
     * 是否允许javascript脚本执行
     */
    public static Boolean youkuJavaScriptEnabled;
    /**
     * 是否允许css样式
     */
    public static Boolean youkuCssEnabled;
    /**
     * 是否在遇到返回失败响应码时抛出异常
     */
    public static Boolean youkuThrowExceptionOnFailingStatusCode;
    /**
     * 是否在遇到脚本运行错误时抛出异常
     */
    public static Boolean youkuThrowExceptionOnScriptError;

    /**
     * http请求字符编码
     */
    public static String youkuCharset;
    /**
     * 是否使用代理抓取
     */
    public static boolean youkuIsProxy;
    /**
     * 优酷爬虫入口
     */
    public static String youkuStartURL;
    /**
     * proxyPath
     */
    public static String youkuProxyPath;
    //-----------------------------------------------------------------------------------------youtube-----------------------------------------------------------------------------//
    /**
     * 连接超时时间
     */
    public static int youtubeTimeout;

    /**
     * 用户代理器
     */
    public static String youtubeUserAgent;

    /**
     * 浏览器cookie key
     */
    public static String youtubeCookieKey;

    /**
     * 浏览器cookie value
     */
    public static String youtubeCookieValue;

    /**
     * 浏览器cookies
     */
    public static Map<String,Object> youtubeCookies;

    /**
     * 需要传递数据的key
     */
    public static String youtubeDataKey;

    /**
     * 需要传递数据的value
     */
    public static String youtubeDataValue;

    /**
     * 需要传递datas
     */
    public static Map<String,Object> youtubeDatas;

    /**
     * 是否忽略数据类型
     */
    public static Boolean youtubeIgnoreContentType;

    /**
     * 是否接受重定向
     */
    public static Boolean youtubeFollowRedirects;

    /**
     * 是否忽略http请求错误
     */
    public static Boolean youtubeIgnoreHttpErrors;

    /**
     * 文档内容容量大小
     */
    public static Integer youtubeMaxBodySize;

    /**
     * 访问者
     */
    public static String youtubeReferrer;

    /**
     * 是否允许javascript脚本执行
     */
    public static Boolean youtubeJavaScriptEnabled;
    /**
     * 是否允许css样式
     */
    public static Boolean youtubeCssEnabled;
    /**
     * 是否在遇到返回失败响应码时抛出异常
     */
    public static Boolean youtubeThrowExceptionOnFailingStatusCode;
    /**
     * 是否在遇到脚本运行错误时抛出异常
     */
    public static Boolean youtubeThrowExceptionOnScriptError;

    /**
     * http请求字符编码
     */
    public static String youtubeCharset;


    //-------------------------------------------------------------------common------------------------------------------------------------------------------------------------//
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
     * 创建AnalizedMessage表语句
     */
    public static String createAnalizedMessageTable;
    /**
     * 创建GrabLib表语句
     */
    public static String createGrabLibTable;
    /**
     * 创建GrabMessage表语句
     */
    public static String createGrabMessageTable;
    /**
     * 创建Style表语句
     */
    public static String createStyleTable;
    /**
     * 创建VideoAuthor表语句
     */
    public static String createVideoAuthorTable;
    /**
     * 创建Video表语句
     */
    public static String createVideoTable;
    /**
     * 创建WebSites表语句
     */
    public static String createWebSitesTable;
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
        //----------------------------------------------acfun-------------------------------------------------//
        acfunStartURL = p.getProperty("acfunStartURL");
        acfunProxyPath = p.getProperty("acfunProxyPath");
        acfunIsProxy = Boolean.valueOf(p.getProperty("acfunIsProxy"));
        acfunTimeout=Integer.valueOf(p.getProperty("acfunTimeout"));
        //----------------------------------------------biliBili-------------------------------------------------//
        biliBiliStartURL = p.getProperty("biliBiliStartURL");
        biliBiliProxyPath = p.getProperty("biliBiliProxyPath");
        bilibiliIsProxy = Boolean.valueOf(p.getProperty("bilibiliIsProxy"));
        //----------------------------------------------douyu-------------------------------------------------//
        douyuStartURL = p.getProperty("douyuStartURL");
        douyuProxyPath = p.getProperty("douyuProxyPath");
        douyuIsProxy = Boolean.valueOf(p.getProperty("douyuIsProxy"));
        //----------------------------------------------iqiyi-------------------------------------------------//
        iqiyiStartURL = p.getProperty("iqiyiStartURL");
        iqiyiProxyPath = p.getProperty("iqiyiProxyPath");
        iqiyiIsProxy = Boolean.valueOf(p.getProperty("iqiyiIsProxy"));
        //----------------------------------------------letv-------------------------------------------------//
        letvStartURL = p.getProperty("letvStartURL");
        letvProxyPath = p.getProperty("letvProxyPath");
        letvIsProxy = Boolean.valueOf(p.getProperty("letvIsProxy"));
        //----------------------------------------------pptv-------------------------------------------------//
        pptvStartURL = p.getProperty("pptvStartURL");
        pptvProxyPath = p.getProperty("pptvProxyPath");
        pptvIsProxy = Boolean.valueOf(p.getProperty("pptvIsProxy"));
        //----------------------------------------------sohu-------------------------------------------------//
        sohuStartURL = p.getProperty("sohuStartURL");
        sohuProxyPath = p.getProperty("sohuProxyPath");
        sohuIsProxy = Boolean.valueOf(p.getProperty("sohuIsProxy"));
        //----------------------------------------------tudou-------------------------------------------------//
        tudouStartURL = p.getProperty("tudouStartURL");
        tudouProxyPath = p.getProperty("tudouProxyPath");
        tudouIsProxy = Boolean.valueOf(p.getProperty("tudouIsProxy"));
        //----------------------------------------------youku-------------------------------------------------//
        youkuStartURL = p.getProperty("youkuStartURL");
        youkuProxyPath = p.getProperty("youkuProxyPath");
        youkuIsProxy = Boolean.valueOf(p.getProperty("youkuIsProxy"));
        //----------------------------------------------youtube-------------------------------------------------//
        youtubeStartURL = p.getProperty("youtubeStartURL");
        youtubeProxyPath = p.getProperty("youtubeProxyPath");
        youtubeIsProxy = Boolean.valueOf(p.getProperty("youtubeIsProxy"));
        youtubeTimeout=Integer.valueOf(p.getProperty("youtubeTimeout"));
        youtubeUserAgent=p.getProperty("youtubeUserAgent");
        //----------------------------------------------common-------------------------------------------------//
        dbEnable = Boolean.parseBoolean(p.getProperty("db.enable"));
        verificationCodePath = p.getProperty("verificationCodePath");
        emailOrPhoneNum = p.getProperty("zhiHu.emailOrPhoneNum");
        password = p.getProperty("zhiHu.password");
        startUserToken = p.getProperty("startUserToken");
        downloadPageCount = Integer.valueOf(p.getProperty("downloadPageCount"));
        downloadThreadSize = Integer.valueOf(p.getProperty("downloadThreadSize"));
        websitesThreadSize = Integer.valueOf(p.getProperty("websitesThreadSize"));
        cookiePath = p.getProperty("cookiePath");
        proxyPath = p.getProperty("proxyPath");
        isProxy = Boolean.valueOf(p.getProperty("isProxy"));
        if (dbEnable) {
            dbName = p.getProperty("db.name");
            dbHost = p.getProperty("db.host");
            dbUsername = p.getProperty("db.username");
            dbPassword = p.getProperty("db.password");
            createUrlTable = p.getProperty("createUrlTable");
            createUserTable = p.getProperty("createUserTable");
            createAnalizedMessageTable = p.getProperty("createAnalizedMessageTable");
            createGrabLibTable = p.getProperty("createGrabLibTable");
            createGrabMessageTable = p.getProperty("createGrabMessageTable");
            createStyleTable = p.getProperty("createStyleTable");
            createVideoAuthorTable = p.getProperty("createVideoAuthorTable");
            createVideoTable = p.getProperty("createVideoTable");
            createWebSitesTable = p.getProperty("createWebSitesTable");
        }
    }

}
