/**
 * 
 */
package com.boliao.sunshine.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

/**
 * 该类主要用于初始化 log4j的相关配置
 * 
 * @author liaobo
 * 
 */
public class Log4JInit extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8694624533076889292L;

	@Override
	public void init() throws ServletException {
		String prefix = getServletContext().getRealPath("/");
		System.setProperty("webappHome", prefix);
		String file = getServletConfig().getInitParameter("log4j-config-file");
		// 从Servlet参数读取log4j的配置文件
		if (file != null) {
			PropertyConfigurator.configure(prefix + file);

		}
		super.init();
	}
}
