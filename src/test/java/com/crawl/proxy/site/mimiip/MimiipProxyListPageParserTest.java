package com.crawl.proxy.site.mimiip;


import com.crawl.proxy.entity.Proxy;
import com.crawl.videosite.CommonHttpClient;
import com.crawl.videosite.entity.Page;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class MimiipProxyListPageParserTest {
    @Test
    public void testParse() throws IOException {
        System.out.println(Charset.defaultCharset().toString());
        Page page = CommonHttpClient.getInstance().getWebPage("http://www.mimiip.com/gngao/");
        List<Proxy> urlList = new MimiipProxyListPageParser().parse(page.getHtml());
        System.out.println(urlList.size());
    }
}
