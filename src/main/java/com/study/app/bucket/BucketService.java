package com.study.app.bucket;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.common.StudyException;

@Service( "BucketService" )
public class BucketService {

	@Autowired
	private BucketDAO bucketDAO;
	
	public String getServerTime()throws Exception {
		
		if( 1 == 1 ) {
			throw new StudyException( "1000", "아이디를 입력해 주세요" );
		}
		
		return bucketDAO.getServerTime();
	}
}
