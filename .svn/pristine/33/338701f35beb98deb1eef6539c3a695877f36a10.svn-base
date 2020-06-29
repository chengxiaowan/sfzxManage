const config = {
	isKhGh: yo.getQueryString("isKhGh") || 0, //0,客户公海,2潜在客户,3有意向公海,1无意向公海,4大客户公海
	role: localStorage.userRole,
	api_detail: '/mainIndex/showYzjbDetatil', //商务顾问业绩目标完成情况
	time: yo.getQueryString("time"),
	id: yo.getQueryString("id"),
	saleName: yo.getQueryString("saleName"),
	api_user: '/user/getUserInfo',
}

if(config.role == '销售精英' || config.role == '经理') {
	config.isKhGh = 4
}

var app = {};
var option = null;
option = {
	title: {
		text: '',
		textStyle: {
			//文字颜色
			color: '#3a3a3a',
			//字体风格,'normal','italic','oblique'
			fontStyle: 'normal',
			//字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
			//fontWeight: 'bold',
			//字体大小
			fontSize: 16
		}
	},
	tooltip: {
		trigger: 'axis'
	},
	legend: {
		data: ['商务顾问业绩目标完成情况']
	},
	xAxis: {
		type: 'category',
		data: []
	},
	yAxis: {
		type: 'value',
		max: ''
	},
	series: [{
		data: [],
		type: 'line',
		symbol: 'circle',
		symbolSize: 6,
		areaStyle: {
			normal: {
				color: new echarts.graphic.LinearGradient(
					0, 0, 0, 1, [{
							offset: 0,
							color: '#d9ebfd'
						},
						{
							offset: 0.5,
							color: '#f6fafe'
						},
						{
							offset: 0.8,
							color: '#ffffff'
						}
					]
				)
			}
		},
		itemStyle: {
			normal: {
				color: '#459df5'
			}
		},
	}]
};

//商务顾问环形进度1


var pieChartOption = {
title: {
		//              text: '业务指标', //标题文本内容
	},
	toolbox: { //可视化的工具箱
		show: false,
		feature: {
			restore: { //重置
				show: false
			},
			saveAsImage: { //保存图片
				show: false
			}
		}
	},
	tooltip: { //弹窗组件
		formatter: "{a} <br/>{b} : {c}%"
	},
	series: [{
		name: '签约客户数',
		center: ["50%", "60%"], // 仪表位置
		radius: "90%", //仪表大小
		axisLine: { // 坐标轴线
			lineStyle: { // 属性lineStyle控制线条样式
				color: [
					[0.3, '#ff878e'],
					[0.8, '#ffce89'],
					[1, '#5bd4d1']
				],
				width: 10
			}
		},
		axisTick: { // 坐标轴小标记
			length: 15, // 属性length控制线长
			lineStyle: { // 属性lineStyle控制线条样式
				color: 'auto'
			}
		},
		splitLine: { // 分隔线
			length: 20, // 属性length控制线长
			lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
				color: 'auto'
			}
		},
		pointer: {
			width: 3
		},
		type: 'gauge',
		z: 3,
		min: 0,
		max: 220,
		splitNumber: 10,
		detail: {
			formatter: '{value}%',
		},
		data: [{
			value: 45,
			name: '签约客户数',
			label: {
				textStyle: {
					fontSize: 16
				}
			}
		}]
	}]
};

