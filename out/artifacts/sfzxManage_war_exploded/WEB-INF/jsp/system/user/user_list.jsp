<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ui/css/plugins/chosen/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<body class="gray-bg">
	<div class="ibox-content animated fadeInUp">
		<div class="row">
			<div class="col-xs-12">
				<!-- 检索  -->
				<form class="form-inline" action="user/listUsers.do" method="post" name="userForm" id="userForm">
					<div class="form-group">
						<input id="nav-search-input" name="keywords" placeholder="请输入用户姓名" value="${pd.keywords }" class="form-control" type="text">
					</div>
					<div class="form-group">
						<input class="span10 date-picker form-control" id="lastLoginStart" name="lastLoginStart" value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
							placeholder="开始日期" title="最近登录开始" />
					</div>
					<div class="form-group">
						<input class="span10 date-picker form-control" id="lastLoginEnd" name="lastLoginEnd" value="${pd.lastLoginEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="结束日期"
							title="最近登录结束" />
					</div>
					<div class="form-group">
						<select class="chosen-select form-control" name="ROLE_ID" id="role_id" data-placeholder="请选择角色">
							<option value=""></option>
							<option value="">全部</option>
							<c:forEach items="${roleList}" var="role">
								<option value="${role.ROLE_ID }" <c:if test="${pd.ROLE_ID==role.ROLE_ID}">selected</c:if>>${role.ROLE_NAME }</option>
							</c:forEach>
						</select>
					</div>
					<c:if test="${QX.cha == 1 }">
						<a class="btn btn-primary" onclick="searchs();" title="检索"><i id="nav-search-icon" class=" fa fa-search"></i> 搜索</a>
						<c:if test="${QX.toExcel == 1 }">
							<a class="btn btn-white " onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class=" fa fa-download"></i> 导出到EXCEL</a>
						</c:if>
						<%-- 											<c:if test="${QX.FromExcel == 1 }"> --%>
						<!-- 												<td style="vertical-align: top; padding-left: 2px;"><a class="btn btn-light btn-xs" onclick="fromExcel();" title="从EXCEL导入"><i id="nav-search-icon" -->
						<!-- 														class="ace-icon fa fa-cloud-upload bigger-110 nav-search-icon blue"></i></a></td> -->
						<%-- 											</c:if> --%>
					</c:if>

					<!-- 检索  -->

					<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top: 5px;">
						<thead>
							<tr>
								<th class="center" style="width: 35px;"><label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label></th>
								<th class="center" style="width: 50px;">序号</th>
								<!--<th class="center">编号</th>-->
								<th class="center">用户名</th>
								<th class="center">姓名</th>
								<th class="center">角色</th>
								<th class="center">状态</th>
								<th class="center">入职时间</th>
								<th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>最近登录</th>
								<th class="center">上次登录IP</th>
								<th class="center">操作</th>
							</tr>
						</thead>

						<tbody>

							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty userList}">
									<c:if test="${QX.cha == 1 }">
										<c:forEach items="${userList}" var="user" varStatus="vs">
											
											<tr>
												<td class='center' style="width: 30px;"><c:if test="${user.USERNAME != 'admin'}">
														<label><input type='checkbox' name='ids' value="${user.USER_ID }" id="${user.EMAIL }" alt="${user.PHONE }" title="${user.USERNAME }" class="ace" /><span class="lbl"></span></label>
													</c:if> <c:if test="${user.USERNAME == 'admin'}">
														<label><input type='checkbox' disabled="disabled" class="ace" /><span class="lbl"></span></label>
													</c:if></td>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<!--<td class="center">${user.NUMBER }</td>-->
												<td class="center"><a onclick="viewUser('${user.USERNAME}')" style="cursor: pointer;">${user.USERNAME }</a></td>
												<td class="center">${user.NAME }</td>
												<td class="center">${user.ROLE_NAME }</td>
												<td class="center">${user.isQuit=='0' ? '在职':'离职' }</td>
												<!--入职时间-->
												<td class="center">${user.EMAIL }</td>
												<td class="center">${user.LAST_LOGIN}</td>
												<td class="center">${user.IP}</td>
												<td class="center"><c:if test="${QX.edit != 1 && QX.del != 1 }">
														<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
													</c:if> <%-- 																	<c:if test="${QX.FHSMS == 1 }"> --%> <%-- 																		<a class="btn btn-xs btn-info" title='发送站内信' onclick="sendFhsms('${user.USERNAME }');"> <i class="ace-icon fa fa-envelope-o bigger-120" title="发送站内信"></i> --%>
													<!-- 																		</a> --> <%-- 																	</c:if> --%> <%-- 																	<c:if test="${QX.sms == 1 }"> --%> <%-- 																		<a class="btn btn-xs btn-warning" title='发送短信' onclick="sendSms('${user.PHONE }');"> <i class="ace-icon fa fa-envelope-o bigger-120" title="发送短信"></i> --%>
													<!-- 																		</a> --> <%-- 																	</c:if> --%> <c:if test="${QX.edit == 1 }">
														<a class="btn btn-success" title="查看" onclick="editUser('${user.USER_ID}');"> <i class="ace-icon fa fa-pencil-square-o bigger-120" title="查看"></i> 查看
														</a>
													</c:if> <c:if test="${user.isQuit=='0'}">
														<a class="btn btn-danger" onclick="delUser('${user.USER_ID }','${user.isQuit }','${user.ROLE_ID }')"> <i class="ace-icon fa fa-trash-o bigger-120" title="离职"></i> 离职
														</a>
													</c:if>
													</td>
											</tr>

										</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="10" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="10" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>

					<div class="page-header position-relative">
						<table style="width: 100%;">
							<tr>
								<td style="vertical-align: top;"><c:if test="${QX.add == 1 }">
										<a class="btn btn-mini btn-success" onclick="add();"><i class="fa fa-plus"></i> 新增</a>
									</c:if> <%-- 												<c:if test="${QX.FHSMS == 1 }"> --%> <!-- 													<a title="批量发送站内信" class="btn btn-mini btn-info" onclick="makeAll('确定要给选中的用户发送站内信吗?');"><i class="ace-icon fa fa-envelope-o bigger-120"></i></a> -->
									<%-- 												</c:if> <c:if test="${QX.email == 1 }"> --%> <!-- 													<a title="批量发送电子邮件" class="btn btn-mini btn-primary" onclick="makeAll('确定要给选中的用户发送邮件吗?');"><i class="ace-icon fa fa-envelope bigger-120"></i></a> -->
									<%-- 												</c:if> <c:if test="${QX.sms == 1 }"> --%> <!-- 													<a title="批量发送短信" class="btn btn-mini btn-warning" onclick="makeAll('确定要给选中的用户发送短信吗?');"><i class="ace-icon fa fa-envelope-o bigger-120"></i></a> -->
									<%-- 												</c:if>  --%> <c:if test="${QX.del == 1 }">
										<a title="批量删除" class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');"><i class='fa fa-trash-o'></i> 删除</a>
									</c:if></td>
								<td style="vertical-align: top;"><div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
					</div>
				</form>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
	</div>
	<!-- 页面底部js¨ -->
	<%@ include file="../index/js.jsp"%>
	<!-- 日期框 -->
	<script src="static/ace/js/laydate/laydate.js" type="text/javascript" charset="utf-8"></script>
	<!-- 下拉框 -->
	<script src="${pageContext.request.contextPath}/static/ui/js/plugins/chosen/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>

