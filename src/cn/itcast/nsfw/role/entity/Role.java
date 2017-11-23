package cn.itcast.nsfw.role.entity;
/**
 * 角色实体类
 */
import java.io.Serializable;
import java.util.Set;

public class Role implements Serializable {
	
	
	private String roleId;                         //角色id
	private String name;                           //名称
	private String state;                          //状态
	private Set<RolePrivilege> rolePrivileges;     //权限的集合:RolePrivilege实体类属性
	
	//角色状态
	public static String ROLE_STATE_VALID = "1";//有效
	public static String ROLE_STATE_INVALID = "0";//无效
	
	
	
	//无参构造方法
	public Role() {
		super();
	}
	
	
	//带参构造方法
	public Role(String roleId, String name, String state,
			Set<RolePrivilege> rolePrivileges) {
		super();
		this.roleId = roleId;
		this.name = name;
		this.state = state;
		this.rolePrivileges = rolePrivileges;
	}
	
	//一个参数构造方法，UserServiceImpl.java中用到
	public Role(String roleId) {
		this.roleId = roleId;
	}


	//getter()/setter()方法
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Set<RolePrivilege> getRolePrivileges() {
		return rolePrivileges;
	}
	public void setRolePrivileges(Set<RolePrivilege> rolePrivileges) {
		this.rolePrivileges = rolePrivileges;
	}
	
	
	
	
	
}
