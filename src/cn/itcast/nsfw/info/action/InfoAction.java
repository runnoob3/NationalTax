package cn.itcast.nsfw.info.action;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.page.PageResult;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.info.service.InfoService;

import com.opensymphony.xwork2.ActionContext;

public class InfoAction extends BaseAction {
	
	//创建infoService对象调用方法
	@Resource
	private InfoService infoService;     //属性必须是infoService，因为resource根据bean查找的
	
	//创建info对象接收用户信息
	private Info info;                  //info变量可以随便改，不是注入的
	
	//创建privilegeIds数组接收addUI.jsp页面提交的角色权限信息(5个权限角色)
	private String[] privilegeIds;
	
	//创建信息标题字符串，解决信息标题覆盖的问题
	private String strTitle;
	
	
	/**
	 * 1. 列表页面
	 * @return
	 * @throws Exception  (抛出系统的异常)
	 */
	public String listUI() throws Exception{
		//加载分类集合,设置到键INFO_TYPE_MAP到listUI.jsp
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		
		//实例化一个QueryHelper对象调用QueryHelper工具类
		//Info.class：Info实体类;; i:别名
		QueryHelper queryHelper=new QueryHelper(Info.class, "i");
		
		try {
			//根据信息标题查询
			if(info !=null){         //如果信息不为空
				if(StringUtils.isNotBlank(info.getTitle())){      //如果信息标题不为空
					//把信息标题的中文转码
					info.setTitle(URLDecoder.decode(info.getTitle(), "utf-8"));
					//条件1：根据信息标题模糊查询
					queryHelper.addCondition("i.title like ?", "%"+info.getTitle()+"%");
				}
			}
			//拼接order by语句,根据创建时间降序排序
			queryHelper.addOrderByProperty("i.createTime", QueryHelper.ORDER_BY_DESC);
			//查找用户列表1(无分页)
			//infoList = infoService.findObjects(queryHelper);
			
			//查找用户列表2(分页:查询助手(查询列表)、当前页、页大小)
			pageResult = infoService.getPageResult(queryHelper,getPageNo(),getPageSize());
			
		} catch (Exception e) {
			
			//抛出Action层的异常(e.getMessage():出错的信息)
			throw new Exception(e.getMessage());
		}
		//转发，在页面可以取到infolist的值；逻辑视图名，info-struts.xml中配置
		return "listUI";
	}
	
	
	/**
	 * 2. 跳转到新增页面
	 * @return
	 */
	public String addUI(){
		//加载分类集合,设置到键INFO_TYPE_MAP到addUI.jsp
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		//实例化
		info = new Info();
		//设置时间，当前系统时间
		info.setCreateTime(new Timestamp(new Date().getTime()));
		
		//转发到addUI.jsp页面
		return "addUI";
	}
	
	
	/**
	 * 3. 保存新增
	 * @return
	 */
	public String add(){
		try {
			if(info != null){
				//保存新增的方法
				infoService.save(info);
			}
			//保存新增时不会把信息标题的值也带过去
			info=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		//保存成功后重定向(路径会变),list对应:info-struts.xml文件中name="list"
		//重定向到listUI.jsp页面(info-struts.xml)
		return "list";
	}
	
	
	/**
	 * 4. 跳转到编辑页面
	 * @return
	 */
	public String editUI(){
		//加载分类集合,设置到键INFO_TYPE_MAP到editUI.jsp
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		if (info != null && info.getInfoId() != null) {
			//解决查询信息标题覆盖问题
			strTitle=info.getTitle();
			//跳到编辑页面前先找出用户信息，才能编辑(根据id查找)
			info = infoService.findObjectById(info.getInfoId());
		}
		return "editUI";
	}
	
	
	/**
	 * 5. 保存编辑
	 * @return
	 */
	public String edit(){
		try {
			if(info != null){
				//保存编辑的方法
				infoService.update(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//编辑成功后跳回列表页面,重定向(路径会变),list对应:info-struts.xml文件中name="list"
		return "list";
	}
	
	
	/**
	 * 6. 删除(根据id删除)
	 * @return
	 */
	public String delete(){
		if(info != null && info.getInfoId() != null){
			//解决查询信息标题覆盖问题
			strTitle=info.getTitle();
			//删除的方法
			infoService.delete(info.getInfoId());
		}
		//删除成功后跳回列表页面
		return "list";
	}
	
	
	/**
	 * 7. 批量删除
	 * @return
	 */
	public String deleteSelected(){
		if(selectedRow != null){
			//解决查询信息标题覆盖问题
			strTitle=info.getTitle();
			//把需要删除的项迭代出来
			for(String id: selectedRow){
				//把选中项逐一删除
				infoService.delete(id);
			}
		}
		//批量删除成功后跳回列表页面
		return "list";
	}
	
	
	/**
	 * 8. 异步发布信息
	 */
	public void publicInfo(){
		try {
			if(info != null){
				//1、更新信息状态
				//查出临时的信息
				Info tem = infoService.findObjectById(info.getInfoId());
				//设置状态
				tem.setState(info.getState());
				//更新状态，只设置状态，不改变Info类其它属性
				infoService.update(tem);
				
				//2、输出更新结果
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				//对应listUI.jsp页面：if("更新状态成功"==msg)，状态显示为中文，改为中文编码
				outputStream.write("更新状态成功".getBytes("utf-8"));
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//getter()/setter()方法
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	public String[] getPrivilegeIds() {
		return privilegeIds;
	}
	public void setPrivilegeIds(String[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	public PageResult getPageResult() {
		return pageResult;
	}
	public void setPageResult(PageResult pageResult) {
		this.pageResult = pageResult;
	}

}
