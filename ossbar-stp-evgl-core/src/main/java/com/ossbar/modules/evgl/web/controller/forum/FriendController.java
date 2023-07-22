package com.ossbar.modules.evgl.web.controller.forum;

import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.forum.api.TevglForumFriendTypeService;

/**
 * 友情社区
 * @author huj
 *
 */
@RestController
@RequestMapping("/site/forum/friend")
public class FriendController {
	
	@Reference(version = "1.0.0")
	private TevglForumFriendTypeService tevglForumFriendTypeService;
	
	/**
	 * 分类列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/queryTypeList")
	public R queryTypeList(@RequestParam Map<String, Object> params) {
		return tevglForumFriendTypeService.queryTypeList(params);
	}
	
}
