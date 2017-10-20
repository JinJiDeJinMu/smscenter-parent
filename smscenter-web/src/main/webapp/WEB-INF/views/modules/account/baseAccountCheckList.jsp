<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户信息管理</title>
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
		
		function showRecharge(userid){
			$('#userid').select2('val', userid);
			$("#rechargeModal").modal("show");
		}
		
		//充值
		function onRecharge(){
			var userid = $("#userid").val();
			var changeType = $("#changeType").val();
			var accAmount = $("#accAmount").val();
			var remark = $("#remark").val();
			if(changeType == null || changeType==''){
				alertx("请选择充值方式");
				return;
			}
			
			if(!digits(accAmount)){
				alertx("请输入正确数字");
				return;
			}
			
			remark = encodeURI(encodeURI(remark));
			
			$.ajax({
				type: 'POST',
				url: "${ctx}/account/baseAccount/ajaxRecharge?accAmount="+accAmount+"&changeType="+changeType+"&userid="+userid+"&accType=sms&remark="+remark,
				success: function(result){
					alertx(result);
					page();
				},
				error: function(){
					alertx("系统错误");
				}
			});
			
			
		}
		
		function digits(nubmer){
			var re =  /^[1-9]+[0-9]*]*$/; //判断正整数 /^[1-9]+[0-9]*]*$/ 
			if(re.test(nubmer))return true;
			return false;
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/account/baseAccount/checkList">信息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="baseAccount" action="${ctx}/account/baseAccount/checkList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>机构：</label><sys:treeselect id="company" name="company.id" value="${baseAccount.company.id}" labelName="company.name" labelValue="${baseAccount.company.name}" 
				title="机构" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li>			
			<li><label>登录名：</label><form:input path="user.loginName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>用户名称：</label><form:input path="user.name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>账户ID</th>
				<th>账户名称</th>
				<th>账户状态</th>
				<th>账户类型</th>
				<th>用户ID</th>
				<th>用户名称</th>
				<th>登录名</th>
				<th>组织机构</th>
				<th>创建时间</th>
				<shiro:hasPermission name="account:baseAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="baseAccount">
			<tr>
				<td>
					${baseAccount.id}
				</td> 
				<td>
					${baseAccount.accName}
				</td>
				<td>
					${fns:getDictLabel(baseAccount.accStatus, 'account_status', baseAccount.accStatus)}
				</td>
				<td>
					${fns:getDictLabel(baseAccount.accType, 'account_type', baseAccount.accType)}
				</td>
				<td>
					${baseAccount.userid}
				</td>
				<td>
					${fns:getUserById(baseAccount.userid).name}
				</td>
				<td>
					${fns:getUserById(baseAccount.userid).loginName}
				</td>
				<td>
					${baseAccount.company.name}
				</td>
				<td>
					<fmt:formatDate value="${baseAccount.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="account:baseAccount:edit"><td>
					<a href="${ctx}/account/baseAccount/updateAccStatus?id=${baseAccount.id}&oldAccStatus=${baseAccount.accStatus}&userid=${baseAccount.userid}&type=check" onclick="return confirmx('确认要${baseAccount.accStatus eq 1 ? '下线':'上线' }该账户吗？', this.href)">${baseAccount.accStatus eq 1 ? '下线':'上线' }</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>