<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.boliao.sunshine.servlet.*"%>

<link rel="stylesheet" 	href="<%=BaseServlet.context %>/views/BJAVA_files/screen.css" type="text/css"/>
<link href="<%=BaseServlet.context %>/views/css/jquery/jquery-ui-1.10.4.custom.css" rel="stylesheet" />
<link href="<%=BaseServlet.context %>/views/css/jquery/jquery.alert.css" rel="stylesheet" />
<!--[if lt IE 8]>
    <link rel="stylesheet" href="/sunshine/themes/default/css/ie.css" type="text/css" media="screen, projection"/>
    <![endif]-->
<!-- plugins -->
<script type="text/javascript" src="<%=BaseServlet.context %>/views/js/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="<%=BaseServlet.context %>/views/js/jquery/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="<%=BaseServlet.context %>/views/js/jquery/jquery.alert.js"></script>
<link rel="stylesheet" href="<%=BaseServlet.context %>/views/BJAVA_files/style.css" type="text/css">
<link rel="stylesheet" href="<%=BaseServlet.context %>/views/BJAVA_files/fragment.css" type="text/css">
<script type="text/javascript">
	$(document).ready(function() {
		//var hasLeftColumn = $.trim($('#body .left-column').html()) != '';
		var hasRightColumn = $.trim($('#body .right-column').html()) != '';
		//if (!hasLeftColumn) {
			//$('#body .left-column').remove();
			//$('#body .main-column').addClass('span-16');
		//}
		if (!hasRightColumn) {
			$('#body .right-column').remove();
			$('#body .main-column').addClass('span-19');
			$('#body .main-column').addClass('last');
		}
		if (!hasRightColumn) {
			$('#body .left-column').remove();
			$('#body .right-column').remove();
			$('#body .main-column').addClass('span-24');
			$('#body .main-column').addClass('last');
		}
	});
	var sunContext = "<%=BaseServlet.context %>";
</script>
<script type="text/javascript" src="<%=BaseServlet.context %>/views/js/customerized/datepicker.js"></script>