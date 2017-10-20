<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>短信任务管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function showTestModal(smsContent){
			$('#smsContent').val(smsContent);
			$('#smsContentSize').text(smsContent.length);
			$('#smsContentModal').modal('show');
		}
		
		function viewYy(remarks,reviewTime){
			$("#reviewRemarks").html("审核意见："+remarks);
			$("#reviewTime").html("审核时间："+reviewTime);
			$('#reviewModal').modal('show');
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sms/smsTask/">信息列表</a></li>
		<!--<shiro:hasPermission name="sms:smsTask:edit"><li><a href="${ctx}/sms/smsTask/form">短信任务添加</a></li></shiro:hasPermission>-->
	</ul>
	<form:form id="searchForm" modelAttribute="smsTask" action="${ctx}/sms/smsTask/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>批次ID：</label>
				<form:input path="taskid" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>账户ID：</label>
				<form:input path="accId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>用户ID：</label>
				<form:input path="userid" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>发送时间：</label>
				<input name="sendDatetimeQ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsTask.sendDatetimeQ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="sendDatetimeZ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsTask.sendDatetimeZ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> 
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>批次ID　　</th>
				<th>账户ID　　</th>
				<th>用户ID　　</th>
				<th>短信内容　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　</th>
				<th>状态　　　　</th>
				<th>发送时间　　</th>
				<th>结束时间　　</th>
				<th>发送总量</th>
				<th>成功量</th>
				<th>失败量</th>
				<th>更新时间　　</th>
				<!--<shiro:hasPermission name="sms:smsTask:edit"><th>操作</th></shiro:hasPermission>-->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsTask">
			<tr ${smsTask.sendStatus eq -2 ? 'class="error"' : '' }>
				<td>
					${smsTask.taskid}
				</td>
				<td>
					${smsTask.accId}
				</td>
				<td>
					${smsTask.userid}
				</td>
				<td>
					${fn:substring(smsTask.smsContent, 0, 60)}...
					<a href="javascript:showTestModal('${smsTask.smsContent}')">[更多查看]</a>
				</td>
				<td>
					${fns:getDictLabel(smsTask.sendStatus, 'task_send_status', '')}
					<c:if test="${smsTask.sendStatus eq -2}">
					<a href="javascript:viewYy('${smsTask.recheckRemarks}','<fmt:formatDate value="${smsTask.recheckTime}" pattern="yyyy-MM-dd HH:mm:ss"/>')">[查看原因]</a>
					</c:if>
				</td>				
				<td>
					<fmt:formatDate value="${smsTask.sendDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${smsTask.endDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${smsTask.sendCount}
				</td>
				<td>
					${smsTask.successCount}
				</td>
				<td>
					${smsTask.failCount}
				</td>
				<td>
					<fmt:formatDate value="${smsTask.updateDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<!--<shiro:hasPermission name="sms:smsTask:edit"><td>
    				<a href="${ctx}/sms/smsTask/form?id=${smsTask.id}">修改</a>
					<a href="${ctx}/sms/smsTask/delete?id=${smsTask.id}" onclick="return confirmx('确认要删除该短信任务吗？', this.href)">删除</a>
				</td></shiro:hasPermission>-->
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!-- 短信内容 -->
	<div class="modal fade" style="display:none;" id="smsContentModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   		<div class="modal-dialog">
      		<div class="modal-content">
        		<div class="modal-header">
            		<button type="button" class="close" 
               			data-dismiss="modal" aria-hidden="true">
                  		&times;
            		</button>
		            <h4 class="modal-title" id="myModalLabel">
		              	短信内容
		            </h4>
         		</div>
         		<div class="modal-body">
         			<form id="gateWaySendForm" class="form-horizontal">
	                	<div class="control-group">
	                     	<div class="controls1">
	                         	<textarea style="width:498px;" readonly="readonly" name="smsContent" id="smsContent" rows="6" cols="8"></textarea>
	                         	共 <label name="smsContentSize" id="smsContentSize"></label>  个字。
	                     	</div>
	                 	</div>
                 	</form>
         		</div>
      		</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	<!-- END -->
	
	
	<!-- 审核详情 -->
	<div class="modal fade" style="display:none;" id="reviewModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
   		<div class="modal-dialog">
      		<div class="modal-content">
        		<div class="modal-header">
            		<button type="button" class="close" 
               			data-dismiss="modal" aria-hidden="true">
                  		&times;
            		</button>
		            <h4 class="modal-title" id="myModalLabel">
		              	审核详情
		            </h4>
         		</div>
         		<div class="modal-body">
         			<div class="control-group">
	                     <div class="controls1" id="reviewRemarks">
	                     </div>
	                </div>
	                <div class="control-group">
	                     <div class="controls1" id="reviewTime">
	                     </div>
	                </div>
         		</div>
      		</div><!-- /.modal-content -->
      		<div class="modal-footer">
      			<input id="btnSubmit2" class="btn btn-primary" type="button" value="关 闭" data-dismiss="modal" aria-hidden="true"/>
      		</div>	
		</div><!-- /.modal -->
	</div>
	<!-- END -->	
</body>
</html>