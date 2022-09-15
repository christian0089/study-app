package com.study.app.bucket;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.common.CommonService;
import com.study.app.common.StudyException;

@Service( "BucketService" )
public class BucketService extends CommonService{
	
	public BucketService() {
		super(BucketService.class);
	}

	@Autowired
	private BucketDAO bucketDAO;

	/* 로그인 */
	public long login(JSONObject param) throws Exception {
		long userSeqNo = bucketDAO.login(param);

		if(userSeqNo == 0) {
			throw new StudyException( "0001", "아이디 또는 비밀번호를 확인하여 주세요" );
		}
		
		return userSeqNo;
	}
	
}
