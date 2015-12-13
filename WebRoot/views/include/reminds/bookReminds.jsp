<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.boliao.sunshine.servlet.*"%>

<!DOCTYPE html>
<div class="activity-wrapper">
	<div class="activity-body">

		<div class="formmgr-row" style="margin-left: 100px;">
			<label for="title" class="title">
				书名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			<input id="expComName" name="expComName" class="perSet" style="width: 260px;" />
			<br />
			<br />
			<label for="relTech" class="title">
				相关技术&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			<input id="relTech" name="relTech" class="perSet" style="width: 260px;" />
			<br />
			<br />
			<div style="text-align: center">
				<button type="submit" class="button" id="remindsInfoSave">
					创建图书订阅
				</button>
			</div>
		</div>
	</div>
</div>
<!-- 展示内容 结束 -->
<script type="text/javascript">
	$(function(){
		$( "#startTime" ).datepicker({ changeYear: true, changeMonth:true,yearRange: "1960:2010"});
		$( "#endTime" ).datepicker({ changeYear: true, changeMonth:true,yearRange: "1960:2010"});

		/** 保存用户的基本信息 */
		$("#remindsInfoSave").click(function(){remindsInfoSave();});
	})
	function remindsInfoSave(){
		alert("OK");
	}
	</script>

