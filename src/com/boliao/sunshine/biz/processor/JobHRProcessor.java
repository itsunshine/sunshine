/**
 * 
 */
package com.boliao.sunshine.biz.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boliao.sunshine.biz.model.JobDemandArt;
import com.boliao.sunshine.biz.model.PageBase;
import com.boliao.sunshine.biz.service.JobHRService;
import com.boliao.sunshine.biz.service.SearchService;
import com.boliao.sunshine.biz.service.impl.JobHRServiceImpl;
import com.boliao.sunshine.biz.service.impl.SearchServiceImpl;
import com.boliao.sunshine.servlet.BaseServlet;

/**
 * @author liaobo
 * 
 */
public class JobHRProcessor extends BaseProcessor {

	/** 日志记录器 */
	private static final Logger logger = Logger.getLogger(JobHRProcessor.class);

	/**
	 * 序列号版本号
	 */
	private static final long serialVersionUID = 1L;
	private final JobHRService jobHRService;
	private final static String TITLE = "互联网公司-招聘信息";
	/** 分页展示招聘内容的标识 */
	public final static String SHOW_FLAG = "hr";

	/** 提供搜索服务 */
	private final SearchService searchService;

	public JobHRProcessor() {
		jobHRService = JobHRServiceImpl.getInstance();
		searchService = SearchServiceImpl.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.boliao.sunshine.biz.processor.BaseProcessor#process(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("m");
		request.setAttribute("type", BaseServlet.HR_KEY);
		if (StringUtils.isBlank(method)) {
			return getPages(request, response);
		} else if (StringUtils.equals(method, "getContent")) {
			return getContent(request, response);
		} else if (StringUtils.equals(method, "ajax")) {
			ajaxSearch(request, response);
		}
		return null;
	}

	/**
	 * ajax查询请求
	 * 
	 * @param request
	 * @param response
	 */
	public void ajaxSearch(HttpServletRequest request, HttpServletResponse response) {

		try {
			PageBase<JobDemandArt> artPage = null;
			artPage = this.searchJobArtDemand(request, 1);
			JSONObject pages = JSONObject.fromObject(artPage);
			OutputStream ops = response.getOutputStream();
			if (pages == null || artPage.getTotalCount() == 0)
				pages = JSONObject.fromObject(this.constructErrorResp("查询结果为空"));
			ops.write(pages.toString().getBytes("UTF-8"));
			System.out.println(pages.toString());
			ops.flush();
			ops.close();
		} catch (IOException e) {
			logger.error("ajax搜索异常", e);
		}
	}

	private String getContent(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id) && id.matches("\\d+")) {
			JobDemandArt jobDemandArt = jobHRService.findJobDemandArtById(Long.valueOf(id));
			if (jobDemandArt != null) {
				request.setAttribute("jobDemandArt", jobDemandArt);
				request.setAttribute("conFlag", 7);
				return "/views/index.jsp";
			}
		}
		return null;

	}

	/**
	 * 获得工作内容页列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private String getPages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PageBase<JobDemandArt> page = new PageBase<JobDemandArt>();
		String pageNo = request.getParameter("pageNo");
		if (StringUtils.isNotBlank(pageNo) && pageNo.trim().matches("\\d+")) {
			page.setPageNo(Integer.valueOf(pageNo.trim()));
		} else {
			page.setPageNo(1);
		}
		String ajax = request.getParameter("ajax");
		String uri = null;
		if (StringUtils.isNotBlank(ajax) && StringUtils.equals("true", ajax)) {
			page = searchJobArtDemand(request, page.getPageNo());
			String location = request.getParameter("location");
			String company = request.getParameter("company");
			uri = request.getRequestURI();
			String keyword = request.getParameter("keywords");
			uri = uri + "?ajax=true";
			if (StringUtils.isNotBlank(keyword)) {
				uri = uri + "&keywords=" + keyword;
				request.setAttribute("keywords", keyword);
			}
			if (StringUtils.isNotBlank(location)) {
				uri = uri + "&location=" + location;
				request.setAttribute("location", location);
			}
			if (StringUtils.isNotBlank(company)) {
				uri = uri + "&company=" + company;
				request.setAttribute("company", company);
			}

			request.setAttribute("ajax", "true");
		} else {
			page = jobHRService.getPageJobDemandArtDesc("createTime", page);
			formatCreateDate(page);
			uri = request.getRequestURI();
		}
		request.setAttribute("page", page);
		request.setAttribute("title", TITLE);
		request.setAttribute("showFlag", SHOW_FLAG);
		request.setAttribute("uri", uri);
		return "/views/index.jsp";

	}

	/**
	 * 搜索职位信息
	 * 
	 * @param keywords
	 */
	private PageBase<JobDemandArt> searchJobArtDemand(HttpServletRequest request, int page) {
		StringBuilder sb = null;
		Map<String, String> whereMap = null;
		boolean isTextSearch = false;
		String keyword = request.getParameter("keywords");
		String location = request.getParameter("location");
		String company = request.getParameter("company");

		if (StringUtils.isNotBlank(keyword)) {
			sb = new StringBuilder();
			sb.append(keyword).append(" ");
			isTextSearch = true;
		}
		if (StringUtils.isNotBlank(location)) {
			if (isTextSearch) {
				sb.append(location).append(" ");
			} else {
				if (whereMap == null) {
					whereMap = new HashMap<String, String>();
				}
				whereMap.put("location", location);
			}

		}
		if (StringUtils.isNotBlank(company)) {
			if (isTextSearch) {
				sb.append(company);
			} else {
				if (whereMap == null) {
					whereMap = new HashMap<String, String>();
				}
				whereMap.put("companyName", company + "公司");
			}
		}
		PageBase<JobDemandArt> artPage = new PageBase<JobDemandArt>();
		if (isTextSearch) {
			artPage = searchService.searchIndex(sb.toString(), BaseServlet.HR_KEY, page);
		} else {
			artPage.setPageNo(page);
			artPage = jobHRService.queryPageJobDesc(whereMap, "createTime", artPage);
		}

		formatCreateDate(artPage);
		return artPage;
	}

	/**
	 * 格式化创建日期的格式化字符串
	 * 
	 * @param page
	 */
	private void formatCreateDate(PageBase<JobDemandArt> page) {
		if (null == page || page.getTotalCount() == 0) {
			return;
		}
		// 只要创建字符串里的前8位。
		List<JobDemandArt> results = page.getResults();
		for (JobDemandArt jobDemandArt : results) {
			formatCreateDateToJDA(jobDemandArt);
		}
		page.setResults(results);
	}

	/**
	 * 将jobDemandArt里的创建日期进行格式化
	 * 
	 * @param jobDemandArt
	 */
	private void formatCreateDateToJDA(JobDemandArt jobDemandArt) {
		jobDemandArt.setCreateTime(jobDemandArt.getCreateTime().substring(0, 10));
	}

	/**
	 * 返回错误提示的json字符串
	 * 
	 * @param errorMsg
	 * @return
	 */
	private String constructErrorResp(String errorMsg) {
		JSONObject obj = new JSONObject();
		obj.accumulate("error", errorMsg);
		return obj.toString();
	}
}
