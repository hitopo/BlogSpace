package com.chd.backstage.dao.impl;

import com.chd.backstage.dao.MenuDao;
import com.chd.backstage.entity.Menu;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MenuDao")
public class MenuDaoImpl extends SqlSessionDaoSupport implements MenuDao {
    @Autowired
    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public List<Menu> obtainMenus() {
        String statement = "obtainMenus";
        return this.getSqlSession().selectList(statement);
    }
}
