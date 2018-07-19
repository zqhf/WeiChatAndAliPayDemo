package com.example.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.mrdear.pay.controller.PcPayController;

import com.example.model.User;
import com.example.service.UserService;
import com.example.util.JsonUtils;

/**
 * Created by Administrator on 2018/3/21.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(PcPayController.class);
	@RequestMapping(value={"/add"})
	public void addUser(User user){
		userService.addUser(user);
	}

	@RequestMapping(value = "/deleteuser")
	public void deleteUser(User user){

		userService.delete(user);
	}

	@RequestMapping(value = "/update")
	public void updateUser(User user){

		userService.updateUser(user);
	}

	@RequestMapping(value = {"/all/{pageNum}/{pageSize}"})
	@ResponseBody
	public String findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){

		return JsonUtils.objectToJson(userService.findAllUser(pageNum,pageSize));
	}

	@RequestMapping(value = {"/select/{id}"})
	public ModelAndView selectByPrimaryKey(@PathVariable("id") Integer id,ModelAndView modelAndView) {
		User user=userService.selectByPrimaryKey(id);
		modelAndView.addObject("User", user);
		modelAndView.setViewName("studentList");
		return modelAndView;

	}

	@RequestMapping(value = {"/login"})
	public String selectByUserNamePwd(HttpSession session,User user) {
		logger.debug("进入登录方法！！！");
		if (user.getUsername()!=null&&(user.getUsername()!="")){
			User user1=userService.selectByUserNamePwd(user);
			if(user1!=null){
				session.setAttribute("username", user1.getUsername());
			}
		}
		return "studentList";

	}
	@RequestMapping(value="/logout")  
	public String logout(HttpSession session) throws Exception{  
		//清除Session  
		session.invalidate();  

		return "redirect:/login";  
	}
}
