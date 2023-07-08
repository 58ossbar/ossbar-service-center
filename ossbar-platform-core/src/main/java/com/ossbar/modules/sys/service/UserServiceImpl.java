package com.ossbar.modules.sys.service;

import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ossbar.modules.sys.api.UserService;
import com.ossbar.modules.sys.domain.User;

@Api(tags="用户接口")
@Service(version = "1.0.0")
@RestController
@RequestMapping("/")
public class UserServiceImpl implements UserService {

	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 保存用户
	 * @param user
	 * @return 受影响的行数
	 */
	@RequestMapping("user")
	@SentinelResource("user")
	public Integer save(User user) {
		log.info(user.toString());
		// 假装保存成功返回1
		return 1;
	}

}
