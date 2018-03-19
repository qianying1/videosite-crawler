package com.crawl.videosite.task.acfun.api;

import com.alibaba.fastjson.JSONObject;
import com.crawl.core.util.Config;
import com.crawl.core.util.Constants;
import com.crawl.core.util.HttpClientUtil;
import com.crawl.core.util.JsoupUtil;
import com.crawl.proxy.ProxyPool;
import com.crawl.proxy.entity.Proxy;
import com.crawl.videosite.entity.AcfunParams;
import com.crawl.videosite.entity.AcfunVideoListPersistence;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * 视频列表数据爬取任务
 */
public abstract class AbstractAcfunVideoListApiTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(AbstractAcfunVideoListApiTask.class);
    /**
     * 目标主地址
     */
    private final String DOMAIN = AcfunParams.searchVideoContentDomain;
    /**
     * 视频id
     */
    private static Long id = 0l;
    /**
     * 视频页面大小
     */
    private final Integer PAGE_SIZE = 50;
    /**
     * 视频页码
     */
    private static Integer pageNo = 1;
    /**
     * 视频内容id
     */
    private static Integer type = 1;
    /**
     * 类似于视频分类的东西
     */
    private static Integer cd = 1;
    /**
     * 格式
     */
    private final String format = "system.recomlist";
    /**
     * 时间戳(默认为三十天前)
     */
    private Long _ = new Date().getTime() - 30 * 1000 * 60 * 60 * 24;
    /**
     * 返回空数据的次数
     */
    private static Integer emptyCount = 0;
    /**
     * 需要转换的类型
     */
    private static Integer typeCount = 0;
    /**
     * 最大返回空数据的次数为5次
     */
    private final Integer MAXEMPTYCOUNT = 5;
    /**
     * 抓取的次数
     */
    private static Integer crawlCount = 0;

    protected AbstractAcfunVideoListApiTask(AcfunVideoListPersistence persistence) {
        AbstractAcfunVideoListApiTask.id = persistence.getId();
        AbstractAcfunVideoListApiTask.cd = persistence.getCd();
        AbstractAcfunVideoListApiTask.pageNo = persistence.getPageNo();
        AbstractAcfunVideoListApiTask.type = persistence.getType();
    }

    @Override
    public void run() {
        while (true) {
            crawlCount++;
            if (crawlCount >= 1000) {
                //进行数据持久化
                videoListPersistence();
                crawlCount = 0;
            }
            String jsonStr = null;
            if (Config.acfunIsProxy) {
                try {
                    Proxy currentProxy = ProxyPool.acfunProxyQueue.take();
                    jsonStr = JsoupUtil.getJsonFromApiByProxy(getTargetURL(), currentProxy.getIp(), currentProxy.getPort());
                } catch (InterruptedException e) {
                    logger.error("获取当前线程代理失败，线程被中断", e);
                    try {
                        jsonStr = JsoupUtil.getJsonFromApi(getTargetURL());
                    } catch (IOException io) {
                        logger.error("获取目标页面数据失败，遇到io读写错误", e);
                    }
                }
            } else {
                try {
                    jsonStr = JsoupUtil.getJsonFromApi(getTargetURL());
                } catch (IOException io) {
                    logger.error("获取目标页面数据失败，遇到io读写错误", io);
                }
            }
            if (StringUtils.isBlank(jsonStr)) {
                try {
                    jsonStr = JsoupUtil.getJsonFromApi(getTargetURL());
                } catch (IOException io) {
                    logger.error("获取目标页面数据失败，遇到io读写错误", io);
                }
            }
            if (StringUtils.isBlank(jsonStr)) {
                emptyCount++;
                continue;
            }
            jsonStr = jsonStr.replace("system.recomlist=", "");
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            if (jsonObject == null || jsonObject.isEmpty() || Integer.valueOf(jsonObject.get("status").toString()) != 200) {
                emptyCount++;
                if (emptyCount > MAXEMPTYCOUNT) {
                    if (typeCount % MAXEMPTYCOUNT == 0) {
                        AbstractAcfunVideoListApiTask.id++;
                    } else if (typeCount % MAXEMPTYCOUNT == 1 || typeCount % MAXEMPTYCOUNT == 3) {
                        AbstractAcfunVideoListApiTask.type++;
                        AbstractAcfunVideoListApiTask.id = 1l;
                    } else if (typeCount % MAXEMPTYCOUNT == 2 || typeCount % MAXEMPTYCOUNT == 4) {
                        AbstractAcfunVideoListApiTask.cd++;
                        AbstractAcfunVideoListApiTask.type = 1;
                        AbstractAcfunVideoListApiTask.id = 1l;
                    }
                    pageNo = 1;
                    typeCount++;
                }
                AbstractAcfunVideoListApiTask.pageNo++;
                continue;
            }
            handleJsonData(jsonObject);
            AbstractAcfunVideoListApiTask.pageNo++;
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                logger.error("线程被中断", e);
            }
        }
    }

    protected abstract void handleJsonData(JSONObject jsonObject);

    /**
     * 获取目标地址
     *
     * @return
     */
    private String getTargetURL() {
        return DOMAIN + "format=" + format + "&_=" + _ + "&id=ac" + AbstractAcfunVideoListApiTask.id + "&type=" + AbstractAcfunVideoListApiTask.type + "&cd=" + AbstractAcfunVideoListApiTask.cd + "&pageSize=" + PAGE_SIZE + "&pageNo=" + AbstractAcfunVideoListApiTask.pageNo;
    }

    /**
     * 视频列表数据持久化
     */
    private void videoListPersistence() {
        AcfunVideoListPersistence persistence = new AcfunVideoListPersistence();
        persistence.setCd(AbstractAcfunVideoListApiTask.cd);
        persistence.setFormat(format);
        persistence.setId(AbstractAcfunVideoListApiTask.id);
        persistence.setPageNo(AbstractAcfunVideoListApiTask.pageNo);
        persistence.setPageSize(PAGE_SIZE);
        persistence.setType(AbstractAcfunVideoListApiTask.type);
        HttpClientUtil.serializeObject(persistence, Constants.acfunVideoListDataSerialPath);
    }
}
