define([
	'require'
], function(require) {
	'use strict';
	const module = Vue.component('sale-follow', {
		template: `<div class="col-sm-12 form" id="follow-box">
            <form class="m-t-sm row">
                <div class="form-group col-sm-4">
                    <select style="width:100%" v-model="type" class="form-control">
                    <option v-if="dialogMode==1" selected value="">请选择跟进方式</option>
                   <option  selected value="0">电话</option>
               	   <option v-if="dialogMode==1" value="1">QQ</option>
                   <option v-if="dialogMode==1" value="2">微信</option>
                   <option v-if="dialogMode==1" value="3">拜访</option>
                   <option v-if="dialogMode==1" value="4">邮件</option>
                   <option v-if="dialogMode==1" value="5">短信</option>
                   <option v-if="dialogMode==1" value="6">其他</option>
               </select>
                </div>
                <div class="form-group col-sm-12">
                    <textarea class="form-control" style="resize:none" v-model="content" placeholder="跟进详情" rows="6" cols=""></textarea>
                </div>
                <div class="form-group col-sm-12">
                    <div id="followFilesUploadBtn"></div>
                </div>
                <div class="form-group col-sm-12">

                <table v-if="files.length" class="table table-bordered">
                    <thead>
                        <tr>
                            <th>文件名</th>
                            <th width="180">操作</th>
</tr>
                    </thead>
                    <tbody>
                        <tr v-for="(el,index) of files">
                            <td>{{el.originalFilename}}</td>
                            <td><a target="_blank" :href="el.url" class="btn btn-primary btn-sm">
                            <i class="fa fa-eye"></i> 查看</a>
                            <a @click="files_del(index)" target="_blank" class="btn btn-danger btn-sm"><i class="fa fa-trash"></i> 删除</a></td>
                        </tr>
                    </tbody>
                </table>
                </div>
                <div class="form-group col-sm-6"> 
                    <label class="control-label col-xs-4 p-l-0 p-r-0" for="">提醒谁看 : </label>
                    <div class="col-xs-8">
                    <select style="width:100%" v-model="remind" multiple class="form-control input-sm" id="remindWho">
                    </select>
                    </div>
                </div>
                
                <div class="form-group col-sm-6"> 
                    <label class="control-label col-xs-4 p-l-0 p-r-0" for="">客户名称 : </label>
                    <div class="col-xs-8 " style="overflow:hidden;text-overflow:ellipsis;white-space: nowrap;">
                   			{{customerName}}
                    </div>
                </div>
                <div class="form-group col-sm-6" style="clear: both;"> 
                    <label class="control-label col-xs-4 p-l-0 p-r-0" for="">重要程度 : </label>
                    <div class="col-xs-8 star">
                    <img class=" htz-star" src="/static/ui/img/10icon/x2.png" @click="xx('1')"  style="width:24px;margin-right: 5px;cursor:pointer">
                    <img class=" htz-star"  src="/static/ui/img/10icon/x2.png" @click="xx('2')"  style="width:24px;margin-right: 5px;cursor:pointer">
                    <img class=" htz-star"  src="/static/ui/img/10icon/x2.png" @click="xx('3')"  style="width:24px;margin-right: 5px;cursor:pointer">
                   
                    </div>
                </div>
                
                <div class="form-group col-sm-6"> 
                    <label class="control-label col-xs-4 p-l-0 p-r-0" for="">跟进状态<span class="text-danger">*</span> : </label>
                    <div class="col-xs-8">
                                                    <select name="status" v-model="status" class="form-control">
                                    <option value="">请选择</option>
                                    <option value="0">未处理</option>
                                    <option value="1">初步沟通</option>
                                    <option value="2">无意向</option>
                                    <option value="13">潜在客户</option>
                                    <option v-if="role!='电销员工'&& role!='电销主管'&&a" value="12">有意向存疑</option>
                                    <option value="3">有意向</option>
                                    <option v-if="role!='电销员工'&& role!='电销主管'" value="4">销售外访</option>
                                    <option v-if="role!='电销员工'&& role!='电销主管'" value="5">客户来访</option>
                                    <option v-if="role!='电销员工'&& role!='电销主管'" value="6">需求确定</option>
                                    <option v-if="role!='电销员工'&& role!='电销主管'" value="7">方案/报价</option>
                                    <option v-if="role!='电销员工'&& role!='电销主管'" value="8">谈判/合同</option>
                                    <option v-if="role!='电销员工'&& role!='电销主管'" value="9">成交</option>
                                    <option v-if="role!='电销员工'&& role!='电销主管'" value="10">暂时搁置</option>
                                    <option v-if="role!='电销员工'&& role!='电销主管'" value="11">未成交</option>
                                </select>
                    </div>
                </div>
                <div class="form-group col-sm-6"> 
                    <label class="control-label col-xs-4 p-l-0 p-r-0" for="">联系人 : </label>
                    <div class="col-xs-8">
                    <input v-if="dialogMode==0" type="text" readonly v-model="linkmanName" class="form-control input-sm  p-r-0"  name="" value="">   
                    <div v-show="dialogMode==1">
                     <select style="width:100%" v-model="linkmanId" multiple class="form-control input-sm" id="linkmanId"></select>
                    </div>
                    </div>
                </div>
                <div class="form-group col-sm-6"> 
                    <label class="control-label col-xs-4 p-l-0 p-r-0" for="">下次跟进时间 : </label>
                    <div class="col-xs-8">
                    
                    <input type="text" placeholder='请填写下次跟进时间'  style="width: 100%;text-align: center;" readonly="readonly" class="form-control input-sm  p-r-0" v-model="nextTime" id="nextTime" />
                    </div>
                </div>
            </form>
        </div>
 				 `,
		props: ['dialogMode', 'callId'],
		data() {
			return {
				role: localStorage.userRole || "",
				type: 0,
				time: '',
				content: '',
				files: [],
				remind: [],
				status: '',
				customerId: '',
				customerName: '',
				linkmanName: '',
				linkmanId: [],
				nextTime: '',
				a: false,
				select_init: 0,
				score: 0,
			}
		},
		created() {
			//	this.type =this.dialogMode
		},
		mounted() {
			// dialogMode ==0 /拨打电话时弹出
			//dialogMode==1  /自行选择
			const that = this;
			//			that.$watch('callId', (n, o) => {
			//				if(n) {
			//					that.time = laydate.now(new Date().getTime(), 'YYYY-MM-DD hh:mm')
			//				}
			//			})

			laydate.render({
				elem: '#nextTime',
				type: 'datetime',
				min: that.minDate(),
				done: function(value, date) {
					that.nextTime = value;
				}
			});

		},
		methods: {
			init(customerInfo, linkmanName) {
				console.log(this.dialogMode);
				console.log(customerInfo, customerInfo.h);
				if(this.dialogMode == 1) {
					this.type = ""
				} else {
					this.type = 0
				}
				if(customerInfo.h == 1) {
					this.a = true
				}
				if(customerInfo.h == undefined && customerInfo.type == 1) {
					this.a = true
				}
				if(customerInfo.score == 0) {
					this.score = 0
					$(".htz-star").eq(0).attr('src', '/static/ui/img/10icon/x2.png')
					$(".htz-star").eq(1).attr('src', '/static/ui/img/10icon/x2.png')
					$(".htz-star").eq(2).attr('src', '/static/ui/img/10icon/x2.png')
				} else if(customerInfo.score == 1) {
					this.score = 1
					$(".htz-star").eq(0).attr('src', '/static/ui/img/10icon/x1.png')
					$(".htz-star").eq(1).attr('src', '/static/ui/img/10icon/x2.png')
					$(".htz-star").eq(2).attr('src', '/static/ui/img/10icon/x2.png')
				} else if(customerInfo.score == 2) {
					this.score = 2
					$(".htz-star").eq(0).attr('src', '/static/ui/img/10icon/x1.png')
					$(".htz-star").eq(1).attr('src', '/static/ui/img/10icon/x1.png')
					$(".htz-star").eq(2).attr('src', '/static/ui/img/10icon/x2.png')
				} else if(customerInfo.score == 3) {
					this.score = 3
					$(".htz-star").eq(0).attr('src', '/static/ui/img/10icon/x1.png')
					$(".htz-star").eq(1).attr('src', '/static/ui/img/10icon/x1.png')
					$(".htz-star").eq(2).attr('src', '/static/ui/img/10icon/x1.png')
				}

				if(customerInfo.score == undefined && customerInfo.score == 0) {
					this.score = 0
					$(".htz-star").eq(0).attr('src', '/static/ui/img/10icon/x2.png')
					$(".htz-star").eq(1).attr('src', '/static/ui/img/10icon/x2.png')
					$(".htz-star").eq(2).attr('src', '/static/ui/img/10icon/x2.png')
				} else if(customerInfo.score == undefined && customerInfo.score == 1) {
					this.score = 1
					$(".htz-star").eq(0).attr('src', '/static/ui/img/10icon/x1.png')
					$(".htz-star").eq(1).attr('src', '/static/ui/img/10icon/x2.png')
					$(".htz-star").eq(2).attr('src', '/static/ui/img/10icon/x2.png')
				} else if(customerInfo.score == undefined && customerInfo.score == 2) {
					this.score = 2
					$(".htz-star").eq(0).attr('src', '/static/ui/img/10icon/x1.png')
					$(".htz-star").eq(1).attr('src', '/static/ui/img/10icon/x1.png')
					$(".htz-star").eq(2).attr('src', '/static/ui/img/10icon/x2.png')
				} else if(customerInfo.score == undefined && customerInfo.score == 3) {
					this.score = 3
					$(".htz-star").eq(0).attr('src', '/static/ui/img/10icon/x1.png')
					$(".htz-star").eq(1).attr('src', '/static/ui/img/10icon/x1.png')
					$(".htz-star").eq(2).attr('src', '/static/ui/img/10icon/x1.png')
				}

				if(customerInfo) {
					this.customerId = customerInfo.id || "";
					this.customerName = customerInfo.name || "";
					this.linkmanName = linkmanName || customerInfo.linkmanName || "";
				}
				const that = this;
				const dialog = layer.open({
					scrollbar: true,
					type: "1",
					title: '写跟进',
					content: $("#follow-box"),
					area: ['700px', '620px'],
					btn: ['保存'],
					btnAlign: 'c',
					yes() {
						that.save()
					},
					end() {
						that.remindWhoBox.val(null).trigger('change')
						that.linkmanBox.val(null).trigger('change')
						that.content = '';
						window.callId = '';
						that.files = [];
						that.remind = [];
						that.status = '';
						that.customerId = '';
						that.customerName = '';
						that.linkmanName = '';
						that.linkmanId = [];
						that.nextTime = '';
						that.score = 0;
						$(".htz-star").eq(0).removeClass('active');
						$(".htz-star").eq(1).removeClass('active');
						$(".htz-star").eq(2).removeClass('active');
					},
					success() {
						if(!that.select_init) {
							//提醒谁看
							that.remindWhoBox = $('#remindWho').select2({
								placeholder: "请选择",
								language: 'zh-CN',
								allowClear: true,
								ajax: {
									url: "/user/getUserInfo",
									dataType: 'json',
									type: "post",
									delay: 250,
									data: function(params) {
										return {
											page: params.page || 1,
											ROLE_ID: "7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98,90564dd8b75a4f6d815ce418462d401c,e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b,04fe5e23842f4b998216080bc3b61821,4cb60182bb294cfba622f951e390bc6f ",
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
							}).on("change", function() {
								that.remind = [];
								for(var i = 0; i < this.children.length; i++) {
									if(this.children[i].selected) that.remind.push(this.children[i].value)
								}
							})
							//联系人
							that.linkmanBox = $('#linkmanId').select2({
								placeholder: "请选择",
								language: 'zh-CN',
								allowClear: true,
								ajax: {
									url: "/linkman/getLinkmanInfo",
									dataType: 'json',
									type: "post",
									delay: 250,
									data: function(params) {
										return {
											page: params.page || 1,
											data: {
												q: params.term || "" // search term
											},
											id: that.customerId,
											type: 3
										};
									},
									processResults: function(data, params) {
										params.page = params.page || 1;
										$.each(data, function() {
											this.id = this.linkmanName;
											this.text = this.linkmanName;
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
							}).on("change", function() {
								that.linkmanId = [];
								for(var i = 0; i < this.children.length; i++) {
									if(this.children[i].selected) that.linkmanId.push(this.children[i].value)
								}

							})
							that.select_init = 1
							that.$parent.loading();
							require(['webuploader'], (WebUploader) => {
								app.loading("close")
								var uploader = WebUploader.create({
									server: "/contract/uploadAttachment.do",
									runtimeOrder: 'html5',
									pick: {
										id: '#followFilesUploadBtn',
										innerHTML: '上传附件'
									},
									resize: false,
									fileNumLimit: 10
								});

								//上传成功
								uploader.on('uploadSuccess', function(file, res) {
									that.files.push(res)
								});

								uploader.on('uploadStart', function() {
									that.$parent.loading()
								})
								//上传出错
								uploader.on('uploadError', function(file) {
									layer.msg('上传出错');
								});
								//出错
								uploader.on('error', function(res) {
									if(res == 'Q_TYPE_DENIED') {
										layer.msg('上传文件格式错误，请检查')
									}
								});
								//上传完成
								uploader.on('uploadFinished', function(file) {
									uploader.reset();
									that.$parent.loading('close')
								});
								//文件加入队列
								uploader.on('fileQueued', function() {
									uploader.upload()
								})
							})
						}
					}
				});

			},
			loading: function(s) {
				if(s == "close") layer.close(this.loadingSwitch)
				else this.loadingSwitch = layer.load(3);
			},
			save() {
				const that = this;
				console.log(that.score)
				if(!top.callId && this.dialogMode == 0) {
					layer.msg('请挂断电话之后再保存记录');
					return;
				} else if(this.status === "") {
					layer.msg('请选择跟进状态');
					return;
				} else if(this.type === "") {
					layer.msg('请选择跟进方式');
					return;
				} else if(new Date().getTime() > new Date(this.nextTime).getTime()) {
					layer.msg('不能小于当前时间')
					return false
				}

				//				if(this.nextTime==''){
				//					layer.msg('请填写下次跟进时间');
				//					return;
				//				}
				if((this.status == 3 || this.status == 4 || this.status == 5 || this.status == 6 || this.status == 7 || this.status == 8) && this.score == 0) {
					layer.msg('请勾选重要程度');
					return;
				}
				let data = {
					content: this.content,
					files: this.files,
					remind: this.remind,
					status: this.status,
					customerId: this.customerId,
					nextTime: this.nextTime,
					customerName: this.customerName,
					score: this.score
				}
				if(this.dialogMode == 0) {
					data.callId = top.callId
				}
				if(this.linkmanName) {
					data.linkmanId = []
					data.linkmanId.push(this.linkmanName);
				} else {
					data.linkmanId = this.linkmanId
				}
				data.type = this.type;
				that.loading();
				//				$(".layui-layer-btn0").removeClass('layui-layer-btn0').addClass('btn-default').attr('disabled',true)
				yocto.json('/saleCustomer/saveProcessInfo', data, (res) => {
					layer.msg("销售动态保存成功")
					layer.closeAll();
					that.$parent && that.$parent.getCaseDetail && that.$parent.getCaseDetail()
					that.$parent && that.$parent.getCaseRecord && that.$parent.getCaseRecord()
					window.parentFnc && window.parentFnc()
				})
			},
			files_del(index) {
				const that = this;
				const dialog = layer.confirm("确认删除此项?", () => {
					that.files.splice(index, 1);
					layer.close(dialog)
				})
			},
			laydate(e, elm, flag) {
				const that = this
				laydate({
					vEvent: e,
					format: 'YYYY-MM-DD hh:mm:ss',
					istime: true,
					istoday: true,
					choose: function(data) {
						if(!flag) {
							that.nextTime = data
						} else {
							that.time = data
						}
					}
				})
			},
			minDate() {
				var now = new Date();
				return now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate();
			},
			xx(score) {
				let that = this
				console.log(score)
				if(score == 1) {
					if($(".htz-star").eq(0).attr('src') == '/static/ui/img/10icon/x2.png') {
						$(".htz-star").eq(0).attr('src', '/static/ui/img/10icon/x1.png')
						$(".htz-star").eq(1).attr('src', '/static/ui/img/10icon/x2.png')
						$(".htz-star").eq(2).attr('src', '/static/ui/img/10icon/x2.png')
						that.score = 1
					} else if($(".htz-star").eq(1).attr('src') == '/static/ui/img/10icon/x1.png' || $(".htz-star").eq(2).attr('src') == '/static/ui/img/10icon/x1.png') {
						$(".htz-star").eq(0).attr('src', '/static/ui/img/10icon/x1.png')
						$(".htz-star").eq(1).attr('src', '/static/ui/img/10icon/x2.png')
						$(".htz-star").eq(2).attr('src', '/static/ui/img/10icon/x2.png')
						that.score = 1
					} else {
						$(".htz-star").eq(0).attr('src', '/static/ui/img/10icon/x2.png')
						$(".htz-star").eq(1).attr('src', '/static/ui/img/10icon/x2.png')
						$(".htz-star").eq(2).attr('src', '/static/ui/img/10icon/x2.png')
						that.score = 0
					}
				} else if(score == 2) {
					that.score = 2
					$(".htz-star").eq(0).attr('src', '/static/ui/img/10icon/x1.png')
					$(".htz-star").eq(1).attr('src', '/static/ui/img/10icon/x1.png')
					$(".htz-star").eq(2).attr('src', '/static/ui/img/10icon/x2.png')
				} else if(score == 3) {
					that.score = 3
					$(".htz-star").eq(0).attr('src', '/static/ui/img/10icon/x1.png')
					$(".htz-star").eq(1).attr('src', '/static/ui/img/10icon/x1.png')
					$(".htz-star").eq(2).attr('src', '/static/ui/img/10icon/x1.png')
				}
			}
		}
	})
	return module
});