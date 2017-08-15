package me.stevenkin.spider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wjg on 2017/4/22.
 */
public class Request {
    private String url;
    private HttpMethod method;
    private Map<String,String> parames = new HashMap<>();
    private Map<String,Object> attach = new HashMap<>();

    public Request(String url, HttpMethod method){
        this.method = method==null?HttpMethod.GET:method;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public Map<String, String> getParames() {
        return parames;
    }

    public void setParames(Map<String, String> parames) {
        this.parames = parames;
    }

    public Request addParame(String key, String value){
        this.parames.put(key,value);
        return this;
    }

    public Request addAttach(String key,Object value){
        this.attach.put(key,value);
        return this;
    }

    public Object getAddch(String key){
        return this.attach.get(key);
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", method=" + method +
                ", parames=" + parames +
                '}';
    }
}
