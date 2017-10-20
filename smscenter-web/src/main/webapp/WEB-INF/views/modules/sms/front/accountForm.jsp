<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用添加</title>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/main.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.colorbox.js"></script>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/colorbox.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/style.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/commonstyle.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/app_create.css" type="text/css" />
    <link href="${ctxFrontStatic }/css/default.css" type="text/css" rel="stylesheet" />
    <link href="${ctxFrontStatic }/css/def.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/jquery.searchableSelect.css">
    <script src="${ctxFrontStatic }/js/jquery.searchableSelect.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/core.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/combo.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/checkbox.js"></script>
     <script type="text/javascript">
        //var cb_appProfession;
        $(function(){
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

            //复选框
            $('.checkbox').each(function(){
                if($(this).is(':checked')){
                    $(this).parent().addClass('off-on');
                }else{
                    $(this).parent().removeClass('off-on');
                }
            });
            $('.checkbox, .checked').hover(function(){
                $(this).parent().addClass('off-hover');
            },function(){
                $(this).parent().removeClass('off-hover');
            });
            $('.checkbox').click(function(){
                if($(this).is(':checked')){
                    $(this).parent().addClass('off-on');
                }else{
                    $(this).parent().removeClass('off-on');
                }
            });
            /*内容显示隐藏*/
            $('.checked').click(function(){
                if($(this).is(':checked')){
                    $(this).parent().addClass('off-on');
                    $(this).parent().parent().parent().find('.checkDiv').show();
                    setHeight();
                }else{
                    var chkName=$(this).attr("name");
                    if(chkName=='chkCallBackUrl'){
                        $("#callBackUrl").val("");
                        $("input[name='funInfo']").each(function(){if($(this).val()!='1007'){$(this).attr("checked",false);$(this).parent().removeClass('off-on');}});
                    }
                    if(chkName=='isIvr'){
                        $("#ivrUrl").val("");
                        $("#isAgent").attr("checked",false);
                        $("#isAgent").parent().removeClass('off-on');
                    }
                    if(chkName=='chkwhiteList'){
                        $("#whiteList").val("");
                    }
                    $(this).parent().removeClass('off-on');
                    $(this).parent().parent().parent().find('.checkDiv').hide();
                    setHeight();
                }
            });
        });
        $(document).ready(function() {
        	
        	var callBackUrl = '${entity.callBackUrl}';
        	if(callBackUrl){
	        	$("#chkCallBackUrl").parent().addClass('off-on');
	        	$("#chkCallBackUrl").parent().parent().parent().find('.checkDiv').show();
	        	$("#callBackUrl").val('${entity.callBackUrl}');
        	}
        	
            $("#appAddForm").validate({
                errorElement : "span",
                errorClass : "error",
                onkeyup:false,
                rules : {
                	accName:{
                        required:true,
                        noContainsSpace:true,
                        onlyLetterAndChinese:true,
                        byteRangeLength:[0, 20],
                        isContainBlackWord:true
                    },
                    callBackUrl:{
                        isNotChinese:true,
                        url:true
                    }
                },
                messages : {
                	accName:{
                        required:"请输入应用名称",
                        onlyLetterAndChinese:"只允许输入汉字或字母",
                    },
                    callBackUrl:{
                        url:"请输入正确的URL格式,如:http://www.test.com"
                    }
                },
                errorPlacement : function(error, element) {
                    error.appendTo(element.parent());
                }
            });
        });

        function doSubmit(){
            if($("#appAddForm").valid()){
                var url=$("#callBackUrl").val();
                if(url!=""){
                    if(url.indexOf('**/')>0){
                        var newURL=url.replace("**/","");
                    }else{
                        var newURL=url.replace(/\**/g,"");
                    }
                    if(!IsURL(newURL)){
                        $("#callBackUrlError_1").attr({"style":"display:block"});
                        return false;;
                    }else{
                        $("#callBackUrlError_1").attr({"style":"display:none"});
                    }
                    $.ajax({
                        type : "POST",
                        url : "/member/app/checkCallBackUrl",
                        data : "callBackUrl="+url,
                        dataType : "json",
                        success : function(returnData) {
                            if(!returnData.flag){
                                $("#callBackUrlError").attr({"style":"display:block"});
                                return false;
                            }else{
                                $("#callBackUrlError").attr({"style":"display:none"});
                                appAddForm.submit();
                            }
                        }
                    });
                }else{
                    appAddForm.submit();
                }
            }
        }
        
        function onSubmit(){
        	if($("#appAddForm").valid()){
        		if($("#chkCallBackUrl").is(':checked')){
    				var url=$("#callBackUrl").val();
    				if(url==undefined||url==""||url==null){
    					alert("请先输入应用回调地址");
    					return;
    				}else{
    					var newURL=url.replace(/\**/g,""); 
    					if(!IsURL(newURL)){
    						$("#callBackUrlError_1").attr({"style":"display:block"});
    						$("#callBackUrlError_1").text("请输入正确的URL格式,如:http://www.test.com");
    						return false;
    					}else{
    						$("#callBackUrlError_1").attr({"style":"display:none"});
    					}
    				}
    			}
        		
        		
	        	var data = $('#appAddForm').serialize();
				$.ajax({  
			        type: "post",  
			        url: "${ctxFront}/account/createAccount",
			        data: data,  
			        dataType: 'json',  
			        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
					success : function(result) {
						if(result.code == "0"){
							alert("保存应用信息成功");
						}else{
							alert("保存应用信息成功失败");
						}
					},
					error : function(){
						alert("保存应用信息成功失败");
					}
			    });
        	}
        }

        function checkCallBackUrl(obj){
            if(obj.checked){
                var url=$("#callBackUrl").val();
                if(url==undefined||url==""||url==null){
                    alert("请先输入应用回调地址");
                    obj.checked=false;
                    return;
                }else{
                    var newURL=url.replace(/\**/g,"");
                    if(!IsURL(newURL)){
                        $("#callBackUrlError_1").attr({"style":"display:block"});
                        obj.checked=false;
                        return false;
                    }else{
                        $("#callBackUrlError_1").attr({"style":"display:none"});
                    }
                }
            }
        }

        function isIP(ip)
        {
            var reSpaceCheck = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;
            if (reSpaceCheck.test(ip)){
                ip.match(reSpaceCheck);
                if (RegExp.$1<=255&&RegExp.$1>=0
                    &&RegExp.$2<=255&&RegExp.$2>=0
                    &&RegExp.$3<=255&&RegExp.$3>=0
                    &&RegExp.$4<=255&&RegExp.$4>=0){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
        
    </script>
    
    
    <script>
        $(function(){
            $('select').searchableSelect();
        });
    </script>
</head>
<body>
<div class="wrap">
    <div class="container clearfix">
            
        <div class="right">
            <div class="tit"><h1>${empty entity.id || entity.id =='' ? '创建' : '编辑'}应用</h1></div>
            <form action="${ctxFront}/account/createAccount" name="appAddForm" id="appAddForm" method="post">
            	<input type="hidden" id="id" name="id" value="${entity.id}"/>
                <div class="form">
                    <dl class="clearfix">
                        <dt><i>*</i>应用名称</dt>
                        <dd>
                            <input type="text" class="text" id="accName" name="accName" value="${entity.accName}"/><span class="tip_info">不超过20个字符</span>
                        </dd>
                    </dl>
                    <dl class="clearfix relative;">
                        <dt><i>*</i>使用功能</dt>
                        <dd>
                            <select name="serviceId" id="serviceId">
                                <option value="10" ${entity.serviceId eq 10 ? 'selected="selected"' : ''} >行业短信 </option>
                                <option value="20" ${entity.serviceId eq 20 ? 'selected="selected"' : ''} >验证码</option>
                                <option value="30" ${entity.serviceId eq 30 ? 'selected="selected"' : ''} >营销短信</option>
                            </select>
                        </dd>
                    </dl> 
                    <dl class="clearfix">
                        <dt>启用回调地址</dt>
                        <dd>
                            <div class="check_b">
                                <div class="off"><input id="chkCallBackUrl" type="checkbox" class="checked" name="chkCallBackUrl"/></div><label class="tab">勾选启用</label>
                            </div>
                            <div class="checkDiv">
                                <ul>
                                    <li>
                                        <label class="label1">回调地址URL：</label>
                                        <em class="em1">
                                            <input type="text" class="l_text" id="callBackUrl" name="callBackUrl" value="${entity.callBackUrl}"/>
                                        </em>
                                    </li>
                                </ul>
                                <div style="display:none"><input id="ttsFun" name="funInfo" type="checkbox" class="checkbox"  value="1007"/></div>
                            </div>
                        </dd>
                    </dl>
                    <dl class="clearfix"><dt></dt>
                        <dd><input type="button" value="确 定" class="btn_sure c2" onclick="onSubmit()"/></dd>
                    </dl>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>