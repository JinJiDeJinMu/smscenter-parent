<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>消费统计列表</title>
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
	
	<link rel="stylesheet" href="${ctxFrontStatic }/css/chongzhi_detail.css" type="text/css" />
	<script type="text/javascript" src="${ctxFrontStatic }/js/jquery.watermark.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$('.th_list li:even').css({background:'#f9f9f9'});
			
			$('#dayQ').val(getLastMonthYestdy());
		});
		
		function getList()
		{
			html = '';
			var data = $('#recordForm').serialize();
			
			$.ajax({  
		        type: "post",  
		        url: "${ctxFront}/report/listByAccid",
		        data: data,
		        dataType: 'json',  
		        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
				success : function(result) {
					$('.div34 .clearfix').html(html);
			        $.each(result.data.list, function (i, item) {  
			            html += '<li>';
			            html += '   <span class="t3">'+ FormatDate(item.day) +'</span>';
			            html += '   <span class="t3">'+ item.accId +'</span>';
			     		html += '   <span class="t3">'+ item.accName +'</span>';
			     		html += '   <span class="t3">'+ item.sendCount +'</span>';
			            html += '   <span class="t3">'+ item.successCount +'</span>';
			            html += '   <span class="t3">'+ item.failCount +'</span>';
                        html += '   <span class="t3">'+ item.userCount +'</span>';
			            html += '</li>';
			        }); 
					$('.div34 .clearfix').html(html);
					
					getPageMsg(1,result.data);
				},
				error : function(){
					$('.div34 .clearfix').html(html);
				}
		    });
		}
		
		function FormatDate(strTime) {
		    var date = new Date(strTime);
		    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		}
		
		//获得上个月的日期  
	  	function getLastMonthYestdy(){  
	  		var date = new Date();  
	    	var daysInMonth = new Array([0],[31],[28],[31],[30],[31],[30],[31],[31],[30],[31],[30],[31]);  
		    var strYear = date.getFullYear();    
		    var strDay = date.getDate();    
		    var strMonth = date.getMonth()+1;  
		    if(strYear%4 == 0 && strYear%100 != 0){  
		       daysInMonth[2] = 29;  
		    }  
		    if(strMonth - 1 == 0)  
		    {  
		       strYear -= 1;  
		       strMonth = 12;  
		    }  
		    else  
		    {  
		       strMonth -= 1;  
		    }  
		    strDay = daysInMonth[strMonth] >= strDay ? strDay : daysInMonth[strMonth];  
		    if(strMonth<10)    
		    {    
		       strMonth="0"+strMonth;    
		    }  
		    if(strDay<10)    
		    {    
		       strDay="0"+strDay;    
		    }  
		    datastr = strYear+"-"+strMonth+"-"+strDay;  
		    return datastr;  
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
    	<h1>消费统计</h1>
    </div>
    <form action="/member/acc/rechargeList" name="recordForm" id="recordForm" method="post">
    	<input id="pageNo" name="pageNo" type="hidden"/>
		<input id="pageSize" name="pageSize" type="hidden"/>
      	<div class="div32">
        	<dl class="clearfix">
        		<dt>发送时间：</dt>
        	<dd>
	        	<input type="text" class="txt4" name="dayQ" id="dayQ" value="<%=str_date%>" onFocus="WdatePicker({isShowClear:false, readOnly:true, dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
	        	<i class="hr"></i>
	        	<input type="text" class="txt4" name="dayZ" id="dayZ" value="<%=str_date%>" onFocus="WdatePicker({isShowClear:false, readOnly:true, dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
	        	<input type="button" value="查 询" class="btn_search" onclick="getList()"/>
        	</dd>
        	</dl>
      	</div>
      	<div class="div33">
      	
      	</div>
      	<div class="div34">
	     	<div class="thDiv">
	     		<span class="t3">统计时间</span>
	     		<span class="t3">应用ID</span>
	     		<span class="t3">应用名称</span>
	     		<span class="t3">发送总量</span>
	     		<span class="t3">发送成功量</span>
	     		<span class="t3">系统处理成功量</span>
	     		<span class="t3">系统扣费成功量</span>
		    </div>
	     	<ul class="th_list clearfix">
	      
		    </ul>
		    <div class="page" id="billPage">
			</div>
	    </div>
	</form>
</body>
</html>