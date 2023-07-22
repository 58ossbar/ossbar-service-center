package com.ossbar.modules.evgl.eao.service;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ossbar.common.exception.OssbarException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.eao.api.TeaoFdTradeService;
import com.ossbar.modules.evgl.eao.domain.TeaoFdTrade;
import com.ossbar.modules.evgl.eao.persistence.TeaoFdTradeMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.Identities;
import com.github.pagehelper.PageHelper;

@Service(version = "1.0.0")
public class TeaoFdTradeServiceImpl implements TeaoFdTradeService {

	@Autowired
	private TeaoFdTradeMapper teaoFdTradeMapper;
	@Autowired
    private ConvertUtil convertUtil;
	
	@Override
	public void save(TeaoFdTrade teaoFdTrade) throws OssbarException {
		teaoFdTrade.setTid(Identities.uuid());
		teaoFdTradeMapper.insert(teaoFdTrade);
	}

	@Override
	public R query(Map<String, Object> params) {
		// 构建查询条件对象Query
		if(params.get("sttime") != null && params.get("sttime").toString().length() > 0){
			params.put("sttime",params.get("sttime")+" 00:00:00");
		}
		if(params.get("ettime") != null && params.get("ettime").toString().length() > 0){
			params.put("ettime",params.get("ettime")+" 23:59:59");
		}
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TeaoFdTrade> teaoFdTradeList = teaoFdTradeMapper.selectListByMap(query);
		convertUtil.convertOrgId(teaoFdTradeList, "orgId");
		convertUtil.convertDict(teaoFdTradeList, "tpayment", "tpayment");
		PageUtils pageUtil = new PageUtils(teaoFdTradeList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}

}
