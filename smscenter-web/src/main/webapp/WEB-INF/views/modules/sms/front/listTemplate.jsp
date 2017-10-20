<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>短信模板</title>
	<script type="text/javascript" src="${ctxFrontStatic }/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/main.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/jquery.colorbox.js"></script>
	<%-- <script type="text/javascript" src="${ctxFrontStatic }/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/member.js"></script> 
	<script type="text/javascript" src="${ctxFrontStatic }/js/countCode.js"></script> --%>
	<link rel="stylesheet" href="${ctxFrontStatic }/css/colorbox.css" type="text/css" />
	<link rel="stylesheet" href="${ctxFrontStatic }/css/style.css" type="text/css" />
	<link rel="stylesheet" href="${ctxFrontStatic }/css/commonstyle.css" type="text/css" />
	<link rel="stylesheet" href="${ctxFrontStatic }/css/sms_template.css" type="text/css" />
	<link href="${ctxFrontStatic }/css/default.css" type="text/css" rel="stylesheet" />
	<link href="${ctxFrontStatic }/css/def.css" type="text/css" rel="stylesheet" />
	<%-- <script type="text/javascript" src="${ctxFrontStatic }/js/cn.js"></script> 
	<script type="text/javascript" src="${ctxFrontStatic }/js/core.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/combo.js"></script>--%>
	<script type="text/javascript">
	var html;
	$(function(){
		function setHeight(){
			var nHeight = $('.container').height()+'px';
			var brsHeight = $(window).height()-176+'px';
			var mainHeight = parseInt(brsHeight)+'px';
			if(parseInt(nHeight) < parseInt(brsHeight)){
				$('.left,.left .tabs').css({'height':mainHeight});
			}else if(parseInt(nHeight) >= parseInt(brsHeight)){
				$('.left,.left .tabs').css({'height':nHeight});
			}
		}
		window.onload = function(){setHeight();};
		window.onresize = function(){setHeight();}
		/*列表隔行变色*/
		$('.th_list li:even').css('background','#f9f9f9');
		
		getList();
	});
	
	function getList()
	{
		html = '';
		var data = $('#smsForm').serialize();
		
		$.ajax({  
	        type: "post",  
	        url: "${ctxFront}/smsTemplate/list",
	        data: data,
	        dataType: 'json',  
	        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
			success : function(result) {
				$('.clearfix').html(html);
				//var jsonObj=eval("("+result.data.list+")");  
		        $.each(result.data.list, function (i, item) {
		        	var zt = "未知";
		        	if(item.status == -2){
		        		zt = "审核失败";
		        	}else if(item.status == -1){
		        		zt = "待审核";
		        	}else if(item.status == 0){
		        		zt = "禁用";
		        	}else if(item.status == 1){
		        		zt = "启用";
		        	}
		            html += '<li>';
		            html += '   <span class="t2">'+ item.id +'</span>';
		            html += '   <span class="t2">'+ zt +'</span>';
		            html += '   <span class="t6" title="'+item.name+'">'+ item.name +'</span>';
		            html += '   <span class="t8" title="'+item.content+'">'+ item.content +'</span>';
		            //html += '   <span class="t9"><a href="/member/smsTemplate/toSmsTempConsult" target="_blank">查看参考模板</a></span>';
		            html += '</li>';
		        }); 
				$('.clearfix').html(html);
				
				getPageMsg(1,result.data);
			},
			error : function(){
				$('.clearfix').html(html);
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
	</script>
</head>
<body>
	<div class="tit"><h1>模板列表</h1></div>
   	<div class="tx_div">
      	<h2>使用规则：</h2>
      	<p>
      	1、模板审核通过后才可调用，受运营商限制，每个工作日9点统一提交给运营商审核，正常当日即可获得结果
      	</p>
      </div>
     <form name="smsForm" id="smsForm" method="post">
      	<input id="pageNo" name="pageNo" type="hidden"/>
		<input id="pageSize" name="pageSize" type="hidden"/>
	</form>
	<div class="smsbox">
        <!--新用户未创建模版时显示 start-->
        <div class="tablelist">
        	<div class="thDiv" style="border-top:dotted 1px #DDD;">
        	   <span class="t2">模板ID</span>
        	   <span class="t2">模板状态</span>
               <span class="t6">标题</span>
               <span class="t8">模板内容</span>
               <!-- <span class="t9">操作</span> -->
           </div>
           <ul class="th_list clearfix">
          </ul>
        </div>
        <!--新用户未创建模版时显示 end-->
        <div class="page" id="billPage">
			</div>
   </div>
</body>
</html>