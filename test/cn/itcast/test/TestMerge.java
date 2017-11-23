package cn.itcast.test;

/**
 * 测试项目是否能运行
 */
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.test.entity.Person;
import cn.itcast.test.service.TestService;

public class TestMerge {
	
	ClassPathXmlApplicationContext ctx=null;
	
	
	@Before
	public void loadCtx() {
		//加载Spring容器
		ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		
		//TestService ts=(TestService) ctx.getBean("testService");
		//ts.say();
	}
	
	
	/**
	 * 测试Spring
	 */
	@Test
	public void testSpring() {
		
		TestService ts=(TestService) ctx.getBean("testService");
		
		ts.say();
	}
	
	
	/**
	 * 测试Hibernate
	 */
	@Test
	public void testHibernate() {
		
		SessionFactory sf=(SessionFactory) ctx.getBean("sessionFactory");
		
		//创建一个Session
		Session session=sf.openSession();
		
		//开始一个事务
		Transaction transaction=session.beginTransaction();
		
		//数据库操作，保存人员
		session.save(new Person("人员1"));
		
		//提交事务
		transaction.commit();
		
		//关闭session
		session.close();
	}
	
	
	/**
	 * 测试Dao和Service：添加
	 */
	@Test
	public void testDaoAndService() {
		
		TestService ts=(TestService) ctx.getBean("testService");
		
		//添加
		ts.save(new Person("人员2"));
	}
	
	
	/**
	 * 测试Dao和Service
	 */
	@Test
	public void testDaoAndService2() {
		
		TestService ts=(TestService) ctx.getBean("testService");
		//查找
		System.out.println(ts.findPerson("ff8080815ebe861a015ebe861bac0000").getName());
	}
	
	
	/**
	 * 测试只读事务：如果在只读事务中出现更新则回滚
	 */
	@Test
	public void testReadonlyTransaction() {  
		
		TestService ts=(TestService) ctx.getBean("testService");
		
		//测试：在TestServiceImpl调用findPerson方法前添加：save(new Person("test"));
		//查找
		System.out.println(ts.findPerson("ff8080815ebe861a015ebe861bac0000").getName());
	}
	
	/**
	 * 测试回滚事务：如果在操作中出现人和异常则回滚先前的操作
	 */
	@Test
	public void testRollbackTransaction() {  
		
		//获取bean
		TestService ts=(TestService) ctx.getBean("testService");
		
		//测试：在TestServiceImpl调用save()方法后添加：int i=1/0;
		//添加
		ts.save(new Person("人员3"));
	}
	
	
}
