<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="span-11 main-column span-16">
	<div id="59bf3ce7-f339-4cbe-9a03-2d884fa42968" class="fragment">
		<div class="body">
			<table class="detail-table box-border"
				style="margin-top: 0px; border-collapse: collapse; border-spacing: 0; width: 100%; border: 1px solid #E4E4E4;">
				<tbody>
					<tr>
						<td width="15%">
							发布时间：
						</td>
						<td width="30%">
							<%--
							<fmt:parseDate value="${jobDemandArt.createTime}" pattern="yyyy-MM-dd HH:mm:ss" var="createTime"/>   
							<fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd"/> 
							 --%>
							 ${jobDemandArt.hrNumber }
						</td>
						<td width="15%">
							工作地点：
						</td>
						<td width="15%">
							${jobDemandArt.location }
						</td>
						<td width="15%">
							工作性质：
						</td>
						<td width="10%">
							${jobDemandArt.jobTime}
						</td>
					</tr>
					<tr>
						<td>
							所属部门：
						</td>
						<td>
							${jobDemandArt.companyName }
						</td>
						<td>
							学 &nbsp;&nbsp;历：
						</td>
						<td>
							${jobDemandArt.education }
						</td>
						<td>
							招聘人数：
						</td>
						<td>
							${jobDemandArt.hrNumber }
						</td>
					</tr>
				</tbody>
			</table>

			${jobDemandArt.content }
			<h2 class="left-title pl-10 pt-15">
				互联网-推荐岗位
			</h2>
			<div>
				<%@ include file="jobList.jsp"%>
			</div>
		</div>
	</div>
</div>

