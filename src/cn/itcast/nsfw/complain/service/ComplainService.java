package cn.itcast.nsfw.complain.service;

import java.util.List;
import java.util.Map;

import cn.itcast.core.service.BaseService;
import cn.itcast.nsfw.complain.dao.ComplainDao;
import cn.itcast.nsfw.complain.entity.Complain;

public interface ComplainService extends BaseService<Complain> {
	
	//自动受理投诉(每个月月底最后一天对本月之前的投诉进行自动处理；将投诉信息的状态改为已失效)
	public void autoDeal();
	
	
	/**
	 * 根据年份获取统计年度每个月的投诉
	 * @param year    要统计的年份
	 * @return        统计数据
	 */
	public List<Map> getAnnualStatisticDataByYear(int year);
	
}
