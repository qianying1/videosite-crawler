import com.crawl.core.util.Constants;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.Set;


public class HtmlUnitTest {
    public static void main(String[] args) {
        Document doc = null;
        try {
            doc = Jsoup.connect("" + "1").userAgent(Constants.userAgentArray[11]).timeout(10000).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(doc);
        System.out.println("jsoup\n\n\n");
        String url = "" + "1";//想采集的网址
        String refer = "http://baidu.com/";
        URL link = null;
        try {
            link = new URL(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",    "org.apache.commons.logging.impl.NoOpLog");

        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit")
                .setLevel(Level.OFF);

        java.util.logging.Logger.getLogger("org.apache.commons.httpclient")
                .setLevel(Level.OFF);*/
        WebClient wc = new WebClient();
        WebRequest request = new WebRequest(link);
        request.setCharset(Charset.forName("UTF-8"));
        /*request.setProxyHost("120.120.120.x");
        request.setProxyPort(8080);*/
//        request.setAdditionalHeader("Referer", refer);//设置请求报文头里的refer字段
        ////设置请求报文头里的User-Agent字段
        request.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        //wc.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        //wc.addRequestHeader和request.setAdditionalHeader功能应该是一样的。选择一个即可。
        //其他报文头字段可以根据需要添加
        wc.getCookieManager().setCookiesEnabled(true);//开启cookie管理
        wc.getOptions().setJavaScriptEnabled(true);//开启js解析。对于变态网页，这个是必须的
        wc.getOptions().setCssEnabled(false);//开启css解析。对于变态网页，这个是必须的。
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
        wc.getOptions().setThrowExceptionOnScriptError(false);
        wc.waitForBackgroundJavaScript(30000);
        wc.getOptions().setTimeout(10000);
        // 将探出的页面阻塞住, 防止因为自动弹出页面.
        wc.getOptions().setPopupBlockerEnabled(true);
        //准备工作已经做好了
        HtmlPage page = null;
        try {
            page = wc.getPage(request);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        if (page == null) {
            System.out.println("采集 " + url + " 失败!!!");
            return;
        }
        DomElement element = page.getElementsByTagName("body").get(0);
        DomNodeList<HtmlElement> urlEls = element.getElementsByTagName("a");
        String content = element.asText();//网页内容保存在content里
        if (content == null) {
            System.out.println("采集 " + url + " 失败!!!");
            return;
        }
        System.out.println(content);
        for (HtmlElement aEl : urlEls) {
            System.out.println(aEl.getAttribute("href"));
        }
        //搞定了
        CookieManager CM = wc.getCookieManager(); //WC = Your WebClient's name
        Set<Cookie> cookies_ret = CM.getCookies();//返回的Cookie在这里，下次请求的时候可能可以用上啦
    }
}
