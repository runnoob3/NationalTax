package cn.itcast.nsfw.role.dao.impl;


import org.hibernate.Query;

import cn.itcast.core.dao.impl.BaseDaoImpl;
import cn.itcast.nsfw.role.dao.RoleDao;
import cn.itcast.nsfw.role.entity.Role;

public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	//1.删除该角色对应的所有权限
	public void deleteRolePrivilegeByRoleId(String roleId) {
		
		Query query=getSession().createQuery("DELETE FROM RolePrivilege WHERE id.role.roleId=?");
		//设置角色id
		query.setParameter(0, roleId);
		//更新
		query.executeUpdate();
	}


}
