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
    public static Map<String, Object> acfunCookies;

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
    public static Map<String, Object> acfunDatas;

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
    public static Map<String, Object> biliBiliCookies;

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
    public static Map<String, Object> biliBiliDatas;

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
    public static Map<String, Object> douyuCookies;

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
    public static Map<String, Object> douyuDatas;

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
    public static Map<String, Object> iqiyiCookies;

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
    public static Map<String, Object> iqiyiDatas;

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
    public static Map<String, Object> letvCookies;

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
    public static Map<String, Object> letvDatas;

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
    public static Map<String, Object> pptvCookies;

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
    public static Map<String, Object> pptvDatas;

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
    public static Map<String, Object> sohuCookies;

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
    public static Map<String, Object> sohuDatas;

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
    public static Map<String, Object> tudouCookies;

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
    public static Map<String, Object> tudouDatas;

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
    public static Map<String, Object> youkuCookies;

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
    public static Map<String, Object> youkuDatas;

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
    public static Map<String, Object> youtubeCookies;

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
    public static Map<String, Object> youtubeDatas;

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
     * 视频网站注册手机号码或有限
     */
    public static String emailOrPhoneNum;
    /**
     * 视频网站密码
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

    /**
     * 是否忽略ssl认证
     */
    public static Boolean acfunUseInsecureSSL;
    /**
     * 是否忽略ssl认证
     */
    public static Boolean biliBiliUseInsecureSSL;
    /**
     * 是否忽略ssl认证
     */
    public static Boolean douyuUseInsecureSSL;
    /**
     * 是否忽略ssl认证
     */
    public static Boolean iqiyiUseInsecureSSL;
    /**
     * 是否忽略ssl认证
     */
    public static Boolean letvUseInsecureSSL;
    /**
     * 是否忽略ssl认证
     */
    public static Boolean pptvUseInsecureSSL;
    /**
     * 是否忽略ssl认证
     */
    public static Boolean tudouUseInsecureSSL;
    /**
     * 是否忽略ssl认证
     */
    public static Boolean youkuUseInsecureSSL;
    /**
     * 是否忽略ssl认证
     */
    public static Boolean youtubeUseInsecureSSL;
    /**
     * 是否忽略ssl认证
     */
    public static Boolean sohuUseInsecureSSL;

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
        acfunTimeout = Integer.valueOf(p.getProperty("acfunTimeout"));
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
        youtubeUserAgent = p.getProperty("youtubeUserAgent");
        //----------------------------------------------common-------------------------------------------------//
        dbEnable = Boolean.parseBoolean(p.getProperty("db.enable"));
        verificationCodePath = p.getProperty("verificationCodePath");
        emailOrPhoneNum = p.getProperty("zhiHu.emailOrPhoneNum");
        password = p.getProperty("password");
        startUserToken = p.getProperty("startUserToken");
        downloadPageCount = Integer.valueOf(p.getProperty("downloadPageCount"));
        downloadThreadSize = Integer.valueOf(p.getProperty("downloadThreadSize"));
        websitesThreadSize = Integer.valueOf(p.getProperty("websitesThreadSize"));
        cookiePath = p.getProperty("cookiePath");
        proxyPath = p.getProperty("proxyPath");
        isProxy = Boolean.valueOf(p.getProperty("isProxy"));

        acfunCharset = p.getProperty("acfunCharset");
        biliBiliCharset = p.getProperty("biliBiliCharset");
        douyuCharset = p.getProperty("douyuCharset");
        iqiyiCharset = p.getProperty("iqiyiCharset");
        letvCharset = p.getProperty("letvCharset");
        pptvCharset = p.getProperty("pptvCharset");
        sohuCharset = p.getProperty("sohuCharset");
        tudouCharset = p.getProperty("tudouCharset");
        youkuCharset = p.getProperty("youkuCharset");
        youtubeCharset = p.getProperty("youtubeCharset");

        acfunReferrer = p.getProperty("acfunReferrer");
        biliBiliReferrer = p.getProperty("biliBiliReferrer");
        douyuReferrer = p.getProperty("douyuReferrer");
        iqiyiReferrer = p.getProperty("iqiyiReferrer");
        letvReferrer = p.getProperty("letvReferrer");
        pptvReferrer = p.getProperty("pptvReferrer");
        sohuReferrer = p.getProperty("sohuReferrer");
        tudouReferrer = p.getProperty("tudouReferrer");
        youkuReferrer = p.getProperty("youkuReferrer");
        youtubeReferrer = p.getProperty("youtubeReferrer");

        acfunCookieKey = p.getProperty("acfunCookieKey");
        biliBiliCookieKey = p.getProperty("biliBiliCookieKey");
        douyuCookieKey = p.getProperty("douyuCookieKey");
        iqiyiCookieKey = p.getProperty("iqiyiCookieKey");
        letvCookieKey = p.getProperty("letvCookieKey");
        pptvCookieKey = p.getProperty("pptvCookieKey");
        sohuCookieKey = p.getProperty("sohuCookieKey");
        tudouCookieKey = p.getProperty("tudouCookieKey");
        youkuCookieKey = p.getProperty("youkuCookieKey");
        youtubeCookieKey = p.getProperty("youtubeCookieKey");

        acfunCookieValue = p.getProperty("acfunCookieValue");
        biliBiliCookieValue = p.getProperty("biliBiliCookieValue");
        douyuCookieValue = p.getProperty("douyuCookieValue");
        iqiyiCookieValue = p.getProperty("iqiyiCookieValue");
        letvCookieValue = p.getProperty("letvCookieValue");
        pptvCookieValue = p.getProperty("pptvCookieValue");
        sohuCookieValue = p.getProperty("sohuCookieValue");
        tudouCookieValue = p.getProperty("tudouCookieValue");
        youkuCookieValue = p.getProperty("youkuCookieValue");
        youtubeCookieValue = p.getProperty("youtubeCookieValue");

        acfunUserAgent = p.getProperty("acfunUserAgent");
        biliBiliUserAgent = p.getProperty("biliBiliUserAgent");
        douyuUserAgent = p.getProperty("douyuUserAgent");
        iqiyiUserAgent = p.getProperty("iqiyiUserAgent");
        letvUserAgent = p.getProperty("letvUserAgent");
        pptvUserAgent = p.getProperty("pptvUserAgent");
        sohuUserAgent = p.getProperty("sohuUserAgent");
        tudouUserAgent = p.getProperty("tudouUserAgent");
        youkuUserAgent = p.getProperty("youkuUserAgent");
        youtubeUserAgent = p.getProperty("youtubeUserAgent");

        acfunDataValue = p.getProperty("acfunDataValue");
        biliBiliDataValue = p.getProperty("biliBiliDataValue");
        douyuDataValue = p.getProperty("douyuDataValue");
        iqiyiDataValue = p.getProperty("iqiyiDataValue");
        letvDataValue = p.getProperty("letvDataValue");
        pptvDataValue = p.getProperty("pptvDataValue");
        sohuDataValue = p.getProperty("sohuDataValue");
        tudouDataValue = p.getProperty("tudouDataValue");
        youkuDataValue = p.getProperty("youkuDataValue");
        youtubeDataValue = p.getProperty("youtubeDataValue");

        acfunDataKey = p.getProperty("acfunDataKey");
        biliBiliDataKey = p.getProperty("biliBiliDataKey");
        douyuDataKey = p.getProperty("douyuDataKey");
        iqiyiDataKey = p.getProperty("iqiyiDataKey");
        letvDataKey = p.getProperty("letvDataKey");
        pptvDataKey = p.getProperty("pptvDataKey");
        sohuDataKey = p.getProperty("sohuDataKey");
        tudouDataKey = p.getProperty("tudouDataKey");
        youkuDataKey = p.getProperty("youkuDataKey");
        youtubeDataKey = p.getProperty("youtubeDataKey");

        biliBiliTimeout = Integer.valueOf(p.getProperty("biliBiliTimeout"));
        douyuTimeout = Integer.valueOf(p.getProperty("douyuTimeout"));
        iqiyiTimeout = Integer.valueOf(p.getProperty("iqiyiTimeout"));
        letvTimeout = Integer.valueOf(p.getProperty("letvTimeout"));
        pptvTimeout = Integer.valueOf(p.getProperty("pptvTimeout"));
        sohuTimeout = Integer.valueOf(p.getProperty("sohuTimeout"));
        tudouTimeout = Integer.valueOf(p.getProperty("tudouTimeout"));
        youkuTimeout = Integer.valueOf(p.getProperty("youkuTimeout"));
        youtubeTimeout = Integer.valueOf(p.getProperty("youtubeTimeout"));

        acfunMaxBodySize = Integer.valueOf(p.getProperty("acfunMaxBodySize"));
        biliBiliMaxBodySize = Integer.valueOf(p.getProperty("biliBiliMaxBodySize"));
        douyuMaxBodySize = Integer.valueOf(p.getProperty("douyuMaxBodySize"));
        iqiyiMaxBodySize = Integer.valueOf(p.getProperty("iqiyiMaxBodySize"));
        letvMaxBodySize = Integer.valueOf(p.getProperty("letvMaxBodySize"));
        pptvMaxBodySize = Integer.valueOf(p.getProperty("pptvMaxBodySize"));
        sohuMaxBodySize = Integer.valueOf(p.getProperty("sohuMaxBodySize"));
        tudouMaxBodySize = Integer.valueOf(p.getProperty("tudouMaxBodySize"));
        youkuMaxBodySize = Integer.valueOf(p.getProperty("youkuMaxBodySize"));
        youtubeMaxBodySize = Integer.valueOf(p.getProperty("youtubeMaxBodySize"));

        acfunIgnoreHttpErrors = Boolean.valueOf(p.getProperty("acfunIgnoreHttpErrors"));
        biliBiliIgnoreHttpErrors = Boolean.valueOf(p.getProperty("biliBiliIgnoreHttpErrors"));
        douyuIgnoreHttpErrors = Boolean.valueOf(p.getProperty("douyuIgnoreHttpErrors"));
        iqiyiIgnoreHttpErrors = Boolean.valueOf(p.getProperty("iqiyiIgnoreHttpErrors"));
        letvIgnoreHttpErrors = Boolean.valueOf(p.getProperty("letvIgnoreHttpErrors"));
        pptvIgnoreHttpErrors = Boolean.valueOf(p.getProperty("pptvIgnoreHttpErrors"));
        sohuIgnoreHttpErrors = Boolean.valueOf(p.getProperty("sohuIgnoreHttpErrors"));
        tudouIgnoreHttpErrors = Boolean.valueOf(p.getProperty("tudouIgnoreHttpErrors"));
        youkuIgnoreHttpErrors = Boolean.valueOf(p.getProperty("youkuIgnoreHttpErrors"));
        youtubeIgnoreHttpErrors = Boolean.valueOf(p.getProperty("youtubeIgnoreHttpErrors"));

        acfunFollowRedirects = Boolean.valueOf(p.getProperty("acfunFollowRedirects"));
        biliBiliFollowRedirects = Boolean.valueOf(p.getProperty("biliBiliFollowRedirects"));
        douyuFollowRedirects = Boolean.valueOf(p.getProperty("douyuFollowRedirects"));
        iqiyiFollowRedirects = Boolean.valueOf(p.getProperty("iqiyiFollowRedirects"));
        letvFollowRedirects = Boolean.valueOf(p.getProperty("letvFollowRedirects"));
        pptvFollowRedirects = Boolean.valueOf(p.getProperty("pptvFollowRedirects"));
        sohuFollowRedirects = Boolean.valueOf(p.getProperty("sohuFollowRedirects"));
        tudouFollowRedirects = Boolean.valueOf(p.getProperty("tudouFollowRedirects"));
        youkuFollowRedirects = Boolean.valueOf(p.getProperty("youkuFollowRedirects"));
        youtubeFollowRedirects = Boolean.valueOf(p.getProperty("youtubeFollowRedirects"));

        acfunIgnoreContentType = Boolean.valueOf(p.getProperty("acfunIgnoreContentType"));
        biliBiliIgnoreContentType = Boolean.valueOf(p.getProperty("biliBiliIgnoreContentType"));
        douyuIgnoreContentType = Boolean.valueOf(p.getProperty("douyuIgnoreContentType"));
        iqiyiIgnoreContentType = Boolean.valueOf(p.getProperty("iqiyiIgnoreContentType"));
        letvIgnoreContentType = Boolean.valueOf(p.getProperty("letvIgnoreContentType"));
        pptvIgnoreContentType = Boolean.valueOf(p.getProperty("pptvIgnoreContentType"));
        sohuIgnoreContentType = Boolean.valueOf(p.getProperty("sohuIgnoreContentType"));
        tudouIgnoreContentType = Boolean.valueOf(p.getProperty("tudouIgnoreContentType"));
        youkuIgnoreContentType = Boolean.valueOf(p.getProperty("youkuIgnoreContentType"));
        youtubeIgnoreContentType = Boolean.valueOf(p.getProperty("youtubeIgnoreContentType"));

        acfunUseInsecureSSL = Boolean.valueOf(p.getProperty("acfunUseInsecureSSL"));
        biliBiliUseInsecureSSL = Boolean.valueOf(p.getProperty("biliBiliUseInsecureSSL"));
        douyuUseInsecureSSL = Boolean.valueOf(p.getProperty("douyuUseInsecureSSL"));
        iqiyiUseInsecureSSL = Boolean.valueOf(p.getProperty("iqiyiUseInsecureSSL"));
        letvUseInsecureSSL = Boolean.valueOf(p.getProperty("letvUseInsecureSSL"));
        pptvUseInsecureSSL = Boolean.valueOf(p.getProperty("pptvUseInsecureSSL"));
        sohuUseInsecureSSL = Boolean.valueOf(p.getProperty("sohuUseInsecureSSL"));
        tudouUseInsecureSSL = Boolean.valueOf(p.getProperty("tudouUseInsecureSSL"));
        youkuUseInsecureSSL = Boolean.valueOf(p.getProperty("youkuUseInsecureSSL"));
        youtubeUseInsecureSSL = Boolean.valueOf(p.getProperty("youtubeUseInsecureSSL"));

        acfunThrowExceptionOnScriptError = Boolean.valueOf(p.getProperty("acfunThrowExceptionOnScriptError"));
        biliBiliThrowExceptionOnScriptError = Boolean.valueOf(p.getProperty("biliBiliThrowExceptionOnScriptError"));
        douyuThrowExceptionOnScriptError = Boolean.valueOf(p.getProperty("douyuThrowExceptionOnScriptError"));
        iqiyiThrowExceptionOnScriptError = Boolean.valueOf(p.getProperty("iqiyiThrowExceptionOnScriptError"));
        letvThrowExceptionOnScriptError = Boolean.valueOf(p.getProperty("letvThrowExceptionOnScriptError"));
        pptvThrowExceptionOnScriptError = Boolean.valueOf(p.getProperty("pptvThrowExceptionOnScriptError"));
        sohuThrowExceptionOnScriptError = Boolean.valueOf(p.getProperty("sohuThrowExceptionOnScriptError"));
        tudouThrowExceptionOnScriptError = Boolean.valueOf(p.getProperty("tudouThrowExceptionOnScriptError"));
        youkuThrowExceptionOnScriptError = Boolean.valueOf(p.getProperty("youkuThrowExceptionOnScriptError"));
        youtubeThrowExceptionOnScriptError = Boolean.valueOf(p.getProperty("youtubeThrowExceptionOnScriptError"));

        acfunThrowExceptionOnFailingStatusCode = Boolean.valueOf(p.getProperty("acfunThrowExceptionOnFailingStatusCode"));
        biliBiliThrowExceptionOnFailingStatusCode = Boolean.valueOf(p.getProperty("biliBiliThrowExceptionOnFailingStatusCode"));
        douyuThrowExceptionOnFailingStatusCode = Boolean.valueOf(p.getProperty("douyuThrowExceptionOnFailingStatusCode"));
        iqiyiThrowExceptionOnFailingStatusCode = Boolean.valueOf(p.getProperty("iqiyiThrowExceptionOnFailingStatusCode"));
        letvThrowExceptionOnFailingStatusCode = Boolean.valueOf(p.getProperty("letvThrowExceptionOnFailingStatusCode"));
        pptvThrowExceptionOnFailingStatusCode = Boolean.valueOf(p.getProperty("pptvThrowExceptionOnFailingStatusCode"));
        sohuThrowExceptionOnFailingStatusCode = Boolean.valueOf(p.getProperty("sohuThrowExceptionOnFailingStatusCode"));
        tudouThrowExceptionOnFailingStatusCode = Boolean.valueOf(p.getProperty("tudouThrowExceptionOnFailingStatusCode"));
        youkuThrowExceptionOnFailingStatusCode = Boolean.valueOf(p.getProperty("youkuThrowExceptionOnFailingStatusCode"));
        youtubeThrowExceptionOnFailingStatusCode = Boolean.valueOf(p.getProperty("youtubeThrowExceptionOnFailingStatusCode"));

        acfunJavaScriptEnabled = Boolean.valueOf(p.getProperty("acfunJavaScriptEnabled"));
        biliBiliJavaScriptEnabled = Boolean.valueOf(p.getProperty("biliBiliJavaScriptEnabled"));
        douyuJavaScriptEnabled = Boolean.valueOf(p.getProperty("douyuJavaScriptEnabled"));
        iqiyiJavaScriptEnabled = Boolean.valueOf(p.getProperty("iqiyiJavaScriptEnabled"));
        letvJavaScriptEnabled = Boolean.valueOf(p.getProperty("letvJavaScriptEnabled"));
        pptvJavaScriptEnabled = Boolean.valueOf(p.getProperty("pptvJavaScriptEnabled"));
        sohuJavaScriptEnabled = Boolean.valueOf(p.getProperty("sohuJavaScriptEnabled"));
        tudouJavaScriptEnabled = Boolean.valueOf(p.getProperty("tudouJavaScriptEnabled"));
        youkuJavaScriptEnabled = Boolean.valueOf(p.getProperty("youkuJavaScriptEnabled"));
        youtubeJavaScriptEnabled = Boolean.valueOf(p.getProperty("youtubeJavaScriptEnabled"));

        acfunCssEnabled = Boolean.valueOf(p.getProperty("acfunCssEnabled"));
        biliBiliCssEnabled = Boolean.valueOf(p.getProperty("biliBiliCssEnabled"));
        douyuCssEnabled = Boolean.valueOf(p.getProperty("douyuCssEnabled"));
        iqiyiCssEnabled = Boolean.valueOf(p.getProperty("iqiyiCssEnabled"));
        letvCssEnabled = Boolean.valueOf(p.getProperty("letvCssEnabled"));
        pptvCssEnabled = Boolean.valueOf(p.getProperty("pptvCssEnabled"));
        sohuCssEnabled = Boolean.valueOf(p.getProperty("sohuCssEnabled"));
        tudouCssEnabled = Boolean.valueOf(p.getProperty("tudouCssEnabled"));
        youkuCssEnabled = Boolean.valueOf(p.getProperty("youkuCssEnabled"));
        youtubeCssEnabled = Boolean.valueOf(p.getProperty("youtubeCssEnabled"));

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

        try {
            String[] str = p.getProperty("acfunDatas").split(",");
            for (int i = 0; i < str.length; i += 2) {
                acfunDatas.put(str[i], str[i + 1]);
            }
            str = p.getProperty("biliBiliDatas").split(",");
            for (int i = 0; i < str.length; i += 2) {
                biliBiliDatas.put(str[i], str[i + 1]);
            }
            str = p.getProperty("douyuDatas").split(",");
            for (int i = 0; i < str.length; i += 2) {
                douyuDatas.put(str[i], str[i + 1]);
            }
            str = p.getProperty("iqiyiDatas").split(",");
            for (int i = 0; i < str.length; i += 2) {
                iqiyiDatas.put(str[i], str[i + 1]);
            }
            str = p.getProperty("letvDatas").split(",");
            for (int i = 0; i < str.length; i += 2) {
                letvDatas.put(str[i], str[i + 1]);
            }
            str = p.getProperty("pptvDatas").split(",");
            for (int i = 0; i < str.length; i += 2) {
                pptvDatas.put(str[i], str[i + 1]);
            }
            str = p.getProperty("sohuDatas").split(",");
            for (int i = 0; i < str.length; i += 2) {
                sohuDatas.put(str[i], str[i + 1]);
            }
            str = p.getProperty("tudouDatas").split(",");
            for (int i = 0; i < str.length; i += 2) {
                tudouDatas.put(str[i], str[i + 1]);
            }
            str = p.getProperty("youkuDatas").split(",");
            for (int i = 0; i < str.length; i += 2) {
                youkuDatas.put(str[i], str[i + 1]);
            }
            str = p.getProperty("youtubeDatas").split(",");
            for (int i = 0; i < str.length; i += 2) {
                youtubeDatas.put(str[i], str[i + 1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String[] str = p.getProperty("acfunCookies").split(",");
            for (int i = 0; i < str.length; i += 2) {
                acfunCookies.put(str[i], str[i + 1]);
            }
            str = p.getProperty("biliBiliCookies").split(",");
            for (int i = 0; i < str.length; i += 2) {
                biliBiliCookies.put(str[i], str[i + 1]);
            }
            str = p.getProperty("douyuCookies").split(",");
            for (int i = 0; i < str.length; i += 2) {
                douyuCookies.put(str[i], str[i + 1]);
            }
            str = p.getProperty("iqiyiCookies").split(",");
            for (int i = 0; i < str.length; i += 2) {
                iqiyiCookies.put(str[i], str[i + 1]);
            }
            str = p.getProperty("letvCookies").split(",");
            for (int i = 0; i < str.length; i += 2) {
                letvCookies.put(str[i], str[i + 1]);
            }
            str = p.getProperty("pptvCookies").split(",");
            for (int i = 0; i < str.length; i += 2) {
                pptvCookies.put(str[i], str[i + 1]);
            }
            str = p.getProperty("sohuCookies").split(",");
            for (int i = 0; i < str.length; i += 2) {
                sohuCookies.put(str[i], str[i + 1]);
            }
            str = p.getProperty("tudouCookies").split(",");
            for (int i = 0; i < str.length; i += 2) {
                tudouCookies.put(str[i], str[i + 1]);
            }
            str = p.getProperty("youkuCookies").split(",");
            for (int i = 0; i < str.length; i += 2) {
                youkuCookies.put(str[i], str[i + 1]);
            }
            str = p.getProperty("youtubeCookies").split(",");
            for (int i = 0; i < str.length; i += 2) {
                youtubeCookies.put(str[i], str[i + 1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
