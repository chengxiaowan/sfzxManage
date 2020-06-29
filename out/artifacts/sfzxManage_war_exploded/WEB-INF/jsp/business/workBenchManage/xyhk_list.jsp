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
							<form class="form-inline" role="form" action="workBench/xyHktj.do" method="post" name="customerForm" id="customerForm">
								<div class="form-group">
									<input id="nav-search-input" name="keywords" style="width: 398px" placeholder="请输入客户名称，债务人(公司)" value="${pd.keywords }" class="form-control" type="text">
								</div>
								<c:if test="${empty pd.userId }">
									<select name="runnerId" id="" style="width:200px;">
										<option value="${pd.runnerId}">${pd.runnerName }
											<option>
									</select>

									<input type="hidden" name="runnerName" value="${pd.runnerName }" />
								</c:if>
								<button type="submit" class="btn btn-primary"><i class="fa fa-search"></i> 搜索</button>
								<button type="reset" class="btn cc">
									<i id="nav-search-icon" class="fa fa-refresh nav-search-icon "></i> 重置
							</button>
							</form>

						</div>

					</div>
				</div>
				<div class="ibox-content">
					<div class="htz-box-no col-sm-2" style="cursor: pointer;">
						<span class="pull-left"><img src="/static/ui/img/10icon/h.png"></span>
						<div class="pull-left">
							<div>预计回款总金额</div>
							<div class="blue">¥ ${pd.hkzje }</div>
						</div>
					</div>
					<div class="htz-box-no col-sm-2" style="cursor: pointer;">
						<span class="pull-left"><img src="/static/ui/img/10icon/t.png"></span>
						<div class="pull-left">
							<div>预计佣金总金额</div>
							<div class="blue">¥ ${pd.yjzje  }</div>
						</div>
					</div>
					
					
					
					<table id="simple-table" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center" style="width: 50px;">序号</th>
								<th class="center">客户名称</th>
								<th class="center">债务人(公司)</th>
								<th class="center">欠款总金额(元)</th>
								<th class="center">预计回款金额(元)</th>
								<th class="center">佣金比例</th>
								<th class="center">预计佣金金额(元)</th>
								<th class="center">运作人</th>
								<th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>回款月份</th>
								<th class="center">操作</th>
							</tr>
						</thead>

						<tbody>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty cusList}">
									<c:if test="${QX.cha == 1 }">
										<c:forEach items="${cusList}" var="list" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td class="center">
													<a title="点击查看详情" onclick="view('${list.customerId}')" style="cursor: pointer;">${list.custoemrName}</a>
												</td>
												<td class="center">${list.debtorName}</td>
												<td class="center">${list.debtAmount}</td>
												<td class="center">${list.money}</td>
												<td class="center">${list.commissionRate}</td>
												<%-- 												<td class="center"><c:if test="${list.cType==1}">-</c:if><c:if test="${list.cType==0}">${list.commissionRate}<c:if test="${not empty list.lawCommissionRate}">/${list.lawCommissionRate}</c:if></c:if></td>
 --%>
												<td class="center">${list.commissionaMount}</td>
												<td class="center">${list.runnerName}</td>
												<td class="center">${fn:substring(list.planTime, 0,7)}</td>
												<td class="center">
													<a class="btn btn-success" title="查看" onclick="edit('${list.id}');"> <i class="ace-icon fa fa-pencil-square-o" title="查看"></i> 查看</a>
												</td>
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
			$('[name=runnerId]').select2({
				placeholder: "请选择运作人",
				language: 'zh-CN',
				ajax: {
					url: "/user/getUserInfo",
					dataType: 'json',
					type: "post",
					delay: 250,
					data: function(params) {
						return {
							page: params.page || 1,
							ROLE_ID: "02178e62f17b4926bb7014f3ad5a1ebe",
							data: {
								q: params.term || "", // search term
							}
						};
					},
					processResults: function(data, params) {
						params.page = params.page || 1;
						$.each(data, function() {
							this.id = this.USER_ID;
							this.text = this.NAME;
						})
						return {
							results: data,
							pagination: {
								more: data.length == 10
							}
						};
					},
					cache: true
				},
				minimumInputLength: 0
			}).on("change", function() {
				$("[name=runnerName]").val($(this).find("option:selected").text())

			})

			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});

			if($("#createTimeStart").val()) {
				$("#createTimeEnd").datepicker('setStartDate',
					new Date($("#createTimeStart").val()))
			}

			if($("#createTimeEnd").val()) {
				$("#createTimeStart").datepicker('setEndDate',
					new Date($("#createTimeEnd").val()))
			}

			//开始日期
			$("#createTimeStart").datepicker({}).on(
				'changeDate',
				function(ev) {
					if(ev.date) {
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
					if(ev.date) {
						$("#createTimeStart").datepicker('setEndDate',
							new Date(ev.date.valueOf()))
					} else {
						$("#createTimeStart").datepicker('setEndDate',
							new Date());
					}
				});
			//下拉框
			$('.chosen-select').chosen({
				allow_single_deselect: true
			});
			$(window).off('resize.chosen').on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					var $this = $(this);
					$this.next().css({
						'width': $this.parent().width()
					});
				});
			}).trigger('resize.chosen');
			$(document).on('settings.ace.chosen',
				function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed')
						return;
					$('.chosen-select').each(function() {
						var $this = $(this);
						$this.next().css({
							'width': $this.parent().width()
						});
					});
				});
			$('#chosen-multiple-style .btn').on('click', function(e) {
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if(which == 2)
					$('#form-field-select-4').addClass('tag-input-style');
				else
					$('#form-field-select-4').removeClass('tag-input-style');
			});

			function edit(id) {
				var index = layer.open({
					type: 2,
					title: '查看详情',
					content: '/static/page/case_detail.html?id=' + id,
					area: ['100%', '100%']
				});
			}

			function view(id) {
				var index = layer
					.open({
						type: 2,
						title: '查看详情',
						content: '/static/page/customerN_detail.html?id=' + id,
						area: ['100%', '100%']
					});
			}
			
			$(".cc").click(function(){
				$("#select2--container").text('请选择运作人');
			})
		</script>
		</body>

</html>