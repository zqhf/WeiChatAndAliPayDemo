<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
<meta http-equiv='Refresh' content='3;URL=${ctx}/'>
<title>404 错误</title>

  </head>
  
 <body>
<p>此页面正在开发中...</p>
<p>系统将在 <span style="color:red;">3</span> 秒后跳转到首页，或者直接点击 <a href="javascript:history.back()">返回</a></p>
</body>
</html>
