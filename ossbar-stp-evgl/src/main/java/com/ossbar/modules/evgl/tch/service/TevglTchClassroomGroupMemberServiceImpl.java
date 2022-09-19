package com.ossbar.modules.evgl.tch.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.CbRoomUtils;
import com.ossbar.modules.common.GlobalRoomPermission;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomGroupMemberService;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomGroup;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomGroupMember;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomGroupMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomGroupMemberMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> Title: 课堂小组成员</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/tch/tevgltchclassroomgroupmember")
public class TevglTchClassroomGroupMemberServiceImpl implements TevglTchClassroomGroupMemberService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglTchClassroomGroupMemberServiceImpl.class);
	@Autowired
	private TevglTchClassroomGroupMemberMapper tevglTchClassroomGroupMemberMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private UploadFileUtils uploadPathUtils;
	@Autowired
	private TevglTchClassroomGroupMapper tevglTchClassroomGroupMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private CbRoomUtils cbRoomUtils;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/tch/tevgltchclassroomgroupmember/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglTchClassroomGroupMember> tevglTchClassroomGroupMemberList = tevglTchClassroomGroupMemberMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglTchClassroomGroupMemberList, "createUserId", "updateUserId");
		PageUtils pageUtil = new PageUtils(tevglTchClassroomGroupMemberList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/tch/tevgltchclassroomgroupmember/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglTchClassroomGroupMemberList = tevglTchClassroomGroupMemberMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglTchClassroomGroupMemberList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglTchClassroomGroupMemberList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglTchClassroomGroupMember
	 * @throws CreatorblueException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/tch/tevgltchclassroomgroupmember/save")
	public R save(@RequestBody(required = false) TevglTchClassroomGroupMember tevglTchClassroomGroupMember) throws CreatorblueException {
		tevglTchClassroomGroupMember.setGmId(Identities.uuid());
		tevglTchClassroomGroupMember.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglTchClassroomGroupMember.setCreateTime(DateUtils.getNowTimeStamp());
		//ValidatorUtils.check(tevglTchClassroomGroupMember);
		tevglTchClassroomGroupMemberMapper.insert(tevglTchClassroomGroupMember);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglTchClassroomGroupMember
	 * @throws CreatorblueException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/tch/tevgltchclassroomgroupmember/update")
	public R update(@RequestBody(required = false) TevglTchClassroomGroupMember tevglTchClassroomGroupMember) throws CreatorblueException {
	    //ValidatorUtils.check(tevglTchClassroomGroupMember);
		tevglTchClassroomGroupMemberMapper.update(tevglTchClassroomGroupMember);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/tch/tevgltchclassroomgroupmember/delete")
	public R delete(@PathVariable("id") String id) throws CreatorblueException {
		tevglTchClassroomGroupMemberMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws CreatorblueException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/tch/tevgltchclassroomgroupmember/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws CreatorblueException {
		tevglTchClassroomGroupMemberMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/tch/tevgltchclassroomgroupmember/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglTchClassroomGroupMemberMapper.selectObjectById(id));
	}

	/**
	 * 根据课堂小组id查看课堂小组成员
	 * @param gpId
	 * @return
	 */
	@Override
	@GetMapping("/listClassroomGroupMemberById")
	public R listClassroomGroupMemberById(String gpId) {
		if (StrUtils.isEmpty(gpId)) {
			return R.error("参数gpId为空");
		}
		TevglTchClassroomGroup classroomGroupInfo = tevglTchClassroomGroupMapper.selectObjectById(gpId);
		if (classroomGroupInfo == null) {
			return R.error("无效的记录");
		}
		if (!"Y".equals(classroomGroupInfo.getState())) {
			return R.error("无效的小组，已无法查看");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gpId", gpId);
		map.put("sidx", "t1.sort_num");
		map.put("order", "desc");
		List<Map<String,Object>> list = tevglTchClassroomGroupMemberMapper.selectListMapForCommon(map);
		list.stream().forEach(a -> {
			String traineePic = (String) a.get("traineePic");
			String res = uploadPathUtils.stitchingPath(traineePic, "16");
			a.put("traineePic", res);
			a.put("groupName", classroomGroupInfo.getGroupName());
			a.put("ctId", classroomGroupInfo.getCtId());
		});
		return R.ok().put(Constant.R_DATA, list);
	}

	/**
	 * 删除小组成员
	 * @param gmId
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R deleteClassroomGroupMember(String gmId, String loginUserId) throws CreatorblueException {
		if (StrUtils.isEmpty(gmId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		TevglTchClassroomGroupMember tevglTchClassroomGroupMember = tevglTchClassroomGroupMemberMapper.selectObjectById(gmId);
		if (tevglTchClassroomGroupMember == null) {
			return R.error("该小组成员已被删除");
		}
		TevglTchClassroomGroup tevglTchClassroomGroup = tevglTchClassroomGroupMapper.selectObjectById(tevglTchClassroomGroupMember.getGpId());
		if (tevglTchClassroomGroup == null) {
			return R.error("无效的小组");
		}
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(tevglTchClassroomGroup.getCtId());
		if (tevglTchClassroom == null) {
			return R.error("无效的课堂");
		}
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(tevglTchClassroom, loginUserId, GlobalRoomPermission.DELETE_ROOM_GROUP_TRAINEE);
		if (!hasOperationBtnPermission) {
			return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
		}
		tevglTchClassroomGroupMemberMapper.delete(gmId);
		// 更新小组人数
		TevglTchClassroomGroup t = new TevglTchClassroomGroup();
		t.setGpId(tevglTchClassroomGroupMember.getGpId());
		t.setNumber(-1);
		tevglTchClassroomGroupMapper.plusNum(t);
		return R.ok("删除成功");
	}

	/**
	 * 批量将成员移出小组
	 * @param jsonObject
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R removeGroupMembers(JSONObject jsonObject, String loginUserId) {
		String gpId = jsonObject.getString("gpId"); // 小组id
		TevglTchClassroomGroup tevglTchClassroomGroup = tevglTchClassroomGroupMapper.selectObjectById(gpId);
		if (tevglTchClassroomGroup == null) {
			return R.error("无效的小组");
		}
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(tevglTchClassroomGroup.getCtId());
		if (tevglTchClassroom == null) {
			return R.error("无效的课堂");
		}
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(tevglTchClassroom, loginUserId, GlobalRoomPermission.DELETE_ROOM_GROUP_TRAINEE);
		if (!hasOperationBtnPermission) {
			return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
		}
		// 表t_evgl_tch_classroom_group_member主键
		JSONArray gmIds = jsonObject.getJSONArray("gmIds");
		if (gmIds != null && gmIds.size() > 0) {
			List<Object> idList = (List<Object>) gmIds.stream().collect(Collectors.toList());
			tevglTchClassroomGroupMemberMapper.deleteBatch(idList.stream().toArray(String[]::new));
			// 更新小组人数
			TevglTchClassroomGroup t = new TevglTchClassroomGroup();
			t.setGpId(gpId);
			t.setNumber(-idList.size());
			tevglTchClassroomGroupMapper.plusNum(t);
		}
		// 小程序传的是
		JSONArray traineeIds = jsonObject.getJSONArray("traineeIds");
		if (traineeIds != null && traineeIds.size() > 0) {
			Map<String, Object> map = new HashMap<>();
			map.put("traineeIds", traineeIds);
			map.put("gpId", gpId);
			List<TevglTchClassroomGroupMember> memberList = tevglTchClassroomGroupMemberMapper.selectListByMap(map);
			if (memberList != null && memberList.size() > 0) {
				List<String> idList = memberList.stream().map(a -> a.getGmId()).collect(Collectors.toList());
				tevglTchClassroomGroupMemberMapper.deleteBatch(idList.stream().toArray(String[]::new));
				// 更新小组人数
				TevglTchClassroomGroup t = new TevglTchClassroomGroup();
				t.setGpId(gpId);
				t.setNumber(-idList.size());
				tevglTchClassroomGroupMapper.plusNum(t);
			}
		}
		return R.ok();
	}

	/**
	 * 设置小组成员身份
	 * @param ctId
	 * @param gmId
	 * @param dictCode
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R setIdentity(String ctId, String gmId, String dictCode, String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(gmId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		if (StrUtils.isEmpty(dictCode)) {
			return R.error("请选择身份");
		}
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom == null) {
			return R.error("无法设置");
		}
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(tevglTchClassroom, loginUserId, GlobalRoomPermission.SET_ROOM_GROUP_LEADER);
		if (!hasOperationBtnPermission) {
			return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
		}
		TevglTchClassroomGroupMember t = new TevglTchClassroomGroupMember();
		t.setGmId(gmId);
		t.setDictCode("0".equals(dictCode) ? "" : dictCode);
		tevglTchClassroomGroupMemberMapper.update(t);
		return R.ok("设置成功");
	}

	/**
	 * 批量设置小组成员身份
	 * @param jsonObject
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R setIdentityBatch(JSONObject jsonObject, String loginUserId) {
		String ctId = jsonObject.getString("ctId");
		String dictCode = jsonObject.getString("dictCode");
		JSONArray jsonArray = jsonObject.getJSONArray("gmIds");
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		if (StrUtils.isEmpty(dictCode)) {
			return R.error("请选择身份");
		}
		if (jsonArray == null || jsonArray.size() == 0) {
			return R.error("请选择成员");
		}
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom == null) {
			return R.error("无法设置");
		}
		boolean hasOperationBtnPermission = cbRoomUtils.hasOperationBtnPermission(tevglTchClassroom, loginUserId, GlobalRoomPermission.SET_ROOM_GROUP_LEADER);
		if (!hasOperationBtnPermission) {
			return R.error(GlobalRoomPermission.NO_AUTH_SHOW_TEXT);
		}
		dictCode = "0".equals(dictCode) ? "" : dictCode;
		List<String> gmIdList = new ArrayList<>();
		for (int i = 0; i < jsonArray.size(); i++) {
			gmIdList.add(jsonArray.getString(i));
		}
		tevglTchClassroomGroupMemberMapper.updateBatchDictCode(dictCode, gmIdList);
		return R.ok("设置成功");
	}
}
