<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户基础资料</title>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/main.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/jquery.colorbox.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/My97DatePicker/WdatePicker.js"></script>
    <%-- <script type="text/javascript" src="${ctxFrontStatic }/js/member.js"></script>
    <script type="text/javascript" src="${ctxFrontStatic }/js/countCode.js"></script> --%>
    <link rel="stylesheet" href="${ctxFrontStatic }/css/colorbox.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/style.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/commonstyle.css" type="text/css" />
    <link rel="stylesheet" href="${ctxFrontStatic }/css/account_infor.css" type="text/css" />
    <link href="${ctxFrontStatic }/css/default.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="${ctxFrontStatic }/js/cn.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/core.js"></script>
	<link href="${ctxFrontStatic }//css/def.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="${ctxFrontStatic }/js/combo.js"></script>
	<%-- <script type="text/javascript" src="${ctxFrontStatic }/js/layer/layer.js"></script> --%>
    <script type="text/javascript">
	$(document).ready(function() { 
		getList();
		getBalanceList();
	}); 
	
	function getList()
	{
		$.ajax({  
	        type: "post",  
	        url: "${ctxFront}/user/userInfo",
	        dataType: 'json',  
	        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
			success : function(result) {
				$('.clearfix dl:eq(0)').find('em').html(result.data.id);
				/* if (result.data.delFlag == '0'){
					$('.clearfix dl:eq(0)').find('em').html('正常');
				}
				else{
					$('.clearfix dl:eq(0)').find('em').html('删除');
				} */
				
				/* if (result.data.account.noCheck == '0'){
					$('.clearfix dl:eq(2)').find('em').html('手动审核');
				}
				else if (result.data.noCheck == '0'){
					$('.clearfix dl:eq(2)').find('em').html('免审');
				}
				else{
					$('.clearfix dl:eq(2)').find('em').html('自动审核');
				}
				
				if (result.data.account.signFlag == '0'){
					$('.clearfix dl:eq(3)').find('em').html('不校验');
				}
				else{
					$('.clearfix dl:eq(3)').find('em').html('校验');
				} */
				
				/**$('.clearfix dl:eq(2)').find('em').html(result.data.account.apikey);
				$('.clearfix dl:eq(3)').find('em').html(result.data.account.whiteIP);
				$('.clearfix dl:eq(4)').find('em').html(result.data.account.callbackUrl);
				$('.clearfix dl:eq(5)').find('em').html(result.data.account.upUrl);
				$('.clearfix dl:eq(6)').find('em').html(result.data.companyName);**/
				$('.clearfix dl:eq(2)').find('em').html(result.data.contactName);
				$('.clearfix dl:eq(3)').find('em').html(result.data.email);
				$('.clearfix dl:eq(4)').find('em').html(result.data.mobile);
				$('.clearfix dl:eq(5)').find('em').html(result.data.qq);
				/* $('.clearfix dl:eq(13)').find('em').html('IP：' + result.data.oldLoginIp + '&nbsp;&nbsp;&nbsp;&nbsp;时间：' + result.data.oldLoginDate); */
				$('.clearfix dl:eq(6)').find('em').html(result.data.oldLoginIp);
				$('.clearfix dl:eq(7)').find('em').html(result.data.oldLoginDate);
			},
			error : function(){
			}
	    });
	}
	
	function getBalanceList()
	{
		$.ajax({  
	        type: "post",  
	        url: "${ctxFront}/user/balance",
	        dataType: 'json',  
	        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
			success : function(result) {
				$('.clearfix dl:eq(1)').find('em').html(result.data);
			},
			error : function(){
			}
	    });
	}
	</script>
</head>
<body>
	<form action="" name="editUserForm" id="editUserForm" method="post">
		<div class="tit">
			<h1>基础资料</h1>
		</div>
		<div class="div3">
			<div class="tit2">
				<h1 class="bg10">基础信息</h1>
			</div>
			<div class="div31 clearfix">
				<!-- <dl>
					<dt>账号状态</dt>
					<dd>
						<em ></em>
					</dd>
				</dl> -->
				<dl>
					<dt>用户ID</dt>
					<dd>
						<em ></em>
					</dd>
				</dl>
				<dl>
					<dt>当前余额</dt>
					<dd>
						<em ></em>
					</dd>
				</dl>
				<!-- <dl>
					<dt>免审标识</dt>
					<dd>
						<em ></em>
					</dd>
				</dl>
				<dl>
					<dt>是否校验签名</dt>
					<dd>
						<em ></em>
					</dd>
				</dl> -->
				<!-- <dl>
					<dt>APIKEY</dt>
					<dd>
						<em ></em>
					</dd>
				</dl>
				<dl>
					<dt>绑定IP</dt>
					<dd>
						<em ></em>
					</dd>
				</dl>
				<dl>
					<dt>状态报告回调地址</dt>
					<dd>
						<em ></em>
					</dd>
				</dl>
				<dl>
					<dt>上行短信地址</dt>
					<dd>
						<em ></em>
					</dd>
				</dl>
				<dl>
					<dt>机构名称</dt>
					<dd>
						<em ></em>
					</dd>
				</dl> -->
				<dl>
					<dt>联系人</dt>
					<dd>
						<em ></em>
					</dd>
				</dl>
				<dl>
					<dt>注册邮箱</dt>
					<dd>
						<em ></em>
					</dd>
				</dl>
				<dl>
					<dt>注册手机号</dt>
					<dd>
						<em ></em>
					</dd>
				</dl>
				<dl>
					<dt>QQ</dt>
					<dd>
						<em ></em>
					</dd>
				</dl>
				<dl>
					<dt>上次登录IP</dt>
					<dd>
						<em ></em>
					</dd>
				</dl>
				<dl>
					<dt>上次登录时间</dt>
					<dd>
						<em ></em>
					</dd>
				</dl>
			</div>
		</div>
		<!-- <input type="button" id="save_user_btn" class="btn_save c2"	value="保存" /> -->
	</form>
</body>
</html>