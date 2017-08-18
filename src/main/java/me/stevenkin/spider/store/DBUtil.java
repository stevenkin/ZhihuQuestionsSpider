package me.stevenkin.spider.store;

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
        Properties properties = new Properties();
        try {
            properties.load(DBUtil.class.getClassLoader().getResourceAsStream("app.properties"));
            driver = properties.getProperty("db.driver", "com.mysql.jdbc.Driver");
            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username", "root");
            password = properties.getProperty("db.password", "root");
            Class.forName(driver);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url,username,password);
        return conn;
    }
}
