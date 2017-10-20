<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>索取发票</title>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/main.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.colorbox.js"></script>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/colorbox.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/style.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/commonstyle.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/fapiao.css" type="text/css" />
    <link href="${ctxFrontStatic }/css/default.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="${ctxFrontStatic }/js/core.js"></script>
    <link href="${ctxFrontStatic }/css/def.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="${ctxFrontStatic }/js/combo.js"></script>
    <script type="text/javascript">
        var cb_titleList;
        var cfg = {
            keyField: 'code',
            displayField: 'name',
            multiSelect: false,
            width: 202,
            boxWidth: 202,
            cols : [
                {
                    field: 'name', width: '98%'
                }],
            onSelect:function(){
                getInvoiceAmountByTitle();
            }
        };
        $(function() {
        	
        	$.ajax({
       			type: "POST",
                url:"${ctxFront}/user/getAmount",
                dataType: "json",
                success: function(result){
               	 
              	    if(result.data){
              		 $('#yuInvoiceAmount').html(parseFloat(result.data/100));
              		 $("#yuamount").val(result.data);
              	   }
                },
                error:function(){
        			alert("系统错误");
        		 }
           	});
        	
        	
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
            //radio
            $('.mm').click(function(){
                $(this).parent().addClass('radio-off-on');
                $('.sim').parent().removeClass('radio-off-on');
                //$('.zy_list').hide();
                setHeight();
            });
            $('.sim').click(function(){
                $(this).parent().addClass('radio-off-on');
                $('.mm').parent().removeClass('radio-off-on');
                //$('.zy_list').show();
                setHeight();
            });

            $('.xx').click(function(){
                $(this).parent().addClass('radio-off-on');
                $('.js').parent().removeClass('radio-off-on');
                $('.xxjs').parent().removeClass('radio-off-on');
                setHeight();
            });
            $('.js').click(function(){
                $(this).parent().addClass('radio-off-on');
                $('.xx').parent().removeClass('radio-off-on');
                $('.xxjs').parent().removeClass('radio-off-on');
                setHeight();
            });

            $('.xxjs').click(function(){
                $(this).parent().addClass('radio-off-on');
                $('.xx').parent().removeClass('radio-off-on');
                $('.js').parent().removeClass('radio-off-on');
                setHeight();
            });
             
            $('.radio').hover(function(){
                $(this).parent().addClass('radio-off-hover');
            },function(){
                $(this).parent().removeClass('radio-off-hover');
            });

            $('.mm_before').click(function(){
                $(this).parent().addClass('radio-off-on');
                $('.sim_after').parent().removeClass('radio-off-on');
                $('.state').hide();
                $('.state_before').show();
                setHeight();
            });
            $('.sim_after').click(function(){
                $(this).parent().addClass('radio-off-on');
                $('.mm_before').parent().removeClass('radio-off-on');
                $('.state').show();
                $('.state_before').hide();
                setHeight();
            });
            //alert($("#rechargeType").val());
            if($("#rechargeType").val()=='2'){
                appendRemitTitles();
            }else{
                $("#titleList_dl").hide();
                $("#headerInfo").removeAttr("readonly");
            }
            
            
        });

        function appendRemitTitles(){
            var dd_titleList = [];
            var titleList=$("#remitTitles").val();
            var titleArray=titleList.split("#");
            if(titleArray!=null&&titleArray.length>0){
                dd_titleList.push({ code:"-1",name:"选择发票抬头"});
                for(var i=0;i<titleArray.length;i++){
                    var title=titleArray[i];
                    dd_titleList.push({ code:title,name:title});
                }
                var cfg_titleList = $.extend({data: dd_titleList}, cfg);
                cb_titleList = $('#combo_titleList').mac('combo', cfg_titleList);
                $("#titleList_dl").show();
                $("#headerInfo").attr("readonly","readonly");
            }
        }
        //排序
        function changeOrderType() {
            var orderType = $("#orderType").val();
            if (orderType == "desc") {
                $("#orderType").val("asc");
            } else {
                $("#orderType").val("desc");
            }
            invoviceForm.submit();
        }
        //添加发票信息 成功
        function addInfo(){
            $().colorbox({
                title : "索取发票",
                html : '<div id="pop_record1" class="popbox1"><div class="pop_con_div">'
                + '<p>索取发票任务已经创建，请等待工作人员处理，您可以在该页面看到后续状态变更进度</p></div><div class="btns">'
                + '<form action="/manage/invovice/getInvoviceInfoByPage" method="post">'
                + '<input type="submit" value="确 定" class="btn_sure c2" />'
                + '<input type="reset" value="取 消" class="btn_reset c5" onclick="$.fn.colorbox.close()"/></form></div></div>'
            });

        }

        //添加发票失败
        function addFalseOld(){
            $().colorbox(
                {
                    title : "索取发票",
                    html : '<div id="pop_record2" class="popbox1"><div class="pop_con_div">'
                    + '<p>您输入的「开具发票金额」不符合条件或您选择的「发票类型」不符合条件</p></div><div class="btns">'
                    + '<form action="/manage/invovice/getInvoviceInfoByPage" method="post">'
                    + '<input type="button" value="继续修改" class="btn_sure c2" onclick="$.fn.colorbox.close()" />'
                    + '<input type="submit" value="取 消创建" class="btn_reset c5" onclick="$.fn.colorbox.close()"/></form></div></div>'
                });
        }
        
        //添加发票失败
        function addFalse(){
			alert("您输入的「开具发票金额」不符合条件或您选择的「发票类型」不符合条件");
        }


      
        $(document).ready(function(){
            $("#invoviceInfo").validate({
                errorElement : "em",
                errorClass : "error",
                rules : {
                    nsrsbh:{
                        required:true
                    },
                    edAmount:{
                        required:true,
                        isMoney:true
                    },
                    collectAddress:{
                        required:true,
                        byteRangeLength:[0, 200]
                    },
                    addressee:{
                        required:true,
                        onlyLetterAndChinese:true,
                        byteRangeLength:[0, 20]
                    },
                    mobile:{
                        required:true,
                        isMobile: true
                    }

                },
                messages : {
                    nsrsbh:{
                        required:"请输入纳税人识别号"
                    },
                    edAmount:{
                        required: "请输入开具发票金额"
                    },
                    collectAddress : {
                        required : "请输入收件人地址",
                        byteRangeLength : "地址只能在{0}-{200}位字符之间！"
                    },

                    addressee : {
                        required: "请输入收件人姓名",
                        byteRangeLength : "姓名只能在{0}-{20}位字符之间！"
                    },
                    mobile : {
                        required: "请输入手机号码"
                    }

                },
                errorPlacement: function (error, element){
                    error.appendTo(element.parent());
                    error.css('color','red');
                }
            }); 
 
            $("input[name='invoiceType']").click(function(){
                if ($("input[name='invoiceType']:checked").val()==2) {
                    //$("#nsrsbh").rules("add", { required: true,messages:{required: "纳税人识别号不能为空"} });
                    $("#bankNumber").rules("add", { required: true,onlyDigit:true,byteRangeLength:[1, 30],messages:{required: "银行账号不能为空",onlyDigit:"只能输入数字",byteRangeLength : "银行卡号只能在1-30位数字之间！"} });
                    $("#depositBank").rules("add", { required: true,messages:{required: "开户行不能为空"} });
                    $("#regAddress").rules("add", { required: true,byteRangeLength:[0, 200],messages:{required: "企业注册地址不能为空",byteRangeLength:"企业注册地址输出超出范围"} });
                    $("#phone").rules("add", { required: true,MobileOrTel:true,messages:{required: "企业联系电话不能为空",MobileOrTel:"请输入正确的电话号码"} });
                }else{
                    //$("#nsrsbh").rules("add", { required: false});
                    $("#bankNumber").rules("add", { required: false,onlyDigit:false});
                    $("#depositBank").rules("add", { required: false });
                    $("#regAddress").rules("add", {required: false});
                    $("#phone").rules("add", { required: false});
                    $("#nsrsbh").val("");
                     $("#bankNumber").val("");
                     $("#depositBank").val("");
                     $("#regAddress").val("");
                     $("#phone").val("");
                }

            });

            //var lastInvoiceTitle=$("#lastInvoiceTitle").val();
           // var identityType=0;
           // if(lastInvoiceTitle!=""&&identityType=='2'){
                $("#headerInfo").rules("add", { required: true,byteRangeLength:[0, 100],messages:{required: "发票抬头地址不能为空",byteRangeLength:"发票抬头输出超出范围"} });
          //  }
        });

        function doApplyInvoice(){
        	if($("#invoviceInfo").valid()){
	        	//可索取发票金额
	            var amount = parseFloat($("#yuamount").val()/100);
	            //开具发票金额
	            var amount_= parseFloat($("#edAmount").val());
	            //获取发票类型
	            var invoiceTypes = $("input[name='invoiceType']:checked").val();
	            
	            
	            
	            //获取个人还是企业
	            var identityTypes=1;//默认个人
	            //个人
	            if((identityTypes=='1') && (invoiceTypes=='1' || invoiceTypes=='2')){
	            	if(amount_>=parseFloat(1000) && amount>=amount_){
	            		onSubmit();
	                }else{
	                    addFalse();
	                }
	            }
	            //企业
	            else if(identityTypes=='2') {
	                //如果开取的发票类型是普通发票 必须大于1000
	                if(invoiceTypes=='1'){
	                    if(amount_>=parseFloat(1000) && amount>=amount_){
	                    	onSubmit();
	                    }else{
	                        addFalse();
	                    }
	                    //如果是增值税发票,发票金额 必须大于10000
	                }else if(invoiceTypes=='2'){
	                    if(amount_>=parseFloat(10000) && amount>=amount_){
	                    	onSubmit();
	                    }else{
	                        addFalse();
	                    }
	                }
	            }else {
	                addFalse()
	            }
        	}
        }
        
        function onSubmit(){
        	var data = $('#invoviceInfo').serialize();
			$.ajax({  
		        type: "post",  
		        url: "${ctxFront}/user/applyForInvoice",
		        data: data,  
		        dataType: 'json',  
		        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
				success : function(result) {
					if(result.code == "0"){
						alert("索取发票任务已经创建，请等待工作人员处理，您可以在[开票记录]看到后续状态变更进度");
						window.location.href="${ctxFront}/view/invoiceList";
					}else{
						alert("索取发票失败");
					}
				},
				error : function(){
					alert("索取发票失败");
				}
		    });
				
        }

        function isTakeCarryInvoice(isInvocieCarry){
            if(isInvocieCarry==1){//开具结转
                getCarryInvoiceAmount(1);
            }else{//不开具结转
                getCarryInvoiceAmount(0);
            }
        }

        function getCarryInvoiceAmount(isCarry){
            if(isCarry==1){
                $.ajax({
                    type : "POST",
                    url:"/manage/invovice/getCarryInvoiceAmount?isCarry=1",
                    dataType:"json",
                    async:false,
                    success : function(data) {
                        $("#yuInvoiceAmount").text(data.yuInvoiceAmount);
                        $("#edAmount").val(data.yuInvoiceAmount);
                        $("#isHasRemit").val("1");
                        $("#isInvocieCarry").val("1");
                        if(data.headerInfo!=""){
                            $("#headerInfo").val(data.headerInfo);
                            $("#lastInvoiceTitle").val(data.headerInfo);
                            $("#headerInfo").attr("readonly","readonly");
                            $("#rechargeType").val(data.rechargeType);
                        }else{
                            $("#headerInfo").removeAttr("readonly");
                            $("#rechargeType").val(data.rechargeType);
                        }
                    }
                });
            }else{
                $.ajax({
                    type : "POST",
                    url:"/manage/invovice/getCarryInvoiceAmount?isCarry=0",
                    dataType:"json",
                    async:false,
                    success : function(data) {
                        $("#yuInvoiceAmount").text(data.yuInvoiceAmount);
                        $("#edAmount").val(data.yuInvoiceAmount);
                        $("#edAmount").removeAttr("readonly");
                        $("#isHasRemit").val("0");
                        $("#isInvocieCarry").val("0");
                        $("#rechargeType").val(data.rechargeType);
                        if(data.rechargeType=='2'){
                            appendRemitTitles();
                            $("#titleList_dl").show();
                            $("#headerInfo").attr("readonly","readonly");
                        }else{
                            $("#headerInfo").val(data.headerInfo);
                            $("#lastInvoiceTitle").val(data.headerInfo);
                            $("#headerInfo").removeAttr("readonly");
                        }
                    }
                });
            }
        }
        function getInvoiceAmountByTitle(){
            var title=cb_titleList.selected;
            if(title!="-1"){
                $.ajax({
                    type : "POST",
                    url:"/manage/invovice/getInvoiceAmountByTitle",
                    data:"headerInfo="+title,
                    dataType:"json",
                    async:false,
                    success : function(data) {
                        $("#yuInvoiceAmount").text(data.maxAmountForTitle);
                        $("#edAmount").val(data.maxAmountForTitle);
                        $("#headerInfo").val(title);
                        $("#B").val(data.B);
                    }
                });
            }else{
                $("#headerInfo").val("");
            }
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
            <div class="tit">
                <h1>发票管理</h1>
            </div>
            <div class="tx_div">
                <p>发票说明<br/>
                    1、开票条件：个人开发者可申请普通发票(一千元起)，企业开发者可申请普通发票(一千元起)或增值税专用发票(一万元起)<br/>
                    2、因税务机关账务结算原因，每月25号期前提交的开票申请当月受理，之后申请延期至下月受理<br/>
                    3、请仔细核对发票抬头、金额、税号、地址及其他相关信息，因为信息填写错误导致的发票开具、邮寄出错，将不能重开<br/>
                    4、发票开具后，除发生开票有误、发票抵扣联、发票联均无法认证等情形外，不予退换
                </p>
            </div>
            <!--如果用户没有充值记录，开票系统升级这部分是不显示的-->

            <div class="invoice_sum">
                <div class="invoice_l1">
                    <em>可索取发票金额：</em> <span><i id="yuInvoiceAmount">0</i>元</span>
                </div>
            </div>

            <div class="invoice_info">
                <form method="post" action="${ctxFront}/user/applyForInvoice" name="invoviceInfo" id="invoviceInfo">
                <input type="hidden" name="yuamount" id="yuamount" value="0" />
                    <div class="invoice1">
                        <dl class="dl1 clearfix">
                            <dt>开具发票金额</dt>
                            <dd>
                            <dd><span class="money"><input type="text" class="textc" id="edAmount" name="edAmount"  value="0" /><i class="tip">元</i></span></dd>
                            </dd>

                        </dl>
                        <dl class="dl1 clearfix" id="titleList_dl" style="display:none">
                            <dt>曾用汇款抬头</dt>
                            <dd>
								<span class="firm_name">
									<div id="combo_titleList" class="combo" style="width:202px;"></div>
								</span>
                            </dd>
                        </dl>
                        <dl class="dl1 clearfix">
                            <dt>发票抬头信息</dt>
                            <dd>
								<span class="firm_name">
								
									<input type="text" class="textl" name="headerInfo"  value ="" id="headerInfo"/>提交后不可更改，请谨慎填写
								</span>
                            </dd>
                        </dl>
                        <dl class="dl1 clearfix">
                            <dt>发票类型</dt>
                            <dd>
                                <div class="radio-off radio-on radio-off-on">
                                    <input type="radio" class="radio mm" name="invoiceType" value="1" checked="checked"  />
                                </div>
                                <label>普通发票</label>
                                <div class="radio-off">
                                    <input type="radio" class="radio sim" name="invoiceType" value="2" id="zengzhi"/>
                                </div>
                                <label>增值税专用发票</label>
                            </dd>
                        </dl>
                        <dl class="dl1 clearfix">
                            <dt>发票内容</dt>
                            <dd>
                                <div class="radio-off radio-on radio-off-on">
                                    <input type="radio" class="radio xx" name="invoiceContent" value="1" checked="checked"  />
                                </div>
                                <label>信息服务费</label>
                                <div class="radio-off">
                                    <input type="radio" class="radio js" name="invoiceContent" value="2"/>
                                </div>
                                <label>技术服务费</label>
                                <div class="radio-off">
                                    <input type="radio" class="radio xxjs" name="invoiceContent" value="3"/>
                                </div>
                                <label>信息技术服务费</label>
                            </dd>
                        </dl>
                        <dl class="dl1 clearfix">
                            <dt>纳税人识别号</dt>
                            <dd>
                            <dd><span class="firm_name"><input type="text" class="textc" name="nsrsbh"  value =""  id="nsrsbh"/></span></dd>
                            </dd>
                        </dl>
                        <ul class="zy_list" style="display:block">

                            <li>
                                <label class="label">银行账号</label>
                                <input type="text" class="textc"  style="width:280px;" name="bankNumber"  value =""  id="bankNumber"/>
                            </li>
                            <li>
                                <label class="label">开户行</label>
                                <span class="money"><input type="text" class="textl" name=depositBank value =""  id="depositBank"/><i class="tip">具体到支行</i></span>
                            </li>
                            <li>
                                <label class="label">注册地址</label>
                                <span class="money"><input type="text" class="textl" name="regAddress"  value =""  id="regAddress"/><i class="tip">填写税务登记注册地址</i></span>
                            </li>
                            <li>
                                <label class="label">企业电话</label>
                                <span class="money"><input type="text" class="textl" name="phone"  value =""  id="phone"/></span>
                            </li>
                        </ul>
                    </div>
                    <div class="invoice2">
                        <dl class="dl1 clearfix">
                            <dt>收取地址</dt>
                            <dd>
                                <input type="text" class="textl" name="collectAddress"  value =""  id="collectAddress"/>请规范填写：省市区(县)等信息
                            </dd>
                        </dl>
                        <dl class="dl1 clearfix">
                            <dt>收件人</dt>
                            <dd>
                                <input type="text" class="texts" name="addressee" id="addressee" value ="" />
                            </dd>
                        </dl>
                        <dl class="dl1 clearfix">
                            <dt>手机号</dt>
                            <dd>
                                <input type="text" class="textc" name="mobile"  value ="" />
                            </dd>
                        </dl>
                        <div class="searchDiv"style="text-align: center">
                            <input class="btn_search" type="button" onclick="doApplyInvoice();" value="提交" />
                            <input class="btn_search btn_color" type="button" onclick="window.location.reload();" value="取消" />
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>

</html>