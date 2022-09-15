package com.study.app.bucket;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import com.study.app.common.CommonDAO;

@Repository( "com.study.app.bucket.BucketDAO" )
public class BucketDAO extends CommonDAO {
	// 로그인
	public long login(JSONObject param)throws Exception {
		System.out.println( param.toJSONString() );
		return sqlSession.selectOne( "BucketMapper.login", param );
	}
}