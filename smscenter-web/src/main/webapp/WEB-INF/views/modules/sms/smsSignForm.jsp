<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通道签名管理</title>
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
			
			
			$('#gatewayId').change(function() 
			{
				$.ajax({
					url : "${ctx}/sms/jmsgGatewayInfo/getGatewayInfo?gatewayId="+$(this).children('option:selected').val(),
					dataType : "json",
					success : function(data) {
						$('#spNumber').val(data.spNumber);
					}
				});
			})
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sms/smsSign/">信息列表</a></li>
		<li class="active"><a href="${ctx}/sms/smsSign/form?id=${smsSign.id}">信息<shiro:hasPermission name="sms:smsSign:edit">${not empty smsSign.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sms:smsSign:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="smsSign" action="${ctx}/sms/smsSign/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">用户：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${smsSign.user.id}" labelName="user.name" labelValue="${smsSign.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" disabled="${not empty smsSign.id?'disabled':''}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道：</label>
			<div class="controls">
				<c:if test="${empty smsSign.id}">
				<form:select path="gwCode" class="input-xlarge required">
					<form:option value="" label="请选择"/>
<!--items 待实现  -->
				</form:select>
				</c:if>
				<c:if test="${not empty smsSign.id}">
				<form:hidden path="gwCode"/>
<!--通道名称待实现  -->              <form:input path="gatewayName" htmlEscape="false" value=""/>
				</c:if>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签名：</label>
			<div class="controls">
				<form:input path="sign" htmlEscape="false" maxlength="20" class="input-xlarge required" readonly="${not empty smsSign.id?'true':'false'}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接入号：</label>
			<div class="controls">
				<c:if test="${empty smsSign.id}">
				<form:input path="spNumber" htmlEscape="false" maxlength="20" class="input-xlarge required" readonly="${not empty smsSign.id?'true':'false'}"/>
				</c:if>
				<c:if test="${not empty smsSign.id}">
<!--接入号值待实现  -->				<form:input path="spNumber" htmlEscape="false" value=""/> 
				</c:if>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扩展号：</label>
			<div class="controls">
				<c:if test="${empty smsSign.id}">
				<form:input path="extNumber" htmlEscape="false" maxlength="20" class="input-xlarge" readonly="${not empty smsSign.id?'true':'false'}"/>
				</c:if>
				<c:if test="${not empty smsSign.id}">
				<form:input path="extNumber" htmlEscape="false" value="${smsSign.spNumber}" maxlength="20" class="input-xlarge" readonly="${not empty smsSign.id?'true':'false'}"/>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" class="input-xlarge" id="note"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sms:smsSign:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>