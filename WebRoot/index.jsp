<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	//跳到登录页的action
	response.sendRedirect(basePath+"sys/login_toLoginUI.action");
%>

