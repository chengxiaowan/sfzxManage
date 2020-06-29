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
						<div class="span6">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a data-toggle="tab" href="#home"><i class="green icon-home bigger-110"></i> 填写欠款催收报告</a></li>
								</ul>
								<div class="tab-content">
									<div id="home" class="tab-pane in active">
										<form action="workBench/${msg }.do" name="customerForm" id="customerForm" method="post" class="form form-horizontal">
											<input type="hidden" name="id" id="id" value="<c:out value="${dataList[0].id}" default="0"></c:out>" /> <input type="hidden" name="orderId" id="orderId"
												value="<c:out value="${pd.orderId}" default="0"></c:out>" /><input type="hidden" name="taskId" id="taskId" value="<c:out value="${pd.taskId}" default="0"></c:out>" /><input type="hidden"
												name="type" id="type" value="<c:out value="${pd.type}" default="0"></c:out>" /> <input type="hidden" name="warnType" id="warnType"
												value="<c:out value="${dataList[0].warnType}" default="0"></c:out>" />
											<div class="row" style="margin-top:10px;">
													<div class="col-md-12">
														<div class="form-group">
															<label class="col-sm-2 control-label">提醒方式:</label>
															<div class="col-sm-10">
																<div class="radio radio-success radio-inline">
																	<input type="radio" <c:if test="${ empty dataList[0].warnType || dataList[0].warnType == '0'}">checked="checked"</c:if> onchange="setRadio('0')" id="form-radio1" name="form-radio">
																	<label for="form-radio1"> 短信 </label>
																</div>
																<div class="radio radio-success radio-inline">
																	<input type="radio" <c:if test="${dataList[0].warnType == '1'}">checked="checked"</c:if> onchange="setRadio('1')" id="form-radio2" name="form-radio"> <label for="form-radio2">
																		电话 </label>
																</div>
															</div>
														</div>
													</div>
													<div class="col-md-12">
														<div class="form-group">
															<label class="control-label col-sm-2 no-padding-right">备注&nbsp;<font color="red">*</font>:
															</label>
															<div class="col-sm-10">
																<textarea class="form-control" style="width: 95%; height: 120px;" rows="15" cols="10" id="remark" name="remark" data-validate="require|maxLength=500" placeholder="这里输入备注">${dataList[0].remark}</textarea>
															</div>
														</div>
												</div>
											</div>
										</form>
								</div>
							</div>
						</div>
						<!--/span-->
					</div>
					<div class="text-center">
						<a class="btn btn-mini btn-primary" onclick="save();">保存</a> 
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
	function setRadio(value) {
		$("#warnType").val(value);
	}
	//保存
	function save() {
		if (yoValidate('#customerForm')) {
			$("#customerForm").submit();
		}
	}
</script>
</html>