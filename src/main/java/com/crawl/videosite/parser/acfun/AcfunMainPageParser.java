package com.crawl.videosite.parser.acfun;

import com.crawl.videosite.dao.VideoTypeDao;
import com.crawl.videosite.dao.impl.VideoTypeDaoImp;
import com.crawl.videosite.domain.Style;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * a站主页分析器
 */
public class AcfunMainPageParser {
    private static Logger logger = LoggerFactory.getLogger(AcfunMainPageParser.class);

    private VideoTypeDao typeDao;

    public AcfunMainPageParser() {
        this.typeDao = new VideoTypeDaoImp();
    }

    /**
     * 分析主页视频类型
     *
     * @param page
     */
    public void parseMainPage(HtmlPage page, Connection conn) throws Exception {
        logger.info("开始分析a站主页>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
        DomElement navDomEl = page.getElementById("nav");
        Document doc = Jsoup.parse(navDomEl.asXml());
        Element parentNav = doc.getElementsByClass("nav-parent").get(0);
        Element subNav = doc.getElementsByClass("nav-sub").get(0);
        //父类型
        Elements pEls = parentNav.getElementsByTag("li");
        for (Element el : pEls) {
            String cid = el.attr("data-cid");
            if (StringUtils.isBlank(cid))
                continue;
            Style type = new Style();
            type.setAcfun_tid(Long.valueOf(cid));
            Element aEl = el.getElementsByTag("a").get(0);
//            System.out.println("aEl: " + aEl);
            String ahref = aEl.attr("abs:href");
            logger.info("type url>>>>> " + ahref);
            type.setStyleName(aEl.text());
            insertType(type, conn);
        }
        //子类型
        Elements sEls = subNav.getElementsByTag("ul");
        for (Element uEl : sEls) {
            String pCid = uEl.attr("data-cid");
            if (StringUtils.isBlank(pCid))
                continue;
            Style pType = new Style();
            pType.setAcfun_tid(Long.valueOf(pCid));
            for (Element el : uEl.getElementsByTag("li")) {
                Element aEl = el.getElementsByTag("a").get(0);
                if (!ObjectUtils.notEqual(aEl, null))
                    continue;
                String cid = aEl.attr("data-cid");
                if (StringUtils.isBlank(cid))
                    continue;
                Style type = new Style();
                type.setAcfun_tid(Long.valueOf(cid));
                String ahref = aEl.attr("abs:href");
                logger.info("type url>>>>> " + ahref);
                type.setStyleName(aEl.text());
                type.setParent(pType);
                insertType(type, conn);
            }
        }
    }

    /**
     * 插入视频类型
     *
     * @param type
     */
    private void insertType(Style type, Connection conn) {
        boolean isExsit = typeDao.isExistVideoTypeInAcfun(conn, type.getAcfun_tid());
        if (!isExsit) {
            Long id = typeDao.insertAcfunVideoType(conn, type);
            if (id != -1) {
                type.setId(id);
            } else {
                logger.error("插入视频类型数据失败================: " + type.getStyleName());
            }
        } else {
            Long id = typeDao.updateAcfunVideoType(conn, type);
            type.setId(id);
        }
    }
}
