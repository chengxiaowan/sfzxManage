<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="${pageContext.request.contextPath}/">

<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<!-- 百度echarts -->
<!--<script src="plugins/echarts/echarts.min.js"></script>-->
</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="hr hr-18 dotted hr-double"></div>
					<div class="row">
						<div class="col-xs-12">

							<div class="alert alert-block alert-success">
								<button type="button" class="close" data-dismiss="alert">
									<i class="ace-icon fa fa-times"></i>
								</button>
								<i class="ace-icon fa fa-check green"></i> 欢迎使用 ${pd.SYSNAME} 后台管理系统
							</div>
							<div class="row">
								<div class="col-md-6 col-xs-12">
									<div id="user" style="height: 300px;"></div>

								</div>
								<div class="col-md-6 col-xs-12">
									<div id="order" style="height: 300px;"></div>

								</div>
							</div>
							<script type="text/javascript">
						        // 基于准备好的dom，初始化echarts实例
						        var user = echarts.init(document.getElementById('user'));
						        var order = echarts.init(document.getElementById('order'));
								var orderDetail=${pdlist};
								var orderData=[];
								var orderTime=[];
								for(var i = 0;i<orderDetail.length;i++){
									orderTime.push(orderDetail[i].month+'月')
									orderData.push(orderDetail[i].cishu)
								}
								// 指定图表的配置项和数据
								var userOpt = {
						            title: {
						                text: '${pd.SYSNAME}用户统计'
						            },
						            tooltip: {},
						            xAxis: {
						                data: ["系统用户","系统会员"]
						            },
						            yAxis: {},
						            series: [
						               {
						                name: '',
						                type: 'bar',
						                data: [${pd.userCount},${pd.appUserCount}],
						                itemStyle: {
						                    normal: {
						                        color: function(params) {
						                            // build a color map as your need.
						                            var colorList = ['#6FB3E0','#87B87F'];
						                            return colorList[params.dataIndex];
						                        }
						                    }
						                }
						               }
						            ]
						        };	        
								var orderOpt = {
						            tooltip: {
        trigger: 'axis',
        position: function (pt) {
            return [pt[0], '10%'];
        }
    },
    title: {
        left: 'center',
        text: '已成交订单趋势图',
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: orderTime
    },
    yAxis: {
        type: 'value',
        boundaryGap: [0, '100%']
    },
    series: [
        {
            name:'订单数',
            type:'line',
            smooth:true,
            symbol: 'none',
            sampling: 'average',
            itemStyle: {
                normal: {
                    color: 'rgb(255, 70, 131)'
                }
            },
            areaStyle: {
                normal: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0,
                        color: 'rgb(255, 158, 68)'
                    }, {
                        offset: 1,
                        color: 'rgb(255, 70, 131)'
                    }])
                }
            },
            data: orderData
        }
    ]
						        };	
						        // 使用刚指定的配置项和数据显示图表。
						        user.setOption(userOpt);
						        order.setOption(orderOpt)
						        
						    </script>

						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->


		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse"> <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$(top.hangge());
	</script>
	<script type="text/javascript" src="static/ace/js/jquery.js"></script>
</body>
</html>