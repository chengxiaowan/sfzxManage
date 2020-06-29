<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="${pageContext.request.contextPath}/">
<%@ include file="../../system/index/top.jsp"%>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInUp">
		<!-- /section:basics/sidebar -->
		<div class="ibox-content">
			<div class="row">
				<div class="col-xs-12">
					<!-- 检索  -->
					<form class="form-inline" role="form" <c:if test="${empty pd.flag }">action="lawsuitaudit/list.do"</c:if> <c:if test="${not empty pd.flag }">action="lawsuitaudit/selectList.do"</c:if>method="post" name="contractForm" id="contractForm">
						<div class="form-group">
							<input id="nav-search-input" style="min-width:260px" name="keywords" placeholder="申请人、债权人(公司)、债务人(公司)" value="${pd.keywords }" class="form-control" type="text">
						</div>
						<div class="form-group">
							<input class="span10 date-picker form-control" id="createTimeStart" name="createTimeStart" value="${fn:substring(pd.createTimeStart, 0, 10)}" type="text" data-date-format="yyyy-mm-dd"
								readonly="readonly" placeholder="开始日期" title="注册时间开始" />
						</div>
						<div class="form-group">
							<input class="span10 date-picker form-control" id="createTimeEnd" name="createTimeEnd" value="${fn:substring(pd.createTimeEnd, 0, 10)}" type="text" data-date-format="yyyy-mm-dd"
								readonly="readonly" placeholder="结束日期" title="注册时间结束" />
						</div>
						<div class="form-group">
							审核状态 <select id="auditStatus" name="auditStatus" class="form-control" style="width: 100px;" data-placeholder="审核状态">
								<option value="" <c:if test="${empty pd.auditStatus || pd.auditStatus == null || pd.auditStatus == ''}">selected</c:if>>全部</option>
								<option value="0" <c:if test="${pd.auditStatus == '0'}">selected</c:if>>待审核</option>
								<option value="1" <c:if test="${pd.auditStatus == '1'}">selected</c:if>>已审核</option>
								<option value="2" <c:if test="${pd.auditStatus == '2'}">selected</c:if>>审核未通过</option>
							</select>
						</div>
						<button type="submit" class="btn btn-primary"><i class="fa fa-search"></i> 搜索</button>
						<button type="reset" class="btn">
							<i id="nav-search-icon" class="fa fa-refresh nav-search-icon "></i> 重置
						</button>
						
					</form>
				</div>
			</div>
		</div>
		<div class="ibox-content">

			<table id="simple-table" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center" style="width: 50px;">序号</th>
						<th class="center">申请人</th>
						<th class="center">债权人(公司)</th>
						<th class="center">债务人(公司)</th>
						<th class="center">欠款总金额(元)</th>
						<th class="center">已还款金额(元)</th>
						<th class="center">剩余金额(元)</th>
						<th class="center">佣金比例(诉讼/非诉讼)</th>
						<th class="center">申请类型</th>
						<th class="center">申请状态</th>
						<th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>申请时间</th>
						<th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>审核时间</th>
						<c:if test="${not empty pd.flags }">
							<th class="center">操作</th>
						</c:if>
					</tr>
				</thead>

				<tbody>
					<!-- 开始循环 -->
					<c:choose>
						<c:when test="${not empty lawList}">
							<c:if test="${QX.cha == 1 }">
								<c:forEach items="${lawList}" var="list" varStatus="vs">
									<tr>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td class="center">${list.applyer }</td>
										<td class="center">${list.custoemrName }</td>
										<td class="center">${list.debtorName }</td>
										<td class="center">${list.debtAmount }</td>
										<td class="center">${list.totalMoney }</td>
										<td class="center">${list.syMoney }</td>
										<td class="center"><c:if test="${list.cType==0 }">${list.commissionRate}<c:if test="${not empty list.lawCommissionRate}">/${list.lawCommissionRate}</c:if></c:if><c:if test="${list.cType==1 }">-</c:if></td>
										<td class="center">${list.applyType==0?'诉讼':'仲裁' }</td>
										<td class="center">${list.auditStatus }</td>
										<td class="center">${fn:substring(list.createTime, 0, 10)}</td>
										<td class="center">${fn:substring(list.auditTime, 0, 10)}</td>
										<c:if test="${not empty pd.flags }">
							<td>
									<c:if test="${QX.edit == 1}">
										<button <c:if test="${list.auditStatus!='待审核' }">disabled ="disabled"</c:if> class="btn btn-success editflag" title="审核" onclick="edit('${list.id }');"> <i class="ace-icon fa fa-pencil-square-o" title="审核"></i> 审核
										</button>
									</c:if>
							</td>
						</c:if>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${QX.cha == 0 }">
								<tr>
									<td colspan="13" class="center">您无权查看</td>
								</tr>
							</c:if>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="13" class="center">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<div class="clearfix">
				<div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
			</div>
		</div>
		<!-- /.col -->
	</div>
</body>
<!-- /.main-container -->
<!-- 全局js -->
<%@ include file="../../system/index/js.jsp"%>

<script type="text/javascript">
	
	function viewCustomer(id) {
		var index = layer.open({
			type : 2,
			title : '查看详情',
			content : '${pageContext.request.contextPath}/customer/view.do?id='+id,
			area : [ '90%', '90%' ]
		});
	}
	
	function viewOrder(id) {
		var index = layer.open({
			type : 2,
			title : '查看详情',
			content : '${pageContext.request.contextPath}/order/viewOrder.do?id='+id,
			area : [ '90%', '90%' ]
		});
	}


	function edit(id) {
			var index = layer
			.open({
				type : 2,
				title : '修改',
				content : '${pageContext.request.contextPath}/lawsuitaudit/goEdit.do?id='
						+ id,
				area : [ '700px', '90%' ]
			});
		} 

	$(function() {
		//日期框
		$('.date-picker').datepicker({
			autoclose : true,
			todayHighlight : true
		});

		if ($("#createTimeStart").val()) {
			$("#createTimeEnd").datepicker('setStartDate',
					new Date($("#createTimeStart").val()))
		}

		if ($("#createTimeEnd").val()) {
			$("#createTimeStart").datepicker('setEndDate',
					new Date($("#createTimeEnd").val()))
		}

		//开始日期
		$("#createTimeStart").datepicker({}).on(
				'changeDate',
				function(ev) {
					if (ev.date) {
						$("#createTimeEnd").datepicker('setStartDate',
								new Date(ev.date.valueOf()))
					} else {
						$("#createTimeEnd").datepicker('setStartDate', null);
					}
				});
		//结束日期
		$("#createTimeEnd").datepicker({}).on(
				'changeDate',
				function(ev) {
					if (ev.date) {
						$("#createTimeStart").datepicker('setEndDate',
								new Date(ev.date.valueOf()))
					} else {
						$("#createTimeStart").datepicker('setEndDate',
								new Date());
					}
				});
	})
</script>
</body>
</html>
