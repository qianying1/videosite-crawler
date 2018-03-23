package com.crawl.videosite.parser.tourist;

import com.crawl.videosite.ProxyHttpClient;
import com.crawl.videosite.entity.Page;
import com.crawl.videosite.parser.VideoSiteUserListPageParser;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;

import java.io.IOException;


/*
public class VideoSiteDetailListPageParserTest {
    @Test
    public void testParse(){
        Page page = new Page();
        HttpGet request = new HttpGet("https://www.videosite.com/api/v4/members/wo-yan-chen-mo/followees?include=data[*].educations,employments,answer_count,business,locations,articles_count,follower_count,gender,following_count,question_count,voteup_count,thanked_count,is_followed,is_following,badge[?(type=best_answerer)].topics&offset=0&limit=20");
        request.setHeader("authorization", "oauth " + ProxyHttpClient.getInstance().getAuthorization());
        try {
            page = ProxyHttpClient.getInstance().getWebPage(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        VideoSiteUserListPageParser.getInstance().parseListPage(page);
    }
}*/
