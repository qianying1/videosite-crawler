package com.crawl.proxy.site.ip181;


import com.crawl.proxy.CommonProxyHttpClient;
import com.crawl.proxy.entity.Proxy;
import com.crawl.videosite.entity.Page;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class Ip181ProxyListPageParserTest {
    @Test
    public void testParse() throws IOException {
        System.out.println(Charset.defaultCharset().toString());
        Page page = CommonProxyHttpClient.getInstance().getWebPage("http://www.ip181.com/daili/1.html");
//        ListPage page = ProxyHttpClient.getInstance().getWebPage("http://www.ip181.com/daili/1.html", "gb2312");
        List<Proxy> urlList = new Ip181ProxyListPageParser().parse(page.getHtml());
        System.out.println(urlList.size());
    }
}
