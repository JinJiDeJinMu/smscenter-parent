var footerHtml=
   '<div class="footerbox">'+
        '<div class="f1">'+
            '<h3 class="title">产品 & 解决方案</h3>'+
            '<div class="linklist">'+
            	'<div style="width:100%; overflow:hidden; margin-bottom:12px;">'+
                    '<a href="'+getRootPath_web()+'/api/cc.html" target="_blank">云呼叫中心</a>'+
                    '<a href="'+getRootPath_web()+'/api/sms.html" target="_blank">短信验证码</a>'+
                    '<a href="'+getRootPath_web()+'/product/dianjibohao.html" target="_blank">点击通话</a>'+
                    '<a href="'+getRootPath_web()+'/product/nimingtonghua.html" target="_blank">号码卫士</a>'+
                '</div>'+
                '<div style="width:100%; overflow:hidden; margin-bottom:12px;">'+
	                '<a href="'+getRootPath_web()+'/product/wangyehuihu.html" target="_blank">网页回呼</a>'+
                    '<a href="'+getRootPath_web()+'/product/yuyintongzhi.html" target="_blank">语音通知</a>'+
                    '<a href="'+getRootPath_web()+'/product/yuyinyanzhengma.html" target="_blank">语音验证码</a>'+
                    '<a href="'+getRootPath_web()+'/api/sms.html" target="_blank">短信通知</a>'+
                    '<a href="'+getRootPath_web()+'/api/flow.html" target="_blank">流量</a>'+
                '</div>'+
                '<div style="width:100%; overflow:hidden; margin-bottom:12px;">'+
               		'<a href="'+getRootPath_web()+'/api/video.html" target="_blank">视频</a>'+
                    '<a href="'+getRootPath_web()+'/solution/O2O" target="_blank">O2O运营工具包</a>'+
                    '<a href="'+getRootPath_web()+'/solution/UC" target="_blank">容信-企业IM</a>'+
                    '<a href="'+getRootPath_web()+'/api/im" target="_blank">IM 即时通讯</a>'+
               '</div>'+
               '<div style="width:100%; overflow:hidden; margin-bottom:12px;">'+
	          		'<a href="'+getRootPath_web()+'/product/tonghuaqianming.html" target="_blank">通话签名</a>'+
	               '<a href="'+getRootPath_web()+'/product/guajiduanxin.html" target="_blank">挂机短信</a>'+
	          '</div>'+ 
            '</div>'+
        '</div>'+
        '<div class="f2">'+
            '<h3 class="title">关于我们</h3>'+
            '<ul class="commonlist">'+
                '<li><a href="'+getRootPath_web()+'/aboutus" target="_blank">关于云通讯</a></li>'+
                '<li><a href="'+getRootPath_web()+'/job/weMet" target="_blank">招贤纳士</a></li>'+
                '<li><a href="'+getRootPath_web()+'/honor" target="_blank">企业荣誉</a></li>'+
                '<li><a href="'+getRootPath_web()+'/serviceTerms" target="_blank">服务条款</a></li>'+
            '</ul>'+
        '</div>'+
        '<div class="f3">'+
            '<h3 class="title">联系我们</h3>'+
            '<ul class="commonlist">'+
                '<li>开发者群：348400531</li>'+
                '<li>电话咨询：400-610-1019</li>'+
                '<li><a href="'+getRootPath_web()+'/cooperation" target="_blank">更多联系方式</a></li>'+
            '</ul>'+
        '</div>'+
        '<div class="f4"><img src="front/images/wximg.jpg" class="img" /><span class="font">扫码关注有礼</span></div>'+
    '</div>'+
    '<p class="friendslinks">友情链接：'+
    	'<a href="http://www.appying.com" rel="nofollow" target="_blank">APP运营</a>'+
    	'<a href="http://www.pgyer.com" rel="nofollow" target="_blank">蒲公英</a>'+
    	'<a href="http://www.wex5.com/" rel="nofollow" target="_blank">起步科技</a>'+
    	'<a href="http://dev.hivoice.cn" rel="nofollow" target="_blank">云知声</a>'+
    	'<a href="https://coding.net" rel="nofollow" target="_blank">Coding</a>'+
    	'<a href="http://www.ijiami.cn" rel="nofollow" target="_blank">爱加密</a>'+
    	'<a href="http://www.thinkphp.cn" rel="nofollow" target="_blank">ThinkPHP</a>'+
    	'<a href="http://www.udesk.cn/website/" rel="nofollow" target="_blank">Udesk</a>'+
    	'<a href="http://www.tingyun.com" rel="nofollow" target="_blank">听云</a>'+
    	'<a href="http://apistore.baidu.com" rel="nofollow" target="_blank">API Store</a>'+
    	'<a href="http://www.yidonghua.com" rel="nofollow" target="_blank">移动化网站</a>'+
    	'<a href="http://www.oneapm.com" rel="nofollow" target="_blank">应用性能监控</a>'+
    	'<a href="link" rel="nofollow" target="_blank">更多>></a>'+
    '</p>';
include("js/cookieUtil.js");
$(function(){
	$(".footer").html(footerHtml);
});


