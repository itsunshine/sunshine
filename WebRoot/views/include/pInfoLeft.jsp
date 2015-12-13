<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ page import="com.boliao.sunshine.servlet.*" %>
<div class="span-3 last right-column" style="margin-right: 10px;">
	<div id="broadCast" class="fragment">
						<div class="head pSettings">
							<h3>
								<b>设定提醒</b>
							</h3>
						</div>
						<div class="body">  
							<ul>
								<li>
									<a href=""  onClick="settingPageRep();return false;" class="leftNative">创建招聘提醒</a>
								</li>
								<li>
									<a href=""  class="leftNative">已有招聘提醒</a>
								</li>
								<li>
									<a href=""  class="leftNative">创建技术提醒</a>
								</li>
								<li>
									<a href=""  class="leftNative">已有技术提醒</a>
								</li>
							</ul>
						</div>
					</div>
	<div id="advertisement" class="fragment">

						<div class="head pSettings">
							<h3>
								<b>简历管理</b>
							</h3>
						</div>
						<div class="body">
							<ul>
								<li>
									<a href="<%=BaseServlet.context %>/views/index.do?type=personalInfoInput&m=input" target="_blank" class="leftNative">创建简历</a>
								</li>
								<li>
									<a href=""  class="leftNative">修改简历</a>
								</li>
							</ul>
						</div>
					</div>
	<div id="advertisement" class="fragment">

						<div class="head pSettings">
							<h3>
								<b>订阅管理</b>
							</h3>
						</div>
						<div class="body">
							<ul>
								<li>
									<a href=""  class="leftNative">新书订阅</a>
								</li>
								<li>
									<a href=""  class="leftNative">新闻订阅</a>
								</li>
							</ul>
						</div>
					</div>
</div>
<script type="text/javascript">
	/*
	$(function(){
		$( "#startTime" ).datepicker({ changeYear: true, changeMonth:true,yearRange: "1960:2010"});
		$( "#endTime" ).datepicker({ changeYear: true, changeMonth:true,yearRange: "1960:2010"});
		$("#remindsInfoSave").click(function(){remindsInfoSave();});
	})*/
	function settingPageRep(){
		$("#settingsCon").children().remove();
	}
	</script>