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
					<form class="form-inline" role="form" action="workBench/my/myTaskList.do" method="post" name="customerForm" id="customerForm">
						<c:if test="${pd.flag!=0 && pd.flag!=1 &&pd.flag!=4 }">
						<div class="form-group">
							<input id="nav-search-input" name="keywords" placeholder="发起人、执行人" value="${pd.keywords }" class="form-control" type="text">
						</div>
						</c:if>
						<div class="form-group">
							<input class="span10 date-picker form-control" id="createTimeStart" name="createTimeStart" value="${fn:substring(pd.createTimeStart, 0, 10)}" type="text" data-date-format="yyyy-mm-dd"
								readonly="readonly" placeholder="开始日期" title="日期开始" style="width: 100px;" />
						</div>
						<div class="form-group">
							<input class="span10 date-picker form-control" id="createTimeEnd" name="createTimeEnd" value="${fn:substring(pd.createTimeEnd, 0, 10)}" type="text" data-date-format="yyyy-mm-dd"
								readonly="readonly" placeholder="结束日期" title="日期结束" style="width: 100px;" />
						</div>
						<div class="form-group">
							完成状态 <select id="isCompleted" name="isCompleted" class="form-control" style="width: 100px;" data-placeholder="完成状态">
								<option value="" <c:if test="${empty pd.isCompleted || pd.isCompleted == null || pd.isCompleted == ''}">selected</c:if>>全部</option>
								<option value="0" <c:if test="${pd.isCompleted == '0'}">selected</c:if>>未完成</option>
								<option value="1" <c:if test="${pd.isCompleted == '1'}">selected</c:if>>已完成</option>
							</select>
						</div>
						<div class="form-group">
							任务类型 <select id="type" name="type" class="form-control" style="width: 100px;" data-placeholder="任务类型">
								<option value="" <c:if test="${empty pd.type || pd.type == null || pd.type == ''}">selected</c:if>>全部</option>
								<option value="0" <c:if test="${pd.type == '0'}">selected</c:if>>申请外访</option>
								<option value="1" <c:if test="${pd.type == '1'}">selected</c:if>>指派任务</option>
								<option value="2" <c:if test="${pd.type == '2'}">selected</c:if>>待开发票</option>
								<option value="3" <c:if test="${pd.type == '3'}">selected</c:if>>待发快递</option>
								<option value="4" <c:if test="${pd.type == '4'}">selected</c:if>>待发报告</option>
							</select>
						</div>
						<!-- 						<div class="form-group"> -->
						<!-- 							完成状态 <select id="isCompleted" name="isCompleted" class="form-control" style="width: 100px;" data-placeholder="完成状态"> -->
						<%-- 								<option value="" <c:if test="${empty pd.isCompleted || pd.isCompleted == null || pd.isCompleted == ''}">selected</c:if>>全部</option> --%>
						<%-- 								<option value="0" <c:if test="${pd.isCompleted == '0'}">selected</c:if>>未完成</option> --%>
						<%-- 								<option value="1" <c:if test="${pd.isCompleted == '1'}">selected</c:if>>已完成</option> --%>
						<!-- 							</select> -->
						<!-- 						</div> -->
						<button type="submit" class="btn btn-primary">
							<i class="fa fa-search"></i> 搜索
						</button>
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
						<th class="center" style="width: 35px;"><label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label></th>
						<th class="center" style="width: 50px;">序号</th>
						<th class="center">任务类型</th>
						<th class="center">完成状态</th>
						<th class="center">发起人</th>
						<c:if test="${pd.flag!=0 && pd.flag!=1 &&pd.flag!=4 }"><th class="center">执行人</th></c:if>
						<th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>生成时间</th>
						<th class="center">操作</th>
					</tr>
				</thead>

				<tbody>
					<!-- 开始循环 -->
					<c:choose>
						<c:when test="${not empty dataList}">
							<c:if test="${QX.cha == 1 }">
								<c:forEach items="${dataList}" var="list" varStatus="vs">
									<tr>
										<td class='center' style="width: 30px;"><label><input type='checkbox' name='ids' value="${list.id }" id="${list.id }" alt="${list.mobilePhone }" title="${list.mobilePhone }"
												class="ace" /><span class="lbl"></span></label></td>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td class="center">${list.type }</td>
										<td>${list.isCompleted }</td>
										<td class="center">${list.fqrName }</td>
										<c:if test="${pd.flag!=0 && pd.flag!=1 &&pd.flag!=4 }"><td class="center">${list.zxrName }</td></c:if>								
										<td class="center">${fn:substring(list.createTime, 0, 19)}</td>
										<td>
										<button title="点击查看详情" class="btn btn-primary" data-type="${list.type }" onclick="viewTask(this,'${list.id}','${list.relateId}')"><i class="fa fa-search"></i> 查看详情</button>
										<c:if test="${list.type=='指派外访' }"><button title="点击查看详情" class="btn btn-success"  <c:if test="${list.isCompleted=='已完成' }">disabled</c:if>onclick="doUpdate('${list.id}')"><i class="fa fa-check"></i> 完成</button></c:if>
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
	//删除
	function doUpdate(id) {
		layer
				.confirm(
						"确定要完成任务吗?",
						function(result) {
							if (result) {
								var url = "${pageContext.request.contextPath}/workBench/doUpdate.do?id="
										+ id + "&tm=" + new Date().getTime();
								$.get(url, function(data) {
									nextPage('${page.currentPage}');
								});
							}
							;
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
	
	 function viewTask(dom,id,relateId) {
		 var type = $.trim($(dom).data('type'));
		 var url =  ''
 		switch (type) {
 		case "指派任务":
			url = '/workBench/views.do?id='+ id
			break;
		case "指派外访":
			url = '/workBench/views.do?id='+ id
			break;
		case "申请外访":
			url = '/workBench/views.do?id='+ id
			break;
		case "待开发票":
			url='/workBench/views.do?id='+ id
			break;
		case "待发快递":
			url='/express/view.do?id='+relateId+'#taskViewer'
			break;
		case "待发报告":
			url="/workBench/views.do?id=" +id
			break;
		default:
			break;
		}
		 
			 var index = layer.open({
				type : 2,
				title : '查看详情',
				content : url,
				area : [ '90%', '90%' ]
			}); 
		}
	
</script>
</body>
</html>
