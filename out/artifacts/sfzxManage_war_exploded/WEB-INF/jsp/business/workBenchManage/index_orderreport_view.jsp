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

	<body class="w100">
		<!-- 新增/编辑 案件报告 -->
		<div class="ibox-content">
			<div class="row">
				<div class="col-xs-12">
					<div class="tabbable">
						<form action="workBench/${msg }.do" name="customerForm" id="customerForm" method="post" class="form form-horizontal">
							<div class="tab-content">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group col-md-6">
											<label class="col-sm-3 control-label">客户名称:
												</label>
											<div class="col-sm-9">
												<input class="form-control" disabled="disabled" name="" type="text" value="${pd.bgCustomerName}">
											</div>
										</div>
										<div class="form-group  col-md-6">
											<label class="col-sm-3 control-label">债务人(公司):
												</label>
											<div class="col-sm-7">
												<input class="form-control" disabled="disabled" name="" type="text" value="${pd.bgDebtorName}">
											</div>
											<div class="col-sm-2 " style="line-height: 34px;">
												<a onclick="look(${pd.dfbgOrderId})">去看看>></a>
											</div>
										</div>
									</div>
									<div class="col-md-12">
										<div class="form-group col-md-6">
											<label class="col-sm-3 control-label">报告标题:
												</label>
											<div class="col-sm-9">
												<input class="form-control" disabled="disabled" name="title" type="text" value="${pd.bgTitle}">
											</div>
										</div>
										<div class="form-group col-md-6 marks">
											<label class="col-sm-3 control-label">报告类型：</label>
											<div class="col-sm-9">
												<input type="text" id="type" disabled="disabled" class="form-control" value="<c:if test=" ${pd.reportType==0 } ">进展</c:if><c:if test="${pd.reportType==1 } ">结案</c:if><c:if test="${pd.reportType==2 } ">关闭</c:if>" />
											</div>
										</div>
									</div>

									<div class="col-md-12">
										<div class="form-group col-md-6 ">
											<label class="col-sm-3 control-label">邮箱:</label>
											<div class="col-sm-9">
												<input class="form-control" disabled="disabled" id="email" name="email" type="text" value="${pd.orderEmail}">
											</div>
										</div>
										<div class="form-group col-md-6 " id="hxjhBOX">
											<label class="control-label col-sm-3 no-padding-right">运作人:
												</label>
											<div class="col-sm-9">
												<input class="form-control" disabled="disabled" name="" type="text" value="${pd.bgRunnerName}">
											</div>
										</div>

									</div>
									<div class="col-md-12">
										<div class="form-group col-md-12">
											<label class="control-label col-sm-2 no-padding-right" style="width: 12%;">报告内容:
												</label>
											<div class="col-sm-9" style="width: 85%;">
												<textarea class="form-control" disabled="disabled" style=" height: 120px;resize: none;" rows="15" cols="10" id="remark" name="remark" data-validate="require|maxLength=500">${pd.bgRemark}</textarea>
											</div>
										</div>
									</div>
									<c:if test="${pd.reportType != 1 }">
										<div class="col-md-12" id="hxjhBOX">
											<div class="form-group col-md-12">
												<label class="control-label col-sm-2 no-padding-right" style="width: 12%;">后续计划:
												</label>
												<div class="col-sm-9" style="width: 85%;">
													<textarea class="form-control" readonly="readonly" style=" height: 120px;resize: none;" rows="15" cols="10" id="hxjh" name="hxjh">${pd.hxjh}</textarea>
												</div>
											</div>
										</div>
									</c:if>

									<div class="col-md-12">
										<div class="form-group col-md-6 ">
											<label class="col-sm-3 control-label">联系人:</label>
											<div class="col-sm-9">
												<input class="form-control" id="hName" type="text" value="" placeholder="请输入联系人">
											</div>
										</div>
										<div class="form-group col-md-6 " id="hxjhBOX">
											<label class="control-label col-sm-3 no-padding-right">邮箱:
												</label>
											<div class="col-sm-9">
												<input class="form-control" id="hEmail" name="email" type="text" data-validate="require|email" value="" placeholder="请输入邮箱">
											</div>
										</div>
										<div class="text-center">
											<a class="btn btn-mini btn-primary" onclick="senEmail();"><i class="fa fa-check"></i> 确定</a>
											<a class="btn btn-mini btn-danger" onclick="yocto.closeChildLayer();"><i class="fa fa-remove"></i> 关闭</a>
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
		<!-- 页面底部js¨ -->
		<%@ include file="../../system/index/js.jsp"%>
	</body>
	<script type="text/javascript">
		//不为关闭时隐藏后续计划
		if($("#type")[0].value != 1) {
			$("#hxjhBOX").show().find("textarea").prop("disabled", false)
		} else {
			$("#hxjhBOX").hide().find("textarea").prop("disabled", true).val("")
		}

		function senEmail() {
			if(yoValidate('#customerForm')) {
				var email = $("#hEmail").val();
				var linkmanName = $("#hName").val();
				if(linkmanName == '' || linkmanName == null) {
					layer.msg('请输入联系人');
					return false;
				}

				var url = '/workBench/sendEmail?id=${pd.dfbgId}&email=' + email + '&linkmanName=' + linkmanName;
				$.get(url, function(res) {
					if(res.error == '00') {
						layer.msg('发送成功');
						parent.a()
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);

					} else {
						layer.msg(res.msg);
					}
				})
			}
		}

		function look(id) {
			var index = layer.open({
				type: 2,
				title: '查看详情',
				closeBtn: 1,
				content: '/static/page/case_detail.html?id=' + id,
				area: ['100%', '100%']
			});
		}
	</script>

</html>