package com.bby.crm.workbench.dao;

import com.bby.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {
    Customer getCustomByName(String company);

    int addCustomer(Customer cus);

    List<Customer> getCustomNameList(String name);
}
