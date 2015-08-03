/**
 * 
 */
package com.boliao.sunshine.biz.processor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.boliao.sunshine.biz.model.User;
import com.boliao.sunshine.biz.service.UserService;
import com.boliao.sunshine.biz.service.impl.UserServiceImpl;
import com.boliao.sunshine.mail.service.MailService;
import com.boliao.sunshine.mail.service.MailServiceImpl;
import com.boliao.sunshine.servlet.BaseServlet;
import com.boliao.sunshine.util.CacheUtil;
import com.boliao.sunshine.util.CommonConstants;
import com.boliao.sunshine.util.StrUtil;
import com.google.code.kaptcha.Constants;

/**
 * @author liaobo.lb
 * 
 */
public class UserProcessor extends BaseProcessor {

	/**
	 * 
	 */
	private final UserService userService;
	private final MailService mailService;
	private final CacheUtil cacheUtil;
	private final static String TITLE = "用户注册";

	public UserProcessor() {
		userService = UserServiceImpl.getInstance();
		mailService = MailServiceImpl.getInstance();
		cacheUtil = CacheUtil.getInstance();
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("m");
		if (StringUtils.isBlank(method)) {

		} else if (StringUtils.equals(method, "regPage")) {
			request.setAttribute("conFlag", 5);
			return "/views/index.jsp";
		} else if (StringUtils.equals(method, "loginPage")) {
			request.setAttribute("conFlag", 6);
			return "/views/index.jsp";
		} else if (StringUtils.equals(method, "register")) {
			this.regist(request, response);
		} else if (StringUtils.equals(method, "activate")) {
			this.activate(request, response);
		} else if (StringUtils.equals(method, "login")) {
			this.login(request, response);
		} else if (StringUtils.equals(method, "quite")) {
			this.quite(request, response);
		}
		return null;
	}

	private void quite(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("loginedUser");
		if (user != null) {
			request.getSession().removeAttribute("loginedUser");
			String redirectStr = "http://" + request.getServerName() + ":" + request.getServerPort() + BaseServlet.context + "/views/index.do";
			try {
				response.sendRedirect(redirectStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String valCode = request.getParameter("valCode");
		String realValCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		try {
			if (validateUsername(username) && StringUtils.isNotBlank(password)) {
				String valResult = validateValCode(valCode, realValCode);
				// 设置为空，防止表单重复提交
				request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, null);
				if (StringUtils.isNotBlank(valResult)) {
					ServletOutputStream out = response.getOutputStream();
					out.write(valResult.getBytes("utf-8"));
					out.flush();
					return;
				}
				User user = userService.getUser(username);
				if (user != null && user.getPassword().equals(password.trim())) {
					request.getSession().setAttribute("loginedUser", user);
					valResult = StrUtil.getResultJsonStr(true, null);
					ServletOutputStream out = response.getOutputStream();
					out.write(valResult.getBytes("utf-8"));
					out.flush();
					return;
				} else {
					valResult = StrUtil.getResultJsonStr(false, "用户名或者密码，输入错误！");
					ServletOutputStream out = response.getOutputStream();
					out.write(valResult.getBytes("utf-8"));
					out.flush();
					return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void activate(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("key");
		try {
			username = URLDecoder.decode(username, "UTF-8");
			if (validateUsername(username)) {
				User user = (User) cacheUtil.getElement(CommonConstants.RGT_USER_KEY, username);
				cacheUtil.removeCache(CommonConstants.RGT_USER_KEY, username);
				if (user != null) {
					userService.createUser(user);
					String redirectStr = "http://" + request.getServerName() + ":" + request.getServerPort() + BaseServlet.context + "/views/index.do";
					response.sendRedirect(redirectStr);
				}

			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void regist(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String rePassword = request.getParameter("rePassword");
		String nickname = request.getParameter("nickname");
		String birthday = request.getParameter("birthday");
		String valCode = request.getParameter("valCode");
		String realValCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		// 设置为空，防止表单重复提交
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, null);
		ServletOutputStream out;
		try {
			out = response.getOutputStream();
			// 如果取出的验证码为空，则有可能是重复提交，返回给浏览器空
			if (StringUtils.isEmpty(realValCode)) {
				out.write("".getBytes());
				out.flush();
				return;
			}
			String valResult = validate(username, password, rePassword, nickname, birthday, valCode, realValCode);
			if (StringUtils.isNotEmpty(valResult)) {
				out.write(valResult.getBytes("utf-8"));
				out.flush();
				return;
			}
			User user = new User();
			user.setEmail(username);
			user.setBirthDay(birthday);
			user.setUserName(nickname);
			user.setPassword(password);
			cacheUtil.putCache(CommonConstants.RGT_USER_KEY, user.getEmail(), user);
			String result = StrUtil.getResultJsonStr(true, null);
			if (prepareActivateMail(username, request)) {
				out.write(result.getBytes("utf-8"));
				out.flush();
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private boolean prepareActivateMail(String username, HttpServletRequest request) {
		String activateUrl;
		try {
			activateUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + BaseServlet.context + "/activate.do?type=user&m=activate&key="
					+ URLEncoder.encode(username, "UTF-8");
			boolean result = mailService.sendMailService("您好，这是sunshine新用户激活邮件", "请点击如下链接，激活您的账户：<a href=" + activateUrl + " target=_blank>" + activateUrl + "</a>", username);
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	private String validate(String username, String password, String rePassword, String nickname, String birthday, String valCode, String realValCode) {
		if (!validateUsername(username)) {
			return StrUtil.getResultJsonStr(false, "用户名格式不对，请使用邮箱");
		}
		if (username.length() > 20) {
			return StrUtil.getResultJsonStr(false, "用户名格式不对，字符串过长");
		}
		if (StringUtils.isEmpty(password) || !password.matches("^[a-zA-Z0-9\\+_]{6,20}$")) {
			return StrUtil.getResultJsonStr(false, "密码必须是0到9、大小写字母、+_或者它们的组合,密码长度6到20个字符！");
		}
		if (StringUtils.isEmpty(rePassword) || !password.equals(rePassword)) {
			return StrUtil.getResultJsonStr(false, "两次输入的密码不一致");
		}
		if (StringUtils.isEmpty(nickname) || nickname.length() > 20) {
			return StrUtil.getResultJsonStr(false, "昵称不能超过20个字符");
		}
		return validateValCode(valCode, realValCode);
	}

	private String validateValCode(String valCode, String realValCode) {
		if (StringUtils.isEmpty(valCode) || StringUtils.length(valCode) != 4) {
			return StrUtil.getResultJsonStr(false, "验证码格式不对");
		} else if (!valCode.equals(realValCode)) {
			return StrUtil.getResultJsonStr(false, "验证码不正确");
		}
		return null;
	}

	private boolean validateUsername(String username) {
		if (StringUtils.isEmpty(username) || !username.matches("[a-zA-Z0-9_\\+]{1,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}")) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		String str = "a444sl@nias.com";
		boolean result = str.matches("[a-zA-Z0-9_\\+]{1,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}");
		if (!result) {
			System.out.println(result);
		}
	}
}
