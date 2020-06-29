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
					<div id="zhongxin" style="margin-top: 10px;">
						<div class="span6">
							<div class="tabbable">
								<div class="tab-content">
									<div id="home" class="tab-pane in active">
										<form action="workBench/${msg }.do" name="customerForm" id="customerForm" method="post" class="form form-horizontal">
											<input type="hidden" name="id" id="id" value="<c:out value="${dataList[0].id}" default="0"></c:out>" /><input type="hidden" name="orderId" id="orderId"
												value="<c:out value="${pd.orderId}" default="0"></c:out>" /><input type="hidden" name="taskId" id="taskId" value="<c:out value="${pd.taskId}" default="0"></c:out>" /> <input
												type="hidden" name="type" id="type" value="<c:out value="${pd.type}" default="0"></c:out>" />
											<div class="row">
												<div class="col-md-12 m-t-md">
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">回款时间&nbsp;<font color="red">*</font>:
															</label>
															<div class="col-sm-8">
																<input class="span10 date-picker form-control" type="text" id="planTime" name="planTime" data-validate="require" readonly="readonly" data-date-format="yyyy-mm-dd"
																	placeholder="这里输入回款时间" value="<%-- ${dataList[0].planTime } --%>">
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">回款金额(元)&nbsp;<font color="red">*</font>:
															</label>
															<div class="col-sm-8">
																<input class="form-control"  data-validate="require|price|maxLength=50" type="text" id="money" name="money" placeholder="这里输入回款金额" value="${pd1.money }">
															</div>
														</div>
													</div>
														<c:if test="${pd1.status==0||pd1.status==3||pd1.status==4}">
															<c:if test="${ pd1.cType==0 }">
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">佣金比例(%)&nbsp;<font color="red">*</font>:
															</label>
															<div class="col-sm-8">
																<input class="form-control" data-validate="require|number|maxLength=50" type="text" readonly="readonly" id="commissionRate" name="commissionRate" placeholder="这里输入佣金比例"
																	value="${pd1.commissionRate }">
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">佣金金额(元)&nbsp;<font color="red">*</font>:
															</label>
															<div class="col-sm-8">
																<input class="form-control" data-validate="require|price|maxLength=50" readonly="readonly" type="text" id="commissionaMount" name="commissionaMount" placeholder="这里输入佣金金额"
																	value="<c:if test="${not empty pd1.money }">${pd1.money*pd1.commissionRate/100}</c:if>">
															</div>
														</div>
													</div>
														</c:if>
														<c:if test="${ pd1.cType==1 }">
														<input type="hidden" name = "commissionaMount" id="commHIDDEN" />
														
														</c:if>
														</c:if>
														<%-- <c:if test="${pd1.status==3||pd1.status==4}">
															<c:if test="${ pd1.cType==0 }">
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">佣金比例(%)&nbsp;<font color="red">*</font>:
															</label>
															<div class="col-sm-8">
																<input class="form-control" data-validate="require|number|maxLength=50" type="text" id="commissionRate" name="commissionRate" placeholder="这里输入佣金比例"
																	value="${pd1.commissionRate }">
															</div>
														</div>
													</div>
															</c:if>
													<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">佣金金额(元)&nbsp;<font color="red">*</font>:
															</label>
															<div class="col-sm-8">
																<input class="form-control" data-validate="require|price|maxLength=50" type="text" id="commissionaMount" name="commissionaMount" placeholder="这里输入佣金金额"
																	value="${pd1.commissionaMount }">
															</div>
														</div>
													</div>
														</c:if> --%>
													<div class="col-md-12">
														<div class="form-group">
															<label class="control-label col-sm-2 no-padding-right">备注:</label>
															<div class="col-sm-10">
																<textarea class="form-control" id="remark" name="remark" style=" height: 120px;" rows="15" cols="10" placeholder="这里输入备注"><%-- ${dataList[0].remark} --%></textarea>
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
						<c:if test="${!pd.hasDetail }">
							<a class="btn btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a>
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
	$('.date-picker').datepicker({
		autoclose : true,
		todayHighlight : true
	}).on("changeDate",function(){
		var a = this.value.split("-")[0];
		var d = this.value.split("-")[1];
		var b = d.split("-")[0];
		var c  = a+'-'+b 
		$.post("/workBench/getHkje",{
			orderId:yo.getQueryString("orderId"),
			planTime:c
		}).done(function(res){
			if(res.result!=null){
				$("#money").val(res.result)
			}else{
				$("#money").val("")
			}
			money.trigger("keyup")
		})
	});
	
	var money = $("#money")
	var rate = Number($("#commissionRate").val())/100
	var commi = $("#commissionaMount")
	
	money.on('keyup',function(){
		calc.call(this)
	})
	money.on('change',function(){
		calc.call(this)
	})
	function calc(){
		if(!isNaN(this.value)){
			commi.val((Number(this.value)*rate).toFixed(2))
			$("#commHIDDEN").val(this.value)
		}else{
			commi.val(0)
			layer.msg("回款金额请输入数字")
		}		
		
	}
	
	
	//保存
	function save() {
		if (yoValidate('#customerForm')) {
			$("#customerForm").submit();
		}
	}
</script>
</html>