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
		<div class="ibox-content">
			<div class="row">
				<div class="col-xs-12">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a data-toggle="tab" href="#home">基本信息</a></li>
									<li><a data-toggle="tab" onclick="init_uploader()" href="#attach">相关附件</a></li>
								</ul>
										<form action="handover/${msg }.do" name="handoverForm" id="handoverForm" method="post"  class="form form-horizontal" >
								<div class="tab-content">
									<div id="home" class="tab-pane in active" style="padding-top:15px">
									<input type="hidden" name="id" value="${pd.id }">
										<input type="hidden" name="status" value="${pd.status }">
										<input type="hidden" name="orderId" value="${pd.orderId }">
														<c:if test="${pd.status==3 }">
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">状态<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														        	<select class="chosen-select form-control" data-validate="require"	name="susongstatus" id="susongstatus" data-placeholder="请选择状态" style="vertical-align: top;" >
																		<option value="">请选择</option>
																		<option value="0" <c:if test="${pd.susongstatus == '0' }">selected</c:if>>一审</option>
																		<option value="1" <c:if test="${pd.susongstatus == '1' }">selected</c:if>>二审</option>
																		<option value="2" <c:if test="${pd.susongstatus == '2' }">selected</c:if>>执行</option>
																	</select>
														        </div>
														    </div>
														</div>
														</c:if>
														<c:if test="${pd.status==4 }">
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">状态<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														        	<select class="chosen-select form-control" data-validate="require" name="zongcstatus" id="zongcstatus" data-placeholder="请选择状态" style="vertical-align: top;">
																		<option value="">请选择</option>
																		<option value="0" <c:if test="${pd.zongcstatus == '0' }">selected</c:if>>仲裁</option>
																		<option value="1" <c:if test="${pd.zongcstatus == '1' }">selected</c:if>>执行</option>
																	</select>
														        </div>
														    </div>
														</div>
														</c:if>
														<%-- <div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">宣判时间<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														        	<input class="span10 date-picker form-control" data-validate="require" readonly="readonly" data-date-format="yyyy-mm-dd" type="text" placeholder="这里输入宣判时间" name="time"
																	value="${pd.time }">
														        </div>
														    </div>
														</div> --%>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">备注<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														            <textarea class="form-control" data-validate="require|maxLength=500" placeholder="这里输入备注"  name="remark" id="remark" >${pd.remark }</textarea>
														        </div>
														    </div>
														</div>
														</div>
														<div id="attach" class="tab-pane">
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
											                            		<c:forEach items="${pd.attachs }"  var="attachs">
											 										<tr><td>${attachs.originalFilename }</td><td>案件交接</td><td>${attachs.uploader }</td><td>${attachs.uploadTime }</td><td><a target="_blank" href="${attachs.url }" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a> <a class="btn btn-danger" onclick="list_del(this)"><i class="fa fa-trash-o"></i> 删除</a><input type="hidden" name="fileSize" value="${attachs.fileSize }"><input type="hidden" name="uploader" value="${attachs.uploader }"><input type="hidden" name="originalFilename" value="${attachs.originalFilename }"><input type="hidden" name="uploadTime" value="${attachs.uploadTime }"><input type="hidden" name="realPath" value="${attachs.realPath }"><input type="hidden" name="url" value="${attachs.url }"><input type="hidden" name="attachId" value="${attachs.id }"></td></tr>                           		
											                            		</c:forEach>
											                            	</c:if>
											                            </tbody>
											                        </table>
																</div>
															</div>
															</div>
										</form>
										
										</div>
														</div>
									</div>
								</div>
					<div class="text-center">
						<a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a>
					</div>
	</div>
	<%@ include file="../../system/index/js.jsp"%>
</body>
<script type="text/javascript">
	
	$(".date-picker").datepicker({
		autoclose : true,
		todayHighlight : true
	});
	
	var init_uploader_state=0;

	function init_uploader(){
		if(init_uploader_state) return;
		init_uploader_state=1
	var uploader = WebUploader.create({
	    // 文件接收服务端。
	    server:"${pageContext.request.contextPath}/contract/uploadAttachment.do",
	    runtimeOrder:'html5',
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: {
		    id: '#fileUp',
		    innerHTML: '上传附件'
		},
	    resize: false,
	    fileNumLimit:10,
	});
	
	//上传成功
	uploader.on( 'uploadSuccess', function(file,response ) {
		console.log(file,response);
		$("#realPath").val(response.realPath);
		$("#originalFilename").val(response.originalFilename);
		$("#fileSize").val(response.fileSize);
		$("#url").val(response.url);
		$("#uploadTime").val(response.uploadTime); 
		$("#uploader").val(response.uploader); 
		var hideVal = function(){
			var html = '';
			for(i in response){
				if(i!='_raw') html+='<input type="hidden" name="'+i+'" value="'+response[i]+'" />'
			}
			html+='<input type="hidden" name="attachId" value="0" />';
			return html;
		}
		$("#fileList tbody").append(   
				'<tr><td>'+response.originalFilename+'</td><td>合同</td><td>'+response.uploader+'</td><td>'+response.uploadTime+'</td><td><a target="_blank" href="'+response.url+'" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a> <a class="btn btn-danger" onclick="list_del(this)"><i class="fa fa-trash-o"></i> 删除</a>'+hideVal()+'</td></tr>'
		)
		$( '#'+file.id ).addClass('upload-state-done');
	});
	
	uploader.on('uploadStart',function(){
		layer.load();   //上传过程中开启loading遮罩
	})
	//上传出错
	uploader.on( 'uploadError', function( file ) {
	    $( '#'+file.id ).find('p.state').text('上传出错');
	});
	//出错
	uploader.on( 'error', function(res) {
		if(res =='Q_TYPE_DENIED'){
			alert('上传文件格式错误，请检查')
		}
	});
	//上传完成
	uploader.on( 'uploadComplete', function( file ) {
	    $( '#'+file.id ).find('.progress').fadeOut();
		layer.closeAll()   //关闭遮罩层
	});
	//文件加入队列
	uploader.on('fileQueued',function(){
		uploader.upload()
	})	
	}
	
	//删除附件
	function list_del(dom){
		$(dom).parents('tr').remove()
	}
		
	function save() {
		if (yoValidate('#handoverForm')) {
			 		var index = layer.load(1, {
						shade : [ 0.2, '#fff' ]
					//0.1透明度的白色背景
					});
			 		$("#handoverForm").submit();
				} 
			}
</script>
</html>