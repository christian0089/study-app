package com.study.app.pay;

import com.study.app.common.StudyException;
import com.study.app.common.StudyImageUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.study.app.common.CommonController;
import com.study.app.common.CommonUtil;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
	public JSONObject login( HttpSession session, @RequestBody JSONObject reqItem ) throws Exception {
		long userSeqno = 0;
		try {
			userSeqno = payService.login(reqItem);
			session.setAttribute("USER_SEQNO", userSeqno);
		} catch ( Exception e ) {
			throw e;
		}
		
		return super.getItemResponse("userSeqno", userSeqno);
	}

	@ResponseBody
	@PostMapping( value = "/registerPay" )
	public JSONObject registerPay( HttpServletRequest request, @RequestParam( required=false, value="file" ) MultipartFile file ) throws Exception {
		long userSeqno = super.getUserSeqno(request);
		if ( userSeqno == 0L ) {
			throw new StudyException( "0001", "로그인 해주세요.");
		}

		try {
			JSONObject reqItem = new JSONObject();
			reqItem.put("userSeqno", userSeqno);
			reqItem.put("payItemSeqno", CommonUtil.getLong( request.getParameter("payItemSeqno") ) );
			reqItem.put("payAmt", CommonUtil.getInteger( request.getParameter("payAmt") ) );

			payService.registerPay(reqItem, file);

		} catch ( Exception e ) {
			throw e;
		}

		return super.getSuccessItemResponse();
	}


}