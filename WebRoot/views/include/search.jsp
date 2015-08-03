<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.boliao.sunshine.servlet.*"%>
<%@ page import="com.boliao.sunshine.biz.model.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="left-search-box">
	<form class="J-search-form">
		<div class="search-box" style="position: relative;">
			<input type="text" id="keyword" maxlength="30" topic="请输入职位关键词" class="search-text">
			<input class="search-btn" type="button" value="搜索" id="schbtn">
			<input type="hidden" name="“dress-check”" id="dress-check">
			<input type="hidden" name="“position-check”" id="position-check">
			<input type="hidden" name="“position-first-check”" id="position-first-check" value="技术类">
		</div>
	</form>
	<div class="search-tab" style="padding-top: 10px;">
		<div class="search-li-box layout"
			style="overflow: hidden; padding-top: 6px;">
			<b>工作地点：</b>
			<span class="r-span"> 
				<a href="javascript:;" data-check-type="dress" rel="all" style="display: none">全部</a>
				<span id="search-dress">
						<c:forEach var="city" items="${cityList}" varStatus="status">
						 	<c:choose>
						 		<c:when test="${status.index > 0}">
						 			<a data-check-type="address" rel="${city.location}" href="javascript:;" class="displaynone address" onClick="locationOnChange(this)">${city.location}</a>
						 		</c:when>
						 		<c:otherwise>
						 			<a data-check-type="address" rel="${city.location}" href="javascript:;" onClick="locationOnChange(this)">${city.location}</a>
						 		</c:otherwise>
						 	</c:choose>
						</c:forEach>
				</span> 
				<a class="check-more-dress" href="javascript:;" data-current="4" onClick="showMoreAddress('addr')">更多&gt;&gt;</a> 
			</span>
		</div>
		<div class="search-li-box layout">
			<b>招聘公司：</b>
			<span class="r-span"> 
				<a class="current" href="javascript:;" style="display: none">全部</a> 
				<c:forEach var="company" items="${corporationList}" varStatus="status">
						 	<c:choose>
						 		<c:when test="${status.index > 0}">
						 			<a data-check-type="address" rel="${company.name}" href="javascript:;" class="displaynone company"  onClick="corporationOnChange(this)">${company.name}</a>
						 		</c:when>
						 		<c:otherwise>
						 			<a data-check-type="address" rel="${company.name}" href="javascript:;" onClick="corporationOnChange(this)">${company.name}</a>
						 		</c:otherwise>
						 	</c:choose>
						</c:forEach>
			</span>
			<a class="check-more-dress" href="javascript:;" data-current="4" onClick="showMoreAddress('company')">更多&gt;&gt;</a>
		</div>
	</div>
