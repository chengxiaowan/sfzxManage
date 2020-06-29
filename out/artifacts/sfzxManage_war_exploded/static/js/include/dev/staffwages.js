const config = {
	isKhGh: yo.getQueryString("isKhGh") || 0, //0,客户公海,2潜在客户,3有意向公海,1无意向公海,4大客户公海
	role: localStorage.userRole,
	api_list: "/yjzb/showYgxzOrTc", //员工薪资列表
	power: localStorage.power,
}

require(['select2'], (select2) => {
	window.app = new Vue({
		el: '#app',
		data: {
			s: {
				lastSaleName: '',
				list: [],
			},
			postData: {},
			role: config.role,
			year:'',
			power: config.power,
			isKhGh: config.isKhGh,
			kind: '7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98'
		},

		computed: {},
		created() {
			const that = this;
			document.getElementById("app").classList.remove("hide")

		},

		mounted() {
			this.s.lastSaleName = this.select_init('[name=saleName]', '请选择员工', 1, this.kind)
			const that = this;
			that.year = that.FiscalYear()
			//获取列表
			$(".t1").val(that.nowMonth);
			$(".t2").val(that.nowMonth);
			$(".t3").val(that.nowMonth);
			$(".t4").val(that.nowMonth);
			$(".t6").val(that.nowMonth);
			//			
			if(config.role == '经理' || config.role == '客服') {
				that.postData.time = $(".t1").val();
				this.list_Get(0);
			}
			if(config.power == '电销') {
				that.postData.userId = that.personId()
				that.postData.time = $(".t1").val();
				this.list_Get(0);
			}
			if(config.power == '商务顾问' && config.role != '电销商务顾问') {
				that.postData.userId = that.personId()
				that.postData.time = $(".t2").val();
				this.list_Get(1);
				$("#tab-2").addClass("active")

			}
			if(config.role == '电销商务顾问') {
				that.postData.userId = that.personId()
				that.postData.time = $(".t6").val();
				this.list_Get(5);
				$("#tab-6").addClass("active")

			}
			if(config.power == '运作') {
				that.postData.userId = that.personId()
				this.list_Get(4);
				$("#tab-5").addClass("active")
			}

			lay('.test8').each(function() {
				laydate.render({
					elem: this,
					trigger: 'click',
					type: 'month',
				});
			});
			
			laydate.render({
				elem: '#year',
				type: 'year',
				done: function(value, date) {
					that.year = value;
				}
			});
			
			
		},

		methods: {
			list_Get(a) {
				const that = this;
				that.postData.flag = a;
				console.log(that.postData)
				if(a == 4){
					that.postData.timeStart = that.year+'-04-01';
					that.postData.timeEnd = Number(that.year)+1+'-03-31';
				}
				$.get(config.api_list, that.postData, function(data) { // 回调函数
					if(data.error == '00') {
						that.s.list = data.result;
					} else {
						layer.msg(data.msg)
					}

				});
			},
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
			//切换
			tab(num) {
				const that = this;
				if(num == 1) {
					//					$("#searchName2").val('');
					//					$(".t2").val('');
					//					$("#searchName3").val('');
					//					$(".t3").val('');
					//					$("#searchName4").val('');
					//					$(".t4").val('');
					//					$("#searchName5").val('');
					//					$(".t5").val('');
					//					$("#searchName6").val('');
					//					$(".t6").val('');
					that.postData.userId = '';
					that.postData.time = that.nowMonth;
					that.kind = "7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98"
					this.s.lastSaleName = this.select_init('[name=saleName]', '请选择员工', 1, that.kind);
					that.list_Get(0)
				}
				if(num == 2) {
					//					$("#searchName1").val('');
					//					$(".t1").val('');
					//					$("#searchName3").val('');
					//					$(".t3").val('');
					//					$("#searchName4").val('');
					//					$(".t4").val('');
					//					$("#searchName5").val('');
					//					$(".t5").val('');
					//					$("#searchName6").val('');
					//					$(".t6").val('');
					that.postData.userId = '';
					that.postData.time = that.nowMonth;
					that.kind = "b729e9334ad64c15a4e47d88b8c2638f,e350acd05a6244c79136616302b7dfd6,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b"
					this.s.lastSaleName = this.select_init('[name=saleName]', '请选择员工', 1, that.kind);
					that.list_Get(1);
				}
				if(num == 3) {
					//					$("#searchName1").val('');
					//					$(".t1").val('');
					//					$("#searchName2").val('');
					//					$(".t2").val('');
					//					$("#searchName4").val('');
					//					$(".t4").val('');
					//					$("#searchName5").val('');
					//					$(".t5").val('');
					//					$("#searchName6").val('');
					//					$(".t6").val('');
					that.postData.userId = '';
					that.postData.time = that.nowMonth;
					that.kind = "fbe6f2f9535c4fce9f024f6cb999b2bd";
					this.s.lastSaleName = this.select_init('[name=saleName]', '请选择员工', 1, that.kind);
					that.list_Get(2);
				}
				if(num == 4) {
					//					$("#searchName1").val('');
					//					$(".t1").val('');
					//					$("#searchName2").val('');
					//					$(".t2").val('');
					//					$("#searchName3").val('');
					//					$(".t3").val('');
					//					$("#searchName5").val('');
					//					$(".t5").val('');
					//					$("#searchName6").val('');
					//					$(".t6").val('');
					that.postData.userId = '';
					that.postData.time = that.nowMonth;
					that.kind = "04fe5e23842f4b998216080bc3b61821";
					this.s.lastSaleName = this.select_init('[name=saleName]', '请选择员工', 1, that.kind);
					that.list_Get(3);
				}
				if(num == 5) {
					//					$("#searchName1").val('');
					//					$(".t1").val('');
					//					$("#searchName2").val('');
					//					$(".t2").val('');
					//					$("#searchName3").val('');
					//					$(".t3").val('');
					//					$("#searchName4").val('');
					//					$(".t4").val('');
					//					$("#searchName6").val('');
					//					$(".t6").val('');
					that.postData.userId = '';
					that.postData.time = '';
					that.kind = "02178e62f17b4926bb7014f3ad5a1ebe,b729e9334ad64c15a4e47d88b8c2638f,e350acd05a6244c79136616302b7dfd6,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b";
					this.s.lastSaleName = this.select_init('[name=saleName]', '请选择员工', 1, that.kind);
					that.list_Get(4)
				}
				if(num == 6) {
					//					$("#searchName1").val('');
					//					$(".t1").val('');
					//					$("#searchName2").val('');
					//					$(".t2").val('');
					//					$("#searchName3").val('');
					//					$(".t3").val('');
					//					$("#searchName4").val('');
					//					$(".t4").val('');
					//					$("#searchName5").val('');
					//					$(".t5").val('');
					that.postData.userId = '';
					that.postData.time = that.nowMonth;
					that.kind = "90564dd8b75a4f6d815ce418462d401c";
					this.s.lastSaleName = this.select_init('[name=saleName]', '请选择员工', 1, that.kind);
					that.list_Get(5)
				}
			},
			//搜索
			search1() {
				const that = this;
				if(config.role == '经理' || config.role == '客服') {
					that.postData.userId = $("#searchName1").val();
				} else {
					that.postData.userId = that.personId();
				}

				that.postData.time = $(".t1").val();
				that.list_Get(0)
			},
			search2() {
				const that = this;
				if(config.role == '经理' || config.role == '客服') {
					that.postData.userId = $("#searchName2").val();
				} else {
					that.postData.userId = that.personId();
				}
				that.postData.time = $(".t2").val();
				that.list_Get(1)
			},
			search3() {
				const that = this;
				if(config.role == '经理' || config.role == '客服') {
					that.postData.userId = $("#searchName3").val();
				} else {
					that.postData.userId = that.personId();
				}
				that.postData.time = $(".t3").val();
				that.list_Get(2)
			},
			search4() {
				const that = this;
				if(config.role == '经理' || config.role == '客服') {
					that.postData.userId = $("#searchName4").val();
				} else {
					that.postData.userId = that.personId();
				}
				that.postData.time = $(".t4").val();
				that.list_Get(3)
			},
			search5() {
				const that = this;
				if(config.role == '经理' || config.role == '客服') {
					that.postData.userId = $("#searchName5").val();
				} else {
					that.postData.userId = that.personId();
				}
				that.list_Get(4)
			},
			search6() {
				const that = this;
				if(config.role == '经理' || config.role == '客服') {
					that.postData.userId = $("#searchName6").val();
				} else {
					that.postData.userId = that.personId();
				}
				that.postData.time = $(".t6").val();
				that.list_Get(5)
			},
			personId() {
				var userInfo = localStorage.getItem("userInfo");
				userInfo = $.parseJSON(userInfo)
				var saleId = userInfo.list[0].USER_ID;
				return saleId
			},
			//获取当前月
			nowMonth() {
				var date = new Date;
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				month = (month < 10 ? "0" + month : month);
				var nowDate = (year.toString() + '-' + month.toString());
				return nowDate;
			},
			//获取当前月和财年
			FiscalYear() {
				var nowYear;
				var date = new Date;
				var year = date.getFullYear();
				var month = date.getMonth() + 1;
				if(month == '01' || month == '02' || month == '03'){
					nowYear = Number(year)-1
				}else{
					nowYear = year
				}
				return nowYear;
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
		}
	})

})
//子页面处理方法
window.parentFnc = function(params) {
	layer.closeAll();
	//更新记录
	window.app.getCaseRecord(window.app.type)
}