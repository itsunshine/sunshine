<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.boliao.sunshine.servlet.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<div class="span-11 main-column span-16">
	<div id="7361c531-ec85-4f98-8577-56722c0a88e9" class="fragment">
		<div class="head">
			<h3>
				${title }
			</h3>
		</div>
		<div class="body">
			<c:choose>
				<c:when test="${showFlag == 'hr'}">
					<%@ include file="search.jsp"%>
					<div style="text-align: center">
						<a href="#">广告位</a>
					</div>
					<%@ include file="jobList.jsp"%>
					<div style="text-align: center">
						<a href="#">广告位</a>
					</div>
				</c:when>
				<c:otherwise>
					<%@ include file="mainList.jsp"%>
				</c:otherwise>
			</c:choose>
			<%@ include file="page.jsp"%>

		</div>
	</div>
</div>
