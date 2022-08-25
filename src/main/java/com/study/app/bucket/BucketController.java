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

	@Autowired
	private BucketService bucketService;
	
	@ResponseBody
	@PostMapping( value = "/login" )
	public JSONObject test(@RequestBody JSONObject param) throws Exception {
		param.put("pwd", Encryptor.sha512( (String)param.get("pwd") ) );
		
		long userSeqno = bucketService.login(param);
		
		return super.getItemResponse( "userSeqno", userSeqno );
	}
}