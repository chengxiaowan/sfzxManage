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
				<div class="col-xs-12">
							<div class="tabbable">
									<div id="home" class="tab-pane in active">
										<form action="linkman/${msg }.do" name="linkmanForm" id="linkmanForm" method="post"  class="form form-horizontal" >
										<input type="hidden" name="id" id="id" value="<c:out value="${pd.id}" default="0"></c:out>" />
										<c:if test="${not empty pd.noCustomer }">
										<input type="hidden" name="customerId" id="customerId" value="<c:out value="${pd.customerId}" default="0"></c:out>" />
										</c:if>
										<div class="row m-t-md">
												<div class="col-md-12">
													<c:if test="${empty pd.noCustomer && empty pd.id}">
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">客户名称<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														        <select class="form-control chosen-select"  data-validate="require|maxLength=50" name="customerId" id="customerId" >
														        	<c:if test="${not empty pd.customerId }">
														        		<option value="${pd.customerId }"  selected="selected">${pd.customerName }</option>
														        	</c:if>
														        </select>
														        </div>
														    </div>
														</div>
														</c:if>
														<c:if test="${not empty pd.id }">
														<div class="col-md-6">
															    <div class="form-group">
														        <label class="col-sm-3 control-label">客户名称<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														        	<input type="hidden" name="customerId" id="customerId" value="<c:out value="${pd.customerId}" default="0"></c:out>" />
														            <input class="form-control" data-validate="require|maxLength=50" readonly="readonly" type="text" placeholder="这里输入联系人名称"  name="customerName" id="customerName"  value="${pd.companyName }"  >
														        </div>
														    </div>
														    </div>
														</c:if>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">联系人名称<font color="red">*</font>：</label>
														        <div class="col-sm-9">
														            <input class="form-control" data-validate="require|maxLength=50" type="text" placeholder="这里输入联系人名称"  name="name" id="name"  value="${pd.name }"  >
														        </div>
														    </div>
														</div>
														
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">手机号码：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text"  placeholder="这里输入手机号码"  name="mobilePhone" id="mobilePhone"  value="${pd.mobilePhone }"  >
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">固定电话：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" placeholder="这里输入固定电话"  name="landline" id="landline"  value="${pd.landline }"  >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">邮箱：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" placeholder="这里输入邮箱"  name="email" id="email"  value="${pd.email }"  >
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">职务：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" placeholder="这里输入职务"  name="postion" id="postion"  value="${pd.postion }"  >
														        </div>
														    </div>
														</div>
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">传真：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" placeholder="这里输入传真"  name="fax" id="fax"  value="${pd.fax }"  >
														        </div>
														    </div>
														</div>
														<hr />
														<c:if test="${not empty pd.id }">
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">合同销售人：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text"  disabled="disabled" name="fax" id="fax"  value="${pd.contractSaleName }"  >
														        </div>
														    </div>
														</div>
														<hr />
														<div class="col-md-6">
														    <div class="form-group">
														        <label class="col-sm-3 control-label">订单销售人：</label>
														        <div class="col-sm-9">
														            <input class="form-control" type="text" disabled="disabled"   name="fax" id="fax"  value="${pd.orderSaleName }"  >
														        </div>
														    </div>
														</div>
														<hr />
														</c:if>
														<div class="col-md-12">
													<div class="form-group">
														<label  class="control-label col-sm-2 no-padding-right" style="width: 11.66666667%;">
															备注:
														</label>
														<div class="col-sm-9 col-md-10" style="width: 88.333333%;">
														<textarea class="form-control" style="height: 180px;resize: none;" rows="15" cols="10" name="remark"  placeholder="这里输入备注">${pd.remark}</textarea>
														</div>
													</div>	
													</div>													
												</div>
												</div>
										</form>
							</div>
					<div class="text-center">
						<a class="btn btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a> 
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	</div>
	<!-- /.main-container -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
</body>
<script type="text/javascript">

	 $(function(){
		 $(".chosen-select").select2({
				width: '100%',
				language:"zh-CN",
				allowClear: true,
				placeholder: "请选择",
			    placeholderOption: "first",
					  ajax: {
						    url: '/customer/getCustomerInfo',
						    dataType: 'json',
						    type:'post',
						    delay: 250,
						    data: function (params) {
						      return {
						    	  page: params.page || 1,
						        data:{
						        	q: params.term, 
						        }
						      }
						    },
						    processResults: function (data, params) {
						    	$.each(data,function(){
						    		this.id = this.id;
						    		this.text = this.name
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
		 
		 
		 
		 
		 
	});
	 
	 //清空chosen
	 function clearChosen (){
		 $(".chosen-select").val("");
		 $(".chosen-select").trigger("chosen:updated");
	 }
	 
	 
	//保存
	function save() {
		if (yoValidate('#linkmanForm')) {
			var id = $("#id").val();
			var mobilePhone = $("#mobilePhone").val().trim();
			$("#linkmanForm").submit();
/*			$.ajax({
				type : "POST",
				url : '${pageContext.request.contextPath}/linkman/hasMobilePhone.do',
				data : {
					id : id,
					mobilePhone : mobilePhone,
					tm : new Date().getTime()
				},
				dataType : 'json',
				cache : false,
				async: false,
				success : function(data) {
					if ("success" != data.result) {
						layer.tips('手机号码' + mobilePhone + '已存在',
								'#mobilePhone')
					} else {
					 	var index = layer.load(1, {
								shade : [ 0.2, '#fff' ]
							//0.1透明度的白色背景
							});
							
						} 
				}
			});
*/		}
	}
	
	function chooseOrder() {
		sessionStorage.ListOrdersCache = '0' //关闭缓存
		var index = layer
				.open({
					type : 2,
					title : '选择案件',
					content : '${pageContext.request.contextPath}/order/chooseOrder.do',
					area : [ '90%', '90%' ],
					btn : [ '确定', '取消' ],
					yes : function(index, layero) {
						var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
						var data = iframeWin.pushToParent()
						layer.close(index)
						$("#orderId").val(data.ids); //赋值子页面传过来的IDS
						$("#orderNo").val(data.orderNos);
						sessionStorage.ListOrdersCache = '0' //关闭缓存
					}
				});
	}
	

</script>
</html>