require(['select2'], (select2) => {
	window.app = new Vue({
		el: '#app',
		data: {
			dxmonth: config.time,
			dx: {
				dkje: '',
				mbdkje: '',
				list: [],
				gxtime: '',
			},
			saleId: config.id,
			listId: []
		},
		watch: {
			'dxmonth': function(val) {
				this.getYjmb();
			},
			'saleId': function(val) {
				this.getYjmb();
			},
		},

		computed: {

		},
		created() {
			const that = this;
			document.getElementById("app").classList.remove("hide");
		},

		mounted() {
			const that = this
			//电销独有
			laydate.render({
				elem: '#dxmonth',
				type: 'year',
				done: function(value, date) {
					that.dxmonth = value;
				}
			});

			this.getYjmb(); //商务顾问
			this.getUser()
		},

		methods: {
			/**
			 * 载入中方法
			 * 
			 * @param {string} s 是否关闭
			 */
			loading(s) {
				if(s == "close") layer.close(this.loadingSwitch)
				else this.loadingSwitch = layer.load(3);
				// else this.loadingSwitch = layer.load(3, { shade: [.6, "#fff"] });
			},
			getUser() {
				const that = this;
				that.loading();
				$.ajax({
					type: "post",
					url: config.api_user,
					async: true,
					data: {
						ROLE_ID: '02178e62f17b4926bb7014f3ad5a1ebe'
					},
					success: function(res) {
						that.loading('close')
						that.listId = res
					}
				});
			},
			getYjmb() {
				const that = this
				var startTime = that.dxmonth + '-04-01';
				var endTime = Number(that.dxmonth) + 1 + '-03-31';
				var hhd, hhd1;
				$.ajax({
					type: 'post',
					url: config.api_detail,
					async: true,
					data: {
						startTime: startTime,
						endTime: endTime,
						runnerId: that.saleId
					},
					success: function(res) {
						that.loading('close')
						if(res.error == "00") {
							var list = [];
							var list1 = [];
							var dkje = 0;
							var mbdkje = 0;
							for(var i = 0; i < res.result.length; i++) {
								var el = res.result[i];
								dkje += Number(res.result[i].dkje)
								mbdkje += Number(res.result[i].mbdkje)
								list.push(el.time);
								list1.push(el.dkje)
							}
							that.dx.dkje = dkje;
							that.dx.mbdkje = mbdkje;
							hhd = Math.round(dkje / mbdkje * 10000) / 100.00
							if(hhd == 'Infinity') {
								hhd = 100
							} else if(isNaN(hhd)) {
								hhd = 0
							}
							if(hhd>=100)hhd=100

							hhd1 = hhd + "%"
							that.dxhx(hhd, hhd1)
							var max = Number(Math.max.apply(null, list1));
							max = (max + max * 0.2)
							var myChart = echarts.init(document.getElementById('main'));
							option.xAxis.data = list;
							option.yAxis.max = Math.ceil(max);
							option.series[0].data = list1;
							myChart.setOption(option);
							myChart.off("click");
							myChart.on('click', function(params) {
								var gxtime = res.result[params.dataIndex].time;
								var html = '/static/page/index/saler_yunzuo_list.html?gxtime=' + gxtime + '&saleId=' + that.saleId;
								var index = layer.open({
									type: 2,
									title: '运作到款金额明细',
									content: html,
									area: ['100%', '100%']
								});
							})
						} else {
							layer.msg(res.msg)
						}
					}
				});
			},

			//环形1
			dxhx(hhd, hhd1) {
				//环形1
				const that = this
				pieChartOption.series[0].max = '100'
//				pieChartOption.series[0].max = that.dx.mbdkje || 0
				pieChartOption.series[0].data[0].value = hhd
				var asd = document.getElementById('pie');
				var pieChart = echarts.init(asd);
				pieChart.setOption(pieChartOption);
				pieChart.off("click");
				pieChart.on("click", function(params) {
					var html = '/static/page/index/saler_yunzuo_list1.html?gxtime=' + that.dxmonth + '&saleId=' + that.saleId;
					var index = layer.open({
						type: 2,
						title: '运作到款金额明细',
						content: html,
						area: ['100%', '100%']
					});

				});

			},

			//获取当前月
			nowMonth() {
				var date = new Date;
				var year = date.getFullYear();
				var month = date.getMonth() + 1;
				month = (month < 10 ? "0" + month : month);
				var nowDate = (year.toString() + '-' + month.toString());
				return nowDate;
			}
		},
	})

})
//子页面处理方法
window.parentFnc = function(params) {
	layer.closeAll();
	//更新记录
	window.app.getCaseRecord(window.app.type)
}