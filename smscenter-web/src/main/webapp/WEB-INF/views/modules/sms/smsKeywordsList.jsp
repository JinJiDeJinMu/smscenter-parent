<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>敏感词管理</title>
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
		<li class="active"><a href="${ctx}/sms/smsKeywords/">信息列表</a></li>
		<shiro:hasPermission name="sms:smsKeywords:edit"><li><a href="${ctx}/sms/smsKeywords/form">信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="smsKeywords" action="${ctx}/sms/smsKeywords/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>敏感词：</label>
				<form:input path="keywords" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>敏感词</th>
				<th>创建日期</th>
				<shiro:hasPermission name="sms:smsKeywords:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsKeywords">
			<tr>
				<td>
					${smsKeywords.keywords}
				</td>
				<td>
					<fmt:formatDate value="${smsKeywords.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sms:smsKeywords:edit"><td>
					<a href="javascript:windowOpen('${debug}/cache.jsp?type=6&key=${smsKeywords.keywords}','敏感词',900,550);">调试</a>
					<a href="${ctx}/sms/smsKeywords/delete?id=${smsKeywords.id}&keywords=${smsKeywords.keywords}" onclick="return confirmx('确认要删除该敏感词吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>