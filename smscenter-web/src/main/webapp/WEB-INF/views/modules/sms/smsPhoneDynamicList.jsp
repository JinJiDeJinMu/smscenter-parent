<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>营销黑名单管理</title>
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
		<li class="active"><a href="${ctx}/sms/smsPhoneDynamic/list">信息列表</a></li>
		<shiro:hasPermission name="sms:smsPhoneDynamic:edit"><li><a href="${ctx}/sms/smsPhoneDynamic/form">信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="smsPhoneDynamic" action="${ctx}/sms/smsPhoneDynamic/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户ID：</label>
				<form:input path="userid" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>手机号码：</label>
				<form:input path="phone" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>退订类型：</label>
				<form:select path="type" htmlEscape="false" class="input-medium">
					<form:option value="">全部</form:option>
					<form:option value="0">系统退订</form:option>
					<form:option value="1">用户退订</form:option>
				</form:select>
			</li>
			<li><label>时间：</label>
				<input name="createtimeQ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsPhoneDynamic.createtimeQ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="createtimeZ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsPhoneDynamic.createtimeZ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>手机号码</th>
				<th>退订类型</th>
				<th>备注</th>
				<th>创建时间</th>
				<shiro:hasPermission name="sms:smsPhoneDynamic:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsPhoneDynamic">
			<tr>
				<td>
					${smsPhoneDynamic.userid}
				</td>
				<td>
					${smsPhoneDynamic.phone}
				</td>
				<td>
					${smsPhoneDynamic.type eq 0 ? '系统退订' : '用户退订'}
				</td>
				<td>
					${smsPhoneDynamic.remarks}
				</td>
				<td>
					<fmt:formatDate value="${smsPhoneDynamic.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sms:smsPhoneDynamic:edit"><td>
					<a href="javascript:windowOpen('${debug}/cache.jsp?type=9&key=${smsPhoneDynamic.phone}&userid=${smsPhoneDynamic.userid}','营销黑名单',900,550);">调试</a>
					<a href="${ctx}/sms/smsPhoneDynamic/delete?userid=${smsPhoneDynamic.userid}&phone=${smsPhoneDynamic.phone}" onclick="return confirmx('确认要删除该营销黑名单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>