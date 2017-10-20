<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>短信消费详单</title>
	<%-- <script type="text/javascript" src="${ctxFrontStatic }/js/jquery-1.10.2.min.js"></script> --%>
	<script type="text/javascript" src="${ctxFrontStatic }/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/main.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/jquery.colorbox.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/member.js"></script> 
	<%-- <script type="text/javascript" src="${ctxFrontStatic }/js/countCode.js"></script> --%>
	<link rel="stylesheet" href="${ctxFrontStatic }/css/colorbox.css" type="text/css" />
	<link rel="stylesheet" href="${ctxFrontStatic }/css/style.css" type="text/css" />
	<link rel="stylesheet" href="${ctxFrontStatic }/css/commonstyle.css" type="text/css" />
	<link rel="stylesheet" href="${ctxFrontStatic }/css/xiaofeixiangdan.css" type="text/css" />	
	<link href="${ctxFrontStatic }/css/default.css" type="text/css" rel="stylesheet" />
	<link href="${ctxFrontStatic }/css/def.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="${ctxFrontStatic }/js/cn.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/core.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/combo.js"></script>
	<script type="text/javascript">
		$(function(){
			getList();
		});
		
		function getList()
		{
			html = '';
			var data = $('#smsRepForm').serialize();
			
			$.ajax({  
		        type: "post",  
		        url: "${ctxFront}/send/receiveList",
		        data: data,
		        dataType: 'json',  
		        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
				success : function(result) {
					$('.common_list').html(html);
			        $.each(result.data.list, function (i, item) {  
			            html += '<li>';
			            html += '   <span class="t8">'+ item.phone +'</span>';
			            html += '   <span class="t12">'+ item.smsContent +'</span>';
			            html += '   <span class="t3">'+ item.createtime +'</span>';
			            html += '</li>';
			        }); 
					$('.common_list').html(html);
					
					getPageMsg(1,result.data);
				},
				error : function(){
					$('.common_list').html(html);
				}
		    });
		}
		
		//JS 加载分页
		function getPageMsg(statTimeType,data){
			//不同的消息类型
			var pageShow=$("#billPage");
			
			pageShow.html('');
			pageShow.append(data.pageHtml);
		}
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			getList();
        	return false;
        }
		
		<%   
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
        java.util.Date currentTime = new java.util.Date();//得到当前系统时间 
        String str_date = formatter.format(currentTime); //将日期时间格式化 
		%> 
	</script>
<body>
	<div class="tit">
       	<h1>上行短信记录</h1>
   	</div>
    <div class="search_box">
    	<form action="/member/smsCount/getPageSmsReplyList" name="smsRepForm" id="smsRepForm" method="post">
    		<input id="pageNo" name="pageNo" type="hidden"/>
			<input id="pageSize" name="pageSize" type="hidden"/>
	    	<div class="search_line">
	        	<span class="name1">时间：</span>
	           	<input type="text" class="ltxt" name="createtimeQ" id="createtimeQ" value="<%=str_date%>" onFocus="WdatePicker({isShowClear:false, readOnly:true, dateFmt:'yyyy-MM-dd', maxDate:'%y-%M-%d', minDate:'%y-%M-01'})" readonly="readonly"/>
	            <span class="name1">号码：</span>
	            <input type="text" class="ltxt" name="phone" id="phone" value="" />
	        </div>
	        <div class="search_line">
	        	<span class="name1">签名：</span>
	         	<input type="text" class="ltxt" name="signature" id="signature" value="" />
	            <input type="button" class="sub_btn ml" value="查 询" onclick="getList()"/>
	        </div>
    	</form>
    </div>
    <div class="spend_list">
    	<div class="common_title">
            <span class="t8">手机号码</span>
            <span class="t12">回复内容</span>
            <span class="t3">回复时间</span>
        </div>
        <ul class="common_list">
        	
        </ul>
        <div class="page" id="billPage">
		</div>
    </div> 
</body>
</html>