/**
 * 
 */
package com.boliao.sunshine.util;

import net.sf.json.JSONObject;

/**
 * @author liaobo
 * 
 */
public class StrUtil {

	/**
	 * 将oldStr的首字母，转换成大写
	 * 
	 * @param oldStr
	 * @return
	 */
	public static String upFirstChar(String oldStr) {
		char[] b = oldStr.toCharArray();
		if (b[0] >= 97 && b[0] <= 122) {
			b[0] = (char) (b[0] - 32);
		}
		return new String(b);
	}

	/**
	 * 生成ajax需要的结果码，json格式的
	 * 
	 * @param result
	 * @param errorMessages
	 * @return
	 */
	public static String getResultJsonStr(boolean result, String errorMessages) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("isSuccess", result);
		if (!result) {
			jsonObj.put("errMsg", errorMessages);
		}
		return jsonObj.toString();
	}

}
