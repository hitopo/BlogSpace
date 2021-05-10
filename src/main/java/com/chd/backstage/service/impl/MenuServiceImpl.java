package com.chd.backstage.service.impl;

import com.chd.backstage.dao.MenuDao;
import com.chd.backstage.entity.Menu;
import com.chd.backstage.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MenuService")
public class MenuServiceImpl implements MenuService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);
    @Autowired
    private MenuDao menuDao;

    /**
     * 获取所有可用的菜单
     * @return 所有可用菜单
     */
    @Override
    public List<Menu> obtainMenus() {
        LOGGER.info("Service:obtainMenus,获取所有菜单");
        return menuDao.obtainMenus();
    }
}
