<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>应用管理</title>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/main.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.colorbox.js"></script>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/colorbox.css" type="text/css"/>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/style.css" type="text/css"/>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/commonstyle.css" type="text/css"/>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/app_list.css" type="text/css"/>
    <script type="text/javascript">
        $(function(){
        	
        	accountList();
        	
            function setHeight() {
                var nHeight = $('.container').height() + 'px';
                var brsHeight = $(window).height() - 176 + 'px';
                var mainHeight = parseInt(brsHeight) + 'px';
                if (parseInt(nHeight) < parseInt(brsHeight)) {
                    $('.left,.left .tabs').css({'height': mainHeight});
                } else if (parseInt(nHeight) >= parseInt(brsHeight)) {
                    $('.left,.left .tabs').css({'height': nHeight});
                }
            }

            window.onload = function () {
                setHeight();
            };
            window.onresize = function () {
                setHeight();
            };

            $('.th_list li:odd').css('background', '#f9f9f9');
            $('.th_list li .t1 .qes').mouseover(function (e) {
                var top = $(this).position().top + 14 + 'px';
                var left = e.clientX + 14 + 'px';
                $(this).next('.tsDiv').css({top: top, left: left}).show();
            }).mouseout(function () {
                $('.tsDiv').hide();
            }).mousemove(function (e) {
                var top = $(this).position().top + 14 + 'px';
                var left = e.clientX + 14 + 'px';
                $(this).next('.tsDiv').css({top: top, left: left}).show();
            });

            var appErrorMsg = "";
            if (appErrorMsg != undefined && appErrorMsg != "" && appErrorMsg != null) {
                alert(appErrorMsg);
            }
            
        });
        
        //编辑应用
        function editApp(id,type) {
        	$('#appForm').attr('action','${ctxFront}/account/accountInfo?id='+id+'&type='+type);
        	$('#appForm').submit();
        }
		
        //上线应用
        function applyOnlineApp(id) {
        	
        	$.ajax({  
		        type: "post",  
		        url: "${ctxFront}/account/updateStatus?id="+id+"&accStatus=-1",
		        dataType: 'json',  
		        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
				success : function(result) {
					if(result.code == "0"){
						alert("申请上线成功");
						window.location.reload();
					}else{
						alert("申请上线失败");
					}
				},
				error : function(){
					alert("申请上线失败");
				}
		    });
        }

        function resumeApp(appId) {
            if (confirm("确认恢复该应用?")) {
                appForm.action = "/member/app/regainApp?appId=" + appId + "&randStr=" + Math.random();
                appForm.submit();
            }
        }
		
        //下线应用
        function suspendApp(appId) {
            $.colorbox({href: "/member/app/toSuspend?appId=" + appId + "&randStr=" + Math.random(), title: "暂停应用"});
        }
		
        //删除应用
        function delApp(id) {
        	$.ajax({  
		        type: "post",  
		        url: "${ctxFront}/account/updateStatus?id="+id+"&accStatus=-3",
		        dataType: 'json',  
		        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
				success : function(result) {
					if(result.code == "0"){
						alert("删除应用成功");
						window.location.reload();
					}else{
						alert("删除应用失败");
					}
				},
				error : function(){
					alert("删除应用失败");
				}
		    });
        }

        function fn_focus(ele) {
            var reg = /^[\s]*$/;
            ele.style.border = 'solid 1px rgb(2,185,239)';
            if (ele.value == '应用名称') {
                ele.value = '';
                ele.style.color = 'rgb(51,51,51)';
            }
        }
        function fn_blur(ele) {
            var reg = /^[\s]*$/;
            ele.style.border = 'solid 1px rgb(213,213,213)';
            if (reg.test(ele.value) || ele.value == ele.defaultValue) {
                ele.value = ele.defaultValue;
                ele.style.color = 'rgb(159,159,159)';
            }
        }

        function ReSortList() {
            var sortType = $("#sortType").val();
            if (sortType == "desc") {
                $("#sortType").val("asc");
            } else {
                $("#sortType").val("desc");
            }
            appForm.submit();
        }
        
    </script>
     
    <script>
     function accountList()
     {
    	 var html='';
     	 var accstatus='';
     	 $.ajax({
     		 type: "POST",
              url:"${ctxFront}/account/accountList",
              dataType: "json",
              success: function(result){
            	  if(result.data){
            		   $.each(result.data, function (i,item){
                      	if(item.accStatus==1)
                     	{
                     		accstatus='上线';
                     	}
                     	else if(item.accStatus==0)
                     	{
                     		accstatus='下线';
                     	}
                     	else if(item.accStatus==-1)
                     	{
                     		accstatus='申请上线';
                     	}
                     	else if(item.accStatus==-2)
                     	{
                     		accstatus='待上线';
                     	}
                     	html+=' <ul class="th_list clearfix">';
                     	html+='     <li>';
                     	html+='         <span class="t1">';
                     	html+='             <a  href=javascript:editApp('+item.id+',"view");>'+ item.accName +'</a>';
                     	html+='         </span>';
                     	html+='         <span class="t3 org">'+ accstatus +'</span>';
                     	html+='         <span class="t4">'+ item.createTime +'</span>';
                     	html+='         <span class="t5">';  
                     	html+='             <a href="javascript:void(0);" title="编辑应用" onclick="editApp('+item.id+','+""+')">编辑</a>';
                     	if(item.accStatus==-2){//待上线
                        	html+='             <a href="javascript:void(0);" class="org apply" title="申请上线"  onclick="applyOnlineApp('+item.id+')">上线</a>';
                            html+='             <a href="javascript:void(0);" class="red del" title="删除应用"   onclick="delApp('+item.id+')">删除</a>';
                     	}else if(item.accStatus==1){//上线
                     		html+='             <a href="javascript:void(0);" class="org apply" title="应用下线"  onclick="applyOnlineApp('+item.id+')">下线</a>';
                     	}
                        html+='         </span>'; 
                        html+='     </li>';
                        html+=' </ul>';
                      	 
                    }) 
            	  }
             
                 $('.div11').append(html);
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

            <script type="text/javascript">
                function msgCount() {
                    $.ajax({
                        type: "POST",
                        url: "/member/user/msgCount",
                        dataType: "json",
                        success: function (data) {
                            if (data.flag) {
                                if (data.msgCount > 0) {
                                    $('.tabs li .count').css('display', 'block');
                                    $("#msg_account_sum").html(data.msgCount);
                                } else {
                                    $('.tabs li .count').css('display', 'none');

                                }
                            }
                        }
                    });
                }
                msgCount();
            </script>
            
           
        
        <div class="right">
            <div class="tit"><h1>应用列表</h1></div>
            <!-- <div class="tx_div">
                <h2>应用提醒</h2>
                <p>1、上线应用需先通过实名制认证<br/>
                    2、子账户是云通讯平台提供给开发者自行管理客户的账户，子账户隶属于主账户下的一个应用，使用子账户您可以区分你的客户并保证他们可以独立的使用子账户鉴权的REST能力<a
                            href="#">了解更多</a><br/>
                </p>
            </div> -->
            <div class="div1">
                <div class="tit1"><h1 class="b4">应用列表</h1></div>
                <div class="div11">
                    <form action="/member/app/view" name="appForm" id="appForm" method="post">
                        <input type="hidden" name="sortType" id="sortType" value="desc"/>
                        <div class="searchDiv">
                            <a href="${ctxFront}/view/accountForm" class="btn_search">创建应用</a>
                           
                        </div>
                    </form>
                    <div class="thDiv">
                        <span class="t1">应用名称</span>
                        <span class="t3">状态</span>
                        <span class="t4">创建时间</span>
                        <span class="t5">操作</span>
                    </div>
                 

                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>