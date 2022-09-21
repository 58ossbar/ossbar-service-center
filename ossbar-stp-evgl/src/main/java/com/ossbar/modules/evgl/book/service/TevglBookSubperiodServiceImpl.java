package com.ossbar.modules.evgl.book.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.cbsecurity.logs.annotation.SysLog;
import com.ossbar.common.exception.CreatorblueException;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.book.api.TevglBookSubperiodService;
import com.ossbar.modules.evgl.book.domain.TevglBookSubject;
import com.ossbar.modules.evgl.book.domain.TevglBookSubperiod;
import com.ossbar.modules.evgl.book.persistence.TevglBookSubjectMapper;
import com.ossbar.modules.evgl.book.persistence.TevglBookSubperiodMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.Identities;
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
@RequestMapping("/book/tevglbooksubperiod")
public class TevglBookSubperiodServiceImpl implements TevglBookSubperiodService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglBookSubperiodServiceImpl.class);
	@Autowired
	private TevglBookSubperiodMapper tevglBookSubperiodMapper;
	@Autowired
	private TevglBookSubjectMapper tevglBookSubjectMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/book/tevglbooksubperiod/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglBookSubperiod> tevglBookSubperiodList = tevglBookSubperiodMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglBookSubperiodList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param params
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/book/tevglbooksubperiod/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglBookSubperiodList = tevglBookSubperiodMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglBookSubperiodList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglBookSubperiod
	 * @throws CreatorblueException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/book/tevglbooksubperiod/save")
	public R save(@RequestBody(required = false) TevglBookSubperiod tevglBookSubperiod) throws CreatorblueException {
		tevglBookSubperiod.setSubperiodId(Identities.uuid());
		//ValidatorUtils.check(tevglBookSubperiod);
		tevglBookSubperiodMapper.insert(tevglBookSubperiod);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglBookSubperiod
	 * @throws CreatorblueException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/book/tevglbooksubperiod/update")
	public R update(@RequestBody(required = false) TevglBookSubperiod tevglBookSubperiod) throws CreatorblueException {
	    //ValidatorUtils.check(tevglBookSubperiod);
		tevglBookSubperiodMapper.update(tevglBookSubperiod);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/book/tevglbooksubperiod/delete")
	public R delete(@PathVariable("id") String id) throws CreatorblueException {
		tevglBookSubperiodMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws CreatorblueException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/book/tevglbooksubperiod/deleteBatch")
	public R deleteBatch(@RequestBody(required = true) String[] ids) throws CreatorblueException {
		tevglBookSubperiodMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws CreatorblueException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/book/tevglbooksubperiod/view")
	public R view(@PathVariable("id") String id) {
		TevglBookSubperiod subperiod = tevglBookSubperiodMapper.selectObjectById(id);
		TevglBookSubject subject = tevglBookSubjectMapper.selectObjectById(subperiod.getSubjectId());
		subject.setSubjectProperty(subperiod.getSubjectProperty());
		subject.setTerm(subperiod.getTerm());
		// 查询所在的职业路径，可能会有多个
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectId", subject.getSubjectId());
		List<TevglBookSubperiod> list = tevglBookSubperiodMapper.selectListByMap(map);
		if (list != null && list.size() > 0) {
			String majorIds = list.stream().map(a -> a.getMajorId())
			.distinct()
			.collect(Collectors.joining(","));
			subject.setMajorId(majorIds);
		}
		return R.ok().put(Constant.R_DATA, subject);
	}
	
	/**
	 * <p>获取技术</p>
	 * @author huj
	 * @data 2019年7月16日
	 * @param map
	 * @return
	 */
	@Override
	@SysLog(value="查询技术")
	@GetMapping("/selectListForEvglWeb")
	public List<Map<String, Object>> selectListForEvglWeb(Map<String, Object> map) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list = tevglBookSubperiodMapper.selectListForEvglWeb(map);
		if (list.size() > 0) {
			list.forEach(a -> {
				if (a != null && !"null".equals(a)) {
					result.add(a);
				}
			});
		}
		return result;
	}
}
