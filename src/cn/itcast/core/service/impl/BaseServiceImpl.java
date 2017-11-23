package cn.itcast.core.service.impl;

import java.io.Serializable;
import java.util.List;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.core.page.PageResult;
import cn.itcast.core.service.BaseService;
import cn.itcast.core.util.QueryHelper;

public class BaseServiceImpl<T> implements BaseService<T> {
	
	//创建BaseDao<T>对象调用方法
	private BaseDao<T> baseDao;
	
	//设置BaseDao,接收其他服务实现类的dao(user.role.info...)
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao=baseDao;
	}
	
	//新增
	public void save(T entity) {
		
		baseDao.save(entity);
	}

	//更新
	public void update(T entity) {
		
		baseDao.update(entity);
	}

	//根据id删除
	public void delete(Serializable id) {
		
		baseDao.delete(id);
	}

	//根据id查找
	public T findObjectById(Serializable id) {
		
		return baseDao.findObjectById(id);
	}

	//查找列表
	public List<T> findObjects() {
		
		return baseDao.findObjects();
	}
	
	
	
	//根据条件查询实体列表的方法(模糊查询)
	public List<T> findObjects(String hql, List<Object> parameters) {
		
		return baseDao.findObjects(hql, parameters);
	}

	
	//根据条件查询实体列表的方法(模糊查询,排序)--查询助手queryHelper
	public List<T> findObjects(QueryHelper queryHelper) {
		
		return baseDao.findObjects(queryHelper);
	}

	
	/**
	 * 查询信息列表(分页)
	 * @param queryHelper  查询助手
	 * @param pageNo       当前页
	 * @param pageSize     页大小
	 * @return
	 */
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize) {
		
		return baseDao.getPageResult(queryHelper, pageNo,pageSize);
	}
	
	
	
	
	
}
