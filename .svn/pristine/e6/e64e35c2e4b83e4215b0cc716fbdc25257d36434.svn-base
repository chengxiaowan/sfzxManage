<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<style type="text/css">
footer {
	height: 50px;
	position: fixed;
	bottom: 0px;
	left: 0px;
	width: 100%;
	text-align: center;
}
</style>
<body>
</head>
<body class="gray-bg">
	<div class="ibox-content">
					<div class="row">
						<div class="col-xs-12">
									<ul id="tree" class="tree" style="overflow: auto;"></ul>
						</div>
					</div>
	</div>

	<div class="text-center">
		<a class="btn btn-primary" onclick="save();"><i class="fa fa-save"></i> 保存</a> <a class="btn btn-danger" onclick="yocto.closeChildLayer();"><i class="fa fa-remove"></i> 取消</a>
	</div>
<%@ include file="../index/js.jsp"%>
<link type="text/css" rel="stylesheet" href="plugins/zTree/2.6/zTreeStyle.css" />
<script type="text/javascript" src="plugins/zTree/2.6/jquery.ztree-2.6.min.js"></script>
	<script type="text/javascript">
		var zTree;
		$(document).ready(function(){
			
			var setting = {
			    showLine: true,
			    checkable: true,
			    showIcon:false
			};
			var zn = '${zTreeNodes}';
			var zTreeNodes = JSON.parse(zn);
			zTree = $("#tree").zTree(setting, zTreeNodes);
		});
	
		//保存
		 function save(){
			var nodes = zTree.getCheckedNodes();
			var tmpNode;
			var ids = "";
			for(var i=0; i<nodes.length; i++){
				tmpNode = nodes[i];
				if(i!=nodes.length-1){
					ids += tmpNode.id+",";
				}else{
					ids += tmpNode.id;
				}
			}
			var ROLE_ID = "${ROLE_ID}";
			var url = "<%=basePath%>role/saveMenuqx.do";
			var postData;
			postData = {"ROLE_ID":ROLE_ID,"menuIds":ids};
			layer.load()
			$.post(url,postData,function(data){
				yocto.closeChildLayer()
			});
		 }
	
	</script>
</body>
</html>