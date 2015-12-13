package com.boliao.sunshine.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boliao.sunshine.biz.model.JobCity;
import com.boliao.sunshine.biz.model.JobCorporation;
import com.boliao.sunshine.biz.processor.ArticleProcessor;
import com.boliao.sunshine.biz.processor.BaseProcessor;
import com.boliao.sunshine.biz.processor.DocProcessor;
import com.boliao.sunshine.biz.processor.JobHRProcessor;
import com.boliao.sunshine.biz.processor.PersonalInfoProcessor;
import com.boliao.sunshine.biz.processor.QuestionProcessor;
import com.boliao.sunshine.biz.processor.ToolProcessor;
import com.boliao.sunshine.biz.processor.UserProcessor;
import com.boliao.sunshine.biz.service.JobCityService;
import com.boliao.sunshine.biz.service.JobCorperationService;
import com.boliao.sunshine.biz.service.impl.JobCityServiceImpl;
import com.boliao.sunshine.biz.service.impl.JobCorperationServiceImpl;

/**
 * 基本的servlet处理器
 * 
 * @author liaobo
 * 
 */
public class BaseServlet extends HttpServlet {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -9136886826640625745L;

	// 日志记录器
	protected static final Logger logger = Logger.getLogger(BaseServlet.class);

	// 所有的城市集合
	public static List<JobCity> cityList = new ArrayList<JobCity>();

	// 所有的公司集合
	public static List<JobCorporation> corporationList = new ArrayList<JobCorporation>();

	// 项目所在的绝对路径
	public static String realPath = null;

	// 项目的context

	public static String context = null;
	// 处理器的键
	public static final String ART_KEY = "article";
	public static final String QUE_KEY = "question";
	public static final String MAIN_KEY = "main";
	public static final String DOC_KEY = "doc";
	public static final String TOOL_KEY = "tool";
	public static final String USER_KEY = "user";
	public static final String HR_KEY = "hr";
	/** 校内招聘的type */
	public static final String HR_UNI_KEY = "hrUni";

	/** 个人信息的type */
	public static final String PERSONAL_INFO_INPUT = "personalInfoInput";

	/** jobCity的服务类 */
	JobCityService jobCityService = JobCityServiceImpl.getInstance();

	/** jobCorporation的服务类 */
	JobCorperationService jobCorporationService = JobCorperationServiceImpl.getInstance();

	// 处理器集合
	Map<String, BaseProcessor> processMap = new HashMap<String, BaseProcessor>();

	// 处理器链表
	private final List<BaseProcessor> processorList = new ArrayList<BaseProcessor>();

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
		if (StringUtils.isBlank(context) || StringUtils.isBlank(realPath)) {
			context = request.getContextPath();
			realPath = request.getRealPath("/");
		}
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		BaseProcessor processor = findProcessorByName(request);
		request.setAttribute("context", context);
		if (processor != null) {
			String viewStr = processor.process(request, response);
			request.setAttribute("viewFlag", 1);
			if (StringUtils.isNotBlank(viewStr)) {
				request.getRequestDispatcher(viewStr).forward(request, response);
			}
		} else {
			List<List<Object>> results = new ArrayList<List<Object>>();
			for (BaseProcessor p : processorList) {
				p.process(request, response);
				List<Object> list = new ArrayList<Object>();
				String type = (String) request.getAttribute("type");
				// 为了社招信息和校招信息暂时兼容,有了解决方案将下面代码删除掉
				if (StringUtils.isBlank(type)) {
					type = this.HR_KEY;
				}
				list.add(type);
				request.removeAttribute("type");
				String title = (String) request.getAttribute("title");
				list.add(title);
				request.removeAttribute("title");
				Object obj = request.getAttribute("page");
				list.add(obj);
				request.removeAttribute("page");
				results.add(list);
			}
			request.setAttribute("type", MAIN_KEY);
			request.setAttribute("results", results);
			request.setAttribute("conFlag", 2);
			request.getRequestDispatcher("/views/index.jsp").forward(request, response);
		}

	}

	/**
	 * 获取相应的处理器
	 * 
	 * @param name
	 * @return
	 */
	private BaseProcessor findProcessorByName(HttpServletRequest request) {
		String key = request.getParameter("type");
		if (StringUtils.isNotBlank(key)) {
			BaseProcessor processor = processMap.get(key);
			return processor;
		}
		return null;
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

	@Override
	public void init() throws ServletException {
		realPath = this.getServletContext().getRealPath("/");
		context = this.getServletContext().getContextPath();
		initProcessorMap();
		cityList = jobCityService.queryAllJobCity();
		corporationList = jobCorporationService.queryAllJobCorporation();
		this.getServletContext().setAttribute("cityList", cityList);
		this.getServletContext().setAttribute("corporationList", corporationList);
		super.init();
	}

	/**
	 * 初始化，处理器集合
	 */
	private void initProcessorMap() {
		BaseProcessor articleProcessor = new ArticleProcessor();
		processMap.put(ART_KEY, articleProcessor);
		BaseProcessor questionProcessor = new QuestionProcessor();
		processMap.put(QUE_KEY, questionProcessor);
		BaseProcessor docProcessor = new DocProcessor();
		processMap.put(DOC_KEY, docProcessor);
		BaseProcessor toolProcessor = new ToolProcessor();
		processMap.put(TOOL_KEY, toolProcessor);
		BaseProcessor userProcessor = new UserProcessor();
		processMap.put(USER_KEY, userProcessor);
		BaseProcessor hrProcessor = new JobHRProcessor();
		processMap.put(HR_KEY, hrProcessor);
		processMap.put(HR_UNI_KEY, hrProcessor);
		BaseProcessor personalInfoProcessor = new PersonalInfoProcessor();
		processMap.put(PERSONAL_INFO_INPUT, personalInfoProcessor);

		// 向处理器链表里，按顺序加入需要的处理器，注意这里的添加顺序，会影响前台的展示顺序
		processorList.add(hrProcessor);
		// processorList.add(articleProcessor);
		processorList.add(docProcessor);
		// processorList.add(questionProcessor);
		processorList.add(toolProcessor);
	}
}
