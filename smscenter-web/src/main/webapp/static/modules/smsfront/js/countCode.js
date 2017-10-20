var _vds = _vds || [];
window._vds = _vds;
(function(){
	 _vds.push(['setAccountId', 'a847aef46c854710b916e2e9e076a5de']);
	  $.ajax({
			type : "POST",
			url : "http://www.yuntongxun.com/activity/getUserId",
			dataType : "json",
			success : function(data) {
				if(data.flag){
					 _vds.push(['setCS1','user_id',data.userId]);
				}
			}
		});
  (function() {
    var vds = document.createElement('script');
    vds.type='text/javascript';
    vds.async = true;
    vds.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'dn-growing.qbox.me/vds.js';
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(vds, s);
  })();
})();

(function(){
	    var bp = document.createElement('script');
	    bp.src = '//push.zhanzhang.baidu.com/push.js';
	    var s = document.getElementsByTagName("script")[0];
	    s.parentNode.insertBefore(bp, s);
})();

var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?b0e5f3f0b089014b9b693e3bf355d172";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();

