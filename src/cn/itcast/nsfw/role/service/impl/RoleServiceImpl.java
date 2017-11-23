package cn.itcast.nsfw.role.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.nsfw.role.dao.RoleDao;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.service.RoleService;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
	
	/**
	 * 1. 创建RoleDao对象调用方法,抽取service后不用这种方法
	 * @Resource
	 * private RoleDao roleDao;  创建roleDao对象调用方法
	 */
	
	
	/**
	 * 2. 通过set()方法注入roleDao
	 * @param roleDao
	 */
	private RoleDao roleDao;
	
	@Resource
	public void setRoleDao(RoleDao roleDao) {
		//注入的同时把infoDao设置给BaseDao
		super.setBaseDao(roleDao);
		
		this.roleDao = roleDao;
	}
	
	
	//基础的增删改查(出了重写的方法)都继承公共类BaseServiceImpl<T>
	
	/**
	 * 更新角色的方法
	 */
	public void update(Role role) {
		//如果不进行如下两步操作，编辑更新的时候(编辑的换选另一权限角色)，历史角色权限一起保存更新
		//1.删除该角色对应的所有权限
		roleDao.deleteRolePrivilegeByRoleId(role.getRoleId());
		
		//2.更新角色及其权限
		roleDao.update(role);
	}
}
