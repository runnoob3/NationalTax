package cn.itcast.nsfw.complain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuartzTask {
	
	//1. 简单触发器
	public void doSimpleTriggerTask() {
		//创建一个时间对象
		SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		//输出TimerTask
		System.out.println("doing simpleTriggerTask : "+sdf.format(new Date()));
		
	}
	
	
	//2. 任务触发器
	public void doCronTriggerTask() {
		//创建一个时间对象
		SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		//输出TimerTask
		System.out.println("doing cronTriggerTask : "+sdf.format(new Date()));
		
	}
	
}
