package com.study.app.common;

import java.util.Map;

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
	
	/**
	 * 
	 * @param targetMap : 대상 MAP
	 * @param reqKeys : 필수키 목록
	 * @desc : 필수키 포함여부와 유효값 벨리데이션 체크를 한다.
	 * @throws Exception
	 */
	public void checkVal(Map<String, Object> targetMap, String[] reqKeys) throws Exception {
		// 필수키 체크
		for(String key : reqKeys) {
			Object item = (Object) targetMap.get(key);
			
			// 필수키 누락
			if( item == null ) {
				throw new StudyException("4000", "[" + key + "] 값이 누락되었습니다");
			}
			
			String format = item.getClass().getName().substring( item.getClass().getName().lastIndexOf(".") + 1 );
			
			// 값 누락
			if("String".equals(format)) {
				if( "".equals( ((String)item).trim() ) ) {
					throw new StudyException("4000", "[" + key + "] 값이 누락되었습니다");
				}
				
				if("svcGb".equals(key) && !"B".equals(item)) {
					throw new StudyException("4001", "서비스구분코드가 상이합니다");
				}
			}
		}
	}
}