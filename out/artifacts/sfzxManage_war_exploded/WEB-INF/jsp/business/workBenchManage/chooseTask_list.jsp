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
				<form action="workBench/chooseTask.do" class="form-inline" method="post" name="productForm" id="productForm">
					<div class="form-group">
						<input id="nav-search-input" name="keywords" placeholder="请输入关键词" value="${pd.keywords }" class="form-control" type="text">
					</div>
					<div class="form-group">
						<input class="span10 date-picker form-control" id="createTimeStart" name="createTimeStart" value="${fn:substring(pd.createTimeStart, 0, 10)}" type="text" data-date-format="yyyy-mm-dd"
							readonly="readonly" placeholder="开始日期" title="注册时间开始" />
					</div>
					<div class="form-group">
						<input class="span10 date-picker form-control" id="createTimeEnd" name="createTimeEnd" value="${fn:substring(pd.createTimeEnd, 0, 10)}" type="text" data-date-format="yyyy-mm-dd"
							readonly="readonly" placeholder="结束日期" title="注册时间结束" />
					</div>
					<button type="submit" class="btn btn-primary ">搜索</button>
					<button type="button" onclick="emptyForm()" class="btn">
						<i id="nav-search-icon" class="ace-icon fa fa-refresh bigger-110 nav-search-icon "></i> 重置
					</button>
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
								<th class="center">任务名称</th>
								<th class="center">任务类型</th>
								<th class="center">备注</th>
								<th class="center">操作</th>
							</tr>
						</thead>

						<tbody>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty cusList}">
									<c:if test="${QX.cha == 1 }">
										<c:forEach items="${cusList}" var="list" varStatus="vs">
											<tr>
												<td class="center">${list.taskName }</td>
												<td class="center">${list.typeDesc }</td>
												<td class="center">${list.remark }</td>
												<td class="center"><div data-id="${list.id }" data-taskname="${list.taskName }" class="btn btn-primary join">加入选择</div></td>
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
		var Join = function() {
			var that = this
			this.container = $(".sortable-list")
			this.template = function(id, taskName) {
				return '<li data-id="'+id+'" data-taskname="'+taskName+'"  class="success-element">'
						+ taskName
						+ '<div class="agile-detail">'
						+ '<a href="javascript:;" class="pull-right btn btn-xs btn-white cancel">取消选择</a>'
						+ '<i class="fa fa-caret-right"></i> id:'
						+ id
						+ ' </div></li>'
			}
			//更新&缓存
			this.update = function() {
				var data = that.pushToParent()
				sessionStorage.listTopicsPdtIDS = data.ids
				sessionStorage.listTopicsPdtNames = data.taskNames
				sessionStorage.ListOrdersCache = '1'
			}
			//初始化
			this.init = function() {
				//初始化父页面传值
				try {
					var ids, taskNames
					if (sessionStorage.ListOrdersCache == '0') {
						if (parent.document.getElementById('taskId')) {
							ids = parent.document.getElementById('taskId').value
									.split(',')
							taskNames = parent.document
									.getElementById('taskName').value
									.split(',')
						}
					} else {
						ids = sessionStorage.listTopicsPdtIDS.split(',')
						taskNames = sessionStorage.listTopicsPdtNames
								.split(',')
					}
					var html = ''
					if (ids.length > 0 && ids[0] != '') {
						$.each(ids, function(i) {
							html += that.template(this, taskNames[i])
						})
						that.container.html(html)
					}
				} catch (e) {
				}
				//初始化拖动排序
				that.container.sortable({
					connectWith : ".connectList",
					update : function() {
						that.update()
					}
				}).disableSelection();
				$(document).on(
						'click',
						'.join',
						function() {
							var t = $(this), id = t.data('id'), taskName = t
									.data('taskname'), html = that.template(id,
									taskName)
							var data = that.pushToParent().ids.split(',')
							id = id.toString()
							if ($.inArray(id, data) > -1) {
								layer.msg('已经推送了此条目无法重复操作')
								return;
							} else {
								if (that.container.find('li').length == 1) {
									layer.msg('只允许选择一个')
								} else {
									that.container.append(html)
									that.update()
								}
							}
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
				var ids = [], taskNames = [], list = that.container.find('li')
				$.each(list, function() {
					var t = $(this)
					ids.push(t.data('id'))
					taskNames.push(t.data('taskname'))
				})
				return {
					ids : ids.join(','),
					taskNames : taskNames.join(','),
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
			if ($("#createTimeStart").val()) {
				$("#createTimeEnd").datepicker('setStartDate',
						new Date($("#createTimeStart").val()))
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

			if ($("#createTimeEnd").val()) {
				$("#createTimeStart").datepicker('setEndDate',
						new Date($("#createTimeEnd").val()))
			}
			//开始日期
			$("#createTimeStart").datepicker({}).on(
					'changeDate',
					function(ev) {
						if (ev.date) {
							$("#createTimeEnd").datepicker('setStartDate',
									new Date(ev.date.valueOf()))
						} else {
							$("#createTimeEnd")
									.datepicker('setStartDate', null);
						}
					});
			//结束日期
			$("#createTimeEnd").datepicker({}).on(
					'changeDate',
					function(ev) {
						if (ev.date) {
							$("#createTimeStart").datepicker('setEndDate',
									new Date(ev.date.valueOf()))
						} else {
							$("#createTimeStart").datepicker('setEndDate',
									new Date());
						}
					});
		})
	</script>
</body>
</html>
