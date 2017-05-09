package me.stevenkin.spider;

import me.stevenkin.spider.bean.HttpMethod;
import me.stevenkin.spider.bean.Request;
import me.stevenkin.spider.bean.Page;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
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
                .setSSLSocketFactory(createSSLConnSocketFactory())
                .build();
    }

    public Page download(Request request) throws Exception{
        String url = request.getUrl();
        HttpMethod method = request.getMethod();
        Map<String,String> parames = request.getParames();
        List<NameValuePair> parameList = new ArrayList<>();
        String content = "";
        HttpRequestBase httpUriRequest = null;
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
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpUriRequest.setConfig(requestConfig);
        httpUriRequest.setHeader(new BasicHeader("Accept","*/*"));
        httpUriRequest.setHeader(new BasicHeader("Accept-Encoding","gzip, deflate, sdch, br"));
        httpUriRequest.setHeader(new BasicHeader("Accept-Language","zh-CN,zh;q=0.8"));
        httpUriRequest.setHeader(new BasicHeader("Connection","keep-alive"));
        httpUriRequest.setHeader(new BasicHeader("Cookie","q_c1=e1ef3c5c4adc45b5bc7733bfafd8b44b|1492701919000|1492701919000; aliyungf_tc=AQAAAJyBzkQ2gQ4AFp4ccOAnkAsfyiwI; acw_tc=AQAAAHxQjjvrgQ4AFp4ccJt9YMyC/kFI; _xsrf=b19039228771f1e79177cf9500b0834b; d_c0=\"AGDC7306qQuPTt0pgZbkERrYRQ1hVCI8fOI=|1493115292\"; _zap=f748032f-fab9-4d2d-9994-71d44c39263d; r_cap_id=\"MGY4MTEyZDA1M2E1NGYyZDg5NWQ3ZWYyYWViODQ1YmE=|1493274821|4820db5e80495a8eb45827f61ee015e4da6dafe6\"; cap_id=\"NTU5M2JjZGM5MDg4NDFiMTgxMjg2MmUwNWRhMjYwNjQ=|1493274821|f781ae9a41940cb9a10c36277b6a9eb16e486bfb\"; l_n_c=1; s-q=%E9%80%BC%E4%B9%8E; s-i=4; sid=soe7suo8; s-t=autocomplete; __utma=51854390.1318776062.1493115287.1493447381.1493447917.8; __utmc=51854390; __utmz=51854390.1493447381.7.6.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmv=51854390.100-1|2=registration_date=20131223=1^3=entry_date=20131223=1; z_c0=Mi4wQUFDQVRkTWlBQUFBWU1MdmZUcXBDeGNBQUFCaEFsVk41QjBwV1FEU3M1eFAwcGp2ZWFGTGkzLXJ5VmdQcndoNmh3|1493547790|bf78e3d99fdfd6926c680e3faed577f6769d5a3e"));
        httpUriRequest.setHeader(new BasicHeader("Host","www.zhihu.com"));
        httpUriRequest.setHeader(new BasicHeader("Referer","https://www.zhihu.com/topics"));
        httpUriRequest.setHeader(new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36"));
        httpUriRequest.setHeader(new BasicHeader("X-Requested-With","XMLHttpRequest"));
        HttpResponse response = httpClient.execute(httpUriRequest);
        content = EntityUtils.toString(response.getEntity(),Charset.forName("UTF-8"));
        return new Page(request,content);
    }

    private SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

}