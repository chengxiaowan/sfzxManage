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
<div class="wrapper wrapper-content">
<div class="row">
<div class="col-sm-12">
								<form action="invoice/${msg }.do" name="actionForm" id="actionForm" method="post" class="form form-horizontal">
									<input type="hidden" name="id" id="id" value="<c:out value="${pd.id}" default="0"></c:out>" /> <input type="hidden" name="invoiceType" id="invoiceType"
										value="<c:out value="${pd.invoiceType}" default="0"></c:out>" /> <input type="hidden" name="isTax" id="isTax" value="<c:out value="${pd.isTax}" default="0"></c:out>" />
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
													<label class="col-sm-4 control-label">发票号码：<font color="red">*</font>：
													</label>
													<div class="col-sm-8">
														<input class="form-control" id="invoiceCode" name="invoiceCode" value="${pd.invoiceCode }" data-validate="require|maxLength=50" type="text" placeholder="这里输入发票号码"  >
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">开票内容<font color="red">*</font>：</label>
													<div class="col-sm-8">
														<input class="form-control" id="invoiceContent" name="invoiceContent" value="${pd.invoiceContent }" data-validate="require|maxLength=200" type="text" placeholder="这里输入开票内容"
															 >
													</div>
												</div>
											</div>
											<hr />
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">开票日期<font color="red">*</font>：</label>
													<div class="col-sm-8">
														<input class="span10 date-picker form-control" id="invoiceTime" name="invoiceTime" value="${pd.invoiceTime }" data-validate="require" readonly="readonly" data-date-format="yyyy-mm-dd"
															type="text" placeholder="这里输入开票日期"  >
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group" style="height:34px">
													<label class="col-sm-4 control-label">票据类型：</label>
													<div class="col-sm-8">
														<div class="radio radio-success radio-inline">
															<input type="radio" <c:if test="${ empty pd.invoiceType || pd.invoiceType == '0'}">checked="checked"</c:if> onchange="setinvoiceType('0')" id="form-isinvoiceType-radio1"
																name="form-isinvoiceType-radio"> <label for="form-isinvoiceType-radio1">增值税发票 </label>
														</div>
														<div class="radio radio-success radio-inline">
															<input type="radio" <c:if test="${pd.invoiceType == '1'}">checked="checked"</c:if> onchange="setinvoiceType('1')" id="form-isinvoiceType-radio2" name="form-isinvoiceType-radio">
															<label for="form-isinvoiceType-radio2">普通发票</label>
														</div>
													</div>
												</div>
											</div>
											<hr />
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">票据金额：</label>
													<div class="col-sm-8">
														<input class="form-control" data-validate="require|price|maxLength=50" type="text" placeholder="这里输入票据金额" id="money" name="money" value="${pd.money }"  >
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label class="col-sm-4 control-label">是否含税：</label>
													<div class="col-sm-8">
														<div class="radio radio-success radio-inline">
															<input type="radio" <c:if test="${ empty pd.isTax || pd.isTax == '0'}">checked="checked"</c:if> onchange="setisTax('0')" id="form-isisTax-radio1" name="form-isisTax-radio"> <label
																for="form-isisTax-radio1">是</label>
														</div>
														<div class="radio radio-success radio-inline">
															<input type="radio" <c:if test="${pd.isTax == '1'}">checked="checked"</c:if> onchange="setisTax('1')" id="form-isisTax-radio2" name="form-isisTax-radio"> <label
																for="form-isisTax-radio2">否</label>
														</div>
													</div>
												</div>
											</div>
											<hr />
											<div class="col-md-12">
												<div class="form-group">
													<label class="col-sm-4 col-md-2 control-label">经手人<font color="red">*</font>：</label>
													<div class="col-md-10 col-sm-8">
														<input class="form-control" id="brokerage" name="brokerage" value="${pd.brokerage }" type="text" data-validate="require" placeholder="这里输入经手人"  >
													</div>
												</div>
											</div>
											<hr />
											<div class="col-md-12">
												<div class="form-group">
													<label class="control-label col-sm-4 col-md-2">备注: </label>
													<div class="col-md-10 col-sm-8">
														<textarea class="form-control" style=" height: 120px;" rows="15" cols="10" name="remark" placeholder="这里输入备注">${pd.remark}</textarea>
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
								 										<tr><td>${attachs.originalFilename }</td> <td>发票</td><td>${attachs.uploader }</td><td>${attachs.uploadTime }</td><td><a target="_blank" href="${attachs.url }" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a> <a class="btn btn-danger" onclick="list_del(this)"><i class="fa fa-trash-o"></i> 删除</a><input type="hidden" name="fileSize" value="${attachs.fileSize }"><input type="hidden" name="uploader" value="${attachs.uploader }"><input type="hidden" name="originalFilename" value="${attachs.originalFilename }"><input type="hidden" name="uploadTime" value="${attachs.uploadTime }"><input type="hidden" name="realPath" value="${attachs.realPath }"><input type="hidden" name="url" value="${attachs.url }"><input type="hidden" name="attachId" value="${attachs.id }"></td></tr>                           		
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

			<div class="text-center">
				<a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a>
			</div>
		</div>
	</div>
	<%@ include file="../../system/index/js.jsp"%>
</body>
<script type="text/javascript">
	//日期框
	$('.date-picker').datepicker({
		autoclose : true,
		todayHighlight : true
	});

	function setinvoiceType(value) {
		$("#invoiceType").val(value);
	}

	function setisTax(value) {
		$("#isTax").val(value);
	}

	//保存
	function save() {
		if (yoValidate('#actionForm')) {
			var id = $("#id").val();
			var invoiceCode = $("#invoiceCode").val().trim();
			$
					.ajax({
						type : "POST",
						url : '${pageContext.request.contextPath}/invoice/hasInvoiceCode.do',
						data : {
							id : id,
							invoiceCode : invoiceCode,
							tm : new Date().getTime()
						},
						dataType : 'json',
						cache : false,
						async : false,
						success : function(data) {
							if ("success" != data.result) {
								layer.yoTips('发票号码' + invoiceCode + '已存在',
										'#invoiceCode')
							} else {
								var index = layer.load(1, {
									shade : [ 0.2, '#fff' ]
								//0.1透明度的白色背景
								});
								$("#actionForm").submit();
							}
						}
					});
		}
	}
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
				'<tr><td>'+response.originalFilename+'</td><td>发票</td><td>'+response.uploader+'</td><td>'+response.uploadTime+'</td><td><a target="_blank" href="'+response.url+'" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a> <a class="btn btn-danger" onclick="list_del(this)"><i class="fa fa-trash-o"></i> 删除</a>'+hideVal()+'</td></tr>'
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
</script>
</html>