package com.crawl.core.util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.Random;


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

    public static String postRequest(String postUrl, final Map<String, String> params) throws IOException {
        conn = Jsoup.connect(postUrl);
        if (params != null && !params.isEmpty())
            conn.data(params);
        Document doc = conn.post();
        if (doc == null) {
            return "";
        }
        return doc.toString();
    }

    /**
     * @param encoding 字符编码
     * @return 网页内容
     */
    public static String getWebPage(Connection.Request request, String encoding) throws IOException {
        if (conn == null) {
            return "";
        }
        Document doc = conn.request(request).header("Content-type", "application/x-www-form-urlencoded;charset:" + encoding).get();
        if (doc == null) {
            return "";
        }
        return doc.toString();
    }

    public static Connection.Response getResponse(final Connection.Request request) throws IOException {
        URL url = request.url();
        conn = new Connection() {
            @Override
            public Connection url(URL url) {
                return null;
            }

            @Override
            public Connection url(String url) {
                return null;
            }

            @Override
            public Connection userAgent(String userAgent) {
                return null;
            }

            @Override
            public Connection timeout(int millis) {
                return null;
            }

            @Override
            public Connection maxBodySize(int bytes) {
                return null;
            }

            @Override
            public Connection referrer(String referrer) {
                return null;
            }

            @Override
            public Connection followRedirects(boolean followRedirects) {
                return null;
            }

            @Override
            public Connection method(Method method) {
                return null;
            }

            @Override
            public Connection ignoreHttpErrors(boolean ignoreHttpErrors) {
                return null;
            }

            @Override
            public Connection ignoreContentType(boolean ignoreContentType) {
                return null;
            }

            @Override
            public Connection data(String key, String value) {
                return null;
            }

            @Override
            public Connection data(Map<String, String> data) {
                return null;
            }

            @Override
            public Connection data(String... keyvals) {
                return null;
            }

            @Override
            public Connection header(String name, String value) {
                return null;
            }

            @Override
            public Connection cookie(String name, String value) {
                return null;
            }

            @Override
            public Connection cookies(Map<String, String> cookies) {
                return null;
            }

            @Override
            public Connection parser(Parser parser) {
                return null;
            }

            @Override
            public Document get() throws IOException {
                return null;
            }

            @Override
            public Document post() throws IOException {
                return null;
            }

            @Override
            public Response execute() throws IOException {
                return null;
            }

            @Override
            public Request request() {
                return request;
            }

            @Override
            public Connection request(Request request) {
                return null;
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
                return null;
            }
        };
        return conn.response();
    }

    public static Connection.Response getResponse(String url) throws IOException {
        conn = Jsoup.connect(url).userAgent(Constants.userAgentArray[new Random().nextInt(Constants.userAgentArray.length)]);
        if (conn == null)
            return null;
        return conn.response();
    }

    /**
     * 下载图片
     *
     * @param fileURL       文件地址
     * @param path          保存路径
     * @param saveFileName  文件名，包括后缀名
     * @param isReplaceFile 若存在文件时，是否还需要下载文件
     */
    public static void downloadFile(String fileURL
            , String path
            , String saveFileName
            , Boolean isReplaceFile) {
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
                try {
                    OutputStream os = new FileOutputStream(file);
                    InputStream is = null;
                    byte[] buff = new byte[0];
                    while (true) {
                        int readed = is.read(buff);
                        if (readed == -1) {
                            break;
                        }
                        byte[] temp = new byte[readed];
                        System.arraycopy(buff, 0, temp, 0, readed);
                        os.write(temp);
                        logger.info("文件下载中....");
                    }
                    is.close();
                    os.close();
                    logger.info(fileURL + "--文件成功下载至" + path + saveFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                logger.info(path);
                logger.info("该文件存在！");
            }
//            response.close();
        } catch (IllegalArgumentException e) {
            logger.info("连接超时...");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void main(String args[]) {

    }
}
