package com.boliao.sunshine.biz.processor;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.boliao.sunshine.biz.model.Answer;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.model.Question;
import com.boliao.sunshine.biz.service.QuestionService;
import com.boliao.sunshine.biz.service.impl.QuestionServiceImpl;
import com.boliao.sunshine.servlet.BaseServlet;

public class QuestionProcessor extends BaseProcessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final QuestionService questionService;
	/**
	 * 答案的service
	 */
	private final static String TITLE = "问答";

	public QuestionProcessor() {
		questionService = QuestionServiceImpl.getInstance();
	}

	@Override
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("m");
		request.setAttribute("type", BaseServlet.QUE_KEY);
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
			Question question = questionService.findQuestionById(Long
					.valueOf(id));
			Answer answer = questionService.findAnswerByQuestionId(Long
					.valueOf(id));
			request.setAttribute("question", question);
			request.setAttribute("answer", answer);
			request.setAttribute("conFlag", 3);
			return "/views/index.jsp";
		}
		return null;

	}

	private String getPages(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PageBase<Question> page = new PageBase<Question>();
		String pageNo = request.getParameter("pageNo");
		if (StringUtils.isNotBlank(pageNo) && pageNo.trim().matches("\\d+")) {
			page.setPageNo(Integer.valueOf(pageNo.trim()));
		} else {
			page.setPageNo(1);
		}
		page = questionService.getPageQuestionDesc("createTime", page);

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
