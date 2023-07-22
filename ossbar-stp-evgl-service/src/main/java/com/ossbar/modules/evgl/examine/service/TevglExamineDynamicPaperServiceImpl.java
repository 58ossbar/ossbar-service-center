package com.ossbar.modules.evgl.examine.service;

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
import com.ossbar.utils.tool.Identities;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.validator.ValidatorUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.common.exception.OssbarException;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.examine.api.TevglExamineDynamicPaperService;
import com.ossbar.modules.evgl.examine.domain.TevglExamineDynamicPaper;
import com.ossbar.modules.evgl.examine.persistence.TevglExamineDynamicPaperMapper;

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
@RequestMapping("/examine/tevglexaminedynamicpaper")
public class TevglExamineDynamicPaperServiceImpl implements TevglExamineDynamicPaperService {
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TevglExamineDynamicPaperServiceImpl.class);
	@Autowired
	private TevglExamineDynamicPaperMapper tevglExamineDynamicPaperMapper;
	
	/**
	 * 查询列表(返回List<Bean>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Bean>)")
	@GetMapping("/query")
	@SentinelResource("/examine/tevglexaminedynamicpaper/query")
	public R query(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<TevglExamineDynamicPaper> tevglExamineDynamicPaperList = tevglExamineDynamicPaperMapper.selectListByMap(query);
		PageUtils pageUtil = new PageUtils(tevglExamineDynamicPaperList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	
	/**
	 * 查询列表(返回List<Map<String, Object>)
	 * @param Map
	 * @return R
	 */
	@SysLog(value="查询列表(返回List<Map<String, Object>)")
	@GetMapping("/queryForMap")
	@SentinelResource("/examine/tevglexaminedynamicpaper/queryForMap")
	public R queryForMap(@RequestParam Map<String, Object> params) {
		// 构建查询条件对象Query
		Query query = new Query(params);
		PageHelper.startPage(query.getPage(),query.getLimit());
		List<Map<String, Object>> tevglExamineDynamicPaperList = tevglExamineDynamicPaperMapper.selectListMapByMap(query);
		PageUtils pageUtil = new PageUtils(tevglExamineDynamicPaperList,query.getPage(),query.getLimit());
		return R.ok().put(Constant.R_DATA, pageUtil);
	}
	/**
	 * 新增
	 * @param tevglExamineDynamicPaper
	 * @throws OssbarException
	 */
	@SysLog(value="新增")
	@PostMapping("save")
	@SentinelResource("/examine/tevglexaminedynamicpaper/save")
	public R save(@RequestBody(required = false) TevglExamineDynamicPaper tevglExamineDynamicPaper) throws OssbarException {
		tevglExamineDynamicPaper.setDyId(Identities.uuid());
		ValidatorUtils.check(tevglExamineDynamicPaper);
		tevglExamineDynamicPaperMapper.insert(tevglExamineDynamicPaper);
		return R.ok();
	}
	/**
	 * 修改
	 * @param tevglExamineDynamicPaper
	 * @throws OssbarException
	 */
	@SysLog(value="修改")
	@PostMapping("update")
	@SentinelResource("/examine/tevglexaminedynamicpaper/update")
	public R update(@RequestBody(required = false) TevglExamineDynamicPaper tevglExamineDynamicPaper) throws OssbarException {
	    ValidatorUtils.check(tevglExamineDynamicPaper);
		tevglExamineDynamicPaperMapper.update(tevglExamineDynamicPaper);
		return R.ok();
	}
	/**
	 * 单条删除
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="单条删除")
	@GetMapping("delete/{id}")
	@SentinelResource("/examine/tevglexaminedynamicpaper/delete")
	public R delete(@PathVariable("id") String id) throws OssbarException {
		tevglExamineDynamicPaperMapper.delete(id);
		return R.ok();
	}
	/**
	 * 批量删除
	 * @param ids
	 * @throws OssbarException
	 */
	@SysLog(value="批量删除")
	@PostMapping("deleteBatch")
	@SentinelResource("/examine/tevglexaminedynamicpaper/deleteBatch")
	public R deleteBatch(@RequestParam(required = true) String[] ids) throws OssbarException {
		tevglExamineDynamicPaperMapper.deleteBatch(ids);
		return R.ok();
	}
	/**
	 * 查看明细
	 * @param id
	 * @throws OssbarException
	 */
	@SysLog(value="查看明细")
	@GetMapping("view/{id}")
	@SentinelResource("/examine/tevglexaminedynamicpaper/view")
	public R view(@PathVariable("id") String id) {
		return R.ok().put(Constant.R_DATA, tevglExamineDynamicPaperMapper.selectObjectById(id));
	}
}
