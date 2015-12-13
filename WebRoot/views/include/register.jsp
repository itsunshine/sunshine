<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.boliao.sunshine.servlet.*"%>

<div class="span-11 main-column span-16">
	<div id="3c00cd15-a374-4f18-823d-b19fcc7dd649" class="fragment">
		<div class="body">
			<div id="register-form" class="register-form">
				<fieldset>
					<legend>
						加入ITSUNSHINE，请认真填写如下注册信息 ...
					</legend>
					<div>
						<label for="username" class="title">
							用户名(Email)
							<span class="required"> * </span>
						</label>
						<br>
						<input id="username" name="username" class="text" type="text" maxlength="30"
							value="">
					</div>
					<div>
						<label for="password" class="title">
							密码
							<span class="required"> * </span>
						</label>
						<br>
						<input id="password" name="password" class="text" type="password"
							value="">
					</div>
					<div id="dialog" title="Dear You" style="display:none;">
  						<p></p>
					</div>
					<div>
						<label for="rePassword" class="title">
							确认密码
							<span class="required"> * </span>
						</label>
						<br>
						<input id="rePassword" name="rePassword" class="text"
							type="password" value="">
					</div>
					<div>
						<label for="nickname" class="title">
							昵称
							<span class="required"> * </span>
						</label>
						<br>
						<input id="nickname" name="nickname" class="text" type="text" maxlength="20"
							value="">
					</div>
					<div>
						<label for="birthday" class="title">
							出生日期
						</label>
						<br>
						<input id="birthday" name="birthday" class="text" type="text"
							value="">
					</div>
					
					<div>
						<label for="j_validation" class="title">
							验证码
							<span class="required">*</span>
						</label>
						<br>
						<input name="j_validation" type="text" id="valCode" maxlength="4"
							class="chknumber_input">
						<img src="<%=BaseServlet.context %>/kaptcha/validation.do"
							id="kaptchaImage" style="margin-bottom: -3px">
						<a href="#" onclick="changeCode()">看不清?换一张</a>
					</div>
					<div>
						<button class="button" type="submit" id="ajax_button">
							加入 SUNSHINE
						</button>
						<button class="button" type="reset" id="clear">
							重置
						</button>
						<input id="id" name="id" type="hidden" value="">
						<br class="clear">
					</div>
					<div>
						忘记密码？
						<a href="#">帮助</a> 已经注册？
						<a href="<%=BaseServlet.context %>/views/index.do?type=user&m=loginPage">登录</a>
					</div>
				</fieldset>
				<input name="validation" type="hidden" value="validation">
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$(function() {
	    $( "#birthday" ).datepicker({ changeYear: true, changeMonth:true,yearRange: "1960:2010"});
	    $("#clear").click(function(){
		    $("#username").val('');
		    $("#password").val('');
			$("#rePassword").val('');
			$("#nickname").val('');
			$("#birthday").val('');
			$("#valCode").val('');
		});
	  });
	$(function(){
			$("#ajax_button").click(function(){
				/* 获取表单数据 */
				var username = $("#username").val();
				var password = $("#password").val();
				var rePassword = $("#rePassword").val();
				var nickname = $("#nickname").val();
				var birthday = $("#birthday").val();
				var valCode = $("#valCode").val();

				var passwordPattern = /[a-zA-Z0-9_\+]{6,20}/g;
				/* 验证表单数据 */
				if ($.trim(username) == ''|| $.trim(password) == ''
														|| $.trim(rePassword) == ''
														|| $.trim(nickname) == '') {
					jqueryUIAlter("非空字段必须填！");
													return false;
												}
												if(!validateEmail(username,"用户名")){
													return;
												}
												if(passwordPattern.exec(password) != password){
													jqueryUIAlter("密码必须是0到9、大小写字母、+_或者它们的组合,密码长度6到20个字符！");
													return;
												}
												if (password != rePassword) {
													jqueryUIAlter("两次输入的密码不一致");
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
							rePassword:rePassword,
							nickname:nickname,
							birthday:birthday,
							valCode:valCode,
						}
				$.ajax({
					url:"<%=BaseServlet.context %>/register.do?type=user&m=register",
					type:'post',
					dataType:'json',
					data:formData,
					success : function(resp) {
						if (!resp.isSuccess) {
							jqueryUIAlter(resp.errMsg);
							return;
						}else if(resp.isSuccess){
							jqueryUIAlter('恭喜注册成功，去邮箱激活你的账号吧！');
						}
						//window.location.href = '<%=BaseServlet.context %>/' + 'views/index.do';
					}
				})
			});
		});
</script>
</div>
