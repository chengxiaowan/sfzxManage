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

	<body class="no-skin">
		<!-- /section:basics/navbar.layout -->
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
						<div class="tabbable">
							<div class="ibox-content">
								<form action="express/${msg }.do" name="contractForm" id="contractForm" method="post" class="form form-horizontal">
									<div class="tab-content">
										<div id="home" class="tab-pane in active">
											<input type="hidden" name="id" id="id" value="<c:out value=" ${pd.id} " default="0"></c:out>" />
											<input type="hidden" name="orderId" id="orderId" value="<c:out value="${pd.orderId}" default="0"></c:out>" />
											<div class="row">
												<div class="col-md-12">
													<div class="form-group col-sm-6 ">
														<label class="col-sm-3 control-label">目标单位&nbsp;<font color="red">*</font>：</label>
														<div class="col-sm-9">
															<input class="form-control" type="text" data-validate="require" placeholder="这里输入目标单位" name="target" id="target" value="${empty pd.target ? pd.debtorName:pd.target }">
														</div>
													</div>
													<div class="form-group col-sm-6 ">
														<label class="col-sm-3 control-label"><c:if test="${empty pd.orderId }">寄件</c:if><c:if test="${not empty pd.orderId }">函件</c:if>内容&nbsp;<font color="red">*</font>：</label>
														<div class="col-sm-9">
															<input class="form-control" type="text" data-validate="require" placeholder="这里输入寄件内容" name="jjnr" id="jjnr" value="${pd.jjnr }">
														</div>
													</div>
												</div>
												<div class="col-md-12">
													<div class="form-group col-sm-6 kdff">
														<label class="col-sm-3 control-label">发件方式&nbsp;<font color="red">*</font>：</label>
														<div class="col-sm-9">
															<select class="chosen-select form-control" data-validate="require" name="sendway" id="sendway" data-placeholder="请选择发送方式" style="vertical-align: top;" title="发件方式" style="width:98%;" onchange="selectE()">
																<option value="">请选择</option>
																<option value="0" <c:if test="${pd.sendway == 0 }">selected</c:if>>快递</option>
																<option value="1" <c:if test="${pd.sendway == 1 }">selected</c:if>>客户自取</option>
																<option value="2" <c:if test="${pd.sendway == 2 }">selected</c:if>>代发</option>
															</select>
														</div>
													</div>
													<div class="form-group col-sm-6">
														<label class="col-sm-3 control-label">发件类型&nbsp;<font color="red">*</font>：</label>
														<div class="col-sm-9">
															<select class="form-control" data-validate="require" name="expressType" onchange="showOtherTypeBox(this)">
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
												<%-- <div class="expressTypeBox-hook" style="display:none">
															<div class="col-md-12" >
																     <div class="form-group">
																        <label class="col-sm-3 control-label">快递公司：</label>
																        <div class="col-sm-9">
																            <input class="form-control" disabled="disabled" data-validate="maxLength=100" type="text" placeholder="这里输入快递公司" name="expressCom" value="${pd.expressCom }"  >
																        </div>
																    </div>
																    </div>
																	<div class="col-md-12" >
																     <div class="form-group">
																        <label class="col-sm-3 control-label">快递单号：</label>
																        <div class="col-sm-9">
																            <input class="form-control" disabled="disabled" data-validate="maxLength=100" type="text" placeholder="这里输入快递单号" name="expressNo" value="${pd.expressNo }"  >
																        </div>
																    </div>
																    </div>
																   </div> --%>
												<div class="otherTypeBox-hook" <c:if test="${pd.expressType != 6 }"> style="display:none;" </c:if>>
													<div class="col-md-12">
														<div class="form-group">
															<label class="col-sm-3 control-label">具体类型：</label>
															<div class="col-sm-9">
																<input class="form-control" <c:if test="${pd.expressType != 6 }"> disabled="disabled" </c:if> data-validate="maxLength=100" name="typeDetail" type="text" placeholder="这里输入具体类型" value="${pd.typeDetail }" >
															</div>
														</div>
													</div>
												</div>

												<div class="col-md-12">
													<div class="otherTypeBox-hook" <c:if test="${pd.expressType != 6 }"> style="display:none;" </c:if>>
														<div class="form-group col-sm-6">
															<label class="col-sm-3 control-label">具体类型：</label>
															<div class="col-sm-9">
																<input class="form-control" <c:if test="${pd.expressType != 6 }"> disabled="disabled" </c:if> data-validate="maxLength=100" name="typeDetail" type="text" placeholder="这里输入具体类型" value="${pd.typeDetail }" >
															</div>
														</div>
													</div>
													<div class="form-group col-sm-6">
														<label class="col-sm-3 control-label">发件单位 ：</label>
														<div class="col-sm-9">
															<input class="form-control" type="text" placeholder="这里输入发件单位" name="fjdw" id="fjdw" value="${pd.fjdw }">
														</div>
													</div>
												</div>

												<div class="col-md-12">
													<div class="form-group col-sm-6">
														<label class="col-sm-3 control-label">发件人 &nbsp;<font color="red">*</font>：</label>
														<div class="col-sm-9">
															<input class="form-control" type="text" data-validate="require" placeholder="这里输入发件人" name="fjr" id="fjr" value="${empty pd.fjr ? pd.runnerName:pd.fjr}">
														</div>
													</div>
													<div class="form-group col-sm-6">
														<label class="col-sm-3 control-label">单位地址：</label>
														<div class="col-sm-9">
															<input class="form-control" type="text" placeholder="这里输入单位地址" name="dwdz" id="dwdz" value="${pd.dwdz }">
														</div>
													</div>
												</div>


												<div class="col-md-12">
													<div class="form-group col-sm-6">
														<label class="col-sm-3 control-label">联系方式：</label>
														<div class="col-sm-9">
															<input class="form-control" type="text" placeholder="这里输入联系方式" name="lxfs" id="lxfs" value="${pd.lxfs }">
														</div>
													</div>
													<div class="form-group col-sm-6">
														<label class="col-sm-3 control-label">发件时间&nbsp;<font color="red">*</font>：</label>
														<div class="col-sm-9">
															<input class="span10 date-picker form-control" readonly="readonly" data-validate="require|maxLength=50" type="text" placeholder="这里输入发件时间" data-date-format="yyyy-mm-dd" name="sendTime" id="sendTime" value="${fn:substring(pd.sendTime,0,10) }">
														</div>
													</div>
												</div>


												<c:if test="${not empty pd.mark1}">
													<div class="col-md-12">
														<div class="form-group col-sm-6">
															<label class="col-sm-3 control-label">快递公司&nbsp;<font color="red">*</font>：</label>
															<div class="col-sm-9">
																<input class="form-control" type="text" data-validate="require" placeholder="这里输入快递公司" name="expressCom" id="expressCom" value="${pd.expressCom }">
															</div>
														</div>
														<div class="form-group col-sm-6">
															<label class="col-sm-3 control-label">快递单号&nbsp;<font color="red">*</font>：</label>
															<div class="col-sm-9">
																<input class="form-control" type="text" data-validate="require" placeholder="这里输入快递单号" name="expressNo" id="expressNo" value="${pd.expressNo }">
															</div>
														</div>
													</div>
													<div class="col-md-12">
														<div class="form-group  col-sm-6">
															<label class="col-sm-3 control-label">快递费用&nbsp;<font color="red">*</font>：</label>
															<div class="col-sm-9">
																<input class="form-control" type="text" data-validate="require" placeholder="这里输入快递费用" name="expressMoney" id="expressMoney" value="${pd.expressMoney }">
															</div>
														</div>
													</div>

												</c:if>

												<div class="col-md-12">
													<div class="form-group">
														<label class="col-sm-2 control-label" style="width: 12.5%">收件人：</label>
														<div class="col-sm-9" style="width: 81.5%">
															<a class="btn btn-success m-b-xs" onclick="addReciver()"><i class="fa fa-plus"></i> 新增</a>
															<table class="table table-bordered" id="recivemanList">
																<thead>
																	<tr>
																		<th>姓名</th>
																		<th>电话</th>
																		<th>地址</th>
																		<th>操作</th>
																	</tr>
																</thead>
																<tbody>
																	<c:if test="${not empty pd.links }">
																		<c:forEach items="${pd.links }" var="linkman1">
																			<tr data-params='{"id":${linkman1.id },"name":"${empty linkman1.name?" ":linkman1.name }","phone":"${empty linkman1.mobilePhone?"":linkman1.mobilePhone}","address":"${empty linkman1.address?"":linkman1.address }"}'>
																				<td>
																					${linkman1.name}</td>
																				<td>${linkman1.mobilePhone}</td>
																				<td>${linkman1.address}</td>
																				<td>
																					<div class="btn btn-white btn-xs" onclick="del_receiver(this)">
																						<i class="fa fa-trash-o"></i> 删除
																					</div>
																				</td>
																			</tr>
																		</c:forEach>
																	</c:if>
																</tbody>
															</table>
															<input type="hidden" name="recieveValue" id="recieveValue" value="${ pd.recieveValue}" />
															<input type="hidden" name="orecieveValue" value="${ pd.recieveValue}" />
														</div>
													</div>

												</div>
												<%-- 	<c:if test="${pd.flag==5 &&not empty pd.id}">
														<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">收件时间&nbsp;<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														            <input class="span10 date-picker form-control" data-validate="require|maxLength=50" type="text" placeholder="这里输入收件时间"  readonly="readonly"  name="reciveTime" id="reciveTime"  data-date-format="yyyy-mm-dd" value="${fn:substring(pd.reciveTime,0,10) }"  >
														        </div>
														    </div>
														</div>
													
														<div class="col-md-12">
													<div class="form-group">
														<label  class="control-label col-sm-3">
															收件情况<span class="text-danger">*</span>:
														</label>
														<div class="col-sm-9">
														<textarea data-validate="require" class="form-control" style="height: 120px;"  name="status"  placeholder="这里输入客户收件情况">${pd.status}</textarea>
														</div>
													</div>	
													</div>	
													</c:if>	 --%>
												<div class="col-md-12">
													<div class="form-group">
														<label class="control-label col-sm-2" style="width: 12.5%">
															相关附件:
														</label>
														<div class="col-sm-9" style="width: 81.5%">
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
																			<c:forEach items="${pd.attachs }" var="attachs">
																				<tr>
																					<td>${attachs.originalFilename }</td>
																					<td>快递</td>
																					<td>${attachs.uploader }</td>
																					<td>${attachs.uploadTime }</td>
																					<td>
																						<a target="_blank" href="${attachs.url }" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a>
																						<a class="btn btn-danger" onclick="list_del(this)"><i class="fa fa-trash-o"></i> 删除</a><input type="hidden" name="fileSize" value="${attachs.fileSize }"><input type="hidden" name="uploader" value="${attachs.uploader }"><input type="hidden" name="originalFilename" value="${attachs.originalFilename }"><input type="hidden" name="uploadTime" value="${attachs.uploadTime }"><input type="hidden" name="realPath" value="${attachs.realPath }"><input type="hidden" name="url" value="${attachs.url }"><input type="hidden" name="attachId" value="${attachs.id }"></td>
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
								</form>
								</div>
								</div>
							</div>
						</div>
					</div>
					<div class="text-center m-b-sm">
						<a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a>
					</div>
				</div>
				<!-- /.col -->

				<div class="ibox-content" id="new-float-box1" style="overflow: hidden; display: none">
					<div class="col-md-12">
						<form name="receiverForm" id="receiverForm" method="post" class="form form-horizontal">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="col-sm-2 control-label">姓名<font color="red">*</font>：</label>
										<div class="col-sm-10">
											<input class="form-control" data-validate="require|maxLength=50" type="text" placeholder="这里输入联系人名称" name="name" id="name" value="">
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label class="col-sm-2 control-label">电话号码：</label>
										<div class="col-sm-10">
											<input class="form-control" type="text" placeholder="这里输入电话号码" name="phone" id="phone" value="">
										</div>
									</div>
								</div>
								<hr />
								<div class="col-md-12">
									<div class="form-group">
										<label class="col-sm-2 control-label">地址：</label>
										<div class="col-sm-10">
											<input class="form-control" type="text" placeholder="这里输入地址" name="address" id="address" value="">
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<!-- 页面底部js¨ -->
				<%@ include file="../../system/index/js.jsp"%>
	</body>
	<script type="text/javascript">
		$(function() {

			var dispute = '${pd.sendway}'
			if("0" == dispute) {
				selectE();
			}

			//初始化经手人chosen
			$(".chosen-select-sale").ajaxChosen({
				dataType: 'json',
				type: 'POST',
				url: '/user/getUserInfo'
			}, {
				external: "02178e62f17b4926bb7014f3ad5a1ebe",
				processItems: function(data) {
					var list = [];
					$.each(data, function() {
						list.push({
							id: this.USER_ID,
							text: this.NAME
						})
					})
					return list;
				},
				loadingImg: '${pageContext.request.contextPath}/static/ui/img/loading.gif'
			});

		})

		$('.date-picker').datepicker({
			autoclose: true,
			todayHighlight: true
		});
		//保存
		function save() {
			if(yoValidate('#contractForm', 1, 1)) {
				var index = layer.load(1, {
					shade: [0.2, '#fff']
					//0.1透明度的白色背景
				});
				$("#contractForm").submit();
				if('${pd.mark1}'){
					parent.a()
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				} 
			}
		}
		var uploader = WebUploader.create({
			// 文件接收服务端。
			server: "${pageContext.request.contextPath}/contract/uploadAttachment.do",
			runtimeOrder: 'html5',
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick: {
				id: '#fileUp',
				innerHTML: '上传附件'
			},
			resize: false,
			fileNumLimit: 10,
		});

		//上传成功
		uploader.on('uploadSuccess', function(file, response) {
			console.log(file, response);
			$("#realPath").val(response.realPath);
			$("#originalFilename").val(response.originalFilename);
			$("#fileSize").val(response.fileSize);
			$("#url").val(response.url);
			$("#uploadTime").val(response.uploadTime);
			$("#uploader").val(response.uploader);
			var hideVal = function() {
				var html = '';
				for(i in response) {
					if(i != '_raw') html += '<input type="hidden" name="' + i + '" value="' + response[i] + '" />'
				}
				html += '<input type="hidden" name="attachId" value="0" />';
				return html;
			}
			$("#fileList tbody").append(
				'<tr><td>' + response.originalFilename + '</td><td>快递</td><td>' + response.uploader + '</td><td>' + response.uploadTime + '</td><td><a target="_blank" href="' + response.url + '" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a> <a class="btn btn-danger" onclick="list_del(this)"><i class="fa fa-trash-o"></i> 删除</a>' + hideVal() + '</td></tr>'
			)
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
			if(res == 'Q_TYPE_DENIED') {
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

		function selectE() {
			var sendway = $("#sendway").val();
			if(sendway == '0') {
				$(".expressTypeBox-hook").show().find("input,select").prop("disabled", false)
			} else {
				$(".expressTypeBox-hook").hide().find("input,select").prop("disabled", true).val("")
				$(".otherTypeBox-hook").hide().find("input,select").prop("disabled", true).val("")
			}
		}

		function showOtherTypeBox(dom) {
			if(dom.value == "6") {
				$(".otherTypeBox-hook").show().find("input,select").prop("disabled", false)
			} else {
				$(".otherTypeBox-hook").hide().find("input,select").prop("disabled", true).val("")
			}
		}

		//删除附件
		function list_del(dom) {
			$(dom).parents('tr').remove()
		}

		//添加收件人
		function addReciver() {
			var index = layer.open({
				type: 1,
				title: '新增',
				content: $("#new-float-box1"),
				area: ['70%', '340px'],
				btn: ["保存", "取消"],
				btnAlign:'c',
				yes: function() {
					if(yoValidate("#new-float-box1", 1)) {
						var data = {};
						$.each($("#new-float-box1 form").find("input,textarea"), function() {
							data[this.name] = this.value
						})
						data.mobilePhone = $('#phone').val()
						data.type = "2";
						$.post("/linkman/saveLinkMan",
							data).done(function(res) {
							data.id = res.result;
							var html = '<tr data-params="' + encodeURI(JSON.stringify(data)) + '"><td>' +
								(data.name || "") +
								'</td><td>' +
								(data.phone || "") +
								'</td><td>' +
								(data.address || "") +
								'</td><td><div class="btn btn-white btn-xs" onclick="del_receiver(this)"><i class="fa fa-trash-o"></i> 删除</div></td><tr>'
							$("#recivemanList").find("tbody").append(html)
							cacheUpdate()
							layer.close(index);
						})
					}
				},
				end: function() {
					$("#new-float-box1 form")[0].reset()
				}
			});

		}

		function del_receiver(dom) {
			$(dom).parents('tr').remove()
			cacheUpdate()
		}

		/**
		 **缓存生成与更新
		 **/
		(function(doc) {
			cacheUpdate();
		})(document)

		function cacheUpdate() {
			for(var i = 0; i < 4; i++) {
				var valueContainer = $("#recieveValue");
				var tbody = $("#recivemanList").find("tbody");
				var ids = [];
				var names = [];
				var MobilePhones = [];
				var address = [];
				$.each(tbody.find('tr'), function() {
					if(this.dataset.params) {
						var params = this.dataset.params;
						var json = {}
						if(typeof params == "string") {
							json = JSON.parse(decodeURI(params));
						} else {
							json = params
						}
						ids.push(json.id);
						names.push(json.name);
						MobilePhones.push(json.phone);
						address.push(json.phone);
					}
				})
				if(ids.length) {
					valueContainer.val(ids.join(','))
				} else {
					valueContainer.val("");
				}
			}
		}
	</script>

</html>