package com.zouensi.threadpoll;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPoolExecutorHandler {
	private static final int CORE_POOL_SIZE = 100;//�����̳߳���100
	private static final int MAXIMUM_POOL_SIZE = 100;//����̳߳���100
	private static final long KEEP_ALIVE_TIME = 0;//����ʱ��0
	private static final TimeUnit unit = TimeUnit.SECONDS;
	private static final ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(100);//��������100
	private static final LinkedBlockingQueue<Runnable> tempQueue = new LinkedBlockingQueue<Runnable>();//��ʱ������� 
	private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(100);//��ʱ�̳߳�
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
	//�ܾ�����
	private static final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
		
		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			try {
				tempQueue.put(r);
				if(flag) {//ִ��һ�ξ͹���
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
