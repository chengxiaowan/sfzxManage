<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="${pageContext.request.contextPath}/">
<%@ include file="../../system/index/top.jsp"%>
<body class="gray-bg">
		<div class="ibox-content animated fadeInUp">
					<div class="row">
						<div class="col-xs-12">
							<!-- 检索  -->
							<form action="contract/chooseContract.do?id=${pd.id }" class="form-inline" method="post" name="productForm" id="productForm">
								<div class="form-group">
                                     <input id="nav-search-input" name="keywords" placeholder="合同编号、债务人(公司)" value="${pd.keywords }" class="form-control" type="text">
                                </div>
							 <button type="submit" class="btn btn-primary "> 搜索</button>
							 <button type="reset" class="btn"><i id="nav-search-icon" class="ace-icon fa fa-refresh bigger-110 nav-search-icon "></i> 重置</button> 
							</form>
								<!-- 检索  -->
								</div>
								<div class="col-xs-12">
								<div class="col-md-3">
			                        <ul class="sortable-list connectList agile-list ui-sortable">
			                        </ul>
                   				</div>
								<div class="col-md-9">
								<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top: 5px;">
									<thead>
										<tr>
											<th class="center">合同编号</th>
											<th class="center">债权人(公司)</th>
											<th class="center">债务人(公司)</th>
											<th class="center">合同总金额</th>
											<th class="center">佣金比例</th>
											<th class="center">操作</th>
										</tr>
									</thead>

									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty cusList}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${cusList}" var="order" varStatus="vs">
														<tr>
															<td class="center">${order.contractNo }</td>
															<td class="center">${order.customerName }</td>
															<td class="center">${order.debtorName }</td>
															<td class="center">${order.price }</td>
															<td class="center">${order.commissionRate }</td>
															<td class="center"><div data-id="${order.id }" data-contractNo="${order.contractNo }" data-customerName="${order.customerName}" data-debtorName="${order.debtorName}" data-price="${order.price}" data-commissionRate="${order.commissionRate}"  class="btn btn-primary join">加入选择</div></td>
														</tr>
													</c:forEach>
												</c:if>
												<c:if test="${QX.cha == 0 }">
													<tr>
														<td colspan="13" class="center">您无权查看</td>
													</tr>
												</c:if>
											</c:when>
											<c:otherwise>
												<tr class="main_info">
													<td colspan="13" class="center">没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>

								<div class="page-header position-relative">
									<table style="width: 100%;">
										<tr>
											<td style="vertical-align: top;"></td>
											<td style="vertical-align: top;"><div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.tips.js"></script>
<script type="text/javascript">

