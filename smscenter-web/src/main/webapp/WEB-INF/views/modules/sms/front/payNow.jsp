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
    <script type="text/javascript" src="${ctxFrontStatic }/js/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.colorbox.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/member.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/countCode.js"></script>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/colorbox.css" type="text/css"/>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/style.css" type="text/css"/>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/commonstyle.css" type="text/css"/>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/chongzhi.css" type="text/css"/>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/price_v3.css" type="text/css"/>
    
     <script type="text/javascript">
        $(function () {
        	$.ajax({
       			type: "POST",
                url:"${ctxFront}/user/balance",
                dataType: "json",
                success: function(result){
              		$('.count').html(result.data);
                },
                error:function(){
        			alert("系统错误");
        		 }
           	});
        	
            function setHeight() {
                var nHeight = $('.container').height() + 'px';
                var brsHeight = $(window).height() - 176 + 'px';
                var mainHeight = parseInt(brsHeight) + 'px';
                if (parseInt(nHeight) < parseInt(brsHeight)) {
                    $('.left,.left .tabs').css({'height': mainHeight});
                } else if (parseInt(nHeight) >= parseInt(brsHeight)) {
                    $('.left,.left .tabs').css({'height': nHeight});
                }
            }

            window.onload = function () {
                setHeight();
            };
            window.onresize = function () {
                setHeight();
            }
            $('.step3 dl:even,.step4 dl:even').css({background: '#f9f9f9'});
            $('.cz').colorbox({inline: true});

            //单选框
            $('.radio').each(function () {
                if ($(this).is(':checked')) {
                    $(this).parent().addClass('radio-off-on');
                } else {
                    $(this).parent().removeClass('radio-off-on');
                }
            });
            $('.radio').hover(function () {
                $(this).parent().addClass('radio-off-hover');
            }, function () {
                $(this).parent().removeClass('radio-off-hover');
            });
            $('.radio').click(function () {
                //如果是微信支付方式，则增加页面的控制
                
                /**
                if ($(this).val() == "2") {
                    //显示微信入口图片，隐藏下一步
                    $("#btn-onclick1").hide();
                    $(".paycode").show();
                } else {
                    $("#btn-onclick1").show();
                    $(".paycode").hide();
                }**/
                $('.radio').parent().removeClass('radio-off-on');
                $(this).parent().addClass('radio-off-on');
            });
            /*锚点加运动*/
            $('a[href*=#]').click(function () {
                if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
                    var $target = $(this.hash);
                    $target = $target.length && $target || $('[name=' + this.hash.slice(1) + ']');
                    if ($target.length) {
                        var targetOffset = $target.offset().top;
                        $('html,body').animate({
                                scrollTop: targetOffset
                            },
                            1000);
                        return false;
                    }
                }
            });

        });
        function fn_focus(ele) {
            var reg = /^[\s]*$/;
            ele.style.border = 'solid 1px rgb(2,185,239)';
            if (ele.value == ele.defaultValue) {
                ele.value = '';
                ele.style.color = 'rgb(51,51,51)';
            }
        }
        function fn_blur(ele) {
            var reg = /^[\s]*$/;
            ele.style.border = 'solid 1px rgb(213,213,213)';
            if (reg.test(ele.value) || ele.value == ele.defaultValue) {
                ele.value = ele.defaultValue;
                ele.style.color = 'rgb(159,159,159)';
            }
        }
    </script>


    <script type="text/javascript">
        $(document).ready(function () {
            $("#creditForm").validate({
                errorElement: "span",
                errorClass: "error",
                rules: {
                    credit: {
                        required: true,
                        isMoney: true,
                        min: 0.01
                    },
                    payMent: {
                        required: true
                    }
                },
                messages: {
                    credit: {
                        required: "充值金额不能为空",
                        //min:jQuery.format("充值金额不能小于{0}")
                        min: $.validator.format("充值金额不能小于{0}")
                    },
                    payMent: {
                        required: "请选择支付方式"
                    }
                },
                errorPlacement: function (error, element) {
                	
                    if (element.attr("name") == "credit") {
                        error.appendTo(element.parent());
                    } else {
                    	//alert("请选择支付方式");
                        error.appendTo('#divChannel');
                    }
                }

            });
            $('#btn-onclick1').click(function () {
                $('#creditForm').submit();
            });
        });
        
        //设置金额
        function initCredit(money){
        	if(money > 0){
        		$("#credit").val(money);
        	}else{
        		$("#credit").val(null);
        	}
        	$("#credit").focus();
        }
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
                <h1>在线充值</h1>
            </div>
            <div class="tx_div">
                <h3>充值说明：</h3>
                <p>1、我们提供在线充值服务（支付宝、银联、微信）及<a href="#notice"
                                              style="color:#44caf9; text-decoration:underline;">银行对公汇款</a><br/>
                    2、请尽量保障账户余额大于您在资费配置内设置的保底套餐额度，避免因余额不足造成业务中断
                </p>
            </div>
            
            <div style="width: 100%;height: 108px;margin-top: 20px">
	    		<div>
		        <ul class="duanxin_list">
		            <li>
		                <a href="javascript:initCredit(30000)" target="_blank">
		                    <ul class="kuang-2" style="width: 180px">
		
		                    </ul>
		                </a>
		            </li>
		            <li><a href="javascript:initCredit(10000)" target="_blank">
		                <ul class="kuang-3" style="width: 180px">
		
		                </ul>
		            </a>
		            </li>
		            <li><a href="javascript:initCredit(5000)" target="_blank">
		                <ul class="kuang-4" style="width: 180px">
		
		                </ul>
		            </a>
		            </li>
		            <li class="mr">
		                <a href="javascript:initCredit(0)" target="_blank">
		                    <ul class="kuang-5" style="width: 180px">
		
		                    </ul>
		                </a>
		            </li>
		        </ul>
		   		</div>
			</div>
            
            
            <div class="process clearfix">
                <span class="on"><i>1</i><em>输入金额</em></span>
                <span class="w22"><i>2</i><em>选择充值方式</em></span>
                <span><i>3</i><em>确认订单</em></span>
                <span><i>4</i><em>进行付款</em></span>
                <span class="w18"><i>5</i><em>完成</em></span>
            </div>
            <form method="post" action="${ctxFront}/user/recharge" id="creditForm" name="creditForm">
                <input type="hidden" name="activityId" value="">
                <div class="step1">
                    <h2><i>1</i>输入金额</h2>
                    <div class="s11">
                        <div class="s11_l" style="position:relative;"><span class="base_tit">充值金额：</span>
                            <input type="text" class="txt3" name="credit" id="credit" maxlength="7"
                                   onfocus="fn_focus(this);" onblur="fn_blur(this);" value=""/><em>元</em></div>
                        <div class="s11_r"><span class="img"> <img src="${ctxFrontStatic }/images/img_s2.png"/><em>剩余条数</em> </span>
                            <em class="count">8.00&nbsp;</em></div>
                    </div>

                </div>
                <div class="step2">
                    <h2><i>2</i>选择充值方式</h2><span id="divChannel"></span>
                    <ul>
                        <li>
                            <div class="radio-off"><input type="radio" name="payMent" class="radio" value="1"/></div>
                            <label><img src="${ctxFrontStatic }/images/pay_m1.jpg"/></label>
                       	</li>
                        <li>
                            <div class="radio-off"><input type="radio" name="payMent" class="radio" value="2"/></div>
                            <label><img src="${ctxFrontStatic }/images/pay_m4.jpg"/></label>
                       	</li>
                    </ul>
                    <a href="javascript:void(0);" class="next c2" id="btn-onclick1">下一步</a>
                    <div class="paycode">
                        <img src="${ctxFrontStatic }/images/paycode_img.png" class="img"/>
                        <em class="info">扫码快速充值</em>
                    </div>
                    <div class="notice" id="notice">
                        <div class="tit">
                            <h1>银行对公汇款</h1>
                        </div>
                        <!--<h3><i>& </i><span></span></h3> -->
                        <p>汇款时请在备注中写明您在xxxxxxx的认证名，以便入账；<br/>到账时间取决于汇款行与开户行间的结算，正常为1个工作日，若有疑问请联系客户经理或客服。</p>
                        <div class="money">
                            <p class="p1">
                                开户名称：xxxxxxxxx有限公司<br/>
                                收款账号：xxxxxxxxxx <br/>
                                开户银行：xxxxxxxxxxxx支行
                            </p>
                            <div class="chat"><img src="${ctxFrontStatic }/images/cz_code.png" class="img"/><em
                                    class="font">关注服务号</em><em class="font1">汇款到账早知道</em></div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>