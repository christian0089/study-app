package com.study.app.bucket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.common.CommonService;
import com.study.app.common.CommonUtil;
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

		if(userSeqNo == 0) {	// 해당 사용자번호가 존재하지 않는 경우
			throw new StudyException( "0001", "아이디 또는 비밀번호를 확인하여 주세요" );
		}
		
		return userSeqNo;
	}
	
	// 버킷 목록 조회
	public Map<String, Object> getBucketItems(JSONObject param) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		String moreYn = "N";
		
		// 버킷목록 조회
		List<JSONObject> bucketItemsList = bucketDAO.getBucketItems(param);
		
		if( !CommonUtil.isEmptyList(bucketItemsList) ) {
			// 더보기여부 SET
			moreYn = (String) bucketItemsList.get(0).get("moreYn");
		}
		
		resMap.put( "items", bucketItemsList );
		resMap.put( "moreYn", moreYn );
		
		return resMap;
	}
	
	// 버킷 상세정보 조회
	public JSONObject getBucketDtlItem(JSONObject param) throws Exception {
		return bucketDAO.getBucketDtlItem(param);
	}
	
	// 버킷스토리 목록조회
	public Map<String, Object> getStoryItems(JSONObject param) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		String moreYn = "N";
		
		// 버킷목록 조회
		List<JSONObject> bucketItemsList = bucketDAO.getStoryItems(param);
		
		if( !CommonUtil.isEmptyList(bucketItemsList) ) {
			// 더보기여부 SET
			moreYn = (String) bucketItemsList.get(0).get("moreYn");
		}
		
		resMap.put( "items", bucketItemsList );
		resMap.put( "moreYn", moreYn );
		
		return resMap;
	}
	
}
