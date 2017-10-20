<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通道分组关系管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/docheck/docheck.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function onDelete(){
			var ids = getCheckboxValue("id");
			
			if(!ids){
				alertx("请选择要删除的数据");
			}else{
				
				top.$.jBox.confirm("确认要删除该数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sms/smsGatewayGroupRel/batchDelete?ids="+ids);
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			}
		}
		function onRefresh(){
			$("#searchForm").attr("action","${ctx}/sms/smsGatewayGroupRel/refreshCache");
			$("#searchForm").submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sms/smsGatewayGroupRel/">信息列表</a></li>
		<shiro:hasPermission name="sms:smsGatewayGroupRel:edit"><li><a href="${ctx}/sms/smsGatewayGroupRel/form">信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="smsGatewayGroupRel" action="${ctx}/sms/smsGatewayGroupRel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>分组名称：</label>
				<form:select path="groupId" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getGatewayGroupList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>运营商：</label>
				<form:select path="phoneType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('phone_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>省份：</label>
				<form:select path="provinceId" class="input-medium ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('phone_province')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>网关名称：</label>
				<form:select path="gwCode" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getGatewayList()}" itemLabel="gwName" itemValue="gwCode" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<input id="btnDelete" class="btn btn-primary" type="button" value="批量删除" onclick="javascript:onDelete()"/> 
			<input id="btnRef" class="btn btn-primary" type="button" value="刷新缓存" onclick="javascript:onRefresh()"/> 
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input type="checkbox" onclick="doCheckAll(this,'id', this.checked)"></th>
				<th>分组名称</th>
				<th>运营商</th>
				<th>省份</th>
				<th>网关名称</th>
				<th>等级</th>
				<th>创建时间</th>
				<shiro:hasPermission name="sms:smsGatewayGroupRel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsGatewayGroupRel">
			<tr onclick="selectTr(this, '${smsGatewayGroupRel.id}' ,'id')">
				<td><input type="checkbox" name="id" id="${smsGatewayGroupRel.id}" value="${smsGatewayGroupRel.id}_${smsGatewayGroupRel.groupId}" 
				onclick="if(this.checked){this.checked=false;}else{this.checked=true;}"></td>
				<td>
					${smsGatewayGroupRel.groupName}
				</td>
				<td>
					${fns:getDictLabel(smsGatewayGroupRel.phoneType,'phone_type',smsGatewayGroupRel.phoneType)}
				</td>
				<td>
					${fns:getDictLabel(smsGatewayGroupRel.provinceId,'phone_province',smsGatewayGroupRel.provinceId)}
				</td>
				<td>
					${smsGatewayGroupRel.gwName}
				</td>
				<td>
					${smsGatewayGroupRel.level}
				</td>
				<td>
					<fmt:formatDate value="${smsGatewayGroupRel.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sms:smsGatewayGroupRel:edit"><td>
    				<!-- <a href="${ctx}/sms/smsGatewayGroupRel/form?id=${smsGatewayGroupRel.id}">修改</a> -->
    				<a href="javascript:windowOpen('${debug}/cache.jsp?type=3&key=${smsGatewayGroupRel.groupId}&provinceId=${smsGatewayGroupRel.provinceId}&phoneType=${smsGatewayGroupRel.phoneType}','网关分组关系信息',900,550);">调试</a>
					<a href="${ctx}/sms/smsGatewayGroupRel/delete?id=${smsGatewayGroupRel.id}&groupId=${smsGatewayGroupRel.groupId}" onclick="return confirmx('确认要删除该通道分组关系吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>