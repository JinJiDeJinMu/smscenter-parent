<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>云通讯平台</title>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/home.css" type="text/css"/>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/main.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.colorbox.js"></script>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/colorbox.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/style.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/commonstyle.css" type="text/css" />
    <script type="text/javascript">
        $(document).ready(function() {
            function setHeight(){
                var nHeight = $('.container').height()+'px';
                var brsHeight = $(window).height()-128+'px';
                var mainHeight = parseInt(brsHeight)+'px';
                if(parseInt(nHeight) < parseInt(brsHeight)){
                    $('.left,.left .tabs').css({'height':mainHeight});
                }else if(parseInt(nHeight) >= parseInt(brsHeight)){
                    $('.left,.left .tabs').css({'height':nHeight});
                }
                /*公告下面图片大小设置*/
                var W = $('.right').width()+'px';
                var oImg = $('.activity_logo').find('img');

                oImg.css("width",W);
            }
            window.onload = function(){setHeight();};
            window.onresize = function(){setHeight();}
            $('.btn_preview').colorbox();
            //是否弹出引导流程
            var proFlag ="0";
            if(proFlag=="1"){
                $(".pop_joincont_div").show();
                $(".pop_join_back").show();
            }

            $('.btn_cancel').click(function(){
                $('#overlayBg').hide();
                $('#helpDiv').hide();
            });
            $('.help').click(function(){
                $('#overlayBg').show();
                $('#helpDiv').show();
            });
            
            $('.listnav a').click(function(){
                $(".listnav a").removeClass("cur");
                $(this).addClass("cur");
            });
            
            $("#spanId").click();
        });
        
        function modtag(id,num)
        {
        	$(".left .tabs li").removeClass("on");
            $(".left .tabs li:eq("+num+")").addClass("on");
            
            $('div .con').hide();
            $('#'+id).show();
            //$("#spanId").click();
            $('#'+id).find('span[id^="spanId"]').click();
            $('#'+id).find('span[id^="spanId"]').parent().addClass("cur");
        }
    </script>
</head>
<body>
<div class="wrap">
    <div class="header">
        <div class="top">
            <div class="logo"><a href="#" onclick="modtag('m1Div',0);"><img src="${ctxFrontStatic }/images/logo.png"/></a></div>
            <div class="inform">
                <a href="#" onclick="modtag('m3Div',3);" class="account">账号管理</a>
                <span class="line">|</span>
                <!-- <a href="javascript:void(0);" class="quit" onclick="javascript:location.replace('/user/logout');event.returnValue=false;">退出</a> -->
                <a href="${ctxFront}/logout"class="quit">退出</a>
            </div>
        </div>
    </div>
    <div class="container clearfix">
        <div class="left">
            <ul class="tabs">
                <%-- <li class="on"><a href="${ctxFront}/view/main"><div class="m1">1</div><span>管理</span></a></li> --%>
                <li class="on"><a href="#" onclick="modtag('m1Div',0);"><div class="m1">1</div><span>管理</span></a></li>
                <li><a href="#" onclick="modtag('m2Div',1);"><div class="m2">2</div><span>财务</span></a></li>
                <li><a href="#" onclick="modtag('m6Div',2);"><div class="m6">4</div><span>详单</span></a></li>
                <li><a href="#" onclick="modtag('m3Div',3);"><div class="m3">3</div><span>帐号</span><b class="count" style="display:none"><i id="msg_account_sum" ></i></b></a></li>
            </ul>
            <div class="con" id="m1Div">
                <div class="nr" id="selectDiv1" style="display:block;">
                    <ul class="listnav">
                        <li>
                            <h2>控制台</h2>
                            <div class="nav2">
                                <a href="${ctxFront}/view/index" target="mainFrame" class="cur"><span id="spanId">控制台首页</span></a>
                                <a href="${ctxFront}/view/account" target="mainFrame">应用管理</a>
                            </div>
                        </li>
                        <li>
                            <h2>短信模板</h2>
                            <div class="nav2">
                                <a href="${ctxFront}/view/listTemplate" target="mainFrame" >模板列表</a>
                                <a href="${ctxFront}/view/addTemplate" target="mainFrame" >新增模板</a>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="con" id="m2Div" style="display: none;">
  				<div class="nr" id="selectDiv1" style="display:block;">
    				<ul class="listnav">
				      	<li><h2>充值</h2>
				      	    <div class="nav2"><a href="${ctxFront}/view/payNow" target="mainFrame" class="cur"><span id="spanId">立即充值</span></a></div>
				      		<div class="nav2"><a href="${ctxFront}/user/rechargeList" target="mainFrame">充值记录</a></div>
				      	     
				      	</li>
				      	 <li>
                            <h2>发票信息</h2>
                            <div class="nav2">
                                <a href="${ctxFront}/view/invoice" target="mainFrame" >索取发票</a>
                                <a href="${ctxFront}/view/invoiceList" target="mainFrame" >开票记录</a>
                            </div>
                        </li>
    				</ul>
  				</div>
			</div>
			<div class="con" id="m6Div" style="display: none;">
  				<div class="nr" id="selectDiv1" style="display:block;">
    				<ul class="listnav">
      					<li><h2>消费详单</h2>
      					<div class="nav2"><a href="${ctxFront}/view/smsDayAccidReport" target="mainFrame" class="cur"><span id="spanId">日应用消费统计</span></a></div>
				      	<div class="nav2"><a href="${ctxFront}/view/smsDayReport" target="mainFrame">日消费统计</a></div>
				      	<div class="nav2"><a href="${ctxFront}/view/smsSendList" target="mainFrame">短信发送记录</a></div>
				      	<div class="nav2"><a href="${ctxFront}/view/smsReceiveList" target="mainFrame">上行短信记录</a></div>
				      	</li>
				    </ul>
				</div>
			</div>
			<div class="con" id="m3Div" style="display: none;">
  				<div class="nr" id="selectDiv1" style="display:block;">
    				<ul class="listnav">
				      	<li>
					        <h2>帐号管理</h2>
					      	<div class="nav2">
					      	<a href="${ctxFront}/view/smsUserInfo" target="mainFrame" class="cur"><span id="spanId">基础资料</span></a>
					      	</div>
					    </li>
    				</ul>
  				</div>
			</div>
        </div>
        <div class="right">
        	<iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="1191"></iframe>
        </div>
    </div>
    <div class="footer">
        <p><span class="copy_con">© Copyright ${fns:getConfig('copyrightYear')} ${fns:getConfig('productName')}&nbsp;&nbsp;${fns:getConfig('archival.info')}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;All Rights Reserved ${fns:getConfig('company.info')}</span><span class="contact"><em class="con_info">在线咨询：</em>
	<a href="#" target="_blank" class="zxqq"></a>
	<em class="con_info">客服电话：</em><i class="tel">${fns:getConfig('company.phone')}</i></span></p>
    </div>
</div>
</body>
</html>