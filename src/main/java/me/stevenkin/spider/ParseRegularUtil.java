package me.stevenkin.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.stevenkin.spider.bean.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

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
                    .addParame("params",object.toJSONString()).addAttach("offset",Integer.valueOf(0)).addAttach("topic_id",Integer.parseInt(topicId));
            result.addRequest(request);
        }
    }

    public static void parseZhihuTopics1(Page page, Result result) {
        String json = page.getContent();
        JSONObject object = JSON.parseObject(json);
        JSONArray array = object.getJSONArray("msg");
        if(array.size()==0) {
            result.setSkip(true);
            return;
        }
        for (int i = 0; i < array.size(); i++) {
            String topicStr = array.getString(i);
            Document doc = Jsoup.parseBodyFragment(topicStr);
            Element a = doc.body().select("div.item").first().select("a[target]").first();
            String href = "https://www.zhihu.com" + a.attr("href")+"/newest";
            result.addRequest(new Request(href, HttpMethod.GET));
        }
        Request request = new Request("https://www.zhihu.com/node/TopicsPlazzaListV2", HttpMethod.POST);
        JSONObject object1 = new JSONObject();
        object1.put("topic_id", page.getRequest().getAddch("topic_id"));
        object1.put("offset", Integer.valueOf(((Integer) page.getRequest().getAddch("offset")) + 20));
        object1.put("hash_id", "22e50cd21ed9df7085ff76d62175e986");
        request.addParame("method", "next")
                .addParame("params", object1.toJSONString()).addAttach("offset", Integer.valueOf(((Integer) page.getRequest().getAddch("offset")) + 20)).addAttach("topic_id", page.getRequest().getAddch("topic_id"));
        result.addRequest(request);
    }

    public static void parseZhihuTopicNewest(Page page, Result result){
        String html = page.getContent();
        Document doc = Jsoup.parse(html);
        Elements divs = doc.body().select("div.feed-item.feed-item-hook.folding");
        List<QuestionLink> linkList = new ArrayList<>();
        for(Element element:divs){
            QuestionLink link = new QuestionLink("https://www.zhihu.com"+element.select("a.question_link").first().attr("href"),element.attr("data-score"));
            linkList.add(link);
        }
        for(QuestionLink link:linkList){
            result.addRequest(new Request(link.getLink(),null));
        }
        Request request = new Request(page.getRequest().getUrl(),HttpMethod.POST).addParame("start","0").addParame("offset",linkList.get(linkList.size()-1).getDataScore());
        result.addRequest(request);
    }

    public static void parseZhihuTopicNewest_post(Page page,Result result) {
        String json = page.getContent();
        JSONObject object = JSON.parseObject(json);
        if(object.getJSONArray("msg").getInteger(0)==0){
            result.setSkip(true);
            return ;
        }
        String content = object.getJSONArray("msg").getString(1);
        Document doc = Jsoup.parseBodyFragment(content);
        Elements divs = doc.body().select("div.feed-item.feed-item-hook.folding");
        List<QuestionLink> linkList = new ArrayList<>();
        for (Element element : divs) {
            QuestionLink link = new QuestionLink("https://www.zhihu.com" + element.select("a.question_link").first().attr("href"), element.attr("data-score"));
            linkList.add(link);
        }
        for (QuestionLink link : linkList) {
            result.addRequest(new Request(link.getLink(), null));
        }
        Request request = new Request(page.getRequest().getUrl(), HttpMethod.POST).addParame("start", "0").addParame("offset", linkList.get(linkList.size() - 1).getDataScore());
        result.addRequest(request);
    }

    public static void parseZhihuQuestion(Page page, Result result){
        String html  = page.getContent();
        Document doc = Jsoup.parse(html);
        Question question = new Question();
        question.setQuestionName(doc.body().select("h1.QuestionHeader-title").first().text());
        question.setQuestionUrl(page.getRequest().getUrl());
        question.setFollowers(Integer.parseInt(doc.body().select("div.NumberBoard.QuestionFollowStatus-counts").first().select("div.NumberBoard-value").first().text()));
        question.setBrowseNum(Integer.parseInt(doc.body().select("div.NumberBoard.QuestionFollowStatus-counts div.NumberBoard-item div.NumberBoard-value").get(1).text()));
        Elements elements = doc.body().select("div.Tag.QuestionTopic");
        for(Element element:elements){
            question.addTopicLink(new TopicLink(element.select("#null-toggle").first().text(),element.select("a.TopicLink").first().attr("href")));
        }
        result.putData("data",question);
    }
}