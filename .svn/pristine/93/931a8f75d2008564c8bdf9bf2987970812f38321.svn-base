<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<body class="gray-bg">
<div class="ibox-content">
							<div class="form-inline" >
					<div class="row">
						<div class="col-xs-12">
									<c:if test="${QX.add == 1 }">
									<a href="javascript:addRole(0);" class="btn btn-success"><i class="fa fa-plus"></i> 新增组</a>
									</c:if>
									<c:choose>
										<c:when test="${not empty roleList}">
											<c:forEach items="${roleList}" var="role" varStatus="vs">
													<a href="role.do?ROLE_ID=${role.ROLE_ID }" class="btn <c:choose><c:when test="${pd.ROLE_ID == role.ROLE_ID}">btn-default</c:when><c:otherwise>btn-white</c:otherwise></c:choose>"><i class="menu-icon fa fa-users"></i>${role.ROLE_NAME }</a>
											</c:forEach>
										</c:when>
										<c:otherwise>
												<span colspan="100">没有相关数据</span>
										</c:otherwise>
									</c:choose>
							<table>
								<tr height="7px;">
									<td colspan="100"></td>
								</tr>
								<tr>
									<td><font color="#808080">本组：</font></td>
									<td><c:if test="${QX.edit == 1 }">
											<a class="btn btn-mini btn-info" onclick="editRole('${pd.ROLE_ID }');"><i class="fa fa-pencil-square-o"></i> 修改组名称</a>
										</c:if> <c:choose>
											<c:when test="${pd.ROLE_ID == '99'}">
											</c:when>
											<c:otherwise>
												<c:if test="${QX.edit == 1 }">
													<a class="btn btn-white" onclick="editRights('${pd.ROLE_ID }');"><i class="fa fa-pencil"></i> 组菜单权限</a>
												</c:if>
											</c:otherwise>
										</c:choose> <c:choose>
											<c:when test="${pd.ROLE_ID == '1' or pd.ROLE_ID == '2'}">
											</c:when>
											<c:otherwise>
												<c:if test="${QX.del == 1 }">
													<a class='btn btn-danger' title="删除" onclick="delRole('${pd.ROLE_ID }','z','${pd.ROLE_NAME }');"><i class='fa fa-trash-o'></i> 删除</a>
												</c:if>
											</c:otherwise>
										</c:choose></td>
								</tr>
							</table>

							<table id="dynamic-table" class="table table-striped table-bordered table-hover" style="margin-top: 7px;">
								<thead>
									<tr>
										<th class="center" style="width: 50px;">序号</th>
										<th class='center'>角色</th>
										<c:if test="${QX.edit == 1 }">
											<th class="center">增</th>
											<th class="center">删</th>
											<th class="center">改</th>
											<th class="center">查</th>
										</c:if>
										<th width="350" class="center">操作</th>
									</tr>
								</thead>
								<c:choose>
									<c:when test="${not empty roleList_z}">
										<c:if test="${QX.cha == 1 }">
											<c:forEach items="${roleList_z}" var="var" varStatus="vs">

												<tr>
													<td class='center' style="width: 30px;">${vs.index+1}</td>
													<td id="ROLE_NAMETd${var.ROLE_ID }">${var.ROLE_NAME }</td>
													<c:if test="${QX.edit == 1 }">
														<td style="width: 60px;"><a onclick="roleButton('${var.ROLE_ID }','add_qx');" class="btn btn-warning" title="分配新增权限"><i
																class="fa fa-wrench"></i> 分配权限</a></td>
														<td style="width: 60px;"><a onclick="roleButton('${var.ROLE_ID }','del_qx');" class="btn btn-warning" title="分配删除权限"><i
																class="fa fa-wrench"></i> 分配权限</a></td>
														<td style="width: 60px;"><a onclick="roleButton('${var.ROLE_ID }','edit_qx');" class="btn btn-warning" title="分配修改权限"><i
																class="fa fa-wrench"></i> 分配权限</a></td>
														<td style="width: 60px;"><a onclick="roleButton('${var.ROLE_ID }','cha_qx');" class="btn btn-warning" title="分配查看权限"><i
																class="fa fa-wrench"></i> 分配权限</a></td>
													</c:if>
													<td><c:if test="${QX.edit != 1 && QX.del != 1 }">
															<div style="width: 100%;" class="center">
																<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
															</div>
														</c:if> <c:if test="${QX.edit == 1 }">
															<a class="btn btn-white" onclick="editRights('${var.ROLE_ID }');"><i class="fa fa-pencil"></i> 菜单权限</a>
															<a class='btn btn-primary' title="编辑" onclick="editRole('${var.ROLE_ID }');"><i class='fa fa-pencil-square-o'></i> 编辑</a>
														</c:if> <c:choose>
															<c:when test="${var.ROLE_ID == '2' or var.ROLE_ID == '1'}">
															</c:when>
															<c:otherwise>
																<c:if test="${QX.del == 1 }">
																	<a class='btn btn-mini btn-danger' title="删除" onclick="delRole('${var.ROLE_ID }','c','${var.ROLE_NAME }');"><i class='fa fa-trash-o'></i> 删除</a>
																</c:if>
															</c:otherwise>
														</c:choose></td>
												</tr>
											</c:forEach>
										</c:if>
										<c:if test="${QX.cha == 0 }">
											<tr>
												<td colspan="100" class="center">您无权查看</td>
											</tr>
										</c:if>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="100" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</table>
							<div>
								<c:if test="${QX.add == 1 }">
								<a class="btn btn-sm btn-success" onclick="addRole('${pd.ROLE_ID }');"><i class="fa fa-plus"></i> 新增角色</a>
								</c:if>
							</div>

						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

	</div>

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/js.jsp"%>
	<!-- ace scripts -->
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		
		//新增组
		function addRole(pid){
			var index = layer.open({
				  type: 2,
				  title:'新增',
				  content: '<%=basePath%>role/toAdd.do?parent_id='+pid,
				});
		}
		
		//修改
		function editRole(ROLE_ID){
			var index = layer.open({
				  type: 2,
				  title:'编辑',
				  content: '<%=basePath%>role/toEdit.do?ROLE_ID='+ROLE_ID,
				});
		}
		
		//删除
		function delRole(ROLE_ID,msg,ROLE_NAME){
			layer.confirm("确定要删除["+ROLE_NAME+"]吗?", function(result) {
					var url = "<%=basePath%>role/delete.do?ROLE_ID="+ROLE_ID+"&guid="+new Date().getTime();
					$.get(url,function(data){
						if("success" == data.result){
							if(msg == 'c'){
								document.location.reload();
							}else{
								window.location.href="role.do";
							}
							
						}else if("false" == data.result){
							layer.msg( "<span class='bigger-110'>删除失败，请先删除下级角色!</span>");
						}else if("false2" == data.result){
							layer.msg("<span class='bigger-110'>删除失败，此角色已被使用!</span>");
						}
					});
			});
		}
		
		//菜单权限
		function editRights(ROLE_ID){
			var index = layer.open({
				  type: 2,
				  title:'菜单权限',
				  content: '<%=basePath%>role/menuqx.do?ROLE_ID='+ROLE_ID,
				  area: ['50%', '50%']
				});			
		}
		
		//按钮权限(增删改查)
		function roleButton(ROLE_ID,msg){
			if(msg == 'add_qx'){
				var Title = "授权新增权限(此角色下打勾的菜单拥有新增权限)";
			}else if(msg == 'del_qx'){
				Title = "授权删除权限(此角色下打勾的菜单拥有删除权限)";
			}else if(msg == 'edit_qx'){
				Title = "授权修改权限(此角色下打勾的菜单拥有修改权限)";
			}else if(msg == 'cha_qx'){
				Title = "授权查看权限(此角色下打勾的菜单拥有查看权限)";
			}
			var index = layer.open({
				  type: 2,
				  title:'权限设置',
				  content: '<%=basePath%>role/b4Button.do?ROLE_ID='+ROLE_ID+'&msg='+msg,
				  area: ['50%', '50%']
				});	
		}
	</script>


</body>
</html>