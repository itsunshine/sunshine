<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.boliao.sunshine.biz.model.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
	<c:when test="${viewFlag == 1}">
		<div class="pagination" style="text-align: center">
			<span class='prev_page' id='currentPage'>第&nbsp;${page.start + 1} &nbsp;&nbsp;-&nbsp;&nbsp;${page.pageSize + page.start}&nbsp;条&nbsp;&nbsp;|&nbsp;&nbsp;共&nbsp;${page.totalPage }&nbsp;页&nbsp;&nbsp;</span> |&nbsp;&nbsp;
			<span id="lastPage"> 
				<c:choose>
					<c:when test="${page.pageNo == 1}">
						<span class="disabled prev_page">« 上一页</span>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${fn:contains(uri,'?') }">
								<a href="${uri }&pageNo=<c:out value="${page.pageNo-1 }&type=${type }" />">&lt;&lt; 上一页</a>
							</c:when>
							<c:otherwise>
								<a href="${uri }?pageNo=<c:out value="${page.pageNo-1 }&type=${type }" />">&lt;&lt; 上一页</a>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose> 
			</span> &nbsp;&nbsp;
			<span id="nextPage">
				<c:choose>
					<c:when test="${page.pageNo == page.totalPage}">
						<span class="disabled prev_page">下一页 »</span>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${fn:contains(uri,'?') }">
								<a href="${uri }&pageNo=<c:out value="${page.pageNo+1 }&type=${type }" />">下一页&gt;&gt;</a>
							</c:when>
							<c:otherwise>
								<a href="${uri }?pageNo=<c:out value="${page.pageNo+1 }&type=${type }" />">下一页&gt;&gt;</a>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</span>
			<form action="${uri }" id="pageAction">
				<span id='pageNum'>第${page.pageNo }页&nbsp;&nbsp;|&nbsp;&nbsp;</span>
				<input type="text" name="pageNo" size="4" />
				<input type="hidden" name="type" value="${type }" />
				<c:if test="${not empty keywords}">
					<input type="hidden" name="keywords" value="${keywords }" />
				</c:if>
				<c:if test="${not empty ajax }">
					<input type="hidden" name="ajax" value="${ajax }" />
				</c:if>
				<c:if test="${not empty location }">
					<input type="hidden" name="location" value="${location }" />
				</c:if>
				<c:if test="${not empty company }">
					<input type="hidden" name="company" value="${company }" />
				</c:if>
				<input type="submit" />
			</form>
		</div>
	</c:when>
	<c:otherwise>
		<c:if test="${page.totalPage gt page.pageNo}">
			<div style="text-align: center">
				<a id="more-action" href="${uri }">更多</a>
			</div>
		</c:if>
	</c:otherwise>
</c:choose>
