package com.crawl.core.htmlunit;

import com.crawl.core.util.HtmlUnitWebClientUtil;
import com.crawl.core.util.HttpClientUtil;
import com.crawl.videosite.entity.Page;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * http客户端，用于提供主要接口
 */
public abstract class AbstractHtmlUnit {
    private Logger logger = LoggerFactory.getLogger(AbstractHtmlUnit.class);

    public InputStream getWebPageInputStream(String url) {
        try {
            CloseableHttpResponse response = HttpClientUtil.getResponse(url);
            return response.getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Page getWebPage(String url) throws IOException {
        return getWebPage(url, "UTF-8");
    }

    public Page getWebPage(String url, String charset) throws IOException {
        Page page = new Page();
        CloseableHttpResponse response = null;
        response = HttpClientUtil.getResponse(url);
        page.setStatusCode(response.getStatusLine().getStatusCode());
        page.setUrl(url);
        try {
            if (page.getStatusCode() == 200) {
                page.setHtml(EntityUtils.toString(response.getEntity(), charset));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return page;
    }

    public Page getWebPage(WebRequest request) throws IOException {
        WebResponse response = null;
        response = HtmlUnitWebClientUtil.getResponse(request);
        Page page = new Page();
        page.setStatusCode(response.getStatusCode());
        page.setHtml(response.getContentAsString());
        page.setUrl(request.getUrl().toString());
        return page;
    }

    /**
     * 反序列化CookiesStore
     *
     * @return
     */
    public boolean deserializeCookieStore(String path) {
        try {
            CookieStore cookieStore = (CookieStore) HttpClientUtil.deserializeObject(path);
            HttpClientUtil.setCookieStore(cookieStore);
        } catch (Exception e) {
            logger.warn("反序列化Cookie失败,没有找到Cookie文件");
            return false;
        }
        return true;
    }
}
