package com.crawl.videosite.dao.impl;


import com.crawl.core.dao.ConnectionManager;
import com.crawl.videosite.dao.VideoSiteDao1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class VideoSiteDao1Imp implements VideoSiteDao1 {
    private static Logger logger = LoggerFactory.getLogger(VideoSiteDao1.class);

    public static void DBTablesInit() {
        ResultSet rs = null;
        Properties p = new Properties();
        Connection cn = ConnectionManager.getConnection();
        try {
            //加载properties文件
            p.load(VideoSiteDao1Imp.class.getResourceAsStream("/config.properties"));
            rs = cn.getMetaData().getTables(null, null, "url", null);
            Statement st = cn.createStatement();
            //不存在url表
            if (!rs.next()) {
                //创建url表
                st.execute(p.getProperty("createUrlTable"));
                logger.info("url表创建成功");
//                st.execute(p.getProperty("createUrlIndex"));
//                logger.info("url表索引创建成功");
            } else {
                logger.info("url表已存在");
            }
            //analized_message表
            rs = cn.getMetaData().getTables(null, null, "analized_message", null);
            if (!rs.next()) {
                //创建analized_message表
                st.execute(p.getProperty("createAnalizedMessageTable"));
                logger.info("analized_message表创建成功");
            } else {
                logger.info("analized_message表已存在");
            }

            //grab_lib
            rs = cn.getMetaData().getTables(null, null, "grab_lib", null);
            if (!rs.next()) {
                //创建analized_message表
                st.execute(p.getProperty("createGrabLibTable"));
                logger.info("grab_lib表创建成功");
            } else {
                logger.info("grab_lib表已存在");
            }

            //grab_message
            rs = cn.getMetaData().getTables(null, null, "grab_message", null);
            if (!rs.next()) {
                //创建analized_message表
                st.execute(p.getProperty("createGrabMessageTable"));
                logger.info("grab_message表创建成功");
            } else {
                logger.info("grab_message表已存在");
            }

            //style
            rs = cn.getMetaData().getTables(null, null, "style", null);
            if (!rs.next()) {
                //创建analized_message表
                st.execute(p.getProperty("createStyleTable"));
                logger.info("style表创建成功");
            } else {
                logger.info("style表已存在");
            }

            //video_author
            rs = cn.getMetaData().getTables(null, null, "video_author", null);
            if (!rs.next()) {
                //创建analized_message表
                st.execute(p.getProperty("createVideoAuthorTable"));
                logger.info("video_author表创建成功");
            } else {
                logger.info("video_author表已存在");
            }

            //video
            rs = cn.getMetaData().getTables(null, null, "video", null);
            if (!rs.next()) {
                //创建analized_message表
                st.execute(p.getProperty("createVideoTable"));
                logger.info("video表创建成功");
            } else {
                logger.info("video表已存在");
            }

            //websites
            rs = cn.getMetaData().getTables(null, null, "websites", null);
            if (!rs.next()) {
                //创建analized_message表
                st.execute(p.getProperty("createWebSitesTable"));
                logger.info("websites表创建成功");
            } else {
                logger.info("websites表已存在");
            }

            //teleplay
            rs = cn.getMetaData().getTables(null, null, "teleplay", null);
            if (!rs.next()) {
                //创建analized_message表
                st.execute(p.getProperty("createTeleplayTable"));
                logger.info("teleplay表创建成功");
            } else {
                logger.info("teleplay表已存在");
            }

            rs = cn.getMetaData().getTables(null, null, "user", null);
            //不存在user表
            if (!rs.next()) {
                //创建user表
                st.execute(p.getProperty("createUserTable"));
                logger.info("user表创建成功");
//                st.execute(p.getProperty("createUserIndex"));
//                logger.info("user表索引创建成功");
            } else {
                logger.info("user表已存在");
            }
            rs.close();
            st.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
