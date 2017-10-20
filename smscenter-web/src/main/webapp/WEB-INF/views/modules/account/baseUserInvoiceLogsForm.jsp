<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户发票明细管理</title>
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
		<li><a href="${ctx}/account/baseUserInvoiceLogs/${baseUserInvoiceLogs.queryType eq 'history' ? 'hisList' : 'list'}">信息列表</a></li>
		<li class="active"><a href="${ctx}/account/baseUserInvoiceLogs/form?id=${baseUserInvoiceLogs.id}&queryType=${baseUserInvoiceLogs.queryType}">信息查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="baseUserInvoiceLogs" action="${ctx}/account/baseUserInvoiceLogs/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div id="expressInfo" ${baseUserInvoiceLogs.queryType eq 'history' ? '' : 'hidden'}>
		<div class="control-group">
			<label class="control-label">发票号：</label>
			<div class="controls">
				<form:input path="invoiceNumber" htmlEscape="false" maxlength="40" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">快递公司：</label>
			<div class="controls">
				<form:input path="expressCompany" htmlEscape="false" maxlength="80" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">快递单号：</label>
			<div class="controls">
				<form:input path="expressNumber" htmlEscape="false" maxlength="40" class="input-xlarge " readonly="true"/>
			</div>
		</div>		
		</div>
		
		<div class="control-group">
			<label class="control-label">开具发票金额：</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" maxlength="20" class="input-xlarge required digits" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发票抬头信息：</label>
			<div class="controls">
				<form:input path="headerInfo" htmlEscape="false" maxlength="200" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发票类型：</label>
			<div class="controls">
				<form:select path="invoiceType" class="input-xlarge required" disabled="true">
					<form:options items="${fns:getDictList('invoice_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发票内容：</label>
			<div class="controls">
				<form:select path="invoiceContent" class="input-xlarge required" disabled="true">
					<form:options items="${fns:getDictList('invoice_content')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">纳税人识别号：</label>
			<div class="controls">
				<form:input path="nsrsbh" htmlEscape="false" maxlength="40" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行账号：</label>
			<div class="controls">
				<form:input path="bankNumber" htmlEscape="false" maxlength="40" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户行：</label>
			<div class="controls">
				<form:input path="depositBank" htmlEscape="false" maxlength="200" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册地址：</label>
			<div class="controls">
				<form:textarea path="regAddress" htmlEscape="false" maxlength="200" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="20" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收取地址：</label>
			<div class="controls">
				<form:textarea path="collectAddress" htmlEscape="false" maxlength="200" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收件人：</label>
			<div class="controls">
				<form:input path="addressee" htmlEscape="false" maxlength="20" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" maxlength="200" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>