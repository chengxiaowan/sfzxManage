"use strict";var config={id:yo.getQueryString("id"),type:yo.getQueryString("type"),index:yo.getQueryString("index"),role:localStorage.userRole,api_detail:"/yjzb/showXzDetail",api_role:"/yjzb/showRoleList",api_addOrUpdate:"/yjzb/addOrUpdateDxandTs",api_del:"/yjzb/deleteById"};function colseThis(){var e=parent.layer.getFrameIndex(window.name);parent.layer.close(e)}require(["dateTimeInit"]),require(["select2"],function(e){window.app=new Vue({el:"#app",data:{c:{list:[]},dtype:"",postData:{},saleRole:{}},computed:{},created:function(){},mounted:function(){this.list_Get(),this.saleRole=this.select_init("[name=saleRole]","请选择岗位",1)},methods:{list_Get:function(){var t=this;$.get(config.api_detail,{parentId:config.id,flag:0},function(e){"00"==e.error&&(t.c.list=e.result)})},addSalary:function(){var a=this;this.saleRole.val(null).trigger("change"),$("#salaryName").val("");var i=layer.open({type:1,title:"新增岗位绩效薪资",closeBtn:1,content:$("#salaryBox"),area:["480px","220px"],btn:"确定",btnAlign:"c",yes:function(){var e=$("#saleRole  option:selected").val(),t=$("#salaryName").val();a.postData.targetDetail=t,a.postData.userId=e,a.postData.parentId=config.id,a.postData.flag=0,a.postData.type=config.type,""!=e?""!=t?$.get(config.api_addOrUpdate,a.postData,function(e){"00"==e.error?(layer.close(i),layer.msg("添加成功"),a.list_Get()):layer.msg(e.msg)}):layer.msg("绩效薪资不能为空"):layer.msg("请选择岗位")}})},edit:function(t){var a=this;$("#gangwei").val(t.name),$("#salaryName1").val(t.targetDetail);var i=layer.open({type:1,title:"编辑岗位绩效薪资",closeBtn:1,content:$("#salaryBox1"),area:["480px","220px"],btn:"确定",btnAlign:"c",yes:function(){var e=$("#salaryName1").val();""!=e?(a.postData.id=t.id,a.postData.targetDetail=e,a.postData.type=config.type,$.get(config.api_addOrUpdate,a.postData,function(e){"00"==e.error?(layer.close(i),layer.msg("修改成功"),a.list_Get()):layer.msg(e.msg)})):layer.msg("请输入绩效薪资")}})},del:function(e){var t=this,a=layer.confirm("删除该岗位绩效薪资后，该岗位的员工后续将不会享受到该绩效薪资提成，受否确定删除该岗位绩效薪资?",{title:"提示"},function(){$.get(config.api_del,{id:e},function(e){"00"==e.error?(layer.close(a),layer.msg("删除成功"),t.list_Get()):layer.msg(e.msg)})})},select_init:function(e,t,a,i){return $(e).select2({placeholder:t||"请选择",language:"zh-CN",allowClear:a||!1,ajax:{url:config.api_role,dataType:"json",type:"get",delay:250,data:function(e){return{type:config.type}},processResults:function(e,t){return $.each(e.result,function(){this.id=this.roleId,this.text=this.name}),{results:e.result,pagination:{more:10==e.result.length}}},cache:!0},minimumInputLength:0})}}})}),window.parentFnc=function(e){layer.closeAll(),window.app.getCaseRecord(window.app.type)};