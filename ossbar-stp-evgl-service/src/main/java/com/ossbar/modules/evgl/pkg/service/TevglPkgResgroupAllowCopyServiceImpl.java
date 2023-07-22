package com.ossbar.modules.evgl.pkg.service;

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
import com.ossbar.modules.evgl.pkg.api.TevglPkgResgroupAllowCopyService;
import com.ossbar.modules.evgl.pkg.domain.TevglPkgResgroupAllowCopy;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgResgroupAllowCopyMapper;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroom;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.utils.constants.Constant;
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
@RequestMapping("/pkg/tevglpkgresgroupallowcopy")
public class TevglPkgResgroupAllowCopyServiceImpl implements TevglPkgResgroupAllowCopyService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglPkgResgroupAllowCopyServiceImpl.class);
	@Autowired
	private TevglPkgResgroupAllowCopyMapper tevglPkgResgroupAllowCopyMapper;
	@Autowired
	private TevglTchClassroomMapper tevglTchClassroomMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/pkg/tevglpkgresgroupallowcopy/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglPkgResgroupAllowCopy> tevglPkgResgroupAllowCopyList = tevglPkgResgroupAllowCopyMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglPkgResgroupAllowCopyList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/pkg/tevglpkgresgroupallowcopy/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglPkgResgroupAllowCopyList = tevglPkgResgroupAllowCopyMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglPkgResgroupAllowCopyList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglPkgResgroupAllowCopy
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/pkg/tevglpkgresgroupallowcopy/save")
	public R save(@RequestBody(required = false) TevglPkgResgroupAllowCopy tevglPkgResgroupAllowCopy) throws OssbarException {
		tevglPkgResgroupAllowCopy.setCpId(Identities.uuid());
		ValidatorUtils.check(tevglPkgResgroupAllowCopy);
		tevglPkgResgroupAllowCopyMapper.insert(tevglPkgResgroupAllowCopy);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglPkgResgroupAllowCopy
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/pkg/tevglpkgresgroupallowcopy/update")
	public R update(@RequestBody(required = false) TevglPkgResgroupAllowCopy tevglPkgResgroupAllowCopy) throws OssbarException {
	    ValidatorUtils.check(tevglPkgResgroupAllowCopy);
		tevglPkgResgroupAllowCopyMapper.update(tevglPkgResgroupAllowCopy);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/pkg/tevglpkgresgroupallowcopy/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglPkgResgroupAllowCopyMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/pkg/tevglpkgresgroupallowcopy/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglPkgResgroupAllowCopyMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/pkg/tevglpkgresgroupallowcopy/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglPkgResgroupAllowCopyMapper.selectObjectById(id));
	}

	/**
	 * 更新是否可复制资源内容（Y可复制N不可复制）
	 * @author zhouyunlong加
	 * @data 2020年12月9日
	 * @param ctId
	 * @param pkgId
	 * @param cpId
	 * @param value
	 * @param loginUserId
	 * @return
	 */
	@Override
	@PostMapping("updateValue")
	public R updateValue(String ctId, String pkgId, String cpId, String value, String loginUserId) {
		if(StrUtils.isEmpty(pkgId) || StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		//只有创建课堂的创建人才有复制功能
		TevglTchClassroom classroom = tevglTchClassroomMapper.selectObjectById(ctId);
		if (classroom == null) {
			return R.error("无效的记录，请刷新后重试");
		}
		if (!loginUserId.equals(classroom.getCreateUserId())) {
			return R.error("只有课堂创建者才有复制功能");
		}
		//判断课堂是否开启或结束
		if ("1".equals(classroom.getClassroomState())) {
			return R.error("课堂尚未开始，不允许复制/禁止复制");
		}
		if ("3".equals(classroom.getClassroomState())) {
			return R.error("课堂已经结束，不允许复制/禁止复制");
		}
		if (!pkgId.equals(classroom.getPkgId())) {
			return R.error("教学包不存在");
		}
		String msg = null;
		//填充内容
		TevglPkgResgroupAllowCopy resgroupAllowCopy = new TevglPkgResgroupAllowCopy();
		resgroupAllowCopy.setCpId(cpId);
		if ("Y".equals(value)) {
			msg = "允许复制"; 
			resgroupAllowCopy.setIsCanCopy(value);
		}else if("N".equals(value)){
			msg = "禁止复制";
			resgroupAllowCopy.setIsCanCopy(value);
		}
		
		tevglPkgResgroupAllowCopyMapper.update(resgroupAllowCopy);
		return R.ok(msg);
	}
}
