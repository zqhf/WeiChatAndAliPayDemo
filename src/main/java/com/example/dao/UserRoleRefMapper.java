package com.example.dao;

import com.example.model.UserRoleRef;

public interface UserRoleRefMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserRoleRef record);

    int insertSelective(UserRoleRef record);

    UserRoleRef selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserRoleRef record);

    int updateByPrimaryKey(UserRoleRef record);
}