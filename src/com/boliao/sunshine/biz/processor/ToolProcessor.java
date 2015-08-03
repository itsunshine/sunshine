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

import com.boliao.sunshine.biz.utils.HttpUtil;
import com.boliao.sunshine.servlet.BaseServlet;

/**
 * @author liaobo
 * 
 */
public class ToolProcessor extends BaseProcessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String TITLE = "在线工具";

	private final String idnUrl = "http://api.k780.com:88/?appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json";

	public ToolProcessor() {
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
		request.setAttribute("type", BaseServlet.TOOL_KEY);
		request.setAttribute("conFlag", 4);
		if (StringUtils.isBlank(method)) {
			return getPages(request, response);
		} else if (StringUtils.equals(method, "search")) {
			search(request, response);
		}
		return null;
	}

	private String searchIp(HttpServletRequest request, HttpServletResponse response) {

		return null;
	}

	// 根据身份证号查询 城市位置
	private String search(HttpServletRequest request, HttpServletResponse response) {
		try {

			String identity = request.getParameter("value");
			if (StringUtils.isBlank(identity)) {
				writeToClient(response, "faild");
				return null;
			}
			String reqUrl = idnUrl + identity;
			String result = HttpUtil.getHtmlContent(reqUrl);
			if (StringUtils.isNotBlank(result)) {
				System.out.println(result);
				JSONObject job = JSONObject.fromObject(result);
				int flag = job.getInt("success");
				if (flag == 1)
					writeToClient(response, job.getString("result"));
				else
					writeToClient(response, "faild");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

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

	private String getContent(HttpServletRequest request, HttpServletResponse response) {
		return null;

	}

	private String getPages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("title", TITLE);
		return "/views/index.jsp";

	}

	private void getRegionFromNum() {/*
									 * Mobile m =
									 * MobileUtil.getMobileInfo("15996258283");
									 * System.out.println("号码：" + m.getNumber()
									 * + "\n省份：" + m.getProvince() + "\n城市：" +
									 * m.getCity() + "\n城市编号：" + m.getAreaCode()
									 * + "\n邮政编码：" + m.getPostCode() + "\n运营商："
									 * + m.getCorp() + "\n卡类型：" + m.getCard());
									 */
	}

}
