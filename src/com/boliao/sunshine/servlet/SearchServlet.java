package com.boliao.sunshine.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.boliao.sunshine.biz.model.BaseModel;
import com.boliao.sunshine.biz.model.JobDemandArt;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.service.SearchService;
import com.boliao.sunshine.biz.service.impl.SearchServiceImpl;
import com.boliao.sunshine.common.CommonConstants;

public class SearchServlet extends HttpServlet {

	SearchService searchService;

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		PageBase<BaseModel> page = new PageBase<BaseModel>();
		String pageNo = request.getParameter("pageNo");
		if (StringUtils.isNotBlank(pageNo) && pageNo.trim().matches("\\d+")) {
			page.setPageNo(Integer.valueOf(pageNo.trim()));
		} else {
			page.setPageNo(1);
		}

		String keywords = request.getParameter("keywords");
		String type = request.getParameter("type");
		if (StringUtils.isNotBlank(keywords) && !StringUtils.equals(type, BaseServlet.HR_KEY)) {
			page = searchService.searchIndex(keywords, type, page);
			request.setAttribute("page", page);
			request.setAttribute("index", true);
			request.setAttribute("title", "搜索到相关结果（" + page.getTotalCount() + "）");
			request.getRequestDispatcher("/views/index.jsp").forward(request, response);
		} else if (StringUtils.isNotBlank(keywords) && StringUtils.equals(type, BaseServlet.HR_KEY)) {
			PageBase<JobDemandArt> artPage = new PageBase<JobDemandArt>();
			artPage = searchService.searchIndex(keywords, type, page.getPageNo());
			request.setAttribute("page", artPage);
			String uri = request.getRequestURI();
			uri += "?" + CommonConstants.KEY_WORDS + "=" + keywords;
			// request.setAttribute("index", true);
			request.setAttribute("type", BaseServlet.HR_KEY);
			request.setAttribute(CommonConstants.VIEW_FLAG, 1);
			request.setAttribute(CommonConstants.KEY_WORDS, keywords);
			request.setAttribute("uri", uri);
			// request.setAttribute("showFlag", JobHRProcessor.SHOW_FLAG);
			request.setAttribute("title", "搜索到相关结果（" + artPage.getTotalCount() + "）");
			request.getRequestDispatcher("/views/index.jsp").forward(request, response);
		}

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		searchService = SearchServiceImpl.getInstance();
	}

}
