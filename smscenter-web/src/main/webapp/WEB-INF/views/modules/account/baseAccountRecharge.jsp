<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资金账户充值</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/account/baseAccount/rechargeInit?accType=${baseAccount.accType}">
		 ${baseAccount.accType eq 'sms'?'短信':baseAccount.accType eq 'mms'?'彩信':'流量'}账户充值</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="baseAccount" action="${ctx}/account/baseAccount/recharge" method="post" class="form-horizontal">
		<form:hidden path="accType"/>
		<input type="hidden" name="serviceId" value="10">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">用户：</label>
			<div class="controls">
				<form:select path="userid" class="input-xlarge required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getUserList('')}" itemLabel="label" itemValue="value"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">充值方式：</label>
			<div class="controls">
				<form:select path="changeType" class="input-xlarge required">
					<form:option value="" label="请选择"/>
					<form:option value="CZ00" label="充值转入"/>
					<form:option value="CZ01" label="手动返充"/>
					<form:option value="XF01" label="手动扣款"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">充值额度：</label>
			<div class="controls">
				<form:input path="accAmount" htmlEscape="false" maxlength="8" class="input-xlarge required digits" id="accAmount"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" maxlength="250" class="input-xlarge "/>
			</div>
		</div>		
		<div class="form-actions">
			<shiro:hasPermission name="account:baseAccount:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>