package com.ossbar.modules.evgl.eao.api;

import java.util.Map;

import com.ossbar.common.exception.OssbarException;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.eao.domain.TeaoFdTrade;

public interface TeaoFdTradeService {

	R query(Map<String, Object> params);
	
	void save(TeaoFdTrade teaoFdTrade) throws OssbarException;
	
}
