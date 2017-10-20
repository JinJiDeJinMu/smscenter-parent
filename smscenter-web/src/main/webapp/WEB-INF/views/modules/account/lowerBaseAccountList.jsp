<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>下级用户管理</title>
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
		<li class="active"><a href="${ctx}/account/baseAccount/lowerList">信息列表</a></li>
		<shiro:hasPermission name="account:baseAccount:edit"><li><a href="${ctx}/account/baseAccount/lowerForm">信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="baseAccount" action="${ctx}/account/baseAccount/lowerList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>登录名：</label><form:input path="user.loginName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>用户名称：</label><form:input path="user.name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>账户状态：</label>
				<form:select path="accStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="1" label="启用"/>
					<form:option value="0" label="禁用"/>
				</form:select>
			</li>
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
				<th>用户ID</th>
				<th>账户状态</th>
				<th>账户类型</th>
				<th>账户额度</th>
				<th>账户余额</th>
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
					${baseAccount.userid}
				</td>
				<td>
					<c:if test="${baseAccount.accStatus eq 0}">
						<font color="red">下线</font>
					</c:if>
					<c:if test="${baseAccount.accStatus ne 0}">
						${fns:getDictLabel(baseAccount.accStatus, 'account_status', baseAccount.accStatus)}
					</c:if>	
				</td>
				<td>
					${fns:getDictLabel(baseAccount.accType, 'account_type', baseAccount.accType)}
				</td>
				<td>
					${fns:getDBAmount(baseAccount.userid)}
				</td>
				<td>
					${fns:getAmount(baseAccount.userid)}
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
					<a href="javascript:showRecharge(${baseAccount.userid});">充值</a>
    				<a href="${ctx}/account/baseAccount/lowerForm?id=${baseAccount.id}">修改</a>
    				<shiro:hasPermission name="sms:public:debug">
    				<a href="javascript:windowOpen('${debug}/cache.jsp?type=4&key=${baseAccount.userid}','用户账号信息',900,550);">调试</a>
    				</shiro:hasPermission>
					<a href="${ctx}/account/baseAccount/updateAccStatus?id=${baseAccount.id}&oldAccStatus=${baseAccount.accStatus}&userid=${baseAccount.userid}&type=lower" onclick="return confirmx('确认要${baseAccount.accStatus eq 1 ? '下线':'上线' }该账户吗？', this.href)">${baseAccount.accStatus eq 1 ? '下线':'上线' }</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!-- 充值modal -->
	<div class="modal fade" style="display:none;" id="rechargeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   		<div class="modal-dialog">
      		<div class="modal-content">
        		<div class="modal-header">
            		<button type="button" class="close" 
               			data-dismiss="modal" aria-hidden="true">
                  		&times;
            		</button>
		            <h4 class="modal-title" id="myModalLabel">
		              	充值
		            </h4>
         		</div>
         		<div class="modal-body">
         			<form id="rechargeForm" class="form-horizontal">
	                	<div class="control-group">
	                     	<div class="controls1">
	                     		<div class="control-group">
									<label class="control-label">用户：</label>
									<div class="controls">
										<select id="userid" name="id" class="input-xlarge required">
											<c:forEach items="${fns:getUserList('lower')}" var="item">
												<option value="${item.value }">${item.label}</option>
											</c:forEach>
										</select>
										<span class="help-inline"><font color="red">*</font> </span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">充值方式：</label>
									<div class="controls">
										<select id="changeType" name="changeType" class="input-xlarge required">
											<option value="">请选择</option>
											<option value="CZ00">充值转入</option>
											<option value="CZ01">手动返充</option>
											<option value="XF01">手动扣款</option>
										</select>
										<span class="help-inline"><font color="red">*</font> </span>
									</div>
								</div>								
	                     		<div class="control-group">
									<label class="control-label">充值额度：</label>
									<div class="controls">
										<input type="text" id="accAmount" name="accAmount" htmlEscape="false" maxlength="8" class="input-xlarge"/>
										<span class="help-inline"><font color="red">*</font> </span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">备注：</label>
									<div class="controls">
										<input type="text" id="remark" name="remark" htmlEscape="false" maxlength="100" class="input-xlarge"/>
									</div>
								</div>
	                     	</div>
	                 	</div>
                 	</form>
         		</div>
         		<div class="modal-footer">
      				<input id="btnSubmit1" class="btn btn-primary" type="button" value="充值" onclick="javascript:onRecharge();"/>
      				<input id="btnSubmit2" class="btn btn-primary" type="button" value="关 闭" data-dismiss="modal" aria-hidden="true"/>
      			</div>
      		</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	<!-- END -->
</body>
</html>