var projectName ="";
var path="";
var reg=/\/$/gi;
var flag=true;
var referrer=document.referrer;

function getRefer(){
	return referrer;
}
function getRootPath_web() {	
	if(path!=""){
		return path;
	}
	if (!window.location.origin) {
		window.location.origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port: '');
	}
	path=location.origin;
    path=path+projectName;
    return path;
}
function include(path){ 
    var a=document.createElement("script");
    a.type = "text/javascript"; 
    a.src=getRootPath_web()+"/"+path; 
    var head=document.getElementsByTagName("head")[0];
    head.appendChild(a);
}
var topHtml='<div class="top">'+
'<div class="header">'+
'<div class="logo"><a href="'+getRootPath_web()+'/index.html" class="logoimg"></a></div>'+ 
'<ul class="navlist">'+
	'<li class=""><a href="'+getRootPath_web()+'/index.html" class="link"><span class="name">首页</span></a></li>'+
    '<li t_nav="produce"><a href="javascript:void(0)" class="link default"><span class="name">服务与产品</span></a></li>'+
    '<li><a href="'+getRootPath_web()+'/price/price.html" class="link" target="_blank"><span class="name">价格</span></a></li>'+
    '<li><a href="'+getRootPath_web()+'/taste/center.html" class="link"><span class="name">免费体验</span></a></li>'+
    '<li><a href="'+getRootPath_web()+'/doc.html" class="link"><span class="name">开发者中心</span></a></li>'+
'</ul>'+
'<div class="login_box"><a href="'+getRootPath_web()+'/user/login" class="loginbtn" id="loginbtn">登录</a><a href="'+getRootPath_web()+'/user/reg/init" class="zhuce" target="_blank" id="zhuce">注册</a></div>'+
'</div>'+
'<div class="navigation_down">'+
'<div class="child_nav" t_nav="produce" id="produce">'+
	'<div class="content_inner">'+
    	'<div class="dl1">'+
        	'<h2 class="title">服  务</h2>'+
            '<div class="servicelist">'+
            	'<a href="'+getRootPath_web()+'/api/voice.html" class="link a1"><em class="icon"></em><span class="name">通 话</span></a>'+
                '<a href="'+getRootPath_web()+'/api/sms.html" class="link a2"><em class="icon"></em><span class="name">短 信</span></a>'+
                '<a href="'+getRootPath_web()+'/api/cc.html" class="link a6"><em class="icon"></em><span class="name">呼叫中心</span></a>'+
                '<a href="'+getRootPath_web()+'/api/flow.html" class="link a5"><em class="icon"></em><span class="name">流 量</span></a>'+
                '<a href="'+getRootPath_web()+'/api/im" class="link a3"><em class="icon"></em><span class="name">IM Plus</span></a>'+
                '<a href="'+getRootPath_web()+'/api/video.html" class="link a4"><em class="icon"></em><span class="name">视 频</span></a>'+
            '</div>'+
        '</div>'+
        '<div class="dl2">'+
        	'<h2 class="title">产  品</h2>'+
            '<div class="producewrap">'+
            	'<div class="telbox">'+
                	'<h3 class="stitle">< 电 话 类 ></h3>'+
                    '<ul class="tellist">'+
                        '<li><em class="dot">·</em><a href="'+getRootPath_web()+'/product/nimingtonghua.html" class="link">号码卫士</a></li>'+
                        '<li><em class="dot">·</em><a href="'+getRootPath_web()+'/product/tonghuaqianming.html" class="link">通话签名</a></li>'+
                        '<li><em class="dot">·</em><a href="'+getRootPath_web()+'/product/wangyehuihu.html" class="link">网页回呼</a></li>'+
                    	'<li><em class="dot">·</em><a href="'+getRootPath_web()+'/product/dianjibohao.html" class="link">点击通话</a></li>'+                        
                       /* '<li><em class="dot">·</em><a href="'+getRootPath_web()+'/solution/ipcall" class="link">网络电话平台</a></li>'+*/
                    '</ul>'+
                '</div>'+
                '<div class="noticebox">'+
                	'<h3 class="stitle">< 通 知 类 ></h3>'+
                    '<ul class="noticelist">'+
                        '<li><em class="dot">·</em><a href="'+getRootPath_web()+'/product/smscode.html" class="link">短信验证码</a></li>'+                        
                    	'<li class="relative"><em class="dot">·</em><a href="'+getRootPath_web()+'/product/yuyinyanzhengma.html" class="link">语音验证码</a><em class="posred red1">活动中</em></li>'+
                        '<li><em class="dot">·</em><a href="'+getRootPath_web()+'/product/yuyintongzhi.html" class="link">语音通知</a></li>'+
                        '<li><em class="dot">·</em><a href="'+getRootPath_web()+'/product/guajiduanxin.html" class="link">挂机短信</a></li>'+                        
                    '</ul>'+
                '</div>'+
                '<div class="gtbox">'+
                	'<h3 class="stitle">< 沟 通 类 ></h3>'+
                    '<ul class="gtlist">'+
                    	'<li class="relative"><em class="dot">·</em><a href="'+getRootPath_web()+'/api/cc.html" class="link">云呼叫中心</a><em class="posgreen green1">推荐</em></li>'+
                       /* '<li><em class="dot">·</em><a href="'+getRootPath_web()+'/solution/video_cc" class="link">视频呼叫中心</a></li>'+*/
                        '<li><em class="dot">·</em><a href="'+getRootPath_web()+'/solution/UC" class="link">容信-企业IM</a></li>'+
                        /*'<li><em class="dot">·</em><a href="#" class="link">容 信 - 企业微信</a></li>'+*/
                    '</ul>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="dl3">'+
        	'<h2 class="title">行  业</h2>'+
            '<ul class="tradelist">'+
                '<li><em class="dot">·</em><a href="'+getRootPath_web()+'/solution/lvyou.html" class="link">在线旅游</a></li>'+
                '<li><em class="dot">·</em><a href="'+getRootPath_web()+'/solution/dianshang.html" class="link">电 商</a></li>'+
                '<li><em class="dot">·</em><a href="'+getRootPath_web()+'/solution/fangdichan.html" class="link">房地产</a></li>'+
                '<li><em class="dot">·</em><a href="'+getRootPath_web()+'/solution/kuaidi.html" class="link">快递物流</a></li>'+
                '<li><em class="dot">·</em><a href="'+getRootPath_web()+'/solution/zixun.html" class="link">咨 询</a></li>'+
                '<li><em class="dot">·</em><a href="'+getRootPath_web()+'/solution/chuxing.html" class="link">出 行</a></li>'+
            '</ul>'+
        '</div>'+
    '</div>'+
