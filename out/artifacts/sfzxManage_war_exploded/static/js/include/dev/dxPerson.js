const config = {
	isKhGh: yo.getQueryString("isKhGh") || 0, //0,客户公海,2潜在客户,3有意向公海,1无意向公海,4大客户公海
	role: localStorage.userRole,
	api_detail: '/main/showdfSaleProcessDetail.do', //电销业绩目标完成情况
	time: yo.getQueryString("time"),
	id: yo.getQueryString("id"),
	saleName: yo.getQueryString("saleName"),
	api_user: '/user/getUserInfo',
}

if(config.role == '销售精英' || config.role == '经理') {
	config.isKhGh = 4
}

//电销独自拥有 高端大气上档次
//电销业绩目标完成情况
var dxOnly = {
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
			fontSize: 18
		}
	},
	tooltip: {
		trigger: 'axis'
	},
	legend: {
		data: ['电话量', '意向客户数']
	},
	grid: {
		x: 40,
		x2: 40
	},
	xAxis: [{
		type: 'category',
		data: ['四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月', '一月', '二月', '三月', ]
	}],
	yAxis: [{
			type: 'value',
			name: '电话量',
			position: 'left',
		},
		{
			type: 'value',
			name: '意向客户数',
			position: 'right',
		},
	],
	series: [{
			name: '电话量',
			type: 'bar',
			data: [],
			barWidth: 10, //柱图宽度
			itemStyle: {
				normal: {
					color: '#459df5'
				}
			},
		},
		{
			name: '意向客户数',
			type: 'bar',
			yAxisIndex: 1,
			data: [],
			barWidth: 10, //柱图宽度
			itemStyle: {
				normal: {
					color: '#53c1a6'
				}
			},
		},
	]
}
//电销环形进度1

var pieChartOption = {
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
		name: '电话量',
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
			name: '电话量',
			label: {
				textStyle: {
					fontSize: 16
				}
			}
		}]
	}]
};

