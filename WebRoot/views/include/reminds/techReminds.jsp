<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.boliao.sunshine.servlet.*"%>

<!DOCTYPE html>
<div class="activity-wrapper">
	<div class="activity-body">

		<div class="formmgr-row">
			<label for="title" class="title">
				期望技术&nbsp;&nbsp;
				<b class="req"> * </b>
			</label>
			<input id="expComName" name="expComName" class="perSet" />
			<div style="float: right;">
				<label for="phoneNum">
					技术框架
					<b class="req"> * </b>
				</label>
				<input id="expPosition" name="expPosition" class="perSet" />
			</div>
			<br />
			<br />
			<label for="startTime">
				开始时间&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			<input id="startTime" name="startTime" class="perSet" />
			<div style="float: right;">
				<label for="endTime">
					结束时间&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</label>
				<input id="endTime" name="endTime" class="perSet" />
			</div>
			<br />
			<br />
			<div style="text-align: center">
				<button type="submit" class="button" id="remindsInfoSave">
					创建技术提醒
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

