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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<body class="gray-bg">
	<!-- /section:basics/navbar.layout -->
	<div class="ibox-content">
		<div class="row">
			<div class="col-xs-12">

				<form class="form" action="dictionaries/${msg }.do" name="Form" id="Form" method="post">
					<input type="hidden" name="DICTIONARIES_ID" id="DICTIONARIES_ID" value="${pd.DICTIONARIES_ID}" /> <input type="hidden" name="PARENT_ID" id="PARENT_ID"
						value="${null == pd.PARENT_ID ? DICTIONARIES_ID:pd.PARENT_ID}" />
					<div id="zhongxin">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width: 70px; text-align: right; padding-top: 13px;">上级:</td>
								<td>
									<div class="col-xs-4 label label-lg label-light arrowed-in arrowed-right">
										<b>${null == pds.NAME ?'(无) 此项为顶级':pds.NAME}</b>
									</div>
								</td>
							</tr>
							<tr>
								<td style="width: 70px; text-align: right; padding-top: 13px;">名称:</td>
								<td><input class="form-control" type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="50" placeholder="这里输入名称" title="名称" style="width: 98%;" /></td>
							</tr>
							<tr>
								<td style="width: 70px; text-align: right; padding-top: 13px;">英文:</td>
								<td><input class="form-control" type="text" name="NAME_EN" id="NAME_EN" value="${pd.NAME_EN}" maxlength="50" placeholder="这里输入英文" title="英文" style="width: 98%;" /></td>
							</tr>
							<tr>
								<td style="width: 70px; text-align: right; padding-top: 13px;">编码:</td>
								<td><input class="form-control" type="text" name="BIANMA" id="BIANMA" value="${pd.BIANMA}" maxlength="32" placeholder="这里输入编码 (不重复, 禁止修改)" title="编码" style="width: 76%;"
									<c:if test="${null != pd.BIANMA}">readonly="readonly"</c:if> <c:if test="${null == pd.BIANMA}">onblur="hasBianma();"</c:if> /></td>
							</tr>
							<tr>
								<td style="width: 70px; text-align: right; padding-top: 13px;">排序:</td>
								<td><input class="form-control" type="text" name="ORDER_BY" id="ORDER_BY" value="${pd.ORDER_BY}" maxlength="32" placeholder="这里输入排序" title="排序" onkeyup="clearNoNum(this)" /></td>
							</tr>
							<tr>
								<td style="width: 70px; text-align: right; padding-top: 13px;">备注:</td>
								<td><textarea class="form-control" rows="3" cols="46" name="BZ" id="BZ" placeholder="这里输入备注" title="备注" style="width: 98%;">${pd.BZ}</textarea></td>
							</tr>
							<!-- 										<tr> -->
							<!-- 											<td style="width: 70px; text-align: right; padding-top: 13px;">排查表:</td> -->
							<%-- 											<td><input type="text" name="TBSNAME" id="TBSNAME" value="${pd.TBSNAME}" maxlength="100" placeholder="这里输入表名, 多个用逗号分隔(非必录)" title="排查表" style="width: 98%;" /></td> --%>
							<!-- 										</tr> -->
							<!-- 										<tr> -->
							<!-- 											<td colspan="10" class="center"><p class="text-warning bigger-110 orange">排查表：删除此条数据时会去此表查询是否被占用(是:禁止删除)</p></td> -->
							<!-- 										</tr> -->
							<tr>
								<td class="text-center" colspan="10"><a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a> <a class="btn btn-mini btn-danger" onclick="yocto.closeChildLayer();"><i class="fa fa-remove"></i> 取消</a></td>
							</tr>
						</table>
					</div>


				</form>

			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</div>
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		function clearNoNum(obj) {
			obj.value = obj.value.replace(/[^\d.]/g, ""); //清除“数字”和“.”以外的字符  
			obj.value = obj.value.replace(/^\./g, ""); //验证第一个字符是数字而不是. 
			obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的.   
			obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
			// 		obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
		}
		
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
			if($("#ORDER_BY").val()==""){
				$("#ORDER_BY").tips({
					side:3,
		            msg:'请输入数字',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ORDER_BY").focus();
			return false;
		}
			
			$("#Form").submit();
/* 			$("#zhongxin").hide();
			$("#zhongxin2").show(); */
		}
		
		//判断编码是否存在
		function hasBianma(){
			var BIANMA = $.trim($("#BIANMA").val());
			if("" == BIANMA)return;
			$.ajax({
				type: "POST",
				url: '<%=basePath%>dictionaries/hasBianma.do',
				data : {
					BIANMA : BIANMA,
					tm : new Date().getTime()
				},
				dataType : 'json',
				cache : false,
				success : function(data) {
					if ("success" == data.result) {
					} else {
						$("#BIANMA").tips({
							side : 1,
							msg : '编码' + BIANMA + '已存在,重新输入',
							bg : '#AE81FF',
							time : 5
						});
						$('#BIANMA').val('');
					}
				}
			});
		}
	</script>
</body>
</html>