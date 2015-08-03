/**
 * 
 */
package com.boliao.sunshine.biz.processor;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.boliao.sunshine.biz.model.Doc;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.service.DocService;
import com.boliao.sunshine.biz.service.impl.DocServiceImpl;
import com.boliao.sunshine.servlet.BaseServlet;

/**
 * @author liaobo
 * 
 */
public class DocProcessor extends BaseProcessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final DocService docService;
	private final static String TITLE = "在线文档";

	public DocProcessor() {
		docService = DocServiceImpl.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.processor.BaseProcessor#process(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("m");
		request.setAttribute("type", BaseServlet.DOC_KEY);
		if (StringUtils.isBlank(method)) {
			return getPages(request, response);
		} else if (StringUtils.equals(method, "getContent")) {
			return getContent(request, response);
		}
		return null;
	}

	private String getContent(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id) && id.matches("\\d+")) {
			Doc doc = docService.findDocById(Long.valueOf(id));
			try {
				response.sendRedirect(BaseServlet.context + doc.getContent());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	private String getPages(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PageBase<Doc> page = new PageBase<Doc>();
		String pageNo = request.getParameter("pageNo");
		if (StringUtils.isNotBlank(pageNo) && pageNo.trim().matches("\\d+")) {
			page.setPageNo(Integer.valueOf(pageNo.trim()));
		} else {
			page.setPageNo(1);
		}
		page = docService.getPageDocDesc("createTime", page);

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
