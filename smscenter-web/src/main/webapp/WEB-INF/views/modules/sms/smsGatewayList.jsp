<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通道信息管理</title>
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
		<li class="active"><a href="${ctx}/sms/smsGateway/">信息列表</a></li>
		<shiro:hasPermission name="sms:smsGateway:edit"><li><a href="${ctx}/sms/smsGateway/form">信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="smsGateway" action="${ctx}/sms/smsGateway/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>网关名称：</label>
				<form:input path="gwName" htmlEscape="false" maxlength="80" class="input-medium"/>
			</li>
			<li><label>网关状态：</label>
				<form:select path="gwStatus" htmlEscape="false" class="input-medium ">
					<form:option value="">全部</form:option>
					<form:option value="1">运行中</form:option>
					<form:option value="0">停止</form:option>
					<form:option value="2">禁用</form:option>
				</form:select>
			</li>
			<li><label>网关协议：</label>
				<form:select path="gwProto" htmlEscape="false" class="input-medium ">
					<form:option value="">全部</form:option>
					<form:option value="CMPP">移动CMPP</form:option>
					<form:option value="SGIP">联通SGIP</form:option>
					<form:option value="SMGP">电信SMGP</form:option>
					<form:option value="HTTP">HTTP</form:option>
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
				<th>网关编号</th>
				<th>网关名称</th>
				<th>网关状态</th>
				<th>网关协议</th>
				<th>协议版本</th>
				<th>网关用户名</th>
				<th>网关接入号</th>
				<th>备注</th>
				<shiro:hasPermission name="sms:smsGateway:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsGateway">
			<tr>
				<td><a href="${ctx}/sms/smsGateway/form?id=${smsGateway.id}">
					${smsGateway.gwCode}
				</a></td>
				<td>
					${smsGateway.gwName}
				</td>
				<td>
					${smsGateway.gwStatus eq 0 ? '停止' : smsGateway.gwStatus eq 1 ? '运行中' : '禁用'}
				</td>
				<td>
					${smsGateway.gwProto}
				</td>
				<td>
					${smsGateway.gwProtoVersion}
				</td>
				<td>
					${smsGateway.gwUsername}
				</td>
				<td>
					${smsGateway.gwSpNumber}
				</td>
				<td>
					${smsGateway.gwRemark}
				</td>
				<shiro:hasPermission name="sms:smsGateway:edit"><td>
    				<a href="${ctx}/sms/smsGateway/form?id=${smsGateway.id}">修改</a>
    				<a href="javascript:windowOpen('${debug}/cache.jsp?type=1&key=${smsGateway.gwCode}','网关信息',900,550);">调试</a>
					<!-- <a href="${ctx}/sms/smsGateway/delete?id=${smsGateway.id}" onclick="return confirmx('确认要删除该通道信息吗？', this.href)">删除</a> -->
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>