//推送方法
var Join = function(){
	var that = this;
	//将type定为常量
	this.type = 'chooseContract';
	this.container = $(".sortable-list");
	this.template = function(id,contractNo,customerName,debtorName,price,commissionRate){
		return '<li data-id="'+id+'" data-contractNo="'+contractNo+'" data-customerName="'+customerName+'" data-debtorName="'+debtorName+'" data-price="'+price+'" data-commissionRate="'+commissionRate+'"  class="success-element">'+contractNo+'<div class="agile-detail">'+'<a href="javascript:;" class="pull-right btn btn-xs btn-white cancel">取消选择</a>'+'<i class="fa fa-caret-right"></i> id:'+id+' </div></li>'
	}
	//更新&缓存
	this.update = function(){
		var data = that.pushToParent()
		//如无任何内容则关闭缓存
		if(!data.ids.length) {
			sessionStorage[that.type+'_contract'] = '0'
		}
		sessionStorage[that.type+'_IDS'] = data.ids
		sessionStorage[that.type+'_contractNo'] =data.contractNo
		sessionStorage[that.type+'_customerName'] = data.customerName
		sessionStorage[that.type+'_debtorName'] =data.debtorName
		sessionStorage[that.type+'_price'] = data.price
		sessionStorage[that.type+'_commissionRate'] = data.commissionRate
		sessionStorage[that.type+'_contract'] = '1'
	}
	//初始化
	this.init = function(){  
		//初始化父页面传值
		try{
			var ids,contractNo,customerName,debtorName,price,commissionRate
			if(sessionStorage[that.type+'_contract'] =='0'){
				 if(parent.document.getElementById('contractNo')){
					ids = parent.document.getElementById('contractId').value.split(',')
					contractNo = parent.document.getElementById('contractNo').value.split(',')
					customerName = parent.document.getElementById('c_customerName').value.split(',')
					debtorName = parent.document.getElementById('c_debtorName').value.split(',')
					price = parent.document.getElementById('c_price').value.split(',')
					commissionRate = parent.document.getElementById('c_commissionRate').value.split(',')
				} 
			}else{
				ids =sessionStorage[that.type+'_IDS'].split(',');
				contractNo = sessionStorage[that.type+'_contractNo'].split(',');
				customerName = sessionStorage[that.type+'_customerName'].split(',');
				debtorName = sessionStorage[that.type+'_debtorName'].split(',');
				price = sessionStorage[that.type+'_price'].split(',');
				commissionRate = sessionStorage[that.type+'_commissionRate'].split(',');
			}
			var html = ''
				if(ids.length>0&&ids[0]!=''){
				$.each(ids,function(i){
					html+=that.template(this,contractNo[i],customerName[i],debtorName[i],price[i],commissionRate[i])
				})
				that.container.html(html)					
			}
		}catch(e){
		}
		//初始化拖动排序
		that.container.sortable({
            connectWith: ".connectList"
        }).disableSelection();
		$(document).on('click','.join',function(){
			var t = $(this),
				id = t.data('id'),
				contractNo = t.data('contractno'),
				customerName=t.data('customername'),
				debtorName=t.data('debtorname'),
				price=t.data('price'),
				commissionRate=t.data('commissionrate'),
				html = that.template(id,contractNo,customerName,debtorName,price,commissionRate)
				var data = that.pushToParent().ids.split(',')
				id = id.toString()
				if($.inArray(id,data)>-1){
                    layer.msg('已经选择了该合同,无需再次加入选择')
                    return;
                }else{
                   			if(that.container.find('li').length==1){
                                layer.msg('只允许选择一个')
                            }else{
                            	that.container.append(html)
                            }
                }
		})
		//取消推送事件
		$(document).on('click','.cancel',function(){
			$(this).parents('.success-element').remove()
		})
		window.pushToParent = that.pushToParent
	}
	//合成字符串并推送
	this.pushToParent = function(){
		var ids = [],
		contractNo=[],
			mobilePhones=[],
			customerName=[],
			debtorName=[],
			price= [],
			commissionRate=[],
			list = that.container.find('li')
		$.each(list,function(){
			var t = $(this)
			ids.push(t.data('id')=="undefined"?" ":t.data('id'))
			contractNo.push(t.data('contractno')=="undefined"?" ":t.data('contractno'))
			customerName.push(t.data('customername')=="undefined"?" ":t.data('customername'))
			debtorName.push(t.data('debtorname')=="undefined"?" ":t.data('debtorname'))
			price.push(t.data('price')=="undefined"?" ":t.data('price'))
			commissionRate.push(t.data('commissionrate')=="undefined"?" ":t.data('commissionrate'))
		})
		console.log('ids:' + ids);
		return {
			ids : ids.join(','),
			contractNo: (contractNo.length&&contractNo.join(','))||'',
			customerName : (customerName.length&&customerName.join(','))||'',
			debtorName: (debtorName.length&&debtorName.join(','))||'',
			price :(price.length&&price.join(','))||'',
			commissionRate :(commissionRate.length&&commissionRate.join(','))||''
		}
	}
	this.init()
}


$(function() {
	window.push = new Join()
	//日期框
	$('.date-picker').datepicker({
		autoclose : true,
		todayHighlight : true
	});
	if($("#createTimeStart").val()){
		 $("#createTimeEnd").datepicker('setStartDate', new Date($("#createTimeStart").val())) 
	}
	
	//复选框全选控制
	var active_class = 'active';
	$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
		var th_checked = this.checked;//checkbox inside "TH" table header
		$(this).closest('table').find('tbody > tr').each(function(){
			var row = this;
			if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
			else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
		});
	});
	
	if($("#createTimeEnd").val()){
		 $("#createTimeStart").datepicker('setEndDate', new Date($("#createTimeEnd").val())) 
	}
	//开始日期
	$("#createTimeStart").datepicker({ }).on('changeDate', function(ev){               
		            if(ev.date){  
			                $("#createTimeEnd").datepicker('setStartDate', new Date(ev.date.valueOf()))  
			            }else{  
			                $("#createTimeEnd").datepicker('setStartDate',null);  
			            }  
			          });
	//结束日期
	$("#createTimeEnd").datepicker({}).on('changeDate', function(ev){    
		            if(ev.date){  
			                $("#createTimeStart").datepicker('setEndDate', new Date(ev.date.valueOf()))  
			            }else{  
			                $("#createTimeStart").datepicker('setEndDate',new Date());  
			            }   
			          });
})
	</script>
</body>
</html>
