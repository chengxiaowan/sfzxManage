"use strict";var config={customerId:yo.getQueryString("id"),role:localStorage.userRole,api_getOrderDetail:"/contract/goEdit1",api_xgOrderDetail:"/contract/doEdit1",type:yo.getQueryString("type"),api_costomerList:"/customer/getCustomerInfo",api_costomerList1:"/user/getUserInfo",api_chufa:"/contract/getDebtorInfo1",api_showAll:"/saleCustomer/showAll",api_saveLinkManInfo:"/linkman/saveLinkMan.do",api_saveProductAttach:"/order/saveProductAttach",api_updateSaleCustomer:"/saleCustomer/updateSaleCustomer",api_hasName:"/saleCustomer/hasName",api_saveLinkMan:"/linkman/saveLinkMan",api_updateLinkMans:"/saleCustomer/updateLinkMans",api_updateGjStatus:"/order/updateGjStatus",api_saveCallInfo:"/order/saveCallInfo",api_call:"/saleCustomer/call.do",api_updateCallInfo:"/order/updateCallInfo",api_isYxx:"/saleCustomer/isYxx"};require(["module_dialog_saleoffollow"],function(e){window.app=new Vue({el:"#app",data:{c:{},type:3,uploader:0,role:config.role,editParam:"",hoverParam:"",oldData:"",posting:!1,saleFollowMode:1,sa:[],customerNameList:[],userNameList:[],debtorNameList:[],swwxNameList:[],orderInfo:[],userName:"",customerName:"",swwxName:"",hoverEl:{contractNo:{show:0,el:{}},customerName:{show:0,el:{}},debtorName:{show:0,el:{}},signingTime:{},endTime:{show:0,el:{}},address:{show:0,el:{}},price:{show:0,el:{}},commissionRate:{show:0,el:{}},userName:{show:0,el:{}},remark:{show:0,el:{}}},editEl:{show:"",el:{},oldValue:""},cId:""},watch:{"c.signingTime":function(e){this.saveData()},"c.endTime":function(e){this.saveData()}},computed:{attachs:function(){var e=this.c.attachs,a={type:[]};for(var t in $.each(e,function(){for(var e in this)null==a[e]&&(a[e]=[]),a[e].push(this[e])}),a)a[t]=a[t].join(",");return a.contractId=config.customerId,a}},directives:{focus:{inserted:function(e){e.focus(),"SELECT"==e.tagName&&$(e).select2({language:"zh-CN",placeholder:"请选择"}).on("select2:close",function(e){var a=e.target.value||app.c[app.editParam],t=$(this);if(app.c[app.editParam]==a)t.select2("destroy"),app.editParam="";else{var i=app.editParam;Vue.set(app.c,i,a),Vue.nextTick(function(){app.saveData(),t.select2("destroy"),app.editParam=""})}}).select2("open")}}},created:function(){document.getElementById("app").classList.remove("hide"),this.getCaseDetail()},mounted:function(){this.customerName1(),this.customerName2(),this.customerName3()},methods:{enter:function(e,a){var t=this;if("number"==typeof e)if(a.target.id){if(this.editParam==a.target.id)return;this.hoverParam=a.target.id,this.editParam=a.target.id}else t.c.linkmans&&t.c.linkmans[e].remark&&(t.toolTips_tips=layer.yoTips('<span style="color:#337ab7">'+t.c.linkmans[e].remark+"</span>",a.target,{tips:[3,"#fff"]}));else{if(this.editParam==e)return;this.hoverParam=e,a&&t.c[e]&&(t.toolTips_tips=layer.yoTips('<span style="color:#337ab7">'+t.c[e]+"</span>",a.target,{tips:[3,"#fff"]}))}},leave:function(){this.hoverParam==this.editParam&&(this.hoverParam=""),0<this.editParam.indexOf("_")&&(this.editParam=""),this.toolTips_tips&&layer.close(this.toolTips_tips)},saveData:function(e){var a=this;a.posting||(""==this.c.customerName&&(this.c.customerName=this.oldData),this.oldData!=this.c[this.editParam]?function(){var e={id:config.customerId};a.posting=!0,"customerName"==a.editParam?(a.customerName=a.c.customerName.split(",")[1],e.customerName=a.c.customerName.split(",")[1],e.customerId=a.c.customerName.split(",")[0],a.cId=a.c.customerName.split(",")[0]):"userName"==a.editParam?(a.userName=a.c.userName.split(",")[1],e.userName=a.c.userName.split(",")[1],e.userId=a.c.userName.split(",")[0]):"swwxName"==a.editParam?(a.swwxName=a.c.swwxName.split(",")[1],e.swwxName=a.c.swwxName.split(",")[1],e.swwxId=a.c.swwxName.split(",")[0]):(e[a.editParam]=$.trim(a.c[a.editParam]),Vue.set(a.c,a.editParam,$.trim(a.c[a.editParam])));if(!a.editParam)return a.posting=!1;$.post(config.api_xgOrderDetail,e,function(e){a.editParam="",a.posting=!1,a.getCaseDetail()})}():this.editParam="")},change:function(e){var t=this;laydate.render({elem:"#signingTime",trigger:"click",done:function(e,a){t.c.signingTime=e}}),laydate.render({elem:"#endTime",trigger:"click",done:function(e,a){t.c.endTime=e}}),this.hoverParam="",this.editParam=e,this.oldData=this.c[e],"customerName"==e&&$.ajax({type:"get",url:config.api_chufa+"?customerId="+t.cId+"&key",async:!0,success:function(e){this.debtorNameList=e}})},loading:function(e){"close"==e?layer.close(this.loadingSwitch):this.loadingSwitch=layer.load(3)},getCaseDetail:function(a){var t=this;a&&(this.orderInfo.pageNum=a),t.loading(),$.ajax({url:config.api_getOrderDetail,async:!1,data:{id:config.customerId,type:config.type,pageSize:t.orderInfo.pageSize||10,pageNo:t.orderInfo.pageNum||1},success:function(e){t.c=e.result,t.orderInfo=e.result.orderInfo,t.userName=e.result.userName,t.customerName=e.result.customerName,t.swwxName=e.result.swwxName,t.cId=e.result.customerId,t.loading("close"),t.pagi?($(".pagi").pagination("updatePages",t.orderInfo.pages),1==a&&$(".pagi").pagination("goToPage",t.orderInfo.pageNum)):t.pagi=$(".pagi").pagination({pages:t.orderInfo.pages,showCtrl:!0,displayPage:6,currentPage:t.orderInfo.pageNum,onSelect:function(e){t.orderInfo.pageNum=e,t.getCaseDetail(),yo.scrollTo("body")}})}})},initUploader:function(){if(!this.uploader){var t=this;this.loading(),require(["webuploader"],function(e){t.loading("close"),t.uploader=1;var a=e.create({server:"/contract/uploadAttachment.do",runtimeOrder:"html5",pick:{id:"#fileUp",innerHTML:"上传附件"},resize:!1,fileNumLimit:10});a.on("uploadSuccess",function(e,a){t.c.attachs.push(a)}),a.on("uploadStart",function(){t.loading()}),a.on("uploadError",function(e){layer.msg("上传出错")}),a.on("error",function(e){"Q_TYPE_DENIED"==e&&layer.msg("上传文件格式错误，请检查")}),a.on("uploadFinished",function(e){a.reset(),$.post(config.api_saveProductAttach,t.attachs).done(function(){t.loading("close")})}),a.on("fileQueued",function(){a.upload()})})}},attachs_delete:function(e){var a=this,t=layer.confirm("确定删除此附件?",function(){a.c.attachs.splice(e,1),$.ajax({type:"POST",url:config.api_saveProductAttach,data:a.attachs,timeout:99999}).done(function(){a.loading("close"),layer.close(t)})})},customerName1:function(){var a=this;$.ajax({type:"get",url:config.api_costomerList,async:!0,success:function(e){a.customerNameList=e}})},customerName2:function(){var a=this;$.ajax({type:"get",url:config.api_costomerList1,data:{ROLE_ID:"30b1487221464d369ca4c2462ccca920,b729e9334ad64c15a4e47d88b8c2638f"},async:!0,success:function(e){a.userNameList=e}})},customerName3:function(){var a=this;$.ajax({type:"get",url:config.api_costomerList1,data:{ROLE_ID:"fbe6f2f9535c4fce9f024f6cb999b2bd"},async:!0,success:function(e){a.swwxNameList=e}})},viewContract:function(e){layer.open({type:2,title:"查看详情",content:"/order/viewOrder.do?id="+e,area:["100%","100%"]})}}})}),window.parentFnc=function(e){layer.closeAll(),window.app.getCaseRecord(window.app.type)};