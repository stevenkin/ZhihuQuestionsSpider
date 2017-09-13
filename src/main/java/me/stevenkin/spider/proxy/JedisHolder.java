package me.stevenkin.spider.proxy;

import me.stevenkin.spider.SpiderConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by wjg on 2017/9/3.
 */
public class JedisHolder {
    private static class Holder{
        static JedisHolder holder = new JedisHolder(SpiderConfig.getSpiderConfig());
    }

    public static JedisHolder getJedisHolder(){
        return Holder.holder;
    }

    private SpiderConfig config;

    private String redisIp;

    private int redisPort;

    private JedisPool jedisPool;

    private JedisHolder(SpiderConfig config) {
        this.config = config;
        this.redisIp = this.config.getRedisIp();
        this.redisPort = this.config.getRedisPort();
        JedisPoolConfig config1 = new JedisPoolConfig();
        config1.setLifo(true);
        config1.setMaxIdle(8);
        config1.setMaxTotal(8);
        config1.setMaxWaitMillis(-1);
        config1.setMinEvictableIdleTimeMillis(1800000);
        config1.setMinIdle(0);
        config1.setNumTestsPerEvictionRun(3);
        config1.setSoftMinEvictableIdleTimeMillis(1800000);
        config1.setTestOnBorrow(false);
        config1.setTestWhileIdle(false);
        config1.setTimeBetweenEvictionRunsMillis(-1);

        this.jedisPool = new JedisPool(config1, this.redisIp, this.redisPort, 3000);
    }

    public synchronized Jedis getJedis(){
        return this.jedisPool.getResource();
    }

}
