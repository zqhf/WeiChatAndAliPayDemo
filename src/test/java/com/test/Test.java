package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.model.User;
import com.example.service.UserService;
import com.example.util.JsonUtils;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Singleton singleton = Singleton.INSTANCE;
		 System.out.println(singleton.whateverMethod());
		 //UserClass userClass = UserClass.getinstance();
		 //userClass.GetUserClass();
//		 ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/applicationContext.xml");  
//	        UserService userService=(UserService) context.getBean("userService");
//	        User user=new User();
//	        user.setUsername("imooc");
//	        user.setPwdMd5("111111");
//	        System.out.println(JsonUtils.objectToJson(userService.selectByUserNamePwd(user)));
//	        user.setId(1);
//	        user.setUsername("zhangsan");
//	        userService.delete(user);
//	        System.out.println(JsonUtils.objectToJson(userService.findAllUser(1,10)));
//	        System.out.println(JsonUtils.objectToJson(userService.addUser(user)));
	}

}
