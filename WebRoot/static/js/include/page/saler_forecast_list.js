"use strict";var config={role:localStorage.userRole,saleId:JSON.parse(localStorage.userInfo).list[0].USER_ID,api_showSaleFroecast:"/report/showSaleFroecast",api_saveForecast:"/report/saveForecast",api_getQzkh:"/report/getQzkh",api_updateForecast:"/report/updateForecast"};window.app=new Vue({el:"#app",data:{c:{},totalMoney:0,role:config.role,isKhGh:2,postData:{},posting:!1,checkAll:0,checks:[],callId:"",s:{keywords:{},saleName:{},provinceName:"",cityName:""},select2:{}},created:function(){document.getElementById("app").classList.remove("hide"),localStorage.citys&&(this.citys=JSON.parse(localStorage.citys))},mounted:function(){if(yo.getQueryString("type")){$("[name=time]").val(yo.getQueryString("month"));var e=yo.getQueryString("saleId"),t=decodeURI(yo.getQueryString("saleName"));e&&t&&$("#saleId").html('<option value="'+e+'" selected>'+t+"</option>")}$(".date-picker").datepicker({autoclose:!0,startView:3,minViewMode:"months"}),this.list_Get(),this.salerId=this.select_init("#saleId"),this.customer_init()},methods:{clear:function(){this.salerId.val(null).trigger("change"),$(".date-picker").val("")},loading:function(e){"close"==e?layer.close(this.loadingSwitch):this.loadingSwitch=layer.load(3)},search:function(){this.postData.saleId=$.trim($("[name=saleId]").val()),this.postData.month=$.trim($("[name=time]").val()),this.list_Get(1)},list_Get:function(t){$("body,html").scrollTop(0);var a=this;t&&(this.c.pageNum=t),a.loading(),a.postData.pageSize=this.c.pageSize,a.postData.pageNo=this.c.pageNum,a.postData.month=$("#time").val(),a.postData.saleId=$("#saleId").val(),$.ajax({url:config.api_showSaleFroecast,async:!1,data:a.postData}).done(function(e){a.c=e.result,a.totalMoney=e.totalMoney,Vue.nextTick(function(){$(".tips-hook").hover(function(){var e=$(this).find("div").text();layer.yoTips('<span style="color:#337ab7">'+e+"</span>",this,{tips:[3,"#fff"]})})}),a.pagi?($(".pagi").pagination("updatePages",a.c.pages),1==t&&$(".pagi").pagination("goToPage",a.c.pageNum)):a.pagi=$(".pagi").pagination({pages:a.c.pages,showCtrl:!0,displayPage:6,currentPage:a.c.pageNum,onSelect:function(e){a.c.pageNum=e,a.list_Get()}}),a.loading("close")})},add:function(e){var t=this,a=layer.open({type:1,title:"编辑",content:$("#addBox"),area:["600px","500px"],btn:["保存"],yes:function(){require(["yoValidate"],function(){if(yoValidate("#addBox",1)){var e={};$.each($("#addBox").find("select,input,textarea"),function(){e[this.name]=this.value}),e.saleId=config.saleId,$.post(config.api_saveForecast,e,function(e){"00"==e.error?(layer.close(a),layer.msg("保存成功"),t.list_Get(1)):layer.msg(e.msg)})}})},end:function(){$('[name="date"]').datepicker("update",""),t.customer.val(null).trigger("change"),$.each($("#addBox").find("select,input,textarea"),function(){this.value=""})}})},edit:function(t){var a=this;$("#customer").html('<option value="'+t.saleCustomerId+'">'+t.saleCustomerName+"</option>"),a.customer.val(t.saleCustomerId).trigger("change"),$.each($("#addBox").find("input,textarea"),function(){this.value=t[this.name]});var i=layer.open({type:1,title:"编辑",content:$("#addBox"),area:["600px","500px"],btn:["保存"],btnAlign:"c",yes:function(){require(["yoValidate"],function(){if(yoValidate("#addBox",1)){var e={};$.each($("#addBox").find("select,input,textarea"),function(){e[this.name]=this.value}),e.saleId=config.saleId,e.id=t.id,$.post(config.api_saveForecast,e,function(e){"00"==e.error?($.each($("#addBox").find("input,textarea"),function(){this.value=""}),a.customer.val(null).trigger("change"),layer.close(i),layer.msg("保存成功"),a.list_Get(1)):layer.msg(e.msg)})}})}})},select_init:function(e){return $(e).select2({placeholder:"请选择销售",language:"zh-CN",allowClear:1,ajax:{url:"/user/getUserInfo",dataType:"json",type:"post",delay:250,data:function(e){return{page:e.page||1,ROLE_ID:"b729e9334ad64c15a4e47d88b8c2638f",data:{q:e.term||""}}},processResults:function(e,t){return t.page=t.page||1,$.each(e,function(){this.id=this.USER_ID,this.text=this.NAME}),{results:e,pagination:{more:10==e.length}}},cache:!0},minimumInputLength:0})},customer_init:function(){this.customer=$("#customer").select2({placeholder:"请选择客户",language:"zh-CN",ajax:{url:config.api_getQzkh,dataType:"json",type:"post",delay:250,data:function(e){return{page:e.page||1,saleId:config.saleId,data:{q:e.term||""}}},processResults:function(e,t){return t.page=t.page||1,$.each(e,function(){this.id=this.id,this.text=this.name}),{results:e,pagination:{more:10==e.length}}},cache:!0},minimumInputLength:0})}}}),window.parentFnc=function(e){layer.closeAll(),window.app.list_Get()};