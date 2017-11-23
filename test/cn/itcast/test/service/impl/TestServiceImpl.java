package cn.itcast.test.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.test.dao.TestDao;
import cn.itcast.test.entity.Person;
import cn.itcast.test.service.TestService;

@Service("testService")
public class TestServiceImpl implements TestService {
	
	@Resource
	private TestDao testDao;     //创建TestDao对象调用方法 
	
	@Override
	public void say() {
		
		System.out.println("Service saying hi");

	}
	
	
	//保存人员的方法--测试层级开发
	public void save(Person person) {
		
		//调用方法
		testDao.save(person);
		
		//int i=1/0;
	}
	
	
	
	//根据id查询人员
	public Person findPerson(Serializable id) {
		
		//save(new Person("test"));
		//调用方法
		return testDao.findPerson(id);
	}

}
