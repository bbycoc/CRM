package com.bby.crm.settings.service.impl;

import com.bby.crm.exception.LoginException;
import com.bby.crm.settings.dao.UserDao;
import com.bby.crm.settings.domain.User;
import com.bby.crm.settings.service.UserService;
import com.bby.crm.utils.DateTimeUtil;
import com.bby.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    public User login(String usename, String password, String ip) throws LoginException {

        Map<String,Object> map=new HashMap<String, Object>();
        map.put("usename",usename);
        map.put("password",password);

        User user=userDao.login(map);

        if(user==null){

            throw new LoginException("账号密码错误");
        }

        String expireTime=user.getExpireTime();
        String currentTime= DateTimeUtil.getSysTime();

        if (expireTime.compareTo(currentTime)<0){

            throw new LoginException("账号已失效");
        }

        if("0".equals(user.getLockState())){

            throw new LoginException("账号已锁定");
        }

        System.out.println("---------ip:"+ip);
        if (!user.getAllowIps().contains(ip)){

            throw new LoginException("ip地址受限");
        }

        return user;
    }

    public List<User> getUserList() {
        List<User> userList=userDao.getUserList();
        return userList;
    }
}