'</div>'+
'</div>'+
'</div>';

//控制登陆注册显示
$(function(){
	$.ajax({
		type : "POST",
		url : getRootPath_web()+"/subsystem/checkSession",
		dataType : "json",
		success : function(data) {
			if(data.flag){
				$('.login_box .loginbtn').remove();
				$('.login_box .zhuce').remove();
				$('.login_box').html('<div class="login_con"><a href="'+getRootPath_web()+'/member/main" class="control_btn" target="_blank">控制台</a><a href="'+getRootPath_web()+'/user/logout" class="exit">退出</a></div>'+
						'</div>');
			}
		}
	});
$(".pagetop").html(topHtml);

if(typeof(topFlag)!="undefined"){
//控制页头下划线
if(topFlag == 'index'){
//	alert("首页");
	$('.navlist li:eq(0)').attr('class','current');
}
if(topFlag == 'service'){
//	alert("服务与产品");
	$('.navlist li:eq(1)').attr('class','current');
}
if(topFlag == 'price'){
//	alert("价格");
	$('.navlist li:eq(2)').attr('class','current');
}
if(topFlag == 'freeExp'){
//	alert("免费体验");
	$('.navlist li:eq(3)').attr('class','current');
}
if(topFlag == 'devCenter'){
//	alert("开发者中心");
	$('.navlist li:eq(4)').attr('class','current');
}
if(topFlag=='' || topFlag==undefined || topFlag==null){
//	alert("你没有传参哦");
	$('.navlist li:eq(0)').attr('class','current');
}
}
});
include("js/countCode.js");
