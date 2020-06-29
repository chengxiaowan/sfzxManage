"use strict";define(["require"],function(n){return Vue.component("sale-follow",{template:'<div class="col-sm-12 form" id="follow-box">\n            <form class="m-t-sm row">\n                <div class="form-group col-sm-4">\n                    <select style="width:100%" v-model="type" class="form-control">\n                   <option v-if="dialogMode==1" selected value="">请选择跟进方式</option>\n                   <option selected value="0">电话</option>\n               \t   <option v-if="dialogMode==1" value="1">QQ</option>\n                   <option v-if="dialogMode==1" value="2">微信</option>\n                   <option v-if="dialogMode==1" value="3">拜访</option>\n                   <option v-if="dialogMode==1" value="4">邮件</option>\n                   <option v-if="dialogMode==1" value="5">短信</option>\n                   <option v-if="dialogMode==1" value="6">其他</option>\n               </select>\n                </div>\n                <div class="form-group col-sm-12">\n                    <textarea class="form-control" style="resize:none" v-model="content" placeholder="跟进详情" rows="6" cols=""></textarea>\n                </div>\n                <div class="form-group col-sm-12">\n                    <div id="followFilesUploadBtn"></div>\n                </div>\n                <div class="form-group col-sm-12">\n\n                <table v-if="files.length" class="table table-bordered">\n                    <thead>\n                        <tr>\n                            <th>文件名</th>\n                            <th width="180">操作</th>\n                        </tr>\n                    </thead>\n                    <tbody>\n                        <tr v-for="(el,index) of files">\n                            <td>{{el.originalFilename}}</td>\n                            <td><a target="_blank" :href="el.url" class="btn btn-primary btn-sm">\n                            <i class="fa fa-eye"></i> 查看</a>\n                            <a @click="files_del(index)" target="_blank" class="btn btn-danger btn-sm"><i class="fa fa-trash"></i> 删除</a></td>\n                        </tr>\n                    </tbody>\n                </table>\n                </div>\n                <div class="form-group col-sm-12"> \n                    <label class="control-label col-xs-2 p-l-0 p-r-0" for="">提醒谁看 : </label>\n                    <div class="col-xs-10">\n                    <select style="width:100%" v-model="remind" multiple class="form-control input-sm" id="remindWho">\n                    </select>\n                    </div>\n                </div>\n                <div class="form-group col-sm-6"> \n                    <label class="control-label col-xs-4 p-l-0 p-r-0" for="">客户 : </label>\n                    <div class="col-xs-8">\n                    <input type="text" v-model="customerName" class="form-control input-sm p-r-0" readonly name="" value="">     \n                    </div>\n                </div>\n                <div class="form-group col-sm-6"> \n                    <label class="control-label col-xs-4 p-l-0 p-r-0" for="">跟进状态<span class="text-danger">*</span> : </label>\n                    <div class="col-xs-8">\n                                                    <select name="status" v-model="status" class="form-control">\n                                    <option value="">请选择</option>\n                                    <option value="0">未处理</option>\n                                    <option value="1">初步沟通</option>\n                                    <option value="2">无意向</option>\n                                    <option value="3">有意向</option>\n                                    <option v-if="role!=\'电访销售\'" value="4">销售外访</option>\n                                    <option v-if="role!=\'电访销售\'" value="5">客户来访</option>\n                                    <option v-if="role!=\'电访销售\'" value="6">需求确定</option>\n                                    <option v-if="role!=\'电访销售\'" value="7">方案/报价</option>\n                                    <option v-if="role!=\'电访销售\'" value="8">谈判/合同</option>\n                                    <option v-if="role!=\'电访销售\'" value="9">成交</option>\n                                    <option v-if="role!=\'电访销售\'" value="10">暂时搁置</option>\n                                    <option v-if="role!=\'电访销售\'" value="11">未成交</option>\n                                </select>\n                    </div>\n                </div>\n                <div class="form-group col-sm-6"> \n                    <label class="control-label col-xs-4 p-l-0 p-r-0" for="">联系人 : </label>\n                    <div class="col-xs-8">\n                    <input v-if="dialogMode==0" type="text" readonly v-model="linkmanName" class="form-control input-sm  p-r-0"  name="" value="">   \n                    <div v-show="dialogMode==1">\n                     <select style="width:100%" v-model="linkmanId" multiple class="form-control input-sm" id="linkmanId"></select>\n                    </div>\n                    </div>\n                </div>\n                <div class="form-group col-sm-6"> \n                    <label class="control-label col-xs-4 p-l-0 p-r-0" for="">下次跟进时间 : </label>\n                    <div class="col-xs-8">\n                    <input type="text" @click="laydate($event)" readonly placeholder="选择下次跟进时间" v-model="nextTime" class="form-control input-sm  p-r-0"  name="" value="">     \n                    </div>\n                </div>\n            </form>\n        </div>\n \t\t\t\t ',props:["dialogMode","callId"],data:function(){return{role:localStorage.userRole||"",type:0,time:"",content:"",files:[],remind:[],status:"",customerId:"",customerName:"",linkmanName:"",linkmanId:[],nextTime:"",select_init:0}},created:function(){},mounted:function(){var n=this;n.$watch("callId",function(e,t){e&&(n.time=laydate.now((new Date).getTime(),"YYYY-MM-DD hh:mm"))})},methods:{init:function(e,t){console.log(this.dialogMode),1==this.dialogMode?this.type="":this.type=0,e&&(this.customerId=e.id||"",this.customerName=e.name||"",this.linkmanName=t||e.linkmanName||"");var o=this;layer.open({type:"1",title:"写跟进",content:$("#follow-box"),area:["700px","620px"],btn:["保存"],btnAlign:"c",yes:function(){o.save()},end:function(){o.remindWhoBox.val(null).trigger("change"),o.linkmanBox.val(null).trigger("change"),o.content="",window.callId="",o.files=[],o.remind=[],o.status="",o.customerId="",o.customerName="",o.linkmanName="",o.linkmanId=[],o.nextTime=""},success:function(){o.select_init||(o.remindWhoBox=$("#remindWho").select2({placeholder:"请选择",language:"zh-CN",allowClear:!0,ajax:{url:"/user/getUserInfo",dataType:"json",type:"post",delay:250,data:function(n){return{page:n.page||1,ROLE_ID:"cf54c0b5567542e084778c3f5b230054,4cb60182bb294cfba622f951e390bc6f,1",data:{q:n.term||""}}},processResults:function(n,e){return e.page=e.page||1,$.each(n,function(){this.id=this.USER_ID,this.text=this.NAME}),{results:n,pagination:{more:10==n.length}}},cache:!0},minimumInputLength:0}).on("change",function(){o.remind=[];for(var n=0;n<this.children.length;n++)this.children[n].selected&&o.remind.push(this.children[n].value)}),o.linkmanBox=$("#linkmanId").select2({placeholder:"请选择",language:"zh-CN",allowClear:!0,ajax:{url:"/linkman/getLinkmanInfo",dataType:"json",type:"post",delay:250,data:function(n){return{page:n.page||1,data:{q:n.term||""},id:o.customerId}},processResults:function(n,e){return e.page=e.page||1,$.each(n,function(){this.id=this.linkmanName,this.text=this.linkmanName}),{results:n,pagination:{more:10==n.length}}},cache:!0},minimumInputLength:0}).on("change",function(){o.linkmanId=[];for(var n=0;n<this.children.length;n++)this.children[n].selected&&o.linkmanId.push(this.children[n].value)}),o.select_init=1,o.$parent.loading(),n(["webuploader"],function(n){app.loading("close");var e=n.create({server:"/contract/uploadAttachment.do",runtimeOrder:"html5",pick:{id:"#followFilesUploadBtn",innerHTML:"上传附件"},resize:!1,fileNumLimit:10});e.on("uploadSuccess",function(n,e){o.files.push(e)}),e.on("uploadStart",function(){o.$parent.loading()}),e.on("uploadError",function(n){layer.msg("上传出错")}),e.on("error",function(n){"Q_TYPE_DENIED"==n&&layer.msg("上传文件格式错误，请检查")}),e.on("uploadFinished",function(n){e.reset(),o.$parent.loading("close")}),e.on("fileQueued",function(){e.upload()})}))}})},save:function(){var n=this;if(!top.callId&&0==this.dialogMode)return void layer.msg("请挂断电话之后再保存记录");if(""===this.status)return void layer.msg("请选择跟进状态");if(""===this.type)return void layer.msg("请选择跟进方式");var e={content:this.content,files:this.files,remind:this.remind,status:this.status,customerId:this.customerId,nextTime:this.nextTime,customerName:this.customerName};0==this.dialogMode&&(e.callId=top.callId),this.linkmanName?(e.linkmanId=[],e.linkmanId.push(this.linkmanName)):e.linkmanId=this.linkmanId,e.type=this.type,yocto.json("/saleCustomer/saveProcessInfo",e,function(e){layer.closeAll(),layer.msg("销售动态保存成功"),n.$parent&&n.$parent.getCaseRecord&&n.$parent.getCaseRecord(),window.parentFnc&&window.parentFnc()})},files_del:function(n){var e=this,t=layer.confirm("确认删除此项?",function(){e.files.splice(n,1),layer.close(t)})},laydate:function(n){function e(e,t,o){return n.apply(this,arguments)}return e.toString=function(){return n.toString()},e}(function(n,e,t){var o=this;laydate({vEvent:n,format:"YYYY-MM-DD hh:mm:ss",istime:!0,istoday:!0,choose:function(n){t?o.time=n:o.nextTime=n}})})}})});
//# sourceMappingURL=data:application/json;charset=utf8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIm1vZHVsZV9kaWFsb2dfcmVwb3J0LmpzIl0sIm5hbWVzIjpbImRlZmluZSIsInJlcXVpcmUiLCJWdWUiLCJjb21wb25lbnQiLCJ0ZW1wbGF0ZSIsInEiLCJkYXRhIiwicHJvY2Vzc1Jlc3VsdHMiLCJwYXJhbXMiLCIkIiwiY29udGVudCIsImZpbGVzIiwicmVtaW5kIiwic3RhdHVzIiwicmVzdWx0cyIsInBhZ2luYXRpb24iLCJtb3JlIiwibGlua21hbklkIiwibmV4dFRpbWUiLCJjYWNoZSIsImNyZWF0ZWQiLCJtb3VudGVkIiwidGhhdCIsInRoaXMiLCJwbGFjZWhvbGRlciIsImxhbmd1YWdlIiwiRGF0ZSIsImdldFRpbWUiLCJ0eXBlIiwiZGVsYXkiLCJkaWFsb2dNb2RlIiwicGFnZSIsImlkIiwiY3VzdG9tZXJJZCIsImN1c3RvbWVySW5mbyIsImN1c3RvbWVyTmFtZSIsIm5hbWUiLCJsYXllciIsIm9wZW4iLCJ0aXRsZSIsInllcyIsInNhdmUiLCJyZW1pbmRXaG9Cb3giLCJ2YWwiLCJ0cmlnZ2VyIiwibWluaW11bUlucHV0TGVuZ3RoIiwic2VsZWN0X2luaXQiLCIkcGFyZW50IiwibG9hZGluZyIsImFwcCIsInNlcnZlciIsInJ1bnRpbWVPcmRlciIsInNlbGVjdDIiLCJpbm5lckhUTUwiLCJyZXNpemUiLCJmaWxlTnVtTGltaXQiLCJ1cmwiLCJkYXRhVHlwZSIsInVwbG9hZGVyIiwidGVybSIsIlVTRVJfSUQiLCJvbiIsImkiLCJjaGlsZHJlbiIsImxlbmd0aCIsInNlbGVjdGVkIiwicHVzaCIsInZhbHVlIiwidG9wIiwiYWxsb3dDbGVhciIsImxpbmttYW5OYW1lIiwidGV4dCIsIndpbmRvdyIsImZpbGVzX2RlbCIsImxheWRhdGUiLCJXZWJVcGxvYWRlciIsImNyZWF0ZSIsInBpY2siLCJmaWxlIiwicmVzIiwibXNnIiwicmVzZXQiLCJ1cGxvYWQiLCJjYWxsSWQiLCJ5b2N0byIsImpzb24iLCJjbG9zZUFsbCIsImdldENhc2VSZWNvcmQiLCJwYXJlbnRGbmMiLCJpbmRleCIsImRpYWxvZyIsImNvbmZpcm0iLCJzcGxpY2UiLCJjbG9zZSIsImUiLCJlbG0iLCJmbGFnIiwidkV2ZW50IiwiZm9ybWF0IiwiaXN0aW1lIiwiaXN0b2RheSIsImNob29zZSIsInRpbWUiXSwibWFwcGluZ3MiOiJZQUFBQSxTQUNDLFdBRERBLFNBQ0NDLEdBMFdBLE1BdldlQyxLQUFJQyxVQUFVLGVBQTdCQyxTQUFBQSxnMEtBbUxPQyxPQUFBQSxhQUFBQSxVQUpLQyxLQS9LZ0MsV0FzTHZDLE9BQ0RDLEtBQUFBLGFBQUFBLFVBQUFBLEdBQ0NDLEtBQUFBLEVBQ0FDLEtBQUFBLEdBQ0NDLFFBQUEsR0FDQUMsU0FDQUMsVUFDREMsT0FBQSxHQUNDQyxXQUFBQSxHQUNBQyxhQUFBQSxHQUNDQyxZQUFBQSxHQURXQyxhQUZOQyxTQUFBLEdBT1JDLFlBQUFBLElBL0J3REMsUUFyS2hCLGFBME16QkMsUUExTXlCLFdBNk1qQyxHQUFBQyxHQUFBQyxJQUNLRCxHQUFBQSxPQUFBQSxTQUFBQSxTQUFBQSxFQUFBQSxHQUNiRSxJQUNBQyxFQUFBQSxLQUFBQSxRQUFBQSxLQUFVLEdBQUFDLE9BRjZDQyxVQUFBLHdCQU90REMsU0FDQUMsS0FEQUQsU0FDQUMsRUFBQUEsR0FDQXZCLFFBQUFBLElBQUFBLEtBQU13QixZQUNMLEdBQUFQLEtBQUFPLFdBQ0NDLEtBQUFBLEtBQUFBLEdBRUMxQixLQUFBQSxLQUFBQSxFQUVvQjJCLElBTGZULEtBQUFVLFdBQVBDLEVBQUFGLElBQUEsR0FPQVQsS0FiSVksYUFBQUQsRUFBQUUsTUFBQSxHQWNMN0IsS0FBQUEsWUFBQUEsR0FBZ0IyQixFQUFTNUIsYUFBYyxHQUV0Q0csSUFBQUEsR0FBQUEsSUFDQzRCLE9BQUFDLE1BQ0FWLEtBQUEsSUFDQVcsTUFBQSxNQUNEN0IsUUFBQUQsRUFBQSxlQUNDSyxNQUFBQSxRQUFBQSxTQUNBQyxLQUFBQSxNQUNDQyxTQUFBQSxJQURXd0IsSUFMWixXQUdNbEIsRUFBQW1CLFFBT1J0QixJQVZFLFdBakJHRyxF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TUFBTWlGLE9BQU9ILEVBQU0sR0FDeEJwRCxNQUFNd0QsTUFBTUgsTUFHZGhCLFFBaElFOUMsU0FBQUEsR0FBQUEsUUFBQUEsR0FBQUEsRUFBQUEsRUFBQUEsR0FBQUEsTUFBQUEsR0FBQUEsTUFBQUEsS0FBQUEsV0FBQUEsTUFBQUEsR0FBQUEsU0FBQUEsV0FBQUEsTUFBQUEsR0FBQUEsWUFBQUEsR0FBQUEsU0FnSU1rRSxFQUFFQyxFQUFJQyxHQUNiLEdBQU0xRSxHQUFPQyxJQUNibUQsVUFDQ3VCLE9BQU9ILEVBQ1BJLE9BQVEsc0JBQ09DLFFBQVEsRUFDdkJDLFNBQVMsRUFDVEMsT0FBUSxTQUFTL0YsR0FDWjBGLEVBR0gxRSxFQUFLZ0YsS0FBT2hHLEVBRlpnQixFQUFLSixTQUFXWiIsImZpbGUiOiJtb2R1bGVfZGlhbG9nX3JlcG9ydC5qcyIsInNvdXJjZXNDb250ZW50IjpbImRlZmluZShbXHJcblx0J3JlcXVpcmUnIFxyXG5dLCBmdW5jdGlvbihyZXF1aXJlKSB7XHJcblx0J3VzZSBzdHJpY3QnO1xyXG5cdGNvbnN0IG1vZHVsZSA9IFZ1ZS5jb21wb25lbnQoJ3NhbGUtZm9sbG93Jywge1xyXG5cdFx0dGVtcGxhdGU6IGA8ZGl2IGNsYXNzPVwiY29sLXNtLTEyIGZvcm1cIiBpZD1cImZvbGxvdy1ib3hcIj5cclxuICAgICAgICAgICAgPGZvcm0gY2xhc3M9XCJtLXQtc20gcm93XCI+XHJcbiAgICAgICAgICAgICAgICA8ZGl2IGNsYXNzPVwiZm9ybS1ncm91cCBjb2wtc20tNFwiPlxyXG4gICAgICAgICAgICAgICAgICAgIDxzZWxlY3Qgc3R5bGU9XCJ3aWR0aDoxMDAlXCIgdi1tb2RlbD1cInR5cGVcIiBjbGFzcz1cImZvcm0tY29udHJvbFwiPlxyXG4gICAgICAgICAgICAgICAgICAgPG9wdGlvbiB2LWlmPVwiZGlhbG9nTW9kZT09MVwiIHNlbGVjdGVkIHZhbHVlPVwiXCI+6K+36YCJ5oup6Lef6L+b5pa55byPPC9vcHRpb24+XHJcbiAgICAgICAgICAgICAgICAgICA8b3B0aW9uIHNlbGVjdGVkIHZhbHVlPVwiMFwiPueUteivnTwvb3B0aW9uPlxyXG4gICAgICAgICAgICAgICBcdCAgIDxvcHRpb24gdi1pZj1cImRpYWxvZ01vZGU9PTFcIiB2YWx1ZT1cIjFcIj5RUTwvb3B0aW9uPlxyXG4gICAgICAgICAgICAgICAgICAgPG9wdGlvbiB2LWlmPVwiZGlhbG9nTW9kZT09MVwiIHZhbHVlPVwiMlwiPuW+ruS/oTwvb3B0aW9uPlxyXG4gICAgICAgICAgICAgICAgICAgPG9wdGlvbiB2LWlmPVwiZGlhbG9nTW9kZT09MVwiIHZhbHVlPVwiM1wiPuaLnOiuvzwvb3B0aW9uPlxyXG4gICAgICAgICAgICAgICAgICAgPG9wdGlvbiB2LWlmPVwiZGlhbG9nTW9kZT09MVwiIHZhbHVlPVwiNFwiPumCruS7tjwvb3B0aW9uPlxyXG4gICAgICAgICAgICAgICAgICAgPG9wdGlvbiB2LWlmPVwiZGlhbG9nTW9kZT09MVwiIHZhbHVlPVwiNVwiPuefreS/oTwvb3B0aW9uPlxyXG4gICAgICAgICAgICAgICAgICAgPG9wdGlvbiB2LWlmPVwiZGlhbG9nTW9kZT09MVwiIHZhbHVlPVwiNlwiPuWFtuS7ljwvb3B0aW9uPlxyXG4gICAgICAgICAgICAgICA8L3NlbGVjdD5cclxuICAgICAgICAgICAgICAgIDwvZGl2PlxyXG4gICAgICAgICAgICAgICAgPGRpdiBjbGFzcz1cImZvcm0tZ3JvdXAgY29sLXNtLTEyXCI+XHJcbiAgICAgICAgICAgICAgICAgICAgPHRleHRhcmVhIGNsYXNzPVwiZm9ybS1jb250cm9sXCIgc3R5bGU9XCJyZXNpemU6bm9uZVwiIHYtbW9kZWw9XCJjb250ZW50XCIgcGxhY2Vob2xkZXI9XCLot5/ov5vor6bmg4VcIiByb3dzPVwiNlwiIGNvbHM9XCJcIj48L3RleHRhcmVhPlxyXG4gICAgICAgICAgICAgICAgPC9kaXY+XHJcbiAgICAgICAgICAgICAgICA8ZGl2IGNsYXNzPVwiZm9ybS1ncm91cCBjb2wtc20tMTJcIj5cclxuICAgICAgICAgICAgICAgICAgICA8ZGl2IGlkPVwiZm9sbG93RmlsZXNVcGxvYWRCdG5cIj48L2Rpdj5cclxuICAgICAgICAgICAgICAgIDwvZGl2PlxyXG4gICAgICAgICAgICAgICAgPGRpdiBjbGFzcz1cImZvcm0tZ3JvdXAgY29sLXNtLTEyXCI+XHJcblxyXG4gICAgICAgICAgICAgICAgPHRhYmxlIHYtaWY9XCJmaWxlcy5sZW5ndGhcIiBjbGFzcz1cInRhYmxlIHRhYmxlLWJvcmRlcmVkXCI+XHJcbiAgICAgICAgICAgICAgICAgICAgPHRoZWFkPlxyXG4gICAgICAgICAgICAgICAgICAgICAgICA8dHI+XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGg+5paH5Lu25ZCNPC90aD5cclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0aCB3aWR0aD1cIjE4MFwiPuaTjeS9nDwvdGg+XHJcbiAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+XHJcbiAgICAgICAgICAgICAgICAgICAgPC90aGVhZD5cclxuICAgICAgICAgICAgICAgICAgICA8dGJvZHk+XHJcbiAgICAgICAgICAgICAgICAgICAgICAgIDx0ciB2LWZvcj1cIihlbCxpbmRleCkgb2YgZmlsZXNcIj5cclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD57e2VsLm9yaWdpbmFsRmlsZW5hbWV9fTwvdGQ+XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQ+PGEgdGFyZ2V0PVwiX2JsYW5rXCIgOmhyZWY9XCJlbC51cmxcIiBjbGFzcz1cImJ0biBidG4tcHJpbWFyeSBidG4tc21cIj5cclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxpIGNsYXNzPVwiZmEgZmEtZXllXCI+PC9pPiDmn6XnnIs8L2E+XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICA8YSBAY2xpY2s9XCJmaWxlc19kZWwoaW5kZXgpXCIgdGFyZ2V0PVwiX2JsYW5rXCIgY2xhc3M9XCJidG4gYnRuLWRhbmdlciBidG4tc21cIj48aSBjbGFzcz1cImZhIGZhLXRyYXNoXCI+PC9pPiDliKDpmaQ8L2E+PC90ZD5cclxuICAgICAgICAgICAgICAgICAgICAgICAgPC90cj5cclxuICAgICAgICAgICAgICAgICAgICA8L3Rib2R5PlxyXG4gICAgICAgICAgICAgICAgPC90YWJsZT5cclxuICAgICAgICAgICAgICAgIDwvZGl2PlxyXG4gICAgICAgICAgICAgICAgPGRpdiBjbGFzcz1cImZvcm0tZ3JvdXAgY29sLXNtLTEyXCI+IFxyXG4gICAgICAgICAgICAgICAgICAgIDxsYWJlbCBjbGFzcz1cImNvbnRyb2wtbGFiZWwgY29sLXhzLTIgcC1sLTAgcC1yLTBcIiBmb3I9XCJcIj7mj5DphpLosIHnnIsgOiA8L2xhYmVsPlxyXG4gICAgICAgICAgICAgICAgICAgIDxkaXYgY2xhc3M9XCJjb2wteHMtMTBcIj5cclxuICAgICAgICAgICAgICAgICAgICA8c2VsZWN0IHN0eWxlPVwid2lkdGg6MTAwJVwiIHYtbW9kZWw9XCJyZW1pbmRcIiBtdWx0aXBsZSBjbGFzcz1cImZvcm0tY29udHJvbCBpbnB1dC1zbVwiIGlkPVwicmVtaW5kV2hvXCI+XHJcbiAgICAgICAgICAgICAgICAgICAgPC9zZWxlY3Q+XHJcbiAgICAgICAgICAgICAgICAgICAgPC9kaXY+XHJcbiAgICAgICAgICAgICAgICA8L2Rpdj5cclxuICAgICAgICAgICAgICAgIDxkaXYgY2xhc3M9XCJmb3JtLWdyb3VwIGNvbC1zbS02XCI+IFxyXG4gICAgICAgICAgICAgICAgICAgIDxsYWJlbCBjbGFzcz1cImNvbnRyb2wtbGFiZWwgY29sLXhzLTQgcC1sLTAgcC1yLTBcIiBmb3I9XCJcIj7lrqLmiLcgOiA8L2xhYmVsPlxyXG4gICAgICAgICAgICAgICAgICAgIDxkaXYgY2xhc3M9XCJjb2wteHMtOFwiPlxyXG4gICAgICAgICAgICAgICAgICAgIDxpbnB1dCB0eXBlPVwidGV4dFwiIHYtbW9kZWw9XCJjdXN0b21lck5hbWVcIiBjbGFzcz1cImZvcm0tY29udHJvbCBpbnB1dC1zbSBwLXItMFwiIHJlYWRvbmx5IG5hbWU9XCJcIiB2YWx1ZT1cIlwiPiAgICAgXHJcbiAgICAgICAgICAgICAgICAgICAgPC9kaXY+XHJcbiAgICAgICAgICAgICAgICA8L2Rpdj5cclxuICAgICAgICAgICAgICAgIDxkaXYgY2xhc3M9XCJmb3JtLWdyb3VwIGNvbC1zbS02XCI+IFxyXG4gICAgICAgICAgICAgICAgICAgIDxsYWJlbCBjbGFzcz1cImNvbnRyb2wtbGFiZWwgY29sLXhzLTQgcC1sLTAgcC1yLTBcIiBmb3I9XCJcIj7ot5/ov5vnirbmgIE8c3BhbiBjbGFzcz1cInRleHQtZGFuZ2VyXCI+Kjwvc3Bhbj4gOiA8L2xhYmVsPlxyXG4gICAgICAgICAgICAgICAgICAgIDxkaXYgY2xhc3M9XCJjb2wteHMtOFwiPlxyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHNlbGVjdCBuYW1lPVwic3RhdHVzXCIgdi1tb2RlbD1cInN0YXR1c1wiIGNsYXNzPVwiZm9ybS1jb250cm9sXCI+XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxvcHRpb24gdmFsdWU9XCJcIj7or7fpgInmi6k8L29wdGlvbj5cclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPG9wdGlvbiB2YWx1ZT1cIjBcIj7mnKrlpITnkIY8L29wdGlvbj5cclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPG9wdGlvbiB2YWx1ZT1cIjFcIj7liJ3mraXmsp/pgJo8L29wdGlvbj5cclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPG9wdGlvbiB2YWx1ZT1cIjJcIj7ml6DmhI/lkJE8L29wdGlvbj5cclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPG9wdGlvbiB2YWx1ZT1cIjNcIj7mnInmhI/lkJE8L29wdGlvbj5cclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPG9wdGlvbiB2LWlmPVwicm9sZSE9J+eUteiuv+mUgOWUridcIiB2YWx1ZT1cIjRcIj7plIDllK7lpJborr88L29wdGlvbj5cclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPG9wdGlvbiB2LWlmPVwicm9sZSE9J+eUteiuv+mUgOWUridcIiB2YWx1ZT1cIjVcIj7lrqLmiLfmnaXorr88L29wdGlvbj5cclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPG9wdGlvbiB2LWlmPVwicm9sZSE9J+eUteiuv+mUgOWUridcIiB2YWx1ZT1cIjZcIj7pnIDmsYLnoa7lrpo8L29wdGlvbj5cclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPG9wdGlvbiB2LWlmPVwicm9sZSE9J+eUteiuv+mUgOWUridcIiB2YWx1ZT1cIjdcIj7mlrnmoYgv5oql5Lu3PC9vcHRpb24+XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxvcHRpb24gdi1pZj1cInJvbGUhPSfnlLXorr/plIDllK4nXCIgdmFsdWU9XCI4XCI+6LCI5YikL+WQiOWQjDwvb3B0aW9uPlxyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8b3B0aW9uIHYtaWY9XCJyb2xlIT0n55S16K6/6ZSA5ZSuJ1wiIHZhbHVlPVwiOVwiPuaIkOS6pDwvb3B0aW9uPlxyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8b3B0aW9uIHYtaWY9XCJyb2xlIT0n55S16K6/6ZSA5ZSuJ1wiIHZhbHVlPVwiMTBcIj7mmoLml7bmkIHnva48L29wdGlvbj5cclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPG9wdGlvbiB2LWlmPVwicm9sZSE9J+eUteiuv+mUgOWUridcIiB2YWx1ZT1cIjExXCI+5pyq5oiQ5LqkPC9vcHRpb24+XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC9zZWxlY3Q+XHJcbiAgICAgICAgICAgICAgICAgICAgPC9kaXY+XHJcbiAgICAgICAgICAgICAgICA8L2Rpdj5cclxuICAgICAgICAgICAgICAgIDxkaXYgY2xhc3M9XCJmb3JtLWdyb3VwIGNvbC1zbS02XCI+IFxyXG4gICAgICAgICAgICAgICAgICAgIDxsYWJlbCBjbGFzcz1cImNvbnRyb2wtbGFiZWwgY29sLXhzLTQgcC1sLTAgcC1yLTBcIiBmb3I9XCJcIj7ogZTns7vkurogOiA8L2xhYmVsPlxyXG4gICAgICAgICAgICAgICAgICAgIDxkaXYgY2xhc3M9XCJjb2wteHMtOFwiPlxyXG4gICAgICAgICAgICAgICAgICAgIDxpbnB1dCB2LWlmPVwiZGlhbG9nTW9kZT09MFwiIHR5cGU9XCJ0ZXh0XCIgcmVhZG9ubHkgdi1tb2RlbD1cImxpbmttYW5OYW1lXCIgY2xhc3M9XCJmb3JtLWNvbnRyb2wgaW5wdXQtc20gIHAtci0wXCIgIG5hbWU9XCJcIiB2YWx1ZT1cIlwiPiAgIFxyXG4gICAgICAgICAgICAgICAgICAgIDxkaXYgdi1zaG93PVwiZGlhbG9nTW9kZT09MVwiPlxyXG4gICAgICAgICAgICAgICAgICAgICA8c2VsZWN0IHN0eWxlPVwid2lkdGg6MTAwJVwiIHYtbW9kZWw9XCJsaW5rbWFuSWRcIiBtdWx0aXBsZSBjbGFzcz1cImZvcm0tY29udHJvbCBpbnB1dC1zbVwiIGlkPVwibGlua21hbklkXCI+PC9zZWxlY3Q+XHJcbiAgICAgICAgICAgICAgICAgICAgPC9kaXY+XHJcbiAgICAgICAgICAgICAgICAgICAgPC9kaXY+XHJcbiAgICAgICAgICAgICAgICA8L2Rpdj5cclxuICAgICAgICAgICAgICAgIDxkaXYgY2xhc3M9XCJmb3JtLWdyb3VwIGNvbC1zbS02XCI+IFxyXG4gICAgICAgICAgICAgICAgICAgIDxsYWJlbCBjbGFzcz1cImNvbnRyb2wtbGFiZWwgY29sLXhzLTQgcC1sLTAgcC1yLTBcIiBmb3I9XCJcIj7kuIvmrKHot5/ov5vml7bpl7QgOiA8L2xhYmVsPlxyXG4gICAgICAgICAgICAgICAgICAgIDxkaXYgY2xhc3M9XCJjb2wteHMtOFwiPlxyXG4gICAgICAgICAgICAgICAgICAgIDxpbnB1dCB0eXBlPVwidGV4dFwiIEBjbGljaz1cImxheWRhdGUoJGV2ZW50KVwiIHJlYWRvbmx5IHBsYWNlaG9sZGVyPVwi6YCJ5oup5LiL5qyh6Lef6L+b5pe26Ze0XCIgdi1tb2RlbD1cIm5leHRUaW1lXCIgY2xhc3M9XCJmb3JtLWNvbnRyb2wgaW5wdXQtc20gIHAtci0wXCIgIG5hbWU9XCJcIiB2YWx1ZT1cIlwiPiAgICAgXHJcbiAgICAgICAgICAgICAgICAgICAgPC9kaXY+XHJcbiAgICAgICAgICAgICAgICA8L2Rpdj5cclxuICAgICAgICAgICAgPC9mb3JtPlxyXG4gICAgICAgIDwvZGl2PlxyXG4gXHRcdFx0XHQgYCxcclxuXHRcdFx0XHRwcm9wczpbJ2RpYWxvZ01vZGUnLCdjYWxsSWQnXSxcclxuXHRcdGRhdGEoKSB7XHJcblx0XHRcdHJldHVybiB7XHJcblx0XHRcdFx0cm9sZTpsb2NhbFN0b3JhZ2UudXNlclJvbGUgfHwgXCJcIixcclxuXHRcdFx0XHR0eXBlOjAsXHJcbiAgICAgICAgICAgICAgICB0aW1lOicnLFx0XHJcbiAgICAgICAgICAgICAgICBjb250ZW50OicnLFxyXG4gICAgICAgICAgICAgICAgZmlsZXM6W10sXHJcbiAgICAgICAgICAgICAgICByZW1pbmQ6W10sXHJcbiAgICAgICAgICAgICAgICBzdGF0dXM6JycsXHJcbiAgICAgICAgICAgICAgICBjdXN0b21lcklkOicnLFxyXG4gICAgICAgICAgICAgICAgY3VzdG9tZXJOYW1lOicnLFxyXG4gICAgICAgICAgICAgICAgbGlua21hbk5hbWU6JycsXHJcbiAgICAgICAgICAgICAgICBsaW5rbWFuSWQ6W10sXHJcbiAgICAgICAgICAgICAgICBuZXh0VGltZTonJyxcclxuXHJcbiAgICAgICAgICAgICAgICBzZWxlY3RfaW5pdDowXHJcblx0XHRcdH1cclxuXHRcdH0sXHJcblx0XHRjcmVhdGVkKCl7XHJcblx0XHQvL1x0dGhpcy50eXBlID10aGlzLmRpYWxvZ01vZGVcclxuXHRcdH0sXHJcblx0XHRtb3VudGVkKCkge1xyXG5cdFx0XHQvLyBkaWFsb2dNb2RlID09MCAv5ouo5omT55S16K+d5pe25by55Ye6XHJcblx0XHRcdC8vZGlhbG9nTW9kZT09MSAgL+iHquihjOmAieaLqVxyXG5cdFx0XHRjb25zdCB0aGF0ID0gdGhpcztcdFx0XHRcclxuXHRcdFx0dGhhdC4kd2F0Y2goJ2NhbGxJZCcsKG4sbyk9PntcclxuICAgICAgICAgICAgICAgaWYobil7XHJcbiAgICAgICAgICAgICAgICAgICB0aGF0LnRpbWUgPSBsYXlkYXRlLm5vdyhuZXcgRGF0ZSgpLmdldFRpbWUoKSwgJ1lZWVktTU0tREQgaGg6bW0nKVxyXG4gICAgICAgICAgICAgICB9XHJcbiAgICAgICAgICAgIH0pXHJcblxyXG5cdFx0fSxcclxuXHRcdG1ldGhvZHM6IHtcclxuXHRcdFx0XHRcdFx0aW5pdChjdXN0b21lckluZm8sbGlua21hbk5hbWUpIHtcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIGNvbnNvbGUubG9nKHRoaXMuZGlhbG9nTW9kZSk7XHJcbiAgICAgICAgICAgICAgICAgIGlmKHRoaXMuZGlhbG9nTW9kZT09MSl7XHJcbiAgICAgICAgICAgICAgICB0aGlzLnR5cGU9XCJcIlxyXG4gICAgICAgICAgICB9ZWxzZXtcclxuICAgICAgICAgICAgICAgIHRoaXMudHlwZT0wXHJcbiAgICAgICAgICAgIH1cclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIGlmKGN1c3RvbWVySW5mbyl7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgdGhpcy5jdXN0b21lcklkID0gY3VzdG9tZXJJbmZvLmlkIHx8IFwiXCI7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgdGhpcy5jdXN0b21lck5hbWUgPSBjdXN0b21lckluZm8ubmFtZSB8fCBcIlwiO1xyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHRoaXMubGlua21hbk5hbWUgPSBsaW5rbWFuTmFtZSB8fCBjdXN0b21lckluZm8ubGlua21hbk5hbWUgfHwgXCJcIjtcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIH1cclxuXHRcdFx0XHRjb25zdCB0aGF0ID0gdGhpcztcclxuXHRcdFx0XHRjb25zdCBkaWFsb2cgPSBsYXllci5vcGVuKHtcclxuXHRcdFx0XHRcdHR5cGU6IFwiMVwiLFxyXG5cdFx0XHRcdFx0dGl0bGU6ICflhpnot5/ov5snLFxyXG5cdFx0XHRcdFx0Y29udGVudDogJChcIiNmb2xsb3ctYm94XCIpLFxyXG5cdFx0XHRcdFx0YXJlYTogWyc3MDBweCcsICc2MjBweCddLFxyXG5cdFx0XHRcdFx0YnRuOiBbJ+S/neWtmCddLFxyXG5cdFx0XHRcdFx0YnRuQWxpZ246ICdjJyxcclxuXHRcdFx0XHRcdHllcygpIHtcclxuICAgICAgICAgICAgICAgICAgICAgICAgdGhhdC5zYXZlKClcclxuXHRcdFx0XHRcdH0sXHJcblx0XHRcdFx0XHRlbmQoKSB7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICB0aGF0LnJlbWluZFdob0JveC52YWwobnVsbCkudHJpZ2dlcignY2hhbmdlJylcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIHRoYXQubGlua21hbkJveC52YWwobnVsbCkudHJpZ2dlcignY2hhbmdlJylcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIHRoYXQuY29udGVudD0nJztcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIHdpbmRvdy5jYWxsSWQgPSAnJztcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIHRoYXQuZmlsZXM9W107XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICB0aGF0LnJlbWluZD1bXTtcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIHRoYXQuc3RhdHVzPScnO1xyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgdGhhdC5jdXN0b21lcklkPScnO1xyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgdGhhdC5jdXN0b21lck5hbWU9Jyc7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICB0aGF0LmxpbmttYW5OYW1lPScnO1xyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgdGhhdC5saW5rbWFuSWQ9W107XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICB0aGF0Lm5leHRUaW1lPScnO1xyXG5cdFx0XHRcdFx0fSxcclxuICAgICAgICAgICAgICAgICAgICBzdWNjZXNzKCl7XHJcbiAgICAgICAgICAgICAgICBpZighdGhhdC5zZWxlY3RfaW5pdCl7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgIC8v5o+Q6YaS6LCB55yLXHJcbiAgICAgICAgICAgICAgICAgdGhhdC5yZW1pbmRXaG9Cb3ggPSAkKCcjcmVtaW5kV2hvJykuc2VsZWN0Mih7XHJcblx0XHRcdFx0cGxhY2Vob2xkZXI6IFwi6K+36YCJ5oupXCIsXHJcblx0XHRcdFx0bGFuZ3VhZ2U6ICd6aC1DTicsXHJcbiAgICAgICAgICAgICAgICBhbGxvd0NsZWFyOiB0cnVlLFxyXG5cdFx0XHRcdGFqYXg6IHtcclxuXHRcdFx0XHRcdHVybDogXCIvdXNlci9nZXRVc2VySW5mb1wiLFxyXG5cdFx0XHRcdFx0ZGF0YVR5cGU6ICdqc29uJyxcclxuXHRcdFx0XHRcdHR5cGU6IFwicG9zdFwiLFxyXG5cdFx0XHRcdFx0ZGVsYXk6IDI1MCxcclxuXHRcdFx0XHRcdGRhdGE6IGZ1bmN0aW9uKHBhcmFtcykge1xyXG5cdFx0XHRcdFx0XHRyZXR1cm4ge1xyXG5cdFx0XHRcdFx0XHRcdHBhZ2U6IHBhcmFtcy5wYWdlIHx8IDEsXHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICBST0xFX0lEOiBcImNmNTRjMGI1NTY3NTQyZTA4NDc3OGMzZjViMjMwMDU0LDRjYjYwMTgyYmIyOTRjZmJhNjIyZjk1MWUzOTBiYzZmLDFcIixcclxuXHRcdFx0XHRcdFx0XHRkYXRhOiB7XHJcblx0XHRcdFx0XHRcdFx0XHRxOiBwYXJhbXMudGVybSB8fCBcIlwiLCAvLyBzZWFyY2ggdGVybVxyXG5cdFx0XHRcdFx0XHRcdH1cclxuXHRcdFx0XHRcdFx0fTtcclxuXHRcdFx0XHRcdH0sXHJcblx0XHRcdFx0XHRwcm9jZXNzUmVzdWx0czogZnVuY3Rpb24oZGF0YSwgcGFyYW1zKSB7XHJcblx0XHRcdFx0XHRcdHBhcmFtcy5wYWdlID0gcGFyYW1zLnBhZ2UgfHwgMTtcclxuXHRcdFx0XHRcdFx0JC5lYWNoKGRhdGEsIGZ1bmN0aW9uKCkge1xyXG5cdFx0XHRcdFx0XHRcdHRoaXMuaWQgPSB0aGlzLlVTRVJfSUQ7XHJcblx0XHRcdFx0XHRcdFx0dGhpcy50ZXh0ID0gdGhpcy5OQU1FO1xyXG5cdFx0XHRcdFx0XHR9KVxyXG5cdFx0XHRcdFx0XHRyZXR1cm4ge1xyXG5cdFx0XHRcdFx0XHRcdHJlc3VsdHM6IGRhdGEsXHJcblx0XHRcdFx0XHRcdFx0cGFnaW5hdGlvbjoge1xyXG5cdFx0XHRcdFx0XHRcdFx0bW9yZTogZGF0YS5sZW5ndGggPT0gMTBcclxuXHRcdFx0XHRcdFx0XHR9XHJcblx0XHRcdFx0XHRcdH07XHJcblx0XHRcdFx0XHR9LFxyXG5cdFx0XHRcdFx0Y2FjaGU6IHRydWVcclxuXHRcdFx0XHR9LFxyXG5cdFx0XHRcdG1pbmltdW1JbnB1dExlbmd0aDogMFxyXG5cdFx0XHR9KS5vbihcImNoYW5nZVwiLGZ1bmN0aW9uKCl7XHJcbiAgICAgICAgICAgICAgICB0aGF0LnJlbWluZD1bXTtcclxuICAgICAgICAgICAgICAgIGZvciAodmFyIGkgPSAwOyBpIDwgdGhpcy5jaGlsZHJlbi5sZW5ndGg7IGkrKykge1xyXG4gICAgICAgICAgICAgICAgICAgIGlmKHRoaXMuY2hpbGRyZW5baV0uc2VsZWN0ZWQpICB0aGF0LnJlbWluZC5wdXNoKHRoaXMuY2hpbGRyZW5baV0udmFsdWUpXHJcbiAgICAgICAgICAgICAgICB9XHJcbiAgICAgICAgICAgIH0pXHJcbiAgICAgICAgICAgIC8v6IGU57O75Lq6XHJcbiAgICAgICAgICAgICAgICAgdGhhdC5saW5rbWFuQm94ID0gJCgnI2xpbmttYW5JZCcpLnNlbGVjdDIoe1xyXG5cdFx0XHRcdHBsYWNlaG9sZGVyOiBcIuivt+mAieaLqVwiLFxyXG5cdFx0XHRcdGxhbmd1YWdlOiAnemgtQ04nLFxyXG4gICAgICAgICAgICAgICAgYWxsb3dDbGVhcjogdHJ1ZSxcclxuXHRcdFx0XHRhamF4OiB7XHJcblx0XHRcdFx0XHR1cmw6IFwiL2xpbmttYW4vZ2V0TGlua21hbkluZm9cIixcclxuXHRcdFx0XHRcdGRhdGFUeXBlOiAnanNvbicsXHJcblx0XHRcdFx0XHR0eXBlOiBcInBvc3RcIixcclxuXHRcdFx0XHRcdGRlbGF5OiAyNTAsXHJcblx0XHRcdFx0XHRkYXRhOiBmdW5jdGlvbihwYXJhbXMpIHtcclxuXHRcdFx0XHRcdFx0cmV0dXJuIHtcclxuXHRcdFx0XHRcdFx0XHRwYWdlOiBwYXJhbXMucGFnZSB8fCAxLFxyXG5cdFx0XHRcdFx0XHRcdGRhdGE6IHtcclxuXHRcdFx0XHRcdFx0XHRcdHE6IHBhcmFtcy50ZXJtIHx8IFwiXCIgLy8gc2VhcmNoIHRlcm1cclxuXHRcdFx0XHRcdFx0XHR9LFxyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgaWQ6dGhhdC5jdXN0b21lcklkXHJcblx0XHRcdFx0XHRcdH07XHJcblx0XHRcdFx0XHR9LFxyXG5cdFx0XHRcdFx0cHJvY2Vzc1Jlc3VsdHM6IGZ1bmN0aW9uKGRhdGEsIHBhcmFtcykge1xyXG5cdFx0XHRcdFx0XHRwYXJhbXMucGFnZSA9IHBhcmFtcy5wYWdlIHx8IDE7XHJcblx0XHRcdFx0XHRcdCQuZWFjaChkYXRhLCBmdW5jdGlvbigpIHtcclxuXHRcdFx0XHRcdFx0XHR0aGlzLmlkID0gdGhpcy5saW5rbWFuTmFtZTtcclxuXHRcdFx0XHRcdFx0XHR0aGlzLnRleHQgPSB0aGlzLmxpbmttYW5OYW1lO1xyXG5cdFx0XHRcdFx0XHR9KVxyXG5cdFx0XHRcdFx0XHRyZXR1cm4ge1xyXG5cdFx0XHRcdFx0XHRcdHJlc3VsdHM6IGRhdGEsXHJcblx0XHRcdFx0XHRcdFx0cGFnaW5hdGlvbjoge1xyXG5cdFx0XHRcdFx0XHRcdFx0bW9yZTogZGF0YS5sZW5ndGggPT0gMTBcclxuXHRcdFx0XHRcdFx0XHR9XHJcblx0XHRcdFx0XHRcdH07XHJcblx0XHRcdFx0XHR9LFxyXG5cdFx0XHRcdFx0Y2FjaGU6IHRydWVcclxuXHRcdFx0XHR9LFxyXG5cdFx0XHRcdG1pbmltdW1JbnB1dExlbmd0aDogMFxyXG5cdFx0XHR9KS5vbihcImNoYW5nZVwiLGZ1bmN0aW9uKCl7XHJcbiAgICAgICAgICAgICAgICAgdGhhdC5saW5rbWFuSWQ9W107XHJcbiAgICAgICAgICAgICAgICBmb3IgKHZhciBpID0gMDsgaSA8IHRoaXMuY2hpbGRyZW4ubGVuZ3RoOyBpKyspIHtcclxuICAgICAgICAgICAgICAgICAgICBpZih0aGlzLmNoaWxkcmVuW2ldLnNlbGVjdGVkKSAgdGhhdC5saW5rbWFuSWQucHVzaCh0aGlzLmNoaWxkcmVuW2ldLnZhbHVlKVxyXG4gICAgICAgICAgICAgICAgfVxyXG4gICAgICAgICAgICAgICAgXHJcbiAgICAgICAgICAgIH0pXHJcbiAgICAgICAgICAgIHRoYXQuc2VsZWN0X2luaXQgPSAxXHJcbiAgICAgICAgICAgICB0aGF0LiRwYXJlbnQubG9hZGluZygpO1xyXG4gICAgICAgICAgICByZXF1aXJlKFsnd2VidXBsb2FkZXInXSwgKFdlYlVwbG9hZGVyKSA9PiB7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBhcHAubG9hZGluZyhcImNsb3NlXCIpXHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICB2YXIgdXBsb2FkZXIgPSBXZWJVcGxvYWRlci5jcmVhdGUoe1xyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHNlcnZlcjogXCIvY29udHJhY3QvdXBsb2FkQXR0YWNobWVudC5kb1wiLFxyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHJ1bnRpbWVPcmRlcjogJ2h0bWw1JyxcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBwaWNrOiB7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIGlkOiAnI2ZvbGxvd0ZpbGVzVXBsb2FkQnRuJyxcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgaW5uZXJIVE1MOiAn5LiK5Lyg6ZmE5Lu2J1xyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIH0sXHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgcmVzaXplOiBmYWxzZSxcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBmaWxlTnVtTGltaXQ6IDEwXHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICB9KTtcclxuXHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAvL+S4iuS8oOaIkOWKn1xyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgdXBsb2FkZXIub24oJ3VwbG9hZFN1Y2Nlc3MnLCBmdW5jdGlvbiAoZmlsZSwgcmVzKSB7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgdGhhdC5maWxlcy5wdXNoKHJlcylcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIH0pO1xyXG5cclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHVwbG9hZGVyLm9uKCd1cGxvYWRTdGFydCcsIGZ1bmN0aW9uICgpIHtcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICB0aGF0LiRwYXJlbnQubG9hZGluZygpXHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICB9KVxyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgLy/kuIrkvKDlh7rplJlcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHVwbG9hZGVyLm9uKCd1cGxvYWRFcnJvcicsIGZ1bmN0aW9uIChmaWxlKSB7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgbGF5ZXIubXNnKCfkuIrkvKDlh7rplJknKTtcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIH0pO1xyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgLy/lh7rplJlcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHVwbG9hZGVyLm9uKCdlcnJvcicsIGZ1bmN0aW9uIChyZXMpIHtcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBpZiAocmVzID09ICdRX1RZUEVfREVOSUVEJykge1xyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBsYXllci5tc2coJ+S4iuS8oOaWh+S7tuagvOW8j+mUmeivr++8jOivt+ajgOafpScpXHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgfVxyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgfSk7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAvL+S4iuS8oOWujOaIkFxyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgdXBsb2FkZXIub24oJ3VwbG9hZEZpbmlzaGVkJywgZnVuY3Rpb24gKGZpbGUpIHtcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICB1cGxvYWRlci5yZXNldCgpO1xyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHRoYXQuJHBhcmVudC5sb2FkaW5nKCdjbG9zZScpXHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICB9KTtcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIC8v5paH5Lu25Yqg5YWl6Zif5YiXXHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICB1cGxvYWRlci5vbignZmlsZVF1ZXVlZCcsIGZ1bmN0aW9uICgpIHtcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICB1cGxvYWRlci51cGxvYWQoKVxyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgfSlcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgfSlcclxuICAgICAgICAgICAgICAgIH1cclxuICAgICAgICAgICAgICAgICAgICB9XHJcblx0XHRcdFx0fSk7XHJcbiAgICAgICAgICAgICAgICBcclxuXHRcdFx0XHRcclxuXHRcdFx0fSxcclxuICAgICAgICAgICAgc2F2ZSgpe1xyXG4gICAgICAgICAgICAgICAgY29uc3QgdGhhdCA9IHRoaXM7XHJcbiAgICAgICAgICAgICAgICBpZighdG9wLmNhbGxJZCYmdGhpcy5kaWFsb2dNb2RlPT0wKXtcclxuICAgICAgICAgICAgICAgICAgICBsYXllci5tc2coJ+ivt+aMguaWreeUteivneS5i+WQjuWGjeS/neWtmOiusOW9lScpO1xyXG4gICAgICAgICAgICAgICAgICAgIHJldHVybjtcclxuICAgICAgICAgICAgICAgIH1lbHNlICBpZih0aGlzLnN0YXR1cz09PVwiXCIpe1xyXG4gICAgICAgICAgICAgICAgICAgIGxheWVyLm1zZygn6K+36YCJ5oup6Lef6L+b54q25oCBJyk7XHJcbiAgICAgICAgICAgICAgICAgICAgcmV0dXJuO1xyXG4gICAgICAgICAgICAgICAgfWVsc2UgaWYodGhpcy50eXBlPT09XCJcIil7XHJcbiAgICAgICAgICAgICAgICAgICAgIGxheWVyLm1zZygn6K+36YCJ5oup6Lef6L+b5pa55byPJyk7XHJcbiAgICAgICAgICAgICAgICAgICAgcmV0dXJuO1xyXG4gICAgICAgICAgICAgICAgfVxyXG4gICAgICAgICAgICAgICAgbGV0IGRhdGEgPSB7XHJcbiAgICAgICAgICAgICAgICBjb250ZW50OnRoaXMuY29udGVudCxcclxuICAgICAgICAgICAgICAgIGZpbGVzOnRoaXMuZmlsZXMsXHJcbiAgICAgICAgICAgICAgICByZW1pbmQ6dGhpcy5yZW1pbmQsXHJcbiAgICAgICAgICAgICAgICBzdGF0dXM6dGhpcy5zdGF0dXMsXHJcbiAgICAgICAgICAgICAgICBjdXN0b21lcklkOnRoaXMuY3VzdG9tZXJJZCxcclxuICAgICAgICAgICAgICAgIG5leHRUaW1lOnRoaXMubmV4dFRpbWUsXHJcbiAgICAgICAgICAgICAgICBjdXN0b21lck5hbWU6dGhpcy5jdXN0b21lck5hbWVcclxuICAgICAgICAgICAgICAgIH1cclxuICAgICAgICAgICAgICAgIGlmKHRoaXMuZGlhbG9nTW9kZT09MCl7XHJcbiAgICAgICAgICAgICAgICAgICAgZGF0YS5jYWxsSWQgPSB0b3AuY2FsbElkXHJcbiAgICAgICAgICAgICAgICB9XHJcbiAgICAgICAgICAgICAgICBpZih0aGlzLmxpbmttYW5OYW1lKXtcclxuICAgICAgICAgICAgICAgICAgICBkYXRhLmxpbmttYW5JZCA9IFtdXHJcbiAgICAgICAgICAgICAgICAgICAgZGF0YS5saW5rbWFuSWQucHVzaCh0aGlzLmxpbmttYW5OYW1lKTtcclxuICAgICAgICAgICAgICAgIH1lbHNle1xyXG4gICAgICAgICAgICAgICAgICAgIGRhdGEubGlua21hbklkID0gdGhpcy5saW5rbWFuSWRcclxuICAgICAgICAgICAgICAgIH1cclxuICAgICAgICAgICAgICAgICAgICBkYXRhLnR5cGUgPSB0aGlzLnR5cGU7XHJcbiAgICAgICAgICAgICAgICB5b2N0by5qc29uKCcvc2FsZUN1c3RvbWVyL3NhdmVQcm9jZXNzSW5mbycsZGF0YSwocmVzKT0+e1xyXG4gICAgICAgICAgICAgICAgICAgIGxheWVyLmNsb3NlQWxsKCk7XHJcbiAgICAgICAgICAgICAgICAgICBsYXllci5tc2coXCLplIDllK7liqjmgIHkv53lrZjmiJDlip9cIilcclxuICAgICAgICAgICAgICAgICAgIHRoYXQuJHBhcmVudCYmdGhhdC4kcGFyZW50LmdldENhc2VSZWNvcmQmJnRoYXQuJHBhcmVudC5nZXRDYXNlUmVjb3JkKClcclxuICAgICAgICAgICAgICAgICAgIHdpbmRvdy5wYXJlbnRGbmMmJndpbmRvdy5wYXJlbnRGbmMoKVxyXG4gICAgICAgICAgICAgICAgfSlcclxuICAgICAgICAgICAgfSxcclxuXHRcdFx0ZmlsZXNfZGVsKGluZGV4KXtcclxuXHRcdFx0XHRjb25zdCB0aGF0ID0gdGhpcztcclxuXHRcdFx0XHRjb25zdCBkaWFsb2cgPSBsYXllci5jb25maXJtKFwi56Gu6K6k5Yig6Zmk5q2k6aG5P1wiLCgpPT57XHJcblx0XHRcdFx0XHR0aGF0LmZpbGVzLnNwbGljZShpbmRleCwxKTtcclxuXHRcdFx0XHRcdGxheWVyLmNsb3NlKGRpYWxvZylcclxuXHRcdFx0XHR9KVxyXG5cdFx0XHR9LFxyXG5cdFx0XHRsYXlkYXRlKGUsZWxtLGZsYWcpIHtcclxuXHRcdFx0XHRjb25zdCB0aGF0ID0gdGhpc1xyXG5cdFx0XHRcdGxheWRhdGUoe1xyXG5cdFx0XHRcdFx0dkV2ZW50OmUsXHJcblx0XHRcdFx0XHRmb3JtYXQ6ICdZWVlZLU1NLUREIGhoOm1tOnNzJyxcclxuICAgICAgICAgICAgICAgICAgICBpc3RpbWU6IHRydWUsXHJcblx0XHRcdFx0XHRpc3RvZGF5OiB0cnVlLFxyXG5cdFx0XHRcdFx0Y2hvb3NlOiBmdW5jdGlvbihkYXRhKSB7XHJcblx0XHRcdFx0XHRcdGlmKCFmbGFnKSB7XHJcblx0XHRcdFx0XHRcdFx0dGhhdC5uZXh0VGltZSA9IGRhdGFcclxuXHRcdFx0XHRcdFx0fSBlbHNlIHtcclxuXHRcdFx0XHRcdFx0XHR0aGF0LnRpbWUgPSBkYXRhXHJcblx0XHRcdFx0XHRcdH1cclxuXHRcdFx0XHRcdH1cclxuXHRcdFx0XHR9KVxyXG5cdFx0XHR9XHJcblx0XHR9XHJcblx0fSlcclxuXHRyZXR1cm4gbW9kdWxlXHJcbn0pOyJdfQ==