<script type="text/javascript">
//检索
function searchs(){
	$("#userForm").submit();
}

//删除
function delUser(userId,isquit,roleId){
	layer.confirm("是否确认离职?", function(result) {
			var url = "/user/isQuit?USER_ID="+userId+"&isQuit=1"+"&ROLE_ID="+roleId;
			$.get(url,function(data){
				location.reload()
			});
	});
}

//新增
function add(){
	var index =layer.open({
	      type: 2,
	      title:'新增',
	      content: '<%=basePath%>user/goAddU.do',
	      area: ['100%', '100%']
	})
}

//修改
function editUser(user_id){
	var index =layer.open({
	      type: 2,
	      title:'编辑',
	      content: '<%=basePath%>user/goEditU.do?USER_ID='+user_id,
	      area: ['100%', '100%']
	})
}

//批量操作
function makeAll(msg){
	layer.confirm(msg, function(result) {
			var str = '';
			var emstr = '';
			var phones = '';
			var username = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++)
			{
				  if(document.getElementsByName('ids')[i].checked){
				  	if(str=='') str += document.getElementsByName('ids')[i].value;
				  	else str += ',' + document.getElementsByName('ids')[i].value;
				  	
				  	if(emstr=='') emstr += document.getElementsByName('ids')[i].id;
				  	else emstr += ';' + document.getElementsByName('ids')[i].id;
				  	
				  	if(phones=='') phones += document.getElementsByName('ids')[i].alt;
				  	else phones += ';' + document.getElementsByName('ids')[i].alt;
				  	
				  	if(username=='') username += document.getElementsByName('ids')[i].title;
				  	else username += ';' + document.getElementsByName('ids')[i].title;
				  }
			}
			if(str==''){
				bootbox.dialog({
					message: "<span class='bigger-110'>您没有选择任何内容!</span>",
					buttons: 			
					{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
				});
				$("#zcheckbox").tips({
					side:3,
		            msg:'点这里全选',
		            bg:'#AE81FF',
		            time:8
		        });
				
				return;
			}else{
				if(msg == '确定要删除选中的数据吗?'){
					$.ajax({
						type: "POST",
						url: '<%=basePath%>user/deleteAllU.do?tm='+new Date().getTime(),
				    	data: {USER_IDS:str},
						dataType:'json',
						//beforeSend: validateData,
						cache: false,
						success: function(data){
							 $.each(data.list, function(i, list){
									nextPage(${page.currentPage});
							 });
						}
					});
				}else if(msg == '确定要给选中的用户发送邮件吗?'){
					sendEmail(emstr);
				}else if(msg == '确定要给选中的用户发送短信吗?'){
					sendSms(phones);
				}else if(msg == '确定要给选中的用户发送站内信吗?'){
					sendFhsms(username);
				}
			}
	});
}

