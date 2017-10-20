<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>上行短信记录管理</title>
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
		<li class="active"><a href="${ctx}/sms/smsReceive/list">信息列表</a></li>
		<!--<shiro:hasPermission name="sms:smsReceive:edit"><li><a href="${ctx}/sms/smsReceive/form">上行短信记录添加</a></li></shiro:hasPermission>-->
	</ul>
	<form:form id="searchForm" modelAttribute="smsReceive" action="${ctx}/sms/smsReceive/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>接入号：</label>
				<form:input path="spnumber" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>上行手机号：</label>
				<form:input path="phone" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>短信内容：</label>
				<form:input path="smsContent" htmlEscape="false" maxlength="2000" class="input-medium"/>
			</li>
			<li><label>时间：</label>
				<input name="createtimeQ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsReceive.createtimeQ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="createtimeZ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsReceive.createtimeZ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>接入号</th>
				<th>上行手机号</th>
				<th>短信内容</th>
				<th>创建时间</th>
				<!--<shiro:hasPermission name="sms:smsReceive:edit"><th>操作</th></shiro:hasPermission>-->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsReceive">
			<tr>
				<td>
					${smsReceive.spnumber}
				</td>
				<td>
					${smsReceive.phone}
				</td>
				<td>
					${smsReceive.smsContent}
				</td>
				<td>
					<fmt:formatDate value="${smsReceive.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<!--<shiro:hasPermission name="sms:smsReceive:edit"><td>
    				<a href="${ctx}/sms/smsReceive/form?id=${smsReceive.id}">修改</a>
					<a href="${ctx}/sms/smsReceive/delete?id=${smsReceive.id}" onclick="return confirmx('确认要删除该上行短信记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>-->
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>