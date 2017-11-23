package cn.itcast.nsfw.complain.entity;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Complain entity. @author MyEclipse Persistence Tools
 */

public class Complain implements java.io.Serializable {

	private String compId;             //投诉人ID
	private String compCompany;        //投诉人单位
	private String compName;           //投诉人姓名
	private String compMobile;         //投诉人单位手机号
	private Boolean isNm;              //是否匿名投诉
	private Timestamp compTime;        //投诉时间
	private String compTitle;          //投诉标题
	private String toCompName;         //被投诉人
	private String toCompDept;         //被投诉部门
	private String compContent;        //投诉内容
	private String state;              //状态
	private Set complainReplies = new HashSet(0);      //投诉的回复集合
	
	//状态
	public static String COMPLAIN_STATE_UNDONE = "0";        //待受理
	public static String COMPLAIN_STATE_DONE = "1";          //已受理
	public static String COMPLAIN_STATE_INVALID = "2";       //已失效
	public static Map<String, String> COMPLAIN_STATE_MAP;
	
	//初始化值
	static {
		COMPLAIN_STATE_MAP = new HashMap<String, String>();
		COMPLAIN_STATE_MAP.put(COMPLAIN_STATE_UNDONE, "待受理");
		COMPLAIN_STATE_MAP.put(COMPLAIN_STATE_DONE, "已受理");
		COMPLAIN_STATE_MAP.put(COMPLAIN_STATE_INVALID, "已失效");
	}
	
	
	//无参构造
	public Complain() {
	}
	
	//最小参数构造
	public Complain(String compTitle) {
		this.compTitle = compTitle;
	}
	
	//全参构造
	public Complain(String compCompany, String compName, String compMobile, Boolean isNm, Timestamp compTime, String compTitle, String toCompName, String toCompDept, String compContent, String state,
			Set complainReplies) {
		this.compCompany = compCompany;
		this.compName = compName;
		this.compMobile = compMobile;
		this.isNm = isNm;
		this.compTime = compTime;
		this.compTitle = compTitle;
		this.toCompName = toCompName;
		this.toCompDept = toCompDept;
		this.compContent = compContent;
		this.state = state;
		this.complainReplies = complainReplies;
	}
	
	
	//getter()/setter()方法
	public String getCompId() {
		return this.compId;
	}
	public void setCompId(String compId) {
		this.compId = compId;
	}
	public String getCompCompany() {
		return this.compCompany;
	}
	public void setCompCompany(String compCompany) {
		this.compCompany = compCompany;
	}
	public String getCompName() {
		return this.compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getCompMobile() {
		return this.compMobile;
	}
	public void setCompMobile(String compMobile) {
		this.compMobile = compMobile;
	}
	public Boolean getIsNm() {
		return this.isNm;
	}
	public void setIsNm(Boolean isNm) {
		this.isNm = isNm;
	}
	public Timestamp getCompTime() {
		return this.compTime;
	}
	public void setCompTime(Timestamp compTime) {
		this.compTime = compTime;
	}
	public String getCompTitle() {
		return this.compTitle;
	}
	public void setCompTitle(String compTitle) {
		this.compTitle = compTitle;
	}
	public String getToCompName() {
		return this.toCompName;
	}
	public void setToCompName(String toCompName) {
		this.toCompName = toCompName;
	}
	public String getToCompDept() {
		return this.toCompDept;
	}
	public void setToCompDept(String toCompDept) {
		this.toCompDept = toCompDept;
	}
	public String getCompContent() {
		return this.compContent;
	}
	public void setCompContent(String compContent) {
		this.compContent = compContent;
	}
	public String getState() {
		return this.state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Set getComplainReplies() {
		return this.complainReplies;
	}
	public void setComplainReplies(Set complainReplies) {
		this.complainReplies = complainReplies;
	}

}