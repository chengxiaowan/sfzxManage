<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="${pageContext.request.contextPath}/">
<%@ include file="../../system/index/top.jsp"%>
<style>
	.form-control[disabled]{
		    background-color: white;
	}
</style>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInUp">
		<!-- /section:basics/sidebar -->
		<div class="ibox-content">
			<div class="row">
				<div class="col-xs-12">
					<!-- 检索  -->
					<form class="form-inline" role="form" action="linkman/list.do" method="post" name="customerForm" id="customerForm">
						<div class="form-group">
							<input id="nav-search-input" name="keywords" style="width: 398px" placeholder="客户名称、联系人名、电话号码、合同销售人、订单销售人" value="${pd.keywords }" class="form-control" type="text">
						</div>
						<button type="submit" class="btn btn-primary"><i class="fa fa-search"></i> 搜索</button>
						<button type="reset" class="btn">
							<i id="nav-search-icon" class="fa fa-refresh nav-search-icon "></i> 重置
						</button>
						
						<div class="pull-right">
							<c:if test="${QX.add == 1 }">
								<a class="btn btn-success" onclick="add();"><i class="fa fa-plus"></i> 新增</a>
							</c:if>
							<c:if test="${empty pd.saleId }">
							<c:if test="${QX.del == 1 }">
								<a title="批量删除" class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');"><i class='ace-icon fa fa-trash-o'></i>批量删除</a>
							</c:if>
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
						<th class="center">客户名称</th>
						<th class="center">联系人名称</th>
						<th class="center">手机号码</th>
						<th class="center">固定电话</th>
						<th class="center">职务</th>
						<th class="center">合同销售人</th>
						<th class="center">订单销售人</th>
						<th class="center">操作</th>
					</tr>
				</thead>

				<tbody>
					<!-- 开始循环 -->
					<c:choose>
						<c:when test="${not empty linkmanList}">
							<c:if test="${QX.cha == 1 }">
								<c:forEach items="${linkmanList}" var="list" varStatus="vs">
									<tr>
										<td class='center' style="width: 30px;"><label><input type='checkbox' name='ids' value="${list.id }" id="${list.id }" alt="${list.mobilePhone }" title="${list.mobilePhone }" class="ace" /><span
												class="lbl"></span></label></td>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td class="center"><a title="点击查看详情" onclick="viewCustomer('${list.relateId}')" style="cursor: pointer;">${list.companyName }</a></td>
										<td class="center"><a title="点击查看详情" onclick="viewLinkman('${list.id}')" style="cursor: pointer;">${list.name }</a></td>
										<td class="center">${list.mobilePhone }</td>
										<td class="center">${list.landline }</td>
										<td class="center">${list.postion }</td>
										<td class="center">${list.contractSaleName }</td>
										<td class="center">${list.orderSaleName }</td>
										
										<td class="center"><c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
											</c:if>
												<c:if test="${QX.edit == 1 }">
													<a class="btn btn-success" title="编辑" onclick="edit('${list.id}');"> <i class="ace-icon fa fa-pencil-square-o" title="编辑"></i> 编辑
													</a>
												</c:if>
												<c:if test="${empty pd.saleId }">
												<c:if test="${QX.del == 1 }">
													<a class="btn btn-danger" onclick="del('${list.id }','${list.name }');"> <i class="ace-icon fa fa-trash-o" title="删除"></i> 删除
													</a>
												</c:if>
												</c:if>
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
<%@ include file="../../system/index/js.jsp"%>

<script type="text/javascript">
	//删除
	function del(id, msg) {
		layer
				.confirm(
						"确定要删除此列数据吗?",
						function(result) {
							if (result) {
								var url = "${pageContext.request.contextPath}/linkman/delete.do?ids="
										+ id + "&tm=" + new Date().getTime();
								$.get(url, function(data) {
									nextPage('${page.currentPage}');
								});
							}
							;
						});
	}

	//新增
	function add() {
		var index = layer.open({
			type : 2,
			title : '新增',
			content : '${pageContext.request.contextPath}/linkman/goAdd.do',
			area : [ '1100px', '530px' ]
		});
	}
	
	
	function viewLinkman(id) {
		var index = layer.open({
			type : 2,
			title : '联系人详情',
			content : '${pageContext.request.contextPath}/linkman/view.do?id='+id,
			area : [ '1100px', '530px' ]
		});
	}
	
	
	function viewCustomer(id) {
		var index = layer.open({
			type : 2,
			title : '客户详情',
			content : '/static/page/customerN_detail.html?id='+id,
			area : [ '100%', '100%' ]
		});
	}

	//修改
	function edit(id) {
		var index = layer
				.open({
					type : 2,
					title : '修改',
					content : '${pageContext.request.contextPath}/linkman/goEdit.do?id='
							+ id,
					area : [ '1100px', '580px' ]
				});
	}

	//批量操作
	function makeAll(msg) {
		layer
				.confirm(
						msg,
						function(result) {
							if (result) {
								var str = '';
								var emstr = '';
								var phones = '';
								var username = '';
								for (var i = 0; i < document
										.getElementsByName('ids').length; i++) {
									if (document.getElementsByName('ids')[i].checked) {
										if (str == '')
											str += document
													.getElementsByName('ids')[i].value;
										else
											str += ','
													+ document
															.getElementsByName('ids')[i].value;

										if (emstr == '')
											emstr += document
													.getElementsByName('ids')[i].id;
										else
											emstr += ';'
													+ document
															.getElementsByName('ids')[i].id;

										if (phones == '')
											phones += document
													.getElementsByName('ids')[i].alt;
										else
											phones += ';'
													+ document
															.getElementsByName('ids')[i].alt;

										if (username == '')
											username += document
													.getElementsByName('ids')[i].title;
										else
											username += ';'
													+ document
															.getElementsByName('ids')[i].title;
									}
								}
								if (str == '') {
									layer.msg('您没有选择任何内容!')
									$("#zcheckbox").tips({
										side : 3,
										msg : '点这里全选',
										bg : '#AE81FF',
										time : 8
									});

									return;
								} else {
									if (msg == '确定要删除选中的数据吗?') {
										$
												.ajax({
													type : "POST",
													url : '${pageContext.request.contextPath}/linkman/delete.do?tm='
															+ new Date()
																	.getTime(),
													data : {
														ids : str
													},
													dataType : 'json',
													//beforeSend: validateData,
													cache : false,
													success : function(data) {
														 nextPage('${page.currentPage}');
													}
												});
									}
								}
							}
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
