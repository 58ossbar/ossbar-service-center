package com.ossbar.modules.evgl.empirical.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ossbar.modules.evgl.empirical.persistence.TevglEmpiricalSettingMapper;
import com.ossbar.modules.evgl.empirical.persistence.TevglEmpiricalTypeMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.CbRoomUtils;
import com.ossbar.modules.common.DictService;
import com.ossbar.modules.common.enums.EmpiricalValueEnum;
import com.ossbar.modules.evgl.empirical.api.TevglEmpiricalSettingService;
import com.ossbar.modules.evgl.empirical.domain.TevglEmpiricalSetting;
import com.ossbar.modules.evgl.empirical.domain.TevglEmpiricalType;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
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
@RequestMapping("/empirical/tevglempiricalsetting")
public class TevglEmpiricalSettingServiceImpl implements TevglEmpiricalSettingService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglEmpiricalSettingServiceImpl.class);
	@Autowired
	private TevglEmpiricalTypeMapper tevglEmpiricalTypeMapper;
	@Autowired
	private TevglEmpiricalSettingMapper tevglEmpiricalSettingMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private DictService dictService;
	@Autowired
	private CbRoomUtils roomUtils;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/empirical/tevglempiricalsetting/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglEmpiricalSetting> tevglEmpiricalSettingList = tevglEmpiricalSettingMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglEmpiricalSettingList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/empirical/tevglempiricalsetting/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglEmpiricalSettingList = tevglEmpiricalSettingMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglEmpiricalSettingList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglEmpiricalSetting
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/empirical/tevglempiricalsetting/save")
	public R save(@RequestBody(required = false) TevglEmpiricalSetting tevglEmpiricalSetting) throws OssbarException {
		tevglEmpiricalSetting.setStId(Identities.uuid());
		ValidatorUtils.check(tevglEmpiricalSetting);
		tevglEmpiricalSettingMapper.insert(tevglEmpiricalSetting);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglEmpiricalSetting
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/empirical/tevglempiricalsetting/update")
	public R update(@RequestBody(required = false) TevglEmpiricalSetting tevglEmpiricalSetting) throws OssbarException {
	    ValidatorUtils.check(tevglEmpiricalSetting);
		tevglEmpiricalSettingMapper.update(tevglEmpiricalSetting);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/empirical/tevglempiricalsetting/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglEmpiricalSettingMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/empirical/tevglempiricalsetting/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglEmpiricalSettingMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/empirical/tevglempiricalsetting/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglEmpiricalSettingMapper.selectObjectById(id));
	}

	/**
	 * 经验值设置页面
	 * @param ctId
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R viewEmpiricalSetting(String ctId, String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom == null) {
			return R.error("无效的课堂，数据获取未成功");
		}
		boolean hasAuth = roomUtils.hasOperatingAuthorization(tevglTchClassroom, loginUserId);
		if (!hasAuth) {
			return R.error("非法操作，没有权限");
		}
		// 从字典获取，活教材，考勤，活动
		List<Map<String,Object>> dictList = dictService.getDictList("empirical_type");
		if (dictList == null || dictList.size() == 0) {
			return R.error("数据获取未成功");
		}
		List<Map<String,Object>> empiricalRulesList = dictService.getDictList("empirical_rules");
		if (empiricalRulesList == null || empiricalRulesList.size() == 0) {
			return R.error("数据获取未成功");
		}
		// 规则大类
		List<Map<String,Object>> bigTypeList = getBigTypeList(ctId, loginUserId, dictList);
		// 具体规则
		List<Map<String,Object>> ruleList = getRuleList(ctId, loginUserId, empiricalRulesList, bigTypeList);
		bigTypeList.stream().forEach(typeInfo -> {
			List<Map<String, Object>> children = ruleList.stream().filter(a -> a.get("typeId").equals(typeInfo.get("typeId"))).collect(Collectors.toList());
			// 单独设置下
			if ("3".equals(typeInfo.get("dictCode"))) {
				Map<String, Object> m = new HashMap<>();
				m.put("stId", Identities.uuid());
				m.put("typeId", typeInfo.get("typeId"));
				m.put("flag", true);
				m.put("name", "测试活动，总分累加");
				m.put("value", null);
				children.add(m);
				Map<String, Object> m2 = new HashMap<>();
				m2.put("stId", Identities.uuid());
				m2.put("typeId", typeInfo.get("typeId"));
				m2.put("flag", true);
				m2.put("name", "课堂表现，老师评分");
				m2.put("value", null);
				children.add(m2);
				Map<String, Object> m3 = new HashMap<>();
				m3.put("stId", Identities.uuid());
				m3.put("typeId", typeInfo.get("typeId"));
				m3.put("flag", true);
				m3.put("name", "实现考核，总分累加");
				m3.put("value", null);
				children.add(m3);
			}
			typeInfo.put("children", children);
		});
		return R.ok().put(Constant.R_DATA, bigTypeList);
	}

	private List<Map<String, Object>> getBigTypeList(String ctId, String loginUserId, List<Map<String,Object>> dictList) {
		List<Map<String,Object>> resultList = new ArrayList<>();
		// 先判断课堂是否有记录，有则更新，无则新增
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ctId", ctId);
		List<TevglEmpiricalType> list = tevglEmpiricalTypeMapper.selectListByMap(params);
		for (Map<String, Object> dict : dictList) {
			String dictCode = dict.get("dictCode").toString();
			String dictValue = dict.get("dictValue").toString();
			if (!list.stream().anyMatch(a -> a.getDictCode().equals(dictCode))) {
				int val = 0;
				switch (dictCode) {
				case "1":
					val = 40;
					break;
				case "2":
					val = 40;
					break;
				case "3":
					val = 20;
					break;

				default:
					break;
				}
				TevglEmpiricalType t = new TevglEmpiricalType();
				t.setTypeId(Identities.uuid());
				t.setCtId(ctId);
				t.setDictCode(dictCode);
				t.setName(dictValue);
				t.setWeight(new BigDecimal(val));
				t.setCreateTime(DateUtils.getNowTimeStamp());
				t.setCreateUserId(loginUserId);
				tevglEmpiricalTypeMapper.insert(t);
				Map<String, Object> m = new HashMap<>();
				m.put("typeId", t.getTypeId());
				m.put("dictCode", dictCode);
				m.put("name", t.getName());
				m.put("weight", val);
				if (!resultList.contains(m)) {
					resultList.add(m);
				}
			} else {
				List<TevglEmpiricalType> collect = list.stream().filter(a -> a.getDictCode().equals(dictCode)).collect(Collectors.toList());
				if (collect != null && collect.size() > 0) {
					TevglEmpiricalType t = collect.get(0);
					Map<String, Object> m = new HashMap<>();
					m.put("typeId", t.getTypeId());
					m.put("dictCode", dictCode);
					m.put("name", t.getName());
					m.put("weight", t.getWeight());
					if (!resultList.contains(m)) {
						resultList.add(m);
					}	
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 
	 * @param ctId
	 * @param loginUserId
	 * @param dictList
	 * @param bigTypeList
	 * @return
	 */
	private List<Map<String, Object>> getRuleList(String ctId, String loginUserId, List<Map<String,Object>> dictList, List<Map<String,Object>> bigTypeList){
		List<Map<String,Object>> resultList = new ArrayList<>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ctId", ctId);
		List<TevglEmpiricalSetting> list = tevglEmpiricalSettingMapper.selectListByMap(params);
		for (Map<String, Object> dict : dictList) {
			String dictCode = dict.get("dictCode").toString();
			String dictValue = dict.get("dictValue").toString();
			if (!list.stream().anyMatch(a -> a.getDictCode().equals(dictCode))) {
				TevglEmpiricalSetting t = new TevglEmpiricalSetting();
				t.setStId(Identities.uuid());
				t.setCtId(ctId);
				t.setDictCode(dictCode);
				t.setName(dictValue);
				t.setValue(EmpiricalValueEnum.getValueByCode(dictCode));
				// 设置typeId
				String type = getByMap(dictCode);
				List<Map<String,Object>> collect = bigTypeList.stream().filter(a -> a.get("dictCode").equals(type)).collect(Collectors.toList());
				if (collect != null && collect.size() > 0) {
					t.setTypeId(collect.get(0).get("typeId").toString());
				}
				tevglEmpiricalSettingMapper.insert(t);
				Map<String, Object> m = new HashMap<>();
				m.put("stId", t.getStId());
				m.put("typeId", t.getTypeId());
				m.put("dictCode", dictCode);
				m.put("name", t.getName());
				m.put("value", t.getValue());
				if (!resultList.contains(m)) {
					resultList.add(m);
				}
			} else {
				List<TevglEmpiricalSetting> tevglEmpiricalSettingList = list.stream().filter(a -> a.getDictCode().equals(dictCode)).collect(Collectors.toList());
				TevglEmpiricalSetting t = tevglEmpiricalSettingList.get(0);
				Map<String, Object> m = new HashMap<>();
				m.put("stId", t.getStId());
				m.put("typeId", t.getTypeId());
				m.put("dictCode", dictCode);
				m.put("name", t.getName());
				m.put("value", t.getValue());
				if (!resultList.contains(m)) {
					resultList.add(m);
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 人工归类，获取当前归属的大类
	 * @param dictCode
	 * @return
	 */
	private String getByMap(String dictCode) {
		Map<String, Object> m = new HashMap<>();
		// 活教材
		m.put("1", "1"); // 章节阅读(每个章节知识点)，
		m.put("2", "1"); // 查看视频，加
		m.put("3", "1"); // 查看音频，加
		// 考勤
		m.put("4", "2"); // 正常签到一次，加
		m.put("5", "2"); // 迟到一次，扣
		m.put("6", "2"); // 旷课一次，扣
		m.put("7", "2"); // 请假一次，扣
		// 活动
		m.put("8", "3"); // 投票问卷，加
		m.put("9", "3"); // 头脑风暴，加
		m.put("10", "3"); // 答疑讨论，加
		return m.get(dictCode).toString();
	}
	
	/**
	 * 保存
	 * @param jsonObject
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R saveSessting(JSONObject jsonObject, String loginUserId) {
		String ctId = jsonObject.getString("ctId");
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom == null) {
			return R.error("无效的课堂，数据获取未成功");
		}
		if (!loginUserId.equals(tevglTchClassroom.getCreateUserId())) {
			return R.error("非法操作");
		}
		JSONArray jsonArray = jsonObject.getJSONArray("list");
		if (jsonArray == null || jsonArray.size() == 0) {
			return R.ok();
		}
		BigDecimal val = new BigDecimal("0");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			String weight = object.getString("weight");
			val = val.add(new BigDecimal(weight));
		}
		// 小于
		if (val.compareTo(new BigDecimal("100")) != 0) {
			return R.error("权重必须满足100%");
		}
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			String typeId= object.getString("typeId");
			String weight = object.getString("weight");
			TevglEmpiricalType t = new TevglEmpiricalType();
			t.setTypeId(typeId);
			t.setWeight(new BigDecimal(weight));
			tevglEmpiricalTypeMapper.update(t);
			JSONArray children = object.getJSONArray("children");
			if (children == null || children.size() == 0) {
				continue;
			}
			for (int j = 0; j < children.size(); j++) {
				JSONObject obj = children.getJSONObject(j);
				String stId = obj.getString("stId");
				String value = obj.getString("value");
				TevglEmpiricalSetting s = new TevglEmpiricalSetting();
				s.setStId(stId);
				s.setValue(new BigDecimal(value));
				tevglEmpiricalSettingMapper.update(s);
			}
		}
		return R.ok("设置成功");
	}

	/**
	 * 统一格式处理文本
	 * @param classroomName
	 * @param activityName
	 * @param empiricalValue
	 * @return
	 */
	@Override
	public String handleMessage(String classroomName, String activityName, Integer empiricalValue) {
		if (StrUtils.isEmpty(classroomName) || StrUtils.isEmpty(activityName)) {
			return "";
		}
		empiricalValue = empiricalValue == null ? 0 : empiricalValue;
		return "在课堂 【 " + classroomName + " 】 的 [ " + activityName + " ] 活动中获得" + empiricalValue + "经验值";
	}

	/**
	 * 根据类型获取，课堂的各种经验值
	 * @param ctId
	 * @param dictCode 该值来源字典，亦可见EmpiricalValueEnum枚举类
	 * @return
	 */
	@Override
	public Integer getEmpiricalValueByMap(String ctId, String dictCode) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(dictCode)) {
			return 0;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("ctId", ctId);
		map.put("dictCode", dictCode);
		Integer value = tevglEmpiricalSettingMapper.getEmpiricalValueByMap(map);
		if (value == null || value == 0) {
			value = EmpiricalValueEnum.getValueByCode(dictCode).intValue();
		}
		return value;
	}
	
}
