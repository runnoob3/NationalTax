package cn.itcast.nsfw.complain.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.complain.dao.ComplainDao;
import cn.itcast.nsfw.complain.entity.Complain;
import cn.itcast.nsfw.complain.service.ComplainService;

@Service("complainService")
public class ComplainServiceImpl extends BaseServiceImpl<Complain> implements ComplainService {
	
	/**
	 * 1. 通过set()方法注入complainDao
	 * @param infoDao
	 */
	private ComplainDao complainDao;
	
	
	@Resource
	public void setComplainDao(ComplainDao complainDao) {
		//注入的同时把complainDao设置给BaseDao
		super.setBaseDao(complainDao);
		this.complainDao = complainDao;
	}
	
	
	//自动受理投诉(每个月月底最后一天对本月之前的投诉进行自动处理；将投诉信息的状态改为已失效)
	public void autoDeal() {
		
		//JDK自带日历工具类
		Calendar cal =Calendar.getInstance();    //获取当前时间
		
		//cal.add(Calendar.MONTH, -2);        //上两个月
		
		//改变月份的日期为1日
		cal.set(Calendar.DAY_OF_MONTH, 1);  //设置当前时间的日期为1号
		//改变日的日期为0时
		cal.set(Calendar.HOUR_OF_DAY, 0);   //设置当前时间的日期为1号,0时
		//改变分钟为0时
		cal.set(Calendar.MINUTE, 0);        //设置当前时间的日期为1号,0时,0分
		//改变秒为0秒
		cal.set(Calendar.SECOND, 0);        //设置当前时间的日期为1号,0时,0分,0秒
		
		
		//1、查询本月之前的待受理投诉列表;"c":别名
		QueryHelper queryHelper=new QueryHelper(Complain.class, "c");
		//状态
		queryHelper.addCondition("c.state=?", Complain.COMPLAIN_STATE_UNDONE);
		//时间(本月之前: 本月1号0时0分0秒)
		queryHelper.addCondition("c.compTime < ?", cal.getTime());
		//查询本月之前的待受理投诉列表
		List<Complain> list=findObjects(queryHelper);
		
		if(list != null && list.size()>0){
			//2、更新投诉信息的状态为已失效
			for(Complain comp:list){
				//把状态改为失效
				comp.setState(Complain.COMPLAIN_STATE_INVALID);
				update(comp);
			}
		}
	}


	/**
	 * 根据年份获取统计年度每个月的投诉
	 * @param year    要统计的年份
	 * @return        统计数据
	 */
	public List<Map> getAnnualStatisticDataByYear(int year) {
		
		List<Map> resList=new ArrayList<Map>();
		
		//1. 获取统计数据
		List<Object[]> list=complainDao.getAnnualStatisticDataByYear(year);
		if(list !=null && list.size()>0){
			
			Calendar cal=Calendar.getInstance();   //获取当前日期
			
			//是否当前年份
			boolean isCurYear= (cal.get(Calendar.YEAR ) == year);
			//当前月份
			int curMonth=cal.get(Calendar.MONTH) + 1;  //当前月份
			
			//2. 格式化统计结果,遍历结果集
			int temMonth=0;            //临时月份
			
			Map<String,Object> map=null;
			
			for(Object[] obj:list){
				
				temMonth= Integer.valueOf((obj[0])+"");
				//当前年份
				map=new HashMap<String,Object>();
				map.put("label",temMonth+"月");  
				
				if(isCurYear){   //①当前年份
					//当前年份：如果月份已过的则直接取投诉数并且值为空/null时，投诉数设为0, 如果月份未过则全部投诉值置空
					if(temMonth > curMonth){    //1. 未到月份，则全部投诉值置空
						map.put("value", "");   //置空
						
					}else{                      //2. 已过月份
						map.put("value", obj[1]==null ? "0":obj[1]);
					}
				}else{           //②非当前年份：直接取投诉数并且值为空/null时投诉数设为0
					map.put("value", obj[1]==null ? "0":obj[1]);
				}
				//添加到返回结果
				resList.add(map);
			}
			
		}
		return resList;
	}
	
	
}