</div>
<script type="text/javascript">
// 为搜索定义全局变量
var globalLocation=null;
var globalCoporation=null;
var globalKeyword=null;
// 为搜索按钮，绑定点击事件
$(document).ready(function(){
	$("#schbtn").click(function(){
			var keyword = $("#keyword").val();
			globalKeyword = keyword;
			ajaxSearch(keyword);
		})
});
// 发起异步搜索请求
function ajaxSearch(keyword,location,company){
	var searchUrl = "/sunshine_new/views/index.do?m=ajax";
	//dataStr = '{"type":"hr","keywords":"'+keyword+'","m":"ajax"}';
	dataStr = '{"type":"hr","m":"ajax"}';
	var dataObj = $.parseJSON(dataStr);
	if(!isNullOrEmpty(keyword)){
		dataObj.keywords=keyword;
	}
	else if(!isNullOrEmpty(globalKeyword)){
		dataObj.keywords=globalKeyword;
	}
	//设置城市信息
	if(!isNullOrEmpty(location)){
		dataObj.location=location;
	}
	else if(!isNullOrEmpty(globalLocation)){
		dataObj.location=globalLocation
	}
	// 设置公司信息
	if(!isNullOrEmpty(company)){
		dataObj.company=company;
	}
	else if(!isNullOrEmpty(globalCoporation)){
		dataObj.company=globalCoporation;
	}
	alert(dataObj.keywords);
	alert(dataObj.location);
	alert(dataObj.company);
	$.ajax({
		url:searchUrl,
		type:'post',
		data:dataObj,
		dataType:"json",  
        timeout:"5000",  
        error:function(){alert("服务加载出错");},   
        success:function(data)  
                {  
                    if(!isNullOrEmpty(data.error)){
                        jAlert('查询出错 ',"data.error");
                        return;
                    }
                    
                    $("#J-list-box").children().remove();
                    var ajaxResult = '';
                    $.each(data.results,function(n,jd){
                        ajaxResult +='<tr><td><span> <a href='+sunContext+'/views/index.do?m=getContent&id='+jd.id+'&type=hr target="_blank">'+ jd.title +' </a> </span></td>';
                        ajaxResult += '<td><span>技术类</span></td>';
                        ajaxResult += '<td><span>'+jd.location+'</span></td>';
                        ajaxResult += '<td><span>'+jd.hrNumber+'</span></td>';
                        ajaxResult += '<td><span>'+jd.createTime+'</span></td>';
                        ajaxResult += '<td><span>'+jd.companyName+'</span></td></tr>';
                        });
                    $("#J-list-box").append(ajaxResult);

                    // 设置下一页
                    $("#nextPage").children().remove();
                    var nextPageHtmlText = null;

                 	// 修改page页提交的action
                    var pageForm = $("#pageAction");
                    if(data.totalPage > 1){
                        var nextSubStr = null;
                        if(!isNullOrEmpty(dataObj.keywords)){
                        	nextSubStr = '&keywords='+ dataObj.keywords;
                        	var keywordInput = '<input type="hidden" name="keywords" value="'+dataObj.keywords+'" />';
                        	pageForm.append(keywordInput);
                        }
                    	if(!isNullOrEmpty(dataObj.location)){
                    		nextSubStr = '&location='+ dataObj.location;
                    		var locationInput = '<input type="hidden" name="location" value="'+dataObj.location+'" />';
                        	pageForm.append(locationInput);
                        }
                    	if(!isNullOrEmpty(dataObj.company)){
                    		nextSubStr = '&company='+ dataObj.company;
                    		var companyInput = '<input type="hidden" name="company" value="'+dataObj.company+'" />';
                        	pageForm.append(companyInput);
                        }
                    	nextPageHtmlText = '<a href='+sunContext+'/views/index.do?type=hr&ajax=true'+ nextSubStr +'&pageNo='+(data.pageNo+1)+' target="blank">下一页</a>';
                    }else{
                    	nextPageHtmlText = '<span class="disabled prev_page">下一页 »</span>';
                    }
                    $("#nextPage").append(nextPageHtmlText);

                    // 改变当前页显示
                    var currentPage = "第&nbsp;1&nbsp;&nbsp;-&nbsp;&nbsp;"+data.pageSize+"&nbsp;条&nbsp;&nbsp;|&nbsp;&nbsp;共&nbsp;"+data.totalPage+"&nbsp;页&nbsp;&nbsp;";
                    $("#currentPage").html(currentPage);

                    var pageNum = $("#pageNum");
                    
                    pageNum.html("第"+data.pageNo +"页&nbsp;&nbsp;|&nbsp;&nbsp");

                    
                    var ajaxInput = '<input type="hidden" name="ajax" value="true" />';
                    pageForm.append(ajaxInput);
                   	
                }  
		});
}
// 显示更多的城市和公司
function showMoreAddress(para){
	if(para=='addr'){
		var clazz = $(".address").attr("class","");
	}
	if(para=='company'){
		var clazz = $(".company").attr("class","");
	}
	//alert(clazz);
}
// 根据点击地理位置 事件搜索职位
function locationOnChange(obj){
	var qObj = $(obj);
	var location = qObj.text();
	globalLocation = qObj.text();
	ajaxSearch('',globalLocation);
}
//根据点击公司 事件搜索职位
function corporationOnChange(obj){
	var qObj = $(obj);
	var coporationName = qObj.text();
	globalCoporation = qObj.text();
	ajaxSearch('','',globalCoporation);
}
</script>