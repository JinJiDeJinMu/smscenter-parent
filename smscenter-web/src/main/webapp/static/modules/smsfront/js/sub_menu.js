// JavaScript Document
$(function(){
	var ytx={};
	$('[t_nav]').hover(function(){
		var _nav = $(this).attr('t_nav');
		clearTimeout( ytx[ _nav + '_timer' ] );
		ytx[ _nav + '_timer' ] = setTimeout(function(){
			$('[t_nav]').each(function(){
				//$(this)[ _nav == $(this).attr('t_nav') ? 'addClass':'removeClass' ]('current');
			});
			$('#'+_nav).stop(true,true).slideDown(200);
		}, 150);
	},function(){
		var _nav = $(this).attr('t_nav');
		clearTimeout( ytx[ _nav + '_timer' ] );
		ytx[ _nav + '_timer' ] = setTimeout(function(){
			//$('[t_nav]').removeClass('current');
			$('#'+_nav).stop(true,true).slideUp(200);
		}, 150);
	});		
});