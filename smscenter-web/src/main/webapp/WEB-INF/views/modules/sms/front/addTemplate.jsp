<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新增短信模板</title>
	<script type="text/javascript" src="${ctxFrontStatic }/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/main.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/jquery.colorbox.js"></script>
	<%-- <script type="text/javascript" src="${ctxFrontStatic }/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/member.js"></script> 
	<script type="text/javascript" src="${ctxFrontStatic }/js/countCode.js"></script> --%>
	<link rel="stylesheet" href="${ctxFrontStatic }/css/colorbox.css" type="text/css" />
	<link rel="stylesheet" href="${ctxFrontStatic }/css/style.css" type="text/css" />
	<link rel="stylesheet" href="${ctxFrontStatic }/css/commonstyle.css" type="text/css" />
	<link rel="stylesheet" href="${ctxFrontStatic }/css/sms_template.css" type="text/css" />
	<link href="${ctxFrontStatic }/css/default.css" type="text/css" rel="stylesheet" />
	<link href="${ctxFrontStatic }/css/def.css" type="text/css" rel="stylesheet" />
	<%-- <script type="text/javascript" src="${ctxFrontStatic }/js/cn.js"></script> 
	<script type="text/javascript" src="${ctxFrontStatic }/js/core.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/combo.js"></script>--%>
	<script type="text/javascript">
	var isBigCustomer=0;

	$(document).ready(function() {
		jQuery.validator.addMethod("notEqual", function(value,element,param) {
			return value != param;
		}, $.validator.format("请输入模板标题"));
		$("#smsTemplateAddForm").validate({
			errorElement : "span",
			errorClass : "error",
			rules : {
				name:{
					required:true,
					notEqual:'模板备注用，只做识别使用',
					onlyLetterAndChinese:true,
					byteRangeLength:[0, 24]
				},
				sign:{
					required:true
				},
				content:{
					required:true,
					rangelength:[0, 400]
				}
			},
			messages : {
				name:{
					required:"请输入模板标题",
					onlyLetterAndChinese:"只允许输入汉字或字母",
					byteRangeLength:"请确保输入的值在0-24个字节之间(一个中文字算2个字节)"
				},
				sign:{
					required:"请输入模板签名"
				},
				content:{
					required:"请输入模板内容",
					rangelength:"请确保输入的值在0-400个字节之间(一个中文字算2个字节)"
				}
			}
		});
	});
	
	function forbiddenStr(str,forbiddenArray){
	    //var destString = trim(str);
	   	var re = '';
     	for(var i=0;i<forbiddenArray.length;i++){
           if(i==forbiddenArray.length-1){
               re+=forbiddenArray[i];
           }else{
        	   re+=forbiddenArray[i]+"|";
           }
	   }
         //定义正则表示式对象
         //利用RegExp可以动态生成正则表示式
         var pattern = new RegExp(re,"g");
         if(pattern.test(str)){
             return false;
         }else{
             return true;
         }
     }

	function chkStrFromBrackets(str){
	    var pattern =new RegExp("\\{(.| )+?\\}","igm");;
		var numArray=str.match(pattern);
		if(numArray!=null){
			
			for(var i=0;i<numArray.length;i++){
				numArray=numArray[i].substring(1,numArray[i].length-1);
				if(isNaN(numArray)){
					return false;
				}
			}
		}
		return true;
	}
	
	function fn_focus(ele){
		var reg = /^[\s]*$/;
		ele.style.border = 'solid 1px rgb(2,185,239)';
		if(ele.value == ele.defaultValue){
			ele.value = '';
			ele.style.color = 'rgb(51,51,51)';
		}
	}
	function fn_blur(ele){
		var reg = /^[\s]*$/;
		ele.style.border = 'solid 1px rgb(213,213,213)';
		if( reg.test(ele.value) || ele.value == ele.defaultValue){
			ele.value = ele.defaultValue;
			ele.style.color = 'rgb(159,159,159)';
		}
	}
	
	function displayContent(){
		var content=$("#content").val();
		if ( isBigCustomer=='0' ){
			//sig=sig.replace(/\ +/g,"");//去掉空格
			content=content.replace(/\ +/g,"");//去掉空格
			content=content.replace(/[\r\n]/g,"");//去掉回车换行
		} 
		content=content.replace("【","");
		content=content.replace("】","");
		$("#content").val(content);
		/* if(!chkHalf(content)){
			content=toCDB(content);
			$("#templateContent").val(content);
		} */
		//if(content!=''&&iGetInnerText(content)){
			//$("#content").val("【"+sig+"】"+content); 
//		}
	}


	function doSubmit(){
		var smsTempType=$('input[name="type"]:checked').val();

		if($("#smsTemplateAddForm").valid()){
			if(smsTempType==1){
				var forbiddenArray =['验证码','动态码','激活码','校验码','驗證碼','注册码','登录密码','手机动态码'];
				if(!forbiddenStr($("#content").val(),forbiddenArray)){
					alert("您提交的模版可能属于验证码类，请正确选择模版类型或修改模版内容。若有疑问请联系客服咨询");
					return false;
				}
			}
			if(!chkStrFromBrackets($("#content").val())){
				alert("内容中{}中内容必须为数字");
				return false;
			}
			displayContent();
			//$.colorbox({href:"/member/smsTemplate/preAddTip?m="+Math.random()});
			//smsTemplateAddForm.submit();
			
			var data = $('#smsTemplateAddForm').serialize();
			$.ajax({  
		        type: "post",  
		        url: "${ctxFront}/smsTemplate/save",
		        data: data,  
		        dataType: 'json',  
		        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
				success : function(result) {
					if(result.code == "0"){
						alert("新增短信模板成功");
						window.location.reload();
					}else{
						alert("新增短信模板失败");
					}
				},
				error : function(){
					alert("新增短信模板失败");
				}
		    });
		}
	}
	</script>
