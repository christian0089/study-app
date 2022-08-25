package com.study.app.pay;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.app.common.CommonController;

@Controller
@RequestMapping( value = "/pay" )
public class PayController extends CommonController{

	@Autowired
	private PayService payService;
	
	@ResponseBody
	@PostMapping( value = "/login")
	public JSONObject login(@RequestBody JSONObject reqItem) throws Exception{
		long userSeqno = 0;
		try {
			userSeqno = payService.login(reqItem);
		} catch (Exception e) {
			throw e;
		}
		
		return super.getItemResponse("userSeqno", userSeqno);
	}
}