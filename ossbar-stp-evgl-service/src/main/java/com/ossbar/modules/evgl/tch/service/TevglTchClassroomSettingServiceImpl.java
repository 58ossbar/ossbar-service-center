package com.ossbar.modules.evgl.tch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
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
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.GlobalRoomSetting;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomSettingService;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomSetting;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomSettingMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import com.github.pagehelper.PageHelper;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/tch/tevgltchclassroomsetting")
public class TevglTchClassroomSettingServiceImpl implements TevglTchClassroomSettingService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglTchClassroomSettingServiceImpl.class);
	@Autowired
	private TevglTchClassroomSettingMapper tevglTchClassroomSettingMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/tch/tevgltchclassroomsetting/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglTchClassroomSetting> tevglTchClassroomSettingList = tevglTchClassroomSettingMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglTchClassroomSettingList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/tch/tevgltchclassroomsetting/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglTchClassroomSettingList = tevglTchClassroomSettingMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglTchClassroomSettingList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglTchClassroomSetting
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/tch/tevgltchclassroomsetting/save")
	public R save(@RequestBody(required = false) TevglTchClassroomSetting tevglTchClassroomSetting) throws OssbarException {
		tevglTchClassroomSetting.setSettingId(Identities.uuid());
		ValidatorUtils.check(tevglTchClassroomSetting);
		tevglTchClassroomSettingMapper.insert(tevglTchClassroomSetting);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglTchClassroomSetting
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/tch/tevgltchclassroomsetting/update")
	public R update(@RequestBody(required = false) TevglTchClassroomSetting tevglTchClassroomSetting) throws OssbarException {
	    ValidatorUtils.check(tevglTchClassroomSetting);
		tevglTchClassroomSettingMapper.update(tevglTchClassroomSetting);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/tch/tevgltchclassroomsetting/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglTchClassroomSettingMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/tch/tevgltchclassroomsetting/deleteBatch")
	public R deleteBatch(@RequestBody(required = true) String[] ids) throws OssbarException {
		tevglTchClassroomSettingMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/tch/tevgltchclassroomsetting/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglTchClassroomSettingMapper.selectObjectById(id));
	}

	/**
	 * 保存设置
	 * @param ctId
	 * @param traineeId
	 * @param radio1
	 * @param radio2 up标识升序，down标识降序
	 * @return
	 */
	@Override
	public R saveSetting(String ctId, String traineeId, String radio1, String radio2) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(traineeId)) {
			return R.error("必传参数为空");
		}
		if (StrUtils.isEmpty(radio1) || "undefined".equals(radio1) || "NaN".equals(radio1)) {
			return R.error("请选择排序条件");
		}
		if (StrUtils.isEmpty(radio2) || "undefined".equals(radio2)) {
			return R.error("请选择升序还是降序");
		}
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom == null || !tevglTchClassroom.getCreateUserId().equals(traineeId)) {
			return R.ok("无法设置");
		}
		radio2 = "up".equals(radio2) ? "asc" : "desc";
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		params.put("modules", GlobalRoomSetting.MDULES_CLASSROOM_TRAINEE);
		List<TevglTchClassroomSetting> list = tevglTchClassroomSettingMapper.selectListByMap(params);
		if (list == null || list.size() == 0) {
			TevglTchClassroomSetting t = new TevglTchClassroomSetting();
			t.setSettingId(Identities.uuid());
			t.setCtId(ctId);
			t.setModule(GlobalRoomSetting.MDULES_CLASSROOM_TRAINEE);
			t.setSidx(radio1);
			t.setOrder(radio2);
			t.setCreateTime(DateUtils.getNowTimeStamp());
			tevglTchClassroomSettingMapper.insert(t);
		} else {
			TevglTchClassroomSetting t = list.get(0);
			t.setSidx(radio1);
			t.setOrder(radio2);
			tevglTchClassroomSettingMapper.update(t);
		}
		return R.ok("保存成功");
	}

	@Override
	public R viewSetting(String ctId) {
		if (StrUtils.isEmpty(ctId)) {
			return R.error("必传参数为空");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", ctId);
		params.put("modules", GlobalRoomSetting.MDULES_CLASSROOM_TRAINEE);
		List<TevglTchClassroomSetting> classroomTraineeSettingList = tevglTchClassroomSettingMapper.selectListByMap(params);
		Map<String, Object> data = new HashMap<>();
		if (classroomTraineeSettingList.size() > 0) {
			data.put("radio1", classroomTraineeSettingList.get(0).getSidx());
			data.put("radio2", "asc".equals(classroomTraineeSettingList.get(0).getOrder()) ? "up" : "down");	
		}
		return R.ok().put(Constant.R_DATA, data);
	}
}
