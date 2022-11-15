package com.study.app.pay;

import com.study.app.common.CommonService;
import com.study.app.common.CommonUtil;
import com.study.app.common.StudyImageUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.common.Encryptor;
import com.study.app.common.StudyException;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings( "unchecked" )
public class PayService extends CommonService {
	
	@Autowired
	private PayDAO payDAO;

	public PayService() {
		super(PayService.class);

	}
	
	public long login(JSONObject reqItem) throws Exception {
		String svcGb = (String) reqItem.get("svcGb");	// 서비스 구분( "P" 고정 )
		String userId = (String) reqItem.get("userId");	// 회원 아이디
		String pwd = (String) reqItem.get("pwd");		// 비밀번호
		
		if( svcGb == null || "".equals( svcGb ) ) {
			throw new StudyException( "4000", "[svcGb] 값이 누락되었습니다." );
		}
		if( userId == null || "".equals( userId ) ) {
			throw new StudyException( "4000", "[userId] 값이 누락되었습니다." );
		}
		if( pwd == null || "".equals( pwd ) ) {
			throw new StudyException( "4000", "[pwd] 값이 누락되었습니다." );
		}
		if( !"P".equals( svcGb ) ) {
			throw new StudyException( "4001", "[svcGb] 값이 잘못되었습니다." );
		}
		
		reqItem.put("pwd", Encryptor.sha512(pwd));
		long userSeqno = payDAO.login(reqItem);	// 회원 시퀀스 번호
		
		if (userSeqno == 0) {
			throw new StudyException("1001", "아이디 또는 비밀번호 확인!");
		}
		return userSeqno;
	}

	public void registerPay(JSONObject reqItem, MultipartFile file) throws Exception {
		long payItemSeqno = CommonUtil.getLong( reqItem.get("payItemSeqno") );	// 지출항목 시퀀스
		int payAmt = CommonUtil.getInteger( reqItem.get("payAmt") );			// 지출 금액
		long paySeqno = 0L;

		if( payItemSeqno == 0L ) {
			throw new StudyException( "4000", "[payItemSeqno] 값이 누락되었습니다." );
		}
		if( payAmt == 0 ) {
			throw new StudyException( "4000", "[payAmt] 값이 누락되었습니다." );
		}

		reqItem.put("paySeqno", paySeqno);

		paySeqno = payDAO.registerPay(reqItem);

		if ( file != null && !file.isEmpty() ){
			reqItem.put("paySeqno", paySeqno);
			reqItem.put("imgUrl", studyImageUtil.uplaodPayImage( file ));
			payDAO.registerPayImage(reqItem);
		}
	}

	public JSONObject getMonthlyPaidItems(long userSeqno) {

		Map item = new HashMap();							// 조회결과(총누적, 월평균)
		List<JSONObject> monthlyItems = new ArrayList<>();	// 월별 지출내역
		List<JSONObject> payTypeItems = new ArrayList<>();	// 지출 항목별 소계

		item = payDAO.getTotalAmounts(userSeqno);
		monthlyItems = payDAO.getMonthlyItems(userSeqno);

		for(JSONObject monthlyItem : monthlyItems) {
			// 전월대비 항목소계 고려
			JSONObject reqItem = new JSONObject();
			reqItem.put("userSeqno", userSeqno);
			reqItem.put("month", monthlyItem.get("month"));

			payTypeItems = payDAO.getPayTypeItems(reqItem);
			monthlyItem.put("payTypeItems", payTypeItems);
		}


		JSONObject result = new JSONObject();
		result.put("item", item);
		result.put("monthlyItems", monthlyItems);

		return result;
	}

	public JSONObject getTodayPaidItems(long userSeqno) {

		Map item = new HashMap();								// 조회결과(오늘일자, 오늘지출금액)
		List<JSONObject> todayPaidItems = new ArrayList<>();	// 지출내역

		JSONObject result = new JSONObject();

		item = payDAO.getTodayPaidTotal(userSeqno);
		todayPaidItems = payDAO.getTodayPaidItems(userSeqno);

		result.put("item", item);
		result.put("todayPaidItems", todayPaidItems);

		return result;
	}

	public JSONObject getTypePaidItems(JSONObject reqItems) {

		Integer totAmt = 0;										// 총 누적 지출금액
		List<JSONObject> typePaidItems = new ArrayList<>();		// 지출 항목별 목록

		JSONObject result = new JSONObject();

		typePaidItems = payDAO.getTypePaidItems(reqItems);

		for (JSONObject typePaidItem : typePaidItems) {
			totAmt += Integer.parseInt(String.valueOf(typePaidItem.get("paidAmt")));
		}

		result.put("totAmt", totAmt);
		result.put("payTypeItems", typePaidItems);

		return result;
	}

	public JSONObject getTypeItems(long userSeqno) {

		List<JSONObject> typeItems = new ArrayList<>();	// 지출항목 목록

		JSONObject result = new JSONObject();

		typeItems = payDAO.getTypeItems(userSeqno);

		result.put("items", typeItems);

		return result;
	}

	public void registerPayType(JSONObject reqItem) throws Exception {
		Integer duplicateCheck = payDAO.checkPayType(reqItem);
		if( duplicateCheck > 0 ) {
			throw new StudyException( "4000", "이미 등록된 지출항목 입니다." );
		}

		payDAO.registerPayType(reqItem);

	}

	public void deletePayType(JSONObject reqItem) {
		payDAO.deletePayType(reqItem);
	}

	public void updatePwd(JSONObject reqItem) throws Exception {
		String crntPwd = payDAO.getCurrentPwd(reqItem);
		String reqCrntPwd = Encryptor.sha512((String) reqItem.get("crntPwd"));
		String chgPwd = Encryptor.sha512((String) reqItem.get("chgPwd"));

		if ( !reqCrntPwd.equals(crntPwd) ) {
			throw new StudyException( "4000", "기존 비밀번호와 다릅니다." );
		}

		reqItem.replace("chgPwd", chgPwd);
		reqItem.replace("crntPwd", reqCrntPwd);

		payDAO.updatePwd(reqItem);
	}

	public void withdrawal(JSONObject reqItem) throws Exception {
		String crntPwd = payDAO.getCurrentPwd(reqItem);
		String pwd = Encryptor.sha512((String) reqItem.get("pwd"));

		if ( crntPwd == null ) {
			throw new StudyException( "4000", "탈퇴 처리된 회원입니다." );
		}
		if ( !crntPwd.equals(pwd) ) {
			throw new StudyException( "4000", "비밀번호가 맞지 않습니다." );
		}

		reqItem.replace("pwd", pwd);

		payDAO.withdrawal(reqItem);
	}
}
