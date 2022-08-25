package com.study.app.pay;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.common.Encryptor;
import com.study.app.common.StudyException;

@Service
@SuppressWarnings( "unchecked" )
public class PayService {
	
	@Autowired
	private PayDAO payDAO;

	
	public long login(JSONObject reqItem) throws Exception {
		String svcGb = (String) reqItem.get("svcGb");
		String userId = (String) reqItem.get("userId");
		String pwd = (String) reqItem.get("pwd");
		
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
		long userSeqno = payDAO.login(reqItem);
		
		if (userSeqno == 0) {
			throw new StudyException("1001", "아이디 또는 비밀번호 확인!");
		}
		return userSeqno;
	}
}
