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
					<div id="zhongxin" style="margin-top: 10px;">
						<div class="span6">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a data-toggle="tab" href="#home"><i class="green icon-home bigger-110"></i> 基本信息</a></li>
								</ul>
								<div class="tab-content">
									<div id="home" class="tab-pane in active">
										<form action="taskAssign/orderReport/${msg }.do"  name="visitForm" id="visitForm" method="post"  class="form form-horizontal" >
										<input type="hidden" name="orderId" id="orderId" value="${pd.orderId }" />
										<%-- <input type="hidden" name="userId" id="userId" value="${pd.userId }" /> --%>
										<div class="row">
												<div id="zhongxin" style="padding-top: 13px;">
														<div class="col-md-12  marks">
														    <div class="form-group">
														        <label class="col-sm-2 control-label">标题<font color="red">*</font>：</label>
														        <div class="col-sm-8">
														            <input class="form-control" data-validate="require|maxLength=50"  type="text" placeholder="这里输入标题"  name="title" id="title"  value="${pd.title }" >
														        </div>
														    </div>
														</div>
														<div class="col-md-12  marks">
														    <div class="form-group">
														        <label class="col-sm-2 control-label">案件编号<font color="red">*</font>：</label>
														        <div class="col-sm-8">
														            <input class="form-control" data-validate="require|maxLength=50" readonly="readonly" type="text" placeholder="这里输入案件编号"  name="orderNo" id="orderNo"  value="${pd.orderNo }"  onclick="chooseOrder()">
														        </div>
														    </div>
														</div>
														<div class="col-md-12 marks">
													    <div class="form-group">
													        <label class="col-sm-2 control-label">执行人员<font color="red">*</font>：</label>
													        <div class="col-sm-8">
													           <select class="form-control chosen-select-sale" data-validate="require" name="userId" id="userId" >
														        </select>
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
						<a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a> <a class="btn btn-mini btn-danger" onclick="yocto.closeChildLayer();"><i class="fa fa-remove"></i> 取消</a>
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
</body>
<script type="text/javascript">

//初始化销售人chosen
var ROLE_ID;
		if("2"=='${pd.marks}'){
			ROLE_ID='02178e62f17b4926bb7014f3ad5a1ebe';
		}else if("3"=='${pd.marks}'){
			ROLE_ID='b729e9334ad64c15a4e47d88b8c2638f';
		}else{
			ROLE_ID='02178e62f17b4926bb7014f3ad5a1ebe,b729e9334ad64c15a4e47d88b8c2638f';
		}
$(".chosen-select-sale").ajaxChosen({
            dataType: 'json',
            type: 'POST',
            url:'/user/getUserInfo'
    },{
   	 external:ROLE_ID,
   	 processItems: function(data){
   		var list = [];
   	$.each(data,function(){
   		list.push({
   			id:this.USER_ID,
   			text:this.NAME
   		})
   	})	
   		 return list; 
   	 },
            loadingImg: '${pageContext.request.contextPath}/static/ui/img/loading.gif'
    });



	$('.date-picker').datepicker({
		autoclose : true,
		todayHighlight : true
	});
	
	//查找案件列表
	function chooseOrder() {
		sessionStorage.ListOrdersCache = '0' //关闭缓存
		var index = layer
				.open({
					type : 2,
					title : '选择案件',
					content : '${pageContext.request.contextPath}/order/chooseOrder.do?flag=1',
					area : [ '90%', '90%' ],
					btn : [ '确定', '取消' ],
					yes : function(index, layero) {
						var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
						var data = iframeWin.pushToParent()
						layer.close(index)
						$("#orderId").val(data.ids); //赋值子页面传过来的IDS
						$("#orderNo").val(data.orderNos);
						if($("#linkmanId").val()){
							layer.msg("案件已修改，请重新填写联系人");
							$("#linkmanId").val('');
							$("#linkmanName").val('');
						}
						sessionStorage.ListOrdersCache = '0' //关闭缓存
					}
				});
	}
	
	
	function chooseUser() {
		sessionStorage.ListTagsCache = '0' //关闭缓存
		var ROLE_ID;
		if("2"=='${pd.marks}'){
			ROLE_ID='02178e62f17b4926bb7014f3ad5a1ebe';
		}else if("3"=='${pd.marks}'){
			ROLE_ID='b729e9334ad64c15a4e47d88b8c2638f';
		}else{
			ROLE_ID='02178e62f17b4926bb7014f3ad5a1ebe,b729e9334ad64c15a4e47d88b8c2638f';
		}
		var index = layer
				.open({
					type : 2,
					title : '选择标签',
					content : '${pageContext.request.contextPath}/user/chooseUser.do?flags=0&ROLE_ID='+ROLE_ID+'&parIds=userId&parNames=userName',
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
	
	
	function save() {
		if (yoValidate('#visitForm')) {
			 		var index = layer.load(1, {
						shade : [ 0.2, '#fff' ]
					//0.1透明度的白色背景
					});
			 		$("#visitForm").submit();
				} 
			}
</script>
</html>