package cn.itcast.nsfw.user.entity;
/**
 * 联合主键类
 * 
 * 注意:需要重写hashCode()和equals()方法
 */
import java.io.Serializable;

import cn.itcast.nsfw.role.entity.Role;

public class UserRoleId implements Serializable {
	
	
	private Role role;         //因为需要角色实体类的其它属性，所以定义为类类型,而不是角色id
	
	private String userId;     //用户id
	
	
	
	//无参构造方法
	public UserRoleId() {
		
	}
	
	
	//带参构造方法
	public UserRoleId(Role role, String userId) {
		this.role = role;
		this.userId = userId;
	}
	
	
	
	//getter()/setter()方法
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}


	//重写hashCode()方法
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}


	//重写equals()方法
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRoleId other = (UserRoleId) obj;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	
	
}
