package com.crawl;

import com.crawl.core.util.Constants;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 爬虫入口
 */
public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) {
        //开始进行视频网站的抓取
//        Initializer.getInstance().startVideoSitesCrawler();
        Document doc=null;
        try{
            doc =Jsoup.connect(Constants.BILIBILI_INDEX_URL).userAgent(Constants.userAgentArray[11]).timeout(10000).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(doc);
        System.out.println("jsoup\n\n\n");
        String  url=Constants.BILIBILI_INDEX_URL;//想采集的网址
        String refer="http://outofmemory.cn/";
        URL link=null;
        try{
            link=new URL(url);
        }catch (Exception e){
            e.printStackTrace();
        }
        WebClient wc=new WebClient();
        WebRequest request=new WebRequest(link);
        request.setCharset(Charset.forName("UTF-8"));
        /*request.setProxyHost("120.120.120.x");
        request.setProxyPort(8080);*/
        request.setAdditionalHeader("Referer", refer);//设置请求报文头里的refer字段
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
        //设置cookie。如果你有cookie，可以在这里设置
        /*Set<Cookie> cookies=null;
        Iterator<Cookie> i = cookies.iterator();
        while (i.hasNext())
        {
            wc.getCookieManager().addCookie(i.next());
        }*/
        //准备工作已经做好了
        HtmlPage page=null;
        try{
            page = wc.getPage(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(page==null)
        {
            System.out.println("采集 "+url+" 失败!!!");
            return ;
        }
        DomElement element=page.getElementsByTagName("body").get(0);
        DomNodeList<HtmlElement> urlEls=element.getElementsByTagName("a");
        String content=element.asText();//网页内容保存在content里
        if(content==null)
        {
            System.out.println("采集 "+url+" 失败!!!");
            return ;
        }
        System.out.println(content);
        for (HtmlElement aEl:urlEls){
            System.out.println(aEl.getAttribute("href"));
        }
        //搞定了
        CookieManager CM = wc.getCookieManager(); //WC = Your WebClient's name
        Set<Cookie> cookies_ret = CM.getCookies();//返回的Cookie在这里，下次请求的时候可能可以用上啦

    }

}
