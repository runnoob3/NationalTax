package cn.itcast.login.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.constant.Constant;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LoginAction extends ActionSupport {
	
	@Resource
	private UserService userService;   //注入userService调用方法
	
	
	private User user;            //创建用户类类属性，调用它的其他属性
	
	private String loginResult;   //登录结果(成功/失败)
	
	
	//跳转到登录页面
	public String toLoginUI(){
		//逻辑视图名，登录到登录页面
		return "loginUI";
	}
	
	
	//登录的方法
	public String login(){
		//判断user对象里面有无信息
		if(user != null){
			//判断用户名和密码是否为空
			if(StringUtils.isNotBlank(user.getAccount()) && StringUtils.isNotBlank(user.getPassword())){
				
				//根据用户的账号和密码查询用户列表
				List<User> list = userService.findUserByAccountAndPassword(user.getAccount(),user.getPassword());
				
				//有数据，登录成功
				if(list !=null && list.size()>0){
					//获取用户
					User user=list.get(0);
					//1、根据用户id查询该用户的所有角色
					//找出一个列表，然后设置到各个对象中
					user.setUserRoles(userService.getUserRolesByUserId(user.getId()));
					
					//2、将用户信息保存到session中
					//ServletActionContext.getRequest().getSession().setAttribute("user", user);   //这种方法不友好，其它模块也需要用到Seesion
					//2、将用户信息保存到session中：：使用常量
					ServletActionContext.getRequest().getSession().setAttribute(Constant.USER, user);
					//3、将用户登录记录到日志文件
					Log log=LogFactory.getLog(getClass());
					log.info("用户名称为: "+user.getName()+"的用户登录了系统");
					//4、重定向跳转到首页
					return "home";
				}else{
					//登录失败
					loginResult="账号或密码不正确！";
				}
			}else{
				//如果账号或密码为空
				loginResult="账号或密码不能为空！";
			}
		}else{
			//如果没有用户信息
			loginResult="请输入账号和密码！";
		}
		//返回到toLoginUI()方法
		return toLoginUI();
	}
	
	//退出/注销(清除session中保存的用户信息)
	public String logout(){
		//清除session中保存的用户信息
		ServletActionContext.getRequest().getSession().removeAttribute(Constant.USER);
		
		//返回到toLoginUI()方法
		return toLoginUI();
	}
	
	
	//跳转到没有权限提示页面
	public String toNoPermissionUI(){
		
		
		return "noPermissionUI";
	}
	
	
	//getter()/setter()方法
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}
	
}
