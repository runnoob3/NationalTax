package cn.itcast.core.exception;

/**
 * 服务层异常: 自定义显示报错信息
 * @author Administrator
 *
 */
public class ServiceException extends SysException {
	
	
	//构造方法
	public ServiceException() {
		super("业务操作失败！");
	}

	public ServiceException(String message) {
		super(message);
	}
	
	
	
	
}
