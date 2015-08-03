<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.boliao.sunshine.servlet.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
			<ul id="activities-list" class="activities-list">
				<c:forEach var="article" items="${page.results}">
					<li class="activity">
						<div class="activity-wrapper">
							<div class="activity-body">
								<div class="right">
									<span class="pretty-tim ">广告位</span>
									<a class="comment-action" id="78" href="#"> 广告位 </a>
								</div>
								<a
									href="<%=BaseServlet.context%>/views/index.do?m=getContent&id=${article.id}&type=${type}"
									target="blank"> ${article.title }</a>
								<br />
								<span> ${article.content } </span>
							</div>
						</div>
					</li>
				</c:forEach>
				<div style="text-align: center">
					<a href="#">广告位</a>
				</div>
			</ul>
