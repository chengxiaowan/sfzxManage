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
				<div class="col-md-3">
					<ul class="sortable-list connectList agile-list ui-sortable">
					</ul>
				</div>
				<div class="col-md-9">
					<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top: 5px;">
						<thead>
							<tr>
								<th class="center">期数</th>
								<th class="center">回款金额</th>
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
												<td class="center">${list.periods }</td>
												<td class="center">${list.onceMoney }</td>
												<td class="center">${list.remark }</td>
												<td class="center"><div data-id="${list.periods }" class="btn btn-primary join">加入选择</div>
													</button></td>
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
			this.template = function(id) {
				return '<li data-id="'+id+'" class="success-element">'
						+ '<div class="agile-detail">'
						+ '<a href="javascript:;" class="pull-right btn btn-xs btn-white cancel">取消选择</a>'
						+ '<i class="fa fa-caret-right"></i> 期数:' + id
						+ ' </div></li>'
			}
			//更新&缓存
			this.update = function() {
				var data = that.pushToParent()
				sessionStorage.listTopicsPdtIDS = data.ids
				sessionStorage.listUsersCache = '1'
			}
			//初始化
			this.init = function() {
				//初始化父页面传值
				try {
					var ids, names
					if (sessionStorage.listUsersCache == '0') {
						if (parent.document.getElementById('currentPeriods')) {
							ids = parent.document
									.getElementById('currentPeriods').value
									.split(',')
						}
					} else {
						ids = sessionStorage.listTopicsPdtIDS.split(',')
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
							var t = $(this), id = t.data('id'), html = that
									.template(id)
							var data = that.pushToParent().ids.split(',')
							id = id.toString()
							if ($.inArray(id, data) > -1) {
								layer.msg('已经选择了此条目无法重复操作')
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
				var ids = [], names = [], list = that.container.find('li')
				$.each(list, function() {
					var t = $(this)
					ids.push(t.data('id'))
				})
				return {
					ids : ids.join(','),
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
		})
	</script>
</body>
</html>
