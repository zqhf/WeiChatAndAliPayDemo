package com.test;

public class UserClass {
	private UserClass(){};
	private static UserClass userClass = new UserClass();
	public static UserClass getinstance(){
		System.out.println("普通函数");
		return userClass;
	}
	static{
		System.out.println("静态代码块");
	}
	void UserClass1(){
		System.out.println("构造函数");
	}
	{
		System.out.println("构造代码块");
	}
	
	public void GetUserClass(){
		System.out.println("函数");
	}
}
