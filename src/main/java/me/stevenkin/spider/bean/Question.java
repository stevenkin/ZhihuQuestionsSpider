package me.stevenkin.spider.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wjg on 2017/8/13.
 */
public class Question {
    private String questionName;
    private String questionUrl;
    private List<TopicLink> topicLinks = new ArrayList<>();
    private Integer followers;
    private Integer browseNum;

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionUrl() {
        return questionUrl;
    }

    public void setQuestionUrl(String questionUrl) {
        this.questionUrl = questionUrl;
    }

    public List<TopicLink> getTopicLinks() {
        return topicLinks;
    }

    public void setTopicLinks(List<TopicLink> topicLinks) {
        this.topicLinks = topicLinks;
    }

    public Question addTopicLink(TopicLink link){
        this.topicLinks.add(link);
        return this;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionName='" + questionName + '\'' +
                ", questionUrl='" + questionUrl + '\'' +
                ", topicLinks=" + topicLinks +
                ", followers=" + followers +
                ", browseNum=" + browseNum +
                '}';
    }
}
