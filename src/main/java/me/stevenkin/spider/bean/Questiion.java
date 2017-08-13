package me.stevenkin.spider.bean;

import java.util.List;

/**
 * Created by wjg on 2017/8/13.
 */
public class Questiion {
    private String questionName;
    private String questionUrl;
    private List<TopicLink> topicLinks;
    private Integer followers;
    private Integer browseNum;
    private Integer commentNum;

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

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
}