//去发送短信页面
function sendSms(phone){
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="发送短信";
	 diag.URL = '<%=basePath%>head/goSendSms.do?PHONE='+phone+'&msg=appuser';
	 diag.Width = 600;
	 diag.Height = 265;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//去发送电子邮件页面
function sendEmail(EMAIL){
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="发送电子邮件";
	 diag.URL = '<%=basePath%>head/goSendEmail.do?EMAIL='+EMAIL;
	 diag.Width = 660;
	 diag.Height = 486;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//发站内信
function sendFhsms(username){
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="站内信";
	 diag.URL = '<%=basePath%>fhsms/goAdd.do?username='+username;
	 diag.Width = 660;
	 diag.Height = 444;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

$(function() {
	//日期框
	laydate({
    elem: '#lastLoginStart', 
    event: 'focus',
    format: 'YYYY-MM-DD',
	});
	laydate({
    elem: '#lastLoginEnd', 
//    format: 'YYYY-MM-DD',
    format: 'YYYY-MM-DD hh:mm:ss', //日期格式
    istime: true,
    event: 'focus' 
	});	
	//下拉框
		$('.chosen-select').chosen({allow_single_deselect:true}); 
		$(window)
		.off('resize.chosen')
		.on('resize.chosen', function() {
			$('.chosen-select').each(function() {
				 var $this = $(this);
				 $this.next().css({'width': $this.parent().width()});
			});
		}).trigger('resize.chosen');
		$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
			if(event_name != 'sidebar_collapsed') return;
			$('.chosen-select').each(function() {
				 var $this = $(this);
				 $this.next().css({'width': $this.parent().width()});
			});
		});
		$('#chosen-multiple-style .btn').on('click', function(e){
			var target = $(this).find('input[type=radio]');
			var which = parseInt(target.val());
			if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
			 else $('#form-field-select-4').removeClass('tag-input-style');
		});

	
	//复选框全选控制
	var active_class = 'active';
	$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
		var th_checked = this.checked;//checkbox inside "TH" table header
		$(this).closest('table').find('tbody > tr').each(function(){
			var row = this;
			if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
			else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
		});
	});
});

//导出excel
function toExcel(){
	var keywords = $("#nav-search-input").val();
	var lastLoginStart = $("#lastLoginStart").val();
	var lastLoginEnd = $("#lastLoginEnd").val();
	var ROLE_ID = $("#role_id").val();
	window.location.href='<%=basePath%>user/excel.do?keywords='+keywords+'&lastLoginStart='+lastLoginStart+'&lastLoginEnd='+lastLoginEnd+'&ROLE_ID='+ROLE_ID;
}

//打开上传excel页面
function fromExcel(){
	var index =layer.open({
	      type: 2,
	      title:'EXCEL 导入到数据库',
	      content: '<%=basePath%>user/goUploadExcel.do',
	      area: ['60%', '60%']
	})
}	

//查看用户
function viewUser(USERNAME){
	if('admin' == USERNAME){
		bootbox.dialog({
			message: "<span class='bigger-110'>不能查看admin用户!</span>",
			buttons: 			
			{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
		});
		return;
	}
	
	var index =layer.open({
	      type: 2,
	      title:'查看用户',
	      content: '<%=basePath%>user/view.do?USERNAME='+USERNAME,
	      area: ['100%', '100%']
	})
}
		
</script>
</html>
