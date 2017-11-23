package cn.itcast.core.permission.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.core.permission.PermissionCheck;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.entity.RolePrivilege;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.service.UserService;

public class PermissionCheckImpl implements PermissionCheck {
	
	
	//注入userService,struts不能找到这个Action中的Bean需要，在Spring中配置文件中注入Bean
	@Resource
	private UserService userService;
	
	/**
	 * 判断用户是否有code对应的权限的方法
	 * @param user   用户
	 * @param code   子系统的权限标识符
	 * @return       true/false
	 */
	public boolean isAccessiable(User user, String code) {
		
		//1. 获取用户的所有角色
		//List<UserRole> list=userService.getUserRolesByUserId(user.getId());
		List<UserRole> list=user.getUserRoles();
		//一般不为空，为了保险
		if(list==null){
			list=userService.getUserRolesByUserId(user.getId());
		}
		
		//2. 根据每个角色对应的所有权限进行对比
		if(list !=null && list.size()>0){
			for(UserRole ur:list){
				//获取每一个用户角色
				Role role=ur.getId().getRole();
				//获取角色对应的权限
				for(RolePrivilege rp:role.getRolePrivileges()){
					//对比是否有code对应的权限
					if(code.equals(rp.getId().getCode())){
						//说明有权限吗，返回true
						return true;
					}
				}
			}
		}
		
		return false;
	}

}
