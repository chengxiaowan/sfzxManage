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
					<form class="form-inline" role="form" action="order/listMonthReport.do" method="post" name="customerForm" id="customerForm">
						<div class="form-group">
						<c:if test="${empty pd.userId }">
						<select name="runnerId" id="" style="width:200px;">
							<option value="${pd.runnerId}">${pd.runnerName }<option>
						</select>
						<input type="hidden" name="runnerName" value="${pd.runnerName }" />
						</c:if>
							<input id="nav-search-input" name="keywords" placeholder="月报标题" value="${pd.keywords }" class="form-control" type="text">
						</div>
						<div class="form-group">
							<input class="span10 date-picker form-control" id="createTimeStart" name="createTimeStart" value="${fn:substring(pd.createTimeStart, 0, 10)}" type="text" data-date-format="yyyy-mm"
								readonly="readonly" placeholder="月份" title="" />
						</div>
						<button type="submit" class="btn btn-primary"><i class="fa fa-search"></i> 搜索</button>
						<button type="reset" class="btn">
							<i id="nav-search-icon" class="fa fa-refresh nav-search-icon "></i> 重置
						</button>
						<c:if test="${pd.flag ==1}">
							<div class="pull-right">
							<a href="javascript:;" onclick="newReport()" class="btn btn-success"><i class="fa fa-plus"></i> 新建月报</a>
							</div>
						</c:if>
						</form>
						
					
				</div>
			</div>
		</div>
		<div class="ibox-content">

			<table id="simple-table" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center" style="width: 50px;">序号</th>
						<th class="center">运作人</th>
						<th class="center">月报标题</th>
						<th class="center">审核状态</th>
						<th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建时间</th>
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
										<td class="center">${list.userName }</td>
										<td class="center">${list.title }</td>
										<td class="center"><c:if test="${list.status==0 }">草稿</c:if>
										<c:if test="${list.status==1 }">已提交</c:if>
										<c:if test="${list.status==2 }">已审核</c:if>
										<c:if test="${list.status==3 }">已驳回</c:if>
										</td>
										<td class="center">${fn:substring(list.createTime, 0, 19)}</td>
										<td class="center">
										<c:if test="${((list.status==0 ||list.status==3)&& not empty pd.userId)}"><button onclick="editReport(${list.id })" class="btn btn-success"><i class="fa fa-edit"></i> 编辑</button></c:if>
										<c:if test="${ empty pd.userId}"><button onclick="jl_editReport(${list.id },${list.status })" class="btn btn-success"><i class="fa fa-edit"></i> 编辑</button></c:if>
										<c:if test="${list.status==1 && empty pd.userId}"><button onclick="shReport(${list.id })" class="btn btn-info"><i class="fa fa-legal"></i> 审核</button></c:if>
										<button onclick="viewReport(${list.id })" class="btn btn-primary"><i class="fa fa-search"></i> 查看</button>
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
}).on("change",function(){
	$("[name=runnerName]").val($(this).find("option:selected").text())
	
})

	function newReport()
			{
				var index = layer.open({
					type : 2,
					title : ' ',
					content : '/static/page/report_month_edit.html',
					area : [ '100%', '100%' ],
					btnAlign:'c',
					btn : [ '存为草稿','提交' ],
					yes : function(index, layero) {
						var child = window[layero.find('iframe')[0]['name']]; 
						child.app.save(0)
					},
					btn2:function(index, layero){
						var child = window[layero.find('iframe')[0]['name']];
						child.app.save(1)
					}
				});
				
			}
	function editReport(id)
	{
		var index = layer.open({
			type : 2,
			title : ' ',
			content : '/static/page/report_month_edit.html?id=' + id,
			area : [ '100%', '100%' ],
			btnAlign:'c',
			btn : [ '存为草稿','提交' ],
			yes : function(index, layero) {
				var child = window[layero.find('iframe')[0]['name']]; 
				child.app.edit(0)
			},
			btn2:function(index, layero){
				var child = window[layero.find('iframe')[0]['name']];
				child.app.edit(1)
			}
		});
	}
	function jl_editReport(id,status)
	{
		var index = layer.open({
			type : 2,
			title : ' ',
			content : '/static/page/report_month_edit.html?id=' + id,
			area : [ '100%', '100%' ],
			btnAlign:'c',
			btn : [ '保存' ],
			yes : function(index, layero) {
				var child = window[layero.find('iframe')[0]['name']]; 
				child.app.edit(status)
			}
		});
	}
	function viewReport(id)
	{
		var index = layer.open({
			type : 2,
			title : ' ',
			content : '/static/page/report_month_edit.html?id=' + id +'&mode=1',
			area : [ '100%', '100%' ],
			btnAlign:'c',
			end : function(index, layero) {
				layer.close(index)
			}
		});
	}
	
	function shReport(id)
	{
		var index = layer.open({
			type : 2,
			title : ' ',
			content : '/static/page/report_month_edit.html?id=' + id +'&mode=1',
			area : [ '100%', '100%' ],
			btnAlign:'c',
			btn : [ '通过','驳回' ],
			yes : function(index, layero) {
				var child = window[layero.find('iframe')[0]['name']]; 
				child.app.edit(2)
			},
			btn2:function(index, layero){
				var child = window[layero.find('iframe')[0]['name']];
				child.app.edit(3)
			}
		});
	}
	
	
	
	
	
	//批量操作
	$(function() {
		$('.date-picker').datepicker({
			startView:3,
			minViewMode :'months',
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
	});
</script>
</body>
</html>
