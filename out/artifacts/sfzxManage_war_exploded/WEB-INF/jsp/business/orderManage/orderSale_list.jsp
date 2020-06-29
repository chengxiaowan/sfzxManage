<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<base href="${pageContext.request.contextPath}/">
		<%@ include file="../../system/index/top.jsp"%>

		<body class="gray-bg">
			<div class="wrapper wrapper-content animated fadeInUp">
				<!-- /section:basics/sidebar -->
				<div class="ibox-content">
					<div class="row">
						<div class="col-xs-12">
							<!-- 检索  -->
							<form class="form-inline" role="form" action="order/listSaleOrder.do" method="post" name="customerForm" id="customerForm">
								<div class="form-group">
									<input id="nav-search-input" name="keywords" style="width: 398px" placeholder="请输入客户名称、债务人(公司)、运作" value="${pd.keywords }" class="form-control" type="text">
								</div>
								<div class="form-group">
									<input class="span10 date-picker form-control" id="createTimeStart" name="startTime" value="${fn:substring(pd.startTime, 0, 10)}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="开始日期" title="回款时间开始" />
								</div>
								<div class="form-group">
									<input class="span10 date-picker form-control" id="createTimeEnd" name="endTime" value="${fn:substring(pd.endTime, 0, 10)}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="结束日期" title="回款时间结束" />
								</div>
								<%-- <div class="form-group">
							案件类型 
							<select id="type" name="type" class="form-control" style="width: 100px;" data-placeholder="案件类型">
								<option value="" <c:if test="${empty pd.type || pd.type == null || pd.type == ''}">selected</c:if>>全部</option>
								<option value="0" <c:if test="${pd.type == '0'}">selected</c:if>>非诉讼</option>
								<option value="1" <c:if test="${pd.type == '1'}">selected</c:if>>诉讼</option>
								<option value="2" <c:if test="${pd.type == '2'}">selected</c:if>>仲裁</option>
							</select>
						</div> --%>
								<div class="form-group">
									案件状态
									<select id="status1" name="status1" class="form-control" style="width: 100px;" data-placeholder="案件状态">
										<option value="">全部</option>
										<option value="0" <c:if test="${pd.status1 == '0'}">selected</c:if>>运作中</option>
										<option value="1" <c:if test="${pd.status1 == '1'}">selected</c:if>>已结案</option>
										<option value="2" <c:if test="${pd.status1 == '2'}">selected</c:if>>已关闭</option>
										<option value="3" <c:if test="${pd.status1 == '3'}">selected</c:if>>诉讼中</option>
										<option value="4" <c:if test="${pd.status1 == '4'}">selected</c:if>>仲裁中</option>
									</select>
								</div>
								<%-- <div class="form-group">
							是否归档 <select id="isFiled" name="isFiled" class="form-control" style="width: 100px;" data-placeholder="是否归档">
								<option value="" <c:if test="${empty pd.isFiled || pd.isFiled == null || pd.isFiled == ''}">selected</c:if>>全部</option>
								<option value="0" <c:if test="${pd.isFiled == '0'}">selected</c:if>>否</option>
								<option value="1" <c:if test="${pd.isFiled == '1'}">selected</c:if>>是</option>
							</select>
						</div> --%>
								<button type="submit" class="btn btn-primary"><i class="fa fa-search"></i> 搜索</button>
								<!--<button type="reset" class="btn">
							<i id="nav-search-icon" class="fa fa-refresh nav-search-icon "></i> 重置
						</button>-->

								<div class="pull-right">
									<%-- <c:if test="${QX.add == 1 }">
								<a class="btn btn-success" onclick="add();"><i class="fa fa-plus"></i> 新增</a>
							</c:if> --%>
									<%-- <c:if test="${QX.del == 1 }">
								<a title="批量删除" class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');"><i class='ace-icon fa fa-trash-o'></i> 删除</a>
							</c:if> --%>
								</div>
							</form>
						</div>
						<div class="col-md-12" style="margin-top: 10px;">
							<c:if test="${(isYunzuoAdmin||isKefu) &&(!isKefu1) }">
								<a class="btn btn-info" id="btn-distribution" onclick="run(this);" disabled> <i class="fa fa-send-o" title="运作分配"></i> 分配运作人
								</a>
							</c:if>
							<c:if test="${isKefu &&(!isKefu1) }">
								<a class="btn btn-info" id="btn-close" onclick="total(this,5);" disabled> <i class="fa fa-remove" title="关闭"></i> 关闭
								</a>
							</c:if>
							<c:if test="${isKefu &&(!isKefu1) }">
								<a class="btn btn-info" id="btn-end" onclick="total(this,6);" disabled> <i class="fa fa-send-o" title="结案"></i> 结案
								</a>
							</c:if>
							<c:if test="${isKefu}">
								<a class="btn btn-info" id="btn-placeon" onclick="total(this,8);"> <i class="fa fa-folder-o" title="归档"></i> 归档
								</a>
							</c:if>
						</div>
					</div>
				</div>
				<div class="ibox-content">
					<div class="htz-box-no col-sm-2" style="cursor: pointer;">
						<span class="pull-left"><img src="/static/ui/img/10icon/h.png"></span>
						<div class="pull-left">
							<div>欠款总金额</div>
							<div class="blue">¥ ${pd.qkzje }</div>
						</div>
					</div>
					<div class="htz-box-no col-sm-2" style="cursor: pointer;">
						<span class="pull-left"><img src="/static/ui/img/10icon/t.png"></span>
						<div class="pull-left">
							<div>已回款总金额</div>
							<div class="blue">¥ ${pd.hkzje }</div>
						</div>
					</div>
					<div class="htz-box-no col-sm-2" style="cursor: pointer;">
						<span class="pull-left"><img src="/static/ui/img/10icon/z.png"></span>
						<div class="pull-left">
							<div>剩余总金额</div>
							<div class="blue">¥ ${pd.syzje }</div>
						</div>
					</div>

					<!--<div class="alert alert-warning">
						<span class="alert-link"> 合计: </span> 欠款总金额 <span class="alert-link" style="font-size:18px"> ${pd.qkzje } </span> 元 已回款总金额<span class="alert-link" style="font-size:18px"> ${pd.hkzje  }</span> 元 剩余总金额
						<span class="alert-link" style="font-size:18px"> ${pd.syzje  }</span> 元
					</div>-->
					<table id="simple-table" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center" style="width: 35px;">
									<input type='checkbox' class="checkAll-hook" />
								</th>
								<th class="center" style="width: 50px;">序号</th>
								<th class="center">客户名称</th>
								<th class="center">债务人(公司)</th>
								<th class="center">欠款总金额(元)</th>
								<th class="center">已回款金额(元)</th>
								<th class="center">剩余金额(元)</th>
								<th class="center">案件状态</th>
								<th class="center">合同签约日期</th>
								<th class="center">合同到期日期</th>
								<th class="center">订单委托日期</th>
								<th class="center">运作</th>
								<!-- 	<th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>录入时间</th> -->
							</tr>
						</thead>
						<tbody>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty orderList}">
									<c:if test="${QX.cha == 1 }">
										<c:forEach items="${orderList}" var="list" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;"><label><input type='checkbox' name='ids' value="${list.id }" data-id="${list.id }" data-runname="${list.runnerName }" data-runid="${list.runnerId }" data-status="${list.status }" data-type="${list.type }" data-filed="${list.isFiled }" /><span
												class="lbl"></span></label></td>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td class="center">${list.custoemrName}</td>
												<td class="center">${list.debtorName}</td>
												<td class="center">${list.debtAmount}</td>
												<td class="center">${list.totalMoney}</td>
												<td class="center">${list.syMoney}</td>
												<td class="center">
													<c:if test="${list.status==0 }">运作中</c:if>
													<c:if test="${list.status==1 }">已结案</c:if>
													<c:if test="${list.status==2 }">已关闭</c:if>
													<c:if test="${list.status==3 }">诉讼中</c:if>
													<c:if test="${list.status==4 }">仲裁中</c:if>
												</td>
												<%-- <td class="center">${fn:substring(list.createTime, 0, 19)}</td> --%>
												<td class="center">${fn:substring(list.signingTime, 0, 10)}</td>
												<td class="center">${fn:substring(list.endTime, 0, 10)}</td>
												<td class="center">${fn:substring(list.createTime, 0, 10)}</td>
												<td class="center">${list.runnerName}</td>
												<!-- <td class="center"> -->
												<%-- <a title="点击查看详情" class="btn btn-primary" onclick="viewCustomer('${list.id}')" style="cursor: pointer;"><i class="fa fa-search"></i> 查看</a>
										
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
											</c:if>
												<c:if test="${QX.edit == 1 }">
													<a class="btn btn-success" title="编辑" onclick="edit('${list.id}');"> <i class="ace-icon fa fa-pencil-square-o" title="编辑"></i> 编辑
													</a>
												</c:if>
												<c:if test="${QX.del == 1 }">
													<a class="btn btn-danger" onclick="del('${list.id }','${list.orderNo }');"> <i class="ace-icon fa fa-trash-o" title="删除"></i> 删除
													</a>
												</c:if>
											</td> --%>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="13" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="13" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					<div class="clearfix">
						<div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
					</div>
				</div>
				<!-- /.col -->
			</div>
		</body>
		<!-- /.main-container -->
		<!-- 全局js -->
		<%@ include file="../../system/index/js.jsp"%>

		<script type="text/javascript">
			//已选项目
			var checks = [];
			//删除
			function del(id, msg) {
				layer
					.confirm(
						"确定要删除此项吗?",
						function(result) {
							if(result) {
								var url = "${pageContext.request.contextPath}/order/delete.do?ids=" +
									id + "&tm=" + new Date().getTime();
								$.get(url, function(data) {
									nextPage('${page.currentPage}');
								});
							};
						});
			}

			//新增
			function add() {
				var index = layer.open({
					type: 2,
					title: '新增',
					content: '${pageContext.request.contextPath}/order/goAdd.do',
					area: ['90%', '90%']
				});
			}

			//关闭、结案
			function total(dom, type) {
				var orderId = []
				$.each(checks, function() {
					orderId.push(this.id)
				})
				if(dom.getAttribute("disabled") != null) {
					return;
				}
				var index = layer.open({
					type: 2,
					title: '修改',
					content: '${pageContext.request.contextPath}/order/goTotal.do?type=' + type + "&orderId=" + orderId.join(','),
					area: ['90%', '90%']
				});
			}

			function viewDetail(id) {
				var index = layer.open({
					type: 2,
					title: '查看详情',
					content: '${pageContext.request.contextPath}/order/view.do?id=' + id,
					area: ['90%', '90%']
				});
			}
			//分配运作
			function run(dom) {
				var id = [];
				var runId = [];
				$.each(checks, function() {
					id.push(this.id)
					runId.push(this.runId)
				})
				if(dom.getAttribute("disabled") != null) {
					return;
				}
				//	var status = $checked.data("status");
				//var type = $checked.data("type")
				var title = '待分配的运作';
				var ROLE_ID = "02178e62f17b4926bb7014f3ad5a1ebe";
				/* 		if(status==3 ||status ==4){
							ROLE_ID = 'b693f826af0545b5a1c60447a27c3089,45efba0a0fa946aebf7befe614c74e55';
							title = '待分配的律师'
						}
						if(type ==1 ||type==2){
							ROLE_ID = '45efba0a0fa946aebf7befe614c74e55';
						} */
				console.log(id)
				if(id.length <= 1) {
					var info = {
						id: runId[0],
						name: checks[0].runName
					};
					sessionStorage['runInfo'] = JSON.stringify(info)
				} else {
					sessionStorage['runInfo'] = ''
				}
				var index = layer
					.open({
						type: 2,
						title: title,
						content: '${pageContext.request.contextPath}/user/chooseUser.do?flags=0&ROLE_ID=' + ROLE_ID + '&parIds=userId&parNames=userName&',
						area: ['90%', '90%'],
						btnAlign: 'c',
						btn: ['保存'],
						yes: function(index, layero) {
							sessionStorage['runInfo'] = ''
							var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
							var data = iframeWin.pushToParent()
							layer.close(index)
							var url = "${pageContext.request.contextPath}/order/doRun.do?id=" +
								id.join(",") + "&runnerId=" + data.ids;
							$.get(url, function(data) {
								if(data.error == "00") {
									nextPage('${page.currentPage}');
								}
							});
						}
					});
			}

			function viewCustomer(id) {
				var index = layer.open({
					type: 2,
					title: '查看详情',
					content: '${pageContext.request.contextPath}/order/viewOrder.do?id=' + id,
					area: ['90%', '90%']
				});
			}

			//修改
			function edit(id) {
				var index = layer
					.open({
						type: 2,
						title: '修改',
						content: '${pageContext.request.contextPath}/order/goEdit.do?id=' +
							id,
						area: ['90%', '90%']
					});
			}

			//批量操作

			var btnPlaceOn = $("#btn-placeon"); //归档
			var btnDistribution = $("#btn-distribution"); //分配
			var btnClose = $("#btn-close"); //关闭
			var btnEnd = $("#btn-end"); //结案

			$(function() {
				//订单选择/指可选1
				function checkAuthority() {
					checks = []
					var checkedBoxs = $("#simple-table tbody").find("input[type=checkbox]:checked");
					checkedBoxs.each(function() {
						var $this = $(this);
						checks.push({
							id: this.value,
							runName: $this.data("runname"),
							runId: $this.data("runid"),
							status: $this.data("status"),
							type: $this.data("type"),
							filed: $this.data("filed")
						})
					})
					if(!checks.length) {
						btnPlaceOn.attr("disabled", true);
						btnDistribution.attr("disabled", true);
						btnClose.attr("disabled", true);
						btnEnd.attr("disabled", true);
						return;
					} else {
						btnPlaceOn.attr("disabled", false);
						btnDistribution.attr("disabled", false);
						btnClose.attr("disabled", false);
						btnEnd.attr("disabled", false);
					}

					$.each(checks, function() {
						switch(this.status) { //(0:运作中，1:已结案，2:已关闭，3：诉讼中，4：仲裁中)
							case 0:
								btnPlaceOn.attr("disabled", true)
								break;
							case 1:
								btnClose.attr("disabled", true);
								btnDistribution.attr("disabled", true);
								btnEnd.attr("disabled", true);
								break;
							case 2:
								btnClose.attr("disabled", true);
								btnDistribution.attr("disabled", true);
								btnEnd.attr("disabled", true);
								break;
							case 3:
								btnPlaceOn.attr("disabled", true)
								break;
							case 4:
								btnPlaceOn.attr("disabled", true)
								break;
							default:
								break;
						}
						if(this.filed == 1) btnPlaceOn.attr("disabled", true);
					})
				}

				//全选
				$(document).on("change", ".checkAll-hook", function() {
					var checkBoxs = $("#simple-table tbody").find("input[type=checkbox]")
					if(this.checked) {
						checkBoxs.each(function() {
							this.checked = true;
						})
					} else {
						checkBoxs.prop("checked", false)
						checks = []
					}
					checkAuthority()
				})
				$(document).on("change", "tbody input[type=checkbox]", function() {
					var checkBoxs = $("#simple-table tbody").find("input[type=checkbox]")
					var checkedBoxs = $("#simple-table tbody").find("input[type=checkbox]:checked")
					if(checkBoxs.length == checkedBoxs.length) {
						$(".checkAll-hook").prop("checked", true)
					} else {
						$(".checkAll-hook").prop("checked", false)
					}
					checkAuthority()
				})

				//日期框
				$('.date-picker').datepicker({
					autoclose: true,
					todayHighlight: true
				});

				if($("#createTimeStart").val()) {
					$("#createTimeEnd").datepicker('setStartDate',
						new Date($("#createTimeStart").val()))
				}

				if($("#createTimeEnd").val()) {
					$("#createTimeStart").datepicker('setEndDate',
						new Date($("#createTimeEnd").val()))
				}

				//开始日期
				$("#createTimeStart").datepicker({}).on(
					'changeDate',
					function(ev) {
						if(ev.date) {
							$("#createTimeEnd").datepicker('setStartDate',
								new Date(ev.date.valueOf()))
						} else {
							$("#createTimeEnd").datepicker('setStartDate', null);
						}
					});
				//结束日期
				$("#createTimeEnd").datepicker({}).on(
					'changeDate',
					function(ev) {
						if(ev.date) {
							$("#createTimeStart").datepicker('setEndDate',
								new Date(ev.date.valueOf()))
						} else {
							$("#createTimeStart").datepicker('setEndDate',
								new Date());
						}
					});
				//下拉框
				$('.chosen-select').chosen({
					allow_single_deselect: true
				});
				$(window).off('resize.chosen').on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						var $this = $(this);
						$this.next().css({
							'width': $this.parent().width()
						});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen',
					function(e, event_name, event_val) {
						if(event_name != 'sidebar_collapsed')
							return;
						$('.chosen-select').each(function() {
							var $this = $(this);
							$this.next().css({
								'width': $this.parent().width()
							});
						});
					});
				$('#chosen-multiple-style .btn').on('click', function(e) {
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					if(which == 2)
						$('#form-field-select-4').addClass('tag-input-style');
					else
						$('#form-field-select-4').removeClass('tag-input-style');
				});

			});
		</script>
		</body>

</html>