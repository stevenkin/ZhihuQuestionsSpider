package me.stevenkin.spider.proxy;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Created by wjg on 2017/9/9.
 */
public abstract class AbstractProxySite implements ProxySite {
    private CloseableHttpClient httpClient;

    private ProxyPool proxyPool;

    public AbstractProxySite(CloseableHttpClient httpClient, ProxyPool proxyPool) {
        this.httpClient = httpClient;
        this.proxyPool = proxyPool;
    }

    @Override
    public String download(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        for(Map.Entry<String,String> header : this.headers().entrySet()){
            httpGet.setHeader(header.getKey(),header.getValue());
        }
        CloseableHttpResponse response = this.httpClient.execute(httpGet);
        String content = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
        response.close();
        return content;
    }

    @Override
    public void run() {
        String urlPatten = this.getUrlPatten();
        for(int i=1;i<=this.getLastIndex();i++){
            String url = urlPatten.replace("{}",Integer.toString(i));
            String content = null;
            try {
                content = this.download(url);
                List<Proxy> proxies = this.parseProxys(content);
                for(Proxy proxy : proxies){
                    System.out.println("no check proxy:"+proxy.toJson());
                    if(this.checkProxy(proxy))
                        //this.proxyPool.addProxy(proxy);
                        System.out.println(proxy.toJson());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean checkProxy(Proxy proxy){
        HttpGet httpGet = new HttpGet("http://icanhazip.com/");
        CloseableHttpResponse response = null;
        RequestConfig requestConfig = RequestConfig.custom().setProxy(new HttpHost(proxy.getIp(),proxy.getPort())).build();
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
        try {
            response = this.httpClient.execute(httpGet);
            String content = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
            response.close();
            return proxy.getIp().equals(content.trim());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
