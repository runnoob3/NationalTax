package cn.itcast.nsfw.info.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class Info implements Serializable {

	private String infoId;          //主键，id
	private String type;            //分类
	private String source;          //来源
	private String title;           //标题
	private String content;         //内容
	private String memo;            //备注
	private String creator;         //申请人
	private Timestamp createTime;   //创建时间
	private String state;           //状态
	
	//状态，定义常量
	public static String INFO_STATE_PUBLIC = "1";    //发布
	public static String INFO_STATE_STOP = "0";      //停用
	
	//信息分类，定义常量
	public static String INFO_TYPE_TZGG = "tzgg";    //通知公告
	public static String INFO_TYPE_ZCSD = "zcsd";    //政策速递
	public static String INFO_TYPE_NSZD = "nszd";    //纳税指导
	
	//定义一个集合接收
	public static Map<String, String> INFO_TYPE_MAP;
	static {
		//实例化对象
		INFO_TYPE_MAP = new HashMap<String, String>();
		INFO_TYPE_MAP.put(INFO_TYPE_TZGG, "通知公告");
		INFO_TYPE_MAP.put(INFO_TYPE_ZCSD, "政策速递");
		INFO_TYPE_MAP.put(INFO_TYPE_NSZD, "纳税指导");
	}
	
	//无参构造方法
	public Info() {
	}
	
	//全参构造方法
	public Info(String infoId, String type, String source, String title, String content, String memo, String creator, Timestamp createTime, String state) {
		this.infoId = infoId;
		this.type = type;
		this.source = source;
		this.title = title;
		this.content = content;
		this.memo = memo;
		this.creator = creator;
		this.createTime = createTime;
		this.state = state;
	}
	
	//getter()/setter()方法
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
