package com.ossbar.modules.evgl.tch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.tch.api.TevglTchClassroomTopService;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomTop;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomTopMapper;

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
@RequestMapping("/tch/tevgltchclassroomtop")
public class TevglTchClassroomTopServiceImpl implements TevglTchClassroomTopService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglTchClassroomTopServiceImpl.class);
	@Autowired
	private TevglTchClassroomTopMapper tevglTchClassroomTopMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/tch/tevgltchclassroomtop/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglTchClassroomTop> tevglTchClassroomTopList = tevglTchClassroomTopMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglTchClassroomTopList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/tch/tevgltchclassroomtop/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglTchClassroomTopList = tevglTchClassroomTopMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglTchClassroomTopList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglTchClassroomTop
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/tch/tevgltchclassroomtop/save")
	public R save(@RequestBody(required = false) TevglTchClassroomTop tevglTchClassroomTop) throws OssbarException {
		tevglTchClassroomTop.setTopId(Identities.uuid());
		ValidatorUtils.check(tevglTchClassroomTop);
		tevglTchClassroomTopMapper.insert(tevglTchClassroomTop);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglTchClassroomTop
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/tch/tevgltchclassroomtop/update")
	public R update(@RequestBody(required = false) TevglTchClassroomTop tevglTchClassroomTop) throws OssbarException {
	    ValidatorUtils.check(tevglTchClassroomTop);
		tevglTchClassroomTopMapper.update(tevglTchClassroomTop);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/tch/tevgltchclassroomtop/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglTchClassroomTopMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/tch/tevgltchclassroomtop/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglTchClassroomTopMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/tch/tevgltchclassroomtop/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglTchClassroomTopMapper.selectObjectById(id));
	}

	@Override
	public R setTop(String ctId, String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		Map<String, Object> ps = new HashMap<String, Object>();
		ps.put("ctId", ctId);
		ps.put("traineeId", loginUserId);
		List<TevglTchClassroomTop> list = tevglTchClassroomTopMapper.selectListByMap(ps);
		if (list == null || list.size() == 0) {
			TevglTchClassroomTop top = new TevglTchClassroomTop();
			top.setTopId(Identities.uuid());
			top.setTraineeId(loginUserId);
			top.setCtId(ctId);
			top.setCreateTime(DateUtils.getNowTimeStamp());
			top.setUpdateTime(DateUtils.getNowTimeStamp());
			tevglTchClassroomTopMapper.insert(top);
		} else {
			TevglTchClassroomTop tevglTchClassroomTop = list.get(0);
			TevglTchClassroomTop top = new TevglTchClassroomTop();
			top.setTopId(tevglTchClassroomTop.getTopId());
			top.setUpdateTime(DateUtils.getNowTimeStamp());
			tevglTchClassroomTopMapper.update(top);
		}
		return R.ok("置顶成功");
	}

	/**
	 * 取消置顶
	 * @param ctId
	 * @param loginUserId
	 * @return
	 */
	@Override
	public R cancelTop(String ctId, String loginUserId) {
		if (StrUtils.isEmpty(ctId) || StrUtils.isEmpty(loginUserId)) {
			return R.error("必传参数为空");
		}
		Map<String, Object> ps = new HashMap<String, Object>();
		ps.put("ctId", ctId);
		ps.put("traineeId", loginUserId);
		List<TevglTchClassroomTop> list = tevglTchClassroomTopMapper.selectListByMap(ps);
		if (list != null && list.size() > 0) {
			for (TevglTchClassroomTop tevglTchClassroomTop : list) {
				tevglTchClassroomTopMapper.delete(tevglTchClassroomTop.getTopId());
			}
		}
		return R.ok("取消置顶");
	}
}
