package com.crawl.proxy.site.xicidaili;


import com.crawl.proxy.ProxyListPageParser;
import com.crawl.proxy.entity.Proxy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.crawl.core.util.Constants.TIME_INTERVAL;

/**
 * Xici代理列表页分析器
 */
public class XicidailiProxyListPageParser implements ProxyListPageParser {
    @Override
    public List<Proxy> parse(String hmtl) {
        Document document = Jsoup.parse(hmtl);
        Elements elements = document.select("table[id=ip_list] tr[class]");
        List<Proxy> proxyList = new ArrayList<>(elements.size());
        for (Element element : elements) {
            String ip = element.select("td:eq(1)").first().text();
            String port = element.select("td:eq(2)").first().text();
            String isAnonymous = element.select("td:eq(4)").first().text();
            if (!anonymousFlag || isAnonymous.contains("匿")) {
                proxyList.add(new Proxy(ip, Integer.valueOf(port), TIME_INTERVAL));
            }
        }
        return proxyList;
    }
}
