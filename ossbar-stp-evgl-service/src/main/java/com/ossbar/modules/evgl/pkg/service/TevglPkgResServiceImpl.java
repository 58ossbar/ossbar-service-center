package com.ossbar.modules.evgl.pkg.service;

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
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.CbRoomUtils;
import com.ossbar.modules.common.PkgPermissionUtils;
import com.ossbar.modules.common.enums.BizCodeEnume;
import com.ossbar.modules.evgl.book.domain.TevglBookChapter;
import com.ossbar.modules.evgl.book.persistence.TevglBookChapterMapper;
import com.ossbar.modules.evgl.pkg.api.TevglPkgResService;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgRes;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgResgroup;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgResMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgResgroupMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import com.github.pagehelper.PageHelper;

/**
 * <p> Title: 教学包资源</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/pkg/tevglpkgres")
public class TevglPkgResServiceImpl implements TevglPkgResService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglPkgResServiceImpl.class);
	@Autowired
	private TevglPkgResMapper tevglPkgResMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	@Autowired
	private TevglPkgResgroupMapper tevglPkgResgroupMapper;
	@Autowired
	private TevglBookChapterMapper tevglBookChapterMapper;
	@Autowired
	private PkgPermissionUtils pkgPermissionUtils;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	@Autowired
	private CbRoomUtils cbRoomUtils;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/pkg/tevglpkgres/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglPkgRes> tevglPkgResList = tevglPkgResMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglPkgResList, "createUserId", "updateUserId");
		PageUtils pageUtil = new PageUtils(tevglPkgResList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/pkg/tevglpkgres/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglPkgResList = tevglPkgResMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglPkgResList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglPkgResList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglPkgRes
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/pkg/tevglpkgres/save")
	public R save(@RequestBody(required = false) TevglPkgRes tevglPkgRes) throws OssbarException {
		tevglPkgRes.setResId(Identities.uuid());
		tevglPkgRes.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglPkgRes.setCreateTime(DateUtils.getNowTimeStamp());
		ValidatorUtils.check(tevglPkgRes);
		tevglPkgResMapper.insert(tevglPkgRes);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglPkgRes
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/pkg/tevglpkgres/update")
	public R update(@RequestBody(required = false) TevglPkgRes tevglPkgRes) throws OssbarException {
	    tevglPkgRes.setUpdateUserId(serviceLoginUtil.getLoginUserId());
	    tevglPkgRes.setUpdateTime(DateUtils.getNowTimeStamp());
	    ValidatorUtils.check(tevglPkgRes);
		tevglPkgResMapper.update(tevglPkgRes);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/pkg/tevglpkgres/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglPkgResMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/pkg/tevglpkgres/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglPkgResMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/pkg/tevglpkgres/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglPkgResMapper.selectObjectById(id));
	}

	/**
	 * 新增资源---即将废弃
	 * @author huj
	 * @data 2019年8月13日	
	 * @param tevglPkgRes
	 * @return
	 */
	@Override
	@PostMapping("/saveResInfo")
	public R saveResInfo(@RequestBody TevglPkgRes tevglPkgRes, String loginUserId) throws OssbarException {
		if (StrUtils.isEmpty(loginUserId)) {
			return R.error("参数loginUserId为空");
		}
		if (StrUtils.isEmpty(tevglPkgRes.getResgroupId())) {
			return R.error("参数resgroupId为空");
		}
		if (StrUtils.isEmpty(tevglPkgRes.getResContent())) {
			return R.error("内容不能为空");
		}
		// 获取资源分组信息
		TevglPkgResgroup tevglPkgResgroup = tevglPkgResgroupMapper.selectObjectById(tevglPkgRes.getResgroupId());
		if (tevglPkgResgroup == null) {
			return R.error("保存失败，请刷新后重试");
		}
		// 权限校验
		R r = pkgPermissionUtils.hasPermissionChapterV2(tevglPkgResgroup.getPkgId(), loginUserId, tevglPkgResgroup.getChapterId());
		if (!r.get("code").equals(0)) {
			return r;
		}
		Map<String, Object> map = new HashMap<>();
		// 理解:按照原型的话，貌似一个分组下只有一个资源
		map.put("resgroupId", tevglPkgRes.getResgroupId());
		List<TevglPkgRes> list = tevglPkgResMapper.selectListByMap(map);
		String resId = "";
		if (list != null && list.size() > 0) {
			TevglPkgRes pkgRes = list.get(0);
			resId = pkgRes.getResId();
			TevglPkgRes t = new TevglPkgRes();
			t.setResId(resId);
			t.setResContent(tevglPkgRes.getResContent());
			t.setUpdateTime(DateUtils.getNowTimeStamp());
			t.setUpdateUserId(tevglPkgRes.getCreateUserId());
			tevglPkgResMapper.update(t);
		} else {
			// 填充信息
			tevglPkgRes.setResId(Identities.uuid());
			tevglPkgRes.setPkgId(tevglPkgResgroup.getPkgId()); // 所属教学包
			tevglPkgRes.setCreateTime(DateUtils.getNowTimeStamp()); // 创建时间
			tevglPkgRes.setState("Y");
			// 排序号处理
			map.clear();
			map.put("pkgId", tevglPkgRes.getPkgId());
			map.put("resgroupId", tevglPkgRes.getResgroupId());
			map.put("state", "Y");
			Integer sortNum = tevglPkgResMapper.getMaxSortNum(map);
			tevglPkgRes.setSortNum(sortNum);
			tevglPkgRes.setViewNum(0);
			// 保存并返回数据
			tevglPkgResMapper.insert(tevglPkgRes);
			resId = tevglPkgRes.getPkgId();
		}
		return R.ok().put(Constant.R_DATA, resId);
	}

	/**
	 * 修改资源
	 * @author huj
	 * @data 2019年8月13日	
	 * @param tevglPkgRes
	 * @return
	 */
	@Override
	@PostMapping("/editResInfo")
	public R editResInfo(@RequestBody TevglPkgRes tevglPkgRes, String loginUserId, String pkgId) throws OssbarException {
		if (StrUtils.isEmpty(loginUserId) || StrUtils.isEmpty(tevglPkgRes.getResId()) || StrUtils.isEmpty(pkgId)) {
			return R.error(BizCodeEnume.PARAM_MISSING.getCode(), BizCodeEnume.PARAM_MISSING.getMsg());
		}
		if (StrUtils.isNotEmpty(tevglPkgRes.getResContent())) {
			tevglPkgRes.setResContent(tevglPkgRes.getResContent().trim());	
		}
		if (StrUtils.isEmpty(tevglPkgRes.getResContent())) {
			return R.error("内容不能为空");
		}
		TevglPkgRes pkgRes = tevglPkgResMapper.selectObjectById(tevglPkgRes.getResId());
		if (pkgRes == null) {
			return R.error("无效的记录");
		}
		TevglPkgResgroup resgroup = tevglPkgResgroupMapper.selectObjectById(pkgRes.getResgroupId());
		if (resgroup == null) {
			return R.error("无效的记录");
		}
		// 权限校验
		R r = pkgPermissionUtils.hasPermissionChapterV2(pkgId, loginUserId, resgroup.getChapterId());
		if (!r.get("code").equals(0)) {
			return r;
		}
		// 已发布的版本中，不允许再次编辑 pkg_id值有问题？
		/*TevglPkgInfo tevglPkgInfo = tevglPkgInfoMapper.selectObjectById(resgroup.getPkgId());
		if (tevglPkgInfo == null || "Y".equals(tevglPkgInfo.getReleaseStatus())) {
			return R.error(BizCodeEnume.EDIT_CONTENT_IS_NOT_ALLOWED.getCode(), BizCodeEnume.EDIT_CONTENT_IS_NOT_ALLOWED.getMsg());
		}*/
		// 实例化对象并填充
		TevglPkgRes t = new TevglPkgRes();
		t.setResId(tevglPkgRes.getResId());
		t.setResContent(tevglPkgRes.getResContent());
		t.setUpdateTime(DateUtils.getNowTimeStamp());
		// 保存数据
		tevglPkgResMapper.update(t);
		// 特殊情况更新章节数据
		TevglPkgResgroup pkgResgroup = tevglPkgResgroupMapper.selectObjectById(pkgRes.getResgroupId());
		if (pkgResgroup != null) {
			// 当分组为[课程内容时],同步更新章节表的内容
			if ("1".equals(pkgResgroup.getDictCode())
					&& StrUtils.isNotEmpty(pkgResgroup.getChapterId())) {
				TevglBookChapter chapter = new TevglBookChapter();
				chapter.setChapterId(pkgResgroup.getChapterId());
				chapter.setChapterContent(tevglPkgRes.getResContent());
				tevglBookChapterMapper.update(chapter);
			}
		}
		// 即时通讯
		/*TevglTchClassroom tevglTchClassroom = tevglTchClassroomMapper.selectObjectByPkgId(resgroup.getPkgId());
		if (tevglTchClassroom != null) {
			// 重新刷新章节与资源
			cbRoomUtils.sendIm(tevglTchClassroom.getCtId(), "reloadrescontent", "other", pkgResgroup.getChapterId());
		}*/
		return R.ok("保存成功");
	}

	/**
	 * 查看资源
	 * @author huj
	 * @data 2019年8月13日	
	 * @param params {'resgroupId':''}
	 * @return
	 */
	@Override
	@GetMapping("/viewResInfo")
	public Map<String, Object> viewResInfo(@RequestParam Map<String, Object> params) {
		String resgroupId = (String) params.get("resgroupId");
		if (StrUtils.isEmpty(resgroupId)) {
			return R.error("参数resgroupId为空");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("resId", "");
		result.put("resgroupId", "");
		result.put("resContent", "");
		// 根据章节和分组获取资源
		List<TevglPkgRes> list = tevglPkgResMapper.selectListByMap(params);
		if (list != null && list.size() > 0) {
			TevglPkgRes tevglPkgRes = list.get(0);
			if (tevglPkgRes != null) {
				result.put("resId", tevglPkgRes.getResId());
				result.put("resgroupId", tevglPkgRes.getResgroupId());
				result.put("resContent", tevglPkgRes.getResContent());
			}
		}
		return R.ok().put(Constant.R_DATA, result);
	}

}
