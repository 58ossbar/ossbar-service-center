package com.ossbar.modules.sys.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.core.common.cbsecurity.log.SysLog;
import com.ossbar.modules.core.common.utils.LoginUtils;
import com.ossbar.modules.sys.api.TsysParameterService;
import com.ossbar.modules.sys.domain.TsysParameter;

@RestController
@RequestMapping("/api/sys/parameter")
public class TsysParameterController {
	@Reference(version = "1.0.0")
	private TsysParameterService tsysParameterService;
	@Autowired
	private LoginUtils loginUtils;

	/**
	 * 保存修改
	 * 
	 * @param tsysparameter
	 * @return R
	 * @author huangwb
	 * @date 2019-05-20 15:18
	 */
	@PostMapping("/saveOrUpdate")
	@PreAuthorize("hasAuthority('sys:tsysparameter:add') and hasAuthority('sys:tsysparameter:edit')")
	@SysLog("参数信息保存或修改")
	public R saveorupdate(@RequestBody(required = false) TsysParameter tsysparameter) {
		if (tsysparameter.getParaid() == null || StringUtils.isBlank(tsysparameter.getParaid())) {
			tsysparameter.setCreateUserId(loginUtils.getLoginUserId());
		} else {
			tsysparameter.setUpdateUserId(loginUtils.getLoginUserId());
		}
		return tsysParameterService.saveorupdate(tsysparameter);
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return R
	 * @author huangwb
	 * @date 2019-05-20 15:18
	 */
	@DeleteMapping("/deletes")
	@PreAuthorize("hasAuthority('sys:tsysparameter:remove')")
	@SysLog("删除参数信息")
	public R delete(@RequestBody(required = false) String[] ids) {
		if (ids == null) {
			return R.error("您的参数信息有误，请检查参数信息是否正确");
		}
		return tsysParameterService.delete(ids);
	}

	/**
	 * 所有配置列表
	 * 
	 * @param params (page页码,limit显示条数，paraType字典类型)
	 * @return R
	 * @author huangwb
	 * @date 2019-05-20 15:18
	 */
	@GetMapping("/query")
	@PreAuthorize("hasAuthority('sys:tsysparameter:query')")
	@SysLog("查询所有参数信息")
	public R list(@RequestParam(required = false) Map<String, Object> params) {
		return tsysParameterService.list(params);
	}

	/**
	 * 根据参数Id获取参数详情
	 * 
	 * @param parameterId
	 * @return R
	 * @author huangwb
	 * @date 2019-05-20 15:18
	 */
	@GetMapping("/get/{parameterId}")
	@PreAuthorize("hasAuthority('sys:tsysparameter:view')")
	@SysLog("获取指定参数详情")
	public R getParameterInfo(@PathVariable("parameterId") String parameterId) {
		return tsysParameterService.get(parameterId);
	}

	/**
	 * 根据参数管理左侧的树结构
	 * 
	 * @return R
	 * @author huangwb
	 * @date 2019-05-20 15:18
	 */
	@GetMapping("/paraTree")
	@PreAuthorize("hasAuthority('sys:tsysparameter:query')")
	@SysLog("参数管理左侧树结构")
	public R paraTree() {
		return tsysParameterService.paraTree();
	}

	@GetMapping("/getPara")
	@SysLog("查询下拉选项参数信息")
	public R getPara(@RequestParam("paraType")String paraType) {
		return tsysParameterService.getPara(paraType);
	}
}
