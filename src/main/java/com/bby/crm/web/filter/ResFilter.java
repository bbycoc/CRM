package com.bby.crm.web.filter;

import com.bby.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResFilter implements Filter {
    public void doFilter(ServletRequest res, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request= (HttpServletRequest) res;
        HttpServletResponse respose= (HttpServletResponse) resp;

        String path=request.getServletPath();
        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){
            chain.doFilter(res,resp);
        }else {
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                chain.doFilter(res, resp);
            } else {
                respose.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }
    }
}
