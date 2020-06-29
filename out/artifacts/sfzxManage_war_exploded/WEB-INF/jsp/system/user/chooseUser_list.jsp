<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
				<form action="user/chooseUser.do?flags=${pd.flags }" class="form-inline" method="post" name="userForm" id="userForm">
					<div class="form-group">
						<input id="nav-search-input" name="keywords" style="width:250px" placeholder="请输入运作、内部律师姓名" value="${pd.keywords }" class="form-control" type="text">
					</div>
					<c:if test="${QX.cha == 1 }">
						<a class="btn btn-primary" onclick="searchs();" title="检索"><i id="nav-search-icon" class=" fa fa-search"></i> 搜索</a>
						<button type="reset" class="btn">
							<i id="nav-search-icon" class="ace-icon fa fa-refresh bigger-110 nav-search-icon "></i> 重置
						</button>
					</c:if>
					<input type="hidden" name="ROLE_ID" id="ROLE_ID" value="${pd.ROLE_ID}" />
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
								<th class="center">用户名</th>
								<th class="center">姓名</th>
								<th class="center">角色</th>
								<th class="center">操作</th>
							</tr>
						</thead>

						<tbody>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty userList}">
									<c:if test="${QX.cha == 1 }">
										<c:forEach items="${userList}" var="user" varStatus="vs">
											<tr>
												<td class="center"><a onclick="viewUser('${user.USERNAME}')" style="cursor: pointer;">${user.USERNAME }</a></td>
												<td class="center">${user.NAME }</td>
												<td class="center">${user.ROLE_NAME }</td>
												<td class="center"><div data-id="${user.USER_ID }" data-name="${user.NAME }" class="btn btn-primary join">加入选择</div>
													</td>
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
		// 检索
		function searchs() {
			$("#userForm").submit();
		}

		//推送方法
		var Join = function() {
			var that = this;
			this.json = {};
			this.container = $(".sortable-list");
			this.template = function(id, name) {
				id = id || "";
				name = name || "";
				return '<li data-id='+id+' data-name='+name+' class="success-element">'
						+ name
						+ '<div class="agile-detail">'
						+ '<a href="javascript:;" class="pull-right btn btn-xs btn-white cancel">取消选择</a>'
						+ '<i class="fa fa-caret-right"></i> id:'
						+ id
						+ ' </div></li>'
			}
			//更新&缓存
			this.update = function() {
					var data = that.pushToParent()
					sessionStorage.ListTagsIds = data.ids
					sessionStorage.ListTagsNames = data.names
					sessionStorage.ListTagsCache = '1'
			}
			//初始化
			this.init = function() {
				//初始化父页面传值
				if(sessionStorage.runInfo){
					that.info = JSON.parse(sessionStorage.runInfo);
					if(that.info.name) that.container.html(that.template(that.info.id,that.info.name));
				}else{
				try {
					var ids, names;
					if (sessionStorage.ListTagsCache == '0') {
						if (parent.document.getElementById('${pd.parIds}')) {
							ids = parent.document
									.getElementById('${pd.parIds}').value
									.split(',');
							names = parent.document
									.getElementById('${pd.parNames}').value
									.split(',');
						}
					} else {
						ids = sessionStorage.ListTagsIds.split(',')
						names = sessionStorage.ListTagsNames.split(',')
					}
					var html = ''
					if (ids.length > 0 && ids[0] != '') {
						$.each(ids, function(i) {
							html += that.template(this, names[i])
						})
						that.container.html(html)
					}
				} catch (e) {
				}
				}
				//初始化拖动排序
				that.container.sortable({
					connectWith : ".connectList",
					update : function() {
						that.update()
					}
				}).disableSelection();
				$(document)
						.on(
								'click',
								'.join',
								function() {
									var flag = '${pd.flags}' //0单选 ,1多选
									var t = $(this), id = t.data('id'), name = t
											.data('name'), html = that
											.template(id, name)
									var data = that.pushToParent().ids
											.split(',');
									id = id.toString();
										if (flag == '0') {
											that.container.html(html)
										} else {
											that.container.append(html)
										}
											that.update()
								})
				//取消推送事件
				$(document).on('click', '.cancel', function() {
					$(this).parents('.success-element').remove()
					that.update()
				})
				window.pushToParent = that.pushToParent
			}
			//合成字符串并推送
			this.pushToParent = function() {
				var ids = [], names = [], list = that.container.find('li')
				$.each(list, function() {
					var t = $(this)
					ids.push(t.data('id'))
					names.push(t.data('name'))
				})
				return {
					ids : ids.join(','),
					names : names.join(',')
				}
			}
			this.init()
		}

		$(function() {
			var push = new Join()
			//日期框
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true
			});
			if ($("#lastLoginStart").val()) {
				$("#lastLoginEnd").datepicker('setStartDate',
						new Date($("#lastLoginStart").val()))
			}

			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on(
					'click',
					function() {
						var th_checked = this.checked;//checkbox inside "TH" table header
						$(this).closest('table').find('tbody > tr').each(
								function() {
									var row = this;
									if (th_checked)
										$(row).addClass(active_class).find(
												'input[type=checkbox]').eq(0)
												.prop('checked', true);
									else
										$(row).removeClass(active_class).find(
												'input[type=checkbox]').eq(0)
												.prop('checked', false);
								});
					});

			if ($("#lastLoginEnd").val()) {
				$("#lastLoginStart").datepicker('setEndDate',
						new Date($("#lastLoginEnd").val()))
			}
			//开始日期
			$("#lastLoginStart").datepicker({})
					.on(
							'changeDate',
							function(ev) {
								if (ev.date) {
									$("#lastLoginEnd").datepicker(
											'setStartDate',
											new Date(ev.date.valueOf()))
								} else {
									$("#lastLoginEnd").datepicker(
											'setStartDate', null);
								}
							});
			//结束日期
			$("#lastLoginEnd").datepicker({}).on(
					'changeDate',
					function(ev) {
						if (ev.date) {
							$("#lastLoginStart").datepicker('setEndDate',
									new Date(ev.date.valueOf()))
						} else {
							$("#lastLoginStart").datepicker('setEndDate',
									new Date());
						}
					});
		})

		//查看用户
		function viewUser(USERNAME) {
			if ('admin' == USERNAME) {
				bootbox.dialog({
					message : "<span class='bigger-110'>不能查看admin用户!</span>",
					buttons : {
						"button" : {
							"label" : "确定",
							"className" : "btn-sm btn-success"
						}
					}
				});
				return;
			}

			var index = layer
					.open({
						type : 2,
						title : '查看用户',
						content : '${pageContext.request.contextPath}//user/view.do?USERNAME='
								+ USERNAME,
						area : [ '100%', '100%' ]
					})
		}
	</script>
</body>
</html>
