<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日签名报表管理</title>
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
		<li class="active"><a href="${ctx}/sms/smsDaySign/">日签名报表列表</a></li>
		<shiro:hasPermission name="sms:smsDaySign:edit"><li><a href="${ctx}/sms/smsDaySign/form">日签名报表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="smsDaySign" action="${ctx}/sms/smsDaySign/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>日期：</label>
				<input name="beginDay" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsDaySign.beginDay}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endDay" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsDaySign.endDay}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>用户ID：</label>
				<sys:treeselect id="userid" name="userid" value="${smsDaySign.userid}" labelName="" labelValue="${smsDaySign.}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>日期</th>
				<th>账户ID</th>
				<th>签名</th>
				<th>日总发送量</th>
				<th>系统处理失败量</th>
				<th>发送成功量 暂时不用置0</th>
				<th>网关成功量</th>
				<th>网关失败量</th>
				<th>状态成功量</th>
				<th>无状态返回量</th>
				<th>状态报告失败量</th>
				<th>推送成功</th>
				<th>推送失败</th>
				<th>推送未知</th>
				<shiro:hasPermission name="sms:smsDaySign:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsDaySign">
			<tr>
				<td><a href="${ctx}/sms/smsDaySign/form?id=${smsDaySign.id}">
					<fmt:formatDate value="${smsDaySign.day}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${smsDaySign.accId}
				</td>
				<td>
					${smsDaySign.sign}
				</td>
				<td>
					${smsDaySign.sendCount}
				</td>
				<td>
					${smsDaySign.failCount}
				</td>
				<td>
					${smsDaySign.successCount}
				</td>
				<td>
					${smsDaySign.submitSuccessCount}
				</td>
				<td>
					${smsDaySign.submitFailCount}
				</td>
				<td>
					${smsDaySign.reportSuccessCount}
				</td>
				<td>
					${smsDaySign.reportNullCount}
				</td>
				<td>
					${smsDaySign.reportFailCount}
				</td>
				<td>
					${smsDaySign.pushSuccessCount}
				</td>
				<td>
					${smsDaySign.pushFailCount}
				</td>
				<td>
					${smsDaySign.pushUnkownCount}
				</td>
				<shiro:hasPermission name="sms:smsDaySign:edit"><td>
    				<a href="${ctx}/sms/smsDaySign/form?id=${smsDaySign.id}">修改</a>
					<a href="${ctx}/sms/smsDaySign/delete?id=${smsDaySign.id}" onclick="return confirmx('确认要删除该日签名报表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>