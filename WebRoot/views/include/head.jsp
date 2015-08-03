<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="com.boliao.sunshine.servlet.*" %>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<div id="head">
				<a href="http://www.itsunshine.net"> <img id="logo"
						src="<%=BaseServlet.context %>/views/BJAVA_files/logo.png">
				</a>
				<div id="wrapper">
					<div id="user-menu">
						<c:choose>
							<c:when test="${!empty loginedUser.userName}">
								<ul>
									<li>
										<a href="#">${loginedUser.userName}</a>
									</li>
									<li class="last">
										<a href="<%=BaseServlet.context %>/views/index.do?type=user&m=quite">退出</a>
									</li>
								</ul>
							</c:when>
							<c:otherwise>
								<ul>
									<li>
										<a href="<%=BaseServlet.context %>/views/index.do?type=user&m=loginPage">登录</a>
									</li>
									<li class="last">
										<a href="<%=BaseServlet.context %>/views/index.do?type=user&m=regPage">注册</a>
									</li>
								</ul>
							</c:otherwise>
						</c:choose>
					</div>
					<div id="site-search">
						<form id="site-search-form" class="inline" action="<%=BaseServlet.context %>/servlet/SearchServlet" method="GET">
							<select id="typeId" name="type">
								<option value="">
									全站
								</option>
								<option value="hr">
									招聘
								</option>
							</select>
							<input id="keywords" name="keywords" type="text" value="">
							<input type="submit" value="搜索">
						</form>
					</div>

				</div>
				<div id="wrapper" style="margin-top:16px;margin-right:06px;">
					<a href="#">
					 <img width="410px;" height="62px"	src="<%=BaseServlet.context %>/views/BJAVA_files/ad.gif" />
					 </a>
				</div>
				<br class="clear">
					<div id="main-menu">
						<ul>

							<li>
								<c:choose>
									<c:when test="${type=='main'}">
										<a href="<%=BaseServlet.context %>/views/index.do" title="主页" style="background-color: #ffffff;font-weight:bold;"> <span>主页</span>
										</a>
									</c:when>
									<c:otherwise>
										<a href="<%=BaseServlet.context %>/views/index.do" title="主页" > <span>主页</span>
										</a>
									</c:otherwise>
								</c:choose>
							</li>
							
							<li>
								<c:choose>
									<c:when test="${type=='hr'}">
										<a href="<%=BaseServlet.context %>/views/index.do?type=hr" title="互联网-招聘" style="background-color: #ffffff;font-weight:bold;"> <span>互联网-招聘</span>
										</a>
									</c:when>
									<c:otherwise>
										<a href="<%=BaseServlet.context %>/views/index.do?type=hr" title="互联网-招聘">
									<span>互联网-招聘</span> </a>
									</c:otherwise>
								</c:choose>
							</li>
							<li>
								<c:choose>
									<c:when test="${type=='hrUni'}">
										<a href="<%=BaseServlet.context %>/views/index.do?type=hr" title="互联网-校招" style="background-color: #ffffff;font-weight:bold;"> <span>互联网-校招</span>
										</a>
									</c:when>
									<c:otherwise>
										<a href="<%=BaseServlet.context %>/views/index.do?type=hr" title="互联网-校招">
									<span>互联网-校招</span> </a>
									</c:otherwise>
								</c:choose>
							</li>
							<!-- 
							<li>
								<c:choose>
									<c:when test="${type=='article'}">
										<a href="<%=BaseServlet.context %>/views/index.do?type=article" title="技术文章" style="background-color: #ffffff;font-weight:bold;"> <span>技术文章</span>
										</a>
									</c:when>
									<c:otherwise>
										<a href="<%=BaseServlet.context %>/views/index.do?type=article" title="技术文章">
									<span>技术文章</span> </a>
									</c:otherwise>
								</c:choose>
							</li>


							<li>
								<c:choose>
									<c:when test="${type=='question'}">
										<a href="<%=BaseServlet.context %>/views/index.do?type=question" title="问答" style="background-color: #ffffff;font-weight:bold;"> <span>问答</span>
										</a>
									</c:when>
									<c:otherwise>
										<a href="<%=BaseServlet.context %>/views/index.do?type=question" title="问答"> <span>问答</span>
										</a>
									</c:otherwise>
								</c:choose>
								
							</li>

							 -->
							<li>
								<c:choose>
									<c:when test="${type=='doc'}">
										<a href="<%=BaseServlet.context %>/views/index.do?type=doc" title="在线文档" style="background-color: #ffffff;font-weight:bold;"> <span>在线文档</span>
										</a>
									</c:when>
									<c:otherwise>
										<a href="<%=BaseServlet.context %>/views/index.do?type=doc" title="在线文档"> <span>在线文档</span>
										</a>
									</c:otherwise>
								</c:choose>
								
							</li>
							<li>
								<c:choose>
									<c:when test="${type=='tool'}">
										<a href="<%=BaseServlet.context %>/views/index.do?type=tool" title="在线工具" style="background-color: #ffffff;font-weight:bold;"> <span>在线工具</span>
										</a>
									</c:when>
									<c:otherwise>
										<a href="<%=BaseServlet.context %>/views/index.do?type=tool" title="在线工具"> <span>在线工具</span>
										</a>
									</c:otherwise>
								</c:choose>
								
							</li>
						</ul>
					</div>


					<div id="sub-menu">
						广告位
					</div>
			</div>