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
	<div class="wrapper wrapper-content">
		<div class="ibox-content">
			<div class="row">
				<div class="col-xs-12">
					<!-- 检索  -->
					<form class="form-inline" role="form" action="${pageContext.request.contextPath}/handover/list.do" method="post" name="customerForm" id="customerForm">
						<div class="form-group">
							<input id="nav-search-input" name="keywords" placeholder="运作人、债务人(公司)、债权人(公司)" value="${pd.keywords }" class="form-control" type="text" style="width: 350px;">
						</div>
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
			<div class="row">
				<div class="col-md-7">
					<table id="log_list" class="table table-bordered table-hover">
						<thead>
					<tr>
						<th class="center">债权人(公司)</th>
									<th class="center">债务人(公司)</th>
									<th class="center">欠款总金额(元)</th>
									<th class="center">已回款金额(元)</th>
									<th class="center">剩余金额(元)</th>
									<th class="center">佣金比例<br>(诉讼/非诉讼)</th>
									<th class="center">运作人</th>
								<th class="center">操作</th>
					</tr>
				</thead>

				<tbody>
					<!-- 开始循环 -->
					<c:choose>
						<c:when test="${not empty orderList}">
							<c:if test="${QX.cha == 1 }">
								<c:forEach items="${orderList}" var="list" varStatus="vs">
									<tr data-id="${list.orderId }">
													<td class="center">${list.customerName }</td>
													<td class="center">${list.debtorName }</td>
													<td class="center">${list.debtAmount }</td>
													<td class="center">${list.totalMoney }</td>
													<td class="center">${list.syMoney }</td>
													<td class="center"><c:if test="${list.cType==0 }">${list.commissionRate}<c:if test="${not empty list.lawCommissionRate}">/${list.lawCommissionRate}</c:if></c:if><c:if test="${list.cType==1 }">-</c:if></td>
													<td class="center">${list.runnerName }</td>
												<td>
												<button class="btn btn-primary" onclick="log_load('${list.orderId}','${list.orderNo }','${list.debtorName }','${list.status }',this)">
														<i class="fa fa-file"></i> 查看
													</button>
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
						<div style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
					</div>
				</div>
				<div class="col-md-5" style="display: none" id="side_timeline">
					<div class="well well-sm clearfix" style="margin-bottom: 0" id="log_title">
						<span></span>
						<button type="button" class="btn btn-success btn-sm pull-right m-t-sm" onclick="doTask(this)">
							<i class="fa fa-plus"></i> 新增
						</button>
					</div>
					<div style="max-height: 900px; overflow-y: scroll;">
						<div id="vertical-timeline" class="vertical-container dark-timeline"></div>
					</div>
				</div>
			</div>
			<!-- /.col -->
		</div>

	</div>
	<!-- 写跟进 -->
	<div class="ibox-content" id="new-float-box" style="overflow: hidden; display: none">
		<div class="col-md-12">
			<form onsubmit="return false;" action="" class="form form-horizontal">
				<div class="form-group">
					<label class="control-label">跟进方式：</label> <select class="form-control" name="link" id="link">
						<option value="电话">电话</option>
						<option value="QQ">QQ</option>
						<option value="微信">拜访</option>
						<option value="邮件">邮件</option>
						<option value="短信">短信</option>
						<option value="其他">其他</option>
					</select>
				</div>
				<div class="form-group">
					<label class="control-label">跟进时间：</label> <input id="log_createTime" class="form-control layer-date" type="text" name="createTime" value="">
				</div>
				<div class="form-group">
					<label class="control-label">跟进记录：</label>
					<textarea class="form-control" id="remark" name="remark" style="height: 120px"></textarea>
				</div>
				<div class="form-group">
					<label class="control-label">附件：</label>
					<div class="well well-sm">
						<div id="fileUp"></div>
					</div>
					<div class="file-list"></div>
				</div>
			</form>
		</div>
	</div>


	<!-- /.main-container -->
	<!-- 日期框 -->
	<!-- 全局js -->
	<%@ include file="../../system/index/js.jsp"%>


	<script type="text/javascript">
	//查看
	function viewOrder(id) {
		var index = layer
				.open({
					type : 2,
					title : '点击查看详情',
					content : '${pageContext.request.contextPath}/order/viewOrder.do?id='
							+ id,
					area : [ '90%', '90%' ]
				});
	}


	//切换页面参数
	var log_title = $("#log_title").find("span")
	var log_btn = $("#log_title").find("button")

	//全局变量
	var orderId = "";  //当前选择项Id
	var status = "";
	var uploader_init = 0;//上传组件状态
	
	//新增
	function doTask(dom,type) {
		if(dom.getAttribute("disabled")!=null){
			return;
		}
		var index = layer
				.open({
					type : 2,
					title : '新增记录',
					content : '/handover/goAdd.do?orderId=' + orderId + '&status=' + status,
					area : [ '600px', '600px' ]
				});
	}
	
	//编辑
	function edit(orderId,id){
		var index = layer
		.open({
			type : 2,
			title : '编辑',
			content : '/handover/goEdit.do?id='+ id +'&status=' + status,
			area :  [ '600px', '600px' ]
		});
	}
	
	
	/**
	*跟进记录加载
	**/
	function log_load (id,orderNo,debName,letStatus,dom){
		$("#side_timeline").show();
		var loading = layer.load(3,{
			shade:[.6,"#fff"]
		});
		if(!window.debName){
			window.debName = debName
		}
		status = letStatus;
		if(dom){
			$(dom).parents("tr").addClass("success").siblings().removeClass("success")
			
		}
		log_title.html('<span class="pull-left" style="line-height:48px">案件交接 &nbsp;</span> <div class="pull-left"><button class="btn btn-info btn-xs m-b-xs" type="button">案件编号 : '+orderNo+'</button>' + '<br><button style="position:relative;top:-2px;" class="btn btn-info btn-xs" type="button">债务人 : '+debName+'</button></div>')
		if(id) orderId = id;
		$.post("/order/showAll",{
			orderId:id,  //21
			type:6
		}).done(function(res){
			if(res.error=="00"){
					if(!res.result.length)	{
					$("#vertical-timeline").html("<div style='text-indent:3em;'>暂无记录</div>");	
					}else{
						var html = "";
						$.each(res.result,function(){
							html+='<div class="vertical-timeline-block"><div class="vertical-timeline-icon blue-bg"><i class="fa fa-file-text"></i>'
                        	html+='</div><div class="vertical-timeline-content">'	
                        	var contentTitle ='';
							var _timeTEXT = "时间";
                            var _editBtn = '编辑';
                            var _status = ""
                            if(status =="4"){
                            	_status = this.zongcstatus=="0"?"仲裁":"执行";
                            }else if(this.susongstatus=="0"){
                            	_status = "一审"
                            }else if(this.susongstatus=="1"){
                            	_status = "二审"
                            }else if(this.susongstatus=="2"){
                            	_status = "执行"
                            }
                            
                            html+='<p>状态: <span class="label label-warning label-outline">'+_status+'</span></p>';
                            
                        	contentTitle='备注';
                            html+='<p>'+contentTitle+' : '+this.remark+'</p>'
                            if(this.attachs&&this.attachs.length){
                            	html+='<div><strong>相关附件 : </strong> </div>'
                            	$.each(this.attachs,function(){
	                       			html+='<div><a href="'+this.url+'" target="_blank">'+this.originalFilename+'</a></div>'
                            	})
                            }
                            /* 	html+='<div class="vertical-date">'+_timeTEXT+' :  <span class="text-info">'+this.time+'</span></div>' */
                            			html+='<a href="javascript:;" onclick="edit('+ id +','+this.id+')" class="btn btn-xs btn-primary"> '+_editBtn+'</a>';
                            html+='</div></div>' 
						})
						$("#vertical-timeline").html(html);	
					}
			}
			layer.close(loading);
		})
	}

    $("#log_createTime").change(function(){
    	if(this.value=="")  $("#log_createTime").val(laydate.now((new Date()).getTime(), 'YYYY-MM-DD hh:mm:ss'));
    })
	
	
	
	//批量操作

	$(function() {
		$("#log_list tbody tr:first").find("button").click()
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
	});

	
	
	
	</script>
</body>
</html>

