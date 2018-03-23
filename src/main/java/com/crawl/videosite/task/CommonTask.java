package com.crawl.videosite.task;

import com.crawl.core.dao.ConnectionManager;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 共同任务
 */
public class CommonTask {

    /**
     * Thread-数据库连接
     */
    private static Map<Thread, Connection> connectionMap = new ConcurrentHashMap<>();

    /**
     * 每个thread维护一个Connection
     *
     * @return
     */
    protected Connection getConnection() {
        Thread currentThread = Thread.currentThread();
        Connection cn = null;
        if (!connectionMap.containsKey(currentThread)) {
            cn = ConnectionManager.createConnection();
            connectionMap.put(currentThread, cn);
        } else {
            cn = connectionMap.get(currentThread);
        }
        return cn;
    }

    public static Map<Thread, Connection> getConnectionMap() {
        return connectionMap;
    }
}
