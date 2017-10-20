<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>充值详单</title>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/main.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.colorbox.js"></script>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/colorbox.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/style.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/commonstyle.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/chongzhi_detail.css" type="text/css" />
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.watermark.min.js"></script>
    <script type="text/javascript">
        $(function(){

            $("#rechargeId").watermark("请输入订单号");

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
            $('.th_list li:even').css({background:'#f9f9f9'});
        });
        /*
         function overPay(){
         alert("超过订单支付时间");
         return false;
         }*/
    </script>
</head>
<body>
<div class="wrap">
    <script charset="utf-8" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODA1MDYzMV8yMDY2MTFfNDAwNjEwMTAxOV8"></script>
    <script type="text/javascript">
        BizQQWPA.addCustom([
            {aty: '0', a: '0', nameAccount: 4006101019, selector: 'BizQQWPA1'},
            {aty: '0', a: '0', nameAccount: 4006101019, selector: 'BizQQWPA2'},
            {aty: '0', a: '0', nameAccount: 4006101019, selector: 'BizQQWPA3'}
        ]);
    </script>

    <div class="container clearfix">
        <div class="right">
            <div class="tit">
                <h1>充值记录</h1>
            </div>
            <form action="${ctxFront}/user/rechargeList" name="recordForm" id="recordForm" method="post">
                <div class="div32">
                    <dl class="clearfix">
                        <dt>开始结束时间：</dt>
                        <dd>
                               	<input type="text" class="txt4" id="sTime" name ="sTime" value="2017-07-23" onClick="WdatePicker({dateFmt: 'yyyy-MM-dd',isShowClear:false,readOnly:true,maxDate:'#F{$dp.$D(\'eTime\',{d:-1})}'})" readonly="readonly"/>
    							<i class="hr"></i>
    							<input type="text" class="txt4" id="eTime" name="eTime" value="2017-08-22" onClick="WdatePicker({dateFmt: 'yyyy-MM-dd',isShowClear:false,readOnly:true,minDate:'#F{$dp.$D(\'sTime\',{d:1})}'})" readonly="readonly"/>
    							<input type="submit" value="查 询" class="btn_search" />
                        </dd>
                    </dl>
                </div>
                <div class="div33">

                </div>
                <div class="div34">
                    <div class="thDiv">
                        <span class="t1">订单号</span>
                        <span class="t2">生成时间</span>
                        <span class="t3">充值方式</span>
                        <span class="t4">金额(元)</span>
                        <span class="t5">状态</span>
                    </div>
                    <ul class="th_list clearfix">
                    	<c:forEach items="${page.list}" var="item" >
                        <li>
                            <span class="t1"><a href="${ctxFront}/user/orderDetail?payid=${item.payid}" target="_blank">${item.payid}</a></span>
                            <span class="t2"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </span>
                            <span class="t3">${item.payMent eq 1 ? '支付宝' : '微信'}</span>
                            <span class="t4"><fmt:formatNumber value="${item.money/100}" pattern="0.00" maxFractionDigits="2"/></span>
                            <span class="t5">
                            <c:if test="${item.orderStatus eq '-1'}">
                            	<a href="${ctxFront}/user/orderDetail?payid=${item.payid}" title="点击支付">未支付</a>
                            </c:if>
                             <c:if test="${item.orderStatus eq '1'}">
                            	支付成功
                            </c:if>
                            <c:if test="${item.orderStatus eq '0'}">
                            	支付失败
                            </c:if>
                            </span>
                        </li>
						</c:forEach>
                    </ul>
                    
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>