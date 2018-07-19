package com.example.inteceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.util.IMoocJSONResult;
import com.example.util.JsonUtils;

public class MyInteceptor  implements HandlerInterceptor{
	/**
	 * 在请求处理之前进行调用（Controller方法调用之前）
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object object) throws Exception {
		request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        response.setContentType("text/html;charset=UTF-8");  
		//获取请求的URL  
        String url = request.getRequestURI();  
        //URL:login.jsp是公开的;这个demo是除了login.jsp是可以公开访问的，其它的URL都进行拦截控制  
        if(url.indexOf("login")>=0){ 
        	System.out.println("被one拦截，放行...");
            return true;  
        }  
        //获取Session  
        HttpSession session = request.getSession();  
        String username = (String)session.getAttribute("username");  
          
        if(username != null){  
        	System.out.println("被one拦截，放行...");
            return true;  
        }  
        //不符合条件的，跳转到登录界面  
//        PrintWriter out = response.getWriter();  
//        String path = request.getContextPath();
//        StringBuilder builder = new StringBuilder();  
//        builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");  
//        builder.append("alert(\"页面过期，请重新登录\");");  
//        builder.append("window.top.location.href=\"");  
//        builder.append(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/");  //这里是http://ip:port/项目名
//        builder.append("login.jsp\";</script>");  //这里是重新登录的页面url
//        out.print(builder.toString());  
//        out.close();  
        request.getRequestDispatcher("/login.jsp").forward(request, response);  
        System.out.println("被one拦截，不放行...");
        return false;  
    }  
	
	/**
	 * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object object, ModelAndView mv)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行
	 * （主要是用于进行资源清理工作）
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object object, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void returnErrorResponse(HttpServletResponse response, IMoocJSONResult result) 
			throws IOException, UnsupportedEncodingException {
		OutputStream out=null;
		try{
		    response.setCharacterEncoding("utf-8");
		    response.setContentType("text/json");
		    out = response.getOutputStream();
		    out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
		    out.flush();
		} finally{
		    if(out!=null){
		        out.close();
		    }
		}
	}
}
