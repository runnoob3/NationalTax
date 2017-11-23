package cn.itcast.core.service;
/*
 * 抽取服务类公共类
 * 
 */
import java.io.Serializable;
import java.util.List;

import cn.itcast.core.page.PageResult;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;

public interface BaseService<T> {
	
	//新增
	public void save(T entity);
		
	//更新
	public void update(T entity);
		
	//根据id删除
	public void delete(Serializable id);
		
	//根据id查找
	public T findObjectById(Serializable id);
		
	//查找列表
	public List<T> findObjects();
	
	
	//根据条件查询实体列表的方法(模糊查询)
	@Deprecated  //不推荐使用，使用查询助手QueryHelper
	public List<T> findObjects(String hql,List<Object> parameters);
	
	//根据条件查询实体列表的方法(模糊查询,排序)--查询助手queryHelper
	public List<T> findObjects(QueryHelper queryHelper);
	
	/**
	 * 查询信息列表(分页)
	 * @param queryHelper  查询助手
	 * @param pageNo       当前页
	 * @param pageSize     页大小
	 * @return
	 */
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize);
	
	
	
}
