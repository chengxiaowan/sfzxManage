<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
</head>
<body class="gray-bg">
	<!-- /section:basics/navbar.layout -->
				<div class="ibox-content">
					<div class="row">
						<div class="col-xs-12">
							<form action="role/${msg}.do" name="form1" id="form1" method="post">
								<input type="hidden" name="ROLE_ID" id="id" value="${pd.ROLE_ID}" /> <input name="PARENT_ID" id="parent_id" value="${pd.parent_id }" type="hidden">
							<div class="form-group">
											<input class="form-control" type="text" name="ROLE_NAME" id="roleName" placeholder="这里输入名称" value="${pd.ROLE_NAME}" title="名称" style="width: 99%;" /></td>
								</div>
								<a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a> <a class="btn btn-mini btn-danger" onclick="yocto.closeChildLayer()"><i class="fa fa-remove"></i> 取消</a>
									
									</table>
								</div>
							</form>
							<div id="zhongxin2" class="center" style="display: none">
								<img src="static/images/jzx.gif" style="width: 50px;" /><br />
								<h4 class="lighter block green"></h4>
							</div>

						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../index/js.jsp"%>
	<!--提示框-->
	<script type="text/javascript">
	//保存
	function save(){
		if($("#roleName").val()==""){
			layer.yoTips('请输入','#roleName',{tips:3})
			$("#roleName").focus();
			return false;
		}
		layer.load()
			$("#form1").submit();
	}
	
	
	</script>
</body>
</html>

