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
		<div class="ibox-content">
			<div class="row">
				<div class="col-xs-12">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a data-toggle="tab" href="#home"><i class="green icon-home bigger-110"></i> 基本信息</a></li>
								</ul>
								<div class="tab-content">
									<div id="home" class="tab-pane in active">
										<form action="workBench/${msg }.do" name="customerForm" id="customerForm" method="post" class="form form-horizontal">
											<input type="hidden" name="id" id="id" value="<c:out value="${pd.id}" default="0"></c:out>" /> <input type="hidden" name="isCompleted" id="isCompleted"
												value="<c:out value="${pd.isCompleted}" default="0"></c:out>" /> <input name="visitId" type="hidden" value="${pd.visitId}"><input type="hidden" name="evaluatSelf" id="evaluatSelf"
												value="<c:out value="${pd.evaluatSelf}" default="0"></c:out>" />
											<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">案件编号:</label>
															<div class="col-sm-8">
																<input class="form-control" readonly="readonly" type="text" value="${pd.orderNo}">
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">联系人:</label>
															<div class="col-sm-8">
																<input class="form-control" readonly="readonly" type="text" value="${pd.linkman}">
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">任务类型:</label>
															<div class="col-sm-8">
																<input class="form-control" readonly="readonly" type="text" value="${pd.typeDesc}">
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">预计完成时间:</label>
															<div class="col-sm-8">
																<input class="form-control" readonly="readonly" type="text" value="${pd.evaluateTime}">
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">外访原由:</label>
															<div class="col-sm-8">
																<textarea class="form-control" style="height: 120px;" readonly="readonly">${pd.reason}</textarea>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">领导指示:</label>
															<div class="col-sm-8">
																<textarea class="form-control" style="height: 120px;" readonly="readonly">${pd.leaderShip}</textarea>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">完成时间:</label>
															<div class="col-sm-8">
																<input class="span10 date-picker form-control" data-validate="require" readonly="readonly" data-date-format="yyyy-mm-dd" type="text" id="realTime" name="realTime"
																	value="${pd.realTime}">
															</div>
														</div>
													</div>
													<hr />
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">是否完成:</label>
															<div class="col-sm-8">
																<div class="radio radio-success radio-inline">
																	<input type="radio" <c:if test="${ empty pd.isCompleted || pd.isCompleted == '0'}">checked="checked"</c:if> onchange="setIsCompleted('0')" id="form-completed-radio1"
																		name="form-completed-radio"> <label for="form-completed-radio1"> 未完成 </label>
																</div>
																<div class="radio radio-success radio-inline">
																	<input type="radio" <c:if test="${pd.isCompleted == '1'}">checked="checked"</c:if> onchange="setIsCompleted('1')" id="form-completed-radio2" name="form-completed-radio"> <label
																		for="form-completed-radio2"> 已完成 </label>
																</div>
															</div>
														</div>
													</div>
													<div class="col-md-12">
														<div class="form-group">
															<label class="control-label col-sm-2 no-padding-right">完成情况:</label>
															<div class="col-sm-10">
																<textarea class="form-control" <c:if test="${pd.isCompleted == 1}">readonly="readonly"</c:if> style="width: 95%; height: 120px;" rows="15" cols="10" id="remark" name="remark"
																	data-validate="require|maxLength=500" placeholder="这里输入完成情况">${pd.remark}</textarea>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">自我评价:</label>
															<div class="col-sm-8">
																<div class="radio radio-success radio-inline">
																	<input type="radio" <c:if test="${pd.isCompleted == 1}">disabled="disabled"</c:if> <c:if test="${ empty pd.evaluatSelf || pd.evaluatSelf == '0'}">checked="checked"</c:if>
																		onchange="setEvaluatSelf('0')" id="form-evaluatSelf-radio1" name="form-evaluatSelf-radio"> <label for="form-evaluatSelf-radio1"> 优秀 </label>
																</div>
																<div class="radio radio-success radio-inline">
																	<input type="radio" <c:if test="${pd.isCompleted == 1}">disabled="disabled"</c:if> <c:if test="${pd.evaluatSelf == '1'}">checked="checked"</c:if> onchange="setEvaluatSelf('1')"
																		id="form-evaluatSelf-radio2" name="form-evaluatSelf-radio"> <label for="form-evaluatSelf-radio2"> 良好 </label>
																</div>
																<div class="radio radio-success radio-inline">
																	<input type="radio" <c:if test="${pd.isCompleted == 1}">disabled="disabled"</c:if> <c:if test="${pd.evaluatSelf == '2'}">checked="checked"</c:if> onchange="setEvaluatSelf('2')"
																		id="form-evaluatSelf-radio3" name="form-evaluatSelf-radio"> <label for="form-evaluatSelf-radio3"> 有待改进 </label>
																</div>
															</div>
														</div>
													</div>
												</div>
										</form>
									</div>
						</div>
						<!--/span-->
					</div>
					<div class="text-center">
						<c:if test="${pd.isCompleted == 0}">
							<a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a>
						</c:if>
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	</div>
	<!-- /.main-container -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
</body>
<script type="text/javascript">
	function setIsCompleted(value) {
		$("#isCompleted").val(value);
	}
	function setEvaluatSelf(value) {
		$("#evaluatSelf").val(value);
	}

	$('.date-picker').datepicker({
		autoclose : true,
		todayHighlight : true
	});
	//保存
	function save() {
		if (yoValidate('#customerForm')) {
			$("#customerForm").submit();
		}
	}
</script>
</html>