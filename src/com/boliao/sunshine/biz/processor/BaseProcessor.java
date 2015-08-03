package com.boliao.sunshine.biz.processor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基本处理器
 * @author liaobo
 *
 */
public abstract class BaseProcessor {
	 
	/**
	 * 处理方法
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public abstract String process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException;

}
