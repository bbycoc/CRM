package com.bby.crm.workbench.dao;

import com.bby.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface RemarkActivityDao {

    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRAbyId(String id);

    boolean deleteRemark(String id);

    int saveRemark(ActivityRemark remark);

    int updateRemark(ActivityRemark remark);
}
