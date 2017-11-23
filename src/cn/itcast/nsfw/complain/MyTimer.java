package cn.itcast.nsfw.complain;

import java.util.Timer;

public class MyTimer {

	public static void main(String[] args) {
		//创建Timer对象
		Timer timer=new Timer();
		//创建一个TimerTask,延迟2秒执行，每3秒执行一次
		timer.schedule(new MyTimerTask(), 2000,3000);
	}

}
