package com.crawl.videosite.entity;


import com.crawl.proxy.entity.Proxy;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 抓取到的页面
 */
public class WebHtmlPage {
    private String url;
    private int statusCode;//响应状态码
    private HtmlPage htmlPage;//response content
    private Proxy proxy;

    public WebHtmlPage(String url, int statusCode, HtmlPage htmlPage, Proxy proxy) {
        this.url = url;
        this.statusCode = statusCode;
        this.htmlPage = htmlPage;
        this.proxy = proxy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public HtmlPage getHtmlPage() {
        return htmlPage;
    }

    public void setHtmlPage(HtmlPage htmlPage) {
        this.htmlPage = htmlPage;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebHtmlPage page = (WebHtmlPage) o;

        return url.equals(page.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return "WebHtmlPage{" +
                "url='" + url + '\'' +
                ", statusCode=" + statusCode +
                ", htmlPage=" + htmlPage +
                ", proxy=" + proxy +
                '}';
    }
}
