<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.boliao.sunshine.servlet.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<div class="span-11 main-column span-16">
	<!-- 填写个人信息表单 开始 -->
	<div id="7361c531-ec85-4f98-8577-56722c0a88e9" class="fragment">
		<div class="head">
			<h3>
				个人信息
			</h3>
		</div>
		<div class="body">
			<ul id="activities-list" class="activities-list">
				<li class="activity">

					<div class="activity-wrapper">
						<div class="activity-body">
							<div class="formmgr-row">
								<label for="title" class="title">
									姓名&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
									<b class="req"> * </b>
								</label>
								<input id="username" name="username" class="perInfo"/>
								<div style="float:right;margin-right:10px;">
									<label for="title">
										性别
										<b class="req"> * </b>
									</label>
										<input name="sex" type="radio" value="0" />
										男
										<input name="sex" type="radio" value="1" />
										女
								</div>
								<br />
								<label for="birthday">
									出生日期
									<b class="req"> * </b>
								</label>
								<input id="birthday" name="birthday" class="perInfo"/>
								<div style="float:right;">
								<label for="phoneNum">
									手机号
									<b class="req"> * </b>
								</label>
								<input id="phoneNum" name="phoneNum" type="number" class="perInfo"/>
								</div>
								<br />
								<label for="email" class="title">
									Email&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
									<b class="req"> * </b>
								</label>
								<input id="email" name="email" type="email" class="perInfo"/>
								<div style="float:right">
								<label for="salary" class="title">
									目前薪资
									<b class="req"> * </b>
								</label>
								<input id="salary" name="salary" type="number" class="perInfo" placeholder="按年薪多少元填写"/>
								</div>
								<br />
								<label for="salary" class="title">
									婚姻状况&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								</label>
								<select name="marrage" id="marrage" class="select" >
									<option value="0">
										未婚
									</option>
									<option value="1">
										已婚
									</option>
									<option value="2">
										保密
									</option>
								</select>
								<div style="float:right;margin-top:8px;">
								<label for="height" class="title">
									身高&nbsp&nbsp&nbsp&nbsp&nbsp
								</label>
								<input id="height" name="height" class="perInfo" placeholder="单位cm"/>
								</div>
								<br />
								<label for="weixin">
									微信号&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								</label>
								<input id="weixin" name="weixin" class="perInfo" placeholder="微信号" />
								<div style="float:right;">
								<label for="weixin">
									QQ号&nbsp&nbsp&nbsp&nbsp&nbsp
								</label>
								<input id="qq" name="qq" class="perInfo" placeholder="QQ号" />
								</div>
								<br />
								<label for="skillDesc" style="position:relative;top:-100px;">
									个人技术总结
								</label>
								<textarea id="skillDesc" style="width:510px;height:100px;" placeholder="填写个人擅长的点，比如：擅长java语言、或者hadoop等大数据开发"></textarea>
								<br />
								<br />
								<div style="text-align: center">
								<button type="submit" class="button" id="basicInfoSave">
									保存
								</button>
								</div>
							</div>
						</div>
					</div>
				</li>
				<div style="text-align: center">
					<a href="#">广告位</a>
				</div>
			</ul>
		</div>
	</div>
	<!-- 填写个人信息表单 结束 -->

	<!-- 填写教育经历表单 开始 -->
	<div id="7361c531-ec85-4f98-8577-56722c0a88e9" class="fragment">
		<div class="head">
			<h3>
				教育经历
			</h3>
		</div>
		<div class="body">
			<ul id="activities-list" class="activities-list">
				<li class="activity">
					<div class="activity-wrapper">
						<div class="activity-body">
							<div class="formmgr-row">
								<label for="startTime" class="title">
									时间&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
									<b class="req"> * </b>
								</label>
								<input id="startTime" name="startTime" class="perInfo" />
								<span>到&nbsp&nbsp&nbsp&nbsp</span>
								<input id="endTime" name="endTime" class="perInfo" />
								<br />
								<label for="school" class="title">
									学校&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
									<b class="req"> * </b>
								</label>
								<input id="school" name="school" class="perInfo" />
								<div style="float:right;">
								<label for="mayor" class="title">
									专业
									<b class="req"> * </b>
								</label>
								<input id="mayor" name="mayor" class="perInfo" />
								</div>
								<br />
								<label for="proDesc" style="position:relative;top:-100px;">
									专业描述&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								</label>
								<textarea rows="" cols="" id="proDesc" style="width:510px;height:100px;"></textarea>
								<br />
								<br />
								<div style="text-align: center;">
								<button type="submit" class="button" id="eduConBtn">
									继续添加
								</button>
								<button type="submit" class="button" id="eduSaveBtn">
									保存
								</button>
								</div>
							</div>
							<div class="jHtmlArea tool-result" id="phnResult">

							</div>
						</div>
					</div>
				</li>
				<div style="text-align: center">
					<a href="#">广告位</a>
				</div>
			</ul>
		</div>
	</div>
	<!-- 根据身份证信息查询 城市信息 结束 -->

	<!-- 用户填入工作经验 开始 -->
	<div id="7361c531-ec85-4f98-8577-56722c0a88e9" class="fragment">
		<div class="head">
			<h3>
				工作经验（无工作经验的学生可以不填）
			</h3>
		</div>
		<div class="body">
			<ul id="activities-list" class="activities-list">
				<li class="activity">

					<div class="activity-wrapper">
						<div class="activity-body">
							<div class="formmgr-row">
								<label for="jobStartTime">
									时间&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
									<b class="req"> * </b>
								</label>
								<input id="jobStartTime" name="jobStartTime" class="perInfo" />
								到&nbsp&nbsp&nbsp&nbsp
								<input id="jobEndTime" name="jobEndTime" class="perInfo" />
								<br />
								<label for="company">
									公司&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
									<b class="req"> * </b>
								</label>
								<input id="company" name="company" class="perInfo" />
								<div style="float:right;">
								<label for="industry">
									行业
									<b class="req"> * </b>
								</label>
								<input id="industry" name="industry" class="perInfo" />
								</div>
								<br />
								<label for="staffNums">
									公司规模
									<b class="req"> * </b>
								</label>
								<input id="staffNums" name="staffNums" type="number" class="perInfo" placeholder="所在公司人数"/>
								<div style="float:right;">
								<label for="compType">
									公司性质
									<b class="req"> * </b>
								</label>
								<input id="compType" name="compType" class="perInfo" />
								</div>
								<br />
								<label for="departMent">
									部门&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
									<b class="req"> * </b>
								</label>
								<input id="departMent" name="departMent" class="perInfo"/>
								<div style="float:right;">
								<label for="position">
									职位
									<b class="req"> * </b>
								</label>
								<input id="position" name="position" class="perInfo" />
								</div>
								<br />
								<label for="jobDesc" style="position:relative;top:-100px;">
									工作描述
									<b class="req"> * </b>
								</label>
								<textarea id="jobDesc"  style="width:510px;height:100px;" placeholder="在公司里做的主要工作"></textarea>
								<br />
								<br />
								<div style="text-align: center;">
								<button type="submit" class="button" id="jobExpConBtn">
									继续添加
								</button>
								<button type="submit" class="button" id="jobExpSaveBtn">
									保存
								</button>
								</div>
							</div>
						</div>
					</div>
				</li>
				<div style="text-align: center">
					<a href="#">广告位</a>
				</div>
			</ul>
		</div>
	</div>
	<!-- 用户填入工作经验 结束 -->
	<!-- 提示信息 -->
	<div id="dialog" title="Dear You" style="display:none;">
  		<p></p>
	</div>
	<!-- 用户填入求职意向 开始 -->
	<div id="7361c531-ec85-4f98-8577-56722c0a88e9" class="fragment">
		<div class="head">
			<h3>
				项目经验
			</h3>
		</div>
		<div class="body">
			<ul id="activities-list" class="activities-list">
				<li class="activity">

					<div class="activity-wrapper">
						<div class="activity-body">
							<div class="formmgr-row">
								<label for="jobStartTime">
									时间&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
									<b class="req"> * </b>
								</label>
								<input id="proStartTime" name="proStartTime" class="perInfo" />
								到&nbsp&nbsp&nbsp&nbsp
								<input id="proEndTime" name="proEndTime" class="perInfo" />
								<br />
								<label for="projectName" class="title">
									项目名称
									<b class="req"> * </b>
								</label>
								<input id="projectName" name="projectName" class="title" type="text" />
								<br />
								<label for="softEnv" class="title">
									软件环境
									<b class="req"> * </b>
								</label>
								<input id="softEnv" name="softEnv" class="title" type="text" />
								<br />
								<label for="tools" class="title">
									开发工具
									<b class="req"> * </b>
								</label>
								<input id="tools" name="tools" class="title" type="text" />
								<br />
								<label for="mainResp" class="title" style="position:relative;top:-100px;">
									责任描述
									<b class="req"> * </b>
								</label>
								<textarea rows="" cols="" id="mainResp" style="width:510px;height:100px;"></textarea><br/>
								<label for="mainContr" class="title" style="position:relative;top:-100px;">
									主要贡献
									<b class="req"> * </b>
								</label>
								<textarea rows="" cols="" id="mainContr" style="width:510px;height:100px;"></textarea>
								<br />
								<br />

								<div style="text-align: center;">
								<button type="submit" class="button" id="proExpConBtn">
									继续添加
								</button>
								<button type="submit" class="button" id="proExpSaveBtn">
									保存
								</button>
								</div>
							</div>
						</div>
					</div>
				</li>
				<div style="text-align: center">
					<a href="#">广告位</a>
				</div>
			</ul>
		</div>
	</div>
	<!-- 用户填入求职意向 结束 -->

	<script type="text/javascript">
					$(function() {
						$( "#birthday" ).datepicker({ changeYear: true, changeMonth:true,yearRange: "1960:2010"});
						$( "#startTime" ).datepicker({ changeYear: true, changeMonth:true,yearRange: "1960:2010"});
						$( "#endTime" ).datepicker({ changeYear: true, changeMonth:true,yearRange: "1960:2010"});
						$( "#jobStartTime" ).datepicker({ changeYear: true, changeMonth:true,yearRange: "1960:2010"});
						$( "#jobEndTime" ).datepicker({ changeYear: true, changeMonth:true,yearRange: "1960:2010"});
						$( "#proStartTime" ).datepicker({ changeYear: true, changeMonth:true,yearRange: "1960:2010"});
						$( "#proEndTime" ).datepicker({ changeYear: true, changeMonth:true,yearRange: "1960:2010"});
						
						/** 保存用户的基本信息 */
						$("#basicInfoSave").click(function(){saveBasicInfo();});
						/** 保存教育经历信息 */
						$("#eduSaveBtn").click(function(){saveEducation(false);});
						$("#eduConBtn").click(function(){saveEducation(true);});
						$("#jobExpSaveBtn").click(function(){saveJobExperience(false);});
						$("#jobExpConBtn").click(function(){saveJobExperience(true);});
						$("#proExpSaveBtn").click(function(){saveProExperiences(false);});
						$("#proExpConBtn").click(function(){saveProExperiences(true);});
					});

					// 保存项目经验
					function saveProExperiences(isContinue){
						var proStartTime = $("#proStartTime").val();
						var proEndTime = $("#proEndTime").val();
						var projectName = $("#projectName").val();
						var softEnv = $("#softEnv").val();
						var tools = $("#tools").val();
						var mainResp = $("#mainResp").val();
						var mainContr = $("#mainContr").val();

						if(isNullOrEmpty(proStartTime)){
							jqueryUIAlter("项目经历开始时间必须选");
							return;
						}
						if(isNullOrEmpty(proEndTime)){
							jqueryUIAlter("项目经历结束时间必须选");
							return;
						}
						if(isNullOrEmpty(projectName)){
							jqueryUIAlter("项目名称必须填");
							return;
						}
						if(isNullOrEmpty(softEnv)){
							jqueryUIAlter("软件环境必须填");
							return;
						}
						if(isNullOrEmpty(tools)){
							jqueryUIAlter("开发中用到的工具必须填");
							return;
						}
						if(isNullOrEmpty(mainResp)){
							jqueryUIAlter("项目开发中的主要职责必须填");
							return;
						}
						if(isNullOrEmpty(mainContr)){
							jqueryUIAlter("项目开发中的主要贡献必须填");
							return;
						}

						// 项目经验表单数据
						var formData={
								proStartTime:proStartTime,
								proEndTime:proEndTime,
								projectName:projectName,
								softEnv:softEnv,
								tools:tools,
								mainResp:mainResp,
								mainContr:mainContr,
						}

						// 发送请求
						url="<%=BaseServlet.context %>/resume.do?type=personalInfoInput&m=registerP";
						ajaxRequest(url,formData,"项目经验保存成功");
						if(isContinue){
							$("#proStartTime").val("");
							$("#proEndTime").val("");	
							$("#projectName").val("");
							$("#softEnv").val("");
							$("#tools").val("");
							$("#compType").val("");
							$("#mainResp").val("");
							$("#mainContr").val("");
						}
					}

					// 保存工作经验
					function saveJobExperience(isContinue){ 
						var jobStartTime = $("#jobStartTime").val();
						var jobEndTime = $("#jobEndTime").val();
						var company = $("#company").val();
						var industry = $("#industry").val();
						var staffNums = $("#staffNums").val();
						var compType = $("#compType").val();
						var departMent = $("#departMent").val();
						var position = $("#position").val();
						var jobDesc = $("#jobDesc").val();
						
						if(isNullOrEmpty(jobStartTime)){
							jqueryUIAlter("工作经验开始时间必须填");
							return;
						}
						if(isNullOrEmpty(jobEndTime)){
							jqueryUIAlter("工作经验结束时间必须填");
							return;
						}
						if(isNullOrEmpty(industry)){
							jqueryUIAlter("工作所在的行业必须填");
							return;
						}
						if(isNullOrEmpty(staffNums)){
							jqueryUIAlter("公司员工数必须填");
							return;
						}
						if(isNullOrEmpty(compType)){
							jqueryUIAlter("公司性质必须填");
							return;
						}
						if(isNullOrEmpty(departMent)){
							jqueryUIAlter("工作所在部门必须填");
							return;
						}
						if(isNullOrEmpty(position)){
							jqueryUIAlter("工作时的职位必须填");
							return;
						}
						if(isNullOrEmpty(jobDesc)){
							jqueryUIAlter("工作时工作描述必须填");
							return;
						}

						// 教育经历表单数据
						var formData={
								jobStartTime:jobStartTime,
								jobEndTime:jobEndTime,
								company:company,
								industry:industry,
								staffNums:staffNums,
								compType:compType,
								departMent:departMent,
								position:position,
								jobDesc:jobDesc,
						}

						// 发送请求
						url="<%=BaseServlet.context %>/resume.do?type=personalInfoInput&m=registerJ";
						ajaxRequest(url,formData,"教育经历保存成功");
						if(isContinue){
							$("#jobStartTime").val("");
							$("#jobEndTime").val("");	
							$("#company").val("");
							$("#industry").val("");
							$("#staffNums").val("");
							$("#compType").val("");
							$("#departMent").val("");
							$("#position").val("");
							$("#jobDesc").val("");
						}
						
					}
					
					// 保存教育经历的方法
					function saveEducation(isContinue){
						var startTime = $("#startTime").val();
						var endTime = $("#endTime").val();
						var school = $("#school").val();
						var mayor = $("#mayor").val();
						var proDesc = $("#proDesc").val();
						if(isNullOrEmpty(startTime)){
							jqueryUIAlter("开始时间必须填");
							return;
						}
						if(isNullOrEmpty(endTime)){
							jqueryUIAlter("结束时间必须填");
							return;
						}
						if(isNullOrEmpty(school)){
							jqueryUIAlter("学校 必须填");
							return;
						}
						if(isNullOrEmpty(mayor)){
							jqueryUIAlter("专业必须填");
							return;
						}
						// 教育经历表单数据
						var formData={
								startTime:startTime,
								endTime:endTime,
								school:school,
								mayor:mayor,
								proDesc:proDesc,
						}

						// 发送请求
						url="<%=BaseServlet.context %>/resume.do?type=personalInfoInput&m=registerE";
						ajaxRequest(url,formData,"教育经历保存成功");
						if(isContinue){
							$("#startTime").val("");
							$("#endTime").val("");	
							$("#school").val("");
							$("#mayor").val("");
							$("#proDesc").val("");
						}
						
					}

					// 保存基本信息
					function saveBasicInfo(){
						// 下面的代码是获取，基本的个人信息
						var username = $("#username").val();
						var sex = $('input[name="sex"]:checked').val();
						var birthDay = $("#birthday").val();
						var phoneNum = $("#phoneNum").val();
						var email = $("#email").val();
						var salary = $("#salary").val();
						var marriage = $('#marrage option:selected').val();
						var height = $("#height").val();
						var weixin = $("#weixin").val();
						var qq = $("#qq").val();
						var skillDesc = $("#skillDesc").val();

						// 进行基本信息校验
						if(isNullOrEmpty(username)){
							jqueryUIAlter("姓名必须填");
							return;
						}
						if(isNullOrEmpty(sex)){
							jqueryUIAlter("性别必须选");
							return;
						}
						if(isNullOrEmpty(birthDay)){
							jqueryUIAlter("出生日期必须");
							return;
						}
						if(!validatemobile(phoneNum)){
							return;
						}
						if(!validateEmail(email,"邮箱")){
							return;
						}
						if(isNullOrEmpty(salary)){
							jqueryUIAlter("目前年薪必须填");
							return;
						}

						// 基本信息数据
						var formData = {
								username:username,
								sex:sex,
								birthDay:birthDay,
								phoneNum:phoneNum,
								email:email,
								salary:salary,
								marriage:marriage,
								height:height,
								weixin:weixin,
								qq:qq,
								skillDesc:skillDesc,
							}
						url = "<%=BaseServlet.context %>/resume.do?type=personalInfoInput&m=register"
						ajaxRequest(url,formData,"基本信息保存成功");
						
					}
					</script>

</div>

