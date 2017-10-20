<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录—云通讯平台</title>
<script type="text/javascript" src="${ctxFrontStatic }/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctxFrontStatic }/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctxFrontStatic }/js/main.js"></script>
<link rel="stylesheet" href="${ctxFrontStatic }/css/style.css" type="text/css" />
<link rel="stylesheet" href="${ctxFrontStatic }/css/common_v3.css" type="text/css" />

<link rel="stylesheet" href="${ctxFrontStatic }/css/zhuce.css" type="text/css"/>
<script type="text/javascript" src="${ctxFrontStatic }/js/base64.js"></script>
<script type="text/javascript" src="${ctxFrontStatic }/js/jquery.md5.js"></script>
<script type="text/javascript">
//var loadtime = parseInt(new Date().getTime());
function fn_focus(ele){
	if(ele.value == ele.defaultValue){
		ele.value = '';
		ele.style.color = 'rgb(51,51,51)';
	}
}
function fn_blur(ele){
	var reg = /^[\s]*$/;
	if(reg.test(ele.value) || ele.value == ele.defaultValue){
		ele.value = ele.defaultValue;
		ele.style.color = 'rgb(200,200,200)';
	}
}
//var host = window.location.href.replace("/user/login","");
$(document).ready(function() {
	
		$("#PWD").focus(function(){ 
			$(this).hide(); 
			$("#loginPwd").val("").show().css("backgroundColor","#fff").focus(); 
		}); 
		$("#loginPwd").blur(function(){ 
			$(this).show().css("backgroundColor","#fff"); 
			$("#PWD").hide(); 
		}); 
		$("#UN").focus(function(){ 
			$(this).hide(); 
			$("#loginName").val("").show().css("backgroundColor","#fff").focus(); 
		}); 
		$("#loginName").blur(function(){ 
			$(this).show().css("backgroundColor","#fff"); 
			$("#UN").hide(); 
		}); 
	
	
	var msg = "";
	if(msg.length>0){
		alert(msg);
	}
	//固定底部位置
	var docHeight = $('body').height();
	$(window).load(function(){
		if( docHeight <=$(window).height()){
			$('.footerwrap').css({position:'absolute',bottom:'0'});
		}else{
			$('.footerwrap').css({position:'relative',bottom:'auto'});
		}
	});
	$(window).resize(function(){
		if(docHeight <= $(window).height()){
			$('.footerwrap').css({position:'absolute',bottom:'0'});
		}else{
			$('.footerwrap').css({position:'relative',bottom:'none'});
		}
	});
	
	$("#loginBtn").click(function() {
		var result = $("#loginForm").valid();
		var remeber="";
		if ($("#remeberMe").is(":checked")){
			remeber="on";
		}
		if(result){
			$("#loginBtn").attr("disabled","disabled"); 
			$("#loginBtn").val("登录中...");
			var pwd=$("#loginPwd").val();
			var loginName=$("#loginName").val();
			var j_captcha= "gc1e";
			var loginCode = $("#loginCode").val();
			if(loginCode !=""&&loginCode!=null&&loginCode!=undefined){
				j_captcha =loginCode;
			}
			$.ajax({
				type : "POST",
				url : "${ctx}/login",
				data:{username:$("#loginName").val(),
					password:pwd,
					loginCode:$("#loginCode").val(),
					remeberMe:remeber,preUrl:$("#goUrl").val()},
				dataType : "json",
				success : function(data) {
					if(data.code == 0){
						window.location.href = "${ctxFront}/view/main";
					}else{
						$("#loginBtn").removeAttr("disabled"); 
						$("#loginBtn").val("登  录");
						alert(data.message);
						if(data.errorCount>=3){
							if ( $("#loginCode").length <= 0 ) { 
								addRandCode();
								$("#loginCode").rules("add", { required: true,messages:{required: "<b>验证码不能为空</b>"} });
							}
						}
						refreshRandCode();
					}
				}
			});
		}else{
			return false;
		}
	});
	$("#loginForm").validate({
		errorElement:"span",
		errorClass: "error_error",
		rules: {
			/* loginName: {
				required: true,
				isEmailOrMobel: true
			}, */
			loginPwd: {
				required: true
			}
		},
		messages: {
			loginName: {
				required: "<b>邮件地址不能为空</b>",
				isEmailOrMobel:"<b>请输入正确的邮件或手机号</b>"
			},
			loginPwd: {
				required: "<b>密码不能为空</b>"
			}
		},
		errorPlacement : function(error, element) {
			error.appendTo(element.parent());
		}
	});
	if ( $("#loginCode").length > 0 ) {  
		$("#loginCode").rules("add", { required: true,messages:{required: "<b>验证码不能为空</b>"} });
	} 
	
	// 如果在框架或在对话框中，则弹出提示并跳转到首页
	if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
		alert('未登录或登录超时。请重新登录，谢谢！');
		top.location = "${ctxFront}";
	}
});
function addRandCode(){
	var randCodeDl="<dl>";
	randCodeDl=randCodeDl+"<dt><label>验证码：</label></dt>";
    randCodeDl=randCodeDl+'<dd><input type="text" class="txt" name="loginCode" id="loginCode"  onfocus="fn_focus(this);" onblur="fn_blur(this);"/><div class="checkdiv"><img src="images/a310de5561af4741bc49d2979cb76e42.gif" id="randCodeImg" name="randCodeImg" onclick="refreshRandCode();" /><a href="javascript:void(0);" onclick="refreshRandCode();">换一张</a></div></dd>';
	randCodeDl=randCodeDl+"</dl>";
	$("#remeberDl").before(randCodeDl);
}
function refreshRandCode() {
	$('#randCodeImg').hide().attr('src','/jcaptcha?' + Math.floor(Math.random()*100)).fadeIn();
}
$(document).keyup(function(event){
	if(event.keyCode ==13){
		$("#loginBtn").trigger("click");
	}
});

