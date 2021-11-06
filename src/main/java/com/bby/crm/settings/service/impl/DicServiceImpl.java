package com.bby.crm.settings.service.impl;

import com.bby.crm.settings.dao.DicTypeDao;
import com.bby.crm.settings.dao.DicValueDao;
import com.bby.crm.settings.dao.UserDao;
import com.bby.crm.settings.domain.DicType;
import com.bby.crm.settings.domain.DicValue;
import com.bby.crm.settings.domain.User;
import com.bby.crm.settings.service.DicService;
import com.bby.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao= SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao= SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);
    private UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map=new HashMap<String, List<DicValue>>();

        List<DicType> dtlist=dicTypeDao.getTypeList();
        for (DicType dicType:dtlist){
            String type=dicType.getCode();
            List<DicValue> list=dicValueDao.getValue(type);
            map.put(type,list);
        }
        return map;
    }

}
