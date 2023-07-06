package com.ossbar.modules.wx.tch.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.tch.api.TevglTchClassService;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 班级相关
 * @author huj
 *
 */
@RestController
@RequestMapping("/wx/class")
public class WxClassController {

	@Reference(version = "1.0.0")
	private TevglTchClassService tevglTchClassService;
	
	@GetMapping("/getClassDictTypeList")
	public R getClassDictTypeList(@RequestParam Map<String, Object> params){
		return tevglTchClassService.getClassDictTypeList(params);
	}
	
	/**
	 * 根据条件查询班级（单科强化、订单就业）
	 * @param params
	 * @return
	 */
	@GetMapping("queryClassListForWeb")
	public R queryClassListForWeb(@RequestParam Map<String, Object> params) {
		if (StrUtils.isNull(params.get("type"))) {
			params.put("type", "1");
		}
		params.put("classState", "1"); // 1开放，3授课，4完成
		return tevglTchClassService.queryClassListForWeb(params);
	}
	
	/**
	 * 查看详情
	 * @param classId
	 * @return
	 */
	@GetMapping("/view/{id}")
	public R view(@PathVariable("id") String classId) {
		return tevglTchClassService.view(classId);
	}
}
