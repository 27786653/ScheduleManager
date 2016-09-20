<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'add.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <form action="insertSchedule.do">
    	任务名：<input name="jobName" >
    	别名：<input name="aliasName" >
    	任务组：<input name="jobGroup" >
    	状态：<input name="status" >
    表达式<input name="cronExpression" >
    是否异步<input name="isSync" type="checkbox" >
    描述<input name="description" >
    类路径<input name="ClassPackage" >
    <input type="submit">
    </form>
  </body>
</html>
