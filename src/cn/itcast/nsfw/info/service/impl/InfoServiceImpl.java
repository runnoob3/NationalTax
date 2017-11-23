package cn.itcast.nsfw.info.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.nsfw.info.dao.InfoDao;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.info.service.InfoService;

@Service("infoService")
public class InfoServiceImpl extends BaseServiceImpl<Info> implements InfoService {
	
	/*
	 * 1. 注入infoDao调用方法,抽取service后不用这种方法
	 * @Resource
	 * private InfoDao infoDao; 创建infoDao对象调用方法
	 */
	
	
	/**
	 * 2. 通过set()方法注入infoDao
	 * @param infoDao
	 */
	private InfoDao infoDao;
	
	@Resource
	public void setInfoDao(InfoDao infoDao) {
		
		//注入的同时把infoDao设置给BaseDao
		super.setBaseDao(infoDao);
		
		this.infoDao = infoDao;
	}
	
	//基础的增删改查都继承公共类BaseServiceImpl<T>
	
	
	
}
