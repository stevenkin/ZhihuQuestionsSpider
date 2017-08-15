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
        Downloader downloader = new Downloader();
        PageParser pageParser = new PageParser();
        Request request = new Request("https://www.zhihu.com/node/TopicsPlazzaListV2", HttpMethod.POST);
        JSONObject object = new JSONObject();
        object.put("topic_id", 253);
        object.put("offset", 20);
        object.put("hash_id", "22e50cd21ed9df7085ff76d62175e986");
        request.addParame("method", "next")
                .addParame("params", object.toJSONString()).addAttach("topic_id",253).addAttach("offset",20);
        Page page = downloader.download(request);
        System.out.println("page is "+page);
        Result result = pageParser.parsePage(page);
        System.out.println("result is "+result);
    }
}
