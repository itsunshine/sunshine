/** 日期中文显示 */
$(function() {
			$.datepicker.regional['zh-CN'] = {
				clearText : '清除',
				clearStatus : '清除已选日期',
				closeText : '关闭',
				closeStatus : '不改变当前选择',
				prevText : '<上月',
				prevStatus : '显示上月',
				prevBigText : '<<',
				prevBigStatus : '显示上一年',
				nextText : '下月>',
				nextStatus : '显示下月',
				nextBigText : '>>',
				nextBigStatus : '显示下一年',
				currentText : '今天',
				currentStatus : '显示本月',
				monthNames : ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月',
						'九月', '十月', '十一月', '十二月'],
				monthNamesShort : ['一', '二', '三', '四', '五', '六', '七', '八', '九',
						'十', '十一', '十二'],
				monthStatus : '选择月份',
				yearStatus : '选择年份',
				weekHeader : '周',
				weekStatus : '年内周次',
				dayNames : ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
				dayNamesShort : ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
				dayNamesMin : ['日', '一', '二', '三', '四', '五', '六'],
				dayStatus : '设置 DD 为一周起始',
				dateStatus : '选择 m月 d日, DD',
				dateFormat : 'yy-mm-dd',
				firstDay : 1,
				initStatus : '请选择日期',
				isRTL : false
			};
			$.datepicker.setDefaults($.datepicker.regional['zh-CN']);

		});
/* 生成验证码以及验证码相关 */
$(function() {
			$('#kaptchaImage').click(function() {// 生成验证码
						$(this).hide().attr(
								'src',
								sunContext + '/kaptcha/validation.do?'
										+ Math.floor(Math.random() * 100))
								.fadeIn();
						event.cancelBubble = true;
					});
		});
function changeCode() {
	$('#kaptchaImage').hide().attr(
			'src',
			sunContext + '/kaptcha/validation.do?'
					+ Math.floor(Math.random() * 100)).fadeIn();
	event.cancelBubble = true;
}

/* 提示框 */
function jqueryUIAlter(msgInfo) {
	$("#dialog p")
			.html("<p align='center' style='margin-top:30px;font-size:16px;'>"
					+ msgInfo + "</p>");
	$("#dialog").dialog({
				show : {
					effect : "blind",
					duration : 800
				}
			});
}
/* 判断字符串是否为空 */
function isNullOrEmpty(strVal) {
	if (strVal == '' || strVal == null || strVal == undefined) {
		return true;
	} else {
		return false;
	}
}

/* 验证手机号码 */
function validatemobile(mobile) {
	if (mobile.length == 0) {
		jqueryUIAlter('请输入手机号码！');
		return false;
	}
	if (mobile.length != 11) {
		jqueryUIAlter('请输入有效的手机号码！');
		return false;
	}

	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	if (!myreg.test(mobile)) {
		jqueryUIAlter('请输入有效的手机号码！');
		return false;
	}
	return true;
}

/* 验证email */
function validateEmail(email,prefix) {
	if (isNullOrEmpty(email)) {
		jqueryUIAlter(prefix + "必须填写！");
		return false;
	}
	if (!email
			.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
		jqueryUIAlter(prefix + "格式不正确！请重新输入");
		return false;
	}
	return true;
}

/* 发起ajax请求 */
function ajaxRequest(url,data,successMsg){
	$.ajax({
					url:url,
					type:'post',
					dataType:'json',
					data:data,
					success : function(resp) {
						if (!resp.isSuccess) {
							jqueryUIAlter(resp.errMsg);
							return;
						}else if(resp.isSuccess){
							jqueryUIAlter(successMsg);
						}
						//window.location.href = '<%=BaseServlet.context %>/' + 'views/index.do';
					}
				})
}