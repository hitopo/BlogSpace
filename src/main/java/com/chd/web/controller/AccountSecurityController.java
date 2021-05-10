package com.chd.web.controller;

import com.chd.backstage.entity.Account;
import com.chd.backstage.entity.Menu;
import com.chd.backstage.service.MenuService;
import com.chd.backstage.service.SecurityService;
import com.chd.web.JsonDataResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.chd.web.constant.ForwardConstant.*;
import static com.chd.web.constant.URIConstant.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class AccountSecurityController {
    private static Logger LOGGER = LoggerFactory.getLogger(AccountSecurityController.class);
    @Autowired
    private SecurityService securityService;
    @Autowired
    private MenuService menuService;

    /**
     * 后台管理员登录
     * @param loginName 登录名
     * @param password  密码
     * @return 登录成功跳转后台首页，失败跳转登录失败页面
     * 后台只有管理员才能够登录
     */
    @RequestMapping(value = {URL_SECURITY_ADMIN_LOGIN}, method = {POST})
    public ModelAndView accountAuthenticate(HttpSession session, @RequestParam("loginName") String loginName, @RequestParam("password") String password) {
        LOGGER.info("Controller:accountAuthenticate,loginName={},password={}", loginName, password);
        if (!loginName.equals("admin")) {
            LOGGER.info("非管理员登录");
            return new ModelAndView(FWD_SECURITY_LOGIN_FAIL);
        }
        // 用户没有认证也返回错误
        Account account = securityService.accountAuthenticate(loginName, password);
        if (account.getId() == null) {
            LOGGER.info("后台登录失败");
            return new ModelAndView(FWD_SECURITY_LOGIN_FAIL);
        }
        Map<String, Object> map = Maps.newHashMap();
        List<Menu> menus = menuService.obtainMenus();
        map.put("account", account);
        map.put("menus", menus);
        session.setAttribute("account", account);
        LOGGER.info("enter system index page,with account={}", account);
        LOGGER.info("enter system index page,with menus={}", menus);
        return new ModelAndView(FWD_SECURITY_SYSTEM_INDEX, map);
    }

    /**
     * 跳转用户注册页面
     */
    @RequestMapping({URL_SECURITY_ACCOUNT_REGISTER_RQEUQEST})
    public ModelAndView fwdRegister() {
        LOGGER.info("enter Account Register page");
        return new ModelAndView(FWD_SECURITY_ACCOUNT_REGISTER);
    }

    /**
     * 跳转用户登录页面
     */
    @RequestMapping({URL_SECURITY_ACCOUNT_LOGIN_RQEUQEST})
    public ModelAndView fwdLogin() {
        LOGGER.info("enter Account Login page");
        return new ModelAndView(FWD_SECURITY_ACCOUNT_LOGIN);
    }

    /**
     * 用户前台登录数据提交，这里面用到了权限的检查
     * 返回的是分装好的json对象
     * @param loginName 登录名
     * @param password  密码
     * @return 登录成功跳转博客空间首页，失败弹窗提示
     */
    @RequestMapping(value = {URL_SECURITY_ACCOUNT_LOGIN}, method = {POST}
            , produces = "application/json; charset=utf-8")
    @ResponseBody
    //使用@RequestParam("loginName")解析前端发送的loginName变量的值
    public JsonDataResponse accountLogin(HttpSession session, @RequestParam("loginName") String loginName, @RequestParam("password") String password) {
        LOGGER.info("Controller:accountLogin,loginName={},password={}", loginName, password);
        Account account = securityService.accountAuthenticate(loginName, password);
        if (account.getId() == null) {
            LOGGER.info("用户登录失败");
            JsonDataResponse data = new JsonDataResponse(false);
            data.setReason("帐号错误或者密码错误");
            return data;
        }
        if (!account.isEnabled()) {
            LOGGER.info("帐号不可用");
            JsonDataResponse data = new JsonDataResponse(false);
            data.setReason("帐号已经被冻结，请联系管理员!");
            return data;
        }
        session.setAttribute("account", account);
        //最终返回一个JsonReponseVO对象，作为ajax异步请求的响应
        return new JsonDataResponse();
    }


    /**
     * 去用户管理页面
     * 携带的参数有:menus,accounts
     * accounts经过了基本的分页查询,每页显示X条数据
     * @return 携带上菜单数据，用户数据，去用户管理页面
     */
    @RequestMapping(value = {URL_SECURITY_ACCOUNT_MANAGER})
    public ModelAndView accountManager() {
        LOGGER.info("enter accountManager page");
        Map<String, Object> param = Maps.newHashMap();
        Map<String, Object> map = Maps.newHashMap();
        //菜单数据
        List<Menu> menus = menuService.obtainMenus();
        map.put("menus", menus);
        //账户数据,每页显示X条数据
        PageHelper.startPage(1, 4);
        List<Account> accounts = securityService.obtainAllAccount(param);
        PageInfo<Account> pageInfo = new PageInfo<>(accounts);
        map.put("accounts", accounts);
        map.put("pageInfo", pageInfo);
        LOGGER.info("enter account manager page");
        LOGGER.info("Account size = {}", accounts.size());
        LOGGER.info("Actually total account size ={}", pageInfo.getTotal());
        return new ModelAndView(FWD_SECURITY_ACCOUNT_MANAGER, map);
    }

    /**
     * 分页查询Account数据
     * 在URL路径上携带了应该显示第几页的参数。每页显示X条数据
     * @param page 第几页的数据
     * @return 跳转帐号管理页面
     */
    @RequestMapping(value = {URL_SECURITY_ACCOUNT_MANAGER_PAGE_QUERY})
    public ModelAndView pageQuery(@PathVariable("page") Integer page) {
        LOGGER.info("Controller:enter accountManager page");
        Map<String, Object> param = Maps.newHashMap();
        Map<String, Object> map = Maps.newHashMap();
        //菜单数据
        List<Menu> menus = menuService.obtainMenus();
        map.put("menus", menus);
        //账户数据
        PageHelper.startPage(page, 4);
        List<Account> accounts = securityService.obtainAllAccount(param);
        PageInfo<Account> pageInfo = new PageInfo<>(accounts);
        map.put("accounts", accounts);
        map.put("pageInfo", pageInfo);
        LOGGER.info("enter account manager page");
        LOGGER.info("Account size = {}", accounts.size());
        LOGGER.info("Actually total account size ={}", pageInfo.getTotal());
        return new ModelAndView(FWD_SECURITY_ACCOUNT_MANAGER, map);
    }

    /**
     * 跳转添加账户页面
     */
    @RequestMapping(value = {URL_SECURITY_ACCOUNT_ADD})
    public ModelAndView addAccount() {
        LOGGER.info("ready fwd addAccount Page !");
        Map<String, Object> map = Maps.newHashMap();
        //菜单数据
        List<Menu> menus = menuService.obtainMenus();
        map.put("menus", menus);
        return new ModelAndView(FWD_SECURITY_ACCOUNT_ADD, map);
    }

    /**
     * 跳转修改账户页面
     */
    @RequestMapping(value = {URL_SECURITY_ACCOUNT_UPDATE})
    public ModelAndView updateAccount(@PathVariable("accountId") Integer accountId) {
        LOGGER.info("ready fwd updateAccount Page !");
        Account account = securityService.getAccountById(accountId);
        Map<String, Object> map = Maps.newHashMap();
        //菜单数据
        List<Menu> menus = menuService.obtainMenus();
        map.put("menus", menus);
        map.put("account", account);
        return new ModelAndView(FWD_SECURITY_ACCOUNT_UPDATE, map);
    }

    /**
     * 从前端接收到ajax请求,插入account实体到数据库
     * @param account 来自前端，由JSON数据封装
     * @return 是否插入成功，返回Json格式的JsonDataResponse对象。
     */
    @RequestMapping(value = {URL_SECURITY_ACCOUNT_SYSTEM_REGISTER}, method = {POST})
    @ResponseBody
    public JsonDataResponse registerSubmit(Account account) {
        LOGGER.info("receive from system register Account,Account_Json={}", account);
        int i = securityService.insertAccount(account);
        if (i <= 0) {
            LOGGER.info("插入数据失败");
            JsonDataResponse data = new JsonDataResponse(false);
            if (i < 0) {
                data.setReason("用户昵称已存在");
            } else {
                data.setReason("用户名已存在");
            }
            return data;
        }
        /*
         * 目标是跳转到插入的最后一条记录的位置
         * 步骤还是要获取到那个页面的menus,accounts。
         * 先弄到所有的记录数，然后算出应该跳转第几页page
         * 把这个插入的数据在第几页返回给前台。
         * ajax的success如果获取到数据，那么就发送去这个页面的请求
         * 请求路径是  actions/accountManager/{page}
         * 现在规定每页4跳数据，如果要换的话，在本页面搜索4即可
         */
        Map<String, Object> param = Maps.newHashMap();
        Map<String, Object> map = Maps.newHashMap();
        //菜单数据
        List<Menu> menus = menuService.obtainMenus();
        map.put("menus", menus);
        //获取所有帐号，包括刚才才插入的
        List<Account> accounts = securityService.obtainAllAccount(param);
        int totalCount = accounts.size();
        int page;
        //算出本次插入的数据在第几页
        if (totalCount % 4 == 0) {
            page = totalCount / 4;
        } else {
            page = (totalCount / 4) + 1;
        }
        JsonDataResponse data = new JsonDataResponse();
        //将第几页封装到data中
        data.setReason(String.valueOf(page));
        return data;
    }

    /**
     * 检查用户名和用户昵称是否已经在数据库中存在
     * @return Json包装的返回信息
     * 返回json数据必须指定ResponseBody，否则返回的只能是jsp页面，自然是404
     */
    @RequestMapping(value = {URL_SECURITY_ACCOUNT_NAME_CHECK}, method = {POST})
    @ResponseBody
    public JsonDataResponse nameCheck(HttpServletRequest request) {
        String nickName = request.getParameter("nickname");
        String userName = request.getParameter("username");

        if (securityService.checkAccountExistByNickname(nickName)) {
            // 用户昵称重复
            return new JsonDataResponse(false, "用户昵称已存在");
        }
        if (securityService.checkAccountExistByLoginName(userName)) {
            //用户名重复
            return new JsonDataResponse(false, "用户名已存在");
        }
        //用户名和昵称都没有重复
        return new JsonDataResponse();
    }

    /**
     * 从前端接收到ajax请求,将更新后的account实体到数据库
     * @param account 来自前端，由JSON数据封装
     * @return 是否更新成功，返回Json格式的JsonDataResponse对象。
     */
    @RequestMapping(value = {URL_SECURITY_ACCOUNT_SYSTEM_UPDATE}, method = {POST})
    @ResponseBody
    public JsonDataResponse updatesubmit(Account account) {
        LOGGER.info("receive from system update Account,Account_Json={}", account);
        int i = securityService.updateAccount(account);
        if (i == 0) {
            LOGGER.info("更新数据失败");
            return new JsonDataResponse(false);
        }
        return new JsonDataResponse();
    }

    /**
     * 跳转重置账户密码页面
     */
    @RequestMapping(value = {URL_SECURITY_ACCOUNT_RESET_PASS})
    public ModelAndView resetPassword(@PathVariable("accountId") Integer accountId) {
        LOGGER.info("ready fwd resetPassword Page !");
        Account account = securityService.getAccountById(accountId);
        Map<String, Object> map = Maps.newHashMap();
        //菜单数据
        List<Menu> menus = menuService.obtainMenus();
        map.put("menus", menus);
        map.put("account", account);
        return new ModelAndView(FWD_SECURITY_ACCOUNT_RESET_PASS, map);
    }

    /**
     * 接收从页面的ajax请求，获取到用户输入的密码
     * @param password  用户输入的新密码
     * @param accountId 账户的ID
     * @return 封装着是否操作成功的JSON数据
     */
    @RequestMapping(value = {URL_SECURITY_ACCOUNT_SYSTEM_RESET_PASSWORD}, method = {POST})
    public @ResponseBody
    JsonDataResponse resetPasswordsubmit(@RequestParam("password") String password, @RequestParam("accountId") Integer accountId) {
        LOGGER.info("receive from system resetPassword_submit,password={},accountId={}", password, accountId);
        int i = securityService.resetPassword(accountId, password);
        if (i == 0) {
            return new JsonDataResponse(false, "重置密码失败！");
        }
        return new JsonDataResponse();
    }

    /**
     * 在博客空间里面发送修改密码的操作，需要输入旧的密码
     * @param loginName   登录名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param response
     * @return 是否修改成功锁跳转的页面
     */
    @RequestMapping(value = {URL_SECURITY_ACCOUNT_RESET_PASSWORD_BY_OLD_PASSWORD}, method = {POST})
    public ModelAndView resetPasswordByOldPassword(@RequestParam("loginName") String loginName, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword,
                                                   HttpServletResponse response) {
        LOGGER.info("resetPasswordByOldPassword,oldPassword={},newPassword={}", oldPassword, newPassword);

        int i = securityService.updatePassword(loginName, oldPassword, newPassword);
        if (i == 0) {
            // 修改密码失败，跳转到失败页面
            return new ModelAndView(FWD_FAIL_PAGE);
        }
        try {
            response.sendRedirect(URL_PROJECT + URL_WEB_ROOT + URL_ARTICLE_MY_BLOG_SPACE);
        } catch (Exception e) {
            LOGGER.info("转发出现一些问题");
        }
        return null;
    }

    /**
     * 根据指定的情况查询相应的用户账户
     * 从后台管理发送出来的请求
     */
    @RequestMapping(value = {URL_SECURITY_ACCOUNT_SEARCH}, method = {GET, POST})
    public ModelAndView accountSearch(@RequestParam("conditionValue") String conditionValue, @RequestParam("searchValue") String searchValue,
                                      @RequestParam("enabledValue") String enabledValue) {
        LOGGER.info("conditionValue={}", conditionValue);
        LOGGER.info("searchValue={}", searchValue);
        LOGGER.info("enabledValue={}", enabledValue);
        Map<String, Object> params = Maps.newHashMap();
        params.put("condition", conditionValue);
        params.put("searchValue", searchValue);
        params.put("enabled", enabledValue);
        Map<String, Object> map = Maps.newHashMap();
        //菜单数据
        List<Menu> menus = menuService.obtainMenus();
        //根据指定的情况搜索用户
        List<Account> accounts = securityService.getAccountByCondition(params);
        map.put("menus", menus);
        map.put("accounts", accounts);
        return new ModelAndView(FWD_SECURITY_ACCOUNT_SEARCH_RESULT, map);
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = {URL_SECURITY_ACCOUNT_DELETE})
    public ModelAndView deleteAccount(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("delete account");
        Integer accountId = Integer.parseInt(request.getParameter("accountId"));
        LOGGER.info("articleId={}", accountId);
        //删除这篇文章
        Account account = securityService.getAccountById(accountId);
        int i = securityService.deleteAccount(account);
        if (i == 0) {
            LOGGER.info("删除失败");
            return new ModelAndView(FWD_FAIL_PAGE);
        }
        try {
            LOGGER.info("redirect");
            response.sendRedirect(URL_PROJECT + URL_WEB_ROOT + URL_SECURITY_ACCOUNT_MANAGER);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }


}
