<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>${pd.SYSNAME}</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="shortcut icon" href="favicon.ico">
<link href="static/ui/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="static/ui/css/font-awesome.css?v=4.4.0" rel="stylesheet">

<link href="static/ui/css/animate.css" rel="stylesheet">
<!--[if lt IE 9]>
 <meta http-equiv="refresh" content="0;ie.html" />
 <![endif]-->
<script>
	if (window.top !== window.self) {
		window.top.location = window.location;
	}
</script>
<style>
html {
	height: 100%
}

body {
	background: url(static/ui/img/login-bg.jpg) center no-repeat;
	background-size: cover;
}

strong {
	font-weight: 500;
}

a, a:hover, a:focus {
	color: #4aaf51;
	text-decoration: none;
	-o-transition: all .3s;
	-moz-transition: all .3s;
	-webkit-transition: all .3s;
	-ms-transition: all .3s;
	transition: all .3s;
}

h1, h2 {
	margin-top: 10px;
	font-size: 38px;
	font-weight: 100;
	color: #555;
	line-height: 50px;
}

h3 {
	font-size: 22px;
	font-weight: 300;
	color: #555;
	line-height: 30px;
}

img {
	max-width: 100%;
}

.btn-link-1 {
	display: inline-block;
	height: 50px;
	margin: 5px;
	padding: 16px 20px 0 20px;
	background: #4aaf51;
	font-size: 16px;
	font-weight: 300;
	line-height: 16px;
	color: #fff;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border-radius: 4px;
}

.btn-link-1:hover, .btn-link-1:focus, .btn-link-1:active {
	outline: 0;
	opacity: 0.6;
	color: #fff;
}

.btn-link-1.btn-link-1-facebook {
	background: #4862a3;
}

.btn-link-1.btn-link-1-twitter {
	background: #55acee;
}

.btn-link-1.btn-link-1-google-plus {
	background: #dd4b39;
}

.btn-link-1 i {
	padding-right: 5px;
	vertical-align: middle;
	font-size: 20px;
	line-height: 20px;
}

.btn-link-2 {
	display: inline-block;
	height: 50px;
	margin: 5px;
	padding: 15px 20px 0 20px;
	background: rgba(0, 0, 0, 0.3);
	border: 1px solid #fff;
	font-size: 16px;
	font-weight: 300;
	line-height: 16px;
	color: #fff;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border-radius: 4px;
}

.btn-link-2:hover, .btn-link-2:focus, .btn-link-2:active, .btn-link-2:active:focus {
	outline: 0;
	opacity: 0.6;
	background: rgba(0, 0, 0, 0.3);
	color: #fff;
}

/***** Top content *****/
.inner-bg {
	padding: 100px 0 170px 0;
}

.top-content .text {
	color: #fff;
}

.top-content .text h1 {
	color: #fff;
}

.top-content .description {
	margin: 20px 0 10px 0;
}

.top-content .description p {
	opacity: 0.8;
}

.top-content .description a {
	color: #fff;
}

.top-content .description a:hover, .top-content .description a:focus {
	border-bottom: 1px dotted #fff;
}

.form-box {
	margin-top: 35px;
}

.form-top {
	overflow: hidden;
	padding: 25px;
	background: #fff;
	-moz-border-radius: 4px 4px 0 0;
	-webkit-border-radius: 4px 4px 0 0;
	border-radius: 4px 4px 0 0;
}

.form-top-left {
	float: left;
	width: 75%;
	padding-top: 25px;
}

.form-top-left h3 {
	margin-top: 0;
}

.form-top-right {
	float: left;
	width: 25%;
	padding-top: 5px;
	font-size: 66px;
	color: #ddd;
	line-height: 100px;
	text-align: right;
}

.form-bottom {
	padding: 25px 25px 30px 25px;
	background: #eee;
	-moz-border-radius: 0 0 4px 4px;
	-webkit-border-radius: 0 0 4px 4px;
	border-radius: 0 0 4px 4px;
	text-align: left;
}

.form-bottom form textarea {
	height: 100px;
}

.form-bottom form button.btn {
	width: 100%;
}

.form-bottom form .input-error {
	border-color: #4aaf51;
}

.social-login {
	margin-top: 35px;
}

.social-login h3 {
	color: #fff;
}

.social-login-buttons {
	margin-top: 25px;
}

/***** Media queries *****/
@media ( min-width : 992px) and (max-width: 1199px) {
}

@media ( min-width : 768px) and (max-width: 991px) {
}

@media ( max-width : 767px) {
	.inner-bg {
		padding: 60px 0 110px 0;
	}
}

