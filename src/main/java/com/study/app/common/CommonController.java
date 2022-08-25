package com.study.app.common;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SuppressWarnings( "unchecked" )
public class CommonController {

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
	
	public JSONObject getErrorResponse( String errCode, String errMsg ) {
		JSONObject resItem = new JSONObject();
		resItem.put( "resCode", errCode);
		resItem.put( "resMsg", errMsg);
		
		return resItem;
	}
}