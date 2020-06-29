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
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/chosen.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/colorbox.css" />
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-content-inner">
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
					<div id="zhongxin" style="margin-top: 10px;">
						<div class="span6">
							<div class="tabbable">
								<!--<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a data-toggle="tab" href="#home"><i class="green icon-home bigger-110"></i> 基本信息</a></li>
								</ul>-->
								<div class="tab-content">
									<div id="home" class="tab-pane in active">
										<form action="linkman/${msg }.do" name="linkmanForm" id="linkmanForm" method="post"  class="form form-horizontal" >
										<input type="hidden" name="id" id="id" value="<c:out value="${pd.id}" default="0"></c:out>" />
										<div class="row">
												<div id="zhongxin" style="padding-top: 13px;">
													<div class="col-md-12">
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">联系人名称：</label>
														        <div class="col-sm-9">
														            <input class="form-control" data-validate="require|maxLength=50" type="text" readonly="readonly"  name="name" id="name"  value="${pd.name }" style="width: 98%">
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														<div class="form-group">
														        <label class="col-sm-3 control-label">客户名称：</label>
														        <div class="col-sm-9">
														            <input class="form-control" data-validate="require|maxLength=50" readonly="readonly" type="text"  style="width: 98%"   value="${pd.companyName }"  >
														        </div>
														   </div>
														   </div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">手机号码：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" data-validate="mobile" readonly="readonly"  name="mobilePhone" id="mobilePhone"  value="${pd.mobilePhone }" style="width: 98%">
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">固定电话：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" readonly="readonly"  name="landline" id="landline"  value="${pd.landline }" style="width: 98%">
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">邮箱：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" readonly="readonly"  name="email" id="email"  value="${pd.email }" style="width: 98%">
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">职务：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" readonly="readonly"  name="postion" id="postion"  value="${pd.postion }" style="width: 98%">
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">传真：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" readonly="readonly"  name="fax" id="fax"  value="${pd.fax }" style="width: 98%">
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">合同销售人：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text"  disabled="disabled" name="fax" id="fax"  value="${pd.contractSaleName }"  style="width: 98%">
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">订单销售人：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" disabled="disabled"   name="fax" id="fax"  value="${pd.orderSaleName }" style="width: 98%" >
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-12">
													<div class="form-group">
														<label  class="control-label col-sm-2 no-padding-right" style="width: 11.66666667%;">
															备注:
														</label>
														<div class="col-sm-10" style="width: 88.33333333%;">
														<textarea class="form-control" style=" height: 180px;resize: none;width: 98%;margin-left: 10px;" rows="15" cols="10" name="remark"  readonly="readonly">${pd.remark}</textarea>
														</div>
													</div>	
													</div>
													</div>	
												</div>
												</div>
										</form>
									</div>
								</div>
							</div>
						</div>
						<!--/span-->
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	</div>
	<%@ include file="../../system/index/js.jsp"%>
</body>
</html>