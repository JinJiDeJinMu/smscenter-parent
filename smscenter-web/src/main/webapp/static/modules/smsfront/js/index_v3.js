// JavaScript Document
/*提供通讯的一切hover效果*/
function productHover(){
	$('.productlist').find('li').hover(function(){
		$(this).css({'background':'#f4f4f4'});
		$(this).find('.content a').css({'color':'#132f41'});
		$(this).find('h3').css({'background':'#132f41','color':'#FFF'});
		$(this).find('.icon1').addClass('icon1_ac');
		$(this).find('.icon2').addClass('icon2_ac');
		$(this).find('.icon3').addClass('icon3_ac');
		$(this).find('.icon4').addClass('icon4_ac');
		$(this).find('.icon5').addClass('icon5_ac');
		$(this).find('.icon6').addClass('icon6_ac');
		
	},function(){
		$(this).css({'background':''});	
		$(this).find('h3').css({'background':'#dce0e3','color':'#132f41'});
		$(this).find('.content a').css({'color':'#aaaaaa'});
		$(this).find('.icon1').removeClass('icon1_ac');
		$(this).find('.icon2').removeClass('icon2_ac');
		$(this).find('.icon3').removeClass('icon3_ac');
		$(this).find('.icon4').removeClass('icon4_ac');
		$(this).find('.icon5').removeClass('icon5_ac');
		$(this).find('.icon6').removeClass('icon6_ac');
	});		
}
$(function(){
	function setHeight(){
		var window_H = $(window).height();
		var top_H = $('.pagetop').height();
		var height = window_H - top_H+'px';
		/*//兼容ie7
		var browser=navigator.appName;
		var b_version=navigator.appVersion 
		var version=b_version.split(";"); 
		var trim_Version=version[1].replace(/[ ]/g,""); 
		if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE7.0") 
		{ 
		height=window_H +'px';
		} */
		//换了一种写法，貌似兼容性更好
		if (navigator.userAgent.indexOf("MSIE") > 0) {
				if (navigator.userAgent.indexOf("MSIE 7.0") > 0) {
					height=window_H +'px';
				}
			}
		//这里结束
		$('.banner').css({height:height});
	};
	window.onload=function(){setHeight();
		//var oScrollTop = document.documentElement.scrollTop || document.body.scrollTop;
		//if(oScrollTop){
			//$('.pagetop').hide();
		//}
		//$('body,html').animate({scrollTop:0},50);
	};
	window.onresize=function(){setHeight();};
	/*6大类通讯服务hover效果*/
	productHover();
	/*播放视频*/
	$('.flash_btn').click(function(){
		$.layer({
			type : 2,
			title: false,
			shade:[0.8, '#393D49'],
			shadeClose: false,
			maxmin: false,
			fix : true,  
			area: ['640px', '360px'],                     
			iframe: {
				src : 'shipin.html'
			}
		});
	});	
	$('.flash').click(function(){
		$(this).parent().parent().find('.flashbox').show();	
	});
	/*客户评价轮播*/
	jQuery(".judgebox").slide({
		titCell:".circle_btns a",
		mainCell: ".judgelist",
		effect: "fade",
		autoPlay: true,
		delayTime: 400
		//effect: "leftLoop"
	});
	$(window).scroll(function(){
		 var oScrollTop = document.documentElement.scrollTop || document.body.scrollTop;
		 if(oScrollTop > 72){
			$('.pagetop').slideUp(100); 
			$('.gonggao').slideUp(100);
			topnav();
		 }else{
			$('.pagetop').slideDown(100);
			$('.gonggao').slideDown(100);
			notopnav();
		 }
	});
	function topnav(){
		document.onmousemove=function(ev){
			var oEvent=ev||event;
			var y =oEvent.clientY;
			if(y<72){
				$('.pagetop').slideDown(100);
				$('.gonggao').slideDown(100);
			}else{
				if($('.child_nav').is(":visible")){
					//$('.pagetop').slideDown(100);
					if(y>430){
						$('.pagetop').slideUp(100); 
						$('.gonggao').slideUp(100);
					}
				}else{
					$('.pagetop').slideUp(100); 
					$('.gonggao').slideUp(100);
				}
			}
		};	
	};
	function notopnav(){
		document.onmousemove=function(ev){
			
		};
	};
});