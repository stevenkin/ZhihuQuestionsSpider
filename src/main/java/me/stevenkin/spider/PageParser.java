package me.stevenkin.spider;

import me.stevenkin.spider.bean.Page;
import me.stevenkin.spider.bean.Result;

/**
 * Created by wjg on 2017/4/20.
 */
public class PageParser {
    public Result parsePage(Page page){
        Result result = new Result();
        String url = page.getRequest().getUrl();
        if(url.equals("https://www.zhihu.com/topics")) {
            ParseRegularUtil.parseZhihuTopics(page, result);
        }else if(url.equals("https://www.zhihu.com/node/TopicsPlazzaListV2")){
            ParseRegularUtil.parseZhihuTopics1(page,result);
        }else{

        }
        return result;
    }
}
