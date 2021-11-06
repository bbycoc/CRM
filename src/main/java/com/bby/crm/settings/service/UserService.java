package com.bby.crm.settings.service;

import com.bby.crm.exception.LoginException;
import com.bby.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String usename, String password, String ip) throws LoginException;

    List<User> getUserList();
}
