<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账号信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			showReviewCount();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/account/baseAccount/checkLoginName?oldLoginName="+encodeURIComponent('${baseAccount.user.loginName}')}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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
		
		function onRefresh(){
			$("#apikey").val(guid());
		}
		
		function guid() {
		    return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
		        return v.toString(16);
		    });
		}
		function showReviewCount(){
			var noCheck = $("#noCheck").val();
			if('2' == noCheck){
				$("#reviewCount").show();
			}else{
				$("#reviewCount").hide();
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/account/baseAccount/lowerList">信息列表</a></li>
		<li class="active"><a href="${ctx}/account/baseAccount/lowerForm?id=${baseAccount.id}">信息<shiro:hasPermission name="account:baseAccount:edit">${not empty baseAccount.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="account:baseAccount:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="baseAccount" action="${ctx}/account/baseAccount/lowerSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="user.id"/>
		<input type="hidden" name="accType" value="sms">
		<input type="hidden" name="serviceId" value="10">
		<input type="hidden" name="accName" value="短信">
		<input type="hidden" name="user.userType" value="1">
		<sys:message content="${message}"/>
		<fieldset><legend>基本信息</legend></fieldset>
		<div class="control-group">
			<label class="control-label">归属机构:</label>
			<div class="controls">
                <sys:treeselect id="company" name="company.id" value="${baseAccount.company.id}" labelName="company.name" labelValue="${baseAccount.company.name}"
					title="机构" url="/sys/office/treeData?type=1" cssClass="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登录账号:</label>
			<div class="controls">
				<input id="oldLoginName" name="oldLoginName" type="hidden" value="${baseAccount.user.loginName}">
				<input type="text" name="loginName" htmlEscape="false" maxlength="50" class="required userName" ${not empty baseAccount.user.id && baseAccount.user.id !='' ?'readonly="readonly"':''} value="${baseAccount.user.loginName}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户名称:</label>
			<div class="controls">
				<form:input path="user.name" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="20" minlength="3" class="${empty baseAccount.user.id?'required':''}"/>
				<c:if test="${empty baseAccount.user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
				<c:if test="${not empty baseAccount.user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="20" minlength="3" equalTo="#newPassword"/>
				<c:if test="${empty baseAccount.user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司名称:</label>
			<div class="controls">
				<form:input path="user.companyName" htmlEscape="false" maxlength="40"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系手机:</label>
			<div class="controls">
				<form:input path="user.mobile" htmlEscape="false" maxlength="15"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮箱:</label>
			<div class="controls">
				<form:input path="user.email" htmlEscape="false" maxlength="100" class="email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">QQ:</label>
			<div class="controls">
				<form:input path="user.qq" htmlEscape="false" maxlength="20"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人:</label>
			<div class="controls">
				<form:input path="user.contactName" htmlEscape="false" maxlength="20"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">基本信息备注:</label>
			<div class="controls">
				<form:textarea path="user.remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		
		<fieldset><legend>权限信息</legend></fieldset>
		<div class="control-group">
			<label class="control-label">用户角色:</label>
			<div class="controls">
				<form:checkboxes path="user.roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="account:baseAccount:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>