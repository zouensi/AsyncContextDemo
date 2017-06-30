package com.zouensi.threadpoll;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPoolExecutorHandler {
	private static final int CORE_POOL_SIZE = 100;//核心线程池数100
	private static final int MAXIMUM_POOL_SIZE = 100;//最大线程池数100
	private static final long KEEP_ALIVE_TIME = 0;//保活时间0
	private static final TimeUnit unit = TimeUnit.SECONDS;
	private static final ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(100);//阻塞队列100
	private static final LinkedBlockingQueue<Runnable> tempQueue = new LinkedBlockingQueue<Runnable>();//临时缓冲队列 
	private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(100);//延时线程池
	private static boolean flag = true;
	static class MyRunnable implements Runnable{

		@Override
		public void run() {
			Runnable runnable = tempQueue.poll();
			if(runnable!=null) {
				executor.execute(runnable);
			}
		}
	}
	//拒绝策略
	private static final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
		
		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			try {
				tempQueue.put(r);
				if(flag) {//执行一次就够了
					scheduledExecutorService.scheduleWithFixedDelay(new MyRunnable(), 0, 1000, TimeUnit.MILLISECONDS);
					flag = false;
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	public static final ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, unit, workQueue, handler);
}
