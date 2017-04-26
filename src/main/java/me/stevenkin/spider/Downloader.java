package me.stevenkin.spider;

import me.stevenkin.spider.bean.HttpMethod;
import me.stevenkin.spider.bean.Request;
import me.stevenkin.spider.bean.Page;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wjg on 2017/4/20.
 */
public class Downloader {
    private CloseableHttpClient httpClient;

    public Downloader(){
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        this.httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }

    public Page download(Request request) throws Exception{
        String url = request.getUrl();
        HttpMethod method = request.getMethod();
        Map<String,String> parames = request.getParames();
        List<NameValuePair> parameList = new ArrayList<>();
        String content = "";
        HttpUriRequest httpUriRequest = null;
        if(parames!=null&&!parames.isEmpty()){
            for(Map.Entry<String ,String> entry : parames.entrySet()){
                parameList.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }

        }
        if(method==HttpMethod.GET){
            String parameStr = URLEncodedUtils.format(parameList, Charset.forName("UTF-8"));
            char c = url.indexOf("?")>-1 ? '&':'?';
            if(parameList!=null&&!parameStr.equals("")){
                url = new StringBuilder().append(url).append(c).append(parameStr).toString();
            }
            httpUriRequest = new HttpGet(url);
        }else if(method==HttpMethod.POST){
            HttpPost httpPost = new HttpPost(url);
            HttpEntity entiry = null;
            entiry = new UrlEncodedFormEntity(parameList, HTTP.UTF_8);
            httpPost.setEntity(entiry);
            httpUriRequest = httpPost;
        }else{

        }
        HttpResponse response = httpClient.execute(httpUriRequest);
        content = EntityUtils.toString(response.getEntity(),Charset.forName("UTF-8"));
        return new Page(request,content);
    }

}