package cn.itcast.home.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import cn.itcast.core.constant.Constant;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.complain.entity.Complain;
import cn.itcast.nsfw.complain.service.ComplainService;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.info.service.InfoService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport {
	
	@Resource      //注入UserService
	private UserService userService;
	
	
	@Resource      //注入ComplainService
	private ComplainService complainService;
	
	//Json返回值集合
	private Map<String,Object> return_map;
	
	//加载首页信息列表
	@Resource      //注入infoService
	private InfoService infoService;
	
	
	//定义Complain对象，接收前台(complainAddUI.jsp)传过来的投诉信息
	private Complain comp;
	
	//首页超链接查看信息
	private Info info;
	
	//跳转到首页的方法
	public String execute(){
		//1. 首页加载信息列表
		//加载分类集合,设置INFO_TYPE_MAP到listUI.jsp
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		//查询
		QueryHelper queryHelper1=new QueryHelper(Info.class, "i");
		queryHelper1.addOrderByProperty("i.createTime", QueryHelper.ORDER_BY_DESC);
		
		//首页信息列表显示最新的5条(获取列表)
		List<Info> infoList= infoService.getPageResult(queryHelper1, 1, 5).getItems();
		//设置到页面
		ActionContext.getContext().getContextMap().put("infoList", infoList);
		
		
		//2. 首页加载我的投诉信息列表
		//获取用户
		User user=(User) ServletActionContext.getRequest().getSession().getAttribute(Constant.USER);
		//加载分类集合,设置COMPLAIN_STATE_MAP到listUI.jsp
		ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		//查询
		QueryHelper queryHelper2=new QueryHelper(Complain.class, "c");
		//添加用户名
		queryHelper2.addCondition("c.compName = ?", user.getName());
		//根据时间降序排序
		queryHelper2.addOrderByProperty("c.compTime", QueryHelper.ORDER_BY_ASC);
		//根据状态升序排序
		queryHelper2.addOrderByProperty("c.state", QueryHelper.ORDER_BY_DESC);
		
		//首页我的投诉列表显示最新的5条(获取列表)
		List<Complain> complainList= complainService.getPageResult(queryHelper2, 1, 5).getItems();
		//设置到页面
		ActionContext.getContext().getContextMap().put("complainList", complainList);
		
		
		//逻辑试图名，跳到home.jsp
		return "home";
	}
	
	
	//跳转到我要投诉页面
	public String complainAddUI(){
		
		return "complainAddUI";
	}
	
	//1、没有使用框架(直接使用输出流输出JSON格式字符串)
	//根据部门查询该部门下的用户列表(被投诉人是哪个部门)
	public void getUserJson(){
		
		try {
			//1、获取部门
			String dept=ServletActionContext.getRequest().getParameter("dept");
			
			//如果部门不为空
			if(StringUtils.isNoneBlank(dept)){
				//创建queryHelper对象，查找User类
				QueryHelper queryHelper=new QueryHelper(User.class,"u");
				//添加条件(根据部门查询)
				queryHelper.addCondition("u.dept like ?", "%"+dept);
				//2、根据部门查询用户列表
				List<User> userList =userService.findObjects(queryHelper);
				//创建Json对象
				JSONObject jso=new JSONObject();
				//"msg", "success":对应前台complainAddUI.jsp中脚本的变量
				jso.put("msg", "success");
				//把userList转成JSON对象
				jso.accumulate("userList", userList);
				
				//3、输出用户列表
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				//对应listUI.jsp页面：if("更新状态成功"==msg)，状态显示为中文，改为中文编码
				outputStream.write(jso.toString().getBytes("utf-8"));
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
		
		
		//2、使用struts框架(使用struts-json-pluin.jar将action中对应的对象自动生成json格式字符串)
		//根据部门查询该部门下的用户列表(被投诉人是哪个部门)
		public String getUserJson2(){
			try {
				//1、获取部门
				String dept=ServletActionContext.getRequest().getParameter("dept");
				
				//如果部门不为空
				if(StringUtils.isNoneBlank(dept)){
					//创建queryHelper对象，查找User类
					QueryHelper queryHelper=new QueryHelper(User.class,"u");
					//添加条件(根据部门查询)
					queryHelper.addCondition("u.dept like ?", "%" + dept);
					//2、根据部门查询用户列表
					//userList =userService.findObjects(queryHelper);
					return_map=new HashMap<String,Object>();
					return_map.put("msg", "success");
					return_map.put("userList", userService.findObjects(queryHelper));
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return SUCCESS;
		}

		
		//保存投诉
		public void complainAdd(){
			try {
				//如果投诉信息不为空
				if(comp !=null){
					//设置默认投诉状态为待受理
					comp.setState(Complain.COMPLAIN_STATE_UNDONE);
					//设置投诉时间
					comp.setCompTime(new Timestamp(new Date().getTime()));
					//保存
					complainService.save(comp);
					
					//输出投诉结果
					//3、输出用户列表
					HttpServletResponse response = ServletActionContext.getResponse();
					response.setContentType("text/html");
					ServletOutputStream outputStream = response.getOutputStream();
					//对应listUI.jsp页面：if("更新状态成功"==msg)，状态显示为中文，改为中文编码
					outputStream.write("success".getBytes("utf-8"));
					outputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		//首页超链接查看信息
		public String infoViewUI(){
			//加载分类集合,设置INFO_TYPE_MAP到listUI.jsp
			ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
			
			if(info != null){
				//根据id查找信息的方法
				info=infoService.findObjectById(info.getInfoId());
			}
			
			return "infoViewUI";
		}
		
		
		//首页超链接查看投诉
		public String complainViewUI(){
			//加载分类集合,设置INFO_TYPE_MAP到listUI.jsp
			ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
			
			if(comp != null){
				//根据id查找信息的方法
				comp=complainService.findObjectById(comp.getCompId());
			}
			
			return "complainViewUI";
		}
		
		
		
		//getter()/setter()方法
		public Map<String,Object> getReturn_map() {
			return return_map;
		}
		public void setReturn_map(Map<String,Object> return_map) {
			this.return_map = return_map;
		}
		public Complain getComp() {
			return comp;
		}
		public void setComp(Complain comp) {
			this.comp = comp;
		}
		public Info getInfo() {
			return info;
		}
		public void setInfo(Info info) {
			this.info = info;
		}
		
}
