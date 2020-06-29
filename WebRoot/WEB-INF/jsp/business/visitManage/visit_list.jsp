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
					<form class="form-inline" role="form" <c:if test="${pd.flag==3||pd.flag==4||pd.flag==5 }">action="vistsh/list.do"</c:if><c:if test="${pd.flag==1||pd.flag==2 }">action="vist/list.do"</c:if> method="post" name="visitForm" id="visitForm">
						<div class="form-group">
							<input id="nav-search-input" name="keywords" placeholder="申请人" value="${pd.keywords }" class="form-control" type="text">
						</div>
						<div class="form-group">
							类别 <select name="type" class="form-control" >
							<option value="">全部</option>
								<option value="0" <c:if test="${pd.type == '0'}">selected</c:if>>外出</option>
								<option value="1" <c:if test="${pd.type == '1'}">selected</c:if>>出差</option>
							</select>
						</div>
						<div class="form-group">
							审核状态 <select id="auditStatus" name="auditStatus" class="form-control" style="width: 100px;" data-placeholder="审核状态">
								<option value="" <c:if test="${empty pd.auditStatus || pd.auditStatus == null || pd.auditStatus == ''}">selected</c:if>>全部</option>
								<option value="0" <c:if test="${pd.auditStatus == '0'}">selected</c:if>>待审核</option>
								<option value="1" <c:if test="${pd.auditStatus == '1'}">selected</c:if>>审核未通过</option>
								<option value="2" <c:if test="${pd.auditStatus == '2'}">selected</c:if>>已审核</option>
							</select>
						</div>
						<button type="submit" class="btn btn-primary"><i class="fa fa-search"></i> 搜索</button>
						<button type="reset"  class="btn">
							<i id="nav-search-icon" class="fa fa-refresh nav-search-icon "></i> 重置
						</button>
						<div class="pull-right">
						<c:if test="${pd.flag==1||pd.flag==2 }">
							<c:if test="${QX.add == 1 }">
								<a class="btn btn-success" onclick="add();"><i class="fa fa-plus"></i> 申请外访</a>
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
						<th class="center" style="width: 50px;">序号</th>
						<th class="center">申请人(协作人)</th>
						<th class="center">外出目标</th>
						<th class="center">拜访人</th>
						<th class="center">联系方式</th>
						<th class="center">类别</th>
						<th class="center">预计时长/天数</th>
						<th class="center">审核状态</th>
							<th class="center">操作</th>
					</tr>
				</thead>

				<tbody>
					<!-- 开始循环 -->
					<c:choose>
						<c:when test="${not empty orderList}">
							<c:if test="${QX.cha == 1 }">
								<c:forEach items="${orderList}" var="list" varStatus="vs">
									<tr>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td class="center">${list.sqName}<c:if test="${not empty list.collaboratorName}">(${list.collaboratorName})</c:if></td>
										<td class="center">${list.target}</td>
										<td class="center">${list.visitman }</td>
										<td class="center">${list.mobilPhone }</td>
										<td class="center">${list.type==0 ?'外出':'出差' }</td>
										<td class="center">${empty list.yjsc? list.yjts:list.yjsc }${list.type==0 ?'小时':'天' }</td>
										<td class="center"><c:if test="${list.auditStatus==0 }">待审核</c:if><c:if test="${list.auditStatus==2 }">已审核</c:if><c:if test="${list.auditStatus==1 }">审核未通过</c:if></td>
									
										<td class="center">
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
											</c:if>
												<c:if test="${QX.edit == 1 }">
												<a class="btn btn-primary" title="查看" onclick="taskViewer('${list.id}');"> <i class="fa fa-search" ></i> 查看</a>
													<c:if test="${pd.flag==1||pd.flag==2}">
													<c:if test="${(list.auditStatus!=2&& not empty list.wcqk)||(list.auditStatus==2&&  empty list.wcqk) }">
													<a class="btn btn-success" onclick="edit('${list.id}');"> <i class="ace-icon fa fa-pencil-square-o" title="编辑"></i> 编辑</a>
													</c:if>
												<c:if test="${QX.del == 1 }">
													<c:if test="${list.auditStatus==0 }">
														<a class="btn btn-danger" onclick="del('${list.id }');"> <i class="ace-icon fa fa-trash-o" title="删除"></i> 删除
													</a>
													</c:if>
													</c:if>
												</c:if>
												</c:if>
												<c:if test="${(pd.flag==3||pd.flag==4||pd.flag==5)}">
														<button class="btn btn-success editflag" <c:if test="${list.auditStatus!=0}">disabled="disabled"</c:if> title="审核" onclick="exam('${list.id }');"> <i class="ace-icon fa fa-pencil-square-o" title="审核"></i> 审核
														</button>
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
						"确定要删除该条记录吗?",
						function(result) {
							if (result) {
								var url = "${pageContext.request.contextPath}/vist/delete.do?ids="
										+ id + "&tm=" + new Date().getTime();
								$.get(url, function(data) {
									nextPage('${page.currentPage}');
								});
							}
							;
						});
	}
	
	function chufa(){
		var checks=[];
		$.each($("input[name=ids]:checked"),function(){
			checks.push(this.alt)
		});
		if(checks.length == 1){
			if(checks[0]=='0'){
				$(".editflag").attr("onclick","edit()");
				$(".editflag").attr("disabled",false);
			}else{
				$(".editflag").removeAttr("onclick");
				$(".editflag").attr("disabled",true);
			}
		}else if(checks.length > 1){
			$(".editflag").removeAttr("onclick");
			$(".editflag").attr("disabled",true);
		}else{
			$(".editflag").attr("onclick","edit()");
			$(".editflag").attr("disabled",false);
		}
	}
	
