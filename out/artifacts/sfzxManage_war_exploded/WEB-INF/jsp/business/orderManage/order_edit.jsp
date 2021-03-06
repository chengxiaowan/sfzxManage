<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

<head>
<base href="${pageContext.request.contextPath}/">
<%@ include file="../../system/index/top.jsp"%>
<!-- 下拉框 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/colorbox.css" />
<!-- webuploader上传插件js -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/ui/css/plugins/webuploader/webuploader.css">
</head>
<style>
	.active{
		background: #FFFFFF!important;
	}
	li.active a{
		color: #459df5!important;
		border-top: 2px solid #459df5!important;
	}
</style>
<body style="display:none">
	<div class="wrapper wrapper-content" style="padding-bottom:60px;">
	<div class="topNav">
					<ul class="nav nav-tabs nav-wrap" id="myTab">
						<li id="info" class="active"><a data-toggle="tab" href="#tab1">案件信息</a></li>
						<li id="zwr"><a data-toggle="tab" href="#tab2">债务人信息</a></li>
						<li id="zqr"><a data-toggle="tab" href="#tab3">债权人信息</a></li>
						<li id="htxx"><a data-toggle="tab" href="#tab5">合同信息</a></li>
						<li id="attach"><a data-toggle="tab" href="#tab4">相关附件</a></li>
					</ul>
					</div>
					<form action="order/${msg }.do" name="contractForm" id="contractForm" method="post" class="form form-horizontal">
						<input type="hidden" name="id" id="id" value="<c:out value=" ${pd.id} " default="0 "></c:out>" />
						<c:if test="${isYunzuo}">
							<input type="hidden" name="customerId" id="customerId" value="${pd.customerId}" />
						</c:if>
						<input type="hidden" name="origCustomerId" id="origCustomerId" value="${pd.customerId}" />
						<input type="hidden" name="status" id="status" value="${pd.status}" />
						<input type="hidden" name="saleId" id="saleId" value="${pd.saleId}" />
						<input type="hidden" name="debtorId" id="debtorId" value="${pd.debtorId}" />
						<%-- <input type="hidden" name="userId" id="userId" value="${pd.saleId}" /> --%>
						<input type="hidden" name="isDispute" id="isDispute" value="${pd.isDispute}" /> <input type="hidden" name="linkId1" id="linkId1" value="${pd.linkId1}" /> <input type="hidden" name="linkId2"
							id="linkId2" value="${pd.linkId2}" /> <input type="hidden" name="linkId3" id="linkId3" value="${pd.linkId3}" /> <input type="hidden" name="linkId4" id="linkId4" value="${pd.linkId4}" /> <input
							type="hidden" name="origLinkId1" id="origLinkId1" value="${pd.linkId1}" /> <input type="hidden" name="origLinkId2" id="origLinkId2" value="${pd.linkId2}" /> <input type="hidden"
							name="origLinkId3" id="origLinkId3" value="${pd.linkId3}" /> <input type="hidden" name="origLinkId4" id="origLinkId4" value="${pd.linkId4}" /> <input type="hidden" name="orederAttachType"
							id="orederAttachType" value="${pd.orederAttachType}" />
						<div class="tab-content">
						<div class="mark panel panel-primary" id="tab1" style="margin-top: 10px;">
									<div class="panel-heading">案件信息</div>
									<div class="panel-body">
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">案件编号&nbsp;<font color="red">*</font>：
												</label>
												<div class="col-sm-8">
													<input class="form-control" required type="text" placeholder="这里输入案件编号" name="orderNo" id="orderNo" value="${pd.orderNo }">
												</div>
											</div>
										</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">案件类型&nbsp;<font color="red">*</font>：
													</label>
													<div class="col-sm-8">
														<select class="chosen-select form-control" required	<c:if test="${ not empty pd.id }">  </c:if> name="type" id="type" data-placeholder="请选择案件类型" style="vertical-align: top;" 
															onchange="selectE()">
															<option value="">请选择</option>
															<option value="0" <c:if test="${pd.type == '0' }">selected</c:if>>非诉讼</option>
															<option value="1" <c:if test="${pd.type == '1' }">selected</c:if>>诉讼</option>
															<option value="2" <c:if test="${pd.type == '2' }">selected</c:if>>仲裁</option>
														</select>
													</div>
												</div>
											</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">委托时间&nbsp;<font color="red">*</font>：
												</label>
												<div class="col-sm-8">
													<input class="span10 date-picker form-control" required readonly="readonly" data-date-format="yyyy-mm-dd" type="text" placeholder="这里输入委托时间" name="orderPlacement"
														value="${pd.orderPlacement }">
												</div>
											</div>
										</div>
										<%-- 
												<div class="col-md-6">
													<div class="form-group">
														<label class="col-sm-4 control-label">相关合同&nbsp;<font color="red">*</font>：
														</label>
														<div class="col-sm-8">
															<select class="form-control chosen-select-contract" required name="contractId" id="contractId">
																<c:if test="${not empty pd.contractId }">
																	<option value="${pd.contractId }">${pd.contractNo }</option>
																</c:if>
															</select>
														</div>
													</div>
												</div> --%>
												<div class="col-md-6">
													<div class="form-group">
														<label class="col-sm-4 control-label">欠款总金额(元)&nbsp;<font color="red">*</font>：
														</label>
														<div class="col-sm-8">
															<input class="form-control" required number="true" required type="text" placeholder="这里输入欠款总金额" name="debtAmount" id="debtAmount" value="${pd.debtAmount }">
														</div>
													</div>
												</div>
												<div class="clearfix"></div>
											
										
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">最近一次付款时间：
												</label>
												<div class="col-sm-8">
													<input class="form-control"   type="text" placeholder="这里输入最近一次付款时间" name="lastPaymentDate"
														id="lastPaymentDate" value="${pd.lastPaymentDate }">
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">账龄(月)&nbsp;<font color="red">*</font>：
												</label>
												<div class="col-sm-8">
													<input class="form-control" required type="text" placeholder="这里输入账龄" name="ageOfDebt" id="ageOfDebt" value="${pd.ageOfDebt }">
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">债务性质类型&nbsp;<font color="red">*</font>：
												</label>
												<div class="col-sm-8">
													<select class="chosen-select form-control" required name="debtType" id="debtType" data-placeholder="请选择债务人营业现状" style="vertical-align: top;" onchange="selectE()">
														<option value="">请选择</option>
														<option value="0" <c:if test="${pd.debtType == 0 }">selected</c:if>>产品交易欠款</option>
														<option value="1" <c:if test="${pd.debtType == 1 }">selected</c:if>>信用保险索赔欠款</option>
														<option value="2" <c:if test="${pd.debtType == 2 }">selected</c:if>>资金借贷欠款</option>
														<option value="3" <c:if test="${pd.debtType == 3 }">selected</c:if>>其他类型</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">佣金类型&nbsp;<font color="red">*</font>：
												</label>
												<div class="col-sm-8">
												<select class="form-control" required id="commType" name="cType">
													<option value="0" <c:if test="${pd.cType == 0 }"> selected </c:if> >佣金比例</option>
													<option value="1" <c:if test="${pd.cType == 1 }"> selected </c:if>>固定金额</option>
													</select>
												</div>
											</div>
										</div>
										<div id="commType0">
										<c:if test="${not empty pd.id }">
										<c:if test="${pd.status==0 }">
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">佣金比例(%)&nbsp;<font color="red">*</font>：
												</label>
												<div class="col-sm-8">
													<input class="form-control" required type="text" placeholder="这里输入非诉讼佣金比例" name="commissionRate" id="" value="${pd.commissionRate }" onkeyup = "value=value.replace(/[^\d]/g,'')">
												</div>
											</div>
										</div><div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">佣金比例折扣：<br>(1-100)&nbsp;&nbsp;&nbsp;&nbsp;
												</label>
												<div class="col-sm-8">
													<input class="form-control" type="text" range="1,100" placeholder="这里输入非诉讼佣金比例折扣" name="discount" id="" value="${pd.discount }">
												</div>
											</div>
										</div>
										</c:if>
										
										<c:if test="${pd.status==3||pd.status==4 }">
										<input type="hidden" value="${pd.commissionRate }" name="commissionRate" />
										<input type="hidden" value="${pd.discount }" name="discount" />
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">佣金比例(%)&nbsp;<font color="red">*</font>：
												</label>
												<div class="col-sm-8">
													<input class="form-control" required type="text" placeholder="这里输入诉讼仲裁佣金比例" name="lawCommissionRate" id="" value="${not empty pd.lawCommissionRate?pd.lawCommissionRate:pd.commissionRate }" onkeyup = "value=value.replace(/[^\d]/g,'')">
												</div>
											</div>
										</div><div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">佣金折扣：<br>(1-100)&nbsp;&nbsp;&nbsp;&nbsp;
												</label>
												<div class="col-sm-8">
													<input class="form-control" range="1,100" type="text" placeholder="这里输入诉讼仲裁佣金折扣" name="susongdiscount" id="" value="${not empty pd.susongdiscount?pd.susongdiscount:pd.discount }" >
												</div>
											</div>
										</div>
										</c:if>
										</c:if>
										
										<c:if test="${empty pd.id }">
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">佣金比例(%)&nbsp;<font color="red">*</font>：
												</label>
												<div class="col-sm-8">
													<input class="form-control" required type="text" placeholder="这里输入非诉讼佣金比例" name="commissionRate" id="" value="${pd.commissionRate }" onkeyup = "value=value.replace(/[^\d]/g,'')">
												</div>
											</div>
										</div><div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">佣金比例折扣：<br>(1-100)&nbsp;&nbsp;&nbsp;&nbsp;
												</label>
												<div class="col-sm-8">
													<input class="form-control" type="text" range="1,100" placeholder="这里输入非诉讼佣金比例折扣" name="discount" id="" value="${pd.discount }">
												</div>
											</div>
										</div>
										</c:if>
										</div>
										
										<div id="commType1" style="display:none">
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">佣金金额&nbsp;<font color="red">*</font>：
												</label>
												<div class="col-sm-8">
													<input class="form-control" required number="true" type="text" placeholder="这里输入佣金金额" name="fixedMoney"  value="${pd.fixedMoney }"  onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'')">
												</div>
											</div>
										</div>
										</div>
										
										<%-- <div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">债权人所属省&nbsp;<font color="red">*</font>：
												</label>
												<div class="col-sm-8">
													<select required class=" form-control" name="provinceName" id="provinceName">
														<option value="">请选择</option>
														<c:forEach items="${sfList }" var="block">
															<option data-id="${block.id}" value="${block.regionName}" <c:if test="${pd.provinceName eq block.regionName}">selected="selected"</c:if>>${empty block.regionName?'其他省份':block.regionName }</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="col-sm-4 control-label">债权人所属市&nbsp;<font color="red">*</font>：
														</label>
														<div class="col-sm-8">
															<select required class="form-control" name="cityName" id="cityName">
																<option value="">请选择</option>
																<c:forEach items="${sqList }" var="project">
																	<option data-parent="${project.parentId }" data-id="${project.id}" value="${project.regionName}" <c:if test="${pd.cityId eq project.id}">selected="selected"</c:if>>${empty project.regionName?'其他市区':project.regionName }</option>
																</c:forEach>
															</select>
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="col-sm-4 control-label">债权人具体地址：</label>
														<div class="col-sm-8">
															<input class="form-control" maxlength="50" type="text" placeholder="这里输入具体地址" name="addressDetail" id="addressDetail" value="${pd.addressDetail }">
														</div>
													</div>
												</div> --%>
										<div class="col-md-6">
											<div class="form-group chosen-input-group">
												<label class="col-sm-4 control-label">销售人&nbsp;<font color="red">*</font>：
												</label>
												<div class="col-sm-8">
													<select class="form-control chosen-select-sale" name="userId" id="userId">
														<c:if test="${not empty pd.saleId }">
															<option value="${pd.saleId }" selected="selected">${pd.userName }</option>
														</c:if>
													</select>
												</div>
											</div>
										</div>
										
										<div class="zwxz col-md-6"></div>
										<div class="col-md-12">
											<div class="form-group">
												<label class="col-sm-2 control-label">案件附件类型：</label>
												<div class="col-sm-10">
													<div class="checkbox checkbox-success checkbox-inline">
														<input type="checkbox" id="inlineCheckbox1" name="orederAttachType1" value="${pd.orederAttachType }"> <label for="inlineCheckbox1"> 合同/订货单 </label>
													</div>
													<div class="checkbox checkbox-success checkbox-inline">
														<input type="checkbox" id="inlineCheckbox2" name="orederAttachType1" value="${pd.orederAttachType }"> <label for="inlineCheckbox2"> 对账单/索款函 </label>
													</div>
													<div class="checkbox checkbox-success checkbox-inline">
														<input type="checkbox" id="inlineCheckbox4" name="orederAttachType1" value="${pd.orederAttachType }"> <label for="inlineCheckbox4"> 账款明细帐 </label>
													</div>
													<div class="checkbox checkbox-success checkbox-inline">
														<input type="checkbox" id="inlineCheckbox5" name="orederAttachType1" value="${pd.orederAttachType }"> <label for="inlineCheckbox5"> 往来信函 </label>
													</div>
													<div class="checkbox checkbox-success checkbox-inline">
														<input type="checkbox" id="inlineCheckbox6" name="orederAttachType1" value="${pd.orederAttachType }"> <label for="inlineCheckbox6"> 案件情况介绍 </label>
													</div>
													<div class="checkbox checkbox-success checkbox-inline">
														<input type="checkbox" id="inlineCheckbox7" name="orederAttachType1" value="${pd.orederAttachType }"> <label for="inlineCheckbox7"> 运货单/发票 </label>
													</div>
													<div class="checkbox checkbox-success checkbox-inline">
														<input type="checkbox" id="inlineCheckbox8" name="orederAttachType1" value="${pd.orederAttachType }"> <label for="inlineCheckbox8"> 欠款确认函/付款计划 </label>
													</div>
													<div class="checkbox checkbox-success checkbox-inline">
														<input type="checkbox" id="inlineCheckbox9" name="orederAttachType1" value="${pd.orederAttachType }"> <label for="inlineCheckbox9"> 判决书/执行申请 </label>
													</div>
													<div class="checkbox checkbox-success checkbox-inline">
														<input type="checkbox" id="inlineCheckbox10" name="orederAttachType1" value="${pd.orederAttachType }"> <label for="inlineCheckbox10"> 债务人调查文件 </label>
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">最近一次联络时间：</label>
												<div class="col-sm-8">
													<input class="form-control" maxlength="50" type="text" placeholder="这里输入最近一次联络时间" name="lastContactTime" id="lastContactTime" value="${pd.lastContactTime }">
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">债务有无争议：</label>
												<div class="col-sm-8">
													<div class="radio radio-success radio-inline">
														<input type="radio" <c:if test="${ empty pd.isDispute || pd.isDispute == '0'}">checked="checked"</c:if> onchange="setDispute('0')" id="form-isDispute-radio1" name="form-isDispute-radio">
														<label for="form-isDispute-radio1"> 无 </label>
													</div>
													<div class="radio radio-success radio-inline">
														<input type="radio" <c:if test="${pd.isDispute == '1'}">checked="checked"</c:if> onchange="setDispute('1')" id="form-isDispute-radio2" name="form-isDispute-radio"> <label
															for="form-isDispute-radio2"> 有 </label>
													</div>
												</div>
											</div>
										</div>
										<div class="zyxx col-md-12"></div>
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-sm-2"> 其他拖欠理由: </label>
												<div class="col-sm-10">
													<textarea class="form-control" style="height: 120px;" name="nonPayReasons" placeholder="这里输入其他拖欠理由">${pd.nonPayReasons}</textarea>
												</div>
											</div>
										</div>
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-sm-2"> 已往收款行为描述: </label>
												<div class="col-sm-10">
													<textarea class="form-control" style="height: 120px;" name="collectHistory" placeholder="这里输入已往收款行为描述">${pd.collectHistory}</textarea>
												</div>
											</div>
										</div>
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-sm-2"> 其他说明: </label>
												<div class="col-sm-10">
													<textarea class="form-control" style="height: 120px;" name="othersRequest" placeholder="这里输入其他说明">${pd.othersRequest}</textarea>
												</div>
											</div>
										</div>
									</div>
								</div>
								</div>
								<div class="panel panel-primary" id="tab2">
									<div class="panel-heading">债务人信息</div>
									<div class="ibox-content">
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">债务人(公司)&nbsp;<font color="red">*</font>：
													</label>
													<div class="col-sm-8">
													<input type="text" class="form-control" required placeholder="这里输入债务人(公司)" name="debtorName" value="${pd.debtorName }" />
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">债务公司固定电话：</label>
													<div class="col-sm-8">
														<input class="form-control" maxlength="50" placeholder="这里输入债务公司电话"  type="text" name="debtorMobilePhone" id="debtorMobilePhone" value="${pd.debtorMobilePhone }">
													</div>
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">公司传真：</label>
													<div class="col-sm-8">
														<input class="form-control" maxlength="50"  placeholder="这里输入公司传真" type="text" name="debtorFax" id="debtorFax" value="${pd.debtorFax }">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">其他联系：</label>
													<div class="col-sm-8">
														<input  class="form-control" maxlength="100" type="text" placeholder="这里输入其他联系" name="debtorOtherContract" id="debtorOtherContract" value="${pd.debtorOtherContract }">
													</div>
												</div>
											</div>
											<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">所属省份：
															</label>
															<div class="col-sm-8">
																<select class=" form-control" name="provinceName" id="provinceName" >
																	<option value="">请选择</option>
																	<c:forEach items="${sfList }" var="block">
																		<option data-id="${block.id}" value="${block.regionName}" <c:if test="${pd.provinceName1 eq block.regionName}">selected="selected"</c:if>>${empty block.regionName?'其他省份':block.regionName }</option>
																	</c:forEach>
																</select>
															</div>
														</div>
														</div>
														<div class="col-md-6">
															<div class="form-group">
																<label class="col-sm-4 control-label">所属市：
																</label>
																<div class="col-sm-8">
																	<select class="form-control" name="cityName" id="cityName" >
																		<option value="">请选择</option>
																		<c:forEach items="${sqList }" var="project">
																			<option data-parent="${project.parentId }" data-id="${project.id}" value="${project.regionName}" <c:if test="${pd.cityId1 eq project.id}">selected="selected"</c:if>>${empty project.regionName?'其他市区':project.regionName }</option>
																		</c:forEach>
																	</select>
																</div>
															</div>
														</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">具体地址：</label>
													<div class="col-sm-8">
														<input  class="form-control"  maxlength="500" type="text" placeholder="这里输入债务人地址" name="debtorAddress" id="debtorAddress" value="${pd.debtorAddress }">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">邮政编码：</label>
													<div class="col-sm-8">
														<input class="form-control"  maxlength="50" type="text" placeholder="这里输入邮政编码" name="debtorPostcode" id="debtorPostcode" value="${pd.debtorPostcode }">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">债务人营业现状：</label>
													<div class="col-sm-8">
													<select name="debtorStatus" id="debtorStatus" class="form-control">
													<option value="">请选择</option>
													<option value="0" <c:if test="${pd.debtorStatus == '0' }">selected</c:if>>营业中</option>
													<option value="1" <c:if test="${pd.debtorStatus == '1' }">selected</c:if>>停止/暂停营业</option>
													<option value="2" <c:if test="${pd.debtorStatus == '2' }">selected</c:if>>不详</option>
													<option value="3" <c:if test="${pd.debtorStatus == '3' }">selected</c:if>>债务主体破产/已注销</option>
													</select>
														<%-- <input disabled="disabled" class="form-control" data-validate="maxLength=50" type="text" name="debtorStatus" id="debtorStatus"
															value="<c:if test="${pd.debtorStatus=='0' }">营业中</c:if><c:if test="${pd.debtorStatus==1 }">停止/暂停营业</c:if><c:if test="${pd.debtorStatus==2 }">不详</c:if><c:if test="${pd.debtorStatus==3 }">债务主体破产/已注销</c:if>"> --%>
													</div>
												</div>
											</div>
										</div>
										<div class="clearfix"></div>

										<div class="col-md-12">
										<div class="panel-group" id="accordion">
											<div class="panel panel-default">
												<div class="panel-heading">
													<div class="panel-title">
														债务知情人 <a data-toggle="collapse" data-parent="#accordion" class="btn-expand-hook btn btn-white btn-xs pull-right" href="#collapseOne"> <i class="fa fa-angle-down"></i>
														</a> &nbsp;<span style="color: #fff;" onclick="add_linkMan('#zwzqr')" class="btn btn-primary btn-xs"><i class="fa fa-plus"></i> 新增 </span>
													</div>
												</div>
												<div class="panel-body">
													<div id="collapseOne" class="panel-collapse collapse in">
														<table class="table table-bordered table-hover" id="zwzqr">
															<tr>
																<td>姓名</td>
																<td>手机</td>
																<td>电话</td>
																<td>职务</td>
																<c:if test="${!isYunzuo }">
																<td>操作</td>
																</c:if>
															</tr>
															<tbody id="typeList_0">
																<c:if test="${not empty pd.linkMan1 }">
																	<c:forEach items="${pd.linkMan1 }" var="linkman1">
																		<tr
																			data-params='{"id":${linkman1.linkId1 },"name":"${empty linkman1.zqrName?" ":linkman1.zqrName }","phone":"${empty linkman1.zqrMobilePhone?"":linkman1.zqrMobilePhone}","landline":"${empty linkman1.zqrLandline?"":linkman1.zqrLandline }","postion":"${empty linkman1.zqrPostion?"":linkman1.zqrPostion }"}'>
																			<td>
																			<a onclick="add_linkMan('view',this)" >
																			${linkman1.zqrName}</a></td>
																			<td>${linkman1.zqrMobilePhone}</td>
																			<td>${linkman1.zqrLandline}</td>
																			<td>${linkman1.zqrPostion}</td>
																			<c:if test="${!isYunzuo }">
																<td>
																				<div class="btn btn-white btn-xs" onclick="delete_linkMan(this)">
																					<i class="fa fa-trash-o"></i> 删除
																				</div>
																			</td>
																</c:if>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>
										<div class="panel-group" id="accordionTwo">
											<div class="panel panel-default">
												<div class="panel-heading">
													其他负责人 <a data-toggle="collapse" data-parent="#accordionTwo" class="btn-expand-hook btn btn-white btn-xs pull-right" href="#collapseTwo"> <i class="fa fa-angle-down"></i>
													</a> &nbsp;<span style="color: #fff;" onclick="add_linkMan('#qtfzr')" class="btn btn-primary btn-xs"><i class="fa fa-plus"></i> 新增 </span>
												</div>
												<div class="panel-body">
													<div id="collapseTwo" class="panel-collapse collapse in">
														<table class="table table-bordered table-hover" id="qtfzr">
															<tr>
																<td>姓名</td>
																<td>手机</td>
																<td>电话</td>
																<td>职务</td>
																<c:if test="${!isYunzuo }">
																<td>操作</td>
																</c:if>
															</tr>
															<tbody id="typeList_1">
																<c:if test="${not empty pd.linkMan2 }">
																	<c:forEach items="${pd.linkMan2 }" var="linkman2">
																		<tr
																			data-params='{"id":${linkman2.linkId2 },"name":"${empty linkman2.fzrName?" ":linkman2.fzrName }","phone":"${empty linkman2.fzrMobilePhone?"":linkman2.fzrMobilePhone}","landline":"${empty linkman2.fzrLandline?"":linkman2.fzrLandline }","position":"${empty linkman2.fzrPostion?"":linkman2.fzrPostion }"}'>
																			<td>
																			<a onclick="add_linkMan('view',this)">
																			${linkman2.fzrName}</a>
																			</td>
																			<td>${linkman2.fzrMobilePhone}</td>
																			<td>${linkman2.fzrLandline}</td>
																			<td>${linkman2.fzrPostion}</td>
																			<c:if test="${!isYunzuo }">
															<c:if test="${!isYunzuo }">
																<td>
																				<div class="btn btn-white btn-xs" onclick="delete_linkMan(this)">
																					<i class="fa fa-trash-o"></i> 删除
																				</div>
																			</td>
																</c:if>
																</c:if>
																			
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>
										</div>
										<div class="clearfix"></div>
									</div>
								<div class="mark panel panel-primary" id="tab3">
									<div class="panel-heading">债权人信息</div>
									<div class="ibox-content">
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">债权人(公司)&nbsp;<font color="red">*</font>：
													</label>
													<div class="col-sm-8">
														<select class="ajax-select ajax-select2 form-control" name="customerId" id="customerId" >
															<c:if test="${not empty pd.customerId }">
																<option value="${pd.customerId }" selected="selected">${pd.customerName }</option>
															</c:if>
														</select>
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">传真:</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" disabled name="customerFax" id="customerFax" value="${pd.customerFax }">
													</div>
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">邮政编码:</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" disabled  name="customerPostCode" id="customerPostCode" value="${pd.customerPostCode }">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">开户行:</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" disabled name="customerBankAccountName" id="bankName" value="${pd.customerBankAccountName }">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">开户行账号:</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" disabled name="customerBankNumber" id="customerBankNumber" value="${pd.customerBankNumber }">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">所属省:</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" disabled name="provinceName2" id="provinceName2" value="${pd.provinceName }">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">所属市:</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" disabled name="cityName2" id="cityName2" value="${pd.cityName }">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">具体地址:</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" disabled  name="customerAddress" id="customerAddress" value="${pd.customerAddress }">
													</div>
												</div>
											</div>
										</div>
										<div class="clearfix"></div>
										<div class="panel-group" id="accordionThree">
											<div class="panel panel-default">
												<div class="panel-heading">
													案件知情人 <a data-toggle="collapse" data-parent="#accordionThree" class="btn-expand-hook btn btn-white btn-xs pull-right" href="#collapseThree"> <i class="fa fa-angle-down"></i>
													</a>
													<c:if test="${!isYunzuo}">&nbsp;<a style="color: #fff;" onclick="chooseLinkman('2','${pd.customerId}')" class="btn btn-primary btn-xs"><i class="fa fa-plus"></i> 新增 </a>
													</c:if>
												</div>
												<div class="panel-body">
													<div id="collapseThree" class="panel-collapse collapse in">
														<table class="table table-bordered table-hover">
															<tr>
																<td>姓名</td>
																<td>手机</td>
																<td>电话</td>
																<td>职务</td>
																<c:if test="${!isYunzuo}">
																	<td>操作</td>
																</c:if>
															</tr>
															<tbody id="typeList_2">
																<c:if test="${not empty pd.linkMan3 }">
																	<c:forEach items="${pd.linkMan3 }" var="linkMan3">
																		<tr
																			data-params='{"id":${linkMan3.linkId3 },"name":"${empty linkMan3.ajzqrName?" ":linkMan3.ajzqrName }","phone":"${empty linkMan3.ajzqrMobilePhone?"":linkMan3.ajzqrMobilePhone}","landline":"${empty linkMan3.ajzqrLandline?"":linkMan3.ajzqrLandline }","position":"${empty linkMan3.ajzqrPostion?"":linkMan3.ajzqrPostion }"}'>
																			<td>
																			<a onclick="add_linkMan('view',this)">
																			${linkMan3.ajzqrName}</a>
																			</td>
																			<td>${linkMan3.ajzqrMobilePhone}</td>
																			<td>${linkMan3.ajzqrLandline}</td>
																			<td>${linkMan3.ajzqrPostion}</td>
																			<c:if test="${!isYunzuo}">
																				<td>
																					<div class="btn btn-white btn-xs" onclick="delete_linkMan(this)">
																						<i class="fa fa-trash-o"></i> 删除
																					</div>
																				</td>
																			</c:if>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>

										<div class="panel-group" id="accordionFour">
											<div class="panel panel-default">
												<div class="panel-heading">
													工作汇报对象人 <a data-toggle="collapse" data-parent="#accordionFour" class="btn-expand-hook btn btn-white btn-xs pull-right" href="#collapseFour"> <i class="fa fa-angle-down"></i>
													</a>
													<c:if test="${!isYunzuo}">&nbsp;<a style="color: #fff;" onclick="chooseLinkman('3','${pd.customerId}')" class="btn btn-primary btn-xs"><i class="fa fa-plus"></i> 新增 </a>
													</c:if>
												</div>
												<div class="panel-body">
													<div id="collapseFour" class="panel-collapse collapse in">
														<table class="table table-bordered table-hover">
															<tr>
																<td>姓名</td>
																<td>手机</td>
																<td>电话</td>
																<td>职务</td>
																<c:if test="${!isYunzuo}">
																	<td>操作</td>
																</c:if>
															</tr>
															<tbody id="typeList_3">
																<c:if test="${not empty pd.linkMan4 }">
																	<c:forEach items="${pd.linkMan4 }" var="linkMan4">
																		<tr
																			data-params='{"id":${linkMan4.linkId4 },"name":"${empty linkMan4.hbdxrName?" ":linkMan4.hbdxrName }","phone":"${empty linkMan4.hbdxrMobilePhone?"":linkMan4.hbdxrMobilePhone}","landline":"${empty linkMan4.hbdxrLandline?"":linkMan4.hbdxrLandline }","position":"${empty linkMan4.hbdxrPostion?"":linkMan4.hbdxrPostion }"}'>
																			<td>
																			<a onclick="add_linkMan('view',this)" >
																			${linkMan4.hbdxrName}</a>
																			</td>
																			<td>${linkMan4.hbdxrMobilePhone}</td>
																			<td>${linkMan4.hbdxrLandline}</td>
																			<td>${linkMan4.hbdxrPostion}</td>
																			<c:if test="${!isYunzuo}">
																				<td>
																					<div class="btn btn-white btn-xs" onclick="delete_linkMan(this)">
																						<i class="fa fa-trash-o"></i> 删除
																					</div>
																				</td>
																			</c:if>
																		</tr>
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
									<div class="mark panel panel-primary" id="tab5">
									<div class="panel-heading">合同信息</div>
									<div class="ibox-content">
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">

													<label class="col-sm-4 control-label">合同编号&nbsp;<font color="red">*</font>：
													</label>
													<div class="col-sm-8">
														<input type="text" readonly="readonly" id="contractNo" onclick="chooseContract()" placeholder="这里选择合同" required="required" class="form-control" name="ContractNo" value="${pd.contractNo }" />
														<input type="hidden" readonly="readonly" id="contractId" name="contractId"  value="${pd.contractId }" />
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">债权人(公司):</label>
													<div class="col-sm-8">
														<input class="form-control" readonly="readonly" id="c_customerName" type="text" disabled name="c_customerName"  value="${pd.customerName }">
													</div>
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">债务人(公司):</label>
													<div class="col-sm-8">
														<input class="form-control" readonly="readonly" id="c_debtorName" type="text" disabled  name="c_debtorName"  value="${pd.c_debtorName }">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">欠款总金额(元):</label>
													<div class="col-sm-8">
														<input class="form-control" readonly="readonly" type="text" id="c_price" disabled name="c_price"  value="${pd.c_price }">
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">佣金比例(%):</label>
													<div class="col-sm-8">
														<input class="form-control" readonly="readonly" id="c_commissionRate" type="text" disabled name="c_commissionRate" value="${pd.c_commissionRate }">
													</div>
												</div>
											</div>
										</div>
										<div class="clearfix"></div>

									</div>
								</div>
								<div class="mark panel panel-primary" id="tab4" >
								<div class="panel-heading">相关附件</div>
								<div class="ibox-content">
								<div class="well well-sm">
									<div id="fileUp"></div>
								</div>
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
												<c:forEach items="${pd.attachs }" var="attachs">
													<tr>
														<td>${attachs.originalFilename }</td>
														<td>${attachs.type }</td>
														<td>${attachs.uploader }</td>
														<td>${attachs.uploadTime }</td>
														<td><a target="_blank" href="${attachs.url }" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a> <a class="btn btn-danger" onclick="list_del(this)"><i
																class="fa fa-trash-o"></i> 删除</a> <input type="hidden" name="fileSize" value="${attachs.fileSize }"><input type="hidden" name="uploader" value="${attachs.uploader }"> <input
															type="hidden" name="originalFilename" value="${attachs.originalFilename }"><input type="hidden" name="uploadTime" value="${attachs.uploadTime }"> <input type="hidden"
															name="realPath" value="${attachs.realPath }"><input type="hidden" name="url" value="${attachs.url }"> <input type="hidden" name="attachId" value="${attachs.id }"></td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
									</div>
								</div>
								</div>
							</div>
			<div class="text-center" style="position:fixed;bottom:0;left:0;width: 100%;background:#fafafa;height:60px;line-height:60px;">
				<button type="submit" class="btn btn-mini btn-primary" ><i class="fa fa-save"></i> 保存</button>
			</div>
					</form>
				</div>
				
				
				
				<div class="ibox-content" id="new-float-box1" style="overflow: hidden; display: none">
		<div class="col-md-12">
				<form name="linkmanForm" id="linkmanForm" method="post"  class="form form-horizontal" >
										<div class="row">
												<div id="zhongxin" style="padding-top: 13px;">
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">姓名<font color="red">*</font>：</label>
														        <div class="col-sm-8">
														            <input class="form-control" data-validate="require|maxLength=50" type="text" placeholder="这里输入联系人名称"  name="name" id="name"  value=""  >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">手机号码：</label>
														        <div class="col-sm-8">
														            <input class="form-control" type="text" placeholder="这里输入手机号码"  name="mobilePhone" id="mobilePhone"  value=""  >
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">固定电话：</label>
														        <div class="col-sm-8">
														            <input class="form-control" type="text" placeholder="这里输入固定电话" name="landline" id="landline"  value=""  >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">邮箱：</label>
														        <div class="col-sm-8">
														            <input class="form-control" type="text" placeholder="这里输入邮箱" name="email" id="email"  value=""  >
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">职务：</label>
														        <div class="col-sm-8">
														            <input class="form-control" type="text" placeholder="这里输入职务"  name="postion" id="postion"  value=""  >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">传真：</label>
														        <div class="col-sm-8">
														            <input class="form-control" type="text" placeholder="这里输入传真" name="fax" id="fax"  value=""  >
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-12">
													<div class="form-group">
														<label  class="control-label col-sm-3 col-md-2 no-padding-right">
															备注:
														</label>
														<div class="col-sm-9 col-md-10">
														<textarea class="form-control" style="height: 120px;" rows="15" cols="10" name="remark"  placeholder="这里输入备注"></textarea>
														</div>
													</div>	
													</div>													
												</div>
												</div>
										</form>
		</div>
	</div>
				
				
				
				
				
	<style>
