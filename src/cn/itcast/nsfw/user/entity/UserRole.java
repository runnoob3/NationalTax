package cn.itcast.nsfw.user.entity;
/**
 * 用户角色中间类
 */
import java.io.Serializable;

public class UserRole implements Serializable {
	
	//用户-角色联合主键
	private UserRoleId id;
	
	
	
	//无参构造方法
	public UserRole() {
		
	}
	
	//带参构造方法
	public UserRole(UserRoleId id) {
		this.id = id;
	}
	
	
	//getter()/setter()方法
	public UserRoleId getId() {
		return id;
	}
	public void setId(UserRoleId id) {
		this.id = id;
	}   
	
}
