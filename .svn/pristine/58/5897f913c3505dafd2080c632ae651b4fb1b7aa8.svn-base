<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<base href="${pageContext.request.contextPath}/">
		<!-- jsp文件头和头部 -->
		<%@ include file="../../system/index/top.jsp"%>
	</head>

	<body class="w100">
		<div class="wrapper wrapper-content">
			<div class="row">
				<div class="col-sm-12">
					<form class="form form-horizontal" >
						<div class="row" id="applyForm">
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-sm-3 control-label">外出目标：</label>
									<div class="col-sm-9">
										<input disabled="disabled" class="form-control" data-validate="require" placeholder="这里输入外出目标" type="text" name="target" value="${pd.target }" />
									</div>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-sm-3 control-label">拜访人：</label>
									<div class="col-sm-9">
										<input disabled="disabled" class="form-control" placeholder="这里输入拜访人" data-validate="require" type="text" name="visitman" value="${pd.visitman }" />
									</div>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-sm-3 control-label">联系方式：</label>
									<div class="col-sm-9">
										<input disabled="disabled" class="form-control" type="text" name="mobilPhone" placeholder="这里输入联系方式" value="${pd.mobilPhone }" />
									</div>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-sm-3 control-label">类别：</label>
									<div class="col-sm-9">
										<select id="applytype" disabled="disabled" class="form-control" data-validate="require" onchange="changeType(this)" name="type" id="">
											<option value="0" <c:if test="${pd.type==0 }"> selected="selected" </c:if>>外出</option>
											<option value="1" <c:if test="${pd.type==1 }"> selected="selected" </c:if>>出差</option>
										</select>
									</div>
								</div>
							</div>
							<c:if test="${not empty pd.collaboratorName }">
							<div class="col-md-12 relative">
								<div class="form-group">
									<label class="col-sm-3 control-label">协作人：</label>
									<div class="col-sm-9">
										<select name="collaborator" disabled="disabled" class="chosen-select form-control" id="collaborator">
											<option value="${pd.collaborator}">${pd.collaboratorName}</option>
										</select>
									</div>
								</div>
							</div>
							</c:if>
							<div class="col-md-12 relative">
								<div class="form-group">
									<label class="col-sm-3 control-label">联系人地址：</label>
									<div class="col-sm-9">
								<input class="form-control" disabled placeholder="这里输入联系人地址" type="text" name="address" value="${pd.address}" />
									</div>
								</div>
							</div>
							<c:if test="${pd.type==0  }">
							<div class="col-md-12 range1">
								<div class="form-group">
									<label class="col-sm-3 control-label">预计时长(小时)：</label>
									<div class="col-sm-9">
										<input disabled="disabled" class="form-control" placeholder="这里输入预计时长" data-validate="require" type="text" name="yjsc" value="${pd.yjsc}" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">开始时间：</label>
									<div class="col-sm-9">
										<input disabled="disabled" class="form-control time-picker1 layer-date" data-validate="require" readonly onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" placeholder="这里输入开始时间" type="text" name="kssj" value="${pd.kssj}" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">预计回单位时间：</label>
									<div class="col-sm-9">
										<input disabled="disabled" class="form-control time-picker2 layer-date" data-validate="require" readonly onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" placeholder="这里输入预计回单位时间" type="text" name="yjhdwsj" value="${pd.yjhdwsj}" />
									</div>
								</div>
							</div>
							</c:if>
							<c:if test="${pd.type==1 }">
							<div class="col-md-12 range2">
								<div class="form-group">
									<label class="col-sm-3 control-label">预计天数(天)：</label>
									<div class="col-sm-9">
										<input disabled="disabled" class="form-control" placeholder="这里输入预计天数" data-validate="require" type="text" name="yjts" value="${pd.yjts}" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">开始日期：</label>
									<div class="col-sm-9">
										<input disabled="disabled" class="form-control" data-validate="require" type="text" name="ksrq" readonly="readonly" value="${pd.ksrq}" placeholder="这里输入开始日期" onclick="laydate({format: 'YYYY-MM-DD'})" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">预计回单位日期：</label>
									<div class="col-sm-9">
										<input disabled="disabled" class="form-control time-pickerl4" data-validate="require" type="text" readonly="readonly" name="yjhdwrq" value="${pd.yjhdwrq}" placeholder="这里输入预计回单位日期" onclick="laydate({format: 'YYYY-MM-DD'})" />
									</div>
								</div>
							</div>
							</c:if>
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-sm-3 control-label">外出事由：</label>
									<div class="col-sm-9">
										<input disabled="disabled" class="form-control" placeholder="这里输入外出事由" type="text" data-validate="require" name="reason" value="${pd.reason}" />
									</div>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label class="col-sm-3 control-label">预期达到的效果：</label>
									<div class="col-sm-9">
										<textarea disabled="disabled" class="form-control" placeholder="这里输入预期达到的效果" name="yqxg" rows="" cols="">${pd.yqxg}</textarea>
									</div>
								</div>
							</div>
						</div>
						<c:if test="${pd.auditStatus!=0}">
						<div class="row" id="shenhe">
						<div class="col-md-12" >
							<div class="form-group">
								<label class="col-sm-3 control-label">是否通过审核：</label>
								<div class="col-sm-9">
									<div class="radio radio-success radio-inline">
										<input disabled="disabled" type="radio" <c:if test="${pd.auditStatus == '1'}">checked="checked" </c:if><c:if test="${pd.auditStatus == '1'||pd.auditStatus == '2'}">disabled="disabled"</c:if> value="1" id="form-isDispute-radio1" name="auditStatus">
										<label for="form-isDispute-radio1"> 否 </label>
									</div>
									<div class="radio radio-success radio-inline">
										<input disabled="disabled" type="radio" <c:if test="${pd.auditStatus == '2'}">checked="checked"</c:if><c:if test="${pd.auditStatus == '1'||pd.auditStatus == '2'}">disabled="disabled"</c:if>  id="form-isDispute-radio2" value="2" name="auditStatus">
										<label for="form-isDispute-radio2"> 是 </label>
									</div>
								</div>
							</div>
							<div class="row">
								
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-3 no-padding-right">
																领导指示:
															</label>
								<div class="col-sm-9">
									<textarea disabled="disabled" id="leaderShip" class="form-control" style="height: 120px;" rows="15" cols="10" name="leaderShip" placeholder="这里输入领导指示">${pd.leaderShip}</textarea>
								</div>
							</div>
						</div>
							</div>
				</div>
				</div>
				<div id="complete">
				<c:if test="${pd.auditStatus==2 && not empty pd.wcqk}">
				<c:if test="${pd.type==0}">
					<div class="form-group" id="sjTime">
									<label class="col-sm-3 control-label">实际回单位时间：</label>
									<div class="col-sm-9">
										<input disabled="disabled"  class="form-control time-pickerl4" data-validate="require" type="text" readonly="readonly" name="sjhgssj" value="${pd.sjhgssj}" placeholder="这里输入实际回单位时间" onclick="laydate({isTime:true,format: 'YYYY-MM-DD'})" />
									</div>
								</div>
								</c:if>
								<c:if test="${pd.type==1 }">
				<div class="form-group" id="sjDate"> 
									<label class="col-sm-3 control-label">实际回单位日期：</label>
									<div class="col-sm-9">
										<input disabled="disabled" class="form-control time-pickerl4" data-validate="require" type="text" readonly="readonly" name="sjhgsrq" value="${pd.sjhgsrq}" placeholder="这里输入实际回单位日期" onclick="laydate({format: 'YYYY-MM-DD'})" />
									</div>
								</div>
								</c:if>
								<div class="form-group">
									<label class="col-sm-3 control-label">实际完成情况：</label>
									<div class="col-sm-9">
										<textarea disabled="disabled" class="form-control" style="height: 120px;" rows="15" cols="10" name="wcqk" data-validate="require" placeholder="这里输入实际完成情况">${pd.wcqk}</textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">未完成原因：</label>
									<div class="col-sm-9">
										<textarea disabled="disabled" id="wwcyy" class="form-control" style="height: 120px;" rows="15" cols="10" name="wwcyy" placeholder="这里输入未完成原因">${pd.wwcyy}</textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">自我评价：</label>
									<div class="col-sm-9">
									<input disabled="disabled" class="form-control" id="evaluatSelf"  value="<c:if test="${pd.evaluatSelf==0 }">优秀</c:if><c:if test="${pd.evaluatSelf==1 }">良好</c:if><c:if test="${pd.evaluatSelf==2 }">有待改进</c:if>" />
									</div>
								</div>
				</div>
				</c:if>
				</c:if>
				</form>
				<!--<div class="text-center">
					<a class="btn btn-mini btn-danger" onclick="yocto.closeChildLayer();"><i class="fa fa-remove"></i> 关闭</a>
				</div>-->
			</div>
			</div>
			</div>
		<!-- 页面底部js¨ -->
		<%@ include file="../../system/index/js.jsp"%>
	</body>
	<script type="text/javascript">
		var flag = '${pd.flag}' // 1:运作  / 2:销售  /3|| 4|| 5:审核
		
		var auditStatus='${pd.auditStatus}'   // 2:  任务完成
		//编辑状态
		var type = '${pd.type}';   //0:外出 1:出差
		//查看状态
	</script>

</html>