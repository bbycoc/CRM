package com.bby.crm.workbench.service.impl;

import com.bby.crm.utils.SqlSessionUtil;
import com.bby.crm.workbench.dao.CustomerDao;
import com.bby.crm.workbench.domain.Customer;
import com.bby.crm.workbench.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao= SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    public List<Customer> getCustomerName(String name) {
        return customerDao.getCustomNameList(name);
    }
}
