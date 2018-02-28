package com.crawl.core.htmlunit;

import com.crawl.videosite.entity.Page;
import com.gargoylesoftware.htmlunit.WebRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * htmlunit客户端，用于提供主要接口
 * <p>
 * Created by qianhaibin on 2018/2/28.
 */
public class AbstractHtmlUnit {

    private Logger logger = LoggerFactory.getLogger(AbstractHtmlUnit.class);

    public InputStream getWebPageInputStream(String url) {

        return null;
    }

    public Page getWebPage(String url) throws IOException {
        return getWebPage(url, "UTF-8");
    }

    public Page getWebPage(String url, String charset) throws IOException {

        return null;
    }

    public Page getWebPage(WebRequest request) throws IOException {
        return null;
    }

    /**
     * 反序列化CookiesStore
     *
     * @return
     */
    public boolean deserializeCookieStore(String path) {

        return true;
    }
}
