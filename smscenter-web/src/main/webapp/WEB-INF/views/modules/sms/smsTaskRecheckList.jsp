<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>短信任务管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/docheck/docheck.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function onRecheck(status){
			var ids = getCheckboxValue("id");
			if(!ids){
				alertx("请选择要审核的数据");
			}else{
				if("-2" == status){
					 $("#recheckRemarks").val('')
					$('#recheckRemarksModal').modal('show');
				}else{
					$("#searchForm").attr("action","${ctx}/sms/smsTask/recheckSms?sendStatus="+status+"&ids="+ids);
					$("#searchForm").submit();
				}
				return false;
			}
		}
		
		function onNO(){
			var ids = getCheckboxValue("id");
			var recheckRemarks = $("#recheckRemarks").val();
			if(recheckRemarks == null || recheckRemarks == ''){
				alertx("请输入不通过原因");
				return;
			}
			recheckRemarks = encodeURI(encodeURI(recheckRemarks));
			$("#searchForm").attr("action","${ctx}/sms/smsTask/recheckSms?sendStatus=-2"+"&ids="+ids+"&recheckRemarks="+recheckRemarks);
			$("#searchForm").submit();
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sms/smsTask/recheckList">信息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="smsTask" action="${ctx}/sms/smsTask/recheckList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账户ID：</label>
				<form:input path="accId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>用户ID：</label>
				<form:input path="userid" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li>
				<label>短信内容：</label>
				<form:input path="smsContent" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>发送时间：</label>
				<input name="sendDatetimeQ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsTask.sendDatetimeQ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="sendDatetimeZ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsTask.sendDatetimeZ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> 
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnOK" class="btn btn-primary" type="button" value="审核通过" onclick="javascript:onRecheck(1);"/>
				<input id="btnNO" class="btn btn-primary" type="button" value="审核不通过" onclick="javascript:onRecheck(-2);"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="30px;"><input type="checkbox" onclick="doCheckAll(this,'id', this.checked)"></th>
				<th>账户ID</th>
				<th>用户ID</th>
				<th>发送条数</th>
				<th>短信内容</th>
				<th>创建时间</th>
				<!--<shiro:hasPermission name="sms:smsTask:edit"><th>操作</th></shiro:hasPermission>-->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsTask">
			<tr onclick="selectTr(this, '${smsTask.taskid}' ,'id')">
				<td><input type="checkbox" name="id" id="${smsTask.taskid}" value="${smsTask.taskid}" onclick="if(this.checked){this.checked=false;}else{this.checked=true;}">
				</td>
				<td>
					${smsTask.accId}
				</td>
				<td>
					${smsTask.userid}
				</td>
				<td>
					${smsTask.phoneCount}
				</td>
				<td>
					${smsTask.smsContent}
				</td>
				<td>
					<fmt:formatDate value="${smsTask.createDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<!--<shiro:hasPermission name="sms:smsTask:edit"><td>
    				<a href="${ctx}/sms/smsTask/form?id=${smsTask.id}">修改</a>
					<a href="${ctx}/sms/smsTask/delete?id=${smsTask.id}" onclick="return confirmx('确认要删除该短信任务吗？', this.href)">删除</a>
				</td></shiro:hasPermission>-->
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!-- 审核不通过 -->
	<div class="modal fade" style="display:none;" id="recheckRemarksModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   		<div class="modal-dialog">
      		<div class="modal-content">
        		<div class="modal-header">
            		<button type="button" class="close" 
               			data-dismiss="modal" aria-hidden="true">
                  		&times;
            		</button>
		            <h4 class="modal-title" id="myModalLabel">
		              	不通过原因
		            </h4>
         		</div>
         		<div class="modal-body">
                	<div class="control-group">
                     	<div class="controls1">
                     		<textarea style="width:498px;" name="recheckRemarks" rows="3" cols="8" maxlength="200" id="recheckRemarks"></textarea>
                     	</div>
                 	</div>
         		</div>
      		</div>
      		<div class="modal-footer">
      			<input id="btnSubmit1" class="btn btn-primary" type="button" value="确 定" onclick="javascript:onNO();"/>
      			<input id="btnSubmit2" class="btn btn-primary" type="button" value="关 闭" data-dismiss="modal" aria-hidden="true"/>
      		</div>
		</div>
	</div>
	<!-- END -->
	
</body>
</html>