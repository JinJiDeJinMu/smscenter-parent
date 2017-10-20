<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>号段管理</title>
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
		<li class="active"><a href="${ctx}/sms/smsPhoneInfo/">信息列表</a></li>
		<shiro:hasPermission name="sms:smsPhoneInfo:edit"><li><a href="${ctx}/sms/smsPhoneInfo/form">信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="smsPhoneInfo" action="${ctx}/sms/smsPhoneInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>号段：</label>
				<form:input path="phone" htmlEscape="false" maxlength="7" class="input-medium"/>
			</li>
			<li><label>运营商：</label>
				<form:select path="phoneType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('phone_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>号段</th>
				<th>运营商</th>
				<th>归属省</th>
				<th>归属市</th>
				<th>省市代码</th>
				<th>邮编</th>
				<th>操作</th>
				<!--<shiro:hasPermission name="sms:smsPhoneInfo:edit"><th>操作</th></shiro:hasPermission>-->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsPhoneInfo">
			<tr>
				<td><a href="${ctx}/sms/smsPhoneInfo/form?id=${smsPhoneInfo.phone}">
					${smsPhoneInfo.phone}
				</a></td>
				<td>
					${fns:getDictLabel(smsPhoneInfo.phoneType, 'phone_type', '')}
				</td>
				<td>
					${smsPhoneInfo.phoneProv}
				</td>
				<td>
					${smsPhoneInfo.phoneCity}
				</td>
				<td>
					${smsPhoneInfo.phoneCityCode}
				</td>
				<td>
					${smsPhoneInfo.zip}
				</td>
				<shiro:hasPermission name="sms:smsPhoneInfo:edit"><td>
					<a href="javascript:windowOpen('${debug}/cache.jsp?type=7&key=${smsPhoneInfo.phone}','号段属性',900,550);">调试</a>
    				<!-- <a href="${ctx}/sms/smsPhoneInfo/form?id=${smsPhoneInfo.id}">修改</a>
					<a href="${ctx}/sms/smsPhoneInfo/delete?id=${smsPhoneInfo.id}" onclick="return confirmx('确认要删除该号段吗？', this.href)">删除</a> -->
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>