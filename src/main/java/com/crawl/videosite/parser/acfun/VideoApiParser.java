package com.crawl.videosite.parser.acfun;

import java.util.List;

/**
 * a站视频api数据分析器
 */
public class VideoApiParser {

    /**
     * 分析视频里的数字
     *
     * @param counts
     * @param contentId
     */
    public static void parseVideoCounts(List<Long> counts, Long contentId){
        if (counts==null||counts.isEmpty()||contentId==null)
            return;
        Long views=counts.get(0);
        Long comments=counts.get(1);
        Long count1=counts.get(2);
        Long count2=counts.get(3);
        Long masks=counts.get(4);
        Long favorite=counts.get(5);
        Long banana=counts.get(6);
        Long count3=counts.get(7);
    }
}
