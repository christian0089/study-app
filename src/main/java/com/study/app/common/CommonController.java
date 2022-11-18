package com.study.app.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings( "unchecked" )
public class CommonController {

	protected Logger log;
	
	protected boolean isLoggedIn( HttpServletRequest request ) {
		HttpSession session = request.getSession();
		long userSeqno = CommonUtil.getLong( session.getAttribute( "USER_SEQNO" ) );
		return !( userSeqno < 1L );
	}
	
	protected long getUserSeqno( HttpServletRequest request ) {
		HttpSession session = request.getSession();
		return CommonUtil.getLong( session.getAttribute( "USER_SEQNO" ) );
	}
	
	protected CommonController( Class<?> c ) {
		log = LoggerFactory.getLogger( c );
	}
	
	public JSONObject getSuccessItemResponse() {
		JSONObject resItem = new JSONObject();
		resItem.put( "resCode", "0000" );
		resItem.put( "resMsg", "SUCCESS" );
		
		return resItem;
	}
	
	public JSONObject getItemResponse( JSONObject item ) {
		JSONObject resItem = new JSONObject();
		resItem.put( "resCode", "0000" );
		resItem.put( "resMsg", "SUCCESS" );
		resItem.put( "item", item );
		
		return resItem;
	}
	
	public JSONObject getItemResponse( String key, Object value ) {
		JSONObject resItem = new JSONObject();
		resItem.put( "resCode", "0000" );
		resItem.put( "resMsg", "SUCCESS" );
		resItem.put( key, value );
		
		return resItem;
	}
	
	public JSONObject getItemsResponse( JSONArray items ) {
		JSONObject resItem = new JSONObject();
		resItem.put( "resCode", "0000" );
		resItem.put( "resMsg", "SUCCESS" );
		resItem.put( "items", items );
		
		return resItem;
	}
	
	public JSONObject getItemsResponse( JSONArray items, String key, Object value ) {
		JSONObject resItem = new JSONObject();
		resItem.put( "resCode", "0000" );
		resItem.put( "resMsg", "SUCCESS" );
		resItem.put( "items", items );
		resItem.put( key, value );
		
		return resItem;
	}
	
	public JSONObject getItemsResponse2( JSONArray items, String key, Object value, String key2, Object value2 ) {
		JSONObject resItem = new JSONObject();
		resItem.put( "resCode", "0000" );
		resItem.put( "resMsg", "SUCCESS" );
		resItem.put( "items", items );
		resItem.put( key, value );
		resItem.put( key2, value2 );
		
		return resItem;
	}
	
	public JSONObject getErrorResponse( String errCode, String errMsg ) {
		JSONObject resItem = new JSONObject();
		resItem.put( "resCode", errCode);
		resItem.put( "resMsg", errMsg);
		
		return resItem;
	}
}