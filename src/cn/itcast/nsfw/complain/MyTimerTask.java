package cn.itcast.nsfw.complain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {

	//线程
	public void run() {
		//创建一个时间对象
		SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		//输出TimerTask
		System.out.println("doing TimerTask"+sdf.format(new Date()));
	}

}
