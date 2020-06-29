"use strict";
var config = {
	customerId: yo.getQueryString("id"),
	role: localStorage.userRole,
	api_getOrderDetail: "/saleCustomer/getsaleCustomerDetail",
	api_showAll: "/saleCustomer/showAll",
	api_saveLinkManInfo: "/linkman/saveLinkMan.do",
	api_saveProductAttach: "/order/saveProductAttach",
	api_updateSaleCustomer: "/saleCustomer/updateSaleCustomer",
	api_hasName: "/saleCustomer/hasName",
	api_saveLinkMan: "/linkman/saveLinkMan",
	api_updateLinkMans: "/saleCustomer/updateLinkMans",
	api_updateGjStatus: "/order/updateGjStatus",
	api_saveCallInfo: "/order/saveCallInfo",
	api_call: "/saleCustomer/call.do",
	api_updateCallInfo: "/order/updateCallInfo",
	api_moveGh: "/saleCustomer/moveGh.do",
	api_moveKh: "/saleCustomer/moveKh.do",
	api_isYxx: "/saleCustomer/isYxx",
	power: localStorage.power
};
require(["module_dialog_saleoffollow"], function(e) {
	window.app = new Vue({
		el: "#app",
		data: {
			c: {},
			type: 3,
			uploader: 0,
			role: config.role,
			citys: {},
			editParam: "",
			hoverParam: "",
			oldData: "",
			posting: !1,
			saleFollowMode: 1,
			sa: [],
			isyxx: !0,
			score: 0
		},
		computed: {
			citys_sub: function() {
				var a = this.c.province,
					n = [],
					i = void 0;
				return $.each(this.citys["省份"], function(e, t) {
					a == t.regionName && (i = t.id)
				}), $.each(this.citys["市区"], function(e, t) {
					i == t.parentId && n.push(t)
				}), n
			},
			citys_sub1: function() {
				var a = this.c.province,
					n = this.c.cityName,
					i = [],
					o = void 0,
					c = void 0;
				return console.log(n), $.each(this.citys["省份"], function(e, t) {
					a == t.regionName && (o = t.id)
				}), $.each(this.citys["市区"], function(e, t) {
					o == t.parentId && n == t.regionName && (c = t.id, console.log(c))
				}), $.each(this.citys["区"], function(e, t) {
					c == t.parentId && i.push(t)
				}), i
			},
			attachs: function() {
				var e = this.c.attachs,
					t = {
						type: []
					};
				for(var a in $.each(e, function() {
						for(var e in this) null == t[e] && (t[e] = []), t[e].push(this[e])
					}), t) t[a] = t[a].join(",");
				return t.customerId = config.customerId, t
			}
		},
		directives: {
			focus: {
				inserted: function(e) {
					e.focus(), "SELECT" == e.tagName && $(e).select2({
						language: "zh-CN",
						placeholder: "请选择"
					}).on("select2:close", function(e) {
						var t = e.target.value || app.c[app.editParam],
							a = $(this);
						if(app.c[app.editParam] == t) a.select2("destroy"), app.editParam = "";
						else {
							var n = app.editParam;
							switch(app.editParam) {
								case "province":
									app.c.cityName = "", app.c.area = "";
									break;
								case "province1":
									n = "province", app.c.area = "", app.c.cityName = "";
									break;
								case "cityName1":
									n = "cityName", app.c.cityName = "", app.c.area = "";
									break;
								case "area1":
									n = "area", app.c.area = ""
							}
							Vue.set(app.c, n, t), Vue.nextTick(function() {
								app.saveData(), a.select2("destroy"), app.editParam = ""
							})
						}
					}).select2("open")
				}
			}
		},
		created: function() {
			document.getElementById("app").classList.remove("hide"), localStorage.citys && (this.citys = JSON.parse(localStorage.citys)), this.getCaseDetail(), this.getCaseRecord(), this.yxx()
		},
		mounted: function() {
			$("#salerId").select2({
				placeholder: "请选择",
				language: "zh-CN",
				ajax: {
					url: "/user/getUserInfo",
					dataType: "json",
					type: "post",
					delay: 250,
					data: function(e) {
						return {
							page: e.page || 1,
							ROLE_ID: "7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98,90564dd8b75a4f6d815ce418462d401c,e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b",
							data: {
								q: e.term || ""
							}
						}
					},
					processResults: function(e, t) {
						return t.page = t.page || 1, $.each(e, function() {
							this.id = this.USER_ID, this.text = this.NAME
						}), {
							results: e,
							pagination: {
								more: 10 == e.length
							}
						}
					},
					cache: !0
				},
				minimumInputLength: 0
			})
		},
		methods: {
			enter: function(e, t) {
				var a = this;
				if("number" == typeof e)
					if(t.target.id) {
						if(this.editParam == t.target.id) return;
						this.hoverParam = t.target.id, this.editParam = t.target.id
					} else a.c.linkmans && a.c.linkmans[e].remark && (a.toolTips_tips = layer.yoTips('<span style="color:#337ab7">' + a.c.linkmans[e].remark + "</span>", t.target, {
						tips: [3, "#fff"],
						time: 60000
					}));
				else {
					if(this.editParam == e) return;
					this.hoverParam = e, t && a.c[e] && (a.toolTips_tips = layer.yoTips('<span style="color:#337ab7">' + a.c[e] + "</span>", t.target, {
						tips: [3, "#fff"],
						time: 60000
					}))
				}
			},

			//			enter(p, e) {
			//				const that = this;
			//				//如果返回的是数字序号
			//				if(typeof(p) == 'number') {
			//					if(e.target.id) {
			//						if(this.editParam == e.target.id) return;
			//						this.hoverParam = e.target.id;
			//						this.editParam = e.target.id;
			//					} else if(that.c.linkmans && that.c.linkmans[p].remark) {
			//						layer.tips(`<span style="color:#337ab7">${that.c.linkmans[p].remark}</span>`,e.target)
			//					}
			//				} else { //如果返回的是字符串键值
			//					if(this.editParam == p) return;
			//					this.hoverParam = p;
			//					if(e && that.c[p]) {
			//						layer.alert(`<span style="color:#337ab7">${that.c[p]}</span>`,e.target)
			//					}
			//				}
			//			},

			leave: function() {
				this.hoverParam == this.editParam && (this.hoverParam = ""), 0 < this.editParam.indexOf("_") && (this.editParam = ""), this.toolTips_tips && layer.close(this.toolTips_tips)
			},
			saveData: function(e) {
				var t = this;

				function a() {
					var e = {
						id: config.customerId
					};
					t.posting = !0, "province1" == t.editParam ? e.province = t.c.province : "cityName1" == t.editParam ? e.cityName = t.c.cityName : "area1" == t.editParam ? e.area = t.c.area : (e[t.editParam] = $.trim(t.c[t.editParam]), Vue.set(t.c, t.editParam, $.trim(t.c[t.editParam]))), t.editParam ? $.post(config.api_updateSaleCustomer, e, function(e) {
						t.editParam = "", t.posting = !1
					}) : t.posting = !1
				}
				t.posting || ("" == this.c.name && (this.c.name = this.oldData), this.oldData != this.c[this.editParam] ? "name" == this.editParam ? (t.posting = !0, $.post(config.api_hasName, {
					name: t.c[t.editParam]
				}).done(function(e) {
					if("error" == e.result) return layer.msg("客户名称已存在,请检查重试"), Vue.set(t.c, t.editParam, t.oldData), void(t.posting = !1);
					t.posting = !1, a()
				})) : a() : this.editParam = "")
			},
			change: function(e) {
				this.hoverParam = "", "province1" == (this.editParam = e) ? e = "province" : "cityName1" == e ? e = "cityName" : "area1" == e && (e = "area"), this.oldData = this.c[e]
			},
			shiftToOther: function() {
				var e = this,
					t = layer.open({
						type: 1,
						title: "转移客户",
						closeBtn: 1,
						content: $("#salerBox"),
						area: ["350px", "200px"],
						btn: "转移",
						btnAlign: "c",
						yes: function() {
							$("#salerId").val() ? yocto.json(config.api_moveKh, {
								id: [Number(e.c.id)],
								type: e.c.type,
								saleId: $("#salerId").val()
							}, function(e) {
								layer.close(t), layer.msg("转移成功"), setTimeout(function() {
									parent.app && parent.parentFnc()
								}, 2e3)
							}) : layer.close(t)
						}
					})
			},
			shiftToSea: function() {
				var t = this;
				if("经理" == this.role || "高级商务顾问S2" == this.role || "高级商务顾问S3" == this.role) var a = layer.confirm("请选择转移到的公海", {
					btn: ["有意向公海", "客户公海", "大客户公海", "无意向公海", "新三板公海", "上市公司公海"],
					btnAlign: "c",
					title: "转移公海",
					btn3: function() {
						yocto.json(config.api_moveGh, {
							id: [Number(config.customerId)],
							isKhgh: 4
						}, function(e) {
							layer.msg("转移成功"), layer.close(a), parent.parentFnc && parent.parentFnc()
						})
					},
					btn4: function() {
						yocto.json(config.api_moveGh, {
							id: [Number(config.customerId)],
							isKhgh: 1
						}, function(e) {
							layer.msg("转移成功"), layer.close(a), parent.parentFnc && parent.parentFnc()
						})
					},
					btn5: function() {
						yocto.json(config.api_moveGh, {
							id: t.checks,
							isKhgh: 7
						}, function(e) {
							layer.msg("转移成功"), layer.close(a), t.btn_checkAll(), t.list_Get(1)
						})
					},
					btn6: function() {
						yocto.json(config.api_moveGh, {
							id: t.checks,
							isKhgh: 6
						}, function(e) {
							layer.msg("转移成功"), layer.close(a), t.btn_checkAll(), t.list_Get(1)
						})
					}
				}, function() {
					t.c.gjStatus < 3 || 10 <= t.c.gjStatus ? layer.alert("只有[有意向]及以上的跟进状态客户可以转入[有意向公海]") : yocto.json(config.api_moveGh, {
						id: [Number(config.customerId)],
						isKhgh: 3
					}, function(e) {
						layer.msg("转移成功"), layer.close(a), parent.parentFnc && parent.parentFnc()
					})
				}, function() {
					yocto.json(config.api_moveGh, {
						id: [Number(config.customerId)],
						isKhgh: 0
					}, function(e) {
						layer.msg("转移成功"), layer.close(a), parent.parentFnc && parent.parentFnc()
					})
				});
				else if("高级商务顾问S1" == this.role) var n = layer.confirm("请选择转移到的公海", {
					btn: ["有意向公海", "客户公海", "大客户公海", "无意向公海", "新三板公海"],
					btnAlign: "c",
					title: "转移公海",
					btn3: function() {
						yocto.json(config.api_moveGh, {
							id: [Number(config.customerId)],
							isKhgh: 4
						}, function(e) {
							layer.msg("转移成功"), layer.close(n), parent.parentFnc && parent.parentFnc()
						})
					},
					btn4: function() {
						yocto.json(config.api_moveGh, {
							id: [Number(config.customerId)],
							isKhgh: 1
						}, function(e) {
							layer.msg("转移成功"), layer.close(n), parent.parentFnc && parent.parentFnc()
						})
					},
					btn5: function() {
						yocto.json(config.api_moveGh, {
							id: t.checks,
							isKhgh: 7
						}, function(e) {
							layer.msg("转移成功"), layer.close(n), t.btn_checkAll(), t.list_Get(1)
						})
					}
				}, function() {
					t.c.gjStatus < 3 || 10 <= t.c.gjStatus ? layer.alert("只有[有意向]及以上的跟进状态客户可以转入[有意向公海]") : yocto.json(config.api_moveGh, {
						id: [Number(config.customerId)],
						isKhgh: 3
					}, function(e) {
						layer.msg("转移成功"), layer.close(n), parent.parentFnc && parent.parentFnc()
					})
				}, function() {
					yocto.json(config.api_moveGh, {
						id: [Number(config.customerId)],
						isKhgh: 0
					}, function(e) {
						layer.msg("转移成功"), layer.close(n), parent.parentFnc && parent.parentFnc()
					})
				});
				else if("商务顾问J" == this.role || "商务助理" == this.role) var i = layer.confirm("请选择转移到的公海", {
					btn: ["有意向公海", "客户公海", "大客户公海", "无意向公海"],
					btnAlign: "c",
					title: "转移公海",
					btn3: function() {
						yocto.json(config.api_moveGh, {
							id: [Number(config.customerId)],
							isKhgh: 4
						}, function(e) {
							layer.msg("转移成功"), layer.close(i), parent.parentFnc && parent.parentFnc()
						})
					},
					btn4: function() {
						yocto.json(config.api_moveGh, {
							id: [Number(config.customerId)],
							isKhgh: 1
						}, function(e) {
							layer.msg("转移成功"), layer.close(i), parent.parentFnc && parent.parentFnc()
						})
					}
				}, function() {
					t.c.gjStatus < 3 || 10 <= t.c.gjStatus ? layer.alert("只有[有意向]及以上的跟进状态客户可以转入[有意向公海]") : yocto.json(config.api_moveGh, {
						id: [Number(config.customerId)],
						isKhgh: 3
					}, function(e) {
						layer.msg("转移成功"), layer.close(i), parent.parentFnc && parent.parentFnc()
					})
				}, function() {
					yocto.json(config.api_moveGh, {
						id: [Number(config.customerId)],
						isKhgh: 0
					}, function(e) {
						layer.msg("转移成功"), layer.close(i), parent.parentFnc && parent.parentFnc()
					})
				});
				else var o = layer.confirm("请选择转移到的公海", {
					btn: ["客户公海", "无意向公海"],
					btnAlign: "c"
				}, function() {
					yocto.json(config.api_moveGh, {
						id: [Number(config.customerId)],
						isKhgh: 0
					}, function(e) {
						layer.msg("转移成功"), layer.close(o), parent.parentFnc && parent.parentFnc()
					})
				}, function() {
					yocto.json(config.api_moveGh, {
						id: [Number(config.customerId)],
						isKhgh: 1
					}, function(e) {
						layer.msg("转移成功"), layer.close(o), parent.parentFnc && parent.parentFnc()
					})
				})
			},
			loading: function(e) {
				"close" == e ? layer.close(this.loadingSwitch) : this.loadingSwitch = layer.load(3)
			},
			getCaseDetail: function() {
				var t = this;
				t.loading(), $.ajax({
					url: config.api_getOrderDetail,
					async: !1,
					data: {
						id: config.customerId
					},
					success: function(e) {
						t.c = e.result, t.loading("close")
					}
				})
			},
			getCaseRecord: function() {
				var t = this;
				t.loading(), $.post(config.api_showAll, {
					id: config.customerId
				}).done(function(e) {
					t.loading("close"), t.sa = e.result, t.score = null == e.result[0] ? 0 : e.result[0].score
				})
			},
			linkman_add: function() {
				var a = this;
				require(["text!linkman_add"], function(e) {
					var t = layer.open({
						type: 1,
						title: "新增联系人",
						closeBtn: 1,
						content: e,
						area: ["600px", "550px"],
						btn: "保存",
						btnAlign: "c",
						yes: function() {
							require(["yoValidate"], function() {
								if(yoValidate("#linkman_add", 1)) {
									var e = {};
									$.each($("#linkman_add").find("input"), function() {
										e[this.name] = $.trim(this.value)
									}), e.customerId = config.customerId, e.type = 3, a.loading(), $.post(config.api_saveLinkManInfo, e).done(function(e) {
										a.loading("close"), a.getCaseDetail(), layer.close(t)
									})
								}
							})
						}
					})
				})
			},
			linkman_edit: function(a) {
				var n = this;
				require(["text!linkman_add"], function(e) {
					var t = layer.open({
						type: 1,
						title: "编辑联系人",
						closeBtn: 1,
						content: e,
						area: ["600px", "550px"],
						btn: "保存",
						btnAlign: "c",
						success: function() {
							$.each($("#linkman_add").find("input"), function() {
								this.value = a[this.name]
							})
						},
						yes: function() {
							require(["yoValidate"], function() {
								if(yoValidate("#linkman_add", 1)) {
									var e = {};
									$.each($("#linkman_add").find("input"), function() {
										e[this.name] = $.trim(this.value)
									}), e.id = a.id, e.customerId = config.customerId, n.loading(), $.post(config.api_updateLinkMans, e).done(function(e) {
										n.loading("close"), n.getCaseDetail(), layer.close(t)
									})
								}
							})
						}
					})
				})
			},
			initUploader: function() {
				if(!this.uploader) {
					var a = this;
					this.loading(), require(["webuploader"], function(e) {
						a.loading("close"), a.uploader = 1;
						var t = e.create({
							server: "/contract/uploadAttachment.do",
							runtimeOrder: "html5",
							pick: {
								id: "#fileUp",
								innerHTML: "上传附件"
							},
							resize: !1,
							fileNumLimit: 10
						});
						t.on("uploadSuccess", function(e, t) {
							a.c.attachs.push(t)
						}), t.on("uploadStart", function() {
							a.loading()
						}), t.on("uploadError", function(e) {
							layer.msg("上传出错")
						}), t.on("error", function(e) {
							"Q_TYPE_DENIED" == e && layer.msg("上传文件格式错误，请检查")
						}), t.on("uploadFinished", function(e) {
							t.reset(), $.post(config.api_saveProductAttach, a.attachs).done(function() {
								a.loading("close")
							})
						}), t.on("fileQueued", function() {
							t.upload()
						})
					})
				}
			},
			attachs_delete: function(e) {
				var t = this,
					a = layer.confirm("确定删除此附件?", function() {
						t.c.attachs.splice(e, 1), $.ajax({
							type: "POST",
							url: config.api_saveProductAttach,
							data: t.attachs,
							timeout: 99999
						}).done(function() {
							t.loading("close"), layer.close(a)
						})
					})
			},
			remind_add: function() {
				var a = this;
				require(["text!remind"], function(e) {
					var t = layer.open({
						type: 1,
						title: "新增提醒",
						closeBtn: 1,
						content: e,
						area: ["600px", "300px"],
						btn: "保存",
						btnAlign: "c",
						success: function() {
							laydate.render({
								elem: "#remind_time",
								type: "datetime",
								trigger: "click",
								min: a.minDate()
							})
						},
						yes: function() {
							var e = $("#remind_box");
							if("" != e.find("[name=remark]").val()) {
								if("" != e.find("[name=time]").val()) return(new Date).getTime() > new Date(e.find("[name=time]").val()).getTime() ? (layer.msg("不能小于当前时间"), !1) : void $.post(config.api_saveCallInfo, {
									customerId: config.customerId,
									customerName: a.c.name,
									remark: e.find("[name=remark]").val(),
									time: e.find("[name=time]").val()
								}).done(function(e) {
									a.getCaseDetail(), layer.close(t)
								});
								layer.msg("请填写提醒时间")
							} else layer.msg("请填写提醒内容")
						}
					})
				})
			},
			minDate: function() {
				var e = new Date;
				return e.getFullYear() + "-" + (e.getMonth() + 1) + "-" + e.getDate()
			},
			remind_complete: function(e, t) {
				var a = layer.confirm("确认完成?", function() {
					$.post(config.api_updateCallInfo, {
						id: t.id
					}).done(function(e) {
						"00" == e.error ? (t.flag = 1, layer.close(a)) : (layer.close(a), layer.msg(e.msg))
					})
				})
			},
			record_add: function() {
				console.log("111");
				this.saleFollowMode = 1, this.$refs.follow.init({
					id: config.customerId,
					name: this.c.name,
					h: this.c.type,
					score: this.score
				})
			},
			call: function(t, e, a) {
				var n = this,
					i = this;
				i.loading(), top.callId = "", $.post(config.api_call, {
					mobilePhone: e
				}).done(function(e) {
					i.loading("close"), i.saleFollowMode = 0, Vue.nextTick(function() {
						n.$refs.follow.init(t, a)
					})
				})
			},
			yxx: function() {
				var t = this;
				"电销" == config.power && $.post(config.api_isYxx, {
					id: config.customerId
				}).done(function(e) {
					"01" == e.error && (t.isyxx = !1)
				})
			}
		}
	})
}), window.parentFnc = function(e) {
	layer.closeAll(), window.app.getCaseRecord(window.app.type)
};