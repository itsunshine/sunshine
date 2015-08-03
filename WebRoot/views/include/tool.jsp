<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.boliao.sunshine.servlet.*" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!DOCTYPE html>
				<div class="span-11 main-column span-16">
					<!-- 根据ip地址查询 城市信息 开始 -->
					<div id="7361c531-ec85-4f98-8577-56722c0a88e9" class="fragment">
						<div class="head">
							<h3>
								Ip(域名)查询位置信息
							</h3>
						</div>
						<div class="body">
							<ul id="activities-list"
								class="activities-list">
								<li class="activity">
									
									<div class="activity-wrapper">
										<div class="activity-body">
											<div class="formmgr-row">
				<label for="title" class="title">请输入ip(域名):</label>
				<input id="ippInput" name="title" class="title" type="text" value="" param="&app=ip.get&ip="/>
				<button type="submit" class="button" id="ippBtn">
					提交
				</button>
			</div>
				<div class="jHtmlArea tool-result" id="ippResult" >
					城市位置：中国 北京市 北京市 <br/>
					运营商：电信<br/>
					运营商：电信<br/>
				
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
					<!-- 根据ip地址查询 城市信息 结束 -->
					
					<!-- 根据身份证信息查询 城市信息 开始 -->
					<div id="7361c531-ec85-4f98-8577-56722c0a88e9" class="fragment">
						<div class="head">
							<h3>
								根据手机号查询信息
							</h3>
						</div>
						<div class="body">
							<ul id="activities-list"
								class="activities-list">
								<li class="activity">
									
									<div class="activity-wrapper">
										<div class="activity-body">
											<div class="formmgr-row">
				<label for="title" class="title">请输入手机号:</label>
				<input id="phnInput" name="title" class="title" type="text" value="" validator="(11)n&(^\d{11}$)e" param="&app=phone.get&phone=" />
				<button type="submit" class="button" id="phnBtn">
					提交
				</button>
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
					
					<!-- 根据手机号查询 城市信息 开始 -->
					<div id="7361c531-ec85-4f98-8577-56722c0a88e9" class="fragment">
						<div class="head">
							<h3>
								根据身份证号查询信息
							</h3>
						</div>
						<div class="body">
							<ul id="activities-list"
								class="activities-list">
								<li class="activity">
									
									<div class="activity-wrapper">
										<div class="activity-body">
											<div class="formmgr-row">
				<label for="title" class="title">请输入身份证号:</label>
				<input id="idtInput" name="title" class="title" type="text" value="" validator="(15|18)n" param="&app=idcard.get&&idcard=" />
				<button type="submit" class="button" id="idtBtn">
					提交
				</button>
			</div>
				<div class="jHtmlArea tool-result" id="idtResult">
					城市位置：中国 北京市 北京市 <br/>
					运营商：电信<br/>
					运营商：电信<br/>
				
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
					<!-- 根据手机号查询 城市信息 结束 -->
					
					<script type="text/javascript">
					var url = "<%=BaseServlet.context %>/views/index.do?type=tool&m=";
					$(document).ready(function(){
						$("button").click(function(){
							var type = $(this).attr("id");
							var prefix = type.substring(0,3);
							var inputId = prefix + "Input";
							var msgId = prefix + "Result";
							var val = $("#"+inputId).attr("value");
							var resultBox = $("#"+msgId)
							var valStr = $("#"+inputId).attr("validator");
							var param = $("#"+inputId).attr("param");
							var lengthFlag = false;
							if(val.trim()==''){
								var html="<font color='red'>输入字符串不能为空</font>"
									displayMsg(resultBox,html);
									return;
							}
							if(valStr != '' && valStr!=undefined)
							{
								var valsM = valStr.split("&");
								var lflag = false;
								var eflag = false;
								for(var j=0;j<valsM.length;j++){
									var vs = valsM[j];
									if(endWith(vs,"n")){
										vs = vs.replace("n","");
										vs = removeBracket(vs);
										var vals = vs.split("|");
										for(var i=0;i<vals.length;i++){
											if(val.length==parseInt(vals[i])){
												lflag = true;
												break;
											}
										}
									}else if(endWith(vs,"e")){
										vs = vs.replace("e","");
										vs = removeBracket(vs);
										var reg = new RegExp(vs);
										if(reg.test(val)){
											eflag = true;
										}
									}
								}
								if(lflag &&　eflag){
									lengthFlag = true;
								}								
							}else{
								lengthFlag = true;
							}
							
							if(!lengthFlag){
								var html="<font color='red'>输入字符串长度（格式）不对</font>"
								displayMsg(resultBox,html);
								return;
							}
							val = param + val;
							var ajaxUrl = url + 'search';
							alert(ajaxUrl);
							$.ajax({
								type:"post",
								url:ajaxUrl,
								data:{
									value:val
								},
								success:function(data){
									var resp = $.parseJSON(data);
									var html;
									if(prefix == 'idt'){
										html = "身份证号：" + resp.idcard + "<br/>";
										html = html + "出生年月日：" + resp.born + "<br/>";
										html = html + "性别：" + resp.sex + "<br/>";
										html = html + "城市地址：" + resp.att;
									}else if(prefix == 'ipp'){
										alert(data);
										html = html + "ip：" + resp.ip + "<br/>";
										html = html + "IP地址段开始：" + resp.ip_str + "<br/>";
										html = html + "IP地址段结束：" + resp.ip_end + "<br/>";
										html = html + "数字地址开始：" + resp.inet_str + "<br/>";
										html = html + "数字地址结束：" + resp.inet_end + "<br/>";
										html = html + "该ip对应的数字地址：" + resp.inet_aton + "<br/>";
										html = html + "归属地：" + resp.area_style_areanm + "<br/>";
										html = html + "运营商：" + resp.detailed + "<br/>";
									}else if(prefix == 'phn'){
										alert(data);
										html = html + "电话号码：" + resp.phone + "<br/>";
										html = html + "所属地区：" + resp.att + "<br/>";
										html = html + "号码卡类型：" + resp.ctype + "<br/>";
										html = html + "运营商：" + resp.operators + "<br/>";
									}
									displayMsg(resultBox,html);
								}
							});	
						});
						
					})
					
					// 判断字符串以什么字符结尾
					function endWith(str,symbol){
						if(str.charAt(str.length-1)==symbol){
							return true;
						}
					}
					
					// 去掉字符串中的括号
					function removeBracket(str){
						str=str.replace("(","");
						str=str.replace(")","");
						return str;
					}
					//在结果框中展示要显示的消息
					function displayMsg(resultBox,msg){
						resultBox.html(msg);
						resultBox.css("display","block");
					}
					</script>

				</div>
				
