package com.study.app.bucket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service( "BucketService" )
public class BucketService {

	@Autowired
	private BucketDAO bucketDAO;
	
	public String getServerTime()throws Exception {
		return bucketDAO.getServerTime();
	}
}
