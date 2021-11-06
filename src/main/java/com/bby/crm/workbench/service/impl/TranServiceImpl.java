package com.bby.crm.workbench.service.impl;

import com.bby.crm.settings.dao.UserDao;
import com.bby.crm.settings.domain.User;
import com.bby.crm.utils.SqlSessionUtil;
import com.bby.crm.workbench.dao.TranDao;
import com.bby.crm.workbench.dao.TranHistoryDao;
import com.bby.crm.workbench.domain.Tran;
import com.bby.crm.workbench.service.TranService;

import java.util.List;
import java.util.Map;

public class TranServiceImpl implements TranService {
    private TranDao tranDao=SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao=SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private UserDao userDao=SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public List<Tran> getTranList(Map map) {
        return tranDao.getTranList(map);
    }

    public List<User> getUserList() {
        return userDao.getUserList();
    }
}
