import com.alibaba.fastjson.JSON;
import com.crawl.videosite.entity.BiliBiliParams;
import net.minidev.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Map;


public class JsoupTest {
    public static void main(String[] args) throws Exception {
        /*String s = "<div class=\"profile-navbar clearfix\">\n" +
                "<a class=\"item home first \" href=\"/people/wo-yan-chen-mo\">\n" +
                "<i class=\"icon icon-profile-tab-home\"></i><span class=\"hide-text\">主页</span>\n" +
                "</a>\n" +
                "<a class=\"item \" href=\"/people/wo-yan-chen-mo/asks\">\n" +
                "提问\n" +
                "<span class=\"num\">2</span>\n" +
                "</a>\n" +
                "<a class=\"item \" href=\"/people/wo-yan-chen-mo/answers\">\n" +
                "回答\n" +
                "<span class=\"num\">4</span>\n" +
                "</a>\n" +
                "<a class=\"item \" href=\"/people/wo-yan-chen-mo/posts\">\n" +
                "文章\n" +
                "<span class=\"num\">0</span>\n" +
                "</a>\n" +
                "\n" +
                "<a class=\"item \" href=\"/people/wo-yan-chen-mo/collections\">\n" +
                "收藏\n" +
                "<span class=\"num\">5</span>\n" +
                "</a>\n" +
                "<a class=\"item \" href=\"/people/wo-yan-chen-mo/logs\">\n" +
                "公共编辑\n" +
                "<span class=\"num\">4</span>\n" +
                "</a>\n" +
                "\n" +
                "</div>";
        Document document = Jsoup.parse(s);
        Elements es = document.select("div.profile-navbar a[href$=asks]");*/
        Document doc = Jsoup
                .connect(BiliBiliParams.listDomain + "&rid=1&ps=50&pn=1")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                .header("Content-Type", "application/json").ignoreContentType(true)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                .timeout(10000).get();
        Element body = doc.body();
        Map<String,Object> json=(Map<String, Object>) JSON.parse(body.text());
        System.out.println(json);
        System.out.println(((Map<String,Object>)json.get("data")).get("archives"));
    }
}
