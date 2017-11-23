package cn.itcast.nsfw.complain.action;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.complain.entity.Complain;
import cn.itcast.nsfw.complain.entity.ComplainReply;
import cn.itcast.nsfw.complain.service.ComplainService;

public class ComplainAction extends BaseAction {
	
	@Resource   //注入complainService
	private ComplainService complainService;
	
	//定义Complain类属性,调用其属性
	private Complain complain;
	
	//定义为Sring类型,因为页面传过来的Date类型无法获取
	private String startTime;       //模糊搜索开始时间
	private String endTime;         //模糊搜索结束时间
	
	private ComplainReply reply;    //回复接收对象
	
	//查询回显(点击受理-保存后,搜索框依然显示搜索信息)
	private String strTitle;       //投诉标题
	private String strState;       //状态   
	
	//统计年度每个月投诉数集合
	private Map<String,Object> statisticMap;
	
	
	//投诉列表
	public String listUI(){
		//加载分类集合,设置COMPLAIN_STATE_MAP到listUI.jsp
		ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		
		try {
			//创建查询助手对象:"c": 别名
			QueryHelper queryHelper = new QueryHelper(Complain.class, "c");
			//根据投诉标题查询
			if(complain !=null){
				
				//最不耗性能的放在最前面，筛选后再查询
				
				//查询开始时间之后的投诉数据
				if(StringUtils.isNoneBlank(startTime)){
					//设置编码为UTF-8,因为String类型的startTime存在特殊字符(如: -、空格)
					startTime=URLDecoder.decode(startTime, "utf-8");
					//条件3：根据开始时间查询:大于或等于投诉时间, 把String类型的转换成Date类型
					queryHelper.addCondition("c.compTime >= ?", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss"));
				}
				
				//查询结束时间之前的投诉数据
				if(StringUtils.isNoneBlank(endTime)){
					//设置编码为UTF-8,因为String类型的endTime存在特殊字符(如: -、空格)
					endTime=URLDecoder.decode(endTime, "utf-8");
					//条件3：根据结束时间查询:小于或等于投诉时间, 把String类型的转换成Date类型
					queryHelper.addCondition("c.compTime <= ?", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
				}
				
				//如果状态不为空
				if(StringUtils.isNoneBlank(complain.getState())){
					//条件2：根据状态查询
					queryHelper.addCondition("c.state= ?", complain.getState());
				}
				
				//如果投诉标题不为空
				if(StringUtils.isNoneBlank(complain.getCompTitle())){
					//把信息标题的中文转码
					complain.setCompTitle(URLDecoder.decode(complain.getCompTitle(), "utf-8"));
					//条件1：根据投诉标题模糊查询
					queryHelper.addCondition("c.compTitle like ?", "%"+complain.getCompTitle()+"%");
				}
				
			}
			
			//按状态升序排序:0(待受理),1(已受理),2(已失效)
			queryHelper.addOrderByProperty("c.state", QueryHelper.ORDER_BY_ASC);
			
			//排序:按照投诉时间升序排序(时间从前往后)
			queryHelper.addOrderByProperty("c.compTime", QueryHelper.ORDER_BY_ASC);
			
			
			//分页
			pageResult=complainService.getPageResult(queryHelper, getPageNo(), getPageSize());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return "listUI";
	}
	
	
	//跳转到受理页面
	public String dealUI(){
		//加载分类集合,设置到键INFO_TYPE_MAP到listUI.jsp
		ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		//根据投诉标题查询
		if(complain !=null){
			//解决查询信息标题覆盖问题(点击受理-保存操作后，搜索框依然显示条件)
			setStrTitle(complain.getCompTitle());
			setStrState(complain.getState());
			
			//根据ID查找投诉信息
			complain=complainService.findObjectById(complain.getCompId());
		}
		return "dealUI";
	}
	
	
	//受理操作
	public String deal(){
		if(complain !=null){
			//根据id查找出投诉信息
			Complain tem=complainService.findObjectById(complain.getCompId());
			//1、更新投诉的状态为已受理
			if(!Complain.COMPLAIN_STATE_DONE.equals(tem.getState())){    //已经改过,跟新状态为已经受理
				//修改状态
				tem.setState(Complain.COMPLAIN_STATE_DONE);
			}
			
			//2、保存回复信息
			if(reply != null){
				//设置回复对应哪一条投诉
				reply.setComplain(tem);
				//设置回复时间
				reply.setReplyTime(new Timestamp(new Date().getTime()));
				
				//保存回复信息(集合)
				tem.getComplainReplies().add(reply);
			}
			//更新
			complainService.update(tem);
		}
		
		return "list";
	}
	
	
	//跳转到统计页面
	public String annualStatisticChartUI(){
		
		
		return "annualStatisticChartUI";
	}
	
	
	//根据年度获取该年度下的投诉统计数
	public String getAnnualStatisticData(){
		
		//1. 获取年份
		HttpServletRequest request=ServletActionContext.getRequest();
		int year = 0;
		if(request.getParameter("year") != null){
			year=Integer.valueOf(request.getParameter("year"));
		}else{
			//默认当前年份
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		
		//2. 统计年度的每个月的投诉数
		statisticMap = new HashMap<String,Object>();
		
		statisticMap.put("msg", "success");
		statisticMap.put("chartData", complainService.getAnnualStatisticDataByYear(year));
		
		return "annualStatisticData";
		
	}
	
	
	
	
	//getter()/setter()方法
	public Complain getComplain() {
		return complain;
	}
	public void setComplain(Complain complain) {
		this.complain = complain;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public ComplainReply getReply() {
		return reply;
	}
	public void setReply(ComplainReply reply) {
		this.reply = reply;
	}
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	public String getStrState() {
		return strState;
	}
	public void setStrState(String strState) {
		this.strState = strState;
	}
	public Map<String,Object> getStatisticMap() {
		return statisticMap;
	}
	public void setStatisticMap(Map<String,Object> statisticMap) {
		this.statisticMap = statisticMap;
	}
	
}
