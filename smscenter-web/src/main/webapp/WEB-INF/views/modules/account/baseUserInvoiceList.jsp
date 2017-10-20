<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户发票管理</title>
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
		<li class="active"><a href="${ctx}/account/baseUserInvoice/">信息列表</a></li>
		<!--<shiro:hasPermission name="account:baseUserInvoice:edit"><li><a href="${ctx}/account/baseUserInvoice/form">用户发票添加</a></li></shiro:hasPermission>-->
	</ul>
	<form:form id="searchForm" modelAttribute="baseUserInvoice" action="${ctx}/account/baseUserInvoice/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户ID：</label>
				<form:input path="userid" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>用户姓名：</label>
				<form:input path="user.name" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户ID</th>
				<th>用户姓名</th>
				<th>可开票金额</th>
				<th>累计开票金额</th>
				<th>冻结开票金额</th>
				<!--<shiro:hasPermission name="account:baseUserInvoice:edit"><th>操作</th></shiro:hasPermission>-->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="baseUserInvoice">
			<tr>
				<td>
					${baseUserInvoice.userid}
				</td>
				<td>
					${baseUserInvoice.user.name}
				</td>
				<td>
					<fmt:formatNumber type="number" value="${baseUserInvoice.amount/100}" pattern="0.00" maxFractionDigits="2"/>
				</td>
				<td>
					<fmt:formatNumber type="number" value="${baseUserInvoice.totalAmount/100}" pattern="0.00" maxFractionDigits="2"/>
				</td>
				<td>
					<fmt:formatNumber type="number" value="${baseUserInvoice.freezeAmount/100}" pattern="0.00" maxFractionDigits="2"/>
				</td>
				<!-- 
				<shiro:hasPermission name="account:baseUserInvoice:edit"><td>
    				<a href="${ctx}/account/baseUserInvoice/form?id=${baseUserInvoice.id}">修改</a>
					<a href="${ctx}/account/baseUserInvoice/delete?id=${baseUserInvoice.id}" onclick="return confirmx('确认要删除该用户发票吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
				 -->
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>