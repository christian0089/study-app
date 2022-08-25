package com.study.app.bucket;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping( value = "/bucket" )
public class BucketController {

	@Autowired
	private BucketService bucketService;
	
	@ResponseBody
	@PostMapping( value = "/test" )
	public JSONObject test()throws Exception {
		JSONObject item = new JSONObject();
		item.put( "time", bucketService.getServerTime() );
		item.put( "time2", "adasdds" );
		
		return item;
	}
	
}