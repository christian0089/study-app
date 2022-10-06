package com.study.app.bucket;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.common.CommonService;
import com.study.app.common.CommonUtil;
import com.study.app.common.StudyException;

@Service( "BucketService" )
@Transactional(rollbackFor=Exception.class)
public class BucketService extends CommonService{
	
	public BucketService() {
		super(BucketService.class);
	}

	@Autowired
	private BucketDAO bucketDAO;

	/* 로그인 */
	@SuppressWarnings("unchecked")
	public long login(JSONObject paramObj) throws Exception {
		String[] reqKeys = {"svcGb", "userId", "pwd"};		// 필수키
		super.checkVal(paramObj, reqKeys);					// 벨리데이션 체크
		
		long userSeqNo = bucketDAO.login(paramObj);

		if(userSeqNo == 0) {	// 해당 사용자번호가 존재하지 않는 경우
			throw new StudyException( "0001", "아이디 또는 비밀번호를 확인하여 주세요" );
		}
		
		return userSeqNo;
	}
	
	/* 버킷 목록 조회 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getBucketItems(JSONObject paramObj) throws Exception {
		String[] reqKeys = {"stNo"};						// 필수키
		super.checkVal(paramObj, reqKeys);					// 벨리데이션 체크
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		String moreYn = "N";		// 더보기여부
		
		// 버킷 목록 조회
		List<JSONObject> bucketItemsList = bucketDAO.getBucketItems(paramObj);
		
		if( !CommonUtil.isEmptyList(bucketItemsList) ) {
			moreYn = (String) bucketItemsList.get(0).get("moreYn");		// 더보기여부 SET
		}
		
		resMap.put( "items", bucketItemsList );
		resMap.put( "moreYn", moreYn );
		
		return resMap;
	}
	
	/* 버킷 상세정보 조회 */
	@SuppressWarnings("unchecked")
	public JSONObject getBucketDtlItem(JSONObject paramObj) throws Exception {
		String[] reqKeys = {"bucketSeqno"};					// 필수키
		super.checkVal(paramObj, reqKeys);					// 벨리데이션 체크
		
		return bucketDAO.getBucketDtlItem(paramObj);
	}
	
	/* 스토리 목록조회 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getStoryItems(JSONObject paramObj) throws Exception {
		String[] reqKeys = {"bucketSeqno", "stNo"};			// 필수키
		super.checkVal(paramObj, reqKeys);					// 벨리데이션 체크
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		String moreYn = "N";
		
		// 스토리 목록조회
		List<JSONObject> storyItemsList = bucketDAO.getStoryItems(paramObj);
		
		if( !CommonUtil.isEmptyList(storyItemsList) ) {
			moreYn = (String) storyItemsList.get(0).get("moreYn");		// 더보기여부 SET
		}
		
		resMap.put( "items", storyItemsList );
		resMap.put( "moreYn", moreYn );
		
		return resMap;
	}
	
	/* 버킷 등록 */
	public long regBucket(Map<String, Object> paramMap, MultipartFile file) throws Exception {
		String[] reqKeys = {"bucketNm", "bucketDscr"};		// 필수키
		super.checkVal(paramMap, reqKeys);					// 벨리데이션 체크
		
		// 파일업로드 및 업로드경로 SET
		if ( file != null && !file.isEmpty() ){
			paramMap.put("repImgUrl", studyImageUtil.uplaodBucketRepImage2(file));
		}
		
		bucketDAO.regBucket(paramMap);	// 버킷 등록
		
		return ((BigInteger)paramMap.get("bucketSeqno")).longValue();
	}

	/* 스토리 등록 */
	public long regStory(Map<String, Object> paramMap, MultipartFile file) throws Exception {
		String[] reqKeys = {"bucketSeqno", "storyDscr"};	// 필수키
		super.checkVal(paramMap, reqKeys);					// 벨리데이션 체크
		
		// 파일업로드 및 업로드경로 SET
		if ( file != null && !file.isEmpty() ){
			paramMap.put("imgUrl", studyImageUtil.uplaodBucketStoryImage2(file));
		}
		
		bucketDAO.regStory(paramMap);	// 스토리 등록
		
		return ((BigInteger)paramMap.get("bucketStorySeqno")).longValue();
	}
}
