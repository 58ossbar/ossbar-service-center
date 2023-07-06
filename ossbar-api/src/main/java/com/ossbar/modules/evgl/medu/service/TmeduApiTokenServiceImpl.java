package com.ossbar.modules.evgl.medu.service;

import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.modules.evgl.medu.api.TmeduApiTokenService;
import com.ossbar.modules.evgl.medu.domain.TmeduApiToken;
import com.ossbar.modules.evgl.medu.persistence.TmeduApiTokenMapper;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service(version = "1.0.0")
@RestController
@RequestMapping("/medu/apitoken")
public class TmeduApiTokenServiceImpl implements TmeduApiTokenService {

	@Autowired
	private TmeduApiTokenMapper tmeduApiTokenMapper;
	// 12小时后过期
	private final static int EXPIRE = 3600 * 12;
	
	@Override
	public TmeduApiToken selectTokenByUserId(String userId) {
		return tmeduApiTokenMapper.selectTokenByUserId(userId);
	}

	@Override
	public TmeduApiToken selectTokenByToken(String token) {
		return tmeduApiTokenMapper.selectTokenByToken(token);
	}

	@Override
	public TmeduApiToken selectTokenByMobile(String mobile, String userType) {
		return tmeduApiTokenMapper.selectTokenByMobile(mobile,userType);
	}

	@Override
	public TmeduApiToken selectTokenByopenid(String openid, String userType) {
		return tmeduApiTokenMapper.selectTokenByopenid(openid,userType);
	}

	@Override
	public void insert(TmeduApiToken token) {
		token.setTokenId(Identities.uuid());
		tmeduApiTokenMapper.insert(token);
	}

	@Override
	public void update(TmeduApiToken token) {
		tmeduApiTokenMapper.update(token);
	}

	@Override
	public void updateByMobile(TmeduApiToken token) {
		tmeduApiTokenMapper.updateByMobile(token);
	}

	@Override
	public void updateByOpenid(TmeduApiToken token) {
		tmeduApiTokenMapper.updateByOpenid(token);
	}

	@Override
	public Map<String, Object> createToken(String userId) {
		// 生成一个token
				String token = UUID.randomUUID().toString();
				// 当前时间
				Date now = new Date();

				// 过期时间
				Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

				// 判断是否生成过token
				TmeduApiToken TapiToken = selectTokenByUserId(userId);
				if (TapiToken == null) {
					TapiToken = new TmeduApiToken();
					TapiToken.setUserId(userId);
					TapiToken.setToken(token);
					TapiToken.setUpdateTime(DateUtils.getNowTimeStamp());
					TapiToken.setExpireTime(DateUtils.format(expireTime,DateUtils.DATE_TIME_PATTERN));

					// 保存token
					insert(TapiToken);
				} else {
					TapiToken.setToken(token);
					TapiToken.setUpdateTime(DateUtils.getNowTimeStamp());
					TapiToken.setExpireTime(DateUtils.format(expireTime,DateUtils.DATE_TIME_PATTERN));

					// 更新token
					update(TapiToken);
				}

				Map<String, Object> map = new HashMap<>();
				map.put("token", token);
				map.put("expire", EXPIRE);

				return map;
	}

	@Override
	public void deleteTokenById(String mobile, String userType) throws CreatorblueException {
		tmeduApiTokenMapper.deleteTokenById(mobile, userType);
	}

}
