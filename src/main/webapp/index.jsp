<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>首页</title>
    <script src="<%=basePath%>js/jquery-3.3.1.js"></script>
    <script type="text/javascript">
   $(document).ready(function(){
	  		$("#button").click(function(){
	  			if($("#username").val()==null||$("#username").val()==""){
	  				alert("请输入用户名!");
	  			}
	  			if($("#pwdMd5").val()==null||$("#pwdMd5").val()==""){
	  				alert("请输入密码!");
	  			}
	  		});
	});
    </script>
  </head>
  
  <body>
  <form action="<%=basePath%>user/login" id="form" accept-charset="UTF-8" method="post">
  <table id="table">
  <tr>
  <td>
  <span><label>用户名:</label></span>
  </td>
  <td><input id="username" name="username" value="${user.username}"></td>
  </tr>
  <tr>
  <td>
  <span><label>密码:</label></span>
  </td>
  <td><input id="pwdMd5" name="pwdMd5" type="password" value="${user.pwdMd5}"></td>
  </tr>
  <tr>
  <td><input id="button" type="submit" value="登录"></td>
  </tr>
  </table>
  </form>
  <p>If you click on me, I will disappear.</p>
  </body>
</html>
