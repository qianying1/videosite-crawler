package com.crawl.core.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


/**
 * HttpClient工具类
 */
public class JsoupUtil {

    private static Logger logger = LoggerFactory.getLogger(JsoupUtil.class);
    private static Connection conn;

    static {
        init();
    }

    private static void init() {
        try {

        } catch (Exception e) {
            logger.error("Exception:", e);
        }
    }

    public static void url(String url) {
        conn = Jsoup.connect(url);
    }

    public static String getJsonFromApi(String url) throws IOException {
        Document doc = Jsoup
                .connect(url)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                .ignoreContentType(true)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                .timeout(10000).get();
        Element body = doc.body();
        return body.text();
    }

    /**
     * 获取整个静态页面
     *
     * @param url
     * @param datas
     * @param cookies
     * @param httpParams
     * @return String
     * @throws IOException
     */
    public static String getWebPage(String url, Map<String, String> datas, Map<String, String> cookies, HttpClientParams httpParams) throws IOException {
        if (url.equals("")) {
            return "";
        }
        conn = Jsoup.connect(url);
        conn.userAgent(Constants.userAgentArray[new Random().nextInt(Constants.userAgentArray.length)]).timeout(Constants.TIMEOUT);
        if (datas != null && !datas.isEmpty())
            conn.data(datas);
        if (cookies != null && !cookies.isEmpty())
            conn.cookies(cookies);
        initConn(conn, httpParams);
        Document doc = conn.get();
        if (doc == null) {
            return "";
        }
        return doc.toString();
    }

    /**
     * 对连接器进行参数初始化
     *
     * @param conn
     * @param httpParams
     */
    private static void initConn(Connection conn, HttpClientParams httpParams) {
        if (httpParams != null) {
            if (httpParams.getIgnoreContentType())
                conn.ignoreContentType(httpParams.getIgnoreContentType());
            if (httpParams.getIgnoreHttpErrors())
                conn.ignoreHttpErrors(httpParams.getIgnoreHttpErrors());
            if (httpParams.getCookieKey() != null && StringUtils.isNotBlank(httpParams.getCookieKey()) && httpParams.getCookieValue() != null && StringUtils.isNotBlank(httpParams.getCookieValue()))
                conn.cookie(httpParams.getCookieKey(), httpParams.getCookieValue());
            if (httpParams.getDataKey() != null && StringUtils.isNotBlank(httpParams.getDataKey()) && httpParams.getDataValue() != null && StringUtils.isNotBlank(httpParams.getDataValue()))
                conn.data(httpParams.getDataKey(), httpParams.getDataValue());
            if (httpParams.getReferrer() != null && StringUtils.isNotBlank(httpParams.getReferrer())) {
                conn.referrer(httpParams.getReferrer());
            }
            if (httpParams.getMaxBodySize() != null && httpParams.getMaxBodySize().compareTo(0) > 0) {
                conn.maxBodySize(httpParams.getMaxBodySize());
            }
            if (httpParams.getFollowRedirects() != null) {
                conn.followRedirects(httpParams.getFollowRedirects());
            }
            if (httpParams.getUserAgent() != null && StringUtils.isNotBlank(httpParams.getUserAgent())) {
                conn.userAgent(httpParams.getUserAgent());
            }
            if (httpParams.getTimeout() > 0) {
                conn.timeout(httpParams.getTimeout());
            }
            if (httpParams.getHeaders() != null && !httpParams.getHeaders().isEmpty()) {
                for (String key : httpParams.getHeaders().keySet())
                    if (StringUtils.isBlank(key))
                        continue;
                    else
                        conn.header(key, httpParams.getHeaders().get(key));
            }
        }
    }

    public static String getWebPage(Connection.Request request) throws IOException {
        return getWebPage(request, "utf-8");
    }

    /**
     * 通过post方法请求页面
     *
     * @param postUrl
     * @param params
     * @return
     * @throws IOException
     */
    public static String postRequest(String postUrl, final Map<String, String> params) throws IOException {
        conn = Jsoup.connect(postUrl);
        conn.userAgent(Constants.userAgentArray[new Random().nextInt(Constants.userAgentArray.length)]).timeout(Constants.TIMEOUT);
        if (params != null && !params.isEmpty())
            conn.data(params);
        Document doc = conn.post();
        if (doc == null) {
            return "";
        }
        return doc.toString();
    }

