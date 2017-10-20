<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>短信模板管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			showCreateDiv();
			
			
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
		
		function onChangeScope(){
			showCreateDiv()
		}
		
		function showCreateDiv(){
			var scope = $("#scope").val();
			if(scope == 1){
				$("#createDiv").show();
			}else{
				$("#createDiv").hide();
				$("#createById").val('');
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sms/smsTemplate/">信息列表</a></li>
		<li class="active"><a href="${ctx}/sms/smsTemplate/form?id=${smsTemplate.id}">信息<shiro:hasPermission name="sms:smsTemplate:edit">${not empty smsTemplate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sms:smsTemplate:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="smsTemplate" action="${ctx}/sms/smsTemplate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="status" value="1"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">模板名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="80" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" htmlEscape="false" class="input-xlarge required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sms_type')}" itemValue="value" itemLabel="label"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">范围 ：</label>
			<div class="controls">
				<form:select path="scope" htmlEscape="false" class="input-xlarge required" onchange="javascript:onChangeScope();">
					<form:option value="" label="请选择"/>
					<form:option value="0" label="全局"/>
					<form:option value="1" label="用户"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="createDiv" hidden="true">
			<label class="control-label">创建人ID ：</label>
			<div class="controls">
				<form:input path="createBy.id" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">模板内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sms:smsTemplate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>