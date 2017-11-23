package cn.itcast.nsfw.home.action;

import cn.itcast.core.action.BaseAction;

public class HomeAction extends BaseAction {
	
	
	//跳转到纳税服务系统首页
	public String frame(){
		
		return "frame";
	}
	
	//跳转到纳税服务系统首页--顶部导航栏
	public String top(){
		
		return "top";
	}

	//跳转到纳税服务系统首页--左边菜单栏
	public String left(){
		
		return "left";
	}
}
