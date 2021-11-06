package com.bby.crm.workbench.service;

import com.bby.crm.workbench.domain.Activity;
import com.bby.crm.workbench.domain.Clue;
import com.bby.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface ClueService {
    boolean saveClue(Clue c);

    int getTotal(Map map1);

    List<Clue> getClueList(Map map1);

    Clue getDetail(String id);

    boolean convert(String clueId, Tran t, String createBy);

    List<Activity> getActivityList(String clueId);

    int delActivity(String activityId, String clueId);

    List<Activity> searchActivityByName(String name, String clueId);

    boolean bind(String cid, String[] aids);
}
