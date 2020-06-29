"use strict";define(["require","select2","yoValidate"],function(t,e,a){return Vue.component("report-edit",{template:'\n\t\t<form class="form form-horizontal" id="report_add">\n\t<div class="m-t-md">\n\t\t<div class="col-xs-10 col-xs-offset-1">\n\t\t\t<div class="form-group">\n\t\t\t<div class="row">\n\t\t\t<div class="col-xs-5">\n\t\t\t\t<label class="control-label"><span class="text-danger">*</span> 类型：</label>\n\t\t\t\t<select class="form-control" :disabled="status==\'edit\'" v-model="type">\n\t\t\t\t\t<option value="0">周报</option>\n\t\t\t\t\t<option value="1">月报</option>\n\t\t\t\t</select>\n\t\t\t</div>\n\t\t\t<div class="col-xs-7">\n\t\t\t\t<label class="control-label"><span class="text-danger">*</span> 日期：</label>\n\t\t\t\t<select class="form-control" :disabled="status==\'edit\'" v-model="c.time" >\n\t\t\t\t\t<option v-for="el in time" :value="el">{{el}}</option>\n\t\t\t\t</select>\n\t\t\t</div>\n\t\t\t</div>\n\t\t\t</div>\n\t\t\t<div class="form-group">\n\t\t\t\t<label class="control-label"><span class="text-danger">*</span> 批阅人：</label>\n\t\t\t\t<select class="form-control" id="readMan">\n\t\t\t\t</select>\n\t\t\t</div>\n\t\t\t<div class="form-group">\n\t\t\t\t<label class="control-label">抄送人：</label>\n\t\t\t\t<select class="form-control"  id="copyMan" multiple>\n\t\t\t\t</select>\n\t\t\t</div>\n\t\t\t<div class="form-group">\n\t\t\t\t<label class="control-label"><span class="text-danger">*</span> 总结：</label>\n\t\t\t\t<textarea style="height:120px" v-model="c.content" class="form-control" name="" rows="" cols=""></textarea>\n\t\t\t</div>\n\t\t\t<div class="form-group">\n\t\t\t\t<label class="control-label"><span class="text-danger">*</span> 计划：</label>\n\t\t\t\t<textarea style="height:120px" v-model="c.plan" class="form-control" name="" rows="" cols=""></textarea>\n\t\t\t</div>\n\t\t\t<div class="form-group">\n\t\t\t\t<label class="control-label">附件：</label>\n\t\t\t\t<div class="well well-sm">\n\t\t\t\t\t<div id="filesUploadBtn"></div>\n\t\t\t\t</div>\n\t\t\t\t<div class="form-group col-sm-12">\n\n                <table v-if="c.files&&c.files.length" class="table table-bordered">\n                    <thead>\n                        <tr>\n                            <th>文件名</th>\n                            <th width="180">操作</th>\n                        </tr>\n                    </thead>\n                    <tbody>\n                        <tr v-for="(el,index) of c.files">\n                            <td>{{el.originalFilename}}</td>\n                            <td><a target="_blank" :href="el.url" class="btn btn-primary btn-sm">\n                            <i class="fa fa-eye"></i> 查看</a>\n                            <a @click="files_del(index)" target="_blank" class="btn btn-danger btn-sm"><i class="fa fa-trash"></i> 删除</a></td>\n                        </tr>\n                    </tbody>\n                </table>\n                </div>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n</form>\n  ',data:function(){return{api_saveReportData:"/report/saveReportData.do",api_showReportDate:"/report/showReportDate.do",api_updateTasks:"/report/updateReportData.do",c:{type:0,files:[]},open:0,dialog_init:0,uploadInit:0,status:"add",type:0,time:[],addBox:"addBox"+Math.random().toString().split(".")[1],selectBox:"selectBox"+Math.random().toString().split(".")[1],select2:{}}},created:function(){},watch:{type:function(t,e){var a=this;console.log(this.open),"add"==this.status&&this.open&&$.post(a.api_showReportDate,{type:a.type}).done(function(t){a.time=t.result})}},mounted:function(){},methods:{init:function(t){var e=this;if(t||this.dialog_init||0!=this.type||$.post(e.api_showReportDate,{type:e.type}).done(function(t){e.time=t.result}),t){console.log(t);var a=JSON.parse(JSON.stringify(t));for(var n in this.status="edit",a)"content"==n||"plan"==n?Vue.set(e.c,n,yo.str_textarea(a[n])):Vue.set(e.c,n,a[n]);this.c.files=a.attachs,this.type=a.type,0==a.type?(this.time=[a.zbtimeStart+"-"+a.zbtimeEnd],this.c.time=a.zbtimeStart+"-"+a.zbtimeEnd):(this.time=[a.time],this.c.time=a.time),$("#readMan").html('<option value="'+a.readMan+'" selected>'+a.readManName+"</option>").val(a.readMan).trigger("change");var s="",i=a.copyMan&&a.copyMan.split(",")||[],o=a.copyManName&&a.copyManName.split(",")||[];$.each(i,function(t){s+='<option value="'+this+'" selected>'+o[t]+"</option>"}),$("#copyMan").html(s).trigger("change")}layer.open({type:1,title:"add"==e.status?"新增工作计划":"编辑工作计划",content:$("#report_add"),area:["800px","700px"],btn:["保存"],btnAlign:"c",success:function(){e.open=1,e.dialog_init||(e.uploader_init(),e.select_init(),e.dialog_init=1)},yes:function(){e.save()},end:function(){e.clear()}})},clear:function(){for(var t in this.open=0,this.c)Vue.set(this.c,t,"");this.c.files=[],this.c.copyMan=[],this.c.copyManName=[],this.status="add",this.type=0},select_init:function(){var t=this;this.select2.readMan&&(this.select2.readMan.select2("destroy"),this.select2.copyMan.select2("destroy")),t.select2.readMan=$("#readMan").select2({placeholder:"请选择批阅人",language:"zh-CN",ajax:{url:"/user/getUserInfo",dataType:"json",type:"post",delay:250,data:function(t){return{page:t.page||1,ROLE_ID:"01dc6d29f1704efeab0376d327f47d98,cf54c0b5567542e084778c3f5b230054,4cb60182bb294cfba622f951e390bc6f",data:{q:t.term||""}}},processResults:function(t,e){return e.page=e.page||1,$.each(t,function(){this.id=this.USER_ID,this.text=this.NAME}),{results:t,pagination:{more:10==t.length}}},cache:!0},minimumInputLength:0}).on("change",function(){t.c.readMan=this.value}),t.select2.copyMan=$("#copyMan").select2({placeholder:"请选择抄送人",language:"zh-CN",ajax:{url:"/user/getUserInfo",dataType:"json",type:"post",delay:250,data:function(t){return{page:t.page||1,ROLE_ID:"7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98,90564dd8b75a4f6d815ce418462d401c,e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b",data:{q:t.term||""}}},processResults:function(t,e){return e.page=e.page||1,$.each(t,function(){this.id=this.USER_ID,this.text=this.NAME}),{results:t,pagination:{more:10==t.length}}},cache:!0},minimumInputLength:0}).on("change",function(){t.c.copyMan=this.value})},uploader_init:function(){var n=this;this.uploadStatus||t(["webuploader"],function(t){var e;n.uploadInit=1;var a=t.create({server:"/contract/uploadAttachment.do",runtimeOrder:"html5",pick:{id:"#filesUploadBtn",innerHTML:"上传附件"},resize:!1,fileNumLimit:10});a.on("uploadSuccess",function(t,e){n.c.files.push(e)}),a.on("uploadStart",function(){e=layer.load(3)}),a.on("uploadError",function(t){layer.msg("上传出错")}),a.on("error",function(t){"Q_TYPE_DENIED"==t&&layer.msg("上传文件格式错误，请检查")}),a.on("uploadFinished",function(t){a.reset(),layer.close(e)}),a.on("fileQueued",function(){a.upload()})})},files_del:function(t){var e=this,a=layer.confirm("确认删除此项?",function(){e.c.files.splice(t,1),layer.close(a)})},save:function(t){var e={time:this.c.time};if(this.c.time)if(e.readMan=$("#readMan").val(),e.readManName=$("#readMan").find("option:selected").text(),e.readMan)if(e.copyMan=[],e.copyManName=[],$.each($("#copyMan").find("option:selected"),function(){e.copyMan.push(this.value),e.copyManName.push(this.innerHTML)}),this.c.content)if(e.content=yo.textarea_str(this.c.content),this.c.plan){e.plan=yo.textarea_str(this.c.plan),e.files=this.c.files,e.type=this.type,e.id=this.c.id;var a=layer.load(3),n="edit"==this.status?this.api_updateTasks:this.api_saveReportData;yocto.json(n,e,function(t){layer.close(a),layer.msg("提交成功"),setTimeout(function(){location.reload()},2e3)})}else layer.msg("请填写计划");else layer.msg("请填写总结");else layer.msg("请选择批阅人");else layer.msg("请选择时间")}}})});