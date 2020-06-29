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
						<ul class="nav nav-tabs" id="myTab">
							<li class="active"><a data-toggle="tab" href="#home"><i class="green icon-home bigger-110"></i> 填写律师函</a></li>
							<li><a data-toggle="tab" href="#attach" <c:if test="${empty dataList[0] || dataList[0].status == 0}">onclick="init_uploader()"</c:if>><i class="green icon-home bigger-110"></i> 相关附件</a></li>
						</ul>
						<div class="ibox-content">
							<form action="workBench/${msg }.do" name="contractForm" id="contractForm" method="post" class="form form-horizontal">
								<div class="tab-content">
									<div id="home" class="tab-pane in active">
										<input type="hidden" name="id" id="id" value="<c:out value="${dataList[0].id}" default="0"></c:out>" /><input type="hidden" name="sublawyerId" id="sublawyerId"
											value="${dataList[0].sublawyerId}" /> <input type="hidden" name="orderId" id="orderId" value="<c:out value="${pd.orderId}" default="0"></c:out>" /><input type="hidden" name="taskId"
											id="taskId" value="<c:out value="${pd.taskId}" default="0"></c:out>" /> <input type="hidden" name="type" id="type" value="<c:out value="${pd.type}" default="0"></c:out>" />
										<div class="row">
											<c:if test="${not empty dataList[0]}">
												<div class="col-md-6">
													<div class="form-group">
														<label class="col-sm-4 control-label">当前状态: </label>
														<div class="col-sm-8">
															<input class="form-control" readonly="readonly" type="text" name="statusDesc" id="statusDesc" value="${dataList[0].statusDesc}">
														</div>
													</div>
												</div>
											</c:if>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">外协律师&nbsp;<font color="red">*</font>:
													</label>
													<div class="col-sm-8">
														<input class="form-control" type="text" placeholder="这里输入外协律师" data-validate="require|maxLength=50" name="sublawyerName" id="sublawyerName" value="${dataList[0].sublawyerName }"
															readonly="readonly" onclick="chooseUser()">
													</div>
												</div>
											</div>
											<div class="col-md-12">
												<div class="form-group">
													<label class="control-label col-sm-2"> 备注: </label>
													<div class="col-sm-10">
														<textarea class="form-control" style="height: 120px;" id="remark" name="remark" placeholder="这里输入备注">${dataList[0].remark}</textarea>
													</div>
												</div>
											</div>
										</div>
									</div>
									<c:if test="${empty dataList[0] || dataList[0].status == 0}">
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
																	<c:forEach items="${pd.attachs }" var="attachs">
																		<tr>
																			<td>${attachs.originalFilename }</td>
																			<td>${attachs.type }</td>
																			<td>${attachs.uploader }</td>
																			<td>${attachs.uploadTime }</td>
																			<td><a target="_blank" href="${attachs.url }" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a> <a class="btn btn-danger" onclick="list_del(this)"><i
																					class="fa fa-trash-o"></i> 删除</a><input type="hidden" name="fileSize" value="${attachs.fileSize }"><input type="hidden" name="uploader" value="${attachs.uploader }"><input
																				type="hidden" name="originalFilename" value="${attachs.originalFilename }"><input type="hidden" name="uploadTime" value="${attachs.uploadTime }"><input
																				type="hidden" name="realPath" value="${attachs.realPath }"><input type="hidden" name="url" value="${attachs.url }"><input type="hidden" name="attachId"
																				value="${attachs.id }"></td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>
									</c:if>
									<c:if test="${not empty dataList[0] && dataList[0].status != 0}">
										<div id="attach" class="tab-pane">
											<div class="ibox">
												<div class="ibox-content">
													<div id="fileList" class="uploader-list">
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>文件名</th>
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
																			<td>${attachs.uploader }</td>
																			<td>${attachs.uploadTime }</td>
																			<td><a target="_blank" href="${attachs.url }" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a></td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>
									</c:if>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="text-center">
					<c:if test="${empty dataList[0] || dataList[0].status == 0}">
						<a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a>
					</c:if>
				</div>
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.main-container -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.tips.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/myjs/yoValidate.js" type="text/javascript" charset="utf-8"></script>
	<!-- 日期框 -->
	<script src="${pageContext.request.contextPath}/static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/ui/js/plugins/webuploader/webuploader.min.js"></script>
	<!-- Prettyfile -->
	<script src="${pageContext.request.contextPath}/static/ui/js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
	<script src="${pageContext.request.contextPath}/static/ace/js/jquery.colorbox.js"></script>
</body>
<script type="text/javascript">
	var init_uploader_state = 0;
	$('.date-picker').datepicker({
		autoclose : true,
		todayHighlight : true
	});
	//保存
	function save() {
		if (yoValidate('#contractForm')) {
			var index = layer.load(1, {
				shade : [ 0.2, '#fff' ]
			//0.1透明度的白色背景
			});
			$("#contractForm").submit();
		}
	}
	function init_uploader() {
		if (init_uploader_state)
			return;
		init_uploader_state = 1
		var uploader = WebUploader.create({
			// 文件接收服务端。
			server : "${pageContext.request.contextPath}/attach/upload.do",
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
										html += '<input type="hidden" name="'+i+'" value="'+response[i]+'" />'
								}
								html += '<input type="hidden" name="attachId" value="0" />';
								return html;
							}
							$("#fileList tbody")
									.append(
											'<tr><td>'
													+ response.originalFilename
													+ '</td><td>律师函</td><td>'
													+ response.uploader
													+ '</td><td>'
													+ response.uploadTime
													+ '</td><td><a target="_blank" href="'+response.url+'" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a> <a class="btn btn-danger" onclick="list_del(this)"><i class="fa fa-trash-o"></i> 删除</a>'
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
	}

	//删除附件
	function list_del(dom) {
		$(dom).parents('tr').remove()
	}

	//查找客户	
	function chooseCustomer() {
		sessionStorage.listUsersCache = '0' //关闭缓存
		var index = layer
				.open({
					type : 2,
					title : '选择外协律师',
					content : '${pageContext.request.contextPath}/customer/chooseCustomer.do',
					area : [ '90%', '90%' ],
					btn : [ '确定', '取消' ],
					yes : function(index, layero) {
						var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
						var data = iframeWin.pushToParent()
						layer.close(index)
						$("#customerId").val(data.ids); //赋值子页面传过来的IDS
						$("#customerName").val(data.names);
						sessionStorage.listUsersCache = '0' //关闭缓存
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
					content : '${pageContext.request.contextPath}/user/chooseUser.do?flags=0&ROLE_ID=b693f826af0545b5a1c60447a27c3089&parIds=sublawyerId&parNames=sublawyerName',
					area : [ '90%', '90%' ],
					btn : [ '确定', '取消' ],
					yes : function(index, layero) {
						var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
						var data = iframeWin.pushToParent()
						layer.close(index)
						$("#sublawyerId").val(data.ids); //赋值子页面传过来的IDS
						$("#sublawyerName").val(data.names);
						sessionStorage.ListTagsCache = '0' //关闭缓存
					}
				});
	}
</script>
</html>