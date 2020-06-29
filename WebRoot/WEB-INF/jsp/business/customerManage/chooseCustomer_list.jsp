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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<body class="gray-bg">
		<div class="ibox-content animated fadeInUp">
					<div class="row">
						<div class="col-xs-12">
							<!-- 检索  -->
							<form action="customer/chooseCustomer.do" class="form-inline" method="post" name="productForm" id="productForm">
								<div class="form-group">
                                    <input id="nav-search-input" name="keywords" placeholder="请输入关键词" value="${pd.keywords }" class="form-control" type="text">
                                </div>
							<div class="form-group">
							<input class="span10 date-picker form-control" id="createTimeStart" name="createTimeStart" value="${fn:substring(pd.createTimeStart, 0, 10)}" type="text"
											data-date-format="yyyy-mm-dd" readonly="readonly"  placeholder="开始日期" title="注册时间开始" />
											</div>
							<div class="form-group">
							<input class="span10 date-picker form-control" id="createTimeEnd" name="createTimeEnd" value="${fn:substring(pd.createTimeEnd, 0, 10)}" type="text"
											data-date-format="yyyy-mm-dd" readonly="readonly"  placeholder="结束日期" title="注册时间结束" />
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
											<th class="center">名称</th>
											<th class="center">操作</th>
										</tr>
									</thead>

									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty cusList}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${cusList}" var="customer" varStatus="vs">
														<tr>
															<td class="center">${customer.name }</td>
															<td class="center"><div data-id="${customer.id }" data-bankNumber="${customer.bankNumber }" data-fax="${customer.fax }" data-postCode="${customer.postCode }" data-name="${customer.name }" data-companyAddress="${customer.companyAddress }" class="btn btn-primary join">加入选择</div></button></td>
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
		this.template = function(id,name,fax,postCode,bankNumber,companyAddress){
			return '<li data-id="'+id+'" data-name="'+name+'" data-fax="'+fax+'" data-postCode="'+postCode+'" data-bankNumber="'+bankNumber+'" data-companyAddress="'+companyAddress+'" class="success-element">'+name+'<div class="agile-detail">'+'<a href="javascript:;" class="pull-right btn btn-xs btn-white cancel">取消选择</a>'+'<i class="fa fa-caret-right"></i> id:'+id+' </div></li>'
		}
		//更新&缓存
		this.update = function(){
			var data = that.pushToParent()
			sessionStorage.listTopicsPdtIDS = data.ids
			sessionStorage.listTopicsPdtNames =data.names
			sessionStorage.listTopicsPdtFaxs = data.faxs
			sessionStorage.listTopicsPdtPostCodes =data.postCodes
			sessionStorage.listTopicsPdtBankNumbers = data.bankNumbers
			sessionStorage.listTopicsPdtCompanyAddresses = data.companyAddresses
			sessionStorage.listUsersCache = '1'
		}
		//初始化
		this.init = function(){  
			//初始化父页面传值
			try{
				var ids,names,faxs,postCodes,bankNumbers,companyAddresses
				if(sessionStorage.listUsersCache =='0'){
					if(parent.document.getElementById('customerId')){
						ids = parent.document.getElementById('customerId').value.split(',')
						names = parent.document.getElementById('customerName').value.split(',')
						if(parent.document.getElementById('customerFax')){
							faxs = parent.document.getElementById('customerFax').value.split(',')
							postCodes = parent.document.getElementById('customerPostCode').value.split(',')
							bankNumbers = parent.document.getElementById('customerBankNumber').value.split(',')
							companyAddresses = parent.document.getElementById('customerAddress').value.split(',')
						}else{
							faxs=''.split(',');
							postCodes=''.split(',');
							bankNumbers=''.split(',');
							companyAddresses=''.split(',');
						}
					}
				}else{
					ids = sessionStorage.listTopicsPdtIDS.split(',')
					names = sessionStorage.listTopicsPdtNames.split(',')
					faxs = sessionStorage.listTopicsPdtFaxs.split(',')
					postCodes = sessionStorage.listTopicsPdtPostCodes.split(',')
					bankNumbers = sessionStorage.listTopicsPdtBankNumbers.split(',')
					companyAddresses = sessionStorage.listTopicsPdtCompanyAddresses.split(',')
				}
				var html = ''
					if(ids.length>0&&ids[0]!=''){
					$.each(ids,function(i){
						html+=that.template(this,names[i],faxs[i],postCodes[i],bankNumbers[i],companyAddresses[i])
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
					name = t.data('name'),
					companyAddress=t.data('companyaddress'),
					fax=t.data('fax'),
					postCode=t.data('postcode'),
					bankNumber=t.data('banknumber'),
					html = that.template(id,name,fax,postCode,bankNumber,companyAddress)
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
				names=[],
				faxs=[],
				postCodes=[],
				bankNumbers=[],
				companyAddresses=[],
				list = that.container.find('li')
			$.each(list,function(){
				var t = $(this)
				ids.push(t.data('id'))
				names.push(t.data('name'))
				faxs.push(t.data('fax'))
				postCodes.push(t.data('postcode'))
				bankNumbers.push(t.data('banknumber'))
				companyAddresses.push(t.data('companyaddress'))
			})
			return {
				ids : ids.join(','),
				names: names.join(','),
				faxs : faxs.join(','),
				postCodes: postCodes.join(','),
				bankNumbers : bankNumbers.join(','),
				companyAddresses: companyAddresses.join(',')
			}
		}
		this.init()
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
