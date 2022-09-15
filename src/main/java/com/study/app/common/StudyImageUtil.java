package com.study.app.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope( value = "singleton" )
public class StudyImageUtil {

	@Value("${img.bucket.upload.base.path}")
	private String bucketUploadBasePath;
	
	@Value("${img.bucket.base.url}")
	private String bucketBaseUrl;
	
	@Value("${img.pay.upload.base.path}")
	private String payUploadBasePath;
	
	@Value("${img.pay.base.url}")
	private String payBaseUrl;
	
	public String test() {
		return this.payBaseUrl;
	}
}