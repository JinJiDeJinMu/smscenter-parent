<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>短信发送接口</title>
	<link rel="stylesheet" href="${ctxFrontStatic }/css/wiki_wendang.css" type="text/css" />
	<link href="${ctxFrontStatic }/css/prettify.css" rel="stylesheet">
	<script type="text/javascript" src="${ctxFrontStatic }/js/prettify.js"></script>
	<script type="text/javascript" src="${ctxFrontStatic }/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript">
	</script>
</head>
<body>
	<div class="wrap">
		<div class="txt_r">
        	<div class="wiki_content A_kfzn">
            	<h2 class="wiki_t">>> 1 模板短信接口请求方式</h2>
				<h3 class="wiki_ts pt">1.1 接口简介 </h3>
				<br/>
				<p class="wiki_con">模板短信API引用的地址有Base URL。</p>
				<p class="wiki_con">生产环境的Base URL：http://183.129.129.134:8081/api</p>
				<p class="wiki_con">接口使用HTTP协议，通过POST方法进行接口调用。</p>
				
				<h2 class="wiki_t">>> 2 短信发送接口（MT）</h2>
				<h3 class="wiki_ts pt">2.1  模板发送接口定义</h3>
				<br/>
				<table id="mt" style="margin-top: 0px; margin-right: 0px; margin-bottom: 15px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font: inherit; vertical-align: baseline; border-collapse: collapse; -webkit-border-horizontal-spacing: 0px; -webkit-border-vertical-spacing: 0px; color: rgb(68, 68, 68); font-family: HelveticaNeue-Regular, 'Helvetica Neue Regular', 'Helvetica Neue', Arial, sans-serif; height: 16px; width: 100%" border="1">
					<!-- <tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline; background-color: rgb(248, 248, 248)">
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; vertical-align: baseline; width: 76px; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							接口名称
						</th>
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; vertical-align: baseline; width: 32px; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							<span style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
							短信发送接口
							</span>
						</th>
					</tr> -->
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							接口URL<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							/template/sms/send
						</td>
					</tr>
					
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							请求方式<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top">
							POST
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							参数格式<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top">
							JSON
						</td>
					</tr>
				</table>
				
				<h3 class="wiki_ts pt">2.2 输入参数</h3>
				<br/>
				<table id="transactions" style="margin-top: 0px; margin-right: 0px; margin-bottom: 15px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font: inherit; vertical-align: baseline; border-collapse: collapse; -webkit-border-horizontal-spacing: 0px; -webkit-border-vertical-spacing: 0px; color: rgb(68, 68, 68); font-family: HelveticaNeue-Regular, 'Helvetica Neue Regular', 'Helvetica Neue', Arial, sans-serif; height: 16px; width: 100%" border="1">
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline; background-color: rgb(248, 248, 248)">
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; vertical-align: baseline; width: 76px; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							字段名称
						</th>
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; vertical-align: baseline; width: 76px; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							是否必填
						</th>
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; vertical-align: baseline; width: 32px; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							<span style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
							说明
							</span>
						</th>
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; width: 25px; white-space: nowrap; vertical-align: baseline; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							数据类型
						</th>
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; vertical-align: baseline; width: 381px; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							<span style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
							长度
							</span>
						</th>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							userid<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							必填<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							用户编号，接入方信息唯一标识
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							由平台方分配<br />
						</td>
					</tr>
					
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							appid<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							必填<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							应用id,接入方信息唯一标识
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							由平台方分配<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							ts<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							必填<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							时间戳, 5分钟内有效, 时间戳是指格林威治时间1970年01月01日00时00分00秒起至现在的总毫秒数
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							Long<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							System.currentTimeMillis()<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							sign<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							必填<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							<p class="pt">MD5用32位，小写</p>
							<p class="pt">例：md5(userid + appid + ts + apikey)</p>
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							apikey: 由平台方分配<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							mobile<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							必填<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
						     需要发送的手机号,只支持单个手机号码
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							13800000000<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							templateid<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							必填<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							<p class="pt">模板ID</p>
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							<p class="pt">创建模板后，在后台获取</p>
						</td>
					</tr>
					
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							templateparam<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							必填<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							<p class="pt">模板参数</p>
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							<p class="pt">[“123456”,”4”]</p>
						</td>
					</tr>
					
					
					
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							extnum<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							选填<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							下发扩展号（0-6位）
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							最长6位<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							time<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							选填<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							发送时间(为空表示立即发送，如果定时发送，则需要按yyyyMMddHHmmss格式，如：20110115105822)
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							yyyyMMddHHmmss<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							messageid<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							选填<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							客户侧唯一消息ID,状态报告推送接口原样带回。
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							最长32位<br />
						</td>
					</tr>
				</table>
				
				<h3 class="wiki_ts pt">2.3 输出参数</h3>
				<br/>
				<table id="transactions" style="margin-top: 0px; margin-right: 0px; margin-bottom: 15px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font: inherit; vertical-align: baseline; border-collapse: collapse; -webkit-border-horizontal-spacing: 0px; -webkit-border-vertical-spacing: 0px; color: rgb(68, 68, 68); font-family: HelveticaNeue-Regular, 'Helvetica Neue Regular', 'Helvetica Neue', Arial, sans-serif; height: 16px; width: 100%" border="1">
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline; background-color: rgb(248, 248, 248)">
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; vertical-align: baseline; width: 76px; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							字段名称
						</th>
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; vertical-align: baseline; width: 32px; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							<span style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
							说明
							</span>
						</th>
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; width: 25px; white-space: nowrap; vertical-align: baseline; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							数据类型
						</th>
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; vertical-align: baseline; width: 381px; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							<span style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
							长度
							</span>
						</th>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							code<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							状态码
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							10<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							msg<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							状态描述
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							50<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							data<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							数据节点
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							Object<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							taskid<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							任务ID,接口返回的taskid，如果接口返回非0，则不返回data节点
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							最长32位<br />
						</td>
					</tr>
				</table>
				<h3 class="wiki_ts pt">2.4请求示例</h3>
				<br/>
				<div class="wiki_pre">
		        	<pre class="prettyprint pre">
		    {"userid":"10000", "appid":"27","ts":"1500965695932", "sign":"fcaadfc82c995f74aa75f493327fce6e", "mobile":"13666672546", "templateid":"14", "templateparam":["123456","10"]}
		            </pre>
		        </div>
				<h3 class="wiki_ts pt">2.5响应示例</h3>
				<br/>
				<p class="wiki_con">说明：taskeid是字符串格式，长度最大32位，code=0时才会有data节点和taskid字段， 单次请求只会返回一个taskid。</p>
				<br/>
				<p class="wiki_con">成功返回示例：</p>
				<div class="wiki_pre">
		        	<pre class="prettyprint pre">
		    {“code”:”0”,”msg”:”成功”,”data”:{“taskid”:”0725123213123123123”}}
		            </pre>
		        </div>
				<br/>
				<p class="wiki_con">失败返回示例：</p>
				<div class="wiki_pre">
		        	<pre class="prettyprint pre">
		    {“code”:”7”,”msg”:” IP校验失败”}
		            </pre>
		        </div>
				
				
				
				<h2 class="wiki_t">>> 3 余额查询接口定义）</h2>
				<h3 class="wiki_ts pt">3.1 接口定义</h3>
				<br/>
				<table id="mt" style="margin-top: 0px; margin-right: 0px; margin-bottom: 15px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font: inherit; vertical-align: baseline; border-collapse: collapse; -webkit-border-horizontal-spacing: 0px; -webkit-border-vertical-spacing: 0px; color: rgb(68, 68, 68); font-family: HelveticaNeue-Regular, 'Helvetica Neue Regular', 'Helvetica Neue', Arial, sans-serif; height: 16px; width: 100%" border="1">
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							接口URL：/template/sms/balance<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							<p class="pt">GET</p>
						</td>
					</tr>
					
				</table>
				
				<h3 class="wiki_ts pt">3.2 输入参数</h3>
				<br/>
				<table id="transactions" style="margin-top: 0px; margin-right: 0px; margin-bottom: 15px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font: inherit; vertical-align: baseline; border-collapse: collapse; -webkit-border-horizontal-spacing: 0px; -webkit-border-vertical-spacing: 0px; color: rgb(68, 68, 68); font-family: HelveticaNeue-Regular, 'Helvetica Neue Regular', 'Helvetica Neue', Arial, sans-serif; height: 16px; width: 100%" border="1">
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline; background-color: rgb(248, 248, 248)">
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; vertical-align: baseline; width: 76px; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							字段名称
						</th>
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; vertical-align: baseline; width: 32px; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							<span style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
							说明
							</span>
						</th>
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; width: 25px; white-space: nowrap; vertical-align: baseline; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							数据类型
						</th>
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; vertical-align: baseline; width: 381px; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							<span style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
							长度
							</span>
						</th>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							userid<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							用户编号，接入方信息唯一标识
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							用户在平台上分配的用户编号<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							appid<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							应用ID，接入方信息唯一标识
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							用户在平台上分配的appid<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							ts<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							时间戳, 5分钟内有效, 时间戳是指格林威治时间1970年01月01日00时00分00秒起至现在的总毫秒数
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							Long<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							System.currentTimeMillis()<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							sign<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							MD5用32位，小写
例：md5(userid + appid + ts + apikey)
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							apikey:由系统分配<br />
						</td>
					</tr>
					
				</table>
				
				<h3 class="wiki_ts pt">3.3 输出参数</h3>
				<br/>
				<table id="transactions" style="margin-top: 0px; margin-right: 0px; margin-bottom: 15px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font: inherit; vertical-align: baseline; border-collapse: collapse; -webkit-border-horizontal-spacing: 0px; -webkit-border-vertical-spacing: 0px; color: rgb(68, 68, 68); font-family: HelveticaNeue-Regular, 'Helvetica Neue Regular', 'Helvetica Neue', Arial, sans-serif; height: 16px; width: 100%" border="1">
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline; background-color: rgb(248, 248, 248)">
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; vertical-align: baseline; width: 76px; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							字段名称
						</th>
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; vertical-align: baseline; width: 32px; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							<span style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
							说明
							</span>
						</th>
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; width: 25px; white-space: nowrap; vertical-align: baseline; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							数据类型
						</th>
						<th style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; font-size: 13px; font: inherit; background-color: rgb(237, 237, 237); text-shadow: rgb(255, 255, 255) 0px 1px 0px; box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; -webkit-box-shadow: rgb(255, 255, 255) 0px 1px 0px inset; vertical-align: baseline; width: 381px; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216)"> 
							<span style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
							长度
							</span>
						</th>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							code<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
					                  状态码 
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							10<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							msg<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							状态描述
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							String<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							50<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							data<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
                                                                     数据节点
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							Object<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							<br />
						</td>
					</tr>
					
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							balance<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
                                                                 可用短信条数（条）
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
						  int<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							11<br />
						</td>
					</tr>
					
				</table>
				
				<h3 class="wiki_ts pt">3.3 请求事例</h3>
				<br/>
				<div class="wiki_pre">
		        	<pre class="prettyprint pre">
		 			/template/sms/balance?userid=用户ID&appid=应用ID&ts=时间戳&sign=签名
		            </pre>
		        </div>
				
					<h3 class="wiki_ts pt">3.4 响应事例</h3>
				<br/>
				<div class="wiki_pre">
		        	<pre class="prettyprint pre">
		 		        {"code":"0","msg":"成功","data":{" balance":"74"}}
		            </pre>
		        </div>
				
				
				
				
				<h2 class="wiki_t">>> 8 返回代码一栏表</h2>
				<br/>
				<table id="mt" style="margin-top: 0px; margin-right: 0px; margin-bottom: 15px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font: inherit; vertical-align: baseline; border-collapse: collapse; -webkit-border-horizontal-spacing: 0px; -webkit-border-vertical-spacing: 0px; color: rgb(68, 68, 68); font-family: HelveticaNeue-Regular, 'Helvetica Neue Regular', 'Helvetica Neue', Arial, sans-serif; height: 16px; width: 100%" border="1">
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							0<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							成功<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							1<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							失败<br />
						</td>
						
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							2<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							参数校验失败，参数不能为空<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							3<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							余额不足<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							4<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							验证用户错误（用户不存在、应用不存在、应用状态错误）<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							6<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							MD5校验失败<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							7<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							IP校验失败<br />
						</td>
					</tr>										
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							8<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							time时间格式错误<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							9<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							扩展号格式错误<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							14<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							ts校验有误，要求5分钟内有效<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							15<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							号码个数超过限制<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							16<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							短信内容过长(只支持600字)<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							17<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							短信模板参数不能为空<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							18<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							模板错误(不存在、审核失败、待审核、禁用)<br />
						</td>
					</tr>
					<tr style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-style: initial; border-color: initial; font-size: 13px; font: inherit; vertical-align: baseline">
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							19<br />
						</td>
						<td style="margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding-top: 12px; padding-right: 20px; padding-bottom: 12px; padding-left: 20px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(216, 216, 216); border-right-color: rgb(216, 216, 216); border-bottom-color: rgb(216, 216, 216); border-left-color: rgb(216, 216, 216); font-size: 13px; font: inherit; vertical-align: top"> 
							短信模板参数个数不匹配<br />
						</td>
					</tr>
				</table>
				
				<br/>
				<p class="wiki_con">备注：其他类似Report（code返回码）如MK:XXXX、CBXXXX、MBXXXX、IDXXXX、IBXXXX、DBXXXX、IAXXXX、ICXXXX都表示客户短信接收失败。</p>
				<br/>
            </div>
     	</div>
	</div>
</body>
</html>