.mark {
	padding: 0;
}
.navFix {
background:#fff;
width:100%;
padding-left:20px;
    position: fixed;
    top: 0;
    left: 0;
    box-shadow: 0 0 5px rgba(0,0,0, 0.2);
    border-bottom: 1px solid #e3e3e3\9;
    z-index: 99999;
}
</style>

	<%@ include file="../../system/index/js.jsp"%>
</body>

<script type="text/javascript">
//当用户是运作的时候
var isYunzuo = '${isYunzuo}'
if (isYunzuo == 'true') {
	$("#myTab").remove();
	$("#tab1").remove();
	$("#tab2").addClass("active");
	$("#tab3").remove();
	$("#tab4").remove();
	
}
$("body").show()


	//默认债务人
	var debtorId = '${pd.debtorId}' || 0;
	//默认债权人
	var debtorId2 = '${pd.customerId}' || 0;
	
	//省市
	var area1 = $("select[name='provinceName']")
	var area2 = $("select[name='cityName']")
	function areaInit() {
		area2.find('option').hide()
		if (area1.val() == '') {
			/* area2.find('option').show().eq(0).prop('selected',true)  */
		} else {
			var s = area2.find("[value='']").show();
			var options = area2.find("[data-parent='"
					+ area1.find("option:selected").data('id') + "']")
			options.show()
			/* if (area2.val() != '') {
			    s.eq(0).prop('selected', true)
			} */
		}
		area1.trigger("chosen:updated");
		area2.trigger("chosen:updated");
		/* layer.close(loading) */
	}
	area1.chosen();
	area2.chosen();
	areaInit()
	area1.change(function() {
		area2.val('');
		areaInit()
	})
	
	//内容信息导航吸顶
	$(document).ready(function(){ 
	var navHeight= $(".topNav").offset().top; 
	var navFix=$("#myTab"); 
	$(window).scroll(function(){ 
		if($(this).scrollTop()>navHeight){ 
			navFix.addClass("navFix"); 
		} 
		else{ 
			navFix.removeClass("navFix"); 
		} 
		}) 
	})
	
