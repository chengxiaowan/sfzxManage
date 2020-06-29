const config = {
	isKhGh: yo.getQueryString("isKhGh") || 0, //0,客户公海,2潜在客户,3有意向公海,1无意向公海,4大客户公海
	role: localStorage.userRole,
	api_detail: '/mainIndex/showDxSwgwXstjDetail', //商务顾问业绩目标完成情况
	time: yo.getQueryString("time"),
	id: yo.getQueryString("id"),
	saleName: yo.getQueryString("saleName"),
	api_user: '/user/getUserInfo',
	jidu: yo.getQueryString("jidu"),
	type: yo.getQueryString("type")
}

if(config.role == '销售精英' || config.role == '经理') {
	config.isKhGh = 4
}

var app2 = {};
var app3 = {};
var app4 = {};
var option2 = null;
var option3 = null;
var option4 = null;
option2 = {
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
option3 = {
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
option4 = {
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

//商务顾问环形进度2

var pieChartOption2 = {
	title: {
		x:'center'
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

//商务顾问环形进度3

var pieChartOption3 = {
	title: {
		x:'center'
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
		name: '订单金额',
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
			name: '订单金额',
			label: {
				textStyle: {
					fontSize: 16
				}
			}
		}]
	}]
};

//商务顾问环形进度4

var pieChartOption4 = {
	title: {
		x:'center'
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
		name: '拜访量',
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
			name: '拜访量',
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
			dxmonth: new Date().getFullYear().toString(),
			dxmonth2: config.time.split('-')[0],
			dxmonth3: config.time.split('-')[0],
			dxmonth4: config.time.split('-')[0],
			dx: {
				bfl: '',
				mbbfl: '',
				khs: '',
				mbkhs: '',
				ddje: '',
				mbddje: '',
				list: [],
				list2: [],
				list3: [],
				list4: [],
				idchange2: '',
				idchange3: '',
				idchange4: '',
				gxtime2: '',
				gxtime3: '',
				gxtime4: '',
			},
			saleId: config.id,
			saleId2: config.id,
			saleId3: config.id,
			saleId4: config.id,
			listId: [],
			a1: '', //khs
			b1: '',
			c1: '',
			d1: '',
			a2: '', //ddje
			b2: '',
			c2: '',
			d2: '',
			a3: '', //bfl
			b3: '',
			c3: '',
			d3: '',
		},
		watch: {
			'dxmonth2': function(val) {
				this.getYjmb2(this.dxmonth2, 0, this.saleId2, 1); //最后的1为了控制时间切换时进度盘更新
			},
			'saleId2': function(val) {
				this.getYjmb2(this.dxmonth2, 0, this.saleId2, 2); //最后的2为了控制人员切换时进度盘跟着切换
			},
			'dxmonth3': function(val) {
				this.getYjmb3(this.dxmonth3, 1, this.saleId3, 1);
			},
			'saleId3': function(val) {
				this.getYjmb3(this.dxmonth3, 1, this.saleId3, 2);
			},
			'dxmonth4': function(val) {
				this.getYjmb4(this.dxmonth4, 2, this.saleId4, 1);
			},
			'saleId4': function(val) {
				this.getYjmb4(this.dxmonth4, 2, this.saleId4, 2);
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
				elem: '#dxmonth2',
				type: 'year',
				done: function(value, date) {
					that.dxmonth2 = value;
				}
			}); //电销商务顾问拜访量
			laydate.render({
				elem: '#dxmonth3',
				type: 'year',
				done: function(value, date) {
					that.dxmonth3 = value;
				}
			}); //电销商务顾问订单金额
			laydate.render({
				elem: '#dxmonth4',
				type: 'year',
				done: function(value, date) {
					that.dxmonth4 = value;
				}
			}); //电销商务顾问拜访量
			this.getYjmb2(that.dxmonth2, 0, config.id); //商务顾问签约客户数
			this.getYjmb3(that.dxmonth3, 1, config.id); //电销商务顾问订单金额
			this.getYjmb4(that.dxmonth4, 2, config.id); //电销商务顾问拜访量
			this.getUser()
			this.$nextTick(function(){
				var divHtml = $('#'+config.type+'');
				$('#'+config.type+'').remove()
				$(".c-content").prepend(divHtml)
			})
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
						ROLE_ID: '90564dd8b75a4f6d815ce418462d401c'
					},
					success: function(res) {
						that.loading('close')
						that.listId = res
					}
				});
			},

			getYjmb4(time, type, saleId, ischange) {
				const that = this
				var nowDate = that.nowMonth()
				var isMonth = nowDate.split("-")[1]
				var isdate = new Date;
				var isYear = isdate.getFullYear();
				var startTime, endTime;

				if(isYear == time) {
					if(isMonth == '01' || isMonth == '02' || isMonth == '03') {
						startTime = Number(time) - 1 + '-04-01';
						endTime = time + '-03-31';
					} else {
						startTime = time + '-04-01';
						endTime = Number(time) + 1 + '-03-31';
					}
				} else {
					startTime = time + '-04-01';
					endTime = Number(time) + 1 + '-03-31';
				}


				var hhd, hhd1;
				$.ajax({
					type: 'post',
					url: config.api_detail,
					async: true,
					data: {
						startTime: startTime,
						endTime: endTime,
						type: type,
						saleId: saleId
					},
					success: function(res) {
						that.loading('close')
						if(res.error == "00") {
							var list = [];
							var list1 = [];
							for(var i = 0; i < res.result.length; i++) {
								var el = res.result[i];
								list.push(el.name);
								list1.push(el.bfl)
							}
							if(ischange == undefined) {
								for(var i = 0; i < res.result.length; i++) {
									if(config.jidu == res.result[i].time) {
										that.dx.gxtime4 = res.result[i].time
										that.dx.idchange4 = i
										that.dx.bfl = res.result[i].bfl || 0;
										that.dx.mbbfl = res.result[i].mbbfl || 0;
										hhd = Math.round(that.dx.bfl / that.dx.mbbfl * 10000) / 100.00
										if(hhd == 'Infinity') {
											hhd = 100
										} else if(isNaN(hhd)) {
											hhd = 0
										}
										if(hhd >= 100) hhd = 100
									}
								}
								hhd1 = hhd + "%"
								that.dxhx4(hhd, hhd1, that.dx.gxtime4)
							} else if(ischange == 1) {
								that.dx.gxtime4 = res.result[that.dx.idchange4].time
								that.dx.bfl = res.result[that.dx.idchange4].bfl || 0;
								that.dx.mbbfl = res.result[that.dx.idchange4].mbbfl || 0;
								hhd = Math.round(that.dx.bfl / that.dx.mbbfl * 10000) / 100.00
								if(hhd == 'Infinity') {
									hhd = 100
								} else if(isNaN(hhd)) {
									hhd = 0
								}
								if(hhd >= 100) hhd = 100
								hhd1 = hhd + "%"
								that.dxhx4(hhd, hhd1, that.dx.gxtime4)
							} else if(ischange == 2) {
								that.dx.gxtime4 = res.result[that.dx.idchange4].time
								that.dx.bfl = res.result[that.dx.idchange4].bfl || 0;
								that.dx.mbbfl = res.result[that.dx.idchange4].mbbfl || 0;
								hhd = Math.round(that.dx.bfl / that.dx.mbbfl * 10000) / 100.00
								if(hhd == 'Infinity') {
									hhd = 100
								} else if(isNaN(hhd)) {
									hhd = 0
								}
								if(hhd >= 100) hhd = 100
								hhd1 = hhd + "%"
								that.dxhx4(hhd, hhd1, that.dx.gxtime4)
							}
							var max = Number(Math.max.apply(null, list1));
							max = (max + max * 0.2)
							var myChart = echarts.init(document.getElementById('main4'));
							option4.xAxis.data = ['四月份', '五月份', '六月份', '七月份', '八月份', '九月份', '十月份', '十一月份', '十二月份', '一月份', '二月份', '三月份'];
							option4.yAxis.max = Math.ceil(max);
							option4.series[0].data = list1;
							myChart.setOption(option4);
							myChart.off("click");
							myChart.on('click', function(params) {
								that.dx.idchange4 = params.dataIndex
								that.dx.gxtime4 = res.result[params.dataIndex].time
								that.dx.bfl = res.result[params.dataIndex].bfl || 0;
								that.dx.mbbfl = res.result[params.dataIndex].mbbfl || 0;

								hhd = Math.round(that.dx.bfl / that.dx.mbbfl * 10000) / 100.00
								if(hhd == 'Infinity') {
									hhd = 100
								} else if(isNaN(hhd)) {
									hhd = 0
								}
								if(hhd >= 100) hhd = 100
								hhd1 = hhd + "%"
								that.dxhx4(hhd, hhd1, that.dx.gxtime4)
							})
						} else {
							layer.msg(res.msg)
						}
					}
				});
			},

			getYjmb2(time, type, saleId, ischange) {
				const that = this
				var nowDate = that.nowMonth()
				var isMonth = nowDate.split("-")[1]
				var isdate = new Date;
				var isYear = isdate.getFullYear();
				var startTime, endTime;

				if(isYear == time) {
					if(isMonth == '01' || isMonth == '02' || isMonth == '03') {
						startTime = Number(time) - 1 + '-04-01';
						endTime = time + '-03-31';
					} else {
						startTime = time + '-04-01';
						endTime = Number(time) + 1 + '-03-31';
					}
				} else {
					startTime = time + '-04-01';
					endTime = Number(time) + 1 + '-03-31';
				}
				
				var hhd, hhd1;
				$.ajax({
					type: 'post',
					url: config.api_detail,
					async: true,
					data: {
						startTime: startTime,
						endTime: endTime,
						type: type,
						saleId: saleId
					},
					success: function(res) {
						that.loading('close')
						if(res.error == "00") {
							var list = [];
							var list1 = [];
							for(var i = 0; i < res.result.length; i++) {
								var el = res.result[i];
								list.push(el.name);
								list1.push(el.khs)
							}
							if(ischange == undefined) {
								for(var i = 0; i < res.result.length; i++) {
									if(config.jidu == res.result[i].time) {
										that.dx.gxtime2 = res.result[i].time
										that.dx.idchange2 = i
										that.dx.khs = res.result[i].khs || 0;
										that.dx.mbkhs = res.result[i].mbkhs || 0;
										hhd = Math.round(that.dx.khs / that.dx.mbkhs * 10000) / 100.00
										if(hhd == 'Infinity') {
											hhd = 100
										} else if(isNaN(hhd)) {
											hhd = 0
										}
										if(hhd >= 100) hhd = 100
									}
								}
								hhd1 = hhd + "%"
								that.dxhx2(hhd, hhd1, that.dx.gxtime2)
							} else if(ischange == 1) {
								that.dx.gxtime2 = res.result[that.dx.idchange2].time
								that.dx.khs = res.result[that.dx.idchange2].khs || 0;
								that.dx.mbkhs = res.result[that.dx.idchange2].mbkhs || 0;
								hhd = Math.round(that.dx.khs / that.dx.mbkhs * 10000) / 100.00
								if(hhd == 'Infinity') {
									hhd = 100
								} else if(isNaN(hhd)) {
									hhd = 0
								}
								if(hhd >= 100) hhd = 100
								hhd1 = hhd + "%"
								that.dxhx2(hhd, hhd1, that.dx.gxtime2)
							} else if(ischange == 2) {
								that.dx.gxtime2 = res.result[that.dx.idchange2].time
								that.dx.khs = res.result[that.dx.idchange2].khs || 0;
								that.dx.mbkhs = res.result[that.dx.idchange2].mbkhs || 0;
								hhd = Math.round(that.dx.khs / that.dx.mbkhs * 10000) / 100.00
								if(hhd == 'Infinity') {
									hhd = 100
								} else if(isNaN(hhd)) {
									hhd = 0
								}
								if(hhd >= 100) hhd = 100
								hhd1 = hhd + "%"
								that.dxhx2(hhd, hhd1, that.dx.gxtime2)
							}

							var max = Number(Math.max.apply(null, list1));
							max = (max + max * 0.2)
							var myChart = echarts.init(document.getElementById('main2'));
							option2.xAxis.data = ['四月份', '五月份', '六月份', '七月份', '八月份', '九月份', '十月份', '十一月份', '十二月份', '一月份', '二月份', '三月份'];
							option2.yAxis.max = Math.ceil(max);
							option2.series[0].data = list1;
							myChart.setOption(option2);
							myChart.off("click");
							myChart.on('click', function(params) {
								that.dx.gxtime2 = res.result[params.dataIndex].time
								that.dx.idchange2 = params.dataIndex
								that.dx.khs = res.result[params.dataIndex].khs || 0;
								that.dx.mbkhs = res.result[params.dataIndex].mbkhs || 0;
								hhd = Math.round(that.dx.khs / that.dx.mbkhs * 10000) / 100.00
								if(hhd == 'Infinity') {
									hhd = 100
								} else if(isNaN(hhd)) {
									hhd = 0
								}
								if(hhd >= 100) hhd = 100
								hhd1 = hhd + "%"
								that.dxhx2(hhd, hhd1, that.dx.gxtime2)
							})
						} else {
							layer.msg(res.msg)
						}
					}
				});
			},

			getYjmb3(time, type, saleId, ischange) {
				const that = this
				var nowDate = that.nowMonth()
				var isMonth = nowDate.split("-")[1]
				var isdate = new Date;
				var isYear = isdate.getFullYear();
				var startTime, endTime;

				if(isYear == time) {
					if(isMonth == '01' || isMonth == '02' || isMonth == '03') {
						startTime = Number(time) - 1 + '-04-01';
						endTime = time + '-03-31';
					} else {
						startTime = time + '-04-01';
						endTime = Number(time) + 1 + '-03-31';
					}
				} else {
					startTime = time + '-04-01';
					endTime = Number(time) + 1 + '-03-31';
				}
				var hhd, hhd1;
				$.ajax({
					type: 'post',
					url: config.api_detail,
					async: true,
					data: {
						startTime: startTime,
						endTime: endTime,
						type: type,
						saleId: saleId
					},
					success: function(res) {
						that.loading('close')
						if(res.error == "00") {
							var list = [];
							var list1 = [];
							for(var i = 0; i < res.result.length; i++) {
								var el = res.result[i];
								list.push(el.name);
								list1.push(el.ddje)
							}
							if(ischange == undefined) {
								if(config.jidu == 0 || config.jidu == 1 || config.jidu == 2 || config.jidu == 3) {
									for(var i = 0; i < res.result.length; i++) {
										if(nowDate == res.result[i].time) {
											that.dx.gxtime3 = res.result[i].time
											that.dx.idchange3 = i
											that.dx.ddje = res.result[i].ddje || 0;
											that.dx.mbddje = res.result[i].mbddje || 0;
											hhd = Math.round(that.dx.ddje / that.dx.mbddje * 10000) / 100.00
											if(hhd == 'Infinity') {
												hhd = 100
											} else if(isNaN(hhd)) {
												hhd = 0
											}
											if(hhd >= 100) hhd = 100
										}
									}

								} else {
									for(var i = 0; i < res.result.length; i++) {
										if(config.jidu == res.result[i].time) {
											that.dx.gxtime3 = res.result[i].time
											that.dx.idchange3 = i
											that.dx.ddje = res.result[i].ddje || 0;
											that.dx.mbddje = res.result[i].mbddje || 0;
											hhd = Math.round(that.dx.ddje / that.dx.mbddje * 10000) / 100.00
											if(hhd == 'Infinity') {
												hhd = 100
											} else if(isNaN(hhd)) {
												hhd = 0
											}
											if(hhd >= 100) hhd = 100
										}
									}
								}
								hhd1 = hhd + "%"
								that.dxhx3(hhd, hhd1, that.dx.gxtime3)
							} else if(ischange == 1) {
								that.dx.gxtime3 = res.result[that.dx.idchange3].time
								that.dx.ddje = res.result[that.dx.idchange3].ddje || 0;
								that.dx.mbddje = res.result[that.dx.idchange3].mbddje || 0;
								hhd = Math.round(that.dx.ddje / that.dx.mbddje * 10000) / 100.00
								if(hhd == 'Infinity') {
									hhd = 100
								} else if(isNaN(hhd)) {
									hhd = 0
								}
								if(hhd >= 100) hhd = 100
								hhd1 = hhd + "%"
								that.dxhx3(hhd, hhd1, that.dx.gxtime3)
							} else if(ischange == 2) {
								that.dx.gxtime3 = res.result[that.dx.idchange3].time
								that.dx.ddje = res.result[that.dx.idchange3].ddje || 0;
								that.dx.mbddje = res.result[that.dx.idchange3].mbddje || 0;
								hhd = Math.round(that.dx.ddje / that.dx.mbddje * 10000) / 100.00
								if(hhd == 'Infinity') {
									hhd = 100
								} else if(isNaN(hhd)) {
									hhd = 0
								}
								if(hhd >= 100) hhd = 100
								hhd1 = hhd + "%"
								that.dxhx3(hhd, hhd1, that.dx.gxtime3)
							}
							var max = Number(Math.max.apply(null, list1));
							max = (max + max * 0.2)
							var myChart = echarts.init(document.getElementById('main3'));
							option3.xAxis.data = ['四月份', '五月份', '六月份', '七月份', '八月份', '九月份', '十月份', '十一月份', '十二月份', '一月份', '二月份', '三月份'];
							option3.yAxis.max = Math.ceil(max);
							option3.series[0].data = list1;
							myChart.setOption(option3);
							myChart.off("click");
							myChart.on('click', function(params) {
								that.dx.idchange3 = params.dataIndex
								that.dx.gxtime3 = res.result[params.dataIndex].time
								that.dx.ddje = res.result[params.dataIndex].ddje || 0;
								that.dx.mbddje = res.result[params.dataIndex].mbddje || 0;
								console.log(that.dx.ddje)
								hhd = Math.round(that.dx.ddje / that.dx.mbddje * 10000) / 100.00
								if(hhd == 'Infinity') {
									hhd = 100
								} else if(isNaN(hhd)) {
									hhd = 0
								}
								if(hhd >= 100) hhd = 100
								hhd1 = hhd + "%"
								that.dxhx3(hhd, hhd1, that.dx.gxtime3)
							})
						} else {
							layer.msg(res.msg)
						}
					}
				});
			},
			//环形4
			dxhx4(hhd, hhd1, gxtime) {
				const that = this;
				pieChartOption4.series[0].max = '100'
				//				pieChartOption4.series[0].max = that.dx.mbbfl || 0
				pieChartOption4.series[0].data[0].value = hhd
				var asd = document.getElementById('pie4');
				pieChartOption4.title.text = gxtime + '月份';
				var pieChart = echarts.init(asd);
				pieChart.setOption(pieChartOption4);
				var name = $("#saleId4 option:selected").text();
				var year = gxtime.split('-')[0];
				var month = gxtime.split('-')[1];
				var day = new Date(year, month, 0).getDate()
				var riqi = year + '-' + month + '-01';
				var riqi1 = year + '-' + month + '-' + day;
				that.a1 = name
				that.b1 = that.saleId4
				that.c1 = riqi
				that.d1 = riqi1
				if(name == '') {
					$.ajax({
						type: "post",
						url: config.api_user,
						async: true,
						data: {
							ROLE_ID: '90564dd8b75a4f6d815ce418462d401c'
						},
						success: function(res) {
							that.loading('close')
							for(var i = 0; i < res.length; i++) {
								if(config.id == res[i].USER_ID) {
									name = res[i].NAME
								}
							}
						}
					});
				}
				pieChart.off("click");
				pieChart.on("click", function(params) {
					var html = '/static/page/index/saler_bfl_list.html?saleName=' + name + "&saleId=" + that.saleId4 + "&startTime=" + riqi + "&endTime=" + riqi1
					var index = layer.open({
						type: 2,
						title: '商务顾问业绩目标-拜访量',
						content: html,
						area: ['100%', '100%']
					});

				});

			},
			//环形2
			dxhx2(hhd, hhd1, gxtime) {
				const that = this;
				pieChartOption2.series[0].max = '100'
				//				pieChartOption2.series[0].max = that.dx.mbkhs || 0
				pieChartOption2.series[0].data[0].value = hhd
				pieChartOption2.title.text = gxtime + '月份';
				var year = gxtime.split('-')[0];
				var month = gxtime.split('-')[1];
				var day = new Date(year, month, 0).getDate()
				var riqi = year + '-' + month + '-01';
				var riqi1 = year + '-' + month + '-' + day;
				var asd = document.getElementById('pie2');
				that.a2 = riqi
				that.b2 = riqi1
				that.c2 = that.saleId2
				var pieChart = echarts.init(asd);
				pieChart.setOption(pieChartOption2);
				pieChart.off("click");
				pieChart.on("click", function(params) {
					var html = '/static/page/index/saler_turnover_list.html?startTime=' + riqi + '&endTime=' + riqi1 + '&dxswgwId=' + that.saleId2 + "&flag=1";
					var index = layer.open({
						type: 2,
						title: '商务顾问业绩目标-签约客户数完成情况',
						content: html,
						area: ['100%', '100%']
					});

				});

			},
			//环形3
			dxhx3(hhd, hhd1, gxtime) {
				const that = this;
				pieChartOption3.series[0].max = '100'
				//				pieChartOption3.series[0].max = that.dx.mbddje || 0
				pieChartOption3.series[0].data[0].value = hhd
				var year = gxtime.split('-')[0];
				var month = gxtime.split('-')[1];
				var day = new Date(year, month, 0).getDate()
				var riqi = year + '-' + month + '-01';
				var riqi1 = year + '-' + month + '-' + day;
				var asd = document.getElementById('pie3');
				pieChartOption3.title.text = gxtime + '月份';
				var pieChart = echarts.init(asd);
				pieChart.setOption(pieChartOption3);
				that.a3 = riqi
				that.b3 = riqi1
				that.c3 = that.saleId3
				pieChart.off("click");
				pieChart.on("click", function(params) {
					var html = '/static/page/index/saler_turnover_list.html?startTime=' + riqi + '&endTime=' + riqi1 + '&dxswgwId=' + that.saleId3 + "&flag=2";
					var index = layer.open({
						type: 2,
						title: '商务顾问业绩目标-委案金额',
						content: html,
						area: ['100%', '100%']
					});

				});

			},
			khs(){
				let that = this
				var html = '/static/page/index/saler_turnover_list.html?startTime=' + that.a2 + '&endTime=' + that.b2 + '&dxswgwId=' + that.c2 + "&flag=1";
					var index = layer.open({
						type: 2,
						title: '商务顾问业绩目标-签约客户数完成情况',
						content: html,
						area: ['100%', '100%']
					});
			},
			ddje(){
				let that = this
				var html = '/static/page/index/saler_turnover_list.html?startTime=' + that.a3 + '&endTime=' + that.b3 + '&dxswgwId=' + that.c3 + "&flag=2";
					var index = layer.open({
						type: 2,
						title: '商务顾问业绩目标-委案金额',
						content: html,
						area: ['100%', '100%']
					});
			},
			bfl(){
				let that = this
				var html = '/static/page/index/saler_bfl_list.html?saleName=' + that.a1 + "&saleId=" + that.b1 + "&startTime=" + that.c1 + "&endTime=" + that.d1
					var index = layer.open({
						type: 2,
						title: '商务顾问业绩目标-拜访量',
						content: html,
						area: ['100%', '100%']
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