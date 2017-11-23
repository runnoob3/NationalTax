package cn.itcast.nsfw.user.service;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.itcast.core.exception.ServiceException;
import cn.itcast.core.service.BaseService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public interface UserService extends BaseService<User>{
	
	//基础的增删改查都继承公共接口BaseService<T>
	
	
	//GitTest
	
	//导出用户列表的方法
	public void exportExcel(List<User> userlist,ServletOutputStream outputStream);

	//导入用户列表的方法
	public void importExcel(File userExcel, String userExcelFileName);

	//根据账号和用户id查询用户，返回用户列表
	public List<User> findUserByAccountAndId(String id, String account);

	//保存用户的同时保存角色的方法(保存一个id的集合)
	//public void saveUserAndRole(User user, String[] userRoleIds);
	
	public void saveUserAndRole(User user, String... roleIds);    //可变参数,只有一个id

	//更新用户的同时更新角色的方法
	//public void updateUserAndRole(User user, String[] userRoleIds);
	public void updateUserAndRole(User user, String... roleIds);    //可变参数,只有一个id

	//根据用户id获取该用户对应的所有用户角色
	public List<UserRole> getUserRolesByUserId(String id);
	
	//根据用户的账号和密码查询用户列表--登录
	public List<User> findUserByAccountAndPassword(String account,String password);
	
	
	
	
}
