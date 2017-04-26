package me.stevenkin.spider.bean;

/**
 * Created by wjg on 2017/4/20.
 */
public class Page {
    private Request request;

    private String content;

    public Page(Request request,String content){
        this.request = request;
        this.content = content;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
