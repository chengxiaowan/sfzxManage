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
<body class="w100">
	<!-- 新增/编辑 案件报告 -->
	<div class="ibox-content">
		<div class="row">
			<div class="col-xs-12">
				<div class="tabbable">
					<form action="workBench/${msg }.do" name="customerForm" id="customerForm" method="post" class="form form-horizontal">
						<div class="tab-content">
								<input type="hidden" name="id" id="id" value="<c:out value="${dataList[0].id}" default="0"></c:out>" /> <input type="hidden" name="orderId" id="orderId"
									value="<c:out value="${pd.orderId}" default="0"></c:out>" /><input type="hidden" name="taskId" id="taskId" value="<c:out value="${pd.taskId}" default="0"></c:out>" /> <input type="hidden"
									name="type" id="type" value="<c:out value="${pd.type}" default="0"></c:out>" /> <input type="hidden" name="status" id="status"
									value="<c:out value="${dataList[0].status}" default="0"></c:out>" />
								<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="col-sm-2 control-label">标题&nbsp;<font color="red">*</font>:
												</label>
												<div class="col-sm-10">
													<input class="form-control" data-validate="require|maxLength=50" placeholder="这里填写标题" id="title" name="title" type="text" value="${dataList[0].title}">
												</div>
											</div>
										</div>
										<div class="col-md-12 marks">
									    <div class="form-group">
									        <label class="col-sm-2 control-label">报告类型<font color="red">*</font>：</label>
									        <div class="col-sm-10">
										        <select class="chosen-select form-control" data-validate="require" name="reportType" id="reportType" data-placeholder="请选择案件类型" style="vertical-align: top;" >
													<option value="">请选择</option>
													<option value="0" <c:if test="${dataList[0].reportType == 0 }">selected</c:if>>进展</option>
													<option value="1" <c:if test="${dataList[0].reportType == 1 }">selected</c:if>>结案</option>
													<option value="2" <c:if test="${dataList[0].reportType == 2 }">selected</c:if>>关闭</option>
												</select>
											</div>
									    </div>
										</div>
										<%-- <div class="col-md-12">
											<div class="form-group">
												<label class="col-sm-2 control-label">邮箱<font color="red">*</font>: </label>
												<div class="col-sm-10">
													<input class="form-control" data-validate="require|email|maxLength=50" placeholder="这里输入邮箱" id="email" name="email" type="text" value="${dataList[0].email}">
												</div>
											</div>
										</div> --%>
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-sm-2 no-padding-right">报告内容&nbsp;<font color="red">*</font>:
												</label>
												<div class="col-sm-10">
													<textarea class="form-control" style="height: 120px;" rows="15" cols="10" id="remark" name="remark" data-validate="require|maxLength=1000000" placeholder="这里输入报告内容">${dataList[0].remark}</textarea>
												</div>
											</div>
										</div>
										<div class="col-md-12" id="hxjhBOX">
											<div class="form-group">
												<label class="control-label col-sm-2 no-padding-right">后续计划&nbsp;<font color="red">*</font>:
												</label>
												<div class="col-sm-10">
													<textarea class="form-control" style=" height: 120px;" rows="15" cols="10" id="hxjh" name="hxjh" data-validate="require|maxLength=1000000" placeholder="这里输入后续计划">${dataList[0].remark}</textarea>
												</div>
											</div>
										</div>
								
								<div class="col-md-12">
								<div class="form-group">
									<label class="col-sm-2 control-label">
									相关附件</label>
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
												<c:if test="${not empty dataList[0].attachs }">
													<c:forEach items="${dataList[0].attachs }" var="attachs">
														<tr>
															<td>${attachs.originalFilename }</td>
															<td>${attachs.type }</td>
															<td>${attachs.uploader }</td>
															<td>${attachs.uploadTime }</td>
															<td><a target="_blank" href="${attachs.url }" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a>
															<c:if test="${pd.status!=2 }">
															 <a class="btn btn-danger" onclick="list_del(this)"><i class="fa fa-trash-o"></i> 删除</a>
																	</c:if>
																	<input type="hidden" name="fileSize" value="${attachs.fileSize }"><input type="hidden" name="uploader" value="${attachs.uploader }"><input
																type="hidden" name="originalFilename" value="${attachs.originalFilename }"><input type="hidden" name="uploadTime" value="${attachs.uploadTime }"><input type="hidden"
																name="realPath" value="${attachs.realPath }"><input type="hidden" name="url" value="${attachs.url }"><input type="hidden" name="attachId" value="${attachs.id }"></td>
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
							<!-- 										<div id="dataList" class="tab-pane" style="padding-top:20px"> -->
							<!-- 											开始循环 -->
							<%-- 											<c:choose> --%>
							<%-- 												<c:when test="${not empty dataList}"> --%>
							<%-- 													<c:forEach items="${dataList}" var="list" varStatus="vs"> --%>
							<!-- 													<div class="row"> -->
							<!-- 														<div class="col-md-12"> -->
							<!-- 															<div class="form-group"> -->
							<!-- 																<label class="col-sm-2 control-label">标题:</label> -->
							<!-- 																<div class="col-sm-10"> -->
							<%-- 																	<input class="form-control" readonly="readonly" type="text" value="${list.title}"> --%>
							<!-- 																</div> -->
							<!-- 															</div> -->
							<!-- 														</div> -->
							<!-- 														<hr /> -->
							<!-- 														<div class="col-md-12"> -->
							<!-- 															<div class="form-group"> -->
							<!-- 																<label class="col-sm-2 control-label">报告内容:</label> -->
							<!-- 																<div class="col-sm-10"> -->
							<%-- 																	<textarea class="form-control" readonly="readonly" style="width: 100%; height: 120px;" rows="15" cols="10">${list.remark}</textarea> --%>
							<!-- 																</div> -->
							<!-- 															</div> -->
							<!-- 														</div> -->
							<!-- 														<hr /> -->
							<!-- 														<div class="col-md-12"> -->
							<!-- 															<div class="form-group"> -->
							<!-- 																<label class="col-sm-2 control-label"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>报告时间:</label> -->
							<!-- 																<div class="col-sm-10"> -->
							<%-- 																	<input class="form-control" readonly="readonly" type="text" value="${list.createTime}"> --%>
							<!-- 																</div> -->
							<!-- 															</div> -->
							<!-- 														</div> -->
							<%-- 														<c:if test="${not empty list.attachs }"> --%>
							<!-- 															<hr /> -->
							<!-- 															<div class="col-md-12"> -->
							<!-- 																<div class="form-group"> -->
							<!-- 																	<label class="col-sm-2 control-label">相关附件:</label> -->
							<!-- 																	<div class="col-sm-10"> -->
							<!-- 																		<div class="uploader-list"> -->
							<!-- 																			<table class="table table-bordered"> -->
							<!-- 																				<thead> -->
							<!-- 																					<tr> -->
							<!-- 																						<th>文件名</th> -->
							<!-- 																						<th>上传人</th> -->
							<!-- 																						<th>上传时间</th> -->
							<!-- 																						<th>操作</th> -->
							<!-- 																					</tr> -->
							<!-- 																				</thead> -->
							<!-- 																				<tbody> -->
							<%-- 																					<c:forEach items="${list.attachs }" var="attachs"> --%>
							<!-- 																						<tr> -->
							<%-- 																							<td>${attachs.originalFilename }</td> --%>
							<%-- 																							<td>${attachs.uploader }</td> --%>
							<%-- 																							<td>${attachs.uploadTime }</td> --%>
							<%-- 																							<td><a target="_blank" href="${attachs.url }" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a></td> --%>
							<!-- 																						</tr> -->
							<%-- 																					</c:forEach> --%>
							<!-- 																				</tbody> -->
							<!-- 																			</table> -->
							<!-- 																		</div> -->
							<!-- 																	</div> -->
							<!-- 																</div> -->
							<!-- 															</div> -->
							<!-- 															</div> -->
							<!-- 															<div class="hr-line-dashed"></div> -->
							<!-- 															<div class="hr-line-dashed"></div> -->
							<%-- 														</c:if> --%>
							<%-- 													</c:forEach> --%>
							<%-- 												</c:when> --%>
							<%-- 												<c:otherwise> --%>
							<!-- 													<tr class="main_info"> -->
							<!-- 														<td colspan="13" class="center">没有相关数据</td> -->
							<!-- 													</tr> -->
							<%-- 												</c:otherwise> --%>
							<%-- 											</c:choose> --%>
							<!-- 										</div> -->
					</form>
				</div>
			</div>
		</div>
		<!--/span-->
	</div>
	<div class="text-center">
		<c:if test="${pd.isOperation &&  empty pd.isOperationDirector}">
			<a class="btn  btn-primary" onclick="save(0);"><i class="fa fa-save"></i> 保存</a>
			<a class="btn  btn-primary" onclick="save(1);"><i class="fa fa-send-o"></i> 提交</a>
		</c:if>
		<c:if test="${pd.isOperationDirector }">
		<c:if test="${not empty dataList[0].id}">
			<a class="btn  btn-primary" onclick="save(2);"><i class="fa fa-gavel"></i> 通过</a>
			<a class="btn  btn-danger" onclick="save(3);"><i class="fa fa-remove"></i> 驳回</a>
		</c:if>
		<c:if test="${empty dataList[0].id}">
			<a class="btn  btn-primary" onclick="save(2);"><i class="fa fa-save"></i> 保存</a>
		</c:if>
		</c:if>
		
	</div>
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
</body>
<script type="text/javascript">
	//保存
	function save(status) {
		if (yoValidate('#customerForm')) {
			$("#status").val(status);
			var index = layer.load(1, {
				shade : [ 0.2, '#fff' ]
			//0.1透明度的白色背景
			});
			$("#customerForm").submit();
		}
	}
	
	$(document).on("change","#reportType",function(){
		if(this.value!=1){
			$("#hxjhBOX").show().find("textarea").prop("disabled",false)
		}else{
			$("#hxjhBOX").hide().find("textarea").prop("disabled",true).val("")
		}
	})
	$('#reportType').trigger("change")
	
	
	var status = '${pd.status }'; //状态(0:草稿 1:已提交 2:已审核 3:驳回)
	if(status!=2){
	init_uploader()
	}
	
	var init_uploader_state = 0;

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
													+ '</td><td>案件报告</td><td>'
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
</script>
</html>