<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
	
<%@ page import="com.boliao.sunshine.servlet.*"%>
	
<div class="span-11 main-column span-16">
	<div id="6ddceb1a-5917-47db-889c-7b85bee0e65f" class="fragment">
		<div class="body">
			<div id="login-form">

				<fieldset>
					<legend>
						用户登录
					</legend>
					<div>
						<label class="title">
							用户名
							<span class="required">*</span>
						</label>
						<br>
						<input id="username"
							name="j_username" class="text formmgr-field yiv-required">
					</div>
					<div>
						<label for="j_password" class="title">
							密码
							<span class="required">*</span>
						</label>
						<br>
						<input type="password"
							id="password"
							name="j_password" class="text formmgr-field yiv-required">
					</div>
					<div id="dialog" title="Dear You" style="display:none;">
  						<p></p>
					</div>
					<div>
						<label for="j_validation" class="title">
							验证码
							<span class="required">*</span>
						</label>
						<br>
						<input name="j_validation" type="text"
							id="valCode" maxlength="4"
							class="chknumber_input">
						<img src="/sunshine_new/kaptcha/validation.do" id="kaptchaImage"
							style="margin-bottom: -3px">
						<a href="#" onclick="changeCode()">看不清?换一张</a>
					</div>
					<div>
						<label for="rememberMe" class="title">
							记住我
						</label>
						<input type="checkbox" name="rememberMe" value="true"
							checked="checked">
					</div>
					<div>
						<button id="ajax_button" type="submit" class="button positive">
							<img src="pageUis/tick.png" alt="">
							登录
						</button>
						<button type="reset" class="button negative" id="clear">
							<img src="pageUis/cross.png" alt="">
							重置
						</button>
					</div>
				</fieldset>
				<input name="validation" type="hidden" value="validation">
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$(function() {
	    $("#clear").click(function(){
		    $("#username").val('');
		    $("#password").val('');
		    $("#valCode").val('');
		});
	  });
	$(function(){
		$("#ajax_button").click(function(){
			/* 获取表单数据 */
			var username = $("#username").val();
			var password = $("#password").val();
			var valCode = $("#valCode").val();
			
			var passwordPattern = /[a-zA-Z0-9_\+]{6,20}/g;
			/* 验证表单数据 */
			if ($.trim(username) == ''|| $.trim(password) == ''|| $.trim(valCode) == '') {
				jqueryUIAlter("非空字段必须填！");
												return false;
											}
											if (username.match(/@.+\..+/) == null) {
												jqueryUIAlter("用户名格式不对，请使用邮箱");
												return;
											}
											if(passwordPattern.exec(password) != password){
												jqueryUIAlter("密码必须是0到9、大小写字母、+_或者它们的组合,密码长度6到20个字符！");
												return;
											}
											if (valCode == ''
													|| valCode.length != 4) {
												jqueryUIAlter("验证码格式不对");
												return false;
											}
			var formData = {
						username:username,
						password:password,
						valCode:valCode,
					}
			
			$.ajax({
				url:"<%=BaseServlet.context %>/login.do?type=user&m=login",
				type:'post',
				dataType:'json',
				data:formData,
				success : function(resp) {
					if (!resp.isSuccess) {
						jqueryUIAlter(resp.errMsg);
						return;
					}else if(resp.isSuccess){
						jqueryUIAlter('登陆成功！');
						window.location.href="<%=BaseServlet.context %>/views/index.do"
					}
				}
			})
		});
	});
</script>
</div>
