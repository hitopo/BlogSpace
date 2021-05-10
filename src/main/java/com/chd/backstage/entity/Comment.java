package com.chd.backstage.entity;

import com.google.common.base.MoreObjects;

import java.util.Date;

/**
 * 文章评论，对应的是数据库中comment表
 */
public class Comment {
    private Long id;//主键id

    private String content;//文章内容

    private String name;//评论者，对应Account里面的nickname

    private Date date;//评论日期

    private String time;//方便显示

    private Long articleId;//评论的文章的主键id号,设计为外键。主表为t_acticle表，表明是那篇文章的评论

    public Comment() {
    }

    public Comment(String content, String name, Date date, Long articleId) {
        this.content = content;
        this.name = name;
        this.date = date;
        this.articleId = articleId;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("content", content)
                .add("name", name)
                .add("date", date)
                .add("articleId", articleId)
                .toString();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
