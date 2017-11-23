package cn.itcast.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.core.page.PageResult;
import cn.itcast.core.util.QueryHelper;

public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
	
	//定义一个类
	Class<T> clazz;
	
	
	//构造方法
	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		
		//相当于获取到BaseDaoImpl<user>
		ParameterizedType pt=(ParameterizedType) this.getClass().getGenericSuperclass();
		
		clazz=(Class<T>) pt.getActualTypeArguments()[0];
	}
	
	
	//重写新增的方法
	@Override
	public void save(T entity) {
		
		getHibernateTemplate().save(entity);

	}
	
	
	//重写更新的方法
	@Override
	public void update(T entity) {
		
		getHibernateTemplate().update(entity);

	}
	
	
	//重写删除的方法
	@Override
	public void delete(Serializable id) {
		
		getHibernateTemplate().delete(findObjectById(id));

	}
	
	
	//重写根据id查找的方法
	@Override
	public T findObjectById(Serializable id) {
		
		return getHibernateTemplate().get(clazz, id);
	}
	
	
	//查找列表的方法
	@SuppressWarnings("unchecked")
	public List<T> findObjects() {
		
		//From后面加空格，getSimpleName():返回源代码中给出的底层类的简单类名(From 表)
		Query query=getSession().createQuery("FROM "+clazz.getSimpleName());
		
		return query.list();
	}
	
	
	
	//根据条件查询实体列表的方法(模糊查询)
	public List<T> findObjects(String hql,List<Object> parameters) {
		
		Query query=getSession().createQuery(hql);   //格式: hql="FROM Info WHERE title like ? and state = ? order by createTime";
		if(parameters !=null){
			for(int i=0;i<parameters.size();i++){
				//设置？参数值
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	
	
	//根据条件查询实体列表的方法(模糊查询,排序)--查询助手queryHelper
	public List<T> findObjects(QueryHelper queryHelper) {
		//获取查询hql语句
		Query query=getSession().createQuery(queryHelper.getQueryListHql());
		//获取查询条件“值”的集合
		List<Object> parameters=queryHelper.getParameters();
		if(parameters !=null){
			for(int i=0;i<parameters.size();i++){
				//设置？参数值
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	
	
	/**
	 * 查询信息列表(分页)
	 * @param queryHelper  查询助手
	 * @param pageNo       当前页
	 * @param pageSize     页大小
	 * @return
	 */
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize) {
		//1. 查询信息列表
		//获取查询hql语句
		Query query=getSession().createQuery(queryHelper.getQueryListHql());
		//获取查询条件“值”的集合
		List<Object> parameters=queryHelper.getParameters();
		if(parameters !=null){
			for(int i=0;i<parameters.size();i++){
				//设置？参数值
				query.setParameter(i, parameters.get(i));
			}
		}
		if(pageNo<1){
			pageNo=1;
		}
		
		//设置数据起始索引号(每页从第几条开始)
		query.setFirstResult((pageNo-1)*pageSize);
		//设置页大小
		query.setMaxResults(pageSize);
		//查找列表
		List items = query.list();
		
		//2. 获取总记录数
		//获取查询hql语句
		Query queryCount=getSession().createQuery(queryHelper.getQueryCountHql());
		//获取查询条件“值”的集合
		if(parameters !=null){
			for(int i=0;i<parameters.size();i++){
				//设置？参数值
				queryCount.setParameter(i, parameters.get(i));
			}
		}
		//返回总记录数
		long totalCount=(Long) queryCount.uniqueResult();
		
		return new PageResult(totalCount, pageNo, pageSize, items);
	}
	
	
	
}
