package com.crawl.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * http client浏览器模拟工具需要进行设置的参数
 * <p>
 * Created by qianhaibin on 2018/2/28.
 */
public class WebClientParams {

    /**
     * 连接超时时间
     */
    private int timeout = Constants.TIMEOUT;

    /**
     * 用户代理器
     */
    private String userAgent = Constants.userAgentArray[new Random().nextInt(Constants.userAgentArray.length)];

    /**
     * 浏览器cookie key
     */
    private String cookieKey;

    /**
     * 浏览器cookie value
     */
    private String cookieValue;

    /**
     * 需要传递数据的key
     */
    private String dataKey;

    /**
     * 需要传递数据的value
     */
    private String dataValue;

    /**
     * 是否忽略数据类型
     */
    private Boolean ignoreContentType = true;

    /**
     * 是否接受重定向
     */
    private Boolean followRedirects = false;

    /**
     * 是否忽略ssl认证
     */
    private Boolean useInsecureSSL = false;

    /**
     * 是否忽略http请求错误
     */
    private Boolean ignoreHttpErrors = false;
    /**
     * 是否将探出的页面阻塞住, 防止因为自动弹出页面.
     */
    private Boolean popupBlockerEnabled = true;

    /**
     * 文档内容容量大小
     */
    private Integer maxBodySize = 8192;

    /**
     * 访问者
     */
    private String referrer = "https://baidu.com";

    /**
     * 是否允许javascript脚本执行
     */
    private Boolean javaScriptEnabled = true;
    /**
     * 是否允许css样式
     */
    private Boolean cssEnabled = false;
    /**
     * 是否在遇到返回失败响应码时抛出异常
     */
    private Boolean throwExceptionOnFailingStatusCode = false;
    /**
     * 是否在遇到脚本运行错误时抛出异常
     */
    private Boolean throwExceptionOnScriptError = false;
    /**
     * 是否开启打印log日志
     */
    private Boolean logEnabled = false;
    /**
     * 代理端口
     */
    private Integer proxyPort;

    /**
     * 目标链接地址
     */
    private String url;

    /**
     * http请求字符编码
     */
    private String charset = "UTF-8";

    /**
     * 代理地址
     */
    private String proxyHost;

    /**
     * 是否允许cookie传输
     */
    private Boolean cookieEnable = true;
    /**
     * 需要发送的数据
     */
    private Map<String, String> datas;
    /**
     * 加密类型
     */
    private String encodeType;
    /**
     * 请求内容
     */
    private String requestBody;
    /**
     * header头
     */
    private Map<String, String> headers = new HashMap<>();

    public WebClientParams() {
        headers.put("User-Agent", Constants.userAgentArray[12]);
        headers.put("Referer", referrer);
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getCookieKey() {
        return cookieKey;
    }

    public void setCookieKey(String cookieKey) {
        this.cookieKey = cookieKey;
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public Boolean getIgnoreContentType() {
        return ignoreContentType;
    }

    public void setIgnoreContentType(Boolean ignoreContentType) {
        this.ignoreContentType = ignoreContentType;
    }

    public Boolean getFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(Boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    public Boolean getIgnoreHttpErrors() {
        return ignoreHttpErrors;
    }

    public void setIgnoreHttpErrors(Boolean ignoreHttpErrors) {
        this.ignoreHttpErrors = ignoreHttpErrors;
    }

    public Integer getMaxBodySize() {
        return maxBodySize;
    }

    public void setMaxBodySize(Integer maxBodySize) {
        this.maxBodySize = maxBodySize;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public Boolean getJavaScriptEnabled() {
        return javaScriptEnabled;
    }

    public void setJavaScriptEnabled(Boolean javaScriptEnabled) {
        this.javaScriptEnabled = javaScriptEnabled;
    }

    public Boolean getCssEnabled() {
        return cssEnabled;
    }

    public void setCssEnabled(Boolean cssEnabled) {
        this.cssEnabled = cssEnabled;
    }

    public Boolean getThrowExceptionOnFailingStatusCode() {
        return throwExceptionOnFailingStatusCode;
    }

    public void setThrowExceptionOnFailingStatusCode(Boolean throwExceptionOnFailingStatusCode) {
        this.throwExceptionOnFailingStatusCode = throwExceptionOnFailingStatusCode;
    }

    public Boolean getThrowExceptionOnScriptError() {
        return throwExceptionOnScriptError;
    }

    public void setThrowExceptionOnScriptError(Boolean throwExceptionOnScriptError) {
        this.throwExceptionOnScriptError = throwExceptionOnScriptError;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Boolean getCookieEnable() {
        return cookieEnable;
    }

    public void setCookieEnable(Boolean cookieEnable) {
        this.cookieEnable = cookieEnable;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Boolean getUseInsecureSSL() {
        return useInsecureSSL;
    }

    public void setUseInsecureSSL(Boolean useInsecureSSL) {
        this.useInsecureSSL = useInsecureSSL;
    }

    public Map<String, String> getDatas() {
        return datas;
    }

    public void setDatas(Map<String, String> datas) {
        this.datas = datas;
    }

    public String getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(String encodeType) {
        this.encodeType = encodeType;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Boolean getPopupBlockerEnabled() {
        return popupBlockerEnabled;
    }

    public void setPopupBlockerEnabled(Boolean popupBlockerEnabled) {
        this.popupBlockerEnabled = popupBlockerEnabled;
    }

    public Boolean getLogEnabled() {
        return logEnabled;
    }

    public void setLogEnabled(Boolean logEnabled) {
        this.logEnabled = logEnabled;
    }

}
