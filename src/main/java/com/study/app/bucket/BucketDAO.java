package com.study.app.bucket;

import org.springframework.stereotype.Repository;

import com.study.app.common.CommonDAO;

@Repository( "com.study.app.bucket.BucketDAO" )
public class BucketDAO extends CommonDAO {

	public String getServerTime()throws Exception {
		return sqlSession.selectOne( "BucketMapper.getServerTime" );
	}
}