package cn.itcast.nsfw.role.entity;

/**
 * 角色权限实体类
 */
import java.io.Serializable;

public class RolePrivilege implements Serializable {
	
	//联合主键类id
	private RolePrivilegeId id;
	
	
	//无参构造方法
	public RolePrivilege() {
		
	}
	
	//带参构方法
	public RolePrivilege(RolePrivilegeId id) {
		this.id = id;
	}
	
	
	//getter()/setter()方法
	public RolePrivilegeId getId() {
		return id;
	}
	public void setId(RolePrivilegeId id) {
		this.id = id;
	}
	
	
	
	
}
