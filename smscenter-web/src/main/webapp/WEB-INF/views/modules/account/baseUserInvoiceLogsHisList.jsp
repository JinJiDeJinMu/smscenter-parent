<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>索取发票历史记录</title>
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
		<li class="active"><a href="${ctx}/account/baseUserInvoiceLogs/hisList">信息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="baseUserInvoiceLogs" action="${ctx}/account/baseUserInvoiceLogs/hisList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户ID：</label>
				<form:input path="userid" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>用户姓名：</label>
				<form:input path="user.name" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>发票状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="1" label="已处理"/>
					<form:option value="0" label="未开票"/>
				</form:select>
			</li>
			<li><label>发票类型：</label>
				<form:select path="invoiceType" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('invoice_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>发票内容：</label>
				<form:select path="invoiceContent" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('invoice_content')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>申请时间：</label>
				<input name="createTimeQ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${baseUserInvoiceLogs.createTimeQ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="createTimeZ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${baseUserInvoiceLogs.createTimeZ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>用户ID</th>
				<th>用户姓名</th>
				<th>开具发票金额</th>
				<th>发票抬头信息</th>
				<th>发票类型</th>
				<th>发票内容</th>
				<th>发票状态</th>
				<th>申请开票时间</th>
				<shiro:hasPermission name="account:baseUserInvoiceLogs:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="baseUserInvoiceLogs">
			<tr>
				<td><a href="${ctx}/account/baseUserInvoiceLogs/form?id=${baseUserInvoiceLogs.id}">
					${baseUserInvoiceLogs.userid}
				</a></td>
				<td>
					${baseUserInvoiceLogs.user.name}
				</td>
				<td>
					<fmt:formatNumber type="number" value="${baseUserInvoiceLogs.amount/100}" pattern="0.00" maxFractionDigits="2"/>
				</td>
				<td>
					${baseUserInvoiceLogs.headerInfo}
				</td>
				<td>
					${fns:getDictLabel(baseUserInvoiceLogs.invoiceType, 'invoice_type', baseUserInvoiceLogs.invoiceType)}
				</td>
				<td>
					${fns:getDictLabel(baseUserInvoiceLogs.invoiceContent, 'invoice_content', baseUserInvoiceLogs.invoiceContent)}
				</td>
				<td>
					${fns:getDictLabel(baseUserInvoiceLogs.status, 'invoice_status', baseUserInvoiceLogs.status)}
				</td>
				<td>
					<fmt:formatDate value="${baseUserInvoiceLogs.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="account:baseUserInvoiceLogs:edit"><td>
    				<a href="${ctx}/account/baseUserInvoiceLogs/form?id=${baseUserInvoiceLogs.id}&queryType=history">查看</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>