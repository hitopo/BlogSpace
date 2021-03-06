package com.chd.web.controller;

import com.chd.backstage.entity.*;
import com.chd.backstage.service.ArticleService;
import com.chd.backstage.service.CategoryService;
import com.chd.backstage.service.CommentService;
import com.chd.backstage.service.MenuService;
import com.chd.backstage.util.TimeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.chd.web.constant.ForwardConstant.*;
import static com.chd.web.constant.URIConstant.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * article控制器
 */
@Controller
public class ArticleController extends AbstractController {
    private static Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);
    // 获取service以实现控制功能
    // 这些service交由spring管理使用
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private MenuService menuService;

    /**
     * 去文章详情页面
     * 根据URL路径中指定的文章ID号，去获取指定文章的内容
     * @param articleId 指定的文章的ID号
     * @return 获取此文章的数据，并去文章详情页面
     */
    @RequestMapping(value = {URL_ARTICLE_READ})
    public ModelAndView readArticle(@PathVariable("articleId") Long articleId) {
        LOGGER.info("enter article detail page, articleId = {}", articleId);
        Article article = articleService.getArticleById(articleId);
        //点击量增加1
        Long click = article.getClick();
        ++click;
        article.setClick(click);
        int i = articleService.updateArticle(article);
        //打印日志
        if (i == 1) {
            LOGGER.info("点击量刷新，现在的点击量是={}", click);
        }
        //获取前一篇文章和后一篇文章
        Article previous = articleService.getPreviousArticle(articleId);
        Article next = articleService.getNextArticle(articleId);
        //获取这篇文章的所有评论
        List<Comment> comments = commentService.getCommentsByArticle(article);
        for (Comment comment : comments) {
            TimeUtil.handleTime(comment);
        }
        //获取点击量最多的几篇文章
        //热门文章
        List<Article> hotArticles = articleService.getArticleByClick(5);
        //获取文章标签
        List<Tag> tags = articleService.getTagsByArticleId(articleId);
        //根据文章的标签获取相似文章
        int size = tags.size();
        Map<String, Object> tagNames = Maps.newHashMap();
        for (int j = 1; j <= size; j++) {
            tagNames.put("tag" + j, tags.get(j - 1).getName());
        }
        //先获取到相似的标签
        List<Tag> tagList = articleService.getSimilarTag(tagNames);
        //根据相似的标签的ID去获取此相似的文章
        List<Article> articles = Lists.newArrayList();
        //获取推荐的文章的id
        List<Long> articleIds = articleService.getRecommandArticleId();
        //获取推荐的文章
        List<Article> recommandArticles = articleService.getRecommandArtice(articleIds);
        for (Tag tag : tagList) {
            if (!tag.getArticleId().equals(articleId)) {
                Article newArticle = articleService.getArticleById(tag.getArticleId());
                articles.add(newArticle);
            }
        }
        LOGGER.info("similar article size = {}", articles.size());
        //处理时间字符串
        handleTime(article);
        //封装页面需要的数据
        Map<String, Object> map = Maps.newHashMap();
        map.put("article", article);
        map.put("comments", comments);
        map.put("previous", previous);
        map.put("next", next);
        map.put("hotArticles", hotArticles);
        map.put("tags", tags);
        map.put("articles", articles);
        map.put("recommandArticles", recommandArticles);
        return new ModelAndView(FWD_ARTICLE_DETAIL, map);
    }

    /**
     * 展示所有文章，这个是博客空间的主页
     */
    @RequestMapping(value = {URL_ARTICLE_LIST}, method = {GET, POST})
    public ModelAndView home(HttpServletRequest request) {
        LOGGER.info("enter article list page");
        //获取点击量最高的5篇文章
        //推荐给用户
        List<Article> hotArticles = articleService.getArticleByClick(5);
        LOGGER.info("page query for article");
        //分页显示信息
        //拿到用户需要的是哪一页的文章数据
        String str_pageNo = request.getParameter("pageNo");
        // 第一次进入显示首页，此时没有pageNo属性
        if (str_pageNo == null) {
            // 从第一页显示，总共显示8个数据
            PageHelper.startPage(1, 8);
        } else {
            // 否则显示页数对应的页面
            PageHelper.startPage(Integer.parseInt(str_pageNo), 8);
        }
        //封装数据的map集合
        Map<String, Object> map = Maps.newHashMap();
        // 判断是查询还是显示所有文章
        String searchContent = request.getParameter("searchContent");
        if (searchContent != null) {
            map.put("title", searchContent);
        }
        List<Article> articles = articleService.getAllArticle(map);
        // 分页显示
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        LOGGER.info("actually total size = {}", pageInfo.getTotal());
        //处理时间格式
        LOGGER.info("get Comment size for every Article");
        for (Article article : articles) {
            //获取每篇文章对应的评论
            List<Comment> comments = commentService.getCommentsByArticle(article);
            article.setComments(comments);
            //处理时间
            handleTime(article);
        }
        //获取推荐的文章的id，推荐的文章是评论量最高的文章
        List<Long> articleIds = articleService.getRecommandArticleId();
        //获取推荐的文章
        List<Article> recommandArticles = articleService.getRecommandArtice(articleIds);

        // 将页面所需要的数据封装在map中
        map.put("articles", articles);
        map.put("hotArticles", hotArticles);
        map.put("pageInfo", pageInfo);
        map.put("recommandArticles", recommandArticles);
        //为JSP页面提供用于渲染的数据
        return new ModelAndView(FWD_ARTICLE_LIST_HOME, map);
    }


    /**
     * 去写文章页面
     */
    @RequestMapping(value = {URL_ARTICLE_POSTEDIT_REQUEST})
    public ModelAndView readArticle() {
        LOGGER.info("enter article edit page");
        Map<String, Object> map = Maps.newHashMap();
        List<Category> categories = categoryService.getAllCategory();
        map.put("categories", categories);
        return new ModelAndView(FWD_ARTICLE_POSTEDIT, map);
    }

    /**
     * 写文章，从前台的富文本编辑器上获取数据
     */
    @RequestMapping(value = {URL_ARTICLE_INSERT}, method = {POST})
    public ModelAndView insertArticle(HttpServletRequest request, Article article) {
        LOGGER.info("insert Article,articleTitle={}", article.getTitle());
        //新文章编写日期
        article.setPublishDate(new Date());
        //新文章点击量设置为0
        article.setClick(0L);
        //获取当前登录的用户，也就是新文章的作者是谁
        //这里可能会因为session过期而出现异常，即停留在页面太长时间不动
        Account account = getCurrentAccount(request.getSession());
        article.setAuthor(account.getNickname());
        if (article.getContent() == null) {
            LOGGER.info("文章无任何内容");
            //直接返回失败页面
            return new ModelAndView(FWD_FAIL_PAGE);
        }
        // 插入文章进数据库
        int i = articleService.insertArticle(article, request);
        if (i == 0) {
            LOGGER.info("插入文章失败");
            // 失败页面
            return new ModelAndView(FWD_FAIL_PAGE);
        }
        LOGGER.info("current articleId={}", article.getId());
        return new ModelAndView(FWD_SUCCESS_PAGE);
    }

    /**
     * 去个人中心
     * 携带的参数：用户的文章
     */
    @RequestMapping(value = {URL_ARTICLE_MY_BLOG_SPACE})
    public ModelAndView myBlogSpace(HttpSession session) {
        LOGGER.info("enter my blog space page");
        Map<String, Object> map = Maps.newHashMap();
        // 查询参数map
        Map<String, Object> params = Maps.newHashMap();
        //获取当前用户
        Account account = getCurrentAccount(session);
        //当前用户的昵称
        String authorName = account.getNickname();
        //获取此作者的所有文章
        params.put("author", authorName);
        List<Article> articles = articleService.getAllArticle(params);
        //获取阅读排行的文章,根据点击量click降序DESC获取文章
        params.put("condition", "click");
        params.put("sort", "DESC");
        //获取最为火爆的文章
        List<Article> hotArticles = articleService.getArticleByCondition(params);
        //获取此作者的所有评论
        params.put("name", authorName);
        List<Comment> comments = commentService.getAllComment(params);
        //获取当前用户的评论对应的文章
        List<Model> models = Lists.newArrayList();
        for (Comment comment : comments) {
            Long articleId = comment.getArticleId();
            Article article = articleService.getArticleById(articleId);
            //传递参数到前端页面
            models.add(new Model(comment.getContent(), article.getTitle(), articleId));
        }
        map.put("models", models);
        map.put("articles", articles);
        map.put("comments", comments);
        map.put("hotArticles", hotArticles);
        LOGGER.info("author={}", account.getNickname());
        LOGGER.info("article size={}", articles.size());
        LOGGER.info("comments size={}", comments.size());
        return new ModelAndView(FWD_ARTICLE_BLOG_SPACE, map);
    }


    // 之后的函数是后台文章控制函数

    /**
     * 去文章管理页面
     * 携带的参数有:menus,articles
     * 需要进行分页
     * @return 携带上菜单数据，文章数据，去文章管理页面
     */
    @RequestMapping(value = {URL_ARTICLE_MANAGER})
    public ModelAndView articleManager(HttpServletRequest request) {
        LOGGER.info("enter articleManager page");
        Map<String, Object> map = Maps.newHashMap();
        Map<String, Object> params = Maps.newHashMap();
        List<Menu> menus = menuService.obtainMenus();
        String str_pageNo = request.getParameter("pageNo");
        if (str_pageNo == null) {
            PageHelper.startPage(1, 4);
        } else {
            PageHelper.startPage(Integer.parseInt(str_pageNo), 4);
        }
        List<Article> articles = articleService.getAllArticle(params);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        for (Article article : articles) {
            //处理时间
            handleTime(article);
        }

        //组装携带数据的map
        map.put("articles", articles);
        map.put("menus", menus);
        map.put("pageInfo", pageInfo);
        return new ModelAndView(FWD_ARTICLE_MANAGER, map);
    }

    /**
     * 展示搜索结果
     * 然后去一个与当前页面一模一样的展示数据的页面
     * @param conditionValue 参数是什么，作者还是文章ID？
     * @param searchValue    如果是作者，作者的值是多少。如果是ID,那么id的值又是多少
     * @return 携带上处理结果，去search结果页面
     */
    @RequestMapping(value = {URL_ARTICLE_SEARCH}, method = {POST})
    public ModelAndView articleSearch(@RequestParam("conditionValue") String conditionValue
            , @RequestParam("searchValue") String searchValue) {
        LOGGER.info("conditionValue={}", conditionValue);
        LOGGER.info("searchValue：{}", searchValue);
        Map<String, Object> map = Maps.newHashMap();
        Map<String, Object> params = Maps.newHashMap();
        params.put(conditionValue, searchValue);
        List<Article> articles = articleService.getArticleByCondition(params);
        // int size = articles.size();
        for (Article article : articles) {
            //处理时间
            handleTime(article);
        }
        map.put("menus", menuService.obtainMenus());
        map.put("articles", articles);
        return new ModelAndView(FWD_ARTICLE_SEARCH, map);
    }

    /**
     * 删除文章
     * @param request  request对象，用于获取文章的articleId
     * @param response 响应对象，用于重定向
     * @return 如果成功，返回的还是当前页面。
     */
    @RequestMapping(value = {URL_ARTICLE_DELETE})
    public ModelAndView articleDelete(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("delete article");
        Long articleId = Long.parseLong(request.getParameter("articleId"));
        LOGGER.info("articleId={}", articleId);
        //删除这篇文章
        Article article = articleService.getArticleById(articleId);
        int i = articleService.deleteArticle(article);
        if (i == 0) {
            LOGGER.info("删除失败");
            return new ModelAndView(FWD_FAIL_PAGE);
        }
        String url = request.getHeader("referer");
        try {
            LOGGER.info("redirect");
            //判断是前段删除还是后端
            if (url.contains(FWD_ARTICLE_BLOG_SPACE)) {
                //前端删除跳转到个人主页
                response.sendRedirect(URL_PROJECT + URL_WEB_ROOT + URL_ARTICLE_MY_BLOG_SPACE);
            } else {
                //后端删除跳转到文章管理界面
                response.sendRedirect(URL_PROJECT + URL_WEB_ROOT + URL_ARTICLE_MANAGER);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView();

    }

    /**
     * 从后台发出的查看文章的请求
     * @return 跳转展示文章的页面
     */
    @RequestMapping(value = {URL_ARTICLE_VIEW})
    public ModelAndView articleView(HttpServletRequest request) {
        LOGGER.info("enter article view page");
        Long articleId = Long.parseLong(request.getParameter("articleId"));
        LOGGER.info("articleId={}", articleId);
        Article article = articleService.getArticleById(articleId);
        handleTime(article);
        Map<String, Object> map = Maps.newHashMap();
        List<Menu> menus = menuService.obtainMenus();
        map.put("article", article);
        map.put("menus", menus);
        return new ModelAndView(FWD_ARTICLE_VIEW, map);
    }

    /**
     * 处理Article实体类中的时间格式
     * 将Date类型转为通用的字符串类型
     * 用于在页面上展示时间
     * @param article 文章实体
     */
    private void handleTime(Article article) {
        TimeUtil.handleTime(article);
    }
}
