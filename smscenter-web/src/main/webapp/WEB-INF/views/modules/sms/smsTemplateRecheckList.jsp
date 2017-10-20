<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>短信模板管理</title>
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
					$("#templateId").val('');
					$("#templateContent").val('');
					$('#recheckYESModal').modal('show');
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
			$("#searchForm").attr("action","${ctx}/sms/smsTemplate/recheckTemplate?status=-2"+"&ids="+ids+"&recheckRemarks="+recheckRemarks);
			$("#searchForm").submit();
		}
		
		function onYES(){
			var ids = getCheckboxValue("id");
			var templateId = $("#templateId").val();
			var templateContent = $("#templateContent").val();
			if(templateId == null || templateId == ''){
				alertx("请输入源模板编号");
				return;
			}
			if(templateContent == null || templateContent == ''){
				alertx("请输入源模板内容");
				return;
			}
			templateContent = encodeURI(encodeURI(templateContent));
			$("#searchForm").attr("action","${ctx}/sms/smsTemplate/recheckTemplate?status=1"+"&ids="+ids+"&templateId="+templateId+"&templateContent="+templateContent+"&templateSrc=1");
			$("#searchForm").submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sms/smsTemplate/recheckList">信息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="smsTemplate" action="${ctx}/sms/smsTemplate/recheckList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>创建人：</label>
				<form:input path="createBy.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>模板名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="80" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createtimeQ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsTemplate.createtimeQ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="createtimeZ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsTemplate.createtimeZ}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>模板名称</th>
				<th>模板内容</th>
				<th>类型</th>
				<th>状态</th>
				<th>范围</th>
				<th>创建人ID</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsTemplate">
			<tr onclick="selectTr(this, '${smsTemplate.id}' ,'id')">
				<td>
					<input type="checkbox" name="id" id="${smsTemplate.id}" value="${smsTemplate.id}" onclick="if(this.checked){this.checked=false;}else{this.checked=true;}">
				</td>
				<td>
					${smsTemplate.name}
				</td>
				<td>
					${smsTemplate.content}
				</td>
				<td>
					${fns:getDictLabel(smsTemplate.type, 'sms_type', smsTemplate.type)}
				</td>
				<td>
					${smsTemplate.status eq 0 ? '禁用' : smsTemplate.status  eq -2 ? '审核不通过' : smsTemplate.status eq -1 ? '待审' : '启用'}
				</td>
				<td>
					${smsTemplate.scope eq 0 ? '全局' : '用户'}
				</td>
				<td>
					${smsTemplate.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${smsTemplate.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
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
                     		<textarea style="width:498px;" name="recheckRemarks" rows="3" cols="8" maxlength="200" id="recheckRemarks"></textarea>&nbsp;<span><font color="red">*</font></span>
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
	<!-- 审核通过 -->
	<div class="modal fade" style="display:none;" id="recheckYESModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
   		<div class="modal-dialog">
      		<div class="modal-content">
        		<div class="modal-header">
            		<button type="button" class="close" 
               			data-dismiss="modal" aria-hidden="true">
                  		&times;
            		</button>
		            <h4 class="modal-title" id="myModalLabel">
		              	审核通过
		            </h4>
         		</div>
         		<div class="modal-body">
                	<div class="control-group">
                     	<div class="controls1">
								源模板编号：<input id="templateId" type="text" name="templateId" htmlEscape="false" maxlength="80" style="width:358px;"/>&nbsp;<span><font color="red">*</font></span>
								<br/><br/>
								<input type="hidden" name="templateSrc" value="1">
								源模板内容：<textarea id="templateContent" name="templateContent" rows="3" cols="8" maxlength="200" style="width:358px;" ></textarea>&nbsp;<span><font color="red">*</font></span>
                     	</div>
                 	</div>
         		</div>
      		</div>
      		<div class="modal-footer">
      			<input id="btnSubmit1" class="btn btn-primary" type="button" value="确 定" onclick="javascript:onYES();"/>
      			<input id="btnSubmit2" class="btn btn-primary" type="button" value="关 闭" data-dismiss="modal" aria-hidden="true"/>
      		</div>
		</div>
	</div>
	<!-- END -->
	
</body>
</html>