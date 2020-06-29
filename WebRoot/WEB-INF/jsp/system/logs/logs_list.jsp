<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="${pageContext.request.contextPath}/">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 下拉框 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/chosen.css" />
<!-- 日期框 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/datepicker.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/colorbox.css" />
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<!-- 检索  -->
							<form action="logs/listLogs.do" method="post" name="logsForm" id="logsForm">
								<table style="margin-top: 5px;">
									<tr>
										<td>
											<div class="nav-search">
												<span class="input-icon"> <input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="keywords" value="${pd.keywords }" placeholder="输入模块名称、用户名、类名" /> <i
													class="ace-icon fa fa-search nav-search-icon"></i>
												</span>
											</div>
										</td>
										<td style="padding-left: 2px;"><input class="span10 date-picker" id="createTimeStart" name="createTimeStart" value="${fn:substring(pd.createTimeStart, 0, 10)}" type="text"
											data-date-format="yyyy-mm-dd" readonly="readonly" style="width: 98px;" placeholder="开始日期" title="开始日期" /></td>
										<td style="padding-left: 2px;"><input class="span10 date-picker" id="createTimeEnd" name="createTimeEnd" value="${fn:substring(pd.createTimeEnd, 0, 10)}" type="text"
											data-date-format="yyyy-mm-dd" readonly="readonly" style="width: 98px;" placeholder="结束日期" title="结束日期" /></td>
										<%-- <td style="padding-left: 2px;">
											<select id="status" name="status" class="form-control" style="width: 100px;" data-placeholder="订单状态">
												<option value="" <c:if test="${empty pd.status || pd.status == null || pd.status == ''}">selected</c:if>>全部</option>
												<option value="0" <c:if test="${pd.status == '0'}">selected</c:if>>未处理</option>
												<option value="1" <c:if test="${pd.status == '1'}">selected</c:if>>已处理</option>
											</select>
										</td> --%>
										<c:if test="${QX.cha == 1 }">
											<td style="vertical-align: top; padding-left: 2px;"><button class="btn btn-light btn-xs" onclick="searchs();" title="检索">
													<i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>检索
												</button>
												<button class="btn btn-xs" onclick="javascript:emptyForm();"><i id="nav-search-icon" class="ace-icon fa fa-refresh bigger-110 nav-search-icon blue"></i>重置</button></td>
										</c:if>
									</tr>
								</table>
								<!-- 检索  -->
								<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top: 5px;">
									<thead>
										<tr>
											<th class="center" style="width: 50px;">序号</th>
											<th class="center">模块</th>
											<!-- <th class="center">用户姓名</th> -->
											<th class="center">类名</th>
											<th class="center">方法名</th>
											<th class="center">异常级别</th>
											<!-- <th class="center">异常信息描述</th> -->
											<th class="center">异常信息</th>
											<!-- <th class="center">异常状态</th> -->
											<th class="center">ip</th>
											<th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建时间</th>
										</tr>
									</thead>

									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty logsList}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${logsList}" var="logs" varStatus="vs">
														<tr>
															<td class='center' style="width: 30px;">${vs.index+1}</td>
															<td class="center">${logs.moduleName }</td>
															<%-- <td class="center">${logs.userName }</td> --%>
															<td class="center">${logs.className }</td>
															<td class="center">${logs.method }</td>
															<td class="center">${logs.logLevel }</td>
															<%-- <td class="center">${logs.msg }</td> --%>
															<td class="center"><a title="点击查看详情" onclick="view('${logs.id}')" style="cursor: pointer;">点击查看详情</a></td>
															<%-- <td class="center">${logs.status==0 ?"未处理":"已处理" }</td> --%>
															<td class="center">${logs.ip }</td>
															<td class="center">${fn:substring(logs.createTime, 0, 19)}</td>
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

								<div class="page-header position-relative">
									<table style="width: 100%;">
										<tr>
											<td style="vertical-align: top;"><div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
										</tr>
									</table>
								</div>
							</form>

						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse"> <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="${pageContext.request.contextPath}/static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="${pageContext.request.contextPath}/static/ace/js/ace/ace.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.tips.js"></script>
	<!-- 日期框 -->
	<script src="${pageContext.request.contextPath}/static/ace/js/date-time/bootstrap-datepicker.js"></script>

</body>

<script type="text/javascript">
$(top.hangge());

function emptyForm() {
	$("#nav-search-input").val('');
	$("#createTimeStart").val('');
	$("#createTimeEnd").val('');
}

//检索
function searchs(){
	top.jzts();
	$("#logsForm").submit();
}

//查看
function view(id){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="查看详情";
	 diag.URL = '${pageContext.request.contextPath}/logs/view.do?id=' + id;
	 diag.Width = 1000;
	 diag.Height = 800;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}



$(function() {
	//日期框
	$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
	
	if($("#createTimeStart").val()){
		 $("#createTimeEnd").datepicker('setStartDate', new Date($("#createTimeStart").val())) 
	}
	
	if($("#createTimeEnd").val()){
		 $("#createTimeStart").datepicker('setEndDate', new Date($("#createTimeEnd").val())) 
	}
	
	//开始日期
	$("#createTimeStart").datepicker({ }).on('changeDate', function(ev){
		            if(ev.date){  
			                $("#createTimeEnd").datepicker('setStartDate', new Date(ev.date.valueOf()))  
			            }else{  
			                $("#createTimeEnd").datepicker('setStartDate',null);  
			            }  
			          });
	//结束日期
	$("#createTimeEnd").datepicker({}).on('changeDate', function(ev){    
		            if(ev.date){  
			                $("#createTimeStart").datepicker('setEndDate', new Date(ev.date.valueOf()))  
			            }else{  
			                $("#createTimeStart").datepicker('setEndDate',new Date());  
			            }   
			          });
});

</script>
</html>
