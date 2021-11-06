package com.bby.crm.workbench.service;

import com.bby.crm.vo.PaginationVO;
import com.bby.crm.workbench.domain.Activity;
import com.bby.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean save(Activity activity);

    PaginationVO<Activity> pageList(Map<String, Object> map);

    boolean delele(String[] ids);

    Map<String, Object> editSelect(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRAbyId(String id);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark remark);

    boolean updateRemark(ActivityRemark remark);

    List<Activity> getActivityByName(String name);
}
