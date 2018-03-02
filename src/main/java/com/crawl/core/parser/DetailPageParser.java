package com.crawl.core.parser;

import com.crawl.videosite.entity.Page;
import com.crawl.videosite.domain.User;

/**
 * 详情页分析器
 */
public interface DetailPageParser extends Parser {
    User parseDetailPage(Page page);
}
