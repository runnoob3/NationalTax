package cn.itcast.nsfw.complain.dao;

import java.util.List;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.complain.entity.Complain;

public interface ComplainDao extends BaseDao<Complain> {
	
	/**
	 * 根据年份获取统计数据的方法
	 * @param year     年份
	 * @return         对象数组
	 */
	public List<Object[]> getAnnualStatisticDataByYear(int year);
	
}
