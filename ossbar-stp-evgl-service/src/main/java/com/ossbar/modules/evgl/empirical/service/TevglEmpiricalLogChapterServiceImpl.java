package com.ossbar.modules.evgl.empirical.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ossbar.modules.evgl.book.persistence.TevglBookChapterMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookSubjectMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgInfoMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgResMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeEmpiricalValueLogMapper;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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
import com.alibaba.fastjson.JSONObject;
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
import com.ossbar.modules.evgl.empirical.api.TevglEmpiricalLogChapterService;
import com.ossbar.modules.evgl.empirical.domain.TevglEmpiricalLogChapter;
import com.ossbar.modules.evgl.empirical.domain.TevglEmpiricalSetting;
import com.ossbar.modules.evgl.empirical.persistence.TevglEmpiricalLogChapterMapper;
import com.ossbar.modules.evgl.empirical.persistence.TevglEmpiricalSettingMapper;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgInfo;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgRes;
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
@RequestMapping("/empirical/tevglempiricallogchapter")
public class TevglEmpiricalLogChapterServiceImpl implements TevglEmpiricalLogChapterService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglEmpiricalLogChapterServiceImpl.class);
	@Autowired
	private TevglEmpiricalLogChapterMapper tevglEmpiricalLogChapterMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private TevglPkgInfoMapper tevglPkgInfoMapper;
	@Autowired
	private TevglBookSubjectMapper tevglBookSubjectMapper;
	@Autowired
	private TevglPkgResMapper tevglPkgResMapper;
	@Autowired
	private TevglBookChapterMapper tevglBookChapterMapper;
	@Autowired
	private TevglEmpiricalSettingMapper tevglEmpiricalSettingMapper;
	@Autowired
	private TevglTraineeEmpiricalValueLogMapper tevglTraineeEmpiricalValueLogMapper;
	@Autowired
	private RedissonClient redisson;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/empirical/tevglempiricallogchapter/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglEmpiricalLogChapter> tevglEmpiricalLogChapterList = tevglEmpiricalLogChapterMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglEmpiricalLogChapterList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/empirical/tevglempiricallogchapter/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglEmpiricalLogChapterList = tevglEmpiricalLogChapterMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglEmpiricalLogChapterList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglEmpiricalLogChapter
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/empirical/tevglempiricallogchapter/save")
	public R save(@RequestBody(required = false) TevglEmpiricalLogChapter tevglEmpiricalLogChapter) throws OssbarException {
		tevglEmpiricalLogChapter.setCbId(Identities.uuid());
		ValidatorUtils.check(tevglEmpiricalLogChapter);
		tevglEmpiricalLogChapterMapper.insert(tevglEmpiricalLogChapter);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglEmpiricalLogChapter
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/empirical/tevglempiricallogchapter/update")
	public R update(@RequestBody(required = false) TevglEmpiricalLogChapter tevglEmpiricalLogChapter) throws OssbarException {
	    ValidatorUtils.check(tevglEmpiricalLogChapter);
		tevglEmpiricalLogChapterMapper.update(tevglEmpiricalLogChapter);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/empirical/tevglempiricallogchapter/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglEmpiricalLogChapterMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/empirical/tevglempiricallogchapter/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglEmpiricalLogChapterMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/empirical/tevglempiricallogchapter/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglEmpiricalLogChapterMapper.selectObjectById(id));
	}

	/**
	 * 查看章节时，记录并获得经验值
	 * @param ctId
	 * @param pkgId
	 * @param subjectId
	 * @param chapterId
	 * @param resId
	 * @param loginUserId
	 * @return
	 */
	@Override
	@NoRepeatSubmit
	@PostMapping("viewChapter")
	public R viewChapter(@RequestBody JSONObject jsonObject, String loginUserId) {
		String ctId = jsonObject.getString("ctId");
		String pkgId = jsonObject.getString("pkgId");
		String subjectId = jsonObject.getString("subjectId");
		String chapterId = jsonObject.getString("chapterId");
		String resId = jsonObject.getString("resId");
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(pkgId) || StrUtils.isEmpty(subjectId)
				|| StrUtils.isEmpty(chapterId) || StrUtils.isEmpty(resId) || StrUtils.isEmpty(loginUserId)) {
			log.debug("必传参数为空，直接返回");
			return R.ok();
		}
		// 课堂创建者无需记录
		TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (tevglTchClassroom != null && loginUserId.equals(tevglTchClassroom.getCreateUserId())) {
			log.debug("登录用户为课堂创建者，直接返回");
			return R.ok();
		}
		// 内容为空，不记录，没必要
		TevglPkgRes tevglPkgRes = tevglPkgResMapper.selectObjectById(resId);
		if (tevglPkgRes == null || StrUtils.isEmpty(tevglPkgRes.getResContent())) {
			log.debug("课程内容为空，直接返回");
			return R.ok();
		}
		// 是否已经记录过
		Map<String, Object> parmas = new HashMap<String, Object>();
		parmas.put("ctId", ctId);
		parmas.put("pkgId", pkgId);
		parmas.put("subjectId", subjectId);
		parmas.put("chapterId", chapterId);
		parmas.put("resId", resId);
		parmas.put("traineeId", loginUserId);
		List<TevglEmpiricalLogChapter> list = tevglEmpiricalLogChapterMapper.selectListByMap(parmas);
		// 已经得到过直接返回
		if (list != null && list.size() > 0) {
			log.debug("已获得过经验值，直接返回");
			return R.ok("厉害呀~早已获得过该经验值");
		}
		// 已登录用户id+所在课堂id+所查阅的章节为锁名
		RLock lock = redisson.getLock(loginUserId + "-" + tevglTchClassroom.getCtId() + "-" + resId);
		lock.lock();
		try {
			TevglPkgInfo tevglPkgInfo = tevglPkgInfoMapper.selectObjectById(pkgId);
			TevglPkgInfo refPkgInfo = null;
			if (tevglPkgInfo != null) {
				refPkgInfo = tevglPkgInfoMapper.selectObjectById(tevglPkgInfo.getRefPkgId());
			}
			TevglBookSubject tevglBookSubject = tevglBookSubjectMapper.selectObjectById(subjectId);
			TevglBookChapter tevglBookChapter = tevglBookChapterMapper.selectObjectById(chapterId);
			// 获取经验值规则
			parmas.clear();
			parmas.put("ctId", ctId);
			parmas.put("dictCode", "1");
			List<TevglEmpiricalSetting> tevglEmpiricalSettingList = tevglEmpiricalSettingMapper.selectListByMap(parmas);
			Integer empiricalValue = null;
			if (tevglEmpiricalSettingList != null && tevglEmpiricalSettingList.size() > 0) {
				BigDecimal value = tevglEmpiricalSettingList.get(0).getValue();
				if (value != null) {
					empiricalValue = value.intValue();
				}
			} else {
				empiricalValue = EmpiricalValueEnum.TYPE_1.getValue().intValue();
			}
			// 入库
			TevglEmpiricalLogChapter t = new TevglEmpiricalLogChapter();
			t.setCbId(Identities.uuid());
			t.setTraineeId(loginUserId);
			t.setCtId(ctId);
			t.setPkgId(pkgId);
			t.setSubjectId(subjectId);
			t.setChapterId(chapterId);
			t.setResId(resId);
			t.setEmpiricalValue(empiricalValue);
			t.setCreateTime(DateUtils.getNowTimeStamp());
			tevglEmpiricalLogChapterMapper.insert(t);
			// 经验值记录表插入记录
			TevglTraineeEmpiricalValueLog log = new TevglTraineeEmpiricalValueLog();
			log.setEvId(Identities.uuid());
			log.setType(GlobalEmpiricalValueGetType.VIEW_CHAPTER_9);
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
			String chapterName = tevglBookChapter == null ? "" : "查阅章节[" + tevglBookChapter.getChapterName() + "]，获得" + empiricalValue + "经验值";
			msg = classroomName + subjectName + chapterName;
			log.setMsg(msg);
			tevglTraineeEmpiricalValueLogMapper.insert(log);
		} finally {
			lock.unlock();
		}
		return R.ok("经验值获取成功");
	}
}
