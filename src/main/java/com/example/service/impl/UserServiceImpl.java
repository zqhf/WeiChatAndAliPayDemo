package com.example.service.impl;

import com.example.dao.UserMapper;
import com.example.model.User;
import com.example.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService{
	@Autowired
    private UserMapper userMapper;
	@Transactional(propagation = Propagation.REQUIRED)
    public int addUser(User user) {

        return userMapper.insertSelective(user);
        //return userMapper.insert(user);
    }
	@Transactional(propagation = Propagation.REQUIRED)
    public void delete(User user) {

        userMapper.deleteByPrimaryKey(user.getId());
    }
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUser(User user) {
		userMapper.updateByPrimaryKey(user);
	}
    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
	@Transactional(propagation = Propagation.SUPPORTS)
    public List<User> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.selectAllUser();
    }
	@Transactional(propagation = Propagation.SUPPORTS)
	public User selectByPrimaryKey(Integer id){
		return userMapper.selectByPrimaryKey(id);
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public User selectByUserNamePwd(User user){
		return userMapper.selectByUserNamePwd(user);
	}
}
