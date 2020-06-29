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
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/colorbox.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<body class="gray-bg">
	<!-- /section:basics/navbar.layout -->
				<div class="ibox-content">
					<div class="row">
						<div class="col-xs-12">
							<form action="user/${msg }.do" class="form" name="userForm" id="userForm" method="post" enctype="multipart/form-data">
								<input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }" />
								<input type="hidden" name="originalPicName" id="originalPicName" value="${pd.originalPicName }"/>
								<input type="hidden" name="qnKey" id="qnKey" value="${pd.qnKey }"/>
								<input type="hidden" name="pic" id="pic" value="${pd.pic }"/>
								  <input type="hidden" name="isQuit" id="isQuit" value="${pd.isQuit}" />
								   <input type="hidden" name="isUrl" id="isUrl" value="${pd.isUrl}" />
								<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">姓名:</td>
											<td><input class="form-control" type="text" name="NAME" id="name" value="${pd.NAME }" maxlength="32" placeholder="这里输入姓名" title="姓名" style="width: 98%;" /></td>
										</tr>
										<c:if test="${fx != 'head'}">
											<tr>
												<td style="width: 79px; text-align: right; padding-top: 13px;">角色:</td>
												<td id="juese"><select class="form-control" name="ROLE_ID" id="role_id" data-placeholder="请选择角色" style="vertical-align: top;" style="width:98%;">
														<option value=""></option>
														<c:forEach items="${roleList}" var="role">
															<option value="${role.ROLE_ID }" <c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
														</c:forEach>
												</select></td>
											</tr>
										</c:if>
										<c:if test="${fx == 'head'}">
											<input class="form-control" name="ROLE_ID" id="role_id" value="${pd.ROLE_ID }" type="hidden" />
										</c:if>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">用户名:</td>
											<td><input class="form-control" type="text" name="USERNAME" id="loginname" value="${pd.USERNAME }" maxlength="32" placeholder="这里输入用户名" title="用户名" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">编号:</td>
											<td><input class="form-control" type="text" name="NUMBER" id="NUMBER" value="1" maxlength="32" placeholder="这里输入编号" title="编号" onblur="hasN('${pd.USERNAME }')" style="width: 98%;" readonly="readonly"/></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">密码:</td>
											<td><input class="form-control" type="password" name="PASSWORD" id="password" maxlength="32" placeholder="输入密码" title="密码" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">确认密码:</td>
											<td><input class="form-control" type="password" name="chkpwd" id="chkpwd" maxlength="32" placeholder="确认密码" title="确认密码" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">手机号:</td>
											<td><input class="form-control" type="number" name="PHONE" id="PHONE" value="${pd.PHONE }" maxlength="32" placeholder="这里输入手机号" title="手机号" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">qq号:</td>
											<td><input class="form-control" type="number" name="QQ" id="QQ" value="${pd.QQ }" maxlength="32" placeholder="这里输入qq号" title="qq号" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">微信号:</td>
											<td><input class="form-control" type="text" name="weChatId" id="weChatId" value="${pd.weChatId }" maxlength="32" placeholder="这里输入微信号" title="微信号" style="width: 98%;" /></td>
										</tr>
										<%-- <tr>
											<td style="width: 115px; text-align: right; padding-top: 13px;">微信二维码:</td>
											<td colspan="3"><c:choose>
													<c:when test="${empty pd.pic}">
														<input type="file" id="tp" name="tp" onchange="fileType(this)" />
													</c:when>
													<c:otherwise>
														<c:set var="pic" value="${pd.pic }" />
														<c:set var="pIndex" value="${fn:indexOf(pic, '?')}" />
														<c:set var="picLength" value="${fn:length(pic)}" />
														<c:set var="originalImgUrl" value="${fn:substring(pic, 0, pIndex)}" />
														<c:set var="detailImgUrl" value="${fn:substring(pic, 0, pIndex)}-detailStyle" />
														<a href="${originalImgUrl}" data-rel="colorbox"><img src="${detailImgUrl}" /></a>
														<input type="button" class="btn btn-mini btn-danger" value="删除" onclick="delP('${pd.USER_ID }');" />
													</c:otherwise>
												</c:choose>
											</td>
										</tr> --%>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">邮箱:</td>
											<td><input class="form-control" type="email" name="EMAIL" id="EMAIL" value="${pd.EMAIL }" maxlength="32" placeholder="这里输入邮箱" title="邮箱" onblur="hasE('${pd.USERNAME }')" style="width: 98%;" /></td>
										</tr>
										<tr>
                                                          <td style="width: 79px; text-align: right; padding-top: 13px;">是否允许外网访问:</td>
                                                          <td>
                                                                 <input type="radio" <c:if test="${ empty pd.isUrl || pd.isUrl == '0'}">checked="checked"</c:if>
                                                                 onchange="setIsUrl('0')" id="form-isDispute-radio1"
                                                                 name="form-isUrl-radio">
                                                                 <label for="form-isDispute-radio1"> 否</label>
                                                                 <input type="radio" <c:if test="${pd.isUrl == '1'}">checked="checked"</c:if>
                                                                 onchange="setIsUrl('1')" id="form-isDispute-radio2"
                                                                 name="form-isUrl-radio">
                                                                 <label for="form-isDispute-radio2"> 是 </label>
                                                            </td>
                                          </tr>
										<tr>
                                                          <td style="width: 79px; text-align: right; padding-top: 13px;">是否离职:</td>
                                                          <td>
                                                                 <input type="radio" <c:if test="${ empty pd.isQuit || pd.isQuit == '0'}">checked="checked"</c:if>
                                                                 onchange="setDispute('0')" id="form-isDispute-radio3"
                                                                 name="form-isDispute-radio">
                                                                 <label for="form-isDispute-radio1"> 否</label>
                                                                 <input type="radio" <c:if test="${pd.isQuit == '1'}">checked="checked"</c:if>
                                                                 onchange="setDispute('1')" id="form-isDispute-radio4"
                                                                 name="form-isDispute-radio">
                                                                 <label for="form-isDispute-radio2"> 是 </label>
                                                            </td>
                                          </tr>
                                          <tr class="quit"></tr>
										
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">个人优势:</td>
											<td><textarea class="form-control" style="width: 95%; height: 120px;" rows="5" cols="15" name="BZ" id="BZ" title="个人优势" maxlength="1000" placeholder="这里输入个人优势">${pd.BZ }</textarea></td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="10"><a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a> <a class="btn btn-mini btn-danger" onclick="yocto.closeChildLayer();"><i class="fa fa-remove"> 取消</a></td>
										</tr>
									</table>
								</div>
							</form>
			</div>
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/js.jsp"%>
	<!-- Prettyfile -->
    <script src="${pageContext.request.contextPath}/static/ui/js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
	<!-- 下拉框 -->
	<script src="${pageContext.request.contextPath}/static/ui/js/plugins/chosen/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script src="${pageContext.request.contextPath}/static/ace/js/jquery.colorbox.js"></script>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		if($("#user_id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
		
	 	var dispute='${pd.isQuit}'
           	if("1"==dispute){
           		setDispute(1);
           	}
           	
	});
	
	
	 function setIsUrl(value) {
        $("#isUrl").val(value);
	}
	
	 function setDispute(value) {
        $("#isQuit").val(value);
        if (value == 1) {
            $(".quit").append(
                '<td style="width: 79px; text-align: right; padding-top: 13px;">离职时间：</td>' +
              	'<td><input class="span10 date-picker form-control" data-validate="require" readonly="readonly" data-date-format="yyyy-mm-dd" type="text" placeholder="这里输入离职时间" name="quitTime" '+
                'id="quitTime" value="${fn:substring(pd.quitTime, 0, 10)}"></td>'
            )
            $('.date-picker').datepicker({
                autoclose: true,
                todayHighlight: true
            });
        } else {
            $(".quit").empty();
        }
    }
	
	//保存
	function save(){
		if($("#role_id").val()==""){
			$("#juese").tips({
				side:3,
	            msg:'选择角色',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#role_id").focus();
			return false;
		}
		if($("#loginname").val()=="" || $("#loginname").val()=="此用户名已存在!"){
			$("#loginname").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#loginname").focus();
			$("#loginname").val('');
			$("#loginname").css("background-color","white");
			return false;
		}else{
			$("#loginname").val(jQuery.trim($('#loginname').val()));
		}
		
		if($("#NUMBER").val()==""){
			$("#NUMBER").tips({
				side:3,
	            msg:'输入编号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#NUMBER").focus();
			return false;
		}else{
			$("#NUMBER").val($.trim($("#NUMBER").val()));
		}
		if($("#user_id").val()=="" && $("#password").val()==""){
			$("#password").tips({
				side:3,
	            msg:'输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#password").focus();
			return false;
		}
		if($("#password").val()!=$("#chkpwd").val()){
			
			$("#chkpwd").tips({
				side:3,
	            msg:'两次密码不相同',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#chkpwd").focus();
			return false;
		}
		if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'输入姓名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		
		 if (!$("#isQuit").val()) {
             setDispute('0');
         }
		 
		 if (!$("#isUrl").val()) {
			 setIsUrl('0');
         }
		/* var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
		if($("#PHONE").val()==""){
			
			$("#PHONE").tips({
				side:3,
	            msg:'输入手机号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#PHONE").focus();
			return false;
		}else if($("#PHONE").val().length != 11 && !myreg.test($("#PHONE").val())){
			$("#PHONE").tips({
				side:3,
	            msg:'手机号格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#PHONE").focus();
			return false;
		} */
		
		/* var qqreg = /^[1-9][0-9]{4,9}$/;
		if($("#QQ").val()==""){
			
			$("#QQ").tips({
				side:3,
	            msg:'输入qq号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#QQ").focus();
			return false;
		}else if($("#QQ").val().length > 10 && !qqreg.test($("#QQ").val())){
			$("#QQ").tips({
				side:3,
	            msg:'请输入正确的qq号码',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#QQ").focus();
			return false;
		}
		 */
		/* if($("#EMAIL").val()==""){
			$("#EMAIL").tips({
				side:3,
	            msg:'输入邮箱',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}else if(!ismail($("#EMAIL").val())){
			$("#EMAIL").tips({
				side:3,
	            msg:'邮箱格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		} */
		if($("#user_id").val()==""){
			hasU();
		}else{
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
		}
	//删除图片
	function delP(id) {
		if (confirm("确定要删除图片？")) {
			var url = "user/deltp.do?id=" + id + "&guid="
					+ new Date().getTime();
			$.get(url, function(data) {
				if (data == "success") {
					alert("删除成功!");
					document.location.reload();
				}
			});
		}
	}
	
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
	
	//过滤类型
	function fileType(obj) {
		var fileType = obj.value.substr(obj.value.lastIndexOf("."))
				.toLowerCase();//获得文件后缀名
		if (fileType != '.gif' && fileType != '.png' && fileType != '.jpg'
				&& fileType != '.jpeg') {
			$("#tp").tips({
				side : 3,
				msg : '请上传图片格式的文件',
				bg : '#AE81FF',
				time : 3
			});
			$("#tp").val('');
			document.getElementById("tp").files[0] = '请选择图片';
		}
	}
	
	//判断编码是否存在
	function hasN(USERNAME){
		var NUMBER = $.trim($("#NUMBER").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasN.do',
	    	data: {NUMBER:NUMBER,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#NUMBER").tips({
							side:3,
				            msg:'编号 '+NUMBER+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#NUMBER").val('');
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
		
			//初始化上传控件
			$( 'input[type="file"]' ).prettyFile();		
		
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
	});
</script>
</html>