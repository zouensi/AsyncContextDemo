package com.zouensi.runnable;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;

public class RequestRunnableHandler implements Runnable {
	private AsyncContext asyncContext = null;
	public RequestRunnableHandler(AsyncContext asyncContext) {
		this.asyncContext = asyncContext;
	}
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+"��ʼִ������");
		ServletResponse response = asyncContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		try {
			response.getWriter().print("�̳߳�����ʵ�����"+Thread.currentThread().getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		asyncContext.complete();//ִ�����
	}

}
