package me.stevenkin.spider;

import me.stevenkin.spider.bean.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * Created by wjg on 2017/4/20.
 */
public class Storer {
    public void store(Result result){
        Pattern pattern1 = Pattern.compile("https://www.zhihu.com/question/\\d+");
        if(pattern1.matcher(result.getPage().getRequest().getUrl()).matches()){
            Question question = (Question) result.getData("data");
            try {
                Connection connection = DBUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement("insert into question(question_name,question_url,followers,browseNum) values (?,?,?,?)");
                statement.setString(1,question.getQuestionName());
                statement.setString(2,question.getQuestionUrl());
                statement.setInt(3,question.getFollowers());
                statement.setInt(4,question.getBrowseNum());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
