<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="${pageContext.request.contextPath}/">
<%@ include file="../../system/index/top.jsp"%>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInUp">
		<!-- /section:basics/sidebar -->
		<div class="ibox-content">
			<div class="row">
				<div class="col-xs-12">
					<!-- 检索  -->
					<form class="form-inline" role="form" action="workBench/wdaj/myOrderList.do" method="post" name="customerForm" id="customerForm">
						<div class="form-group">
							<input id="nav-search-input" name="keywords" placeholder="<c:if test="${pd.runnerId == null && pd.lawerId ==null }">运作人、</c:if><c:if test="${pd.saleId == null}">销售人、</c:if>债权人(公司)、债务人(公司)" value="${pd.keywords }" class="form-control" type="text" style="width: 350px;">
						</div>
						<div class="form-group">
							<input class="span10 date-picker form-control" id="createTimeStart" name="createTimeStart" value="${fn:substring(pd.createTimeStart, 0, 10)}" type="text" data-date-format="yyyy-mm-dd"
								readonly="readonly" placeholder="开始日期" title="注册时间开始" style="width: 100px;" />
						</div>
						<div class="form-group">
							<input class="span10 date-picker form-control" id="createTimeEnd" name="createTimeEnd" value="${fn:substring(pd.createTimeEnd, 0, 10)}" type="text" data-date-format="yyyy-mm-dd"
								readonly="readonly" placeholder="结束日期" title="注册时间结束" style="width: 100px;" />
						</div>
						<div class="form-group">
							案件类型 <select id="type" name="type" class="form-control" style="width: 100px;" data-placeholder="案件类型">
								<option value="" <c:if test="${empty pd.type || pd.type == null || pd.type == ''}">selected</c:if>>全部</option>
								<option value="0" <c:if test="${pd.type == '0'}">selected</c:if>>非诉讼</option>
								<option value="1" <c:if test="${pd.type == '1'}">selected</c:if>>诉讼</option>
								<option value="2" <c:if test="${pd.type == '2'}">selected</c:if>>仲裁</option>
							</select>
						</div>
						<div class="form-group">
							案件状态 <select id="status" name="status" class="form-control" style="width: 100px;" data-placeholder="案件状态">
								<option value="" <c:if test="${empty pd.status || pd.status == null || pd.status == ''}">selected</c:if>>全部</option>
								<option value="0" <c:if test="${pd.status == '0'}">selected</c:if>>运作中</option>
								<option value="1" <c:if test="${pd.status == '1'}">selected</c:if>>已结案</option>
								<option value="2" <c:if test="${pd.status == '2'}">selected</c:if>>已关闭</option>
								<option value="3" <c:if test="${pd.status == '3'}">selected</c:if>>诉讼中</option>
								<option value="4" <c:if test="${pd.status == '4'}">selected</c:if>>仲裁中</option>
							</select>
						</div>
						<%-- <div class="form-group">
							是否归档 <select id="isFiled" name="isFiled" class="form-control" style="width: 100px;" data-placeholder="归档状态">
								<option value="" <c:if test="${empty pd.isFiled || pd.isFiled == null || pd.isFiled == ''}">selected</c:if>>全部</option>
								<option value="0" <c:if test="${pd.isFiled == '0'}">selected</c:if>>否</option>
								<option value="1" <c:if test="${pd.isFiled == '1'}">selected</c:if>>是</option>
							</select>
						</div> --%>
						<button type="submit" class="btn btn-primary">
							<i class="fa fa-search"></i> 搜索
						</button>
						<button type="reset" class="btn">
							<i id="nav-search-icon" class="fa fa-refresh nav-search-icon "></i> 重置
						</button>
					</form>
				</div>
				<%-- <c:if test="${not empty pd.runnerId ||not empty pd.lawerId}">
					<div class="col-xs-12" style="margin-top: 10px;">
						<button class="btn btn-info" disabled="disabled" id="btn-precalc" onclick="doTask(this,'8');"> <i class="fa fa-send-o" title="下月回款预估"></i> 下月回款预估
						</button>
						 <button class="btn btn-info" disabled="disabled" id="btn-supplylaw" onclick="doTask(this,'5');"> <i class="fa fa-send-o" title="申请诉讼仲裁"></i> 申请诉讼仲裁
						</button> 
											</div>
				</c:if> --%>
			</div>
		</div>
		<div class="ibox-content">
			<!--<div class="alert alert-warning">
                            <span class="alert-link"> 合计: </span> 欠款总金额    <span class="alert-link" style="font-size:18px"> ${pd.qkzje } </span> 元 已回款总金额<span class="alert-link" style="font-size:18px"> ${pd.hkzje  }</span> 元
                           		  剩余总金额<span class="alert-link" style="font-size:18px"> ${pd.syzje  }</span> 元
                        </div>-->
            <div class="htz-box-no col-sm-2" style="cursor: pointer;">
						<span class="pull-left"><img src="/static/ui/img/10icon/h.png"></span>
						<div class="pull-left">
							<div>欠款总金额</div>
							<div class="blue">¥${pd.qkzje }</div>
						</div>
					</div>
			<div class="htz-box-no col-sm-2" style="cursor: pointer;">
						<span class="pull-left"><img src="/static/ui/img/10icon/t.png"></span>
						<div class="pull-left">
							<div>已回款总金额</div>
							<div class="blue">¥${pd.hkzje }</div>
						</div>
					</div>   	
			<div class="htz-box-no col-sm-2" style="cursor: pointer;">
						<span class="pull-left"><img src="/static/ui/img/10icon/z.png"></span>
						<div class="pull-left">
							<div>剩余总金额</div>
							<div class="blue">¥${pd.syzje }</div>
						</div>
					</div>             
			<table id="simple-table" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
					<th class="center" style="width: 35px;"></th>
						<th class="center" style="width: 50px;">序号</th>
						<c:if test="${pd.runnerId == null && pd.lawerId ==null }"><th class="center">运作人</th></c:if>
						<th class="center">债权人(公司)</th>
						<th class="center">债务人(公司)</th>
						<th class="center">欠款总金额(元)</th>
						<th class="center">已回款金额(元)</th>
						<th class="center">剩余金额(元)</th>
						<th class="center">佣金比例<br>(诉讼/非诉讼)</th>
						<th class="center">案件类型</th>
						<th class="center">案件状态</th>
						<!-- <th class="center">是否归档</th> -->
						<th class="center">跟进状态</th>
						<th class="center">跟进时间</th>
						<!-- <th class="center">时间</th> -->
						<c:if test="${pd.saleId == null}"><th class="center">销售人</th></c:if>
							<th class="center">操作</th>
					</tr>
				</thead>

				<tbody>
					<!-- 开始循环 -->
					<c:choose>
						<c:when test="${not empty dataList}">
							<c:if test="${QX.cha == 1 }">
								<c:forEach items="${dataList}" var="list" varStatus="vs">
									<tr>
										<td class='center' style="width: 30px;"><label><input class="list-item-hook" type='checkbox' name='ids' data-isLs="${pd.lawerId }" data-status="${list.status }" value="${list.id }" /><span class="lbl"></span></label></td>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<c:if test="${pd.runnerId == null && pd.lawerId ==null }"><td class="center">${list.runnerName}</td></c:if>
										<td class="center">${list.custoemrName}</td>
										<td class="center">${list.debtorName}</td>
										<td class="center">${list.debtAmount }</td>
										<td class="center">${list.totalMoney }</td>
										<td class="center">${list.syMoney }</td>
										<td class="center"><c:if test="${list.cType==0 }">${list.commissionRate}<c:if test="${not empty list.lawCommissionRate}">/${list.lawCommissionRate}</c:if></c:if><c:if test="${list.cType==1 }">-</c:if></td>
										<td class="center"><c:if test="${list.type=='0' }">非诉讼</c:if><c:if test="${list.type=='1' }">诉讼</c:if><c:if test="${list.type=='2' }">仲裁</c:if></td>
										<td class="center"><c:if test="${list.status==0 }">运作中</c:if><c:if test="${list.status==1 }">已结案</c:if><c:if test="${list.status==2 }">已关闭</c:if><c:if test="${list.status==3 }">诉讼中</c:if><c:if test="${list.status==4 }">仲裁中</c:if></td>
										<td class="center">
											<c:if test="${list.gjStatus==0 }">未处理</c:if><c:if test="${list.gjStatus==1 }">初步沟通</c:if>
											<c:if test="${list.gjStatus==2 }">无意向还款</c:if><c:if test="${list.gjStatus==3 }">有意向还款</c:if>
											<c:if test="${list.gjStatus==4 }">外访</c:if><c:if test="${list.gjStatus==5 }">部分还款</c:if>
											<c:if test="${list.gjStatus==6 }">已结清</c:if>
										</td>
										<td class="center">${list.gjTime }</td>
										<c:if test="${pd.saleId == null}"><td class="center">${list.saleName}</td></c:if>
										<td class="center"><a title="点击查看详情" class="btn btn-primary" onclick="view('${list.id}')" style="cursor: pointer;"><i class="fa fa-search"></i> 查看</a>
										<%-- <c:if test="${not empty pd.runnerId }">
											<a class="btn btn-success" title="编辑债务人" onclick="edit('${list.id}');"> <i class="ace-icon fa fa-pencil-square-o" title="编辑债务人"></i> 编辑债务人
											</a>
										</c:if> --%>
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
			<div class="clearfix">
				<div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
			</div>
		</div>
		<!-- /.col -->
	</div>
</body>
<!-- /.main-container -->
<!-- 全局js -->
<%@ include file="../../system/index/js.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/include/business/workBenchManage/order_list.js"></script>
<script>
function viewDetail(id) {
	var index = layer.open({
		type : 2,
		title : '查看详情',
		content : '${pageContext.request.contextPath}/order/view.do?id='+id,
		area : [ '90%', '90%' ]
	});
}

</script>
</body>
</html>
