<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="${pageContext.request.contextPath}/">
	<script type="text/javascript">
	
	var flag =   '${pd.flag}' //0:客服 1：运作精英、销售精英 2：运作总监 3：销售总监 4：公司内部律师(诉讼/仲裁) 5:经理
	var flags = '${pd.flags}'
	location.href= '${pageContext.request.contextPath}/static/page/home.html?flag=' + flag + '&flags=' + flags
	</script>
</body>

</html>
