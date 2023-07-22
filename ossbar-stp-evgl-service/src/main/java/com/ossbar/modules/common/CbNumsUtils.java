package com.ossbar.modules.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ossbar.modules.evgl.medu.me.persistence.TmeduMeLikeMapper;
import com.ossbar.modules.evgl.site.persistence.TevglSiteSysMsgMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;


/**
 * 即时通讯im的一些数量的工具类
 * @author huj
 *
 */
@Component
@RefreshScope
public class CbNumsUtils {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TmeduMeLikeMapper tmeduMeLikeMapper;
	@Autowired
	private TevglSiteSysMsgMapper tevglSiteSysMsgMapper;

	
	/**
	 * 获取各种未读数量
	 * @param loginUserId
	 * @return
	 */
	public JSONObject getNums(String loginUserId) {
		JSONObject msg = new JSONObject();
		msg.put("busitype", "numbers/get");
		msg.put("msgtype", "other");
		JSONObject busiMsg = new JSONObject();
		busiMsg.put("send_id", loginUserId);
		busiMsg.put("user_id", loginUserId);
		// 查询条件
		Map<String, Object> params = new HashMap<>();
		// 标识
		boolean hasNum = false;
		// 统计未读点赞数
		int unReadLikeNum = tmeduMeLikeMapper.countUnreadNum(loginUserId);
		// 未读系统通知数
		int unReadMsgNum = tevglSiteSysMsgMapper.countUnReadMsgNum(loginUserId);
		// 统计未读回复数
		
		// 统计未读群聊消息数
		params.put("userId", loginUserId);
		params.put("readState", "1"); // 阅读状态(1.未读;2.已读)
		// 统计未读私聊消息数
		params.clear();
		params.put("receiveId", loginUserId);
		params.put("readState", "N");
		if (unReadLikeNum > 0) {
			hasNum = true;
		}
		if (unReadMsgNum > 0) {
			hasNum = true;
		}

		// 发送数据
		busiMsg.put("hasNum", hasNum); // 是否显示红点
		busiMsg.put("unReadLikeNum", unReadLikeNum); // 未读点赞数
		busiMsg.put("unReadMsgNum", unReadMsgNum); // 未读系统通知数
		busiMsg.put("unReadReplyNum", 0); // 未读回复数
		msg.put("msg", busiMsg);
		return msg;
	}
	
}
