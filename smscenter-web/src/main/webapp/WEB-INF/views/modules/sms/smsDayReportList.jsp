<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>平台总日报表</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sms/smsDayReport/list">信息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="smsDayReport" action="${ctx}/sms/smsDayReport/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>统计时间：</label>
				<input name="dayQ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsDayReport.dayQ}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="dayZ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsDayReport.dayZ}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>					
			</li>
			<li><label>运营商：</label>
				<form:select path="phoneType" htmlEscape="false" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('phone_type')}" itemValue="value" itemLabel="label"/>
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
				<th>统计时间</th>
				<th>网关总量</th>
				<th>网关成功量</th>
				<th>网关失败量</th>
				<th>网关成功率</th>
				<th>状态报告成功</th>
				<th>状态成功占比</th>
				<th>状态报告失败</th>
				<th>状态失败占比</th>
				<th>状态报告空</th>
				<th>状态空占比</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="smsDayReport">
			<tr>
				<td>
					<fmt:formatDate value="${smsDayReport.day}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${smsDayReport.successCount}
				</td>
				<td>
					${smsDayReport.submitSuccessCount}
				</td>
				<td>
					${smsDayReport.submitFailCount}
				</td>
				<td>
					<c:if test="${smsDayReport.successCount eq 0 }">0%</c:if>
					<c:if test="${smsDayReport.successCount gt 0 }">
					<fmt:formatNumber type="number" value="${smsDayReport.submitSuccessCount*100/smsDayReport.successCount}" maxFractionDigits="2" pattern="0.00"></fmt:formatNumber>%
					</c:if>
				</td>
				<td>
					${smsDayReport.reportSuccessCount}
				</td>
				<td>
					<c:if test="${smsDayReport.successCount eq 0 }">0%</c:if>
					<c:if test="${smsDayReport.successCount gt 0 }">
					<fmt:formatNumber type="number" value="${smsDayReport.reportSuccessCount*100/smsDayReport.successCount}" maxFractionDigits="2" pattern="0.00"></fmt:formatNumber>%
					</c:if>
				</td>
				<td>
					${smsDayReport.reportFailCount-smsDayReport.submitFailCount}
				</td>
				<td>
					<c:if test="${smsDayReport.successCount eq 0 }">0%</c:if>
					<c:if test="${smsDayReport.successCount gt 0 }">
					<fmt:formatNumber type="number" value="${(smsDayReport.reportFailCount-smsDayReport.submitFailCount)*100/smsDayReport.successCount}" maxFractionDigits="2" pattern="0.00"></fmt:formatNumber>%
					</c:if>
				</td>	
				<td>
					${smsDayReport.reportNullCount}
				</td>
				<td>
					<c:if test="${smsDayReport.successCount eq 0 }">0%</c:if>
					<c:if test="${smsDayReport.successCount gt 0 }">
					<fmt:formatNumber type="number" value="${smsDayReport.reportNullCount*100/smsDayReport.successCount}" maxFractionDigits="2" pattern="0.00"></fmt:formatNumber>%
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>