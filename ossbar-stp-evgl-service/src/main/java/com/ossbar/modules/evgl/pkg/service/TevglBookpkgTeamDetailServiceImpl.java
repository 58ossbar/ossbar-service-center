package com.ossbar.modules.evgl.pkg.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ossbar.modules.evgl.pkg.persistence.TevglBookpkgTeamDetailMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglBookpkgTeamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.pkg.api.TevglBookpkgTeamDetailService;
import com.ossbar.modules.evgl.pkg.domain.TevglBookpkgTeam;
import com.ossbar.modules.evgl.pkg.domain.TevglBookpkgTeamDetail;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.tch.persistence.TevglTchTeacherMapper;

/**
 * <p> Title: 资源共建权限明细</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/pkg/tevglbookpkgteamdetail")
public class TevglBookpkgTeamDetailServiceImpl implements TevglBookpkgTeamDetailService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglBookpkgTeamDetailServiceImpl.class);
	@Autowired
	private TevglBookpkgTeamDetailMapper tevglBookpkgTeamDetailMapper;
	@Autowired
	private TevglPkgInfoMapper tevglPkgInfoMapper;
	@Autowired
	private TevglTchTeacherMapper tevglTchTeacherMapper;
	@Autowired
	private TevglBookpkgTeamMapper tevglBookpkgTeamMapper;
	 
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/pkg/tevglbookpkgteamdetail/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglBookpkgTeamDetail> tevglBookpkgTeamDetailList = tevglBookpkgTeamDetailMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglBookpkgTeamDetailList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/pkg/tevglbookpkgteamdetail/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglBookpkgTeamDetailList = tevglBookpkgTeamDetailMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglBookpkgTeamDetailList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglBookpkgTeamDetail
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/pkg/tevglbookpkgteamdetail/save")
	public R save(@RequestBody(required = false) TevglBookpkgTeamDetail tevglBookpkgTeamDetail) throws OssbarException {
		tevglBookpkgTeamDetail.setDetailId(Identities.uuid());
		ValidatorUtils.check(tevglBookpkgTeamDetail);
		tevglBookpkgTeamDetailMapper.insert(tevglBookpkgTeamDetail);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglBookpkgTeamDetail
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/pkg/tevglbookpkgteamdetail/update")
	public R update(@RequestBody(required = false) TevglBookpkgTeamDetail tevglBookpkgTeamDetail) throws OssbarException {
	    ValidatorUtils.check(tevglBookpkgTeamDetail);
		tevglBookpkgTeamDetailMapper.update(tevglBookpkgTeamDetail);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/pkg/tevglbookpkgteamdetail/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglBookpkgTeamDetailMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/pkg/tevglbookpkgteamdetail/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglBookpkgTeamDetailMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/pkg/tevglbookpkgteamdetail/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglBookpkgTeamDetailMapper.selectObjectById(id));
	}

	/**
	 * 单独对某个人授权
	 * @param jsonObject
	 * @param loginUserId
	 * @return
	 */
	@Override
	@Transactional
	@RequestMapping("/authorizationAlone")
	public R authorizationAlone(@RequestBody JSONObject jsonObject, String loginUserId) throws OssbarException {
		String pkgId = jsonObject.getString("pkgId");
		String teacherId = jsonObject.getString("teacherId");
		JSONArray jsonArray = jsonObject.getJSONArray("chapterIdList");
		// 合法性校验
		if (jsonArray == null || jsonArray.size() == 0) {
			return R.error("请选择授权的章节");
		}
		R r = checkIsPass(pkgId, teacherId, loginUserId);
		if ((Integer)r.get("code") != 0) {
			return r;
		}
		String subjectId = (String) r.get("subjectId");
		// 先判断主表是否有记录,若没有则生成
		String teamId = null;
		Map<String, Object> ps = new HashMap<>();
		ps.put("userId", teacherId);
		ps.put("pkgId", pkgId);
		List<TevglBookpkgTeam> list = tevglBookpkgTeamMapper.selectListByMap(ps);
		if (list == null || list.size() == 0) {
			TevglBookpkgTeam t = new TevglBookpkgTeam();
			t.setTeamId(Identities.uuid());
			t.setPgkId(pkgId);
			t.setSubjectId(subjectId);
			t.setUserId(teacherId);
			tevglBookpkgTeamMapper.insert(t);
			teamId = t.getTeamId();
		} else {
			teamId = list.get(0).getTeamId();
		}
		for (int i = 0; i < jsonArray.size(); i++) {
			String chapterId = jsonArray.getString(i);
			TevglBookpkgTeamDetail t = new TevglBookpkgTeamDetail();
			t.setDetailId(Identities.uuid());
			t.setTeamId(teamId);
			t.setChapterId(chapterId);
			t.setUserId(teacherId);
			tevglBookpkgTeamDetailMapper.insert(t);
		}
		return R.ok("授权成功");
	}
	
	/**
	 * 合法性校验
	 * @param pkgId 教学包主键
	 * @param teacherId 被授权的人
	 * @param loginUserId 当前登录用户
	 * @return
	 */
	private R checkIsPass(String pkgId, String teacherId, String loginUserId) {
		if (StrUtils.isEmpty(pkgId)) {
			return R.error("参数pkgId为空");
		}
		if (StrUtils.isEmpty(teacherId)) {
			return R.error("参数teacherId为空");
		}
		if (StrUtils.isEmpty(loginUserId)) {
			return R.error("参数loginUserId为空");
		}
		TevglPkgInfo pkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
		if (pkgInfo == null) {
			return R.error("无效的记录");
		}
		if (!loginUserId.equals(pkgInfo.getCreateUserId())) {
			return R.error("非法操作，无权限，操作失败");
		}
		if (!"Y".equals(pkgInfo.getState())) {
			return R.error("教学包已无效");
		}
		TevglTchTeacher tchTeacher = tevglTchTeacherMapper.selectObjectById(teacherId);
		if (tchTeacher == null) {
			return R.error("非法用户，授权失败");
		}
		return R.ok().put("subjectId", pkgInfo.getSubjectId());
	}

	@Override
	public List<TevglBookpkgTeamDetail> selectListByMap(Map<String, Object> params) {
		return tevglBookpkgTeamDetailMapper.selectListByMap(params);
	}
}
