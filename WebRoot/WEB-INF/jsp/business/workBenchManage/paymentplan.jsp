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
						<div class="tab-content">
							<%-- 									<c:if test="${!pd.hasDetail }"> --%>
							<div id="home" class="tab-pane in active">
								<form action="workBench/${msg }.do" name="customerForm" id="customerForm" method="post" class="form form-horizontal">
									<input type="hidden" name="orderId" id="orderId" value="<c:out value="${pd.orderId}" default="0"></c:out>" /><input type="hidden" name="taskId" id="taskId"
										value="<c:out value="${pd.taskId}" default="0"></c:out>" /> <input type="hidden" name="type" id="type" value="<c:out value="${pd.type}" default="0"></c:out>" />
									<div class="row">
										<div id="zhongxin" style="padding-top: 13px;">
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">计划开始回款时间&nbsp;<font color="red">*</font>:
													</label>
													<div class="col-sm-8">
														<input class="span10 date-picker form-control" type="text" id="planTime" name="planTime" data-validate="require" readonly="readonly" data-date-format="yyyy-mm-dd"
															placeholder="这里输入计划开始回款时间" value="${pd.planTime }">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">单次回款金额(元)&nbsp;<font color="red">*</font>:
													</label>
													<div class="col-sm-8">
														<input class="form-control" data-validate="require|price|maxLength=50" type="text" id="onceMoney" name="onceMoney" placeholder="这里输入单次回款金额" value="${pd.onceMoney }">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">回款期数:
													</label>
													<div class="col-sm-8">
														<input class="form-control" data-validate="number|maxLength=50" type="text" id="periods" name="periods" placeholder="这里输入回款期数" value="${pd.periods }">
													</div>
												</div>
											</div>
											<div class="col-md-12">
												<div class="form-group">
													<label class="control-label col-sm-2 no-padding-right">备注:</label>
													<div class="col-sm-10">
														<textarea class="form-control" id="remark" name="remark" style="width: 95%; height: 120px;" rows="15" cols="10" placeholder="这里输入备注">${pd.remark}</textarea>
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
						<%-- 						<c:if test="${!pd.hasDetail }"> --%>
						<a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a>
						<%-- 						</c:if> --%>
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	</div>
	<%@ include file="../../system/index/js.jsp"%>
</body>
<script type="text/javascript">
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