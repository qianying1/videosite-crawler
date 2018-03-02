package com.crawl.core.htmlunit;

import com.crawl.core.util.HtmlUnitWebClientUtil;
import com.crawl.videosite.entity.WebHtmlPage;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * http客户端，用于提供主要接口
 */
public abstract class AbstractHtmlUnit {
    private Logger logger = LoggerFactory.getLogger(AbstractHtmlUnit.class);

    public InputStream getWebPageInputStream(String url) {
        try {
            WebResponse response = HtmlUnitWebClientUtil.getResponse(url);
            return response.getContentAsStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public WebHtmlPage getWebPage(String url) throws Exception {
        return getWebPage(url, "UTF-8");
    }

    public WebHtmlPage getWebPage(String url, String charset) throws Exception {
        WebResponse response = HtmlUnitWebClientUtil.getResponse(url);
        return new WebHtmlPage(url, response.getStatusCode(), getWebPage(response.getWebRequest()).getHtmlPage(), null);
    }

    public WebHtmlPage getWebPage(WebRequest request) throws Exception {
        return HtmlUnitWebClientUtil.getWebPage(request);
    }

}