//内容信息导航锚点
   $('.nav-wrap').navScroll({
      mobileDropdown: true,
      mobileBreakpoint: 768,
      scrollSpy: true
    });

    $('.click-me').navScroll({
      navHeight: 0
    });

    $('.nav-wrap').on('click', '.nav-mobile', function (e) {
      e.preventDefault();
      $('.nav-wrap ul').slideToggle('fast');
    });

	
	
	//初始化销售人chosen
					 $(".chosen-select-sale").select2({
						 placeholder:"请选择",
						 language: 'zh-CN',
						  ajax: {
						    url: "/user/getUserInfo",
						    dataType: 'json',
						    type:"post",
						    delay: 250,
						    data: function (params) {
						      return {
						        page: params.page || 1,
						           ROLE_ID:"fbe6f2f9535c4fce9f024f6cb999b2bd,90564dd8b75a4f6d815ce418462d401c,e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b",
						        data:{
						        	 q: params.term || "", // search term
						        }
						      };
						    },
						    processResults: function (data, params) {
						      params.page = params.page || 1;
						      $.each(data,function(){
						    	  this.id= this.USER_ID;
						    	  this.text = this.NAME;
						      })
						      return {
						        results: data,
						         pagination: {
						          more: data.length==10
						        } 
						      };
						    },
						    cache: true
						  },
						  minimumInputLength: 0
						}); 
	
	//初始化相关合同
	/* $(".chosen-select-contract")
			.ajaxChosen(
					{
						dataType : 'json',
						type : 'POST',
						url : '/contract/getContractInfo'
					},
					{
						processItems : function(data) {
							var list = [];
							$.each(data, function() {
								list.push({
									id : this.id,
									text : this.contractNo,
									saleId:this.saleId
								})
							})
							sessionStorage.dataCache3 = JSON.stringify(list);
							return list;
						},
						loadingImg : '${pageContext.request.contextPath}/static/ui/img/loading.gif'
					}).on("change",function(){
						var value = this.value;
						var cache = JSON.parse(sessionStorage.dataCache3)
						$.each(cache, function() {
							if (this.id == value) {
								console.log(this)
								$("#saleId").val(this.saleId)
							}
						})
					}) */

					//佣金比例
					var commType0 = $("#commType0");
					var commType1 = $("#commType1")
					$("#commType").on("change",function(){
						if(this.value=="0"){
							commType0.show().find("input").prop("disabled",false);
							commType1.hide().find("input").prop("disabled",true).val("");
						}else{
							commType1.show().find("input").prop("disabled",false);
							commType0.hide().find("input").prop("disabled",true).val("");
						}
					})
					$(function(){
						$("#commType").trigger("change")
					})
					
					
	
	$('.date-picker').datepicker({
		autoclose : true,
		todayHighlight : true
	}).on("change",function(e){
		$("#contractForm").validate().element($(e.target))
	});
	
	
		$("#contractForm").validate({
			 ignore: "",   
             submitHandler: function(form) {
            	 //验证
            	 if($("#userId").length&&!$("#userId").val()){
     				layer.msg("销售人未填写");	
     				return false;
     			}else if($("#customerId").length&&!$("#customerId").val()){
     				layer.msg("债权人(公司)未填写");
     				return false;
     			}
            	 	var orederAttachType = '';
     			$("input[name='orederAttachType1']:checked").each(
     					function() {
     						orederAttachType = orederAttachType
     								+ $(this).next().html() + ',';
     					});
     			orederAttachType = orederAttachType.substring(0,
     					orederAttachType.length - 1);
     			$("#orederAttachType").val(orederAttachType);
     			if (!$("#isDispute").val()) {
     				setDispute('0');
     			}
     			var isYunzuo = "${isYunzuo}";
     			if (isYunzuo == "true") {
     				var index = layer.load(1, {
     					shade : [ 0.2, '#fff' ]
     				//0.1透明度的白色背景
     				});
     				form.submit();
     				return false;
     			}else{
     			var id = $("#id").val();
     			var orderNo = $("#orderNo").val().trim();
     			$.ajax({
     				type : "POST",
     				url : '/order/hasOrderNo.do',
     				data : {
     					id : id,
     					orderNo : orderNo,
     					tm : new Date().getTime()
     				},
     				dataType : 'json',
     				cache : false,
     				success : function(data) {
     					if ("success" != data.result) {
     						layer.msg('订单号' + orderNo + '已存在')
     					} else {
     						var index = layer.load(1, {
     							shade : [ 0.2, '#fff' ]
     						//0.1透明度的白色背景
     						});
     						form.submit();
     					}
     				}
     			}); 
     			}
     		}
     });

		init_uploader_state = 1
		var uploader = WebUploader.create({
			// 文件接收服务端。
			server : "/contract/uploadAttachment.do",
			runtimeOrder : 'html5',
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick : {
				id : '#fileUp',
				innerHTML : '上传附件'
			},
			resize : false,
			fileNumLimit : 10,
		});

		//上传成功
		uploader
				.on(
						'uploadSuccess',
						function(file, response) {
							console.log(file, response);
							$("#realPath").val(response.realPath);
							$("#originalFilename").val(
									response.originalFilename);
							$("#fileSize").val(response.fileSize);
							$("#url").val(response.url);
							$("#uploadTime").val(response.uploadTime);
							$("#uploader").val(response.uploader);
							var hideVal = function() {
								var html = '';
								for (i in response) {
									if (i != '_raw')
										html += '<input type="hidden" name="' + i + '" value="' + response[i] + '" />'
								}
								html += '<input type="hidden" name="attachId" value="0" />';
								return html;
							}
							$("#fileList tbody")
									.append(
											'<tr><td>'
													+ response.originalFilename
													+ '</td><td>案件</td><td>'
													+ response.uploader
													+ '</td><td>'
													+ response.uploadTime
													+ '</td><td><a target="_blank" href="' + response.url + '" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a> <a class="btn btn-danger" onclick="list_del(this)"><i class="fa fa-trash-o"></i> 删除</a>'
													+ hideVal() + '</td></tr>')
							$('#' + file.id).addClass('upload-state-done');
						});

		uploader.on('uploadStart', function() {
			layer.load(); //上传过程中开启loading遮罩
		})
		//上传出错
		uploader.on('uploadError', function(file) {
			$('#' + file.id).find('p.state').text('上传出错');
		});
		//出错
		uploader.on('error', function(res) {
			if (res == 'Q_TYPE_DENIED') {
				alert('上传文件格式错误，请检查')
			}
		});

		//上传完成
		uploader.on('uploadComplete', function(file) {
			$('#' + file.id).find('.progress').fadeOut();
			layer.closeAll() //关闭遮罩层
		});
		//文件加入队列
		uploader.on('fileQueued', function() {
			uploader.upload()
		})

	//删除附件
	function list_del(dom) {
		$(dom).parents('tr').remove()
	}

	//tab3初始化
	var area1 = $("select[name='provinceName']")
	var area2 = $("select[name='cityName']")
	function areaInit() {
		area2.find('option').hide()
		if (area1.val() == '') {
			/* area2.find('option').show().eq(0).prop('selected',true)  */
		} else {
			var s = area2.find("[value='']").show();
			var options = area2.find("[data-parent='"
					+ area1.find("option:selected").data('id') + "']")
			options.show()
			/* if (area2.val() != '') {
			    s.eq(0).prop('selected', true)
			} */
		}
		area1.trigger("chosen:updated");
		area2.trigger("chosen:updated");
	}
	area1.chosen();
	area2.chosen();
	areaInit()
	area1.change(function() {
		area2.val('');
		areaInit()
	})

	//tab2初始化
			var dataCache = []
			window.initTab2Status = 1
			 $(".ajax-select2").select2({
				 placeholder:"请选择",
				 language: 'zh-CN',
				  ajax: {
				    url: '/customer/getCustomerInfo',
				    dataType: 'json',
				    type:'post',
				    delay: 250,
				    data: function (params) {
				      return {
				        page: params.page || 1,
				        data:{
				        	 q: params.term || "", // search term
				        }
				      };
				    },
				    processResults: function (data, params) {
				      params.page = params.page || 1;
				      $.each(data,function(){
				    	  this.text = this.name;
							this.cacheId = this.id
							dataCache.push(this)
				      })
						sessionStorage.dataCache2 = JSON.stringify(dataCache);
						dataCache = [];
				      return {
				        results: data,
				         pagination: {
				          more: data.length==10
				        } 
				      };
				    },
				    cache: true
				  },
				  minimumInputLength: 0
				}).change(function() {
					var id = "";
					var value = this.value;
					debtorId2 = this.value;
					var cache = JSON.parse(sessionStorage.dataCache2)
					$.each(cache, function() {
						if (this.cacheId == value) {
							$("#customerBankNumber").val(this.bankNumber);
							$("#customerFax").val(this.fax);
							$("#bankName").val(this.bankName);
							$("#customerAddress").val(this.companyAddress);
							$("#customerPostCode").val(this.postCode);
							$("#provinceName2").val(this.provinceName);
							$("#cityName2").val(this.cityName);
						}
					})
				}); 
			
			
			
			
			
			/* 	$(".ajax-select2").ajaxChosen({
					dataType : 'json',
					type : 'POST',
					url : '/customer/getCustomerInfo'
				}, {
					processItems : function(data) {
						$.each(data, function() {
							this.text = this.name;
							this.cacheId = this.id
							dataCache.push(this)
						})
						sessionStorage.dataCache2 = JSON.stringify(dataCache);
						dataCache = [];
						return data;
					},
					loadingImg : '/static/ui/img/loading.gif'
				}).change(function() {
					var id = "";
					var value = this.value;
					debtorId2 = this.value;
					var cache = JSON.parse(sessionStorage.dataCache2)
					$.each(cache, function() {
						if (this.cacheId == value) {
							$("#customerBankNumber").val(this.bankNumber);
							$("#customerFax").val(this.fax);
							$("#customerAddress").val(this.companyAddress);
							$("#customerPostCode").val(this.postCode);
							$("#provinceName2").val(this.provinceName);
							$("#cityName2").val(this.cityName);
						}
					})
				}) ; */
	//tab3chosen初始化
			//初始化下拉数据
			var dataCache = [];
