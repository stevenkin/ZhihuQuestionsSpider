package me.stevenkin.spider;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by wjg on 2017/9/11.
 */
public class SpiderConfig {
    private static class Holder{
        static SpiderConfig config = new SpiderConfig();
    }

    private String redisIp;

    private Integer redisPort;

    private String dbDirver;

    private String dbUrl;

    private String dbUsername;

    private String dbPassword;


    private SpiderConfig() {
        Properties properties = new Properties();
        try {
            properties.load(SpiderConfig.class.getResourceAsStream("/app.properties"));
            this.dbDirver = properties.getProperty("db.driver");
            this.dbUrl = properties.getProperty("db.url");
            this.dbUsername = properties.getProperty("db.username");
            this.dbPassword = properties.getProperty("db.password");
            this.redisIp = properties.getProperty("redis.ip");
            this.redisPort = Integer.parseInt(properties.getProperty("redis.port"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static SpiderConfig getSpiderConfig() {
        return Holder.config;
    }

    public String getRedisIp() {
        return redisIp;
    }

    public Integer getRedisPort() {
        return redisPort;
    }

    public String getDbDirver() {
        return dbDirver;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }
}
