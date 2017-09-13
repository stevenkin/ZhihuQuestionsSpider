package me.stevenkin.spider.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wjg on 2017/9/6.
 */
public class ProxyPool {
    private static String PROXY_LIST_KEY = "proxy_list_key";

    private static String PROXY_SET_KEY = "proxy_set_key";

    private static String PROXY_TEST_URL = "";

    private ThreadLocal<Jedis> jedis = new ThreadLocal<Jedis>(){
        public Jedis initialValue(){
            return JedisHolder.getJedisHolder().getJedis();
        }
    };

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    public Proxy getProxy() {
        String prosyJson = this.jedis.get().rpop(PROXY_LIST_KEY);
        return this.jsonToProxy(prosyJson);
    }

    public void returnProxy(Proxy proxy){
        this.jedis.get().lpush(PROXY_LIST_KEY,proxy.toJson());
    }

    public void addProxy(Proxy proxy){
        long i = this.jedis.get().sadd(PROXY_SET_KEY,proxy.toJson());
        if(i!=0){
            this.jedis.get().rpush(PROXY_LIST_KEY,proxy.toJson());
        }
    }

    public void checkProxys(){
        while(true){
            Proxy proxy = this.poplProxy();
            HttpHost host = new HttpHost(proxy.getIp(),proxy.getPort());
            RequestConfig config = RequestConfig.custom().setProxy(host).build();
            HttpGet httpGet = new HttpGet(PROXY_TEST_URL);
            httpGet.setConfig(config);
            try {
                CloseableHttpResponse response = this.httpClient.execute(httpGet);
                String content = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                if(content!=null&&content.trim().equals(proxy.getIp()))
                    this.pushrProxy(proxy);
            } catch (IOException e) {
            }
        }
    }

    public Proxy poplProxy(){
        return this.jsonToProxy(this.jedis.get().lpop(PROXY_LIST_KEY));
    }

    public void pushrProxy(Proxy proxy){
        this.jedis.get().rpush(PROXY_LIST_KEY,proxy.toJson());
    }

    private Proxy jsonToProxy(String json){
        JSONObject jsonObject = JSON.parseObject(json);
        return new Proxy(jsonObject.getString("ip"), jsonObject.getInteger("port"));
    }
}
