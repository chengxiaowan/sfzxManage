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
</head>
<body class="no-skin" style="display:none">

	<!-- /section:basics/navbar.layout -->
	<div class="main-content-inner">
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
							<div class="tabbable">
								<div class="ibox-content">
										<form action="express/${msg }.do" name="contractForm" id="contractForm" method="post"  class="form form-horizontal" >
										<input disabled="disabled" type="hidden" name="id" id="id" value="<c:out value="${pd.id}" default="0"></c:out>" />
										<div class="row">
														<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-2 control-label">目标单位：</label>
														        <div class="col-sm-8">
														            <input disabled="disabled" class="form-control" type="text" data-validate="require"  name="target" id="target"  value="${pd.target }" >
														        </div>
														    </div>
														</div>
														<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-2 control-label">寄件内容：</label>
														        <div class="col-sm-8">
														            <input disabled="disabled" class="form-control" type="text" data-validate="require"  name="jjnr" id="jjnr"  value="${pd.jjnr }" >
														        </div>
														    </div>
														</div>
														<div class="col-md-12 kdff">
														    <div class="form-group">
                                                                        <label class="col-sm-2 control-label">发件方式：</label>
                                                                        <div class="col-sm-8">
                                                                            <select disabled="disabled" class="chosen-select form-control" data-validate="require" name="sendway" id="sendway" 
                                                                                style="vertical-align: top;" title="发件方式"
                                                                                style="width:98%;" onchange="selectE()">
																		<option value="">请选择</option>
																		<option value="0" <c:if test="${pd.sendway == 0 }">selected</c:if>>快递</option>
																		<option value="1" <c:if test="${pd.sendway == 1 }">selected</c:if>>上门自取</option>
																		<option value="2" <c:if test="${pd.sendway == 2 }">selected</c:if>>代发</option>
															</select>
                                                                        </div>
                                                                    </div>
														</div>
															<div class="expressTypeBox-hook">
															<c:if test="${pd.sendway ==0 }">
															<c:if test="${not empty pd.expressCom }">
																<div class="col-md-12" >
																     <div class="form-group">
																        <label class="col-sm-2 control-label">快递公司：</label>
																        <div class="col-sm-8">
																            <input class="form-control" disabled="disabled" data-validate="maxLength=100" type="text" name="expressCom" value="${pd.expressCom }"  >
																        </div>
																    </div>
																    </div>
																	<div class="col-md-12" >
																     <div class="form-group">
																        <label class="col-sm-2 control-label">快递单号：</label>
																        <div class="col-sm-8">
																            <input disabled="disabled" class="form-control" disabled="disabled" data-validate="require|maxLength=100" type="text"  name="expressNo" value="${pd.expressNo }"  >
																        </div>
																    </div>
																    </div>
																    </c:if>
																    </c:if>
																    <div class="col-md-12" >
																	    <div class="form-group">
																	        <label class="col-sm-2 control-label">发件类型：</label>
																	        <div class="col-sm-8">
																	        <select class="form-control" data-validate="require" disabled="disabled" name="expressType" onchange="showOtherTypeBox(this)" >
																	       			<option value="1" <c:if test="${pd.expressType == 1 }">selected</c:if>>询证函</option>
																	       			<option value="2" <c:if test="${pd.expressType == 2 }">selected</c:if>>催款函</option>
																	       			<option value="3" <c:if test="${pd.expressType == 3 }">selected</c:if>>律师函</option>
																	       			<option value="4" <c:if test="${pd.expressType == 4 }">selected</c:if>>合同</option>
																	       			<option value="5" <c:if test="${pd.expressType == 5 }">selected</c:if>>发票</option>
																	       			<option value="6" <c:if test="${pd.expressType == 6 }">selected</c:if>>其他</option>
																	        </select>
																	        </div>
																	    </div>
																	</div>
																	</div>
																	<c:if test="${pd.expressType == 6 }">
																	<div class="otherTypeBox-hook">
																		<div class="col-md-12" >
																	     <div class="form-group">
																	        <label class="col-sm-2 control-label">具体类型：</label>
																	        <div class="col-sm-8">
																	            <input disabled="disabled" class="form-control" disabled="disabled"  name="typeDetail"  data-validate="require|maxLength=100" type="text" placeholder="这里输入具体类型"  value="${pd.typeDetail }"  >
																	        </div>
																	    </div>
																	    </div>
																	</div>
																	</c:if>
														
															<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-2 control-label">发件单位 ：</label>
														        <div class="col-sm-8">
														            <input disabled="disabled" class="form-control" type="text" data-validate="require"  name="fjdw" id="fjdw"  value="${pd.fjdw }" >
														        </div>
														    </div>
															</div>
															
															<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-2 control-label">发件人 ：</label>
														        <div class="col-sm-8">
													            <input disabled="disabled" class="form-control" type="text" data-validate="require"  name="fjr" id="fjr"  value="${pd.fjr }" >
														        </div>
														    </div>
															</div>
															
															<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-2 control-label">单位地址 ：</label>
														        <div class="col-sm-8">
														            <input disabled="disabled" class="form-control" type="text" data-validate="require"  name="dwdz" id="dwdz"  value="${pd.dwdz }" >
														        </div>
														    </div>
															</div>
															
															<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-2 control-label">联系方式 ：</label>
														        <div class="col-sm-8">
														            <input disabled="disabled" class="form-control" type="text" data-validate="require"   name="lxfs" id="lxfs"  value="${pd.lxfs }" >
														        </div>
														    </div>
															</div>
															
															<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-2 control-label">发件时间：</label>
														        <div class="col-sm-8">
														            <input disabled="disabled" class="span10 date-picker form-control" readonly="readonly" data-validate="require|maxLength=50" type="text"  data-date-format="yyyy-mm-dd" name="sendTime" id="sendTime"  value="${fn:substring(pd.sendTime,0,10) }"  >
														        </div>
														    </div>
															</div>
															
														<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-2 control-label">收件人：</label>
														        <div class="col-sm-8">
														            <table class="table table-bordered" id="recivemanList">
														            <thead>
														            <tr>
														            <th>姓名</th>
																<th>电话</th>
																<th>地址</th>
																</tr>
														            </thead>
															<tbody>
																<c:if test="${not empty pd.links }">
																	<c:forEach items="${pd.links }" var="linkman1">
																		<tr
																			data-params='{"id":${linkman1.id },"name":"${empty linkman1.name?" ":linkman1.name }","phone":"${empty linkman1.mobilePhone?"":linkman1.mobilePhone}","address":"${empty linkman1.address?"":linkman1.address }"}'>
																			<td>
																			${linkman1.name}</td>
																			<td>${linkman1.mobilePhone}</td>
																			<td>${linkman1.address}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
														<input disabled="disabled" type="hidden" name="recieveValue" id="recieveValue" value="${ pd.recieveValue}" />
														<input disabled="disabled" type="hidden" name="orecieveValue"  value="${ pd.recieveValue}" />
														        </div>
														    </div>
														    
														</div>
														<c:if test="${pd.flag==5 }">
														<div id="hasRevieved">
														<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-2 control-label">收件时间：</label>
														        <div class="col-sm-8">
														            <input disabled="disabled" class="span10 date-picker form-control" data-validate="require|maxLength=50" type="text" placeholder="这里输入收件时间"  readonly="readonly"  name="reciveTime" id="reciveTime"  data-date-format="yyyy-mm-dd" value="${fn:substring(pd.reciveTime,0,10) }"  >
														        </div>
														    </div>
														</div>
													
														<div class="col-md-12">
													<div class="form-group">
														<label  class="control-label col-sm-2">
															收件情况:
														</label>
														<div class="col-sm-8">
														<textarea class="form-control" disabled="disabled" style="height: 120px;"  name="status"  placeholder="这里输入客户收件情况">${pd.status}</textarea>
														</div>
													</div>	
													</div>		
													</div>
													</c:if>											
												<div class="col-md-12">
													<div class="form-group">
														<label  class="control-label col-sm-2">
															相关附件:
														</label>
														<div class="col-sm-8">
														<div id="fileUp"></div>
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
								 										<tr><td>${attachs.originalFilename }</td> <td>快递</td><td>${attachs.uploader }</td><td>${attachs.uploadTime }</td><td><a target="_blank" href="${attachs.url }" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a></td></tr>                           		
								                            		</c:forEach>
								                            	</c:if>
								                            </tbody>
								                        </table>
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
					</div>
				</div>
				<!-- /.col -->
		
		
		<div class="ibox-content" id="new-float-box1" style="overflow: hidden; display: none">
		<div class="col-md-12">
				<form name="receiverForm" id="receiverForm" method="post"  class="form form-horizontal" >
										<div class="row">
														<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">姓名<font color="red">*</font>：</label>
														        <div class="col-sm-8">
														            <input disabled="disabled" class="form-control" data-validate="require|maxLength=50" type="text" placeholder="这里输入联系人名称"  name="name" id="name"  value=""  >
														        </div>
														    </div>
														</div>
														<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">电话号码：</label>
														        <div class="col-sm-8">
														            <input disabled="disabled" class="form-control" type="text" placeholder="这里输入电话号码"  name="phone" id="phone"  value=""  >
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">地址：</label>
														        <div class="col-sm-8">
														            <input disabled="disabled" class="form-control" type="text" placeholder="这里输入地址"  name="address" id="address"   value=""  >
														        </div>
														    </div>
														</div>
												</div>
										</form>
		</div>
	</div>
		
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
	<script type="text/javascript">
	//如果是从主页查看,不需要收件时间
	if(location.href.indexOf("#taskViewer")>0){
		$("#hasRevieved").remove()
	}
	$("body").show();
	</script>
</body>
</html>