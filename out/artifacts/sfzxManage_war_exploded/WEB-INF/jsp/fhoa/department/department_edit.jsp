<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
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
						<div class="col-xs-12">

							<form action="department/${msg }.do" name="Form" id="Form" method="post">
								<input type="hidden" name="DEPARTMENT_ID" id="DEPARTMENT_ID" value="${pd.DEPARTMENT_ID}" /> <input type="hidden" name="PARENT_ID" id="PARENT_ID"
									value="${null == pd.PARENT_ID ? DEPARTMENT_ID:pd.PARENT_ID}" />
								<div id="zhongxin">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">上级:</td>
											<td>
												<div class="col-xs-4 label label-lg label-light arrowed-in arrowed-right">
													<b>${null == pds.NAME ?'(无) 此部门为顶级':pds.NAME}</b>
												</div>
											</td>
										</tr>
										<tr>
											<td style="width: 70px; text-align: right; padding-top: 13px;">名称:</td>
											<td><input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="50" placeholder="这里输入名称" title="名称" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="width: 70px; text-align: right; padding-top: 13px;">英文:</td>
											<td><input type="text" name="NAME_EN" id="NAME_EN" value="${pd.NAME_EN}" maxlength="50" placeholder="这里输入英文" title="英文" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="width: 70px; text-align: right; padding-top: 13px;">负责人:</td>
											<td><input type="text" name="HEADMAN" id="HEADMAN" value="${pd.HEADMAN}" maxlength="32" placeholder="这里输入负责人" title="负责人" style="width: 66%;" /></td>
										</tr>
										<tr>
											<td style="width: 70px; text-align: right; padding-top: 13px;">电话:</td>
											<td><input type="text" name="TEL" id="TEL" value="${pd.TEL}" maxlength="32" placeholder="这里输入电话" title="电话" style="width: 66%;" /></td>
										</tr>
										<tr>
											<td style="width: 70px; text-align: right; padding-top: 13px;">部门职能:</td>
											<td><input type="text" name="FUNCTIONS" id="FUNCTIONS" value="${pd.FUNCTIONS}" maxlength="32" placeholder="这里输入部门职能" title="部门职能" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="width: 70px; text-align: right; padding-top: 13px;">地址:</td>
											<td><input type="text" name="ADDRESS" id="ADDRESS" value="${pd.ADDRESS}" maxlength="32" placeholder="这里输入地址" title="地址" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="width: 70px; text-align: right; padding-top: 13px;">备注:</td>
											<td><textarea rows="3" cols="46" name="BZ" id="BZ" placeholder="这里输入备注" title="备注" style="width: 98%;">${pd.BZ}</textarea></td>
										</tr>
										<tr>
											<td class="text-center" colspan="10"><a class="btn btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a></td>
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
	</div>
	<!-- /.main-container -->

	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		//保存
		function save(){
			if($("#NAME").val()==""){
				$("#NAME").tips({
					side:3,
		            msg:'请输入名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NAME").focus();
			return false;
		}
			if($("#NAME_EN").val()==""){
				$("#NAME_EN").tips({
					side:3,
		            msg:'请输入英文',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NAME_EN").focus();
			return false;
		}
			if($("#BIANMA").val()==""){
				$("#BIANMA").tips({
					side:3,
		            msg:'请输入编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BIANMA").focus();
			return false;
		}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
		}
		</script>
</body>
</html>