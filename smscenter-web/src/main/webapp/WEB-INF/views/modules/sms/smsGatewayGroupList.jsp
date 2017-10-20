<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通道分组管理</title>
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
		<li class="active"><a href="${ctx}/sms/smsGatewayGroup/">信息列表</a></li>
		<shiro:hasPermission name="sms:smsGatewayGroup:edit"><li><a href="${ctx}/sms/smsGatewayGroup/form">信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="smsGatewayGroup" action="${ctx}/sms/smsGatewayGroup/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>分组名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="45" class="input-medium"/>
			</li>
			<li><label>分组状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="1" label="启用"/>
					<form:option value="0" label="禁用"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>分组名称</th>
				<th>分组描述</th>
				<th>分组状态</th>
				<th>排序</th>
				<th>创建时间</th>
				<shiro:hasPermission name="sms:smsGatewayGroup:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsGatewayGroup">
			<tr>
				<td><a href="${ctx}/sms/smsGatewayGroup/form?id=${smsGatewayGroup.id}">
					${smsGatewayGroup.name}
				</a></td>
				<td>
					${smsGatewayGroup.remark}
				</td>
				<td>
					${smsGatewayGroup.status eq 1 ? '启用' : '禁用'}
				</td>				
				<td>
					${smsGatewayGroup.sort}
				</td>
				<td>
					<fmt:formatDate value="${smsGatewayGroup.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sms:smsGatewayGroup:edit"><td>
    				<a href="${ctx}/sms/smsGatewayGroup/form?id=${smsGatewayGroup.id}">修改</a>
    				<a href="javascript:windowOpen('${debug}/cache.jsp?type=2&key=${smsGatewayGroup.id}','网关分组信息',900,550);">调试</a>
					<a href="${ctx}/sms/smsGatewayGroup/updateStatus?id=${smsGatewayGroup.id}&oldStatus=${smsGatewayGroup.status}" onclick="return confirmx('确认要${smsGatewayGroup.status eq 1 ? '禁用' : '启用'}该通道分组吗？', this.href)">${smsGatewayGroup.status eq 1 ? '禁用' : '启用'}</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>