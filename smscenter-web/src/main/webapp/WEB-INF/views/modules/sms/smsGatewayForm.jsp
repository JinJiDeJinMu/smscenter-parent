<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通道信息管理</title>
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
		<li><a href="${ctx}/sms/smsGateway/">信息列表</a></li>
		<li class="active"><a href="${ctx}/sms/smsGateway/form?id=${smsGateway.id}">信息<shiro:hasPermission name="sms:smsGateway:edit">${not empty smsGateway.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sms:smsGateway:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="smsGateway" action="${ctx}/sms/smsGateway/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">网关编号：</label>
			<div class="controls">
				<form:input path="gwCode" htmlEscape="false" maxlength="10" class="input-xlarge required" readonly="${not empty smsGateway.id && smsGateway.id != null ? 'true' : 'false' }"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应用编号：</label>
			<div class="controls">
				<form:input path="appCode" htmlEscape="false" maxlength="45" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网关名称：</label>
			<div class="controls">
				<form:input path="gwName" htmlEscape="false" maxlength="80" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网关状态：</label>
			<div class="controls">
				<form:select path="gwStatus" htmlEscape="false" maxlength="11" class="input-xlarge required">
					<form:option value="1">运行中</form:option>
					<form:option value="0">停止</form:option>
					<form:option value="2">禁止</form:option>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网关类型：</label>
			<div class="controls">
				<form:select path="gwType" htmlEscape="false" class="input-xlarge required">
					<form:option value="">请选择</form:option>
					<form:option value="QW">全网</form:option>
					<form:option value="YD">移动</form:option>
					<form:option value="LT">联通</form:option>
					<form:option value="DX">电信</form:option>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网关协议：</label>
			<div class="controls">
				<form:select path="gwProto" htmlEscape="false" class="input-xlarge required">
					<form:option value="">请选择</form:option>
					<form:option value="CMPP">移动CMPP</form:option>
					<form:option value="SGIP">联通SGIP</form:option>
					<form:option value="SMGP">电信SMGP</form:option>
					<form:option value="HTTP">HTTP</form:option>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">协议版本(30,20)：</label>
			<div class="controls">
				<form:input path="gwProtoVersion" htmlEscape="false" maxlength="45" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">协议实现类：</label>
			<div class="controls">
				<form:input path="gwProtoClass" htmlEscape="false" maxlength="100" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">协议扩展参数：</label>
			<div class="controls">
				<form:textarea path="gwProtoExtparam" htmlEscape="false" maxlength="800" class="input-xxlarge" rows="3" cols="3"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务器IP：</label>
			<div class="controls">
				<form:input path="gwServerIp" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务器端口：</label>
			<div class="controls">
				<form:input path="gwServerPort" htmlEscape="false" maxlength="11" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本地IP：</label>
			<div class="controls">
				<form:input path="gwLocalIp" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本地端口：</label>
			<div class="controls">
				<form:input path="gwLocalPort" htmlEscape="false" maxlength="11" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网关用户名：</label>
			<div class="controls">
				<form:input path="gwUsername" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网关密码：</label>
			<div class="controls">
				<form:input path="gwPassword" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网关接入号：</label>
			<div class="controls">
				<form:input path="gwSpNumber" htmlEscape="false" maxlength="20" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网关企业代码：</label>
			<div class="controls">
				<form:input path="gwCorpId" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网关服务代码：</label>
			<div class="controls">
				<form:input path="gwServiceId" htmlEscape="false" maxlength="45" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态报告获取模式：</label>
			<div class="controls">
				<form:select path="gwReceiveModel" htmlEscape="false" class="input-xlarge required">
					<form:option value="1">主动查询</form:option>
					<form:option value="0">异步通知</form:option>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">短信签名模式：</label>
			<div class="controls">
				<form:select path="smsSignModel" htmlEscape="false" class="input-xlarge required">
					<form:option value="0">自定义</form:option>
					<form:option value="1">强制签名</form:option>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">短信签名端：</label>
			<div class="controls">
				<form:select path="smsSignNose" htmlEscape="false" class="input-xlarge required">
					<form:option value="0">本地</form:option>
					<form:option value="1">网关</form:option>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支持长短信：</label>
			<div class="controls">
				<form:select path="gwLongsms" htmlEscape="false" class="input-xlarge required">
					<form:option value="0">支持</form:option>
					<form:option value="1">不支持</form:option>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">一次提交数量：</label>
			<div class="controls">
				<form:input path="gwOnceSubmit" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发送速率：</label>
			<div class="controls">
				<form:input path="gwSendRate" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接收速率：</label>
			<div class="controls">
				<form:input path="gwReceiveRate" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网关连接数：</label>
			<div class="controls">
				<form:input path="gwConnections" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="gwRemark" htmlEscape="false" maxlength="120" class="input-xlarge " rows="4" cols="3"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新备注：</label>
			<div class="controls">
				<form:textarea path="modifyRemark" htmlEscape="false" maxlength="120" class="input-xlarge " rows="4" cols="3"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sms:smsGateway:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>