    /**
     * 通过请求获取页面
     *
     * @param encoding 字符编码
     * @return 网页内容
     */
    public static String getWebPage(Connection.Request request, String encoding) throws IOException {
        if (conn == null) {
            return "";
        }
        conn.request(request).header("Content-type", "application/x-www-form-urlencoded;charset:" + encoding);
        conn.userAgent(Constants.userAgentArray[new Random().nextInt(Constants.userAgentArray.length)]).timeout(Constants.TIMEOUT);
        Document doc = conn.get();
        if (doc == null) {
            return "";
        }
        return doc.toString();
    }

    /**
     * 通过请求获取响应
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Connection.Response getResponse(final Connection.Request request) throws IOException {
        conn = new Connection() {
            @Override
            public Connection url(URL url) {
                return this.url(url);
            }

            @Override
            public Connection url(String url) {
                return this.url(url);
            }

            @Override
            public Connection userAgent(String userAgent) {
                return this.userAgent(userAgent);
            }

            @Override
            public Connection timeout(int millis) {
                return this.timeout(millis);
            }

            @Override
            public Connection maxBodySize(int bytes) {
                return this.maxBodySize(bytes);
            }

            @Override
            public Connection referrer(String referrer) {
                return this.referrer(referrer);
            }

            @Override
            public Connection followRedirects(boolean followRedirects) {
                return this.followRedirects(followRedirects);
            }

            @Override
            public Connection method(Method method) {
                return this.method(method);
            }

            @Override
            public Connection ignoreHttpErrors(boolean ignoreHttpErrors) {
                return this.ignoreHttpErrors(ignoreHttpErrors);
            }

            @Override
            public Connection ignoreContentType(boolean ignoreContentType) {
                return this.ignoreContentType(ignoreContentType);
            }

            @Override
            public Connection data(String key, String value) {
                return this.data(key, value);
            }

            @Override
            public Connection data(Map<String, String> data) {
                return this.data(data);
            }

            @Override
            public Connection data(String... keyvals) {
                return this;
            }

            @Override
            public Connection header(String name, String value) {
                return this.header(name, value);
            }

            @Override
            public Connection cookie(String name, String value) {
                return this.cookie(name, value);
            }

            @Override
            public Connection cookies(Map<String, String> cookies) {
                return this.cookies(cookies);
            }

            @Override
            public Connection parser(Parser parser) {
                return this.parser(parser);
            }

            @Override
            public Document get() throws IOException {
                return this.get();
            }

            @Override
            public Document post() throws IOException {
                return this.post();
            }

            @Override
            public Response execute() throws IOException {
                this.request(request);
                return this.execute();
            }

            @Override
            public Request request() {
                this.request(request);
                return this.request();
            }

            @Override
            public Connection request(Request request) {
                return this.request(request);
            }

            @Override
            public Response response() {
                this.request(request);
                try {
                    this.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return this.response();
            }

            @Override
            public Connection response(Response response) {
                this.request(request);
                Response response1 = null;
                try {
                    response1 = this.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.response(response1);
                return this;
            }
        };
        conn.userAgent(Constants.userAgentArray[new Random().nextInt(Constants.userAgentArray.length)]).timeout(Constants.TIMEOUT);
        conn.execute();
        return conn.response();
    }

    /**
     * 通过地址请求获取响应
     *
     * @param url
     * @param datas
     * @return
     * @throws IOException
     */
    public static Connection.Response getResponse(String url, Map<String, String> datas) throws IOException {
        conn = Jsoup.connect(url).userAgent(Constants.userAgentArray[new Random().nextInt(Constants.userAgentArray.length)]).timeout(Constants.TIMEOUT);
        if (datas != null && !datas.isEmpty())
            conn.data(datas);
        if (conn == null)
            return null;
        conn.execute();
        return conn.response();
    }

