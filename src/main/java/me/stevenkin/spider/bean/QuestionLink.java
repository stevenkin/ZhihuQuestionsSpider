package me.stevenkin.spider.bean;

/**
 * Created by wjg on 2017/8/10.
 */
public class QuestionLink {
    private String link;
    private String dataScore;

    public QuestionLink(String link, String dataScore) {
        this.link = link;
        this.dataScore = dataScore;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDataScore() {
        return dataScore;
    }

    public void setDataScore(String dataScore) {
        this.dataScore = dataScore;
    }
}
