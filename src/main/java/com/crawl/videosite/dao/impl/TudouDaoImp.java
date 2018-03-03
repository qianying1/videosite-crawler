package com.crawl.videosite.dao.impl;


import com.crawl.core.dao.ConnectionManager;
import com.crawl.videosite.dao.TudouDao;
import com.crawl.videosite.dao.VideoSiteDao1;
import com.crawl.videosite.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class TudouDaoImp extends DaoImp implements TudouDao {
    private static Logger logger = LoggerFactory.getLogger(TudouDao.class);
}
