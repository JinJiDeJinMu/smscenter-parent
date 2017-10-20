// JavaScript Document
$(function(){
	/*function setHeight(){
		var window_height = $(window).height()-72;
		$('.tastewrap').css({height:window_height});
	}
	window.onload=function(){setHeight();};
	window.onresize=function(){setHeight();};*/
	
	/*切换	
	$('.tastetags').find('a').click(function(){
		var index = $(this).index();
		if(index == 0){
			$(this).addClass('tag11');	
			$('.tastetags').find('a').eq(1).removeClass('tag22');
			$('.tastetags').find('a').eq(2).removeClass('tag33');
			$('.tastetags').find('a').eq(3).removeClass('tag44');
			$('.tastetags').find('a').eq(4).removeClass('tag55');
			$('.tastetags').find('a').eq(5).removeClass('tag66');
		}else if(index == 1){
			$(this).addClass('tag22');	
			$('.tastetags').find('a').eq(0).removeClass('tag11');
			$('.tastetags').find('a').eq(2).removeClass('tag33');
			$('.tastetags').find('a').eq(3).removeClass('tag44');
			$('.tastetags').find('a').eq(4).removeClass('tag55');
			$('.tastetags').find('a').eq(5).removeClass('tag66');	
		}else if(index == 2){
			$(this).addClass('tag33');	
			$('.tastetags').find('a').eq(0).removeClass('tag11');
			$('.tastetags').find('a').eq(1).removeClass('tag22');
			$('.tastetags').find('a').eq(3).removeClass('tag44');
			$('.tastetags').find('a').eq(4).removeClass('tag55');
			$('.tastetags').find('a').eq(5).removeClass('tag66');	
		}else if(index == 3){
			$(this).addClass('tag44');	
			$('.tastetags').find('a').eq(0).removeClass('tag11');
			$('.tastetags').find('a').eq(1).removeClass('tag22');
			$('.tastetags').find('a').eq(2).removeClass('tag33');
			$('.tastetags').find('a').eq(4).removeClass('tag55');
			$('.tastetags').find('a').eq(5).removeClass('tag66');	
		}else if(index == 4){
			$(this).addClass('tag55');	
			$('.tastetags').find('a').eq(0).removeClass('tag11');
			$('.tastetags').find('a').eq(1).removeClass('tag22');
			$('.tastetags').find('a').eq(2).removeClass('tag33');
			$('.tastetags').find('a').eq(3).removeClass('tag44');
			$('.tastetags').find('a').eq(5).removeClass('tag66');	
		}else if(index == 5){
			$(this).addClass('tag66');
			$('.tastetags').find('a').eq(0).removeClass('tag11');		
			$('.tastetags').find('a').eq(1).removeClass('tag22');
			$('.tastetags').find('a').eq(2).removeClass('tag33');
			$('.tastetags').find('a').eq(3).removeClass('tag44');
			$('.tastetags').find('a').eq(4).removeClass('tag55');
		}
		$('.tastebox').find('.tastelist').eq(index).show().siblings().hide();	
	});*/
	/*短信*/
	$('.tastesms .taste_t .stitle').click(function(){
		$.layer({
			type : 2,
			title: false,
			shade:[0.8, '#000'],
			shadeClose: false,
			maxmin: false,
			fix : true,
			border: [0] , 
			area: ['854px', '480px'],                     
			iframe: {
				src : 'pop_taste_sms.html'
			}
		});

	});

	/*流量*/
	$('.tasteflow .taste_t .stitle').click(function(){
		$.layer({
			type : 2,
			title: false,
			shade:[0.8, '#000'],
			shadeClose: false,
			maxmin: false,
			fix : true,
			border: [0] , 
			area: ['854px', '480px'],                     
			iframe: {
				src : 'pop_taste_flow.html'
			}
		});

	});
	/*语音验证码*/
	$('.voicecode .taste_t .stitle').click(function(){
		$.layer({
			type : 2,
			title: false,
			shade:[0.8, '#000'],
			shadeClose: false,
			maxmin: false,
			fix : true,
			border: [0] , 
			area: ['854px', '480px'],                     
			iframe: {
				src : 'pop_taste_voicecode.html'
			}
		});

	});	
	/*匿名通话*/
	$('.tastecall .taste_t .stitle').click(function(){
		$.layer({
			type : 2,
			title: false,
			shade:[0.8, '#000'],
			shadeClose: false,
			maxmin: false,
			fix : true,
			border: [0] , 
			area: ['854px', '480px'],                     
			iframe: {
				src : 'pop_taste_niming.html'
			}
		});

	});	
	/*移动IM
	$('.tasteim').click(function(){
		$.layer({
			type : 2,
			title: false,
			shade:[0.8, '#000'],
			shadeClose: false,
			maxmin: false,
			fix : true,
			border: [0] , 
			area: ['854px', '480px'],                     
			iframe: {
				src : 'pop_taste_im.html'
			}
		});

	});	*/
	/*语音通知*/
	$('.tastevoice .taste_t .stitle').click(function(){
		$.layer({
			type : 2,
			title: false,
			shade:[0.8, '#000'],
			shadeClose: false,
			maxmin: false,
			fix : true,
			border: [0] , 
			area: ['854px', '480px'],                     
			iframe: {
				src : 'pop_taste_voice.html'
			}
		});

	});
	/*音视频通话
	$('.voiceflash').click(function(){
		$.layer({
			type : 2,
			title: false,
			shade:[0.8, '#000'],
			shadeClose: false,
			maxmin: false,
			fix : true,
			border: [0] , 
			area: ['854px', '480px'],                     
			iframe: {
				src : 'pop_taste_voiceflash.html'
			}
		});

	});	*/
	/*Html5 IM
	$('.tastehtml5').click(function(){
		$.layer({
			type : 2,
			title: false,
			shade:[0.8, '#000'],
			shadeClose: false,
			maxmin: false,
			fix : true,
			border: [0] , 
			area: ['854px', '480px'],                     
			iframe: {
				src : 'pop_taste_html5.html'
			}
		});

	});*/	
	/*电话会议*/
	$('.callmeeting .taste_t .stitle').click(function(){
		$.layer({
			type : 2,
			title: false,
			shade:[0.8, '#000'],
			shadeClose: false,
			maxmin: false,
			fix : true,
			border: [0] , 
			area: ['854px', '480px'],                     
			iframe: {
				src : 'pop_taste_callmeeting.html'
			}
		});

	});
	$(".tastelist li").hover(function(){
		$(this).children().eq(0).css("visibility","visible");
	},function(){
		$(this).children().eq(0).css("visibility","hidden");
	})
/*	var ronglian=true;
	if(ronglian){
		return success()
		console.log("share  to others");
	}*/
/*	$('.taste_t').hover(function(){
		$(this).parent().css({'background-position':'0 -353px'});
		$(this).find('.tastebtn').css({'background-position':'0 -274px'});
		$(this).find('.tasteim').css({'background-position':'34px -58px'});
		$(this).find('.voiceflash').css({'background-position':'34px -58px'});
	},function(){
		$(this).parent().css({'background-position':'0 0'});
		$(this).find('.tastebtn').css({'background-position':'0 0'});
		$(this).find('.tasteim').css({'background-position':'34px 216px'});
		$(this).find('.voiceflash').css({'background-position':'34px 216px'});
	});*/
});