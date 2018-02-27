package com.crawl.videosite.dao;

import com.crawl.core.dao.ConnectionManager;
import org.junit.Test;

import java.sql.Connection;
public class VideoSiteDAOTest {
    @Test
    public void testDBTablesInit(){
        Connection cn = ConnectionManager.getConnection();
        VideoSiteDAO.DBTablesInit(cn);
    }
}
