package com.bby.crm.settings.dao;

import com.bby.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {

    List<DicValue> getValue(String type);
}
