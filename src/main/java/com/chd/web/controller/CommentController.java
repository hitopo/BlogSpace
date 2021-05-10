package com.chd.web.controller;

import com.chd.backstage.entity.Comment;
import com.chd.backstage.entity.Menu;
import com.chd.backstage.service.CommentService;
import com.chd.backstage.service.MenuService;
import com.chd.backstage.util.TimeUtil;
import com.chd.web.JsonDataResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.chd.web.constant.ForwardConstant.FWD_COMMENT_VIEW;
import static com.chd.web.constant.URIConstant.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
public class CommentController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    private CommentService commentService;
    @Autowired
    private MenuService menuService;

    /**
     * 插入评论
     * 使用ajax从前端获取到json数据封装到comment中
     * @return Json数据，返回给页面。逻辑控制由页面操作
     */
    @RequestMapping(value = {URL_COMMENT_INSERT}, method = {POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public JsonDataResponse insertComment(@RequestParam("authorName") String name, @RequestParam("content") String content,
                                          @RequestParam("articleId") String articleId) throws ParseException {
        LOGGER.info("enter insert commment controller");
        Comment comment = new Comment();
        //设置相关属性
        comment.setArticleId(Long.parseLong(articleId));
        comment.setContent(content);

        //设置时间格式
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sf.format(new Date());
        Date parse = sf.parse(format);
        comment.setDate(parse);

        comment.setName(name);
        LOGGER.info("Controller:insert commment,comment={}", comment);
        int i = commentService.insertComment(comment);
        if (i == 0) {
            //插入数据失败
            return new JsonDataResponse(false);
        }
        return new JsonDataResponse();
    }


    //  之后的函数是后台管理函数

    /**
     * 跳转评论页面
     */
    @RequestMapping(value = {URL_COMMENT_MANAGER})
    public ModelAndView commentManager(HttpServletRequest request) {
        LOGGER.info("enter commentManager page");
        Map<String, Object> map = Maps.newHashMap();
        Map<String, Object> params = Maps.newHashMap();
        List<Menu> menus = menuService.obtainMenus();
        String str_pageNo = request.getParameter("pageNo");
        if (str_pageNo == null) {
            PageHelper.startPage(1, 13);
        } else {
            PageHelper.startPage(Integer.parseInt(str_pageNo), 13);
        }
        List<Comment> comments = commentService.getAllComment(params);
        for (Comment comment : comments) {
            handleTime(comment);
        }
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        map.put("menus", menus);
        map.put("comments", comments);
        map.put("pageInfo", pageInfo);
        return new ModelAndView(FWD_COMMENT_VIEW, map);
    }

    @RequestMapping(value = {URL_COMMENT_DELETE})
    public ModelAndView deleteComment(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("delete comment request");
        Long commentId = Long.parseLong(request.getParameter("commentId"));
        Comment comment = commentService.getCommentById(commentId);
        commentService.deleteComment(comment);
        try {
            LOGGER.info("redirect to comment manager page");
            response.sendRedirect(URL_PROJECT + URL_WEB_ROOT + URL_COMMENT_MANAGER);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }

    /**
     * 将date时间转化成比较容易接受的时间
     * @param comment 评论主题
     */
    private void handleTime(Comment comment) {
        TimeUtil.handleTime(comment);
    }
}
