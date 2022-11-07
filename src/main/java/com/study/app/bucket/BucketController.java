package com.study.app.bucket;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.common.CommonController;
import com.study.app.common.CommonUtil;
import com.study.app.common.Encryptor;
import com.study.app.common.StudyException;

@Controller
@RequestMapping( value = "/bucket" )
public class BucketController extends CommonController {
	
	public BucketController() {
		super(BucketController.class);
	}
	
	@Autowired
	private BucketService bucketService;
	
	/* 로그인 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@PostMapping( value = "/login" )
	public JSONObject login(@RequestBody JSONObject param, HttpServletRequest request) throws Exception {
		param.put("pwd", Encryptor.sha512( (String)param.get("pwd") ) );
		
		// 로그인 프로세스
		long userSeqno = bucketService.login(param);
		
		// 세션 SET
		request.getSession().setAttribute("USER_SEQNO", userSeqno);
		
		return super.getItemResponse( "userSeqno", userSeqno );
	}

	/* 버킷 목록 조회 */
	@ResponseBody
	@PostMapping( value = "/getBucketItems" )
	public JSONObject getBucketItems(@RequestBody JSONObject param) throws Exception {
		Map<String, Object> resMap =  bucketService.getBucketItems(param);
		
		return super.getItemsResponse( CommonUtil.parseListToJSONArray( resMap.get( "items") ), "moreYn", resMap.get( "moreYn") );
	}
	
	/* 버킷 상세정보 조회 */
	@ResponseBody
	@PostMapping( value = "/getBucketDtlItem" )
	public JSONObject getBucketDtlItem(@RequestBody JSONObject param) throws Exception {
		JSONObject resObj = bucketService.getBucketDtlItem(param);
		
		return super.getItemResponse( resObj );
	}
	
	/* 스토리 목록조회 */
	@ResponseBody
	@PostMapping( value = "/getStoryItems" )
	public JSONObject getStoryItems(@RequestBody JSONObject param) throws Exception {
		Map<String, Object> resMap = bucketService.getStoryItems(param);
		
		return super.getItemsResponse( CommonUtil.parseListToJSONArray( resMap.get( "items") ), "moreYn", resMap.get( "moreYn") );
	}
	
	/* 버킷 등록 */
	@ResponseBody
	@PostMapping( value = "/regBucket" )
	public JSONObject regBucket(HttpServletRequest request, @RequestParam( required=false, value="file" ) MultipartFile file, @RequestParam Map<String, Object> paramMap) throws Exception {
		
		
		if(!isLoggedIn(request)) {
			throw new StudyException("1000", "로그인 정보가 없습니다");
		}
		
		paramMap.put("userSeqno", getUserSeqno(request));	// 사용자번호 SET
		
		long bucketSeqno = bucketService.regBucket(paramMap, file);
		
		return super.getItemResponse( "bucketSeqno", bucketSeqno );
	}

	/* 스토리 등록 */
	@ResponseBody
	@PostMapping( value = "/regStory" )
	public JSONObject regStory(HttpServletRequest request, @RequestParam( required=false, value="file" ) MultipartFile file, @RequestParam Map<String, Object> paramMap) throws Exception {
		if(!isLoggedIn(request)) {
			throw new StudyException("1000", "로그인 정보가 없습니다");
		}
		
		long bucketStorySeqno = bucketService.regStory(paramMap, file);
		
		return super.getItemResponse( "bucketStorySeqno", bucketStorySeqno );
	}
	
	/* 스토리 삭제 */
	@ResponseBody
	@PostMapping( value = "/delBucket" )
	public JSONObject delBucket(@RequestBody JSONObject param) throws Exception {
		return super.getItemResponse( "delYn", bucketService.delBucket(param) );
	}
}