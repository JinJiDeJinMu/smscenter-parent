<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>短信发送管理</title>
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
		
		function showTestModal(smsContent){
			$('#smsContent').val(smsContent);
			$('#smsContentSize').text(smsContent.length);
			$('#smsContentModal').modal('show');
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sms/smsSend/">信息列表</a></li>
		<!--<shiro:hasPermission name="sms:smsSend:edit"><li><a href="${ctx}/sms/smsSend/form">短信发送添加</a></li></shiro:hasPermission>-->
	</ul>
	<form:form id="searchForm" modelAttribute="smsSend" action="${ctx}/sms/smsSend/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>短信ID：</label>
				<form:input path="id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>用户ID：</label>
				<form:input path="userid" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>批次ID：</label>
				<form:input path="taskid" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>手机号码：</label>
				<form:input path="phone" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>运营商：</label>
				<form:select path="phoneType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('phone_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>发送状态：</label>
				<form:select path="sendStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="P" label="待发送"/>
					<form:option value="T" label="发送成功"/>
					<form:option value="F" label="发送失败"/>
				</form:select>
			</li>
			<li><label>网关编号：</label>
				<form:input path="gatewayId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<!-- <li><label>网关状态：</label>
				<form:input path="gatewayStatus" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li> -->
			<li><label>发送时间：</label>
				<input name="sendTimeQ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsSend.sendTimeQ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="sendTimeZ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsSend.sendTimeZ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>手机号码</th>
				<th>发送时间　　</th>
				<th>短信内容　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　</th>
				<th>计数</th>
				<th>运营商</th>
				<th>省份</th>
				<th>发送号码</th>
				<th>发送状态</th>
				<th>网关编号</th>
				<th>回执状态</th>
				<th>提交时间　　</th>
				<th>回执时间　　</th>
				<!-- <th>发送时间　　　　　　</th> -->
				<!-- <th>推送状态</th> -->
				<th>短信ID</th>
				<th>批次ID</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsSend">
			<tr>
				<td>
					${smsSend.userid}
				</td>
				<td>
					${smsSend.phone}
				</td>
				<td>
					<fmt:formatDate value="${smsSend.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fn:substring(smsSend.smsContent, 0, 60)}...
					<a href="javascript:showTestModal('${fns:escapeHtml(smsSend.smsContent)}')">[更多查看]</a>
				</td>
				<td>
					${smsSend.smsSize}
				</td>
				<td>
					${fns:getDictLabel(smsSend.phoneType, 'phone_type', '')}
				</td>
				<td>
					${fns:getDictLabel(fn:substring(smsSend.phoneArea,0,2),'phone_province',smsSend.phoneArea)}	
				</td>
				<td>
					${smsSend.spnumber}
				</td>
				<td>
					${fns:startsWith(smsSend.sendStatus,'T') ? '发送成功' : '发送失败'}
				</td>
				<td>
					${smsSend.gatewayId}
				</td>
				<td>
					${smsSend.gatewayStatus eq 'T100' ? 'DELIVRD' : smsSend.gatewayStatus}
				</td>
				<td>
					<fmt:formatDate value="${smsSend.receiveTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>				
				 <td>
					<fmt:formatDate value="${smsSend.reportTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>				
				<!-- <td>
					<fmt:formatDate value="${smsSend.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td> -->
				<!-- 
				<td>
					${smsSend.userNotifyStatus eq 1 ? '成功' : smsSend.userNotifyStatus eq 0 ? '无' : smsSend.userNotifyStatus eq 2  ? '失败' : '待推'}
				</td>
				 -->
				<td>
					${smsSend.id}
				</td>
				<td>
					${smsSend.taskid}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!-- 短信内容 -->
	<div class="modal fade" style="display:none;" id="smsContentModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   		<div class="modal-dialog">
      		<div class="modal-content">
        		<div class="modal-header">
            		<button type="button" class="close" 
               			data-dismiss="modal" aria-hidden="true">
                  		&times;
            		</button>
		            <h4 class="modal-title" id="myModalLabel">
		              	短信内容
		            </h4>
         		</div>
         		<div class="modal-body">
         			<form id="gateWaySendForm" class="form-horizontal">
	                	<div class="control-group">
	                     	<div class="controls1">
	                         	<textarea style="width:498px;" readonly="readonly" name="smsContent" id="smsContent" rows="6" cols="8"></textarea>
	                         	共 <label name="smsContentSize" id="smsContentSize"></label>  个字。
	                     	</div>
	                 	</div>
                 	</form>
         		</div>
      		</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	<!-- END -->
	
</body>
</html>