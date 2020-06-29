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
							<form action="order/chooseOrder.do" class="form-inline" method="post" name="productForm" id="productForm">
								<div class="form-group">
                                    <input id="nav-search-input" name="keywords" placeholder="案件编号、债务人、债权人" value="${pd.keywords }" class="form-control" type="text">
                                </div>
							 <button type="submit" class="btn btn-primary "> 搜索</button>
							 <button type="button" onclick="emptyForm()" class="btn"><i id="nav-search-icon" class="ace-icon fa fa-refresh bigger-110 nav-search-icon "></i> 重置</button> 
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
											<th class="center">案件编号</th>
											<th class="center">债务人名称</th>
											<th class="center">债权人名称</th>
											<th class="center">欠款总金额</th>
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
															<td class="center"><a title="点击查看详情" onclick="viewCustomer('${order.id}')" style="cursor: pointer;">${order.orderNo }</a></td>
															<td class="center">${order.custoemrName }</td>
															<td class="center">${order.debtorName }</td>
															<td class="center">${order.debtAmount }</td>
															<td class="center"><div data-id="${order.id }"  data-orderNo="${order.orderNo }"  class="btn btn-primary join">加入选择</div></td>
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
		var that = this
		this.container = $(".sortable-list")
		this.template = function(id,orderNo){
			return '<li data-id="'+id+'" data-orderNo="'+orderNo+'"  class="success-element">'+orderNo+'<div class="agile-detail">'+'<a href="javascript:;" class="pull-right btn btn-xs btn-white cancel">取消选择</a>'+'<i class="fa fa-caret-right"></i> id:'+id+' </div></li>'
		}
		//更新&缓存
		this.update = function(){
			var data = that.pushToParent()
			sessionStorage.listTopicsPdtIDS = data.ids
			sessionStorage.listTopicsPdtNames =data.orderNos
			sessionStorage.ListOrdersCache = '1'
		}
		//初始化
		this.init = function(){  
			//初始化父页面传值
			try{
				var ids,orderNos
				if(sessionStorage.ListOrdersCache =='0'){
					if(parent.document.getElementById('orderId')){
						ids = parent.document.getElementById('orderId').value.split(',')
						orderNos = parent.document.getElementById('orderNo').value.split(',')
					}
				}else{
					ids = sessionStorage.listTopicsPdtIDS.split(',')
					orderNos = sessionStorage.listTopicsPdtNames.split(',')
				}
				var html = ''
					if(ids.length>0&&ids[0]!=''){
					$.each(ids,function(i){
						html+=that.template(this,orderNos[i])
					})
					that.container.html(html)					
				}
			}catch(e){
			}
			//初始化拖动排序
			that.container.sortable({
                connectWith: ".connectList",
                update:function(){
					that.update()
                }
            }).disableSelection();
			$(document).on('click','.join',function(){
				var t = $(this),
					id = t.data('id'),
					orderNo = t.data('orderno'),
					html = that.template(id,orderNo)
					var data = that.pushToParent().ids.split(',')
					id = id.toString()
					if($.inArray(id,data)>-1){
                        layer.msg('已经推送了此条目无法重复操作')
                        return;
                    }else{
                        if(that.container.find('li').length==1){
                            layer.msg('只允许选择一个')
                        }else{
                            that.container.append(html)
                            that.update()   
                        }
                    }
			})
			//取消推送事件
			$(document).on('click','.cancel',function(){
				$(this).parents('.success-element').remove()
				that.update()
			})
			window.pushToParent = that.pushToParent
		}
		//合成字符串并推送
		this.pushToParent = function(){
			var ids = [],
				orderNos=[],
				list = that.container.find('li')
			$.each(list,function(){
				var t = $(this)
				ids.push(t.data('id'))
				orderNos.push(t.data('orderno'))
			})
			return {
				ids : ids.join(','),
				orderNos: orderNos.join(','),
			}
		}
		this.init()
	}
	
	function viewCustomer(id) {
		var index = layer.open({
			type : 2,
			title : '点击查看详情',
			content : '${pageContext.request.contextPath}/order/viewOrder.do?id='+id,
			area : [ '100%', '100%' ]
		});
		layer.full(index);
	}
	
	
	function emptyForm() {
		$("#nav-search-input").val('');
		$("#createTimeStart").val('');
		$("#createTimeEnd").val('');
	}

	$(function() {
		var push = new Join()
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
