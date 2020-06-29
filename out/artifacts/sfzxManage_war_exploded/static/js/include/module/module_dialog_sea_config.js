"use strict";var _typeof="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t};define(["require","select2","yoValidate"],function(t,n,e){return Vue.component("sea-config",{template:'\n\t\t<div class="col-sm-12 form form-horizontal m-t-sm" :id="addBox">\n            <div class="row">\n\t\t\t\t\t\t\t<div class="col-md-12">\n\t\t\t\t\t\t\t\t<div class="form-group" style="margin-bottom: 25px;">\n\t\t\t\t\t\t\t\t\t<label class="col-sm-2 control-label"  style="text-align: left;width:12%">公海成员<span class="text-danger">*</span>：</label>\n\t\t\t\t\t\t\t\t\t<div class="col-sm-10" style="width:88%">\n\t\t\t\t\t\t\t\t\t<select style="width:100%" name="" v-model="c.saleIds" multiple :id="selectBox">\n\t\t\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div class="col-md-12">\n\t\t\t\t\t\t\t\t<div class="form-group" style="margin-bottom: 25px;">\n\t\t\t\t\t\t\t\t\t<label class="col-sm-2 control-label" style="text-align: left;width:12%">划入规则<span class="text-danger">*</span>：</label>\n\t\t\t\t\t\t\t\t\t<div class="col-sm-10" style="width:88%">\n\t\t\t\t\t\t\t\t\t\t<table class="table table-bordered table-hover" style="margin-bottom:0">\n\t\t\t\t\t\t\t\t\t\t<thead>\n  \t\t\t\t\t\t\t\t\t\t\t<tr>\n    \t\t\t\t\t\t\t\t\t\t\t<th>客户</th>\n    \t\t\t\t\t\t\t\t\t\t\t<th>未跟进天数</th>\n    \t\t\t\t\t\t\t\t\t\t\t<th>划入客户公海时间</th>\n  \t\t\t\t\t\t\t\t\t\t\t</tr>\n  \t\t\t\t\t\t\t\t\t\t</thead>\n  \t\t\t\t\t\t\t\t\t\t<tbody>\n  \t\t\t\t\t\t\t\t\t\t\t<tr>\n    \t\t\t\t\t\t\t\t\t\t\t<td>所有客户</td>\n    \t\t\t\t\t\t\t\t\t\t\t<td>超过<input type="number" style="width: 80px;margin:0 5px;display: inline-block;"  class="form-control" v-model="c.days"  value="">天未跟进</td>\n    \t\t\t\t\t\t\t\t\t\t\t<td>系统会在每天的\n    \t\t\t\t\t\t\t\t\t\t\t<select class="form-control" v-model="c.time" style="width: 100px;margin:0 5px;display: inline-block;">\n\t\t\t\t\t\t\t\t\t<option value="00:00">00:00</option>\n\t\t\t\t\t\t\t\t\t<option value="00:30">00:30</option>\n\t\t\t\t\t\t\t\t\t<option value="01:00">01:00</option>\n\t\t\t\t\t\t\t\t\t<option value="01:30">01:30</option>\n\t\t\t\t\t\t\t\t\t<option value="02:00">02:00</option>\n\t\t\t\t\t\t\t\t\t<option value="02:30">02:30</option>\n\t\t\t\t\t\t\t\t\t<option value="03:00">03:00</option>\n\t\t\t\t\t\t\t\t\t<option value="03:30">03:30</option>\n\t\t\t\t\t\t\t\t\t<option value="04:00">04:00</option>\n\t\t\t\t\t\t\t\t\t<option value="04:30">04:30</option>\n\t\t\t\t\t\t\t\t\t<option value="05:00">05:00</option>\n\t\t\t\t\t\t\t\t\t<option value="05:30">05:30</option>\n\t\t\t\t\t\t\t\t\t<option value="06:00">06:00</option>\n\t\t\t\t\t\t\t\t\t<option value="06:30">06:30</option>\n\t\t\t\t\t\t\t\t\t<option value="07:00">07:00</option>\n\t\t\t\t\t\t\t\t\t<option value="07:30">07:30</option>\n\t\t\t\t\t\t\t\t\t<option value="08:00">08:00</option>\n\t\t\t\t\t\t\t\t\t<option value="08:30">08:30</option>\n\t\t\t\t\t\t\t\t\t<option value="09:00">09:00</option>\n\t\t\t\t\t\t\t\t\t<option value="09:30">09:30</option>\n\t\t\t\t\t\t\t\t\t<option value="10:00">10:00</option>\n\t\t\t\t\t\t\t\t\t<option value="10:30">10:30</option>\n\t\t\t\t\t\t\t\t\t<option value="11:00">11:00</option>\n\t\t\t\t\t\t\t\t\t<option value="11:30">11:30</option>\n\t\t\t\t\t\t\t\t\t<option value="12:00">12:00</option>\n\t\t\t\t\t\t\t\t\t<option value="12:30">12:30</option>\n\t\t\t\t\t\t\t\t\t<option value="13:00">13:00</option>\n\t\t\t\t\t\t\t\t\t<option value="13:30">13:30</option>\n\t\t\t\t\t\t\t\t\t<option value="14:00">14:00</option>\n\t\t\t\t\t\t\t\t\t<option value="14:30">14:30</option>\n\t\t\t\t\t\t\t\t\t<option value="15:00">15:00</option>\n\t\t\t\t\t\t\t\t\t<option value="15:30">15:30</option>\n\t\t\t\t\t\t\t\t\t<option value="16:00">16:00</option>\n\t\t\t\t\t\t\t\t\t<option value="16:30">16:30</option>\n\t\t\t\t\t\t\t\t\t<option value="17:00">17:00</option>\n\t\t\t\t\t\t\t\t\t<option value="17:30">17:30</option>\n\t\t\t\t\t\t\t\t\t<option value="18:00">18:00</option>\n\t\t\t\t\t\t\t\t\t<option value="18:30">18:30</option>\n\t\t\t\t\t\t\t\t\t<option value="19:00">19:00</option>\n\t\t\t\t\t\t\t\t\t<option value="19:30">19:30</option>\n\t\t\t\t\t\t\t\t\t<option value="20:00">20:00</option>\n\t\t\t\t\t\t\t\t\t<option value="20:30">20:30</option>\n\t\t\t\t\t\t\t\t\t<option value="21:00">21:00</option>\n\t\t\t\t\t\t\t\t\t<option value="21:30">21:30</option>\n\t\t\t\t\t\t\t\t\t<option value="22:00">22:00</option>\n\t\t\t\t\t\t\t\t\t<option value="22:30">22:30</option>\n\t\t\t\t\t\t\t\t\t<option value="23:00">23:00</option>\n\t\t\t\t\t\t\t\t\t<option value="23:30">23:30</option>\n\t\t\t\t\t\t\t\t\t</select>\n    \t\t\t\t\t\t\t\t\t\t\t\n    \t\t\t\t\t\t\t\t\t\t\t将超过未跟进天数的客户划入客户公海</td>\n  \t\t\t\t\t\t\t\t\t\t\t</tr>\n  \t\t\t\t\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<div class="col-md-12">\n\t\t\t\t\t\t\t\t<div class="form-group" style="margin-bottom: 25px;" >\n\t\t\t\t\t\t\t\t\t<label class="col-sm-2 control-label" style="text-align: left;width:12%">抢回限制<span class="text-danger">*</span>：</label>\n\t\t\t\t\t\t\t\t\t<div class="col-sm-10" style="width:88%">\n\t\t\t\t\t\t\t\t\t<div class="btn-group btn-toggle" style="margin:0 5px;"> \n    \t\t\t\t\t\t\t\t\t<button class="btn btn-default">启用</button>\n   \t\t\t\t\t\t\t\t\t\t<button class="btn btn-primary active">停用</button>\n  \t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t<input type="number" style="width: 80px;margin:0 5px;display: inline-block;"  class="form-control" v-model="c.cfdays"  value="">\n\t\t\t\t\t\t\t\t\t天内不能连续"抢"同一个客户\n\t\t\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t<div class="col-sm-10" style="margin-top:10px;color:#666;text-indent: 12.2%;">\n\t\t\t\t\t\t\t\t\t <span class="text-danger" style="color:#96c2ef">*</span>客户被转移或者系统定时划入客户公海后，前负责人在N天内不能抢回\n\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t</div>\n        </div>\n  ',data:function(){return{api_saveTasks:"/qzTask/saveTasks.do",api_updateTasks:"/qzTask/updateTasks.do",saleId:"",editEl:{},init_add:0,addBox:"addBox"+Math.random().toString().split(".")[1],selectBox:"selectBox"+Math.random().toString().split(".")[1],select2:{select:{}},c:{id:"",saleIds:[],saleNames:[],time:"00:00",days:"",type:"0",cfdays:"",isOpen:"0"}}},computed:{saleInfo:function(){for(var t=[],n=0;n<this.c.saleIds.length;n++)t.push({id:this.c.saleIds[n],name:this.c.saleNames[n]});return t}},created:function(){},mounted:function(){this.select_init(),this.qiehuan()},methods:{init:function(t){var n=this;if(t){this.editEl=t,this.c.saleIds=t.saleIds.split(","),this.c.saleNames=t.saleNames.split(","),this.c.id=t.id,this.c.type=t.type,this.c.time=t.time,this.c.days=t.days,this.c.cfdays=t.cfdays,this.c.isOpen=t.isOpen;var e="",o=void 0;1==t.isOpen?o=0:0==t.isOpen&&(o=1),$(".btn-toggle button").eq(o).addClass("btn-primary active").removeClass("btn-default").siblings().removeClass("btn-primary active").addClass("btn-default");for(var i=0;i<this.c.saleIds.length;i++)e+='<option selected value="'+this.c.saleIds[i]+'">'+this.c.saleNames[i]+"</option>";n.select2.select.html(e),this.select_init();var a=layer.open({type:1,title:"编辑规则",content:$("#"+n.addBox),area:["920px","400px"],btn:["保存"],btnAlign:"c",yes:function(){n.save(function(){layer.close(a),window.parentFnc&&window.parentFnc()})},end:function(){n.editEl={},n.clear()}})}else{this.select_init();var s=layer.open({type:1,title:"新增规则",content:$("#"+n.addBox),area:["920px","450px"],btn:["保存"],btnAlign:"c",yes:function(){n.save(function(){layer.close(s),window.parentFnc&&window.parentFnc()})},end:function(){n.editEl={},n.clear()}})}},clear:function(){this.c.id="",this.c.saleIds=[],this.c.saleNames=[],this.c.time="00:00",this.c.days="",this.c.type="0",this.c.cfdays="",this.c.isOpen=0,this.select2.select.select2("destroy"),this.select2.select.find("option").remove()},select_init:function(){var n=this;n.select2.select=$("#"+this.selectBox).select2({placeholder:"请选择公海成员",language:"zh-CN",allowClear:!0,ajax:{url:"/user/getUserInfo",dataType:"json",type:"post",delay:250,data:function(t){return{page:t.page||1,ROLE_ID:"7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98,90564dd8b75a4f6d815ce418462d401c,e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b",data:{q:t.term||""}}},processResults:function(t,n){return n.page=n.page||1,$.each(t,function(){this.id=this.USER_ID,this.text=this.NAME}),{results:t,pagination:{more:10==t.length}}},cache:!0},minimumInputLength:0}).on("change",function(){n.c.saleIds=[],n.c.saleNames=[];for(var t=0;t<this.children.length;t++)this.children[t].selected&&(n.c.saleIds.push(this.children[t].value),n.c.saleNames.push(this.children[t].innerHTML))})},save:function(n){var e=this,t=this;if(t.c.saleIds.length)if(t.c.days&&/^[0-9]\d*$/.test(t.c.days))if(t.c.cfdays&&/^[0-9]\d*$/.test(t.c.cfdays)){this.$parent.loading();var o=JSON.parse(JSON.stringify(t.$data.c));for(var i in o)o.hasOwnProperty(i)&&"object"==_typeof(o[i])&&(o[i]=o[i].join(","));this.editEl.id?$.post(t.api_updateTasks,o).done(function(t){e.$parent.loading("close"),n&&n()}):$.post(t.api_saveTasks,o).done(function(t){e.$parent.loading("close"),n&&n()})}else layer.msg("抢回限制天数格式不正确");else layer.msg("划入公海天数格式不正确");else layer.msg("请选择公海成员")},laydate:function(e){function t(t,n){return e.apply(this,arguments)}return t.toString=function(){return e.toString()},t}(function(t,n){var e=this;laydate({vEvent:t,format:"YYYY-MM-DD",istime:!0,istoday:!0,choose:function(t){e.c.xcgetTime=t}})}),qiehuan:function(){var e=this;$(".btn-toggle").click(function(){var t=$(this);0<t.find(".btn-primary").length&&t.find(".btn").toggleClass("btn-primary"),t.find(".btn").toggleClass("btn-default").toggleClass("active");var n=t.find(".active").text();e.c.isOpen="启用"==n?1:0})}}})});