/* 	//新增
	function add1() {
		var marks=''
		var flags='${pd.flags}'
		if(flags=='0'){
			marks=2
		}
		if(flags=='1'){
			marks=3
		}
		var index = layer.open({
			type : 2,
			title : '新增',
			content : '${pageContext.request.contextPath}/vistsh/visit/goAdd.do?marks='+marks,
			area : [ '90%', '90%' ]
		});
	} */

	//新增
	function add() {
		var index = layer.open({
			type : 2,
			title : '申请外访',
			content : '${pageContext.request.contextPath}/vist/goAdd.do',
			area : [ '90%', '90%' ]
		});
	}
	
	
	function viewLinkMan(id) {
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

	//修改
	function edit(id) {
		if(typeof(id)=="string"){
			window.index = layer
			.open({
				type : 2,
				title : '修改',
				content : '${pageContext.request.contextPath}/vist/goEdit.do?id=' + id,
				area : [ '90%', '90%' ]
			});
		}
	}
	
	//审核
	function exam(id){
		var url='${pageContext.request.contextPath}/vistsh/goEdit.do?id='
			var index = layer
					.open({
						type : 2,
						title : '修改',
						content : url + id,
						area : [ '90%', '90%' ]
					});
		
	}
	

	function taskViewer (id){
		var url='${pageContext.request.contextPath}/vist/view.do?id='
			var index = layer
					.open({
						type : 2,
						title : '查看',
						content : url + id,
						area : [ '90%', '90%' ]
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
								for (var i = 0; i < document.getElementsByName('ids').length; i++) {
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
													.getElementsByName('ids')[i].title;
										else
											emstr += ';'
													+ document
															.getElementsByName('ids')[i].title;
								}
								}
								console.log(emstr);
								if (str == '') {
									layer.msg('您没有选择任何内容!')
									$("#zcheckbox").tips({
										side : 3,
										msg : '点这里全选',
										bg : '#AE81FF',
										time : 8
									});
									return;
								} else if(emstr.indexOf('1')>=0||emstr.indexOf('2')>=0){
									layer.msg('您不能删除已审核或者审核通过的外访列表!');
									return;
								}else if (msg == '确定要删除选中的数据吗?') {
										$
												.ajax({
													type : "POST",
													url : '${pageContext.request.contextPath}/vist/delete.do?tm='
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
