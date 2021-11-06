package com.bby.crm.web.listener;
import com.bby.crm.settings.domain.DicValue;
import com.bby.crm.settings.service.DicService;
import com.bby.crm.settings.service.impl.DicServiceImpl;
import com.bby.crm.utils.ServiceFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        //存数据字典
        ServletContext application=event.getServletContext();
        DicService service= (DicService) ServiceFactory.getService(new DicServiceImpl());
        Map<String, List<DicValue>> map=service.getAll();
        Set<String> set=map.keySet();
        for (String key: set) {
            application.setAttribute(key,map.get(key));
        }
        //解析properties文件
        ResourceBundle rb=ResourceBundle.getBundle("Stage2Possibility");
        Map<String,String> pmap=new HashMap<String, String>();
        Enumeration<String> e=rb.getKeys();
        while (e.hasMoreElements()){
            String key=e.nextElement();
            String value=rb.getString(key);
            pmap.put(key,value);
        }
        application.setAttribute("pmap",pmap);
    }
}
