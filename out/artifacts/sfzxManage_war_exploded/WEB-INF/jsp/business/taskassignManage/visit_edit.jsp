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
<div class="col-xs-12">
				<form action="taskAssign/visit/${msg }.do"  name="visitForm" id="visitForm" method="post"  class="form form-horizontal" >
				<input type="hidden" name="id" id="id" value="${pd.id }" />
							<div class="col-md-12 marks">
						    <div class="form-group">
						        <label class="col-sm-2 control-label">指派对象<font color="red">*</font>：</label>
						        <div class="col-sm-10">
						        <select name="userId" id="userId" data-validate="require|maxLength=50" class="form-control chosen-select-sale">
						        	<c:if test="${not empty pd.userId }">
						        		<option value="${pd.userId }"  selected="selected">${pd.sqName }</option>
						        	</c:if>
						        </select>
						        </div>
						    </div>
							</div>
							<div class="col-md-12 marks">
							<div class="form-group">
								<label  class="control-label col-sm-2 no-padding-right">
									事由<font color="red">*</font>:
								</label>
								<div class="col-sm-10">
								<textarea class="form-control" style="height: 80px;" rows="15" cols="10" name="reason"  placeholder="这里输入事由">${pd.reason}</textarea>
								</div>
							</div>	
							</div>		
				</form>
			</div>
					<div class="text-center">
						<a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a>
					</div>
					</div>
				</div>
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
</body>
<script type="text/javascript">

//初始化销售人chosen
var ROLE_ID;
		if("1"=='${pd.flag}'){
			ROLE_ID='02178e62f17b4926bb7014f3ad5a1ebe,45efba0a0fa946aebf7befe614c74e55';
		}else if("2"=='${pd.flag}'){
			ROLE_ID='b729e9334ad64c15a4e47d88b8c2638f';
		}else{
			ROLE_ID='7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98,90564dd8b75a4f6d815ce418462d401c,e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b,02178e62f17b4926bb7014f3ad5a1ebe,de9de2f006e145a29d52dfadda295353,04fe5e23842f4b998216080bc3b61821,4cb60182bb294cfba622f951e390bc6f,3264c8e83d0248bb9e3ea6195b4c0216,1';
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
	        ROLE_ID:ROLE_ID,
	        data:{
	        	orderId:$("#orderId").val(),
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
    
    
    
    
    
    
    
//初始化chosen
$(".chosen-select").ajaxChosen({
    dataType: 'json',
    type: 'POST',
    url:'/linkman/getLinkmanInfo'
},{
external2:{
	flags:"${flag}"
},
processItems: function(data){
	var list = [];
$.each(data,function(){
	list.push({
		id:this.id,
		text:this.name
	})
})	
	 return list; 
},
    loadingImg: '${pageContext.request.contextPath}/static/ui/img/loading.gif'
});




	$('.date-picker').datepicker({
		startDate:yo.getDateStr(),
		autoclose : true,
		todayHighlight : true
	});
	
	//查找案件列表
	function chooseOrder() {
		sessionStorage.ListOrdersCache = '0' //关闭缓存
		var orderId = $("#orderId").val();
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
						if(orderId != data.ids){
							$("#orderId").val(data.ids); //赋值子页面传过来的IDS
							$("#orderNo").val(data.orderNos);
							$(".chosen-select").find("option").remove();
							$(".chosen-select").append("<option value=''>请选择</option>");
							$(".chosen-select").trigger("chosen:updated");
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
	
	
	function chooseLinkman() {
		var orderId=$("#orderId").val();
		if(!orderId){
			layer.msg("请先填写案件编号");
			return;
		}
		sessionStorage.listLinkmanCache = '0'   //关闭缓存
		var index = layer.open({
			  type: 2,
			  title:'选择联系人',
			  content: '${pageContext.request.contextPath}/linkman/chooseLinkman.do?flags=${flag}&onlySelect=true&orderId='+orderId,
			  area: ['90%', '90%'],
			  btn: ['确定', '取消'], 
			  yes: function(index, layero){
				    var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：姓名、职务、电话、手机
				    var data = iframeWin.pushToParent()
				    layer.close(index)
				    console.log(data)
				    	//债务知情人名称
			    	 $("#linkmanId").val(data.ids);   //赋值子页面传过来的IDS
					 $("#linkmanName").val(data.names);
					sessionStorage.listLinkmanCache = '0'   //关闭缓存
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