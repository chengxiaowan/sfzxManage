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
<%@ include file="../../system/index/top.jsp"%>
<body>
<body class="no-skin">
				<div class="ibox-content">
					<div class="row">
						<div class="col-xs-12">
							<!-- 检索  -->
							<form class="form-inline" action="department/list.do" method="post" name="Form" id="Form">
							<div class="form-group">
						<input id="keywords" name="keywords" placeholder="请输入关键词" value="${page.pd.keywords }" class="form-control" type="text">
					</div>
					<div class="form-group">
						<select class="chosen-select form-control" name="DEPARTMENT_ID" id="DEPARTMENT_ID" >
							<option value="${DEPARTMENT_ID}" <c:if test="${DEPARTMENT_ID != ''}">selected</c:if>>本级</option>
												<option value="" <c:if test="${DEPARTMENT_ID == ''}">selected</c:if>>全部</option>
						</select>
					</div>
								<table style="margin-top: 5px;">
									<tr>
										<c:if test="${QX.cha == 1 }">
										<a class="btn btn-primary" onclick="gsearch();" title="检索"><i id="nav-search-icon" class=" fa fa-search"></i> 搜索</a>
										</c:if>
									</tr>
								</table>
								<!-- 检索  -->

								<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top: 5px;">
									<thead>
										<tr>
											<th class="center" style="width: 50px;">序号</th>
											<th class="center">名称</th>
											<th class="center">英文</th>
											<th class="center">负责人</th>
											<th class="center">操作</th>
										</tr>
									</thead>

									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty varList}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${varList}" var="var" varStatus="vs">
														<tr>
															<td class='center' style="width: 30px;">${vs.index+1}</td>
															<td class='center'><a href="javascript:goSondict('${var.DEPARTMENT_ID }')"><i class="ace-icon fa fa-share bigger-100"></i>&nbsp;${var.NAME}</a></td>
															<td class='center'><a href="javascript:goSondict('${var.DEPARTMENT_ID }')">${var.NAME_EN}</a></td>
															<td class='center'>${var.HEADMAN}</td>
															<td class="center"><c:if test="${QX.edit != 1 && QX.del != 1 }">
																	<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
																</c:if>
																	<c:if test="${QX.edit == 1 }">
																		<a class="btn btn-success" title="编辑" onclick="edit('${var.DEPARTMENT_ID}');"> <i class="fa fa-pencil-square-o" title="编辑"></i> 编辑
																		</a>
																	</c:if>
																	<c:if test="${QX.del == 1 }">
																		<a class="btn btn-danger" onclick="del('${var.DEPARTMENT_ID}');"> <i class="fa fa-trash-o" title="删除"></i> 删除
																		</a>
																	</c:if>
																</td>
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
												<tr class="main_info">
													<td colspan="100" class="center">没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								<div class="page-header position-relative">
									<table style="width: 100%;">
										<tr>
											<td style="vertical-align: top;"><c:if test="${QX.add == 1 }">
													<a class="btn btn-success" onclick="add('${DEPARTMENT_ID}');"><i class="fa fa-plus"></i> 新增</a>
												</c:if> <c:if test="${null != pd.DEPARTMENT_ID && pd.DEPARTMENT_ID != ''}">
													<a class="btn btn-white" onclick="goSondict('${pd.PARENT_ID}');"><i class="fa fa-arrow-left"></i> 返回</a>
												</c:if></td>
											<td style="vertical-align: top;"><div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
										</tr>
									</table>
								</div>
							</form>

						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
	</div>
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		//检索
		function gsearch(){
			$("#Form").submit();
		}
		
		//去此ID下子级列表
		function goSondict(DEPARTMENT_ID){
			window.location.href="<%=basePath%>department/list.do?DEPARTMENT_ID="+DEPARTMENT_ID;
		};
		
		//新增
		function add(DEPARTMENT_ID){
			var index = layer.open({
				  type: 2,
				  title:'新增',
				  content: '<%=basePath%>department/goAdd.do?DEPARTMENT_ID='+DEPARTMENT_ID,
				  area: ['80%', '80%']
				});
		}
		
		//删除
		function del(Id){
			layer.confirm("确定要删除吗?", function(result) {
					var url = "<%=basePath%>department/delete.do?DEPARTMENT_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						if("success" == data.result){
							parent.location.href="<%=basePath%>department/listAllDepartment.do?DEPARTMENT_ID=${DEPARTMENT_ID}&dnowPage=${page.currentPage}";
						}else if("false" == data.result){
							layer.msg("删除失败！请先删除子级部门.");						}
					});
			});
		}
		
		//修改
		function edit(Id){
			var index = layer.open({
				  type: 2,
				  title:'新增',
				  content: '<%=basePath%>department/goEdit.do?DEPARTMENT_ID='+Id,
				  area: ['80%', '80%']
				});
		}
		
	</script>


</body>
</html>