/* 				$(".ajax-select1").ajaxChosen({
					dataType : 'json',
					type : 'POST',
					url : '/customer/getCustomerInfo'
				}, {
					processItems : function(data) {
						$.each(data, function() {
							this.text = this.name;
							this.cacheId = this.id
							dataCache.push(this)
						})
						sessionStorage.dataCache1 = JSON.stringify(dataCache);
						dataCache = [];
						return data;
					},
					loadingImg : '/static/ui/img/loading.gif'
				}); */

	$(function() {
		//收缩按钮效果
		$('.btn-expand-hook').on('click', function() {
			var angle = $(this).find('i.fa');
			if (angle.hasClass('fa-angle-down')) {
				angle.removeClass('fa-angle-down').addClass('fa-angle-up');
			} else {
				angle.removeClass('fa-angle-up').addClass('fa-angle-down');
			}
		})

		var attachType = '${pd.orederAttachType}'
		if (attachType) {
			$("input[name='orederAttachType1']").each(function() {
				if (attachType.indexOf($(this).next().html()) != -1) {
					$(this).attr("checked", true);
				}
			});
		}
		var dispute = '${pd.isDispute}'
		if ("1" == dispute) {
			setDispute(1);
		}

		var debtType = '${pd.debtType}'
		if ("3" == debtType) {
			selectE();
		}

	});

	function setDispute(value) {
		$("#isDispute").val(value);
		if (value == 1) {
			$(".zyxx")
					.append(
							'<div class="form-group">'
									+ '<label class="col-sm-2 control-label">争议原因：</label>'
									+ '<div class="col-sm-10">'
									+ '<textarea class="form-control" style="height: 120px;"  name="disputedReasons"  placeholder="这里输入其他说明">${pd.disputedReasons}</textarea>'
									+ '</div>' + '</div>')
		} else {
			$(".zyxx").empty();
		}
	}

	//查找客户	
	function chooseCustomer() {

	}

	//查找联系人	
	function chooseLinkman(type, relateId) {
			
		if(!$("#customerId").val() &&(type == "2" || type == "3")){
			layer.msg("请先选择债权人(公司)");
			return false;
		}
		//联系人模板
		var that = this;
		this.TEMP = function(ids, name, phone, landline, position) {
			ids = ids.split(',');
			name = name.split(',');
			phone = phone.split(',');
			landline = landline.split(',');
			position = position.split(',');
			var html = '';
			if (!ids.length)
				return html;
			$
					.each(
							ids,
							function(i) {
								var params = JSON.stringify({
									id : this,
									name : name[i] || "",
									phone : phone[i] || "",
									landline : landline[i] || "",
									position : position[i] || ""
								})
								params = encodeURI(params);
								html += '<tr data-params="' + params + '"><td><a onclick="add_linkMan(\''+"view"+'\',this)" >'
										+ (name[i] || "")
										+ '</a></td><td>'
										+ (phone[i] || "")
										+ '</td><td>'
										+ (landline[i] || "")
										+ '</td><td>'
										+ (position[i] || "")
										+ '</td><td><div class="btn btn-white btn-xs" onclick="delete_linkMan(this)"><i class="fa fa-trash-o"></i> 删除</div></td><tr>'
							})
			return html
		}
		this.type = 'chooseLinkManCacheType_' + type;
		var debtorId3 = debtorId;
		if (type == "2" || type == "3") {
			debtorId3 = debtorId2
		}
		var index = layer.open({
			type : 2,
			title : '选择联系人',
			content : '/linkman/chooseLinkman.do?type=' + type + "&relateId="
					+ relateId + "&customerId=" + (debtorId3 || 0),
			area : [ '90%', '90%' ],
			btnAlign:'c',
			btn : [ '保存' ],
			yes : function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：姓名、职务、电话、手机
				var data = iframeWin.pushToParent();
				iframeWin.push.update();
				layer.close(index)
				console.log(data)
				type = Number(type);
				//赋值子页面传过来的IDS
				$("#linkId" + (type + 1)).val(data.ids)
				if (data.ids)
					$("#typeList_" + type).html(
							that.TEMP(data.ids, data.names, data.mobilePhones,
									data.landLines, data.postions));
				else
					$("#typeList_" + type).html("")
					//sessionStorage[that.type] = '0'   //关闭缓存
				cacheUpdate()
			},
			end : function() {
				localStorage.addCacheHook = ""; //清空新增联系人的缓存
			}
		});
	}
	//查找合同
	function chooseContract() {
		if(!$("#customerId").val()){
			layer.msg("请先选择债权人(公司)");
			return false;
		}
		var that = this;
		var index = layer.open({
			type : 2,
			title : '选择合同',
			content : '/contract/chooseContract?id=' + $("#customerId").val(),
			area : [ '90%', '90%' ],
			btnAlign:'c',
			btn : [ '保存' ],
			yes : function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：姓名、职务、电话、手机
				var data = iframeWin.pushToParent();
				iframeWin.push.update();
				layer.close(index)
				document.getElementById('contractId').value= data.ids
				document.getElementById('contractNo').value = data.contractNo
				document.getElementById('c_customerName').value=data.customerName
				document.getElementById('c_debtorName').value = data.debtorName
				document.getElementById('c_price').value = data.price
				document.getElementById('c_commissionRate').value = data.commissionRate
			},
			end : function() {
				sessionStorage.chooseContract_contract = '0' //关闭缓存
			}
		});
	}

	//查找销售人
	function chooseUser() {
		sessionStorage.ListTagsCache = '0' //关闭缓存
		var index = layer
				.open({
					type : 2,
					title : '选择标签',
					content : '/user/chooseUser.do?flags=0&ROLE_ID=30b1487221464d369ca4c2462ccca920,b729e9334ad64c15a4e47d88b8c2638f&parIds=userId&parNames=userName',
					area : [ '90%', '90%' ],
					btnAlign:'c',
					btn : [ '保存' ],
					yes : function(index, layero) {
						var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
						var data = iframeWin.pushToParent()
						layer.close(index)
						$("#userId").val(data.ids); //赋值子页面传过来的IDS
						$("#userName").val(data.names);
						sessionStorage.ListTagsCache = '0' //关闭缓存
					}
				});
	}

	function selectE() {
		var debtType = $("#debtType").val();
		console.log(debtType);
		if (debtType == 3) {
			$(".zwxz")
					.append(
							'<div class="form-group">'
									+ '<label class="col-sm-4 control-label">其他具体类型&nbsp;<font color="red">*</font>：</label>'
									+ '<div class="col-sm-8">'
									+ '<input class="form-control" required type="text" placeholder="其他具体类型"  name="otherdebtDetail" id="otherdebtDetail"  value="${pd.otherdebtDetail}"  >'
									+ '</div>' + '</div>')
		} else {
			$(".zwxz").empty();
		}
	}

	/**
	 **缓存生成与更新
	 **/
	(function(doc) {
		cacheUpdate();
	})(document)
	function cacheUpdate() {
		for (var i = 0; i < 4; i++) {
			var type = 'chooseLinkManCacheType_' + i;
			var valueContainer = $("#linkId" + (i + 1));
			var tbody = $("#typeList_" + i);
			var ids = [];
			var names = [];
			var MobilePhones = [];
			var Landlines = [];
			var Postions = [];
			$.each(tbody.find('tr'), function() {
				if (this.dataset.params) {
					var params =this.dataset.params;
					var json = {}
					if(typeof params =="string"){
						json = JSON.parse(decodeURI(params));
					}else{
						json = params
					}
					ids.push(json.id);
					names.push(json.name);
					MobilePhones.push(json.phone);
					Landlines.push(json.landline)
					Postions.push(json.postion);
				}
			})
			if (ids.length) {
				valueContainer.val(ids.join(','))
				sessionStorage[type + '_IDS'] = ids.join(',');
				sessionStorage[type + '_PdtNames'] = names.join(',');
				sessionStorage[type + '_PdtMobilePhones'] = MobilePhones
						.join(',')
				sessionStorage[type + '_PdtLandlines'] = Landlines.join(',');
				sessionStorage[type + '_PdtPostions'] = Postions.join(',');
				sessionStorage[type] = '1';
			} else {
				valueContainer.val("");
				sessionStorage[type] = '0'
			}
		}
	}
	function delete_linkMan(dom) {
		$(dom).parents('tr').remove()
		cacheUpdate()
	}
	function add_linkMan(idName,dom){
		if(idName == "view"){
			var params = $(dom).parents("tr").data("params");
			var params_name = $(dom).parents("tbody").attr("id");
			console.log($(dom).parents("tbody"),params_name)
			var json = {}
			if(typeof params =="string"){
				json = JSON.parse(decodeURI(params));
			}else{
				json = params
			}
			json.mobilePhone = json.phone || ""
					$.each($("#new-float-box1 form").find("input,textarea"),function(){
//						$(this).prop("disabled",true)
						this.value = json[this.name] || ""
					})
					var id = json.id
			var index = layer.open({
				type : 1,
				title : '查看',
				content :$("#new-float-box1"),
				area : [ '70%', '70%' ],
				btn:["保存"],
				btnAlign:'c',
				yes :function(){
					console.log(json,id)
					if(yoValidate("#new-float-box1",1)){
						var data = {};
						$.each($("#new-float-box1 form").find("input,textarea"),function(){
							data[this.name] = this.value
						})
						data.type = "1";
						data.id = id;
			 	$.post("/linkman/updateLinkMan",
					data).done(function(res){
						$(dom).parents("tr").remove();
						data.id = res.result.id;
						data.phone = data.mobilePhone;
						var html = '<tr  data-params="' +  encodeURI(JSON.stringify(data)) + '"><td><a onclick="add_linkMan(\''+"view"+'\',this)" >'
										+ (data.name || "")
										+ '</a></td><td>'
										+ (data.phone|| "")
										+ '</td><td>'
										+(data.landline || "")
										+ '</td><td>'
										+ (data.postion||"")
										+ '</td>'
									if(isYunzuo !='true')	html+='<td><div class="btn btn-white btn-xs" onclick="delete_linkMan(this)"><i class="fa fa-trash-o"></i> 删除</div></td>'
									html+='</tr>'
										if(params_name == "typeList_0"){
									$("#typeList_0").append(html)			
										}else if(params_name == "typeList_1"){
											$("#typeList_1").append(html)
										}else if(params_name == "typeList_2"){
											$("#typeList_2").append(html)
										}else if(params_name == "typeList_3"){
											$("#typeList_3").append(html)
										}
										cacheUpdate()
						layer.close(index);
					}) 
		}
					
					
				},
				end:function(){
					$.each($("#new-float-box1 form").find("input,textarea"),function(){
						$(this).prop("disabled",false)
					})
					$("#new-float-box1 form")[0].reset();
				}
		})
		}else{
		
		var index = layer.open({
			type : 1,
			title : '新增',
			closeBtn: 1,
			content :$("#new-float-box1"),
			area : [ '70%', '70%' ],
			btnAlign:'c',
			btn:["保存"],
			yes :function(){
					if(yoValidate("#new-float-box1",1)){
						var data = {};
						$.each($("#new-float-box1 form").find("input,textarea"),function(){
							data[this.name] = this.value
						})
						data.type = "1";
			 	$.post("/linkman/saveLinkMan",
					data).done(function(res){
						data.id = res.result;
						data.phone = data.mobilePhone;
						var html = '<tr  data-params="' +  encodeURI(JSON.stringify(data)) + '"><td><a onclick="add_linkMan(\''+"view"+'\',this)" >'
										+ (data.name || "")
										+ '</a></td><td>'
										+ (data.phone|| "")
										+ '</td><td>'
										+(data.landline || "")
										+ '</td><td>'
										+ (data.postion||"")
										+ '</td>'
									if(isYunzuo !='true')	html+='<td><div class="btn btn-white btn-xs" onclick="delete_linkMan(this)"><i class="fa fa-trash-o"></i> 删除</div></td>'
									html+='</tr>'
										if(idName == "#zwzqr"){
									$("#typeList_0").append(html)			
										}else{
											$("#typeList_1").append(html)
										}
										cacheUpdate()
						layer.close(index);
					}) 
		}
			},
			end:function(){
				$("#new-float-box1 form")[0].reset()
			}
		});
		
		}
	}
	
	
</script>

</html>