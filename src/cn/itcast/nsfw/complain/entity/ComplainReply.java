package cn.itcast.nsfw.complain.entity;

import java.sql.Timestamp;

/**
 * ComplainReply entity. @author MyEclipse Persistence Tools
 */

public class ComplainReply implements java.io.Serializable {
	
	private String replyId;           //回复ID
	private Complain complain;        //投诉实体类属性
	private String replyer;           //回复人
	private String replyDept;         //回复部门
	private Timestamp replyTime;      //回复时间
	private String replyContent;      //回复内容


	//无参构造方法
	public ComplainReply() {
	}

	//最小参数构造方法
	public ComplainReply(Complain complain) {
		this.complain = complain;
	}

	//全参构造方法
	public ComplainReply(Complain complain, String replyer, String replyDept, Timestamp replyTime, String replyContent) {
		this.complain = complain;
		this.replyer = replyer;
		this.replyDept = replyDept;
		this.replyTime = replyTime;
		this.replyContent = replyContent;
	}
	
	
	//getter()/setter()方法
	public String getReplyId() {
		return this.replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	public Complain getComplain() {
		return this.complain;
	}
	public void setComplain(Complain complain) {
		this.complain = complain;
	}
	public String getReplyer() {
		return this.replyer;
	}
	public void setReplyer(String replyer) {
		this.replyer = replyer;
	}
	public String getReplyDept() {
		return this.replyDept;
	}
	public void setReplyDept(String replyDept) {
		this.replyDept = replyDept;
	}
	public Timestamp getReplyTime() {
		return this.replyTime;
	}
	public void setReplyTime(Timestamp replyTime) {
		this.replyTime = replyTime;
	}
	public String getReplyContent() {
		return this.replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
}