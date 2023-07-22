package com.ossbar.modules.evgl.tch.imhandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.redis.RedisUtils;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.StrUtils;

/**
 * 已废弃，等待删除
 * @author huj
 *
 */
@Service
public class TevglTchClassroomHandler {

	private static Logger log = LoggerFactory.getLogger(TevglTchClassroomHandler.class);
	
	@Autowired
	private RedisUtils redisUtils;

	

	
}
