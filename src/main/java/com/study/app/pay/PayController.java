package com.study.app.pay;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.study.app.common.CommonController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping( value = "/pay" )
public class PayController extends CommonController{

	@Autowired
	private PayService payService;

	public PayController() {
		super(PayController.class);
	}
	
	@ResponseBody
	@PostMapping( value = "/login" )
	public JSONObject login( @RequestBody JSONObject reqItem ) throws Exception {
		long userSeqno = 0;
		try {
			userSeqno = payService.login(reqItem);
		} catch ( Exception e ) {
			throw e;
		}
		
		return super.getItemResponse("userSeqno", userSeqno);
	}

	@ResponseBody
	@PostMapping( value = "/registerPay" )
	public JSONObject registerPay( HttpServletRequest request, @RequestParam( required=false, value="file" ) MultipartFile file ) {

		try {
			String payItemSeqno = request.getParameter("payItemSeqno");
			String payAmt = request.getParameter("payAmt");

			System.out.println("payItemSeqno [" + payItemSeqno +"], payAmt [" + payAmt + "]" );


		} catch ( Exception e ) {
			throw e;
		}

		return super.getSuccessItemResponse();
	}


}