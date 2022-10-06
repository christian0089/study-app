package com.study.app.bucket;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import com.study.app.common.CommonDAO;

@Repository( "BucketDAO" )
public class BucketDAO extends CommonDAO {
	
	/* 로그인 */
	public long login(JSONObject param) throws Exception {
		return sqlSession.selectOne( "BucketMapper.login", param );
	}
	
	/* 버킷 목록조회 */
	public List<JSONObject> getBucketItems(JSONObject param) throws Exception {
		return sqlSession.selectList( "BucketMapper.selectBucketItems", param );
	}
	
	/* 버킷 상세정보 조회 */
	public JSONObject getBucketDtlItem(JSONObject param) throws Exception {
		return sqlSession.selectOne( "BucketMapper.selectBucketDtlItem", param );
	}
	
	/* 스토리 목록조회 */
	public List<JSONObject> getStoryItems(JSONObject param) throws Exception {
		return sqlSession.selectList( "BucketMapper.selectStoryItems", param );
	}
	
	/* 버킷 등록 */
	public long regBucket(Map<String, Object> paramMap) throws Exception {
		return sqlSession.insert( "BucketMapper.insertBucket", paramMap);
	}
	
	/* 스토리 등록 */
	public long regStory(Map<String, Object> paramMap) throws Exception {
		return sqlSession.insert( "BucketMapper.insertStory", paramMap);
	}
}