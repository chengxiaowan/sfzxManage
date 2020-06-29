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
							<c:if test="${!pd.hasDetail }">
								<li class="active"><a data-toggle="tab" href="#home">填写回款明细</a></li>
							</c:if>
							<c:if test="${not empty dataList }">
								<li><a data-toggle="tab" href="#dataList">回款明细列表</a></li>
							</c:if>
						</ul>
						<div class="tab-content">
							<c:if test="${!pd.hasDetail }">
								<div id="home" class="tab-pane in active">
									<form action="workBench/${msg }.do" name="customerForm" id="customerForm" method="post" class="form form-horizontal">
										<input type="hidden" name="orderId" id="orderId" value="<c:out value="${pd.orderId}" default="0"></c:out>" /><input type="hidden" name="taskId" id="taskId"
											value="<c:out value="${pd.taskId}" default="0"></c:out>" /> <input type="hidden" name="type" id="type" value="<c:out value="${pd.type}" default="0"></c:out>" />
											
										<div class="row">
											<input type="hidden" name="planId" id="planId" value="<c:out value="${pd.planList[0].planId}" default="0"></c:out>" />
											<div id="zhongxin" style="padding-top: 13px;">
												<div class="col-md-6">
													<div class="form-group">
														<label class="col-sm-4 control-label">本次回款金额(元)&nbsp;<font color="red">*</font>:
														</label>
														<div class="col-sm-8">
															<input class="form-control" data-validate="require|price|maxLength=50" type="text" id="currentMoney" name="currentMoney" placeholder="这里输入本次回款金额" <c:if test="${not empty pd.planList[0].planId}">value="${pd.planList[0].currentMoney }"</c:if>>
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="col-sm-4 control-label">本次回款时间&nbsp;<font color="red">*</font>:
														</label>
														<div class="col-sm-8">
															<input class="span10 date-picker form-control" type="text" id=currentTime name="currentTime" data-validate="require" readonly="readonly" data-date-format="yyyy-mm-dd"
																placeholder="这里输入本次回款时间" <c:if test="${not empty pd.planList[0].planId}">value="${fn:substring(pd.planList[0].currentTime,0,10) }"</c:if>>
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="col-sm-4 control-label">当前余额(元): </label>
														<div class="col-sm-8">
															<input class="form-control" readonly="readonly" data-validate="price|maxLength=50" type="text" id="currentBalance" name="currentBalance" placeholder="这里输入当前余额" <c:if test="${not empty pd.planList[0].planId}">value="${pd.planList[0].currentBalance }"</c:if>>
														</div>
													</div>
												</div>
												<div id="hidden" style="display: none">
												<div class="col-md-6">
													<div class="form-group">
														<label class="col-sm-4 control-label">计划金额(元): </label>
														<div class="col-sm-8">
															<input class="form-control" readonly type="text" id="onceMoney" name="onceMoney" <c:if test="${not empty pd.planList[0].planId}">value="${pd.planList[0].onceMoney }"</c:if>>
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="col-sm-4 control-label">计划时间: </label>
														<div class="col-sm-8">
															<input class="form-control" readonly type="text" id="planTime" name="planTime"   <c:if test="${not empty pd.planList[0].planId}">value="${pd.planList[0].planTime }"</c:if>>
														</div>
													</div>
												</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="control-label col-sm-2 no-padding-right">备注:</label>
														<div class="col-sm-10">
															<textarea class="form-control" id="remark" name="remark" style="width: 95%; height: 120px;" rows="15" cols="10" placeholder="这里输入备注"><c:if test="${not empty pd.planList[0].planId}">${pd.planList[0].remark}</c:if></textarea>
														</div>
													</div>
												</div>
											</div>
										</div>
									</form>
								</div>
							</c:if>
							<c:if test="${not empty dataList }">
								<div id="dataList" class="tab-pane">
									<div class="ibox">
										<div class="ibox-content">
											<table id="simple-table" class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th class="center" style="width: 50px;">序号</th>
														<th class="center">回款金额</th>
														<th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>回款时间</th>
														<th class="center">余额</th>
														<th class="center">期数</th>
														<th class="center">备注</th>
													</tr>
												</thead>

												<tbody>
													<!-- 开始循环 -->
													<c:choose>
														<c:when test="${not empty dataList}">
															<c:forEach items="${dataList}" var="list" varStatus="vs">
																<tr>
																	<td class='center' style="width: 30px;">${vs.index+1}</td>
																	<td class="center">${list.currentMoney}</td>
																	<td class="center">${list.currentTime }</td>
																	<td class="center">${list.currentBalance }</td>
																	<td class="center"><c:choose><c:when test="${list.currentPeriods == 0}">-</c:when><c:otherwise>${list.currentPeriods}</c:otherwise></c:choose></td>
																	<td class="center">${list.remark }</td>
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr class="main_info">
																<td colspan="13" class="center">没有相关数据</td>
															</tr>
														</c:otherwise>
													</c:choose>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</c:if>
						</div>
						<!--/span-->
					</div>
					<div class="text-center">
						<c:if test="${!pd.hasDetail }">
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
	<!-- basic scripts -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/colorbox.css" />
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
</body>
<script type="text/javascript">
	$('.date-picker').datepicker({
		autoclose : true,
		todayHighlight : true
	}).on("changeDate",function(){
		if(this.value){
		$.post("/workBench/findPayDetail",
				{
				orderId:'${pd.orderId}',
				time:this.value
				}
				).done(function(res){
					if(res.result!=null){
						$("#hidden").show()
						$("#planTime").val(res.result.planTime);
						$("#onceMoney").val(res.result.onceMoney);
					}else{
						$("#hidden").hide()
					}
				})
		}
	});
	
	var currentMoney = $("#currentMoney");
	var syMoney = yo.getQueryString("syMoney");
	var banlance = $("#currentBalance");
	
	currentMoney.on('keyup',function(){
		calc.call(this)
	})
	currentMoney.on('change',function(){
		calc.call(this)
	})
	
	var TEMP_crMoney = (Number($("#currentMoney").val()) || 0)
	
	
	function calc(){
		if(!isNaN(this.value)){
			var val = (Number(syMoney) +TEMP_crMoney -Number(this.value)).toFixed(2)
			if(val<=0)val=0
			banlance.val(val)
		}else{
			banlance.val(0)
			layer.msg("回款金额请输入数字")
		}
	}
	
	
	//保存
	function save() {
		if (yoValidate('#customerForm')) {
			$("#customerForm").submit();
		}
	}

	//查找回款计划
	function choosePaymentplan(orderId) {
		sessionStorage.ListTagsCache = '0' //关闭缓存
		var index = layer
				.open({
					type : 2,
					title : '选择回款计划',
					content : '${pageContext.request.contextPath}/workBench/choosePaymentplan.do?flags=0&orderId='
							+ orderId,
					area : [ '90%', '90%' ],
					btn : [ '确定', '取消' ],
					yes : function(index, layero) {
						var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
						var data = iframeWin.pushToParent()
						layer.close(index)
						$("#currentPeriods").val(data.ids); //赋值子页面传过来的IDS
						sessionStorage.ListTagsCache = '0' //关闭缓存
					}
				});
	}
</script>
</html>