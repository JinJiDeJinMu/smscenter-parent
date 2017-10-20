// JavaScript Document
$(function(){
	function setHeight(){
		var window_h = $(window).height();
		$('.registerwrap').css({height:window_h});	
	}	
	window.onload=function(){setHeight();};
	window.onresize=function(){setHeight();};
	$('.txtbox').find('.txt').focus(function(){
		$(this).parent().css({borderBottom:'solid 1px #FFF'});	
		$(this).parent().find('.icon').hide();
	});
	$('.txtbox').find('.txt').blur(function(){
		$(this).parent().css({borderBottom:'solid 1px #575b53'});	
		$(this).parent().find('.icon').show();
	});
	$('.zclist').find('.label').click(function(){
		$(this).parent().find('.txt').focus();	
	});
	$('.step1').click(function(){
		$(this).parent().animate({marginLeft:'-488px'});
	});
	$('.step2').click(function(){
		$(this).parent().animate({marginLeft:'-488px'});
	});
});