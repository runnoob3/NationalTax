package cn.itcast.test.action;

import javax.annotation.Resource;

import cn.itcast.test.service.TestService;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class TestAction extends ActionSupport {
	
	@Resource
	private TestService testService;
	
	public TestService getTestService() {
		return testService;
	}
	public void setTestService(TestService testService) {
		this.testService = testService;
	}


	public String execute(){
		//调用方法
		testService.say();
		
		return SUCCESS;
	}
}
