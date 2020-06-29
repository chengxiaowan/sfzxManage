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
							<form action="linkman/chooseLinkman.do" class="form-inline" method="post" name="productForm" id="productForm">
								<input type="hidden" name="flags" value="${pd.flags }">
								<input type="hidden" name="debtorId" value="${pd.debtorId }">
								<div class="form-group">
                                    <input id="nav-search-input" name="keywords" placeholder="联系人名称/手机号" value="${pd.keywords }" class="form-control" type="text">
                                </div>
<!-- 							<div class="form-group"> -->
<%-- 							<input class="span10 date-picker form-control" id="createTimeStart" name="createTimeStart" value="${fn:substring(pd.createTimeStart, 0, 10)}" type="text" --%>
<!-- 											data-date-format="yyyy-mm-dd" readonly="readonly"  placeholder="开始日期" title="注册时间开始" /> -->
<!-- 											</div> -->
<!-- 							<div class="form-group"> -->
<%-- 							<input class="span10 date-picker form-control" id="createTimeEnd" name="createTimeEnd" value="${fn:substring(pd.createTimeEnd, 0, 10)}" type="text" --%>
<!-- 											data-date-format="yyyy-mm-dd" readonly="readonly"  placeholder="结束日期" title="注册时间结束" /> -->
<!-- 											</div> -->
							 <button type="submit" class="btn btn-primary "> 搜索</button>
							 <button type="reset" class="btn"><i id="nav-search-icon" class="ace-icon fa fa-refresh bigger-110 nav-search-icon "></i> 重置</button> 
							<c:if test="${empty pd.flags }">
								<div class="pull-right">
										<a class="btn btn-success" onclick="add();"><i class="fa fa-plus"></i> 新增</a>
								</div>
							</c:if>
							</form>
								
								<!-- 检索  -->
								</div>
								<div class="col-xs-12">
								<div class="col-md-3" id="add-cache-hook">
			                        <ul class="sortable-list connectList agile-list ui-sortable">
			                        </ul>
                   				</div>
								<div class="col-md-9">
								<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top: 5px;">
									<thead>
										<tr>
											<th class="center">联系人名称</th>
											<th class="center">手机</th>
											<th class="center">电话</th>
											<th class="center">职务</th>
											<th class="center">操作</th>
										</tr>
									</thead>

									<tbody>
										<!-- 开始循环 -->
											<c:if test="${not empty cusList}">
													<c:forEach items="${cusList}" var="linkman" varStatus="vs">
														<tr>
															<td class="center">${linkman.name }</td>
															<td class="center">${linkman.mobilePhone }</td>
															<td class="center">${linkman.landline }</td>
															<td class="center">${linkman.postion }</td>
															<td class="center"><div data-id="${linkman.id }" data-postion="${linkman.postion }" data-landline="${linkman.landline }" data-mobilePhone="${linkman.mobilePhone }" data-name="${linkman.name }" class="btn btn-primary join">加入选择</div></td>
														</tr>
													</c:forEach>
											</c:if>
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

	//新增
	function add() {
		var data = pushToParent()
		if(data.ids.length){
			localStorage.addCacheHook = $("#add-cache-hook").html()
		}
		var index = layer.open({
			type : 2,
			title : '新增',
			content : '${pageContext.request.contextPath}/linkman/goAdd.do?noCustomer=1&customerId=${pd.customerId}',
			area : [ '70%', '70%' ]
		});
	}
	if(localStorage.addCacheHook){
		$("#add-cache-hook").html(localStorage.addCacheHook)
	}
	//推送方法
	var Join = function(){
		var that = this;
		//将type定为常量
		this.type = 'chooseLinkManCacheType_${pd.type}';
		this.container = $(".sortable-list");
		this.template = function(id,name,mobilePhone,landLine,postion){
			return '<li data-id="'+id+'" data-name="'+name+'" data-mobilePhone="'+mobilePhone+'" data-landLine="'+landLine+'" data-postion="'+postion+'"  class="success-element">'+name+'<div class="agile-detail">'+'<a href="javascript:;" class="pull-right btn btn-xs btn-white cancel">取消选择</a>'+'<i class="fa fa-caret-right"></i> id:'+id+' </div></li>'
		}
		//更新&缓存
		this.update = function(){
			var data = that.pushToParent()
			//如无任何内容则关闭缓存
			if(!data.ids.length) {
				sessionStorage[that.type] = '0'
			}
			sessionStorage[that.type+'_IDS'] = data.ids
			sessionStorage[that.type+'_PdtNames'] =data.names
			sessionStorage[that.type+'_PdtMobilePhones'] = data.MobilePhones
			sessionStorage[that.type+'_PdtLandlines'] =data.Landlines
			sessionStorage[that.type+'_PdtPostions'] = data.Postions
			sessionStorage[that.type] = '1'
		}
		//初始化
		this.init = function(){  
			//初始化父页面传值
			try{
				var ids,names,mobilePhones,landLines,postions
				if(sessionStorage[that.type] =='0'){
					if(parent.document.getElementById('linkmanId')){
						ids = parent.document.getElementById('linkmanId').value.split(',')
						names = parent.document.getElementById('linkmanName').value.split(',')
						mobilePhones="".split(',')
						landLines="".split(',')
						postions="".split(',')
					}
				}else{
					ids =sessionStorage[that.type+'_IDS'].split(',');
					names = sessionStorage[that.type+'_PdtNames'].split(',');
					mobilePhones = sessionStorage[that.type+'_PdtMobilePhones'].split(',');
					landLines = sessionStorage[that.type+'_PdtLandlines'].split(',');
					postions = sessionStorage[that.type+'_PdtPostions'].split(',');
				}
				var html = ''
					if(ids.length>0&&ids[0]!=''){
					$.each(ids,function(i){
						html+=that.template(this,names[i],mobilePhones[i],landLines[i],postions[i])
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
					name = t.data('name'),
					mobilePhone=t.data('mobilephone'),
					landLine=t.data('landline'),
					postion=t.data('postion'),
					html = that.template(id,name,mobilePhone,landLine,postion)
					var data = that.pushToParent().ids.split(',')
					id = id.toString()
					if($.inArray(id,data)>-1){
                        layer.msg('已经推送了此条目无法重复操作')
                        return;
                    }else{
                      /*  	if(!'${pd.flags}'){
                        	var flag=true;
                        	$.ajax({
    							type : "POST",
    							url : '${pageContext.request.contextPath}/linkman/hasLinkCustomer.do?tm='
    									+ new Date()
    											.getTime(),
    							data : {
    								id : id,
    								relateId:'${pd.relateId}'
    							},
    							dataType : 'json',
    							cache : false,
    							async:false,
    							success : function(data) {
    								if("error"==data.result){
    									 layer.msg('该联系人已有绑定,请重新选择联系人')
    				                     flag=false
    								}
    							}
    						});
                        	if(flag){
	                            that.container.append(html)
                        	}
                       	}else{ */
                       		if(!'${pd.onlySelect}'){
                       			that.container.append(html)
                       		}else{
                       			if(that.container.find('li').length==1){
                                    layer.msg('只允许选择一个')
                                }else{
                                	that.container.append(html)
                                }
                       		}
                       /* 	} */
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
				names=[],
				mobilePhones=[],
				landLines=[],
				postions=[],
				list = that.container.find('li')
			$.each(list,function(){
				var t = $(this)
				ids.push(t.data('id')=="undefined"?" ":t.data('id'))
				names.push(t.data('name')=="undefined"?" ":t.data('name'))
				mobilePhones.push(t.data('mobilephone')=="undefined"?" ":t.data('mobilephone'))
				landLines.push(t.data('landline')=="undefined"?" ":t.data('landline'))
				postions.push(t.data('postion')=="undefined"?" ":t.data('postion'))
			})
			console.log('ids:' + ids);
			return {
				ids : ids.join(','),
				names: (names.length&&names.join(','))||'',
				mobilePhones : (mobilePhones.length&&mobilePhones.join(','))||'',
				landLines: (landLines.length&&landLines.join(','))||'',
				postions :(postions.length&&postions.join(','))||''
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