@media ( max-width : 415px) {
	h1, h2 {
		font-size: 32px;
	}
}
</style>
</head>
<body>
	<!-- Top content -->
	<div class="top-content">

		<div class="inner-bg">
			<div class="container">
				<div class="row">
					<div class="form-box" style="max-width:420px;margin-left:auto;margin-right:auto">
						<div class="form-top text-center">
							<img src="/static/ui/img/logo.png" />
						</div>
						<div class="form-bottom">
							<div class="form-group">
								<label class="sr-only" for="form-username">账号</label> <input type="text" name="form-username" placeholder="账号" class="form-username form-control" id="loginname">
							</div>
							<div class="form-group">
								<label class="sr-only" for="form-password">密码</label> <input type="password" name="form-password" placeholder="密码" class="form-password form-control" id="password">
							</div>
							<!-- <div class="form-group">
								<div class="row">
									<div class="col-sm-8 col-xs-12">
										<input placeholder="验证码" name="code" id="code" class="form-control" type="text">
									</div>
									<div class="col-sm-4 col-xs-12">
										<img style="height: 32px; width: 100%" id="codeImg" alt="点击更换" title="点击更换" src="" />
									</div>
								</div>
							</div> -->
							<div class="form-group">
							<div class="checkbox">
                                        <label>
                                            <input type="checkbox" value="" id="saveid" onchange="saveCookie()">记住用户名密码</label>
                                    </div>
							</div>
							<button type="submit" onclick="severCheck();" id="loginBtn" class="btn btn-primary btn-block">登录</button>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<!-- 全局js -->
	<script src="static/ui/js/jquery.min.js?v=2.1.4"></script>
	<script src="static/ui/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="static/ui/js/plugins/layer/layer.min.js"></script>
	<script src="static/ui/js/jquery.cookie.js"></script>
	<script type="text/javascript">
		//服务器校验
		function severCheck() {
			if (check()) {
				var loginname = $("#loginname").val();
				var password = $("#password").val();
				var code = loginname + ",fh," + password + ",fh,"
						+ $("#code").val();
				$.ajax({
					type : "POST",
					url : 'login_login',
					data : {
						KEYDATA : code,
						tm : new Date().getTime()
					},
					dataType : 'json',
					cache : false,
					success : function(data) {
						if ("success" == data.result) {
							saveCookie();
							window.location.href = "main/index";
						} else if ("usererror" == data.result) {
							layer.tips('用户名或密码有误', "#loginBtn")
							$("#loginname").focus();
						} else if ("codeerror" == data.result) {
							layer.tips('验证码输入有误', "#code", {
								tips : [ 4 ]
							})
							$("#code").focus();
						} else {
							layer.tips(data.result, "#loginname")
							$("#loginname").focus();
						}
					}
				});
			}
		}
		$(document).on('keyup', function(event) {
			if (event.keyCode == "13") {
				//回车执行查询
				severCheck()
			}
		});
		$(document).ready(function() {
			changeCode();
			$("#codeImg").bind("click", changeCode);
		});

		$(document).keyup(function(event) {
			if (event.keyCode == 13) {
				$("#to-recover").trigger("click");
			}
		});

		function genTimestamp() {
			var time = new Date();
			return time.getTime();
		}

		function changeCode() {
			$("#codeImg").attr("src", "code.do?t=" + genTimestamp());
		}

		//客户端校验
		function check() {

			if ($("#loginname").val() == "") {
				layer.tips('用户名不得为空', "#loginname")
				$("#loginname").focus();
				return false;
			} else {
				$("#loginname").val(jQuery.trim($('#loginname').val()));
			}

			if ($("#password").val() == "") {
				layer.tips('密码不得为空', "#password")
				$("#password").focus();
				return false;
			}
			if ($("#code").val() == "") {
				layer.tips('验证码不得为空', "#code", {
					tips : [ 4 ]
				})
				$("#code").focus();
				return false;
			}
			layer.tips('正在登录,请稍后', "#loginBtn")
			return true;
		}

		/* 		function savePaw() {
		 if (!$("#saveid").attr("checked")) {
		 $.cookie('loginname', '', {
		 expires : -1
		 });
		 $.cookie('password', '', {
		 expires : -1
		 });
		 $("#loginname").val('');
		 $("#password").val('');
		 }
		 } */

		function saveCookie() {
			 			if ($("#saveid").prop("checked")) { 
			$.cookie('loginname', $("#loginname").val(), {
				expires : 7
			});
			$.cookie('password', $("#password").val(), {
			 expires : 7
			 }); 
			 			}else{
			 				$.cookie('loginname', '', {
			 					 expires : -1
			 					 });
			 			}
		}
		function quxiao() {
			$("#loginname").val('');
			$("#password").val('');
		}

		jQuery(function() {
			var loginname = $.cookie('loginname');
			var password = $.cookie('password');
			if (typeof (loginname) != "undefined") {
				$("#loginname").val(loginname);
				$("#password").val(password);
				$("#saveid").prop("checked", true);
				$("#code").focus();
			}
		});
	</script>
	<c:if test="${'1' == pd.msg}">
		<script type="text/javascript">
			$(tsMsg());
			function tsMsg() {
				alert('此用户在其它终端已经早于您登录,您暂时无法登录');
			}
		</script>
	</c:if>
	<c:if test="${'2' == pd.msg}">
		<script type="text/javascript">
			$(tsMsg());
			function tsMsg() {
				alert('您被系统管理员强制下线');
			}
		</script>
	</c:if>

</body>

</html>