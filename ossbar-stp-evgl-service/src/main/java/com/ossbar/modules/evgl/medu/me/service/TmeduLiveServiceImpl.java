package com.ossbar.modules.evgl.medu.me.service;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.medu.me.api.TmeduLiveService;
import com.ossbar.modules.sys.domain.TsysSettings;
import com.ossbar.modules.sys.persistence.TsysSettingsMapper;
import com.ossbar.utils.constants.Constant;

import weixin.popular.api.WxaLiveAPI;
import weixin.popular.bean.CbBaseResult;

/**
 * 小程序直播服务类
 * @author zhuq
 *
 */
@Service(version = "1.0.0")
public class TmeduLiveServiceImpl implements TmeduLiveService{

	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TmeduLiveServiceImpl.class);
	@Autowired
	private TsysSettingsMapper tsysSettingsMapper;
	
	/**
	 * 从数据库中获取小程序accessToken
	 * @return
	 */
	public String getAccessToken() {
		TsysSettings settings = tsysSettingsMapper.selectObjectById("wxaccesstoken");
		return settings == null ? null : settings.getSettingValue(); 
	}
	
	/**
	 * 获取直播间列表数据
	 * @param start 从第几条开始
	 * @param limit 拉取多少条
	 */
	@Override
	public R getliveinfo(Integer start, Integer limit) {
		CbBaseResult liveinfo = WxaLiveAPI.getliveinfo(getAccessToken(), start, limit);
		return R.ok().put(Constant.R_DATA, liveinfo);
	}

}
