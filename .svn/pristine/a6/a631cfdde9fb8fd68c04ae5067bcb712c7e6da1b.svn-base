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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>保存结果</title>
<base href="<%=basePath%>">
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

</head>
<body>
	<div id="zhongxin"></div>
	<script type="text/javascript">
		var msg = "${msg}";
		var action ="${action}"
		if(action =="parent"){
			parent&&parent.parentFnc&&parent.parentFnc()
		}else if(msg=="success" || msg==""){
			if(top.Dialog){
			document.getElementById('zhongxin').style.display = 'none';
			top.Dialog.close();
			}else{
				if('${sy_workwench}'){
					console.log(111)
					parent.a()
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				}else{
					console.log('222')
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				parent.yocto.reload()
				parent.layer.close(index)
				}
			}
		}else{
			top.Dialog.close();
		}
		
	</script>
</body>
</html>