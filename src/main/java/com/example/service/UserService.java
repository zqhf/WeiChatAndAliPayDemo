package com.example.service;

import com.example.model.User;

import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */
public interface UserService {
    int addUser(User user);
    
    public void delete(User user);
    
    public void updateUser(User user);

    List<User> findAllUser(int pageNum, int pageSize);

    public User selectByPrimaryKey(Integer id);
    
    public User selectByUserNamePwd(User user);
}
