package com.crawl.videosite.task.acfun.api;

import com.crawl.core.util.Config;
import com.crawl.core.util.Constants;
import com.crawl.core.util.HtmlUnitWebClientUtil;
import com.crawl.core.util.WebClientParams;
import com.crawl.proxy.ProxyPool;
import com.crawl.proxy.entity.Proxy;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

/**
 * 视频类型抓取任务
 */
public abstract class AbstractAcfunTypePageTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(AbstractAcfunTypePageTask.class);

    protected static boolean not_completed=true;
    @Override
    public void run() {
        while (not_completed) {
            WebClientParams params = new WebClientParams();
            params.setCharset("utf-8");
            params.setJavaScriptEnabled(false);
            params.setUserAgent(Constants.userAgentArray[new Random().nextInt(Constants.userAgentArray.length)]);
            HtmlPage mainPage = null;
            try {
                if (Config.acfunIsProxy) {
                    Proxy proxy = ProxyPool.acfunProxyQueue.take();
                    mainPage = HtmlUnitWebClientUtil.getWebPage(Constants.ACFUN_INDEX_URL, params, proxy);
                } else {
                    mainPage = HtmlUnitWebClientUtil.getWebPage(Constants.ACFUN_INDEX_URL, params, null);
                }
                if (mainPage == null || StringUtils.isBlank(mainPage.toString())) {
                    mainPage = HtmlUnitWebClientUtil.getWebPage(Constants.ACFUN_INDEX_URL, params, null);
                }
            } catch (IOException e) {
                logger.error("爬取a站主页发生io读写错误", e);
            } catch (InterruptedException ie) {
                logger.error("爬取a站主页发生线程被中断错误", ie);
            }
            handleMainPage(mainPage);
        }
    }

    protected abstract void handleMainPage(HtmlPage page);
}