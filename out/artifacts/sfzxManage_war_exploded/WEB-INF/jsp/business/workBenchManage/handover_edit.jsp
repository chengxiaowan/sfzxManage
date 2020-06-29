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
										<form action="workBench/${msg }.do" name="handoverForm" id="handoverForm" method="post" class="form form-horizontal">
											<input type="hidden" name="id" id="id" value="${dataList[0].id }" /><input type="hidden" name="taskId" id="taskId" value="<c:out value="${pd.taskId}" default="0"></c:out>" /><input
												type="hidden" name="type" id="type" value="<c:out value="${pd.type}" default="0"></c:out>" /> <input type="hidden" name="orderId" id="orderId" value="${pd.orderId }" /> <input
												type="hidden" name="recipientId" id="recipientId" value="${dataList[0].recipientId }" /> <input type="hidden" name="status" id="status" value="${dataList[0].status}" />
											<div class="row">
												<div id="zhongxin" style="padding-top: 13px;">
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">承接人名称<font color="red">*</font>：
															</label>
															<div class="col-sm-8">
																<input class="form-control" type="text" data-validate="require|maxLength=50" readonly="readonly" placeholder="这里输入承接人名称" name="recipientName" id="recipientName"
																	value="${dataList[0].recipientName }" onclick="chooseUser()" style="width: 98%">
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">交接时间&nbsp;<font color="red">*</font>：
															</label>
															<div class="col-sm-8">
																<input class="span10 date-picker form-control" data-validate="require" readonly="readonly" data-date-format="yyyy-mm-dd" type="text" placeholder="这里输入交接时间" name="handoverTime"
																	value="${dataList[0].handoverTime }" style="width: 98%">
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">交接状态：</label>
															<div class="col-sm-8">
																<div class="radio radio-success radio-inline">
																	<input type="radio" <c:if test="${ empty dataList[0].status || dataList[0].status == 0}">checked="checked"</c:if> onchange="setStatus('0')" id="form-isDispute-radio1"
																		name="form-isDispute-radio"> <label for="form-isDispute-radio1"> 进行中 </label>
																</div>
																<div class="radio radio-success radio-inline">
																	<input type="radio" <c:if test="${dataList[0].status == 1}">checked="checked"</c:if> onchange="setStatus('1')" id="form-isDispute-radio2" name="form-isDispute-radio"> <label
																		for="form-isDispute-radio2"> 已完毕 </label>
																</div>
															</div>
														</div>
													</div>
													<div class="col-md-12">
														<div class="form-group">
															<label class="control-label col-sm-2 no-padding-right"> 交接原因: </label>
															<div class="col-sm-10">
																<textarea class="form-control" style="height: 120px;" rows="15" cols="10" name="reason" placeholder="这里输入原因">${dataList[0].reason}</textarea>
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
					<div class="text-center">
						<a class="btn btn-mini btn-primary" onclick="save();">保存</a> 
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
	$(function() {
		if ('${pd.id}') {
			$("#orderNo").attr("disabled", true);
		}
	})

	function setStatus(value) {
		$("#status").val(value);
	}

	$('.date-picker').datepicker({
		autoclose : true,
		todayHighlight : true
	});

	function chooseUser() {
		sessionStorage.ListTagsCache = '0' //关闭缓存
		var index = layer
				.open({
					type : 2,
					title : '选择案件交接人',
					content : '${pageContext.request.contextPath}/user/chooseUser.do?flags=0&ROLE_ID=b693f826af0545b5a1c60447a27c3089&parIds=recipientId&parNames=recipientName',
					area : [ '90%', '90%' ],
					btn : [ '确定', '取消' ],
					yes : function(index, layero) {
						var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
						var data = iframeWin.pushToParent()
						layer.close(index)
						$("#recipientId").val(data.ids); //赋值子页面传过来的IDS
						$("#recipientName").val(data.names);
						sessionStorage.ListTagsCache = '0' //关闭缓存
					}
				});
	}

	function save() {
		if (yoValidate('#handoverForm')) {
			if (!$("#status").val()) {
				setStatus('0');
			}
			var index = layer.load(1, {
				shade : [ 0.2, '#fff' ]
			//0.1透明度的白色背景
			});
			$("#handoverForm").submit();
		}
	}
</script>
</html>