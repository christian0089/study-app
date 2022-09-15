package com.study.app.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonService {

	protected Logger log;
	
	protected CommonService( Class<?> c ) {
		log = LoggerFactory.getLogger( c );
	}
	
	@Autowired
	protected StudyImageUtil studyImageUtil;
}