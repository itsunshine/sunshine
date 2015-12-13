<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.boliao.sunshine.servlet.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<%@ include file="pInfoLeft.jsp"%>
<div class="main-column span-13">
	<!-- 展示内容 -->
	<div id="7361c531-ec85-4f98-8577-56722c0a88e9" class="fragment">

		<div class="body">
			<ul id="activities-list" class="activities-list">
				<li class="activity">

					<div class="activity-wrapper" id="settingsCon">
					<%--  
						<%@ include file="reminds/jobReminds.jsp"%>
					
						<%@ include file="reminds/techReminds.jsp"%>
					
						<%@ include file="reminds/bookReminds.jsp"%>
					--%>
						<%@ include file="reminds/newsReminds.jsp" %>
					</div>
				</li>
				<div style="text-align: center">
					<a href="#">广告位</a>
				</div>
			</ul>
		</div>
	</div>
	<!-- 展示内容 结束 -->
</div>

