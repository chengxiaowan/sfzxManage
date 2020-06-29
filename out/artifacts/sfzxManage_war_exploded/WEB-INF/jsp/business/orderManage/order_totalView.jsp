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
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-content-inner">
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a data-toggle="tab" href="#home"><i class="green icon-home bigger-110"></i> 基本信息</a></li>
									<li><a data-toggle="tab" href="#attach" onclick="init_uploader()"><i class="green icon-home bigger-110"></i> 相关附件</a></li>
								</ul>
								<div class="ibox-content">
										<form action="order/${msg }.do" name="contractForm" id="contractForm" method="post"  class="form form-horizontal" >
										<div class="tab-content">
										<div id="home" class="tab-pane in active">
										<div class="row">
														<div class="col-md-12">
														    <div class="form-group">
														        <label class="col-sm-2 control-label">标题&nbsp;<font color="red">*</font>：</label>
														        <div class="col-sm-10">
														            <input class="form-control" data-validate="require|maxLength=50" type="text"  readonly="readonly" name="title" id="title"  value="${pd.title }"  >
														        </div>
														    </div>
														</div>
														<div class="col-md-12">
													<div class="form-group">
														<label  class="control-label col-sm-2">
															备注:
														</label>
														<div class="col-sm-10">
														<textarea class="form-control" style="height: 120px;"  name="content"  readonly="readonly" >${pd.content}</textarea>
														</div>
													</div>	
													</div>													
												</div>
												</div>
												<div id="attach" class="tab-pane">
												<div class="ibox">
													<div id="fileUp"></div>
												<div class="ibox-content">
													<div id="fileList" class="uploader-list">
													<table class="table table-bordered">
								                            <thead>
								                                <tr>
								                                    <th>文件名</th>
								                                    <th>上传人</th>
								                                    <th>上传时间</th>
								                                    <th>操作</th>
								                                </tr>
								                            </thead>
								                            <tbody>
								                            	<c:if test="${not empty pd.attachs }">
								                            		<c:forEach items="${pd.attachs }"  var="attachs">
								 										<tr><td>${attachs.originalFilename }</td><td>${attachs.uploader }</td><td>${attachs.uploadTime }</td><td><a target="_blank" href="${attachs.url }" class="btn btn-primary"><i class="fa fa-eye"></i> 查看</a> <input type="hidden" name="fileSize" value="${attachs.fileSize }"><input type="hidden" name="uploader" value="${attachs.uploader }"><input type="hidden" name="originalFilename" value="${attachs.originalFilename }"><input type="hidden" name="uploadTime" value="${attachs.uploadTime }"><input type="hidden" name="realPath" value="${attachs.realPath }"><input type="hidden" name="url" value="${attachs.url }"><input type="hidden" name="attachId" value="${attachs.id }"></td></tr>                           		
								                            		</c:forEach>
								                            	</c:if>
								                            </tbody>
								                        </table>
													</div>
												</div>
												</div>
												</div>
												</div>
										</form>
								</div>
							</div>
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	<!-- /.main-container -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/js.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.tips.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/myjs/yoValidate.js" type="text/javascript" charset="utf-8"></script>
	<!-- 日期框 -->
	<script src="${pageContext.request.contextPath}/static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/ui/js/plugins/webuploader/webuploader.min.js"></script>
	<!-- Prettyfile -->
	<script src="${pageContext.request.contextPath}/static/ui/js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
	<script src="${pageContext.request.contextPath}/static/ace/js/jquery.colorbox.js"></script>
</body>
</html>