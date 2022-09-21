package com.ossbar.modules.evgl.site.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.site.api.TevglSiteCheckinfoService;
import com.ossbar.modules.evgl.site.domain.TevglSiteCheckinfo;
import com.ossbar.modules.evgl.site.persistence.TevglSiteCheckinfoMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/site/tevglsitecheckinfo")
public class TevglSiteCheckinfoServiceImpl implements TevglSiteCheckinfoService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglSiteCheckinfoServiceImpl.class);
	@Autowired
	private TevglSiteCheckinfoMapper tevglSiteCheckinfoMapper;
	@Autowired
	private ConvertUtil convertUtil;
	@Autowired
	private ServiceLoginUtil serviceLoginUtil;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/site/tevglsitecheckinfo/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglSiteCheckinfo> tevglSiteCheckinfoList = tevglSiteCheckinfoMapper.selectListByMap(query);
		convertUtil.convertUserId2RealName(tevglSiteCheckinfoList, "createUserId", "updateUserId");
		convertUtil.convertDict(tevglSiteCheckinfoList, "state", "state4"); // 状态(Y有效N无效)
		convertUtil.convertDict(tevglSiteCheckinfoList, "pass", "isPass");// 是否通过(Y通过N未通过)
		PageUtils pageUtil = new PageUtils(tevglSiteCheckinfoList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/site/tevglsitecheckinfo/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglSiteCheckinfoList = tevglSiteCheckinfoMapper.selectListMapByMap(query);
		convertUtil.convertUserId2RealName(tevglSiteCheckinfoList, "create_user_id", "update_user_id");
		PageUtils pageUtil = new PageUtils(tevglSiteCheckinfoList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglSiteCheckinfo
	 * @throws CreatorblueException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/site/tevglsitecheckinfo/save")
	public R save(@RequestBody(required = false) TevglSiteCheckinfo tevglSiteCheckinfo) throws CreatorblueException {
		tevglSiteCheckinfo.setId(Identities.uuid());
		tevglSiteCheckinfo.setCreateUserId(serviceLoginUtil.getLoginUserId());
		tevglSiteCheckinfo.setCreateTime(DateUtils.getNowTimeStamp());
		//ValidatorUtils.check(tevglSiteCheckinfo);
		tevglSiteCheckinfoMapper.insert(tevglSiteCheckinfo);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglSiteCheckinfo
	 * @throws CreatorblueException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/site/tevglsitecheckinfo/update")
	public R update(@RequestBody(required = false) TevglSiteCheckinfo tevglSiteCheckinfo) throws CreatorblueException {
	    //ValidatorUtils.check(tevglSiteCheckinfo);
		tevglSiteCheckinfoMapper.update(tevglSiteCheckinfo);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/site/tevglsitecheckinfo/delete")
	public R delete(@PathVariable("id") String id) throws CreatorblueException {
		tevglSiteCheckinfoMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws CreatorblueException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/site/tevglsitecheckinfo/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws CreatorblueException {
		tevglSiteCheckinfoMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/site/tevglsitecheckinfo/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglSiteCheckinfoMapper.selectObjectById(id));
	}
}
