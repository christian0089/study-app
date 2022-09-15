package com.study.app.pay;

import com.study.app.common.CommonUtil;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import com.study.app.common.CommonDAO;

@Repository( "com.study.app.pay.PayDAO" )
public class PayDAO extends CommonDAO {

	public long login(JSONObject reqItem) {
		return sqlSession.selectOne( "PayMapper.getLoginUserSeqno", reqItem );
	}

    public long registerPay(JSONObject reqItem) {
		sqlSession.insert("PayMapper.registerPay", reqItem);
		return CommonUtil.getLong( reqItem.get("paySeqno") );
	}

	public void registerPayImage(JSONObject reqItem) {
		sqlSession.insert("PayMapper.registerPayImage", reqItem);
	}

}