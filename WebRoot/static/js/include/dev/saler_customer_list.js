var config = {
			role: localStorage.userRole || "",
			power: localStorage.power || "",
			saleId: yo.getQueryString("saleId"),
			dfSaleId: yo.getQueryString("dfSaleId"),
			dfSaleName: decodeURIComponent(yo.getQueryString("dfSaleName")) || "",
			saleName: decodeURIComponent(yo.getQueryString("saleName")) || "",
			startTime: yo.getQueryString("startTime") || "",
			endTime: yo.getQueryString("endTime") || "",
			api_showDetail: '/saleCustomer/showSaleCustomer', //查询
		}

		window.app = new Vue({
			el: '#app',
			data: {
				role: config.role,
				power: config.power,
				c: []
			},
			created: function() {
				var that = this;
				document.getElementById("app").classList.remove("hide");
			},
			mounted: function() {
				//			tips
				$(document).on('mouseenter', ".showTips", function() {
					var tips = $(this).find('i').text()
					var that = this
					layer.yoTips(`<span style="color:#337ab7">${tips}</span>`, that, {
						tips: [2, '#fff'],
						time: 6000
					})
				})

				$('.date-picker').datepicker({
					autoclose: true,
					todayHighlight: true
				});
				$("#startTime").val(config.startTime)
				$("#endTime").val(config.endTime)
				if($("#startTime").val()) {
					$("#endTime").datepicker('setStartDate',
						new Date($("#startTime").val()))
				}

				if($("#endTime").val()) {
					$("#startTime").datepicker('setEndDate',
						new Date($("#endTime").val()))
				}

				//开始日期
				$("#startTime").datepicker({}).on(
					'changeDate',
					function(ev) {
						if(ev.date) {
							$("#endTime").datepicker('setStartDate',
								new Date(ev.date.valueOf()))
						} else {
							$("#endTime").datepicker('setStartDate', null);
						}
					});
				//结束日期
				$("#endTime").datepicker({}).on(
					'changeDate',
					function(ev) {
						if(ev.date) {
							$("#startTime").datepicker('setEndDate',
								new Date(ev.date.valueOf()))
						} else {
							$("#startTime").datepicker('setEndDate',
								new Date());
						}
					});

				var roleId = ""
				if(config.role == '经理') {
					$('#saleId').html('"<option value="' + config.saleId + '">' + config.saleName + '</option>"')
					roleId = "7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98,90564dd8b75a4f6d815ce418462d401c,e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b"
				} else if(config.role == '电销主管') {
					$('#saleId').html('"<option value="' + config.saleId + '">' + config.saleName + '</option>"')
					roleId = "7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98"
				}

				//				if(config.dfSaleId) {
				//					$('#saleId').html('"<option value="' + config.dfSaleId + '">' + config.dfSaleName + '</option>"')
				//					roleId = "30b1487221464d369ca4c2462ccca920"
				//				} else {
				//					$('#saleId').html('"<option value="' + config.saleId + '">' + config.saleName + '</option>"')
				//					roleId = "7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98"
				//				}
				$('#saleId').select2({
					placeholder: "请选择销售",
					language: 'zh-CN',
					allowClear: 1,
					ajax: {
						url: "/user/getUserInfo",
						dataType: 'json',
						type: "post",
						delay: 250,
						data: function(params) {
							return {
								page: params.page || 1,
								ROLE_ID: roleId,
								data: {
									q: params.term || "", // search term
								}
							};
						},
						processResults: function(data, params) {
							params.page = params.page || 1;
							$.each(data, function() {
								this.id = this.USER_ID;
								this.text = this.NAME;
							})
							return {
								results: data,
								pagination: {
									more: data.length == 10
								}
							};
						},
						cache: true
					},
					minimumInputLength: 0
				})
				this.getData()
			},
			methods: {
				/**
				 * 载入中方法
				 * 
				 * @param {string} s 是否关闭
				 */
				loading: function(s) {
					if(s == "close") layer.close(this.loadingSwitch)
					else this.loadingSwitch = layer.load(3);
				},
				getData: function(page) {
					$('body,html').scrollTop(0)
					if(page) this.c.pageNum = page
					var that = this;
					that.loading();
					var data = {
						startTime: $('[name=startTime]').val(),
						endTime: $('[name=endTime]').val(),
						type: 99,
						pageSize: that.c.pageSize || 10,
						pageNo: that.c.pageNum || 1,
						tDfsaleId: config.dfSaleId
					}

					if(config.dfSaleId) {
						data.dfSaleId = $('#saleId').val()
					} else {
						data.saleId = $('#saleId').val()
					}
					if(config.role == '电销主管') {
						data.tDfsaleId = $('#saleId').val();
						data.dfSaleId = '';
						data.saleId = '';
					}
//					if(config.role == '电销员工') {
//						console.log('11')
//						data.tDfsaleId = '';
//						data.dfSaleId = that.personId();
//						data.saleId = '';
//					}
//					if(config.power == '商务顾问') {
//						data.tDfsaleId = '';
//						data.dfSaleId = '';
//						data.saleId = that.personId();
//					}
					console.log(data);
					$.ajax({
						url: config.api_showDetail,
						async: false,
						data: data,
						success: function(res) {
							that.loading('close')
							if(res.error == "00") {
								that.c = res.result;
								//分页
								if(that.pagi) {
									$('.pagi').pagination('updatePages', that.c.pages)
									if(page == 1) $('.pagi').pagination('goToPage', that.c.pageNum)
								} else {
									that.pagi = $('.pagi').pagination({
										pages: that.c.pages, //总页数
										showCtrl: true,
										displayPage: 6,
										currentPage: that.c.pageNum,
										onSelect: function(num) {
											that.c.pageNum = num
											that.getData()
											yo.scrollTo('body')
										}
									})
								}
							} else {
								layer.msg(res.msg)
							}
						}
					});
				},
				openSalerList: function(id) {
					salerList(id);
				},
				//查看客户详情
				viewCustomer: function(id) {
					const dialog = layer.open({
						type: 2,
						title: '客户详情',
						content: '/static/page/customer_detail.html?id=' + id,
						area: ['100%', '100%'],
					})
				},
				personId() {
					var userInfo = localStorage.getItem("userInfo");
					userInfo = $.parseJSON(userInfo)
					var saleId = userInfo.list[0].USER_ID;
					return saleId
				}
			}
		})