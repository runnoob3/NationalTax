package cn.itcast.nsfw.user.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;

import cn.itcast.core.dao.impl.BaseDaoImpl;
import cn.itcast.nsfw.user.dao.UserDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	//根据账号和用户id查询用户，返回用户列表
	public List<User> findUserByAccountAndId(String id, String account) {
		
		String hql="FROM User WHERE account=?";
		
		//如果id不为空，拼接id
		if(StringUtils.isNotBlank(id)){
			//id不等于，编辑时排除当前账号
			hql+=" AND id !=?";
			
		}
		
		Query query=getSession().createQuery(hql);
		//设置account
		query.setParameter(0, account);
		//设置id,有值才设置
		if(StringUtils.isNotBlank(id)){
			query.setParameter(1, id);
		}
		
		return query.list();
	}
	
	
	//保存用户角色的方法
	public void saveUserRole(UserRole userRole) {
		
		getHibernateTemplate().save(userRole);
	}
	
	
	//根据用户id删除该用户的所有角色
	public void deleteUserRoleByUserId(Serializable id) {
		//创建删除语句
		Query query=getSession().createQuery("DELETE FROM UserRole WHERE id.userId=?");
		//设置参数
		query.setParameter(0, id);
		//执行更新
		query.executeUpdate();
	}
	
	
	//根据用户id获取该用户对应的所有用户角色
	public List<UserRole> getUserRolesByUserId(String id) {
		//创建删除语句
		Query query=getSession().createQuery("FROM UserRole WHERE id.userId=?");
		//设置参数
		query.setParameter(0, id);
		//返回列表
		return query.list();
	}
	
	
	//根据用户的账号和密码查询用户列表--登录
	public List<User> findUserByAccountAndPassword(String account,String password) {
		
		//创建删除语句
		Query query=getSession().createQuery("FROM User WHERE account=? AND password=?");
		//设置参数值
		query.setParameter(0, account);
		query.setParameter(1, password);
		//返回列表
		return query.list();
	}
	
	
}
