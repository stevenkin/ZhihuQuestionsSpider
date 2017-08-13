package me.stevenkin.spider.bean;

/**
 * Created by wjg on 2017/8/13.
 */
public class TopicLink {
    private String topicName;
    private String topicUrl;

    public TopicLink(String topicName, String topicUrl) {
        this.topicName = topicName;
        this.topicUrl = topicUrl;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicUrl() {
        return topicUrl;
    }

    public void setTopicUrl(String topicUrl) {
        this.topicUrl = topicUrl;
    }
}
