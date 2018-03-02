package com.crawl.core.util;

import com.crawl.proxy.entity.Proxy;
import com.crawl.videosite.entity.WebHtmlPage;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;


/**
 * WebClient浏览器模拟工具
 */
public class HtmlUnitWebClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HtmlUnitWebClientUtil.class);
    private static CookieStore cookieStore = new BasicCookieStore();
    private static HttpHost proxy;

    static {
        init();
    }

    /**
     * 参数初始化
     */
    private static void init() {
        try {
            SSLContext sslContext =
                    SSLContexts.custom()
                            .loadTrustMaterial(KeyStore.getInstance(KeyStore.getDefaultType()), new TrustStrategy() {
                                @Override
                                public boolean isTrusted(X509Certificate[] chain, String authType)
                                        throws CertificateException {
                                    return true;
                                }
                            }).build();
            SSLConnectionSocketFactory sslSFactory =
                    new SSLConnectionSocketFactory(sslContext);
            Registry<ConnectionSocketFactory> socketFactoryRegistry =
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", sslSFactory)
                            .build();

            PoolingHttpClientConnectionManager connManager =
                    new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        } catch (Exception e) {
            logger.error("Exception:", e);
        }
    }

    /**
     * 通过url地址获取网页
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static HtmlPage getWebPage(String url, WebClientParams params, Proxy proxy) throws IOException {
        try {
            WebClient webClient = getWebClient();
            initWebClientParams(webClient, params);
            //设置代理
            ProxyConfig proxyConfig;
            if (proxy != null && proxy.getIp() != null) {
                proxyConfig = webClient.getOptions().getProxyConfig();
                proxyConfig.setProxyHost(proxy.getIp());
                proxyConfig.setProxyPort(proxy.getPort());
            }
            //获取页面
            HtmlPage page = webClient.getPage(url);
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("fail to crawl page from url: " + url, e);
            return null;
        }
    }

    /**
     * 初始化浏览器模拟工具
     *
     * @param webClient
     * @param params
     */
    private static void initWebClientParams(WebClient webClient, WebClientParams params) throws Exception {
        if (params.getJavaScriptEnabled() != null)
            webClient.getOptions().setJavaScriptEnabled(params.getJavaScriptEnabled());
        if (params.getCookieEnable() != null)
            webClient.getCookieManager().setCookiesEnabled(params.getCookieEnable());
        // 启动JS
        if (params.getJavaScriptEnabled() != null) {
            webClient.getOptions().setJavaScriptEnabled(params.getJavaScriptEnabled());
            if (params.getJavaScriptEnabled()) {
                webClient.waitForBackgroundJavaScript(Constants.WAITFORBACKGROUNDJAVASCRIPT);
            }
        }
        if (params.getTimeout() != 0) {
            webClient.getOptions().setTimeout(params.getTimeout());
        }
        //忽略ssl认证
        if (params.getUseInsecureSSL() != null)
            webClient.getOptions().setUseInsecureSSL(params.getUseInsecureSSL());
        //禁用Css，可避免自动二次请求CSS进行渲染
        if (params.getCssEnabled() != null)
            webClient.getOptions().setCssEnabled(params.getCssEnabled());
        else
            webClient.getOptions().setCssEnabled(false);
        //运行错误时，不抛出异常
        if (params.getThrowExceptionOnScriptError() != null)
            webClient.getOptions().setThrowExceptionOnScriptError(params.getThrowExceptionOnScriptError());
        else
            webClient.getOptions().setThrowExceptionOnScriptError(false);
        if (params.getThrowExceptionOnFailingStatusCode() != null) {
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(params.getThrowExceptionOnFailingStatusCode());
        } else
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        // 设置Ajax异步
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        if (params.getPopupBlockerEnabled() != null) {
            webClient.getOptions().setPopupBlockerEnabled(params.getPopupBlockerEnabled());
        }
        if (params.getLogEnabled() != null) {
            if (params.getLogEnabled()) {
                LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.OpLog");
                java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit")
                        .setLevel(Level.ALL);
                java.util.logging.Logger.getLogger("org.apache.commons.httpclient")
                        .setLevel(Level.ALL);
            } else {
                LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
                java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit")
                        .setLevel(Level.OFF);
                java.util.logging.Logger.getLogger("org.apache.commons.httpclient")
                        .setLevel(Level.OFF);
            }
        }
    }

    /**
     * 恢复打印log日志
     */
    public static void showLog() {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.OpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit")
                .setLevel(Level.ALL);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient")
                .setLevel(Level.ALL);
    }

    /**
     * 初始化请求参数
     *
     * @param request
     * @param params
     * @param proxy
     */
    private static void initWebRequest(WebRequest request, WebRequestParams params, Proxy proxy, Map<String, String> datas) {
        if (params.getCharset() != null) {
            request.setCharset(Charset.forName(params.getCharset()));
        } else
            request.setCharset(Charset.forName("UTF-8"));
        if (params.getHeaders() != null && !params.getHeaders().isEmpty())
            request.setAdditionalHeaders(params.getHeaders());
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        if (StringUtils.isNotBlank(params.getDataKey()) && !StringUtils.isBlank(params.getDataValue()))
            nameValuePairList.add(new NameValuePair(params.getDataKey(), params.getDataValue()));
        if (ObjectUtils.notEqual(params.getDatas(), null) && !params.getDatas().isEmpty())
            for (String key : params.getDatas().keySet())
                nameValuePairList.add(new NameValuePair(key, params.getDatas().get(key)));
        if (ObjectUtils.notEqual(proxy, null)) {
            request.setSocksProxy(true);
            request.setProxyHost(proxy.getIp());
            request.setProxyPort(proxy.getPort());
        }
        if (datas != null && !datas.isEmpty())
            for (String key : datas.keySet())
                nameValuePairList.add(new NameValuePair(key, datas.get(key)));
        request.setRequestParameters(nameValuePairList);
        if (StringUtils.isNotBlank(params.getEncodeType()))
            try {
                request.setEncodingType(FormEncodingType.getInstance(params.getEncodeType()));
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("fail to get form encode type from name: " + params.getEncodeType(), e);
            }
        if (StringUtils.isNotBlank(params.getRequestBody()))
            request.setRequestBody(params.getRequestBody());
    }

    /**
     * 按照个版本的浏览器内核获取浏览器模拟工具
     *
     * @return
     */
    private static WebClient getWebClient() {
        WebClient client = new WebClient();
        if (client == null)
            client = new WebClient(BrowserVersion.FIREFOX_45);
        if (client == null)
            client = new WebClient(BrowserVersion.FIREFOX_52);
        if (client == null)
            client = new WebClient(BrowserVersion.EDGE);
        if (client == null)
            client = new WebClient(BrowserVersion.INTERNET_EXPLORER);
        if (client == null) {
            client = new WebClient(BrowserVersion.CHROME);
        }
        if (client == null) {
            client = new WebClient(BrowserVersion.getDefault());
        }
        return client;
    }

    public static WebHtmlPage getWebPage(WebRequest request) throws Exception {
        return getWebPage(request, "utf-8");
    }

    /**
     * 通过url发送post请求
     *
     * @param postUrl
     * @param datas
     * @param params
     * @param proxy
     * @return
     * @throws IOException
     */
    public static String requestPost(String postUrl, Map<String, String> datas, WebClientParams params, WebRequestParams requestParams, Proxy proxy) throws IOException {
        try {
            WebClient webClient = getWebClient();
            initWebClientParams(webClient, params);
            WebConnection connection = getWebClient().getWebConnection();
            URL url = new URL(postUrl);
            WebRequest request = new WebRequest(url, HttpMethod.POST);
            initWebRequest(request, requestParams, proxy, datas);
            WebResponse response = connection.getResponse(request);
            return response.getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("fail to crawl page from url: " + postUrl + " in post method", e);
            return "";
        }
    }

    /**
     * 通过url发送get请求
     *
     * @param getUrl
     * @param params
     * @return
     * @throws IOException
     */
    public static String requestGet(String getUrl, Map<String, String> datas, WebClientParams params, WebRequestParams requestParams, Proxy proxy) throws IOException {
        try {
            WebClient webClient = getWebClient();
            initWebClientParams(webClient, params);
            WebConnection connection = getWebClient().getWebConnection();
            URL url = new URL(getUrl);
            WebRequest request = new WebRequest(url, HttpMethod.GET);
            initWebRequest(request, requestParams, proxy, datas);
            WebResponse response = connection.getResponse(request);
            return response.getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("fail to crawl page from url: " + getUrl + " in get method", e);
            return "";
        }
    }

    /**
     * 通过url发送get请求
     *
     * @param getUrl
     * @param params
     * @return
     * @throws IOException
     */
    public static WebRequest getRequest(String getUrl, Map<String, String> datas, WebRequestParams params, Proxy proxy) throws IOException {
        try {
            WebRequest request = new WebRequest(new URL(getUrl), HttpMethod.GET);
            initWebRequest(request, params, proxy, datas);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("fail to get request from url: " + getUrl + " in get method", e);
            return null;
        }
    }

    /**
     * @param encoding 字符编码
     * @return 网页内容
     */
    public static WebHtmlPage getWebPage(WebRequest request
            , String encoding) throws Exception {
        WebClient client = getWebClient();
        if (StringUtils.isNotBlank(encoding)) {
            request.setCharset(Charset.forName(encoding));
        }
        WebClientParams webClientParams = new WebClientParams();
        initWebClientParams(client, webClientParams);
        HtmlPage page = client.getPage(request);
        return new WebHtmlPage(request.getUrl().toString(), page.getWebResponse().getStatusCode(), page, new Proxy(request.getProxyHost(), request.getProxyPort(), 0));
    }

    /**
     * 通过请求获取响应
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static WebResponse getResponse(WebRequest request, WebClientParams params) throws Exception {
        if (request == null)
            return null;
        WebClient client = getWebClient();
        initWebClientParams(client, params);
        WebConnection connection = client.getWebConnection();
        if (connection == null) {
            return new WebResponse(null, request, 0);
        }
        return connection.getResponse(request);
    }

    /**
     * 通过url地址获取响应
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static WebResponse getResponse(String url) throws Exception {
        if (StringUtils.isBlank(url))
            return null;
        WebRequest request = new WebRequest(new URL(url));
        return getResponse(request, null);
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

    }

    /**
     * 获取cookie集(已弃用)
     *
     * @return
     */
    public static CookieStore getCookieStore() {
        return cookieStore;
    }

    public static void setCookieStore(CookieStore cookieStore) {
        HtmlUnitWebClientUtil.cookieStore = cookieStore;
    }

    /**
     * 设置request请求参数
     *
     * @param request
     * @param params
     */
    public static void setHttpPostParams(WebRequest request, Map<String, String> params) {
        if (request == null || params == null || params.isEmpty())
            return;
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (String key : params.keySet())
            nameValuePairs.add(new NameValuePair(key, params.get(key)));
        request.setRequestParameters(nameValuePairs);
    }

    /**
     * 获取请求配置工厂
     *
     * @return
     */
    public static RequestConfig.Builder getRequestConfigBuilder() {
        return RequestConfig.custom().setSocketTimeout(Constants.TIMEOUT).
                setConnectTimeout(Constants.TIMEOUT).
                setConnectionRequestTimeout(Constants.TIMEOUT).
                setCookieSpec(CookieSpecs.STANDARD);
    }

    public static void main(String args[]) {

    }
}
