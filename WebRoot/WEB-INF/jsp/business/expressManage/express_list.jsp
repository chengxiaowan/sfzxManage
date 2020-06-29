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
					<form class="form-inline" role="form" action="express/list.do" method="post" name="contractForm" id="contractForm">
						<div class="form-group">
							<input id="nav-search-input" name="keywords" placeholder="目标单位、发件人" value="${pd.keywords }" class="form-control" type="text">
						</div>
						<div class="form-group">
							<input class="span10 date-picker form-control" id="createTimeStart" name="createTimeStart" value="${fn:substring(pd.createTimeStart, 0, 10)}" type="text" data-date-format="yyyy-mm-dd"
								readonly="readonly" placeholder="开始日期"  />
						</div>
						<div class="form-group">
							<input class="span10 date-picker form-control" id="createTimeEnd" name="createTimeEnd" value="${fn:substring(pd.createTimeEnd, 0, 10)}" type="text" data-date-format="yyyy-mm-dd"
								readonly="readonly" placeholder="结束日期"  />
						</div>
						<div class="form-group">
							发件方式 <select id="sendway" name="sendway" class="form-control" style="width: 120px;" data-placeholder="案件状态">
								<option value="">全部</option>
								<option value="0" <c:if test="${pd.sendway == '0'}">selected</c:if>>快递</option>
								<option value="1" <c:if test="${pd.sendway == '1'}">selected</c:if>>客户自取</option>
								<option value="2" <c:if test="${pd.sendway == '2'}">selected</c:if>>代发</option>
							</select>
						</div>
						<div class="form-group">
							发件类型  <select class="form-control" name="expressType"  >
							<option value="">全部</option>
		       			<option value="1" <c:if test="${pd.expressType == 1 }">selected</c:if>>询证函</option>
		       			<option value="2" <c:if test="${pd.expressType == 2 }">selected</c:if>>催款函</option>
		       			<option value="3" <c:if test="${pd.expressType == 3 }">selected</c:if>>律师函</option>
		       			<option value="4" <c:if test="${pd.expressType == 4 }">selected</c:if>>合同</option>
		       			<option value="5" <c:if test="${pd.expressType == 5 }">selected</c:if>>发票</option>
		       			<option value="6" <c:if test="${pd.expressType == 6 }">selected</c:if>>其他</option>
		        </select>
						</div>
						<div class="form-group">
							是否收件<select id="issou" name="issou" class="form-control" style="width: 120px;" data-placeholder="是否收件">
								<option value="">全部</option>
								<option value="1" <c:if test="${pd.issou==1 }">selected</c:if>>否</option>
								<option value="2" <c:if test="${pd.issou==2 }">selected</c:if>>是</option>
							</select>
						</div>
						<button type="submit" class="btn btn-primary"><i class="fa fa-search"></i> 搜索</button>
						<!--<button type="reset" class="btn">
							<i id="nav-search-icon" class="fa fa-refresh nav-search-icon "></i> 重置
						</button>-->
						<div class="pull-right">
							<c:if test="${QX.add == 1 }">
								<a class="btn btn-success" onclick="add();"><i class="fa fa-plus"></i> 新增</a>
							</c:if>
							<%-- <c:if test="${QX.del == 1 }">
								<a title="批量删除" class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');"><i class='ace-icon fa fa-trash-o'></i> 批量删除</a>
							</c:if> --%>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="ibox-content">
				<!--<div class="alert alert-warning">
                            <span class="alert-link"> 合计: </span> 快递费用总金额    <span class="alert-link" style="font-size:18px"> ${pd.qkzje } </span> 元 
                        </div>-->
                <div class="htz-box-no col-sm-2" style="cursor: pointer;">
						<span class="pull-left"><img src="/static/ui/img/10icon/h.png"></span>
						<div class="pull-left">
							<div>快递费用总金额</div>
							<div class="blue">¥${pd.qkzje }</div>
						</div>
					</div>        
			<table id="simple-table" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center" style="width: 35px;"><label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label></th>
						<th class="center" style="width: 50px;">序号</th>
						<th class="center">目标单位</th>
						<!-- <th class="center">发件方式</th> -->
						<th class="center">发件类型</th>
						<th class="center">发件单位</th>
						<th class="center">发件人</th>
						<th class="center">发件时间</th>
						<th class="center">快递公司</th>
						<th class="center">快递单号</th>
						<th class="center">是否收件</th>
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
										<td class="center">${list.target }</td>
										<%-- <td class="center"><c:if test="${list.sendway == '0'}">快递</c:if><c:if test="${list.sendway == '1'}">客户自取</c:if><c:if test="${list.sendway == '2'}">代发</c:if></td> --%>
										<td class="center"><c:if test="${list.expressType == '1'}">询证函</c:if><c:if test="${list.expressType == '2'}">催款函</c:if><c:if test="${list.expressType == '3'}">律师函</c:if><c:if test="${list.expressType == '4'}">合同</c:if><c:if test="${list.expressType == '5'}">发票</c:if><c:if test="${list.expressType == '6'}">其他</c:if></td>
										<td class="center">${list.fjdw }</td>
										<td class="center">${list.fjr }</td>
										<td class="center">${fn:substring(list.sendTime,0,10) }</td>
										<td class="center">
										<a href="javascript:;" class="express-info-hook" <c:if test="${pd.flag==5 }">onclick="expressInfo('${list.id },${list.expressCom },${list.expressMoney},${list.expressNo }')"</c:if> >
										${list.expressCom }
										</a>
										</td>
										<td class="center">
										<a href="javascript:;" class="express-info-hook" <c:if test="${pd.flag==5 }">onclick="expressInfo('${list.id },${list.expressCom },${list.expressMoney},${list.expressNo }')"</c:if> >
										${list.expressNo }
										</a>
										</td>
										<td class="center">${not empty list.reciveTime?'是':'否' }</td>
										<td class="center">
										<c:if test="${pd.flag!=5 }">
										<a title="点击查看详情" onclick="view('${list.id}')" class="btn btn-primary"><i class="fa fa-search"></i> 查看</a>
										</c:if>
										<c:if test="${pd.flag==5 }">
											<button title="点击查看详情" onclick="send('${list.id}')"
											<c:if test="${list.sendway!=0 }">
											disabled
											</c:if>
											<c:if test="${list.sendway==0 && not empty list.expressNo && not empty list.expressCom}">
											disabled
											</c:if>
											<c:if test="${not empty list.reciveTime && not empty list.status}">
											disabled
											</c:if>
											 class="btn btn-primary"><i class="fa fa-send"></i> 发送</button>
										</c:if>
										<c:if test="${pd.flag==5 }">
											<button title="点击查看详情" onclick="complete('${list.id}')"
											<c:if test="${not empty list.reciveTime && not empty list.status && not empty list.expressNo && not empty list.expressCom }">
											disabled
											</c:if> 
											<c:if test="${not empty list.reciveTime && not empty list.status}">
											disabled
											</c:if> 
											<c:if test="${list.sendway!=0}">
											disabled
											</c:if>
											<c:if test="${list.sendway==0 && empty list.expressCom && empty list.expressNo}">
											disabled
											</c:if> 
											
											 class="btn btn-primary"><i class="fa fa-check"></i> 完成</button>
										</c:if>
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="fa fa-lock" title="无权限"></i></span>
											</c:if>
												<c:if test="${pd.flag!=5 }">
													<c:if test="${list.sendway!=0 }">
													<c:if test="${QX.edit == 1 }">
														<button class="btn btn-success"
														<c:if test="${not empty list.reciveTime && not empty list.status }">
														disabled="disabled"
														</c:if>
														 title="编辑" onclick="edit('${list.id}');"> <i class="ace-icon fa fa-pencil-square-o" title="编辑"></i> 编辑
														</button>
													</c:if>
													</c:if>
													<c:if test="${list.sendway==0 }">
													<c:if test="${QX.edit == 1 }">
														<button class="btn btn-success"
														<c:if test="${not empty list.reciveTime && not empty list.status && not empty list.expressNo && not empty list.expressCom }">
														disabled="disabled"
														</c:if>
														 title="编辑" onclick="edit('${list.id}');"> <i class="ace-icon fa fa-pencil-square-o" title="编辑"></i> 编辑
														</button>
													</c:if>
													</c:if>
												</c:if>
												<c:if test="${pd.flag==5 }">
													<button class="btn btn-success"
														 title="编辑" onclick="edit('${list.id}');"> <i class="ace-icon fa fa-pencil-square-o" title="编辑"></i> 编辑
													</button>
												</c:if>
												<c:if test="${QX.del == 1 }">
													<button class="btn btn-danger"
													<c:if test="${not empty list.reciveTime && not empty list.status && pd.flag!=5 }">
													disabled="disabled"
													</c:if>
													 onclick="del('${list.id }','${list.contractNo }');"> <i class="ace-icon fa fa-trash-o" title="删除"></i> 删除
													</button>
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
	
		<!-- 发送 -->
				<div class="ibox-content" id="new-float-box1" style="overflow: hidden; display: none">
		<div class="col-md-12">
				<form method="post" class="form form-horizontal" >
										<div class="row">
														<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">快递公司 <font color="red">*</font>：</label>
														        <div class="col-sm-8">
														            <input class="form-control" required type="text" placeholder="这里输入快递公司"  name="expressCom"  value=""  >
														        </div>
														    </div>
														</div>
														<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">快递费用<font color="red">*</font>：</label>
														        <div class="col-sm-8">
														            <input class="form-control" required number="true" type="text" placeholder="这里输入快递费用"  name="expressMoney"  value=""  >
														        </div>
														    </div>
														</div>
														<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">快递单号 <font color="red">*</font>：</label>
														        <div class="col-sm-8">
														            <input class="form-control" required type="text" placeholder="这里输入快递单号"  name="expressNo" value=""  >
														        </div>
														    </div>
														</div>
												</div>
										</form>
		</div>
	</div>
	<!-- 完成-->
				<div class="ibox-content" id="new-float-box2" style="overflow: hidden; display: none">
		<div class="col-md-12">
				<form method="post"  class="form form-horizontal" >
										<div class="row">
														<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">收件时间 <font color="red">*</font>：</label>
														        <div class="col-sm-8">
														            <input class="form-control date-picker"  data-date-format="yyyy-mm-dd" readonly="readonly" type="text" required placeholder="这里输入收件时间"  name="reciveTime"  value=""  >
														        </div>
														    </div>
														</div>
														<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">收件情况 <font color="red">*</font>：</label>
														        <div class="col-sm-8">
														        <textarea required class="form-control" name="status" style="height:70px" placeholder="这里输入收件情况" cols="30" rows="10"></textarea>
														        </div>
														    </div>
														</div>
												</div>
										</form>
		</div>
	</div>
	
