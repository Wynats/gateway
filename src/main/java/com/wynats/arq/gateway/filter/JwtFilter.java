package com.wynats.arq.gateway.filter;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.file.Matcher;
import org.springframework.beans.factory.annotation.Value;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class JwtFilter extends ZuulFilter {
	
	@Value("${gateway.excludedPaths}")    
	String[] excludedPaths;
	
	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 2;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String currentPath= request.getRequestURI();
		int start= currentPath.indexOf("/", 1);
		int end = currentPath.indexOf("/", start+1);
		String path="";
		if(end==-1){
			path=currentPath.substring(start);
		}else{
			path=currentPath.substring(start, end);
		}
		 
		boolean should = !Arrays.stream(excludedPaths).anyMatch(path::equals);
		return should;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
		Enumeration<String> headersIterator=request.getHeaderNames();
		StringBuilder headerNames= new StringBuilder();
		while(headersIterator.hasMoreElements()){
			String currentHeaderName=headersIterator.nextElement();
			headerNames.append(currentHeaderName);
			headerNames.append(": ");
			headerNames.append(request.getHeader(currentHeaderName));
			headerNames.append("\n");
		}
		log.info("Cabeceras de la peticion \n{}",headerNames.toString());
		return null;
	}
}


