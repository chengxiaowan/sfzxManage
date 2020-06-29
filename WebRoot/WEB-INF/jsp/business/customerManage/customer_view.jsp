<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="${pageContext.request.contextPath}/">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 下拉框 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/colorbox.css" />
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-content-inner">
			<div class="row">
				<div class="col-xs-12">
				<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a data-toggle="tab" href="#home">基本信息</a></li>
									<c:if test="${not empty pd.serviceAndManager}">
									<li><a data-toggle="tab" href="#attach" onclick="init_uploader()">相关附件</a></li>
									</c:if>
								</ul>
							<div class="ibox-content">
								<div class="tab-content">
									<div id="home" class="tab-pane in active">
											<div class="ibox-content">
										<form  class="form form-horizontal" >
										<input type="hidden" name="id" id="id" value="<c:out value="${pd.id}" default="0"></c:out>" />
										<div class="row">
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-md-4 col-sm-3 control-label">客户名称：</label>
														        <div class="col-md-8 col-sm-9">
														            <input class="form-control" data-validate="require|maxLength=50" type="text"   name="name" id="name"  value="${pd.name }" style="width: 98%" disabled="disabled">
														        </div>
														    </div>
														</div>
													<%-- 	<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-md-4 col-sm-3 control-label">手机号码：</label>
														        <div class="col-md-8 col-sm-9">
														            <input class="form-control" type="text" data-validate="mobile" placeholder="这里输入手机号码"  name="mobilePhone" id="mobilePhone"  value="${pd.mobilePhone }" style="width: 98%" disabled="disabled">
														        </div>
														    </div>
														</div>
														<hr /> --%>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-md-4 col-sm-3 control-label">所属省：</label>
														        <div class="col-md-8 col-sm-9">
														            <input class="form-control" type="text" data-validate="mobile" name="provinceName" id="provinceName"  value="${pd.provinceName }" style="width: 98%" disabled="disabled">
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-md-4 col-sm-3 control-label">所属市：</label>
														        <div class="col-md-8 col-sm-9">
														            <input class="form-control" type="text" data-validate="mobile"   name="cityName" id="cityName"  value="${pd.cityName }" style="width: 98%" disabled="disabled">
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-md-4 col-sm-3 control-label">公司地址：</label>
														        <div class="col-md-8 col-sm-9">
														            <input class="form-control" type="text"   name="companyAddress" id="companyAddress"  value="${pd.companyAddress }" style="width: 98%" disabled="disabled">
														        </div>
														    </div>
														</div>
														<%-- <div class="col-md-6">
														    <div class="form-group">
														        <label class="col-md-4 col-sm-3 control-label">银行名称：</label>
														        <div class="col-md-8 col-sm-9">
														            <input class="form-control" type="text" placeholder="这里输入银行名称"  name="bankAccountName" id="bankAccountName"  value="${pd.bankAccountName }" style="width: 98%" disabled="disabled">
														        </div>
														    </div>
														</div>
														<hr /> --%>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-md-4 col-sm-3 control-label">开户行：</label>
														        <div class="col-md-8 col-sm-9">
														            <input class="form-control" type="text"   name="bankName" id="bankName"  value="${pd.bankName }" style="width: 98%" disabled="disabled">
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-md-4 col-sm-3 control-label">开户行账号：</label>
														        <div class="col-md-8 col-sm-9">
														            <input class="form-control" type="text"   name="bankNumber" id="bankNumber"  value="${pd.bankNumber }" style="width: 98%" disabled="disabled">
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-md-4 col-sm-3 control-label">纳税人识别号：</label>
														        <div class="col-md-8 col-sm-9">
														            <input class="form-control" type="text"   name="taxIdentificationNumber" id="taxIdentificationNumber"  value="${pd.taxIdentificationNumber }" style="width: 98%" disabled="disabled">
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-md-4 col-sm-3 control-label">传真：</label>
														        <div class="col-md-8 col-sm-9">
														            <input class="form-control" type="text"   name="fax" id="fax"  value="${pd.fax }" style="width: 98%" disabled="disabled">
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-md-4 col-sm-3 control-label">邮政编码：</label>
														        <div class="col-md-8 col-sm-9">
														            <input class="form-control" type="text"  name="postCode"  id="postCode"  value="${pd.postCode }"  style="width: 98%" disabled="disabled">
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-md-4 col-sm-3 control-label">合同销售人：</label>
														        <div class="col-md-8 col-sm-9">
														            <input class="form-control" data-validate="require|maxLength=50" type="text"   name="name" id="name"  value="${pd.contractSaleName }" style="width: 98%" disabled="disabled">
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-md-4 col-sm-3 control-label">订单销售人：</label>
														        <div class="col-md-8 col-sm-9">
														            <input class="form-control" data-validate="require|maxLength=50" type="text"   name="name" id="name"  value="${pd.orderSaleName }" style="width: 98%" disabled="disabled">
														        </div>
														    </div>
														</div>
														<%-- <div class="col-md-6">
														    <div class="form-group">
														        <label class="col-md-4 col-sm-3 control-label">营业现状：</label>
														        <div class="col-md-8 col-sm-9">
														            <input class="form-control" type="text" placeholder="这里输入营业现状"  name="status"  id="status"  value="<c:if test="${pd.status == '0' }">营业中</c:if><c:if test="${pd.status == '1' }">停止/暂停营业</c:if><c:if test="${pd.status == '2' }">不详</c:if><c:if test="${pd.status == '3' }">债务主体破产/已注销</c:if>"  style="width: 98%" disabled="disabled">
														        </div>
														    </div>
														</div>
														<hr /> --%>
														<div class="col-md-12">
													<div class="form-group">
														<label  class="control-label col-md-2 col-sm-3 no-padding-right">
															备注:
														</label>
														<div class="col-md-10 col-sm-9">
														<textarea class="form-control" style="height: 120px;" rows="15" cols="10" name="remark" disabled="disabled" >${pd.remark}</textarea>
														</div>
													</div>
													</div>		
													<hr />
														<div class="col-md-12">
													<div class="form-group">
														<label  class="control-label col-md-2 col-sm-3 no-padding-right">
															联系人列表:
														</label>
														<div class="col-md-10 col-sm-9">
													<table class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th>联系人</th>
														<th>手机</th>
														<th>电话</th>
														<th>职务</th>
													</tr>
												</thead>
												<tbody>
													<c:if test="${not empty pd.linkmans }">
													<c:forEach items="${pd.linkmans }" var="linkman">
														<tr>
															<td>${linkman.name }</td>
															<td>${linkman.mobilePhone }</td>
															<td>${linkman.landline }</td>
															<td>${linkman.postion }</td>
														</tr>
													</c:forEach>
													</c:if>
												</tbody>
												</table>
												<c:if test="${empty pd.saleId }">
												<div><a onclick="addLinkMan()" class="btn btn-primary"><i class="fa fa-plus"></i> 新增联系人</a></div>
														</c:if>
														</div>
													</div>
													</div>	
												</div>
										</form>
								</div>
						</div>
						<!--/span-->
						
						<div id="attach" class="tab-pane">
												<div class="ibox">
													<div id="fileUp"></div>
												<div class="ibox-content">
													<div id="fileList" class="uploader-list">
													<table class="table table-bordered">
								                            <thead>
								                                <tr>
								                                    <th>文件名</th>
								                                    <th>文件类型</th>
								                                    <th>上传人</th>
								                                    <th>上传时间</th>
								                                    <th>操作</th>
								                                </tr>
								                            </thead>
								                            <tbody>
								                            	<c:if test="${not empty pd.attachs }">
								                            		<c:forEach items="${pd.attachs }"  var="attachs">
								 										<tr><td>${attachs.originalFilename }</td><td>${attachs.type }</td><td>${attachs.uploader }</td><td>${attachs.uploadTime }</td><td><a target="_blank" href="${attachs.url }" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a> <a class="btn btn-danger" onclick="list_del(this,'${attachs.id }')"><i class="fa fa-trash-o"></i> 删除</a><input type="hidden" name="fileSize" value="${attachs.fileSize }"><input type="hidden" name="uploader" value="${attachs.uploader }"><input type="hidden" name="originalFilename" value="${attachs.originalFilename }"><input type="hidden" name="uploadTime" value="${attachs.uploadTime }"><input type="hidden" name="realPath" value="${attachs.realPath }"><input type="hidden" name="url" value="${attachs.url }"><input type="hidden" name="attachId" value="${attachs.id }"></td></tr>                           		
								                            		</c:forEach>
								                            	</c:if>
								                            </tbody>
								                        </table>
													</div>
												</div>
												</div>
												</div>
					</div>
				</div>
				</div>
				
				
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
	<!-- ace scripts -->
	<!-- inline scripts related to this page -->
	<!--提示框-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.tips.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/myjs/yoValidate.js" type="text/javascript" charset="utf-8"></script>
	<!-- 下拉框 -->
	<!-- Prettyfile -->
	<script src="${pageContext.request.contextPath}/static/ui/js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
	<script src="${pageContext.request.contextPath}/static/ace/js/jquery.colorbox.js"></script>
</body>
<script type="text/javascript">
	function addLinkMan(){
		var index = layer.open({
			type : 2,
			title : '新增',
			content : '${pageContext.request.contextPath}/linkman/goAdd.do?customerId=${pd.id}&noCustomer=1',
			area : [ '90%', '90%' ]
		});
	}
	
	
	//删除附件
	function list_del(dom,id){
		$(dom).parents('tr').remove()
		$.post('${pageContext.request.contextPath}/customer/deleteAttach.do',{"id":id})
	}

</script>
</html>