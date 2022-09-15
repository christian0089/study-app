package com.study.app.bucket;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.app.common.CommonController;
import com.study.app.common.Encryptor;

@Controller
@RequestMapping( value = "/bucket" )
public class BucketController extends CommonController {
	
	public BucketController() {
		super(BucketController.class);
	}
	
	@Autowired
	private BucketService bucketService;
	
	// 로그인
	@ResponseBody
	@PostMapping( value = "/login" )
	public JSONObject login(@RequestBody JSONObject param) throws Exception {
		param.put("pwd", Encryptor.sha512( (String)param.get("pwd") ) );
		
		System.out.println("!! : " + param.get("pwd"));
		
		long userSeqno = bucketService.login(param);
		
		return super.getItemResponse( "userSeqno", userSeqno );
	}
	
	@ResponseBody
	@PostMapping( value = "/getMyBucketItems" )
	public JSONObject test(@RequestBody JSONObject param) throws Exception {
		long userSeqno = bucketService.login(param);
		
		return super.getItemResponse( "userSeqno", userSeqno );
	}
}