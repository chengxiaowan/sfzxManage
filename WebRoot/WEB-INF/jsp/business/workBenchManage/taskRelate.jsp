<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<div class="col-md-6">
	<div class="form-group">
		<label class="col-sm-4 control-label">关联任务： </label>
		<div class="col-sm-8">
			<input class="form-control" type="text" data-validate="require|maxLength=50" readonly="readonly" placeholder="这里选择要关联的任务" id="taskName" name="taskName" value="${dataList[0].taskName }"
				onclick="chooseTask()" style="width: 98%">
		</div>
	</div>
</div>
<script type="text/javascript">
	function chooseTask() {
		sessionStorage.ListTagsCache = '0' //关闭缓存
		var index = layer
				.open({
					type : 2,
					title : '选择要关联任务',
					content : '${pageContext.request.contextPath}/workBench/chooseTask.do?orderId=${pd.orderId }&type=${pd.type}',
					area : [ '90%', '90%' ],
					btn : [ '确定', '取消' ],
					yes : function(index, layero) {
						var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
						var data = iframeWin.pushToParent()
						layer.close(index)
						$("#taskId").val(data.ids); //赋值子页面传过来的IDS
						$("#taskName").val(data.names);
						sessionStorage.ListTagsCache = '0' //关闭缓存
					}
				});
	}
</script>
</html>