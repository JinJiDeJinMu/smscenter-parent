<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>云通讯会员注册</title>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/main.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/countCode.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/sub_menu.js"></script>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/style.css" type="text/css"/>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/common_v3.css" type="text/css"/>
    <link href="${ctxFrontStatic }/css/guide_zc_v3.css" type="text/css" rel="stylesheet"/>
    <script src="${ctxFrontStatic }/js/guide_zc.js"></script>
    <script src="${ctxFrontStatic }/js/layer/layer.min.js"></script>
    <script type="text/javaScript" src="${ctxFrontStatic }/js/passwordStrength.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/security.js"></script>
     <script type="text/javascript">
        var ctx = "";
    </script>
    <script type="text/javascript">
        var InterValObj; //timer变量，控制时间
        var count = 60; //间隔函数，1秒执行
        var reCount = 5;//跳转
        var curCount;//当前剩余秒数
        var flag = 0;//标识
        var validate;
        function fn_focus(ele) {
            if (ele.value == ele.defaultValue) {
                ele.value = '';
                ele.style.color = 'rgb(51,51,51)';
            }
        }
        function fn_blur(ele) {
            var reg = /^[\s]*$/;
            if (reg.test(ele.value) || ele.value == ele.defaultValue) {
                ele.value = ele.defaultValue;
                ele.style.color = 'rgb(200,200,200)';
            }
        }

        $(document).ready(function () {
            $("#PWD").focus(function () {
                $(this).hide();
                $("#userPwd").val("").show().css("backgroundColor", "").focus();
            });
            $("#userPwd").blur(function () {
                $(this).show().css("backgroundColor", "");
                $("#PWD").hide();
            });
            $('#userPwd').passwordStrength();
            $("#userPwd").val("");

            $("#regForm").validate({
                errorElement: "span",
                errorClass: "error_error",
                rules: {
                    email: {
                        required: true,
                        email: true
                        //checkValidEmail: true
                    },
                    userPwd: {
                        required: true,
                        rangelength: [6, 12]
                    },
                    mobile: {
                        required: true,
                        isMobile: true
                    }
                },
                messages: {
                    email: {
                        required: "<em class='erroricon'></em><em class='error'>邮箱不能为空</em>",
                        email: " <em class='erroricon'></em><em class='error'>请输入正确格式的邮箱</em>",
                        //checkValidEmail: "<em class='erroricon'></em><em class='error'>请输入有效的邮箱</em>"
                    },
                    userPwd: {
                        required: "<em class='erroricon'></em><em class='error'>密码不能为空</em>",
                        rangelength: "<em class='erroricon'></em><em class='error'>密码长度为6~12位</em>"
                    },
                    mobile: {
                        required: "<em class='erroricon'></em><em class='error'>手机号码不能为空</em>",
                        isMobile: "<em class='erroricon'></em><em class='error'>请填写正确的手机号码</em>"
                    }
                },
                errorPlacement: function (error, element) {
                    error.appendTo(element.parent());
                }
            });
            
            $("#submitButton").click(function () {
            	
                var formVerifyCode = $("#verifyCode").val();
                if(!formVerifyCode){
                	alert("请输入验证码");
                	return;
                }
            	
                var formEmail = $("#email").val();
                var formMobile = $("#mobile").val();
                var formUserPwd = $("#userPwd").val();
                $("#submitButton").html("注册中...");
                $.ajax({
                    type: "POST",
                    url: "${ctxFront}/reg/register",
                    data: {
                        "email": formEmail,
                        "mobile": formMobile,
                        "newPassword": formUserPwd,
                        "verifyCode": formVerifyCode
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 0) {
                            $('.step22').parent().animate({marginLeft: '-488px'});
                            curCount = reCount;
                            InterValObj = window.setInterval(setRedirectTime, 1000); //启动计时器，1秒执行一次
                            sendStep = 1;
                        } else {
                        	alert(data.msg);
                        	$("#submitButton").html("完成");
                            flag = 0;
                            /**
                            $('.second').remove();
                            $('.resend').remove();
                            $("#yzmTime").append('<a href="javascript:void(0)" class="resend" onclick="sendSmsBtn_real()">重新发送</a>');
                            **/
                            
                        }
                    }
                });
            });

        });
        //注册成功返回代码
        function mediavCount(memname, muser) {
            var _mvq = window._mvq || [];
            window._mvq = _mvq;
            _mvq.push(['$setAccount', 'm-40585-0']);
            _mvq.push(['$setGeneral', 'registered', '', memname, muser]);
            _mvq.push(['$logConversion']);
        }

        var sendStep = 1;
        /**
        function sendSmsBtn() {
            $.layer({
                type: 2,
                title: false,
                shade: [0.8, '#000'],
                shadeClose: false,
                maxmin: false,
                fix: true,
                area: ['336px', '216px'],
                iframe: {
                    src: '/pop_code.html'
                },
                end: function () {
                    if ($("#numberCode").val() != null && $("#numberCode").val() != "") {
                        sendSmsBtn_real();
                    }
                }
            });
        }**/
        function sendSmsBtn_real() {
            var mobile = $("#mobile").val();
            if (mobile != '') {
                $.ajax({
                    type: "POST",
                    url: "${ctxFront}/reg/verifyCode",
                    data: {
                        "mobile": mobile
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 0) {
                            $('.resend').remove();
                            //滑动
                            $('.zcstep2').find('p.tips').remove();
                            $('.step11').parent().animate({marginLeft: '-488px'});
                            $(".zcstep2").prepend('<p class="tips" >云通讯已向 <em class="red">' + mobile + '</em> 发送了验证码<br/>，请注意接收短信</p>');
                            curCount = count;
                            InterValObj = window.setInterval(setRemainTime, 1000);
                            sendStep = 2;
                        } else {
                            alert(data.msg);
                        }
                    }
                });
            } else {
                alert("手机号码不能为空");
                return false;
            }
        }
        function sendVoice() {
            if ($("#regForm").valid()) {
            	sendVoice_real();
            }
        }
        function sendVoice_real() {
        	
            var mobile = $("#mobile").val();
            $("#sendStep1").removeAttr("onclick");
            if (mobile != '') {
                $.ajax({
                    type: "POST",
                    url: "${ctxFront}/reg/verifyCode",
                    data: {
                        "mobile": mobile
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 0) {
                            //滑动
                            $('.step11').parent().animate({marginLeft: '-488px'});
                            $(".zcstep2").prepend('<p class="tips" >云通讯已向 <em class="red">' + mobile + '</em> 发送了验证码，请注意接收短信<br/></p>');
                            curCount = count;
                            InterValObj = window.setInterval(setRemainTime, 1000); //启动计时器，1秒执行一次
                        } else {
                            $("#sendStep1").attr({"onclick": "sendVoice()"});
                            alert(data.msg);
                        }
                    }
                });
            } else {
                alert("手机号码不能为空!");
                return false;
            }
        }
        //timer处理函数
        function setRemainTime() {
            if (curCount == 0) {
                window.clearInterval(InterValObj);//停止计时器
                $('.second').remove();
                $('.resend').remove();
                $("#yzmTime").append('<a href="javascript:void(0)" class="resend" onclick="sendSmsBtn_real()">重新发送</a>');
            } else {
                curCount--;
                $('.second').remove();
                $('.resend').remove();
                $("#yzmTime").append('<span class="second"><em class="num">' + curCount + '</em>s</span>');
            }
        }
        //跳转
        function setRedirectTime() {
            if (curCount == 0) {
                window.clearInterval(InterValObj);//停止计时器
                window.location.href = "${ctxFront}/view/login";
            } else {
                curCount--;
                $("#thirdRedirect").html("" + curCount);
            }
        }


    </script>
    
