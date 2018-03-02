package com.crawl.core.htmlunit;

import com.crawl.core.util.HtmlUnitWebClientUtil;
import com.crawl.core.util.HttpClientParams;
import com.crawl.videosite.entity.Page;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
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
            WebResponse response = HtmlUnitWebClientUtil.getResponse(url);
            return response.getContentAsStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Page getWebPage(String url) throws Exception {
        return getWebPage(url, "UTF-8");
    }

    public Page getWebPage(String url, String charset) throws Exception {
        Page page = new Page();
        WebResponse response = null;
        response = HtmlUnitWebClientUtil.getResponse(url);
        page.setStatusCode(response.getStatusCode());
        page.setUrl(url);
        if (page.getStatusCode() == 200) {
            page.setHtml(response.getContentAsString());
        }
        return page;
    }

    public Page getWebPage(WebRequest request) throws Exception {
        WebResponse response = null;
        HttpClientParams params = new HttpClientParams();
        response = HtmlUnitWebClientUtil.getResponse(request, params);
        Page page = new Page();
        page.setStatusCode(response.getStatusCode());
        page.setHtml(HtmlUnitWebClientUtil.getWebPage(request));
        page.setUrl(request.getUrl().toString());
        System.out.println(page.getHtml());
//        HtmlUnitWebClientUtil.showLog();
        return page;
    }

}