</head>
<body>
	<div class="tit"><h1>新增模板</h1></div>
   	<div class="tx_div">
    	<h2>使用规则：</h2>
       	<p>
       	<!-- 1、提交模板前请仔细阅读文档：<a href="http://www.yuntongxun.com/doc/rest/sms/3_2_2_1.html" target="_blank" style="color:#44caf9;">模板短信</a><br/>
       	2、短信模版只能在当前归属应用下使用，不可跨应用使用<br/>
       	3、模板审核通过后才可调用，受运营商限制，每个工作日9点统一提交给运营商审核，正常当日即可获得结果 -->
       	1、模板审核通过后才可调用，受运营商限制，每个工作日9点统一提交给运营商审核，正常当日即可获得结果
       	</p>
    </div>
    <form action="/member/smsTemplate/add" name="smsTemplateAddForm" id="smsTemplateAddForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="scope" id="scope" value="1"/>
		<input type="hidden" name="status" id="status" value="-1"/>
		<input type="hidden" name="id" id="id"/>
		<div class="newsms_temp">
	        <div class="newsms_div2">
	        	<dl class="dl1 clearfix">
                    <dt>模板名称：</dt>
                    <dd><input name="name" id="name" type="text" class="ltxt ys" value="模板备注用，只做识别使用" onfocus="fn_focus(this);" onblur="fn_blur(this);"/></dd>
                </dl>
                <dl class="dl1 clearfix">
                    <dt>模板签名：</dt>
                    <dd><input name="sign" id="sign" type="text" class="ltxt ys" onfocus="fn_focus(this);" onblur="fn_blur(this);"/></dd>
                </dl>
                
       			<!-- 内容 start-->
       			<dl class="dl1 clearfix no_margin">
                   <dt class="dt1">模板内容：</dt>
                   <dd style="width:548px;">
                       <textarea class="txtarea" name="content" id="content" onkeyup="displayContent()"></textarea>
                       <!-- <p class="graytips">模板有疑问？<a href="/member/smsTemplate/toSmsTempConsult" class="link">使用参考模板</a></p> -->
                       <span class="error" id="smsContentError" style="display:none"></span>
                   </dd>
             	</dl>
           		<dl class="dl1 clearfix">
                	<dt>&nbsp;</dt><dd><p class="info">示例：您的验证码是{1}，请于{2}分钟内正确输入<br/><em class="red">模板内容必须为半角字符,全角字符将会影响审核和短信下发,其中的数字必须从1开始顺序排列</em><br/>模版内容不可含有空格、换行、以及【】，否则自动删除非法字符</p></dd>
            	</dl>
            	<!-- 内容end -->
            	
            	<dl class="dl1 clearfix">
                	<dt>模板类型：</dt>
                    <dd>
                    	<input type="radio" class="radio" name="type" checked="checked" value="1"/>
                    		<label class="label">行业短信</label>
                    	<input type="radio" class="radio" name="type"  value="2"/>
                    		<label class="label">验证码</label>
                    	<input type="radio" class="radio" name="type"  value="3"/>
                    		<label class="label">营销短信</label>
                    	<input type="radio" class="radio" name="type"  value="4"/>
                    		<label class="label">群发短信</label>
                    </dd>
                </dl>
                
                <dl class="dl1 clearfix">
                    <dt>&nbsp;</dt>
                    <dd>
                        <a href="javascript:void(0);" type="button" class="btn_sure c2" title="提交信息确认" onclick="doSubmit()">提交</a>
                        <input type="reset" class="btn_reset c5" value="取消"/>
                    </dd>
                </dl>
          </div>
      	</div>
   	</form>
</body>
</html>