package com.zouensi.servlet;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zouensi.runnable.RequestRunnableHandler;

import static com.zouensi.threadpoll.ThreadPoolExecutorHandler.*;//��̬����

@WebServlet(urlPatterns="/AsyncContestLogsHandler",asyncSupported= true)
public class AsyncContestLogsHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AsyncContestLogsHandler() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("AsyncContestLogsHandlerִ����");
		AsyncContext asyncContext = request.startAsync();
		executor.execute(new RequestRunnableHandler(asyncContext));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
