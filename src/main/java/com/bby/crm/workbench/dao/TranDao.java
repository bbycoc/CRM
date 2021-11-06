package com.bby.crm.workbench.dao;

import com.bby.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int add(Tran t);

    List<Tran> getTranList(Map map);
}
