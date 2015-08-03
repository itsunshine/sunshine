package com.boliao.sunshine.biz.processor;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.boliao.sunshine.biz.model.Article;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.service.ArticleService;
import com.boliao.sunshine.biz.service.impl.ArticleServiceImpl;
import com.boliao.sunshine.servlet.BaseServlet;

public class ArticleProcessor extends BaseProcessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ArticleService articleService;
	private final static String TITLE = "技术文章";

	public ArticleProcessor() {
		articleService = ArticleServiceImpl.getInstance();
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("m");
		request.setAttribute("type", BaseServlet.ART_KEY);
		if (StringUtils.isBlank(method)) {
			return getPages(request, response);
		} else if (StringUtils.equals(method, "getContent")) {
			return getContent(request, response);
		}
		return null;
	}

	private String getContent(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id) && id.matches("\\d+")) {
			Article article = articleService.findArticleById(Long.valueOf(id));
			request.setAttribute("content", article);
			request.setAttribute("conFlag", 1);
			return "/views/index.jsp";
		}
		return null;

	}

	private String getPages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PageBase<Article> page = new PageBase<Article>();
		String pageNo = request.getParameter("pageNo");
		if (StringUtils.isNotBlank(pageNo) && pageNo.trim().matches("\\d+")) {
			page.setPageNo(Integer.valueOf(pageNo.trim()));
		} else {
			page.setPageNo(1);
		}
		page = articleService.getPageArticleDesc("createTime", page);

		String ajax = request.getParameter("ajax");
		if (StringUtils.isNotBlank(ajax) && StringUtils.equals("true", ajax)) {
			OutputStream print = response.getOutputStream();
			JSONObject obj = JSONObject.fromObject(page);
			print.write(obj.toString().getBytes("UTF-8"));
			print.flush();
			return null;
		} else {
			String uri = request.getRequestURI();
			request.setAttribute("page", page);
			request.setAttribute("title", TITLE);
			request.setAttribute("uri", uri);
			return "/views/index.jsp";
		}

	}
}
