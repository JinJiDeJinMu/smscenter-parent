<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>开票记录</title>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/main.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.colorbox.js"></script>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/colorbox.css" type="text/css"/>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/style.css" type="text/css"/>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/commonstyle.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/fapiao.css" type="text/css"/>
    <link href="${ctxFrontStatic }/css/default.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctxFrontStatic}/js/core.js"></script>
    <link href="${ctxFrontStatic }/css/def.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctxFrontStatic }/js/combo.js"></script>
    <script type="text/javascript">
        $(function(){
        	receiptRecord();
            function setHeight(){
                var nHeight = $('.right').height()+82+'px';
                var brsHeight = $(window).height()-176+'px';
                var mainHeight = parseInt(brsHeight)+'px';
                if(parseInt(nHeight) < parseInt(brsHeight)){
                    $('.left,.left .tabs').css({'height':mainHeight});
                }else if(parseInt(nHeight) >= parseInt(brsHeight)){
                    $('.left,.left .tabs').css({'height':nHeight});
                }
            }
            window.onload = function(){setHeight();};
            window.onresize = function(){setHeight();}
            
            /*查看
             $('.recordlist .view').mouseover(function(e){
             var top = parseInt($(this).position().top)+42+'px';
             var left =parseInt($(this).position().left)-268+'px';
             $(this).next('.view_info').css({top:top,left:left}).show();
             }).mouseout(function(){
             $('.view_info').hide();
             });
             var timer = null;
             $('.recordlist .view, .view_info').mouseover(function(e){
             var top = parseInt($(this).position().top)+42+'px';
             var left =parseInt($(this).position().left)-268+'px';
             $(this).next('.view_info').css({top:top,left:left}).show();
             clearTimeout(timer);
             }).mouseout(function(){
             timer=setTimeout(function(){$('.view_info').hide();},50);	
             })*/

            /*列表隔行变色*/
            $('.recordlist li:even').css('background','#f9f9f9');

            //删除发票记录
            //$('.del').colorbox({inline:true}); 
            //拒绝提示信息
            $(".refuse_tips").hover(function(){
                $(this).next(".refuse_tips_cont").show();
            },function(){
                $(this).next(".refuse_tips_cont").hide();
            });
        });
        //删除发票
        function delOne(id) {
            $().colorbox({
                title : "删除发票",
                html : '<div id="pop_del" class="popbox1"><div class="pop_con_div">'
                + '<p>您确定删除发票？删除后不能恢复！</p></div><div class="btns">'
                + '<form action="/manage/invovice/deleInvovice" method="post">'
                + '<input type="hidden" name="id" value="'+id+'"/>'
                + '<input type="submit" value="确 定" class="btn_sure c2" />'
                + '<input type="reset" value="取 消" class="btn_reset c5" onclick="$.fn.colorbox.close()"/></form></div></div>'
            });
        }
        
        function receiptRecord(){
        	 var html='';
        	 $.ajax({
        		 type: "POST",
                 url:"${ctxFront}/user/invoiceList",
                 dataType: "json",
                 success: function(result){
               	  if(result.data.list){
               		   $.each(result.data.list, function (i,item){
               			   
               			var receiptType='';
                   	 	var receiptStatus='';
                   	 	var invoiceNumber='';
                   		var expressCompany = '';
                   	 	var expressNumber = '';
               			   
           			   if(item.invoiceNumber !=undefined){
           			   		invoiceNumber = item.invoiceNumber;
           			   }
           			   if(item.expressCompany !=undefined){
           				  	expressCompany = item.expressCompany;
                		   }
           			   if(item.expressNumber !=undefined){
           				  	expressNumber = item.expressNumber;
               		   }  
             		   if(item.invoiceType == 1)
               		   {
               			 receiptType="普通发票";   
               		   }
               		   else if(item.invoiceType == 2)
               		   {
               			 receiptType="普通发票增值税专用发票";  
               		   }
           			   if(item.status == -1)
           			   {
           				 receiptStatus="索取发票 ";  
           			   }
           			   else if(item.status == 0)
           			   {
           				 receiptStatus="不开票 "; 
           			   }
           			   else if(item.status == 1)
           			   {
           				 receiptStatus="已开票"; 
           			   }
                       	html+='<span class="t1">'+item.createTime+'</span>';
                       	html+='<span class="t2">'+parseFloat(item.amount/100)+'</span>';
                       	html+='<span class="t2">'+receiptType+'</span>';
                       	html+='<span class="t2">'+invoiceNumber+'</span>';
                       	html+='<span class="t2">'+receiptStatus+'</span>';
                       	html+='<span class="t2">'+expressCompany+'</span>';
                       	html+='<span class="t2">'+expressNumber+'</span>';
                       }) 
               	  }
                
                    $('.recordlist.clearfix').html(html);
                 },
                 error:function(){
         			alert("系统错误");
         		}
           }); 
        	
        	
        }
        
    </script>
    
</head>
<body>
<div class="wrap">
    <script charset="utf-8" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODA1MDYzMV8yMDY2MTFfNDAwNjEwMTAxOV8"></script>
    <script type="text/javascript">
        BizQQWPA.addCustom([
            {aty: '0', a: '0', nameAccount: 4006101019, selector: 'BizQQWPA1'},
            {aty: '0', a: '0', nameAccount: 4006101019, selector: 'BizQQWPA2'},
            {aty: '0', a: '0', nameAccount: 4006101019, selector: 'BizQQWPA3'}
        ]);
    </script>
    <div class="container clearfix">
        <div class="right">
            <div class="tit"><h1>开票记录</h1></div>
            <form method="post" action="/manage/invovice/getInvoviceInfoList"
                  id="invoviceForm" name="invoviceForm">
                <input type="hidden" name="orderType" id="orderType"
                       value="" />
                <div class="recordDiv">
                    <span class="t1">申请时间</span>
                    <span class="t2">发票金额</span>
                    <span class="t2">发票类型</span>
                    <span class="t2">发票号</span>
                    <span class="t2">状态</span>
                    <span class="t2">快递公司</span>
                    <span class="t2">快递单号</span>
                </div>
                <ul class="recordlist clearfix">
                    
                </ul>
            </form>


        </div>
    </div>
</div>
</body>

</html>