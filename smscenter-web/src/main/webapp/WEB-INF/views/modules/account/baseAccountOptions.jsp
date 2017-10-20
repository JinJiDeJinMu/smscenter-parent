<%@ page contentType="text/html;charset=UTF-8" %>
<fieldset><legend>权限信息</legend></fieldset>
		<input type="hidden" name="noCheck" value="1" />
		<input type="hidden" name="reviewCount" value="0" />
		<input type="hidden" name="user.userType" value="1" />
		<div class="control-group">
			<label class="control-label">用户角色:</label>
			<div class="controls">
				<form:checkboxes path="user.roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<%-- <div class="control-group">
			<label class="control-label">用户分类:</label>
			<div class="controls">
				<form:select path="user.userType" class="input-xlarge required">
					<form:option value="">请选择</form:option>
					<form:options items="${fns:getDictList('user_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					<form:option value="2">其他</form:option>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">计费方式:</label>
			<div class="controls">
				<form:radiobuttons path="opt_feeType" items="${fns:getDictList('fee_type')}" itemLabel="label" itemValue="value" class="required" htmlEscape="false"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<%-- <div class="control-group">
			<label class="control-label">免审标识:</label>
			<div class="controls">
				<select name="opt_noCheck" class="input-xlarge" onchange="showReviewCount()" id="noCheck">
					<option value="0" ${options.noCheck eq 0 ? 'selected' : ''}>手动审核</option>
					<option value="2" ${options.noCheck eq 2 ? 'selected' : ''}>自动审核</option>
					<option value="1" ${options.noCheck eq 1 ? 'selected' : ''}>免审</option>
				</select>
			</div>
		</div>
		<div class="control-group" id="reviewCount">
			<label class="control-label">审核条数:</label>
			<div class="controls">
				<input name="opt_reviewCount" htmlEscape="false" maxlength="4" class="required number" value="${options.reviewCount}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">过滤敏感词:</label>
			<div class="controls">
				<select name="opt_filterWordFlag" class="input-xlarge" id="filterWordFlag">
					<option value="1" ${options.filterWordFlag eq 1 ? 'selected' : ''}>是</option>
					<option value="0" ${options.filterWordFlag eq 0 ? 'selected' : ''}>否</option>
				</select>
			</div>
		</div>			
		<!-- <div class="control-group">
			<label class="control-label">校验签名:</label>
			<div class="controls">
				<select name="opt_signFlag" class="input-xlarge" id="signFlag">
					<option value="1" ${options.signFlag eq 1 ? 'selected' : ''}>校验</option>
					<option value="0" ${options.signFlag eq 0 ? 'selected' : ''}>不校验</option>
				</select>
			</div>
		</div> -->	
		<div class="control-group">
			<label class="control-label">校验系统黑名单:</label>
			<div class="controls">
				<select name="opt_sysBlackListFlag" class="input-xlarge" id="sysBlackListFlag" >
					<option value="1" ${options.sysBlackListFlag eq 1 ? 'selected' : ''}>校验</option>
					<option value="0" ${options.sysBlackListFlag eq 0 ? 'selected' : ''}>不校验</option>
				</select>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">校验营销黑名单:</label>
			<div class="controls">
				<select name="opt_userBlackListFlag" class="input-xlarge" id="userBlackListFlag">
					<option value="1" ${options.userBlackListFlag eq 1 ? 'selected' : ''}>校验</option>
					<option value="0" ${options.userBlackListFlag eq 0 ? 'selected' : ''}>不校验</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通道分组:</label>
			<div class="controls">
				<select name="opt_groupId" class="input-xxlarge required" id="groupId" >
					<option value="">请选择</option>
					<c:forEach items="${fns:getGatewayGroupList()}" var="item">
						<option value="${item.id}" ${options.groupId eq item.id ? 'selected' : ''}>${item.name}【${item.remark}】</option>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		
		
			
		<!-- <div class="control-group">
			<label class="control-label">内容规则配置:</label>
			<div class="controls">
				<input type="text" name="opt_contentRule" htmlEscape="false" maxlength="100" class="input-xlarge" value="${options.contentRule}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">余额提醒（条数）:</label>
			<div class="controls">
				<input type="text" name="opt_balanceCaution" htmlEscape="false" maxlength="12" cssClass="input-xlarge number" value="${options.balanceCaution}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">余额提醒号码:</label>
			<div class="controls">
				<input type="text" name="opt_cautionMobile" htmlEscape="false" maxlength="50" class="input-xlarge" value="${options.cautionMobile}"/>
			</div>
		</div>-->
		
		<fieldset><legend>接口信息</legend></fieldset>
		<div class="control-group">
			<label class="control-label">账号名称:</label>
			<div class="controls">
				<input type="text" name="accName" htmlEscape="false" maxlength="100" id="accName" class="input-xlarge required" value="${baseAccount.accName}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">业务类型:</label>
			<div class="controls">
				<select name="serviceId" class="input-xlarge required" id="serviceId" >
					<option value="">请选择</option>
					<c:forEach items="${fns:getDictList('service_id')}" var="item">
						<option value="${item.value}" ${baseAccount.serviceId eq item.value ? 'selected' : ''}>${item.label}</option>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>	
		
		<div class="control-group">
			<label class="control-label">是否开通接口:</label>
			<div class="controls">
				<select name="opt_interfaceFlag" class="input-xlarge" id="interfaceFlag">
					<option value="1" ${options.interfaceFlag eq 1 ? 'selected' : ''}>开通</option>
					<option value="0" ${options.interfaceFlag eq 0 ? 'selected' : ''}>关闭</option>
				</select>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">apikey:</label>
			<div class="controls">
				<input type="text" name="opt_apikey" htmlEscape="false" maxlength="32" readonly="true" id="apikey" class="input-xlarge" value="${options.apikey}"/>
				&nbsp;<input id="refresh" class="btn" type="button" value="刷新" onclick="onRefresh();"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">绑定ip:</label>
			<div class="controls">
				<input type="text" name="opt_whiteIP" htmlEscape="false" maxlength="100" class="input-xlarge" value="${options.whiteIP}"/>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">状态报告回调地址:</label>
			<div class="controls">
				<input type="text" name="opt_callbackUrl" htmlEscape="false" maxlength="100" class="input-xlarge" value="${options.callbackUrl}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">短信上行地址:</label>
			<div class="controls">
				<input type="text" name="opt_upUrl" htmlEscape="false" maxlength="100" id="upUrl" class="input-xlarge" value="${options.upUrl}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">响应内容类型:</label>
			<div class="controls">
				<select name="opt_rspContentType" class="input-xlarge" id="rspContentType">
					<option value="1" ${options.rspContentType eq 1 ? 'selected' : ''}>JSON</option>
				</select>
			</div>
		</div>
		<!-- <div class="control-group">
			<label class="control-label">用户关键词:</label>
			<div class="controls">
				<textarea name="opt_keyword" htmlEscape="false" rows="3" class="input-xlarge" id="keyword">${options.keyword}</textarea>
				<span class="help-inline"><font color="red">多个以,分开</font> </span>
			</div>
		</div> -->
