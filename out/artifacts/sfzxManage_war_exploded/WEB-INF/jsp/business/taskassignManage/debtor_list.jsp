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
					<form class="form-inline" role="form" action="taskAssign/debtRoport/list.do" method="post" name="visitForm" id="visitForm">
						<div class="form-group">
							<input id="nav-search-input" name="keywords" placeholder="案件编号" value="${pd.keywords }" class="form-control" type="text">
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
							完成情况<select id="isCompleted" name="isCompleted" class="form-control" style="width: 100px;" data-placeholder="完成情况">
								<option value="" <c:if test="${empty pd.auditStatus || pd.auditStatus == null || pd.auditStatus == ''}">selected</c:if>>全部</option>
								<option value="0" <c:if test="${pd.isCompleted == '0'}">selected</c:if>>未完成</option>
								<option value="1" <c:if test="${pd.isCompleted == '1'}">selected</c:if>>已完成</option>
							</select>
						</div>
						<button type="submit" class="btn btn-primary"><i class="fa fa-search"></i> 搜索</button>
						<button type="button" onclick="emptyForm()" class="btn">
							<i id="nav-search-icon" class="fa fa-refresh nav-search-icon "></i> 重置
						</button>
						<div class="pull-right">
							<c:if test="${QX.add == 1 }">
								<a class="btn btn-success" onclick="add();"><i class="fa fa-plus"></i> 新增指派任务</a>
							</c:if>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="ibox-content">

			<table id="simple-table" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center" style="width: 35px;"><label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label></th>
						<th class="center" style="width: 50px;">序号</th>
						<th class="center">案件编号</th>
						<th class="center">执行人</th>
						<th class="center">指派人员</th>
						<th class="center">完成情况</th>
						<th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>指派时间</th>
					</tr>
				</thead>

				<tbody>
					<!-- 开始循环 -->
					<c:choose>
						<c:when test="${not empty debtList}">
							<c:if test="${QX.cha == 1 }">
								<c:forEach items="${debtList}" var="list" varStatus="vs">
									<tr>
										<td class='center' style="width: 30px;"><label><input type='checkbox' name='ids' value="${list.id }" id="${list.id }" alt="${list.mobilePhone }" title="${list.mobilePhone }" class="ace" /><span
												class="lbl"></span></label></td>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td class="center"><a title="点击查看详情" onclick="viewOrder('${list.orderId}')" style="cursor: pointer;">${list.orderNo }</a></td>
										<td class="center"><a title="点击查看详情" onclick="viewCustomer('${list.userId}')" style="cursor: pointer;">${list.userName }</a></td>
										<td class="center">${list.aduiterName }</td>
										<td class="center">${list.isCompleted==0?'未完成':'已完成'}</td>
										<td class="center">${fn:substring(list.createTime, 0, 19)}</td>
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
<!-- css¨ -->
<!-- 下拉框 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ui/css/plugins/chosen/chosen.css" />
<!-- 日期框 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/datepicker.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/colorbox.css" />
<!-- 全局js -->
<%@ include file="../../system/index/js.jsp"%>
<!-- 日期框 -->
<script src="${pageContext.request.contextPath}/static/ace/js/date-time/bootstrap-datepicker.js"></script>
<!-- 下拉框 -->
<script src="${pageContext.request.contextPath}/static/ui/js/plugins/chosen/chosen.jquery.js"></script>
<!--提示框-->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.tips.js"></script>
<script src="${pageContext.request.contextPath}/static/ace/js/jquery.colorbox.js"></script>

<script type="text/javascript">
	function emptyForm() {
		$("#nav-search-input").val('');
		$("#createTimeStart").val('');
		$("#createTimeEnd").val('');
	}

	//新增
	function add() {
		var index = layer.open({
			type : 2,
			title : '新增',
			content : '${pageContext.request.contextPath}/taskAssign/debtRoport/goAdd.do?marks=${pd.marks}',
			area : [ '90%', '90%' ]
		});
	}
	
	
	function viewCustomer(id) {
		var index = layer.open({
			type : 2,
			title : '点击查看详情',
			content : '${pageContext.request.contextPath}/linkman/view.do?id='+id,
			area : [ '90%', '90%' ]
		});
	}
	
	
	function viewOrder(id) {
		var index = layer.open({
			type : 2,
			title : '点击查看详情',
			content : '${pageContext.request.contextPath}/order/viewOrder.do?id='+id,
			area : [ '90%', '90%' ]
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
		//下拉框
		$('.chosen-select').chosen({
			allow_single_deselect : true
		});
		$(window).off('resize.chosen').on('resize.chosen', function() {
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({
					'width' : $this.parent().width()
				});
			});
		}).trigger('resize.chosen');
		$(document).on('settings.ace.chosen',
				function(e, event_name, event_val) {
					if (event_name != 'sidebar_collapsed')
						return;
					$('.chosen-select').each(function() {
						var $this = $(this);
						$this.next().css({
							'width' : $this.parent().width()
						});
					});
				});
		$('#chosen-multiple-style .btn').on('click', function(e) {
			var target = $(this).find('input[type=radio]');
			var which = parseInt(target.val());
			if (which == 2)
				$('#form-field-select-4').addClass('tag-input-style');
			else
				$('#form-field-select-4').removeClass('tag-input-style');
		});

		var colorbox_params = {
			rel : 'colorbox',
			reposition : true,
			scalePhotos : true,
			scrolling : false,
			previous : '<i class="ace-icon fa fa-arrow-left"></i>',
			next : '<i class="ace-icon fa fa-arrow-right"></i>',
			close : '&times;',
			current : '{current} of {total}',
			maxWidth : '100%',
			maxHeight : '100%',
			onOpen : function() {
				$overflow = document.body.style.overflow;
				document.body.style.overflow = 'hidden';
			},
			onClosed : function() {
				document.body.style.overflow = $overflow;
			},
			onComplete : function() {
				$.colorbox.resize();
			}
		};

		$('[data-rel="colorbox"]').colorbox(colorbox_params);

		//复选框全选控制
		var active_class = 'active';
		$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on(
				'click',
				function() {
					var th_checked = this.checked;//checkbox inside "TH" table header
					$(this).closest('table').find('tbody > tr').each(
							function() {
								var row = this;
								if (th_checked)
									$(row).addClass(active_class).find(
											'input[type=checkbox]').eq(0).prop(
											'checked', true);
								else
									$(row).removeClass(active_class).find(
											'input[type=checkbox]').eq(0).prop(
											'checked', false);
							});
				});
	});
</script>
</body>
</html>
