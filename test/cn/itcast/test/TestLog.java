package cn.itcast.test;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 测试日志
 */
import org.junit.Test;


public class TestLog {

	@Test
	public void testLog() {
		
		//获取Log
		Log log=LogFactory.getLog(getClass());
		
		//可以在log4j.properties配置文件中配置日志级别
		log.debug("debug级别日志");
		log.info("info级别日志");
		log.warn("warn级别日志");
		log.error("error级别日志");
		log.fatal("fatal级别日志");
		
	}

}
