package com.chd.backstage.entity;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.List;

/**
 * 文章实体，对应的是数据库中article表
 */
public class Article {
    private Long id;//主键id

    private String brief;//摘要

    private String title;//标题

    private String content;//内容

    private String author;//作者，与Account里面的nickname关联，不是loginName

    private Date publishDate;//发布日期

    private String time;//字符串类型的发布日期，为了将从数据库中的publishDate转为字符串。

    private Long click;//点击量

    private Long categoryId;//分类的category类的主键

    private List<Comment> comments;//这篇文章的评论

    public Article() {
    }

    public Article(String brief, String title, String content, String author, Date publishDate, Long categoryId) {
        this.brief = brief;
        this.title = title;
        this.content = content;
        this.author = author;
        this.publishDate = publishDate;
        // 新建的文件点击量为0
        click = 0L;
        this.categoryId = categoryId;
    }


    public Article(Long id, String brief, String title, String content, String author, Date publishDate, String time, Long click, Long categoryId, List<Comment> comments) {
        this.id = id;
        this.brief = brief;
        this.title = title;
        this.content = content;
        this.author = author;
        this.publishDate = publishDate;
        this.time = time;
        this.click = click;
        this.categoryId = categoryId;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getClick() {
        return click;
    }

    public void setClick(Long click) {
        this.click = click;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("brief", brief)
                .add("title", title)
                .add("content", content)
                .add("author", author)
                .add("publishDate", publishDate)
                .add("click", click)
                .add("categoryId", categoryId)
                .toString();
    }

}
