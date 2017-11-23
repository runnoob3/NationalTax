package cn.itcast.nsfw.user.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.exception.ActionException;
import cn.itcast.core.exception.ServiceException;
import cn.itcast.core.exception.SysException;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.role.service.RoleService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class UserAction extends BaseAction {
	
	
	//创建userService对象调用方法
	@Resource
	private UserService userService;    //属性必须是userService，因为resource根据bean查找的
	
	//注入角色服务接口：roleService
	@Resource
	private RoleService roleService;    //创建roleService对象调用方法
	
	//创建user对象接收用户信息
	private User user;   //user变量可以随便改，不是注入的
	
	
	//接收复选框的值，进行批量删除（抽取到BaseAction,继承它就可以，但是要把属性改为protected才可以在本Action调用）
	//private String[] selectedRow;
	
	//头像上传
	private File headImg;                     //封装上传到服务器的文件对象
	private String headImgContentType;        //封装上传文件的类型
	private String headImgFileName;           //封装上传文件的名称
	
	//Excel文件上传
	private File userExcel;                   //封装上传到服务器的文件对象
	private String userExcelContentType;      //封装上传文件的类型
	private String userExcelFileName;         //封装上传文件的名称
	
	
	private String[] userRoleIds;            //保存用户角色(addUI.jsp)
	
	//创建用户名字符串(模糊搜索)，解决用户名覆盖的问题
	private String strName;
	
	
	/**
	 * 1. 列表页面
	 * @return
	 * @throws SysException  (抛出系统的异常)
	 */
	public String listUI() throws Exception{
		// 实例化一个QueryHelper对象调用QueryHelper工具类
		// Info.class：Info实体类;; u:别名
		QueryHelper queryHelper = new QueryHelper(User.class, "u");

		try {
			// 根据信息标题查询
			if (user != null) { // 如果信息不为空
				if (StringUtils.isNotBlank(user.getName())) { // 如果信息标题不为空
					// 把信息标题的中文转码
					user.setName(URLDecoder.decode(user.getName(), "utf-8"));
					// 条件1：根据信息标题模糊查询
					queryHelper.addCondition("u.name like ?","%" + user.getName() + "%");
				}
			}
			
			// 查找用户列表1(无分页)
			// userList = userService.findObjects(queryHelper);

			// 查找用户列表2(分页:查询助手(查询列表)、当前页、页大小)
			pageResult = userService.getPageResult(queryHelper, getPageNo(),getPageSize());

		} catch (Exception e) {

			// 抛出Action层的异常(e.getMessage():出错的信息)
			throw new Exception(e.getMessage());
		}
		// 转发，在页面可以取到userlist的值；逻辑视图名，user-struts.xml中配置
		return "listUI";
		
	}
	
	
	/**
	 * 2. 跳转到新增页面
	 * @return
	 */
	public String addUI(){
		
		//加载角色列表,设置到addUI.jsp页面显示,调用查找列表的方法
		ActionContext.getContext().getContextMap().put("rolelist", roleService.findObjects());
		
		return "addUI";

	}
	
	
	/**
	 * 3. 保存新增
	 * @return
	 */
	public String add(){
		try {
			if(user!=null){
				
				//处理头像，不一定要上传头像，加个判断
				if(headImg!=null){
					//2.1、保存头像到指定的文件目录中(upload/user)
					//获取保存路径的绝对地址
					String filePath=ServletActionContext.getServletContext().getRealPath("upload/user");
					//文件名重命名(replaceAll("-", "")：把文件名中斜杠换成空)+获取后缀名
					String fileName=UUID.randomUUID().toString().replaceAll("-", "")+headImgFileName.substring(headImgFileName.lastIndexOf("."));
					//赋值文件
					FileUtils.copyFile(headImg, new File(filePath,fileName));
					
					//2.2、设置用户头像路径
					user.setHeadImg("user/"+fileName);
					
					
				}
				//保存新增的方法
				//userService.save(user);
				
				//保存新增的方法：保存用户的同时保存角色
				userService.saveUserAndRole(user,userRoleIds);
				
				//保存新增时不会把用户名的值也带过去(模糊查询)
				user=null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//保存成功后重定向(路径会变),list对应:user-struts.xml文件中name="list"
		//跳到用户列表页面,逻辑视图名
		return "list";

	}
	
	/**
	 * 4. 跳转到编辑页面
	 * @return
	 */
	public String editUI(){
		
		//加载角色列表,设置到editUI.jsp页面显示,调用查找角色列表的方法
		ActionContext.getContext().getContextMap().put("rolelist", roleService.findObjects());
		
		if (user != null && user.getId()!=null) {
			//解决用户名覆盖问题
			strName=user.getName();
			//跳到编辑页面前先找出用户信息，才能编辑(根据id查找)
			user = userService.findObjectById(user.getId());
			//处理角色回显
			List<UserRole> list=userService.getUserRolesByUserId(user.getId());
			if(list != null && list.size()>0){
				userRoleIds=new String[list.size()];
				//遍历出角色数组
				for(int i=0;i<list.size();i++){
					userRoleIds[i]=list.get(i).getId().getRole().getRoleId();
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
		
		try {
			if(user!=null){
				
				//处理头像，不一定要上传头像，加个判断
				if(headImg!=null){
					//2.1、保存头像到指定的文件目录中(upload/user)
					//获取保存路径的绝对地址
					String filePath=ServletActionContext.getServletContext().getRealPath("upload/user");
					//文件名重命名(replaceAll("-", "")：把文件名中斜杠换成空)+获取后缀名
					String fileName=UUID.randomUUID().toString().replaceAll("-", "")+headImgFileName.substring(headImgFileName.lastIndexOf("."));
					//赋值文件
					FileUtils.copyFile(headImg, new File(filePath,fileName));
					
					//2.2、设置用户头像路径
					user.setHeadImg("user/"+fileName);
				}
				
				//保存编辑的方法
				//userService.update(user);
				
				//保存(更新)用户的同时保存(更新)角色
				userService.updateUserAndRole(user,userRoleIds);
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
		
		if (user !=null && user.getId() !=null) {
			//解决用户名覆盖问题
			strName=user.getName();
			//删除的方法
			userService.delete(user.getId());
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
			strName=user.getName();
			//把需要删除的项迭代出来
			for(String id:selectedRow){
				//把选中项逐一删除
				userService.delete(id);
			}
		}
		
		//批量删除成功后跳回列表页面
		return "list";

	}

	//导出用户列表的方法
	public void exportExcel(){
		
		try {
			//1、查找用户列表userlist抽取分页的时候删除，直接用userService.findObjects();
			//userlist=userService.findObjects();
			
			//2、导出
			//输出到浏览器,输出流,输出响应
			HttpServletResponse response=ServletActionContext.getResponse();
			//告诉浏览器输出内容是Excel文档，
			response.setContentType("application/x-excel");
			//让浏览器将这个文档作为附件弹框出来给别人下载,生成低版本(2003)的文档，都可以打开
			//Content-Disposition：内容处理方式; attachment：以附件的形式; filename:指定文件的名称; getBytes():转换编码格式，使中文兼容各种浏览器
			response.setHeader("Content-Disposition", "attachment;filename="+ new String("用户列表.xls".getBytes(),"ISO-8859-1"));
			//获取输出流
			ServletOutputStream outputStream=response.getOutputStream();
			
			//调用导出的方法
			userService.exportExcel(userService.findObjects(),outputStream);
			
			//outputStream不为空就关闭资源
			if(outputStream !=null){
				outputStream.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 导入用户列表的方法
	 * @return  返回到用户列表，查看是否导入成功
	 */
	public String importExcel(){
		//1. 获取Excel文件
		if(userExcel !=null){
			//判断是否是Excel文件，正则表达式:^：开始; .+:任意的文件名称和路径; \\.:后缀名必须是.xxx; (?i):不区分大小写; (xls):文件后缀; $:结束
			if(userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
				//2.导入Excel文件
				userService.importExcel(userExcel,userExcelFileName);
			}
		}
		
		//重定向
		return "list";
		
	}
	
	
	/**
	 * 效验账号的唯一的方法
	 */
	public void verifyAccount(){
		try {
			//1.获取账号
			//判断用户和账号是否为空
			//StringUtils.isNotBlank(user.getAccount())==user.getAccount() !=null&&user.getAccount().equals("")
			if(user !=null && StringUtils.isNotBlank(user.getAccount())){
				//2.根据账号到数据库效验是否存在该账号对应的用户,调用查找的方法
				List<User> list=userService.findUserByAccountAndId(user.getId(),user.getAccount());
				String strResult="true";
				//如果列表不为空
				if(list!=null && list.size()>0){
					//说明该用户已经存在
					strResult="false";
				}
				
				//输出
				//输出到浏览器,输出流,输出响应
				HttpServletResponse response=ServletActionContext.getResponse();
				//告诉浏览器输出内容是Excel文档，
				response.setContentType("text/html");
				//获取输出流
				ServletOutputStream outputStream=response.getOutputStream();
				//输出
				outputStream.write(strResult.getBytes());
				//关闭资源
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	//getter()/setter()方法
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	/*
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	*/
	
	public File getHeadImg() {
		return headImg;
	}
	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}

	public String getHeadImgContentType() {
		return headImgContentType;
	}
	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}

	public String getHeadImgFileName() {
		return headImgFileName;
	}
	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}
	
	public File getUserExcel() {
		return userExcel;
	}
	public void setUserExcel(File userExcel) {
		this.userExcel = userExcel;
	}
	
	public String getUserExcelContentType() {
		return userExcelContentType;
	}
	public void setUserExcelContentType(String userExcelContentType) {
		this.userExcelContentType = userExcelContentType;
	}
	
	public String getUserExcelFileName() {
		return userExcelFileName;
	}
	public void setUserExcelFileName(String userExcelFileName) {
		this.userExcelFileName = userExcelFileName;
	}
	
	public String[] getUserRoleIds() {
		return userRoleIds;
	}
	public void setUserRoleIds(String[] userRoleIds) {
		this.userRoleIds = userRoleIds;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	
}