//电销环形进度2

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
		name: '意向客户数',
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
			name: '意向客户数',
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
			dxmonth: config.time.split('-')[0], //电销独有
			dx: {
				dhl: '',
				mbdhl: '',
				yxkhs: '',
				mbyxkhs: '',
				list: []
			},
			saleId: config.id,
			listId: [],
			idchange: '',
			sjbt: '',
			a:'',
			b:'',
			c:'',
			d:'',
		},
		watch: {
			'dxmonth': function(val) {
				this.getDataDx(val, this.saleId, 1);
			},
			'saleId': function(val) {
				this.getDataDx(this.dxmonth, val, 2);
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

			this.getDataDx(this.dxmonth, config.id); //电销独有
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
						ROLE_ID: '7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98'
					},
					success: function(res) {
						that.loading('close')
						that.listId = res
					}
				});
			},
			//电销独有————电销业绩目标完成情况
			getDataDx: function(time, saleId, ischange) {
				var that = this;
				var nowDate = that.nowMonth()
				var hhd, hhd1, mmd, mmd1;
				var nextTime = nowDate;
				that.loading();
				$.ajax({
					url: config.api_detail,
					async: true,
					data: {
						startTime: time + '-04-01',
						endTime: Number(time) + 1 + '-03-31',
						saleId: saleId
					},
					success: function(res) {
						that.loading('close')
						if(res.error == "00") {
							that.dx.list = res.result
							var list1 = [];
							var list2 = [];
							for(var i = 0; i < res.result.length; i++) {
								var el = res.result[i];
								if(el.mbyxkhs == 0){
									el.mbyxkhs='0,0'
								}
								list1.push(el.dhl)
								list2.push(el.yxkhs||0)
							}
							if(ischange == undefined) {
								for(var i = 0; i < res.result.length; i++) {
									var el = res.result[i];
									if(nowDate == el.time) {
										that.idchange = i;
										that.dx.dhl = el.dhl || 0;
										that.dx.mbdhl = el.mbdhl || 0;
										that.dx.yxkhs = el.yxkhs || 0;
										that.dx.mbyxkhs = el.mbyxkhs .split(',')[0]|| 0;
										hhd = Math.round(el.dhl / el.mbdhl * 10000) / 100.00
										if(hhd == 'Infinity') {
											hhd = 100
										} else if(isNaN(hhd)) {
											hhd = 0
										}
										if(hhd >= 100) {
											hhd = 100
										}
										hhd1 = hhd + "%"

										mmd = Math.round(el.yxkhs / el.mbyxkhs.split(',')[0] * 10000) / 100.00
										if(mmd == 'Infinity') {
											mmd = 100
										} else if(isNaN(mmd)) {
											mmd = 0
										}
										if(mmd >= 100) {
											mmd = 100
										}
										mmd1 = mmd + "%"
										that.dxhx(hhd, hhd1, mmd, mmd1, nextTime, saleId);

									}
								}
							} else if(ischange == 1 || ischange == 2) {
								var qTime = res.result[that.idchange].time
								that.dx.dhl = res.result[that.idchange].dhl || 0;
								that.dx.mbdhl = res.result[that.idchange].mbdhl || 0;
								that.dx.yxkhs = res.result[that.idchange].yxkhs || 0;
								that.dx.mbyxkhs = res.result[that.idchange].mbyxkhs.split(',')[0] || 0;
								hhd = Math.round(that.dx.dhl / that.dx.mbdhl * 10000) / 100.00
								if(hhd == 'Infinity') {
									hhd = 100
								} else if(isNaN(hhd)) {
									hhd = 0
								}
								if(hhd >= 100) {
											hhd = 100
										}
								hhd1 = hhd + "%"

								mmd = Math.round(that.dx.yxkhs / that.dx.mbyxkhs.split(',')[0] * 10000) / 100.00
								if(mmd == 'Infinity') {
									mmd = 100
								} else if(isNaN(mmd)) {
									mmd = 0
								}if(mmd >= 100) {
											mmd = 100
										}
								mmd1 = mmd + "%"
								that.dxhx(hhd, hhd1, mmd, mmd1, qTime, saleId);
							}

							var max = Number(Math.max.apply(null, list1));
							max = (max + max * 0.2)
							var max1 = Number(Math.max.apply(null, list2));
							max1 = (max1 + max1)
							var myChartDx = echarts.init(document.getElementById('dxOnly'));
							dxOnly.series[0].data = list1;
							dxOnly.series[1].data = list2;
							dxOnly.yAxis[0].max = Math.ceil(max);
							dxOnly.yAxis[1].max = Math.ceil(max1);
							myChartDx.setOption(dxOnly);
							myChartDx.off("click");
							myChartDx.on("click", function(params) {
								if(params.seriesIndex == 0 || params.seriesIndex == 1) {
									nextTime = that.dx.list[params.dataIndex].time
									var shuju = that.dx.list[params.dataIndex];
									that.dx.dhl = shuju.dhl || 0;
									that.dx.mbdhl = shuju.mbdhl || 0;
									that.dx.yxkhs = shuju.yxkhs || 0;
									that.dx.mbyxkhs = shuju.mbyxkhs.split(',')[0] || 0;
									hhd = Math.round(shuju.dhl / shuju.mbdhl * 10000) / 100.00
									console.log(hhd)
									if(hhd == 'Infinity') {
										hhd = 100
									} else if(isNaN(hhd)) {
										hhd = 0
									}
									if(hhd >= 100) {
											hhd = 100
										}
									hhd1 = hhd + "%"
									mmd = Math.round(shuju.yxkhs / shuju.mbyxkhs.split(',')[0] * 10000) / 100.00;
									if(mmd == 'Infinity') {
										mmd = 100
									} else if(isNaN(mmd)) {
										mmd = 0
									}
									if(mmd >= 100) {
											mmd = 100
										}
									mmd1 = mmd + "%"

									that.dxhx(hhd, hhd1, mmd, mmd1, nextTime, saleId);
								}
							})

						} else {
							layer.msg(res.msg)
						}
					}
				});

			},
			//电销环形
			dxhx(hhd, hhd1, mmd, mmd1, nextTime, id) {
				const that = this;
				var n = nextTime.split('-');
				var year = n[0];
				var month = n[1];
				var d = new Date(year, month, 0);
				var day = d.getDate();
				t0 = nextTime + '-01';
				t1 = nextTime + '-' + day
				//环形1
				pieChartOption.series[0].max = '100'
//				pieChartOption.series[0].max = that.dx.mbdhl || 0
				pieChartOption.series[0].data[0].value = hhd
				
//				pieChartOption.series[0].data[0].name = hhd1
//				pieChartOption.series[0].data[0].value = hhd
//				pieChartOption.series[0].data[1].value = 100 - hhd
				pieChartOption.title.text = nextTime + '月份';
				var asd = document.getElementById('pie');
				var pieChart = echarts.init(asd);
				that.a = t0
				that.b = t1
				that.c = id
				that.d = config.saleName
				pieChart.setOption(pieChartOption);
				pieChart.off("click");
				pieChart.on("click", function(params) {
					var html = '/static/page/index/saler_follow_list.html?startTime=' + t0 + '&endTime=' + t1 + '&type=1' + '&saleId=' + id + '&saleName=' + config.saleName;
					var index = layer.open({
						type: 2,
						title: '电话量',
						content: html,
						area: ['100%', '100%']
					});

				});

				//环形2
				pieChartOption2.series[0].max = '100'
//				pieChartOption2.series[0].max = that.dx.mbyxkhs.split(',')[0] || 0
				pieChartOption2.series[0].data[0].value = mmd
//				pieChartOption2.series[0].data[0].name = mmd1
//				pieChartOption2.series[0].data[0].value = mmd
//				pieChartOption2.series[0].data[1].value = 100 - mmd
				pieChartOption2.title.text = nextTime + '月份';
				var asd2 = document.getElementById('pie2');
				var pieChart2 = echarts.init(asd2);
				pieChart2.setOption(pieChartOption2);
				pieChart2.off("click");
				pieChart2.on("click", function(params) {
					var index = layer.open({
						type: 2,
						title: '意向客户数',
						content: '/static/page/index/saler_customer_list.html?startTime=' + t0 + '&endTime=' + t1 + '&saleId=' + id + '&saleName=' + config.saleName,
						area: ['100%', '100%']
					});
				});
			},
			dx_location(type){
				let that = this
				if(type=='1'){
					var html = '/static/page/index/saler_follow_list.html?startTime=' + that.a + '&endTime=' + that.b + '&type=1' + '&saleId=' + that.c + '&saleName=' + that.d;
					var index = layer.open({
						type: 2,
						title: '电话量',
						content: html,
						area: ['100%', '100%']
					});
				}else{
					var index = layer.open({
						type: 2,
						title: '查看详情',
						content: '/static/page/index/saler_customer_list.html?startTime=' + that.a + '&endTime=' + that.b + '&saleId=' + that.c + '&saleName=' + that.d,
						area: ['100%', '100%']
					});
				}
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