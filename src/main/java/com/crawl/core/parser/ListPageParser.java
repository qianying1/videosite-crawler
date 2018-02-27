package com.crawl.core.parser;

import com.crawl.videosite.entity.Page;

import java.util.List;

/**
 * 列表页分析器
 */
public interface ListPageParser extends Parser {
    List parseListPage(Page page);
}
