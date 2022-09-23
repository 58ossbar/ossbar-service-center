package com.ossbar.modules.wx.pkg.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.medu.api.TmeduApiTokenService;
import com.ossbar.modules.evgl.medu.domain.TmeduApiToken;
import com.ossbar.modules.evgl.pkg.api.TevglPkgInfoService;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 教学包
 * @author huj
 *
 */
@RestController
@RequestMapping("/wx/pkg-api")
public class WxPkgController {

	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TmeduApiTokenService tmeduApiTokenService;
	@Reference(version = "1.0.0.")
	private TevglPkgInfoService tevglPkgInfoService;
	
	/**
	 * 教学包下拉列表（包含自己创建的、被授权的、以及免费的）
	 * @param params
	 * @param token
	 * @return
	 */
	@GetMapping("/listPkgInfoSelect")
	public R listPkgInfoSelect(@RequestParam Map<String, Object> params, String token) {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if(te == null){
			return R.error(401, "Invalid token");
		}
		TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectObjectById(te.getUserId());
		if (traineeInfo == null) {
			return R.error("无效的用户信息");
		}
		/*params.put("displayNo", "3");
		List<Map<String,Object>> list = tevglPkgInfoService.listPkgInfoForSelect(params, traineeInfo.getTraineeId(), true, false);
		return R.ok().put(Constant.R_DATA, list);*/
		return tevglPkgInfoService.queryPkgListByUnionAllForSelect(params, traineeInfo.getTraineeId());
	}
	
}
