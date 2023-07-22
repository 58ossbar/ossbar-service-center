package com.ossbar.modules.evgl.web.controller;

import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.stu.api.TevglStuStarService;

/**
 * <p>就业明星、学员感言、优秀学生</p>
 * <p>Title: TraineeStory.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: 湖南创蓝信息科技有限公司</p> 
 * @author huj
 * @date 2019年7月4日
 */
@RestController
@RequestMapping("trainee-story")
public class TraineeStoryController {
	
	@Reference(version = "1.0.0")
	private TevglStuStarService tevglStuStarService;
	
	/**
	 * <p>查看列表</p>
	 * @author huj
	 * @data 2019年7月10日
	 * @param parmas
	 * @return
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> parmas) {
		return tevglStuStarService.query(parmas);
	}
	
	/**
	 * <p>查看明细</p>
	 * @author huj
	 * @data 2019年7月4日
	 * @param id
	 * @return
	 */
	@GetMapping("/view/{id}")
	public R view(@PathVariable("id") String id) {
		return tevglStuStarService.view(id);
	}

}