</head>
<body>
<div class="registerwrap">
    <div class="zccontent">
        <div class="zccon_l">
            <h2 class="title"></h2>
            <h3 class="stitle">省时、省力、省钱！</h3>
            <p class="con">云通讯™ 致力于让通讯能力"零门槛"、"低资费"、"轻松按需取用"</p>
            <p class="cons">已有云通讯帐号？<a href="${ctxFront }/view/login">立即登录 >></a></p>
        </div>
        <div class="zccon_r">
            <div class="zcconwrap">
                <form action="" name="regForm" id="regForm" method="post">
                    <div class="zcstep1">
                        <ul class="zclist">
                            <li>
                                <div class="txtbox">
                                    <label class="label">手 机</label>
                                    <span class="line"></span>
                                    <input type="text" class="txt" name="mobile" id="mobile"/>
                                    <em class="icon"></em>
                                </div>
                            </li>
                            <li>
                                <div class="txtbox">
                                    <label class="label">邮 箱</label>
                                    <span class="line"></span>
                                    <input type="text" name="email" id="email" class="txt" value=""/>
                                    <em class="icon"></em>
                                </div>
                            </li>
                            <li>
                                <div class="txtbox">
                                    <label class="label">密 码</label>
                                    <span class="line"></span>
                                    <input id="PWD" type="text" class="txt" value="请输入密码" style="color:transparent;"/>
                                    <input type="password" name="userPwd" id="userPwd" class="txt"
                                           style="zdisplay:none"/>
                                    <em class="icon"></em>
                                </div>

                            </li>
                        </ul>
                        <a href="javascript:;" class="subbtn step11" onclick="sendVoice()" id="sendStep1">下一步</a>
                    </div>
                    <div class="zcstep2">
                        <div class="listwrap" id="yzmTime">
                            <ul class="zclist">
                                <li>
                                    <div class="txtbox">
                                        <label class="label">验证码</label>
                                        <span class="line"></span>
                                        <input type="text" class="txt" name="verifyCode" id="verifyCode"/>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <a href="javascript:void(0)" class="subbtn step22" id="submitButton">完成</a>
                    </div>
                    <div class="zcstep3">
                        <div class="imgbox"></div>
                        <p class="tips"><span class="num" id="thirdRedirect">3</span>秒后自动跳转到 <a href="${ctxFront }/view/login" class="link">进入云通讯登录页面</a></p>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%--<div class="zcfooter">
        <div class="footerimg"></div>
    </div>--%>
</div>
</body>

</html>