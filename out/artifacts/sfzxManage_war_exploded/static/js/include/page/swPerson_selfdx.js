"use strict";var config={isKhGh:yo.getQueryString("isKhGh")||0,role:localStorage.userRole,userId:JSON.parse(localStorage.userInfo).list[0].USER_ID,api_detail:"/mainIndex/showDxSwgwXstjDetail",time:yo.getQueryString("time"),id:yo.getQueryString("id"),saleName:yo.getQueryString("saleName"),api_user:"/user/getUserInfo",ch:yo.getQueryString("ch"),jidu:yo.getQueryString("jidu"),type:yo.getQueryString("type")};"销售精英"!=config.role&&"经理"!=config.role||(config.isKhGh=4);var app={},app2={},app3={},app4={},option=null,option2=null,option3=null,option4=null;option={title:{text:"",textStyle:{color:"#3a3a3a",fontStyle:"normal",fontSize:16}},tooltip:{trigger:"axis"},legend:{data:["商务顾问业绩目标完成情况"]},xAxis:{type:"category",data:[]},yAxis:{type:"value",max:""},series:[{data:[],type:"line",symbol:"circle",symbolSize:6,areaStyle:{normal:{color:new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:"#d9ebfd"},{offset:.5,color:"#f6fafe"},{offset:.8,color:"#ffffff"}])}},itemStyle:{normal:{color:"#459df5"}}}]},option2={title:{text:"",textStyle:{color:"#3a3a3a",fontStyle:"normal",fontSize:16}},tooltip:{trigger:"axis"},legend:{data:["商务顾问业绩目标完成情况"]},xAxis:{type:"category",data:[]},yAxis:{type:"value",max:""},series:[{data:[],type:"line",symbol:"circle",symbolSize:6,areaStyle:{normal:{color:new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:"#d9ebfd"},{offset:.5,color:"#f6fafe"},{offset:.8,color:"#ffffff"}])}},itemStyle:{normal:{color:"#459df5"}}}]},option3={title:{text:"",textStyle:{color:"#3a3a3a",fontStyle:"normal",fontSize:16}},tooltip:{trigger:"axis"},legend:{data:["商务顾问业绩目标完成情况"]},xAxis:{type:"category",data:[]},yAxis:{type:"value",max:""},series:[{data:[],type:"line",symbol:"circle",symbolSize:6,areaStyle:{normal:{color:new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:"#d9ebfd"},{offset:.5,color:"#f6fafe"},{offset:.8,color:"#ffffff"}])}},itemStyle:{normal:{color:"#459df5"}}}]},option4={title:{text:"",textStyle:{color:"#3a3a3a",fontStyle:"normal",fontSize:16}},tooltip:{trigger:"axis"},legend:{data:["商务顾问业绩目标完成情况"]},xAxis:{type:"category",data:[]},yAxis:{type:"value",max:""},series:[{data:[],type:"line",symbol:"circle",symbolSize:6,areaStyle:{normal:{color:new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:"#d9ebfd"},{offset:.5,color:"#f6fafe"},{offset:.8,color:"#ffffff"}])}},itemStyle:{normal:{color:"#459df5"}}}]};var pieChartOption2={title:{x:"center"},toolbox:{show:!1,feature:{restore:{show:!1},saveAsImage:{show:!1}}},tooltip:{formatter:"{a} <br/>{b} : {c}%"},series:[{name:"签约客户数",center:["50%","60%"],radius:"90%",axisLine:{lineStyle:{color:[[.3,"#ff878e"],[.8,"#ffce89"],[1,"#5bd4d1"]],width:10}},axisTick:{length:15,lineStyle:{color:"auto"}},splitLine:{length:20,lineStyle:{color:"auto"}},pointer:{width:3},type:"gauge",z:3,min:0,max:220,splitNumber:10,detail:{formatter:"{value}%"},data:[{value:45,name:"签约客户数",label:{textStyle:{fontSize:16}}}]}]},pieChartOption3={title:{x:"center"},toolbox:{show:!1,feature:{restore:{show:!1},saveAsImage:{show:!1}}},tooltip:{formatter:"{a} <br/>{b} : {c}%"},series:[{name:"订单金额",center:["50%","60%"],radius:"90%",axisLine:{lineStyle:{color:[[.3,"#ff878e"],[.8,"#ffce89"],[1,"#5bd4d1"]],width:10}},axisTick:{length:15,lineStyle:{color:"auto"}},splitLine:{length:20,lineStyle:{color:"auto"}},pointer:{width:3},type:"gauge",z:3,min:0,max:220,splitNumber:10,detail:{formatter:"{value}%"},data:[{value:45,name:"订单金额",label:{textStyle:{fontSize:16}}}]}]},pieChartOption4={title:{x:"center"},toolbox:{show:!1,feature:{restore:{show:!1},saveAsImage:{show:!1}}},tooltip:{formatter:"{a} <br/>{b} : {c}%"},series:[{name:"拜访量",center:["50%","60%"],radius:"90%",axisLine:{lineStyle:{color:[[.3,"#ff878e"],[.8,"#ffce89"],[1,"#5bd4d1"]],width:10}},axisTick:{length:15,lineStyle:{color:"auto"}},splitLine:{length:20,lineStyle:{color:"auto"}},pointer:{width:3},type:"gauge",z:3,min:0,max:220,splitNumber:10,detail:{formatter:"{value}%"},data:[{value:45,name:"拜访量",label:{textStyle:{fontSize:16}}}]}]};require(["select2"],function(e){window.app=new Vue({el:"#app",data:{dxmonth:"",dxmonth2:"",dxmonth3:"",dxmonth4:"",dx:{khs:"",mbkhs:"",ddje:"",mbddje:"",bfl:"",mbbfl:"",list:[],list2:[],list3:[],list4:[],idchange2:"",idchange3:"",idchange4:"",gxtime:"",gxtime2:"",gxtime3:"",gxtime4:""},saleId:config.userId,listId:[],a1:"",b1:"",c1:"",d1:"",a2:"",b2:"",c2:"",d2:"",a3:"",b3:"",c3:"",d3:""},watch:{dxmonth2:function(e){this.getYjmb2(this.dxmonth2,0,1)},dxmonth3:function(e){this.getYjmb3(this.dxmonth3,1,1)},dxmonth4:function(e){this.getYjmb4(this.dxmonth4,2,1)}},computed:{},created:function(){document.getElementById("app").classList.remove("hide")},mounted:function(){var a=this,e=a.nowMonth().split("-")[1],t=(new Date).getFullYear();"01"==e||"02"==e||"03"==e?(this.dxmonth2=Number(t)-1,this.dxmonth3=Number(t)-1,this.dxmonth4=Number(t)-1):(this.dxmonth2=t,this.dxmonth3=t,this.dxmonth4=t),laydate.render({elem:"#dxmonth2",type:"year",done:function(e,t){a.dxmonth2=e}}),laydate.render({elem:"#dxmonth3",type:"year",done:function(e,t){a.dxmonth3=e}}),laydate.render({elem:"#dxmonth4",type:"year",done:function(e,t){a.dxmonth4=e}}),this.getYjmb2(this.dxmonth2,0),this.getYjmb3(this.dxmonth3,1),this.getYjmb4(this.dxmonth4,2),this.$nextTick(function(){var e=$("#"+config.type);$("#"+config.type).remove(),$(".c-content").prepend(e)})},methods:{loading:function(e){"close"==e?layer.close(this.loadingSwitch):this.loadingSwitch=layer.load(3)},getYjmb2:function(e,t,d){var a,i,r,s,x=this,c=x.nowMonth();c.split("-")[1],(new Date).getFullYear();a=e+"-04-01",i=Number(e)+1+"-03-31",$.ajax({type:"post",url:config.api_detail,async:!0,data:{startTime:a,endTime:i,type:t,saleId:x.saleId},success:function(t){if(x.loading("close"),"00"==t.error){for(var e=[],a=[],i=0;i<t.result.length;i++){var n=t.result[i];e.push(n.name),a.push(n.khs)}if(null==d){for(i=0;i<t.result.length;i++)c==t.result[i].time&&(x.dx.gxtime2=t.result[i].time,x.dx.idchange2=i,x.dx.khs=t.result[i].khs||0,x.dx.mbkhs=t.result[i].mbkhs||0,"Infinity"==(r=Math.round(x.dx.khs/x.dx.mbkhs*1e4)/100)?r=100:isNaN(r)&&(r=0),100<=r&&(r=100));s=r+"%",x.dxhx2(r,s,x.dx.gxtime2)}else 1==d&&(x.dx.gxtime2=t.result[x.dx.idchange2].time,x.dx.gxtime2=t.result[x.dx.idchange2].time,x.dx.khs=t.result[x.dx.idchange2].khs||0,x.dx.mbkhs=t.result[x.dx.idchange2].mbkhs||0,"Infinity"==(r=Math.round(x.dx.khs/x.dx.mbkhs*1e4)/100)?r=100:isNaN(r)&&(r=0),100<=r&&(r=100),s=r+"%",x.dxhx2(r,s,x.dx.gxtime2));var o=Number(Math.max.apply(null,a));o+=.2*o;var l=echarts.init(document.getElementById("main2"));option2.xAxis.data=["四月份","五月份","六月份","七月份","八月份","九月份","十月份","十一月份","十二月份","一月份","二月份","三月份"],option2.yAxis.max=Math.ceil(o),option2.series[0].data=a,l.setOption(option2),l.off("click"),l.on("click",function(e){x.dx.gxtime2=t.result[e.dataIndex].time,x.dx.idchange2=e.dataIndex,x.dx.khs=t.result[e.dataIndex].khs||0,x.dx.mbkhs=t.result[e.dataIndex].mbkhs||0,"Infinity"==(r=Math.round(x.dx.khs/x.dx.mbkhs*1e4)/100)?r=100:isNaN(r)&&(r=0),100<=r&&(r=100),s=r+"%",x.dxhx2(r,s,x.dx.gxtime2)})}else layer.msg(t.msg)}})},getYjmb3:function(e,t,d){var a,i,r,s,x=this,c=x.nowMonth();c.split("-")[1],(new Date).getFullYear();a=e+"-04-01",i=Number(e)+1+"-03-31",$.ajax({type:"post",url:config.api_detail,async:!0,data:{startTime:a,endTime:i,type:t,saleId:x.saleId},success:function(t){if(x.loading("close"),"00"==t.error){for(var e=[],a=[],i=0;i<t.result.length;i++){var n=t.result[i];e.push(n.name),a.push(n.ddje)}if(null==d){for(i=0;i<t.result.length;i++)c==t.result[i].time&&(x.dx.gxtime3=t.result[i].time,x.dx.idchange3=i,x.dx.ddje=t.result[i].ddje||0,x.dx.mbddje=t.result[i].mbddje||0,"Infinity"==(r=Math.round(x.dx.ddje/x.dx.mbddje*1e4)/100)?r=100:isNaN(r)&&(r=0),100<=r&&(r=100));s=r+"%",x.dxhx3(r,s,x.dx.gxtime3)}else 1==d&&(x.dx.gxtime3=t.result[x.dx.idchange3].time,x.dx.ddje=t.result[x.dx.idchange3].ddje||0,x.dx.mbddje=t.result[x.dx.idchange3].mbddje||0,"Infinity"==(r=Math.round(x.dx.ddje/x.dx.mbddje*1e4)/100)?r=100:isNaN(r)&&(r=0),100<=r&&(r=100),s=r+"%",x.dxhx3(r,s,x.dx.gxtime3));var o=Number(Math.max.apply(null,a));o+=.2*o;var l=echarts.init(document.getElementById("main3"));option3.xAxis.data=["四月份","五月份","六月份","七月份","八月份","九月份","十月份","十一月份","十二月份","一月份","二月份","三月份"],option3.yAxis.max=Math.ceil(o),option3.series[0].data=a,l.setOption(option3),l.off("click"),l.on("click",function(e){x.dx.idchange3=e.dataIndex,x.dx.gxtime3=t.result[e.dataIndex].time,x.dx.ddje=t.result[e.dataIndex].ddje||0,x.dx.mbddje=t.result[e.dataIndex].mbddje||0,"Infinity"==(r=Math.round(x.dx.ddje/x.dx.mbddje*1e4)/100)?r=100:isNaN(r)&&(r=0),100<=r&&(r=100),s=r+"%",x.dxhx3(r,s,x.dx.gxtime3)})}else layer.msg(t.msg)}})},getYjmb4:function(e,t,d){var a,i,r,s,x=this,c=x.nowMonth();c.split("-")[1],(new Date).getFullYear();a=e+"-04-01",i=Number(e)+1+"-03-31",$.ajax({type:"post",url:config.api_detail,async:!0,data:{startTime:a,endTime:i,type:t,saleId:x.saleId},success:function(t){if(x.loading("close"),"00"==t.error){for(var e=[],a=[],i=0;i<t.result.length;i++){var n=t.result[i];e.push(n.name),a.push(n.bfl)}if(null==d){for(i=0;i<t.result.length;i++)c==t.result[i].time&&(x.dx.gxtime4=t.result[i].time,x.dx.idchange4=i,x.dx.bfl=t.result[i].bfl||0,x.dx.mbbfl=t.result[i].mbbfl||0,"Infinity"==(r=Math.round(x.dx.bfl/x.dx.mbbfl*1e4)/100)?r=100:isNaN(r)&&(r=0),100<=r&&(r=100));s=r+"%",x.dxhx4(r,s,x.dx.gxtime4)}else 1==d&&(x.dx.gxtime4=t.result[x.dx.idchange4].time,x.dx.bfl=t.result[x.dx.idchange4].bfl||0,x.dx.mbbfl=t.result[x.dx.idchange4].mbbfl||0,"Infinity"==(r=Math.round(x.dx.bfl/x.dx.mbbfl*1e4)/100)?r=100:isNaN(r)&&(r=0),100<=r&&(r=100),s=r+"%",x.dxhx4(r,s,x.dx.gxtime4));var o=Number(Math.max.apply(null,a));o+=.2*o;var l=echarts.init(document.getElementById("main4"));option4.xAxis.data=["四月份","五月份","六月份","七月份","八月份","九月份","十月份","十一月份","十二月份","一月份","二月份","三月份"],option4.yAxis.max=Math.ceil(o),option4.series[0].data=a,l.setOption(option4),l.off("click"),l.on("click",function(e){x.dx.idchange4=e.dataIndex,x.dx.gxtime4=t.result[e.dataIndex].time,x.dx.bfl=t.result[e.dataIndex].bfl||0,x.dx.mbbfl=t.result[e.dataIndex].mbbfl||0,"Infinity"==(r=Math.round(x.dx.bfl/x.dx.mbbfl*1e4)/100)?r=100:isNaN(r)&&(r=0),100<=r&&(r=100),s=r+"%",x.dxhx4(r,s,x.dx.gxtime4)})}else layer.msg(t.msg)}})},dxhx2:function(e,t,a){var i=this;pieChartOption2.series[0].max="100",pieChartOption2.series[0].data[0].value=e,pieChartOption2.title.text=a+"月份";var n=document.getElementById("pie2"),o=a.split("-")[0],l=a.split("-")[1],d=new Date(o,l,0).getDate(),r=o+"-"+l+"-01",s=o+"-"+l+"-"+d;i.a2=r,i.b2=s,i.c2=i.saleId2;var x=echarts.init(n);x.setOption(pieChartOption2),x.off("click"),x.on("click",function(e){var t="/static/page/index/saler_turnover_list.html?startTime="+r+"&endTime="+s+"&dxswgwId="+i.saleId2+"&flag=1&htz=1";layer.open({type:2,title:"电销商务顾问业绩目标-签约客户数完成情况",content:t,area:["100%","100%"]})})},dxhx3:function(e,t,a){var i=this;pieChartOption3.series[0].max="100",pieChartOption3.series[0].data[0].value=e;var n=document.getElementById("pie3");pieChartOption3.title.text=a+"月份";var o=a.split("-")[0],l=a.split("-")[1],d=new Date(o,l,0).getDate(),r=o+"-"+l+"-01",s=o+"-"+l+"-"+d,x=echarts.init(n);i.a3=r,i.b3=s,i.c3=i.saleId3,x.setOption(pieChartOption3),x.off("click"),x.on("click",function(e){var t="/static/page/index/saler_turnover_list.html?startTime="+r+"&endTime="+s+"&dxswgwId="+i.saleId3+"&flag=2&htz=1";layer.open({type:2,title:"电销商务顾问业绩目标-委案金额",content:t,area:["100%","100%"]})})},dxhx4:function(e,t,a){var i=this;pieChartOption4.series[0].max="100",pieChartOption4.series[0].data[0].value=e;var n=document.getElementById("pie4");pieChartOption4.title.text=a+"月份";var o=echarts.init(n);o.setOption(pieChartOption4);var l=$("#saleId4 option:selected").text(),d=a.split("-")[0],r=a.split("-")[1],s=new Date(d,r,0).getDate(),x=d+"-"+r+"-01",c=d+"-"+r+"-"+s;i.a1=l,i.b1=i.saleId4,i.c1=x,i.d1=c,""==l&&$.ajax({type:"post",url:config.api_user,async:!0,data:{ROLE_ID:"b729e9334ad64c15a4e47d88b8c2638f,e350acd05a6244c79136616302b7dfd6,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b"},success:function(e){i.loading("close");for(var t=0;t<e.length;t++)config.id==e[t].USER_ID&&(l=e[t].NAME)}}),o.off("click"),o.on("click",function(e){var t="/static/page/index/saler_bfl_list1.html?saleName="+l+"&saleId="+i.saleId4+"&startTime="+x+"&endTime="+c;layer.open({type:2,title:"电销商务顾问业绩目标-拜访量",content:t,area:["100%","100%"]})})},khs:function(){var e="/static/page/index/saler_turnover_list.html?startTime="+this.a2+"&endTime="+this.b2+"&dxswgwId="+this.c2+"&flag=1&htz=1";layer.open({type:2,title:"商务顾问业绩目标-签约客户数完成情况",content:e,area:["100%","100%"]})},ddje:function(){var e="/static/page/index/saler_turnover_list.html?startTime="+this.a3+"&endTime="+this.b3+"&dxswgwId="+this.c3+"&flag=2&htz=1";layer.open({type:2,title:"商务顾问业绩目标-委案金额",content:e,area:["100%","100%"]})},bfl:function(){var e="/static/page/index/saler_bfl_list.html?saleName="+this.a1+"&saleId="+this.b1+"&startTime="+this.c1+"&endTime="+this.d1;layer.open({type:2,title:"商务顾问业绩目标-拜访量",content:e,area:["100%","100%"]})},nowMonth:function(){var e=new Date,t=e.getFullYear(),a=e.getMonth()+1;return a=a<10?"0"+a:a,t.toString()+"-"+a.toString()}}})}),window.parentFnc=function(e){layer.closeAll(),window.app.getCaseRecord(window.app.type)};