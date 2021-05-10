package com.chd.backstage.dao;


import com.chd.backstage.entity.Article;
import com.chd.backstage.entity.Tag;

import java.util.List;
import java.util.Map;

public interface ArticleDao {


    /**
     * 插入文章
     * @param article 文章参数
     * @return 是否成功
     */
    int insertArticle(Article article);

    /**
     * 删除文章
     * @param article 动态参数
     * @return 是否成功
     */
    int deleteArticle(Article article);

    /**
     * 更新文章
     * @param article 动态参数设置
     * @return 是否成功
     */
    int updateArticle(Article article);


    /**
     * 由id获取文章
     * @param id
     * @return 文章实体
     */
    Article getArticleById(Long id);


    /**
     * 获取所有文章
     * @param map 封装的查询条件
     * @return 文章列表
     */
    List<Article> getAllArticle(Map<String, Object> map);

    /**
     * 获取点击量最高的，数量为limit的文章
     * @param limit ָ限制
     * @return 文章列表
     */
    List<Article> getArticleByClick(Integer limit);

    /**
     * 获取前一篇文章
     * @param currentArticleId
     * @return 文章实体
     */
    Article getPreviousArticle(Long currentArticleId);

    /**
     * 获取后一篇文章
     * @param currentArticleId
     * @return 文章实体
     */
    Article getNextArticle(Long currentArticleId);

    /**
     * @param tags 插入标签
     * @return
     */
    int insertArticleTag(List<Tag> tags);

    /**
     * 获取文章标签
     * @param articleId id
     * @return 标签列表
     */
    List<Tag> getTagsByArticleId(Long articleId);

    /**
     * 获取类似标签
     * @param tagNames
     * @return 标签列表
     */
    List<Tag> getSimilarTag(Map<String, Object> tagNames);

    /**
     * 获取推荐的文章id
     * @return id
     */
    List<Long> getRecommandArticleId();

    /**
     * 获取推荐文章
     * @param articleIds ids
     * @return 文章列表
     */
    List<Article> getRecommandArtice(List<Long> articleIds);

    /**
     * 由条件获取文章列表
     * @param map 查询条件
     * @return 文章列表
     */
    List<Article> getArticleByCondition(Map<String, Object> map);

    /**
     * 删除id对应文章的标签
     * @param articleId
     * @return 是否成功
     */
    int deleteTagByArticleId(Long articleId);
}


















