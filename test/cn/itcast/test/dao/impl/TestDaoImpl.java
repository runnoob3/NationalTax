package cn.itcast.test.dao.impl;

import java.io.Serializable;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.test.dao.TestDao;
import cn.itcast.test.entity.Person;

public class TestDaoImpl extends HibernateDaoSupport implements TestDao{

	//保存人员的方法--测试层级开发
	public void save(Person person) {
		
		//保存
		getHibernateTemplate().save(person);
		
	}
	
	
	//根据id查询人员
	public Person findPerson(Serializable id) {
		
		//查找
		return getHibernateTemplate().get(Person.class, id);
	}
	
	
	
}
