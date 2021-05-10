package com.chd.backstage.util;

import com.chd.backstage.entity.Article;
import com.chd.backstage.entity.Comment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将时间转换成比较易于显示的信息
 */
public class TimeUtil {
    /**
     * 转换文章实体
     * @param article
     */
    public static void handleTime(Article article) {
        if (article == null) {
            return;
        }
        // 将时间转换成显示的字符串类型并给article属性重新赋值
        Date date = article.getPublishDate();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sf.format(date);
        article.setTime(time);

        //转换文章的同时将文章的评论也转换了
        if (article.getComments() != null) {
            for (Comment comment : article.getComments()) {
                handleTime(comment);
            }
        }
    }

    /**
     * 转换评论实体
     * @param comment
     */
    public static void handleTime(Comment comment) {
        if (comment == null) {
            return;
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sf.format(comment.getDate());
        comment.setTime(format);
    }

}
