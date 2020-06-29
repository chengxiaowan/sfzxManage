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
					<form class="form-inline" role="form" action="workBench/orderReportList.do" method="post" name="visitForm" id="visitForm">
						<div class="form-group">
							<input id="nav-search-input" style="width:250px" name="keywords" placeholder="债权人(公司)、债务人(公司)、运作人" value="${pd.keywords }" class="form-control" type="text">
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
							发送状态<select id="isSend" name="isSend" class="form-control" style="width: 100px;" data-placeholder="邮件是否发送">
								<option value="" <c:if test="${empty pd.isSend || pd.isSend == null || pd.isSend == ''}">selected</c:if>>全部</option>
								<option value="0" <c:if test="${pd.isSend == '0'}">selected</c:if>>待发送</option>
								<option value="1" <c:if test="${pd.isSend == '1'}">selected</c:if>>已发送</option>
								<option value="2" <c:if test="${pd.isSend == '2'}">selected</c:if>>已收到</option>
							</select>
						</div>
						<%-- <div class="form-group">
							是否已收<select id="isRecieve" name="isRecieve" class="form-control" style="width: 100px;" data-placeholder="邮件是否已收">
								<option value="" <c:if test="${empty pd.isRecieve || pd.isRecieve == null || pd.isRecieve == ''}">selected</c:if>>全部</option>
								<option value="0" <c:if test="${pd.isRecieve == '0'}">selected</c:if>>未收</option>
								<option value="1" <c:if test="${pd.isRecieve == '1'}">selected</c:if>>已收</option>
							</select>
						</div> --%>
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
						<th class="center">债权人(公司)</th>
						<th class="center">债务人(公司)</th>
						<th class="center">欠款总金额(元)</th>
						<th class="center">已回款金额(元)</th>
						<th class="center">剩余金额(元)</th>
						<th class="center">佣金比例<br>(诉讼/非诉讼)</th>
						<th class="center">邮箱</th>
						<th class="center">联系人</th>
						<th class="center">发送状态</th>
						<th class="center">运作人</th>
						<th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>生成时间</th>
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
										<td class="center">${list.customerName }</td>
										<td class="center">${list.debtorName }</td>
										<td class="center">${list.debtAmount }</td>
										<td class="center">${list.totalMoney }</td>
										<td class="center">${list.syMoney }</td>
										<td class="center"><c:if test="${list.cType==0 }">${list.commissionRate}<c:if test="${not empty list.lawCommissionRate}">/${list.lawCommissionRate}</c:if></c:if><c:if test="${list.cType==1 }">-</c:if></td>
										<td class="center">${list.orderEmail }</td>
										<td class="center">${list.linkmanName }</td>
										<td class="center"><c:if test="${list.isSend==0}">待发送</c:if><c:if test="${list.isSend==1}">已发送</c:if><c:if test="${list.isSend==2}"><a title="点击查看详情" onclick="view('${list.id}')" style="cursor: pointer;">已收到</a></c:if></td>
										<td class="center">${list.runnerName }</td>
										<td class="center">${fn:substring(list.createTime, 0, 19)}</td>
										<td class="center">
							<button title="发送邮件"  class="btn <c:if test="${list.isSend==0}">btn-info</c:if>" <c:if test="${list.isSend==1||list.isSend==2}">disabled="disabled"</c:if> onclick="sendEmail('${list.customerName }','${list.id}');"> <i class="fa fa-send" title="编辑"></i> 发邮件
		 											</button>
							<button title="是否已收" class="btn <c:if test="${list.isSend==1}">btn-info</c:if>" <c:if test="${list.isSend==0||list.isSend==2}">disabled="disabled"</c:if> onclick="editIsRecive('${list.id}');"> <i class="fa fa-pencil-square-o" title="编辑"></i> 是否已收
													</button>
													</td>
									</tr>
								</c:forEach>
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
	<div id="floatBox" style="display:none">
	<form class="form form-horizontal" id="sendMail_box">
    <div class="m-t-md">
        <div class="col-md-10 col-md-offset-1">
				<div class="form-group">
					<label class="control-label">客户名称：</label> 
                    <input class="form-control" readonly="readonly" id="floatName" type="text"  value="">
				</div>
				<div class="form-group">
					<label class="control-label">联系人：</label> 
                    <input class="form-control" type="text" id="linkmanName" value="">
				</div>
				<div class="form-group">
					<label class="control-label">邮箱 <span class="text-danger">*</span>：</label> 
                    <input class="form-control" type="text" data-validate="require|email" id="email" value="">
				</div>
        </div>
    </div>
</form>
</div>
	
</body>
<%@ include file="../../system/index/js.jsp"%>

<script type="text/javascript">
	//发送邮件
	function sendEmail(name,id) {
		$("#floatName").val(name)
		var dialog = layer.open({
            type: 1,
            title: '发送邮件',
            closeBtn: 1,
            content: $("#floatBox"),
            area: ['600px', '400px'],
            btn: "发送",
            btnAlign: 'c',
            yes:function() {
            	if(yoValidate("#sendMail_box",1)){
            	$.post("${pageContext.request.contextPath}/workBench/sendEmail",{
            		"id":id,
            		linkmanName:$("#linkmanName").val(),
            		email:$("#email").val()
            		} ,function(data){
    				if("00"==data.error){
    					layer.msg("邮件发送成功");
    					setTimeout(function(){
    						location.reload()
    					}, 2000)
    				}else{
    					layer.msg(data.msg);
    				} 
    			});
            	
            	}
            }
        });

	}
	
	
	function editIsRecive(id) {
		var index = layer.open({
			type : 2,
			title : '修改',
			content : '${pageContext.request.contextPath}/workBench/goTotal.do?id='+id,
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
	
	function view(id) {
		var index = layer.open({
			type : 2,
			title : '查看详情',
			content : '${pageContext.request.contextPath}/workBench/viewOrderReportInfo.do?id='+id,
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