</script>
</head>
<body>
<div class="wrap">
	<div class="top">
		<div class="header">
			<div class="logo">
				<a href="javascript:void(0);" class="logoimg"></a>
			</div>
		</div>
	</div>
  <div class="main">
    <div class="content1">
      <div class="c_left2">
        <div class="tit_div">
          <h1>登录</h1>
        </div>
        <div class="sign_div">
          <div class="step1">
            <div class="form">
              <form name="form" method="post" action="" id="loginForm">
            <input type="hidden" id="goUrl" value="${ctxFront }/view/main">
                <dl>
                  <dt>
                    <label for="regEmail">邮箱/手机：</label>
                  </dt>
                  <dd>
                  	<input id="UN"  type="text"  class="txt" value=""/> 
                    <input type="text" name="loginName" id="loginName" class="txt" value="" onfocus="fn_focus(this);" onblur="fn_blur(this);" style="display:none"/>
                   </dd>
                </dl>
                <dl>
                  <dt>
                    <label for="pwd">登录密码：</label>
                  </dt>
                  <dd>
                  	<input id="PWD" type="text" class="txt" value="请输入密码" style="color:#FFF"/>
                    <input type="password" name="loginPwd" id="loginPwd" class="txt" style="display:none"/>                    
                  </dd>
                </dl>
                 
               	<dl id="remeberDl">
                  <dt></dt>
                  <dd>
                    <input type="checkbox" class="checkbox" name="remeberMe" id="remeberMe" value="on" checked="checked"/>
                    <label for="remeberMe" class="ag">记住用户名</label><!-- <a href="/user/toFindPwd" class="pwd_btn">忘记密码？</a> -->
                </dl>
                <dl>
                  <dt></dt>
                  <dd>
                    <input type="button" id="loginBtn" value="登&nbsp;&nbsp;录" class="yj-btn-blue btn_submit"/>
                  </dd>
                </dl>
              </form>
            </div>
          </div>
        </div>
      </div>
      <div class="c_right">
          <p class="p1">还没有账号</p>
          <a href="${ctxFront}/reg/index" class="yj-btn btn-login">立即注册</a>
          <p class="p2">若 <a href="javascript:void(0);">忘记密码？</a>可以通过邮箱或手机找回</p>
          <ul class="tips">
              <li>开发者零门槛加入</li>
              <li>自助化服务</li>
              <li>快速集到现有产品</li>
              <li>新的通讯世界在这里等您</li>
          </ul>
      </div>
    </div>
  </div>
	<div class="footerwrap">
		<div class="copyright">
			<div class="copycon">
				<p class="con">© Copyright ${fns:getConfig('copyrightYear')} ${fns:getConfig('productName')} All Rights Reserved
					${fns:getConfig('company.info')}</p>
				<p class="con">
					<span>ICP备案：${fns:getConfig('archival.info')}</span>
				</p>
			</div>
		</div>
	</div>
 </div>
</body>
</html>