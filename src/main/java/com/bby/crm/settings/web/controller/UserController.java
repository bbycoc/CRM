package com.bby.crm.settings.web.controller;

import com.bby.crm.settings.domain.User;
import com.bby.crm.settings.service.UserService;
import com.bby.crm.settings.service.impl.UserServiceImpl;
import com.bby.crm.utils.MD5Util;
import com.bby.crm.utils.PrintJson;
import com.bby.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("用户进入控制器");

        String path=request.getServletPath();
        if("/settings/user/login.do".equals(path)){

            login(request,response);
        }

    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        String Usename=request.getParameter("usename");

        String Password=request.getParameter("password");

        //将明文转为MD5
        Password= MD5Util.getMD5(Password);

        //获取用户ip地址
        String ip=request.getRemoteAddr();

        //业务层用代理对象
        UserService us= (UserService) ServiceFactory.getService(new UserServiceImpl());
        try {
            User user=us.login(Usename,Password,ip);
            request.getSession().setAttribute("user",user);
            PrintJson.printJsonFlag(response,true);
            //登录失败所做操作
        }catch (Exception e){
            e.printStackTrace();
            String msg=e.getMessage();

            Map<String,Object> map=new HashMap<String, Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
            }

        }
    }

