"use strict";var config={isKhGh:yo.getQueryString("isKhGh")||0,role:localStorage.userRole,api_detail:"/mainIndex/showDxSwgwXstjDetail",time:yo.getQueryString("time"),id:yo.getQueryString("id"),saleName:yo.getQueryString("saleName"),api_user:"/user/getUserInfo",jidu:yo.getQueryString("jidu"),type:yo.getQueryString("type")};"销售精英"!=config.role&&"经理"!=config.role||(config.isKhGh=4);var app2={},app3={},app4={},option2=null,option3=null,option4=null;option2={title:{text:"",textStyle:{color:"#3a3a3a",fontStyle:"normal",fontSize:16}},tooltip:{trigger:"axis"},legend:{data:["商务顾问业绩目标完成情况"]},xAxis:{type:"category",data:[]},yAxis:{type:"value",max:""},series:[{data:[],type:"line",symbol:"circle",symbolSize:6,areaStyle:{normal:{color:new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:"#d9ebfd"},{offset:.5,color:"#f6fafe"},{offset:.8,color:"#ffffff"}])}},itemStyle:{normal:{color:"#459df5"}}}]},option3={title:{text:"",textStyle:{color:"#3a3a3a",fontStyle:"normal",fontSize:16}},tooltip:{trigger:"axis"},legend:{data:["商务顾问业绩目标完成情况"]},xAxis:{type:"category",data:[]},yAxis:{type:"value",max:""},series:[{data:[],type:"line",symbol:"circle",symbolSize:6,areaStyle:{normal:{color:new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:"#d9ebfd"},{offset:.5,color:"#f6fafe"},{offset:.8,color:"#ffffff"}])}},itemStyle:{normal:{color:"#459df5"}}}]},option4={title:{text:"",textStyle:{color:"#3a3a3a",fontStyle:"normal",fontSize:16}},tooltip:{trigger:"axis"},legend:{data:["商务顾问业绩目标完成情况"]},xAxis:{type:"category",data:[]},yAxis:{type:"value",max:""},series:[{data:[],type:"line",symbol:"circle",symbolSize:6,areaStyle:{normal:{color:new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:"#d9ebfd"},{offset:.5,color:"#f6fafe"},{offset:.8,color:"#ffffff"}])}},itemStyle:{normal:{color:"#459df5"}}}]};var pieChartOption2={title:{x:"center"},toolbox:{show:!1,feature:{restore:{show:!1},saveAsImage:{show:!1}}},tooltip:{formatter:"{a} <br/>{b} : {c}%"},series:[{name:"签约客户数",center:["50%","60%"],radius:"90%",axisLine:{lineStyle:{color:[[.3,"#ff878e"],[.8,"#ffce89"],[1,"#5bd4d1"]],width:10}},axisTick:{length:15,lineStyle:{color:"auto"}},splitLine:{length:20,lineStyle:{color:"auto"}},pointer:{width:3},type:"gauge",z:3,min:0,max:220,splitNumber:10,detail:{formatter:"{value}%"},data:[{value:45,name:"签约客户数",label:{textStyle:{fontSize:16}}}]}]},pieChartOption3={title:{x:"center"},toolbox:{show:!1,feature:{restore:{show:!1},saveAsImage:{show:!1}}},tooltip:{formatter:"{a} <br/>{b} : {c}%"},series:[{name:"订单金额",center:["50%","60%"],radius:"90%",axisLine:{lineStyle:{color:[[.3,"#ff878e"],[.8,"#ffce89"],[1,"#5bd4d1"]],width:10}},axisTick:{length:15,lineStyle:{color:"auto"}},splitLine:{length:20,lineStyle:{color:"auto"}},pointer:{width:3},type:"gauge",z:3,min:0,max:220,splitNumber:10,detail:{formatter:"{value}%"},data:[{value:45,name:"订单金额",label:{textStyle:{fontSize:16}}}]}]},pieChartOption4={title:{x:"center"},toolbox:{show:!1,feature:{restore:{show:!1},saveAsImage:{show:!1}}},tooltip:{formatter:"{a} <br/>{b} : {c}%"},series:[{name:"拜访量",center:["50%","60%"],radius:"90%",axisLine:{lineStyle:{color:[[.3,"#ff878e"],[.8,"#ffce89"],[1,"#5bd4d1"]],width:10}},axisTick:{length:15,lineStyle:{color:"auto"}},splitLine:{length:20,lineStyle:{color:"auto"}},pointer:{width:3},type:"gauge",z:3,min:0,max:220,splitNumber:10,detail:{formatter:"{value}%"},data:[{value:45,name:"拜访量",label:{textStyle:{fontSize:16}}}]}]};require(["select2"],function(e){window.app=new Vue({el:"#app",data:{dxmonth:(new Date).getFullYear().toString(),dxmonth2:config.time.split("-")[0],dxmonth3:config.time.split("-")[0],dxmonth4:config.time.split("-")[0],dx:{bfl:"",mbbfl:"",khs:"",mbkhs:"",ddje:"",mbddje:"",list:[],list2:[],list3:[],list4:[],idchange2:"",idchange3:"",idchange4:"",gxtime2:"",gxtime3:"",gxtime4:""},saleId:config.id,saleId2:config.id,saleId3:config.id,saleId4:config.id,listId:[],a1:"",b1:"",c1:"",d1:"",a2:"",b2:"",c2:"",d2:"",a3:"",b3:"",c3:"",d3:""},watch:{dxmonth2:function(e){this.getYjmb2(this.dxmonth2,0,this.saleId2,1)},saleId2:function(e){this.getYjmb2(this.dxmonth2,0,this.saleId2,2)},dxmonth3:function(e){this.getYjmb3(this.dxmonth3,1,this.saleId3,1)},saleId3:function(e){this.getYjmb3(this.dxmonth3,1,this.saleId3,2)},dxmonth4:function(e){this.getYjmb4(this.dxmonth4,2,this.saleId4,1)},saleId4:function(e){this.getYjmb4(this.dxmonth4,2,this.saleId4,2)}},computed:{},created:function(){document.getElementById("app").classList.remove("hide")},mounted:function(){var i=this;laydate.render({elem:"#dxmonth2",type:"year",done:function(e,t){i.dxmonth2=e}}),laydate.render({elem:"#dxmonth3",type:"year",done:function(e,t){i.dxmonth3=e}}),laydate.render({elem:"#dxmonth4",type:"year",done:function(e,t){i.dxmonth4=e}}),this.getYjmb2(i.dxmonth2,0,config.id),this.getYjmb3(i.dxmonth3,1,config.id),this.getYjmb4(i.dxmonth4,2,config.id),this.getUser(),this.$nextTick(function(){var e=$("#"+config.type);$("#"+config.type).remove(),$(".c-content").prepend(e)})},methods:{loading:function(e){"close"==e?layer.close(this.loadingSwitch):this.loadingSwitch=layer.load(3)},getUser:function(){var t=this;t.loading(),$.ajax({type:"post",url:config.api_user,async:!0,data:{ROLE_ID:"90564dd8b75a4f6d815ce418462d401c"},success:function(e){t.loading("close"),t.listId=e}})},getYjmb4:function(e,t,i,o){var a,d,s,r,x=this,n=x.nowMonth().split("-")[1];(new Date).getFullYear()==e&&("01"==n||"02"==n||"03"==n)?(a=Number(e)-1+"-04-01",d=e+"-03-31"):(a=e+"-04-01",d=Number(e)+1+"-03-31"),$.ajax({type:"post",url:config.api_detail,async:!0,data:{startTime:a,endTime:d,type:t,saleId:i},success:function(t){if(x.loading("close"),"00"==t.error){for(var e=[],i=[],a=0;a<t.result.length;a++){var d=t.result[a];e.push(d.name),i.push(d.bfl)}if(null==o){for(a=0;a<t.result.length;a++)config.jidu==t.result[a].time&&(x.dx.gxtime4=t.result[a].time,x.dx.idchange4=a,x.dx.bfl=t.result[a].bfl||0,x.dx.mbbfl=t.result[a].mbbfl||0,"Infinity"==(s=Math.round(x.dx.bfl/x.dx.mbbfl*1e4)/100)?s=100:isNaN(s)&&(s=0),100<=s&&(s=100));r=s+"%",x.dxhx4(s,r,x.dx.gxtime4)}else 1==o?(x.dx.gxtime4=t.result[x.dx.idchange4].time,x.dx.bfl=t.result[x.dx.idchange4].bfl||0,x.dx.mbbfl=t.result[x.dx.idchange4].mbbfl||0,"Infinity"==(s=Math.round(x.dx.bfl/x.dx.mbbfl*1e4)/100)?s=100:isNaN(s)&&(s=0),100<=s&&(s=100),r=s+"%",x.dxhx4(s,r,x.dx.gxtime4)):2==o&&(x.dx.gxtime4=t.result[x.dx.idchange4].time,x.dx.bfl=t.result[x.dx.idchange4].bfl||0,x.dx.mbbfl=t.result[x.dx.idchange4].mbbfl||0,"Infinity"==(s=Math.round(x.dx.bfl/x.dx.mbbfl*1e4)/100)?s=100:isNaN(s)&&(s=0),100<=s&&(s=100),r=s+"%",x.dxhx4(s,r,x.dx.gxtime4));var n=Number(Math.max.apply(null,i));n+=.2*n;var l=echarts.init(document.getElementById("main4"));option4.xAxis.data=["四月份","五月份","六月份","七月份","八月份","九月份","十月份","十一月份","十二月份","一月份","二月份","三月份"],option4.yAxis.max=Math.ceil(n),option4.series[0].data=i,l.setOption(option4),l.off("click"),l.on("click",function(e){x.dx.idchange4=e.dataIndex,x.dx.gxtime4=t.result[e.dataIndex].time,x.dx.bfl=t.result[e.dataIndex].bfl||0,x.dx.mbbfl=t.result[e.dataIndex].mbbfl||0,"Infinity"==(s=Math.round(x.dx.bfl/x.dx.mbbfl*1e4)/100)?s=100:isNaN(s)&&(s=0),100<=s&&(s=100),r=s+"%",x.dxhx4(s,r,x.dx.gxtime4)})}else layer.msg(t.msg)}})},getYjmb2:function(e,t,i,o){var a,d,s,r,x=this,n=x.nowMonth().split("-")[1];(new Date).getFullYear()==e&&("01"==n||"02"==n||"03"==n)?(a=Number(e)-1+"-04-01",d=e+"-03-31"):(a=e+"-04-01",d=Number(e)+1+"-03-31"),$.ajax({type:"post",url:config.api_detail,async:!0,data:{startTime:a,endTime:d,type:t,saleId:i},success:function(t){if(x.loading("close"),"00"==t.error){for(var e=[],i=[],a=0;a<t.result.length;a++){var d=t.result[a];e.push(d.name),i.push(d.khs)}if(null==o){for(a=0;a<t.result.length;a++)config.jidu==t.result[a].time&&(x.dx.gxtime2=t.result[a].time,x.dx.idchange2=a,x.dx.khs=t.result[a].khs||0,x.dx.mbkhs=t.result[a].mbkhs||0,"Infinity"==(s=Math.round(x.dx.khs/x.dx.mbkhs*1e4)/100)?s=100:isNaN(s)&&(s=0),100<=s&&(s=100));r=s+"%",x.dxhx2(s,r,x.dx.gxtime2)}else 1==o?(x.dx.gxtime2=t.result[x.dx.idchange2].time,x.dx.khs=t.result[x.dx.idchange2].khs||0,x.dx.mbkhs=t.result[x.dx.idchange2].mbkhs||0,"Infinity"==(s=Math.round(x.dx.khs/x.dx.mbkhs*1e4)/100)?s=100:isNaN(s)&&(s=0),100<=s&&(s=100),r=s+"%",x.dxhx2(s,r,x.dx.gxtime2)):2==o&&(x.dx.gxtime2=t.result[x.dx.idchange2].time,x.dx.khs=t.result[x.dx.idchange2].khs||0,x.dx.mbkhs=t.result[x.dx.idchange2].mbkhs||0,"Infinity"==(s=Math.round(x.dx.khs/x.dx.mbkhs*1e4)/100)?s=100:isNaN(s)&&(s=0),100<=s&&(s=100),r=s+"%",x.dxhx2(s,r,x.dx.gxtime2));var n=Number(Math.max.apply(null,i));n+=.2*n;var l=echarts.init(document.getElementById("main2"));option2.xAxis.data=["四月份","五月份","六月份","七月份","八月份","九月份","十月份","十一月份","十二月份","一月份","二月份","三月份"],option2.yAxis.max=Math.ceil(n),option2.series[0].data=i,l.setOption(option2),l.off("click"),l.on("click",function(e){x.dx.gxtime2=t.result[e.dataIndex].time,x.dx.idchange2=e.dataIndex,x.dx.khs=t.result[e.dataIndex].khs||0,x.dx.mbkhs=t.result[e.dataIndex].mbkhs||0,"Infinity"==(s=Math.round(x.dx.khs/x.dx.mbkhs*1e4)/100)?s=100:isNaN(s)&&(s=0),100<=s&&(s=100),r=s+"%",x.dxhx2(s,r,x.dx.gxtime2)})}else layer.msg(t.msg)}})},getYjmb3:function(e,t,i,o){var a,d,s,r,x=this,c=x.nowMonth(),n=c.split("-")[1];(new Date).getFullYear()==e&&("01"==n||"02"==n||"03"==n)?(a=Number(e)-1+"-04-01",d=e+"-03-31"):(a=e+"-04-01",d=Number(e)+1+"-03-31"),$.ajax({type:"post",url:config.api_detail,async:!0,data:{startTime:a,endTime:d,type:t,saleId:i},success:function(t){if(x.loading("close"),"00"==t.error){for(var e=[],i=[],a=0;a<t.result.length;a++){var d=t.result[a];e.push(d.name),i.push(d.ddje)}if(null==o){if(0==config.jidu||1==config.jidu||2==config.jidu||3==config.jidu)for(a=0;a<t.result.length;a++)c==t.result[a].time&&(x.dx.gxtime3=t.result[a].time,x.dx.idchange3=a,x.dx.ddje=t.result[a].ddje||0,x.dx.mbddje=t.result[a].mbddje||0,"Infinity"==(s=Math.round(x.dx.ddje/x.dx.mbddje*1e4)/100)?s=100:isNaN(s)&&(s=0),100<=s&&(s=100));else for(a=0;a<t.result.length;a++)config.jidu==t.result[a].time&&(x.dx.gxtime3=t.result[a].time,x.dx.idchange3=a,x.dx.ddje=t.result[a].ddje||0,x.dx.mbddje=t.result[a].mbddje||0,"Infinity"==(s=Math.round(x.dx.ddje/x.dx.mbddje*1e4)/100)?s=100:isNaN(s)&&(s=0),100<=s&&(s=100));r=s+"%",x.dxhx3(s,r,x.dx.gxtime3)}else 1==o?(x.dx.gxtime3=t.result[x.dx.idchange3].time,x.dx.ddje=t.result[x.dx.idchange3].ddje||0,x.dx.mbddje=t.result[x.dx.idchange3].mbddje||0,"Infinity"==(s=Math.round(x.dx.ddje/x.dx.mbddje*1e4)/100)?s=100:isNaN(s)&&(s=0),100<=s&&(s=100),r=s+"%",x.dxhx3(s,r,x.dx.gxtime3)):2==o&&(x.dx.gxtime3=t.result[x.dx.idchange3].time,x.dx.ddje=t.result[x.dx.idchange3].ddje||0,x.dx.mbddje=t.result[x.dx.idchange3].mbddje||0,"Infinity"==(s=Math.round(x.dx.ddje/x.dx.mbddje*1e4)/100)?s=100:isNaN(s)&&(s=0),100<=s&&(s=100),r=s+"%",x.dxhx3(s,r,x.dx.gxtime3));var n=Number(Math.max.apply(null,i));n+=.2*n;var l=echarts.init(document.getElementById("main3"));option3.xAxis.data=["四月份","五月份","六月份","七月份","八月份","九月份","十月份","十一月份","十二月份","一月份","二月份","三月份"],option3.yAxis.max=Math.ceil(n),option3.series[0].data=i,l.setOption(option3),l.off("click"),l.on("click",function(e){x.dx.idchange3=e.dataIndex,x.dx.gxtime3=t.result[e.dataIndex].time,x.dx.ddje=t.result[e.dataIndex].ddje||0,x.dx.mbddje=t.result[e.dataIndex].mbddje||0,console.log(x.dx.ddje),"Infinity"==(s=Math.round(x.dx.ddje/x.dx.mbddje*1e4)/100)?s=100:isNaN(s)&&(s=0),100<=s&&(s=100),r=s+"%",x.dxhx3(s,r,x.dx.gxtime3)})}else layer.msg(t.msg)}})},dxhx4:function(e,t,i){var a=this;pieChartOption4.series[0].max="100",pieChartOption4.series[0].data[0].value=e;var d=document.getElementById("pie4");pieChartOption4.title.text=i+"月份";var n=echarts.init(d);n.setOption(pieChartOption4);var l=$("#saleId4 option:selected").text(),o=i.split("-")[0],s=i.split("-")[1],r=new Date(o,s,0).getDate(),x=o+"-"+s+"-01",c=o+"-"+s+"-"+r;a.a1=l,a.b1=a.saleId4,a.c1=x,a.d1=c,""==l&&$.ajax({type:"post",url:config.api_user,async:!0,data:{ROLE_ID:"90564dd8b75a4f6d815ce418462d401c"},success:function(e){a.loading("close");for(var t=0;t<e.length;t++)config.id==e[t].USER_ID&&(l=e[t].NAME)}}),n.off("click"),n.on("click",function(e){var t="/static/page/index/saler_bfl_list.html?saleName="+l+"&saleId="+a.saleId4+"&startTime="+x+"&endTime="+c;layer.open({type:2,title:"商务顾问业绩目标-拜访量",content:t,area:["100%","100%"]})})},dxhx2:function(e,t,i){var a=this;pieChartOption2.series[0].max="100",pieChartOption2.series[0].data[0].value=e,pieChartOption2.title.text=i+"月份";var d=i.split("-")[0],n=i.split("-")[1],l=new Date(d,n,0).getDate(),o=d+"-"+n+"-01",s=d+"-"+n+"-"+l,r=document.getElementById("pie2");a.a2=o,a.b2=s,a.c2=a.saleId2;var x=echarts.init(r);x.setOption(pieChartOption2),x.off("click"),x.on("click",function(e){var t="/static/page/index/saler_turnover_list.html?startTime="+o+"&endTime="+s+"&dxswgwId="+a.saleId2+"&flag=1";layer.open({type:2,title:"商务顾问业绩目标-签约客户数完成情况",content:t,area:["100%","100%"]})})},dxhx3:function(e,t,i){var a=this;pieChartOption3.series[0].max="100",pieChartOption3.series[0].data[0].value=e;var d=i.split("-")[0],n=i.split("-")[1],l=new Date(d,n,0).getDate(),o=d+"-"+n+"-01",s=d+"-"+n+"-"+l,r=document.getElementById("pie3");pieChartOption3.title.text=i+"月份";var x=echarts.init(r);x.setOption(pieChartOption3),a.a3=o,a.b3=s,a.c3=a.saleId3,x.off("click"),x.on("click",function(e){var t="/static/page/index/saler_turnover_list.html?startTime="+o+"&endTime="+s+"&dxswgwId="+a.saleId3+"&flag=2";layer.open({type:2,title:"商务顾问业绩目标-委案金额",content:t,area:["100%","100%"]})})},khs:function(){var e="/static/page/index/saler_turnover_list.html?startTime="+this.a2+"&endTime="+this.b2+"&dxswgwId="+this.c2+"&flag=1";layer.open({type:2,title:"商务顾问业绩目标-签约客户数完成情况",content:e,area:["100%","100%"]})},ddje:function(){var e="/static/page/index/saler_turnover_list.html?startTime="+this.a3+"&endTime="+this.b3+"&dxswgwId="+this.c3+"&flag=2";layer.open({type:2,title:"商务顾问业绩目标-委案金额",content:e,area:["100%","100%"]})},bfl:function(){var e="/static/page/index/saler_bfl_list.html?saleName="+this.a1+"&saleId="+this.b1+"&startTime="+this.c1+"&endTime="+this.d1;layer.open({type:2,title:"商务顾问业绩目标-拜访量",content:e,area:["100%","100%"]})},nowMonth:function(){var e=new Date,t=e.getFullYear(),i=e.getMonth()+1;return i=i<10?"0"+i:i,t.toString()+"-"+i.toString()}}})}),window.parentFnc=function(e){layer.closeAll(),window.app.getCaseRecord(window.app.type)};