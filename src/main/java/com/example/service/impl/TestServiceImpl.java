package com.example.service.impl;  
  
import com.test.service.TestService;  
  
public class TestServiceImpl implements TestService {  
  
    public String test() {  
        System.out.println("test success");  
        return "provider002";  
    }  
  
}