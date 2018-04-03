package com.example.filter;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import lombok.Data;

@Component
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS, scopeName=WebApplicationContext.SCOPE_REQUEST)
@Data
public class RequestDatas {
	private Integer aa;
}
