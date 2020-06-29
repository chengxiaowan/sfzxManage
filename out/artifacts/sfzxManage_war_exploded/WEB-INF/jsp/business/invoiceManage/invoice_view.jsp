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
					<div class="tab-content">
						<div id="home" class="tab-pane in active">
							<div class="ibox-content">
								<form class="form form-horizontal">
									<div class="row">
										<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">客户名称：<font color="red">*</font>：
													</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" value="${pd.customerName }"   readonly="readonly">
													</div>
												</div>
											</div>
											
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">纳税人识别号：</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" value="${pd.taxIdentificationNumber }"   readonly="readonly">
													</div>
												</div>
											</div>
												<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">开户行：</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" value="${pd.bankName }"   readonly="readonly">
													</div>
												</div>
											</div>
												<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">银行账号：</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" value="${pd.bankNumber }"   readonly="readonly">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">省：</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" value="${pd.provinceName }"   readonly="readonly">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">市：</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" value="${pd.cityName }"   readonly="readonly">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">具体地址：</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" value="${pd.adress }"   readonly="readonly">
													</div>
												</div>
											</div>
												<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">电话：</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" value="${pd.fax }"   readonly="readonly">
													</div>
												</div>
											</div>
											
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">本次回款时间：</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" value="${pd.currentTime }"   readonly="readonly">
													</div>
												</div>
											</div>
											<hr />
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-md-4 col-sm-3 control-label">发票号码：</label>
												<div class="col-md-8 col-sm-9">
													<input class="form-control" type="text" value="${pd.invoiceCode }" style="width: 98%" disabled="disabled">
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-md-4 col-sm-3 control-label">开票内容：</label>
												<div class="col-md-8 col-sm-9">
													<input class="form-control" type="text" value="${pd.invoiceContent }" style="width: 98%" disabled="disabled">
												</div>
											</div>
										</div>
										<hr />
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-md-4 col-sm-3 control-label">开票日期：</label>
												<div class="col-md-8 col-sm-9">
													<input class="form-control" type="text" value="${pd.invoiceTime }" style="width: 98%" disabled="disabled">
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">票据类型：</label>
												<div class="col-sm-8">
														<div class="radio radio-success radio-inline">
															<input type="radio" disabled <c:if test="${ empty pd.invoiceType || pd.invoiceType == '0'}">checked="checked"</c:if> onchange="setinvoiceType('0')" id="form-isinvoiceType-radio1"
																name="form-isinvoiceType-radio"> <label for="form-isinvoiceType-radio1">增值税发票 </label>
														</div>
														<div class="radio radio-success radio-inline">
															<input type="radio" disabled <c:if test="${pd.invoiceType == '1'}">checked="checked"</c:if> onchange="setinvoiceType('1')" id="form-isinvoiceType-radio2" name="form-isinvoiceType-radio">
															<label for="form-isinvoiceType-radio2">普通发票</label>
														</div>
													</div>
											</div>
										</div>
										<hr />
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-md-4 col-sm-3 control-label">票据金额：</label>
												<div class="col-md-8 col-sm-9">
													<input class="form-control" type="text" value="<c:if test="${not empty pd.invoiceCode}">${pd.money }</c:if>" style="width: 98%" disabled="disabled">
												</div>
											</div>
										</div>
										<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">是否含税：</label>
													<div class="col-sm-8">
														<div class="radio radio-success radio-inline">
															<input type="radio" disabled <c:if test="${ empty pd.isTax || pd.isTax == '0'}">checked="checked"</c:if> onchange="setisTax('0')" id="form-isisTax-radio1" name="form-isisTax-radio"> <label
																for="form-isisTax-radio1">是</label>
														</div>
														<div class="radio radio-success radio-inline">
															<input type="radio" disabled <c:if test="${pd.isTax == '1'}">checked="checked"</c:if> onchange="setisTax('1')" id="form-isisTax-radio2" name="form-isisTax-radio"> <label
																for="form-isisTax-radio2">否</label>
														</div>
													</div>
												</div>
											</div>
										<hr />
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-md-4 col-sm-3 control-label">经手人：</label>
												<div class="col-md-8 col-sm-9">
													<input class="form-control" type="text" value="${pd.brokerage }" style="width: 98%" disabled="disabled">
												</div>
											</div>
										</div>
										<hr />
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2 col-sm-3 no-padding-right"> 备注: </label>
												<div class="col-md-10 col-sm-9">
													<textarea class="form-control" style="height: 120px;" rows="15" cols="10" name="remark" disabled="disabled">${pd.remark}</textarea>
												</div>
											</div>
										</div>
										<div class="col-md-12">
													<div class="form-group">
														<label  class="control-label col-sm-2">
															相关附件:
														</label>
														<div class="col-sm-10">
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
								 										<tr><td>${attachs.originalFilename }</td> <td>发票</td><td>${attachs.uploader }</td><td>${attachs.uploadTime }</td><td><a target="_blank" href="${attachs.url }" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a><input type="hidden" name="fileSize" value="${attachs.fileSize }"><input type="hidden" name="uploader" value="${attachs.uploader }"><input type="hidden" name="originalFilename" value="${attachs.originalFilename }"><input type="hidden" name="uploadTime" value="${attachs.uploadTime }"><input type="hidden" name="realPath" value="${attachs.realPath }"><input type="hidden" name="url" value="${attachs.url }"><input type="hidden" name="attachId" value="${attachs.id }"></td></tr>                           		
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
						<!--/span-->
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	</div>
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
</body>
</html>