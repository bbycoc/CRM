package com.bby.crm.workbench.dao;

import com.bby.crm.workbench.domain.ClueActivityRelation;

import java.util.List;
import java.util.Map;

public interface ClueActivityRelationDao {
    int delById(Map map);

    int bind(ClueActivityRelation car);

    List<ClueActivityRelation> getListByClueId(String clueId);

    int del(ClueActivityRelation clueActivityRelation);
}
