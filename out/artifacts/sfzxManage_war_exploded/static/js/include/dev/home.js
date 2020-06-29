const config = {
	isKhGh: yo.getQueryString("isKhGh") || 0, //0,客户公海,2潜在客户,3有意向公海,1无意向公海,4大客户公海
	role: localStorage.userRole,
	power: localStorage.power,
	api_xspmReport: '/report/xspmReport.do', //查询
	api_SaleYjtj: '/mainIndex/showSaleYjtj', //销售业绩统计
	api_Xstj: '/mainIndex/showSwgwXstj', //商务顾问销售统计
	api_Yjmb: '/mainIndex/showSwgwYjmb', //商务顾问业绩目标  
	api_yzjb: '/mainIndex/showYzsjdk', //运作简报 
	api_detail: '/main/showdfSaleProcessDetail.do', //电销业绩目标完成情况
	api_zhou: '/report/showReportDate.do', //周
	api_all: '/mainIndex/showAll', // 电话量、意向客户数、拜访量、签单量
	api_task: '/workBench/taskList.do', //我的任务
	api_shenhe: '/workBench/mysh.do', //我的审核
	api_wdtx: '/workBench/callList.do?flag=2', //我的提醒
	api_gqtx: '/workBench/callList.do?flag=1', //过期提醒
	api_swgw: '/mainIndex/showSwgwYjmb', //商务顾问独有
	flag: yo.getQueryString("flag"),
	api_dxswgw: '/mainIndex/showDxSwgwXstj', //电销商务顾问业绩目标  
	api_dxswgwDetail: '/mainIndex/showDxSwgwXstjDetail', //电销商务顾问业绩目标  -自己
	api_showWindow: '/mainIndex/showWindow', //业绩是否完成
	api_yz: '/mainIndex/showYzjbDetatil',
	api_num: '/main/xszs',
}

//电话量
var colors = ['#666666', '#666666'];
var option = {
	title: {
		text: '电话量',
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
		data: ['电话量', '有效电话量']
	},
	xAxis: [{
		type: 'category',
		data: ['周一', '周二', '周三', '周四', '周五']
	}],
	yAxis: [{
			type: 'value',
			name: '电话量',
			position: 'left',
			axisLine: {
				lineStyle: {
					color: colors[0]
				}
			},
		},
		{
			type: 'value',
			name: '有效电话量',
			position: 'right',
			min: 0,
			axisLine: {
				lineStyle: {
					color: colors[1]
				}
			},
		},
	],
	series: [{
			name: '电话量',
			type: 'bar',
			data: [],
			barWidth: 18, //柱图宽度
			itemStyle: {
				normal: {
					color: '#a2b9cb'
				}
			},
		},
		{
			name: '有效电话量',
			type: 'bar',
			yAxisIndex: 1,
			data: [],
			barWidth: 18, //柱图宽度
			itemStyle: {
				normal: {
					color: '#459df5'
				}
			},
		}
	]
}
//电话量跳转
function salerList(id, name, startTime, endTime, flag) {
	layer.open({
		type: 2,
		title: "合同记录详情",
		area: ["100%", "100%"],
		content: "saler_sales_detail.html?saleId=" + id + "&saleName=" + name + "&startTime=" + startTime + "&endTime=" + endTime + "&flag=" + flag
	})
}
//意向客户数
var app1 = {};
var option1 = null;
option1 = {
	title: {
		text: '意向客户数',
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
		data: ['意向客户数']
	},
	xAxis: {
		type: 'category',
		data: ['周一', '周二', '周三', '周四', '周五']
	},
	yAxis: {
		type: 'value'
	},
	series: [{
		data: [],
		type: 'line',
		symbol: 'circle',
		symbolSize: 6,
		itemStyle: {
			normal: {
				color: '#fdb54e'
			}
		},
	}]
};

//拜访量
var app2 = {};
var option2 = null;
option2 = {
	title: {
		text: '拜访量',
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
		data: ['拜访量']
	},
	xAxis: {
		type: 'category',
		data: ['周一', '周二', '周三', '周四', '周五']
	},
	yAxis: {
		type: 'value'
	},
	series: [{
		data: [820, 932, 901, 934, 1290],
		type: 'line',
		symbol: 'circle',
		symbolSize: 6,
		itemStyle: {
			normal: {
				color: '#fdb54e'
			}
		},
	}]
};

//签单量
var app3 = {};
var option3 = null;
option3 = {
	title: {
		text: '签单量',
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
		data: ['签单量']
	},
	xAxis: {
		type: 'category',
		data: ['周一', '周二', '周三', '周四', '周五']
	},
	yAxis: {
		type: 'value'
	},
	series: [{
		data: [],
		type: 'line',
		symbol: 'circle',
		symbolSize: 6,
		itemStyle: {
			normal: {
				color: '#459df5'
			}
		},
	}]
};

//商务顾问销售统计
var option4 = {
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
		data: ['已成交客户数', '订单数量', '订单总金额', '到款金额']
	},
	xAxis: [{
		type: 'category',
		data: []
	}],
	yAxis: [{
			type: 'value',
			name: '数量',
			position: 'left',

		},
		{
			type: 'value',
			name: '金额',
			position: 'right',
		},

	],
	series: [{
			name: '已成交客户数',
			type: 'bar',
			data: [],
			barWidth: 20, //柱图宽度
			itemStyle: {
				normal: {
					color: '#a2b9cb'
				}
			},
		},
		{
			name: '订单数量',
			type: 'bar',
			data: [],
			barWidth: 20, //柱图宽度
			itemStyle: {
				normal: {
					color: '#459df5'
				}
			},
		},
		{
			name: '订单总金额',
			type: 'bar',
			yAxisIndex: 1,
			data: [],
			barWidth: 20, //柱图宽度
			itemStyle: {
				normal: {
					color: '#52c1a6'
				}
			},
		},
		{
			name: '到款金额',
			type: 'bar',
			yAxisIndex: 1,
			data: [],
			barWidth: 20, //柱图宽度
			itemStyle: {
				normal: {
					color: '#fdb54e'
				}
			},
		},
	]
}

//商务顾问业绩目标完成情况
var app5 = {};
var option5 = null;
option5 = {
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
//电销商务顾问业绩目标完成情况
var app5_dx = {};
var option5_dx = null;
option5_dx = {
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
		data: ['拜访量', '签约客户数', '订单金额']
	},
	xAxis: [{
		type: 'category',
		data: []
	}],
	yAxis: [{
			type: 'value',
			name: '数量',
			position: 'left',

		},
		{
			type: 'value',
			name: '金额',
			position: 'right',
		},

	],
	series: [{
			name: '拜访量',
			type: 'bar',
			data: [],
			barWidth: 20, //柱图宽度
			itemStyle: {
				normal: {
					color: '#a2b9cb'
				}
			},
		},
		{
			name: '签约客户数',
			type: 'bar',
			data: [],
			barWidth: 20, //柱图宽度
			itemStyle: {
				normal: {
					color: '#459df5'
				}
			},
		},
		{
			name: '订单金额',
			type: 'bar',
			yAxisIndex: 1,
			data: [],
			barWidth: 20, //柱图宽度
			itemStyle: {
				normal: {
					color: '#52c1a6'
				}
			},
		},
	]
}

//电销业绩目标完成情况
var option6 = {
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
	xAxis: [{
		type: 'category',
		data: []
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
			barWidth: 20, //柱图宽度
			itemStyle: {
				normal: {
					color: '#a2b9cb'
				}
			},
		},
		{
			name: '意向客户数',
			type: 'bar',
			yAxisIndex: 1,
			data: [],
			barWidth: 20, //柱图宽度
			itemStyle: {
				normal: {
					color: '#459df5'
				}
			},
		},
	]
}

//运作简报
var app7 = {};
var option7 = null;
option7 = {
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
		data: ['运作简报']
	},
	xAxis: {
		type: 'category',
		data: []
	},
	yAxis: {
		type: 'value',
		max: '',
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

//商务顾问独有
//业绩到款

var pieChartOption_1 = {
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
		name: '业绩到款',
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
			name: '业绩到款',
			label: {
				textStyle: {
					fontSize: 16
				}
			}
		}]
	}]
};

//合同数量

var pieChartOption_2 = {
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
		name: '合同数量',
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
			name: '合同数量',
			label: {
				textStyle: {
					fontSize: 16
				}
			}
		}]
	}]
};

//委案金额

var pieChartOption_3 = {
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
		name: '委案金额',
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
			name: '委案金额',
			label: {
				textStyle: {
					fontSize: 16
				}
			}
		}]
	}]
};

//拜访量

