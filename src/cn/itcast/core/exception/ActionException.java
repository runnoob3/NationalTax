package cn.itcast.core.exception;

public class ActionException extends SysException {
	
	
	
	//构造方法
	public ActionException() {
		super("请求发生错误！");
	}

	public ActionException(String message) {
		super(message);
	}
	
	
	
	
}
