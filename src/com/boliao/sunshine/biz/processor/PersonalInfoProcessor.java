/**
 * 
 */
package com.boliao.sunshine.biz.processor;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.boliao.sunshine.biz.model.Education;
import com.boliao.sunshine.biz.model.JobExperiences;
import com.boliao.sunshine.biz.model.PersonBasicInfo;
import com.boliao.sunshine.biz.model.ProjExperiences;
import com.boliao.sunshine.biz.model.User;
import com.boliao.sunshine.biz.service.PersonalInfoService;
import com.boliao.sunshine.biz.service.impl.PersonalInfoServiceImpl;
import com.boliao.sunshine.servlet.BaseServlet;
import com.boliao.sunshine.util.StrUtil;

/**
 * @author liaobo
 * 
 */
public class PersonalInfoProcessor extends BaseProcessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String TITLE = "个人中心";

	private PersonalInfoService personalInfoService = null;

	public PersonalInfoProcessor() {
		personalInfoService = PersonalInfoServiceImpl.getInstance();
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
		request.setAttribute("type", BaseServlet.PERSONAL_INFO_INPUT);
		request.setAttribute("conFlag", 8);
		if (StringUtils.isBlank(method)) {
			return getPages(request, response);
		} else if (StringUtils.equals(method, "input")) {
			request.setAttribute("conFlag", 9);
			return "/views/index.jsp";
		} else if (StringUtils.equals(method, "register")) {
			registerBasicInfo(request, response);
		} else if (StringUtils.equals(method, "registerE")) {
			registerE(request, response);
		} else if (StringUtils.equals(method, "registerJ")) {
			registerJ(request, response);
		} else if (StringUtils.equals(method, "registerP")) {
			registerP(request, response);
		} else {
			writeToClient(response, StrUtil.getResultJsonStr(false, "错误的请求！"));
		}
		return null;
	}

	/**
	 * 注册项目经验
	 * 
	 * @param request
	 * @param response
	 */
	private void registerP(HttpServletRequest request, HttpServletResponse response) {
		String proStartTime = request.getParameter("proStartTime");
		String proEndTime = request.getParameter("proEndTime");
		String projectName = request.getParameter("projectName");
		String softEnv = request.getParameter("softEnv");
		String tools = request.getParameter("tools");
		String mainResp = request.getParameter("mainResp");
		String mainContr = request.getParameter("mainContr");

		ProjExperiences projExperiences = new ProjExperiences();
		projExperiences.setProStartTime(proStartTime);
		projExperiences.setProEndTime(proEndTime);
		projExperiences.setMainContr(mainContr);
		projExperiences.setMainResp(mainResp);
		projExperiences.setSoftEnv(softEnv);
		projExperiences.setTools(tools);
		projExperiences.setProjectName(projectName);
		User user = getUser(request);
		projExperiences.setUid(user.getId());

		personalInfoService.registerProExperiences(projExperiences);

		// 输出存储好的结果
		try {
			writeToClient(response, StrUtil.getResultJsonStr(true, null));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注册工作经验
	 * 
	 * @param request
	 * @param response
	 */
	private void registerJ(HttpServletRequest request, HttpServletResponse response) {
		String jobStartTime = request.getParameter("jobStartTime");
		String jobEndTime = request.getParameter("jobEndTime");
		String company = request.getParameter("company");
		String industry = request.getParameter("industry");
		String staffNums = request.getParameter("staffNums");
		String compType = request.getParameter("compType");
		String departMent = request.getParameter("departMent");
		String position = request.getParameter("position");
		String jobDesc = request.getParameter("jobDesc");

		JobExperiences jobEp = new JobExperiences();
		jobEp.setJobStartTime(jobStartTime);
		jobEp.setJobEndTime(jobEndTime);
		jobEp.setCompType(compType);
		jobEp.setDepartMent(departMent);
		jobEp.setIndustry(industry);
		jobEp.setPosition(position);
		jobEp.setJobDesc(jobDesc);
		jobEp.setCompany(company);

		if (StringUtils.isNotBlank(staffNums) && StringUtils.isNumeric(staffNums)) {
			jobEp.setStaffNums(Integer.valueOf(staffNums));
		}
		User user = this.getUser(request);
		jobEp.setUid(user.getId());
		personalInfoService.registerJobExperiences(jobEp);

		// 输出存储好的结果
		try {
			writeToClient(response, StrUtil.getResultJsonStr(true, null));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注册教育 经历
	 * 
	 * @param request
	 * @param response
	 */
	private void registerE(HttpServletRequest request, HttpServletResponse response) {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String school = request.getParameter("school");
		String mayor = request.getParameter("mayor");
		String proDesc = request.getParameter("proDesc");

		Education edu = new Education();
		edu.setEndTime(endTime);
		edu.setStartTime(startTime);
		edu.setSchool(school);
		edu.setMayor(mayor);
		edu.setProDesc(proDesc);
		User user = this.getUser(request);
		edu.setUid(user.getId());
		personalInfoService.registerPersonEduInfo(edu);

		// 输出存储好的结果
		try {
			writeToClient(response, StrUtil.getResultJsonStr(true, null));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 基本信息注册
	 * 
	 * @param request
	 */
	private void registerBasicInfo(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String sex = request.getParameter("sex");
		String birthDay = request.getParameter("birthDay");
		String phoneNum = request.getParameter("phoneNum");
		String email = request.getParameter("email");
		String salary = request.getParameter("salary");
		String marriage = request.getParameter("marriage");
		String height = request.getParameter("height");
		String weixin = request.getParameter("weixin");
		String qq = request.getParameter("qq");
		String skillDesc = request.getParameter("skillDesc");

		PersonBasicInfo pBasicInfo = new PersonBasicInfo();
		pBasicInfo.setUsername(username);
		pBasicInfo.setSex(Integer.parseInt(sex));
		pBasicInfo.setBirthDay(birthDay);
		pBasicInfo.setEmail(email);
		pBasicInfo.setPhoneNum(phoneNum);
		if (StringUtils.isNotBlank(salary) && StringUtils.isNumeric(salary))
			pBasicInfo.setSalary(Integer.parseInt(salary));
		if (StringUtils.isNotBlank(marriage) && StringUtils.isNumeric(marriage))
			pBasicInfo.setMarriage(Integer.parseInt(marriage));
		pBasicInfo.setQq(qq);
		pBasicInfo.setWeixin(weixin);
		if (StringUtils.isNotBlank(height) && StringUtils.isNumeric(height))
			pBasicInfo.setHeight(Integer.parseInt(height));
		pBasicInfo.setSkillDesc(skillDesc);

		User user = this.getUser(request);
		pBasicInfo.setUid(user.getId());

		personalInfoService.registerPersonBasicInfo(pBasicInfo);

		// 输出存储好的结果
		try {
			writeToClient(response, StrUtil.getResultJsonStr(true, null));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从session获取登陆用户信息
	 * 
	 * @param request
	 * @return
	 */
	private User getUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("loginedUser");
		return user;
	}

	// 将结果 写回客户端
	private void writeToClient(HttpServletResponse response, String result) throws IOException {
		OutputStream os = response.getOutputStream();
		try {
			os.write(result.getBytes("UTF-8"));
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			os.close();
		}

	}

	private String getPages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("title", TITLE);
		return "/views/index.jsp";

	}

}
