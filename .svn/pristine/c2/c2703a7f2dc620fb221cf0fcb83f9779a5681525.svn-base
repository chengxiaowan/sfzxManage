<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="${pageContext.request.contextPath}/">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-content-inner">
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
							<div class="tabbable">
								<div class="ibox-content">
										<form action="workBench/${msg }.do" name="contractForm" id="contractForm" method="post"  class="form form-horizontal" >
										<div class="tab-content">
										<div id="home" class="tab-pane in active">
										<input type="hidden" name="id" id="id" value="<c:out value="${pd.id}" default="0"></c:out>" />
										<input type="hidden" name="isSend" id="isSend" value="${pd.isSend }"/>
										<div class="row">
														<div class="col-md-12">
															<div class="form-group">
																<label class="col-sm-2 control-label">是否已收：</label>
																<div class="col-sm-10">
																	<div class="radio radio-success radio-inline">
																		<input type="radio" <c:if test="${ empty pd.isSend || pd.isSend == '0'|| pd.isSend == '1'}">checked="checked"</c:if> onchange="setDispute('0')" id="form-isDispute-radio1" name="form-isDispute-radio">
																		<label for="form-isDispute-radio1"> 否 </label>
																	</div>
																	<div class="radio radio-success radio-inline">
																		<input type="radio" <c:if test="${pd.isSend == '2'}">checked="checked"</c:if> onchange="setDispute('2')" id="form-isDispute-radio2" name="form-isDispute-radio"> <label
																			for="form-isDispute-radio2"> 是 </label>
																	</div>
																</div>
															</div>
														</div>
														<div class="col-md-12">
													<div class="form-group">
														<label  class="control-label col-sm-2">
															备注:
														</label>
														<div class="col-sm-10">
														<textarea class="form-control" style="height: 120px;"  name="content"  placeholder="这里输入备注内容">${pd.content}</textarea>
														</div>
													</div>	
													</div>													
												</div>
												</div>
												</div>
										</form>
								</div>
							</div>
					</div>
					<div class="text-center">
						<a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a>
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	<!-- /.main-container -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.tips.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/myjs/yoValidate.js" type="text/javascript" charset="utf-8"></script>
	<!-- 日期框 -->
	<script src="${pageContext.request.contextPath}/static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/ui/js/plugins/webuploader/webuploader.min.js"></script>
	<!-- Prettyfile -->
	<script src="${pageContext.request.contextPath}/static/ui/js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
	<script src="${pageContext.request.contextPath}/static/ace/js/jquery.colorbox.js"></script>
</body>
<script type="text/javascript">

	var init_uploader_state=0;
	$('.date-picker').datepicker({
		autoclose : true,
		todayHighlight : true
	});
	//保存
	function save() {
		if (yoValidate('#contractForm')) {
			 	var index = layer.load(1, {
						shade : [ 0.2, '#fff' ]
					//0.1透明度的白色背景
					});
				 	if (!$("#isSend").val()) {
	     				setDispute('0');
	     			}
					$("#contractForm").submit();
				} 
			}

	function setDispute(value) {
		$("#isSend").val(value);
	}
</script>
</html>