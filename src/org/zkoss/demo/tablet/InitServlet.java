package org.zkoss.demo.tablet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.zkoss.demo.tablet.mock.MockServer;

public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException{
		MockServer foo = new MockServer();
//		new Thread(this).start();
	}

//	@Override
//	public void run() {
//		long sleep = 60*60*1000L;
//		while(true){
//			try {
//				Thread.sleep(sleep);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}
}
