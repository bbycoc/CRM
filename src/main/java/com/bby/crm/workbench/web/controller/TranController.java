package com.bby.crm.workbench.web.controller;

import com.bby.crm.settings.domain.User;
import com.bby.crm.settings.service.UserService;
import com.bby.crm.utils.PrintJson;
import com.bby.crm.utils.ServiceFactory;
import com.bby.crm.workbench.domain.Customer;
import com.bby.crm.workbench.domain.Tran;
import com.bby.crm.workbench.service.CustomerService;
import com.bby.crm.workbench.service.TranService;
import com.bby.crm.workbench.service.impl.ClueServiceImpl;
import com.bby.crm.workbench.service.impl.CustomerServiceImpl;
import com.bby.crm.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranController extends HttpServlet{
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path=request.getServletPath();

        if ("/workbench/transaction/getTranList.do".equals(path)){
            getTranList(request,response);
        }else if ("/workbench/transaction/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if ("/workbench/transaction/getCustomerName.do".equals(path)){
            getCustomerName(request,response);
        }
    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        String name=request.getParameter("name");
        CustomerService customerService= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        List<Customer> clist=customerService.getCustomerName(name);
        PrintJson.printJsonObj(response,clist);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());
        List<User> uList=tranService.getUserList();
        request.getSession().setAttribute("uList",uList);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);
    }

    private void getTranList(HttpServletRequest request, HttpServletResponse response) {
        String owner=request.getParameter("owner");
        String name=request.getParameter("name");
        String cusName=request.getParameter("cusName");
        String stage=request.getParameter("stage");
        String type=request.getParameter("type");
        String source=request.getParameter("source");
        String conName=request.getParameter("conName");

        Map map=new HashMap();
        map.put("owner",owner);
        map.put("name",name);
        map.put("cusName",cusName);
        map.put("stage",stage);
        map.put("type",type);
        map.put("source",source);
        map.put("conName",conName);

        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());
        List<Tran> transList=tranService.getTranList(map);
        PrintJson.printJsonObj(response,transList);
    }
}
