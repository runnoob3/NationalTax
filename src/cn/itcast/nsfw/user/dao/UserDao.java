package cn.itcast.nsfw.user.dao;

import java.io.Serializable;
import java.util.List;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public interface UserDao extends BaseDao<User> {
	
	
	//根据账号和用户id查询用户，返回用户列表
	public List<User> findUserByAccountAndId(String id, String account);
	
	
	//保存用户角色的方法
	public void saveUserRole(UserRole userRole);

	
	//根据用户id删除该用户的所有角色
	public void deleteUserRoleByUserId(Serializable id);

	//根据用户id获取该用户对应的所有用户角色
	public List<UserRole> getUserRolesByUserId(String id);
	
	//根据用户的账号和密码查询用户列表--登录
	public List<User> findUserByAccountAndPassword(String account,String password);
	
}