</body>
<!-- 全局js -->
<%@ include file="../../system/index/js.jsp"%>

<script type="text/javascript">
	//删除
	function del(id, msg) {
		layer
				.confirm(
						"确定要删除此项?",
						function(result) {
							if (result) {
								var url = "${pageContext.request.contextPath}/express/delete.do?ids="
										+ id + "&tm=" + new Date().getTime();
								$.get(url, function(data) {
									nextPage('${page.currentPage}');
								});
							}
							;
						});
	}

	//新增
	function add() {
		var index = layer.open({
			type : 2,
			title : '新增',
			content : '${pageContext.request.contextPath}/express/goAdd.do',
			area : [ '1000px', '600px' ]
		});
	}
	
	//查看
	function view(id) {
		var index = layer.open({
			type : 2,
			title : '查看详情',
			content : '${pageContext.request.contextPath}/express/view.do?id='+id,
			area : [ '90%', '90%' ],
			yes:function(){
				layer.close(index);
			}
		});
	}
	
	//发送
			function send(id){
				var index = layer.open({
					type : 1,
					title : '发送',
					content :$("#new-float-box1"),
					area : [ '500px', '350px' ],
					btnAlign:'c',
					btn:['保存'],
					yes:function(){
								$("#new-float-box1 form").validate()
								if($("#new-float-box1 form").valid()){
						$.post('/express/update',{
							id:id,
							expressCom:$.trim($("input[name=expressCom]").val()),
							expressMoney:$.trim($("input[name=expressMoney]").val()),
							expressNo:$.trim($("input[name=expressNo]").val())
						}).done(function(res){
							if(res.error=="00"){
								layer.msg("保存成功")
							layer.close(index)
							setTimeout(function(){
								location.reload();
							}, 1000)
							}else{
								layer.msg(res.msg)
							}
						})
								}
					},
					end:function(){
						$("#new-float-box1 form")[0].reset();
					}
			})
			}
			//修改发件信息
			function expressInfo(info){
				console.log(info)
				var info = $.trim(info).split(",")
				$("input[name=expressCom]").val(info[1])
				$("input[name=expressMoney]").val(info[2])
				$("input[name=expressNo]").val(info[3])
				send(info[0])
			}	
			//完成
			function complete(id){
				var index = layer.open({
					type : 1,
					title : '完成',
					content :$("#new-float-box2"),
					area : [ '500px', '300px' ],
					btnAlign:'c',
					btn:['保存'],
					yes:function(){
								$("#new-float-box2 form").validate()
								if($("#new-float-box2 form").valid()){
						$.post('/express/update',{
							id:id,
							reciveTime:$.trim($("input[name=reciveTime]").val()),
							status:$.trim($("textarea[name=status]").val())
						}).done(function(res){
							if(res.error=="00"){
								layer.msg("保存成功")
							layer.close(index)
							setTimeout(function(){
								location.reload();
							}, 1000)
							}else{
								layer.msg(res.msg)
							}
						})
								}
					},
					end:function(){
						$("#new-float-box2 form")[0].reset();
					}
			})
			}	
	
	//修改
	function edit(id) {
		var index = layer
				.open({
					type : 2,
					title : '修改',
					content : '${pageContext.request.contextPath}/express/goEdit.do?id='
							+ id,
					area : [ '90%', '90%' ]
				});
	}

	//批量操作
	function makeAll(msg) {
		layer
				.confirm(
						msg,
						function(result) {
							if (result) {
								var str = '';
								var emstr = '';
								var phones = '';
								var username = '';
								for (var i = 0; i < document
										.getElementsByName('ids').length; i++) {
									if (document.getElementsByName('ids')[i].checked) {
										if (str == '')
											str += document
													.getElementsByName('ids')[i].value;
										else
											str += ','
													+ document
															.getElementsByName('ids')[i].value;

										if (emstr == '')
											emstr += document
													.getElementsByName('ids')[i].id;
										else
											emstr += ';'
													+ document
															.getElementsByName('ids')[i].id;

										if (phones == '')
											phones += document
													.getElementsByName('ids')[i].alt;
										else
											phones += ';'
													+ document
															.getElementsByName('ids')[i].alt;

										if (username == '')
											username += document
													.getElementsByName('ids')[i].title;
										else
											username += ';'
													+ document
															.getElementsByName('ids')[i].title;
									}
								}
								if (str == '') {
									layer.msg('您没有选择任何内容!')
									$("#zcheckbox").tips({
										side : 3,
										msg : '点这里全选',
										bg : '#AE81FF',
										time : 8
									});

									return;
								} else {
									if (msg == '确定要删除选中的数据吗?') {
										$
												.ajax({
													type : "POST",
													url : '${pageContext.request.contextPath}/express/delete.do?tm='
															+ new Date()
																	.getTime(),
													data : {
														ids : str
													},
													dataType : 'json',
													//beforeSend: validateData,
													cache : false,
													success : function(data) {
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
			autoclose : true,
			todayHighlight : true
		});

		if ($("#createTimeStart").val()) {
			$("#createTimeEnd").datepicker('setStartDate',
					new Date($("#createTimeStart").val()))
		}

		if ($("#createTimeEnd").val()) {
			$("#createTimeStart").datepicker('setEndDate',
					new Date($("#createTimeEnd").val()))
		}

		//开始日期
		$("#createTimeStart").datepicker({}).on(
				'changeDate',
				function(ev) {
					if (ev.date) {
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
					if (ev.date) {
						$("#createTimeStart").datepicker('setEndDate',
								new Date(ev.date.valueOf()))
					} else {
						$("#createTimeStart").datepicker('setEndDate',
								new Date());
					}
				});
		//下拉框
		$('.chosen-select').chosen({
			allow_single_deselect : true
		});
		$(window).off('resize.chosen').on('resize.chosen', function() {
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({
					'width' : $this.parent().width()
				});
			});
		}).trigger('resize.chosen');
		$(document).on('settings.ace.chosen',
				function(e, event_name, event_val) {
					if (event_name != 'sidebar_collapsed')
						return;
					$('.chosen-select').each(function() {
						var $this = $(this);
						$this.next().css({
							'width' : $this.parent().width()
						});
					});
				});
		$('#chosen-multiple-style .btn').on('click', function(e) {
			var target = $(this).find('input[type=radio]');
			var which = parseInt(target.val());
			if (which == 2)
				$('#form-field-select-4').addClass('tag-input-style');
			else
				$('#form-field-select-4').removeClass('tag-input-style');
		});


		//复选框全选控制
		var active_class = 'active';
		$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on(
				'click',
				function() {
					var th_checked = this.checked;//checkbox inside "TH" table header
					$(this).closest('table').find('tbody > tr').each(
							function() {
								var row = this;
								if (th_checked)
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
</script>
</body>
</html>
