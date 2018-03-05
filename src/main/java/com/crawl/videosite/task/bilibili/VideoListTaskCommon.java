package com.crawl.videosite.task.bilibili;

import com.crawl.core.util.Config;
import com.crawl.core.util.JsoupUtil;
import com.crawl.proxy.ProxyPool;
import com.crawl.proxy.entity.Direct;
import com.crawl.proxy.entity.Proxy;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * Created by hboxs014 on 2018/3/5.
 */
public class VideoListTaskCommon {

    public static String getWebPageText(String targetUrl){
        /*String result;
        Proxy currentProxy;
        try {
            if (Config.bilibiliIsProxy) {
                currentProxy = ProxyPool.biliBiliProxyQueue.take();
                if (!(currentProxy instanceof Direct)) {
                    result = JsoupUtil.getJsonFromApiByProxy(targetUrl, currentProxy.getIp(), currentProxy.getPort());
                } else {
                    result = JsoupUtil.getJsonFromApi(targetUrl);
                }
                if (StringUtils.isBlank(result)) {
                    result = JsoupUtil.getJsonFromApi(targetUrl);
                }
            } else {
                result = JsoupUtil.getJsonFromApi(targetUrl);
            }
        } catch (IOException e) {
            logger.warn("正在爬取目标链接时产生io读写错误: " + targetUrl);
            continue;
        } catch (InterruptedException e) {
            logger.error("当前代理线程已被中断: " + getTargetUrl(), e);
            try {
                result = JsoupUtil.getJsonFromApi(getTargetUrl());
            } catch (IOException e1) {
                logger.error("从链接获取json数据失败: " + getTargetUrl(), e1);
                continue;
            }
        }*/
        return "";
    }
}
