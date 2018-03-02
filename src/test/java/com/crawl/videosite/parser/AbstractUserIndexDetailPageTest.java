
package com.crawl.videosite.parser;

import com.crawl.core.parser.DetailPageParser;
import com.crawl.videosite.entity.Page;
import com.crawl.videosite.domain.User;
import com.crawl.videosite.CommonHttpClient;

import java.io.IOException;

public abstract class AbstractUserIndexDetailPageTest {
    public void testParse(String url, DetailPageParser parser) throws IOException {
        Page page = CommonHttpClient.getInstance().getWebPage(url);
        User user = parser.parseDetailPage(page);
        System.out.println(user);
    }
}