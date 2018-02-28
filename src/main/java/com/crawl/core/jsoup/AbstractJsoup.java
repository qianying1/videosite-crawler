package com.crawl.core.jsoup;

import com.crawl.videosite.entity.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * jsoup客户端，用于提供主要接口
 * <p>
 * Created by qianhaibin on 2018/2/28.
 */
public class AbstractJsoup {

    private Logger logger = LoggerFactory.getLogger(AbstractJsoup.class);

    private AbstractJsoup(){

    }

    public InputStream getWebPageInputStream(String url) {

        return null;
    }

    public Page getWebPage(String url) throws IOException {
        return getWebPage(url, "UTF-8");
    }

    public Page getWebPage(String url, String charset) throws IOException {

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
