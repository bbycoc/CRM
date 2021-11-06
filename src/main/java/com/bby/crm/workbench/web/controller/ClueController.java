package com.bby.crm.workbench.web.controller;

import com.bby.crm.settings.domain.User;
import com.bby.crm.settings.service.UserService;
import com.bby.crm.settings.service.impl.UserServiceImpl;
import com.bby.crm.utils.DateTimeUtil;
import com.bby.crm.utils.PrintJson;
import com.bby.crm.utils.ServiceFactory;
import com.bby.crm.utils.UUIDUtil;
import com.bby.crm.workbench.domain.Activity;
import com.bby.crm.workbench.domain.Clue;
import com.bby.crm.workbench.domain.Tran;
import com.bby.crm.workbench.service.ActivityService;
import com.bby.crm.workbench.service.ClueService;
import com.bby.crm.workbench.service.impl.ActivityServiceImpl;
import com.bby.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("用户进入线索控制器");

        String path=request.getServletPath();

        if("/workbench/clue/getUserList.do".equals(path)){
            getUser(request,response);
        }else if("/workbench/clue/saveClue.do".equals(path)){
            saveClue(request,response);
        }else if ("/workbench/clue/getClueList.do".equals(path)){
            getClueList(request,response);
        }else if ("/workbench/clue/detail.do".equals(path)){
            getClueDetail(request,response);
        }else if ("/workbench/clue/getActivityByName.do".equals(path)){
            getActivityByname(request,response);
        }else if ("/workbench/clue/convert.do".equals(path)){
            addTrad(request,response);
        }else if ("/workbench/clue/getActivityList.do".equals(path)){
            getActivityList(request,response);
        }else if ("/workbench/clue/delActivityList.do".equals(path)){
            delActivityList(request,response);
        }else if ("/workbench/clue/searchActivityByName.do".equals(path)){
            searchActivityByName(request,response);
        }else if ("/workbench/clue/bind.do".equals(path)){
            bind(request,response);
        }

    }

    private void bind(HttpServletRequest request, HttpServletResponse response) {
        String cid=request.getParameter("cid");
        String aids[] =request.getParameterValues("aid");
        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag=clueService.bind(cid,aids);
        PrintJson.printJsonFlag(response,flag);
    }

    private void searchActivityByName(HttpServletRequest request, HttpServletResponse response) {
        String name=request.getParameter("name");
        String clueId=request.getParameter("clueId");
        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        List<Activity> activityList=clueService.searchActivityByName(name,clueId);
        PrintJson.printJsonObj(response,activityList);
    }

    private void delActivityList(HttpServletRequest request, HttpServletResponse response) {
        String activityId=request.getParameter("activityId");
        String clueId=request.getParameter("clueId");
        boolean flag=true;
        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        int count=clueService.delActivity(activityId,clueId);
        if (count==-1){
            flag=false;
        }
        PrintJson.printJsonFlag(response,flag);
    }

    private void getActivityList(HttpServletRequest request, HttpServletResponse response) {
        String clueId=request.getParameter("clueId");
        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        List<Activity> activityList=clueService.getActivityList(clueId);
        PrintJson.printJsonObj(response,activityList);
    }

    private void addTrad(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String flag=request.getParameter("flag");
        String clueId=request.getParameter("clueId");
        String createBy=((User)request.getSession().getAttribute("user")).getName();

        Tran t=null;
        if ("a".equals(flag)){
            t=new Tran();
            String name=request.getParameter("name");
            String money=request.getParameter("money");
            String expectedDate=request.getParameter("expectedDate");
            String stage=request.getParameter("stage");
            String activityId=request.getParameter("activityId");
            String id=UUIDUtil.getUUID();
            String createTime=DateTimeUtil.getSysTime();

            t.setActivityId(activityId);
            t.setName(name);
            t.setMoney(money);
            t.setCreateBy(createBy);
            t.setExpectedDate(expectedDate);
            t.setStage(stage);
            t.setId(id);
            t.setCreateTime(createTime);
            t.setCreateBy(createBy);
        }
        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag1=clueService.convert(clueId,t,createBy);
        if (flag1){
            response.sendRedirect(request.getContextPath()+"/workbench/clue/index.jsp");
        }
    }

    private void getActivityByname(HttpServletRequest request, HttpServletResponse response) {
        String name=request.getParameter("Name");
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> alist=activityService.getActivityByName(name);
        PrintJson.printJsonObj(response,alist);
    }

    private void getClueDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id=request.getParameter("id");
        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue clue=clueService.getDetail(id);
        request.getSession().setAttribute("c",clue);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) {
        UserService userService= (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList=userService.getUserList();
        PrintJson.printJsonObj(response,userList);
    }
    private void saveClue(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        String id= UUIDUtil.getUUID();
        String createTime= DateTimeUtil.getSysTime();
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        String fullname= request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner= request.getParameter("owner");
        String company= request.getParameter("company");
        String job= request.getParameter("job");
        String email= request.getParameter("email");
        String phone= request.getParameter("phone");
        String website= request.getParameter("website");
        String mphone= request.getParameter("mphone");
        String state= request.getParameter("state");
        String source= request.getParameter("source");
        String description = request.getParameter("description");
        String contactSummary= request.getParameter("contactSummary");
        String nextContactTime= request.getParameter("nextContactTime");
        String address= request.getParameter("address");

        Clue c= new Clue();
        c.setId(id);
        c.setAddress(address);
        c.setWebsite(website);c.setState(state) ;
        c.setSource(source);c.setPhone(phone);c.setOwner(owner);
        c.setNextContactTime(nextContactTime);c.setMphone(mphone);
        c.setJob(job);
        c.setFullname(fullname);c.setEmail(email);
        c.setDescription(description) ;c.setCreateTime( createTime) ;c.setCreateBy(createBy);
        c.setContactSummary( contactSummary );c.setCompany( company) ;
        c.setAppellation(appellation);

        boolean flag=clueService.saveClue(c);
        PrintJson.printJsonFlag(response,flag);
    }
    private void getClueList(HttpServletRequest request, HttpServletResponse response) {
        String pageNoStr=request.getParameter("pageNo");
        String pageSizeStr =request.getParameter("pageSize");
        String fullname=request.getParameter("fullname");
        String company=request.getParameter("company");
        String phone=request.getParameter("phone");
        String source=request.getParameter("source");
        String owner=request.getParameter("owner");
        String mphone=request.getParameter("mphone");
        String state=request.getParameter("state");
        Map map1=new HashMap();
        map1.put("fullname",fullname);
        map1.put("company",company);
        map1.put("phone",phone);
        map1.put("source",source);
        map1.put("owner",owner);
        map1.put("mphone",mphone);
        map1.put("state",state);

        int pageNo=Integer.valueOf(pageNoStr);
        int pageSize=Integer.valueOf(pageSizeStr);
        int skipCount=(pageNo-1)*pageSize;

        map1.put("pageNo",pageNo);
        map1.put("pageSize",pageSize);
        map1.put("skipCount",skipCount);

        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        int total=clueService.getTotal(map1);
        ClueService clueService2= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        List<Clue> cluelist=clueService2.getClueList(map1);

        Map map2=new HashMap();
        map2.put("total",total);
        map2.put("cluelist",cluelist);

        PrintJson.printJsonObj(response,map2);
    }
}
