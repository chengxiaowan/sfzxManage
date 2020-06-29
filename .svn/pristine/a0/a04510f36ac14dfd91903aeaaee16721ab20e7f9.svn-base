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
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
					<div class="ibox-content">
						<div id="home" class="tab-pane in active">
							<form action="lawsuitaudit/${msg }.do" name="customerForm" id="customerForm" method="post" class="form form-horizontal">
								<input type="hidden" name="sy_workwench" id="sy_workwench" value="${pd.sy_workwench}" />
								<input type="hidden" name="auditStatus" id="auditStatus" value="${pd.auditStatus}" /> <input type="hidden" name="applyType" id="applyType" value="${pd.applyType}" /> <input type="hidden"
									name="id" id="id" value="${pd.id}" /> <input type="hidden" name="orderId" id="orderId" value="${pd.orderId}" /> <input type="hidden" name="userId" id="userId" value="${pd.userId}" /> <input
									type="hidden" name="remark" id="remark" value="${pd.remark}" />
									<input type="hidden" name="recipientName" id="recipientName" value="${pd.recipientName }" />
								<div id="dataList" class="tab-pane active">
									<div class="col-md-12">
										<div class="form-group">
											<label class="col-sm-3 control-label">客户名称:</label>
											<div class="col-sm-9">
												<input class="form-control" disabled="disabled" type="text" value="${pd.custoemrName}">
											</div>
										</div>
									</div>
									<div class="col-md-12">
										<div class="form-group">
											<label class="col-sm-3 control-label">债务人(公司):</label>
											<div class="col-sm-9">
											<div class="input-group">
												<input class="form-control" disabled="disabled" type="text" value="${pd.debtorName}">
												<span class="input-group-btn">
                                       			 <button type="button" class="btn btn btn-primary" onclick="viewCase(${pd.orderId})"> <i class="fa fa-search"></i> 查看</button>
                                </span>
                                </div>
											</div>
										</div>
									</div>
									<div class="col-md-12">
										<div class="form-group">
											<label class="col-sm-3 control-label">申请时间:</label>
											<div class="col-sm-9">
												<input class="form-control" disabled="disabled" type="text" value="${pd.createTime}">
											</div>
										</div>
									</div>
									<hr />
									<div class="col-md-12">
										<div class="form-group">
											<label class="col-sm-3 control-label">申请原因:</label>
											<div class="col-sm-9">
												<textarea class="form-control" disabled="disabled" style="width: 100%; height: 120px;" rows="15" cols="10">${pd.remark}</textarea>
											</div>
										</div>
									</div>
									<hr />
									<!-- 											<div class="col-md-12"> -->
									<!-- 												<div class="form-group"> -->
									<!-- 													<label class="col-sm-3 control-label">审核状态:</label> -->
									<!-- 													<div class="col-sm-9"> -->
									<%-- 														<input class="form-control" disabled="disabled" type="text" value="${pd.applyType==0?'诉讼':'仲裁' }"> --%>
									<!-- 													</div> -->
									<!-- 												</div> -->
									<!-- 											</div> -->
									<!-- 											<hr /> -->
									<!-- 											<div class="col-md-12"> -->
									<!-- 												<div class="form-group"> -->
									<!-- 													<label class="col-sm-3 control-label">审核人:</label> -->
									<!-- 													<div class="col-sm-9"> -->
									<%-- 														<input class="form-control"  disabled="disabled" type="text" value="${pd.auditer}"> --%>
									<!-- 													</div> -->
									<!-- 												</div> -->
									<!-- 											</div> -->
									<!-- 											<hr /> -->
									<div class="col-md-12">
										<div class="form-group">
											<label class="col-sm-3 control-label">是否通过审核&nbsp;<font color="red">*</font>：
											</label>
											<div class="col-sm-9">
												<div class="radio radio-success radio-inline">
													<input type="radio" <c:if test="${ pd.auditStatus=='0' || pd.auditStatus == '2'}">checked="checked"</c:if> onchange="setDispute('2')" id="form-isDispute-radio1"
														name="form-isDispute-radio"> <label for="form-isDispute-radio1"> 否 </label>
												</div>
												<div class="radio radio-success radio-inline">
													<input type="radio" <c:if test="${pd.auditStatus == '1'}">checked="checked"</c:if> onchange="setDispute('1')" id="form-isDispute-radio2" name="form-isDispute-radio"> <label
														for="form-isDispute-radio2"> 是 </label>
												</div>
											</div>
										</div>
									</div>
									<div class="zyxx col-md-12"></div>
									<!-- 									<div class="col-md-12"> -->
									<!-- 										<div class="form-group"> -->
									<!-- 											<label class="col-sm-3 control-label">审核时间&nbsp;<font color="red">*</font>: -->
									<!-- 											</label> -->
									<!-- 											<div class="col-sm-9"> -->
									<%-- 												<input class="span10 date-picker form-control" name="auditTime" data-date-format="yyyy-mm-dd" type="text" value="${fn:substring(pd.auditTime, 0, 10)}"> --%>
									<!-- 											</div> -->
									<!-- 										</div> -->
									<!-- 									</div> -->
									<hr />
									<div class="col-md-12">
										<div class="form-group">
											<label class="col-sm-3 control-label">审核意见:</label>
											<div class="col-sm-9">
												<textarea class="form-control" name="auditIdea" style="width: 100%; height: 120px;" rows="15" cols="10">${pd.auditIdea}</textarea>
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
		<div class="text-center m-b-md">
			<c:if test="${empty dataList[0].auditer }">
				<a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a>
			</c:if>
		</div>
	</div>
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
</body>
<script type="text/javascript">
	$('.date-picker').datepicker({
		autoclose : true,
		todayHighlight : true
	});

	function viewCase (id){
		top.layer.open({
			type:2,
			title:"详情",
			area:["100%","100%"],
			content:"/static/page/case_detail.html?id="+id
		})
	}
	
	
	
	function setDispute(value) {
		$("#auditStatus").val(value);
		if (value == 1) {
			var html = '<div class="form-group">'
					+ '<label class="col-sm-3 control-label">交接人&nbsp;<font color="red">*</font>：</label>'
					+ '<div class="col-sm-9">'
					/* + '<input class="form-control" type="text" onclick="chooseUser()" data-validate="require|maxLength=50" placeholder="这里输入交接人"  name="recipientName" id="recipientName"  value="${pd.recipientName }"">' */
					+'<select class="form-control chosen-select-sale" name="recipientId" id="recipientId">'
					+'<c:if test="${not empty pd.recipientId }">'
					+'<option value="${pd.recipientId }" selected="selected">${pd.recipientName }</option>'
					+'</c:if>'
					+'</select>'
					+ '</div></div>'
			html += '<div class="form-group">'
					+ '<label class="col-sm-3 control-label">交接时间：</label>'
					+ '<div class="col-sm-9">'
					+ '<input class="form-control date-picker" type="text" data-date-format="yyyy-mm-dd" name="handoverTime" id="handoverTime"  value="" >'
					+ '</div></div>'
			$(".zyxx").append(html)
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
		           ROLE_ID:"02178e62f17b4926bb7014f3ad5a1ebe",
		        data:{
		        	 q: params.term || "", // search term
		        }
		      };
		    },
		    processResults: function (data, params) {
		      params.page = params.page || 1;
		      $.each(data,function(){
		    	  this.id= this.USER_ID;
		    	  console.log(this.id)
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
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true
			});
		} else {
			$("#recipientId").val('');
			$(".zyxx").empty();
		}
	}

	function chooseUser() {
		sessionStorage.ListTagsCache = '0' //关闭缓存
		var index = layer
				.open({
					type : 2,
					title : '选择案件交接人',
					content : '${pageContext.request.contextPath}/user/chooseUser.do?flags=0&ROLE_ID=b693f826af0545b5a1c60447a27c3089&parIds=recipientId&parNames=recipientName',
					area : [ '90%', '90%' ],
					btn : [ '确定', '取消' ],
					yes : function(index, layero) {
						var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
						var data = iframeWin.pushToParent()
						layer.close(index)
						$("#recipientId").val(data.ids); //赋值子页面传过来的IDS
						$("#recipientName").val(data.names);
						sessionStorage.ListTagsCache = '0' //关闭缓存
					}
				});
	}

	//保存
	function save() {
		if (yoValidate('#customerForm')) {
			if (!$("#auditStatus").val()||$("#auditStatus").val()=='0') {
				console.log(111)
				setDispute('2');
			}
			$("#customerForm").submit();
		}
	}
	
	
</script>
</html>