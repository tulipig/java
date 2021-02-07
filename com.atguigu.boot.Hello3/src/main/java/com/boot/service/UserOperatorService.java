package com.boot.service;

import com.boot.bean.User;

public interface UserOperatorService {

    public User getUserAgeById(Long id);

    public void createUser(User user);
}
