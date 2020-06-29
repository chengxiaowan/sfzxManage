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
		<div class="wrapper wrapper-content">
			<div class="row">
				<div class="col-sm-12">
					<div class="row form form-horizontal" id="applyForm">
						<div class="col-md-12">
							<div class="form-group">
								<label class="col-sm-2 control-label">发起人：</label>
								<div class="col-sm-9">
									<input disabled="disabled" class="form-control" type="text" name="target" value="${pd.fqr }" />
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="col-sm-2 control-label">指派时间：</label>
								<div class="col-sm-9">
									<input disabled="disabled" class="form-control" type="text" name="target" value="${pd.zpCreateTime }" />
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="col-sm-2 control-label">指派事由：</label>
								<div class="col-sm-9">
									<input disabled="disabled" class="form-control" type="text" name="target" value="${pd.zpReason }" />
								</div>
							</div>
						</div>
						<input type="hidden" name="wcqk" id="wcqk" value="<c:out value=" ${pd.wcqk} " default="0 "></c:out>" />
						<div class="col-md-12">
							<div class="form-group">
								<label class="col-sm-2 control-label">是否完成：</label>
								<div class="col-sm-9">
									<input type="radio" <c:if test="${empty pd.wcqk || pd.wcqk == '0'}">checked="checked"</c:if> onchange="setinvoiceType('0')" id="form-isinvoiceType-radio2" name="form-isinvoiceType-radio">
									<label for="form-isinvoiceType-radio2">否</label>
									<input type="radio" <c:if test="${pd.wcqk == '1'}">checked="checked"</c:if> onchange="setinvoiceType('1')" id="form-isinvoiceType-radio1" name="form-isinvoiceType-radio"> <label for="form-isinvoiceType-radio1">是</label>
								</div>
							</div>
						</div>
						<div class="col-md-12 aa" style="margin-bottom: 5px;">
							<div class="form-group">
								<label class="col-sm-2 control-label">备注：</label>
								<div class="col-sm-9">
									<textarea class="form-control" style="height: 180px;resize: none;" name="remark" value="" placeholder="请输入备注" id="remark" value="${pd.remark }">${pd.remark }</textarea>
								</div>
							</div>
						</div>
						<c:if test="${pd.mark==1}">
							<div class="text-center">
								<a class="btn btn-mini btn-primary" onclick="sure();" style="padding: 7px 20px;margin-right: 10px;"> 确定</a>
								<a class="btn btn-mini btn-danger" onclick="yocto.closeChildLayer();" style="padding: 7px 20px;">关闭</a>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<!-- 页面底部js¨ -->
			<%@ include file="../../system/index/js.jsp"%>
	</body>
	<script>
		function setinvoiceType(value) {
			$("#wcqk").val(value);
		}

		function sure() {
			var remark = $("#remark").val();
			if(remark == '' || remark == null) {
				layer.msg('请输入备注')
				return false
			}
			var wcqk = $("#wcqk").val();
			var url = '/workBench/doUpdate.do?id=${pd.id}' + '&remark=' + remark + '&wcqk=' + wcqk
			var index = parent.layer.getFrameIndex(window.name);
			$.get(url, function(data) {
				if(data.error == '00') {
					layer.msg('保存成功')
					parent.a()
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				} else {
					layer.msg(data.msg)
				}
			})
		}
	</script>

</html>