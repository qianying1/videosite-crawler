package com.crawl.videosite.parser;

import com.crawl.core.parser.DetailPageParser;
import org.junit.Test;

import java.io.IOException;

public class VideoSiteNewUserDetailPageParserTest extends AbstractUserIndexDetailPageTest{
    @Test
    public void testParse() throws IOException {
        /**
         * 新版本页面
         */
//        String url = "https://www.zhihu.com/people/cheng-yi-nan/following";
//        String url = "https://www.zhihu.com/people/excited-vczh/following";
        String url = "https://www.videosite.com/people/Vincen.t/following";
        DetailPageParser parser = VideoSiteNewUserDetailPageParser.getInstance();
        super.testParse(url, parser);
    }
}