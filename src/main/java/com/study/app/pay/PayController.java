package com.study.app.pay;

import com.study.app.common.StudyException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.study.app.common.CommonController;
import com.study.app.common.CommonUtil;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping( value = "/pay" )
public class PayController extends CommonController{

	@Autowired
	private PayService payService;

	public PayController() {
		super(PayController.class);
	}
	
	@ResponseBody
	@CrossOrigin( origins = "*" )
	@PostMapping( value = "/login" )	// 2.1.1 로그인
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
	@PostMapping( value = "/getTodayPaidItems" )	// 2.1.2 오늘의 지출내역 목록조회
	public JSONObject getTodayPaidItems( HttpServletRequest request ) throws Exception {
		long userSeqno = this.checkLogin(request);

		JSONObject result = new JSONObject();

		try {

			result = payService.getTodayPaidItems(userSeqno);

		} catch ( Exception e ) {
			throw e;
		}

		return super.getItemResponse(result);
	}

	@ResponseBody
	@PostMapping( value = "/registerPay" )	// 2.1.3 지출 등록
	public JSONObject registerPay( HttpServletRequest request, @RequestParam( required=false, value="file" ) MultipartFile file ) throws Exception {
		long userSeqno = this.checkLogin(request);

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

	@ResponseBody
	@PostMapping( value = "/getMonthlyPaidItems")	// 2.1.4 월별 지출금액 목록조회
	public JSONObject getMonthlyPaidItems( HttpServletRequest request ) throws Exception {
		long userSeqno = this.checkLogin(request);

		JSONObject result = new JSONObject();

		try {

			result = payService.getMonthlyPaidItems(userSeqno);

		} catch ( Exception e ) {
			throw e;
		}

		return super.getItemResponse(result);
	}

	@ResponseBody
	@PostMapping( value = "/getTypePaidItems" )	// 2.1.5 지출항목별 지출금액 목록조회
	public JSONObject getTypePaidItems( HttpServletRequest request, @RequestBody JSONObject reqItem) throws Exception {
		long userSeqno = this.checkLogin(request);

		JSONObject result = new JSONObject();
		reqItem.put("userSeqno", userSeqno);

		try {

			result = payService.getTypePaidItems(reqItem);

		} catch ( Exception e ) {
			throw e;
		}

		return super.getItemResponse(result);
	}

	@ResponseBody
	@PostMapping( value = "/getTypeItems")	// 2.1.6 지출항목 목록조회
	public JSONObject getTypeItems( HttpServletRequest request ) throws Exception {
		long userSeqno = this.checkLogin(request);

		JSONObject result = new JSONObject();

		try {

			result = payService.getTypeItems(userSeqno);

		} catch ( Exception e ) {
			throw e;
		}

		return super.getItemResponse(result);
	}

	@ResponseBody
	@PostMapping( value = "/registerPayType")	// 2.1.7 지출항목 등록
	public JSONObject registerPayType( HttpServletRequest request, @RequestBody JSONObject reqItem ) throws Exception {
		long userSeqno = this.checkLogin(request);
		reqItem.put("userSeqno", userSeqno);

		try {

			payService.registerPayType(reqItem);

		} catch ( Exception e ) {
			throw e;
		}

		return super.getSuccessItemResponse();
	}

	@ResponseBody
	@PostMapping( value = "/deletePayType")	// 2.1.8 지출항목 삭제
	public JSONObject deletePayType( HttpServletRequest request, @RequestBody JSONObject reqItem ) throws Exception {
		long userSeqno = this.checkLogin(request);
		reqItem.put("userSeqno", userSeqno);

		try {

			payService.deletePayType(reqItem);

		} catch ( Exception e ) {
			throw e;
		}

		return super.getSuccessItemResponse();
	}

	@ResponseBody
	@PostMapping( value = "/updatePwd")	// 2.1.9 비밀번호 변경
	public JSONObject updatePwd( HttpServletRequest request, @RequestBody JSONObject reqItem ) throws Exception {
		long userSeqno = this.checkLogin(request);
		reqItem.put("userSeqno", userSeqno);

		try {

			payService.updatePwd(reqItem);

		} catch ( Exception e ) {
			throw e;
		}

		return super.getSuccessItemResponse();
	}

	@ResponseBody
	@PostMapping( value = "/withdrawal")	// 2.1.10 회원 탈퇴
	public JSONObject withdrawal( HttpServletRequest request, @RequestBody JSONObject reqItem ) throws Exception {
		long userSeqno = this.checkLogin(request);
		reqItem.put("userSeqno", userSeqno);

		try {

			payService.withdrawal(reqItem);

		} catch ( Exception e ) {
			throw e;
		}

		return super.getSuccessItemResponse();
	}

	private Long checkLogin( HttpServletRequest request ) throws Exception {
		long userSeqno = super.getUserSeqno(request);
		if (userSeqno == 0L) {
			throw new StudyException("0001", "로그인 해주세요.");
		}
		return userSeqno;
	}
}