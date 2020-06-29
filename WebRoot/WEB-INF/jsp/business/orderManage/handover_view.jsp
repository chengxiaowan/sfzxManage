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
<!-- 下拉框 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/chosen.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/colorbox.css" />
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-content-inner">
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
					<div id="zhongxin" style="margin-top: 10px;">
						<div class="span6">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a data-toggle="tab" href="#home"><i class="green icon-home bigger-110"></i> 基本信息</a></li>
								</ul>
								<div class="tab-content">
									<div id="home" class="tab-pane in active">
										<form action="handover/${msg }.do" name="handoverForm" id="handoverForm" method="post" class="form form-horizontal">
											<div class="row">
												<div id="zhongxin" style="padding-top: 13px;">
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-3 control-label">案件编号：</label>
															<div class="col-sm-9">
																<input class="form-control" readonly="readonly" type="text" value="${pd.orderNo }" style="width: 98%">
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-3 control-label">债务人：</label>
															<div class="col-sm-9">
																<input class="form-control" readonly="readonly" type="text" value="${pd.debtorName }" style="width: 98%">
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-3 control-label">承接人名称<font color="red">*</font>：
															</label>
															<div class="col-sm-9">
																<input class="form-control" readonly="readonly" type="text" value="${pd.recipientName }" style="width: 98%">
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-3 control-label">交接时间：</label>
															<div class="col-sm-9">
																<input class="span10 date-picker form-control" data-validate="require" readonly="readonly" data-date-format="yyyy-mm-dd" type="text" name="handoverTime" value="${pd.handoverTime }"
																	style="width: 98%">
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-3 control-label">交接状态：</label>
															<div class="col-sm-8">
																<div class="radio radio-success radio-inline">
																	<input type="radio" disabled="disabled" <c:if test="${ empty pd.status || pd.status == 0}">checked="checked"</c:if> onchange="setStatus('0')" id="form-isDispute-radio1"
																		name="form-isDispute-radio"> <label for="form-isDispute-radio1"> 进行中 </label>
																</div>
																<div class="radio radio-success radio-inline">
																	<input type="radio" disabled="disabled" <c:if test="${pd.status == 1}">checked="checked"</c:if> onchange="setStatus('1')" id="form-isDispute-radio2" name="form-isDispute-radio">
																	<label for="form-isDispute-radio2"> 已完毕 </label>
																</div>
															</div>
														</div>
													</div>
													<div class="col-md-12">
														<div class="form-group">
															<label class="control-label col-sm-2 no-padding-right"> 交接原因: </label>
															<div class="col-sm-10">
																<textarea style="width: 95%; height: 120px;" rows="15" cols="10" name="reason" readonly="readonly">${pd.reason}</textarea>
															</div>
														</div>
													</div>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
						<!--/span-->
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/colorbox.css" />
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
	<!-- ace scripts -->
	<script src="${pageContext.request.contextPath}/static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!--提示框-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.tips.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/myjs/yoValidate.js" type="text/javascript" charset="utf-8"></script>
	<!-- 下拉框 -->
	<!-- 日期框 -->
	<script src="${pageContext.request.contextPath}/static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<script src="${pageContext.request.contextPath}/static/ace/js/chosen.jquery.js"></script>
	<!-- Prettyfile -->
	<script src="${pageContext.request.contextPath}/static/ui/js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
	<script src="${pageContext.request.contextPath}/static/ace/js/jquery.colorbox.js"></script>
</body>
</html>