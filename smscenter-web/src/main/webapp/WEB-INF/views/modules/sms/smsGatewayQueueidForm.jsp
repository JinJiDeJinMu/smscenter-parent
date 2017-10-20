<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>网关队列管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
		<li><a href="${ctx}/sms/smsGatewayQueueid/">信息列表</a></li>
		<li class="active"><a href="${ctx}/sms/smsGatewayQueueid/form?id=${smsGatewayQueueid.id}">信息<shiro:hasPermission name="sms:smsGatewayQueueid:edit">${not empty smsGatewayQueueid.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sms:smsGatewayQueueid:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="smsGatewayQueueid" action="${ctx}/sms/smsGatewayQueueid/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">网关编号：</label>
			<div class="controls">
				<form:select path="gwCode" class="input-xlarge required" disabled="${not empty smsGatewayQueueid.id && smsGatewayQueueid.id !='' ?  'true' : '' }">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getGatewayList()}" itemLabel="gwName" itemValue="gwCode" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务模型：</label>
			<div class="controls">
				<form:select path="serviceid" htmlEscape="false" class="input-xlarge required" >
					<form:option value="0">默认</form:option>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">队列组：</label>
			<div class="controls">
				<form:input path="topic" htmlEscape="false" maxlength="45" class="input-xlarge required"  readonly="${not empty smsGatewayQueueid.id && smsGatewayQueueid.id !='' ?  'true' : '' }"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">队列编号：</label>
			<div class="controls">
				<form:input path="queueId" htmlEscape="false" maxlength="11" class="input-xlarge required" readonly="${not empty smsGatewayQueueid.id && smsGatewayQueueid.id !='' ?  'true' : '' }"/>
				<span class="help-inline"><font color="red">*请输入大于0的数字</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">队列名称：</label>
			<div class="controls">
				<form:input path="queueName" htmlEscape="false" maxlength="45" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">队列状态：</label>
			<div class="controls">
				<form:input path="queueStatus" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">队列执行线程数：</label>
			<div class="controls">
				<form:input path="threadNum" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">位置：</label>
			<div class="controls">
				<form:input path="offset" htmlEscape="false" maxlength="20" class="input-xlarge  digits" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">当前速率：</label>
			<div class="controls">
				<form:input path="tps" htmlEscape="false" maxlength="11" class="input-xlarge digits" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权重：</label>
			<div class="controls">
				<form:input path="weight" htmlEscape="false" maxlength="11" class="input-xlarge digits"/>
				<span class="help-inline"><font color="red">请输入小于10的数字</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" maxlength="255" class="input-xlarge " rows="3" cols="3"/>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="sms:smsGatewayQueueid:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>