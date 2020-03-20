package com.example.demo.soapclient;

import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

public class SOAPConnector extends WebServiceGatewaySupport {

	@Value("${SOAPclient.uid}")
	private String authUsername;

	@Value("${SOAPclient.pwd}")
	private String authPassword;

	@Bean
	public Wss4jSecurityInterceptor securityInterceptor() {
		Wss4jSecurityInterceptor security = new Wss4jSecurityInterceptor();
		// Set values for "UsernameToken" sections in SOAP header
		security.setSecurementActions(WSHandlerConstants.USERNAME_TOKEN);
		security.setSecurementMustUnderstand(true);
		security.setSecurementPasswordType(WSConstants.PW_TEXT);
		security.setSecurementUsername(authUsername);
		security.setSecurementPassword(authPassword);
		return security;
	}

	public Object callWebService(String url, Object request) {
		WebServiceTemplate temp = getWebServiceTemplate();
		temp.setInterceptors(new ClientInterceptor[] { securityInterceptor() });
		return temp.marshalSendAndReceive(url, request);
	}
}
