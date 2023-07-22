package com.ossbar.modules.evgl.eao.service;

import java.util.List;
import java.util.Map;

import com.ossbar.modules.evgl.eao.persistence.TeaoTraineeExammemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.utils.tool.Identities;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.eao.api.TeaoTraineeExammemberService;
import com.ossbar.modules.evgl.eao.domain.TeaoTraineeExammember;

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
@RequestMapping("/eao/teaotraineeexammember")
public class TeaoTraineeExammemberServiceImpl implements TeaoTraineeExammemberService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TeaoTraineeExammemberServiceImpl.class);
	@Autowired
	private TeaoTraineeExammemberMapper teaoTraineeExammemberMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/eao/teaotraineeexammember/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TeaoTraineeExammember> teaoTraineeExammemberList = teaoTraineeExammemberMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(teaoTraineeExammemberList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/eao/teaotraineeexammember/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> teaoTraineeExammemberList = teaoTraineeExammemberMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(teaoTraineeExammemberList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param teaoTraineeExammember
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/eao/teaotraineeexammember/save")
	public R save(@RequestBody(required = false) TeaoTraineeExammember teaoTraineeExammember) throws OssbarException {
		teaoTraineeExammember.setEtId(Identities.uuid());
		ValidatorUtils.check(teaoTraineeExammember);
		teaoTraineeExammemberMapper.insert(teaoTraineeExammember);
		return R.ok();
	}
	/**
	 * 修改
	 * @param teaoTraineeExammember
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/eao/teaotraineeexammember/update")
	public R update(@RequestBody(required = false) TeaoTraineeExammember teaoTraineeExammember) throws OssbarException {
	    ValidatorUtils.check(teaoTraineeExammember);
		teaoTraineeExammemberMapper.update(teaoTraineeExammember);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/eao/teaotraineeexammember/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		teaoTraineeExammemberMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/eao/teaotraineeexammember/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		teaoTraineeExammemberMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/eao/teaotraineeexammember/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, teaoTraineeExammemberMapper.selectObjectById(id));
	}
}
