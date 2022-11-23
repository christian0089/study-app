package com.study.app.bucket;

import java.io.File;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.common.CommonService;
import com.study.app.common.CommonUtil;
import com.study.app.common.StudyException;

@Service( "BucketService" )
@Transactional(rollbackFor=Exception.class)
public class BucketService extends CommonService{
	
	@Value("${img.max.size}")
	private long imgMaxSize;
	
	@Value("${img.bucket.upload.base.path}")
	private String bucketUploadBasePath;
	
	@Value("${img.bucket.base.url}")
	private String bucketBaseUrl;
	
	@Value("${img.pay.upload.base.path}")
	private String payUploadBasePath;
	
	@Value("${img.pay.base.url}")
	private String payBaseUrl;
	
	private final String BUCKET_REP_SUFFIX = "/rep";
	private final String BUCKET_STORY_SUFFIX = "/story";
	
	public BucketService() {
		super(BucketService.class);
	}

	@Autowired
	private BucketDAO bucketDAO;
	
	private static final int GET_BUCKET_ITEMS_SEARCH_COUNT = 30;		// 버킷리스트 조회건수
	private static final int GET_STORY_ITEMS_SEARCH_COUNT = 30;			// 스토리리스트 조회건수

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
		int nextStNo = 0;
		
		paramObj.put("searchCnt", GET_BUCKET_ITEMS_SEARCH_COUNT);
		
		// 버킷 목록 조회
		List<JSONObject> bucketItemsList = bucketDAO.getBucketItems(paramObj);
		
		if( !CommonUtil.isEmptyList(bucketItemsList) ) {
			moreYn = (String) bucketItemsList.get(0).get("moreYn");		// 더보기여부 SET
			nextStNo = (Integer) paramObj.get("stNo") + GET_BUCKET_ITEMS_SEARCH_COUNT;
		}
		
		resMap.put( "items", bucketItemsList );
		resMap.put( "moreYn", moreYn );
		resMap.put( "nextStNo", nextStNo );
		
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
		int nextStNo = 0;
		
		paramObj.put("searchCnt", GET_STORY_ITEMS_SEARCH_COUNT);
		
		// 스토리 목록조회
		List<JSONObject> storyItemsList = bucketDAO.getStoryItems(paramObj);
		
		if( !CommonUtil.isEmptyList(storyItemsList) ) {
			moreYn = (String) storyItemsList.get(0).get("moreYn");
			nextStNo = (Integer) paramObj.get("stNo") + GET_STORY_ITEMS_SEARCH_COUNT;// 더보기여부 SET
		}
		
		resMap.put( "items", storyItemsList );
		resMap.put( "moreYn", moreYn );
		resMap.put( "nextStNo", nextStNo );
		
		return resMap;
	}
	
	/* 버킷 등록 */
	public long regBucket(Map<String, Object> paramMap, MultipartFile file) throws Exception {
		String[] reqKeys = {"bucketNm", "bucketDscr"};		// 필수키
		super.checkVal(paramMap, reqKeys);					// 벨리데이션 체크
		
		// 파일업로드 및 업로드경로 SET
		if ( file != null && !file.isEmpty() ){
			paramMap.put("repImgUrl", studyImageUtil.uplaodBucketRepImage(file));
		}
		
		bucketDAO.regBucket(paramMap);	// 버킷 등록
		
		return ((BigInteger)paramMap.get("bucketSeqno")).longValue();
	}
	
	public String uplaodBucketRepImage2( MultipartFile file ) throws Exception {
		String uploadFileName = file.getOriginalFilename();
		
		String uploadFileFormat = uploadFileName.substring( uploadFileName.lastIndexOf(".") +1 );
		
		// 파일 확장자 검증
		if( !CommonUtil.isAcceptedUploadFileFormat( uploadFileFormat ) ) {
			throw new StudyException( "1002", "[" + uploadFileFormat + "] 형식의 파일은 업로드 할 수 없습니다." );
		}
		
		String monthSuffix = CommonUtil.SEPARATOR + CommonUtil.getServerTime( "%Y%m" );
		String saveDir = this.bucketUploadBasePath + this.BUCKET_REP_SUFFIX + monthSuffix;
		String saveFileName = CommonUtil.SEPARATOR + CommonUtil.getRandomString( -1, 8 ) + CommonUtil.getServerTime( "%Y%m%d" ) + CommonUtil.getRandomString( -1, 8 ) + "." + uploadFileFormat;
		
		CommonUtil.makeDirs( saveDir );
		
		file.transferTo( new File( saveDir + saveFileName ) );
		
		return this.bucketBaseUrl + this.BUCKET_REP_SUFFIX + monthSuffix + saveFileName;
	}
	
	

	/* 스토리 등록 */
	public long regStory(Map<String, Object> paramMap, MultipartFile file) throws Exception {
		String[] reqKeys = {"bucketSeqno", "storyDscr"};	// 필수키
		super.checkVal(paramMap, reqKeys);					// 벨리데이션 체크
		
		// 파일업로드 및 업로드경로 SET
		if ( file != null && !file.isEmpty() ){
			paramMap.put("imgUrl", studyImageUtil.uplaodBucketStoryImage(file));
		}
		
		bucketDAO.regStory(paramMap);	// 스토리 등록
		
		return ((BigInteger)paramMap.get("bucketStorySeqno")).longValue();
	}
	
	/* 버킷 삭제 */
	@SuppressWarnings("unchecked")
	public String delBucket(JSONObject paramObj) throws Exception {
		String[] reqKeys = {"bucketSeqno"};					// 필수키
		super.checkVal(paramObj, reqKeys);					// 벨리데이션 체크
		
		String delYn = "N";			// 삭제여부
		int rs = bucketDAO.delBucket(paramObj);
		
		if( rs > 0 ) {
			delYn = "Y";
		}
		
		return delYn;
	}
}
