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
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-content-inner">
		<div class="page-content">
				<div class="col-xs-12">
					<div class="tabbable">
						<ul class="nav nav-tabs" id="myTab">
							<c:if test="${empty dataList[0].auditer }">
								<li class="active"><a data-toggle="tab" href="#home">申请<c:if test="${pd.type == 5}">诉讼</c:if> <c:if test="${pd.type == 6}">仲裁</c:if></a></li>
							</c:if>
							<c:if test="${not empty dataList[0].auditer }">
								<li><a class="active" data-toggle="tab" href="#dataList">申请状况</a></li>
							</c:if>
						</ul>
						<div class="tab-content">
							<c:if test="${empty dataList[0].auditer }">
								<div id="home" class="tab-pane in active">
									<form action="workBench/${msg }.do" name="customerForm" id="customerForm" method="post" class="form form-horizontal">
										<input type="hidden" name="id" id="id" value="<c:out value="${dataList[0].id}" default="0"></c:out>" /> <input type="hidden" name="orderId" id="orderId"
											value="<c:out value="${pd.orderId}" default="0"></c:out>" /><input type="hidden" name="taskId" id="taskId" value="<c:out value="${pd.taskId}" default="0"></c:out>" /> <input type="hidden"
											name="type" id="type" value="<c:out value="${pd.type}" default="0"></c:out>" /> <input type="hidden" name="applyType" id="applyType"
											value="<c:out value="${dataList[0].applyType}" default="0"></c:out>" />
										<div class="row">
											<div id="zhongxin" style="padding-top: 13px;">
												<div class="col-md-6">
													<div class="form-group">
														<label class="col-sm-4 control-label">申请类型:</label>
														<div class="col-sm-8">
															<div class="radio radio-success radio-inline">
																<input type="radio" <c:if test="${ empty dataList[0].applyType || dataList[0].applyType == '0'}">checked="checked"</c:if> onchange="setRadio('0')" id="form-radio1" name="form-radio">
																<label for="form-radio1"> 诉讼 </label>
															</div>
															<div class="radio radio-success radio-inline">
																<input type="radio" <c:if test="${dataList[0].applyType == '1'}">checked="checked"</c:if> onchange="setRadio('1')" id="form-radio2" name="form-radio"> <label for="form-radio2">
																	仲裁 </label>
															</div>
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="control-label col-sm-2 no-padding-right">申请原因&nbsp;<font color="red">*</font>:
														</label>
														<div class="col-sm-10">
															<textarea class="form-control" style="width: 95%; height: 120px;" rows="15" cols="10" id="remark" name="remark" data-validate="require|maxLength=500" placeholder="这里输入申请原因">${dataList[0].remark}</textarea>
														</div>
													</div>
												</div>
											</div>
										</div>
									</form>
								</div>
							</c:if>
							<c:if test="${not empty dataList[0].auditer }">
								<div id="dataList" class="tab-pane active form-horizontal">
									<div class="col-md-12">
										<div class="form-group">
											<label class="col-sm-2 control-label">申请时间:</label>
											<div class="col-sm-5">
												<input class="form-control" readonly="readonly" type="text" value="${dataList[0].createTime}">
											</div>
										</div>
									</div>
									<hr />
									<div class="col-md-12">
										<div class="form-group">
											<label class="col-sm-2 control-label">申请原因:</label>
											<div class="col-sm-5">
												<textarea class="form-control" readonly="readonly" style="width: 100%; height: 120px;" rows="15" cols="10">${dataList[0].remark}</textarea>
											</div>
										</div>
									</div>
									<hr />
									<div class="col-md-12">
										<div class="form-group">
											<label class="col-sm-2 control-label">审核状态:</label>
											<div class="col-sm-5">
												<input class="form-control" readonly="readonly" type="text" value="${dataList[0].auditStatus}">
											</div>
										</div>
									</div>
									<hr />
									<div class="col-md-12">
										<div class="form-group">
											<label class="col-sm-2 control-label">审核人:</label>
											<div class="col-sm-5">
												<input class="form-control" readonly="readonly" type="text" value="${dataList[0].auditer}">
											</div>
										</div>
									</div>
									<hr />
									<div class="col-md-12">
										<div class="form-group">
											<label class="col-sm-2 control-label">审核时间:</label>
											<div class="col-sm-5">
												<input class="form-control" readonly="readonly" type="text" value="${dataList[0].auditTime}">
											</div>
										</div>
									</div>
									<hr />
									<div class="col-md-12">
										<div class="form-group">
											<label class="col-sm-2 control-label">审核意见:</label>
											<div class="col-sm-5">
												<textarea class="form-control" readonly="readonly" style="width: 100%; height: 120px;" rows="15" cols="10">${pd.auditIdea}</textarea>
											</div>
										</div>
									</div>
								</div>
							</c:if>
						</div>
					</div>
				</div>
				<!--/span-->
			<div class="text-center">
				<c:if test="${empty dataList[0].auditer }">
					<a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a>
				</c:if>
			</div>
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	<!-- /.main-container -->
	<%@ include file="../../system/index/js.jsp"%>

	<!--提示框-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.tips.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/myjs/yoValidate.js" type="text/javascript" charset="utf-8"></script>

</body>
<script type="text/javascript">
	function setRadio(value) {
		$("#applyType").val(value);
	}
	//保存
	function save() {
		if (yoValidate('#customerForm')) {
			$("#customerForm").submit();
		}
	}
</script>
</html>