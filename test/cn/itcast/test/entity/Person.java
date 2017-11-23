package cn.itcast.test.entity;

import java.io.Serializable;

public class Person implements Serializable {
	
	private String id;
	
	private String name;
	
	
	//无参构造方法
	public Person() {
		super();
	}
	
	//一个参数构造方法
	public Person(String name) {
		super();
		this.name = name;
	}
	
	//两个参数构造方法
	public Person(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
	//getter()/setter()方法
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