    /**
     * 下载指定地址的单个图片
     *
     * @param fileURL       文件地址
     * @param path          保存路径
     * @param saveFileName  文件名，包括后缀名
     * @param isReplaceFile 若存在文件时，是否还需要下载文件
     */
    public static void downloadFile(String fileURL
            , String path
            , String saveFileName
            , Boolean isReplaceFile) throws IOException {
        //获取下载地址
        URL url = new URL(fileURL);
        //链接网络地址
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            File file = new File(path);
            //如果文件夹不存在则创建
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            } else {
                logger.info("//目录存在");
            }
            file = new File(path + saveFileName);
            if (!file.exists() || isReplaceFile) {
                //如果文件不存在，则下载
                OutputStream os = new FileOutputStream(file);
                InputStream is = connection.getInputStream();
                byte[] buff = new byte[0];
                while (true) {
                    Integer readed = is.read(buff);
                    if (readed == -1) {
                        break;
                    }
                    byte[] temp = new byte[readed];
                    System.arraycopy(buff, 0, temp, 0, readed);
                    os.write(temp);
                    logger.info("文件下载中....");
                }
                os.flush();
                is.close();
                os.close();
                logger.info(fileURL + "--文件成功下载至" + path + saveFileName);
            } else {
                logger.info(path);
                logger.info("该文件存在！");
            }
            connection.disconnect();
        } catch (IllegalArgumentException e) {
            logger.info("连接超时...");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 批量下载文件
     *
     * @param url
     * @throws UnsupportedEncodingException
     */
    public static void downImages(String outPath, String url, Boolean isReplaceFile) throws IOException {

        //获取网站资源
        Document doument = (Document) Jsoup.connect(url).get();
        //获取网站资源图片
        Elements elements = doument.select("img[src]");
        //循环读取
        for (Element e : elements) {//读取网站所有图片
            String outImage = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
            //创建连接
            String imgSrc = e.attr("src");
            logger.info("正在下载图片：" + imgSrc);
            downloadFile(imgSrc, outPath, outImage, isReplaceFile);
            logger.info("图片下载完毕：" + imgSrc);
        }
    }

    /**
     * 通过代理进行get操作
     *
     * @param uri
     * @return
     */
    public static String getWebPageByProxy(String uri, String proxyIp, int proxyPort, Map<String, String> datas) {
        openSystemProxy(proxyIp, proxyPort);

        Document doc = null;
        Connection conn;
        String agent = Constants.userAgentArray[new Random().nextInt(Constants.userAgentArray.length)];
        try {
            conn = Jsoup.connect(uri).ignoreContentType(true)
                    .userAgent(agent)
                    // ignoreHttpErrors
                    //这个很重要 否则会报HTTP error fetching URL. Status=404
                    .ignoreHttpErrors(false)  //这个很重要
                    .timeout(Constants.TIMEOUT);
            if (datas != null && !datas.isEmpty()) {
                conn.data(datas);
            }
            if (conn != null && conn.response().statusCode() != 200) {
                logger.warn("fail to crawl page from uri: " + uri + " in get method");
                closeSystemProxy();
                return "";
            }
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("fail to crawl page from uri: " + uri + " in get method");
        }
        closeSystemProxy();
        if (doc != null) {
            return doc.body().text();
        }
        return null;
    }

    /**
     * 通过代理进行post操作
     *
     * @param uri
     * @return
     */
    public static String postWebPageByProxy(String uri, String proxyIp, int proxyPort, Map<String, String> datas) {
        openSystemProxy(proxyIp, proxyPort);

        Document doc = null;
        Connection conn;
        String agent = Constants.userAgentArray[new Random().nextInt(Constants.userAgentArray.length)];
        try {
            conn = Jsoup.connect(uri).ignoreContentType(true)
                    .userAgent(agent)
                    // ignoreHttpErrors
                    //这个很重要 否则会报HTTP error fetching URL. Status=404
                    .ignoreHttpErrors(false)  //这个很重要
                    .timeout(Constants.TIMEOUT);
            if (datas != null && !datas.isEmpty()) {
                conn.data(datas);
            }
            if (conn != null && conn.response().statusCode() != 200) {
                logger.warn("fail to crawl page from uri: " + uri + " in post method");
                return "";
            }
            doc = conn.post();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("fail to crawl page from uri: " + uri + " in post method");
        }
        if (doc != null) {
            return doc.body().text();
        }
        return null;
    }

    /**
     * 开启系统代理功能
     *
     * @param ip
     * @param port
     */
    private static void openSystemProxy(String ip, int port) {
        System.setProperty("https.proxySet", "true");
        System.getProperties().put("https.proxyHost", ip);
        System.getProperties().put("https.proxyPort", port);
    }

    /**
     * 关闭系统代理功能
     */
    private static void closeSystemProxy() {
        System.setProperty("https.proxySet", "false");
    }

    public static void main(String args[]) {

    }
}
