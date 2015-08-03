<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<div class="span-11 main-column span-16">
	<c:forEach var="result" items="${results}">
		<c:forEach var="page" varStatus="vStatus" items="${result}">
			<c:if test="${vStatus.index == 0}">
				<c:set var="type" value="${page}"></c:set>
			</c:if>
			<c:if test="${vStatus.index == 1}">
				<c:set var="title" value="${page}"></c:set>
			</c:if>
			<c:if test="${vStatus.index == 2}">
				<div id="7361c531-ec85-4f98-8577-56722c0a88e9" class="fragment">
					<div class="head">
						<h3>
							${title }
						</h3>
					</div>
					<div class="body">
						<ul id="activities-list" class="activities-list">
							<c:choose>
								<c:when test="${title == '互联网公司-招聘信息'}">
									<%@ include file="jobList.jsp" %>
								</c:when>
								<c:otherwise>
									<c:forEach var="article" items="${page.results}">
										<li class="activity">
											<div class="activity-wrapper">
												<div class="activity-body">
													<div class="right">
														<span class="pretty-tim ">广告位</span>
														<a class="comment-action" id="78" href="#"> 广告位 </a>
													</div>
													<a
														href="${context }/views/index.do?m=getContent&id=${article.id}&type=${type}"
														target="blank"> ${article.title } </a>
												</div>
											</div>
										</li>
									</c:forEach>
								</ul>
							</c:otherwise>
						</c:choose>
						<div style="text-align: center">
							<a id="more-action" href="${context }/views/index.do?type=${type}"
								target="_blank">更多</a>
						</div>
						<div style="text-align: center">
										<a id="more-action7361c531-ec85-4f98-8577-56722c0a88e9"
											href="#">广告位</a>
						</div>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</c:forEach>

</div>
