package com.bby.crm.settings.service;

import com.bby.crm.settings.domain.DicValue;
import com.bby.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface DicService {
    Map<String, List<DicValue>> getAll();
}
