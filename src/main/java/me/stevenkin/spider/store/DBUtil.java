package me.stevenkin.spider.store;

import me.stevenkin.spider.SpiderConfig;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by wjg on 2017/8/17.
 */
public class DBUtil {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/**";
    private static String username = "root";
    private static String password = "root";

    static{
        try {
            SpiderConfig spiderConfig = SpiderConfig.getSpiderConfig();
            driver = spiderConfig.getDbDirver();
            url = spiderConfig.getDbUrl();
            username = spiderConfig.getDbUsername();
            password = spiderConfig.getDbPassword();
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url,username,password);
        return conn;
    }
}
