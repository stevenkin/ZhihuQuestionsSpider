package me.stevenkin.spider.proxy;

import redis.clients.jedis.Jedis;

/**
 * Created by wjg on 2017/9/11.
 */
public class TestProxy {

    public static void main(String[] args){
        ProxyPool proxyPool = new ProxyPool();
        for(int i=0;i<10;i++){
            proxyPool.addProxy(new Proxy(Integer.toString(i),i));
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(proxyPool.getProxy().toJson());
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(proxyPool.getProxy().toJson());
            }
        }).start();

    }
}
