<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/user/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/user/listV1");
			$("#searchForm").submit();
	    	return false;
	    }
		
		function showUpdate(userId){
			$("#userId").val(userId);
			$("#createUserId").val("");
			$('#updateModal').modal('show');
		}
		
		function onSubmit(){
			var createUserId = $("#createUserId").val();
			if(!createUserId){
				alertx("请选择创建人员");
				return;
			}
			$.ajax({
	             type: "POST",
	             url: "${ctx}/sys/user/updateCreate",
	             data: {userId:$("#userId").val(), createUserId:createUserId},
	             dataType: "json",
	             success: function(data){
	            	 if(data == 0){
	            		 alertx("操作成功");
	            		 window.location.reload(true);
	            	 }else{
	            		 alertx("操作失败");
	            	 }
	             },
	             error: function(){
	            	 alertx("系统错误");
	             }
	         });
		} 
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/listV1">用户列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/listV1" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>归属机构：</label><sys:treeselect url="/sys/office/treeData?type=1" id="company" value="${user.company.id}" labelName="company.name" 
				labelValue="${user.company.name }" title="机构" name="company.id" cssClass="input-small" allowClear="true"/></li>
			<li><label>登录名：</label><form:input path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/></li> 
			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
			<li class="clearfix"></li>
		</ul>	
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			<th>归属机构</th>
			<th>用户ID</th>
			<th>登录名</th>
			<th>姓名</th>
			<th>电话</th>
			<th>手机</th>
			<th>创建人</th>
			<th>创建时间</th>
			<shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td>${user.company.name}</td>
				<td>${user.id}</td>
				<td>${user.loginName}</td>
				<td>${user.name}</td>
				<td>${user.phone}</td>
				<td>${user.mobile}</td>
				<td>${user.createBy.name}</td>
				<td><fmt:formatDate value="${user.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
				<shiro:hasPermission name="sys:user:edit"><td>
    				<a href="javascript:showUpdate(${user.id})">修改创建人</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
		<!-- 审核不通过 -->
	<div class="modal fade" style="display:none;" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   		<div class="modal-dialog">
      		<div class="modal-content">
        		<div class="modal-header">
            		<button type="button" class="close" 
               			data-dismiss="modal" aria-hidden="true">
                  		&times;
            		</button>
		            <h4 class="modal-title" id="myModalLabel">
		              	修改创建人
		            </h4>
         		</div>
         		<div class="modal-body">
                	<div class="control-group">
                     	<div class="controls1">
                     		<input type="hidden" id="userId">
                     		<select id="createUserId" name="createUserId" style="width:498px;">
                     			<option value="">请选择</option>
                     			<c:forEach var="item" items="${fns:getUserListByUT('0')}">
                     				<option value="${item.value}">${item.label}</option>
                     			</c:forEach>
                     		</select>
                     	</div>
                 	</div>
         		</div>
      		</div>
      		<div class="modal-footer">
      			<input id="btnSubmit1" class="btn btn-primary" type="button" value="确 定" onclick="javascript:onSubmit();"/>
      			<input id="btnSubmit2" class="btn btn-primary" type="button" value="关 闭" data-dismiss="modal" aria-hidden="true"/>
      		</div>
		</div>
	</div>
	<!-- END -->
	
	
</body>
</html>