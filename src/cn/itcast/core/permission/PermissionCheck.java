package cn.itcast.core.permission;

import cn.itcast.nsfw.user.entity.User;

/**
 * 用户权限检查类
 * @author Administrator
 *
 */
public interface PermissionCheck {
	
	
	/**
	 * 判断用户是否有code对应的权限的方法
	 * @param user   用户
	 * @param code   子系统的权限标识符
	 * @return   true/false
	 */
	public boolean isAccessiable(User user, String code); 
	
	
	
}
