package com.ossbar.modules.evgl.activity.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
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
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.CbRoomUtils;
import com.ossbar.modules.common.enums.BizCodeEnume;
import com.ossbar.modules.common.enums.CommonEnum;
import com.ossbar.modules.evgl.activity.api.TevglActivityTaskScoreService;
import com.ossbar.modules.evgl.activity.domain.TevglActivityTask;
import com.ossbar.modules.evgl.activity.domain.TevglActivityTaskScore;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityTaskGroupMemberMapper;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityTaskMapper;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityTaskRelevanceGroupMapper;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityTaskScoreMapper;
import com.ossbar.modules.evgl.activity.vo.ActivityTaskScoreVo;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
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
@RequestMapping("/activity/tevglactivitytaskscore")
public class TevglActivityTaskScoreServiceImpl implements TevglActivityTaskScoreService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglActivityTaskScoreServiceImpl.class);
	
	@Autowired
	private TevglActivityTaskScoreMapper tevglActivityTaskScoreMapper;
	@Autowired
	private TevglActivityTaskMapper tevglActivityTaskMapper;
	@Autowired
	private TevglActivityTaskRelevanceGroupMapper tevglActivityTaskRelevanceGroupMapper;
	@Autowired
	private TevglActivityTaskGroupMemberMapper tevglActivityTaskGroupMemberMapper;
	@Autowired
	private TevglPkgInfoMapper tevglPkgInfoMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;

	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private CbRoomUtils cbRoomUtils;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/activity/tevglactivitytaskscore/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglActivityTaskScore> tevglActivityTaskScoreList = tevglActivityTaskScoreMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglActivityTaskScoreList, "createUserId", "updateUserId");
		convertUtil.convertUserId2RealName(tevglActivityTaskScoreList, "createUserId", "updateUserId");
		PageUtils pageUtil = new PageUtils(tevglActivityTaskScoreList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/activity/tevglactivitytaskscore/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglActivityTaskScoreList = tevglActivityTaskScoreMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglActivityTaskScoreList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglActivityTaskScoreList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglActivityTaskScore
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/activity/tevglactivitytaskscore/save")
	public R save(@RequestBody(required = false) TevglActivityTaskScore tevglActivityTaskScore) throws OssbarException {
		tevglActivityTaskScore.setScoreId(Identities.uuid());
		tevglActivityTaskScore.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglActivityTaskScore.setCreateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglActivityTaskScore);
		tevglActivityTaskScoreMapper.insert(tevglActivityTaskScore);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglActivityTaskScore
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/activity/tevglactivitytaskscore/update")
	public R update(@RequestBody(required = false) TevglActivityTaskScore tevglActivityTaskScore) throws OssbarException {
	    tevglActivityTaskScore.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglActivityTaskScore.setUpdateTime(DateUtils.getNowTimeStamp());
	    ValidatorUtils.check(tevglActivityTaskScore);
		tevglActivityTaskScoreMapper.update(tevglActivityTaskScore);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/activity/tevglactivitytaskscore/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglActivityTaskScoreMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/activity/tevglactivitytaskscore/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglActivityTaskScoreMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/activity/tevglactivitytaskscore/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglActivityTaskScoreMapper.selectObjectById(id));
	}

	@Override
	@SysLog(value="老师评分")
	@GetMapping("/teachGiveScore")
	@SentinelResource("/activity/tevglactivitytaskscore/teachGiveScore")
	public R teachGiveScore(ActivityTaskScoreVo vo, String loginUserId) {
		if (StrUtils.isEmpty(vo.getPkgId()) || StrUtils.isEmpty(vo.getActivityId()) || StrUtils.isEmpty(loginUserId)) {
			return R.error(BizCodeEnume.PARAM_MISSING.getCode(), BizCodeEnume.PARAM_MISSING.getMsg());
		}
		if (vo.getScoreList() == null || vo.getScoreList().size() == 0) {
			return R.error("请选择要评分的学员");
		}
		// 如果课堂不存在
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(vo.getCtId());
		if (tevglTchClassroom == null) {
			return R.error(BizCodeEnume.INEFFECTIVE_CLASSROOM.getCode(), BizCodeEnume.INEFFECTIVE_CLASSROOM.getMsg());
		}
		if (!tevglTchClassroom.getPkgId().equals(vo.getPkgId())) {
			return R.error(BizCodeEnume.PARAM_INVALID.getCode(), BizCodeEnume.PARAM_INVALID.getMsg());
		}
		// 如果没有权限
		boolean hasOperatingAuthorization = cbRoomUtils.hasOperatingAuthorization(tevglTchClassroom, loginUserId);
		if (!hasOperatingAuthorization) {
			return R.error(BizCodeEnume.WITHOUT_PERMISSION.getCode(), BizCodeEnume.WITHOUT_PERMISSION.getMsg());
		}
		// 如果教学包已不存在
		TevglPkgInfo tevglPkgInfo = tevglPkgInfoMapper.selectObjectById(vo.getPkgId());
		if (tevglPkgInfo == null) {
			return R.error(BizCodeEnume.WITHOUT_PACKAGE.getCode(), BizCodeEnume.WITHOUT_PACKAGE.getMsg());
		}
		// 如果参数不合法
		List<String> groupIds = tevglActivityTaskRelevanceGroupMapper.findGroupIdBy(vo.getCtId(), vo.getActivityId());
		Map<String, Object> map = new HashMap<>();
		map.put("pkgId", vo.getPkgId());
		map.put("activityId", vo.getActivityId());
		TevglActivityTask tevglActivityTask = tevglActivityTaskMapper.selectObjectByIdAndPkgId(map);
		if (tevglActivityTask == null || groupIds == null || groupIds.size() == 0) {
			return R.error(BizCodeEnume.PARAM_INVALID.getCode(), BizCodeEnume.PARAM_INVALID.getMsg());
		}
		String groupId = groupIds.get(0);
		// 先查询已存在的评分记录
		Map<String, Object> params = new HashMap<>();
		params.put("ctId", vo.getCtId());
		params.put("activityId", vo.getActivityId());
		List<TevglActivityTaskScore> existedTevglActivityTaskScores = tevglActivityTaskScoreMapper.selectListByMap(params);
		// 先找到该小组的成员
		List<String> memberIds = tevglActivityTaskGroupMemberMapper.findMemberIdsByGroupId(groupId);
		// 校验数据
		checkDatas(vo, memberIds);
		// 等待新增的数据
		List<TevglActivityTaskScore> insertScoreList = new ArrayList<>();
		// 等待更新的数据
		List<TevglActivityTaskScore> updateScoreList = new ArrayList<>();
		// 处理数据
		String nowTimeStamp = DateUtils.getNowTimeStamp();
		vo.getScoreList().stream().forEach(item -> {
			TevglActivityTaskScore t = new TevglActivityTaskScore();
			if (StrUtils.isEmpty(item.getScoreId())) {
				t.setScoreId(Identities.uuid());
				t.setCtId(vo.getCtId());
				t.setType("2"); // 类型（1表示小组2表示人）
				t.setGroupId(groupId);
				t.setMemberId(item.getMemberId());
				t.setActivityId(vo.getActivityId());
				t.setScore(item.getScore());
				t.setUserType("2"); // 评分人类型1小组2人
				t.setCreateUserId(loginUserId);
				t.setCreateTime(nowTimeStamp);
				t.setState(CommonEnum.STATE_YES.getCode());
				// 控制不重复插入
				List<TevglActivityTaskScore> collect = existedTevglActivityTaskScores.stream().filter(a -> a.getMemberId().equals(item.getMemberId())
						&& a.getActivityId().equals(vo.getActivityId())
						&& a.getGroupId().equals(groupId)
				).collect(Collectors.toList());
				boolean match = (collect != null && collect.size() > 0) ? true : false;
				if (match) {
					t.setScoreId(collect.get(0).getScoreId());
					t.setScore(item.getScore());
					t.setUpdateTime(nowTimeStamp);
					t.setUpdateUserId(loginUserId);
					updateScoreList.add(t);
				} else {
					insertScoreList.add(t);
				}
			} else {
				t.setScoreId(item.getScoreId());
				t.setScore(item.getScore());
				t.setUpdateTime(nowTimeStamp);
				t.setUpdateUserId(loginUserId);
				updateScoreList.add(t);
			}
		});
		if (insertScoreList.size() > 0) {
			tevglActivityTaskScoreMapper.insertBatch(insertScoreList);
		}
		if (updateScoreList.size() > 0) {
			tevglActivityTaskScoreMapper.updateBatchByDuplicateKey(updateScoreList);
		}
		return R.ok("评分成功");
	}
	
	/**
	 * 校验数据
	 * @param vo
	 */
	private void checkDatas(ActivityTaskScoreVo vo, List<String> memberIds) {
		for (int i = 0; i < vo.getScoreList().size(); i++) {
			TevglActivityTaskScore tevglActivityTaskScore = vo.getScoreList().get(i);
			if (tevglActivityTaskScore.getScore() == null) {
				throw new OssbarException("请输入分值");
			}
			if (StrUtils.isEmpty(tevglActivityTaskScore.getMemberId())) {
				throw new OssbarException("参数 memberId 为空");
			}
			if (!memberIds.contains(tevglActivityTaskScore.getMemberId())) {
				throw new OssbarException("参数 memberId 不合法");
			}
		}
	}
}
