package cn.itcast.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.core.constant.Constant;
import cn.itcast.core.permission.PermissionCheck;
import cn.itcast.nsfw.user.entity.User;

public class LoginFilter implements Filter{
	
	//销毁的方法
	public void destroy() {
		
	}
	
	//过滤的方法
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		
		//强转成HttpServletRequest类型，因为要调用它的方法
		HttpServletRequest request=(HttpServletRequest) servletRequest;
		HttpServletResponse response=(HttpServletResponse) servletResponse;
		
		//获取请求地址
		String uri=request.getRequestURI();
		
		//判断当前请求地址是否是登录请求地址
		if(!uri.contains("sys/login_")){
			//非登录请求
			if(request.getSession().getAttribute(Constant.USER) !=null){
				//说明已经登录过，放行
				//判断是否访问纳税服务系统
				if(uri.contains("/nsfw/")){
					//访问纳税服务子系统
					//判断当前用户有无权限访问
					//1. 获取用户信息
					User user=(User) request.getSession().getAttribute(Constant.USER);
					//取到spring..IoC容器（随着应用服务器启动）
					WebApplicationContext applicationContext=WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
					
					//2. 权限检查:判断用户是否有权限
					//获取Bean
					PermissionCheck pc=(PermissionCheck) applicationContext.getBean("permissionCheck");
					//是否有权限
					if(pc.isAccessiable(user,"nsfw")){
						//说明有权限，放行
						chain.doFilter(request, response);
					}else{
						//没有权限，跳转到没有权限提示页面
						response.sendRedirect(request.getContextPath() + "/sys/login_toNoPermissionUI.action");
					}
				}else{
					//非访问纳税服务子系统(其它系统)，则直接放行
					chain.doFilter(request, response);
				}
				
			}else{
				//未登陆过，跳转到登录页面(地址)
				response.sendRedirect(request.getContextPath() + "/sys/login_toLoginUI.action");
			}
		}else{
			//登录请求，直接放行
			chain.doFilter(request, response);
		}
	}
	
	//初始化的方法
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
