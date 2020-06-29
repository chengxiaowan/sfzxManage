<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
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
<base href="${pageContext.request.contextPath}/">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12" style="padding-top: 20px"">
							<form action="aboutUs/${msg}.do" name="aboutUsForm" id="aboutUsForm" method="post" class="form-horizontal">
								<input type="hidden" name="id" id="id" value="<c:out value="${pd.id}" default="0"></c:out>" /> <input type="hidden" name="type" id="type"
									value="<c:out value="${pd.type}" default="0"></c:out>" />
								<div class="form-group">
									<div class="col-md-12">
									    <div class="form-group">
								        <label class="col-sm-3 control-label">原密码<font color="red">*</font>：</label>
								        <div class="col-sm-7">
								            <input class="form-control" data-validate="require|maxLength=50"  type="password" placeholder="请填写原密码"  name="originalPassword" id="originalPassword"  value="${pd.originalPassword }"  >
								        </div>
								    </div>
								    </div>
								    <div class="col-md-12">
									    <div class="form-group">
								        <label class="col-sm-3 control-label">新密码<font color="red">*</font>：</label>
								        <div class="col-sm-7">
								            <input class="form-control" data-validate="require|maxLength=50"  type="password" placeholder="请填写新密码"  name="password" id="password"  value=""  >
								        </div>
								    </div>
							    	</div>
							    	<div class="col-md-12">
									    <div class="form-group">
								        <label class="col-sm-3 control-label">确认密码<font color="red">*</font>：</label>
								        <div class="col-sm-7">
								            <input class="form-control" data-validate="require|maxLength=50"  type="password" placeholder="请填写确认密码"  name="confirmPassword" id="confirmPassword"  value=""  >
								        </div>
								    </div>
							    	</div>
								</div>
								<div class="clearfix form-actions">
									<div class="col-md-offset-1 col-md-10 text-center">
										<a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a>
									</div>
								</div>
								<div class="hr hr-18 dotted hr-double"></div>
							</form>
						</div>
						<!--/span-->
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content -->
	</div>

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.tips.js"></script>
	<!-- 百度富文本编辑框-->
	<script type="text/javascript" charset="utf-8">
		window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";
	</script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugins/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/plugins/ueditor/ueditor.all.js"></script>
	<script type="text/javascript" charset="utf-8">
		var ue = UE.getEditor('content', {
			toolbars : [
					[ 'fullscreen', 'source', 'undo', 'redo', 'preview' ],
					[ 'bold', 'italic', 'underline', 'fontborder',
							'strikethrough', 'superscript', 'subscript',
							'removeformat', 'formatmatch', 'autotypeset',
							'fontfamily', 'forecolor', 'fontsize',
							'insertvideo', 'simpleupload', 'insertimage',
							'emotion', 'spechars', 'pasteplain', '|',
							'backcolor', 'insertorderedlist',
							'insertunorderedlist', 'selectall', 'cleardoc' ] ],
			autoHeightEnabled : true,
			autoFloatEnabled : true,
			wordCount : true,
			maximumWords : 2000
		});
	</script>
	<!-- 百度富文本编辑框-->
</body>
<script type="text/javascript">
	//保存
	function save() {
		
		if (yoValidate('#aboutUsForm')) {
		if($("#password").val()!=$("#confirmPassword").val()){
			layer.msg("密码与确认密码不一致");
			return
		}
		$.ajax({
			cache : true,
			type : "POST",
			url : '${pageContext.request.contextPath}/aboutUs/edit.do',
			data : $('#aboutUsForm').serialize(),// 你的formid
			async : false,
			success : function(data) {
				if (data.msg == "success") {
					setTimeout(function(){
						location.reload()
					}, 2000);
					layer.msg("保存成功");
				} else if(data.msg == "error1"){
					layer.msg("原密码不正确");
				}else{
					layer.msg("保存失败");
				}
			}
		});
		}
	}
</script>
</html>