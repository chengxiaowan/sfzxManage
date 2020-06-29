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
									<li class="active"><a data-toggle="tab" href="#home">基本信息</a></li>
									<li><a data-toggle="tab" href="#attach" onclick="init_uploader()">相关附件</a></li>
								</ul>
								<div class="ibox-content">
										<form action="contract/${msg }.do" name="contractForm" id="contractForm" method="post"  class="form form-horizontal" >
										<div class="tab-content">
										<div id="home" class="tab-pane in active">
										<input type="hidden" name="id" id="id" value="<c:out value="${pd.id}" default="0"></c:out>" />
										<input type="hidden" name="type" id="type" value="<c:out value="${pd.type}" default="0"></c:out>" />
										<input type="hidden" name="originalSaleId" id="originalSaleId" value="<c:out value="${pd.userId}" default="0"></c:out>" />
										<%-- <input type="hidden" name="customerId" id="customerId" value="${pd.customerId}" />
										<input type="hidden" name="userId" id="userId" value="${pd.userId}" /> --%>
										<div class="row">
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">合同编号&nbsp;<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														            <input class="form-control" data-validate="require|maxLength=50" type="text" placeholder="这里输入合同编号"  name="contractNo" id="contractNo"  value="${pd.contractNo }"  >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group chosen-input-group">
														        <label class="col-sm-3 control-label">客户名称&nbsp;<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														        <select class="form-control chosen-select" data-validate="require|maxLength=50" name="customerId" id="customerId" >
														        	<c:if test="${not empty pd.customerId }">
														        		<option value="${pd.customerId }"  selected="selected">${pd.customerName }</option>
														        	</c:if>
														        </select>
														        </div>
														    </div>
														</div>
														<c:if test="${pd.type==1 }">
														<%-- <div class="col-md-6">
														    <div class="form-group chosen-input-group">
														        <label class="col-sm-3 control-label">债务人名称&nbsp;<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														        <select class="form-control" data-validate="require|maxLength=50" name="debtorId" id="debtorId" >
														        	<c:if test="${not empty pd.debtorId }">
														        		<option value="${pd.debtorId }"  selected="selected">${pd.debtorName }</option>
														        	</c:if>
														        </select>
														        </div>
														    </div>
														</div> --%>
														<div class="col-md-6">
														    <div class="form-group ">
														        <label class="col-sm-3 control-label">债务人(公司) &nbsp;<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														        <divn class="input-group">
																	<input type="text" class="form-control" data-id="${pd.debtorId }" name="debtorName" id="debtorName"
																	value= "${pd.debtorName }" />
								                                    <input type="hidden" name="debtorId" id="debtorId" value = "${pd.debtorId }"/>
								                                  <div class="input-group-btn">
								                                        <button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown">
								                                            <span class="caret"></span>
								                                        </button>
								                                        <ul class="dropdown-menu dropdown-menu-right" role="menu"></ul>
								                                    </div>
								                                    </div>
														        </div>
														    </div>
														
														</c:if>
														<c:if test="${pd.type==0 }">
														<input type="hidden" name="fszss" id="fszss" value="<c:out value="${pd.fszss}" default="0"></c:out>" />
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">债务人(公司)&nbsp;<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" data-validate="require" placeholder="这里输入债务人名称"  name="debtorName" id="debtorName"  value="${pd.debtorName }"  >
														        </div>
														    </div>
														</div>
														</c:if>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">合同签订时间&nbsp;<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														            <input class="span10 date-picker form-control" data-validate="require" readonly="readonly" data-date-format="yyyy-mm-dd" type="text" placeholder="这里输入合同签订时间"  name="signingTime" id="signingTime"  value="${pd.signingTime }"  >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">合同到期时间&nbsp;<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														            <input class="span10 date-picker form-control" data-validate="require" readonly="readonly" data-date-format="yyyy-mm-dd" type="text" placeholder="这里输入合同到期时间"  name="endTime" id="endTime"  value="${pd.endTime }"  >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">合同签约地点：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" placeholder="这里输入合同签约地点"  name="address" id="address"  value="${pd.address }"  >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">合同总金额(元)<!-- &nbsp;<font color="red">*</font> -->：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" data-validate="price" placeholder="这里输入合同总金额"  name="price" id="price"  value="${pd.price }"  >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">佣金比例(%)<!-- &nbsp;<font color="red">*</font> -->：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" data-validate="number" placeholder="这里输入佣金比例"  name="commissionRate" id="commissionRate"  value="${pd.commissionRate }"  >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">销售人&nbsp;<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														        <select class="form-control chosen-select-sale" data-validate="require" name="userId" id="userId" >
														        	<c:if test="${not empty pd.userId }">
														        		<option value="${pd.userId }"  selected="selected">${pd.userName }</option>
														        	</c:if>
														        </select>
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">商务外协&nbsp;:</label>
														        <div class="col-sm-9">
														        <select class="form-control chosen-select-swwx"  name="swwxId" id="swwxId" >
														        	<c:if test="${not empty pd.swwxId }">
														        		<option value="${pd.swwxId }"  selected="selected">${pd.swwxName }</option>
														        	</c:if>
														        </select>
														        </div>
														    </div>
														</div>
														<c:if test="${pd.type==1 }">
														<input type="hidden" name="fszss" id="fszss" value="<c:out value="${pd.fszss}" default="1"></c:out>" />
														<div class="col-md-6">
															<div class="form-group" style="height:34px">
																<label class="col-sm-3 control-label">非诉转诉讼&nbsp;<font color="red">*</font>:</label>
																<div class="col-sm-9">
																	<div class="radio radio-success radio-inline">
																		<input type="radio" <c:if test="${empty pd.fszss || pd.fszss == '1'}">checked="checked"</c:if> onchange="setinvoiceType('1')" id="form-isinvoiceType-radio2" name="form-isinvoiceType-radio">
																		<label for="form-isinvoiceType-radio2">是</label>
																	</div>
																	<div class="radio radio-success radio-inline">
																		<input type="radio" <c:if test="${pd.fszss == '0'}">checked="checked"</c:if> onchange="setinvoiceType('0')" id="form-isinvoiceType-radio1"
																			name="form-isinvoiceType-radio"> <label for="form-isinvoiceType-radio1">否 </label>
																	</div>
																</div>
															</div>
														</div>
														</c:if>
														</div> 
														<div class="col-md-12">
													<div class="form-group">
														<label  class="control-label col-sm-2" style="width: 10.66666667%;">
															备注:
														</label>
														<div class="col-sm-10" style="width: 89.333333%;">
														<textarea class="form-control" style="height: 180px;margin-left: 10px;resize: none;"  name="remark"  placeholder="这里输入备注">${pd.remark}</textarea>
														</div>
													</div>	
													</div>													
												</div>
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
								                            		<c:forEach items="${pd.attachs }"  var="attachs">
								 										<tr><td>${attachs.originalFilename }</td><td>合同</td><td>${attachs.uploader }</td><td>${attachs.uploadTime }</td><td><a target="_blank" href="${attachs.url }" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a> <a class="btn btn-danger" onclick="list_del(this)"><i class="fa fa-trash-o"></i> 删除</a><input type="hidden" name="fileSize" value="${attachs.fileSize }"><input type="hidden" name="uploader" value="${attachs.uploader }"><input type="hidden" name="originalFilename" value="${attachs.originalFilename }"><input type="hidden" name="uploadTime" value="${attachs.uploadTime }"><input type="hidden" name="realPath" value="${attachs.realPath }"><input type="hidden" name="url" value="${attachs.url }"><input type="hidden" name="attachId" value="${attachs.id }"></td></tr>                           		
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
					<div class="text-center">
						<a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a>
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	<!-- /.main-container -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/ui/js/bootstrap-suggest.min.js"></script>
</body>
<script type="text/javascript">
var pdType  = '${pd.type}'
if(pdType!='1'){
var debtorInfo = $("#debtorId").select2()
}
var customerId = $("#customerId").val()
var testdataBsSuggest
var editMode = customerId ? 1 : 0
initSuggest()
function initSuggest (){
	if(!editMode){
		$("#debtorName").bsSuggest("destroy").val(null)
		$("#debtorId").val(null)
	}
		editMode=1
	testdataBsSuggest = $("#debtorName").bsSuggest({
		indexId: 0,
	    indexKey: 1, 
	    getDataMethod:'url',
	    inputWarnColor:'#fff',
	    effectiveFields: ['name'],
	    showBtn: false, 
	    url: '/contract/getDebtorInfo1?customerId=' +customerId + '&key=',
	    processData:function(json){
	    	var data = []
	    	for(var i = 0;i<json.length;i++){
	    		data.push({
	    			id:json[i].id,
	    			name:json[i].name
	    		})
	    	}
	    	return {
	    		value:data
	    	}
	    }
	}).on('onSetSelectValue', function (e, keyword) {
        $("#debtorId").val(keyword.id)
    })
    .on('onUnsetSelectValue', function (e) {
    	$("#debtorId").val(null)
    });
}


