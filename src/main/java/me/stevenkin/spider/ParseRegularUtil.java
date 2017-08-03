package me.stevenkin.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.stevenkin.spider.bean.HttpMethod;
import me.stevenkin.spider.bean.Page;
import me.stevenkin.spider.bean.Request;
import me.stevenkin.spider.bean.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by wjg on 2017/4/26.
 */
public class ParseRegularUtil {
    public static void parseZhihuTopics(Page page, Result result){
        String html = page.getContent();
        Document doc = Jsoup.parse(html);
        Element zmTopicCatMainUl = doc.body().select("div.zg-wrap.zu-main.clearfix").first().select("ul.zm-topic-cat-main.clearfix").first();
        Elements lis = zmTopicCatMainUl.getElementsByTag("li");
        for(Element element : lis){
            String topicId = element.attr("data-id");
            Request request = new Request("https://www.zhihu.com/node/TopicsPlazzaListV2", HttpMethod.POST);
            JSONObject object = new JSONObject();
            object.put("topic_id",Integer.parseInt(topicId));
            object.put("offset",0);
            object.put("hash_id","22e50cd21ed9df7085ff76d62175e986");
            request.addParame("method","next")
                    .addParame("params",object.toJSONString());
            result.addRequest(request);
        }
    }

    public static void parseZhihuTopics1(Page page, Result result){
        String json = page.getContent();
        JSONObject object = JSON.parseObject(json);
        JSONArray array = object.getJSONArray("msg");
        for(int i=0;i<array.size();i++){
            System.out.println(array.get(i));
            String topicStr = array.getString(i);
        }
    }
}