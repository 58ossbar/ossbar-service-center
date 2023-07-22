package com.ossbar.modules.evgl.empirical.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ossbar.modules.evgl.book.persistence.TevglBookChapterMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookSubjectMapper;
import com.ossbar.modules.evgl.empirical.persistence.TevglEmpiricalSettingMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeEmpiricalValueLogMapper;
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
import com.ossbar.modules.common.GlobalEmpiricalValueGetType;
import com.ossbar.modules.common.annotation.NoRepeatSubmit;
import com.ossbar.modules.common.enums.EmpiricalValueEnum;
import com.ossbar.modules.evgl.book.domain.TevglBookChapter;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import com.ossbar.modules.evgl.empirical.api.TevglEmpiricalLogVideoService;
import com.ossbar.modules.evgl.empirical.domain.TevglEmpiricalLogVideo;
import com.ossbar.modules.evgl.empirical.domain.TevglEmpiricalSetting;
import com.ossbar.modules.evgl.empirical.persistence.TevglEmpiricalLogVideoMapper;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeEmpiricalValueLog;
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
@RequestMapping("/empirical/tevglempiricallogvideo")
public class TevglEmpiricalLogVideoServiceImpl implements TevglEmpiricalLogVideoService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglEmpiricalLogVideoServiceImpl.class);
	@Autowired
	private TevglEmpiricalLogVideoMapper tevglEmpiricalLogVideoMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private TevglPkgInfoMapper tevglPkgInfoMapper;
	@Autowired
	private TevglBookSubjectMapper tevglBookSubjectMapper;
	@Autowired
	private TevglBookChapterMapper tevglBookChapterMapper;
	@Autowired
	private TevglEmpiricalSettingMapper tevglEmpiricalSettingMapper;
	@Autowired
	private TevglTraineeEmpiricalValueLogMapper tevglTraineeEmpiricalValueLogMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/empirical/tevglempiricallogvideo/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglEmpiricalLogVideo> tevglEmpiricalLogVideoList = tevglEmpiricalLogVideoMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglEmpiricalLogVideoList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/empirical/tevglempiricallogvideo/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglEmpiricalLogVideoList = tevglEmpiricalLogVideoMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglEmpiricalLogVideoList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglEmpiricalLogVideo
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/empirical/tevglempiricallogvideo/save")
	public R save(@RequestBody(required = false) TevglEmpiricalLogVideo tevglEmpiricalLogVideo) throws OssbarException {
		tevglEmpiricalLogVideo.setCbId(Identities.uuid());
		ValidatorUtils.check(tevglEmpiricalLogVideo);
		tevglEmpiricalLogVideoMapper.insert(tevglEmpiricalLogVideo);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglEmpiricalLogVideo
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/empirical/tevglempiricallogvideo/update")
	public R update(@RequestBody(required = false) TevglEmpiricalLogVideo tevglEmpiricalLogVideo) throws OssbarException {
	    ValidatorUtils.check(tevglEmpiricalLogVideo);
		tevglEmpiricalLogVideoMapper.update(tevglEmpiricalLogVideo);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/empirical/tevglempiricallogvideo/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglEmpiricalLogVideoMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/empirical/tevglempiricallogvideo/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglEmpiricalLogVideoMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/empirical/tevglempiricallogvideo/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglEmpiricalLogVideoMapper.selectObjectById(id));
	}

	/**
	 * 查看视频时，记录并获得经验值
	 * @param ctId
	 * @param pkgId
	 * @param subjectId
	 * @param chapterId
	 * @param videoId
	 * @param loginUserId
	 * @return
	 */
	@Override
	@NoRepeatSubmit
	public R viewVideo(String ctId, String pkgId, String subjectId, String chapterId, String videoId,
			String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(pkgId) || StrUtils.isEmpty(subjectId)
				|| StrUtils.isEmpty(chapterId) || StrUtils.isEmpty(videoId) || StrUtils.isEmpty(loginUserId)) {
			return R.ok();
		}
		// 课堂创建者无需记录
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom != null && loginUserId.equals(tevglTchClassroom.getCreateUserId())) {
			return R.ok();
		}
		TevglPkgInfo tevglPkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
		TevglPkgInfo refPkgInfo = null;
		if (tevglPkgInfo != null) {
			refPkgInfo = tevglPkgInfoMapper.selectObjectById(tevglPkgInfo.getRefPkgId());
		}
		TevglBookSubject tevglBookSubject = tevglBookSubjectMapper.selectObjectById(subjectId);
		TevglBookChapter tevglBookChapter = tevglBookChapterMapper.selectObjectById(chapterId);
		// 是否已经记录过
		Map<String, Object> parmas = new HashMap<String, Object>();
		parmas.put("ctId", ctId);
		parmas.put("pkgId", pkgId);
		parmas.put("subjectId", subjectId);
		parmas.put("chapterId", chapterId);
		parmas.put("videoId", videoId);
		parmas.put("traineeId", loginUserId);
		List<TevglEmpiricalLogVideo> list = tevglEmpiricalLogVideoMapper.selectListByMap(parmas);
		// 已经得到过直接返回
		if (list != null && list.size() > 0) {
			return R.ok();
		}
		// 获取经验值规则
		parmas.clear();
		parmas.put("ctId", ctId);
		parmas.put("dictCode", "2");
		List<TevglEmpiricalSetting> tevglEmpiricalSettingList = tevglEmpiricalSettingMapper.selectListByMap(parmas);
		Integer empiricalValue = null;
		if (tevglEmpiricalSettingList != null && tevglEmpiricalSettingList.size() > 0) {
			BigDecimal value = tevglEmpiricalSettingList.get(0).getValue();
			if (value != null) {
				empiricalValue = value.intValue();
			}
		} else {
			empiricalValue = EmpiricalValueEnum.TYPE_2.getValue().intValue();
		}
		// 入库
		TevglEmpiricalLogVideo t = new TevglEmpiricalLogVideo();
		t.setCbId(Identities.uuid());
		t.setTraineeId(loginUserId);
		t.setCtId(ctId);
		t.setPkgId(pkgId);
		t.setSubjectId(subjectId);
		t.setChapterId(chapterId);
		t.setVideoId(videoId);
		t.setEmpiricalValue(empiricalValue);
		t.setCreateTime(DateUtils.getNowTimeStamp());
		tevglEmpiricalLogVideoMapper.insert(t);
		// 经验值记录表插入记录
		TevglTraineeEmpiricalValueLog log = new TevglTraineeEmpiricalValueLog();
		log.setEvId(Identities.uuid());
		log.setType(GlobalEmpiricalValueGetType.VIEW_VIDEO_10);
		log.setTraineeId(loginUserId);
		log.setCtId(ctId);
		log.setEmpiricalValue(empiricalValue);
		log.setState("Y");
		log.setCreateTime(DateUtils.getNowTimeStamp());
		String msg = "";
		String classroomName = tevglTchClassroom == null ? "" : "在课堂【" + tevglTchClassroom.getName() + "】的";
		String subjectName = tevglBookSubject == null ? "" : tevglBookSubject.getSubjectName();
		String version = refPkgInfo == null ? "" : refPkgInfo.getPkgVersion();
		if (StrUtils.isNotEmpty(version)) {
			subjectName = "教材【" + subjectName + "  " + version +"】中，";
		}
		String chapterName = tevglBookChapter == null ? "" : "查阅视频[" + tevglBookChapter.getChapterName() + "]，获得" + empiricalValue + "经验值";
		msg = classroomName + subjectName + chapterName;
		log.setMsg(msg);
		tevglTraineeEmpiricalValueLogMapper.insert(log);
		return R.ok();
	}
}
