<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- jsp文件头和头部 -->
<!DOCTYPE html>
<html lang="en">

	<head>
		<%@ include file="top.jsp"%>

		<body class="fixed-sidebar full-height-layout gray-bg fixWidth" style="overflow: hidden">
			<div id="wrapper">
				<!--左侧导航开始-->
				<%@ include file="left.jsp"%>
				<!--左侧导航结束-->
				<!--右侧部分开始-->
				<div id="page-wrapper" class="gray-bg dashbard-1">
					<div class="row border-bottom">
						<nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
							<div class="navbar-header">
								<form role="search" class="navbar-form-custom" method="post" onsubmit="return goSearchResult(this)">
									<div class="form-group htz-group">
										<input style="width:250px" type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">
										<button class="btn htz-btn" type="submit"><i class="fa fa-search"></i></button>
									</div>
								</form>
							</div>
							<ul class="nav navbar-top-links navbar-right">
								<li class="dropdown htz-li" id="newMessageBell">
									<a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">

									</a>
									<ul class="dropdown-menu dropdown-alerts">
										<li>
											<i class="fa fa-envelope fa-fw"></i> <span class="unread-info"></span>
											<button title="全部设为已读" onclick="readAll()" style="top: -4px;position: relative;" class="pull-right btn btn-white fa fa-check"></button>
										</li>
										<li class="divider unread-info-list-prehook"></li>
										<li class="unread-info-list">
											<a href="profile.html">
												<div>
													跟进信息 <span class="pull-right text-muted small">12分钟钱</span>
												</div>
											</a>
										</li>
										<li class="divider"></li>
										<li>
											<div class="text-center link-block">
												<a class="" href="javascript:;" onclick="viewAllMessage()">
													<strong>查看所有 </strong> <i class="fa fa-angle-right"></i>
												</a>
											</div>
										</li>
									</ul>
								</li>

								<li class="dropdown  htz-li">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown">
										<span class="htz-name"></span>
										<b class="caret"></b>
									</a>
									<ul class="dropdown-menu">
										<li>
											<a class="personInfo">修改密码</a>
										</li>
										<li>
											<a href="logout">退出</a>
										</li>
									</ul>
								</li>

							</ul>
						</nav>
					</div>
					<div class="row content-tabs">
						<button class="roll-nav roll-left J_tabLeft">
					<i class="fa fa-backward"></i>
				</button>
						<nav class="page-tabs J_menuTabs">
							<div class="page-tabs-content">
								<a href="javascript:;" class="active J_menuTab" data-id="/workBench/myTaskList.do?myIndex=1">主页</a>
							</div>
						</nav>
						<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
						<div class="btn-group roll-nav roll-right">
							<span>
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						关闭操作<span class="caret"></span>
							</button>
							<ul role="menu" class="dropdown-menu dropdown-menu-right">
								<li class="J_tabShowActive">
									<a>定位当前选项卡</a>
								</li>
								<li class="divider"></li>
								<li class="J_tabCloseAll">
									<a>关闭全部选项卡</a>
								</li>
								<li class="J_tabCloseOther">
									<a>关闭其他选项卡</a>
								</li>
							</ul>

							<!-- <span>
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						通知信息  <span class="fa fa-lightbulb-o"></span>
					</button>
					 <ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive"><a>通知信息 01</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>查看所有通知信息</a></li>
					</ul>
					</span> -->

						</div>
						<a href="logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
					</div>
					<div class="row J_mainContent" id="content-main">
						<iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${pageContext.request.contextPath}/workBench/sy/myTaskList.do?myIndex=1" frameborder="0" data-id="${pageContext.request.contextPath}/workBench/myTaskList.do?myIndex=1" seamless></iframe>
						<%-- 										<iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="<c:if test="${pd.isSale||pd.isOperation }">${pageContext.request.contextPath}/workBench/myTaskList.do?myIndex=1</c:if>" frameborder="0" data-id="<c:if test="${pd.isSale||pd.isOperation }">${pageContext.request.contextPath}/workBench/myTaskList.do?myIndex=1</c:if>" seamless></iframe>
 --%>
					</div>
				</div>
				<!--右侧部分结束-->
			</div>

			<div class="" style="height:0;overflow:hidden">
				<div class="row">
					<div class="col-sm-12 form form-horizontal" id="editPass">
						<div class="form-group" style="margin-top: 20px;">
							<label class="col-sm-3 control-label"><font color="red">*</font><span style="font-weight: 600;">原密码：</span></label>
							<div class="col-sm-9">
								<input class="form-control " type="password" id="originalPassword" placeholder="请输入原密码">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"><font color="red">*</font><span style="font-weight: 600;">新密码：</span></label>
							<div class="col-sm-9">
								<input class="form-control " type="password" id="password" placeholder="请输入新密码">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label"><font color="red">*</font><span style="font-weight: 600;">确认密码：</span></label>
							<div class="col-sm-9">
								<input class="form-control " type="password" id="confirmPassword" placeholder="请输入确认密码">
							</div>
						</div>

					</div>
				</div>

			</div>
			<script>
				
				loop();

				function loop() {
					var n = 0
					setInterval(function() {
						$.ajax({
							url: '/workBench/callList.do?flag=2',
							async: true,
							data: {
								pageSize: 5,
								pageNo: 1,
							}
						}).done((res) => {
							var now = new Date();
							now.setMinutes(now.getMinutes() + 10);
							var next10 = now.getTime()
							var list = res.result.list

							for(var i = 0; i < list.length; i++) {
								var target = new Date(list[i].time).getTime()
								var jian = target - next10
								console.log(next10, target, jian);

								if(jian <= 60000 && jian > 0) {
									toastr.options = {
										"closeButton": true,
										"debug": false,
										"progressBar": true,
										"positionClass": "toast-top-right",
										"onclick": null,
										"showDuration": "0",
										"hideDuration": "0",
										"timeOut": "0",
										"extendedTimeOut": "0",
										"showEasing": "swing",
										"hideEasing": "linear",
										"showMethod": "fadeIn",
										"hideMethod": "fadeOut"
									}
									 if(list[i].customerId) {
										toastr.info(list[i].remark.substr(0, list[i].remark.length - 1), '提示', {
											positionClass: 'toast-top-right'
										})
										var content = list[i].remark
										var length = list[i].remark.length - 1
										var id = list[i].customerId
										$("#toast-container").click(function() {
											var type = content.substr(length, 1)
											if(type == 0) {
												html = '/static/page/customer_detail.html?id=' + id
											} else {
												html = '/static/page/customerN_detail.html?id=' + id
											}

											var index = layer.open({
												type: 2,
												title: '客户详情',
												content: html,
												area: ['100%', '100%']
											});
										})
									} else {
										toastr.info(list[i].remark, '提示', {
											positionClass: 'toast-top-right'
										})
//										console.log("OKghjgjhg")
//										var ralateId = list[i].relateId
//										var type = list[i].type
//										$("#toast-container").click(function() {
//											if(type == 16) {
//												html = "/static/page/workreport/listInfo.html?id=" + ralateId
//											}
//											var index = layer.open({
//												type: 2,
//												title: '查看详情',
//												content: html,
//												area: ['100%', '100%']
//											});
//										})
									}

								}
							}
						})
					}, 60000)
				}
			</script>

			<%@ include file="js.jsp"%>
		</body>
	</head>

</html>