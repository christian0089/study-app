package com.study.app.pay;

import com.study.app.common.CommonUtil;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import com.study.app.common.CommonDAO;

import java.util.List;
import java.util.Map;

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

	public Map getTotalAmounts(long userSeqno) {
		return sqlSession.selectOne("PayMapper.getTotalAmounts", userSeqno);
	}

	public List<JSONObject> getMonthlyItems(long userSeqno) {
		return sqlSession.selectList("PayMapper.getMonthlyItems", userSeqno);
	}

	public List<JSONObject> getPayTypeItems(JSONObject reqItem) {
		return sqlSession.selectList("PayMapper.getPayTypeItems", reqItem);
	}

	public Map getTodayPaidTotal(long userSeqno) {
		return sqlSession.selectOne("PayMapper.getTodayPaidTotal", userSeqno);
	}

	public List<JSONObject> getTodayPaidItems(long userSeqno) {
		return sqlSession.selectList("PayMapper.getTodayPaidItems", userSeqno);
	}

	public List<JSONObject> getTypePaidItems(JSONObject reqItems) {
		return sqlSession.selectList("PayMapper.getTypePaidItems", reqItems);
	}

	public List<JSONObject> getTypeItems(long userSeqno) {
		return sqlSession.selectList("PayMapper.getTypeItems", userSeqno);
	}

	public Integer checkPayType(JSONObject reqItems) {
		return sqlSession.selectOne("PayMapper.checkPayType", reqItems);
	}

	public void registerPayType(JSONObject reqItem) {
		sqlSession.insert("PayMapper.registerPayType", reqItem);
	}

	public void deletePayType(JSONObject reqItem) {
		sqlSession.update("PayMapper.deletePayType", reqItem);
	}

	public void updatePwd(JSONObject reqItem) {
		sqlSession.update("PayMapper.updatePwd", reqItem);
	}

	public void withdrawal(JSONObject reqItem) {
		sqlSession.update("PayMapper.withdrawal", reqItem);
	}

	public String getCurrentPwd(JSONObject reqItem) {
		return sqlSession.selectOne("PayMapper.getCurrentPwd", reqItem);
	}
}