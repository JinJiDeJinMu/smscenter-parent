var USER_CHANNEL_COOKIE="_ccopUserChannelCookie";
var USER_CHANNEL_ACCESS_TIME = "_ccopUserAccessTimeCookie";
var USER_CHANNELACTIVITY_COOKIE = "_ccopUserChannelActivityCookie";
var QD_CHANNEL_COOKIE = "_ccopUserChannelQDCookie";
var XL_CHANNEL_COOKIE = "_ccopUserChannelXLCookie";
var CP_CHANNEL_COOKIE = "_ccopUserChannelCPCookie";
var LY_CHANNEL_COOKIE = "_ccopUserChannelLYCookie";
var USER_ACTIVITY_COOKIE = "_ccopUserActivityCookie";

function saveRegSourceCookies(){
	var url=window.location.href;
	//console.log('url：',url);
	/*if(/MSIE (\d+\.\d+);/.test(navigator.userAgent)){
	    var referLink = document.createElement('a');
	    referLink.href = url;
	    document.body.appendChild(referLink);
	    referLink.click();
	} else {
	    location.href = url;
	}*/
	var referer=document.referrer;
	//判断是否底部iframe引用
	if(isContains(url, "homefooter.html")){
		referer=window.parent.getRefer(); //取top中获取的js
	}
	
	var domain=getHost(referer);
	
	if(!filterReferer(domain)){
	saveUserChannelCookie(USER_CHANNEL_COOKIE,domain);
	saveUserChannelActivityCookie(USER_CHANNEL_ACCESS_TIME,getNowFormatDate());
	}
	var kw,qd,xl,cp,ly,act;
	if(!isNull(url)){
		var param=url.getParameter("kw");//关键词
		if(!isNull(param)){
			kw=param;
		}
		param=url.getParameter("qd");//渠道
		if(!isNull(param)){
			qd=param;
		}
		param=url.getParameter("xl");//系列
		if(!isNull(param)){
			xl=param;
		}
		param=url.getParameter("cp");//产品线
		if(!isNull(param)){
			cp=param;
		}
		param=url.getParameter("ly");//来源
		if(!isNull(param)){
			ly=param;
		}
		param=url.getParameter("act");
		if(!isNull(param)){
			act=param;
		}
	}
    if(!isNull(referer)){
		var param=referer.getParameter("kw");//关键词
		if(!isNull(param)){
			kw=param;
		}
		param=referer.getParameter("qd");//渠道
		if(!isNull(param)){
			qd=param;
		}
		param=referer.getParameter("xl");//系列
		if(!isNull(param)){
			xl=param;
		}
		param=referer.getParameter("cp");//产品线
		if(!isNull(param)){
			cp=param;
		}
		param=referer.getParameter("ly");//来源
		if(!isNull(param)){
			ly=param;
		}
		param=referer.getParameter("act");
		if(!isNull(param)){
			act=param;
		}
	}
	
	if(!isNull(kw)&&kw.length<=15){
		saveUserChannelActivityCookie(USER_CHANNELACTIVITY_COOKIE,kw);
	}
	if(!isNull(qd)&&qd.length<=15){
		saveUserChannelActivityCookie(QD_CHANNEL_COOKIE,qd);
	}
	if(!isNull(xl)&&xl.length<=15){
		saveUserChannelActivityCookie(XL_CHANNEL_COOKIE,xl);
	}
	if(!isNull(cp)&&cp.length<=15){
		saveUserChannelActivityCookie(CP_CHANNEL_COOKIE,cp);
	}
	if(!isNull(ly)&&ly.length<=15){
		saveUserChannelActivityCookie(LY_CHANNEL_COOKIE,ly);
	}			
	if(!isNull(act)&&act.length()<=15){
		saveUserChannelCookie(USER_ACTIVITY_COOKIE,act);
	}
}

function saveUserChannelCookie(cookieName,cookieVal){
	setCookie(cookieName,cookieVal,"d30");
}

function saveUserChannelActivityCookie(cookieName,cookieVal){
	setCookie(cookieName,cookieVal,"d30");
}

/* 
功能：保存cookies函数  
参数：name，cookie名字；value，值 
*/  
function setCookie(name,cookieVal,maxAge){  
	 var strSec = getSec(maxAge); 
	 var exp = new Date(); 
	 exp.setTime(exp.getTime() + strSec*1); 
	 document.cookie = name + "="+ escape (cookieVal) + ";expires=" + exp.toGMTString()+";path=/"; 
}  
function getSec(str)
{ 
   var str1=str.substring(1,str.length)*1; 
   var str2=str.substring(0,1); 
   if (str2=="s")
   { 
        return str1*1000; 
   }
   else if (str2=="h")
   { 
       return str1*60*60*1000; 
   }
   else if (str2=="d")
   { 
       return str1*24*60*60*1000; 
   } 
}

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

var getHost = function(url) { 
    var host = "null";
    if(typeof url == "undefined"
                    || null == url)
            url = window.location.href;
    var regex = /.*\:\/\/([^\/]*).*/;
    var match = url.match(regex);
    if(typeof match != "undefined"
                    && null != match)
            host = match[1];
    return host;
}

function filterReferer(url){
	var noFilters=["yuntongxun.com","cloopen.com","42.121.15.9","42.121.15.90"];
	for(var i=0;i<noFilters.length;i++){
		if(url.indexOf(noFilters[i])!= -1){
			return true;
		}
	}
	return false;
}

function isNull(arg1){
	return !arg1 && arg1!==0 && typeof arg1!=="boolean"?true:false;
}

String.prototype.getParameter = function (key) {  
    var re = new RegExp(key + '=([^&]*)(?:&)?');  
    return this.match(re) && this.match(re)[1];  
};
function isContains(str, substr) {
    return new RegExp(substr).test(str);
}
$(function(){
  saveRegSourceCookies(); 
});
