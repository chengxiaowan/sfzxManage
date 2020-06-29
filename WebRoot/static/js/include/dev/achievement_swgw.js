const config = {
	id: yo.getQueryString("id"),
	type: yo.getQueryString("type"),
	index: yo.getQueryString("index"),
	year: yo.getQueryString("year"),
	name: yo.getQueryString("name"),
	role: localStorage.userRole,
	api_detail: '/yjzb/goSecondYm',
}

//时间选择初始化
require(['dateTimeInit'])

require(['select2'], (select2) => {
	window.app = new Vue({
		el: '#app',
		data: {
			c: {
				list: [],
			},
			dtype: '',
			postData: {},
			saleRole: {},
		},

		computed: {

		},
		created() {

		},

		mounted() {
			const that = this;
			//			数据初始化
			this.list_Get();
		},

		methods: {
			//	详情数据初始化
			list_Get() {
				const that = this;
				$.get(config.api_detail, {
					type: config.type,
				}, function(data) { // 回调函数
					if(data.error == '00') {
						that.c.list = data.result;
					}

				});
			},
			edit(el){
				var index = layer.open({
					type: 2,
					title: config.year + '年度' + decodeURI(config.name)+'-'+el.name,
					closeBtn: 1,
					content: 'achievement_edit1.html?userId=' + el.userId + '&type=' + config.type + '&parentId=' + config.id,
					area: ['100%', '100%']
				});
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

function colseThis() {
	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	parent.layer.close(index); //再执行关闭
}