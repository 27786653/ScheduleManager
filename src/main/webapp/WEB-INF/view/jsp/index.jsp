<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <meta charset='utf-8'>
    <title>My JSP 'index.jsp' starting page</title>
    <script type="text/javascript" src="js/curstomjs.js"></script>
  </head>
  <body>
  <a href="<%=basePath %>gotoAddschedule">新增</a>
  <table>
  	<thead>
  		<th>
  		<td>任务id</td><td>任务名称</td>
  		<td>任务别名 </td><td>任务分组</td>
  		<td>触发器</td><td>任务状态</td>
  		<td>任务运行时间表达式 </td><td> 是否异步 </td>
  		<td>任务描述 </td><td>创建时间</td>
  		<td>修改时间</td><td>操作</td>
  		</th>
  	</thead>
  	<tbody>
  	  
  	  <c:forEach var="schedule" items="${schedulelist}" >
   		<tr>
   		 <td>${schedule.scheduleJobId}</td><td>${schedule.jobName}</td>
   		 <td>${schedule.aliasName}</td><td>${schedule.jobGroup}</td>
   		 <td>${schedule.jobTrigger}</td><td>${schedule.status}</td>
   		 <td>${schedule.cronExpression}</td><td>${schedule.isSync}</td>
   		 <td>${schedule.description}</td><td>${schedule.gmtCreate}</td>
   		 <td>${schedule.cronExpression}</td><td>${schedule.isSync}</td>
   		 <td>${schedule.gmtModify}</td>
   		 <td><a onclick="Req('deleteSchedule.do?jobGroup=${schedule.jobGroup}&jobName=${schedule.jobName}')"
   		  href="javascript:void(0);">删除</a>
   		 <a onclick="Req('pauseSchedule.do?jobGroup=${schedule.jobGroup}&jobName=${schedule.jobName}')"
   		 href="javascript:void(0);">暂停</a>
   		 <a onclick="Req('resumeSchedule.do?jobGroup=${schedule.jobGroup}&jobName=${schedule.jobName}')"
   		 href="javascript:void(0);">继续</a>
   		 <a onclick="Req('runOnce.do?jobGroup=${schedule.jobGroup}&jobName=${schedule.jobName}')"
   		 href="javascript:void(0);">立即运行一次</a>
   		 </td>
       </tr>
      </c:forEach>
  	
  	</tbody>
  </table>
  <script type="text/javascript">
  function Req(url){
 	 sendAjaxRequest(url,function(data){
 	 //Callback
 	 });
  }
  	
  </script>
  </body>
</html>
