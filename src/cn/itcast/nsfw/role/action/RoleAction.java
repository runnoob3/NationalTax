package cn.itcast.nsfw.role.action;

import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;


import com.opensymphony.xwork2.ActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.constant.Constant;
import cn.itcast.core.exception.SysException;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.entity.RolePrivilege;
import cn.itcast.nsfw.role.entity.RolePrivilegeId;
import cn.itcast.nsfw.role.service.RoleService;

public class RoleAction extends BaseAction {
	
		//创建roleService对象调用方法
		@Resource
		private RoleService roleService;    //属性必须是roleService，因为resource根据bean查找的
		
		//创建role对象接收用户信息
		private Role role;   //role变量可以随便改，不是注入的
		
		
		//创建privilegeIds数组接收addUI.jsp页面提交的角色权限信息(5个权限角色)
		private String[] privilegeIds;
		
		//创建用户名字符串(模糊搜索)，解决用户名覆盖的问题
		private String strName;
		
		
		/**
		 * 1. 列表页面
		 * @return
		 * @throws SysException  (抛出系统的异常)
		 */
		public String listUI() throws Exception{
			
			//加载权限集合,设置到键privilegeMap到listUI.jsp角色列表页面的角色权限
			ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
			
			//实例化一个QueryHelper对象调用QueryHelper工具类
			//Info.class：Info实体类;; r:别名
			QueryHelper queryHelper=new QueryHelper(Role.class, "r");
			
			try {
				//根据信息标题查询
				if(role !=null){         //如果信息不为空
					if(StringUtils.isNotBlank(role.getName())){      //如果信息标题不为空
						//把信息标题的中文转码
						role.setName(URLDecoder.decode(role.getName(), "utf-8"));
						//条件1：根据信息标题模糊查询
						queryHelper.addCondition("r.name like ?", "%"+role.getName()+"%");
					}
				}
				//查找用户列表1(无分页)
				//roleList = roleService.findObjects(queryHelper);
				
				//查找用户列表2(分页:查询助手(查询列表)、当前页、页大小)
				pageResult = roleService.getPageResult(queryHelper,getPageNo(),getPageSize());
				
			} catch (Exception e) {
				
				//抛出Action层的异常(e.getMessage():出错的信息)
				throw new Exception(e.getMessage());
			}
			//转发，在页面可以取到rolelist的值；逻辑视图名，role-struts.xml中配置
			return "listUI";
			
		}
		
		
		/**
		 * 2. 跳转到新增页面
		 * @return
		 */
		public String addUI(){
			
			//加载权限集合,设置到键privilegeMap到addUI.jsp页面的角色权限
			ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
			
			return "addUI";

		}
		
		/**
		 * 3. 保存新增
		 * @return
		 */
		public String add(){
			
			//处理权限保存，级联更新角色权限
			if(privilegeIds !=null){
				
				HashSet<RolePrivilege> set=new HashSet<RolePrivilege>();
				
				for(int i=0;i<privilegeIds.length;i++){
					
					//添加privilegeIds数组
					set.add(new RolePrivilege(new RolePrivilegeId(role,privilegeIds[i])));
				}
				//设置权限角色
				role.setRolePrivileges(set);
			}
			
			try {
				if(role!=null){
					//保存新增的方法
					roleService.save(role);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//保存成功后重定向(路径会变),list对应:role-struts.xml文件中name="list"
			//跳到用户列表页面,逻辑视图名
			return "list";
		}
		
		
		/**
		 * 4. 跳转到编辑页面
		 * @return
		 */
		public String editUI(){
			
			//加载权限集合,设置到键privilegeMap到editUI.jsp编辑页面的角色权限
			ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
			
			if (role != null && role.getRoleId()!=null) {
				//解决用户名覆盖问题
				strName=role.getName();
				
				//跳到编辑页面前先找出用户信息，才能编辑(根据id查找)
				role = roleService.findObjectById(role.getRoleId());
				
				//处理权限回显(编辑页面editUI.jsp显示勾选过的权限)
				//权限的集合：RolePrivileges
				if(role.getRolePrivileges() !=null){
					//初始化; role.getRolePrivileges().size():大小
					privilegeIds=new String[role.getRolePrivileges().size()];
					
					//把每一角色权限对象的id设置到数组中
					int i=0;
					for(RolePrivilege rp:role.getRolePrivileges()){
						
						privilegeIds[i++]=rp.getId().getCode();
						
					}
					
				}
				
			}
			
			return "editUI";

		}
		
		/**
		 * 5. 保存编辑
		 * @return
		 */
		public String edit(){
			
			//处理权限保存，级联更新角色权限
			if(privilegeIds !=null){
				
				HashSet<RolePrivilege> set=new HashSet<RolePrivilege>();
				
				for(int i=0;i<privilegeIds.length;i++){
					
					//添加privilegeIds数组
					set.add(new RolePrivilege(new RolePrivilegeId(role,privilegeIds[i])));
				}
				
				role.setRolePrivileges(set);
			}
			
			try {
				if(role!=null){
					//保存编辑的方法
					roleService.update(role);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//编辑成功后跳回列表页面
			return "list";

		}
		
		
		/**
		 * 6. 删除(根据id删除)
		 * @return
		 */
		public String delete(){
			
			if (role !=null && role.getRoleId() !=null) {
				//解决用户名覆盖问题
				strName=role.getName();
				//删除的方法
				roleService.delete(role.getRoleId());
			}
			//删除成功后跳回列表页面
			return "list";

		}
		
		/**
		 * 7. 批量删除
		 * @return
		 */
		public String deleteSelected(){
			
			if (selectedRow !=null) {
				//解决用户名覆盖问题
				strName=role.getName();
				//把需要删除的项迭代出来
				for(String id:selectedRow){
					//把选中项逐一删除
					roleService.delete(id);
				}
			}
			//批量删除成功后跳回列表页面
			return "list";
		}


		//getter()/setter()方法
		public Role getRole() {
			return role;
		}
		public void setRole(Role role) {
			this.role = role;
		}
		public String[] getPrivilegeIds() {
			return privilegeIds;
		}
		public void setPrivilegeIds(String[] privilegeIds) {
			this.privilegeIds = privilegeIds;
		}
		public String getStrName() {
			return strName;
		}
		public void setStrName(String strName) {
			this.strName = strName;
		}

}
