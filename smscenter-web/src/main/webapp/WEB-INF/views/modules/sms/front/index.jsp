<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>管理控制台</title>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/home.css" type="text/css"/>
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
    <script type="text/javascript">
	$(document).ready(function() { 
		/*账户余额icon*/
		$('.icon').hover(function(){$('.icon_info').show();},function(){$('.icon_info').hide();});
		/*AppID icon*/
		$('.idcon').hover(function(){$('.id_info').show();},function(){$('.id_info').hide();});
		$('.adrip').colorbox({width:620,height:470});
		
		getList();
		getBalanceList();
	}); 
	function getList()
	{
		$.ajax({  
	        type: "post",  
	        url: "${ctxFront}/user/userInfo",
	        dataType: 'json',  
	        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
			success : function(result) {
				$('.message dl:eq(0)').find('dd').html(result.data.id);
				$('.message dl:eq(1)').find('dd').html(result.data.account.accId);
				$('.message dl:eq(2)').find('dd').html(result.data.account.apikey);
			},
			error : function(){
			}
	    });
	}
	
	function getBalanceList()
	{
		$.ajax({  
	        type: "post",  
	        url: "${ctxFront}/user/balance",
	        dataType: 'json',  
	        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
			success : function(result) {
				$('.div31 dl:eq(0)').find('em').html(result.data);
				//$('.ye').find('em').html(result.data);
				$('.ye .count').html(result.data);
			},
			error : function(){
			}
	    });
	}
	</script>
</head>
<body>
    <div class="div1 clearfix">
        <a href="javascript:void(0);" class="c1 ye"> 
        	<span class="img"> 
        		<img src="${ctxFrontStatic }/images/img_s2.png" />
        		<em>剩余条数</em> 
        	</span>
            <em class="count">
                8.00
            </em>
         </a>
        <a href="javascript:void(0);" class="c3"> 
        	<span class="img"> 
        		<img src="${ctxFrontStatic }/images/img_s3.png" />
        		<em>昨日消费</em> </span> <em class="count">0</em> 
        </a>
    </div>
    <div class="div2">
        <div class="tit1">
            <h1 class="b1">开发者主账号</h1>
        </div>
        <div class="cont">
            <div class="message">
                <dl>
                    <dt>用户 ID：</dt>
                    <dd></dd>
                </dl>
                <dl>
                    <dt>AppID(默认)：</dt>
                    <dd><em style="display:inline-block; font-size:10px; color:#c9c9c9; padding-left:8px;"></em></dd>
                </dl>    
                <dl>
                    <dt>API KEY：</dt>
                    <dd><em style="display:inline-block; font-size:10px; color:#c9c9c9; padding-left:8px;">（API KEY 请到应用管理中获取）</em></dd>
                </dl>
                <dl>
                    <dt>Rest URL(生产)：</dt>
                    <dd>${fns:getConfig('rest.url')}</dd>
                </dl>
            </div>
        </div>
    </div>
    <div class="div3">
        <div class="tit1">
            <h1 class="b6">快速接入</h1>
        </div>
        <div class="btnbox">
            <a href="${ctxFront }/view/wendang" target="_blank">短信</a>
        </div>
    </div>
</body>
</html>