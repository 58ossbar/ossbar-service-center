package com.ossbar.modules.evgl.web.controller.tchclass;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.activity.api.TevglActivitySigninTraineeService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/teachingCenter-api/sign-api")
public class SigninController {

	@Reference(version = "1.0.0")
	private TevglActivitySigninTraineeService tevglActivitySigninTraineeService;
	
	/**
	 * 学员签到详情列表
	 * @param params
	 * @return
	 */
	@GetMapping("/queryTraineeSigninDetail")
	public R queryTraineeSigninDetail(@RequestParam Map<String, Object> params) {
		return tevglActivitySigninTraineeService.queryTraineeSigninDetail(params);
	}
	
}
