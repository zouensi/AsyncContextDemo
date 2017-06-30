package com.zouensi.servlet;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//urlPatterns是求情地址,asuncSupported是是否需要异步,默认为false
@WebServlet(urlPatterns ="/AsyncContextDemoServlet", asyncSupported=true)
public class AsyncContextDemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//线程池的创建
    private final static ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10,0,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10));
    
    public AsyncContextDemoServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置请求可以是异步的
		AsyncContext asyncContext = request.startAsync();
		executor.execute(new MyRunnbale(asyncContext));
		System.out.println("Servlet线程完事了");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	class MyRunnbale implements Runnable{
		private AsyncContext asyncContext;
		public MyRunnbale(AsyncContext asyncContext) {
			this.asyncContext = asyncContext;
		}
		@Override
		public void run() {
			try {
				System.out.println("自定义线程执行了");
				ServletResponse response = asyncContext.getResponse();
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("哈哈,你好啊,我是servlet异步任务加载");
			} catch (IOException e) {
				e.printStackTrace();
			}
			asyncContext.complete();
		}
		
	}

}
