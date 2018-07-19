package com.test;

public enum Singleton {  
    INSTANCE;  
    public String whateverMethod() { 
    	return "test";
    } 
    static{
		System.out.println("静态代码块");
	}
	Singleton(){
		System.out.println("构造函数");
	}
	{
		System.out.println("构造代码块");
	}
	
	public void GetUserClass(){
		System.out.println("函数");
	}
}