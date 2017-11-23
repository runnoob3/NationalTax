package cn.itcast.core.util;

import java.util.ArrayList;
import java.util.List;

/*
 * 带条件查询(模糊)的工具类;  findObjects(hql,parameters);
 */
public class QueryHelper {
	
	//1. from 子句：必定出现；而且只出现一次
	private String fromClause = "";
	
	//2. where 子句：可选；但关键字where 出现一次；可添加多个查询条件
	private String whereClause = "";
	
	//3. order by子句：可选；但关键字order by 出现一次；可添加多个排序属性
	private String orderByClause = "";
	
	
	//查询hql语句中？对应的查询条件“值”的集合
	private List<Object> parameters;
	
	//排序顺序常量
	public static String ORDER_BY_DESC ="DESC";    //降序
	public static String ORDER_BY_ASC ="ASC";      //升序
	
	/**
	 * 1. 构造from子句(必定出现；而且只出现一次): 构造方法只用能调一次,满足要求
	 * @param clazz  实体类
	 * @param alias  实体类对应的别名
	 */
	public QueryHelper(Class clazz,String alias){
		//From子句
		//getSimpleName():获取实体类名称
		fromClause = "FROM " + clazz.getSimpleName() + " " + alias;
	}
	
	
	/**
	 * 2. 构造where子句(可选；但关键字where只出现一次；可添加多个查询条件)
	 * @param condition   查询条件语句： 例如：i.title like ?
	 * @param params      查询条件语句中？对应的查询条件值： 例如:%标题%
	 */
	public void addCondition(String condition,Object... params){
		//where子句
		//拼接hql语句，前面加空格
		if(whereClause.length()>1){    //非第一个查询条件,where只能出现一次
			//累加，加AND
			whereClause += " AND "+ condition;
		}else{                         //第一个查询条件
			//加where
			whereClause += " WHERE "+ condition;
		}
		
		//设置查询条件值到查询条件值集合中
		if(parameters == null){
			//实例化查询条件值的集合parameters
			parameters = new ArrayList<Object>();
		}
		
		if(params !=null){
			for(Object param: params){
				//添加查询条件值到查询条件值集合中
				parameters.add(param);
			}
		}
	}
	
	
	/**
	 * 3. 构造order by子句 (可选；但关键字order by只出现一次；可添加多个排序属性)
	 * @param property   排序属性: 如:i.createTime, i.
	 * @param order      排序顺序: 如:ASC(升序), DESC(降序)
	 */
	public void addOrderByProperty(String property,String order){
		
		//order by子句
		//拼接hql语句，前面加空格
		if(orderByClause.length()>1){    //非第一个排序,orderBy只能出现一次
			//累加, 用逗号","隔开
			orderByClause += "," + property + " " + order;
		}else{                         //第一个查询条件
			//加where
			orderByClause = " ORDER BY "+ property + " " + order;
		}
	}
	
	
	//一、获取查询hql语句
	public String getQueryListHql(){
		
		//返回三个子句
		return fromClause + whereClause + orderByClause;
	}
	
	
	//三、查询统计数的hql语句
	public String getQueryCountHql(){
		//返回子句
		return "SELECT COUNT(*) " + fromClause + whereClause;
	}
	
	
	//二、获取查询hql语句中？对应的查询条件“值”的集合
	public List<Object> getParameters(){
		
		return parameters;
	}
	
	
	
	
	
}
