package com.bby.crm.workbench.web.controller;

import com.bby.crm.settings.domain.User;
import com.bby.crm.settings.service.UserService;
import com.bby.crm.settings.service.impl.UserServiceImpl;
import com.bby.crm.utils.*;
import com.bby.crm.vo.PaginationVO;
import com.bby.crm.workbench.domain.Activity;
import com.bby.crm.workbench.domain.ActivityRemark;
import com.bby.crm.workbench.service.ActivityService;
import com.bby.crm.workbench.service.impl.ActivityServiceImpl;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("用户进入控制器");

        String path=request.getServletPath();

        if("/workbench/activity/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if("/workbench/activity/save.do".equals(path)){
            save(request,response);
        }else if("/workbench/activity/pageList.do".equals(path)){
            pagelist(request,response);
        }else if ("/workbench/activity/delete.do".equals(path)){
            delete(request,response);
        }else if ("/workbench/activity/editSelect.do".equals(path)){
            editSelect(request,response);
        }else if ("/workbench/activity/update.do".equals(path)){
            update(request,response);
        }else if ("/workbench/activity/detail.do".equals(path)){
            detail(request,response);
        }else if ("/workbench/activity/getRAbyId.do".equals(path)){
            getRAbyId(request,response);
        }else if ("/workbench/activity/deleteRemark.do".equals(path)){
            deleteRemark(request,response);
        }else if ("/workbench/activity/saveRemark.do".equals(path)){
            saveRemark(request,response);
        }else if ("/workbench/activity/updateRemark.do".equals(path)){
            updateRemark(request,response);
        }

    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        String id=request.getParameter("id");
        String noteContent=request.getParameter("noteContent");
        String  editTime= DateTimeUtil.getSysTime();
        String  editBy=((User)request.getSession().getAttribute("user")).getName();
        String  editFlag="1";

        ActivityRemark remark=new ActivityRemark();
        remark.setEditBy(editBy);
        remark.setEditTime(editTime);
        remark.setEditFlag(editFlag);
        remark.setNoteContent(noteContent);
        remark.setId(id);

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.updateRemark(remark);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("success",flag);
        map.put("ra",remark);
        PrintJson.printJsonObj(response,map);
    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        String id=UUIDUtil.getUUID();
        String noteContent=request.getParameter("noteContent");
        String createTime=DateTimeUtil.getSysTime();
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        String editFlag="0";
        String activityId=request.getParameter("id");

        ActivityRemark remark=new ActivityRemark();
        remark.setActivityId(activityId);
        remark.setCreateBy(createBy);
        remark.setCreateTime(createTime);
        remark.setId(id);
        remark.setNoteContent(noteContent);
        remark.setEditFlag(editFlag);

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.saveRemark(remark);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("success",flag);
        map.put("ra",remark);
        PrintJson.printJsonObj(response,map);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("删除备注信息");
        String id=request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.deleteRemark(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getRAbyId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入刷新备注信息页");
        String id=request.getParameter("activityId");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> remarkList=as.getRAbyId(id);
        PrintJson.printJsonObj(response,remarkList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入市场活动详细信息页");
        String id=request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity activity=as.detail(id);
        request.setAttribute("a",activity);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入市场活动修改操作");
        String  id= request.getParameter("id");
        String  owner=request.getParameter("owner");
        String  name=request.getParameter("name");
        String  startDate=request.getParameter("startDate");
        String  endDate =request.getParameter("endDate");
        String  cost=request.getParameter("cost");
        String  description=request.getParameter("description");
        String  editTime= DateTimeUtil.getSysTime();
        String  editBy=request.getParameter("owner");

        Activity activity=new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.update(activity);
        PrintJson.printJsonFlag(response,flag);
    }

    private void editSelect(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入查询市场活动和ulist操作");
        String id=request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String,Object> map=new HashMap<String, Object>();
        map=as.editSelect(id);
        PrintJson.printJsonObj(response,map);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行删除市场活动列表操作");
        String ids[]=request.getParameterValues("id");

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.delele(ids);
        PrintJson.printJsonFlag(response,flag);
    }

    private void pagelist(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入市场活动列表查询操作");

       String pageNoStr=request.getParameter("pageNo");
       String pageSizeStr=request.getParameter("pageSize");
       String name=request.getParameter("name");
       String owner=request.getParameter("owner");
       String startDate=request.getParameter("startDate");
       String endDate=request.getParameter("endDate");
       int pageNo=Integer.valueOf(pageNoStr);
       int pageSize=Integer.valueOf(pageSizeStr);
       int skipCount=(pageNo-1)*pageSize;

       Map<String,Object> map=new HashMap<String,Object>();
       map.put("name",name);
       map.put("owner",owner);
       map.put("startDate",startDate);
       map.put("endDate",endDate);
       map.put("skipCount",skipCount);
       map.put("pageSize",pageSize);

       ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
       PaginationVO<Activity> vo= as.pageList(map);
       PrintJson.printJsonObj(response,vo);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进行市场活动添加操作");

        String  id= UUIDUtil.getUUID();
        String  owner=request.getParameter("owner");
        String  name=request.getParameter("name");
        String  startDate=request.getParameter("startDate");
        String  endDate =request.getParameter("endDate");
        String  cost=request.getParameter("cost");
        String  description=request.getParameter("description");
        String  createTime= DateTimeUtil.getSysTime();
        String  createBy=((User)request.getSession().getAttribute("user")).getName();

        Activity activity=new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.save(activity);
        PrintJson.printJsonFlag(response,flag);

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户信息");

        UserService us= (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> userList=us.getUserList();

        PrintJson.printJsonObj(response,userList);
    }
}
