<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>互联网技术_人才网_上ITsunshine</title>
<meta name="description" content="ITsunshine 网站为互联网技术人才，提供最新最全最准确的互联网技术招聘信息、面试经验分享、技术基本知识、互联网新技术咨询、互联网公司最新人事信息以及数据分析等在内的全方位的求职服务,更多尽在ITsunshine!">
<meta name="keywords" content="互联网招聘,技术人才网,求职,交友,学习,分享">
<%@ include file="include/statistic.jsp"  %>
</head>
<body>
<div class="container">
	<%@ include file="include/head.jsp"  %>
	<div id="body">
	<c:choose>
		<c:when test="${conFlag == 1}">
			<%@ include file="include/content.jsp"  %>
		</c:when>
		<c:when test="${conFlag == 2}">
			<%@ include file="include/first.jsp"  %>
		</c:when>
		<c:when test="${conFlag == 3}">
			<%@ include file="include/qa.jsp"  %>
		</c:when>
		<c:when test="${conFlag == 4}">
			<%@ include file="include/tool.jsp"  %>
		</c:when>
		<c:when test="${conFlag == 5}">
			<%@ include file="include/register.jsp"  %>
		</c:when>
		<c:when test="${conFlag == 6}">
			<%@ include file="include/login.jsp"  %>
		</c:when>
		<c:when test="${conFlag == 7}">
			<%@ include file="include/jobHR.jsp"  %>
		</c:when>
		<c:when test="${conFlag == 8}">
			<%@ include file="include/pInfoSettings.jsp"  %>
		</c:when>
		<c:when test="${conFlag == 9}">
			<%@ include file="include/pInfoInput.jsp"  %>
		</c:when>
		<c:otherwise>
			<%@ include file="include/main.jsp"  %>
		</c:otherwise>
	</c:choose>
	<%@ include file="include/right.jsp"  %>
	<br class="clear">
	</div>
	<%@ include file="include/footer.jsp"  %>
</div>
</body>
</html>