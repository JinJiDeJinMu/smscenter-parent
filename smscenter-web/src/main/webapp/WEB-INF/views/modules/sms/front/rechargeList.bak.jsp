<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>充值记录</title>
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
			$("#rechargeId").watermark("请输入订单号");
			
			$('.th_list li:even').css({background:'#f9f9f9'});
			
			$('#createTimeQ').val(getLastMonthYestdy());
		});
		
		function getList()
		{
			html = '';
			var data = $('#recordForm').serialize();
			
			$.ajax({  
		        type: "post",  
		        url: "${ctxFront}/user/rechargeList",
		        data: data,
		        dataType: 'json',  
		        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
				success : function(result) {
					$('.div34 .clearfix').html(html);
					//var jsonObj=eval("("+result.data.list+")");  
			        $.each(result.data.list, function (i, item) {  
			            html += '<li>';
			            if (item.changeType == 'CZ00'){
			            	html += '   <span class="t5">充值转入</span>';
		            	}
			            else if (item.changeType == 'CZ01'){
			            	html += '   <span class="t5">手动返充</span>';
		            	}
			            else if (item.changeType == 'XF01'){
			            	html += '   <span class="t5">手动扣款</span>';
		            	}
			            else if (item.changeType == 'XF00'){
			            	html += '   <span class="t5">充值转出</span>';
		            	}
			            else if (item.changeType == 'CZ02'){
			            	html += '   <span class="t5">失败返充</span>';
		            	}
			            else if (item.changeType == 'XF02'){
			            	html += '   <span class="t5">自消费</span>';
		            	}
			            else if (item.changeType == 'CZ03'){
			            	html += '   <span class="t5">提交计费返充</span>';
		            	}
			            else if (item.changeType == 'CZ04'){
			            	html += '   <span class="t5">状态报告返充</span>';
		            	}
			            
			            html += '   <span class="t2">'+ item.createTime +'</span>';
			            html += '   <span class="t3">'+ item.amount +'</span>';
			            html += '   <span class="t1">'+ item.remark +'</span>';
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
    	<h1>充值记录</h1>
    </div>
    <form action="/member/acc/rechargeList" name="recordForm" id="recordForm" method="post">
    	<input id="pageNo" name="pageNo" type="hidden"/>
		<input id="pageSize" name="pageSize" type="hidden"/>
      	<div class="div32">
        	<dl class="clearfix">
        		<dt>开始结束时间：</dt>
        	<dd>
	        	<input type="text" class="txt4" name="createTimeQ" id="createTimeQ" value="<%=str_date%>" onFocus="WdatePicker({isShowClear:false, readOnly:true, dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
	        	<i class="hr"></i>
	        	<input type="text" class="txt4" name="createTimeZ" id="createTimeZ" value="<%=str_date%>" onFocus="WdatePicker({isShowClear:false, readOnly:true, dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
	        	<input type="button" value="查 询" class="btn_search" onclick="getList()"/>
        	</dd>
        	</dl>
      	</div>
      	<div class="div33">
      	
      	</div>
      	<div class="div34">
	     	<div class="thDiv">
		     	<span class="t5">类型</span>
		     	<span class="t2">操作时间</span>
		     	<span class="t3">条数</span>
		     	<span class="t1">备注</span>
		    </div>
	     	<ul class="th_list clearfix">
	      
		    </ul>
		    <div class="page" id="billPage">
			</div>
	    </div>
	</form>
</body>
</html>