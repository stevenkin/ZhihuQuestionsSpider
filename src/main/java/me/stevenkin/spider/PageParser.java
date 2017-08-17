package me.stevenkin.spider;

import com.alibaba.fastjson.JSONObject;

import java.util.regex.Pattern;

/**
 * Created by wjg on 2017/4/20.
 */
public class PageParser {
    public Result parsePage(Page page){
        Pattern pattern = Pattern.compile("https://www.zhihu.com/topic/\\d+/newest");
        Pattern pattern1 = Pattern.compile("https://www.zhihu.com/question/\\d+");
        Result result = new Result();
        result.setPage(page);
        String url = page.getRequest().getUrl();
        if(url.equals("https://www.zhihu.com/topics")) {
            ParseRegularUtil.parseZhihuTopics(page, result);
        }else if(url.equals("https://www.zhihu.com/node/TopicsPlazzaListV2")){
            ParseRegularUtil.parseZhihuTopics1(page,result);
        }else if(pattern.matcher(url).matches()){
            if(page.getRequest().getMethod()== HttpMethod.POST){
                ParseRegularUtil.parseZhihuTopicNewest_post(page,result);
                return result;
            }
            ParseRegularUtil.parseZhihuTopicNewest(page,result);
        }else if(pattern1.matcher(url).matches()){
            ParseRegularUtil.parseZhihuQuestion(page,result);

        }else{

        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        Request request = new Request("https://www.zhihu.com/question/63870972",HttpMethod.GET);
        Page page = new Downloader().download(request);
        System.out.println(page);
        Result result = new PageParser().parsePage(page);
        result.setPage(page);
        new Storer().store(result);
        System.out.println(result);
    }
}
