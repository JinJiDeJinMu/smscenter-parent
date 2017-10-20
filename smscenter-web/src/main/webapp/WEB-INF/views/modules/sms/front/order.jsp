<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>充值</title>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/main.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.colorbox.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/member.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/countCode.js"></script>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/colorbox.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/style.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/commonstyle.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/chongzhi.css" type="text/css" />
    <script type="text/javascript">
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
            $('.step3 dl:even,.step4 dl:even').css({background:'#f9f9f9'});
            $('.cz').colorbox({inline:true});

            //单选框
            $('.radio').each(function(){
                if($(this).is(':checked')){
                    $(this).parent().addClass('radio-off-on');
                }else{
                    $(this).parent().removeClass('radio-off-on');
                }
            });
            $('.radio').hover(function(){
                $(this).parent().addClass('radio-off-hover');
            },function(){
                $(this).parent().removeClass('radio-off-hover');
            });
            $('.radio').click(function(){
                $('.radio').parent().removeClass('radio-off-on');
                $(this).parent().addClass('radio-off-on');
            });

        });
        function fn_focus(ele){
            var reg = /^[\s]*$/;
            ele.style.border = 'solid 1px rgb(2,185,239)';
            if(ele.value == ele.defaultValue){
                ele.value = '';
                ele.style.color = 'rgb(51,51,51)';
            }
        }
        function fn_blur(ele){
            var reg = /^[\s]*$/;
            ele.style.border = 'solid 1px rgb(213,213,213)';
            if( reg.test(ele.value) || ele.value == ele.defaultValue){
                ele.value = ele.defaultValue;
                ele.style.color = 'rgb(159,159,159)';
            }
        }
    </script>

    <script type="text/javascript">
        $(document).ready(function() {
            $('#rechargeBtn').click(function(){
                //$.colorbox({href:"/member/acc/showTip"});
                $('#orderForm').submit();
            });
        });
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
                <h1>充值</h1>
            </div>
            <div class="process clearfix">
                <span ><i>1</i><em>输入金额</em></span>
                <span class="w22"><i>2</i><em>选择付款方式</em></span>
                <span class="on" ><i>3</i><em>确认订单</em></span>
                <span><i>4</i><em>进行付款</em></span>
                <span class="w18"><i>5</i><em>完成</em></span>
            </div>


            <form method="post" action="${ctxFront }/user/callBackByPay" id="orderForm" name="orderForm" target="_blank">
                <div class="step3">
                    <h2><i>3</i>确认订单</h2>
                    <div class="s31">
                        <dl>
                            <dt>订单号:</dt><dd>${item.payid}</dd>
                        </dl>
                        <dl>
                            <dt>付款金额:</dt><dd><em><fmt:formatNumber type="number" value="${item.money/100}" maxFractionDigits="2" pattern="0.00"/></em>元</dd>
                        </dl>
                        <dl>
                            <dt>付款方式:</dt><dd><em>${item.payMent eq 1 ? '支付宝' : '微信' }</em></dd>
                        </dl>
                        <dl>
                            <dt>订单状态:</dt><dd><span>${item.orderStatus eq -1 ? '未完成支付' : '完成支付'}</span></dd>
                        </dl>
                    </div>

                    <input type="hidden" name="payid" id="payid" value="${item.payid}"/>

                    <!-- <a href="javascript:void(0);" class="next c2 cz" id="rechargeBtn" name="rechargeBtn">付款</a> -->


                </div>
            </form>


        </div>
    </div>


  
</div>

</body>
</html>