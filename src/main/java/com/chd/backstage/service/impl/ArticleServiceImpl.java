package com.chd.backstage.service.impl;


import com.chd.backstage.dao.ArticleDao;
import com.chd.backstage.dao.CommentDao;
import com.chd.backstage.entity.Article;
import com.chd.backstage.entity.Tag;
import com.chd.backstage.service.ArticleService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


@Service("ArticleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CommentDao commentDao;

    @Override
    public int insertArticle(Article article) {
        LOGGER.info("Service:insertArticle,articleTitle={}", article.getTitle());
        return articleDao.insertArticle(article);
    }

    @Override
    public int insertArticle(Article article, HttpServletRequest request) {
        LOGGER.info("Service:insertArticle,articleTitle={}", article.getTitle());
        //插入文章
        int i = insertArticle(article);
        if (i == 0) {
            LOGGER.info("插入文章失败");
            return i;
        }
        List<Tag> tags = handleTag(request, article.getId());
        int j = insertArticleTag(tags);
        if (j == 0) {
            LOGGER.info("插入标签失败");
        }
        return 1;
    }

    @Override
    public int deleteArticle(Article article) {
        LOGGER.info("Service:deleteArticle,articleTitle={}", article.getTitle());
        Long articleId = article.getId();
        //删除文章标签
        deleteTagByArticleId(articleId);
        //删除文章评论
        commentDao.deleteCommentByArticleId(articleId);
        return articleDao.deleteArticle(article);
    }

    @Override
    public int updateArticle(Article article) {
        LOGGER.info("Service:updateArticle,articleTitle={}", article.getTitle());
        return articleDao.updateArticle(article);
    }

    @Override
    public Article getArticleById(Long id) {
        LOGGER.info("Service:getArticleById,id={}", id);
        return articleDao.getArticleById(id);
    }

    @Override
    public List<Article> getAllArticle(Map<String, Object> map) {
        LOGGER.info("Service:getAllArticle,map={}", map);
        return articleDao.getAllArticle(map);
    }

    @Override
    public List<Article> getArticleByClick(Integer limit) {
        LOGGER.info("Service:getArticleByClick,limit={}", limit);
        return articleDao.getArticleByClick(limit);
    }

    @Override
    public List<Article> getArticleByCondition(Map<String, Object> map) {
        LOGGER.info("Service:getArticleByCondition,map={}", map);
        return articleDao.getArticleByCondition(map);
    }

    @Override
    public Article getPreviousArticle(Long currentArticleId) {
        LOGGER.info("Service:getPreviousArticle,currentArticleId={}", currentArticleId);
        return articleDao.getPreviousArticle(currentArticleId);
    }

    @Override
    public Article getNextArticle(Long currentArticleId) {
        LOGGER.info("Service:getNextArticle,currentArticleId={}", currentArticleId);
        return articleDao.getNextArticle(currentArticleId);
    }

    @Override
    public int insertArticleTag(List<Tag> tags) {
        LOGGER.info("Service:insertArticleTag,tags size={}", tags.size());
        return articleDao.insertArticleTag(tags);
    }

    @Override
    public List<Tag> getTagsByArticleId(Long articleId) {
        LOGGER.info("Service:getTagsByArticleId,articleId={}", articleId);
        return articleDao.getTagsByArticleId(articleId);
    }

    @Override
    public List<Tag> getSimilarTag(Map<String, Object> tagNames) {
        LOGGER.info("Service:getSimilarTag,tag size={}", tagNames.size());
        return articleDao.getSimilarTag(tagNames);
    }

    @Override
    public List<Long> getRecommandArticleId() {
        LOGGER.info("Service:getRecommandArticleId");
        return articleDao.getRecommandArticleId();
    }

    @Override
    public List<Article> getRecommandArtice(List<Long> articleIds) {
        LOGGER.info("Service:getRecommandArtice,articleIds size = {}", articleIds.size());
        return articleDao.getRecommandArtice(articleIds);
    }

    @Override
    public int deleteTagByArticleId(Long articleId) {
        LOGGER.info("Service:deleteTagByArticleId, articleId = {}", articleId);
        return articleDao.deleteTagByArticleId(articleId);
    }

    /**
     * 为新文章插入标签
     * @param currentArticleId 当前文章的ID
     * @return 处理的多个标签的List集合
     */
    private List<Tag> handleTag(HttpServletRequest request, Long currentArticleId) {
        List<Tag> tags = Lists.newArrayList();
        for (int i = 1; i <= 4; i++) {
            String name = request.getParameter("tag" + i);
            tags.add(new Tag(name, currentArticleId));
        }
        return tags;
    }
}