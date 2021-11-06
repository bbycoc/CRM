package com.bby.crm.workbench.service;

import com.bby.crm.settings.domain.User;
import com.bby.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranService {
    List<Tran> getTranList(Map map);

    List<User> getUserList();
}
