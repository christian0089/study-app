package com.study.app.common;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler extends CommonController {

	public ControllerExceptionHandler() {
		super( ControllerExceptionHandler.class );
	}
	
	@ResponseBody
	@ExceptionHandler( Exception.class )
	public JSONObject exceptionHandler( HttpServletRequest request, Exception e ) {
		log.error( "exceptionHandler [" + request.getRequestURI() + "]", e );
		return super.getErrorResponse( "9999", "Server-Error" );
	}
	
	@ResponseBody
	@ExceptionHandler( StudyException.class )
	public JSONObject exceptionHandler( HttpServletRequest request, StudyException e ) {
		return super.getErrorResponse( e.getErrCode(), e.getErrMsg() );
	}
}