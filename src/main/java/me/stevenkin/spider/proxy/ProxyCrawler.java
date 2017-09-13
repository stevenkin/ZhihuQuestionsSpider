package me.stevenkin.spider.proxy;

import me.stevenkin.spider.proxy.imp.KuaidailiProxySite;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by wjg on 2017/9/9.
 */
public class ProxyCrawler implements Runnable {
    private ProxyPool proxyPool;

    private CloseableHttpClient httpClient;

    public ProxyCrawler(ProxyPool proxyPool) {
        this.proxyPool = proxyPool;
        this.httpClient = HttpClients.createDefault();
    }

    @Override
    public void run() {
        new KuaidailiProxySite(this.httpClient,this.proxyPool).run();
    }

    public static void main(String[] args){
        new ProxyCrawler(new ProxyPool()).run();
    }
}
