
package com.crawl.videosite.parser;

import com.crawl.core.parser.DetailPageParser;
import com.crawl.videosite.entity.Page;
import com.crawl.videosite.entity.User;
import com.crawl.videosite.VideoSiteHttpClient;

import java.io.IOException;

public abstract class AbstractUserIndexDetailPageTest {
    public void testParse(String url, DetailPageParser parser) throws IOException {
        Page page = VideoSiteHttpClient.getInstance().getWebPage(url);
        User user = parser.parseDetailPage(page);
        System.out.println(user);
    }
}