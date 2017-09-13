package me.stevenkin.spider.proxy;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wjg on 2017/9/2.
 */
public class Proxy {
    private String ip;
    private Integer port;

    public Proxy(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ip",this.ip);
        jsonObject.put("port",this.port);
        return jsonObject.toJSONString();
    }
}
