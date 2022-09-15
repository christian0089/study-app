package com.study.app.common;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Scope( value = "singleton" )
public class StudyImageUtil {

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
	
	/**
	 * 버킷 대표 이미지 파일 업로드
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String uplaodBucketRepImage( MultipartFile file ) throws Exception {
		if( file.getSize() > imgMaxSize ) {
			throw new StudyException( "1001", "업로드 파일의 용량이 초과되었습니다. [Max 10Mb]" );
		}
		String uploadFileName = file.getOriginalFilename();
		String uploadFileFormat = uploadFileName.substring( uploadFileName.lastIndexOf(".") +1 );
		
		// 파일 확장자 검증
		if( !CommonUtil.isAcceptedUploadFileFormat( uploadFileFormat ) ) {
			throw new StudyException( "1002", "[" + uploadFileFormat + "] 형식의 파일은 업로드 할 수 없습니다." );
		}
		
		String monthSuffix = CommonUtil.SEPARATOR + CommonUtil.getServerTime( "%Y%m" );
		String saveFileName = CommonUtil.SEPARATOR + CommonUtil.getRandomString( -1, 8 ) + CommonUtil.getServerTime( "%Y%m%d" ) + CommonUtil.getRandomString( -1, 8 ) + "." + uploadFileFormat;
		
		file.transferTo( new File( this.bucketUploadBasePath + this.BUCKET_REP_SUFFIX + monthSuffix + saveFileName ) );
		
		return this.bucketBaseUrl + this.BUCKET_REP_SUFFIX + monthSuffix + saveFileName;
	}
	
	/**
	 * 버킷 스토리 이미지 파일 업로드
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String uplaodBucketStoryImage( MultipartFile file ) throws Exception {
		if( file.getSize() > imgMaxSize ) {
			throw new StudyException( "1001", "업로드 파일의 용량이 초과되었습니다. [Max 10Mb]" );
		}
		String uploadFileName = file.getOriginalFilename();
		String uploadFileFormat = uploadFileName.substring( uploadFileName.lastIndexOf(".") +1 );
		
		// 파일 확장자 검증
		if( !CommonUtil.isAcceptedUploadFileFormat( uploadFileFormat ) ) {
			throw new StudyException( "1002", "[" + uploadFileFormat + "] 형식의 파일은 업로드 할 수 없습니다." );
		}
		
		String monthSuffix = CommonUtil.SEPARATOR + CommonUtil.getServerTime( "%Y%m" );
		String saveFileName = CommonUtil.SEPARATOR + CommonUtil.getRandomString( -1, 8 ) + CommonUtil.getServerTime( "%Y%m%d" ) + CommonUtil.getRandomString( -1, 8 ) + "." + uploadFileFormat;
		
		file.transferTo( new File( this.bucketUploadBasePath + this.BUCKET_STORY_SUFFIX + monthSuffix + saveFileName ) );
		
		return this.bucketBaseUrl + this.BUCKET_STORY_SUFFIX + monthSuffix + saveFileName;
	}
	
	/**
	 * 지출 이미지 파일 업로드
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String uplaodPayImage( MultipartFile file ) throws Exception {
		if( file.getSize() > imgMaxSize ) {
			throw new StudyException( "1001", "업로드 파일의 용량이 초과되었습니다. [Max 10Mb]" );
		}
		String uploadFileName = file.getOriginalFilename();
		String uploadFileFormat = uploadFileName.substring( uploadFileName.lastIndexOf(".") +1 );
		
		// 파일 확장자 검증
		if( !CommonUtil.isAcceptedUploadFileFormat( uploadFileFormat ) ) {
			throw new StudyException( "1002", "[" + uploadFileFormat + "] 형식의 파일은 업로드 할 수 없습니다." );
		}
		
		String monthSuffix = CommonUtil.SEPARATOR + CommonUtil.getServerTime( "%Y%m" );
		String saveFileName = CommonUtil.SEPARATOR + CommonUtil.getRandomString( -1, 8 ) + CommonUtil.getServerTime( "%Y%m%d" ) + CommonUtil.getRandomString( -1, 8 ) + "." + uploadFileFormat;
		
		file.transferTo( new File( this.payUploadBasePath + monthSuffix + saveFileName ) );
		
		return this.payBaseUrl + monthSuffix + saveFileName;
	}
}