<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>列表页</title>
    <script src="<%=basePath%>js/jquery-3.3.1.js"></script>
    <script type="text/javascript">
   $(document).ready(function(){
	  		$("#add").click(Add);
	  		$("#edit").click(Edit);
	  		$("#delete").click(Delete);
	});
	function Add(){
	$.ajax({
		url:"<%=basePath%>user/add",
		async:false,
		data:{
			username:"zhanghua",
			nickname:"hua"
		},
		success: function(data){
	        //alert("add success!"+data);
	      }
		});
    }
    function Edit(){
    	$.ajax({
		url:"<%=basePath%>user/update",
		async:false,
		success: function(data){
	        //alert("delete success!"+data);
	      }
	});
    }
    function Delete(){
    $.ajax({
	url:"<%=basePath%>user/deleteuser",
	async:false,
	data:{
			username:"zhanghua",
			nickname:"hua"
		},
	success: function(data){
	        //alert("delete success!"+data);
	      }
	});
    }
    </script>
  </head>
  
  <body>
  <form action="<%=basePath%>user/deleteuser" id="form" method="post">
  <table id="table">
  <tr>
  <td>
  <span><label>用户名:</label></span>
  </td>
  <td><input id="user.username" name="username" value="${user.username}"></td>
  </tr>
  <tr>
  <td>
  <span><label>密码:</label></span>
  </td>
  <td><input id="user.pwdMd5" name="pwdMd5" type="password" value="${user.pwdMd5}"></td>
  </tr>
  <tr>
  <td><input id="add" type="button" value="添加"></td>
  <td><input id="edit" type="button" value="修改"></td>
  <td><input id="delete" type="button" value="删除"></td>
  </tr>
  </table>
  </form>
  </body>
</html>
