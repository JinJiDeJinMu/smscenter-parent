/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.sanerzone.smscenter.gateway.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanerzone.common.support.utils.StreamUtils;
import com.sanerzone.smscenter.gateway.GatewayStartUp;
import com.sanerzone.smscenter.gateway.http.handler.GateWayHttpAbstract;
import com.sanerzone.smscenter.gateway.http.message.SmsHttpMsgResponse;

@RestController
@RequestMapping(value = "/api/sms/notify")
public class SmsGatewayNotify {

    private final static Logger logger = Logger.getLogger(SmsGatewayNotify.class);

    /**
     * POST1获取request.getInputStream()
     * ContentType=application/json; text/html
     * @param code
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/pr/{code}")
    public String POSTReport(@PathVariable String code, HttpServletRequest request, HttpServletResponse response) {
        String respText = null;
        try {
            String charEncoding = request.getCharacterEncoding();
            if (charEncoding == null) {
                charEncoding = "UTF-8";
            }
            respText = StreamUtils.InputStreamTOString(request.getInputStream(), charEncoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return push(code, respText, request, response);
    }
        
    /**
     * POST2 读取SpringMVC中的 RequestBody 默认ContentType的情况下，SpringMVC会自动解析，导致request.getInputStream()为空)
     * ContentType=application/x-www-form-urlencoded
     * @param code
     * @param bytes
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/nr/{code}")
    public String FormReport(@PathVariable String code, @RequestBody byte[] bytes, HttpServletRequest request, HttpServletResponse response) {
        String respText = null;
        try {
            String charEncoding = request.getCharacterEncoding();
            if (charEncoding == null) {
                charEncoding = "UTF-8";
            }
            respText = new String(bytes, charEncoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return push(code, respText, request, response);
    }
        
    /**
     * GET 请求 request.getQueryString()
     * @param code
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/gr/{code}")
    public String GETReport(@PathVariable String code, HttpServletRequest request, HttpServletResponse response) {
        String respText = request.getQueryString();
        return push(code, respText, request, response);
    }
    
    public String push(String code, String respText, HttpServletRequest request, HttpServletResponse response){
        logger.info(code + "推送:"  + respText);
        
        SmsHttpMsgResponse smsHttpRtMessageResponse = new SmsHttpMsgResponse("");
        
        if (StringUtils.isNotBlank(respText)) {
        	GateWayHttpAbstract executer = (GateWayHttpAbstract) GatewayStartUp.gatewayFactory.getGateway(code);
            if( executer != null) 
            {
                smsHttpRtMessageResponse = executer.report(respText, false);
            }
        }else{
            logger.debug(String.format("accept post error,ContentType:%s, method:%s, CharacterEncoding:%s", request.getContentType(), request.getMethod(), request.getCharacterEncoding()));
        }
        
        try {
            response.reset();
            response.setContentType(smsHttpRtMessageResponse.getContentType());
            response.setCharacterEncoding(smsHttpRtMessageResponse.getCharacterEncoding());
            response.getWriter().print(smsHttpRtMessageResponse.getResult());
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
