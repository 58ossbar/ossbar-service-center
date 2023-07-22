package com.ossbar.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.common.validator.ValidatorUtils;
import org.apache.dubbo.config.annotation.Service;
//import com.ossbar.modules.sys.domain.TsysUserinfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysSettingsService;
import com.ossbar.modules.sys.domain.TsysSettings;
import com.ossbar.modules.sys.persistence.TsysSettingsMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.github.pagehelper.PageHelper;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2019
 * </p>
 * <p>
 * Company:ossbar.co.,ltd
 * </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/settings")
public class TsysSettingsServiceImpl implements TsysSettingsService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TsysSettingsServiceImpl.class);
	@Autowired
	private TsysSettingsMapper tsysSettingsMapper;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private ConvertUtil convertUtil;

	/**
	 * 查询列表(返回List<Bean>)
	 * 
	 * @param Map
	 * @return R
	 */
	@SysLog(value = "查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/sys/settings/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<TsysSettings> tsysSettingsList = tsysSettingsMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tsysSettingsList, "createUserId", "updateUserId");
		PageUtils pageUtil = new PageUtils(tsysSettingsList, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}

	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * 
	 * @param Map
	 * @return R
	 */
	@SysLog(value = "查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/sys/settings/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<Map<String, Object>> tsysSettingsList = tsysSettingsMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tsysSettingsList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tsysSettingsList, query.getPage(), query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}

	/**
	 * 新增
	 * 
	 * @param tsysSettings
	 * @throws OssbarException
	 */
	@SysLog(value = "新增")
	@PostMapping("save")
	@SentinelResource("/sys/settings/save")
	public R save(@RequestBody(required = false) TsysSettings tsysSettings) throws OssbarException {
		tsysSettings.setSettingId(Identities.uuid());
		tsysSettings.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tsysSettings.setCreateTime(DateUtils.getNowTimeStamp());
		//ValidatorUtils.check(tsysSettings);
		tsysSettingsMapper.insert(tsysSettings);
		return R.ok();
	}

	/**
	 * 修改
	 * 
	 * @param tsysSettings
	 * @throws OssbarException
	 */
	@SysLog(value = "修改")
	@PostMapping("update")
	@SentinelResource("/sys/settings/update")
	public R update(@RequestBody(required = false) TsysSettings tsysSettings) throws OssbarException {
		tsysSettings.setUpdateUserId(serviceLoginUtil.getLoginUserId());
		tsysSettings.setUpdateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tsysSettings);
		tsysSettingsMapper.update(tsysSettings);
		return R.ok();
	}

	/**
	 * 单条删除
	 * 
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value = "单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/sys/settings/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tsysSettingsMapper.delete(id);
		return R.ok();
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value = "批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/sys/settings/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tsysSettingsMapper.deleteBatch(ids);
		return R.ok();
	}

	/**
	 * 查看明细
	 * 
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value = "查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/sys/settings/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tsysSettingsMapper.selectObjectById(id));
	}

	/**
	 * 批量修改
	 */
	@SysLog(value = "批量修改")
	@PostMapping("/updateBatch")
	@SentinelResource("/sys/settings/updateBatch")
	public R updateBatchSettings(@RequestBody List<TsysSettings> settings) {
		settings.stream().forEach(setting -> {
			update(setting);
		});
		return R.ok();
	}

	/**
	 * 查询设置 settingType 系统设置或用户设置（必填） settingUserId 用户设置则必填
	 * 
	 * @param map
	 * @return
	 */
	@SysLog(value = "查询")
	@GetMapping("/querySettings")
	@SentinelResource("/sys/settings/querySettings")
	@Override
	public R querySetting(Map<String, Object> map) {
		if (map.get("settingType") == null || map.get("settingType").equals("")) {
			map.put("settingType", "sys");
		}
		List<TsysSettings> settings = tsysSettingsMapper.selectListByMap(map);
		return R.ok().put("data", settings);
	}

	public enum SettingCodeEnum {
		// 登录页面logo标题
		LOGOTITLE("logoTitle", "logoTitle", "创蓝教育实训云平台"),
		// 公司信息
		COMPANYINFO("companyInfo", "companyInfo", "湖南创蓝信息科技有限公司 "),
		// 联系信息
		CONTACTINFO("contactInfo", "contactInfo", "0731-89913439"),
		// 创蓝图标
		CBLOGO("cbLogo", "cbLogo", "./static/img/logo.a39a449.png"),
		// 登录背景图
		LOGINBGIMG("loginBgImg", "loginBgImg", "../../static/img/bjt.565c312.jpg");
		public String getDeafultValue() {
			return deafultValue;
		}

		public void setDeafultValue(String deafultValue) {
			this.deafultValue = deafultValue;
		}

		private SettingCodeEnum(String settingCodeKey, String settingCodeValue, String deafultValue) {
			this.settingCodeKey = settingCodeKey;
			this.settingCodeValue = settingCodeValue;
			this.deafultValue = deafultValue;
		}

		private String settingCodeKey;
		private String settingCodeValue;
		private String deafultValue;

		public String getSettingCodeKey() {
			return settingCodeKey;
		}

		public void setSettingCodeKey(String settingCodeKey) {
			this.settingCodeKey = settingCodeKey;
		}

		public String getSettingCodeValue() {
			return settingCodeValue;
		}

		public void setSettingCodeValue(String settingCodeValue) {
			this.settingCodeValue = settingCodeValue;
		}

	}

	@SysLog(value = "根据id查询数据")
	@GetMapping("/get")
	@SentinelResource("/sys/settings/get")
	@Override
	public R selectObjectById(String id) {
		return R.ok().put("data", tsysSettingsMapper.selectObjectById(id));
	}

	@Override
	public List<TsysSettings> selectListByMap(Map<String, Object> map) {
		return tsysSettingsMapper.selectListByMap(map);
	}
}
