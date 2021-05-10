package com.chd.backstage.dao;


import com.chd.backstage.entity.Menu;

import java.util.List;

public interface MenuDao {
    /**
     * 获取所有可用的菜单
     * @return 所有可用菜单
     */
    List<Menu> obtainMenus();
}
