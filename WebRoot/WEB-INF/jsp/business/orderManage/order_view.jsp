<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<base href="${pageContext.request.contextPath}/">
		<%@ include file="../../system/index/top.jsp"%>
	</head>
	<style>
			a {
			text-decoration: none;
		}
		
		.tab-pane {
			padding: 20px;
			background: #FFFFFF;
		}
		
		.panel-primary {
			border-color: #ddd;
		}
		
		.panel-heading {
			background-color: #f5f5f5!important;
			border-color: #ddd!important;
			color: #333!important;
		}
		
		li.active {
			border-top: 2px solid #459df5!important;
		}
		
		li.active a {
			color: #459df5!important;
		}
		
		.form-horizontal .control-label {
			text-align: left!important;
		}
		
		input {
			border: none!important;
		}
		
		textarea {
			resize: none;
			border: none!important;
		}
		
		.form-control[disabled] {
			background-color: #FFFFFF!important;
			border-color: #FFFFFF!important;
			appearance: none;
			-moz-appearance: none;
			-webkit-appearance: none;
		}
		
		.panel-default>.panel-heading {
			color: #333!important;
			background-color: #f5f5f5!important;
			border-color: #ddd!important;
	</style>

	<body class="no-skin gray-bg">
		<div class="main-content-inner">
			<div class="wrapper wrapper-content animated fadeInUp">
				<div class="row">
					<div class="col-sm-12"><h2 class="m-t-xs">${pd.customerName } <small class="m-l-md">销售人: ${pd.userName }</small></h2></div>
					<div class="col-xs-12">
						<ul class="nav nav-tabs" id="myTab">
							<li class="active">
								<a data-toggle="tab" href="#tab6">订单全景</a>
							</li>
							<li>
								<a data-toggle="tab" href="#tab3">订单详情</a>
							</li>
							<c:if test="${  empty pd.types||pd.types==1}">
								<li>
									<a data-toggle="tab" href="#tab2">客户信息</a>
								</li>
							</c:if>
							<c:if test="${  empty pd.types||pd.types==2}">
								<li>
									<a data-toggle="tab" href="#tab1">债务人(公司)信息</a>
								</li>
							</c:if>
							<li>
								<a data-toggle="tab" href="#tab4">相关附件</a>
							</li>
							<c:if test="${  not empty pd.flags}">
								<li>
									<a data-toggle="tab" href="#tab5">操作日志</a>
								</li>
							</c:if>

						</ul>
						<form action="order/${msg }.do?type=${pd.type}" name="contractForm" id="contractForm" method="post" class="form form-horizontal">
							<input type="hidden" name="orederAttachType" id="orederAttachType" value="${pd.orederAttachType}" />
							<div class="tab-content">
								<div id="tab1" class="tab-pane in">
									<div class="panel panel-primary">
										<div class="panel-heading">
											债务人信息
										</div>
										<div class="ibox-content">
											<div class="row">
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">债务人(公司)：</label>
														<div class="col-sm-10">
															<input class="form-control" readonly="readonly" type="text" name="debtorName" id="debtorName" value="${pd.debtorName }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">债务公司电话：</label>
														<div class="col-sm-10">
															<input class="form-control" readonly="readonly" type="text" name="debtorMobilePhone" id="debtorMobilePhone" value="${pd.debtorMobilePhone }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">公司传真：</label>
														<div class="col-sm-10">
															<input class="form-control" readonly="readonly" type="text" name="debtorFax" id="debtorFax" value="${pd.debtorFax }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">其他联系：</label>
														<div class="col-sm-10">
															<input class="form-control" readonly="readonly" type="text" name="debtorOtherContract" id="debtorOtherContract" value="${pd.debtorOtherContract }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">所属省份：
															</label>
														<div class="col-sm-10">
															<input type="text" disabled class="form-control" value="${pd.provinceName1 }" />
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">所属市：
																</label>
														<div class="col-sm-10">

															<input type="text" disabled class="form-control" value="${pd.cityName1}" />
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">具体地址：</label>
														<div class="col-sm-10">
															<input class="form-control" type="text" readonly="readonly" name="debtorAddress" id="debtorAddress" value="${pd.debtorAddress }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">邮政编码：</label>
														<div class="col-sm-10">
															<input class="form-control" readonly="readonly" type="text" name="debtorPostcode" id="debtorPostcode" value="${pd.debtorPostcode }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">债务人营业现状：</label>
														<div class="col-sm-10">
															<input class="form-control" readonly="readonly" type="text" name="debtorStatus" id="debtorStatus" value="<c:if test=" ${pd.debtorStatus==0 } ">营业中</c:if><c:if test="${pd.debtorStatus==1 } ">停止/暂停营业（但营业主体存在）</c:if><c:if test="${pd.debtorStatus==2 } ">不详</c:if><c:if test="${pd.debtorStatus==3 } ">债务主体破产/已注销）</c:if>">
														</div>
													</div>
												</div>
											</div>
											<div class="clearfix"></div>
											<div class="panel panel-default">
												<div class="panel-heading">
													债务知情人
												</div>
												<div class="panel-body">
													<c:if test="${not empty pd.linkMan1 }">
														<c:forEach items="${pd.linkMan1 }" var="linkMan1">
															<div class="col-sm-6">
																<div class="contact-box shadow">
																	<div class="row m-b-xs"><b class="col-sm-4">姓名：</b>
																		<div class="col-sm-8">
																			<a href="javascript:;" title="姓名" style="text-decoration: underline;">
																				${linkMan1.zqrName }
																			</a>
																		</div>
																	</div>
																	<div class="row m-b-xs"><b class="col-sm-4">手机：</b>
																		<div class="col-sm-8">
																			<a href="javascript:;" title="手机" style="text-decoration: underline;">
																				${linkMan1.zqrMobilePhone }
																			</a>
																		</div>
																	</div>
																	<div class="row m-b-xs"><b class="col-sm-4">电话：</b>
																		<div class="col-sm-8">
																			<a href="javascript:;" title="电话" style="text-decoration: underline;">
																				${linkMan1.zqrLandline }
																			</a>
																		</div>
																	</div>
																	<div class="row m-b-xs"><b class="col-sm-4">职务：</b>
																		<div class="col-sm-8">
																			<a href="javascript:;" title="职务" style="text-decoration: underline;">
																				${linkMan1.zqrPostion }
																			</a>
																		</div>
																	</div>
																</div>
															</div>
														</c:forEach>
													</c:if>
												</div>
											</div>
											<div class="panel panel-default">
												<div class="panel-heading">
													其他负责人
												</div>
												<div class="panel-body">
													<c:if test="${not empty pd.linkMan2 }">
														<c:forEach items="${pd.linkMan2 }" var="linkMan2">
															<div class="col-sm-6">
																<div class="contact-box shadow">
																	<div class="row m-b-xs"><b class="col-sm-4">姓名：</b>
																		<div class="col-sm-8">
																			<a href="javascript:;" title="姓名" style="text-decoration: underline;">
																				${linkMan2.fzrName }
																			</a>
																		</div>
																	</div>
																	<div class="row m-b-xs"><b class="col-sm-4">手机：</b>
																		<div class="col-sm-8">
																			<a href="javascript:;" title="手机" style="text-decoration: underline;">
																				${linkMan2.fzrMobilePhone }
																			</a>
																		</div>
																	</div>
																	<div class="row m-b-xs"><b class="col-sm-4">电话：</b>
																		<div class="col-sm-8">
																			<a href="javascript:;" title="电话" style="text-decoration: underline;">
																				${linkMan2.fzrLandline }
																			</a>
																		</div>
																	</div>
																	<div class="row m-b-xs"><b class="col-sm-4">职务：</b>
																		<div class="col-sm-8">
																			<a href="javascript:;" title="职务" style="text-decoration: underline;">
																				${linkMan2.fzrPostion }
																			</a>
																		</div>
																	</div>
																</div>
															</div>
														</c:forEach>
													</c:if>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div id="tab2" class="tab-pane in">

									<div class="mark panel panel-primary">
										<div class="panel-heading">
											债权人信息
										</div>
										<div class="ibox-content">
											<div class="row">
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">债权人(公司)：</label>
														<div class="col-sm-10">
															<input class="form-control" data-validate="require" readonly="readonly" type="text" name="customerName" id="customerName" value="${pd.customerName }" onclick="chooseCustomer()">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">传真:</label>
														<div class="col-sm-10">
															<input class="form-control" type="text" disabled="disabled" name="customerFax" id="customerFax" value="${pd.customerFax }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">邮政编码:</label>
														<div class="col-sm-10">
															<input class="form-control" type="text" disabled="disabled" name="customerPostCode" id="customerPostCode" value="${pd.customerPostCode }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">开户行:</label>
														<div class="col-sm-10">
															<input class="form-control" type="text" disabled name="customerBankAccountName" id="customerBankAccountName" value="${pd.customerBankAccountName }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">开户行账号:</label>
														<div class="col-sm-10">
															<input class="form-control" type="text" disabled name="customerBankNumber" id="customerBankNumber" value="${pd.customerBankNumber }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">所属省:</label>
														<div class="col-sm-10">
															<input class="form-control" type="text" disabled name="provinceName" id="provinceName" value="${pd.provinceName }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">所属市:</label>
														<div class="col-sm-10">
															<input class="form-control" type="text" disabled name="cityName" id="cityName" value="${pd.cityName }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">具体地址:</label>
														<div class="col-sm-10">
															<input class="form-control" type="text" disabled name="customerAddress" id="customerAddress" value="${pd.customerAddress }">
														</div>
													</div>
												</div>

												<div class="clearfix"></div>
												<div class="col-md-12">
													<div class="panel panel-default">
														<div class="panel-heading">
															案件知情人
														</div>
														<div class="panel-body">
															<c:if test="${not empty pd.linkMan3 }">
																<c:forEach items="${pd.linkMan3 }" var="linkMan3">
																	<div class="col-sm-6">
																		<div class="contact-box shadow">
																			<div class="row m-b-xs"><b class="col-sm-4">姓名：</b>
																				<div class="col-sm-8">
																					<a href="javascript:;" title="姓名" style="text-decoration: underline;">
																						${linkMan3.ajzqrName }
																					</a>
																				</div>
																			</div>
																			<div class="row m-b-xs"><b class="col-sm-4">手机：</b>
																				<div class="col-sm-8">
																					<a href="javascript:;" title="手机" style="text-decoration: underline;">
																						${linkMan3.ajzqrMobilePhone }
																					</a>
																				</div>
																			</div>
																			<div class="row m-b-xs"><b class="col-sm-4">电话：</b>
																				<div class="col-sm-8">
																					<a href="javascript:;" title="电话" style="text-decoration: underline;">
																						${linkMan3.ajzqrLandline }
																					</a>
																				</div>
																			</div>
																			<div class="row m-b-xs"><b class="col-sm-4">职务：</b>
																				<div class="col-sm-8">
																					<a href="javascript:;" title="职务" style="text-decoration: underline;">
																						${linkMan3.ajzqrPostion }
																					</a>
																				</div>
																			</div>
																		</div>
																	</div>
																</c:forEach>
															</c:if>
														</div>
													</div>

													<div class="panel panel-default">
														<div class="panel-heading">
															工作汇报对象人
														</div>
														<div class="panel-body">
															<c:if test="${not empty pd.linkMan4 }">
																<c:forEach items="${pd.linkMan4}" var="linkMan4">
																	<div class="col-sm-6">
																		<div class="contact-box shadow">
																			<div class="row m-b-xs"><b class="col-sm-4">姓名：</b>
																				<div class="col-sm-8">
																					<a href="javascript:;" title="姓名" style="text-decoration: underline;">
																						${linkMan4.hbdxrName }
																					</a>
																				</div>
																			</div>
																			<div class="row m-b-xs"><b class="col-sm-4">手机：</b>
																				<div class="col-sm-8">
																					<a href="javascript:;" title="手机" style="text-decoration: underline;">
																						${linkMan4.MobilePhone }
																					</a>
																				</div>
																			</div>
																			<div class="row m-b-xs"><b class="col-sm-4">电话：</b>
																				<div class="col-sm-8">
																					<a href="javascript:;" title="电话" style="text-decoration: underline;">
																						${linkMan4.hbdxrLandline }
																					</a>
																				</div>
																			</div>
																			<div class="row m-b-xs"><b class="col-sm-4">职务：</b>
																				<div class="col-sm-8">
																					<a href="javascript:;" title="职务" style="text-decoration: underline;">
																						${linkMan4.hbdxrPostion }
																					</a>
																				</div>
																			</div>
																		</div>
																	</div>
																</c:forEach>
															</c:if>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div id="tab3" class="tab-pane in">
									<div class="mark panel panel-primary" style="width: 50%;">
										<div class="panel-heading">
											基本信息
										</div>
										<div class="panel-body">
											<div class="col-md-12">
												<div class="form-group">
													<label class="col-sm-2 control-label">订单编号：</label>
													<div class="col-sm-10">
														<input class="form-control" readonly="readonly" type="text" name="orderNo" id="orderNo" value="${pd.orderNo }">
													</div>
												</div>
											</div>
											<div class="col-md-12">
												<div class="form-group">
													<label class="col-sm-2 control-label">委托时间：</label>
													<div class="col-sm-10">
														<input class="span10 date-picker form-control" data-validate="require" readonly="readonly" data-date-format="yyyy-mm-dd" type="text" name="orderPlacement" value="${pd.orderPlacement }">
													</div>
												</div>
											</div>
											<div class="col-md-12">
												<div class="form-group">
													<label class="col-sm-2 control-label">欠款总金额(元)：</label>
													<div class="col-sm-10">
														<input class="form-control" readonly="readonly" type="text" placeholder="这里输入欠款总金额" name="debtAmount" id="debtAmount" value="${pd.debtAmount }">
													</div>
												</div>
											</div>
											<div class="col-md-12">
												<div class="form-group">
													<label class="col-sm-2 control-label">最近一次付款时间：</label>
													<div class="col-sm-10">
														<input class="span10 date-picker form-control" readonly="readonly" data-date-format="yyyy-mm-dd" type="text" name="lastPaymentDate" id="lastPaymentDate" value="${pd.lastPaymentDate }">
													</div>
												</div>
											</div>
											<div class="col-md-12">
												<div class="form-group">
													<label class="col-sm-2 control-label">账龄(月)：</label>
													<div class="col-sm-10">
														<input class="form-control" readonly="readonly" type="text" name="ageOfDebt" id="ageOfDebt" value="${pd.ageOfDebt }">
													</div>
												</div>
											</div>
											<div class="col-md-12">
												<div class="form-group">
													<label class="col-sm-2 control-label">佣金类型：
												</label>
													<div class="col-sm-10">
														<select class="form-control" disabled id="commType" name="cType">
															<option value="0" <c:if test="${pd.cType == 0 }"> selected </c:if> >佣金比例</option>
															<option value="1" <c:if test="${pd.cType == 1 }"> selected </c:if>>固定金额</option>
														</select>
													</div>
												</div>
											</div>
											<c:if test="${pd.cType == 0 }">
												<div id="commType0">
													<c:if test="${pd.status==0 }">
														<div class="col-md-12">
															<div class="form-group">
																<label class="col-sm-2 control-label">佣金比例(%)：
												</label>
																<div class="col-sm-10">
																	<input class="form-control" disabled type="text" placeholder="这里输入非诉讼佣金比例" name="commissionRate" id="" value="${pd.commissionRate }" onkeyup = "value=value.replace(/[^\d]/g,'')">
																</div>
															</div>
														</div>
														<div class="col-md-12">
															<div class="form-group">
																<label class="col-sm-2 control-label">佣金比例折扣：<br>(1-100)&nbsp;&nbsp;&nbsp;&nbsp;
												</label>
																<div class="col-sm-10">
																	<input class="form-control" type="text" disabled="disabled" placeholder="这里输入非诉讼佣金比例折扣" name="discount" id="" value="${pd.discount }">
																</div>
															</div>
														</div>
													</c:if>

													<c:if test="${pd.status==3||pd.status==4 }">
														<input type="hidden" value="${pd.commissionRate }" name="commissionRate" />
														<input type="hidden" value="${pd.discount }" name="discount" />
														<div class="col-md-12">
															<div class="form-group">
																<label class="col-sm-2 control-label">佣金比例(%)：
												</label>
																<div class="col-sm-10">
																	<input class="form-control" disabled type="text" placeholder="这里输入诉讼仲裁佣金比例" name="lawCommissionRate" id="" value="${pd.lawCommissionRate }" onkeyup = "value=value.replace(/[^\d]/g,'')">
																</div>
															</div>
														</div>
														<div class="col-md-12">
															<div class="form-group">
																<label class="col-sm-2 control-label">佣金折扣：<br>(1-100)&nbsp;&nbsp;&nbsp;&nbsp;
												</label>
																<div class="col-sm-10">
																	<input class="form-control" disabled type="text" placeholder="这里输入诉讼仲裁佣金折扣" name="susongdiscount" id="" value="${pd.susongdiscount }">
																</div>
															</div>
														</div>
													</c:if>
												</div>
											</c:if>
											<c:if test="${pd.cType ==1 }">
												<div id="commType1">
													<div class="col-md-12">
														<div class="form-group">
															<label class="col-sm-2 control-label">佣金金额：
												</label>
															<div class="col-sm-10">
																<input class="form-control" disabled="disabled" number="true" type="text" placeholder="这里输入佣金金额" name="fixedMoney" value="${pd.fixedMoney }" onkeyup = "value=value.replace(/[^\d]/g,'')" >
															</div>
														</div>
													</div>
												</div>
											</c:if>
											<div class="col-md-12">
												<div class="form-group">
													<label class="col-sm-2 control-label">销售人：</label>
													<div class="col-sm-10">
														<input class="form-control" data-validate="maxLength=50" readonly="readonly" type="text" name="userName" id="userName" value="${pd.userName }">
													</div>
												</div>
											</div>
											<div class="col-md-12">
												<div class="form-group">
													<label class="col-sm-2 control-label">债务性质类型：</label>
													<div class="col-sm-10">
														<input class="form-control" readonly="readonly" type="text" name="userName" id="userName" value="<c:if test="${pd.debtType==0 }">产品交易欠款</c:if><c:if test="${pd.debtType==1 }">信用保险索赔欠款</c:if><c:if test="${pd.debtType==3 }">其他类型</c:if>">
													</div>
												</div>
											</div>
											<c:if test="${pd.debtType == 3 }">
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">其他具体类型：</label>
														<div class="col-sm-10">
															<input class="form-control" readonly="readonly" type="text" name="otherdebtDetail" id="otherdebtDetail" value="${pd.otherdebtDetail }">
														</div>
													</div>
												</div>
											</c:if>
											<div class="zwxz col-md-12"></div>
											<div class="col-md-12">
												<div class="form-group">
													<label class="col-sm-2 control-label">案件附件类型：</label>
													<div class="col-sm-10">
														<div class="checkbox checkbox-success checkbox-inline">
															<input type="checkbox" disabled="disabled" id="inlineCheckbox1" name="orederAttachType1" value="${pd.orederAttachType }">
															<label for="inlineCheckbox1"> 合同/订货单 </label>
														</div>
														<div class="checkbox checkbox-success checkbox-inline">
															<input type="checkbox" disabled="disabled" id="inlineCheckbox2" name="orederAttachType1" value="${pd.orederAttachType }">
															<label for="inlineCheckbox2"> 对账单/索款函 </label>
														</div>
														<!-- 								                              <div class="checkbox checkbox-success checkbox-inline"> -->
														<%-- 								                                        <input type="checkbox"  disabled="disabled" id="inlineCheckbox3" name="orederAttachType1" value="${pd.orederAttachType }" > --%>
														<!-- 								                                        <label for="inlineCheckbox3"> 合同/订货单 </label> -->
														<!-- 								                             </div> -->
														<div class="checkbox checkbox-success checkbox-inline">
															<input type="checkbox" disabled="disabled" id="inlineCheckbox4" name="orederAttachType1" value="${pd.orederAttachType }">
															<label for="inlineCheckbox4"> 账款明细帐 </label>
														</div>
														<div class="checkbox checkbox-success checkbox-inline">
															<input type="checkbox" disabled="disabled" id="inlineCheckbox5" name="orederAttachType1" value="${pd.orederAttachType }">
															<label for="inlineCheckbox5"> 往来信函 </label>
														</div>
														<div class="checkbox checkbox-success checkbox-inline">
															<input type="checkbox" disabled="disabled" id="inlineCheckbox6" name="orederAttachType1" value="${pd.orederAttachType }">
															<label for="inlineCheckbox6"> 案件情况介绍 </label>
														</div>
														<div class="checkbox checkbox-success checkbox-inline">
															<input type="checkbox" disabled="disabled" id="inlineCheckbox7" name="orederAttachType1" value="${pd.orederAttachType }">
															<label for="inlineCheckbox7"> 运货单/发票 </label>
														</div>
														<div class="checkbox checkbox-success checkbox-inline">
															<input type="checkbox" disabled="disabled" id="inlineCheckbox8" name="orederAttachType1" value="${pd.orederAttachType }">
															<label for="inlineCheckbox8"> 欠款确认函/付款计划 </label>
														</div>
														<div class="checkbox checkbox-success checkbox-inline">
															<input type="checkbox" disabled="disabled" id="inlineCheckbox9" name="orederAttachType1" value="${pd.orederAttachType }">
															<label for="inlineCheckbox9"> 判决书/执行申请 </label>
														</div>
														<div class="checkbox checkbox-success checkbox-inline">
															<input type="checkbox" disabled="disabled" id="inlineCheckbox10" name="orederAttachType1" value="${pd.orederAttachType }">
															<label for="inlineCheckbox10"> 债务人调查文件 </label>
														</div>
													</div>
												</div>
											</div>
											<div class="col-md-12">
												<div class="form-group">
													<label class="col-sm-2 control-label">最近一次联络时间：</label>
													<div class="col-sm-10">
														<input class="form-control" readonly="readonly" type="text" name="lastContactTime" id="lastContactTime" value="${pd.lastContactTime }">
													</div>
												</div>
											</div>
											<div class="col-md-12">
												<div class="form-group">
													<label class="col-sm-2 control-label">债务有无争议：</label>
													<div class="col-sm-10">
														<div class="radio radio-success radio-inline">
															<input type="radio" disabled="disabled" <c:if test="${ empty pd.isDispute || pd.isDispute == '0'}">checked="checked"</c:if> onchange="setDispute('0')" id="form-isDispute-radio1" name="form-isDispute-radio">
															<label for="form-isDispute-radio1"> 无 </label>
														</div>
														<div class="radio radio-success radio-inline">
															<input type="radio" disabled="disabled" <c:if test="${pd.isDispute == '1'}">checked="checked"</c:if> onchange="setDispute('1')" id="form-isDispute-radio2" name="form-isDispute-radio">
															<label for="form-isDispute-radio2"> 有 </label>
														</div>
													</div>
												</div>
											</div>
											<div class="col-md-12">
												<div class="form-group">
													<label class="control-label col-sm-2">
														其他拖欠理由:
													</label>
													<div class="col-sm-10">
														<textarea class="form-control" style="height: 120px;" name="nonPayReasons" readonly="readonly">${pd.nonPayReasons}</textarea>
													</div>
												</div>
											</div>
											<div class="col-md-12">
												<div class="form-group">
													<label class="control-label col-sm-2">
														已往收款行为描述:
													</label>
													<div class="col-sm-10">
														<textarea class="form-control" style="height: 120px;" name="collectHistory" readonly="readonly">${pd.collectHistory}</textarea>
													</div>
												</div>
											</div>
											<div class="col-md-12">
												<div class="form-group">
													<label class="control-label col-sm-2">
														其他说明:
													</label>
													<div class="col-sm-10">
														<textarea class="form-control" style="height: 120px;" name="remark" readonly="readonly">${pd.othersRequest}</textarea>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div id="tab4" class="tab-pane in">
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
															<c:forEach items="${pd.attachs }" var="attachs">
																<tr>
																	<td>${attachs.originalFilename }</td>
																	<td>${attachs.type }</td>
																	<td>${attachs.uploader }</td>
																	<td>${attachs.uploadTime }</td>
																	<td>
																		<a target="_blank" href="${attachs.url }" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a> <input type="hidden" name="fileSize" value="${attachs.fileSize }"><input type="hidden" name="uploader" value="${attachs.uploader }"><input type="hidden" name="originalFilename" value="${attachs.originalFilename }"><input type="hidden" name="uploadTime" value="${attachs.uploadTime }"><input type="hidden" name="realPath" value="${attachs.realPath }"><input type="hidden" name="url" value="${attachs.url }"><input type="hidden" name="attachId" value="${attachs.id }"></td>
																</tr>
															</c:forEach>
														</c:if>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>

								<div class="tab-pane in" id="tab5">
									<div class="mark panel panel-primary">
										<div class="panel-heading">操作日志</div>
										<div class="ibox-content">
											<div class="row">
												<table class="table table-bordered">
													<thead>
														<tr>
															<th>操作时间</th>
															<th>操作人员</th>
															<th>操作类型</th>
															<th>旧字段值</th>
															<th>新字段值</th>
														</tr>
													</thead>
													<tbody>
														<c:if test="${not empty pd.logsInfo }">
															<c:forEach items="${pd.logsInfo }" var="attachs">
																<tr>
																	<td>${attachs.createTime }</td>
																	<td>${attachs.name }</td>
																	<td>${attachs.type }</td>
																	<td>${attachs.qzdz }</td>
																	<td>${attachs.hzdz }</td>
																</tr>
															</c:forEach>
														</c:if>
													</tbody>
												</table>
											</div>
											<div class="clearfix"></div>

										</div>
									</div>
								</div>

								<div class="tab-pane in active" id="tab6">
									<div class="mark panel panel-primary" style="width: 50%;">
										<div class="panel-heading">
											订单全景
										</div>
										<div class="ibox-content">
											<div class="row form form-horizontal">
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">债权人(公司)：</label>
														<div class="col-sm-10">
															<input class="form-control" data-validate="require" readonly="readonly" type="text" name="customerName" id="customerName" value="${pd.customerName }" onclick="chooseCustomer()">
														</div>
													</div>
												</div>

												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">委托时间：</label>
														<div class="col-sm-10">
															<input class="span10 date-picker form-control" data-validate="require" readonly="readonly" data-date-format="yyyy-mm-dd" type="text" name="orderPlacement" value="${pd.orderPlacement }">
														</div>
													</div>
												</div>

												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">债务人(公司)：</label>
														<div class="col-sm-10">
															<input class="form-control" readonly="readonly" type="text" name="debtorName" id="debtorName" value="${pd.debtorName }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">债务公司电话：</label>
														<div class="col-sm-10">
															<input class="form-control" readonly="readonly" type="text" name="debtorMobilePhone" id="debtorMobilePhone" value="${pd.debtorMobilePhone }">
														</div>
													</div>
												</div>

												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">欠款总金额(元)：</label>
														<div class="col-sm-10">
															<input class="form-control" readonly="readonly" type="text" placeholder="这里输入欠款总金额" name="debtAmount" id="debtAmount" value="${pd.debtAmount }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">最近一次付款时间：</label>
														<div class="col-sm-10">
															<input class="span10 date-picker form-control" readonly="readonly" data-date-format="yyyy-mm-dd" type="text" name="lastPaymentDate" id="lastPaymentDate" value="${pd.lastPaymentDate }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">账龄(月)：</label>
														<div class="col-sm-10">
															<input class="form-control" readonly="readonly" type="text" name="ageOfDebt" id="ageOfDebt" value="${pd.ageOfDebt }">
														</div>
													</div>
												</div>
												<c:if test="${pd.cType ==0 }">
													<div class="col-md-12">
														<div class="form-group">
															<label class="col-sm-2 control-label">佣金比例(%)：</label>
															<div class="col-sm-10">
																<input class="form-control" readonly="readonly" type="text" name="commissionRate" id="commissionRate" value="${not empty pd.lawCommissionRate?pd.lawCommissionRate:pd.commissionRate }">
															</div>
														</div>
													</div>
												</c:if>
												<c:if test="${pd.cType ==1 }">
													<div class="col-md-12">
														<div class="form-group">
															<label class="col-sm-2 control-label">佣金金额：
												</label>
															<div class="col-sm-10">
																<input class="form-control" disabled="disabled" number="true" type="text" placeholder="这里输入佣金金额" name="fixedMoney" value="${pd.fixedMoney }">
															</div>
														</div>
													</div>

												</c:if>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">销售人：</label>
														<div class="col-sm-10">
															<input class="form-control" data-validate="maxLength=50" readonly="readonly" type="text" name="userName" id="userName" value="${pd.userName }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label">债务性质类型：</label>
														<div class="col-sm-10">
															<input class="form-control" readonly="readonly" type="text" name="userName" id="userName" value="<c:if test="${pd.debtType==0 }">产品交易欠款</c:if><c:if test="${pd.debtType==1 }">信用保险索赔欠款</c:if><c:if test="${pd.debtType==3 }">其他类型</c:if>">
														</div>
													</div>
												</div>
												<c:if test="${pd.debtType == 3 }">
													<div class="col-md-12">
														<div class="form-group">
															<label class="col-sm-2 control-label">其他具体类型：</label>
															<div class="col-sm-10">
																<input class="form-control" readonly="readonly" type="text" name="otherdebtDetail" id="otherdebtDetail" value="${pd.otherdebtDetail }">
															</div>
														</div>
													</div>
												</c:if>
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
		<!-- /.row -->
		</div>
		</div>
		<style>
			.mark {
				padding: 0;
			}
		</style>
		<%@ include file="../../system/index/js.jsp"%>
	</body>
	<script type="text/javascript">
		$(function() {
			var attachType = '${pd.orederAttachType}'
			if(attachType) {
				$("input[name='orederAttachType1']").each(function() {
					if(attachType.indexOf($(this).next().html()) != -1) {
						$(this).attr("checked", true);
					}
				});
			}

			var dispute = '${pd.isDispute}'
			if("1" == dispute) {
				setDispute(1);
			}

		});

		function setDispute(value) {
			$("#isDispute").val(value);
			if(value == 1) {
				$(".zyxx").append(
					'<div class="form-group">' +
					'<label class="col-sm-2 control-label">争议原因：</label>' +
					'<div class="col-sm-10">' +
					'<textarea class="form-control" style="height: 120px;"  name="disputedReasons"  readonly="readonly">${pd.disputedReasons}</textarea>' +
					'</div>' +
					'</div>'
				)
			} else {
				$(".zyxx").empty();
			}
		}
	</script>

</html>