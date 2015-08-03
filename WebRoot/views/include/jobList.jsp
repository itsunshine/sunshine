<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.boliao.sunshine.servlet.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<table class="table-list">
	<thead>
		<tr>
			<th width="40%">
				<span>职位名称</span>
			</th>
			<th width="13%">
				<span>职位类别</span>
			</th>
			<th width="13%">
				<span>工作地点</span>
			</th>
			<th width="8%">
				<span>人数</span>
			</th>
			<th width="15%">
				<span>更新时间</span>
			</th>
			<th>
				<span>公司</span>
			</th>
		</tr>
	</thead>
	<tbody id="J-list-box">
		<c:forEach var="article" items="${page.results}">
			<tr>
				<td>
					<span> <a
						href="<%=BaseServlet.context%>/views/index.do?m=getContent&id=${article.id}&type=${type}"
						target="_blank"> ${article.title } </a> </span>
				</td>
				<td>
					<span>技术类</span>
				</td>
				<td>
					<span>${article.location }</span>
				</td>
				<td>
					<span> ${article.hrNumber } </span>
				</td>
				<td>
					<span>
					${article.createTime }
					</span>
				</td>
				<td>
					<span>${article.companyName}</span>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
