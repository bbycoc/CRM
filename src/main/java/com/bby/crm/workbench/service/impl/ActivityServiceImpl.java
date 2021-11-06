package com.bby.crm.workbench.service.impl;

import com.bby.crm.settings.dao.UserDao;
import com.bby.crm.settings.domain.User;
import com.bby.crm.utils.SqlSessionUtil;
import com.bby.crm.vo.PaginationVO;
import com.bby.crm.workbench.dao.ActivityDao;
import com.bby.crm.workbench.dao.RemarkActivityDao;
import com.bby.crm.workbench.domain.Activity;
import com.bby.crm.workbench.domain.ActivityRemark;
import com.bby.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao= SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private RemarkActivityDao remarkActivityDao=SqlSessionUtil.getSqlSession().getMapper(RemarkActivityDao.class);
    private UserDao userDao=SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public boolean save(Activity activity) {

        System.out.println("用户进入业务层");
        boolean flag=true;
        int count = activityDao.save(activity);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    public PaginationVO<Activity> pageList(Map<String, Object> map) {
        //取得total值
        int total=activityDao.getTatalByCondition(map);
        //取得市场活动集合
        List<Activity> dataList=activityDao.getActivityListByCondition(map);
        //将取得的数据封装成vo类
        PaginationVO<Activity> vo=new PaginationVO<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    public boolean delele(String[] ids) {
        boolean flag=true;
        //查询出需要修改的备注的数量
        int count1=remarkActivityDao.getCountByAids(ids);
        //删除备注，返回受到影响的记录条数
        int count2=remarkActivityDao.deleteByAids(ids);
        if (count1!=count2){
            flag=false;
        }
        //删除市场活动
        int count3=activityDao.delete(ids);
        if (count3!= ids.length){
            flag=false;
        }
        return flag;
    }

    public Map<String, Object> editSelect(String id) {
        //取得用户列表
        List<User> ulist=userDao.getUserList();
        //取得市场活动对象
        Activity a=activityDao.getActivityById(id);
        //封装成map返回
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("ulist",ulist);
        map.put("a",a);
        return map;
    }

    public boolean update(Activity activity) {
        System.out.println("用户进入更新市场信息业务层");
        boolean flag=true;
        int count = activityDao.update(activity);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    public Activity detail(String id) {
        Activity activity=activityDao.detail(id);
        return activity;
    }

    public List<ActivityRemark> getRAbyId(String id) {
        return remarkActivityDao.getRAbyId(id);
    }

    public boolean deleteRemark(String id) {
       return remarkActivityDao.deleteRemark(id);
    }

    public boolean saveRemark(ActivityRemark remark) {
        boolean flag=true;
        int count=remarkActivityDao.saveRemark(remark);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    public boolean updateRemark(ActivityRemark remark) {
        boolean flag=true;
        int count=remarkActivityDao.updateRemark(remark);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    public List<Activity> getActivityByName(String name) {
        return activityDao.getActivityByName(name);
    }
}






















