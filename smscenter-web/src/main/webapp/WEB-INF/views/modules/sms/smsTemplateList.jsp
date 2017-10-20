<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>短信模板管理</title>
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
		<li class="active"><a href="${ctx}/sms/smsTemplate/list">信息列表</a></li>
		<shiro:hasPermission name="sms:smsTemplate:edit"><li><a href="${ctx}/sms/smsTemplate/form">信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="smsTemplate" action="${ctx}/sms/smsTemplate/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>创建人：</label>
				<form:input path="createBy.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>模板名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="80" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createtimeQ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsTemplate.createtimeQ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="createtimeZ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsTemplate.createtimeZ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>模板ID</th>
				<th>模板名称</th>
				<th>模板内容</th>
				<th>类型</th>
				<th>状态</th>
				<th>范围</th>
				<th>创建人ID</th>
				<th>创建时间</th>
				<shiro:hasPermission name="sms:smsTemplate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsTemplate">
			<tr>
				<td>${smsTemplate.id}</td>
				<td><a href="${ctx}/sms/smsTemplate/form?id=${smsTemplate.id}">
					${smsTemplate.name}
				</a></td>
				<td>
					${smsTemplate.content}
				</td>
				<td>
					${fns:getDictLabel(smsTemplate.type, 'sms_type', smsTemplate.type)}
				</td>
				<td>
					${smsTemplate.status eq 0 ? '禁用' : smsTemplate.status  eq -2 ? '审核不通过' : smsTemplate.status eq -1 ? '待审' : '启用'}
				</td>
				<td>
					${smsTemplate.scope eq 0 ? '全局' : '用户'}
				</td>
				<td>
					${smsTemplate.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${smsTemplate.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sms:smsTemplate:edit"><td>
    				<a href="${ctx}/sms/smsTemplate/form?id=${smsTemplate.id}">修改</a>
    				<a href="javascript:windowOpen('${debug}/cache.jsp?type=5&key=${smsTemplate.id}','短信模板',900,550);">调试</a>
					<a href="${ctx}/sms/smsTemplate/delete?id=${smsTemplate.id}" onclick="return confirmx('确认要删除该短信模板吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>