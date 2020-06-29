<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<base href="${pageContext.request.contextPath}/">
		<%@ include file="../../system/index/top.jsp"%>
		<style>
			.form-group{
				margin-right: 10px;
			}
		</style>
		<body class="gray-bg">
			<div class="wrapper wrapper-content animated fadeInUp">
				<!-- /section:basics/sidebar -->
				<div class="ibox-content">
					<div class="row">
						<div class="col-xs-12">
							<!-- 检索  -->
							<form class="form-inline" role="form" action="contract/<c:if test="${pd.type==1 }">ss</c:if>list.do" method="post" name="contractForm" id="contractForm">
								<input type="hidden" name="type" id="type" value="<c:out value=" ${pd.type} " default="0 "></c:out>" />
								<div class="form-group">
									<input id="nav-search-input" name="keywords" placeholder="请输入客户名称" style="width: 250px" value="${pd.keywords }" class="form-control" type="text">
								</div>
								<div class="form-group">
									<select style="width: 100px" class="form-control chosen-select-sale" name="userId" id="userId">
										<c:if test="${not empty pd.userId }">
											<option value="${pd.userId }" selected="selected">${pd.userName }</option>
										</c:if>
									</select>
								</div>
								<input type="hidden" name="userName" value="${pd.userName }" />
								<input type="hidden" name="swwxName" value="${pd.swwxName }" />
								<!--商务外协-->
								<div class="form-group">
									<select style="width: 130px" class="form-control chosen-select-sale1" name="swwxId" id="swwxId">
										<c:if test="${not empty pd.swwxId }">
											<option value="${pd.swwxId }" selected="selected">${pd.swwxName }</option>
										</c:if>
									</select>
								</div>
								
								
								<div class="form-group">
									<input class="span10 date-picker form-control" id="createTimeStart" style="width: 100px" name="createTimeStart" value="${fn:substring(pd.createTimeStart, 0, 10)}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="开始日期" title="注册时间开始" />
								</div>
								<div class="form-group">
									<input class="span10 date-picker form-control" id="createTimeEnd" style="width: 100px" name="createTimeEnd" value="${fn:substring(pd.createTimeEnd, 0, 10)}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="结束日期" title="注册时间结束" />
								</div>
								<button type="submit" class="btn btn-primary"><i class="fa fa-search"></i> 搜索</button>
								<button type="reset" class="btn cc">
							<i id="nav-search-icon" class="fa fa-refresh nav-search-icon "></i> 重置
						</button>
								<div class="pull-right">
									<c:if test="${QX.add == 1 }">
										<a class="btn btn-success" onclick="add();"><i class="fa fa-plus"></i> 新增</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
										<a title="批量删除" class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');"><i class='ace-icon fa fa-trash-o'></i> 批量删除</a>
									</c:if>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="ibox-content">

					<table id="simple-table" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center" style="width: 35px;"><label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label></th>
								<th class="center" style="width: 50px;">序号</th>
								<th class="center">客户名称</th>
								<th class="center">债务人(公司)</th>
								<th class="center">欠款总金额(元)</th>
								<th class="center">佣金比例</th>
								<th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>签约时间</th>
								<th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>到期时间</th>
								<th class="center">销售人</th>
								<th class="center">商务外协</th>
								<th class="center">操作</th>
							</tr>
						</thead>

						<tbody>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty contractList}">
									<c:if test="${QX.cha == 1 }">
										<c:forEach items="${contractList}" var="list" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;"><label><input type='checkbox' name='ids' value="${list.id }" id="${list.id }" alt="${list.contractNo }" title="${list.contractNo }" class="ace" /><span
												class="lbl"></span></label></td>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td class="center">
													<a title="点击查看详情" onclick="viewCustomer('${list.customerId}')" style="cursor: pointer;">${list.customerName }</a>
												</td>
												<td class="center">${list.debtorName }</td>
												<td class="center">${list.price }</td>
												<c:if test="${pd.type==1 }">
													<td class="center">
														<c:if test="${list.cType==0 }">${list.commissionRate}
															<c:if test="${not empty list.lawCommissionRate}">/${list.lawCommissionRate}</c:if>
														</c:if>
														<c:if test="${list.cType==1 }">-</c:if>
														<c:if test="${empty list.cType }">${list.contractCommissionRate }</c:if>
													</td>
												</c:if>
												<c:if test="${pd.type==0 }">
													<td class="center">${list.contractCommissionRate }</td>
												</c:if>
												<td class="center">${fn:substring(list.signingTime, 0, 19)}</td>
												<td class="center">
													<div class="time-hook">${fn:substring(list.endTime, 0, 19)}</div>
												</td>
												<td class="center">${list.userName }</td>
												<td class="center">${list.swwxName }</td>
												<td class="center">
													<!--<a title="查看详情" onclick="viewContract('${list.id}')" class="btn btn-primary"><i class="fa fa-search"></i> 查看</a>-->
													<c:if test="${QX.edit != 1 && QX.del != 1 }">
														<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
													</c:if>
													<c:if test="${QX.edit == 1 }">
														<a class="btn btn-success" title="查看" onclick="edit('${list.id}');"> <i class="ace-icon fa fa-pencil-square-o" title="查看"></i> 查看
														</a>
													</c:if>
													<c:if test="${QX.del == 1 }">
														<a class="btn btn-danger" onclick="del('${list.id }','${list.contractNo }');"> <i class="ace-icon fa fa-trash-o" title="删除"></i> 删除
														</a>
													</c:if>
												</td>
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
		<%@ include file="../../system/index/js.jsp"%>

		<script type="text/javascript">
			$(".time-hook").hover(function() {
				var that = this;
				var date2 = new Date();
				var date1 = new Date($(that).text());
				var date = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
				var time = Math.ceil(Number(date));
				if(time < 0) {
					layer.yoTips('已到期', that, {
						tips: 1
					});
				} else {
					layer.yoTips('距离到期还有 ' + time + ' 天', that, {
						tips: 1
					});
				}

			}, function() {
				layer.closeAll()
			})
			//删除
			function del(id, msg) {
				layer
					.confirm(
						"确定要删除[" + msg + "]吗?",
						function(result) {
							if(result) {
								var url = "${pageContext.request.contextPath}/contract/delete.do?ids=" +
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
					content: '${pageContext.request.contextPath}/contract/goAdd.do?type=${pd.type}',
					area: ['1100px', '650px']
				});
			}

			function viewCustomer(id) {
				var index = layer.open({
					type: 2,
					title: '查看详情',
					content: '/static/page/customerN_detail.html?id=' + id,
					area: ['100%', '100%']
				});
			}

			function viewContract(id) {
				var index = layer.open({
					type: 2,
					title: '查看详情',
					content: '${pageContext.request.contextPath}/contract/view.do?type=${pd.type}&id=' + id + "#view",
					area: ['90%', '90%']
				});
			}

			//修改
			function edit(id) {
				var url;
				if('${pd.type}' == 0){
					url = '/static/page/feisusong_detail.html?type=${pd.type}&id=' +id
				}else{
					url = '/static/page/susong_detail.html?type=${pd.type}&id=' +id
				}
				var index = layer
					.open({
						type: 2,
						title: '查看详情',
						content: url,
						area: ['100%', '100%']
					});
			}

			//批量操作
			function makeAll(msg) {
				layer
					.confirm(
						msg,
						function(result) {
							if(result) {
								var str = '';
								var emstr = '';
								var phones = '';
								var username = '';
								for(var i = 0; i < document
									.getElementsByName('ids').length; i++) {
									if(document.getElementsByName('ids')[i].checked) {
										if(str == '')
											str += document
											.getElementsByName('ids')[i].value;
										else
											str += ',' +
											document
											.getElementsByName('ids')[i].value;

										if(emstr == '')
											emstr += document
											.getElementsByName('ids')[i].id;
										else
											emstr += ';' +
											document
											.getElementsByName('ids')[i].id;

										if(phones == '')
											phones += document
											.getElementsByName('ids')[i].alt;
										else
											phones += ';' +
											document
											.getElementsByName('ids')[i].alt;

										if(username == '')
											username += document
											.getElementsByName('ids')[i].title;
										else
											username += ';' +
											document
											.getElementsByName('ids')[i].title;
									}
								}
								if(str == '') {
									layer.msg('您没有选择任何内容!')
									$("#zcheckbox").tips({
										side: 3,
										msg: '点这里全选',
										bg: '#AE81FF',
										time: 8
									});

									return;
								} else {
									if(msg == '确定要删除选中的数据吗?') {
										$
											.ajax({
												type: "POST",
												url: '${pageContext.request.contextPath}/contract/delete.do?tm=' +
													new Date()
													.getTime(),
												data: {
													ids: str
												},
												dataType: 'json',
												//beforeSend: validateData,
												cache: false,
												success: function(data) {
													nextPage('${page.currentPage}');
												}
											});
									}
								}
							}
						});
			}

			$(function() {
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

				var colorbox_params = {
					rel: 'colorbox',
					reposition: true,
					scalePhotos: true,
					scrolling: false,
					previous: '<i class="ace-icon fa fa-arrow-left"></i>',
					next: '<i class="ace-icon fa fa-arrow-right"></i>',
					close: '&times;',
					current: '{current} of {total}',
					maxWidth: '100%',
					maxHeight: '100%',
					onOpen: function() {
						$overflow = document.body.style.overflow;
						document.body.style.overflow = 'hidden';
					},
					onClosed: function() {
						document.body.style.overflow = $overflow;
					},
					onComplete: function() {
						$.colorbox.resize();
					}
				};

				$('[data-rel="colorbox"]').colorbox(colorbox_params);

				//复选框全选控制
				var active_class = 'active';
				$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on(
					'click',
					function() {
						var th_checked = this.checked; //checkbox inside "TH" table header
						$(this).closest('table').find('tbody > tr').each(
							function() {
								var row = this;
								if(th_checked)
									$(row).addClass(active_class).find(
										'input[type=checkbox]').eq(0).prop(
										'checked', true);
								else
									$(row).removeClass(active_class).find(
										'input[type=checkbox]').eq(0).prop(
										'checked', false);
							});
					});
			});

			$(".chosen-select-sale").select2({
				placeholder: "请选择销售",
				language: 'zh-CN',
				ajax: {
					url: "/user/getUserInfo",
					dataType: 'json',
					type: "post",
					delay: 250,
					data: function(params) {
						return {
							page: params.page || 1,
							ROLE_ID: "90564dd8b75a4f6d815ce418462d401c,e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b",
							data: {
								q: params.term || "", // search term
							}
						};
					},
					processResults: function(data, params) {
						params.page = params.page || 1;
						$.each(data, function() {
							this.id = this.USER_ID;
							this.text = this.NAME;
						})
						return {
							results: data,
							pagination: {
								more: data.length == 10
							}
						};
					},
					cache: true
				},
				minimumInputLength: 0
			}).on("change", function() {
				$("[name=userName]").val($(this).find("option:selected").text())

			});
			
			$(".chosen-select-sale1").select2({
				placeholder: "请选择商务外协",
				language: 'zh-CN',
				ajax: {
					url: "/user/getUserInfo",
					dataType: 'json',
					type: "post",
					delay: 250,
					data: function(params) {
						return {
							page: params.page || 1,
							ROLE_ID: "fbe6f2f9535c4fce9f024f6cb999b2bd",
							data: {
								q: params.term || "", // search term
							}
						};
					},
					processResults: function(data, params) {
						params.page = params.page || 1;
						$.each(data, function() {
							this.id = this.USER_ID;
							this.text = this.NAME;
						})
						return {
							results: data,
							pagination: {
								more: data.length == 10
							}
						};
					},
					cache: true
				},
				minimumInputLength: 0
			}).on("change", function() {
				$("[name=swwxName]").val($(this).find("option:selected").text())

			});
			
			$(".cc").click(function(){
				$("#select2-userId-container").text('请选择销售');
				$("#select2-swwxId-container").text('请选择商务外协');
			})
			
		</script>
		</body>

</html>