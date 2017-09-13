package me.stevenkin.spider.proxy.imp;

import me.stevenkin.spider.proxy.AbstractProxySite;
import me.stevenkin.spider.proxy.Proxy;
import me.stevenkin.spider.proxy.ProxyPool;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * Created by wjg on 2017/9/10.
 */
public class KuaidailiProxySite extends AbstractProxySite {

    public KuaidailiProxySite(CloseableHttpClient httpClient, ProxyPool proxyPool){
        super(httpClient,proxyPool);
    }


    @Override
    public String getUrlPatten() {
        return "http://www.kuaidaili.com/free/inha/{}/";
    }

    @Override
    public Map<String,String> headers() {
        Map<String,String> headers = new HashMap();
        headers.put("Cookie","yd_cookie=5b8e2b81-b410-400894122cd85a5a47f4c1fc4d52c4bf96da; _ydclearance=dfbca6aa197e2ddd33671324-8b43-4db4-b4c6-611698ea0d55-1505308882; _gat=1; channelid=0; sid=1505301229581352; _ga=GA1.2.380607871.1502948468; _gid=GA1.2.713066869.1505265939; Hm_lvt_7ed65b1cc4b810e9fd37959c9bb51b31=1505023977,1505265939,1505265955,1505301680; Hm_lpvt_7ed65b1cc4b810e9fd37959c9bb51b31=1505301980");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
        return headers;
    }

    @Override
    public List<Proxy> parseProxys(String content) {
        Document doc = Jsoup.parse(content);
        Elements elements = doc.select("div#list table tbody tr");
        List<Proxy> proxyList = new ArrayList<>();
        for(Element tr : elements){
            Elements tds = tr.children();
            String ip = tds.get(0).text().trim();
            Integer port = Integer.parseInt(tds.get(1).text());
            proxyList.add(new Proxy(ip,port));
        }
        return proxyList;
    }

    @Override
    public int getLastIndex() {
        return 1820;
    }
}
