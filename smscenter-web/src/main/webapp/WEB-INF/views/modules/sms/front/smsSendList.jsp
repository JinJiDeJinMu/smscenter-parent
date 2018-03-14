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
	<script type="text/javascript" src="${ctxFrontStatic }/js/jquery.autocomplete.js"></script>
	<script type="text/javascript">
		var cb_appList;
		$(function(){
		 	var dd_appList = [];
			dd_appList.push({ code:'0', name:'请选择失败原因'});
			//dd_appList.put( {code:'0000001', name:'成功'});
			dd_appList.push( {code:'160000', name:'系统错误'});
			dd_appList.push( {code:'160032',  name:'短信模板无效'});
			dd_appList.push( {code:'160033',  name:'短信存在黑词'});
			dd_appList.push( {code:'160034',  name:'号码黑名单'});
			dd_appList.push( {code:'160035',  name:'短信下发内容为空'});
			dd_appList.push( {code:'160036',  name:'短信模板类型未知'});
			dd_appList.push( {code:'160037',  name:'短信内容长度限制'});
			dd_appList.push( {code:'160038',  name:'短信验证码发送过频繁'});
			dd_appList.push( {code:'160039',  name:'超出同模板同号天发送次数上限'});
			dd_appList.push( {code:'160040',  name:'验证码超出同模板同号码天发送上限'});
			dd_appList.push( {code:'160041',  name:'通知超出同模板同号码天发送上限'});
			dd_appList.push( {code:'160042',  name:'号码格式有误'});
			dd_appList.push( {code:'160050',  name:'短信发送失败'});
			var cfg = {
				keyField: 'code',
				displayField: 'name',
				multiSelect: false,
				width: 258,
				boxWidth: 258,
				cols : [
				{
					field: 'name', width: '98%'
				}]
			};
			
			var cfg_appList = $.extend({data: dd_appList}, cfg);
			cb_appList=$('#combo2').mac('combo', cfg_appList);  
			cb_appList.select('0');
			
			getList();
		});
		
		function getList()
		{
			$("#errorMsg").val(cb_appList.selected);
			var temp=$("#temp").val();
			if(temp==""){
				$("#tempId").val("");
			}
			
			html = '';
			var data = $('#smsForm').serialize();
			
			$.ajax({  
		        type: "post",  
		        url: "${ctxFront}/send/sendList",
		        data: data,
		        dataType: 'json',  
		        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
				success : function(result) {
					$('.common_list').html(html);
					//var jsonObj=eval("("+result.data.list+")");  
			        $.each(result.data.list, function (i, item) {  
			            html += '<li>';
			            html += '   <span class="t8">'+ item.sendTime +'</span>';
			            html += '   <span class="t10 center">'+ item.phone +'</span>';
			            html += '   <span class="t14 center">'+ item.smsContent +'</span>';
			            html += '   <span class="t6 ">'+ item.sendStatus +'</span>';
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
        <h1>短信发送记录</h1>
       	<span class="tips">显示该账户下所有短信发送详单</span>
    </div>
    <div class="search_box">
        <form action="/member/smsCount/getPageSmsSendList" name="smsForm" id="smsForm" method="post">
        <input id="pageNo" name="pageNo" type="hidden"/>
		<input id="pageSize" name="pageSize" type="hidden"/>
        <input type="hidden" name="type" id="type" value="1">
        <input type="hidden" name="tempId" id="tempId" value="">
        <input type="hidden" name="errorMsg" id="errorMsg" value="">
    	<div class="search_line">
        	<span class="name">发送时间：</span>
            <input type="text" class="ltxt" name="sendTimeQ" id="sendTimeQ" value="<%=str_date%>" onFocus="WdatePicker({isShowClear:false, readOnly:true, dateFmt:'yyyy-MM-dd', maxDate:'%y-%M-%d', minDate:'%y-%M-01'})" readonly="readonly"/>
            <span class="name">号码：</span>
            <input type="text" class="ltxt" name="phone" id="phone" value=""/>
        </div>
        <div class="search_line">
            <span class="name">模板ID：</span>
            <input type="text" class="ltxt" name="temp"  id="temp" value=""/>
        	<span class="name">状态：</span>
            <input type="radio" class="radio" name="sendStatus" value=" "  checked="checked"/>
            <em class="radio_name">全部</em>
            <input type="radio" class="radio" name="sendStatus" value="1"  />
            <em class="radio_name">成功</em>
            <input type="radio" class="radio" name="sendStatus" value="2"  />
            <em class="radio_name em1">失败</em>
            <input type="button" class="sub_btn ml" value="查 询" onclick="getList()"/>
        </div>
        <!-- 
        <div class="search_line">
        <span class="name">失败原因：</span>
            <div id="combo2" class="combo" style="width:258px; margin-right:0;"></div>
        </div>
         -->
        </form>
    </div>
    <div class="spend_list">
    	<div class="common_title">
        	<span class="t8 center">发送时间</span>
            <span class="t10 center">接收号码</span>
            <span class="t14 center">短信内容</span>
            <span class="t6 ">发送状态</span>
        </div>
        <ul class="common_list">
        
        </ul>

		<div class="page" id="billPage">
		</div>
    </div>
</body>
</html>