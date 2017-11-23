package cn.itcast.nsfw.role.entity;

import java.io.Serializable;
/**
 * 联合主键类
 * @author Administrator
 *
 */
public class RolePrivilegeId implements Serializable {
	
	private Role role;           //角色，需要用到角色名称，座椅定义类类型
	
	//private String roleId;     //角色id
	
	private String code;         //权限code

	
	//无参构造方法
	public RolePrivilegeId() {
		super();
	}
	
	//带参构造方法
	public RolePrivilegeId(Role role, String code) {
		super();
		this.role = role;
		this.code = code;
	}
	
	
	//getter()/setter()方法
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	//重写hashCode()方法
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		RolePrivilegeId other = (RolePrivilegeId) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}
	
	
	
	
	
	
}
