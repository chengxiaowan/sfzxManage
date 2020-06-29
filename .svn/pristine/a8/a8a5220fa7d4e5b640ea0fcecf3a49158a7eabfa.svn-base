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
								<div class="ibox-content">
									<div id="home" class="tab-pane in active">
										<form action="saleCustomer/${msg }.do" name="customerForm" id="customerForm" method="post"  class="form form-horizontal" >
										<input type="hidden" name="id" id="id" value="<c:out value="${pd.id}" default="0"></c:out>" />
										<div class="row">
												<div id="zhongxin" style="padding-top: 13px;">
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">客户名称<font color="red">*</font>：</label>
														        <div class="col-sm-8">
														            <input class="form-control" data-validate="require|maxLength=50" type="text" placeholder="这里输入客户名称"  name="name" id="name"  value="${pd.name }" >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														<div class="form-group">
															<label class="col-sm-4 control-label">所属省份&nbsp;<font color="red">*</font>：
															</label>
															<div class="col-sm-8">
																<select data-validate="require" class=" form-control" name="provinceName" id="provinceName" >
																	<option value="">请选择</option>
																	<c:forEach items="${sfList }" var="block">
																		<option data-id="${block.id}" value="${block.regionName}" <%-- <c:if test="${pd.province eq block.regionName}">selected="selected"</c:if> --%>>${empty block.regionName?'其他省份':block.regionName }</option>
																	</c:forEach>
																</select>
															</div>
														</div>
														</div>
														<div class="col-md-6">
															<div class="form-group">
																<label class="col-sm-4 control-label">所属市&nbsp;<font color="red">*</font>：
																</label>
																<div class="col-sm-8">
																	<select data-validate="require" class="form-control" name="cityName" id="cityName" >
																		<option value="">请选择</option>
																		<c:forEach items="${sqList }" var="project">
																			<option data-parent="${project.parentId }" data-id="${project.id}" value="${project.regionName}" <%-- <c:if test="${pd.cityId eq project.id}">selected="selected"</c:if> --%>>${empty project.regionName?'其他市区':project.regionName }</option>
																		</c:forEach>
																	</select>
																</div>
															</div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">具体地址：</label>
														        <div class="col-sm-8">
														            <input class="form-control" type="text" placeholder="这里输入具体地址"  name="companyAddress" id="companyAddress"  value="" >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">开户行：</label>
														        <div class="col-sm-8">
														            <input class="form-control" type="text" placeholder="这里输入开户行"  name="bankName" id="bankName"  value="" >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">开户行账号：</label>
														        <div class="col-sm-8">
														            <input class="form-control" type="text" placeholder="这里输入开户行账号"  name="bankNumber" id="bankNumber"  value="" >
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">纳税人识别号：</label>
														        <div class="col-sm-8">
														            <input class="form-control" type="text" placeholder="这里输入纳税人识别号"  name="taxIdentificationNumber" id="taxIdentificationNumber"  value="" >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">传真：</label>
														        <div class="col-sm-8">
														            <input class="form-control" data-validate="fax" type="text" placeholder="这里输入传真"  name="fax" id="fax"  value="" >
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-4 control-label">邮政编码：</label>
														        <div class="col-sm-8">
														            <input class="form-control" type="text" placeholder="这里输入邮政编码"  name="postCode"  id="postCode"  value=""  >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class=" col-sm-4 control-label">合同销售人：</label>
														        <div class=" col-sm-8">
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
														        <label class=" col-sm-4 control-label">订单销售人：</label>
														        <div class=" col-sm-8">
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
														<label  class="control-label col-sm-2">
															备注:
														</label>
														<div class="col-sm-10">
														<textarea class="form-control" style="height: 120px;" rows="15" cols="10" name="remark"  placeholder="这里输入备注"></textarea>
														</div>
													</div>	
													<div class="form-group">
														<label  class="control-label col-sm-2">
															联系人:
															
														</label>
														<div class="col-sm-10">
														
														
														<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<!-- <th class="center" style="width: 50px;">序号</th> -->
						<th class="center">姓名</th>
						<th class="center">职务</th>
						<th class="center">电话</th>
						<th class="center">手机</th>
					</tr>
				</thead>
				<tbody>
									
										<c:if test="${not empty pd.linkmanName ||not empty pd.postion || not empty pd.linkmanLandline || not empty pd.linkmanMobilePhone}">
										<!-- <td class="center" style="width: 30px;">0
										</td> -->
										<tr>
										<td class="center">${pd.linkmanName }<input type="hidden" name="linkmanName" value="${not empty pd.linkmanName? pd.linkmanName:'@xy@' }" /></td>
										<td class="center">${pd.postion  }<input type="hidden" name="linkmanPostion" value="${not empty pd.postion? pd.postion:'@xy@' }" /></td>
										<td class="center">${pd.linkmanLandline  }<input type="hidden" name="linkmanLandline" value="${not empty pd.linkmanLandline? pd.linkmanLandline:'@xy@' }" /></td>
										<td class="center">${pd.linkmanMobilePhone  }<input type="hidden" name="linkmanMobilePhone" value="${not empty pd.linkmanMobilePhone? pd.linkmanMobilePhone:'@xy@' }" /></td>
										</tr>
										</c:if>
										<c:if test="${not empty pd.linkmans }">
											<c:forEach items="${pd.linkmans }" var="linkman" varStatus="vs">
										<%-- <td class="center" style="width: 30px;"><c:if test="${not empty pd.linkmanName || not empty pd.postion || not empty pd.linkmanLandline || not empty pd.linkmanMobilePhone}">${vs.index+1}</c:if><c:if test="${empty pd.linkmanName &&empty pd.postion &&empty pd.linkmanLandline &&empty pd.linkmanMobilePhone}">${vs.index}</c:if>
										</td> --%>
										<tr>
										<td class="center">${linkman.name }<input type="hidden" name="linkmanName" value="${not empty linkman.name? linkman.name:'@xy@' }" /></td>
										<td class="center">${linkman.postion }<input type="hidden" name="linkmanPostion" value="${not empty linkman.postion ? linkman.postion:'@xy@'}" /></td>
										<td class="center">${linkman.landline }<input type="hidden" name="linkmanLandline" value="${not empty linkman.landline? linkman.landline:'@xy@' }" /></td>
										<td class="center">${linkman.mobilePhone }<input type="hidden" name="linkmanMobilePhone" value="${not empty linkman.mobilePhone? linkman.mobilePhone:'@xy@' }" /></td>
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
							flag:1,
							tm : new Date().getTime()
						},
						dataType : 'json',
						cache : false,
						async: false,
						success : function(data) {
							console.log(data)
							if ("success" != data.msg) {
								flag=false;
								layer.yoTips('客户名称' + name + '已存在',
										'#name')
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