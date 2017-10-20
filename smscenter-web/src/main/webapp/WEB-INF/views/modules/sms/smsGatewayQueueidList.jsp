<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>网关队列管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sms/smsGatewayQueueid/">信息列表</a></li>
		<shiro:hasPermission name="sms:smsGatewayQueueid:edit"><li><a href="${ctx}/sms/smsGatewayQueueid/form">信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="smsGatewayQueueid" action="${ctx}/sms/smsGatewayQueueid/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>网关名称：</label>
				<form:select path="gwCode" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getGatewayList()}" itemLabel="gwName" itemValue="gwCode" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>队列名称：</label>
				<form:input path="queueName" class="input-medium" maxlength="20"/>
			</li>			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>网关编号</th>
				<th>网关名称</th>
				<th>业务模型</th>
				<th>队列组</th>
				<th>队列编号</th>
				<th>队列名称</th>
				<th>位置</th>
				<th>当前速率</th>
				<th>创建时间</th>
				<th>权重</th>
				<shiro:hasPermission name="sms:smsGatewayQueueid:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsGatewayQueueid">
			<tr>
				<td>
					${smsGatewayQueueid.gwCode}
				</td>
				<td>
					${smsGatewayQueueid.gwName}
				</td>
				<td>
					${smsGatewayQueueid.serviceid}
				</td>
				<td>
					${smsGatewayQueueid.topic}
				</td>
				<td>
					${smsGatewayQueueid.queueId}
				</td>
				<td>
					${smsGatewayQueueid.queueName}
				</td>
				<td>
					${smsGatewayQueueid.offset}
				</td>
				<td>
					${smsGatewayQueueid.tps}
				</td>
				<td>
					<fmt:formatDate value="${smsGatewayQueueid.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${smsGatewayQueueid.weight}
				</td>
				<shiro:hasPermission name="sms:smsGatewayQueueid:edit"><td>
    				<a href="${ctx}/sms/smsGatewayQueueid/form?id=${smsGatewayQueueid.id}">修改</a>
					<a href="${ctx}/sms/smsGatewayQueueid/delete?id=${smsGatewayQueueid.id}&gwCode=${smsGatewayQueueid.gwCode}" onclick="return confirmx('确认要删除该网关队列吗？', this.href)">删除</a>
					<a href="javascript:windowOpen('${debug}/cache.jsp?type=11&key=${smsGatewayQueueid.gwCode}','网关队列信息',900,550);">调试</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>