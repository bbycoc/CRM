package com.bby.crm.workbench.dao;

import com.bby.crm.workbench.domain.Activity;
import com.bby.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {
    int saveClue(Clue c);

    int getTotal(Map map1);

    List<Clue> getClueList(Map map1);

    Clue getDetail(String id);

    Clue getClueById(String clueId);

    List<Activity> getActivityList(String clueId);

    int del(String clueId);
}