function setinvoiceType(value) {
	$("#fszss").val(value);
}

$(function(){
	  $(".chosen-select").select2({
		 placeholder:"请选择",
		 language: 'zh-CN',
		  ajax: {
		    url: '/customer/getCustomerInfo',
		    dataType: 'json',
		    type:"post",
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
		}).on('change',function(){
			if(pdType!='1'){
				debtorInfo.select2('destroy');
				}
		if(pdType!='1'){
			$("#debtorId").html("")
			debtorInfo_init(this.value)
			}else{
				$("#debtorName").bsSuggest("destroy").val(null)
				$("#debtorId").val(null)
				customerId = this.value
				initSuggest()
			}
		})
		
		
		function debtorInfo_init(customerId){
		 debtorInfo = $("#debtorId").select2({
			 placeholder:"请选择",
			 language: 'zh-CN',
			  ajax: {
			    url: '/contract/getDebtorInfo',
			    dataType: 'json',
			    type:"post",
			    delay: 250,
			    data: function (params) {
			      return {
			        page: params.page || 1,
				        customerId : customerId,
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
		})
	 } 
		
	  
	    
		
	
	
	
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
			        ROLE_ID:"90564dd8b75a4f6d815ce418462d401c,e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b",
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
		 
		 $(".chosen-select-swwx").select2({
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
			        ROLE_ID:"fbe6f2f9535c4fce9f024f6cb999b2bd",
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
	
})

	var init_uploader_state=0;
	$('.date-picker').datepicker({
		autoclose : true,
		todayHighlight : true
	});
	
	$("#endTime").datepicker('setStartDate',
			new Date($("#signingTime").val()))
	//结束日期
	$("#signingTime").datepicker({}).on(
			'changeDate',
			function(ev) {
				if (ev.date) {
					$("#endTime").datepicker('setStartDate',
							new Date(ev.date.valueOf()))
				} 
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

	//查找客户	
	function chooseCustomer() {
		sessionStorage.listUsersCache = '0'   //关闭缓存
		var index = layer.open({
			  type: 2,
			  title:'选择签约客户',
			  content: '${pageContext.request.contextPath}/customer/chooseCustomer.do',
			  area: ['90%', '90%'],
			  btn: ['确定', '取消'], 
			  yes: function(index, layero){
				    var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
				    var data = iframeWin.pushToParent()
				    layer.close(index)
				    $("#customerId").val(data.ids);   //赋值子页面传过来的IDS
					$("#customerName").val(data.names);
					sessionStorage.listUsersCache = '0'   //关闭缓存
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
					content : '${pageContext.request.contextPath}/user/chooseUser.do?flags=0&ROLE_ID=30b1487221464d369ca4c2462ccca920,b729e9334ad64c15a4e47d88b8c2638f&parIds=userId&parNames=userName',
					area : [ '90%', '90%' ],
					btn : [ '确定', '取消' ],
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
</script>
</html>