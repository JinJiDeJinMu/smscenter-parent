<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>索取发票申请记录</title>
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
		//开具发票页面
		function invoiceInit(id){
			$('#invoiceId').val(id);
			$('#invoiceModal').modal('show');
		}
		
		//开票
		function makeInvoice(){
			var invoiceNumber = $("#invoiceNumber").val();
			if(!invoiceNumber){
				alertx("请输入发票号");
				return;
			}
			var expressCompany = $("#expressCompany").val();
			if(!expressCompany){
				alertx("请输入快递公司");
				return;
			}
			var expressNumber = $("#expressNumber").val();
			if(!expressNumber){
				alertx("请输入快递单号");
				return;
			}
			
			$("#invoiceForm").attr('action','${ctx}/account/baseUserInvoiceLogs/updateStatus');
			$("#invoiceForm").submit();
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/account/baseUserInvoiceLogs/">信息列表</a></li>
		<!--<shiro:hasPermission name="account:baseUserInvoiceLogs:edit"><li><a href="${ctx}/account/baseUserInvoiceLogs/form">用户发票明细添加</a></li></shiro:hasPermission>-->
	</ul>
	<form:form id="searchForm" modelAttribute="baseUserInvoiceLogs" action="${ctx}/account/baseUserInvoiceLogs/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户ID：</label>
				<form:input path="userid" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>用户姓名：</label>
				<form:input path="user.name" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>发票类型：</label>
				<form:select path="invoiceType" class="input-xlarge">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('invoice_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>发票内容：</label>
				<form:select path="invoiceContent" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('invoice_content')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>申请时间：</label>
				<input name="createTimeQ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${baseUserInvoiceLogs.createTimeQ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="createTimeZ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${baseUserInvoiceLogs.createTimeZ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户ID</th>
				<th>用户姓名</th>
				<th>开具发票金额</th>
				<th>发票抬头信息</th>
				<th>发票类型</th>
				<th>发票内容</th>
				<th>状态</th>
				<th>申请开票时间</th>
				<shiro:hasPermission name="account:baseUserInvoiceLogs:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="baseUserInvoiceLogs">
			<tr>
				<td><a href="${ctx}/account/baseUserInvoiceLogs/form?id=${baseUserInvoiceLogs.id}">
					${baseUserInvoiceLogs.userid}
				</a></td>
				<td>
					${baseUserInvoiceLogs.user.name}
				</td>
				<td>
					<fmt:formatNumber type="number" value="${baseUserInvoiceLogs.amount/100}" pattern="0.00" maxFractionDigits="2"/>
				</td>
				<td>
					${baseUserInvoiceLogs.headerInfo}
				</td>
				<td>
					${fns:getDictLabel(baseUserInvoiceLogs.invoiceType, 'invoice_type', baseUserInvoiceLogs.invoiceType)}
				</td>
				<td>
					${fns:getDictLabel(baseUserInvoiceLogs.invoiceContent, 'invoice_content', baseUserInvoiceLogs.invoiceContent)}
				</td>
				<td>
					索取发票
				</td>
				<td>
					<fmt:formatDate value="${baseUserInvoiceLogs.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="account:baseUserInvoiceLogs:edit"><td>
    				<a href="${ctx}/account/baseUserInvoiceLogs/form?id=${baseUserInvoiceLogs.id}">查看</a>
    				<a href="javascript:invoiceInit(${baseUserInvoiceLogs.id})">开具发票</a>
    				<a href="${ctx}/account/baseUserInvoiceLogs/updateStatus?id=${baseUserInvoiceLogs.id}&status=0" onclick="return confirmx('确认不开具发票吗？', this.href)">不开票</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<div class="modal fade" style="display:none;" id="invoiceModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  		<div class="modal-dialog">
     		<div class="modal-content">
       			<div class="modal-header">
           		<button type="button" class="close" 
              			data-dismiss="modal" aria-hidden="true">
                 		&times;
           		</button>
	            <h4 class="modal-title" id="myModalLabel">
	              	开具发票
	            </h4>
        		</div>
        		<div class="modal-body">
        			<form id="invoiceForm" class="form-horizontal" method="post">
                	<div class="control-group">
                		<input type="hidden" id="invoiceId" name="id">
                		<input type="hidden" name="status" value="1">
                		<label class="control-label">发票号：</label>
						<div class="controls">
							<input type="text" name="invoiceNumber" id="invoiceNumber" maxlength="40" class="input-xlarge">
						</div>
                 	</div>
                 	<div class="control-group">
						<label class="control-label">快递公司：</label>
						<div class="controls">
							<input type="text" name="expressCompany" id="expressCompany" class="input-xlarge" maxlength="40">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">快递单号：</label>
						<div class="controls">
							<input type="text" name="expressNumber" id="expressNumber" class="input-xlarge" maxlength="40">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">备注：</label>
						<div class="controls">
							<textarea id="remark" name="remark" rows="3" cols="5" class="input-xlarge" maxlength="255"></textarea>
						</div>
					</div>					
                	</form>
        		</div>
        		<div class="modal-footer">
        			<input id="btnSubmit1" class="btn btn-primary" type="button" value="保存" onclick="javascript:makeInvoice();"/>
     				<input id="btnSubmit2" class="btn btn-primary" type="button" value="关 闭" data-dismiss="modal" aria-hidden="true"/>
        		</div>
     		</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
</body>
</html>