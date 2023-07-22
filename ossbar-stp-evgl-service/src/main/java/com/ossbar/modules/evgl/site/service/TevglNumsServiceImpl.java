package com.ossbar.modules.evgl.site.service;

import java.util.HashMap;
import java.util.Map;

import com.ossbar.modules.evgl.medu.me.persistence.TmeduMeLikeMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.api.TevglNumsService;
import com.ossbar.modules.evgl.site.persistence.TevglSiteSysMsgMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.StrUtils;

/**
 * 统计一些数量
 * @author huj
 *
 */
@Service(version = "1.0.0")
public class TevglNumsServiceImpl implements TevglNumsService {

	@Autowired
	private TmeduMeLikeMapper tmeduMeLikeMapper;
	@Autowired
	private TevglSiteSysMsgMapper tevglSiteSysMsgMapper;
	
	/**
	 * 统计这个人的一些未读数
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R queryNums(String loginUserId) {
		if (StrUtils.isEmpty(loginUserId)) {
			return R.error();
		}
		Map<String, Object> data = new HashMap<>();
		// 未读点赞数
		int unReadLikeNum = tmeduMeLikeMapper.countUnreadNum(loginUserId);
		// 未读系统通知数
		int unReadMsgNum = tevglSiteSysMsgMapper.countUnReadMsgNum(loginUserId);
		// 返回数据给前端
		data.put("unReadLikeNum", unReadLikeNum); // 未读点赞数
		data.put("unReadReplyNum", 0); // 未读回复数
		data.put("unReadMsgNum", unReadMsgNum); // 未读系统通知数
		return R.ok().put(Constant.R_DATA, data);
	}

}
