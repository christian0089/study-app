package com.study.app.pay;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import com.study.app.common.CommonDAO;

@Repository( "com.study.app.pay.PayDAO" )
public class PayDAO extends CommonDAO {

	public long login(JSONObject reqItem) {
		return sqlSession.selectOne( "PayMapper.getLoginUserSeqno", reqItem );
	}
}