var pieChartOption_4 = {
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

//电销商务顾问独有
//拜访量

var pieChartOption_dx1 = {
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

//签约客户数
var pieChartOption_dx2 = {
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

//订单金额

var pieChartOption_dx3 = {
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

//运作个人到款
var app = {};
var yzoption = null;
yzoption = {
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

//运作到款个人仪表盘

var pieChartOption_yz = {
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
		name: '到款金额',
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
			name: '到款金额',
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
			role: config.role,
			power: config.power,
			year: '', //销售业绩统计年份
			startyear: '', //销售简报时间
			endyear: '',
			isactive: true,
			month: '',
			monthShow: true,
			jiduShow: false,
			show: true,
			show1: false,
			show2: true,
			show3: false,
			show4: false,
			show5: false,
			c: [],
			c1: [],
			c2: [], //商务顾问业绩目标
			c4: [], //运作简报
			c_dx: [], //电销商务顾问
			year1: '',
			month1: '',
			SaleYjtj: { //销售业绩统计
				ycj: '',
				dd: '',
				ddzje: '',
				yhk: '',
				dk: ''
			},
			Salejb: { //销售简报
				ddzje: '',
				hkje: '',
				ycjkh: '',
				yjje: '',
				yxkhs: ''
			},
			postSaleYjtj: {}, //销售业绩统计参数
			postSalejb: {}, //销售简报参数
			cainian: '', //运作简报财年
			YjmbTime: '', //商务顾问业绩目标完成情况时间
			YjmbType: '0', //商务顾问业绩目标完成情况类型
			yjmbMonth: '',
			yjmbMonth_dx: '', //电销商务顾问
			qubie: false, //商务顾问月份
			qubie1: true, //商务顾问季度
			sevens: [], //周列表
			cjd: '', //all季度
			task: {
				list: [], //我的任务
			},
			shenhe: {
				list: [], //我的审核
			},
			mytx: {
				list: [], //我的提醒
			},
			gqtx: {
				list: [], //过期提醒
			},
			postDataTask: {},
			postDataShenhe: {},
			postDataMytx: {},
			postDataGqtx: {},
			dxmonth: '', //电销独有
			dx: {
				dhl: '',
				mbdhl: '',
				yxkhs: '',
				mbyxkhs: '',
				list: []
			},
			swgw: {
				ddzje: '',
				htsl: '',
				mbddzje: '',
				mbhtsl: '',
				mbyjdk: '',
				yjdk: '',
				mbbfl: '',
				bfl: ''
			},
			idchange3: '',
			dxswgw: {
				bfl: '',
				mbbfl: '',
				khs: '',
				mbkhs: '',
				ddje: '',
				mbddje: ''
			},
			yz: {
				mbdkje: 0,
				dkje: 0
			},
			yzmonth: '',
			tNum1: 0,
			tNum2: 0,
			tNum3: 0,
			yzl_time: '',
			swl_time: '',
			dxl_time: '',
			dgl_time: '',
			yzl_id: '',
			swl_id: '',
			dxl_id: '',
			dgl_id: '',
		},
		watch: {
			'year': function(val) {
				this.getSaleYjtj(val);
			},
			'startyear': function(val) {
				this.getSalejb(this.startyear, this.endyear);
			},
			'endyear': function(val) {
				this.getSalejb(this.startyear, this.endyear);
			},
			'year1': function(val) {
				this.getData1(val);
			},
			'cainian': function(val) {
				this.getCainian(val);
			},
			'YjmbTime': function(val) {
				this.getYjmb(val, this.YjmbType, 0);
			},
			'YjmbType': function(val) {
				console.log(val);
				if(val == 0) {
					this.getYjmb(this.YjmbTime, val, 0);
					this.qubie1 = true;
					this.qubie = false;
				} else {
					this.getYjmb(this.yjmbMonth, val, 1);
					this.qubie1 = false;
					this.qubie = true;
				}
			},
			'month1': function(val) {
				this.getData2(this.month1);
			},
			'month': function(val) {
				this.getData(val, 0, 0);
			},
			'dxmonth': function(val) {
				this.getDataDx(val, 1);
			},
			'yzmonth': function(val) {
				this.getDatayz(val);
			},

		},

		computed: {
			dhl: function() {
				var list = []
				for(var i = 0; i < this.c.length; i++) {
					var el = this.c[i];
					list.push(el.dhl || 0)
				}
				return list;
			},
			yxdhl: function() {
				var list = []
				for(var i = 0; i < this.c.length; i++) {
					var el = this.c[i];
					list.push(el.yxdhl || 0)
				}
				return list;
			},
			//商务顾问销售统计
			name1: function() {
				var list = []
				for(var i = 0; i < this.c1.length; i++) {
					var el = this.c1[i];
					list.push(el.name)
				}
				return list;
			},
			khsl: function() {
				var list = []
				for(var i = 0; i < this.c1.length; i++) {
					var el = this.c1[i];
					list.push(el.khsl || 0)
				}
				return list;
			},
			ddsl: function() {
				var list = []
				for(var i = 0; i < this.c1.length; i++) {
					var el = this.c1[i];
					list.push(el.ddsl || 0)
				}
				return list;
			},
			ddzje: function() {
				var list = []
				for(var i = 0; i < this.c1.length; i++) {
					var el = this.c1[i];
					list.push(el.ddzje.toFixed(2) || 0)
				}
				return list;
			},
			dkje: function() {
				var list = []
				for(var i = 0; i < this.c1.length; i++) {
					var el = this.c1[i];
					list.push(el.dkje.toFixed(2) || 0)
				}
				return list;
			},
			//电销商务顾问
			name_dx: function() {
				var list = []
				for(var i = 0; i < this.c_dx.length; i++) {
					var el = this.c_dx[i];
					list.push(el.name)
				}
				return list;
			},
			bfl_dx: function() {
				var list = []
				for(var i = 0; i < this.c_dx.length; i++) {
					var el = this.c_dx[i];
					list.push(el.bfl || 0)
				}
				return list;
			},
			ddzje_dx: function() {
				var list = []
				for(var i = 0; i < this.c_dx.length; i++) {
					var el = this.c_dx[i];
					list.push(el.ddzje.toFixed(2) || 0)
				}
				return list;
			},
			khsl_dx: function() {
				var list = []
				for(var i = 0; i < this.c_dx.length; i++) {
					var el = this.c_dx[i];
					list.push(el.khsl || 0)
				}
				return list;
			},
		},
		created() {
			const that = this;
			document.getElementById("app").classList.remove("hide");
		},

		mounted() {
			//			this.s.lastSaleName = this.select_init('[name=saleName]', '请选择员工', 1, this.kind)
			const that = this;
			that.showMonthFirstDay();
			that.year = that.FiscalYear()
			that.year1 = that.FiscalYear()
			that.cainian = that.FiscalYear()
			that.yzmonth = that.FiscalYear()
			that.dxmonth = that.FiscalYear()
			let month_now = that.nowMonth();
			let month_index = month_now.split('-')[1];
			if(month_index == '04' || month_index == '05' || month_index == '06') {
				that.YjmbTime = 0
			} else if(month_index == '07' || month_index == '08' || month_index == '09') {
				that.YjmbTime = 1
			} else if(month_index == '10' || month_index == '11' || month_index == '12') {
				that.YjmbTime = 2
			} else if(month_index == '01' || month_index == '02' || month_index == '03') {
				that.YjmbTime = 3
			}
			//销售业绩统计选择年份
			laydate.render({
				elem: '#year',
				type: 'year',
				done: function(value, date) {
					that.year = value;
				}
			});

			laydate.render({
				elem: '#year1',
				type: 'year',
				done: function(value, date) {
					that.year1 = value;
				}
			});
			var startyear = laydate.render({
				elem: '#startyear',
				max: that.endyear,
				done: function(value, date) {
					that.startyear = value;
					if(value !== '') {
						endyear.config.min.year = date.year;
						endyear.config.min.month = date.month - 1;
						endyear.config.min.date = date.date;
					} else {
						endyear.config.min.year = '';
						endyear.config.min.month = '';
						endyear.config.min.date = '';
					}
				}
			});
			var endyear = laydate.render({
				elem: '#endyear',
				min: that.startyear,
				done: function(value, date) {
					that.endyear = value;
					if(value !== '') {
						startyear.config.max.year = date.year;
						startyear.config.max.month = date.month - 1;
						startyear.config.max.date = date.date;
					} else {
						startyear.config.max.year = '';
						startyear.config.max.month = '';
						startyear.config.max.date = '';
					}
				}
			});
			laydate.render({
				elem: '#month1',
				type: 'month',
				done: function(value, date) {
					that.month1 = value;
				}
			});
			laydate.render({
				elem: '#yjmbMonth',
				type: 'month',
				done: function(value, date) {
					that.yjmbMonth = value;
					that.getYjmb(that.yjmbMonth, that.YjmbType, 1);
				}
			});

			laydate.render({
				elem: '#yjmbMonth_dx',
				type: 'month',
				done: function(value, date) {
					that.yjmbMonth_dx = value;
					that.getYjmb_dx();
				}
			});

			laydate.render({
				elem: '#cainian',
				type: 'year',
				done: function(value, date) {
					that.cainian = value;
				}
			});
			//电销独有
			laydate.render({
				elem: '#dxmonth',
				type: 'year',
				done: function(value, date) {
					that.dxmonth = value;
				}
			});
			//提醒
			laydate.render({
				elem: '#remind_time',
				type: 'datetime',
				min: that.minDate()
			});
			//运作个人
			laydate.render({
				elem: '#yzmonth',
				type: 'year',
				done: function(value, date) {
					that.yzmonth = value;
				}
			});
			this.month1 = this.nowMonth();
			this.yjmbMonth = this.nowMonth();
			this.yjmbMonth_dx = this.nowMonth();
			this.getSaleYjtj(that.year);
			this.getSalejb(this.startyear, this.endyear); //销售简报
			this.getData1(that.year1); //商务顾问销售统计初始化
			this.getData2(this.month1); //电销业绩目标完成情况
			this.getCainian(that.cainian); //运作简报
			this.getYjmb(this.YjmbTime, this.YjmbType, 0); //商务顾问业绩目标完成情况类型

			this.showWen();
			this.taskList(); //我的任务初始化
			this.shen(); //我的审核初始化
			this.tx1(); //我的提醒初始化
			this.tx2(); //过期提醒初始化
			this.getDataDx(that.dxmonth); //电销独有
			this.getDataSwgw(); //商务顾问独有
			this.getDataSwgw_dx(); //电销商务顾问独有
			//			this.loop();
			this.getYjmb_dx(); //电销商务顾问业绩目标完成情况类型

			this.showWindow(); //业绩是否完成

			this.getDatayz(that.yzmonth) //运作到款个人
			this.getNum()
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
			minDate() {
				var now = new Date();
				return now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate();
			},
			//切换数据
			changeActive(e) {
				const that = this;
				if(e == 1) {
					that.show = true;
					that.show1 = false;
					that.monthShow = true;
					that.jiduShow = false;
					that.getData(that.month, 0, 0);
				} else {
					that.show = false;
					that.show1 = true;
					that.monthShow = false;
					that.jiduShow = true;
					var date = new Date().getFullYear();
					var nowDate = that.nowMonth()
					var isMonth = nowDate.split("-")[1]
					var isdate = new Date;
					var isYear = isdate.getFullYear();
					var startTime, endTime;
					var thisYear
					if(isYear == date) {
						if(isMonth == '01' || isMonth == '02' || isMonth == '03') {
							date = date - 1
						} else {
							date = date
						}
					} else {
						date = date
					}

					that.cjd = date + '-04-01' + '~' + date + '-06-30';
					that.getData(that.cjd, 1, 1);
					that.show2 = true;
					that.show3 = false;
					that.show4 = false;
					that.show4 = false;
				}
			},
			//季度切换
			htzjd(e) {
				const that = this;
				var fbw;
				var date = new Date().getFullYear();
				var nowDate = that.nowMonth()
				var isMonth = nowDate.split("-")[1]
				var isdate = new Date;
				var isYear = isdate.getFullYear();
				var startTime, endTime;
				var thisYear
				if(isYear == date) {
					if(isMonth == '01' || isMonth == '02' || isMonth == '03') {
						date = date - 1
					} else {
						date = date
					}
				} else {
					date = date
				}
				if(e == 1) {
					that.cjd = date + '-04-01' + '~' + date + '-06-30';
					that.show2 = true;
					that.show3 = false;
					that.show4 = false;
					that.show5 = false;
					fbw = 1;
				} else if(e == 2) {
					that.cjd = date + '-07-01' + '~' + date + '-09-30';
					that.show2 = false;
					that.show3 = true;
					that.show4 = false;
					that.show4 = false;
					fbw = 2;
				} else if(e == 3) {
					that.cjd = date + '-10-01' + '~' + date + '-12-31';
					that.show2 = false;
					that.show3 = false;
					that.show4 = true;
					that.show5 = false;
					fbw = 3;
				} else if(e == 4) {
					date = Number(date) + 1
					that.cjd = date + '-01-01' + '~' + date + '-03-31';
					that.show2 = false;
					that.show3 = false;
					that.show4 = false;
					that.show5 = true;
					fbw = 4;
				}
				that.getData(that.cjd, 1, fbw);
			},
			//销售select2
			/**
			 * @param {el} param_name
			 * @param {placeholder} param_name
			 * @param {clearbtn} param_name
			 * @param {roleId} aram_name
			 */
			select_init(el, placeholder, clearbtn, kind, roleId) {
				const that = this;
				return $(el).select2({
					placeholder: placeholder || "请选择",
					language: 'zh-CN',
					allowClear: clearbtn || false,
					ajax: {
						url: "/user/getUserInfo",
						dataType: 'json',
						type: "post",
						delay: 250,
						data: function(params) {
							return {
								page: params.page || 1,
								ROLE_ID: roleId || kind,
								data: {
									q: params.term || "", // search term
								}
							};
						},
						processResults: function(data, params) {
							params.page = params.page || 1;
							config.htz_gw = data;
							$.each(data, function() {
								this.id = this.USER_ID;
								this.text = this.NAME;
							})

							return {
								results: data,
								pagination: {
									more: data.length == 10
								},
							};
						},
						cache: true
					},
					minimumInputLength: 0
				})
			},
			//销售业绩统计
			getSaleYjtj(time) {
				const that = this;
				that.loading();
				that.postSaleYjtj.time = time;
				$.ajax({
					type: "post",
					url: config.api_SaleYjtj,
					data: that.postSaleYjtj,
					async: true
				}).done((res) => {
					that.SaleYjtj.ycj = res.result.ycjkh;
					that.SaleYjtj.dd = res.result.ddsl;
					that.SaleYjtj.ddzje = res.result.ddzje;
					that.SaleYjtj.yhk = res.result.hkje;
					that.SaleYjtj.dk = res.result.yjje;
					that.loading('close');
				});
			},
			//销售简报
			getSalejb(time1, time2) {
				const that = this;
				that.loading();
				var startTime = time1;
				var endTime = time2;
				that.postSalejb.startTime = startTime;
				that.postSalejb.endTime = endTime;
				//				if(that.power != '经理') {
				//					console.log('aa')
				//					that.postSalejb.saleId = that.personId();
				//				}
				$.ajax({
					type: "post",
					url: config.api_SaleYjtj,
					data: that.postSalejb,
					async: true
				}).done((res) => {
					that.Salejb.ddzje = res.result.ddzje;
					that.Salejb.hkje = res.result.hkje;
					that.Salejb.ycjkh = res.result.ycjkh;
					that.Salejb.yjje = res.result.yjje;
					that.Salejb.yxkhs = res.result.yxkhs;
					that.loading('close');
				});
			},
			// 电话量、意向客户数、拜访量、签单量
			getData: function(time, type, fbw) {
				var that = this;
				console.log(time)
				var t = time.split('~')
				var t0 = t[0];
				var t1 = t[1];
				that.loading();
				if(that.power != '经理' && that.power != '商务顾问' && that.power != '电销') return false;
				var data_s = {};
				if(that.power == '经理') {
					console.log(that.power)
					data_s = {
						flag: type,
						startTime: t0,
						endTime: t1
					}
				} else {
					data_s = {
						flag: type,
						startTime: t0,
						endTime: t1,
						saleId: that.personId()
					}
				}
				$.ajax({
					url: config.api_all,
					async: true,
					data: data_s,
					success: function(res) {
						that.loading('close')
						if(res.error == "00") {
							var sjList = [];
							for(i = 0; i < res.result1.length; i++) {
								sjList.push(res.result1[i].time)
							}

							if(fbw == 0) {
								option1.xAxis.data = ['周一', '周二', '周三', '周四', '周五']
								option2.xAxis.data = ['周一', '周二', '周三', '周四', '周五']
								option3.xAxis.data = ['周一', '周二', '周三', '周四', '周五']
								option.xAxis[0].data = ['周一', '周二', '周三', '周四', '周五']
							} else if(fbw == 1) {
								option1.xAxis.data = ['四月', '五月', '六月']
								option2.xAxis.data = ['四月', '五月', '六月']
								option3.xAxis.data = ['四月', '五月', '六月']
								option.xAxis[0].data = ['四月', '五月', '六月']

							} else if(fbw == 2) {
								option1.xAxis.data = ['七月', '八月', '九月']
								option2.xAxis.data = ['七月', '八月', '九月']
								option3.xAxis.data = ['七月', '八月', '九月']
								option.xAxis[0].data = ['七月', '八月', '九月']
							} else if(fbw == 3) {
								option1.xAxis.data = ['十月', '十一月', '十二月']
								option2.xAxis.data = ['十月', '十一月', '十二月']
								option3.xAxis.data = ['十月', '十一月', '十二月']
								option.xAxis[0].data = ['十月', '十一月', '十二月']
							} else if(fbw == 4) {
								option1.xAxis.data = ['一月', '二月', '三月']
								option2.xAxis.data = ['一月', '二月', '三月']
								option3.xAxis.data = ['一月', '二月', '三月']
								option.xAxis[0].data = ['一月', '二月', '三月']
							}
							that.c = res.result1;
							if(that.power == "经理" || that.power == "电销") {
								that.chart_init(t0, t1, fbw, type, sjList)
								//意向客户数
								var result2 = res.result2
								var result2List = [];
								for(var i = 0; i < result2.length; i++) {
									var el = result2[i];
									result2List.push(el.yxkhs);
								}
								var max = Number(Math.max.apply(null, result2List));
								max = (max + max * 0.2)
								var myChart1 = echarts.init(document.getElementById('main1'));
								option1.yAxis.max = Math.ceil(max);
								option1.series[0].data = result2List;
								myChart1.setOption(option1);
								myChart1.off("click");
								myChart1.on("click", function(params) {
									if(type == 0) {
										t0 = sjList[params.dataIndex];
										t1 = sjList[params.dataIndex];
									} else if(type == 1) {
										var sj = sjList[params.dataIndex];
										var n = sj.split('-');
										var year = n[0];
										var month = n[1];
										var d = new Date(year, month, 0);
										var day = d.getDate();
										t0 = sj + '-01';
										t1 = sj + '-' + day
									}
									var index = layer.open({
										type: 2,
										title: '意向客户数列表',
										content: '/static/page/index/saler_customer_list.html?startTime=' + t0 + '&endTime=' + t1,
										area: ['100%', '100%']
									});
								})
							}

							if(that.power == "经理" || that.power == "商务顾问") {
								//拜访量
								var result3 = res.result3
								var result3List = [];
								for(var i = 0; i < result3.length; i++) {
									var el = result3[i];
									result3List.push(el.bfl);
								}
								var max = Number(Math.max.apply(null, result3List));
								max = (max + max * 0.2)
								var myChart2 = echarts.init(document.getElementById('main2'));
								option2.yAxis.max = Math.ceil(max);
								option2.series[0].data = result3List;
								myChart2.setOption(option2);
								myChart2.off("click");
								myChart2.on("click", function(params) {
									if(type == 0) {
										t0 = sjList[params.dataIndex];
										t1 = sjList[params.dataIndex];
									} else if(type == 1) {
										var sj = sjList[params.dataIndex];
										var n = sj.split('-');
										var year = n[0];
										var month = n[1];
										var d = new Date(year, month, 0);
										var day = d.getDate();
										t0 = sj + '-01';
										t1 = sj + '-' + day
									}
									var index = layer.open({
										type: 2,
										title: '拜访量列表',
										content: '/static/page/index/saler_bfl_list.html?startTime=' + t0 + '&endTime=' + t1 + '&divide=1',
										area: ['100%', '100%']
									});
								})

								//签单量
								var result4 = res.result4
								var result4List = [];
								for(var i = 0; i < result4.length; i++) {
									var el = result4[i];
									result4List.push(el.qdl);
								}
								var max = Number(Math.max.apply(null, result4List));
								max = (max + max * 0.2)
								var myChart3 = echarts.init(document.getElementById('main3'));
								option3.yAxis.max = Math.ceil(max);
								option3.series[0].data = result4List;
								myChart3.setOption(option3);
								myChart3.off("click");
								myChart3.on("click", function(params) {
									if(type == 0) {
										t0 = sjList[params.dataIndex];
										t1 = sjList[params.dataIndex];
									} else if(type == 1) {
										var sj = sjList[params.dataIndex];
										var n = sj.split('-');
										var year = n[0];
										var month = n[1];
										var d = new Date(year, month, 0);
										var day = d.getDate();
										t0 = sj + '-01';
										t1 = sj + '-' + day
									}
									var index = layer.open({
										type: 2,
										title: '签单量列表',
										content: '/static/page/index/saler_qdl_list.html?startTime=' + t0 + '&endTime=' + t1 + '&divide=1',
										area: ['100%', '100%']
									});
								})
							}

						} else {
							layer.msg(res.msg)
						}
					}
				});
			},
			chart_init: function(t0, t1, fbw, type, sjList) {
				this.myChart = echarts.init(document.getElementById('main'));
				var dhl = this.dhl;
				var yxdhl = this.yxdhl;
				var amount_MAX, amount_MAX1;
				amount_MAX = Number(Math.max.apply(null, dhl))
				amount_MAX = (amount_MAX + amount_MAX * 0.1)
				amount_MAX1 = Number(Math.max.apply(null, yxdhl))
				amount_MAX1 = (amount_MAX1 + amount_MAX1)
				option.yAxis[0].max = Math.ceil(amount_MAX)
				option.yAxis[1].max = Math.ceil(amount_MAX1)
				option.series[0].data = dhl
				option.series[1].data = yxdhl

				this.myChart.setOption(option)
				this.myChart.off("click");
				this.myChart.on("click", function(params) {
					if(type == 0) {
						t0 = sjList[params.dataIndex];
						t1 = sjList[params.dataIndex];
					} else if(type == 1) {
						var sj = sjList[params.dataIndex];
						var n = sj.split('-');
						var year = n[0];
						var month = n[1];
						var d = new Date(year, month, 0);
						var day = d.getDate();
						t0 = sj + '-01';
						t1 = sj + '-' + day
					}
					var html = '/static/page/index/saler_follow_list.html?startTime=' + t0 + '&endTime=' + t1 + '&type=1';
					if(params.seriesIndex == 0) {
						var index = layer.open({
							type: 2,
							title: '电话量',
							content: html,
							area: ['100%', '100%']
						});
					} else if(params.seriesIndex == 1) {
						var index = layer.open({
							type: 2,
							title: '有效电话量',
							content: html + '&valid=1',
							area: ['100%', '100%']
						});
					}
				})
			},
			//商务顾问销售统计
			getData1: function(time) {
				var that = this;
				that.loading();
				if(that.power != '经理') return false;
				$.ajax({
					url: config.api_Xstj,
					async: true,
					data: {
						time: time
					},
					success: function(res) {
						that.loading('close')
						if(res.error == "00") {
							that.c1 = res.result;
							if(that.power != '经理') return false;
							that.chart_init1(time, that.c1)
						} else {
							layer.msg(res.msg)
						}
					}
				});
			},
			chart_init1: function(time, listId) {
				var that = this;
				this.myChart4 = echarts.init(document.getElementById('main4'));
				option4.xAxis[0].data = this.name1;
				var khsl = this.khsl;
				var ddsl = this.ddsl;
				var ddzje = this.ddzje;
				var dkje = this.dkje;

				option4.series[0].data = khsl;
				option4.series[1].data = ddsl;
				option4.series[2].data = ddzje;
				option4.series[3].data = dkje;
				var amount_MAX, amount_MAX1
				amount_MAX = Number(Math.max.apply(null, khsl.concat(ddsl)))
				amount_MAX = (amount_MAX + amount_MAX)
				option4.yAxis[0].max = Math.ceil(amount_MAX)

				amount_MAX1 = Number(Math.max.apply(null, ddzje.concat(dkje)))
				amount_MAX1 = (amount_MAX1 + amount_MAX1 * 0.1)
				option4.yAxis[1].max = Math.ceil(amount_MAX1)
				startTime = Number(time) + '-04-01'
				endTime = Number(time) + 1 + '-03-31'
				this.myChart4.setOption(option4)
				this.myChart4.off("click");
				this.myChart4.on("click", function(params) {
					console.log(params);
					var id = listId[params.dataIndex].userId

					if(params.seriesIndex == 0) {
						var index = layer.open({
							type: 2,
							title: '已成交客户列表',
							content: '/static/page/index/saler_turnover_list.html?startTime=' + startTime + '&endTime=' + endTime + '&flag=1&USER_ID=' + id,
							area: ['100%', '100%']
						});
					} else if(params.seriesIndex == 1) {
						var index = layer.open({
							type: 2,
							title: '订单列表',
							content: '/static/page/index/saler_turnover_list.html?startTime=' + startTime + '&endTime=' + endTime + '&flag=2&USER_ID=' + id,
							area: ['100%', '100%']
						});
					} else if(params.seriesIndex == 2) {
						var index = layer.open({
							type: 2,
							title: '订单列表',
							content: '/static/page/index/saler_turnover_list.html?startTime=' + startTime + '&endTime=' + endTime + '&flag=2&USER_ID=' + id,
							area: ['100%', '100%']
						});
					} else if(params.seriesIndex == 3) {
						var index = layer.open({
							type: 2,
							title: '回款列表',
							content: '/static/page/index/saler_turnover_list.html?startTime=' + startTime + '&endTime=' + endTime + '&flag=3&USER_ID=' + id,
							area: ['100%', '100%']
						});
					}

				});

			},
			//电销业绩目标完成情况
			getData2: function(time) {
				var that = this;
				var swTime;
				var n = time.split('-');
				var year = n[0];
				var month = n[1];
				var d = new Date(year, month, 0);
				var day = d.getDate();
				if(month == '01' || month == '02' || month == '03') {
					swTime = year - 1
				} else {
					swTime = year
				}
				that.loading();
				if(that.power == '经理' || that.role == '电销主管' || that.role == '电销员工') {
					$.ajax({
						url: config.api_detail,
						async: true,
						data: {
							startTime: time + '-01',
							endTime: time + '-' + day
						},
						success: function(res) {
							that.loading('close')
							if(res.error == "00") {
								var list = [];
								var list1 = [];
								var list2 = [];
								for(var i = 0; i < res.result.length; i++) {
									var el = res.result[i];
									list.push(el.name);
									list1.push(el.dhl)
									if(el.yxkhs != undefined) {
										list2.push(el.yxkhs)
									}
								}
								var max = Number(Math.max.apply(null, list1));
								max = (max + max * 0.2)
								var max1 = Number(Math.max.apply(null, list2));
								max1 = (max1 + max1)
								var myChart6 = echarts.init(document.getElementById('main6'));
								option6.xAxis[0].data = list;
								option6.series[0].data = list1;
								option6.series[1].data = list2;
								option6.yAxis[0].max = Math.ceil(max);
								option6.yAxis[1].max = Math.ceil(max1);
								myChart6.setOption(option6);
								myChart6.off("click");
								myChart6.on("click", function(params) {
									console.log(params);
									var id = res.result[params.dataIndex].saleId;
									var name = res.result[params.dataIndex].name;
									var html = '/static/page/dxPerson.html?time=' + swTime + '&id=' + id + '&saleName=' + name;
									var index = layer.open({
										type: 2,
										title: '电销业绩目标完成情况',
										content: html,
										area: ['100%', '100%']
									});

								});

							} else {
								layer.msg(res.msg)
							}
						}
					});
				}

			},

			//电销独有————电销业绩目标完成情况
			getDataDx: function(time, ischange) {
				var that = this;
				var nowDate = that.nowMonth()
				var isMonth = nowDate.split("-")[1]
				var isdate = new Date;
				var isYear = isdate.getFullYear();
				var startTime, endTime;
				startTime = time + '-04-01';
				endTime = Number(time) + 1 + '-03-31';
				that.dxl_time = startTime + ',' + endTime
				var hhd, hhd1, mmd, mmd1;
				var nextTime = nowDate;
				that.loading();
				if(that.power != '电销') return false;
				if(that.role == '电销主管') return false;
				$.ajax({
					url: config.api_detail,
					async: true,
					data: {
						startTime: startTime,
						endTime: endTime,
						saleId: that.personId(),
					},
					success: function(res) {
						that.loading('close')
						if(res.error == "00") {
							that.dx.list = res.result
							var list1 = [];
							var list2 = [];
							if(ischange == undefined) {
								for(var i = 0; i < res.result.length; i++) {
									var el = res.result[i];
									if(el.mbyxkhs == 0) {
										el.mbyxkhs = '0,0'
									}
									list1.push(el.dhl)
									list2.push(el.yxkhs)
									if(nowDate == el.time) {
										that.idchange3 = i
										that.dx.dhl = el.dhl || 0;
										that.dx.mbdhl = el.mbdhl || 0;
										that.dx.yxkhs = el.yxkhs || 0;
										that.dx.mbyxkhs = el.mbyxkhs.split(',')[0] || 0;
										hhd = Math.round(that.dx.dhl / that.dx.mbdhl * 10000) / 100.00
										if(hhd == 'Infinity') {
											hhd = 100
										} else if(isNaN(hhd)) {
											hhd = 0
										}
										if(hhd >= 100) hhd = 100
										hhd1 = hhd + "%";
										mmd = Math.round(that.dx.yxkhs / that.dx.mbyxkhs * 10000) / 100.00
										if(mmd == 'Infinity') {
											mmd = 100
										} else if(isNaN(mmd)) {
											mmd = 0
										}
										if(mmd >= 100) mmd = 100
										mmd1 = mmd + "%";

									}
								}
							} else {
								for(var i = 0; i < res.result.length; i++) {
									var el = res.result[i];
									list1.push(el.dhl)
									list2.push(el.yxkhs)
								}
								that.dx.dhl = res.result[that.idchange3].dhl || 0;
								that.dx.mbdhl = res.result[that.idchange3].mbdhl || 0;
								that.dx.yxkhs = res.result[that.idchange3].yxkhs || 0;
								console.log(res.result, res.result[that.idchange3].mbyxkhs)
								if(res.result[that.idchange3].mbyxkhs == 0) {
									that.dx.mbyxkhs = 0
								} else {
									that.dx.mbyxkhs = res.result[that.idchange3].mbyxkhs.split(',')[0];
								}

								hhd = Math.round(that.dx.dhl / that.dx.mbdhl * 10000) / 100.00
								if(hhd == 'Infinity') {
									hhd = 100
								} else if(isNaN(hhd)) {
									hhd = 0
								}
								if(hhd >= 100) hhd = 100
								hhd1 = hhd + "%";

								mmd = Math.round(that.dx.yxkhs / that.dx.mbyxkhs * 10000) / 100.00
								if(mmd == 'Infinity') {
									mmd = 100
								} else if(isNaN(mmd)) {
									mmd = 0
								}
								if(mmd >= 100) mmd = 100
								mmd1 = mmd + "%";
							}

							var max = Number(Math.max.apply(null, list1));
							max = (max + max * 0.2)
							var max1 = Number(Math.max.apply(null, list2));
							max1 = (max1 + max1)
							if(that.power != '电销') return false;
							var myChartDx = echarts.init(document.getElementById('dxOnly'));
							dxOnly.series[0].data = list1;
							dxOnly.series[1].data = list2;
							dxOnly.yAxis[0].max = Math.ceil(max);
							dxOnly.yAxis[1].max = Math.ceil(max1);
							myChartDx.setOption(dxOnly);
							that.dxhx(hhd, hhd1, mmd, mmd1, nextTime)
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
									if(hhd == 'Infinity') {
										hhd = 100
									} else if(isNaN(hhd)) {
										hhd = 0
									}
									if(hhd >= 100) hhd = 100
									hhd1 = hhd + "%"
									mmd = Math.round(shuju.yxkhs / shuju.mbyxkhs.split(',')[0] * 10000) / 100.00;
									if(mmd == 'Infinity') {
										mmd = 100
									} else if(isNaN(mmd)) {
										mmd = 0
									}
									if(mmd >= 100) mmd = 100
									mmd1 = mmd + "%"

									that.dxhx(hhd, hhd1, mmd, mmd1, nextTime);
								}
							})

						} else {
							layer.msg(res.msg)
						}
					}
				});
			},
			//电销环形
			dxhx(hhd, hhd1, mmd, mmd1, nextTime) {
				let that = this
				var t0, t1
				var n = nextTime.split('-');
				var year = n[0];
				var month = n[1];
				var d = new Date(year, month, 0);
				var day = d.getDate();
				t0 = nextTime + '-01';
				t1 = nextTime + '-' + day
				that.dxl_time = t0 + ',' + t1
				//环形1
				pieChartOption.series[0].max = '100'
				//				pieChartOption.series[0].max = that.dx.mbdhl || 0
				pieChartOption.series[0].data[0].value = hhd
				var asd = document.getElementById('pie');
				var pieChart = echarts.init(asd);
				pieChart.setOption(pieChartOption);
				pieChart.off("click");
				pieChart.on("click", function(params) {
					var html = '/static/page/index/saler_follow_list.html?startTime=' + t0 + '&endTime=' + t1 + '&type=1';
					var index = layer.open({
						type: 2,
						title: '电话量',
						content: html,
						area: ['100%', '100%']
					});

				});

				//环形2
				pieChartOption2.series[0].max = '100'
				//				pieChartOption2.series[0].max = that.dx.mbyxkhs.split('0') || 0
				pieChartOption2.series[0].data[0].value = mmd
				var asd2 = document.getElementById('pie2');
				var pieChart2 = echarts.init(asd2);
				pieChart2.setOption(pieChartOption2);
				pieChart2.off("click");
				pieChart2.on("click", function(params) {
					var index = layer.open({
						type: 2,
						title: '查看详情',
						content: '/static/page/index/saler_customer_list.html?startTime=' + t0 + '&endTime=' + t1,
						area: ['100%', '100%']
					});
				});
			},
			dx_location(type) {
				let that = this
				if(type == '1') {
					var html = '/static/page/index/saler_follow_list.html?startTime=' + that.dxl_time.split(',')[0] + '&endTime=' + that.dxl_time.split(',')[1] + '&type=1';
					var index = layer.open({
						type: 2,
						title: '电话量',
						content: html,
						area: ['100%', '100%']
					});
				} else {
					var index = layer.open({
						type: 2,
						title: '查看详情',
						content: '/static/page/index/saler_customer_list.html?startTime=' + that.dxl_time.split(',')[0] + '&endTime=' + that.dxl_time.split(',')[1],
						area: ['100%', '100%']
					});
				}
			},
			//商务顾问独有
			getDataSwgw() {
				var that = this;
				var aa, bb, cc, dd, aa1, bb1, cc1, dd1;
				if(that.power != '商务顾问' || that.role == "电销商务顾问") return false;
				$.ajax({
					type: "post",
					url: config.api_swgw,
					async: true,
					data: {
						saleId: that.personId()
					},
					success: function(res) {
						var result = res.result2
						that.swgw.yjdk = result.yjdk || 0;
						that.swgw.mbyjdk = result.mbyjdk || 0;
						that.swgw.htsl = result.htsl || 0;
						that.swgw.mbhtsl = result.mbhtsl || 0;
						that.swgw.ddzje = result.ddzje || 0;
						that.swgw.mbddzje = result.mbddzje || 0;
						that.swgw.bfl = result.bfl || 0;
						that.swgw.mbbfl = result.mbbfl || 0;
						aa = Math.round(result.yjdk / result.mbyjdk * 10000) / 100.00
						if(aa == 'Infinity') {
							aa = 100
						} else if(isNaN(aa)) {
							aa = 0
						}
						if(aa >= 100) aa = 100
						aa1 = aa + "%"
						bb = Math.round(result.htsl / result.mbhtsl * 10000) / 100.00;
						if(bb == 'Infinity') {
							bb = 100
						} else if(isNaN(bb)) {
							bb = 0
						}
						if(bb >= 100) bb = 100
						bb1 = bb + "%"
						cc = Math.round(result.ddzje / result.mbddzje * 10000) / 100.00;
						if(cc == 'Infinity') {
							cc = 100
						} else if(isNaN(cc)) {
							cc = 0
						}
						if(cc >= 100) cc = 100
						cc1 = cc + "%"
						dd = Math.round(result.bfl / result.mbbfl * 10000) / 100.00;
						if(dd == 'Infinity') {
							dd = 100
						} else if(isNaN(dd)) {
							dd = 0
						}
						if(dd >= 100) dd = 100
						dd1 = dd + "%"
						if(that.power != '商务顾问') return false;
						//业务到款
						pieChartOption_1.series[0].max = '100'
						//						pieChartOption_1.series[0].max = result.mbyjdk || 0
						pieChartOption_1.series[0].data[0].value = aa
						var s1 = document.getElementById('swgw1');
						var swgw1 = echarts.init(s1);
						swgw1.setOption(pieChartOption_1);
						swgw1.off("click");
						swgw1.on('click', function(params) {
							var html = '/static/page/swPerson_self.html?index=3';
							var index = layer.open({
								type: 2,
								title: '商务顾问业绩目标完成情况',
								content: html,
								area: ['100%', '100%']
							});
						})

						//合同数量
						pieChartOption_2.series[0].max = '100'
						//						pieChartOption_2.series[0].max = result.mbhtsl || 0
						pieChartOption_2.series[0].data[0].value = bb
						var s2 = document.getElementById('swgw2');
						var swgw2 = echarts.init(s2);
						swgw2.setOption(pieChartOption_2);
						swgw2.off("click");
						swgw2.on('click', function(params) {
							var html = '/static/page/swPerson_self.html?index=1';
							var index = layer.open({
								type: 2,
								title: '商务顾问业绩目标完成情况',
								content: html,
								area: ['100%', '100%']
							});
						})
						//委案金额
						pieChartOption_3.series[0].max = '100'
						//						pieChartOption_3.series[0].max = result.mbddzje || 0
						pieChartOption_3.series[0].data[0].value = cc
						var s3 = document.getElementById('swgw3');
						var swgw3 = echarts.init(s3);
						swgw3.setOption(pieChartOption_3);
						swgw3.off("click");
						swgw3.on('click', function(params) {
							var html = '/static/page/swPerson_self.html?index=2';
							var index = layer.open({
								type: 2,
								title: '商务顾问业绩目标完成情况',
								content: html,
								area: ['100%', '100%']
							});
						})
						//拜访量
						pieChartOption_4.series[0].max = '100'
						//						pieChartOption_4.series[0].max = result.mbbfl || 0
						pieChartOption_4.series[0].data[0].value = dd
						var s4 = document.getElementById('swgw4');
						var swgw4 = echarts.init(s4);
						swgw4.setOption(pieChartOption_4);
						swgw4.off("click");
						swgw4.on('click', function(params) {
							var html = '/static/page/swPerson_self.html?index=0';
							var index = layer.open({
								type: 2,
								title: '商务顾问业绩目标完成情况',
								content: html,
								area: ['100%', '100%']
							});
						})

					}
				});
			},
			swgwLocation(type) {
				var html = '/static/page/swPerson_self.html?index=' + type;
				var index = layer.open({
					type: 2,
					title: '商务顾问业绩目标完成情况',
					content: html,
					area: ['100%', '100%']
				});
			},
			//电销商务顾问独有
			getDataSwgw_dx() {
				var that = this;
				var aa, bb, cc, aa1, bb1, cc1;
				if(that.role != '电销商务顾问') return false;
				$.ajax({
					type: "post",
					url: config.api_dxswgw,
					async: true,
					data: {
						saleId: that.personId()
					},
					success: function(res) {
						var result = res.result2
						that.dxswgw.bfl = result.bfl || 0;
						that.dxswgw.mbbfl = result.mbbfl || 0;
						that.dxswgw.khs = result.khs || 0;
						that.dxswgw.mbkhs = result.mbkhs || 0;
						that.dxswgw.ddje = result.ddje || 0;
						that.dxswgw.mbddje = result.mbddje || 0;
						aa = Math.round(result.bfl / result.mbbfl * 10000) / 100.00
						if(aa == 'Infinity') {
							aa = 100
						} else if(isNaN(aa)) {
							aa = 0
						}
						if(aa >= 100) aa = 100
						aa1 = aa + "%"
						bb = Math.round(result.khs / result.mbkhs * 10000) / 100.00;
						if(bb == 'Infinity') {
							bb = 100
						} else if(isNaN(bb)) {
							bb = 0
						}
						if(bb >= 100) bb = 100
						bb1 = bb + "%"
						cc = Math.round(result.ddje / result.mbddje * 10000) / 100.00;
						if(cc == 'Infinity') {
							cc = 100
						} else if(isNaN(cc)) {
							cc = 0
						}
						if(cc >= 100) cc = 100
						cc1 = cc + "%"
						if(that.role != '电销商务顾问') return false;
						//拜访量
						pieChartOption_dx1.series[0].max = '100'
						//						pieChartOption_dx1.series[0].max = result.mbbfl || 0
						pieChartOption_dx1.series[0].data[0].value = aa

						var s1 = document.getElementById('swgw1_dx');
						var swgw1 = echarts.init(s1);
						swgw1.setOption(pieChartOption_dx1);
						swgw1.off("click");
						swgw1.on('click', function(params) {
							var html = '/static/page/swPerson_selfdx.html?type=1';
							var index = layer.open({
								type: 2,
								title: '电销商务顾问业绩目标完成情况',
								content: html,
								area: ['100%', '100%']
							});
						})

						//签约客户数
						pieChartOption_dx2.series[0].max = '100'
						//						pieChartOption_dx2.series[0].max = result.mbkhs || 0
						pieChartOption_dx2.series[0].data[0].value = bb
						var s2 = document.getElementById('swgw2_dx');
						var swgw2 = echarts.init(s2);
						swgw2.setOption(pieChartOption_dx2);
						swgw2.off("click");
						swgw2.on('click', function(params) {
							var html = '/static/page/swPerson_selfdx.html?type=2';
							var index = layer.open({
								type: 2,
								title: '电销商务顾问业绩目标完成情况',
								content: html,
								area: ['100%', '100%']
							});
						})
						//订单金额
						pieChartOption_dx3.series[0].max = '100'
						//						pieChartOption_dx3.series[0].max = result.mbddje || 0
						pieChartOption_dx3.series[0].data[0].value = cc
						var s3 = document.getElementById('swgw3_dx');
						var swgw3 = echarts.init(s3);
						swgw3.setOption(pieChartOption_dx3);
						swgw3.off("click");
						swgw3.on('click', function(params) {
							var html = '/static/page/swPerson_selfdx.html?type=3';
							var index = layer.open({
								type: 2,
								title: '电销商务顾问业绩目标完成情况',
								content: html,
								area: ['100%', '100%']
							});
						})

					}
				});
			},
			dxLocation(type) {
				let html;
				if(type == '1') {
					html = '/static/page/swPerson_selfdx.html?type=1'
				} else if(type == '2') {
					html = '/static/page/swPerson_selfdx.html?type=2'
				} else {
					html = '/static/page/swPerson_selfdx.html?type=3'
				}
				var index = layer.open({
					type: 2,
					title: '电销商务顾问业绩目标完成情况',
					content: html,
					area: ['100%', '100%']
				});
			},
			//商务顾问业绩目标完成情况
			getYjmb(time, type, ch) {
				const that = this
				var date = new Date().getFullYear()
				var startTime, endTime;
				var nowDate = that.nowMonth()
				var isMonth = nowDate.split("-")[1]
				var isdate = new Date;
				var isYear = isdate.getFullYear();
				var thisYear
				if(isYear == date) {
					if(isMonth == '01' || isMonth == '02' || isMonth == '03') {
						date = date - 1
					} else {
						date = date
					}
				} else {
					date = date
				}

				if(ch == 0) {
					if(time == 0) {
						startTime = date + '-04-01';
						endTime = date + '-06-30';
					} else if(time == 1) {
						startTime = date + '-07-01';
						endTime = date + '-09-30';
					} else if(time == 2) {
						startTime = date + '-10-01';
						endTime = date + '-12-31';
					} else if(time == 3) {
						startTime = Number(date) + 1 + '-01-01';
						endTime = Number(date) + 1 + '-03-31';
					}
				} else if(ch == 1) {
					var n = time.split('-');
					var year = n[0];
					var month = n[1];
					var d = new Date(year, month, 0);
					var day = d.getDate();
					startTime = time + '-01';
					endTime = time + '-' + day
				}
				if(that.power != '经理') return false;
				$.ajax({
					type: 'post',
					url: config.api_Yjmb,
					async: true,
					data: {
						startTime: startTime,
						endTime: endTime,
						type: type
					},
					success: function(res) {
						that.loading('close')
						if(res.error == "00") {
							var list = [];
							var list1 = [];

							for(var i = 0; i < res.result.length; i++) {
								var el = res.result[i];
								list.push(el.name);
								if(type == 0 || type == 2) {
									list1.push(el.dkje.toFixed(2))
								} else {
									list1.push(el.dkje)
								}

							}

							var max = Number(Math.max.apply(null, list1));
							max = (max + max * 0.2)
							if(that.power != '经理') return false;
							var myChart5 = echarts.init(document.getElementById('main5'));
							option5.xAxis.data = list;
							option5.yAxis.max = Math.ceil(max);
							option5.series[0].data = list1;
							myChart5.setOption(option5);
							myChart5.off("click");
							myChart5.on('click', function(params) {
								var id = res.result[params.dataIndex].userId;
								var name = res.result[params.dataIndex].name;
								var swTime;
								if(ch == 0) {
									if(isMonth == '01' || isMonth == '02' || isMonth == '03') {
										swTime = new Date().getFullYear() - 1
									} else {
										swTime = new Date().getFullYear()
									}

								} else {
									var n = time.split('-');
									var year = n[0];
									var month = n[1];
									if(month == '01' || month == '02' || month == '03') {
										swTime = year - 1
									} else {
										swTime = year
									}
								}
								var html = '/static/page/swPerson.html?time=' + swTime + '&id=' + id + '&saleName=' + name + '&jidu=' + time + '&type=' + type + '&ch=' + ch;
								var index = layer.open({
									type: 2,
									title: '商务顾问业绩目标完成情况',
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

			//电销商务顾问业绩目标完成情况
			getYjmb_dx: function() {
				var that = this;
				that.loading();
				if(that.power != '经理') return false;
				$.ajax({
					url: config.api_dxswgw,
					async: true,
					data: {
						time: that.yjmbMonth_dx
					},
					success: function(res) {
						that.loading('close')
						if(res.error == "00") {
							that.c_dx = res.result;
							if(that.power != '经理') return false;
							that.chart_init_dx(that.yjmbMonth_dx, that.c_dx, res)
						} else {
							layer.msg(res.msg)
						}
					}
				});
			},
			chart_init_dx: function(time, listId, res) {
				let that = this;
				this.main5_dx = echarts.init(document.getElementById('main5_dx'));
				option5_dx.xAxis[0].data = this.name_dx;
				var bfl = this.bfl_dx;
				var ddzje = this.ddzje_dx;
				var khsl = this.khsl_dx;

				option5_dx.series[0].data = bfl;
				option5_dx.series[1].data = khsl;
				option5_dx.series[2].data = ddzje;
				var amount_MAX, amount_MAX1
				amount_MAX = Number(Math.max.apply(null, bfl.concat(khsl)))
				amount_MAX = (amount_MAX + amount_MAX)
				option5_dx.yAxis[0].max = Math.ceil(amount_MAX)

				amount_MAX1 = Number(Math.max.apply(null, ddzje))
				amount_MAX1 = (amount_MAX1 + amount_MAX1 * 0.1)
				option5_dx.yAxis[1].max = Math.ceil(amount_MAX1)

				this.main5_dx.setOption(option5_dx)
				this.main5_dx.off("click");
				this.main5_dx.on("click", function(params) {
					console.log(params)
					var id = res.result[params.dataIndex].userId;
					var name = res.result[params.dataIndex].name;
					var n = that.yjmbMonth_dx.split('-');
					var year = n[0];
					var month = n[1];
					if(month == '01' || month == '02' || month == '03') {
						swTime = year - 1
					} else {
						swTime = year
					}
					var html = '/static/page/swPerson_dx.html?time=' + swTime + '&id=' + id + '&saleName=' + name + '&jidu=' + time + '&type=' + params.seriesIndex;
					var index = layer.open({
						type: 2,
						title: '电销商务顾问业绩目标完成情况',
						content: html,
						area: ['100%', '100%']
					});

				});
			},
			//运作简报
			getCainian(time) {
				var that = this;
				var nowDate = that.nowMonth()
				var isMonth = nowDate.split("-")[1]
				var isdate = new Date;
				var isYear = isdate.getFullYear();
				var startTime, endTime;
				startTime = time + '-04-01';
				endTime = Number(time) + 1 + '-03-31';
				if(that.power != '经理') return false;
				$.ajax({
					type: 'post',
					url: config.api_yzjb,
					async: true,
					data: {
						startTime: startTime,
						endTime: endTime,
					},
					success: function(res) {
						that.loading('close')
						if(res.error == "00") {
							that.c4 = res.result;
							var list = [];
							var list1 = [];

							for(var i = 0; i < res.result.length; i++) {
								var el = res.result[i];
								list.push(el.NAME);
								list1.push(el.je.toFixed(2))
							}
							var max = Number(Math.max.apply(null, list1));
							max = (max + max * 0.2)
							if(that.power != '经理') return false;
							var myChart7 = echarts.init(document.getElementById('main7'));
							option7.xAxis.data = list;
							option7.yAxis.max = Math.ceil(max);
							option7.series[0].data = list1;
							myChart7.setOption(option7);
							myChart7.off("click");
							myChart7.on('click', function(params) {
								var id = res.result[params.dataIndex].userId;
								var name = res.result[params.dataIndex].NAME;
								var html = '/static/page/index/saler_yunzuo_list1.html?gxtime=' + that.cainian + '&saleId=' + id + '&saleName=' + name;
								var index = layer.open({
									type: 2,
									title: '运作业绩目标完成情况',
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
			//本月范围
			showMonthFirstDay() {
				const that = this;
				var d = new Date();
				d.setMonth(d.getMonth() + 1);
				d.setDate(0);
				var day = new Date(d).getDate();
				var year = new Date().getFullYear().toString();
				var month = new Date().getMonth() + 1;
				month = (month < 10 ? "0" + month : month);
				var riqi = year + '-' + month + '-01';
				var riqi1 = year + '-' + month + '-' + day;
				that.startyear = riqi;
				that.endyear = riqi1;
			},
			showWen() {
				const that = this
				$.get(config.api_zhou, {
					type: 0,
					flag: 1,
				}, function(res) {
					that.sevens = res.result;
					that.month = that.sevens[0];
					that.getData(that.month, 0, 0);
				})
			},
			tab(e) {
				const that = this;
				if(e == 1) {
					that.taskList();
				} else if(e == 2) {
					that.shen();
				} else if(e == 3) {
					that.tx1();
				} else if(e == 4) {
					that.tx2();
				}
			},
			//我的任务
			taskList(page) {
				const that = this;
				if(page) this.task.pageNum = page
				that.loading();
				that.postDataTask.pageSize = this.task.pageSize;
				that.postDataTask.pageNo = this.task.pageNum;
				require(['pagination'], (pagination) => {
					$.ajax({
						url: config.api_task,
						async: true,
						data: that.postDataTask
					}).done((res) => {
						that.task = res.result;
						//分页
						if(that.pagi) {
							$('.pagi').pagination('updatePages', that.task.pages)
							if(page == 1) $('.pagi').pagination('goToPage', that.task.pageNum)
						} else {
							that.pagi = 1
							$('.pagi').pagination({
								pages: that.task.pages, //总页数
								showCtrl: true,
								displayPage: 6,
								currentPage: that.task.pageNum,
								onSelect: function(num) {
									that.task.pageNum = num
									that.taskList()
								}

							});
						}
						that.loading('close');
					})
				})
			},
			//我的审核
			shen(page) {
				const that = this;
				if(page) this.shenhe.pageNum = page
				that.loading();
				that.postDataShenhe.pageSize = this.shenhe.pageSize;
				that.postDataShenhe.pageNo = this.shenhe.pageNum;
				require(['pagination'], (pagination) => {
					$.ajax({
						url: config.api_shenhe,
						async: true,
						data: that.postDataShenhe
					}).done((res) => {
						that.shenhe = res.result;
						//分页
						if(that.pagi1) {
							$('.pagi1').pagination('updatePages', that.shenhe.pages)
							if(page == 1) $('.pagi1').pagination('goToPage', that.shenhe.pageNum)
						} else {
							that.pagi1 = 1;
							$('.pagi1').pagination({
								pages: that.shenhe.pages, //总页数
								showCtrl: true,
								displayPage: 6,
								currentPage: that.shenhe.pageNum,
								onSelect: function(num) {
									that.shenhe.pageNum = num;
									that.shen();
								}
							});
						}
						that.loading('close');
					})
				})
			},
			//我的提醒
			tx1(page) {
				const that = this;
				if(page) this.mytx.pageNum = page
				that.loading();
				that.postDataMytx.pageSize = this.mytx.pageSize;
				that.postDataMytx.pageNo = this.mytx.pageNum;
				require(['pagination'], (pagination) => {
					$.ajax({
						url: config.api_wdtx,
						async: true,
						data: that.postDataMytx
					}).done((res) => {
						that.mytx = res.result;
						//分页
						if(that.pagi2) {
							$('.pagi2').pagination('updatePages', that.mytx.pages)
							if(page == 1) $('.pagi2').pagination('goToPage', that.mytx.pageNum)
						} else {
							that.pagi2 = 1;
							$('.pagi2').pagination({
								pages: that.mytx.pages, //总页数
								showCtrl: true,
								displayPage: 6,
								currentPage: that.mytx.pageNum,
								onSelect: function(num) {
									that.mytx.pageNum = num
									that.tx1()
								}

							});
						}
						that.loading('close');
					})
				})
			},
			//过期提醒
			tx2(page) {
				const that = this;
				if(page) this.gqtx.pageNum = page
				that.loading();
				that.postDataGqtx.pageSize = this.gqtx.pageSize;
				that.postDataGqtx.pageNo = this.gqtx.pageNum;
				require(['pagination'], (pagination) => {
					$.ajax({
						url: config.api_gqtx,
						async: true,
						data: that.postDataGqtx
					}).done((res) => {
						that.gqtx = res.result;
						//分页
						if(that.pagi3) {
							$('.pagi3').pagination('updatePages', that.gqtx.pages)
							if(page == 1) $('.pagi3').pagination('goToPage', that.gqtx.pageNum)
						} else {
							that.pagi3 = 1;
							$('.pagi3').pagination({
								pages: that.gqtx.pages, //总页数
								showCtrl: true,
								displayPage: 6,
								currentPage: that.gqtx.pageNum,
								onSelect: function(num) {
									that.gqtx.pageNum = num
									that.tx2()
								}

							});
						}
						that.loading('close');
					})
				})
			},
			//头部两行跳转
			customer_case_view(flag, type, marker) {
				const that = this;
				var startyear, endyear, html;
				var year = that.year;
				if(type == 0) {
					startyear = year + '-04-01'
					endyear = Number(year) + 1 + '-03-31'
				} else {
					startyear = that.startyear
					endyear = that.endyear
				}
				if(marker) {
					html = '/static/page/index/saler_turnover_list.html?startTime=' + startyear + '&endTime=' + endyear + '&flag=' + flag + '&divide=1' + '&marker=' + marker
				} else {
					html = '/static/page/index/saler_turnover_list.html?startTime=' + startyear + '&endTime=' + endyear + '&flag=' + flag + '&divide=1'
				}
				var index = layer
					.open({
						type: 2,
						title: '查看详情',
						content: html,
						area: ['100%', '100%']
					});
			},
			order_customer_view() {
				const that = this;
				var index = layer
					.open({
						type: 2,
						title: '查看详情',
						content: '/static/page/index/saler_customer_list.html?startTime=' + that.startyear + '&endTime=' + that.endyear + "&role=" + that.role,
						area: ['100%', '100%']
					});
			},
			viewTask(id, fqr, dfbgId, type, relateId) {
				if(type == '待发快递') {
					var index = layer.open({
						type: 2,
						title: '查看详情',
						content: '/express/goEdit.do?id=' + relateId + "&mark1=1",
						area: ['100%', '100%']
					});
				} else {
					var index = layer.open({
						type: 2,
						title: '查看详情',
						content: '/workBench/views.do?id=' + id + "&fqr=" + fqr + "&dfbgId=" + dfbgId,
						area: ['100%', '100%']
					});
				}

			},
			sh(type, id, debtorId) {
				var url = '/vistsh/goEdit.do?id=';
				switch(type) {
					case "外访":
						break;
					case "诉讼/仲裁":
						url = '/lawsuitaudit/goEdit.do?id='
						break;
					case "案件报告":
						url = '/workBench/views1.do?id='
						break;
					default:
						break;
				}
				var index = layer.open({
					type: 2,
					title: '查看详情',
					content: url + id + "&debtorId=" + debtorId + '&sy_workwench=1',
					area: ['100%', '100%'],
				});

			},
			setSuccess(id, en) {
				const that = this;
				var dialog = layer.confirm("确认完成?", function() {
					$.post('/order/updateCallInfo', {
						id: id
					}).done(function(res) {
						if(res.error == '00') {
							if(en == 1) {
								that.tx1();
							} else if(en == 2) {
								that.tx2();
							}

							layer.close(dialog);

						} else {
							layer.close(dialog)
							layer.msg(res.msg)
						}
					})
				})
			},
			//销售助手
			assis_view(flag, title) {
				var index = layer
					.open({
						type: 2,
						title: title,
						content: '/static/page/index/saler_assistant.html?flag=' + flag,
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
			},
			//获取当前财年
			FiscalYear() {
				var nowYear;
				var date = new Date;
				var year = date.getFullYear();
				var month = date.getMonth() + 1;
				if(month == '01' || month == '02' || month == '03') {
					nowYear = Number(year) - 1
				} else {
					nowYear = year
				}
				return nowYear;
			},
			//循环提醒接口 api_wdtx
			//			loop() {
			//				const that = this
			//				var n = 0
			//				setInterval(function() {
			//					$.ajax({
			//						url: config.api_wdtx,
			//						async: true,
			//						data: that.postDataMytx
			//					}).done((res) => {
			//						var now = new Date();
			//						now.setMinutes(now.getMinutes() + 10);
			//						var next10 = now.getTime()
			//						var list = res.result.list
			//
			//						for(var i = 0; i < list.length; i++) {
			//							var target = new Date(list[i].time).getTime()
			//							var jian = target - next10
			//							console.log(next10, target, jian);
			//
			//							if(jian <= 60000 && jian > 0) {
			//								toastr.options = {
			//					"closeButton": true,
			//					"debug": false,
			//					"progressBar": true,
			//					"positionClass": "toast-top-right",
			//					"onclick": null,
			//					"showDuration": "0",
			//					"hideDuration": "0",
			//					"timeOut": "0",
			//					"extendedTimeOut": "0",
			//					"showEasing": "swing",
			//					"hideEasing": "linear",
			//					"showMethod": "fadeIn",
			//					"hideMethod": "fadeOut"
			//				}
			//								toastr.info('有一条提醒即将过期，请及时查看', '提示', {
			//									positionClass: 'toast-top-right'
			//								})
			//							}
			//						}
			//					})
			//				}, 60000)
			//			},
			//业绩是否完成
			showWindow() {
				var date = new Date();
				var year = date.getFullYear();
				var month = date.getMonth() + 1;
				var day = date.getDate();
				if(month < 10) {
					month = "0" + month;
				}
				if(day < 10) {
					day = "0" + day;
				}
				var startTime = year + "-" + month + "-01";
				var endTime = year + "-" + month + "-" + day;
				var userInfo = localStorage.getItem("userInfo");
				userInfo = $.parseJSON(userInfo)
				var roleId = userInfo.list[0].ROLE_ID;
				var saleId = userInfo.list[0].USER_ID;
				if(parseInt(day) > 20) {
					console.log('今天是' + day + '号')
					$.ajax({
						type: "post",
						url: config.api_showWindow,
						async: true,
						data: {
							startTime: startTime,
							endTime: endTime,
							roleId: roleId,
							saleId: saleId,
						},
						success: function(res) {
							if(res.msg == '达标') {
								$(".htz_success").show();
								$(".htz-modal").show();
							} else if(res.msg == '未达标') {
								$(".htz_defult").show();
								$(".htz-modal").show();
							}
						}
					});
				} else {
					console.log('今天是' + day + '号')
				}

			},
			htzClose() {
				$(".htz_success").hide();
				$(".htz-modal").hide();
				$(".htz_defult").hide();
			},
			remind() {
				let that = this
				var form = $('#remind_box')
				form.find("[name=remark]").val('')
				form.find("[name=time]").val('')
				var dialog = layer.open({
					type: 1,
					title: '新增提醒',
					closeBtn: 1,
					content: $("#remind_box"),
					area: ['600px', '300px'],
					btn: "保存",
					btnAlign: 'c',
					success: function() {

					},
					yes: function() {
						var form = $('#remind_box')
						var remark = form.find("[name=remark]").val();
						var time = form.find("[name=time]").val();
						if(new Date().getTime()> new Date(time).getTime()){
							layer.msg('不能小于当前时间')
							return false
						}
						if(remark == '') {
							layer.msg('提醒内容不能为空')
							return false
						}
						if(time == '') {
							layer.msg('提醒时间不能为空')
							return false
						}
						$.post("/order/saveCallInfo", {
							remark: remark,
							time: time
						}).done(function(res) {
							layer.close(dialog)
							that.tx1();
						})
					}
				})
			},
			personId() {
				var userInfo = localStorage.getItem("userInfo");
				userInfo = $.parseJSON(userInfo)
				var saleId = userInfo.list[0].USER_ID;
				return saleId
			},
			//运作到款个人
			getDatayz(time) {
				const that = this
				var nowDate = that.nowMonth()
				var isMonth = nowDate.split("-")[1]
				var isdate = new Date;
				var isYear = isdate.getFullYear();
				var startTime, endTime;
				startTime = time + '-04-01';
				endTime = Number(time) + 1 + '-03-31';
				that.yzl_time = startTime + ',' + endTime
				that.yzl_id = that.personId()
				var hhd, hhd1;
				if(that.role != '公司内部运作') return false;
				$.ajax({
					type: 'post',
					url: config.api_yz,
					async: true,
					data: {
						startTime: startTime,
						endTime: endTime,
						runnerId: that.personId()
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
							that.yz.dkje = dkje;
							that.yz.mbdkje = res.zb;
							hhd = Math.round(dkje / res.zb * 10000) / 100.00
							if(hhd == 'Infinity') {
								hhd = 100
							} else if(isNaN(hhd)) {
								hhd = 0
							}
							if(hhd >= 100) hhd = 100
							hhd1 = hhd + "%"
							that.yzdx(hhd, hhd1)
							var max = Number(Math.max.apply(null, list1));
							max = (max + max * 0.2)
							var myChart = echarts.init(document.getElementById('yzOnly'));
							yzoption.xAxis.data = list;
							yzoption.yAxis.max = Math.ceil(max);
							yzoption.series[0].data = list1;
							myChart.setOption(yzoption);
							myChart.off("click");
							myChart.on('click', function(params) {
								var gxtime = res.result[params.dataIndex].time;
								var n = gxtime.split('-');
								var year = n[0];
								var month = n[1];
								var d = new Date(year, month, 0);
								var day = d.getDate();

								var startTime_1 = gxtime + '-01';
								var endTime_1 = gxtime + '-' + day
								//								that.yzl_time = startTime_1 + ',' + endTime_1
								//								that.yzl_id = that.personId()
								var html = '/static/page/index/saler_yunzuo_list2.html?startTime=' + startTime_1 + '&endTime=' + endTime_1 + '&runnerId=' + that.personId();
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
			yz_location() {
				let that = this
				var html = '/static/page/index/saler_yunzuo_list2.html?startTime=' + that.yzl_time.split(",")[0] + '&endTime=' + that.yzl_time.split(",")[1] + '&runnerId=' + that.yzl_id;
				var index = layer.open({
					type: 2,
					title: '运作到款金额明细',
					content: html,
					area: ['100%', '100%']
				});
			},
			yzdx(hhd, hhd1) {
				const that = this
				pieChartOption_yz.series[0].max = '100'
				pieChartOption_yz.series[0].data[0].value = hhd
				var nowDate = that.nowMonth()
				var isMonth = nowDate.split("-")[1]
				var isdate = new Date;
				var isYear = isdate.getFullYear();
				var startYear;
				if(isYear == that.yzmonth) {
					if(isMonth == '01' || isMonth == '02' || isMonth == '03') {
						startYear = Number(that.yzmonth) - 1
					} else {
						startYear = that.yzmonth
					}
				} else {
					startYear = that.yzmonth
				}

				var asd = document.getElementById('pie_yz');
				var pieChart = echarts.init(asd);
				pieChart.setOption(pieChartOption_yz);
				pieChart.off("click");
				pieChart.on("click", function(params) {
					var endTime = Number(startYear) + 1
					var html = '/static/page/index/saler_yunzuo_list2.html?startTime=' + startYear + '-04-01' + '&endTime=' + endTime + '-03-31' + '&runnerId=' + that.personId();
					var index = layer.open({
						type: 2,
						title: '运作到款金额明细',
						content: html,
						area: ['100%', '100%']
					});

				});
			},
			reDetail(el) {
				var type = el.remark.substr(el.remark.length - 1, 1)
				var html;
				if(type == 0) {
					html = '/static/page/customer_detail.html?id=' + el.customerId
				} else {
					html = '/static/page/customerN_detail.html?id=' + el.customerId
				}

				var index = layer.open({
					type: 2,
					title: '客户详情',
					content: html,
					area: ['100%', '100%']
				});
			},
			getNum() {
				let that = this
				$.ajax({
					type: "post",
					url: config.api_num,
					async: true,
					data: {
						flag: 0,
					},
					success: function(res) {
						that.tNum1 = res.result.total
					}
				});
				$.ajax({
					type: "post",
					url: config.api_num,
					async: true,
					data: {
						flag: 1,
					},
					success: function(res) {
						that.tNum2 = res.result.total
					}
				});
				$.ajax({
					type: "post",
					url: config.api_num,
					async: true,
					data: {
						flag: 2,
					},
					success: function(res) {
						that.tNum3 = res.result.total
					}
				});
			},

		},
	})

})
//子页面处理方法
window.parentFnc = function(params) {
	layer.closeAll();
	//更新记录
	window.app.getCaseRecord(window.app.type)
}

function a() {
	app.taskList();
	app.shen();
}