"use strict";var config={flag:yo.getQueryString("flag"),role:localStorage.userRole||"",api_showDetail:"/main/xszs",api_hasName:"/saleCustomer/hasName",api_updateSaleCustomer:"/saleCustomer/updateSaleCustomer",api_call:"/saleCustomer/call.do"};require(["module_dialog_saleoffollow","module_dialog_importExcel"],function(e,a){window.app=new Vue({el:"#app",data:{flag:config.flag,role:config.role,c:[],callId:"",hoverEl:{name:{show:0,el:{}},linkmanLandline:{show:0,el:{}},linkmanMobilePhone:{show:0,el:{}},email:{},postion:{show:0,el:{}},linkmanName:{show:0,el:{}},remark:{show:0,el:{}}},editEl:{show:"",el:{},oldValue:""},saleFollowMode:0},created:function(){document.getElementById("app").classList.remove("hide")},mounted:function(){this.getData(),$(document).on("mouseover",".remark-hook",function(){var e=$(this).find(".hide").text();e&&layer.yoTips('<span style="color:#337ab7">'+e+"</span>",this,{tips:[2,"#fff"]})})},directives:{focus:{inserted:function(e){e.focus(),e.dataset.tips&&layer.yoTips('<span style="color:#337ab7">'+e.dataset.tips+"</span>",e,{tips:[2,"#fff"]})}}},methods:{loading:function(e){"close"==e?layer.close(this.loadingSwitch):this.loadingSwitch=layer.load(3)},getData:function(a){var e,o=this;"经理"!=o.role&&0==o.flag&&(e=o.personId()),a&&(this.c.pageNum=a),o.loading(),$.ajax({url:config.api_showDetail,async:!1,data:{flag:config.flag,pageSize:o.c.pageSize||10,page:o.c.pageNum||1,saleId:e},success:function(e){"00"==e.error?(o.c=e.result,o.pagi?($(".pagi").pagination("updatePages",o.c.pages),1==a&&$(".pagi").pagination("goToPage",o.c.pageNum)):require(["pagination"],function(e){o.pagi=$(".pagi").pagination({pages:o.c.pages,showCtrl:!0,displayPage:6,currentPage:o.c.pageNum,onSelect:function(e){o.c.pageNum=e,o.getData(),yo.scrollTo("body")}})}),o.loading("close")):layer.msg(e.msg)}})},edit_hover:function(e,a){Vue.set(this.hoverEl,e,{show:1,el:a})},edit_el:function(e,a){this.editEl.show=e,this.editEl.el=a,this.editEl.oldValue=a.name},edit_submit:function(e,a){console.log(2),this.editEl.show="",this.editEl.el={},Vue.set(this.hoverEl,e,{show:0,el:{}}),this.saveData(e,a)},edit_keyup:function(e,a){this.editEl.show="",this.editEl.el={},Vue.set(this.hoverEl,e,{show:0,el:{}})},overTips:function(e){layer.yoTips('<span style="color:#337ab7">'+e.remark+"</span>","#remark_"+e.id,{tips:[2,"#fff"]})},saveData:function(a,o){var i=this;function t(){var e={id:o.id};e[a]=$.trim(o[a]),$.post(config.api_updateSaleCustomer,e,function(e){})}"name"!=a||""!=o.name?"name"==a&&o.name==this.editEl.oldValue||("name"==a?$.post(config.api_hasName,{name:$.trim(o.name)}).done(function(e){if("error"==e.result)return layer.msg("客户名称已存在,请检查重试"),void(o.name=i.editEl.oldValue);t()}):t()):o.name=this.editEl.oldValue},call:function(a,e){var o=this,i=this;this.hoverEl.linkmanMobilePhone.show=0,this.hoverEl.linkmanLandline.show=0,i.loading(),top.callId="",$.post(config.api_call,{mobilePhone:e}).done(function(e){i.loading("close"),i.saleFollowMode=0,Vue.nextTick(function(){o.$refs.follow.init(a)})})},edit_saleRecord:function(e){var a=this;a.saleFollowMode=1,Vue.nextTick(function(){a.$refs.follow.init({id:e.id,name:e.name,h:e.type})})},customer_detail:function(e){layer.open({type:2,title:" 客户详情",content:"/static/page/customer_detail.html?id="+e.id,area:["100%","100%"]})},personId:function(){var e=localStorage.getItem("userInfo");return(e=$.parseJSON(e)).list[0].USER_ID}}})}),window.parentFnc=function(e){layer.closeAll(),window.app.list_Get()};