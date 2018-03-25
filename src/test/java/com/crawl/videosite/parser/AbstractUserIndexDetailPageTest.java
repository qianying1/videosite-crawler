
package com.crawl.videosite.parser;

import com.crawl.core.parser.DetailPageParser;
import com.crawl.videosite.ProxyHttpClient;
import com.crawl.videosite.entity.Page;
import com.crawl.videosite.domain.User;

import java.io.IOException;

public abstract class AbstractUserIndexDetailPageTest {
    public void testParse(String url, DetailPageParser parser) throws IOException {
        Page page = ProxyHttpClient.getInstance().getWebPage(url);
        User user = parser.parseDetailPage(page);
//        System.out.println(user);
    }
}