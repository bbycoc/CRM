package com.bby.crm.workbench.dao;

import com.bby.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> getcRList(String clueId);

    int del(ClueRemark clueRemark1);
}
