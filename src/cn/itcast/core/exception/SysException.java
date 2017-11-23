package cn.itcast.core.exception;
/**
 * 全局异常： 自定义显示报错信息
 * @author Administrator
 * abstract: 抽象的，不让别人实例化
 *
 */
public abstract class SysException extends Exception {
	
	private String errorMsg;   //自定义变量记录异常信息
	
	//构造方法
	public SysException() {
		super();
	}

	public SysException(String message, Throwable cause) {
		super(message, cause);
		//设置值
		errorMsg=message;
	}

	public SysException(String message) {
		super(message);
		//设置值
		errorMsg=message;
	}

	public SysException(Throwable cause) {
		super(cause);
	}
	
	
	//getter()/setter()方法
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
	
	
}
