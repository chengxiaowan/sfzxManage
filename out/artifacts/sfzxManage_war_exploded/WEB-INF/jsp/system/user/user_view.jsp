<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<!-- 下拉框 -->
<body class="gray-bg">
<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- /section:basics/navbar.layout -->
	<div class="ibox-content">
					<div class="row">
						<div class="col-xs-12">
							<form action="user/${msg }.do" name="userForm" id="userForm" method="post">
								<input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }" />
								<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<c:if test="${fx != 'head'}">
											<tr>
												<td style="width: 79px; text-align: right; padding-top: 13px;">角色:</td>
												<td id="juese"><select class="chosen-select form-control" name="ROLE_ID" id="role_id" style="vertical-align: top;" style="width:98%;" disabled="disabled">
														<c:forEach items="${roleList}" var="role">
															<option value="${role.ROLE_ID }" <c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
														</c:forEach>
												</select></td>
											</tr>
										</c:if>
										<c:if test="${fx == 'head'}">
											<input name="ROLE_ID" id="role_id" value="${pd.ROLE_ID }" type="hidden" />
										</c:if>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">用户名:</td>
											<td><input type="text" name="USERNAME" id="loginname" value="${pd.USERNAME }" maxlength="32" title="用户名" style="width: 98%;" disabled="disabled" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">编号:</td>
											<td><input type="text" name="NUMBER" id="NUMBER" value="${pd.NUMBER }" maxlength="32" title="编号" onblur="hasN('${pd.USERNAME }')" style="width: 98%;" disabled="disabled" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">姓名:</td>
											<td><input type="text" name="NAME" id="name" value="${pd.NAME }" maxlength="32" placeholder="这里输入姓名" title="姓名" style="width: 98%;" disabled="disabled" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">手机号:</td>
											<td><input type="number" name="PHONE" id="PHONE" value="${pd.PHONE }" maxlength="32" title="手机号" style="width: 98%;" disabled="disabled" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">qq号:</td>
											<td><input type="number" name="QQ" id="QQ" value="${pd.QQ }" maxlength="32" title="qq号" style="width: 98%;" disabled="disabled" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">微信号:</td>
											<td><input type="number" name="weChatId" id="weChatId" value="${pd.weChatId }" maxlength="32" title="微信号" style="width: 98%;" disabled="disabled" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">邮箱:</td>
											<td><input type="email" name="EMAIL" id="EMAIL" value="${pd.EMAIL }" maxlength="32" title="邮箱" onblur="hasE('${pd.USERNAME }')" style="width: 98%;" disabled="disabled" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">个人优势:</td>
											<td><textarea style="width: 95%; height: 120px;" rows="5" cols="15" name="BZ" id="BZ" title="个人优势" maxlength="1000" placeholder="这里输入个人优势" disabled="disabled">${pd.BZ }</textarea></td>
										</tr>
									</table>
								</div>
							</form>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/js.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		if($("#user_id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	
	//判断用户名是否存在
	function hasU(){
		var USERNAME = $.trim($("#loginname").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasU.do',
	    	data: {USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#userForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					$("#loginname").css("background-color","#D16E6C");
					setTimeout("$('#loginname').val('此用户名已存在!')",500);
				 }
			}
		});
	}
	
	//判断邮箱是否存在
	function hasE(USERNAME){
		var EMAIL = $.trim($("#EMAIL").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasE.do',
	    	data: {EMAIL:EMAIL,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#EMAIL").tips({
							side:3,
				            msg:'邮箱 '+EMAIL+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#EMAIL").val('');
				 }
			}
		});
	}
	
	$(function() {
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
	});
</script>
</html>