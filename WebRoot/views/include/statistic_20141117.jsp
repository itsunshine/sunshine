<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.boliao.sunshine.servlet.*" %>

						<link rel="stylesheet" href="<%=BaseServlet.context %>/views/BJAVA_files/screen.css"
							type="text/css" media="screen, projection">
							<link rel="stylesheet" href="<%=BaseServlet.context %>/views/BJAVA_files/print.css"
								type="text/css" media="print">
								<!--[if lt IE 8]>
    <link rel="stylesheet" href="/sunshine/themes/default/css/ie.css" type="text/css" media="screen, projection"/>
    <![endif]-->
								<!-- plugins -->
									<script type="text/javascript"
										src="<%=BaseServlet.context %>/views/BJAVA_files/jquery.min.js"></script>
									<script type="text/javascript" src="<%=BaseServlet.context %>/views/BJAVA_files/global.js"></script>

									<link rel="stylesheet" type="text/css"
										href="<%=BaseServlet.context %>/views/BJAVA_files/jHtmlArea.css">
										<script type="text/javascript"
											src="<%=BaseServlet.context %>/views/BJAVA_files/jquery.jHtmlArea.min.js"></script>

										<script type="text/javascript"
											src="<%=BaseServlet.context %>/views/BJAVA_files/jquery.form.min.js"></script>
										<script type="text/javascript"
											src="<%=BaseServlet.context %>/views/BJAVA_files/jquery.busy.min.js"></script>

										<link rel="stylesheet" type="text/css"
											href="<%=BaseServlet.context %>/views/BJAVA_files/jquery.datePicker.css">
											<script type="text/javascript"
												src="<%=BaseServlet.context %>/views/BJAVA_files/jquery.datePicker.js"></script>
											<script type="text/javascript" src="<%=BaseServlet.context %>/views/BJAVA_files/date.js"></script>

											<link rel="stylesheet" type="text/css"
												href="<%=BaseServlet.context %>/views/BJAVA_files/jquery.simplemodal.css">
												<script type="text/javascript"
													src="<%=BaseServlet.context %>/views/BJAVA_files/jquery.simplemodal.min.js"></script>

												<link rel="stylesheet" type="text/css"
													href="<%=BaseServlet.context %>/views/BJAVA_files/jquery.aqLayer.css">
													<script type="text/javascript"
														src="<%=BaseServlet.context %>/views/BJAVA_files/jquery.aqLayer.min.js"></script>

													<link rel="stylesheet" type="text/css"
														href="<%=BaseServlet.context %>/views/BJAVA_files/jquery.zTree.css">
														<script type="text/javascript"
															src="<%=BaseServlet.context %>/views/BJAVA_files/jquery.zTree.min.js"></script>

														<script type="text/javascript"
															src="<%=BaseServlet.context %>/views/BJAVA_files/jquery.cycle.min.js"></script>

														<link rel="stylesheet" href="<%=BaseServlet.context %>/views/BJAVA_files/style.css"
															type="text/css" media="screen, projection">
															<link rel="stylesheet" href="<%=BaseServlet.context %>/views/BJAVA_files/fragment.css"
																type="text/css" media="screen, projection">
													<script type="text/javascript"
											src="<%=BaseServlet.context %>/views/js/lib/datepicker.js"></script>
																<script type="text/javascript">
	$(document).ready(function() {
		var hasLeftColumn = $.trim($('#body .left-column').html()) != '';
		var hasRightColumn = $.trim($('#body .right-column').html()) != '';
		if (!hasLeftColumn) {
			$('#body .left-column').remove();
			$('#body .main-column').addClass('span-16');
		}
		if (!hasRightColumn) {
			$('#body .right-column').remove();
			$('#body .main-column').addClass('span-19');
			$('#body .main-column').addClass('last');
		}
		if (!hasLeftColumn && !hasRightColumn) {
			$('#body .left-column').remove();
			$('#body .right-column').remove();
			$('#body .main-column').addClass('span-24');
			$('#body .main-column').addClass('last');
		}
	});
</script>