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
<!-- 下拉框 -->
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/chosen.css" /> --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ace/css/colorbox.css" />
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-content-inner">
		<div class="page-content" style="margin-top:10px">
			<div class="row">
				<div class="col-xs-12">
							<div class="tabbable">
								<div class="ibox-content" style="border-style: none;">
									<div id="home" class="tab-pane in active">
										<form action="customer/${msg }.do" name="customerForm" id="customerForm" method="post"  class="form form-horizontal" >
										<input type="hidden" name="id" id="id" value="<c:out value="${pd.id}" default="0"></c:out>" />
										<input type="hidden" name="orignalOrderSaleId" id="orignalOrderSaleId" value="<c:out value="${pd.orderSaleId}" default="0"></c:out>" />
										<div class="row">
												<div id="zhongxin" style="padding-top: 13px;">
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">客户名称<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														            <input class="form-control" data-validate="require|maxLength=50" type="text" placeholder="这里输入客户名称"  name="name" id="name"  value="${pd.name }" >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-3 control-label">所属省份&nbsp;<font color="red">*</font>：
															</label>
															<div class="col-sm-9">
																<select data-validate="require" class=" form-control" name="provinceName" id="provinceName" >
																	<option value="">请选择</option>
																	<c:forEach items="${sfList }" var="block">
																		<option data-id="${block.id}" value="${block.regionName}" <c:if test="${pd.provinceName eq block.regionName}">selected="selected"</c:if>>${empty block.regionName?'其他省份':block.regionName }</option>
																	</c:forEach>
																</select>
															</div>
														</div>
														</div>
														<div class="col-md-6">
															<div class="form-group">
																<label class="col-sm-3 control-label">所属市&nbsp;<font color="red">*</font>：
																</label>
																<div class="col-sm-9">
																	<select data-validate="require" class="form-control" name="cityName" id="cityName" >
																		<option value="">请选择</option>
																		<c:forEach items="${sqList }" var="project">
																			<option data-parent="${project.parentId }" data-id="${project.id}" value="${project.regionName}" <c:if test="${pd.cityId eq project.id}">selected="selected"</c:if>>${empty project.regionName?'其他市区':project.regionName }</option>
																		</c:forEach>
																	</select>
																</div>
															</div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">具体地址：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" placeholder="这里输入具体地址"  name="companyAddress" id="companyAddress"  value="${pd.companyAddress }" >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">开户行：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" placeholder="这里输入开户行"  name="bankName" id="bankName"  value="${pd.bankName }" >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">开户行账号：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" placeholder="这里输入开户行账号"  name="bankNumber" id="bankNumber"  value="${pd.bankNumber }" >
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">纳税人识别号：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" placeholder="这里输入纳税人识别号"  name="taxIdentificationNumber" id="taxIdentificationNumber"  value="${pd.taxIdentificationNumber }" >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">传真：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" placeholder="这里输入传真"  name="fax" id="fax"  value="${pd.fax }" >
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">邮政编码：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" placeholder="这里输入邮政编码"  name="postCode"  id="postCode"  value="${pd.postCode }"  >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class=" col-sm-3 control-label">合同销售人：</label>
														        <div class=" col-sm-9">
														            <select class="chosen-select-sale form-control" name="contractSaleId" id="contractSaleId">
														            		<c:if test="${not empty pd.contractSaleId }">
														            			<option value="${pd.contractSaleId }">${pd.contractSaleName }</option>
														            		</c:if>
														            </select>
														        </div>
														    </div>
														</div>
														<div class="clearfix"></div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class=" col-sm-3 control-label">订单销售人：</label>
														        <div class=" col-sm-9">
														            <select class="chosen-select-sale form-control" name="orderSaleId" id="orderSaleId">
														            	<c:if test="${not empty pd.orderSaleId }">
														            			<option value="${pd.orderSaleId }">${pd.orderSaleName }</option>
														            	</c:if>
														            </select>
														        </div>
														    </div>
														</div>
														<div class="col-md-12">
													<div class="form-group">
														<label  class="control-label col-sm-2 no-padding-right" style="width: 11.666667%;">
															备注:
														</label>
														<div class="col-sm-10"  style="width: 88.333333%;">
														<textarea class="form-control" style="width: 99%; height: 180px;resize: none;margin-left: 1%;" rows="15" cols="10" name="remark"  placeholder="这里输入备注">${pd.remark}</textarea>
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
						<!--/span-->
					</div>
					<div class="text-center">
						<a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a>
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
	<%@ include file="../../system/index/js.jsp"%>
</body>
<script type="text/javascript">

	var area1 = $("select[name='provinceName']")
	var area2 = $("select[name='cityName']")
	function areaInit() {
		area2.find('option').hide()
		if (area1.val() == '') {
			/* area2.find('option').show().eq(0).prop('selected',true)  */
		} else {
			var s = area2.find("[value='']").show();
			var options = area2.find("[data-parent='"
					+ area1.find("option:selected").data('id') + "']")
			options.show()
		}
		area1.trigger("chosen:updated");
		area2.trigger("chosen:updated");
		/* layer.close(loading) */
	}
	area1.chosen();
	area2.chosen();
	areaInit()
	area1.change(function() {
		area2.val('');
		areaInit()
	})
	//初始化销售人
		$(".chosen-select-sale").select2({
	width: '100%',
	language:"zh-CN",
	allowClear: true,
	placeholder: "请选择",
    placeholderOption: "first",
		  ajax: {
			    url: '/user/getUserInfo',
			    dataType: 'json',
			    type:'post',
			    delay: 250,
			    data: function (params) {
			      return {
			    	  page: params.page || 1,
			        data:{
			        	q: params.term, 
			        },
			      ROLE_ID:"90564dd8b75a4f6d815ce418462d401c,e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b",
			      }
			    },
			    processResults: function (data, params) {
			    	$.each(data,function(){
			    		this.id = this.USER_ID;
			    		this.text = this.NAME
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
	
	//保存
	function save() {
		if (yoValidate('#customerForm')) {
			var flag= false;
			var id = $("#id").val();
			var name = $("#name").val().trim();
		/* 	var mobilePhone = $("#mobilePhone").val().trim(); */
			$.ajax({
						type : "POST",
						url : '${pageContext.request.contextPath}/customer/hasName.do',
						data : {
							id : id,
							name : name,
							tm : new Date().getTime()
						},
						dataType : 'json',
						cache : false,
						async: false,
						success : function(data) {
							if ("00" != data.error) {
								flag=false;
								layer.msg(data.msg)
							} else {
								flag= true;
							}
						}
					});
			if(flag){
				var index = layer.load(1, {
					shade : [ 0.2, '#fff' ]
				//0.1透明度的白色背景
				});
				$("#customerForm").submit();
				
			}
		}
	}

</script>
</html>