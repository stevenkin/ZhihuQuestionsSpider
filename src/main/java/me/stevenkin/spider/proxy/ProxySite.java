package me.stevenkin.spider.proxy;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by wjg on 2017/9/9.
 */
public interface ProxySite {
    public String getUrlPatten();

    public String download(String url) throws Exception;

    public Map<String,String> headers();

    public List<Proxy> parseProxys(String content);

    public int getLastIndex();

    public void run();

    public boolean checkProxy(Proxy proxy);
}
