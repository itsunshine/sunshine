<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${page.totalPage gt page.pageNo}">
		<div style="text-align: center">
			<a id="more-action" href="${uri }">更多</a>
		</div>
</c:if>
<script type="text/javascript">
$(document).ready(function(){
	var pageNo = 2;
	$('#more-action').click(function(){
		var self = this;
		var url = $(this).attr('href');
		var params = {'pageNo':pageNo,'ajax':'true'};
		$.get(url, params, function(resp){
		var jsonObject = jQuery.parseJSON(resp);
		var size = jsonObject.results.length;
		alert(size);
		for(var i=0;i<size;i++){
			var html = '<li class="activity"><a href="http://localhost:8080/sunshine/q1xh4o-8/profile">'+
			'<img class="top left thumbnail" src="./BJAVA_files/people.png"'+
				'width="40" height="40">'+
		'</a>'+
		'<div class="activity-wrapper">'+
			'<div class="activity-body">'+
				'<div class="right">'+
					'<span class="pretty-tim ">广告位</span>'+
					'<a class="comment-action" id="78"'+
						'href="'+ "${context }" +'/views/index.do?m=getContent&id='+ jsonObject.results[i].id+
						'" >广告位 </a>'+
				'</div>'+
				'<a href="'+ "${context }" + '/views/index.do?m=getContent&id='+ jsonObject.results[i].id + '" target="blank">'+
				jsonObject.results[i].title + '</a>'+
				'</div>'+
									'</div>'+
								'</li>'
				
			$(html).appendTo($('#activities-list'));
		}
		var advs = '<div style="text-align: center">' +
		'<a id="more-action7361c531-ec85-4f98-8577-56722c0a88e9" href="http://localhost:8080/sunshine/process/fragment/7361c531-ec85-4f98-8577-56722c0a88e9">广告位</a>' +
		'</div>'
		$(advs).appendTo($('#activities-list'));
		
			if((pageNo+1)<=${page.totalPage}) {
				pageNo++;
			} else {
				$(self).hide();
			}
		});
		return false;
	});
});
</script>