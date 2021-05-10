package com.chd.backstage.service;


import com.chd.backstage.entity.Menu;

import java.util.List;

public interface MenuService {
    /**
     * 获取所有的菜单
     * @return 从数据库查询到的所有菜单
     */
    List<Menu> obtainMenus();
}
