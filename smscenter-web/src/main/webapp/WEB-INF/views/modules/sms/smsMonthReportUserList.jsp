<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户月报表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户的网关统计日报表数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sms/smsDayReport/exportByGateway");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sms/smsDayReport/listByUser?queryType=month");
			$("#searchForm").submit();
        	return false;
        }	
		function onView(userId,day,status){
			//status = 9 无状态量  =0 失败量
			var url="${ctx}/sms/smsDayReport/onView?user.id="+userId+"&sendDatetime="+day+"&reportStatus="+status
			windowOpen(url,userId,800,800);
		}
		
		function viewByPhoneType(user,day,queryType,name){
			//status = 9 无状态量  =0 失败量
			var url="${ctx}/sms/smsDayReport/viewByPhoneType?user.id="+user+"&day="+day+"&queryType="+queryType+"&user.name="+name
			windowOpen(url,userId+day,600,400);
		}	
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sms/smsDayReport/listByUser?queryType=month">信息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="smsDayReport" action="${ctx}/sms/smsDayReport/listByUser?queryType=month" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>机构：</label><sys:treeselect id="company" name="company.id" value="${smsDayReport.company.id}" labelName="company.name" labelValue="${smsDayReport.company.name}" 
				title="机构" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li>
			<li><label>用户名称：</label>
				<sys:treeselect id="user" name="user.id" value="${smsDayReport.user.id}" labelName="user.name" labelValue="${smsDayReport.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>  --%>
			<li><label>用户ID：</label>
				<form:input path="user.id" class="input-medium" maxlength="10"/>
			</li>
			<li><label>统计时间：</label>
				<input name="dayQ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsDayReport.dayQ}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>-
				<input name="dayZ" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${smsDayReport.dayZ}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
							<!-- <input id="btnExport" class="btn btn-primary" type="button" value="导出"/> -->
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>统计时间　　</th>
				<th>用户名称　　　　　　　　</th>
				<th>用户ID</th>
				 <th>登录账号</th>
				<%--<th>机构　　　</th> --%>
				<th>发送总量</th>
				<th>计费成功量</th>
				<th>平台成功率</th>
				<th>发送失败量</th>
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
					<fmt:formatDate value="${smsDayReport.day}" pattern="yyyy-MM"/>
				</td>
				<td>
					${smsDayReport.user.name}
				</td>
				<td>
					${smsDayReport.user.id}
				</td>
				<td>
					${smsDayReport.user.loginName}
				</td>
				<%-- 
				<td>
					${smsDayReport.companyName}
				</td> --%>
				<td>
					${smsDayReport.sendCount}
				</td>
				<td>
					${smsDayReport.userCount}
				</td>
				<td>
					<fmt:formatNumber type="number" value="${smsDayReport.successCount*100/smsDayReport.sendCount}" maxFractionDigits="2" pattern="0.00"></fmt:formatNumber>%
				</td>
				<td>
					${smsDayReport.sendCount-smsDayReport.successCount}
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