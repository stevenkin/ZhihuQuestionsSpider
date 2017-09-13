package me.stevenkin.spider.proxy;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * Created by wjg on 2017/9/8.
 */
public class ProxyChecker implements Runnable {

    private ProxyPool proxyPool;

    public ProxyChecker(ProxyPool proxyPool) {
        this.proxyPool = proxyPool;
    }

    @Override
    public void run() {
        this.proxyPool.checkProxys();
    }
}