// JavaScript Document
$(function(){
	/*function setHeight(){
		var window_height = $(window).height();
		var conH = window_height - $('.fixtitle').height();
		$('.price_nav').css({height:window_height});
		$('.maincon, .scrollwrap').css({height:conH});
	}
	window.onresize=function(){setHeight();};*/
	/*显示隐藏*/
	var now = -1;
	$('.question_list .stitle').click(function(){
		var index = $(this).parent().index();
		if(now==index){
			//收缩
			$(this).next().hide();
			$(this).find('a').removeClass().addClass('pointerBottom');	
			now=-1;
		}else{
			//收起原来的
			if(now!=-1){
				$('.question_list').children().eq(now).find('div').hide();
				$('.question_list').children().eq(now).find('.pointerTop').removeClass().addClass('pointerBottom');
			}
			$(this).next().show();
			$(this).find('a').removeClass().addClass('pointerTop');	
			now=index;
		}
	});
/*	window.onload=function(){
		高度设置
		//setHeight();
		top显示隐藏
		document.onmousemove=function(ev){
			var oEvent=ev||event;
			var y =oEvent.clientY+document.body.scrollTop+document.documentElement.scrollTop;
			if(y<72){
				$('.pagetop').slideDown(100);
			}else{
				if($('.child_nav').is(":visible")){
					if(y>430){
						$('.pagetop').slideUp(100);
					}
				}else{
					$('.pagetop').slideUp(100);
					$('.child_nav').hide();		
				}
			} 	
		};
	};*/
});