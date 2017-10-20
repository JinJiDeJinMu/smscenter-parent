<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账号属性管理</title>
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
		<li class="active"><a href="${ctx}/account/baseAccountOptions/">账号属性列表</a></li>
		<shiro:hasPermission name="account:baseAccountOptions:edit"><li><a href="${ctx}/account/baseAccountOptions/form">账号属性添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="baseAccountOptions" action="${ctx}/account/baseAccountOptions/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>userid：</label>
				<sys:treeselect id="userid" name="userid" value="${baseAccountOptions.userid}" labelName="" labelValue="${baseAccountOptions.}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>acc_id：</label>
				<form:select path="accId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>userid</th>
				<th>acc_id</th>
				<th>option_key</th>
				<th>option_value</th>
				<shiro:hasPermission name="account:baseAccountOptions:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="baseAccountOptions">
			<tr>
				<td><a href="${ctx}/account/baseAccountOptions/form?id=${baseAccountOptions.id}">
					${baseAccountOptions.}
				</a></td>
				<td>
					${fns:getDictLabel(baseAccountOptions.accId, '', '')}
				</td>
				<td>
					${baseAccountOptions.optionKey}
				</td>
				<td>
					${baseAccountOptions.optionValue}
				</td>
				<shiro:hasPermission name="account:baseAccountOptions:edit"><td>
    				<a href="${ctx}/account/baseAccountOptions/form?id=${baseAccountOptions.id}">修改</a>
					<a href="${ctx}/account/baseAccountOptions/delete?id=${baseAccountOptions.id}" onclick="return confirmx('确认要删除该账号属性吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>