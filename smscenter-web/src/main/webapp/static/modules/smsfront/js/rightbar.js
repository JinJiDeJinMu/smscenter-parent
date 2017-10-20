var rightBarHtml='<a href="javascript:void(0)" class="icon-zx" id="BizQQWPA"></a>'+
				 '<a href="../advice_400.html" class="icon-qq" target="_blank" id="consult400"></a>'+
				 '<em class="icon-chat">'+
				 '<span class="chat-tips">'+
				 '<span class="chat-left"></span>'+
				 '<a href="javascript:void(0)" class="chat-right-icon" id="collection"></a>'+
				 '</span></em>'+
$(function(){
	$('.side-bar').html(rightBarHtml);
	$('.chat-right-icon').click(function(){alert('请使用快捷键  Ctrl+D 添加收藏  ^_^');});
});
